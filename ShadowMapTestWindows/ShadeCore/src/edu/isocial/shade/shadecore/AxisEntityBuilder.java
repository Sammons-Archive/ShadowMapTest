/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.scene.Node;
import com.jme.scene.shape.AxisRods;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import edu.isocial.shade.shadeCoreAPI.GenericBuilderImpl;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.WorldManager;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Ben
 */
@ServiceProvider(service=GenericBuilderImpl.class)
public class AxisEntityBuilder implements GenericBuilderImpl{
    private static Entity axis = new Entity("Axis");
  
    
    public AxisEntityBuilder() {
    }

        
    public void buildAxis(WorldManager worldManager) {
                ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        Node axisSG = new Node("Axis");
        AxisRods axisG = new AxisRods("Axis", true, 10.0f, 0.2f);
        axisSG.attachChild(axisG);
        axisSG.setRenderState(buf);

        RenderComponent rc = worldManager.getRenderManager().createRenderComponent(axisSG);
        rc.setLightingEnabled(false);
        axis.addComponent(RenderComponent.class, rc);
        worldManager.addEntity(axis);
    }

    @Override
    public Entity getMyEntity() {
        return axis;
    }
        
        
}
