///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.edii.tps.service;
//
//import com.edii.tools.ExcuteProses;
//import com.gxs.otx.net.OTXInitiator;
//import com.edii.tools.MoveFile;
//import com.edii.tools.Utils;
//import com.edii.xml.Reqpeks;
//import com.edii.xml.Reqsppb;
//import com.edii.xml.XmlReqpeks;
//import com.edii.xml.XmlReqsppb;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.Properties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// *
// * @author aldi
// */
//public class TPSDownload {
//
//    private static final Logger logger = LoggerFactory.getLogger(TPSDownload.class);
//    private static final String PROPERTIES_FILE = "config.properties";
//    private static final Properties PROPERTIES = new Properties();
//    private ExcuteProses exc= new ExcuteProses ();
//    static {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
//
//        if (propertiesFile == null) {
//            logger.debug("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
//        }
//
//        try {
//            PROPERTIES.load(propertiesFile);
//        } catch (IOException e) {
//            logger.debug("Cannot load properties file '" + PROPERTIES_FILE + "'." + e.getMessage());
//        }
//    }
//
//    public String download_Requset_sppb(String usernamews, String passwordws, String No_Sppb, String Tgl_Sppb, String NPWP_Imp) {
//        String outboxdirectory = PROPERTIES.getProperty("tpsTES.outboxfolder");
//        WSClient client;
//        String result = null;
//        try {
//            client = new WSClient();
//            System.out.println("Upload TPSServices");
//            result = client.getImpor_Sppb(outboxdirectory, usernamews, passwordws, No_Sppb, Tgl_Sppb, NPWP_Imp);
//        } catch (Exception e) {
//             exc.ExcuteError(e.getMessage(), "download_request_sppb_class_TPSDownload", usernamews);
//        }
//        return result;
//    }
//
//    public String download_GetSPJM_onDemand(String usernamews, String passwordws, String No_PIB, String Tgl_PIB) {
//        String inboxdirectory = PROPERTIES.getProperty("tps" + usernamews.toUpperCase() + ".inboxfolder");
//        WSClient client;
//        String result = null;
//        String filename = "";
//        try {
//            client = new WSClient();
//            System.out.println("Upload TPSServices");
//            filename = "DOKSPJM_" + "." + usernamews + "." + Utils.getNow() + ".xml";
//            result = client.getSPJMOnDemand(inboxdirectory + filename, usernamews, passwordws, No_PIB, Tgl_PIB);
//        } catch (Exception e) {
//            exc.ExcuteError(e.getMessage(), "download_GetSPJM_onDemand_class_TPSDownload", usernamews);
//        }
//        return result;
//    }
//
//    public String download_TPS(String document, String kdAsp, String usernamews, String passwordws) {
//
//        String inboxdirectory = PROPERTIES.getProperty("tps" + usernamews.toUpperCase() + ".inboxfolder");
//        String historydirectory = PROPERTIES.getProperty("tps" + usernamews.toUpperCase() + ".historyfolder");
//
//        String filename = "";
//        String result = null;
//        WSClient client;
//        MoveFile mv = null;
//
//        try {
//            mv = new MoveFile();
//            client = new WSClient();
//            logger.info("Ready to Execute Class TPSDownload");
//
//            if (document.equalsIgnoreCase("ImporPermit")) {
//                //Fungsi 1 Get Data SPPB (GetImporPermit)
//                filename = "SPPB-" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getImporPermit(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("BC23Permit")) {
//                //Fungsi 2 Get Data SPPB BC23 (GetBC23Permit)
//                filename = "SPPB_BC23-" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getBC23Permit(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ImporPermitFASP")) {
//                //Fungsi 4 Get Data SPPB FASP (GetImporPermit_FASP)
//                filename = "SPPB-" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getImporPermit_FASP(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("BC23PermitFASP")) {
//                //Fungsi 5 Get Data SPPB BC 2.3 FASP (GetBC23Permit_FASP)
//                filename = "SPPB_BC23-" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getBC23PermitFASP(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("GetDataOB")) {
//                //Fungsi 6 Get Data OB (GetDataOB)
//                filename = "OB-" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getDataOB(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ResponPLP")) {
//                //Fungsi 3 Get Response PLP
//                filename = "DOKPLP_ASAL_" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getResponPLP_Asal(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ResponPLPTujuan")) {
//                //Fungsi 3 Get Response PLP
//                filename = "DOKPLP_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getResponPLPTujuan(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ResponBatalPLP")) {
//                //Fungsi 3 Get Response PLP
//                filename = "DOKPLP_BATAL_ASAL_" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getResponPLPBatal_Asal(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ResponBatalPLPTujuan")) {
//                //Fungsi 3 Get Response PLP
//                filename = "DOKPLP_BATAL_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getResponPLPBatal_Tujuan(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("GetSPJM")) {
//                //Fungsi Get SPJM
//                filename = "DOKSPJM_" + "." + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getSPJM(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            } else if (document.equalsIgnoreCase("ResponPLP_Tujuan")) {
////                Method baru
//                //Fungsi 3 Get Response PLP
//                filename = "DOKPLP_TUJUAN_" + usernamews + "." + Utils.getNow() + ".xml";
//                result = client.getResponPLP_Tujuan(inboxdirectory + filename, usernamews, passwordws, kdAsp, historydirectory + filename);
//            }
//            mv.moveFile(inboxdirectory + filename, historydirectory + filename);
//        } catch (Exception ex) {
//            exc.ExcuteError(ex.getMessage(), "download_TPS_class_TPSDownload", usernamews);
//            System.err.println(ex);
//            logger.error(Utils.getStackTrace(ex));
//        } finally {
//            client = null;
//            filename = null;
//            mv = null;
//
//            return result;
//        }
//    }
//}
