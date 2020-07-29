package ro.codespring.aggregator.api.dto;

import javax.validation.constraints.NotNull;

public class StatusReport {

	@NotNull
	private Long timestamp;
	@NotNull
	private Integer voltage;
	@NotNull
	private Status status;
	@NotNull
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
