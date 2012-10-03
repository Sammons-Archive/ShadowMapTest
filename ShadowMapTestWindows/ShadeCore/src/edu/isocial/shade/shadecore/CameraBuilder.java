/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.scene.CameraNode;
import com.jme.scene.Node;
import org.jdesktop.mtgame.AWTInputComponent;
import org.jdesktop.mtgame.CameraComponent;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.InputManager;
import org.jdesktop.mtgame.ProcessorCollectionComponent;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.mtgame.processor.OrbitCameraProcessor;

/**
 *
 * @author Ben
 */
public class CameraBuilder {

    private WorldManager worldManager;
    private CameraComponent cameraComponent;
    private CameraNode cameraNode = null;
    private int width, height;
    private Node cameraSG;
    private Entity camera;
    private RenderBuffer renderBuffer;
    private float aspectRatio;
    private int eventMask;
    private AWTInputComponent cameraListener;
    private OrbitCameraProcessor eventProcessor;
    private AWTInputComponent selectionListener;
    private ProcessorCollectionComponent processorCollectionComp;

    public CameraBuilder(WorldManager worldManager, float aspectRatio) {
        initialize(worldManager, aspectRatio);
    }

    private void initialize(WorldManager worldManager, float aspectRatio) {
        this.worldManager = worldManager;
        this.aspectRatio = aspectRatio;

    }

    public void buildCamera(CanvasBuilder content) {
        this.width = content.getRenderingCanvas().getWidth();
        this.height = content.getRenderingCanvas().getHeight();
        this.renderBuffer = content.getRenderBuffer();
        cameraSG = createCameraGraph();
        camera = new Entity("Default Camera");
        cameraComponent = worldManager.getRenderManager().createCameraComponent(cameraSG, cameraNode, width, height, 45.0f, aspectRatio, 1.0f, 1000.0f, true);
        content.getRenderBuffer().setCameraComponent(cameraComponent);
        camera.addComponent(CameraComponent.class, cameraComponent);
        eventMask = InputManager.KEY_EVENTS | InputManager.MOUSE_EVENTS;
        cameraListener = (AWTInputComponent) worldManager.getInputManager().createInputComponent(content.getRenderingCanvas(), eventMask);

        eventProcessor = new OrbitCameraProcessor(cameraListener, cameraNode, worldManager, camera);
        eventProcessor.setRunInRenderer(true);
        selectionListener = (AWTInputComponent) worldManager.getInputManager().createInputComponent(content.getRenderingCanvas(), eventMask);

        ClickSelectionProcessor clicker = new ClickSelectionProcessor(selectionListener, worldManager, camera, camera, width, height, eventProcessor);
        clicker.setRunInRenderer(true);
        processorCollectionComp = new ProcessorCollectionComponent();
        processorCollectionComp.addProcessor(eventProcessor);
        processorCollectionComp.addProcessor(clicker);
        camera.addComponent(ProcessorCollectionComponent.class, processorCollectionComp);
        worldManager.addEntity(camera);
    }

    private Node createCameraGraph() {
        cameraSG = new Node("MyCamera SG");
        cameraNode = new CameraNode("MyCamera", null);
        cameraSG.attachChild(cameraNode);
        return (cameraSG);
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public RenderBuffer getRenderBuffer() {
        return renderBuffer;
    }
}
