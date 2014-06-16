package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * A utility class containing a BFS algorithm to verify if
 * a created map is solvable or not.
 */
public final class SolvabilityAlgorithm {
	/**
	 * Store the frequency of the sequence colors here.
	 */
	private static HashMap<BlockColor, Integer> sequenceFreq = new HashMap<BlockColor, Integer>();
	/**
	 * A private constructor.
	 */
	private SolvabilityAlgorithm() {
		
	}
	/**
	 * First call to the BFS solvability algorithm, starts at the dropzone.
	 * @param map The map to check sovability for.
	 * @return Whether or not the map is solvable.
	 */
	public static boolean mapIsSolvable(NewMap map) {
		List<BlockColor> seq = map.getSequence();
		for (BlockColor bc : seq) {
			addToMap(sequenceFreq, bc);
		}
		HashMap<Zone, Boolean> visited = new HashMap<Zone, Boolean>();
		HashMap<BlockColor, Integer> frequencies = new HashMap<BlockColor, Integer>();
		Zone dropZone = map.getZone("DropZone");
		if (dropZone == null) {
			EnvironmentStore.getOptionPrompt().showMessageDialog(null,
					"The map is unsolvable, as there is no drop zone.");
			return false;
		}
		List<Zone> neighbours = filterNeighboursList(dropZone);
		visited.put(dropZone, true);
		return mapIsSolvable(visited, frequencies, neighbours);
	}
	/**
	 * The recursive call, checks a new level.
	 * @param visited Keeps track of the nodes that have been visited.
	 * @param freq Keeps track of the frequency of the found colors.
	 * @param level The level to search in.
	 * @return Whether or not the map is solvable.
	 */
	private static boolean mapIsSolvable(HashMap<Zone, Boolean> visited, 
			HashMap<BlockColor, Integer> freq, List<Zone> level) {
		if (level.size() == 0) {
			return checkAccesibleBlocks(freq);
		}
		List<Zone> newLevel = new ArrayList<Zone>();
		for (Zone z : level) {
			visited.put(z, true);
			for (BlockColor bc : z.getBlocks()) {
				addToMap(freq, bc);
			}
			for (Zone neigh : filterNeighboursList(z)) {
				if (visited.get(neigh) == null) {
					visited.put(neigh, true);
					newLevel.add(neigh);
				}
			}
		}
		return mapIsSolvable(visited, freq, newLevel);
	}
	/**
	 * Compare the frequencies of the accessible blocks with those of
	 * the block sequence.
	 * @param map The hashmap with found frequencies.
	 * @return true iff the given map indicates that there are at least
	 * as many blocks in the map, as well as accessible, as in the frequency map
	 * of the color sequence.
	 */
	private static boolean checkAccesibleBlocks(HashMap<BlockColor, Integer> map) {
		for (BlockColor bc : sequenceFreq.keySet()) {
			if (map.get(bc) == null || sequenceFreq.get(bc) > map.get(bc)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Adds a new value to the map.
	 * @param map The hashmap to add the value to.
	 * @param bc The color.
	 */
	private static void addToMap(HashMap<BlockColor, Integer> map, BlockColor bc) {
		if (map.get(bc) == null) {
			map.put(bc, 1);
		} else {
			map.put(bc, map.get(bc) + 1);
		}
	}
	/**
	 * Filter the neighbours list to not include unreachable zones.
	 * @param z
	 * @return
	 */
	private static List<Zone> filterNeighboursList(Zone z) {
		List<Zone> res = new ArrayList<Zone>();
		List<Zone> neigh = z.getNeighbours();
		for (Zone z1 : neigh) {
			if (z1.getType() != Type.BLOCKADE) {
				res.add(z1);
			}
		}
		return res;
	}
}
