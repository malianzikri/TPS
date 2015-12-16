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
public class ParsingXMLPLP {

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
        String GUDANG_ASAL = null;
        String KD_TPS_TUJUAN = null;
        String GUDANG_TUJUAN = null;
        String KD_ALASAN_PLP = null;
        String YOR_ASAL = null;
        String YOR_TUJUAN = null;
        String CALL_SIGN = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String TGL_TIBA = null;
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
        int count = 0;
        boolean duplicateRef = false;
        String tempRefNumber = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            mydb = new Db();
            SAXBuilder builder = new SAXBuilder();
            if (fStream != null) {
                try {
                    fStream = fStream.replace("xmlns=\"loadplp.xsd\"", "");
                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("LOADPLP");
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
                                KD_TPS_ASAL = ListHdr.getChildText("KD_TPS_ASAL");
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
                                GUDANG_ASAL = ListHdr.getChildText("GUDANG_ASAL");
                                if (GUDANG_ASAL == null) {
                                    GUDANG_ASAL = "";
                                }
                            } catch (Exception e) {
                                GUDANG_ASAL = "";
                            }
                            try {
                                KD_TPS_TUJUAN = ListHdr.getChildText("KD_TPS_TUJUAN");
                                if (KD_TPS_TUJUAN == null) {
                                    KD_TPS_TUJUAN = "";
                                }
                            } catch (Exception e) {
                                KD_TPS_TUJUAN = "";
                            }
                            try {
                                GUDANG_TUJUAN = ListHdr.getChildText("GUDANG_TUJUAN");
                                if (GUDANG_TUJUAN == null) {
                                    GUDANG_TUJUAN = "";
                                }
                            } catch (Exception e) {
                                GUDANG_TUJUAN = "";
                            }
                            try {
                                KD_ALASAN_PLP = ListHdr.getChildText("KD_ALASAN_PLP");
                                if (KD_ALASAN_PLP == null) {
                                    KD_ALASAN_PLP = "";
                                }
                            } catch (Exception e) {
                                KD_ALASAN_PLP = "";
                            }
                            try {
                                YOR_ASAL = ListHdr.getChildText("YOR_ASAL");
                                if (YOR_ASAL == null) {
                                    YOR_ASAL = "";
                                }
                            } catch (Exception e) {
                                YOR_ASAL = "";
                            }
                            try {
                                YOR_TUJUAN = ListHdr.getChildText("YOR_TUJUAN");
                                if (YOR_TUJUAN == null) {
                                    YOR_TUJUAN = "";
                                }
                            } catch (Exception e) {
                                YOR_TUJUAN = "";
                            }
                            try {
                                CALL_SIGN = ListHdr.getChildText("CALL_SIGN");
                                if (CALL_SIGN == null) {
                                    CALL_SIGN = "";
                                }
                            } catch (Exception e) {
                                CALL_SIGN = "";
                            }
                            try {
                                NM_ANGKUT = ListHdr.getChildText("NM_ANGKUT");
                                if (NM_ANGKUT == null) {
                                    NM_ANGKUT = "";
                                }
                            } catch (Exception e) {
                                NM_ANGKUT = "";
                            }
                            try {
                                NO_VOY_FLIGHT = ListHdr.getChildText("NO_VOY_FLIGHT");
                                if (NO_VOY_FLIGHT == null) {
                                    NO_VOY_FLIGHT = "";
                                }
                            } catch (Exception e) {
                                NO_VOY_FLIGHT = "";
                            }
                            try {
                                TGL_TIBA = ListHdr.getChildText("TGL_TIBA");
                                if (TGL_TIBA == null) {
                                    TGL_TIBA = "";
                                }
                            } catch (Exception e) {
                                TGL_TIBA = "";
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
                                            + "VALUES (?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),"
                                            + " ?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,"
                                            + "?,?,?,?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, REF_NUMBER);
                                    preparedStatement.setString(2, NO_SURAT);
                                    preparedStatement.setString(3, TGL_SURAT);
                                    preparedStatement.setString(4, KD_TPS_ASAL);
                                    preparedStatement.setString(5, GUDANG_ASAL);
                                    preparedStatement.setString(6, KD_TPS_TUJUAN);
                                    preparedStatement.setString(7, GUDANG_TUJUAN);
                                    preparedStatement.setString(8, NM_ANGKUT);
                                    preparedStatement.setString(9, NO_VOY_FLIGHT);
                                    preparedStatement.setString(10, TGL_TIBA);
                                    preparedStatement.setString(11, CALL_SIGN);
                                    preparedStatement.setString(12, KD_KANTOR);
                                    preparedStatement.setString(13, KD_ALASAN_PLP);
                                    preparedStatement.setString(14, NO_BC11);
                                    preparedStatement.setString(15, TGL_BC11);
                                    preparedStatement.setString(16, TIPE_DATA);
                                    preparedStatement.setString(17, YOR_ASAL);
                                    preparedStatement.setString(18, YOR_TUJUAN);
                                    preparedStatement.setString(19, NM_PEMOHON);
                                    preparedStatement.setString(20, "");
                                    preparedStatement.setString(21, "");
                                    preparedStatement.setString(22, "");
                                    preparedStatement.setString(23, "500");
                                    preparedStatement.setString(24, "");
                                    preparedStatement.setString(25, "");
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
                                    preparedStatement.setString(2, "Parsing Dok PLP");
                                    preparedStatement.setString(3, "Insert DB Header");
                                    preparedStatement.setString(4, "Ref Number : " + REF_NUMBER + "; Error" + e.getMessage());
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
                                if (duplicateRef == true) {
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
                                        preparedStatement.setString(2, "Parsing Dok PLP");
                                        preparedStatement.setString(3, "Insert DB Kms");
                                        preparedStatement.setString(4, "Ref Number : " + REF_NUMBER + "; Error" + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");
                                        result = "Gagal Insert Detail Kms";
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
                                        mydb.execute("commit");
                                        count++;
                                    } catch (Exception e) {
                                        query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                                + "VALUES (?,?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, "UploadMohonPLP");
                                        preparedStatement.setString(2, "Parsing Dok PLP");
                                        preparedStatement.setString(3, "Insert DB Ex" + count);
                                        preparedStatement.setString(4, " Error" + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");
                                        result = "Gagal Insert Detail Cont";
                                    }
                                }
                            }

                        }
                    }

                    if (result == null) {
                        if (tempRefNumber == null) {
                            if (REF_NUMBER != null) {
                                query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
                                        + "VALUES (?,?,?)";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setDate(1, getCurrentTimeStamp());
                                preparedStatement.setString(2, "DOKPLP");
                                preparedStatement.setString(3, REF_NUMBER);

                                preparedStatement.executeUpdate();
                                mydb.execute("commit");
                                result = REF_NUMBER + "_" + count + ";" + "Insert Success";
                            } else {
                                result = "Gagal Insert DB Check Format XML Anda";
                            }
                        } else {
                            result = "Ref Number Sudah Pernah diajukan " + tempRefNumber + "_Gagal Inser";
                        }
                    }

                } catch (Exception e) {
                    result = REF_NUMBER + "_" + count + ";" + e.getMessage();
                    exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLPLP", result);

                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            result = REF_NUMBER + "_" + count + ";" + ex.getMessage();
            exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLPLP", result);

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
