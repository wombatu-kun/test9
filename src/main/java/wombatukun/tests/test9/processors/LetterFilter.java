package wombatukun.tests.test9.processors;

import javax.inject.Singleton;

@Singleton
public class LetterFilter {

	public String filter(String msg) {
		if (msg == null) {
			return "";
		} else {
			return msg.toLowerCase().replaceAll("[^\\p{L}]", "");
		}
	}

}
