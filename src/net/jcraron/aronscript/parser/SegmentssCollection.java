package net.jcraron.aronscript.parser;

import java.util.LinkedList;
import java.util.List;

public class SegmentssCollection {

	private List<Segment> segments;
	private OperatorRegistry registry;

	public SegmentssCollection(OperatorRegistry registry) {
		this.segments = new LinkedList<>();
		this.registry = registry;
	}

	public void run() {
		segments.forEach(this::run);
	}

	private void run(Segment seg) {
		registry.registry.get(seg.operator).accept(seg.vars);
	}

	public void addSegenment(String operator, Object... vars) {
		segments.add(new Segment(operator, vars));
	}

	private static record Segment(String operator, Object... vars) {
	}

}
