package com.example.service.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {

	@Override
	public void copyProperty(Object dest, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

		System.out.println("name: " + name + ": " + value);
		if (value == null)
			return;
		super.copyProperty(dest, name, value);
	}

}
