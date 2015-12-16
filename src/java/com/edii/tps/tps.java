package com.edii.tps;

import com.edii.db.Db;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.Properties;
import com.edii.tools.CreateFile;
import com.edii.tools.Encrypt;
import com.edii.tools.ExcuteProses;
import com.edii.tools.GenerateResponPLP_Tujuan_Tes;
import com.edii.tools.GenerateXMLCFS;
import com.edii.tools.GenerateXMLDW;
import com.edii.tools.ParsingXMLPLP;
import com.edii.tools.ParsingXMLPLP_BATAL;
import com.edii.tools.ParsingXMLCFS;
import com.edii.tools.ParsingXMLResponPLPBatal_Asal;
import com.edii.tools.ParsingXMLResponPLP_Asal;
import com.edii.tools.ResCFS;
import com.edii.tools.Tanggalan;
import com.edii.tps.service.TPSUpload;
import com.edii.tps.service.TPSDownload;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebService()
public class tps {

    String localFolder;
    private ExcuteProses exc = new ExcuteProses();

    private String getLocalFolder() {
        return localFolder;
    }

    //insert tpslog
    private String InsertTPSLOG(String error_code,String file, String resultdb){
        String result=null;
        String query = null;
        PreparedStatement preparedStatement = null;
        Db mydb = null;
        try{
        mydb = new Db();
        query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES (?,'Parsing Dok PLP','Insert DB',?,SYSDATE)";
                        preparedStatement = mydb.preparedstmt(query);
                        preparedStatement.setString(1, error_code);
                        preparedStatement.setString(2, "Nama File " + file + "; Error " + resultdb);
                        preparedStatement.executeUpdate();
                        mydb.execute("commit");
                        result = "Gagal Insert ke Database IPC ";
        } catch (Exception ex) {
        }
        return result;
    }
    
    //update mibiling
    private void UpdateMIBILLING(String Username, String file, String fStream, String refNumber) {
        String query = null;
        PreparedStatement preparedStatement = null;
        Db mydb = null;

        try {
            mydb = new Db();
            query = "UPDATE MIBILLING SET EDINUMKIRIM = ?,EDINUMTERIMA = 'EDITPS001',FILENAME  = ?, FILESIZE = ? WHERE SNRF = ?";
            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, file);
            preparedStatement.setInt(3, fStream.length());
            preparedStatement.setString(4, refNumber);
            preparedStatement.executeUpdate();
            mydb.execute("commit");
        } catch (Exception ex) {
        }
    }

    private void InsertTLog(String LOGID, String NAMA_USER, String PASSWD, String NAMA_SERVICE, String KETERANGAN, String XML_REQUEST_NEW) {
        String query = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Db mydb = null;
        Encrypt encrypt = null;

        try {
            mydb = new Db();
            if (XML_REQUEST_NEW == null) {
                query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN) VALUES "
                        + "(?,?,?,?,SYSDATE,?)";
                preparedStatement = mydb.preparedstmt(query);
                preparedStatement.setString(1, LOGID);
                preparedStatement.setString(2, NAMA_USER);
                preparedStatement.setString(3, encrypt.encrypt(PASSWD));
                preparedStatement.setString(4, NAMA_SERVICE);
                preparedStatement.setString(5, KETERANGAN);
                preparedStatement.executeUpdate();
                mydb.execute("commit");
            } else {
                query = "INSERT INTO T_LOG_SERVICES (LOGID,NAMA_USER,PASSWD,NAMA_SERVICE,TGL_INVOKE,KETERANGAN,XML_REQUEST_NEW) VALUES "
                        + "(?,?,?,?,SYSDATE,?,?)";
                preparedStatement = mydb.preparedstmt(query);
                preparedStatement.setString(1, LOGID);
                preparedStatement.setString(2, NAMA_USER);
                preparedStatement.setString(3, encrypt.encrypt(PASSWD));
                preparedStatement.setString(4, NAMA_SERVICE);
                preparedStatement.setString(5, KETERANGAN);
                preparedStatement.setString(6, XML_REQUEST_NEW);
                preparedStatement.executeUpdate();
                mydb.execute("commit");
            }
        } catch (Exception ex) {
        }
    }

    private void setLocalFolder(String localFolder) {
        this.localFolder = localFolder;
    }
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CoarriCodeco_Container")
    public String CoarriCodeco_Container(@WebParam(name = "fStream") String fStream, @WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        String result = null;
        ExcuteProses exc = null;
        try {
            tpsUpload = new TPSUpload();
            //Invoke Web Service BC
            result = tpsUpload.upload_TPS("COCOCONT", fStream, Username, Password);
        } catch (Exception ex) {
            result = "Pengiriman Data Container gagal.";
            exc = new ExcuteProses();
            ex.printStackTrace();
            exc.ExcuteError(ex.getMessage(), "COCOCONT", Username);
        } finally {
            tpsUpload = null;
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CoarriCodeco_Kemasan")
    public String CoarriCodeco_Kemasan(@WebParam(name = "fStream") String fStream, @WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        String result = null;
        ExcuteProses exc = null;
        try {
            tpsUpload = new TPSUpload();
            //Invoke Web Service BC
            result = tpsUpload.upload_TPS("COCOKMS", fStream, Username, Password);
        } catch (Exception ex) {
            result = "Pengiriman Data Kemasan gagal.";
            exc = new ExcuteProses();
            ex.printStackTrace();
            exc.ExcuteError(ex.getMessage(), "COCOKMS", Username);
        } finally {
            tpsUpload = null;
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetImporPermit")
    public String GetImporPermit(@WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password, @WebParam(name = "Kd_Gudang") String Kd_Gudang) {

        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ImporPermit", Kd_Gudang, Username, Password);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetImporPermit");

            result = "Download Data Impor Permit gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetImpor_Sppb")
    public String GetImpor_Sppb(@WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password, @WebParam(name = "No_Sppb") String No_Sppb, @WebParam(name = "Tgl_Sppb") String Tgl_Sppb, @WebParam(name = "NPWP_Imp") String NPWP_Imp) {

        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_Requset_sppb(Username, Password, No_Sppb, Tgl_Sppb, NPWP_Imp);

        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetImpor_Sppb");

            result = "Download Data Impor Permit gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetBC23Permit")
    public String GetBC23Permit(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_Gudang") String Kd_Gudang) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("BC23Permit", Kd_Gudang, UserName, Password);
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetBC23Permit");

            e.printStackTrace();
            result = "Download Data BC 2.3 Permit gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponPLP")
    public String GetResponPLP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLP", Kd_asp, UserName, Password);
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponPLP");

            e.printStackTrace();
            result = "Download Data Respon PLP gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetImporPermit_FASP")
    public String GetImporPermit_FASP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_ASP") String Kd_ASP) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ImporPermitFASP", Kd_ASP, UserName, Password);
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetImporPermit_FASP");

            e.printStackTrace();
            result = "Download Data Impor Permit gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetBC23Permit_FASP")
    public String GetBC23Permit_FASP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_ASP") String Kd_ASP) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("BC23PermitFASP", Kd_ASP, UserName, Password);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetBC23Permit_FASP");

            result = "Download Data BC 23 Permit gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UploadMohonPLP")
    public String UploadMohonPLP(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        ParsingXMLPLP pars = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;
        PreparedStatement preparedStatement = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            tpsUpload = new TPSUpload();
            pars = new ParsingXMLPLP();

            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadMohonPLP." + tgl.UNIXNUMBER() + ".XML";
            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //invoke ke BC
                result = tpsUpload.upload_TPS("REQPLP_AJU", fStream, Username, Password);
                try {
                    if (result.startsWith("Proses Berhasil")) {
                        //Process parsing & Insert into database
                        resultdb = pars.parseDocument(fStream);

                        if (resultdb.contains("Insert Success")) {
                            refNumber = resultdb.substring(0, resultdb.indexOf("_"));

                            UpdateMIBILLING(Username, file, fStream, refNumber);

                        } else {
                            String error_code = "UploadMohonPLP";
                            InsertTPSLOG(error_code, file, resultdb);
                        }
                    }

                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "UploadMohonPLP", keterangan, file, fStream);

                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadMohonPLP");

                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();

            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "UploadMohonPLP", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";
        } finally {
            tpsUpload = null;
            pars = null;
            mydb = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation //16-10-2014
     */
    @WebMethod(operationName = "UploadUbahStatus")
    public String UploadUbahStatus(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        ParsingXMLCFS pars = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        ResCFS res = null;

        try {
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            pars = new ParsingXMLCFS();
            res = new ResCFS();
            if (Username.equalsIgnoreCase("cds") && Password.equalsIgnoreCase("cdspassword")) {
                if (fStream.contains("<LOADUBAHSTATUS>")) {
                    setLocalFolder(PROPERTIES.getProperty("tpsCFS.inboxfolder"));
                    file = getLocalFolder() + File.separator + Username + ".UploadUbahStatus." + tgl.UNIXNUMBER() + ".XML";
                    if (cf.execute(file)) {
                        //Create File
                        cf.content(file, fStream);
                        try {
                            resultdb = pars.parseDocument(fStream);
                            if (resultdb.contains("Proses Berhasil")) {
                                result = res.KdRes("003");
                            } else {
                                result = resultdb;
                            }

                            String keterangan = result + "/" + resultdb;
                            exc.ExcuteLog(Username, Password, "UploadUbahStatus", keterangan, file, fStream);

                        } catch (Exception e) {

                            exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadUbahStatus");

                        }
                    } else {
                        //kdRes jika fstream yg dikirm kosong/null/""
                        result = res.KdRes("102");
                    }
                }
            } else {
                result = res.KdRes("001");
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "UploadUbahStatus", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";
        } finally {
            pars = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetUbahStatus")
    public String GetUbahStatus(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        String query = null;
        Db mydb = null;
        GenerateXMLCFS cfs = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            cfs = new GenerateXMLCFS();
            mydb = new Db();
            query = "SELECT REF_NUMBER FROM T_REQ_UBAH_STATUS WHERE FL_SEND = ? AND KODE_TPS_ASAL = ?";
            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, "0");
            preparedStatement.setString(2, Kd_asp);
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                result = cfs.execute(PROPERTIES.getProperty("tpsCFS.outboxfolder"), Kd_asp);
            } else {
                result = "Data Belum Ada";
            }

        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetUbahStatus");

            e.printStackTrace();
            result = "Download Data Respon PLP Tujuan gagal.";
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponPLPTujuan")
    public String GetResponPLPTujuan(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        TPSDownload tpsDownload = null;
        try {
            tpsDownload = new TPSDownload();
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLPTujuan", Kd_asp, UserName, Password);
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponPLPTujuan");

            e.printStackTrace();
            result = "Download Data Respon PLP Tujuan gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

//    Start Method baru
    @WebMethod(operationName = "GetResponPLP_Tujuan")
    public String GetResponPLP_Tujuan(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLP_Tujuan", Kd_asp, UserName, Password);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponPLP_Tujuan");

            result = "Download Data Respon PLP Tujuan gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }
//    End Method baru

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UploadBatalPLP")
    public String UploadBatalPLP(@WebParam(name = "fStream") String fStream, @WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        ParsingXMLPLP_BATAL parsBatal = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            tpsUpload = new TPSUpload();
            parsBatal = new ParsingXMLPLP_BATAL();
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadBatalPLP." + tgl.UNIXNUMBER() + ".XML";
            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Invoke Web Service BC
                result = tpsUpload.upload_TPS("REQPLP_BATAL", fStream, Username, Password);
                try {
                    if (result.startsWith("Proses Berhasil")) {
                        //Process Parsing & Insert into database
                        resultdb = parsBatal.parseDocument(fStream);
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        //Update To Billing PLP
                        UpdateMIBILLING(Username, file, fStream, refNumber);
                    } else {
                        String error_code = "UploadBatalPLP";
                        InsertTPSLOG(error_code, file, resultdb);
                    }

                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "UploadBatalPLP", keterangan, file, fStream);

                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadBatalPLP");

                }
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "UploadBatalPLP", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Pembatalan PLP gagal.";
        } finally {
            tpsUpload = null;
            parsBatal = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponBatalPLP")
    public String GetResponBatalPLP(@WebParam(name = "UserName") String UserName, @WebParam(name = "Password") String Password, @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponBatalPLP", Kd_asp, UserName, Password);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponBatalPLP");

            result = "Download Data Respon PLP Batal gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponBatalPLPTujuan")
    public String GetResponBatalPLPTujuan(@WebParam(name = "UserName") String UserName, @WebParam(name = "Password") String Password, @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        TPSDownload tpsDownload = null;

        try {
            tpsDownload = new TPSDownload();

            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponBatalPLPTujuan", Kd_asp, UserName, Password);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponBatalPLPTujuan");

            result = "Download Data Respon PLP Batal Tujuan gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    @WebMethod(operationName = "CoCoCarTer")
    public String CoCoCarTer(@WebParam(name = "fStream") String fStream, @WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password) {
        String result = null;
        TPSUpload tpsUpload = null;
        ExcuteProses exc = null;
        try {
            //Deklarasi intansiasi Class
            tpsUpload = new TPSUpload();
            //Invoke Web Service BC
            result = tpsUpload.upload_TPS("COCOCAR", fStream, Username, Password);
        } catch (Exception ex) {
            exc = new ExcuteProses();
            ex.printStackTrace();
            exc.ExcuteError(ex.getMessage(), "COCOCAR", Username);
            result = "Pengiriman Data Container gagal.";
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetSPJM")
    public String GetSPJM(@WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password, @WebParam(name = "Kd_Tps") String Kd_Tps) {
        String result = null;
        TPSDownload tpsDownload = null;
        try {
            tpsDownload = new TPSDownload();
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("GetSPJM", Username, Password, Kd_Tps);
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetSPJM");

            e.printStackTrace();
            result = "Download Data GetSPJM gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetSPJM_onDemand")
    public String GetSPJM_onDemand(@WebParam(name = "Username") String Username, @WebParam(name = "Password") String Password, @WebParam(name = "No_PIB") String No_PIB, @WebParam(name = "Tgl_PIB") String Tgl_PIB) {
        String result = null;
        TPSDownload tpsDownload = null;
        try {
            tpsDownload = new TPSDownload();
            //Invoke Web Service BC
            result = tpsDownload.download_GetSPJM_onDemand(Username, Password, No_PIB, Tgl_PIB);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetSPJM_onDemand");

            result = "Download Data GetSPJM gagal.";
        } finally {
            tpsDownload = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UploadMohonPLP_IPC")
    public String UploadMohonPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        ParsingXMLPLP pars = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;
        PreparedStatement preparedStatement = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            pars = new ParsingXMLPLP();
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadMohonPLPIPC." + tgl.UNIXNUMBER() + ".XML";

            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Process parsing & Insert into database
                resultdb = pars.parseDocument(fStream);
                try {
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        result = "Proses Berhasil";

                        //Update To Billing PLP
                        UpdateMIBILLING(Username, file, fStream, refNumber);

                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                    } else {
                        String error_code = "UploadMohonPLP";   
                        result = InsertTPSLOG(error_code, file, resultdb);
                    }
                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "UploadMohonPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "UploadMohonPLP_IPC", keterangan, null);
            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";

        } finally {
            pars = null;
            mydb = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UploadBatalPLP_IPC")
    public String UploadBatalPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        ParsingXMLPLP_BATAL pars = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;
        PreparedStatement preparedStatement = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            pars = new ParsingXMLPLP_BATAL();
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadBatalPLPIPC." + tgl.UNIXNUMBER() + ".XML";

            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Process parsing & Insert into database
                resultdb = pars.parseDocument(fStream);
                try {
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        result = "Proses Berhasil";
                        //Update To Billing PLP
                        UpdateMIBILLING(Username, file, fStream, refNumber);

                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                    } else {
                        String error_code = "UploadBatalPLPIPC";
                        result = InsertTPSLOG(error_code, file, resultdb);
                    }
                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "UploadBatalPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "UploadBatalPLP_IPC", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Permohonan Batal PLP gagal.";
        } finally {
            pars = null;
            mydb = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponPLP_IPC")
    public String GetResponPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {
        ParsingXMLResponPLP_Asal pars = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            pars = new ParsingXMLResponPLP_Asal();
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".ResponPLPIPC." + tgl.UNIXNUMBER() + ".XML";

            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Process parsing & Insert into database
                resultdb = pars.parseDocumentPLPAsal(fStream);
                try {
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        result = "Proses Berhasil";
                        //Update To Billing PLP
                        UpdateMIBILLING(Username, file, fStream, refNumber);

                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                    } else {
                        String error_code = "GetResponPLP_IPC";
                        result = InsertTPSLOG(error_code, file, resultdb);
                    }
                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "GetResponPLP_IPC", keterangan, file, fStream);

                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "GetResponPLP_IPC", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Respon PLP gagal.";
        } finally {
            pars = null;
            mydb = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponBatalPLP_IPC")
    public String GetResponBatalPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        ParsingXMLResponPLPBatal_Asal pars = null;
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String resultdb = null;
        String query = null;
        String file = null;
        CreateFile cf = null;
        Tanggalan tgl = null;
        String refNumber = null;
        PreparedStatement preparedStatement = null;

        try {
            mydb = new Db();
            cf = new CreateFile();
            tgl = new Tanggalan();
            encrypt = new Encrypt();
            pars = new ParsingXMLResponPLPBatal_Asal();
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".ResponBatalPLPIPC." + tgl.UNIXNUMBER() + ".XML";

            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Process parsing & Insert into database
                resultdb = pars.parseDocumentPLPBatalAsal(fStream);
                try {
                    if (resultdb.contains("Insert Success")) {
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        result = "Proses Berhasil";
                        //Update To Billing PLP
                        UpdateMIBILLING(Username, file, fStream, refNumber);
                    
                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                    } else {
                        String error_code = "";
                        result = InsertTPSLOG(error_code, file, resultdb);

                    }
                    String keterangan = result + "/" + resultdb;
                    exc.ExcuteLog(Username, Password, "GetResponBatalPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {

            String keterangan = "Error : " + ex.getMessage();
            InsertTLog("LOG_SEQ.NEXTVAL", Username, encrypt.encrypt(Password), "GetResponBatalPLP_IPC", keterangan, null);

            ex.printStackTrace();
            result = "Upload Data Respon Batal PLP gagal.";
        } finally {
            pars = null;
            mydb = null;
            encrypt = null;

            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponPLP_DWPORTAL")
    public String GetResponPLP_DWPORTAL(@WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws Exception {
        Db mydb = null;
        Encrypt encrypt = null;
        String result = null;
        String query = null;
        String RESPONID = null;
        String REF_NUMBER = null;
        GenerateXMLDW dw = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            dw = new GenerateXMLDW();
            mydb = new Db();
//            if (Username.equalsIgnoreCase("PENDAFTARAN")) {
//                query = "INSERT INTO TEMP_T_PLP(ID,USERNAME,PASSWORD,XML_RESPONSE,STATUS,WK_IN) "
//                        + "VALUES (T_PLP_SEQ.NEXTVAL,'" + Username + "','" + Username + "','" + Password + "','Q',SYSDATE)";
//
//                System.out.println("" + query);
//                mydb.execute(query);
//                mydb.execute("commit");
//                result = "Proses Berhasil";
//            } else if (Username.equalsIgnoreCase("PEMBAYARAN")) {
//                query = "SELECT XML_RESPONSE, ID FROM TEMP_T_PLP WHERE STATUS = 'Q' AND USERNAME = 'PENDAFTARAN'";
//                mydb.execute(query);
//                if (mydb.next()) {
//                    RESPONID = mydb.getString("ID");
//                    result = mydb.getString("XML_RESPONSE");
//                } else {
//                    result = "Belum Ada Data";
//                }
//                if (result != null) {
//                    query = "UPDATE TEMP_T_PLP SET STATUS = 'S' WHERE ID = " + RESPONID;
//                    mydb.execute(query);
//                    mydb.execute("commit");
//                }
//            } else {
            query = "SELECT  B.REF_NUMBER, B.RESPONID  "
                    + "FROM T_REQUEST_PLP A "
                    + "INNER JOIN T_RESPON_PLP B ON A.REF_NUMBER = B.REF_NUMBER "
                    + "WHERE A.KODE_TPS_ASAL <> 'PLDC' AND A.KODE_TPS_ASAL <> 'KOJA' AND B.FL_SEND = '0'";
            preparedStatement = mydb.preparedstmt(query);
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                RESPONID = mydb.getString("RESPONID");
                System.out.println(RESPONID);
                REF_NUMBER = mydb.getString("REF_NUMBER");
                System.out.println("" + REF_NUMBER);
                result = dw.excute(REF_NUMBER, RESPONID);
            } else {
                result = "Data Tidak Ditemukan";
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponPLP_DWPORTAL");

            e.printStackTrace();
        } finally {
            if (mydb != null) {
                mydb.close();
            }
        }
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetResponPLP_Tujuan_Tes")
    public String GetResponPLP_Tujuan_Tes(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {
        String result = null;
        GenerateResponPLP_Tujuan_Tes tes = new GenerateResponPLP_Tujuan_Tes();
        String query = null;
        Db mydb = null;
        try {
            mydb = new Db();
            //Invoke Web Service BC
            result = tes.excute(PROPERTIES.getProperty("tpsTES.inboxfolder"), Kd_asp);
            String keterangan = result;
            String xml_request_new = "Panjang File " + result.length();
            InsertTLog("LOG_SEQ.NEXTVAL", UserName, Password, "GetResponPLP_Tujuan_Tes", keterangan, xml_request_new);
        } catch (Exception e) {
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "execute_class_tps", "GetResponPLP_Tujuan_Tes");
            result = "Download Data Respon PLP Tujuan gagal.";
        } finally {
            return result;
        }
    }
}
