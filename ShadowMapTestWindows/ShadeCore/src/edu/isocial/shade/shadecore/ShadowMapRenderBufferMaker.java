/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public class ShadowMapRenderBufferMaker {

    private static ShadowMapRenderBuffer shadow;
    private Vector3f dir, pos;
    private WorldManager worldManager;

    public ShadowMapRenderBufferMaker(WorldManager worldManager, Vector3f dir, Vector3f pos) {
        this.worldManager = worldManager;
        this.dir = dir;
        this.pos = pos;
        createShadowBuffer();
    }

    private void createShadowBuffer() {
        int shadowWidth = 2048;
        int shadowHeight = 2048;
        Node shadowDebug = new Node("Shadow Debug");
        Quad shadowImage = new Quad("Shadow Quad", shadowWidth, shadowHeight);
        Entity e = new Entity("Shadow Debug ");

        shadowDebug.attachChild(shadowImage);
        shadowDebug.setLocalTranslation(new Vector3f(0.0f, 0.0f, -350.0f));

        shadow = (ShadowMapRenderBuffer) worldManager.getRenderManager().createRenderBuffer(RenderBuffer.Target.SHADOWMAP, shadowWidth, shadowHeight);
        shadow.setCameraLookAt(new Vector3f());
        shadow.setCameraUp(new Vector3f(-1.0f, 1.0f, -1.0f));
        shadow.setCameraPosition(pos);
        shadow.setManageRenderScenes(true);

        worldManager.getRenderManager().addRenderBuffer(shadow);

        ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        shadowDebug.setRenderState(buf);

        TextureState ts = (TextureState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Texture);
        ts.setEnabled(true);
        ts.setTexture(shadow.getTexture(), 0);
        shadowDebug.setRenderState(ts);

        RenderComponent shadowDebugRC = worldManager.getRenderManager().createRenderComponent(shadowDebug);
        shadowDebugRC.setLightingEnabled(false);
        e.addComponent(RenderComponent.class, shadowDebugRC);
        //wm.addEntity(e);      
    }

    public static ShadowMapRenderBuffer getShadow() {
        return shadow;
    }
}
