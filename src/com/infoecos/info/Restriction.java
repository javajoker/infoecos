package com.infoecos.info;

public interface Restriction {
	boolean validate();

	Class<?> getRestrictionClass();
}
