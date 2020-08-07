package ro.codespring.sensor.reporter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ro.codespring.sensor.reporter.dto.StatusReport;

@Component
public class StatusReporter {
	
	private static final Logger log = LoggerFactory.getLogger(StatusReporter.class);
	
	private final RestTemplate template = new RestTemplate();
	
	@Value("${AGGREGATOR_SERVICE_ENDPOINT}")
	private String aggregatorEndpoint;
	
	@Autowired
	private StatusReportGenerator generator;

	@Scheduled(fixedRate = 1000)
	public void reportCurrentStatus() {
		final StatusReport report = generator.generate();
		final String response = template.postForObject(aggregatorEndpoint, report, String.class);
		log.debug("Client: {}, Status: {}, Voltage: {} processed, response is {}", 
				report.getClient(), 
				report.getStatus(), 
				report.getVoltage(),
				response);
	}

}
