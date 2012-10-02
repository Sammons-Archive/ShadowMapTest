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

    private Entity teapotEntity;
    private Node node;
    private ShadowMapRenderBuffer shadow;
    private RenderUpdater renderUpdater;
    private ColorRGBA teapotColor = null;
    private float teapotCreationCoordX = 0, teapotCreationCoordY = 0, teapotCreationCoordZ = 0;

    public TeapotBuilder(ShadowMapRenderBuffer shadow, RenderUpdater renderUpdater) {
        this.shadow = shadow;
        this.renderUpdater = renderUpdater;
    }

    public void buildTeapot(WorldManager worldManager, float coordX, float coordY, float coordZ) {
        teapotInitializer(worldManager, teapotColor, coordX, coordY, coordZ);
        worldManager.addEntity(teapotEntity);
    }

    public void buildRotatingTeapot(WorldManager worldManager, float coordX, float coordY, float coordZ) {
        teapotInitializer(worldManager, teapotColor, coordX, coordY, coordZ);
        RotationProcessor rp = new RotationProcessor("Teapot Rotator", worldManager,
                node, (float) (6.0f * Math.PI / 180.0f));
        teapotEntity.addComponent(ProcessorComponent.class, rp);
        worldManager.addEntity(teapotEntity);
    }

    public void buildTeapot(WorldManager worldManager) {
        teapotInitializer(worldManager, teapotColor, teapotCreationCoordX, teapotCreationCoordY, teapotCreationCoordZ);
        worldManager.addEntity(teapotEntity);
    }

    public void buildRotatingTeapot(WorldManager worldManager) {
        teapotInitializer(worldManager, teapotColor, teapotCreationCoordX, teapotCreationCoordY, teapotCreationCoordZ);
        RotationProcessor rp = new RotationProcessor("Teapot Rotator", WorldManager.getDefaultWorldManager(),
                node, (float) (6.0f * Math.PI / 180.0f));
        teapotEntity.addComponent(ProcessorComponent.class, rp);
        worldManager.addEntity(teapotEntity);
    }

    public void buildTeapot(WorldManager worldManager, ColorRGBA colorRGBA) {
        teapotInitializer(worldManager, colorRGBA, teapotCreationCoordX, teapotCreationCoordY, teapotCreationCoordZ);
        worldManager.addEntity(teapotEntity);
    }

    public void buildRotatingTeapot(WorldManager worldManager, ColorRGBA colorRGBA) {
        teapotInitializer(worldManager, colorRGBA, teapotCreationCoordX, teapotCreationCoordY, teapotCreationCoordZ);
        RotationProcessor rp = new RotationProcessor("Teapot Rotator", WorldManager.getDefaultWorldManager(),
                node, (float) (6.0f * Math.PI / 180.0f));
        teapotEntity.addComponent(ProcessorComponent.class, rp);
        worldManager.addEntity(teapotEntity);
    }

    public void buildTeapot(WorldManager worldManager, ColorRGBA colorRGBA, float coordX, float coordY, float coordZ) {
        teapotInitializer(worldManager, colorRGBA, coordX, coordY, coordZ);
        worldManager.addEntity(teapotEntity);
    }

    public void buildRotatingTeapot(WorldManager worldManager, ColorRGBA colorRGBA, float coordX, float coordY, float coordZ) {
        teapotInitializer(worldManager, colorRGBA, coordX, coordY, coordZ);
        RotationProcessor rp = new RotationProcessor("Teapot Rotator", WorldManager.getDefaultWorldManager(),
                node, (float) (6.0f * Math.PI / 180.0f));
        teapotEntity.addComponent(ProcessorComponent.class, rp);
        worldManager.addEntity(teapotEntity);
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
        MaterialState matState;
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

        teapotEntity = new Entity("Teapot");
        RenderComponent sc = worldManager.getRenderManager().createRenderComponent(node);
        teapotEntity.addComponent(RenderComponent.class, sc);
        

        shadow.addRenderScene(sc);
        shadow.setRenderUpdater(renderUpdater);
    }
}
