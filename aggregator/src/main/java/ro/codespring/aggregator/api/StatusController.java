package ro.codespring.aggregator.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import ro.codespring.aggregator.api.dto.StatusReport;
import ro.codespring.aggregator.metrics.VoltMeter;

@RestController
@Validated
public class StatusController {
	
	private final MeterRegistry registry;
	
	private static final Logger log = LoggerFactory.getLogger(StatusController.class);
	
	private static final String electricaVoltageGauge = "Electrica.voltage";
	private static final String eonVoltageGauge = "EON.voltage";
	
	private final VoltMeter electricaVM = new VoltMeter();
	private final VoltMeter eonVM = new VoltMeter();
	
	public StatusController(final MeterRegistry registry) {
        this.registry = registry;
        Gauge.builder(electricaVoltageGauge, electricaVM, VoltMeter::average).register(registry);
        Gauge.builder(eonVoltageGauge, eonVM, VoltMeter::average).register(registry);
    }
	
	@PostMapping("/status")
	@Timed(value="aggregator.response.time")
	public String interceptStatus(
			@RequestBody @Valid
			final StatusReport report) {
		updateVoltMeters(report);
		updatePrometheusMetrics(report);
		return "Status intercepted";
	}
	
	private void updateVoltMeters(final StatusReport report) {
		if (report.getClient().equalsIgnoreCase("eon")) {
			eonVM.addValue(report.getVoltage());
		} else {
			electricaVM.addValue(report.getVoltage());
		}
		log.debug("Voltmeters updated.");
	}
	
	private void updatePrometheusMetrics(final StatusReport report) {
		final String counter = report.getClient() + ".counter." + report.getStatus();
		registry.counter(counter).increment();
		
		if (report.getClient().equalsIgnoreCase("electrica")) {
			registry.gauge(electricaVoltageGauge, report.getVoltage());
		} else {
			registry.gauge(eonVoltageGauge, report.getVoltage());
		}
		
		log.info("Prometheus metrics updated.");
	}
	
	
}
