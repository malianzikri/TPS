package com.edii.tps;

import File.TXT.CreateFile;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.Properties;
import com.edii.tools.Encrypt;
import com.edii.tools.GenerateXMLCFS;
import com.edii.tools.GenerateDW;
import com.edii.parsingfile.ParsingUploadMohonPLP;
import com.edii.parsingfile.ParsingUploadBatalPLP;
import com.edii.parsingfile.ParsingGetResponPLP;
import com.edii.tools.ResCFS;
import com.edii.tools.Tanggalan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Loggers.Loggers;
import com.edii.operation.db.logDatabase;
import com.edii.parsingfile.ParsingCFS;
import com.edii.parsingfile.ParsingGetResponBatalPLP;
import com.edii.tps.service.TPSDownload;
import com.edii.tps.service.TPSUpload;
import java.security.NoSuchAlgorithmException;

@WebService()
public class tps {

    String localFolder;
    private logDatabase logDb = null;
    private Loggers log = null;
    private CreateFile cf = null;
    Tanggalan tgl;

    private String getLocalFolder() {
        return localFolder;
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
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "CoarriCodeco_Container")
    public String CoarriCodeco_Container(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        String result = "";
        String message = "";
        String messageErr = "";
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsUpload = new TPSUpload();
            message += log.LogSuccess(tpsUpload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsUpload.upload_TPS("COCOCONT", fStream, Username, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            result = "Pengiriman Data Container gagal.";
            messageErr += log.LogError(result) + "\r\n";
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "CoarriCodeco_Container");
            messageErr += log.LogError("ExcuteError: " + hasil) + "\r\n";
            e.printStackTrace();
        } finally {
            tpsUpload = null;
            String filename = "CoarriCodeco_Container" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "CoarriCodeco_Kemasan")
    public String CoarriCodeco_Kemasan(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        TPSUpload tpsUpload = null;
        String result = "";
        String message = "";
        String messageErr = "";
        tgl = new Tanggalan();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsUpload = new TPSUpload();
            message += log.LogSuccess(tpsUpload.toString() + "\r\n");
            result = tpsUpload.upload_TPS("COCOKMS", fStream, Username, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            result = "Pengiriman Data Kemasan gagal";
            messageErr += log.LogError(result) + "\r\n";
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "CoarriCodeco_Kemasan");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            e.printStackTrace();
        } finally {
            tpsUpload = null;
            String filename = "CoarriCodeco_Kemasan" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param Username
     * @param Kd_Gudang
     * @param Password
     * @return
     */
    @WebMethod(operationName = "GetImporPermit")
    public String GetImporPermit(@WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_Gudang") String Kd_Gudang) {

        TPSDownload tpsDownload = null;
        String result = "";
        String message = "";
        String messageErr = "";
        tgl = new Tanggalan();
        log = new Loggers();
        cf = new CreateFile();
        logDb = new logDatabase();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ImporPermit", Kd_Gudang, Username, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetImporPermit");
            messageErr += log.LogError("ExcuterError:" + hasil) + "\r\n";
            result = "Download Data Impor Permit gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetImporPermit" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param Username
     * @param NPWP_Imp
     * @param Password
     * @param Tgl_Sppb
     * @param No_Sppb
     * @return
     */
    @WebMethod(operationName = "GetImpor_Sppb")
    public String GetImpor_Sppb(@WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "No_Sppb") String No_Sppb,
            @WebParam(name = "Tgl_Sppb") String Tgl_Sppb,
            @WebParam(name = "NPWP_Imp") String NPWP_Imp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_Requset_sppb(Username, Password, No_Sppb, Tgl_Sppb, NPWP_Imp);
            message += log.LogSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetImpor_Sppb");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            result = "Download Data Impor Permit gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetImpor_Sppb" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_Gudang
     * @return
     */
    @WebMethod(operationName = "GetBC23Permit")
    public String GetBC23Permit(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_Gudang") String Kd_Gudang) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;
        tgl = new Tanggalan();
        log = new Loggers();
        cf = new CreateFile();
        logDb = new logDatabase();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("BC23Permit", Kd_Gudang, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetBC23Permit");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            e.printStackTrace();
            result = "Download Data BC 2.3 Permit gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetBC23Permit" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Kd_asp
     * @param Password
     * @return
     */
    @WebMethod(operationName = "GetResponPLP")
    public String GetResponPLP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLP", Kd_asp, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetResponPLP");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            e.printStackTrace();
            result = "Download Data Respon PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetResponPLP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_ASP
     * @return
     */
    @WebMethod(operationName = "GetImporPermit_FASP")
    public String GetImporPermit_FASP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_ASP") String Kd_ASP) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ImporPermitFASP", Kd_ASP, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetImporPermit_FASP");
            messageErr += log.LogError("ExcuteError" + hasil) + "\r\n";
            e.printStackTrace();
            result = "Download Data Impor Permit gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetImporPermit_FASP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_ASP
     * @return
     */
    @WebMethod(operationName = "GetBC23Permit_FASP")
    public String GetBC23Permit_FASP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_ASP") String Kd_ASP) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("BC23PermitFASP", Kd_ASP, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetBC23Permit_FASP");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            result = "Download Data BC 23 Permit gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetBC23Permit_FASP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "UploadMohonPLP")
    public String UploadMohonPLP(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        TPSUpload tpsUpload = null;
        ParsingUploadMohonPLP pars = null;
        Encrypt encrypt = null;
        String result = "";
        String resultdb = "";
        String file = "";
        String refNumber = "";
        String message = "";
        String messageErr = "";


        tgl = new Tanggalan();
        log = new Loggers();
        logDb = new logDatabase();
        cf = new CreateFile();
        try {
            encrypt = new Encrypt();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            tpsUpload = new TPSUpload();
            pars = new ParsingUploadMohonPLP();
            message += log.LogSuccess(tpsUpload.toString()) + "\r\n";
            message += log.LogSuccess(pars.toString()) + "\r\n";

            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadMohonPLP." + tgl.UNIXNUMBER() + ".XML";
            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //invoke ke BC
                result = tpsUpload.upload_TPS("REQPLP_AJU", fStream, Username, Password);
                message += log.LogSuccess("Upload_TPS:" + result) + "\r\n";
                try {
                    if (result.startsWith("Proses Berhasil")) {
                        //Process parsing & Insert into database
                        resultdb = pars.parseDocument(fStream);
                        message += log.LogSuccess(resultdb) + "\r\n";
                        if (resultdb.contains("Insert Success")) {
                            refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                            logDb.update_mblling(Username, file, fStream, refNumber);
                        } else {
                            String error_code = "UploadMohonPLP";
                            messageErr += log.LogError(error_code) + "\r\n";
                            logDb.excuteLogTpsLog(error_code, file, resultdb);
                        }
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "UploadMohonPLP", keterangan, file, fStream);
                } catch (Exception e) {
                    messageErr += log.LogError(e.getMessage()) + "\r\n";
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadMohonPLP");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadMohonPLP");
            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsUpload = null;
            pars = null;
            encrypt = null;
            String filename = "UploadMohonPLP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation //16-10-2014
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "UploadUbahStatus")
    public String UploadUbahStatus(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        ParsingCFS pars = null;
        Encrypt encrypt = null;
        ResCFS res = null;
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        logDb = new logDatabase();
        tgl = new Tanggalan();
        log = new Loggers();
        cf = new CreateFile();
        try {
            encrypt = new Encrypt();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            pars = new ParsingCFS();
            message += log.LogSuccess(pars.toString()) + "\r\n";
            res = new ResCFS();
            message += log.LogSuccess(res.toString()) + "\r\n";
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
                                messageErr += log.LogError(resultdb) + "\r\n";
                            }
                            String keterangan = result + "/" + resultdb;
                            message += log.LogSuccess(keterangan) + "\r\n";
                            logDb.excuteLogSucces(Username, Password, "UploadUbahStatus", keterangan, file, fStream);
                        } catch (Exception e) {
                            messageErr += log.LogError(e.getMessage()) + "\r\n";
                            logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadUbahStatus");
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
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadUbahStatus");
            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            pars = null;
            encrypt = null;
            String filename = "UploadUbahStatus" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_asp
     * @return
     */
    @WebMethod(operationName = "GetUbahStatus")
    public String GetUbahStatus(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String query = "";
        String message = "";
        String messageErr = "";
        GenerateXMLCFS cfs = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        Tanggalan tgl = new Tanggalan();
        try {
            cfs = new GenerateXMLCFS();
            message += log.LogSuccess(cfs.toString()) + "\r\n";
            query = "SELECT REF_NUMBER FROM T_REQ_UBAH_STATUS WHERE FL_SEND = ? AND KODE_TPS_ASAL = ?";
//            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, "0");
            preparedStatement.setString(2, Kd_asp);
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                result = cfs.execute(PROPERTIES.getProperty("tpsCFS.outboxfolder"), Kd_asp);
            } else {
                result = "Data Belum Ada";
            }
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            messageErr += log.LogError(e.getMessage()) + "\r\n";
            logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetUbahStatus");
            result = "Download Data Respon PLP Tujuan gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            String filename = "GetUbahStatus" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_asp
     * @return
     */
    @WebMethod(operationName = "GetResponPLPTujuan")
    public String GetResponPLPTujuan(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLPTujuan", Kd_asp, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetResponPLPTujuan");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            e.printStackTrace();
            result = "Download Data Respon PLP Tujuan gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetResponPLPTujuan" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

//    Start Method baru
    @WebMethod(operationName = "GetResponPLP_Tujuan")
    public String GetResponPLP_Tujuan(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();

        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponPLP_Tujuan", Kd_asp, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetResponPLP_Tujuan");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            result = "Download Data Respon PLP Tujuan gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetResponPLP_Tujuan" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }
//    End Method baru

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "UploadBatalPLP")
    public String UploadBatalPLP(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        TPSUpload tpsUpload = null;
        ParsingUploadBatalPLP parsBatal = null;
        Encrypt encrypt = null;
        Tanggalan tgl = null;
        String refNumber = "";
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            encrypt = new Encrypt();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            tpsUpload = new TPSUpload();
            parsBatal = new ParsingUploadBatalPLP();
            message += log.LogSuccess(tpsUpload.toString()) + "\r\n";
            message += log.LogSuccess(parsBatal.toString()) + "\r\n";
            setLocalFolder(PROPERTIES.getProperty("tps" + Username.toUpperCase() + ".outboxfolder"));
            file = getLocalFolder() + File.separator + Username + ".UploadBatalPLP." + tgl.UNIXNUMBER() + ".XML";
            if (cf.execute(file)) {
                //Create File
                cf.content(file, fStream);
                //Invoke Web Service BC
                result = tpsUpload.upload_TPS("REQPLP_BATAL", fStream, Username, Password);
                message += log.LogSuccess(result) + "\r\n";
                try {
                    if (result.startsWith("Proses Berhasil")) {
                        //Process Parsing & Insert into database
                        resultdb = parsBatal.parseDocument(fStream);
                        refNumber = resultdb.substring(0, resultdb.indexOf("_"));
                        //Update To Billing PLP
                        logDb.update_mblling(Username, file, fStream, refNumber);
                    } else {
                        String error_code = "UploadBatalPLP";
                        messageErr += log.LogError(error_code) + "\r\n";
                        logDb.excuteLogTpsLog(error_code, file, resultdb);
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "UploadBatalPLP", keterangan, file, fStream);
                } catch (Exception e) {
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadBatalPLP");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadBatalPLP");
            ex.printStackTrace();
            result = "Upload Data Pembatalan PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsUpload = null;
            parsBatal = null;
            String filename = "UploadBatalPLP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_asp
     * @return
     */
    @WebMethod(operationName = "GetResponBatalPLP")
    public String GetResponBatalPLP(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();

        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponBatalPLP", Kd_asp, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetResponBatalPLP");
            messageErr += log.LogError("ExcuteError" + hasil) + "\r\n";
            result = "Download Data Respon PLP Batal gagal.";
            messageErr += log.LogError(result);
        } finally {
            tpsDownload = null;
            String filename = "GetResponBatalPLP" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param UserName
     * @param Password
     * @param Kd_asp
     * @return
     */
    @WebMethod(operationName = "GetResponBatalPLPTujuan")
    public String GetResponBatalPLPTujuan(@WebParam(name = "UserName") String UserName,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_asp") String Kd_asp) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("ResponBatalPLPTujuan", Kd_asp, UserName, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetResponBatalPLPTujuan");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            result = "Download Data Respon PLP Batal Tujuan gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetResponBatalPLPTujuan" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    @WebMethod(operationName = "CoCoCarTer")
    public String CoCoCarTer(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSUpload tpsUpload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            //Deklarasi intansiasi Class
            tpsUpload = new TPSUpload();
            message += log.LogSuccess(tpsUpload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsUpload.upload_TPS("COCOCAR", fStream, Username, Password);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception ex) {
            ex.printStackTrace();
            boolean hasil = logDb.excuteLogError(ex.getMessage(), "COCOCAR", Username);
            messageErr += log.LogError("ExcuteError" + hasil) + "\r\n";
            result = "Pengiriman Data Container gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            String filename = "CoCoCarTer" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param Username
     * @param Password
     * @param Kd_Tps
     * @return
     */
    @WebMethod(operationName = "GetSPJM")
    public String GetSPJM(@WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "Kd_Tps") String Kd_Tps) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_TPS("GetSPJM", Username, Password, Kd_Tps);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetSPJM");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            e.printStackTrace();
            result = "Download Data GetSPJM gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetSPJM" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param Username
     * @param Password
     * @param No_PIB
     * @param Tgl_PIB
     * @return
     */
    @WebMethod(operationName = "GetSPJM_onDemand")
    public String GetSPJM_onDemand(@WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password,
            @WebParam(name = "No_PIB") String No_PIB,
            @WebParam(name = "Tgl_PIB") String Tgl_PIB) {

        String result = "";
        String message = "";
        String messageErr = "";
        TPSDownload tpsDownload = null;

        Tanggalan tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        cf = new CreateFile();
        try {
            tpsDownload = new TPSDownload();
            message += log.LogSuccess(tpsDownload.toString()) + "\r\n";
            //Invoke Web Service BC
            result = tpsDownload.download_GetSPJM_onDemand(Username, Password, No_PIB, Tgl_PIB);
            message += log.LogSuccess(result) + "\r\n";
        } catch (Exception e) {
            e.printStackTrace();
            boolean hasil = logDb.excuteLogError(e.getMessage(), "execute_class_tps", "GetSPJM_onDemand");
            messageErr += log.LogError("ExcuteError:" + hasil) + "\r\n";
            result = "Download Data GetSPJM gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            tpsDownload = null;
            String filename = "GetSPJM_onDemand" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "UploadMohonPLP_IPC")
    public String UploadMohonPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        ParsingUploadMohonPLP pars = null;
        Encrypt encrypt = null;
        Tanggalan tgl = null;
        String refNumber = "";
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        cf = new CreateFile();
        tgl = new Tanggalan();
        logDb = new logDatabase();
        log = new Loggers();
        try {
            encrypt = new Encrypt();
            pars = new ParsingUploadMohonPLP();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            message += log.LogSuccess(pars.toString()) + "\r\n";
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
                        message += log.LogSuccess(result) + "\r\n";
                        //Update To Billing PLP
                        logDb.update_mblling(Username, file, fStream, refNumber);
                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                        message += log.LogSuccess(result) + "\r\n";
                    } else {
                        String error_code = "UploadMohonPLP";
                        messageErr += log.LogError(error_code) + "\r\n";
                        result = logDb.excuteLogTpsLog(error_code, file, resultdb);
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "UploadMohonPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
            ex.printStackTrace();
            result = "Upload Data Permohonan PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            pars = null;
            encrypt = null;
            String filename = "UploadMohonPLP_IPC" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "UploadBatalPLP_IPC")
    public String UploadBatalPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        ParsingUploadBatalPLP pars = null;
        Encrypt encrypt = null;
        Tanggalan tgl = null;
        String refNumber = "";
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        logDb = new logDatabase();
        cf = new CreateFile();
        log = new Loggers();
        tgl = new Tanggalan();
        try {
            encrypt = new Encrypt();
            pars = new ParsingUploadBatalPLP();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";

            message += log.LogSuccess(pars.toString()) + "\r\n";
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
                        logDb.update_mblling(Username, file, fStream, refNumber);
                        message += log.LogSuccess(result) + "\r\n";

                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                        message += log.LogSuccess(result) + "\r\n";
                    } else {
                        String error_code = "UploadBatalPLPIPC";
                        result = logDb.excuteLogTpsLog(error_code, file, resultdb);
                        messageErr += log.LogError(error_code + result) + "\r\n";
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "UploadBatalPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
            ex.printStackTrace();
            result = "Upload Data Permohonan Batal PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            pars = null;
            encrypt = null;
            String filename = "UploadMohonPLP_IPC" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "GetResponPLP_IPC")
    public String GetResponPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {
        ParsingGetResponPLP pars = null;
        Encrypt encrypt = null;
        String refNumber = "";
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        logDb = new logDatabase();
        cf = new CreateFile();
        tgl = new Tanggalan();
        log = new Loggers();
        try {
            encrypt = new Encrypt();
            pars = new ParsingGetResponPLP();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            message += log.LogSuccess(pars.toString()) + "\r\n";
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
                        logDb.update_mblling(Username, file, fStream, refNumber);
                        message += log.LogSuccess(result) + "\r\n";
                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                        message += log.LogSuccess(result) + "\r\n";
                    } else {
                        String error_code = "GetResponPLP_IPC";
                        result = logDb.excuteLogTpsLog(error_code, file, resultdb);
                        messageErr += log.LogError(error_code + result) + "\r\n";
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "GetResponPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
            ex.printStackTrace();
            result = "Upload Data Respon PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            pars = null;
            encrypt = null;
            String filename = "UploadMohonPLP_IPC" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }

    /**
     * Web service operation
     *
     * @param fStream
     * @param Username
     * @param Password
     * @return
     */
    @WebMethod(operationName = "GetResponBatalPLP_IPC")
    public String GetResponBatalPLP_IPC(@WebParam(name = "fStream") String fStream,
            @WebParam(name = "Username") String Username,
            @WebParam(name = "Password") String Password) throws NoSuchAlgorithmException {

        ParsingGetResponBatalPLP pars = null;
        Encrypt encrypt = null;
        Tanggalan tgl = null;
        String refNumber = "";
        String result = "";
        String resultdb = "";
        String file = "";
        String message = "";
        String messageErr = "";

        logDb = new logDatabase();
        cf = new CreateFile();
        tgl = new Tanggalan();
        try {
            encrypt = new Encrypt();
            message += log.LogSuccess(encrypt.toString()) + "\r\n";
            pars = new ParsingGetResponBatalPLP();
            message += log.LogSuccess(pars.toString()) + "\r\n";
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
                        logDb.update_mblling(Username, file, fStream, refNumber);
                        message += log.LogSuccess(result) + "\r\n";
                    } else if (resultdb.contains("Ref")) {
                        refNumber = resultdb.substring(resultdb.indexOf("Ref"), resultdb.indexOf("_"));
                        result = refNumber;
                        message += log.LogSuccess(result) + "\r\n";
                    } else {
                        String error_code = "";
                        result = logDb.excuteLogTpsLog(error_code, file, resultdb);
                        messageErr += log.LogError(error_code + result) + "\r\n";
                    }
                    String keterangan = result + "/" + resultdb;
                    logDb.excuteLogSucces(Username, Password, "GetResponBatalPLP_IPC", keterangan, file, fStream);
                } catch (Exception e) {
                    logDb.excuteLogError(e.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
                }
            }
        } catch (Exception ex) {
            String keterangan = "Error : " + ex.getMessage();
            messageErr += log.LogError(keterangan) + "\r\n";
            logDb.excuteLogError(ex.getMessage(), "execute_class_tps", "UploadMohonPLP_IPC");
            ex.printStackTrace();
            result = "Upload Data Respon Batal PLP gagal.";
            messageErr += log.LogError(result) + "\r\n";
        } finally {
            pars = null;
            encrypt = null;
            String filename = "UploadMohonPLP_IPC" + tgl.UNIXNUMBER() + ".txt";
            if (!messageErr.equalsIgnoreCase("")) {
                cf.newFile("direktori error", filename, messageErr);
            } else {
                cf.newFile("direktori success", filename, message);
            }
        }
        return result;
    }
}