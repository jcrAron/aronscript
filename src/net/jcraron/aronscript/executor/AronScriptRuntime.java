package net.jcraron.aronscript.executor;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.stdlib.Printer;

public class AronScriptRuntime {
	private final static AronScriptRuntime DEFAULT_CONFIG = AronScriptRuntime.newDefaultConfig();

	public final static String DEFAULT_ROOT = "AronScript_root";
	public final static String DEFAULT_PACKAGE_PATH = "AronScript_path";
	private final static String PREFIX_STDLIB = "stdlib";

	private boolean enableImport;
	private String packageRoot;
	private String root;
	private Map<String, Data> importPackages;
	/** immutable */
	private Map<String, Data> preloadPackages;

	public boolean isEnableImport() {
		return enableImport;
	}

	public String getStandardLib() {
		return new File(this.root, PREFIX_STDLIB).getAbsolutePath();
	}

	public String getPackagePath() {
		return packageRoot;
	}

	public Map<String, Data> getPreloadPackages() {
		return preloadPackages;
	}

	public Map<String, Data> getImportedPackages() {
		return importPackages;
	}

	public static AronScriptRuntime newDefaultConfig() {
		AronScriptRuntime config = new AronScriptRuntime();
		config.enableImport = true;
		config.root = System.getenv(DEFAULT_ROOT);
		config.packageRoot = System.getenv(DEFAULT_PACKAGE_PATH);
		Map<String, Data> preload = new HashMap<>();
		preload.put(PREFIX_STDLIB + "/printer", Printer.INSTANCE);
		config.preloadPackages = Collections.unmodifiableMap(preload); // TODO
		config.importPackages = new HashMap<>();
		return config;
	}

	public static AronScriptRuntime getCurrentConfig() {
		if (Thread.currentThread() instanceof AronScriptExecutor executor) {
			return executor.getConfig();
		}
		return DEFAULT_CONFIG;
	}
}
