package wombatukun.tests.test9.processors;

import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(CamelCdiRunner.class)
public class LetterCounterTest {

	@Inject
	private LetterCounter letterCounter;

	private Map<Character, Long> map;

	@Test
	public void testEmptyString() {
		map = letterCounter.count("");
		assertTrue(map.isEmpty());
	}

	@Test
	public void testOk() {
		map = letterCounter.count("fffцыц");
		assertEquals(3, map.size());
		assertEquals(3L, map.get('f').longValue());
		assertEquals(2L, map.get('ц').longValue());
		assertEquals(1L, map.get('ы').longValue());
	}

}
