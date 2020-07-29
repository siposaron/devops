package ro.codespring.sensor.reporter;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StatusReporter {
	
	private static final Logger log = LoggerFactory.getLogger(StatusReporter.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private final RestTemplate template = new RestTemplate();
	
	@Value("${AGGREGATOR_SERVICE_ENDPOINT}")
	private String aggregatorEndpoint;
	
	@Autowired
	private StatusReportGenerator generator;

	@Scheduled(fixedRate = 750)
	public void reportCurrentStatus() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		final String response = template.postForObject(aggregatorEndpoint, generator.generate(), String.class);
		log.info("Response {}", response);
	}

}
