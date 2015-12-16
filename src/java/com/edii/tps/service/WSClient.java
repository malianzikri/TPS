package com.edii.tps.service;

import com.edii.db.Db;
import com.edii.tools.Encrypt;
import com.edii.tools.ParsingXMLGetSPJM;
import com.edii.tools.ParsingXMLPLP_BATAL;
import com.edii.tools.ParsingXMLResponPLPBatal_Asal;
import com.edii.tools.ParsingXMLResponPLPBatal_Tujuan;
import com.edii.tools.ParsingXMLResponPLP_Asal;
import com.edii.tools.ParsingXMLResponPLP_Tujuan;
import com.edii.tools.SPPB;
import com.edii.tools.Utils;
import static com.edii.tps.service.WSClient.WSDL_URL;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSClient {

    private static final Logger logger = LoggerFactory.getLogger(WSClient.class);
//    static String javapath = "/export/home/wsc/TPSClient";//System.getenv("JAVA_HOME");
//    static String javapath = "C:/Oracle/Middleware/jdk160_14_R27.6.5-32/jre/lib/security";
    static String javapath = "/export/home/glass/TPSServices";
    static String WSDL_URL = "https://tpsonline.beacukai.go.id/tps/service.asmx?wsdl";
    private Db mydb = null;
    private Encrypt encrypt = null;

    public WSClient() {
        encrypt=new Encrypt();
    }
    
    //method masukan t log service ke database
    private void Insert_T_Log_Services(String username, String password,String nama_service,String keterangan) throws Exception
    {

            PreparedStatement preparedStatement = null;
            String query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES (?,?,?,?,?,?)";
            try
            {
                    mydb=new Db();
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1,"LOG_SEQ.NEXTVAL");
                    preparedStatement.setString(2,username);
                    preparedStatement.setString(3,password);
                    preparedStatement.setString(4,nama_service);
                    preparedStatement.setTimestamp(5,getCurrentTimeStamp());
                    preparedStatement.setString(6,keterangan);
                    preparedStatement.executeUpdate();
                    mydb.execute("commit");
            }
            catch (SQLException e)
            {

            }finally {
                    if(preparedStatement != null)
                    {
                            preparedStatement.close();
                    }
                    if(mydb !=null)
                    {
                            mydb.close();
                    }
            }

    }
     private void Insert_T_Log_Services_xml(String username, String password,String nama_service,String keterangan,String xml) throws Exception
    {

            PreparedStatement preparedStatement = null;
            String query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW) VALUES (?,?,?,?,?,?,?)";
            try
            {
                    mydb=new Db();
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1,"LOG_SEQ.NEXTVAL");
                    preparedStatement.setString(2,username);
                    preparedStatement.setString(3,password);
                    preparedStatement.setString(4,nama_service);
                    preparedStatement.setTimestamp(5,getCurrentTimeStamp());
                    preparedStatement.setString(6,keterangan);
                    preparedStatement.setString(7, xml);
                    preparedStatement.executeUpdate();
                    mydb.execute("commit");
            }
            catch (SQLException e)
            {

            }finally {
                    if(preparedStatement != null)
                    {
                            preparedStatement.close();
                    }
                    if(mydb !=null)
                    {
                            mydb.close();
                    }
            }

    }
    //satu kesatuan sama method masukin t log services
    private static java.sql.Timestamp getCurrentTimeStamp() {

                    java.util.Date today = new java.util.Date();
                    return new java.sql.Timestamp(today.getTime());

    }

    public String coarriCodeco_Container(String fpath, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coarriCodecoContainer(fpath, username, password);
        } catch (Exception ex) {
            Insert_T_Log_Services(username, encrypt.encrypt(password),"coarriCodeco_Container", "Error Message "+ex.getMessage());
//            //result = ex.getMessage();
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    public String coarriCodeco_Container_Test(String fStresm, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoContTes(fStresm, username, password);
        } catch (Exception ex) {
//            //result = ex.getMessage();
            Insert_T_Log_Services(username, encrypt.encrypt(password),"coarriCodeco_Container_Test", "Error Message "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    public String coarriCodeco_Kemasan(String fpath, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coarriCodecoKemasan(fpath, username, password);
        } catch (Exception ex) {
//            //result = ex.getMessage();
            Insert_T_Log_Services(username, encrypt.encrypt(password),"coarriCodeco_Kemasan", "Error Message "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
       
        return result;
    }

    public String coarriCodeco_Kemasan_Test(String fpath, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {

            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoKmsTes(fpath, username, password);
        } catch (Exception ex) {
//            //result = ex.getMessage();
            Insert_T_Log_Services(username, encrypt.encrypt(password),"coarriCodeco_Kemasan_Test", "Error Message "+ex.getMessage());
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    public String getImporPermit_FASP(String filepath, String username, String password, String kodeASP, String history) throws Exception {
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String result = "";
        String resultdb = null;
        String refNumber = null;
        SPPB sppb = null;
        try {
            mydb = new Db();
            sppb = new SPPB();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporPermitFASP(username, password, kodeASP);
            try {
                if (result.contains("SPPB")) {

                    Utils.writeStringtoFile(filepath, result);
                    resultdb = sppb.parseDocument(result, username);
                    //Insert to LOG
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit_Fasp", "Nama File "+history+"; panjang File"+result.length());
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit_FASP',SYSDATE,'Nama File " + history + "; panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");

                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE ='" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit_Fasp",result);
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit_FASP',SYSDATE,'" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                }
            } catch (SQLException |IOException e) {
                         Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit_Fasp","Error Exception IO or SQL"+e.getMessage());
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit_FASP',SYSDATE,'Eror Exception IO or SQL Get Import Permit FASP"+e.getMessage()+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                
            }
        } catch (Exception e) {
//            //result = e.getMessage();
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit_FASP',SYSDATE,'Eror Exception Get Import Permit FASP "+e.getMessage()+"Result "+result+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                      Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit_Fasp","Error Exception "+e.getMessage()+" Result : "+result);
                    logger.error(e.getMessage());
          throw new RuntimeException(e);
        }
        finally{
            if (mydb != null) {
                try {
                        mydb.close();
                    } catch (SQLException e) { /* ignored */}
            } 
        }
        return result;
    }

    public String getImporPermit(String filepath, String username, String password, String kodeGudang, String history) throws Exception {
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String result = null;
        String resultdb = null;
        String refNumber = null;
        SPPB sppb = null;

        try {
            mydb = new Db();
            sppb = new SPPB();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporPermit(username, password, kodeGudang);
            try {
                if (result.contains("SPPB")) {
                    Utils.writeStringtoFile(filepath, result);
                    resultdb = sppb.parseDocument(result, username);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit',SYSDATE,'Nama File " + history + "; panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit", "Nama File "+history+"; panjang File"+result.length());

                    //refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + filepath + "',FILESIZE ='" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit',SYSDATE,'" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                      Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit", result);

                }
            } catch (SQLException |IOException e) {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit',SYSDATE,'Eror Exception IO or SQL Get Import Permit "+e.getMessage()+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                      Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit", "Error Exception IO or SQL"+e.getMessage());
            }
        } catch (Exception e) {
//            //result = e.getMessage();
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetImporPermit',SYSDATE,'Eror Exception Get Import Permit "+e.getMessage()+"Result "+result+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImportPermit", "Error Exception"+e.getMessage()+" Result"+result);
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
        }
         finally{
            if (mydb != null) {
                try {
                        mydb.close();
                    } catch (SQLException e) { /* ignored */}
            } 
        }
     
        return result;
    }

    public String getBC23Permit(String filepath, String username, String password, String kodeGudang, String history) throws Exception {
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String result = null;
        String resultdb = null;
        String refNumber = null;
        SPPB sppb = null;
        try {
//            mydb = new Db();
            sppb = new SPPB();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getBC23Permit(username, password, kodeGudang);
            Utils.writeStringtoFile(filepath, result);
            try {
                if (result.contains("SPBB")) {
                    Utils.writeStringtoFile(filepath, result);
                    resultdb = sppb.parseDocument(result, username);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23Permit',SYSDATE,'Nama File " + history + "; panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23Permit", "Nama File "+history+"; panjang File"+result.length());
//                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
//                    //Updtae To Billing PLP
//                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + filepath + "',FILESIZE ='" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
//                    mydb.execute("commit");
                } else {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23Permit',SYSDATE,'" + result + "')";
//                    System.out.println("" + query);
//                    mydb.execute(query);
//                    mydb.execute("commit");
                      Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23Permit", result);

                }
            } catch (SQLException |IOException e) {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23Permit',SYSDATE,'Eror Exception IO or SQL Get BC23 Permit "+e.getMessage()+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23Permit", "Error Exception IO or Sql"+e.getMessage());

            }
        } catch (Exception e) {
//            //result = e.getMessage();
//             query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23Permit',SYSDATE,'Eror Exception  Get BC23 Permit "+e.getMessage()+"Result "+result+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23Permit", "Error Exception"+e.getMessage()+" Result"+result);

            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getBC23PermitFASP(String filepath, String username, String password, String kdASP, String history) throws Exception {
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String result = null;
        String resultdb = null;
        String refNumber = null;
        SPPB sppb = null;
        try {
            mydb = new Db();
            sppb = new SPPB();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getBC23PermitFASP(username, password, kdASP);
            try {
                if (result.contains("SPPB")) {
                    Utils.writeStringtoFile(filepath, result);
                    resultdb = sppb.parseDocument(result, username);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23PermitFASP',SYSDATE,'Nama File " + history + "; panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23PermitFASP", "Nama File "+history+"; panjang File"+result.length());

                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE ='" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES (LOG_SEQ.NEXTVAL,'" + username + "','" + encrypt.encrypt(password) + "','GetBC23PermitFASP',SYSDATE,'" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23PermitFASP", result);

                }
            } catch (SQLException |IOException e) {
//                    query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23PermitFASP',SYSDATE,'Eror Exception IO or SQL Get BC23 Permit FASP "+e.getMessage()+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                        Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23PermitFASP", "Error Exception IO or SQL"+e.getMessage());

            }
        } catch (Exception e) {
//            //result = e.getMessage();
//             query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetBC23PermitFASP',SYSDATE,'Eror Exception  Get BC23 PermitFASP "+e.getMessage()+"Result "+result+"')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
            Insert_T_Log_Services(username, encrypt.encrypt(password),"GetBC23PermitFASP", "Error Exception"+e.getMessage()+" Result"+result);

            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean getImporSppb(String filepath, String username, String password, String noSppb, String tglSppb, String npwpImp) throws Exception {
        boolean result = false;
        Encrypt encrypt = new Encrypt();
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getImporSppb(username, password, noSppb, tglSppb, npwpImp);
            if (fileResult.length() < 50) {
                System.out.println("Data tidak ditemukan untuk no sppb = " + noSppb
                        + "dan tglsppb = " + tglSppb + " serta npwpimp = " + npwpImp);
            } else {
                result = Utils.writeStringtoFile(filepath, fileResult);
            }
        } catch (Exception e) {
            Insert_T_Log_Services(username, encrypt.encrypt(password),"GetImporSppb", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getImpor_Sppb(String filepath, String username, String password, String noSppb, String tglSppb, String npwpImp) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
//            mydb = new Db();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getImporSppb(username, password, noSppb, tglSppb, npwpImp);
            if (result.contains("SPPB")) {
                // write file result
                Utils.writeStringtoFile(filepath, result);
                //Insert to LOG
//                query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//                query = query + "(LOG_SEQ.NEXTVAL,'";
//                query = query + username + "','" + encrypt.encrypt(password) + "','GetImpor_Sppb',SYSDATE,'','" + result + "','" + noSppb + "/" + tglSppb + "/" + npwpImp + "')";
//                mydb.execute(query);
//                mydb.execute("commit");
                Insert_T_Log_Services_xml(username, encrypt.encrypt(password),"GetImpor_Sppb", result,noSppb+"/"+tglSppb+"/"+npwpImp);
            } else {
                //Insert to LOG
//                query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//                query = query + "(LOG_SEQ.NEXTVAL,'";
//                query = query + username + "','" + encrypt.encrypt(password) + "','GetImpor_Sppb',SYSDATE,'','" + result + "','" + noSppb + "/" + tglSppb + "/" + npwpImp + "')";
//                mydb.execute(query);
//                mydb.execute("commit");
                Insert_T_Log_Services_xml(username, encrypt.encrypt(password),"GetImpor_Sppb", result,noSppb+"/"+tglSppb+"/"+npwpImp);
            }
        } catch (Exception e) {
//                query = "INSERT INTO T_LOG_SERVICES(LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//                query = query + "(LOG_SEQ.NEXTVAL,'";
//                query = query + username + "','" + encrypt.encrypt(password) + "','GetImpor_Sppb',SYSDATE,'Eror Exception Get Impor SPPB "+e.getMessage()+"Result "+result+"','" + noSppb + "/" + tglSppb + "/" + npwpImp + "')";
//                mydb.execute(query);
//                mydb.execute("commit");
            Insert_T_Log_Services_xml(username, encrypt.encrypt(password),"GetImpor_Sppb", "Error Exception "+e.getMessage()+" Result :"+result,noSppb+"/"+tglSppb+"/"+npwpImp);
                logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
         
        return result;
    }

    public boolean getResponPLP(String filepath, String username, String password, String kdASP) throws Exception {
        boolean result = false;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getResponPLP(username, password, kdASP);
            if (fileResult.length() < 50) {
                System.out.println("DATA Response PLP Tidak Ditemukan");
            } else {
                result = Utils.writeStringtoFile(filepath, Utils.addXmlHeader() + fileResult);
            }

        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }
        return result;
    }

    public String permohonanPLP_TPS(String localdirectory, String document, String content, String username, String password) throws Exception {

        String result = "";

        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String result1 = Services.permohonanPLP(content, username, password);
            Utils.writeStringtoFile(localdirectory + "RESPLP-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESPLP>" + result1 + "</RESPLP>");
            result = result1;
        } catch (Exception e) {
            Insert_T_Log_Services(username, encrypt.encrypt(password),"permohonanaPLP_TPS", "Error Exception "+e.getMessage());

//            //result = e.getMessage();
        } finally {
            return result;
        }
    }

    public String GetPendukungPLP_TPS(String filepath, String noBC11, String tglBC11, String noCont, String username, String password) throws Exception {

        String result = "";

        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getPendukungPLP(username, password, noBC11, tglBC11, noCont);

            result = fileResult;
            Utils.writeStringtoFile(filepath, Utils.addXmlHeader() + fileResult);
        } catch (Exception e) {
//            //result = e.getMessage();
              Insert_T_Log_Services(username, encrypt.encrypt(password),"GetPendukungPLP_TPS", "Error Exception "+e.getMessage());

        } finally {
            return result;
        }
    }

    public void permohonanPLP(String localdirectory, String filename, String username, String password) throws Exception {
        try{
            Services.httpsConnect(WSDL_URL, javapath);
            String result1 = Services.permohonanPLP(Utils.readFileAsString(localdirectory + filename), username, password);
            Utils.writeStringtoFile(localdirectory + "RESPLP-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESPLP>" + result1 + "</RESPLP>");
        }
        catch (Exception e){
                Insert_T_Log_Services(username, encrypt.encrypt(password),"permohonanaPLP", "Error Exception "+e.getMessage());
       }
    }

    public boolean getSppbBc23(String filepath, String username, String password, String noSppb, String tglSppb, String npwpImp) throws Exception {
        boolean result = false;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getSppbBc23(username, password, noSppb, tglSppb, npwpImp);
            if (fileResult.length() < 50) {
                System.out.println("DATA SPPB BC23 tidak ditemukan");
            } else {
                result = Utils.writeStringtoFile(filepath, fileResult);
            }

        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"getSppbBc23", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }
        return result;
    }

    public String getDataOB(String filepath, String username, String password, String kdASP, String history) throws Exception {
        String result = "";
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.getDataOB(username, password, kdASP);
//            if (fileResult.length() < 50) {
//                System.out.println("DATA OB tidak ditemukan");
//            } else {
            Utils.writeStringtoFile(filepath, Utils.addXmlHeader() + fileResult);
            result = fileResult;
//            }
        } catch (Exception e) {
//            //result = e.getMessage();
             Insert_T_Log_Services(username, encrypt.encrypt(password),"getDataOB", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean GetEksporPermit_FNPE(
            String filepath, String username, String password, String noPE, String npwp, String kdKantor) throws Exception {
        boolean result = false;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            String fileResult = Services.GetEksporPermit_FNPE(username, password, noPE, npwp, kdKantor);
            if (fileResult.length() < 100) {
                System.out.println("DATA EKSPORT PERMIT NPE Tidak Ditemukan");
            } else {
                result = Utils.writeStringtoFile(filepath, fileResult);
            }

        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetEksporPermit_FNPE", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            result = false;
            throw new RuntimeException(e);
        }
        return result;
    }

    public void insertCODECO(String localdirectory, String fileName, String username, String password) throws Exception {
        try {
            if (fileName.startsWith("COCOCONT")) {
                System.out.println("prepare send COCOCONT File");
                String result1 = coarriCodeco_Container(Utils.readFileAsString(localdirectory + fileName), username, password);
                Utils.writeStringtoFile(localdirectory + "RESCOCO-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESCOCO>" + result1 + "</RESCOCO>");

            } else if (fileName.startsWith("COCOKMS")) {
                System.out.println("prepare send COCOKMS File");
                String result2 = coarriCodeco_Kemasan(Utils.readFileAsString(localdirectory + fileName), username, password);
                Utils.writeStringtoFile(localdirectory + "RESCOCO-" + Utils.getNow() + ".xml", Utils.addXmlHeader() + "<RESCOCO>" + result2 + "</RESCOCO>");
            }
        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"insertCODECO", "Error Exception "+e.getMessage());
            logger.error(e.getMessage());
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    public String insertCOARRICODECO_TPS(String document, String content, String username, String password) throws Exception {

        String result = "";

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
        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"InsertCOARICODECO_TPS", "Error Exception "+e.getMessage());
            ////result = e.getMessage();
            logger.error(e.getMessage());
            System.err.println(e);
            throw new RuntimeException(e);
        } finally {
            return result;
        }
    }

    public String UploadMohonPLP(String filename, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.UploadMohonPLP(filename, username, password);
        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"UploadMohonPLP", "Error Exception "+e.getMessage());
            ////result = e.getMessage();
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String UploadBatalPLP(String filename, String username, String password) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.UploadBatalPLP(filename, username, password);
        } catch (Exception e) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"UploadBatalPLP", "Error Exception "+e.getMessage());
            //result = e.getMessage();
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String getResponPLP_Asal(String filepath, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        ParsingXMLResponPLP_Asal ResPLP_Asal = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String refNumber = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            ResPLP_Asal = new ParsingXMLResponPLP_Asal();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponPLP(username, password, kdASP);
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    Utils.writeStringtoFile(filepath, result);
                    // Insert Response from BC into database
                    resultdb = ResPLP_Asal.parseDocumentPLPAsal(result);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLP',SYSDATE,'" + kdASP + "','Nama File " + history + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
//                    
                         Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP", kdASP+" Nama File "+history+"; Panjang File "+result.length());
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        //Updtae To Billing PLP
                        mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                        mydb.execute("commit");
                    } else {
                        mydb.execute("INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES ('GetResponPLP','Parsing Dok Respon PLP','Insert DB','Nama File " + history + "; Error " + resultdb + "',SYSDATE)");
                        mydb.execute("commit");
                    }
                } else {
                    //Insert to LOG from Transaction DB
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLP',SYSDATE,'" + kdASP + "','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP", kdASP+" "+result);
                  
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLP',SYSDATE,'" + kdASP + "','Error : " + e.getMessage() + "')";
//            mydb.execute(query);
//            mydb.execute("commit");
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP","Error Exception"+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String getResponPLPTujuan(String filepath, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        //Encrypt encrypt = null;
        ParsingXMLResponPLP_Tujuan ResPLP_Tujuan = null;
        //Db mydb = null;
        String refNumber = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            ResPLP_Tujuan = new ParsingXMLResponPLP_Tujuan();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponPLPTujuan(username, password, kdASP);
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    Utils.writeStringtoFile(filepath, result);
                    // Insert Response from BC into database
                    resultdb = ResPLP_Tujuan.parseDocumentPLPTujuan(result);
                    //Insert to LOG from Transaction DB
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','Nama File " + history + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLPTujuan", kdASP+" Nama File "+history+"; Panjang File "+result.length());
                  
                    //insert to Temp_DWPORTAL
                    try {
                        query = "INSERT INTO TEMP_T_PLP (ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN)"
                                + "VALUES (T_PLP_SEQ.NEXTVAL,'" + username + "','" + password + "','" + result + "','Q',SYSDATE)";
                        mydb.execute(query);
                        mydb.execute("commit");
                    } catch (Exception e) {
                    }
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    //Insert to LOG from Transaction DB
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLPTujuan", kdASP+" "+result);
                  
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','Error : " + e.getMessage() + "')";
//            mydb.execute(query);
//            mydb.execute("commit");
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLPTujuan", "Eror Exception "+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ResPLP_Tujuan = null;
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String getResponPLP_Tujuan(String filepath, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        //Encrypt encrypt = null;
        ParsingXMLResponPLP_Tujuan ResPLP_Tujuan = null;
        //Db mydb = null;
        String refNumber = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            ResPLP_Tujuan = new ParsingXMLResponPLP_Tujuan();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.GetResponPLP_Tujuan(username, password, kdASP);
            
            try {
                if (result.contains("NO_PLP")) {
                    //write xml respon PLP tujuan
                    Utils.writeStringtoFile(filepath, result);
                    // Insert Response from BC into database
                    resultdb = ResPLP_Tujuan.parseDocumentPLPTujuan(result);
                    //Insert to LOG from Transaction DB
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','Nama File " + history + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP_tujuan", kdASP+" Nama File "+history+"; Panjang File "+result.length());
                  
                    //insert to Temp_DWPORTAL
                    try {
                        query = "INSERT INTO TEMP_T_PLP (ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN)"
                                + "VALUES (T_PLP_SEQ.NEXTVAL,'" + username + "','" + password + "','" + result + "','Q',SYSDATE)";
                        mydb.execute(query);
                        mydb.execute("commit");
                    } catch (Exception e) {
                    }
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    //Insert to LOG from Transaction DB
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP_Tujuan", kdASP+" "+result);
                  
                }
            } catch (Exception e) {
                 Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP_tujuan", "Error Exception"+e.getMessage());
                  
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','GetResponPLPTujuan',SYSDATE,'" + kdASP + "','Error : " + e.getMessage() + "')";
//            mydb.execute(query);
//            mydb.execute("commit");
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponPLP_tujuan", "Error Exception"+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            ResPLP_Tujuan = null;
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String getResponPLPBatal_Asal(String filepath, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        //Encrypt encrypt = null;
        ParsingXMLResponPLPBatal_Asal ResPLPBatal_Asal = null;
        //Db mydb = null;
        String refNumber = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            ResPLPBatal_Asal = new ParsingXMLResponPLPBatal_Asal();//tidak error
            Services.httpsConnect(WSDL_URL, javapath);// eror 
            result = Services.getResponBatalPLP(username, password, kdASP);
            System.out.println(result);
            try {
                if (result.contains("NO_BATAL_PLP")) {
                    //write xml respon PLP tujuan
                    Utils.writeStringtoFile(filepath, result);
                    // Insert Response from BC into database
                    resultdb = ResPLPBatal_Asal.parseDocumentPLPBatalAsal(result);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLP',SYSDATE,'" + kdASP + "','Nama File " + history + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP", kdASP+" Nama File "+history+"; Panjang File "+result.length());
                  
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        //Updtae To Billing PLP
                        mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                        mydb.execute("commit");
                    } else {
                        mydb.execute("INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES ('GetResponPLPBATAL','Parsing Dok Respon PLP','Insert DB','Nama File " + history + "; Error " + resultdb + "',SYSDATE)");
                        mydb.execute("commit");
                    }

                } else {
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLP',SYSDATE,'" + kdASP + "','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP", kdASP+" "+result);
                  
                }
            } catch (Exception e) {
                 Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP", "Error Exception "+e.getMessage());
                  
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLP',SYSDATE,'" + kdASP + "','Error : " + e.getMessage() + "')";
//            mydb.execute(query);
//            mydb.execute("commit");
            Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP","Error Exception"+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            ResPLPBatal_Asal = null;
            return result;
        }
    }

    public String getResponPLPBatal_Tujuan(String filepath, String username, String password, String kdASP, String history) throws Exception {

        String result = "";
        String resultdb = "";
        String query = null;
        //Encrypt encrypt = null;
        ParsingXMLResponPLPBatal_Tujuan ResPLPBatal_Tujuan = null;
        //Db mydb = null;
        String refNumber = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            ResPLPBatal_Tujuan = new ParsingXMLResponPLPBatal_Tujuan();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getResponBatalPLPTujuan(username, password, kdASP);
            try {
                if (result.contains("NO_BATAL_PLP")) {
                    //write xml respon PLP tujuan
                    Utils.writeStringtoFile(filepath, result);
                    //Insert Response from BC into database       
                    ResPLPBatal_Tujuan.parseDocumentPLPBatalTujuan(result);
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLPTujuan',SYSDATE,'" + kdASP + "','Nama File " + history + "; Panjang File '" + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP_tujuan", kdASP+" Nama File "+history+"; Panjang File "+result.length());
                  
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    //Updtae To Billing PLP
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLPTujuan',SYSDATE,'" + kdASP + "','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                     Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP_tujuan", kdASP+" "+result);
                  
                }
            } catch (Exception e) {
                 Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP_tujuan", "Error Exception"+e.getMessage());
                  
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','GetResponBatalPLPTujuan',SYSDATE,'" + kdASP + "','Error : " + e.getMessage() + "')";
//            mydb.execute(query);
//            mydb.execute("commit");
             Insert_T_Log_Services(username, encrypt.encrypt(password),"GetResponBatalPLP_tujuan","Error Exception"+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            ResPLPBatal_Tujuan = null;
            return result;
        }
    }

    public String CoCoCarTer(String fStream, String username, String password) throws Exception {
        String result = "";
        String query = null;
        try {
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.coCoCarTer(fStream, username, password);
        } catch (Exception ex) {
             Insert_T_Log_Services(username, encrypt.encrypt(password),"CoCoCarTer","Error Exception"+ex.getMessage());
                  
            //result = ex.getMessage();
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return result;
    }

    public String getSPJM(String filepath, String username, String password, String kdtTps, String history) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String resultdb = null;
        String refNumber = null;
        ParsingXMLGetSPJM parsSPJM = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            parsSPJM = new ParsingXMLGetSPJM();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getSPJM(username, password, kdtTps);
            try {
                if (result.contains("CAR")) {
                    //parsing xml 
                    resultdb = parsSPJM.parseDocument(result);
                    //write xml respon Get SPJM
                    Utils.writeStringtoFile(filepath, result);
                    //insert to log
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','getSPJM',SYSDATE,'Nama File " + history + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetSPJM", " Nama File "+history+"; Panjang File "+result.length());
                  
                    //Updtae To Billing PLP
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + history + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN) VALUES ";
//
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','getSPJM',SYSDATE,'','" + result + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"GetSPJM", result);
                  
                }
            } catch (Exception e) {
                Insert_T_Log_Services(username, encrypt.encrypt(password),"GetSPJM", "Error Exception"+e.getMessage());
                  
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','getSPJM',SYSDATE,'','Error : " + e.getMessage() + "','')";
//            mydb.execute(query);
//            mydb.execute("commit");
            Insert_T_Log_Services(username, encrypt.encrypt(password),"GetSPJM","Error Exception"+e.getMessage());
                  

            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            return result;
        }
    }

    public String getSPJMOnDemand(String filepath, String username, String password, String noPIB, String tglPIB) throws Exception {
        String result = "";
        String query = null;
        //Encrypt encrypt = null;
        //Db mydb = null;
        String resultdb = null;
        String refNumber = null;
        ParsingXMLGetSPJM parsSPJM = null;
        try {
            mydb = new Db();
            encrypt = new Encrypt();
            parsSPJM = new ParsingXMLGetSPJM();
            Services.httpsConnect(WSDL_URL, javapath);
            result = Services.getSPJMOnDemand(username, password, noPIB, tglPIB);
            try {
                if (result.contains("CAR")) {
                    //parsing xml 
                    resultdb = parsSPJM.parseDocument(result);
                    //write xml respon Get SPJM
                    Utils.writeStringtoFile(filepath, result);
                    //insert to log
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','getSPJMOnDemand',SYSDATE,'Nama File " + filepath + "; Panjang File " + result.length() + "')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"getSPJMOnDemand", " Nama File "+filepath+"; Panjang File "+result.length());
                  
                    //Updtae To Billing PLP
                    refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                    mydb.execute("UPDATE MIBILLING SET EDINUMKIRIM = '" + username + "',EDINUMTERIMA = 'EDITPS001',FILENAME  = '" + filepath + "',FILESIZE = '" + result.length() + "' WHERE SNRF = '" + refNumber + "'");
                    mydb.execute("commit");
                } else {
                    //Insert to LOG
//                    query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//                    query = query + "(LOG_SEQ.NEXTVAL,'";
//                    query = query + username + "','" + encrypt.encrypt(password) + "','getSPJMOnDemand',SYSDATE,'','" + result + "','')";
//                    mydb.execute(query);
//                    mydb.execute("commit");
                    Insert_T_Log_Services(username, encrypt.encrypt(password),"getSPJMOnDemand", result);
                  
                }

            } catch (Exception e) {
                Insert_T_Log_Services(username, encrypt.encrypt(password),"getSPJMOnDemand", "Error Exception"+e.getMessage());
                  
            }
        } catch (Exception e) {
            //result = e.getMessage();
            //Insert to LOG
//            query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KD_ASP,KETERANGAN,XML_REQUEST_NEW) VALUES ";
//            query = query + "(LOG_SEQ.NEXTVAL,'";
//            query = query + username + "','" + encrypt.encrypt(password) + "','getSPJMOnDemand',SYSDATE,'','Error : " + e.getMessage() + "','')";
//            mydb.execute(query);
//            mydb.execute("commit");
            Insert_T_Log_Services(username, encrypt.encrypt(password),"getSPJMOnDemand", "Erorr Exception"+e.getMessage());
                  
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            encrypt = null;
            mydb = null;
            return result;
        }
    }
}
