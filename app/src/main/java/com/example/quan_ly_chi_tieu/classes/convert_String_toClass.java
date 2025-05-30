package com.example.quan_ly_chi_tieu.classes;

public class convert_String_toClass {
    public static Class<?> resolveClass(String className) throws ClassNotFoundException {
        // Check if the className represents a primitive type
        switch (className) {
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            case "boolean":
                return boolean.class;
        }

        // If the className is not a primitive type, load the class dynamically
        return Class.forName(className);
    }

}
