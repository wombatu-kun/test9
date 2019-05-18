package wombatukun.tests.test9.configs;

import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AppConfig {

	@Produces
	@Named("properties")
	PropertiesComponent propertiesComponent() {
		PropertiesComponent component = new PropertiesComponent();
		component.setLocation("classpath:application.properties");
		return component;
	}

	@Produces
	@Named("aggregationStrategy")
	AggregationStrategy aggregationStrategy() {
		return (oldExchange, newExchange) -> {
			if (oldExchange == null) {
				return newExchange;
			}
			String letters = oldExchange.getIn().getBody(String.class);
			String words = newExchange.getIn().getBody(String.class);
			String body = letters.equals("{}") ? words : letters + ", " + words;
			oldExchange.getIn().setBody(body);
			return oldExchange;
		};
	}

}
