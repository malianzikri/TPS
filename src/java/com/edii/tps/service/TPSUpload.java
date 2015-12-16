package com.edii.tps.service;

import com.edii.db.Db;
import com.edii.tools.ExcuteProses;
import com.edii.tools.Utils;
import com.gxs.otx.net.OTXInitiator;
import com.edii.tools.MoveFile;
import com.edii.tools.Tanggalan;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.helpers.DateTimeDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TPSUpload {

    private static final Logger logger = LoggerFactory.getLogger(TPSUpload.class);
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

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

    public void start() {

        String outboxdirectory = PROPERTIES.getProperty("ftp.outbox");
        String historydirectory = PROPERTIES.getProperty("ftp.historydirectory");
        String usernamews = PROPERTIES.getProperty("ftp.usernamews");
        String passwordws = PROPERTIES.getProperty("ftp.passwordws");
        String host = PROPERTIES.getProperty("ftp.host");
        String password = PROPERTIES.getProperty("ftp.password");
        String username = PROPERTIES.getProperty("ftp.username");
        String usertun = PROPERTIES.getProperty("ftp.usertun");
        String receiver = PROPERTIES.getProperty("ftp.receiver");
        String receivertun = PROPERTIES.getProperty("ftp.receivertun");

        String[] file;
        String filename;
        WSClient client;
        MoveFile mv;
        try {
            mv = new MoveFile();
            client = new WSClient();
            System.out.println("Upload Scheduler");
            File folder = new File(outboxdirectory);
            file = folder.list();
            for (int j = 0; j < file.length; j++) {
                //fileter file to send ws Server
                if (file[j].startsWith("COCOKMS") || file[j].startsWith("COCOCONT")) {
                    filename = file[j].toString();
                    System.out.println(filename);
                    logger.info("prepare send File to WS Server ");
                    client.insertCODECO(outboxdirectory, filename, usernamews, passwordws);
                    if (mv.move(outboxdirectory + filename, historydirectory + filename)) {
                        System.out.println(filename + " has been Moved to " + historydirectory);
                    } else {
                        System.out.println(filename + " Can't be Moved to " + historydirectory);
                    }
                } else if (file[j].startsWith("REQPLP")) {
                    filename = file[j].toString();
                    client.permohonanPLP(outboxdirectory, filename, usernamews, passwordws);
                    if (mv.move(outboxdirectory + filename, historydirectory + filename)) {
                        System.out.println(filename + " has been Moved to " + historydirectory);
                    } else {
                        System.out.println(filename + " Can't be Moved to " + historydirectory);
                    }
                }
            }

            //move local file to local history  folder
            OTXInitiator sender = new OTXInitiator(host, 7777);

            if (null == sender) {
                logger.error("Can't Connect to ftp E*S");
                throw new RuntimeException("Can't Connect to ftp E*S");
            }
            for (int j = 0; j < file.length; j++) {
                if (file[j].startsWith("RESCOCO")) {
                    sender.send(username, password, receiver, "RESCOCO", Utils.getUniqueString(), usertun, receivertun, 'X', outboxdirectory + file[j]);
                    System.out.println("Send RESCOCO =>" + outboxdirectory + file[j]);
                    if (!mv.move(outboxdirectory + file[j], historydirectory + file[j])) {
                        System.out.println(file[j] + " Can't Move to " + historydirectory);
                    }
                } else if (file[j].startsWith("RESPLP")) {
                    sender.send(username, password, receiver, "RESPLP", Utils.getUniqueString(), usertun, receivertun, 'X', outboxdirectory + file[j]);
                    System.out.println("Send RESPLP =>" + outboxdirectory + file[j]);
                    if (!mv.move(outboxdirectory + file[j], historydirectory + file[j])) {
                        System.out.println(file[j] + " Can't Move to " + historydirectory);
                    }
                }
            }
            sender.signOff();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error(Utils.getStackTrace(ex));
        } finally {
            client = null;
        }
    }

    public String upload_TPS(String document, String fStream, String usernamews, String passwordws) {

        String result = null;
        ExcuteProses exc = null;
        WSClient client;
        String refNumber = null;
        String resultdb = null;
        MoveFile mv = null;
        String localOut = null;
        String localHis = null;
        String localError = null;
        Tanggalan tgl = null;
        File cekDir = null;
        String DirDaily = null;
        String Nmfile = null;
        DateFormat df = new DateTimeDateFormat();
        Date date = null;
        boolean sementara = false;
        df = new SimpleDateFormat("yyyyMMdd");
        tgl = new Tanggalan();
        mv = new MoveFile();
        client = new WSClient();
        exc = new ExcuteProses();
        Nmfile = usernamews + "." + document + "." + tgl.UNIXNUMBER() + ".XML";
        String temp = null;
        try {
            temp = exc.ExcuteCrateFile(usernamews, fStream, localOut, Nmfile);
        } catch (Exception ex) {
            // tambah log create file error 
            java.util.logging.Logger.getLogger(TPSUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //fileter file to send ws Server
            if (document.equalsIgnoreCase("COCOKMS") || document.equalsIgnoreCase("COCOCONT") || document.equalsIgnoreCase("COCOCONT-Test") || document.equalsIgnoreCase("COCOKMS-Test")) {
                if (fStream.contains("<KD_TPS>PLDC</KD_TPS>") || usernamews.equalsIgnoreCase("KOJA") || usernamews.equalsIgnoreCase("CART")) {
                    result = client.insertCOARRICODECO_TPS(document, fStream, usernamews, passwordws);
                } else {
                    resultdb = exc.ExcuteParsingDok(document, fStream, "");
                    if (resultdb.contains("Proses Berhasil")) {
                        result = resultdb.substring(resultdb.indexOf("_"), resultdb.indexOf("."));
                        result = result.replace("_", "");
                    }  else if (resultdb.contains("PROSES SUCCESS") || resultdb.contains("Format") ||resultdb.contains("Error")) {
                        result = resultdb;
                    }  else {
                        result = "Username tidak sesuai";
                    }
                    if (!temp.equalsIgnoreCase("false")) {
                        date = new Date();
                        //Checking Directory Daily
                        cekDir = new File(localHis);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        DirDaily = localHis + File.separator + df.format(date);
                        //Checking Directory Daily
                        cekDir = new File(DirDaily);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        mv.moveFile(localOut + File.separator + Nmfile, DirDaily + File.separator + Nmfile);
                    }
                    exc.ExcuteLog(usernamews, passwordws, document, result, DirDaily + File.separator + Nmfile, fStream);
                }
            } else if (document.equalsIgnoreCase("REQPLP_AJU")) {
                result = client.UploadMohonPLP(fStream, usernamews, passwordws);
            } else if (document.equalsIgnoreCase("REQPLP_BATAL")) {
                result = client.UploadBatalPLP(fStream, usernamews, passwordws);
            } else if (document.equalsIgnoreCase("COCOCAR")) {
                result = client.CoCoCarTer(fStream, usernamews, passwordws);
            }

            //Create File
            try {
                cekDir = new File(PROPERTIES.getProperty("tps.outbox"));
                if (!cekDir.exists()) {
                    cekDir.mkdir();
                }
                cekDir = new File(PROPERTIES.getProperty("tps.history"));
                if (!cekDir.exists()) {
                    cekDir.mkdir();
                }
                localOut = PROPERTIES.getProperty("tps.outbox") + File.separator + usernamews;
                localHis = PROPERTIES.getProperty("tps.history") + File.separator + usernamews;

                if (result.contains("Proses Berhasil")) {
                    //parsing coarri_codeco
//                    resultdb = exc.ExcuteParsingDok(document, fStream, "");
                    if (!temp.equalsIgnoreCase("false")) {
                        date = new Date();
                        //Checking Directory Daily
                        cekDir = new File(localHis);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        DirDaily = localHis + File.separator + df.format(date);
                        //Checking Directory Daily
                        cekDir = new File(DirDaily);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        mv.moveFile(localOut + File.separator + Nmfile, DirDaily + File.separator + Nmfile);
                    }

                } else {
                    localError = PROPERTIES.getProperty("tps.error") + File.separator + usernamews;
                    cekDir = new File(PROPERTIES.getProperty("tps.error"));
                    if (!cekDir.exists()) {
                        cekDir.mkdir();
                    }
                    if (!temp.equalsIgnoreCase("false")) {
                        date = new Date();
                        //Checking Directory Daily
                        cekDir = new File(localError);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        DirDaily = localError + File.separator + df.format(date);
                        //Checking Directory Daily
                        cekDir = new File(DirDaily);
                        if (!cekDir.exists()) {
                            cekDir.mkdir();
                        }
                        mv.moveFile(localOut + File.separator + Nmfile, DirDaily + File.separator + Nmfile);
                    }
                }
                exc.ExcuteLog(usernamews, passwordws, document, result, DirDaily + File.separator + Nmfile, fStream);
            } catch (Exception e) {
                e.printStackTrace();
                exc.ExcuteError(e.getMessage(), "CoCoCarTer", usernamews);
            }

        } catch (Exception ex) {
            exc.ExcuteError(ex.getMessage(), "CoCoCarTer", usernamews);
            logger.error(ex.getMessage());
            logger.error(Utils.getStackTrace(ex));
        } finally {
            client = null;
            return result;
        }
    }
}
