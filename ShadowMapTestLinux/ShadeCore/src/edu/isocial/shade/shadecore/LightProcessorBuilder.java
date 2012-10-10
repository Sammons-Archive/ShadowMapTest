
package edu.isocial.shade.shadecore;

import com.jme.light.LightNode;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import org.jdesktop.mtgame.NewFrameCondition;
import org.jdesktop.mtgame.ProcessorArmingCollection;
import org.jdesktop.mtgame.ProcessorComponent;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;

public class LightProcessorBuilder extends ProcessorComponent {

    private WorldManager worldManager = null;
    private float degrees = 0.0f;
    private float increment = 0.0f;
    private Quaternion quaternion = new Quaternion();
    private LightNode target = null;
    private ShadowMapRenderBuffer smb = null;
    private Vector3f position = new Vector3f(50.0f, 50.0f, 50.0f);
    private Vector3f positionOut = new Vector3f(50.0f, 50.0f, 50.0f);
    private Vector3f up = new Vector3f(-1.0f, 1.0f, -1.0f);
    private Vector3f upOut = new Vector3f(-1.0f, 1.0f, -1.0f);

    public LightProcessorBuilder(WorldManager worldManager, LightNode ln, ShadowMapRenderBuffer sb,
            float increment) {
        this.worldManager = worldManager;
        target = ln;
        this.increment = increment;
        smb = sb;
        setArmingCondition(new NewFrameCondition(this));
    }
    //note that if you set both position and positionOut the 
    //light will spin faster exponentially(?), then slow down
    public void setLightPosition(Vector3f position) {
        this.position = position;
    }

    @Override
    public void compute(ProcessorArmingCollection collection) {
        degrees += increment;
        quaternion.fromAngles(0.0f, degrees, 0.0f);
        quaternion.mult(up, upOut);
        quaternion.mult(position, positionOut);
    }

    @Override
    public void commit(ProcessorArmingCollection collection) {
        target.setLocalTranslation(positionOut);
        worldManager.addToUpdateList(target);
        smb.setCameraPosition(positionOut);
        //smb.setCameraUp(upOut);
    }

    @Override
    public void initialize() {
    }
}
