package ro.codespring.sensor.reporter;

import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ro.codespring.sensor.reporter.dto.Status;
import ro.codespring.sensor.reporter.dto.StatusReport;

@Component
public class StatusReportGenerator {
	
	@Value("${CLIENT_NAME}")
	private String clientName;
	
	public StatusReport generate() {
		final Integer voltage = randomVoltage();
		final Status status = statusFromVoltage(voltage);
		return new StatusReport(
				Calendar.getInstance().getTimeInMillis(),
				voltage,
				status,
				clientName);
	}

	private Integer randomVoltage() { 
        final Random rand = new Random(); 
        return rand.nextInt(240);
	}
	
	private Status statusFromVoltage(final Integer voltage) {
		if (voltage < 50) {
			return Status.FAULTY;
		} else if (voltage < 120 ) {
			return Status.WARN;
		} else {
			return Status.OK;
		}
	}
}
