/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.operation.db;

import DatabaseGenerator.DatabaseOracle;
import com.edii.controller.ControllerGetResponPLP;
import com.edii.db.Db;
import com.edii.model.ModelGetResponPLP;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Aslichatun Nisa
 */
public class OperationDBGetResponPLP implements ControllerGetResponPLP {

    static org.apache.commons.logging.Log logger = LogFactory.getLog(Db.class);
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();
    DatabaseOracle dbO = new DatabaseOracle();
    private ArrayList<String> data;
    String value = "";
    String column = "";
    String tabel = "";

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage());
        }
    }

    private void OpenConnection() throws ClassNotFoundException {
        dbO.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
    }

    @Override
    public String savedata_getresponplp_header(ModelGetResponPLP plp) {

        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = new ArrayList<>();
        tabel = "DUAL";
        column = "RESPLP_SEQ.NEXTVAL as ID";
        data = dbO.query_select_raw(tabel, column);
        String respon_id = data.get(0);

        tabel = "T_RESPON_PLP";
        column = "RESPONID,KD_KANTOR,KD_TPS,REF_NUMBER,NO_PLP,TGL_PLP,ALASAN_REJECT,RECEIVED_DATE";
        value = respon_id + "," + plp.getKd_kantor() + "," + plp.getRef_number() + "," + plp.getNo_plp()
                + "," + plp.getTgl_plp() + "," + plp.getAlasan_reject() + ",SYSDATE";
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();

        return respon_id;
    }

    @Override
    public String savedata_getresponplp_kms(ModelGetResponPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_PLP_KMS";
        column = "RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL,STATUS_PLP";
        value = plp.getRef_number() + "," + plp.getJns_kms() + "," + plp.getJml_kms()
                + "," + plp.getNo_bl_awb() + "," + plp.getTgl_bl_awb() + "," + plp.getFl_setuju_kms();
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();
        return plp.getKd_kantor();
    }

    @Override
    public String savedata_getresponplp_cont(ModelGetResponPLP plp, String respon_id) {
        try {
            OpenConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabel = "T_RESPON_PLP_CONT";
        column = "RESPONID,NO_CONT,UK_CONT,JNS_CONT,STATUS_PLP";
        value = respon_id + "," + plp.getNo_cont() + "," + plp.getUk_cont()
                + "," + plp.getJns_cont() + "," + plp.getFl_setuju_cont();
        dbO.query_insert(tabel, column, value);
        dbO.close_connection();
        return plp.getKd_kantor();
    }
}
