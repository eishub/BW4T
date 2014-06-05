package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.controller.MapRenderSettings;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;

public class ActionPopUpMenu {
    /**
     * Used for building the pop up menu that displays the actions a user can undertake
     */
    public static void buildPopUpMenu(BW4TClientGUI gui) {
        // Check if pressing on a color in the goal sequence list
        int startPosX = 0;
        ClientMapController cmc = gui.getController().getMapController();
        MapRenderSettings set = cmc.getRenderSettings();
        for (BlockColor color : cmc.getSequence()) {
            Shape colorBounds = new Rectangle2D.Double(startPosX, set.scale(set.getWorldHeight()),
                    set.getSequenceBlockSize(), set.getSequenceBlockSize());
            if (colorBounds.contains(gui.getSelectedLocation())) {
                MapOperations.buildPopUpMenuForGoalColor(color, gui);
                gui.getjPopupMenu().show(gui, gui.getSelectedLocation().x, gui.getSelectedLocation().y);
                return;
            }
            startPosX += set.getSequenceBlockSize();
        }
        
        if(cmc.getTheBot().getHoldingEpartner() >= 0) {
            ViewEPartner ep = cmc.getViewEPartner(cmc.getTheBot().getHoldingEpartner());
            Shape ePartnerBox = set.transformCenterRectangle(new Rectangle2D.Double(ep.getLocation().getX(), ep.getLocation().getY(), ep.EPARTNER_SIZE, ep.EPARTNER_SIZE));
            if (ePartnerBox.contains(gui.getSelectedLocation())) {
                EPartnerMenu.buildPopUpMenuForEPartner(ep, gui);
                gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
                return;
            }
        } else {
            for (ViewEPartner ep : cmc.getVisibleEPartners()) {
                Shape ePartnerBox = set.transformCenterRectangle(new Rectangle2D.Double(ep.getLocation().getX(), ep.getLocation().getY(), ep.EPARTNER_SIZE, ep.EPARTNER_SIZE));
                if (ePartnerBox.contains(gui.getSelectedLocation())) {
                    if (MapOperations.closeToBox(ep, gui.getController())) {
                        EPartnerMenu.buildPopUpMenuPickUpEPartner(ep, gui);
                    }
                    else {
                        EPartnerMenu.buildPopUpMenuMoveToEPartner(ep, gui);
                    }
                    gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
                    return;
                }
            }
        }

        // Check if pressing on a room
        for (Zone room : cmc.getRooms()) {
            Shape roomBoundaries = set.transformRectangle(room.getBoundingbox().getRectangle());
            if (roomBoundaries.contains(gui.getSelectedLocation())) {
                // Check if pressing on a block
                for (ViewBlock box : cmc.getVisibleBlocks()) {

                    Shape boxBoundaries = set.transformCenterRectangle(new Rectangle2D.Double(box.getPosition().getX(), box.getPosition().getY(), ViewBlock.BLOCK_SIZE, ViewBlock.BLOCK_SIZE));
                    if (boxBoundaries.contains(gui.getSelectedLocation())) {
                        if (MapOperations.closeToBox(box, gui.getController())) {
                            RoomMenus.buildPopUpMenuForBeingAtBlock(box, room, gui);
                        }
                        else {
                            RoomMenus.buildPopUpMenuForBlock(box, room, gui);
                        }
                        gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
                        return;
                    }
                }
                RoomMenus.buildPopUpMenuRoom(room, gui);
                gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
                return;
            }
        }
        // Check for dropzone
        //      DropZoneInfo dropZone = gui.getEnvironmentDatabase().getDropZone();
        //      Shape dropZoneBoundaries = MapOperations.transformRectangle(new Rectangle2D.Double(dropZone.getX(), dropZone
        //              .getY(), dropZone.getWidth(), dropZone.getHeight()));
        //      if (dropZoneBoundaries.contains(new Point(gui.getSelectedLocation()[0], gui.getSelectedLocation()[1]))) {
        //          RoomMenus.buildPopUpMenuRoom(dropZone, gui);
        //          gui.getjPopupMenu().show(gui, gui.getSelectedLocation()[0], gui.getSelectedLocation()[1]);
        //          return;
        //      }

        // Otherwise it is a hallway
        HallwayMenu.buildPopUpMenuForHallway(gui);
        gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(), (int) gui.getSelectedLocation().getY());
    }
}
