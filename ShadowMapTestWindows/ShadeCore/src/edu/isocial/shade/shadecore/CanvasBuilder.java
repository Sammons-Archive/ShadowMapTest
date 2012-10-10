/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.mtgame.FrameRateListener;
import org.jdesktop.mtgame.OnscreenRenderBuffer;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public class CanvasBuilder implements FrameRateListener {

    private Canvas renderingCanvas;
    private RenderBuffer renderBuffer;
    private int width = 800, height = 600;
    private JLabel fpsLabel;

    public CanvasBuilder() {
    }

    public void buildCanvas(JPanel pane, WorldManager worldManager) {
        //prepare fpsLabel
        prepareFPSLabel(worldManager);
        pane.add(fpsLabel);

        //prepareCanvas
        renderBuffer = worldManager.getRenderManager().createRenderBuffer(RenderBuffer.Target.ONSCREEN, width, height);
        worldManager.getRenderManager().addRenderBuffer(renderBuffer);
        renderingCanvas = ((OnscreenRenderBuffer) renderBuffer).getCanvas();
        renderingCanvas.setBounds(0, 0, width, height);
        renderingCanvas.setVisible(true);
        pane.add(renderingCanvas);

    }

    private void prepareFPSLabel(WorldManager worldManager) {
        fpsLabel = new JLabel();
        fpsLabel.setSize(125, 25);
        fpsLabel.setLocation(width - 125, height - 25);
        fpsLabel.setOpaque(true);
        fpsLabel.setBackground(Color.BLACK);
        fpsLabel.setForeground(Color.WHITE);
        worldManager.getRenderManager().setFrameRateListener(this, 100);
    }

    public RenderBuffer getRenderBuffer() {
        return renderBuffer;
    }

    public Canvas getRenderingCanvas() {
        return renderingCanvas;
    }

    @Override
    public void currentFramerate(float f) {
        fpsLabel.setText("FPS: " + f);

    }
//    public void kill(JPanel mainPane, WorldManager worldManager) {
//        worldManager.getRenderManager().setRunning(false);
////        mainPane.remove(renderingCanvas);
////        mainPane.remove(fpsLabel);
////        renderBuffer = null;
////        renderingCanvas = null;
//    }
}
