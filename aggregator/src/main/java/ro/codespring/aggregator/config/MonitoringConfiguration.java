package ro.codespring.aggregator.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;

@Configuration
public class MonitoringConfiguration {

	@Bean
	MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
		return registry -> registry.config().meterFilter(MeterFilter.deny(id -> {
			String uri = id.getTag("uri");
			return uri != null && uri.startsWith("/actuator");
		})).meterFilter(MeterFilter.deny(id -> {
			String uri = id.getTag("uri");
			return uri != null && uri.contains("favicon");
		})).meterFilter(new MeterFilter() {
			@Override
			public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
				return config.merge(DistributionStatisticConfig.builder()
						.percentiles(0.1, 0.25, 0.5, 0.75, 0.9, 0.95, 0.99, 0.995, 0.999).percentilesHistogram(true)
						.build());
			}
		});
	}

}
