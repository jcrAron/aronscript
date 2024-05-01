package net.jcraron.aronscript.parser.serialcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;
import net.jcraron.aronscript.core.base.BooleanData;

public class BunaryCodeRuntime {
	private int pc;
	private Data output;
	/** first is top of stack */
	private Deque<Data> envStack;
	/** first is top of stack */
	private Deque<Integer> backtrackStack;
	private Map<Integer, Data> cache;
	private Map<Integer, Integer> labels;
	private ReturnThrowDataSet finish;

	public void init(Data env) {
		this.pc = 0;
		this.output = null;
		this.envStack = new LinkedList<>();
		this.envStack.push(env);
		this.backtrackStack = new LinkedList<>();
		this.cache = new HashMap<>();
		this.labels = new HashMap<>();
		this.finish = null;
	}

	void reset(Data env) {
		this.pc = 0;
		this.output = null;
		this.envStack = new LinkedList<>();
		this.envStack.push(env);
		this.backtrackStack = new LinkedList<>();
		this.cache = new HashMap<>();
		// this.labels = new HashMap<>();
		this.finish = null;
	}

	/** @param command that is a whole line */
	public void setOptimization(String key, String value) {
		// TODO
	}

	/** @return output from magic function */
	Data getOutput() {
		return output;
	}

	void setOutput(Data data) {
		output = data;
	}

	/** @return the current ENV */
	public Data getEnv() {
		return envStack.peekFirst();
	}

	/** @return Program Counter */
	public int getPC() {
		return pc;
	}

	/** count Program Counter */
	void countPC(int count) {
		if (finish != null) {
			throw new RuntimeException("program is finish.");
		}
		pc += count;
	}

	/** @return data on specific key */
	Data getCache(int cacheIndex) {
		return cache.get(cacheIndex);
	}

	/** set data on specific key */
	void setCache(int cacheIndex, Data data) {
		cache.put(cacheIndex, data);
	}

	/**
	 * mark the current PC for a specific label
	 * 
	 * @param label name
	 */
	void label(int labelsIndex) {
		labels.put(labelsIndex, pc);
	}

	/** jump to the specific label */
	void flow_jump(int labelsIndex) {
		pc = labels.get(labelsIndex);
	}

	/** jump to the specific label if the output is false */
	void flow_ifFalse(int labelsIndex) {
		if (output == BooleanData.FALSE) {
			pc = labels.get(labelsIndex);
		}
	}

	/** push ENV to stack */
	void flow_catch(int labelsIndex) {
		backtrackStack.push(-labels.get(labelsIndex));
	}

	/** push ENV and the current PC to stack. Then jump to the specific label. */
	void flow_call(int labelsIndex, Data env) {
		envStack.push(env);
		backtrackStack.push(pc);
		flow_jump(labelsIndex);
	}

	/**
	 * set data to the output. Then pop the stack until it is the PC location
	 * defined by {@link flow_call} and jump there.
	 */
	void flow_return(Data data) {
		output = data;
		while (!backtrackStack.isEmpty()) {
			int num = backtrackStack.pop();
			if (num > 0) {
				envStack.pop();
				pc = num;
				return;
			} else if (num < 0) {
				// pass
			}
		}
		finish = ReturnThrowDataSet.returnData(output);
	}

	/**
	 * set data to the output. Then pop the stack until it is the PC location
	 * defined by {@link flow_catch} and jump there.
	 */
	void flow_throw(Data data) {
		output = data;
		while (!backtrackStack.isEmpty()) {
			int num = backtrackStack.pop();
			if (num > 0) {
				envStack.pop();
			} else if (num < 0) {
				pc = num;
				return;
			}
		}
		finish = ReturnThrowDataSet.throwData(output);
	}

	ReturnThrowDataSet finish() {
		return finish;
	}

	/*---------------------------------------------------------------------------------------------*/
	/** @return debug text */
	public String debugText() {
		StringBuilder sb = new StringBuilder();
		addDebugEntry(sb, "pc", pc);
		addDebugEntry(sb, "backtrackStack", backtrackStack);
		addDebugEntry(sb, "labels", labels);
		addDebugEntry(sb, "cache", cache);
		return sb.toString();
	}

	private void addDebugEntry(StringBuilder sb, String key, Object value) {
		sb.append(key);
		sb.append(": ");
		sb.append(Objects.toString(value, "<none>"));
		sb.append('\n');
	}
}
