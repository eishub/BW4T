package nl.tudelft.bw4t.map.editor.controller;

import nl.tudelft.bw4t.map.editor.MapEditor;
import nl.tudelft.bw4t.map.editor.model.Map;

/**
 * The MapEditorController class handles interaction between the View and Model
 * @author Rothweiler
 * 
 *
 */
public class MapEditorController {

	private MapEditor view;
	
	private Map map;
	
	public MapEditorController(MapEditor theView, Map theMap) {
		this.view = theView;
		this.map = theMap;
		
		
	}
	
}
