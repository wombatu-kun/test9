package wombatukun.tests.test9.processors;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Singleton
public class MapSorter {

	public Map<Character, Long> sort(Map<Character, Long> unsortedMap) {
		return unsortedMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
	}

}
