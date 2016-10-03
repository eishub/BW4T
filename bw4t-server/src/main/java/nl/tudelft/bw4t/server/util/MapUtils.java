package nl.tudelft.bw4t.server.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapUtils {
	/**
	 * @param map
	 *            a map to search
	 * @param value
	 *            the value to find.
	 * @return keys that have given value as value in the given map
	 */
	public static <K, V> Set<K> getKeys(Map<K, V> map, V value) {
		Set<K> keys = new HashSet<>();

		for (K key : map.keySet()) {
			if (map.get(key).equals(value)) {
				keys.add(key);
			}
		}
		return keys;
	}
}
