package ro.codespring.aggregator.metrics;

import java.util.ArrayList;
import java.util.List;

public class VoltMeter {
	
	private List<Integer> values = new ArrayList<>();
	
	public void addValue(final Integer value) {
		values.add(value);
	}
	
	public double average() {
		return values.stream().mapToInt(val -> val).average().orElse(0.0);
	}

}
