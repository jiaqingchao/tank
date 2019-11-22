package com.jqc.tank.common;


import java.util.Properties;

public class PropertyMgr {

    private PropertyMgr(){}

    public static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getString(String key){
        return properties.getProperty(key);
    }

    public static int getInt(String key){
        return Integer.valueOf(properties.getProperty(key));

    }



}
