/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.light.DirectionalLight;
import com.jme.light.LightNode;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import edu.isocial.shade.shadeCoreAPI.GenericBuilderImpl;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Ben
 */

public class LightNodeBuilder{

    private LightProcessorBuilder lightProcessor;
    private ShadowMapRenderBufferMaker shadowCaster;
    private ShadowMapRenderBuffer shadow;
    private float lightCoordX = 50, lightCoordY = 50, lightCoordZ = 50;
    private Vector3f direction;
    private Vector3f position;
    private static LightNode lightNode;
    private static Entity lightRotatorEntity;
    private boolean rotation;

    public LightNodeBuilder() {
    }

    public void buildLight(WorldManager worldManager) {
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
        lightProcessor = new LightProcessorBuilder(worldManager, lightNode, shadowCaster.getShadow(),
                (float) (1.0f * Math.PI / 120.0f));
        lightProcessor.setLightPosition(position);
        lightRotatorEntity = new Entity("Light Rotator" + ":" + worldManager.numEntities());
        lightRotatorEntity.addComponent(ProcessorComponent.class, lightProcessor);

        //by default make the light spin
        setLightRotation(true, worldManager);

    }

    public void setLightRotation(boolean rotationState, WorldManager worldManager) {
        if (!(rotationState)) {
            lightRotatorEntity.removeComponent(ProcessorComponent.class);
        } else if (rotationState) {
            //WorldManager.getDefaultWorldManager().removeEntity(lightRotatorEntity);
            lightRotatorEntity.addComponent(ProcessorComponent.class, lightProcessor);
            worldManager.addEntity(lightRotatorEntity);
        }

    }

//    public void setLightPosition(float lightCoordX, float lightCoordY, float lightCoordZ, WorldManager worldManager) {
//       //set new position
//        position = new Vector3f(lightCoordX, lightCoordY, lightCoordZ);
//        //change out lightnode with new updated one
//        worldManager.getRenderManager().removeLight(worldManager.getRenderManager().getLight(0));
//        lightNode.setLocalTranslation(position);
//        worldManager.getRenderManager().addLight(lightNode);
//        //change out lightprocessor with fresh one constructed with the updated lightnode and the new position
//        worldManager.removeEntity(lightRotatorEntity);
//        lightProcessor = new LightProcessorBuilder(worldManager, lightNode, shadowCaster.getShadow(),
//                (float) (1.0f * Math.PI / 120.0f));
//        lightProcessor.setLightPosition(position);
//        lightRotatorEntity.addComponent(ProcessorComponent.class, lightProcessor);
//        //process the method for turning on/off light rotation so the rotation status does not change from what it was before
//        setLightRotation(rotation, worldManager);
//
//    }

    public ShadowMapRenderBufferMaker getShadowCaster() {
        return shadowCaster;
    }

    public ShadowMapRenderBuffer getShadow() {
        return shadow;
    }


}
