package com.tencent.helloui3.util;

import android.os.Environment;
import android.support.test.uiautomator.UiObject2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Random;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by xinmei365 on 17/1/17.
 */

public class Utils {
    static private Logger log = Utils.getLogger(Utils.class);

    public static org.apache.log4j.Logger getLogger(Class clazz) {
//        final LogConfigurator logConfigurator = new LogConfigurator();
//        logConfigurator.setFileName(Environment.getExternalStorageDirectory().toString() + File.separator + "log4jfile.log");
//        logConfigurator.setRootLevel(Level.ALL);
//        logConfigurator.setLevel("org.apache", Level.ALL);
//        logConfigurator.setUseFileAppender(true);
//        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
////        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
//        logConfigurator.setMaxFileSize(1024 * 500);
//        logConfigurator.setImmediateFlush(true);
//        logConfigurator.setMaxBackupSize(1);
//        logConfigurator.configure();
        Logger log = Logger.getLogger(clazz);
        return log;
    }
    static public void sleep(Integer milliseconds, String desc) {
        //整体值得1/3进行随机
        Random random = new Random();
        Double val = random.nextDouble()*milliseconds/3;
        val += milliseconds;

        try {
            Thread.sleep(val.intValue());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
