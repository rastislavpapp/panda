package eu.nyerel.panda.util;

import java.util.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class CollectionUtil {

	public static <T> List<T> list(T ... items) {
		List<T> list = new ArrayList<T>();
		Collections.addAll(list, items);
		return list;
	}

	public static <T> Set<T> set(Set<T> ... sets) {
		Set<T> result = new HashSet<T>();
		for (Set<T> set : sets) {
			result.addAll(set);
		}
		return result;
	}

	public static <T> Set<T> set(T ... items) {
		Set<T> set = new HashSet<T>();
		Collections.addAll(set, items);
		return set;
	}

	public static <K, V> Map<K, V> linkedMap(Map.Entry<K, V> ... entries) {
		return map(new LinkedHashMap<K, V>(), entries);
	}

	public static <K, V> Map<K, V> treeMap(Map.Entry<K, V> ... entries) {
		return map(new TreeMap<K, V>(), entries);
	}

	public static <K, V> Map<K, V> map(Map.Entry<K, V> ... entries) {
		return map(new HashMap<K, V>(), entries);
	}

	private static <K, V> Map<K, V> map(Map<K, V> mapInstance, Map.Entry<K, V> ... entries) {
		if (entries != null) {
			for (Map.Entry<K, V> entry : entries) {
				mapInstance.put(entry.getKey(), entry.getValue());
			}
		}
		return mapInstance;
	}

}
