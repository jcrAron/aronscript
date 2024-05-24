package net.jcraron.aronscript.executor;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.jcraron.aronscript.core.Data;

public class AronScriptRuntime {
	private final static AronScriptRuntime DEFAULT_CONFIG = AronScriptRuntime.newDefaultConfig();

	public final static String DEFAULT_ROOT = "AronScript_root";
	public final static String DEFAULT_PACKAGE_PATH = "AronScript_path";
	public final static String DEFAULT_ROOT_LIB = "stdlib";

	private boolean enableImport;
	private String packageRoot;
	private String root;
	/** immutable */
	private Map<String, Data> preloadPackage;

	public boolean isEnableImport() {
		return enableImport;
	}

	public String getStandardLib() {
		return new File(this.root, DEFAULT_ROOT_LIB).getAbsolutePath();
	}

	public String getPackagePath() {
		return packageRoot;
	}

	public Map<String, Data> getPreloadPackage() {
		return preloadPackage;
	}

	public static AronScriptRuntime newDefaultConfig() {
		AronScriptRuntime config = new AronScriptRuntime();
		config.enableImport = true;
		config.root = System.getenv(DEFAULT_ROOT);
		config.packageRoot = System.getenv(DEFAULT_PACKAGE_PATH);
		config.preloadPackage = Collections.unmodifiableMap(new HashMap<>()); // TODO
		return config;
	}

	public static AronScriptRuntime getCurrentConfig() {
		if (Thread.currentThread() instanceof AronScriptExecutor executor) {
			return executor.getConfig();
		}
		return DEFAULT_CONFIG;
	}
}
