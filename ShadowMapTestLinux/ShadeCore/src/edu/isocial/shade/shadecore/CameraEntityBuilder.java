/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadecore;

import com.jme.scene.CameraNode;
import com.jme.scene.Node;
import edu.isocial.shade.shadeCoreAPI.GenericBuilderImpl;
import org.jdesktop.mtgame.AWTInputComponent;
import org.jdesktop.mtgame.CameraComponent;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.InputManager;
import org.jdesktop.mtgame.ProcessorCollectionComponent;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.mtgame.processor.OrbitCameraProcessor;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Ben
 */
@ServiceProvider(service = GenericBuilderImpl.class)
public class CameraEntityBuilder implements GenericBuilderImpl {

    private WorldManager worldManager;
    private CameraComponent cameraComponent;
    private CameraNode cameraNode = null;
    private Node cameraSG;
    private static Entity camera;
    private float aspectRatio = 800f/600f;
    private int eventMask;
    private AWTInputComponent cameraListener;
    private OrbitCameraProcessor eventProcessor;
    private AWTInputComponent selectionListener;
    private ProcessorCollectionComponent processorCollectionComp;

    public CameraEntityBuilder() {
    }

    public void buildCamera(WorldManager worldManager, CanvasBuilder content) {
        cameraSG = createCameraGraph();
        camera = new Entity("Default Camera");
        cameraComponent = worldManager.getRenderManager().createCameraComponent(cameraSG,
                cameraNode, content.getRenderingCanvas().getWidth(), content.getRenderingCanvas().getHeight(), 45.0f, aspectRatio, 1.0f, 1000.0f, true);
        content.getRenderBuffer().setCameraComponent(cameraComponent);
        camera.addComponent(CameraComponent.class, cameraComponent);
        eventMask = InputManager.KEY_EVENTS | InputManager.MOUSE_EVENTS;
        cameraListener = (AWTInputComponent) worldManager.getInputManager().createInputComponent(content.getRenderingCanvas(), eventMask);

        eventProcessor = new OrbitCameraProcessor(cameraListener, cameraNode, worldManager, camera);
        eventProcessor.setRunInRenderer(true);
        selectionListener = (AWTInputComponent) worldManager.getInputManager().createInputComponent(content.getRenderingCanvas(), eventMask);

        ClickSelectionProcessor clicker = new ClickSelectionProcessor(selectionListener,
                worldManager, camera, camera, content.getRenderingCanvas().getWidth(), content.getRenderingCanvas().getHeight(), eventProcessor);
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

    @Override
    public Entity getMyEntity() {
        return camera;
    }
}
