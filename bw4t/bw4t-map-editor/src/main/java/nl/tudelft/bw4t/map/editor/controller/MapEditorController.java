package nl.tudelft.bw4t.map.editor.controller;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;

public class MapEditorController {
	
	private EnvironmentStore view;
	
	private Map map;
	
	public MapEditorController(EnvironmentStore theView, Map theMap) {
		view = theView;
		map = theMap;
		
		
	}
}
