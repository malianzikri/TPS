/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tps.service;

import File.MoveFile;
import File.TXT.CreateFile;
import Loggers.Loggers;
import com.edii.operation.db.logDatabase;
import com.edii.tools.Tanggalan;
import com.edii.tools.Utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aldi
 */
public class TPSDownload {

    private static final Logger logger = LoggerFactory.getLogger(TPSDownload.class);
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();
    MoveFile mv;
    logDatabase logDb;
    WSClient client;
    Loggers log;
    private static CreateFile cf;
    private static Tanggalan tgl;

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

    public String download_Requset_sppb(String usernamews, String passwordws, String No_Sppb, String Tgl_Sppb, String NPWP_Imp) throws ClassNotFoundException {
        String dirInbox = PROPERTIES.getProperty("tps.inbox") + File.separator + usernamews;
        String dirHistoy = PROPERTIES.getProperty("tps.history.inbox") + File.separator + usernamews;
        String dirError = PROPERTIES.getProperty("tps.error.inbox") + File.separator + usernamews;
        client = new WSClient();
        logDb = new logDatabase();
        String result = null;
        String filename = "";
        String message = "";
        String messageErr = "";
        String dirDaily = "";
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        mv = new MoveFile();
        try {
            //Checking Or Create Directory History
            CheckOrCreateDirectory(dirHistoy);
            message += log.LogSuccess(dirHistoy.toString()) + "\r\n";
            //Checking Or Create Directory Daily History
            dirDaily = dirHistoy + File.separator + df.format(date);
            CheckOrCreateDirectory(dirDaily);
            System.out.println("Upload TPSServices");
            filename = "REQSPPB_" + "." + usernamews + "." + Utils.getNow() + ".xml";
            result = client.getImporSppb(dirInbox + File.separator, filename, usernamews, passwordws, No_Sppb, Tgl_Sppb, NPWP_Imp);
            mv.isExecute(dirInbox + File.separator + filename, dirDaily + File.separator + filename);
        } catch (Exception e) {
            //Checking Or Create Directory History
            CheckOrCreateDirectory(dirError);
            message += log.LogSuccess(dirError.toString()) + "\r\n";
            //Checking Or Create Directory Daily History
            dirDaily = dirError + File.separator + df.format(date);
            CheckOrCreateDirectory(dirDaily);
            mv.isExecute(dirInbox + File.separator + filename, dirDaily + File.separator + filename);
            logDb.excuteLogError(e.getMessage(), "download_request_sppb_class_TPSDownload", usernamews);
        }
        return result;
    }

    public String download_GetSPJM_onDemand(String usernamews, String passwordws, String No_PIB, String Tgl_PIB) throws ClassNotFoundException {
        String dirInbox = PROPERTIES.getProperty("tps.inbox") + File.separator + usernamews;
        String dirHistoy = PROPERTIES.getProperty("tps.history.inbox") + File.separator + usernamews;
        String dirError = PROPERTIES.getProperty("tps.error.inbox") + File.separator + usernamews;
        client = new WSClient();
        logDb = new logDatabase();
        String result = null;
        String filename = "";
        String message = "";
        String messageErr = "";
        String dirDaily = "";
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        mv = new MoveFile();
        try {
            //Checking Or Create Directory History
            CheckOrCreateDirectory(dirHistoy);
            message += log.LogSuccess(dirHistoy.toString()) + "\r\n";
            //Checking Or Create Directory Daily History
            dirDaily = dirHistoy + File.separator + df.format(date);
            CheckOrCreateDirectory(dirDaily);
            System.out.println("Upload TPSServices");
            filename = "DOKSPJM_" + "." + usernamews + "." + Utils.getNow() + ".xml";
            result = client.getSPJMOnDemand(dirInbox + File.separator, filename, usernamews, passwordws, No_PIB, Tgl_PIB);
            mv.isExecute(dirInbox + File.separator + filename, dirDaily + File.separator + filename);
        } catch (Exception e) {
            //Checking Or Create Directory History
            CheckOrCreateDirectory(dirError);
            message += log.LogSuccess(dirError.toString()) + "\r\n";
            //Checking Or Create Directory Daily History
            dirDaily = dirError + File.separator + df.format(date);
            CheckOrCreateDirectory(dirDaily);
            mv.isExecute(dirInbox + File.separator + filename, dirDaily + File.separator + filename);
            logDb.excuteLogError(e.getMessage(), "download_GetSPJM_onDemand_class_TPSDownload", usernamews);
        }
        return result;
    }

    public void CheckOrCreateDirectory(String Directory) {
        log = new Loggers();
        tgl = new Tanggalan();
        String message = "";
        String messageErr = "";
        try {
            //Checking Directory Or Create Directory
            File cekDir = new File(Directory);
            message += log.LogSuccess(cekDir.toString()) + "\r\n";
            if (!cekDir.exists()) {
                cekDir.mkdir();
            }
        } catch (Exception e) {
            messageErr += log.LogError(e.getMessage()) + "\r\n";
        } finally {
            String filename = "CheckOrCreateDirectory" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("isi dengan direktory", filename, messageErr);
            } else {
                cf.newFile("isi dengan direktory", filename, message);
            }
        }
    }

    public String download_TPS(String document, String kdAsp, String usernamews, String passwordws) throws ClassNotFoundException {


        String dirInbox = PROPERTIES.getProperty("tps.inbox") + File.separator + usernamews;
        String dirHistoy = PROPERTIES.getProperty("tps.history.inbox") + File.separator + usernamews;
        String DirError = PROPERTIES.getProperty("tps.error.inbox") + File.separator + usernamews;
        String filename = "";
        String result = null;
        client = new WSClient();
        logDb = new logDatabase();
        mv = new MoveFile();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String message = "";
        String messageErr = "";
        String dirDaily = "";
        try {
            logger.info("Ready to Execute Class TPSDownload");
            //Checking Or Create Directory History
            CheckOrCreateDirectory(dirHistoy);
            message += log.LogSuccess(dirHistoy.toString()) + "\r\n";
            //Checking Or Create Directory Daily History
            dirDaily = dirHistoy + File.separator + df.format(date);
            CheckOrCreateDirectory(dirDaily);
            if (document.equalsIgnoreCase("ImporPermit")) {
                //Fungsi 1 Get Data SPPB (GetImporPermit)
                filename = "SPPB-" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getImporPermit(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("BC23Permit")) {
                //Fungsi 2 Get Data SPPB BC23 (GetBC23Permit)
                filename = "SPPB_BC23-" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getBC23Permit(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ImporPermitFASP")) {
                //Fungsi 4 Get Data SPPB FASP (GetImporPermit_FASP)
                filename = "SPPB-" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getImporPermit_FASP(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("BC23PermitFASP")) {
                //Fungsi 5 Get Data SPPB BC 2.3 FASP (GetBC23Permit_FASP)
                filename = "SPPB_BC23-" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getBC23PermitFASP(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("GetDataOB")) {
                //Fungsi 6 Get Data OB (GetDataOB)
                filename = "OB-" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getDataOB(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ResponPLP")) {
                //Fungsi 3 Get Response PLP
                filename = "DOKPLP_ASAL_" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getResponPLP(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ResponPLPTujuan")) {
                //Fungsi 3 Get Response PLP
                filename = "DOKPLP_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getResponPLPTujuan(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ResponBatalPLP")) {
                //Fungsi 3 Get Response PLP
                filename = "DOKPLP_BATAL_ASAL_" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getResponBatalPLP(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ResponBatalPLPTujuan")) {
                //Fungsi 3 Get Response PLP
                filename = "DOKPLP_BATAL_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getResponBatalPLPTujuan(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("GetSPJM")) {
                //Fungsi Get SPJM
                filename = "DOKSPJM_" + "." + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getSPJM(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            } else if (document.equalsIgnoreCase("ResponPLP_Tujuan")) {
                // Method baru
                //Fungsi 3 Get Response PLP
                filename = "DOKPLP_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
                result = client.getResponPLP_Tujuan(dirInbox + File.separator, filename, usernamews, passwordws, kdAsp, dirDaily + File.separator + filename);
            }

            mv.isExecute(dirInbox + File.separator + filename, dirDaily + File.separator + filename);
        } catch (Exception ex) {
            logDb.excuteLogError(ex.getMessage(), "download_TPS_class_TPSDownload", usernamews);
            System.err.println(ex);
            logger.error(Utils.getStackTrace(ex));
        } finally {
            client = null;
            filename = null;
            mv = null;

            return result;
        }
    }
}
