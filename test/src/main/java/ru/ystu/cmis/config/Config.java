package ru.ystu.cmis.config;

/*import jdk.internal.util.xml.impl.Input;*/

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String EXT = ".properties";
    public static final String URL = "url";
    public static final String DRIVER ="driver";

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    private final Properties properties;
    private Config(){ properties = new Properties();}

    public static Config getConfig(String name){
        Config cfg = new Config();
        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(name+EXT);
            cfg.properties.load(stream);
        } catch (IOException e){
            System.err.println("Error while read config ["+ name +"]");
        }
        System.out.println("Read config [" +name+ "]");
        return cfg;
    }
    public String get(String paramName){
        String val = properties.getProperty(paramName);
        return val == null ?"": val;
    }
}
