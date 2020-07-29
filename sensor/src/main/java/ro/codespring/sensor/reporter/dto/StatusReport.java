package ro.codespring.sensor.reporter.dto;

public class StatusReport {

	private Long timestamp;
		private Integer voltage;
	private Status status;
	private String client;

	public StatusReport(final Long timestamp, final Integer voltage, final Status status, final String client) {
		this.timestamp = timestamp;
		this.voltage = voltage;
		this.status = status;
		this.client = client;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

}
