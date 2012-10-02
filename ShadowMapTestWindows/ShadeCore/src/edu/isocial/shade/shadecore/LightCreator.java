/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.light.DirectionalLight;
import com.jme.light.LightNode;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public class LightCreator {

    private LightProcessor lightProcessor;
    private ShadowMapRenderBufferMaker shadowCaster;
    private ShadowMapRenderBuffer shadow;
    private float lightCoordX = 50, lightCoordY = 50, lightCoordZ = 50;
    private Vector3f direction;
    private Vector3f position;
    private LightNode lightNode;
    private Entity lightRotatorEntity;

    public LightCreator(WorldManager worldManager) {
        buildLight(worldManager);
    }

    private void buildLight(WorldManager worldManager) {
        //setting different direction coords does not have a result
        direction = new Vector3f(0f, 0f, 0f);
        position = new Vector3f(lightCoordX, lightCoordY, lightCoordZ);
        DirectionalLight light = new DirectionalLight();
        light.setEnabled(true);
        lightNode = new LightNode();
        lightNode.setLight(light);
        lightNode.setLocalTranslation(position);
        worldManager.getRenderManager().addLight(lightNode);
        shadowCaster = new ShadowMapRenderBufferMaker(worldManager, direction, position);
        shadow = shadowCaster.getShadow();
        lightProcessor = new LightProcessor(worldManager, lightNode, shadowCaster.getShadow(),
                (float) (1.0f * Math.PI / 120.0f));
        lightProcessor.setLightPosition(position);
        lightRotatorEntity = new Entity("Light Rotator");
        lightRotatorEntity.addComponent(ProcessorComponent.class, lightProcessor);

        //by default make the light not spin
        setLightRotation(true, worldManager);

    }

    public void setLightRotation(boolean lightRotation, WorldManager worldManager) {
        for (int i = 0; i < worldManager.numEntities(); i++) {
            if ((worldManager.getEntity(i).hasComponent(LightProcessor.class))) {
                if (!(lightRotation)) {
                    worldManager.removeEntity(worldManager.getEntity(i));
                }

            }
        }
        if (lightRotation) {
            lightRotatorEntity = new Entity("Light Rotator");
            lightRotatorEntity.addComponent(ProcessorComponent.class, lightProcessor);
            worldManager.addEntity(lightRotatorEntity);
        }

    }

    public void setLightPosition(float lightCoordX, float lightCoordY, float lightCoordZ, WorldManager worldManager) {
        position = new Vector3f(lightCoordX, lightCoordY, lightCoordZ);
        worldManager.getRenderManager().getLight(0).setLocalTranslation(position);
    }

    public ShadowMapRenderBufferMaker getShadowCaster() {
        return shadowCaster;
    }

    public ShadowMapRenderBuffer getShadow() {
        return shadow;
    }
}
