package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;

/**
 * The ActionPopUpMenu class which creates all popupmenu's.
 */
public class ActionPopUpMenu {
    
    /**
     * Used for building the pop up menu that displays the actions a user can undertake.
     * 
     * @param gui
     *            the gui
     */
    public static void buildPopUpMenu(BW4TClientGUI gui) {
        int startPosX = 0;
        ClientMapController cmc = gui.getController().getMapController();
        MapRenderSettings settings = cmc.getRenderSettings();
        boolean buildBlockadeMenu_ChargingZoneMenu = !buildBlockadeMenu(gui, cmc, settings) 
                                                        && !buildChargingZoneMenu(gui, cmc, settings);

        if (!buildSequenceBlockMenu(gui, startPosX, cmc, settings)
                && !buildEPartnerMenu(gui, cmc, settings)
                && !buildRoomMenu(gui, cmc, settings)
                && buildBlockadeMenu_ChargingZoneMenu) {
            buildHallwayMenu(gui);
        }
    }

    /**
     * Builds the hallway menu.
     * 
     * @param gui
     *            the gui
     */
    private static void buildHallwayMenu(BW4TClientGUI gui) {
        HallwayMenu.buildPopUpMenuForHallway(gui, "go to here");
        showJPopupMenu(gui);
    }

    /**
     * Builds the charging zone menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildChargingZoneMenu(BW4TClientGUI gui, ClientMapController cmc,
            MapRenderSettings settings) {
        for (Zone chargingzone : cmc.getChargingZones()) {
            Shape chargeBoundaries = settings.transformRectangle(chargingzone.getBoundingbox().getRectangle());
            if (chargeBoundaries.contains(gui.getSelectedLocation())) {
                HallwayMenu.buildPopUpMenuForHallway(gui, "go recharge");
                showJPopupMenu(gui);
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the blockade menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildBlockadeMenu(BW4TClientGUI gui, ClientMapController cmc, MapRenderSettings settings) {
        for (Zone blockade : cmc.getBlockades()) {
            Shape blockBoundaries = settings.transformRectangle(blockade.getBoundingbox().getRectangle());
            if (blockBoundaries.contains(gui.getSelectedLocation())) {
                BlockadeMenu.buildPopUpMenuForBlockade(gui);
                showJPopupMenu(gui);
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the room menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildRoomMenu(BW4TClientGUI gui, ClientMapController cmc, MapRenderSettings settings) {
        for (Zone room : cmc.getRooms()) {
            Shape roomBoundaries = settings.transformRectangle(room.getBoundingbox().getRectangle());
            if (roomBoundaries.contains(gui.getSelectedLocation())) {
                
                /** We only draw the room menu if the block menu wasn't already drawn: */
                if (!buildVisibleBlockMenu(gui, cmc, settings, room)) {
                    RoomMenus.buildPopUpMenuRoom(room, gui);
                    showJPopupMenu(gui);
                }
                
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the visible block menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @param room
     *            the room
     * @return Whether the menu was built.
     */
    private static boolean buildVisibleBlockMenu(BW4TClientGUI gui, ClientMapController cmc, MapRenderSettings settings,
            Zone room) {
        for (ViewBlock box : cmc.getVisibleBlocks()) {
            Shape boxBoundaries = settings.transformCenterRectangle(new Rectangle2D.Double(box.getPosition()
                    .getX(), box.getPosition().getY(), ViewBlock.BLOCK_SIZE, ViewBlock.BLOCK_SIZE));
            if (boxBoundaries.contains(gui.getSelectedLocation())) {
                if (MapOperations.closeToBox(box, gui.getController())) {
                    RoomMenus.buildPopUpMenuForBeingAtBlock(box, room, gui);
                } else {
                    RoomMenus.buildPopUpMenuForBlock(box, room, gui.getController());
                }
                showJPopupMenu(gui);
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the e partner menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildEPartnerMenu(BW4TClientGUI gui, ClientMapController cmc, MapRenderSettings settings) {
        if (cmc.getTheBot().getHoldingEpartner() >= 0) {
            return buildEPartnerHoldingMenu(gui, cmc, settings);
        } else {
            return buildEPartnerVisibleMenu(gui, cmc, settings);
        }
    }

    /**
     * Builds the e partner visible menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildEPartnerVisibleMenu(BW4TClientGUI gui, ClientMapController cmc,
            MapRenderSettings settings) {
        for (ViewEPartner ep : cmc.getVisibleEPartners()) {
            Shape ePartnerBox = settings.transformCenterRectangle(new Rectangle2D.Double(ep.getLocation().getX(),
                    ep.getLocation().getY(), ViewEPartner.EPARTNER_SIZE, ViewEPartner.EPARTNER_SIZE));
            if (ePartnerBox.contains(gui.getSelectedLocation())) {
                if (MapOperations.closeToBox(ep, gui.getController())) {
                    EPartnerMenu.buildPopUpMenuPickUpEPartner(ep, gui);
                } else {
                    EPartnerMenu.buildPopUpMenuMoveToEPartner(ep, gui);
                }
                showJPopupMenu(gui);
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the e partner holding menu.
     * 
     * @param gui
     *            the gui
     * @param cmc
     *            the client map controller
     * @param settings
     *            the map renderer settings
     * @return Whether the menu was built.
     */
    private static boolean buildEPartnerHoldingMenu(BW4TClientGUI gui, ClientMapController cmc,
            MapRenderSettings settings) {
        ViewEPartner ep = cmc.getViewEPartner(cmc.getTheBot().getHoldingEpartner());
        if (ep != null) {
            final Point2D location = ep.getLocation();
            Shape ePartnerBox = settings.transformCenterRectangle(new Rectangle2D.Double(location.getX(), location
                    .getY(), ViewEPartner.EPARTNER_SIZE, ViewEPartner.EPARTNER_SIZE));
            if (ePartnerBox.contains(gui.getSelectedLocation())) {
                EPartnerMenu.buildPopUpMenuForEPartner(ep, gui);
                showJPopupMenu(gui);
                return true;
            }
        }
        return false;
    }

    /**
     * Show the jpopup menu.
     * 
     * @param gui
     *            the gui
     */
    public static void showJPopupMenu(BW4TClientGUI gui) {
        gui.getjPopupMenu().show(gui, (int) gui.getSelectedLocation().getX(),
                (int) gui.getSelectedLocation().getY());
    }

    /**
     * Builds the sequence block menu.
     * 
     * @param gui
     *            the gui
     * @param startPosX
     *            the start position x
     * @param cmc
     *            the client map controller
     * @param set
     *            the set
     * @return Whether the menu was built.
     */
    private static boolean buildSequenceBlockMenu(BW4TClientGUI gui, int startPosX, ClientMapController cmc,
            MapRenderSettings set) {
        for (BlockColor color : cmc.getSequence()) {
            Shape colorBounds = new Rectangle2D.Double(startPosX, set.scale(set.getWorldHeight()),
                    set.getSequenceBlockSize(), set.getSequenceBlockSize());
            if (colorBounds.contains(gui.getSelectedLocation())) {
                MapOperations.buildPopUpMenuForGoalColor(color, gui);
                showJPopupMenu(gui);
                return true;
            }
            startPosX += set.getSequenceBlockSize();
        }
        return false;
    }
}
