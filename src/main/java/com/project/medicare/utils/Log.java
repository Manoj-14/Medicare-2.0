package com.project.medicare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    public static final Logger logger = LoggerFactory.getLogger(Log.class);

    public static void INFO(Object obj,String message){
        logger.info(message,obj);
    }

    public static void DEBUG(String message){
        logger.debug(message);
    }

    public static void warn(String message){
        logger.warn(message);
    }

    public static void ERROR(String message){
        logger.error(message);
    }
}
