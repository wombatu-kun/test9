package wombatukun.tests.test9.processors;

import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(CamelCdiRunner.class)
public class LetterFilterTest {

	@Inject
	private LetterFilter letterFilter;

	private String result;

	@Test
	public void testEmptyString() {
		result = letterFilter.filter(null);
		assertEquals("", result);
	}

	@Test
	public void testNonLettersString() {
		result = letterFilter.filter("3423# \t\n@&^()*!~`;:-_=+.,<>|");
		assertEquals("", result);
	}

	@Test
	public void testOk() {
		result = letterFilter.filter("342fF@&^(f)*!~`;:-_=+ц.,<>|ыЦ");
		assertEquals("fffцыц", result);
	}

}
