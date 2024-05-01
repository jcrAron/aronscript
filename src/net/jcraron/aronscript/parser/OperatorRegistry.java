package net.jcraron.aronscript.parser;

import java.util.Map;
import java.util.function.Consumer;

public class OperatorRegistry {
	Map<String, Consumer<Object[]>> registry;

	public void register(String operator, Consumer<Object[]> con) {
		registry.put(operator, con);
	}
}