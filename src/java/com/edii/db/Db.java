/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edii.db;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.LogFactory;

public class Db extends com.edii.db.DbOrcl
{
    static org.apache.commons.logging.Log logger = LogFactory.getLog(Db.class);
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();

    static{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
                logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'."+  e.getMessage());
        }
    }

    public Db() throws Exception {               
        super.setHost(PROPERTIES.getProperty("db.host"));
        super.setPort(PROPERTIES.getProperty("db.port"));
        super.setUser(PROPERTIES.getProperty("db.username"));
        super.setPswd(PROPERTIES.getProperty("db.password"));
        super.setSID(PROPERTIES.getProperty("db.SID"));
        super.setAutoCommit(false);
        if (!super.connect()) {
            System.out.println("Database connection failed!");
        }  
    }

    public Db(String host, String port, String sid, String username, String password) throws Exception {
        super.setHost(host);
        super.setPort(port);
        super.setSID(sid);
        super.setUser(username);
        super.setPswd(password);
        super.setAutoCommit(false);
        if (!super.connect()) {
            System.out.println("Database connection failed!");
        }  
    }

    public Db(String jndi, String host, String port, String sid, String username, String password) throws Exception {
        super.setJNDI(jndi);
        if(!super.jndiconnect()) {
            super.setHost(host);
            super.setPort(port);
            super.setSID(sid);
            super.setUser(username);
            super.setPswd(password);
            super.setAutoCommit(false);
            if (!super.connect()) {
                System.out.println("Database connection failed!");
            }
        }
    }

    public Db(String jndi, String host1, String port1, String sid1, String username1, String password1,
            String host2, String port2, String sid2, String username2, String password2) throws Exception {
        super.setJNDI(jndi);
        if(!super.jndiconnect()) {
            super.setHost(host1);
            super.setPort(port1);
            super.setSID(sid1);
            super.setUser(username1);
            super.setPswd(password1);
            super.setAutoCommit(false);
            if(!super.connect()) {
                super.setHost(host2);
                super.setPort(port2);
                super.setSID(sid2);
                super.setUser(username2);
                super.setPswd(password2);
                super.setAutoCommit(false);
                if (!super.connect()) {
                    System.out.println("Database connection failed!");
                }
            }
        }
    }

    public Db(String jndi) {
        try {
            super.setJNDI(jndi);
            super.jndiconnect();
        }
        catch(Exception e) {}
    }
}

