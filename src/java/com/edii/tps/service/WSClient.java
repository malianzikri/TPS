package com.edii.tps.service;
import com.edii.tools.Encrypt;

import com.edii.tools.Utils;
import static com.edii.tps.service.WSClient.WSDL_URL;
import java.io.IOException;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import DatabaseGenerator.DatabaseOracle;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import Loggers.Loggers;
import File.TXT.CreateFile;
import com.edii.parsingfile.ParsingGetResponBatalPLP;
import com.edii.parsingfile.ParsingGetResponBatalPLPTujuan;
import com.edii.parsingfile.ParsingGetResponPLP;
import com.edii.parsingfile.ParsingGetResponPLPTujuan;
import com.edii.parsingfile.ParsingGetSPJM;
import com.edii.parsingfile.ParsingSPPB;

public class WSClient {

    private static final Logger logger = LoggerFactory.getLogger(WSClient.class);
//    static String javapath = "/export/home/wsc/TPSClient";//System.getenv("JAVA_HOME");
//    static String javapath = "C:/Oracle/Middleware/jdk160_14_R27.6.5-32/jre/lib/security";
    static String javapath = "/export/home/glass/TPSServices";
    static String WSDL_URL = "https://tpsonline.beacukai.go.id/tps/service.asmx?wsdl";
    private Encrypt encrypt = null;
    private final DatabaseOracle databaseOracle;
    private static String PROPERTIES_FILE;
    private static Properties PROPERTIES;
    private String value;
    private String column;
    private String table;
    private final DateFormat dateFormat;
    private final Calendar cal;
    private final Loggers loggers;
    private static CreateFile cf;

    public WSClient() throws ClassNotFoundException {
        value = "";
        table = "";
        column = "";
        encrypt = new Encrypt();
        PROPERTIES = new Properties();
        PROPERTIES_FILE = "db.properties";
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        databaseOracle = new DatabaseOracle();
        loggers = new Loggers();
        cf = new CreateFile();
    }

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

    //method masukan t log service ke database
    private boolean insert_t_log_services(String username, String password, String nama_service, String keterangan) throws Exception {
        boolean status;
        value = "LOG_SEQ.NEXTVAL" + "," + username + "," + password + "," + nama_service + "," + dateFormat.format(cal.getTime()) + "," + keterangan;
        column = "LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN";
        table = "T_LOG_SERVICES";
        try {
            databaseOracle.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));

            status = databaseOracle.query_insert(table, column, value);
        } catch (Exception e) {
            status = false;
        } finally {
            databaseOracle.close_connection();
            table = "";
            column = "";
            value = "";
        }
        return status;
    }

    private boolean insert_tlLog_services_xml(String username, String password, String nama_service, String keterangan, String xml) throws Exception {

        value = "LOG_SEQ.NEXTVAL" + "," + username + "," + password + "," + nama_service + "," + dateFormat.format(cal.getTime()) + "," + keterangan + "," + xml;
        column = "LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW";
        table = "T_LOG_SERVICES";
        boolean status;
        try {
            databaseOracle.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));

            status = databaseOracle.query_insert(table, column, value);
        } catch (Exception e) {
            status = false;
        } finally {
            databaseOracle.close_connection();
            table = "";
            column = "";
            value = "";
        }
        return status;
    }

    private boolean update_mbling(String username, String history, int filesize, String ref_number) {
        table = "MIBILLING";
        String column_value = username + "," + "EDITPS001" + "," + history + "," + Integer.toString(filesize);
        column = "EDINUMKIRIM,EDINUMTERIMA,FILENAME,FILESIZE";
        String column_where = "SNRF=";
        String value_where = ref_number;
        String operand = "";
        boolean status;
        try {
            databaseOracle.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
            status = databaseOracle.query_update_with_where(table, column, column_value, column_where, value_where, operand);
        } catch (Exception e) {
            status = false;
        } finally {
            databaseOracle.close_connection();
            table = "";
            column = "";
            value = "";
        }
        return status;
    }

    private boolean insert_tpslog(String error_code, String proces_name, String sub_proces, String error_desc) {
        table = "TPSLOG";
        boolean status;
        column = "ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE";
        value = error_code + "," + proces_name + "," + sub_proces + "," + error_desc + "," + dateFormat.format(cal.getTime());
        try {
            databaseOracle.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));

            status = databaseOracle.query_insert(table, column, value);
        } catch (Exception e) {
            status = false;
        } finally {
            databaseOracle.close_connection();
            table = "";
            column = "";
            value = "";
        }
        return status;
    }

    private boolean insert_temp_t_plp(String username, String password, String result) {
        table = "TEMP_T_PLP";
        column = "ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN";
        value = "T_PLP_SEQ.NEXTVAL" + "," + username + "," + password + "," + result + "," + "Q" + "," + dateFormat.format(cal.getTime());
        boolean status;
        try {
            databaseOracle.connectDatabase(PROPERTIES.getProperty("db.host"), PROPERTIES.getProperty("db.SID"), PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));

            status = databaseOracle.query_insert(table, column, value);
        } catch (Exception e) {
            status = false;
        } finally {
            databaseOracle.close_connection();
            table = "";
            column = "";
            value = "";
        }
        return status;

    }

    public String coarriCodeco_Container(String fpath, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {

            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coarriCodecoContainer(fpath, username, password);
            message_loggers += loggers.LogSuccess(result) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Container", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            //make file loggers
        } catch (Exception ex) {
            message_loggers = loggers.LogError(String.valueOf(ex.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Container", "Error Message " + ex.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            //make file loggers
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;

    }
//note fstream tidak diganti dikarenakan soap service nama variablenya menggunakan fstream

    public String coarriCodeco_Container_Test(String fstream, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoContTes(fstream, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Container_Test", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception ex) {
            message_loggers = loggers.LogError(String.valueOf(ex.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Container_Test", "Error Message " + ex.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;

    }

    public String coarriCodeco_Kemasan(String fpath, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {

            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coarriCodecoKemasan(fpath, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Kemasan", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception ex) {
            message_loggers = loggers.LogError(String.valueOf(ex.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Kemasan", "Error Message " + ex.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;

    }

    public String coarriCodeco_Kemasan_Test(String fpath, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoKmsTes(fpath, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Kemasan_Test", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";

        } catch (Exception ex) {
            message_loggers = loggers.LogError(String.valueOf(ex.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "coarriCodeco_Kemasan_Test", "Error Message " + ex.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;

    }

    public String getImporPermit_FASP(String filepath, String filename, String username, String password, String kodeASP, String history) throws Exception {
        String result = "";
        String resultdb;
        String refNumber;
        ParsingSPPB sppb;
        boolean status;
        cf = new CreateFile();
        String message_loggers = "";
        try {
            sppb = new ParsingSPPB();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporPermitFASP(username, password, kodeASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("SPPB")) {
                    cf.newFile(filepath, filename, result);
                    resultdb = sppb.parseDocument(result, username);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //Insert to LOG
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit_Fasp", "Nama File " + history + "; panjang File" + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit_Fasp", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (SQLException | IOException e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit_Fasp", "Error Exception IO or SQL" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit_Fasp", "Error Exception " + e.getMessage() + " Result : " + result);
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getImporPermit(String filepath, String filename, String username, String password, String kodeGudang, String history) throws Exception {

        String result = "";
        String resultdb;
        String refNumber = "";
        ParsingSPPB sppb;
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;

        try {
            sppb = new ParsingSPPB();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporPermit(username, password, kodeGudang);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("SPPB")) {
                    cf.newFile(filepath, filename, result);
                    resultdb = sppb.parseDocument(result, username);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit", "Nama File " + history + "; panjang File" + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (SQLException | IOException e) {
                message_loggers = loggers.LogError(e.getLocalizedMessage()) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit", "Error Exception IO or SQL" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetImportPermit", "Error Exception" + e.getMessage() + " Result" + result);
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getBC23Permit(String filepath, String filename, String username, String password, String kodeGudang, String history) throws Exception {
        String result = "";
        String resultdb;
        String refNumber = "";
        ParsingSPPB sppb;
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;
        try {
            sppb = new ParsingSPPB();

            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getBC23Permit(username, password, kodeGudang);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            Utils.writeStringtoFile(filepath, result);
            try {
                if (result.contains("SPBB")) {
                    //Create File into Direktory Outbox
                    cf.newFile(filepath, filename, result);
                    resultdb = sppb.parseDocument(result, username);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23Permit", "Nama File " + history + "; panjang File" + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23Permit", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (SQLException | IOException e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23Permit", "Error Exception IO or Sql" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23Permit", "Error Exception" + e.getMessage() + " Result" + result);
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getBC23PermitFASP(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb;
        String refNumber;
        ParsingSPPB sppb;
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;
        try {

            sppb = new ParsingSPPB();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getBC23PermitFASP(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("SPPB")) {
                    //Create File into Direktory Outbox
                    cf.newFile(filepath, filename, result);
                    resultdb = sppb.parseDocument(result, username);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23PermitFASP", "Nama File " + history + "; panjang File" + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23PermitFASP", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (SQLException | IOException e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23PermitFASP", "Error Exception IO or SQL" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetBC23PermitFASP", "Error Exception" + e.getMessage() + " Result" + result);
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getImporSppb(String filepath, String filename, String username, String password, String noSppb, String tglSppb, String npwpImp) throws Exception {
        String result = "";
        boolean status;
        String message_loggers = "";
        cf = new CreateFile();
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporSppb(username, password, noSppb, tglSppb, npwpImp);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            if (result.length() < 50) {
                String message = "Data tidak ditemukan untuk no sppb = " + noSppb
                        + "dan tglsppb = " + tglSppb + " serta npwpimp = " + npwpImp;
                message_loggers += loggers.LogSuccess(String.valueOf(message)) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetImporSppb", "Error length " + message);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            } else {
                //Create File into Direktory Outbox
                cf.newFile(filepath, filename, result);
                message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetImporSppb", "Berhasil" + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetImporSppb", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getPendukungPLP(String filepath, String noBC11, String tglBC11, String noCont, String username, String password) throws Exception {

        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getPendukungPLP(username, password, noBC11, tglBC11, noCont);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";

            Utils.writeStringtoFile(filepath, Utils.addXmlHeader() + result);
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetPendukungPLP_TPS", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetPendukungPLP_TPS", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
        }
        return result;

    }

    public boolean getSppbBc23(String filepath, String filename, String username, String password, String noSppb, String tglSppb, String npwpImp) throws Exception {
        boolean result = false;
        String message_loggers = "";
        cf = new CreateFile();
        ParsingSPPB sppb;
        boolean status;
        try {
            sppb = new ParsingSPPB();
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getSppbBc23(username, password, noSppb, tglSppb, npwpImp);
            message_loggers += loggers.LogSuccess(String.valueOf(fileResult)) + "\r\n";
            if (fileResult.length() < 50) {
                message_loggers += loggers.LogSuccess("Data SPPB BC23 tidak ditemukan") + "\r\n";
                System.out.println("DATA SPPB BC23 tidak ditemukan");
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "getSppbBc23", "Error  data sppb bc23 tidak ditemukan");
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            } else {
                cf.newFile(filepath, filename, fileResult);
                sppb.parseDocument(fileResult, username);
                message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "getSppbBc23", "Berhasil " + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            }

        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "getSppbBc23", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getDataOB(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {
        String result = "";
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getDataOB(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            if (result.length() < 50) {
                System.out.println("DATA OB tidak ditemukan");
            } else {
                cf.newFile(filepath, filename, result);
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "getDataOB", "Berhasil " + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "getDataOB", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean getEksporPermit_FNPE(
            String filepath, String filename, String username, String password, String noPE, String npwp, String kdKantor) throws Exception {
        boolean result = false;
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.GetEksporPermit_FNPE(username, password, noPE, npwp, kdKantor);
            message_loggers += loggers.LogSuccess(String.valueOf(fileResult)) + "\r\n";
            if (fileResult.length() < 100) {
                message_loggers += loggers.LogSuccess("Data eksport permit NPE tidak ditemukan") + "\r\n";
                System.out.println("DATA EKSPORT PERMIT NPE Tidak Ditemukan");
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetEksporPermit_FNPE", "Error data eksport permit NPE tidak ditemukan");
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            } else {
                result = Utils.writeStringtoFile(filepath, fileResult);
                message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetEksporPermit_FNPE", "Error Exception " + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            }

        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetEksporPermit_FNPE", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }

        return result;

    }

    public void insertCODECO(String localdirectory, String fileName, String username, String password) throws Exception {
        String result;
        String message_loggers = "";
        boolean status;
        try {
            if (fileName.startsWith("COCOCONT")) {
                System.out.println("prepare send COCOCONT File");
                result = coarriCodeco_Container(Utils.readFileAsString(localdirectory + fileName), username, password);
                message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
                Utils.writeStringtoFile(localdirectory + "RESCOCO-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESCOCO>" + result + "</RESCOCO>");
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "insertCODECO", "Berhasil " + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            } else if (fileName.startsWith("COCOKMS")) {
                System.out.println("prepare send COCOKMS File");
                message_loggers += loggers.LogSuccess("preapare send cookms file") + "\r\n";
                result = coarriCodeco_Kemasan(Utils.readFileAsString(localdirectory + fileName), username, password);
                message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
                Utils.writeStringtoFile(localdirectory + "RESCOCO-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESCOCO>" + result + "</RESCOCO>");
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "insertCODECO", "Berhasil " + result);
                message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "insertCODECO", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    public String insertCOARRICODECO_TPS(String document, String content, String username, String password) throws Exception {

        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            if (document.equalsIgnoreCase("COCOCONT")) {
                result = coarriCodeco_Container(content, username, password);
            } else if (document.equalsIgnoreCase("COCOKMS")) {
                result = coarriCodeco_Kemasan(content, username, password);
            } else if (document.equalsIgnoreCase("COCOCONT-Test")) {
                result = coarriCodeco_Container_Test(content, username, password);
            } else if (document.equalsIgnoreCase("COCOKMS-Test")) {
                result = coarriCodeco_Kemasan_Test(content, username, password);
            }
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "InsertCOARICODECO_TPS", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "InsertCOARICODECO_TPS", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            System.err.println(e);
            throw new RuntimeException(e);
        }
        return result;

    }

    public String uploadMohonPLP(String filename, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {

            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.UploadMohonPLP(filename, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "UploadMohonPLP", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "UploadMohonPLP", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String uploadBatalPLP(String filename, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.UploadBatalPLP(filename, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "UploadBatalPLP", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "UploadBatalPLP", "Error Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getResponPLP(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        ParsingGetResponPLP plp = new ParsingGetResponPLP();
        cf = new CreateFile();
        String refNumber = null;
        encrypt = new Encrypt();
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponPLP(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    cf.newFile(filepath, filename, result);
                    // Insert Response from BC into database
                    resultdb = plp.parseDocumentPLPAsal(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                        //Updtae To Billing PLP
                        status = update_mbling(username, history, result.length(), refNumber);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                        //insert into table t_log_service
                        status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP", kdASP + " " + result);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    } else {
                        //insert into table tpslog
                        status = insert_tpslog("GetResponPLP", "Parsing Dok Respon PLP", "Insert DB", "Nama File " + history + "; Error " + resultdb + "");
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    }
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP", kdASP + " " + result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (Exception e) {
                //insert into table t_log_service
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP", "Error Exception" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP", "Error Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getResponPLPTujuan(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        ParsingGetResponPLPTujuan plp = new ParsingGetResponPLPTujuan();
        String refNumber = null;
        encrypt = new Encrypt();
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponPLPTujuan(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    cf.newFile(filepath, filename, result);
                    // Insert Response from BC into database
                    resultdb = plp.parseDocumentPLPTujuan(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLPTujuan", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    //insert to Temp_DWPORTAL
                    try {
                        status = insert_temp_t_plp(username, password, result);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                        query = "INSERT INTO TEMP_T_PLP (ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN)"
//                                + "VALUES (T_PLP_SEQ.NEXTVAL,'" + username + "','" + password + "','" + result + "','Q',SYSDATE)";
//                        mydb.execute(query);
//                        mydb.execute("commit");
                    } catch (Exception e) {
                        message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                        //insert into table t_log_service
                        status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLPTujuan", "Eror Exception " + e.getMessage());
                        message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
                    }
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                    //Updtae To Billing PLP
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLPTujuan", kdASP + " " + result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (Exception e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLPTujuan", "Eror Exception " + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLPTujuan", "Eror Exception " + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getResponPLP_Tujuan(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        ParsingGetResponPLPTujuan plp = new ParsingGetResponPLPTujuan();
        String refNumber = null;
        String message_loggers = "";
        boolean status;
        cf = new CreateFile();
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.GetResponPLP_Tujuan(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    cf.newFile(filepath, filename, query);
                    // Insert Response from BC into database
                    resultdb = plp.parseDocumentPLPTujuan(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP_tujuan", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    //insert to Temp_DWPORTAL
                    try {
                        status = insert_temp_t_plp(username, password, result);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                        query = "INSERT INTO TEMP_T_PLP (ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN)"
//                                + "VALUES (T_PLP_SEQ.NEXTVAL,'" + username + "','" + password + "','" + result + "','Q',SYSDATE)";
//                        mydb.execute(query);
//                        mydb.execute("commit");
                    } catch (Exception e) {
                        //insert into table t_log_service
                        message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                        status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP_tujuan", "Error Exception" + e.getMessage());
                        message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
                    }
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                    //Update To Billing PLP
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP_Tujuan", kdASP + " " + result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";

                }
            } catch (Exception e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP_tujuan", "Error Exception" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponPLP_tujuan", "Error Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getResponBatalPLP(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb;
        ParsingGetResponBatalPLP plp = new ParsingGetResponBatalPLP();//tidak error
        String refNumber = "";
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);// eror 
            result = Services.getResponBatalPLP(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            System.out.println(result);
            try {
                if (result.contains("NO_BATAL_PLP")) {
                    //write xml respon PLP tujuan
                    cf.newFile(filepath, filename, result);
                    // Insert Response from BC into database
                    resultdb = plp.parseDocumentPLPBatalAsal(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                        //Updtae To Billing PLP
                        status = update_mbling(username, history, result.length(), refNumber);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                        //insert into table t_log_service
                        status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                        mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                        mydb.execute("commit");
                    } else {
                        //insert into table tpslog
                        status = insert_tpslog(resultdb, username, username, refNumber);
                        message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                        mydb.execute("INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES ('GetResponPLPBATAL','Parsing Dok Respon PLP','Insert DB','Nama File " + history + "; Error " + resultdb + "',SYSDATE)");
//                        mydb.execute("commit");
                    }

                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP", kdASP + " " + result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";

                }
            } catch (Exception e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP", "Error Exception " + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP", "Error Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getResponBatalPLPTujuan(String filepath, String filename, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        ParsingGetResponBatalPLPTujuan plp = new ParsingGetResponBatalPLPTujuan();
        String refNumber;
        String message;
        String message_loggers = "";
        cf = new CreateFile();
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponBatalPLPTujuan(username, password, kdASP);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("NO_BATAL_PLP")) {
                    //write xml respon PLP tujuan
                    cf.newFile(filepath, filename, result);
                    //Insert Response from BC into database       
                    message = plp.parseDocumentPLPBatalTujuan(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(message)) + "\r\n";
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP_tujuan", kdASP + " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                    //Updtae To Billing PLP
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {

                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP_tujuan", kdASP + " " + result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";

                }
            } catch (Exception e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP_tujuan", "Error Exception" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetResponBatalPLP_tujuan", "Error Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String cocoCarTer(String fStream, String username, String password) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoCarTer(fStream, username, password);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "CoCoCarTer", "Berhasil " + result);
            message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";

        } catch (Exception ex) {
            message_loggers = loggers.LogError(String.valueOf(ex.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "CoCoCarTer", "Error Exception" + ex.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    public String getSPJM(String filepath, String filename, String username, String password, String kdtTps, String history) throws Exception {
        String result = "";
        String message_loggers = "";
        boolean status;
        String resultdb;
        String refNumber;
        ParsingGetSPJM spjm = new ParsingGetSPJM();
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getSPJM(username, password, kdtTps);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("CAR")) {
                    //parsing xml 
                    resultdb = spjm.parseDocument(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //write xml respon Get SPJM
                    cf.newFile(filepath, filename, result);
                    //insert into t_log_services
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetSPJM", " Nama File " + history + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    //Updtae To Billing PLP
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                    status = update_mbling(username, history, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
                    //insert into t_log_services
                    status = insert_t_log_services(username, encrypt.encrypt(password), "GetSPJM", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }
            } catch (Exception e) {
                //insert into t_log_services
                insert_t_log_services(username, encrypt.encrypt(password), "GetSPJM", "Error Exception" + e.getMessage());

            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into t_log_services
            status = insert_t_log_services(username, encrypt.encrypt(password), "GetSPJM", "Error Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    public String getSPJMOnDemand(String filepath, String filename, String username, String password, String noPIB, String tglPIB) throws Exception {
        String result = "";
        String resultdb;
        String refNumber;
        String message_loggers = "";
        boolean status;
        ParsingGetSPJM spjm = new ParsingGetSPJM();
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getSPJMOnDemand(username, password, noPIB, tglPIB);
            message_loggers += loggers.LogSuccess(String.valueOf(result)) + "\r\n";
            try {
                if (result.contains("CAR")) {
                    //parsing xml 
                    resultdb = spjm.parseDocument(result);
                    message_loggers += loggers.LogSuccess(String.valueOf(resultdb)) + "\r\n";
                    //write xml respon Get SPJM
                    cf.newFile(filepath, filename, result);
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "getSPJMOnDemand", " Nama File " + filepath + "; Panjang File " + result.length());
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                    //Updtae To Billing PLP
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    message_loggers += loggers.LogSuccess(String.valueOf(refNumber)) + "\r\n";
                    status = update_mbling(username, password, result.length(), refNumber);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + filepath + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
                    //insert into table t_log_service
                    status = insert_t_log_services(username, encrypt.encrypt(password), "getSPJMOnDemand", result);
                    message_loggers += loggers.LogSuccess(String.valueOf(status)) + "\r\n";
                }

            } catch (Exception e) {
                message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
                //insert into table t_log_service
                status = insert_t_log_services(username, encrypt.encrypt(password), "getSPJMOnDemand", "Error Exception" + e.getMessage());
                message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            }
        } catch (Exception e) {
            message_loggers = loggers.LogError(String.valueOf(e.getLocalizedMessage())) + "\r\n";
            //insert into table t_log_service
            status = insert_t_log_services(username, encrypt.encrypt(password), "getSPJMOnDemand", "Erorr Exception" + e.getMessage());
            message_loggers += loggers.LogError(String.valueOf(status)) + "\r\n";
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }
}
