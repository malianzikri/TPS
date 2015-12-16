/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingXMLPLP_BATAL {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocument(String fStream) throws Exception {

        Db mydb = null;
        Document doc = null;
        Element List = null;;
        Element ListRes = null;
        Element ListHdr = null;
        Element ListDtl = null;
        Element ListDtlKMS = null;
        Element ListDtlCONT = null;
        Iterator iter = null;
        Iterator iterRes = null;
        Iterator iterHdr = null;
        Iterator iterDtl = null;
        Iterator iterDtlKMS = null;
        Iterator iterDtlCONT = null;
        String query = null;

        //DOK PLP Element Header
        String KD_KANTOR = null;
        String TIPE_DATA = null;
        String KD_TPS_ASAL = null;
        String REF_NUMBER = null;
        String NO_SURAT = null;
        String TGL_SURAT = null;
        String NO_BATAL_PLP = null;
        String TGL_BATAL_PLP = null;
        String ALASAN = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NM_PEMOHON = null;

        //DOK PLP Element Dtl KMS
        String JNS_KMS = null;
        String JML_KMS = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;

        //DOK PLP Element Dtl CONT
        String NO_CONT = null;
        String UK_CONT = null;

        String result = null;
        boolean duplicateRef = false;
        String tempRefNumber = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            mydb = new Db();
            SAXBuilder builder = new SAXBuilder();
            if (fStream != null) {
                try {
                    fStream = fStream.replace("xmlns=\"loadbatalplp.xsd\"", "");

                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("BATALPLP");
                    for (int i = 0; i < list.size(); i++) {
                        ListRes = (Element) list.get(i);
                        List header = ListRes.getChildren("HEADER");
                        for (int j = 0; j < header.size(); j++) {
                            ListHdr = (Element) header.get(j);
                            try {
                                KD_KANTOR = ListHdr.getChildText("KD_KANTOR");
                                if (KD_KANTOR == null) {
                                    KD_KANTOR = "";
                                }
                            } catch (Exception e) {
                                KD_KANTOR = "";
                            }
                            try {
                                TIPE_DATA = ListHdr.getChildText("TIPE_DATA");
                                if (TIPE_DATA == null) {
                                    TIPE_DATA = "";
                                }
                            } catch (Exception e) {
                                TIPE_DATA = "";
                            }
                            try {
                                KD_TPS_ASAL = ListHdr.getChildText("KD_TPS");
                                if (KD_TPS_ASAL == null) {
                                    KD_TPS_ASAL = "";
                                }
                            } catch (Exception e) {
                                KD_TPS_ASAL = "";
                            }
                            try {
                                REF_NUMBER = ListHdr.getChildText("REF_NUMBER");
                                if (REF_NUMBER == null) {
                                    REF_NUMBER = "";
                                }
                            } catch (Exception e) {
                                REF_NUMBER = "";
                            }
                            try {
                                NO_SURAT = ListHdr.getChildText("NO_SURAT");
                                if (NO_SURAT == null) {
                                    NO_SURAT = "";
                                }
                            } catch (Exception e) {
                                NO_SURAT = "";
                            }
                            try {
                                TGL_SURAT = ListHdr.getChildText("TGL_SURAT");
                                if (TGL_SURAT == null) {
                                    TGL_SURAT = "";
                                }
                            } catch (Exception e) {
                                TGL_SURAT = "";
                            }
                            try {
                                NO_BATAL_PLP = ListHdr.getChildText("NO_PLP");
                                if (NO_BATAL_PLP == null) {
                                    NO_BATAL_PLP = "";
                                }
                            } catch (Exception e) {
                                NO_BATAL_PLP = "";
                            }
                            try {
                                TGL_BATAL_PLP = ListHdr.getChildText("TGL_PLP");
                                if (TGL_BATAL_PLP == null) {
                                    TGL_BATAL_PLP = "";
                                }
                            } catch (Exception e) {
                                TGL_BATAL_PLP = "";
                            }
                            try {
                                ALASAN = ListHdr.getChildText("ALASAN");
                                if (ALASAN == null) {
                                    ALASAN = "";
                                }
                            } catch (Exception e) {
                                ALASAN = "";
                            }
                            try {
                                NO_BC11 = ListHdr.getChildText("NO_BC11");
                                if (NO_BC11 == null) {
                                    NO_BC11 = "";
                                }
                            } catch (Exception e) {
                                NO_BC11 = "";
                            }
                            try {
                                TGL_BC11 = ListHdr.getChildText("TGL_BC11");
                                if (TGL_BC11 == null) {
                                    TGL_BC11 = "";
                                }
                            } catch (Exception e) {
                                TGL_BC11 = "";
                            }
                            try {
                                NM_PEMOHON = ListHdr.getChildText("NM_PEMOHON");
                                if (NM_PEMOHON == null) {
                                    NM_PEMOHON = "";
                                }
                            } catch (Exception e) {
                                NM_PEMOHON = "";
                            }
                            duplicateRef = false;
                            query = "SELECT * FROM T_REQUEST_PLP WHERE REF_NUMBER = ?";

                            preparedStatement = mydb.preparedstmt(query);
                            preparedStatement.setString(1, REF_NUMBER);
                            rs = preparedStatement.executeQuery();
                            if (!rs.next()) {
                                duplicateRef = true;
                                try {
                                    query = "INSERT INTO T_REQUEST_PLP(REF_NUMBER,NO_PLP,TGL_PLP,KODE_TPS_ASAL,KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,"
                                            + "KODE_GUDANG_TUJUAN,NAMA_KAPAL,NO_VOYAGE,TGL_TIBA,CALL_SIGN,KODE_KANTOR,KODE_ALASAN_PLP,NO_BC11,TGL_BC11,"
                                            + "TIPE_DATA,YOR_ASAL,YOR_TUJUAN,NAMA_PEMOHON,NO_BATAL_PLP,TGL_BATAL_PLP,ALASAN_BATAL,STATUS,TGL_BUAT,STATUS_BATAL,KD_RESPON,KD_RESPON_BATAL,RECEIVED_DATE)"
                                            + "VALUES (?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,?,?,"
                                            + " ?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,"
                                            + "TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, REF_NUMBER);
                                    preparedStatement.setString(2, NO_SURAT);
                                    preparedStatement.setString(3, TGL_SURAT);
                                    preparedStatement.setString(4, KD_TPS_ASAL);
                                    preparedStatement.setString(5, "");
                                    preparedStatement.setString(6, "");
                                    preparedStatement.setString(7, "");
                                    preparedStatement.setString(8, "");
                                    preparedStatement.setString(9, "");
                                    preparedStatement.setString(10, "");
                                    preparedStatement.setString(11, "");
                                    preparedStatement.setString(12, KD_KANTOR);
                                    preparedStatement.setString(13, "");
                                    preparedStatement.setString(14, NO_BC11);
                                    preparedStatement.setString(15, TGL_BC11);
                                    preparedStatement.setString(16, TIPE_DATA);
                                    preparedStatement.setString(17, "");
                                    preparedStatement.setString(18, "");
                                    preparedStatement.setString(19, NM_PEMOHON);
                                    preparedStatement.setString(20, NO_BATAL_PLP);
                                    preparedStatement.setString(21, TGL_BATAL_PLP);
                                    preparedStatement.setString(22, ALASAN);
                                    preparedStatement.setString(23, "");
                                    preparedStatement.setString(24, "");
                                    preparedStatement.setString(25, "500");
                                    preparedStatement.setString(26, "");
                                    preparedStatement.setString(27, "");
                                    preparedStatement.setDate(28, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");
                                } catch (Exception e) {
                                    query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                            + "VALUES (?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, "XMLPLPBATAL");
                                    preparedStatement.setString(2, "Parsing Dok PLP Batal");
                                    preparedStatement.setString(3, "Insert DB Header");
                                    preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                    preparedStatement.setDate(5, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");
                                    result = "Gagal Insert Header";
                                }
                            } else {
                                if (tempRefNumber == null) {
                                    tempRefNumber = REF_NUMBER + ";";
                                } else {
                                    tempRefNumber = REF_NUMBER + ";" + tempRefNumber;
                                }
                            }
                        }
                        List detil = ListRes.getChildren("DETIL");
                        for (int k = 0; k < detil.size(); k++) {
                            ListDtl = (Element) detil.get(k);
                            List kms = ListDtl.getChildren("KMS");
                            for (int l = 0; l < kms.size(); l++) {
                                ListDtlKMS = (Element) kms.get(l);
                                try {
                                    JNS_KMS = ListDtlKMS.getChildText("JNS_KMS");
                                    if (JNS_KMS == null) {
                                        JNS_KMS = "";
                                    }
                                } catch (Exception e) {
                                    JNS_KMS = "";
                                }
                                try {
                                    JML_KMS = ListDtlKMS.getChildText("JML_KMS");
                                    if (JML_KMS == null) {
                                        JML_KMS = "";
                                    }
                                } catch (Exception e) {
                                    JML_KMS = "";
                                }
                                try {
                                    NO_BL_AWB = ListDtlKMS.getChildText("NO_BL_AWB");
                                    if (NO_BL_AWB == null) {
                                        NO_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    NO_BL_AWB = "";
                                }
                                try {
                                    TGL_BL_AWB = ListDtlKMS.getChildText("TGL_BL_AWB");
                                    if (TGL_BL_AWB == null) {
                                        TGL_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    TGL_BL_AWB = "";
                                }
                                if (duplicateRef) {
                                    try {

                                        query = "INSERT INTO T_REQUEST_PLP_KMS(REF_NUMBER,KODE_KEMASAN,NO_BL,TGL_BL,JUMLAH_KEMASAN)"
                                                + "VALUES (?,?,?,TO_DATE(?,'YYYYMMDD'),?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, REF_NUMBER);
                                        preparedStatement.setString(2, JNS_KMS);
                                        preparedStatement.setString(3, NO_BL_AWB);
                                        preparedStatement.setString(4, TGL_BL_AWB);
                                        preparedStatement.setString(5, JML_KMS);

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                    } catch (Exception e) {
                                        query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                                + "VALUES (?,?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, "XMLPLPBATAL");
                                        preparedStatement.setString(2, "Parsing Dok PLP Batal");
                                        preparedStatement.setString(3, "Insert DB Cont");
                                        preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                        result = "Gagal Insert Detil Kms";
                                    }
                                }
                            }
                            List cont = ListDtl.getChildren("CONT");
                            for (int l = 0; l < cont.size(); l++) {
                                ListDtlCONT = (Element) cont.get(l);
                                try {
                                    NO_CONT = ListDtlCONT.getChildText("NO_CONT");
                                    if (NO_CONT == null) {
                                        NO_CONT = "";
                                    }
                                } catch (Exception e) {
                                    NO_CONT = "";
                                }
                                try {
                                    UK_CONT = ListDtlCONT.getChildText("UK_CONT");
                                    if (UK_CONT == null) {
                                        UK_CONT = "";
                                    }
                                } catch (Exception e) {
                                    UK_CONT = "";
                                }

                                //DECISION VALIDATION CHARACTER NO_CONT AND UK_CONT
                                if ((11 - NO_CONT.length()) > -1) {
                                    NO_CONT = NO_CONT.substring(0, NO_CONT.length());
                                } else {
                                    NO_CONT = NO_CONT.substring(0, 11);
                                }
                                if ((2 - UK_CONT.length()) > -1) {
                                    UK_CONT = UK_CONT.substring(0, UK_CONT.length());
                                } else {
                                    UK_CONT = UK_CONT.substring(0, 2);
                                }
                                if (duplicateRef) {
                                    try {
                                        query = "INSERT INTO T_REQUEST_PLP_CONT(REF_NUMBER,NO_CONT,UKURAN_CONT)"
                                                + "VALUES (?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, REF_NUMBER);
                                        preparedStatement.setString(2, NO_CONT);
                                        preparedStatement.setString(3, UK_CONT);

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                    } catch (Exception e) {
                                        query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE)"
                                                + "VALUES (?,?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, "XMLPLPBATAL");
                                        preparedStatement.setString(2, "Parsing LP Batal");
                                        preparedStatement.setString(3, "Insert DB Cont");
                                        preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                        result = "Gagal Insert Detil Cont";
                                    }

                                }
                            }
                        }
                    }

                    if (result == null) {
                        if (duplicateRef == true) {

                            query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
                                    + "VALUES (?,?,?)";

                            preparedStatement = mydb.preparedstmt(query);
                            preparedStatement.setDate(1, getCurrentTimeStamp());
                            preparedStatement.setString(2, "DOKPLP");
                            preparedStatement.setString(3, REF_NUMBER);

                            preparedStatement.executeUpdate();
                            mydb.execute("commit");
                            result = REF_NUMBER + "_" + "Insert Success";
                        } else {
                            result = "Ref Number Sudah Pernah diajukan " + tempRefNumber + "_Gagal Inser";
                        }
                    }

                } catch (Exception e) {
                    result = REF_NUMBER + "_" + e.getMessage();
                    exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLPLP_Batal", result);

                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            result = REF_NUMBER + "_" + ex.getMessage();
            exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLPLP_Batal", result);

            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (mydb != null) {
                    mydb.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex1) {
            } finally {
                mydb = null;
            }

            List = null;
            doc = null;
            return result;
        }
    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
