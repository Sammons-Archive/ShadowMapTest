/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public class FloorBuilder {

    private Quad quadGeo;
    private RenderComponent orthoRC;

    public FloorBuilder() {
    }

    public void createFloor(WorldManager worldManager, ShadowMapRenderBuffer shadow) {
        Node orthoQuad = new Node();
        quadGeo = new Quad("Ortho", 100, 100);
        Entity e = new Entity("Ortho ");

        orthoQuad.attachChild(quadGeo);
        Quaternion q = new Quaternion();
        q.fromAngleAxis((float) (Math.PI / 2.0), new Vector3f(1.0f, 0.0f, 0.0f));
        orthoQuad.setLocalRotation(q);

        ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        orthoQuad.setRenderState(buf);

        TextureState ts = (TextureState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Texture);
        ts.setEnabled(true);
        ts.setTexture(shadow.getTexture(), 0);
        quadGeo.setRenderState(ts);

        BlendState discardShadowFragments = (BlendState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Blend);
        discardShadowFragments.setEnabled(true);
        discardShadowFragments.setBlendEnabled(true);
        discardShadowFragments
                .setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        discardShadowFragments
                .setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        quadGeo.setRenderState(discardShadowFragments);

        MaterialState darkMaterial = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
        darkMaterial.setEnabled(true);
        darkMaterial.setDiffuse(new ColorRGBA(0, 0, 0, 0.3f));
        darkMaterial.setAmbient(new ColorRGBA(0, 0, 0, 0f));
        darkMaterial.setShininess(0);
        darkMaterial.setSpecular(new ColorRGBA(0, 0, 0, 0));
        darkMaterial.setEmissive(new ColorRGBA(0, 0, 0, 0));
        darkMaterial.setMaterialFace(MaterialState.MaterialFace.Front);
        quadGeo.setRenderState(darkMaterial);

        orthoRC = worldManager.getRenderManager().createRenderComponent(orthoQuad);
        e.addComponent(RenderComponent.class, orthoRC);
        worldManager.addEntity(e);
    }
}
