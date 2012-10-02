/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.CameraNode;
import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.jdesktop.mtgame.FrameRateListener;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.RenderUpdater;
import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//edu.isocial.shade.shadecore//Main//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "MainTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "edu.isocial.shade.shadecore.MainTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_MainAction",
preferredID = "MainTopComponent")
@Messages({
    "CTL_MainAction=Main",
    "CTL_MainTopComponent=Main Window",
    "HINT_MainTopComponent=This is a Main window"
})
public final class MainTopComponent extends TopComponent implements RenderUpdater {

    private WorldManager worldManager;
    private int desiredFrameRate = 60;
    private int width = 800;
    private int height = 600;
    private float aspect = 800.0f / 600.0f;
    private boolean coordsOn = true;
    private boolean gridOn = true;
    private boolean lightRotateOn = true;
    private int gridWidth = 250;
    private ArrayList models = new ArrayList();
    private ShadowMapRenderBuffer shadow;
    private RenderBuffer renderBuffer;
    private CanvasBuilder canvasBuilder = null;
    private CameraBuilder cameraBuilder;
    private GridBuilder gridBuilder;
    private Axis axis;
    private LightCreator light;
    private TeapotBuilder teapotBuilder;
    private FloorBuilder floorBuilder;

    public MainTopComponent() {
        initComponents();
        setName(Bundle.CTL_MainTopComponent());
        setToolTipText(Bundle.HINT_MainTopComponent());

        construct();
            }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JPanel();
        btnRemoveCanvas = new javax.swing.JButton();
        btnSpinTeapot = new javax.swing.JButton();
        comboColors = new javax.swing.JComboBox();
        btnColor = new javax.swing.JButton();
        textX = new javax.swing.JTextField();
        textY = new javax.swing.JTextField();
        textZ = new javax.swing.JTextField();
        btnToggleLightSpin = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1000, 600));

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(btnRemoveCanvas, org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.btnRemoveCanvas.text")); // NOI18N
        btnRemoveCanvas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCanvasActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnSpinTeapot, org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.btnSpinTeapot.text")); // NOI18N
        btnSpinTeapot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpinTeapotActionPerformed(evt);
            }
        });

        comboColors.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Green", "Blue", "Black", "Yellow", " " }));

        org.openide.awt.Mnemonics.setLocalizedText(btnColor, org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.btnColor.text")); // NOI18N

        textX.setText(org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.textX.text")); // NOI18N

        textY.setText(org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.textY.text")); // NOI18N

        textZ.setText(org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.textZ.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnToggleLightSpin, org.openide.util.NbBundle.getMessage(MainTopComponent.class, "MainTopComponent.btnToggleLightSpin.text")); // NOI18N
        btnToggleLightSpin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToggleLightSpinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemoveCanvas)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSpinTeapot)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addComponent(textY, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textZ, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addComponent(btnToggleLightSpin)
                    .addComponent(btnColor))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRemoveCanvas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpinTeapot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToggleLightSpin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveCanvasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCanvasActionPerformed
        // TODO add your handling code here:
        canvasBuilder.kill(mainPane, worldManager);
    }//GEN-LAST:event_btnRemoveCanvasActionPerformed

    private void btnSpinTeapotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpinTeapotActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSpinTeapotActionPerformed

    private void btnToggleLightSpinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleLightSpinActionPerformed
        if (lightRotateOn) {
            lightRotateOn = false;
            light.setLightRotation(lightRotateOn, worldManager);
        } else {
            lightRotateOn = true;
            light.setLightRotation(lightRotateOn, worldManager);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnToggleLightSpinActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColor;
    private javax.swing.JButton btnRemoveCanvas;
    private javax.swing.JButton btnSpinTeapot;
    private javax.swing.JButton btnToggleLightSpin;
    private javax.swing.JComboBox comboColors;
    private javax.swing.JPanel mainPane;
    private javax.swing.JTextField textX;
    private javax.swing.JTextField textY;
    private javax.swing.JTextField textZ;
    // End of variables declaration//GEN-END:variables

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
        teapotBuilder.buildRotatingTeapot(worldManager, ColorRGBA.green);
        //teapotBuilder.buildTeapot(worldManager);

        floorBuilder = new FloorBuilder();
        floorBuilder.createFloor(worldManager, shadow);
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
        Runtime.getRuntime().exit(0);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void update(Object object) {
        shadow = (ShadowMapRenderBuffer) object;
    }
}