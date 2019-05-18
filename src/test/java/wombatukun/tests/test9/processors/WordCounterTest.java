package wombatukun.tests.test9.processors;

import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(CamelCdiRunner.class)
public class WordCounterTest {

	@Inject
	private WordCounter wordCounter;

	@Test
	public void testEmptyString() {
		assertEquals(0, wordCounter.count(""));
	}

	@Test
	public void testEndOfLine() {
		assertEquals(0, wordCounter.count("\n"));
	}

	@Test
	public void testOneWord() {
		assertEquals(1, wordCounter.count("ololo"));
	}

	@Test
	public void testMultipleSpaces() {
		assertEquals(3, wordCounter.count("ololo   lolo 1 \n"));
	}

	@Test
	public void testDifferentDelimiters() {
		assertEquals(4, wordCounter.count("olol,o   lolo 1 .\n"));
	}

}
