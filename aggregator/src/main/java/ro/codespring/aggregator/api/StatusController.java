package ro.codespring.aggregator.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import ro.codespring.aggregator.api.dto.Status;
import ro.codespring.aggregator.api.dto.StatusReport;

@RestController
@Validated
public class StatusController {
	
	private final MeterRegistry registry;
	
	private final Map<String, Map<Status, Integer>> statusCounts = new HashMap<>();
	
	private static final Logger log = LoggerFactory.getLogger(StatusController.class);

	public StatusController(final MeterRegistry registry) {
        this.registry = registry;
    }
	
	@PostMapping("/status")
	@Timed
	public String interceptStatus(
			@RequestBody @Valid
			final StatusReport report) {
		updateStatusCounts(report);
		updatePrometheusCounters(report);
		return "Status intercepted";
	}
	
	private void updateStatusCounts(final StatusReport report) {
		if (statusCounts.containsKey(report.getClient())) {
			final Map<Status, Integer> clientStatus = statusCounts.get(report.getClient());
			if (clientStatus.containsKey(report.getStatus())) {
				Integer count = clientStatus.get(report.getStatus());
				clientStatus.replace(report.getStatus(), count, ++count);
			} else {
				clientStatus.put(report.getStatus(), 1);
			}
		} else {
			statusCounts.put(report.getClient(), Map.of(report.getStatus(), 1));
		}
		log.debug("Internal counters updated.");
	}
	
	private void updatePrometheusCounters(final StatusReport report) {
		final String counterName = report.getClient() + ".counter." + report.getStatus();
		registry.counter(counterName).increment();
		log.info("Prometheus counter {} updated.", counterName);
	}
}
