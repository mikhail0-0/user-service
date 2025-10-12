package org.example.utils;

import org.hibernate.cfg.Configuration;

public class ConfigurationFactoryUtil {
    private ConfigurationFactoryUtil() {

    }

    public static Configuration getConfiguration(){
        return new Configuration().configure();
    }
}
