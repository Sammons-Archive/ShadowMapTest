/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.bounding.BoundingBox;
import com.jme.math.Triangle;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Teapot;
import com.jme.scene.shape.*;
import com.jme.scene.state.CullState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import java.awt.Color;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.JMECollisionComponent;
import org.jdesktop.mtgame.JMECollisionSystem;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.RenderUpdater;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.mtgame.processor.RotationProcessor;

/**
 *
 * @author Ben
 */
public class TeapotBuilder {

    private Entity shape;
    private Node node;
    private ShadowMapRenderBuffer shadow;
    private RenderUpdater renderUpdater;
    private ColorRGBA teapotColor = new ColorRGBA();
    private float teapotCreationCoordX = 0, teapotCreationCoordY = 0, teapotCreationCoordZ = 0;
    private MaterialState matState;
    private RenderComponent renderComponent;
    private boolean spinState = false;

    public TeapotBuilder(ShadowMapRenderBuffer shadow, RenderUpdater renderUpdater) {
        this.shadow = shadow;
        this.renderUpdater = renderUpdater;
    }

    public void buildTeapot(WorldManager worldManager, float coordX, float coordY, float coordZ) {
        teapotInitializer(worldManager, teapotColor, coordX, coordY, coordZ);
        worldManager.addEntity(shape);
    }

    

    private void teapotInitializer(WorldManager worldManager, ColorRGBA colorRGBA, float posx, float posy, float posz) {
        node = new Node();
        Teapot teapot = new Teapot();
        teapot.updateGeometryData();
        node.attachChild(teapot);
        node.setLocalScale(3.0f);
        JMECollisionSystem collisionSystem = (JMECollisionSystem) worldManager.getCollisionManager().loadCollisionSystem(JMECollisionSystem.class);
        JMECollisionComponent collisionComp = collisionSystem.createCollisionComponent(node);

        Triangle[] tris = new Triangle[teapot.getTriangleCount()];

        BoundingBox bbox = new BoundingBox();
        bbox.computeFromTris(teapot.getMeshAsTriangles(tris), 0, tris.length);

        //make the teapot a specific color
        if (colorRGBA != null) {
            matState = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
            matState.setDiffuse(colorRGBA);
        } else {
            matState = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
            matState.setDiffuse(new ColorRGBA());
        }
        //add rest of renderStates
        ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        CullState cs = (CullState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Cull);
        cs.setEnabled(true);
        cs.setCullFace(CullState.Face.Back);

        node.setRenderState(matState);
        node.setRenderState(buf);
        node.setLocalTranslation(posx, posy, posz);
        teapot.setModelBound(bbox);
        teapot.setCullHint(Spatial.CullHint.Never);

        shape = new Entity("Entity Teapot: " + worldManager.numEntities());
        renderComponent = worldManager.getRenderManager().createRenderComponent(node);
        shape.addComponent(RenderComponent.class, renderComponent);


        shadow.addRenderScene(renderComponent);
        shadow.setRenderUpdater(renderUpdater);
    }

    public void setTeapotColor(WorldManager worldManager, ColorRGBA colorRGBA) {
        //the number the item is in the worldManager is in its name as string, i pull it out here and grab the item
        int index = (char) shape.getName().indexOf(":");
        int numInWorldManager = Integer.valueOf(shape.getName().substring(index + 1).replaceAll(" ", ""));

        shadow.removeRenderScene(worldManager.getEntity(numInWorldManager).getComponent(RenderComponent.class));

        matState = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
        matState.setDiffuse(colorRGBA);

        worldManager.getEntity(numInWorldManager).removeComponent(RenderComponent.class);
        node.setRenderState(matState);
        renderComponent = worldManager.getRenderManager().createRenderComponent(node);
        worldManager.getEntity(numInWorldManager).addComponent(RenderComponent.class, renderComponent);

        shadow.addRenderScene(renderComponent);

    }

    public void setTeapotSpin(WorldManager worldManager, boolean spinState) {
        this.spinState = spinState;
        int index = (char) shape.getName().indexOf(":");
        int numInWorldManager = Integer.valueOf(shape.getName().substring(index + 1).replaceAll(" ", ""));
        if (spinState) {
            worldManager.getEntity(numInWorldManager).removeComponent(ProcessorComponent.class);
            RotationProcessor rotationProcessor = new RotationProcessor("Rotator for Entity:" + "numInWordManager", WorldManager.getDefaultWorldManager(),
                    node, (float) (6.0f * Math.PI / 180.0f));
            worldManager.getEntity(numInWorldManager).addComponent(ProcessorComponent.class, rotationProcessor);
        } else {
            worldManager.getEntity(numInWorldManager).removeComponent(ProcessorComponent.class);
        }
    }
    

    public void setTeapotLocation(WorldManager worldManager, float x, float y, float z) {
        //the number the item is in the worldManager is in its name as string, i pull it out here and grab the item
        int index = (char) shape.getName().indexOf(":");
        int numInWorldManager = Integer.valueOf(shape.getName().substring(index + 1).replaceAll(" ", ""));
        shadow.removeRenderScene(worldManager.getEntity(numInWorldManager).getComponent(RenderComponent.class));
        worldManager.getEntity(numInWorldManager).removeComponent(RenderComponent.class);
        node.setLocalTranslation(x, y, z);
        //node.setLocalTranslation(posx, posy, posz);
        renderComponent = worldManager.getRenderManager().createRenderComponent(node);
        worldManager.getEntity(numInWorldManager).addComponent(RenderComponent.class, renderComponent);
        shadow.addRenderScene(renderComponent);
    }
}