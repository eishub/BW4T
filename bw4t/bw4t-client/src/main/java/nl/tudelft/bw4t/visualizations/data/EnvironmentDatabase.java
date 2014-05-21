package nl.tudelft.bw4t.visualizations.data;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import eis.iilang.Percept;

import nl.tudelft.bw4t.map.BlockColor;

public class EnvironmentDatabase {
    private ArrayList<RoomInfo> rooms;
    private ArrayList<BlockColor> sequence;
    private ArrayList<ArrayList<String>> chatHistory;
    private ArrayList<String> occupiedRooms;
    private HashMap<Long, Point2D.Double> objectPositions;
    private ArrayList<String> otherPlayers;
    private DropZoneInfo dropZone;
    private ArrayList<Long> visibleBlocks;
    private HashMap<Long, BlockColor> allBlocks;
    private HashMap<String, Point> roomLabels;
    private int sequenceIndex;
    private Color entityColor = Color.BLACK;
    private long holdingID = Long.MAX_VALUE;
    private String entityId;
    private Double[] entityLocation = new Double[] { 0., 0., 0., 0. };
    /**
     * Variables which are only used by human players.
     */
    private LinkedList<Percept> toBePerformedAction;

    public EnvironmentDatabase() {
        setRooms(new ArrayList<RoomInfo>());
        setSequence(new ArrayList<BlockColor>());
        setChatHistory(new ArrayList<ArrayList<String>>());
        setOccupiedRooms(new ArrayList<String>());
        setObjectPositions(new HashMap<Long, Point2D.Double>());
        setOtherPlayers(new ArrayList<String>());
        setVisibleBlocks(new ArrayList<Long>());
        setAllBlocks(new HashMap<Long, BlockColor>());
        setRoomLabels(new HashMap<String, Point>());
        setEntityColor(Color.BLACK);
        setHoldingID(Long.MAX_VALUE);
        setToBePerformedAction(new LinkedList<Percept>());
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public ArrayList<RoomInfo> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomInfo> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<String> getOccupiedRooms() {
        return occupiedRooms;
    }

    public void setOccupiedRooms(ArrayList<String> occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
    }

    public DropZoneInfo getDropZone() {
        return dropZone;
    }

    public void setDropZone(DropZoneInfo dropZone) {
        this.dropZone = dropZone;
    }

    public HashMap<Long, Point2D.Double> getObjectPositions() {
        return objectPositions;
    }

    public void setObjectPositions(HashMap<Long, Point2D.Double> hashMap) {
        this.objectPositions = hashMap;
    }

    public ArrayList<String> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(ArrayList<String> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public ArrayList<ArrayList<String>> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ArrayList<ArrayList<String>> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public ArrayList<BlockColor> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<BlockColor> sequence) {
        this.sequence = sequence;
    }

    public ArrayList<Long> getVisibleBlocks() {
        return visibleBlocks;
    }

    public void setVisibleBlocks(ArrayList<Long> visibleBlocks) {
        this.visibleBlocks = visibleBlocks;
    }

    public HashMap<Long, BlockColor> getAllBlocks() {
        return allBlocks;
    }

    public void setAllBlocks(HashMap<Long, BlockColor> allBlocks) {
        this.allBlocks = allBlocks;
    }

    public HashMap<String, Point> getRoomLabels() {
        return roomLabels;
    }

    public void setRoomLabels(HashMap<String, Point> roomLabels) {
        this.roomLabels = roomLabels;
    }

    public int getSequenceIndex() {
        return sequenceIndex;
    }

    public void setSequenceIndex(int sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }

    public long getHoldingID() {
        return holdingID;
    }

    public void setHoldingID(long holdingID) {
        this.holdingID = holdingID;
    }

    public Color getEntityColor() {
        return entityColor;
    }

    public void setEntityColor(Color entityColor) {
        this.entityColor = entityColor;
    }

    public Double[] getEntityLocation() {
        return entityLocation;
    }

    public void setEntityLocation(Double[] entityLocation) {
        this.entityLocation = entityLocation;
    }

    public LinkedList<Percept> getToBePerformedAction() {
        return toBePerformedAction;
    }

    public void setToBePerformedAction(LinkedList<Percept> toBePerformedAction) {
        this.toBePerformedAction = toBePerformedAction;
    }
}
