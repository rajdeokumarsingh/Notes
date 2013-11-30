package com.pekall.csv;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory to create bean object from a csv line
 */
public class CsvBeanFactory {
    /**
     * Fields in CSV file in order.
     */
    final String[] csvFields;
    final Class beanClass;

    public CsvBeanFactory(String[] csvFields, Class clz) {
        this.csvFields = csvFields;
        this.beanClass = clz;
    }

    public  <T> T createBean(String[] tokens) {
        Constructor ctor;
        try {
            ctor = beanClass.getDeclaredConstructor();
            ctor.setAccessible(true);
            Object object = ctor.newInstance();
            return (T) createBeanInner(tokens, object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object createBeanInner(String[] tokens, Object bean) {
        for (int i = 0; i < tokens.length; i++) {
            try {
                if (tokens[i] == null || "".equals(tokens[i])) continue;

                bean.getClass().getDeclaredFields();
                Field field = bean.getClass().getDeclaredField(csvFields[i]);
                field.setAccessible(true);
                if (field.getType().equals(Boolean.class)) {
                    field.set(bean, Boolean.valueOf(tokens[i]));
                } else if (field.getType().equals(Byte.class)) {
                    field.set(bean, Byte.valueOf(tokens[i]));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(bean, Integer.valueOf(tokens[i]));
                } else if (field.getType().equals(Long.class)) {
                    field.set(bean, Long.valueOf(tokens[i]));
                } else if (field.getType().equals(Float.class)) {
                    field.set(bean, Float.valueOf(tokens[i]));
                } else if (field.getType().equals(Double.class)) {
                    field.set(bean, Double.valueOf(tokens[i]));
                } else if (field.getType().equals(String.class)) {
                    field.set(bean, String.valueOf(tokens[i]));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}
