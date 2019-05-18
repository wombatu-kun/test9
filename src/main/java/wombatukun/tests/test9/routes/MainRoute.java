package wombatukun.tests.test9.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import wombatukun.tests.test9.processors.LetterCounter;
import wombatukun.tests.test9.processors.LetterFilter;
import wombatukun.tests.test9.processors.MapSorter;
import wombatukun.tests.test9.processors.WordCounter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainRoute extends RouteBuilder {

	@Inject
	@Uri("file:{{directory.in}}?delete={{delete.sources}}&delay={{delay.poll}}&include=.*\\.txt$")
	private Endpoint input;

	@Inject
	@Uri("file:{{directory.out}}?fileName=${date:now:yyyy-MM-dd_HH:mm:ss}-${header.CamelFileName}")
	private Endpoint output;

	@Inject
	private AggregationStrategy aggregationStrategy;

	@Inject
	private LetterFilter letterFilter;

	@Inject
	private LetterCounter letterCounter;

	@Inject
	private MapSorter mapSorter;

	@Inject
	private WordCounter wordCounter;

	@Override
	public void configure() {
		from(input)
				.multicast(aggregationStrategy)
				.to("direct:letterCounter", "direct:wordCounter")
				.end()
				.log("OUT:\n ${body}")
				.to(output);

		from("direct:letterCounter")
				.bean(letterFilter, "filter")
				.bean(letterCounter, "count")
				.bean(mapSorter, "sort")
				.transform(bodyAs(String.class));

		from("direct:wordCounter")
					.bean(wordCounter, "count")
					.transform(simple("words=${body}"));
	}
}
