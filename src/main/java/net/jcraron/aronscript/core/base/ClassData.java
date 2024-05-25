package net.jcraron.aronscript.core.base;

import java.util.Objects;

import net.jcraron.aronscript.core.Data;
import net.jcraron.aronscript.core.ReturnThrowDataSet;

public class ClassData extends Data {

	public final Class<? extends Data> javaClass;
	public final static ClassData CLASS = new ClassData(ClassData.class);

	public ClassData(Class<? extends Data> javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	public final ReturnThrowDataSet __class__() {
		return ClassData.CLASS.returnThis();
	}

	@Override
	public int hashCode() {
		return Objects.hash(javaClass);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassData other = (ClassData) obj;
		return Objects.equals(javaClass, other.javaClass);
	}

}