package com.pekall.csv.bean;

import java.lang.reflect.Field;

public abstract class CsvVoBase {
    // field to be convert to csv columns
    protected final String[] csvFields;

    protected CsvVoBase(String[] csvFields) {
        this.csvFields = csvFields;
    }

    /**
     * Convert bean to a string array
     * @return a string array from the bean
     */
    public String[] toCvs() {
        if(csvFields == null) throw new IllegalStateException();

        String[] col = new String[csvFields.length];
        for (int i = 0; i < csvFields.length; i++) {
            String fieldName = csvFields[i];
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldObj = field.get(this);
                if (fieldObj != null) {
                    col[i] = fieldObj.toString();
                } else {
                    col[i] = null;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return col;
    }
}
