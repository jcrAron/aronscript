package net.jcraron.aronscript.executor;

public class ExecutorConfig {
	private final static ExecutorConfig DEFAULT_CONFIG = ExecutorConfig.newDefaultConfig();

	private boolean enableImport;

	public boolean isEnableImport() {
		return enableImport;
	}

	public static ExecutorConfig newDefaultConfig() {
		ExecutorConfig config = new ExecutorConfig();
		config.enableImport = false;
		return config;
	}

	public static ExecutorConfig getCurrentConfig() {
		if (Thread.currentThread() instanceof AronScriptExecutor executor) {
			return executor.getConfig();
		}
		return DEFAULT_CONFIG;
	}
}
