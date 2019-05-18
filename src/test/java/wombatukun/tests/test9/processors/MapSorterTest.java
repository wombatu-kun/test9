package wombatukun.tests.test9.processors;

import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(CamelCdiRunner.class)
public class MapSorterTest {

	@Inject
	private MapSorter mapSorter;

	@Test
	public void testOk() {
		Map<Character, Long> unsorted = new HashMap<>();
		unsorted.put('z', 2L);
		unsorted.put('x', 1L);
		unsorted.put('a', 3L);

		Map<Character, Long> sorted = mapSorter.sort(unsorted);
		assertEquals("{a=3, z=2, x=1}", sorted.toString());
	}

}
