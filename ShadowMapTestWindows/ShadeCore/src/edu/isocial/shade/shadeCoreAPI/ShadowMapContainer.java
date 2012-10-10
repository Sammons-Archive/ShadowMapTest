/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shade.shadeCoreAPI;

import edu.isocial.shade.shadecore.AxisEntityBuilder;
import edu.isocial.shade.shadecore.CameraEntityBuilder;
import edu.isocial.shade.shadecore.CanvasBuilder;
import edu.isocial.shade.shadecore.FloorEntityBuilder;
import edu.isocial.shade.shadecore.GridEntityBuilder;
import edu.isocial.shade.shadecore.LightNodeBuilder;
import edu.isocial.shade.shadecore.ShapeEntityBuilder;
import javax.swing.JPanel;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author Ben
 */
public interface ShadowMapContainer {

      public WorldManager getWorldManager();

}
