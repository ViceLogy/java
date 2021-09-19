package com.company.resources;

import com.company.entity.Order;
import com.company.entity.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Parser {

    public static <T> T parse(String line, Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T result = constructor.newInstance();
            String[] str = line.split(",");
            for (String each : str){
                    String[] keyVal = each.split("=");
                    Field declaredField = clazz.getDeclaredField(keyVal[0]);
                    declaredField.setAccessible(true);
                    if (declaredField.getType().isAssignableFrom(int.class)) {
                        declaredField.set(result, Integer.valueOf(keyVal[1]));
                    } else if (declaredField.getType().isAssignableFrom(String.class)) {
                        declaredField.set(result, keyVal[1]);
                    } else {
                        throw new IllegalArgumentException("Unknown type");
                    }
            }
                return result;
            }catch (Exception ex){
                System.out.println(ex.getMessage()+"\n"+ Arrays.toString(ex.getStackTrace()));
            }


        return null;
    }
}
