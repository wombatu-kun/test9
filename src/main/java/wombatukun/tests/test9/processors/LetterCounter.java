package wombatukun.tests.test9.processors;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class LetterCounter {

	public Map<Character, Long> count(String msg) {
		Map<Character, Long> map = new HashMap<>();
		for (Character c : msg.toCharArray()) {
			addLetter(c, map);
		}
		return map;
	}

	private void addLetter(Character c, Map<Character, Long> map) {
		if (map.containsKey(c)) {
			map.put(c, map.get(c) + 1);
		} else {
			map.put(c, 1L);
		}
	}

}
