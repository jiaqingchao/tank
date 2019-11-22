package com.jqc.tank.common;


import java.util.Properties;

public class PropertyMgr {

    public static Properties properties = null;

    private static void loadProperties() {
        try {

            properties = new Properties();
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String get(String key){
        if(properties == null){
            loadProperties();
        }
        return properties.getProperty(key);
    }

    public static int getInt(String key){
        if(properties == null){
            loadProperties();
        }
        return Integer.valueOf(properties.getProperty(key));

    }



}
