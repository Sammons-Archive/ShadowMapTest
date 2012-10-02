/*
 * Copyright (c) 2009, Sun Microsystems, Inc. All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *  . Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  . Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *  . Neither the name of Sun Microsystems, Inc., nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.isocial.shade.shadecore;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.CameraNode;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import org.jdesktop.mtgame.*;

/**
 * A World test application
 *
 * @author Doug Twilleager
 */
public class ShadowMapCore implements RenderUpdater {

    private WorldManager worldManager;
    private CameraNode cameraNode;
    private int desiredFrameRate = 60;
    private int width = 800;
    private int height = 600;
    private float aspect = 800.0f / 600.0f;
    private boolean coordsOn = true;
    private boolean gridOn = true;
    private int gridWidth = 250;
    private ArrayList models = new ArrayList();
    private JRootPane renderingPane;
    private ShadowMapRenderBuffer shadow;
    private RenderBuffer renderBuffer;
    private CanvasBuilder canvasBuilder = null;
    private CameraBuilder cameraBuilder;
    private GridBuilder gridBuilder;
    private Axis axis;
    private LightCreator light;
    private TeapotBuilder teapotBuilder;
    private FloorBuilder floorBuilder;
    private JPanel mainPane;
    
    public ShadowMapCore(JPanel mainPane) {
        this.mainPane = mainPane;
        construct();
    }

    private void construct() {
   
        new Thread(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        }).start();
    }

    
    
    private void initialize() {

        worldManager = new WorldManager("TestWorld");
        worldManager.getRenderManager().setDesiredFrameRate(desiredFrameRate);

        canvasBuilder = new CanvasBuilder(worldManager, width, height);
        canvasBuilder.buildCanvas(mainPane);
        renderBuffer = canvasBuilder.getRenderBuffer();

        cameraBuilder = new CameraBuilder(worldManager, renderBuffer, width, height, aspect);
        cameraBuilder.buildCamera(canvasBuilder);
        renderBuffer = cameraBuilder.getRenderBuffer();

        gridBuilder = new GridBuilder(worldManager, gridWidth);
        worldManager.addEntity(gridBuilder.getGrid());
        
        axis = new Axis(worldManager);
        worldManager.addEntity(axis.getAxis());

        light = new LightCreator(worldManager);
        shadow = light.getShadow();

        teapotBuilder = new TeapotBuilder(shadow, this);
        //teapotBuilder.buildRotatingTeapot(worldManager);
        //teapotBuilder.buildRotatingTeapot(worldManager, ColorRGBA.green);
        teapotBuilder.buildTeapot(worldManager);
        
        floorBuilder = new FloorBuilder();
        floorBuilder.createFloor(worldManager, shadow);
        
        

    }

    public void kill(JPanel mainPane) {
        canvasBuilder.kill(mainPane, worldManager);
    }
    
    @Override
    public void update(Object object) {
        shadow = (ShadowMapRenderBuffer) object;
    }

}
