package wombatukun.tests.test9.processors;

import org.apache.camel.PropertyInject;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class WordCounter {

	@PropertyInject("words.delimiters")
	private String delimiters;

	public long count(String msg) {
		return Arrays.stream(msg.trim().split("[" + delimiters + "]")).filter(s -> !s.isEmpty()).count();
	}

}
