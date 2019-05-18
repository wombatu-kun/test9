package wombatukun.tests.test9;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.PropertyInject;
import org.apache.camel.cdi.Uri;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.camel.test.junit4.TestSupport.deleteDirectory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(CamelCdiRunner.class)
public class MainRouteTest {

	@Inject
	private CamelContext context;

	@Inject @Uri("file:{{directory.in}}")
	private ProducerTemplate template;

	@PropertyInject("directory.in")
	private String input;

	@PropertyInject("directory.out")
	private String output;

	@Before
	public void setUp() {
		deleteDirectory(input);
		deleteDirectory(output);
	}

	@Test
	public void testOk() throws Exception {
		template.sendBodyAndHeader("Hello World", Exchange.FILE_NAME, "hello.txt");
		Thread.sleep(1000);

		List<String> names = getOutputNames();
		assertEquals(1, names.size());

		File target = new File(names.get(0));
		assertTrue(target.exists());

		String content = context.getTypeConverter().convertTo(String.class, target);
		assertTrue(content.endsWith("2"));
	}

	@Test
	public void testNonTxt() throws Exception {
		template.sendBodyAndHeader("kek", Exchange.FILE_NAME, "deep_throat_9.avi");
		Thread.sleep(1000);
		List<String> names = getOutputNames();
		assertEquals(0, names.size());
	}

	@Test
	public void testEmptyFile() throws Exception {
		template.sendBodyAndHeader("", Exchange.FILE_NAME, "empty.txt");
		Thread.sleep(1000);

		List<String> names = getOutputNames();
		assertEquals(1, names.size());

		File target = new File(names.get(0));
		assertTrue(target.exists());

		String content = context.getTypeConverter().convertTo(String.class, target);
		assertTrue(content.endsWith("0"));
	}

	@Test
	public void testFileWithoutLetters() throws Exception {
		template.sendBodyAndHeader("132!", Exchange.FILE_NAME, "no_letters.txt");
		Thread.sleep(1000);

		List<String> names = getOutputNames();
		assertEquals(1, names.size());

		File target = new File(names.get(0));
		assertTrue(target.exists());

		String content = context.getTypeConverter().convertTo(String.class, target);
		assertEquals("words=1", content);
	}

	private List<String> getOutputNames() {
		List<String> names = new ArrayList<>();
		try (Stream<Path> walk = Files.walk(Paths.get(output))) {
			names = walk.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList());
			System.out.println(names);
		} catch (IOException e) {
			//do nothing
		}
		return names;
	}
}
