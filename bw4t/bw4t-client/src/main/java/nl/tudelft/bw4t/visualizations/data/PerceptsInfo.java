package nl.tudelft.bw4t.visualizations.data;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JTextArea;

import nl.tudelft.bw4t.map.BlockColor;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

public class PerceptsInfo {
    private EnvironmentDatabase environmentDatabase;
    private JTextArea chatSession;
    
    public PerceptsInfo(EnvironmentDatabase environmentDatabase, JTextArea chatSession) {
        this.environmentDatabase = environmentDatabase;
        this.chatSession = chatSession;
    }
    
    /**
     * Processes the percepts list from the perceptPoller and updates the
     * following data: - Visible blocks - Holding a block - Location of the
     * entity - Group goal sequence - Room occupation
     * 
     * @param percepts
     *            , a list of all received percepts
     */
    public void processPercepts(LinkedList<Percept> percepts) {

        // first process the not percepts.
        for (Percept percept : percepts) {
            String name = percept.getName();
            if (name.equals("not")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                Function function = ((Function) parameters.get(0));
                if (function.getName().equals("occupied")) {
                    LinkedList<Parameter> paramOcc = function.getParameters();
                    String id = ((Identifier) paramOcc.get(0)).getValue();
                    environmentDatabase.getOccupiedRooms().remove(id);
                } else if (function.getName().equals("holding")) {
                    environmentDatabase.setHoldingID(Long.MAX_VALUE);
                    environmentDatabase.setEntityColor(Color.BLACK);
                }
            }
        }

        // reset the ALWAYS percepts
        environmentDatabase.setVisibleBlocks(new ArrayList<Long>());

        // First create updated information based on the new percepts.
        for (Percept percept : percepts) {
            String name = percept.getName();

            // Initialize room ids in all rooms gotten from the map loader
            // Should only be done one time
            HashMap<Long, BlockColor> allBlocks = environmentDatabase.getAllBlocks();
            if (name.equals("position")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                long id = ((Numeral) parameters.get(0)).getValue().longValue();
                double x = ((Numeral) parameters.get(1)).getValue()
                        .doubleValue();
                double y = ((Numeral) parameters.get(2)).getValue()
                        .doubleValue();
                for (RoomInfo room : environmentDatabase.getRooms()) {
                    if (room.getX() == (int) x && room.getY() == (int) y) {
                        room.setId(id);
                        break;
                    }
                }

                // Also update drop zone id
                if (environmentDatabase.getDropZone().getX() == x && environmentDatabase.getDropZone().getY() == y) {
                    environmentDatabase.getDropZone().setId(id);
                }

                // Else it is a block, add it to all object positions
                environmentDatabase.getObjectPositions().put(id, new Point2D.Double(x, y));
            }

            else if (name.equals("color")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                long id = ((Numeral) parameters.get(0)).getValue().longValue();
                char color = ((Identifier) parameters.get(1)).getValue()
                        .charAt(0);
                environmentDatabase.getVisibleBlocks().add(id);
                if (!allBlocks.containsKey(id)) {
                    allBlocks.put(id, BlockColor.toAvailableColor(color));
                }
            }

            // Prepare updated occupied rooms list
            else if (name.equals("occupied")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                String id = ((Identifier) parameters.get(0)).getValue();
                environmentDatabase.getOccupiedRooms().add(id);
            }

            else if (name.equals("not")) {
                // already processed, skip.
            }

            else if (name.equals("sequenceIndex")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                int index = ((Numeral) parameters.get(0)).getValue().intValue();
                environmentDatabase.setSequenceIndex(index);
            }

            // Location can be updated immediately.
            else if (name.equals("location")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                double x = ((Numeral) parameters.get(0)).getValue()
                        .doubleValue();
                double y = ((Numeral) parameters.get(1)).getValue()
                        .doubleValue();
                environmentDatabase.setEntityLocation(new Double[] { x, y });
            }

            // Check if holding a block
            else if (name.equals("holding")) {
                long holdingID = environmentDatabase.getHoldingID();
                holdingID = ((Numeral) percept.getParameters().get(0))
                        .getValue().longValue();
                environmentDatabase.setEntityColor(allBlocks.get(holdingID).getColor());
            }

            else if (name.equals("player")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                String player = ((Identifier) parameters.get(0)).getValue();
                if (!environmentDatabase.getOtherPlayers().contains(player)) {
                    environmentDatabase.getOtherPlayers().add(player);
                }
            }

            // Update group goal sequence
            else if (name.equals("sequence")) {
                LinkedList<Parameter> parameters = percept.getParameters();
                for (Parameter i : parameters) {
                    ParameterList list = (ParameterList) i;
                    for (Parameter j : list) {
                        char letter = (((Identifier) j).getValue().charAt(0));
                        environmentDatabase.getSequence().add(BlockColor.toAvailableColor(letter));
                    }
                }
            }

            // Update chat history
            else if (name.equals("message")) {
                LinkedList<Parameter> parameters = percept.getParameters();

                ParameterList parameterList = ((ParameterList) parameters
                        .get(0));

                Iterator<Parameter> iterator = parameterList.iterator();

                String sender = ((Identifier) iterator.next()).getValue();
                String message = ((Identifier) iterator.next()).getValue();

                chatSession.append(sender + " : " + message + "\n");
                chatSession.setCaretPosition(chatSession.getDocument()
                        .getLength());

                ArrayList<String> newMessage = new ArrayList<String>();
                newMessage.add(sender);
                newMessage.add(message);
                environmentDatabase.getChatHistory().add(newMessage);
            }

        }

    }
}
