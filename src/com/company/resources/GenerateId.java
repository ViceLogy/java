package com.company.resources;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;

import static com.company.resources.FileSource.*;

public class GenerateId {

    public synchronized static <T> T generateID(Class<?> cls) {
        Integer intValue = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(IDFILE));
            BufferedWriter writer = new BufferedWriter(new FileWriter(POJOFILE))) {
            Field declaredField = cls.getDeclaredField("id");
            declaredField.setAccessible(true);
            String line = reader.readLine();
            if (declaredField.getType() == int.class) {
                while (line != null) {
                    if (line.startsWith(cls.getSimpleName())) {
                        String[] keyVal = line.split("=");
                        int id = Integer.parseInt(keyVal[1]);
                        id++;
                        intValue = id;
                        writer.write(cls.getSimpleName() + "=" + id);
                        writer.newLine();
                    } else if (!line.startsWith(cls.getSimpleName())) {
                        writer.write(line);
                        writer.newLine();
                    }
                    line = reader.readLine();
                }
            }
        }catch (Exception ex){
                System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }

        IDFILE.delete();
        FileSource.POJOFILE.renameTo(FileSource.IDFILE);
        return (T) intValue;
    }
    
}
