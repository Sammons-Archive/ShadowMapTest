/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.math.Vector3f;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public class GridBuilder {

    private WorldManager worldManager;
    private int gridWidth;
    private Entity grid = new Entity("Grid");

    public GridBuilder(WorldManager worldManager, int gridWidth) {
        initialize(worldManager, gridWidth);
        createGrid();
    }

    private void initialize(WorldManager worldManager, int gridWidth) {
        this.worldManager = worldManager;
        this.gridWidth = gridWidth;
    }

    private void createGrid() {
        float startx = 0.0f, startz = 0.0f;
        float endx = 0.0f, endz = 0.0f;

        int numLines = (gridWidth / 5) * 2 + 2;
        Vector3f[] points = new Vector3f[numLines * 2];
        int numSegs = numLines / 2;

        // Start with the Z lines
        startx = -gridWidth / 2.0f;
        startz = -gridWidth / 2.0f;
        endx = -gridWidth / 2.0f;
        endz = gridWidth / 2.0f;
        int pointNum = 0;
        for (int i = 0; i < numSegs; i++) {
            points[pointNum++] = new Vector3f(startx, 0.0f, startz);
            points[pointNum++] = new Vector3f(endx, 0.0f, endz);
            startx += 5.0f;
            endx += 5.0f;
        }

        // Now the Z lines
        startx = -gridWidth / 2.0f;
        startz = -gridWidth / 2.0f;
        endx = gridWidth / 2.0f;
        endz = -gridWidth / 2.0f;
        for (int i = 0; i < numSegs; i++) {
            points[pointNum++] = new Vector3f(startx, 0.0f, startz);
            points[pointNum++] = new Vector3f(endx, 0.0f, endz);
            startz += 5.0f;
            endz += 5.0f;
        }
        
        //create actual entity
        ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        Node gridSG = new Node("Grid");
        Line gridG = new Line("Grid", points, null, null, null);
        gridSG.attachChild(gridG);
        gridSG.setRenderState(buf);

        RenderComponent rc = worldManager.getRenderManager().createRenderComponent(gridSG);
        rc.setLightingEnabled(false);
        grid.addComponent(RenderComponent.class, rc);
    }

    public Entity getGrid() {
        return grid;
    }
}