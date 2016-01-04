package com.edii.tps.service;

import File.TXT.CreateFile;
import Loggers.Loggers;
import com.edii.tools.ExcuteProses;
import com.edii.tools.MoveFile;
import com.edii.tools.Tanggalan;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TPSUpload {

    private static final Logger logger = LoggerFactory.getLogger(TPSUpload.class);
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();
    Loggers log;
    private static CreateFile cf;
    private static String succes = "";
    private static String error = "";
    private static Tanggalan tgl;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
        if (propertiesFile == null) {
            logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
            succes += new Loggers().LogSuccess("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.") + "\r\n";
        }

        try {
            PROPERTIES.load(propertiesFile);
            succes += new Loggers().LogSuccess("Properties load '" + propertiesFile) + "\r\n";
        } catch (IOException e) {
            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage());
            error = new Loggers().LogSuccess("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage()) + "\r\n";
        } finally {
            String filename = "Load propertiesFile di Class TPSUpload" + tgl.UNIXNUMBER() + ".txt";
            if (!error.equalsIgnoreCase("")) {
                new CreateFile().newFile("isi dengan direktory", filename, error);
            } else {
                new CreateFile().newFile("isi dengan direktory", filename, succes);
            }
        }
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

    public String DocumentNotInvokeBC(String resultdb) {
        String result = "";
        if (resultdb.contains("Proses Berhasil")) {
            result = resultdb.substring(resultdb.indexOf("_"), resultdb.indexOf("."));
            result = result.replace("_", "");
        } else if (resultdb.contains("PROSES SUCCESS") || resultdb.contains("Format") || resultdb.contains("Error")) {
            result = resultdb;
        } else {
            result = "Username tidak sesuai";
        }
        return result;
    }

    public String upload_TPS(String document, String fStream, String usernamews, String passwordws) throws ClassNotFoundException {

        ExcuteProses exc = new ExcuteProses();
        WSClient client = new WSClient();
        MoveFile mv = new MoveFile();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        String namafile = usernamews + "." + document + "." + tgl.UNIXNUMBER() + ".XML";
        String dirOutbox = PROPERTIES.getProperty("tps.outbox") + File.separator + usernamews;
        String dirHistoy = PROPERTIES.getProperty("tps.history") + File.separator + usernamews;
        String DirError = PROPERTIES.getProperty("tps.error") + File.separator + usernamews;


        String result = "";
        String dirDaily = "";
        String message = "";
        String messageErr = "";
        boolean createFile;
        boolean moveFile;
        try {
            tgl = new Tanggalan();
            cf = new CreateFile();
            log = new Loggers();
            //Checking Or Create Directory Outbox
            CheckOrCreateDirectory(dirOutbox);
            message += log.LogSuccess(dirOutbox.toString()) + "\r\n";
            //Create File into Direktory Outbox
            createFile = cf.newFile(dirOutbox, namafile, fStream);
            message += log.LogSuccess(String.valueOf(createFile)) + "\r\n";
            //fileter file to send ws Server
            if (document.equalsIgnoreCase("UploadMohonPLP")) {
                result = client.uploadMohonPLP(fStream, usernamews, passwordws);
                message += log.LogSuccess(result.toString()) + "\r\n";
            } else if (document.equalsIgnoreCase("UploadBatalPLP")) {
                result = client.uploadBatalPLP(fStream, usernamews, passwordws);
                message += log.LogSuccess(result.toString()) + "\r\n";
            } else if (document.equalsIgnoreCase("CoCoCarTer")) {
                result = client.cocoCarTer(fStream, usernamews, passwordws);
                message += log.LogSuccess(result.toString()) + "\r\n";
            } else if (document.equalsIgnoreCase("COCOKMS") || document.equalsIgnoreCase("COCOCONT") || document.equalsIgnoreCase("COCOCONT-Test") || document.equalsIgnoreCase("COCOKMS-Test")) {
                if (fStream.contains("<KD_TPS>PLDC</KD_TPS>") || usernamews.equalsIgnoreCase("KOJA") || usernamews.equalsIgnoreCase("CART")) {
                    result = client.insertCOARRICODECO_TPS(document, fStream, usernamews, passwordws);
                    message += log.LogSuccess(result.toString()) + "\r\n";
                } else {
                    //Parsing Dokumen not invoke BC
                    result = exc.ExcuteParsingDok(document, fStream, "");
                    message += log.LogSuccess(result.toString()) + "\r\n";
                    //Checking Result
                    result = DocumentNotInvokeBC(result);
                    message += log.LogSuccess(result.toString()) + "\r\n";
                }
            } else if (document.equalsIgnoreCase("REQPLP_AJU")) {
                result = client.uploadMohonPLP(fStream, usernamews, passwordws);
            } else if (document.equalsIgnoreCase("REQPLP_BATAL")) {
                result = client.uploadBatalPLP(fStream, usernamews, passwordws);
            } else if (document.equalsIgnoreCase("COCOCAR")) {
                result = client.cocoCarTer(fStream, usernamews, passwordws);
            }
            try {
                if (result.contains("Proses Berhasil") || result.contains("PROSES SUCCESS")) {
                    //Checking Or Create Directory History
                    CheckOrCreateDirectory(dirHistoy);
                    message += log.LogSuccess(dirHistoy.toString()) + "\r\n";
                    //Checking Or Create Directory Daily History
                    dirDaily = dirHistoy + File.separator + df.format(date);
                    CheckOrCreateDirectory(dirDaily);
                    message += log.LogSuccess(dirDaily.toString()) + "\r\n";
                    moveFile = mv.moveFile(dirOutbox + File.separator + namafile, dirDaily + File.separator + namafile);
                    message += log.LogSuccess(String.valueOf(moveFile)) + "\r\n";
                } else {
                    //Checking Or Create Directory Error
                    CheckOrCreateDirectory(DirError);
                    message += log.LogSuccess(DirError.toString()) + "\r\n";
                    //Checking Or Create Directory Daily Error
                    dirDaily = DirError + File.separator + df.format(date);
                    CheckOrCreateDirectory(dirDaily);
                    message += log.LogSuccess(dirDaily.toString()) + "\r\n";
                    moveFile = mv.moveFile(dirOutbox + File.separator + namafile, dirDaily + File.separator + namafile);
                    message += log.LogSuccess(String.valueOf(moveFile)) + "\r\n";
                }
                //Insert into Log Database
                exc.ExcuteLog(usernamews, passwordws, document, result, dirDaily + File.separator + namafile, fStream);
            } catch (Exception e) {
                exc.ExcuteError(e.getMessage(), "TPSUpload", usernamews);
                messageErr += log.LogError(e.getMessage()) + "\r\n";
            }

        } catch (Exception ex) {
            exc.ExcuteError(ex.getMessage(), "TPSUpload", usernamews);
            messageErr += log.LogError(ex.getMessage()) + "\r\n";

        } finally {
            client = null;
            String filename = "TPSUpload.upload_TPS" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("isi dengan direktory", filename, messageErr);
            } else {
                cf.newFile("isi dengan direktory", filename, message);
            }
        }
        return result;
    }
}
