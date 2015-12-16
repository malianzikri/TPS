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
public class ParsingXMLResponPLP_Tujuan {
    private ExcuteProses exc = new ExcuteProses();
    public String parseDocumentPLPTujuan(String fStream) throws Exception {

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
        String KD_TPS = null;
        String KD_TPS_ASAL = null;
        String GUDANG_TUJUAN = null;
        String NO_PLP = null;
        String TGL_PLP = null;
        String CALL_SIGN = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String TGL_TIBA = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_SURAT = null;
        String TGL_SURAT = null;

        //DOK PLP Element Dtl KMS
        String JNS_KMS = null;
        String JML_KMS = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;

        //DOK PLP Element Dtl CONT
        String NO_CONT = null;
        String UK_CONT = null;
        String JNS_CONT = null;
        String NO_POS_BC11 = null;
        String CONSIGNEE = null;

        int responID = 0;
        String result = null;
        Tanggalan tgl = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            mydb = new Db();
            SAXBuilder builder = new SAXBuilder();
            if (fStream != null) {
                try {
                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("RESPONPLP");
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
                                KD_TPS = ListHdr.getChildText("KD_TPS");
                                if (KD_TPS == null) {
                                    KD_TPS = "";
                                }
                            } catch (Exception e) {
                                KD_TPS = "";
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
                                GUDANG_TUJUAN = ListHdr.getChildText("GUDANG_TUJUAN");
                                if (GUDANG_TUJUAN == null) {
                                    GUDANG_TUJUAN = "";
                                }
                            } catch (Exception e) {
                                GUDANG_TUJUAN = "";
                            }
                            try {
                                NO_PLP = ListHdr.getChildText("NO_PLP");
                                if (NO_PLP == null) {
                                    NO_PLP = "";
                                }
                            } catch (Exception e) {
                                NO_PLP = "";
                            }
                            try {
                                TGL_PLP = ListHdr.getChildText("TGL_PLP");
                                if (TGL_PLP == null) {
                                    TGL_PLP = "";
                                }
                            } catch (Exception e) {
                                TGL_PLP = "";
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

                            mydb.execute("SELECT RESPLP_SEQ.NEXTVAL as ID FROM DUAL");
                            if (mydb.next()) {
                                responID = mydb.getInt("ID");
                            }

                            query = "INSERT INTO T_PLP(RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,GUDANG_TUJUAN,NO_PLP,TGL_PLP,NAMA_KAPAL,NO_VOYAGE,CALL_SIGN,TGL_TIBA,RECEIVED_DATE,NO_BC11,TGL_BC11,NO_SURAT,TGL_SURAT)"
                                    + "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'))";

                            preparedStatement = mydb.preparedstmt(query);
                            preparedStatement.setInt(1, responID);
                            preparedStatement.setString(2, KD_KANTOR);
                            preparedStatement.setString(3, KD_TPS);
                            preparedStatement.setString(4, KD_TPS_ASAL);
                            preparedStatement.setString(5, GUDANG_TUJUAN);
                            preparedStatement.setString(6, NO_PLP);
                            preparedStatement.setString(7, TGL_PLP);
                            preparedStatement.setString(8, NM_ANGKUT);
                            preparedStatement.setString(9, NO_VOY_FLIGHT);
                            preparedStatement.setString(10, CALL_SIGN);
                            preparedStatement.setString(11,TGL_TIBA);
                            preparedStatement.setDate(12, getCurrentTimeStamp());
                            preparedStatement.setString(13, NO_BC11);
                            preparedStatement.setString(14, TGL_BC11);
                            preparedStatement.setString(15, NO_SURAT);
                            preparedStatement.setString(16,TGL_SURAT);

                            preparedStatement.executeUpdate();
                            mydb.execute("commit");
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
                                query = "INSERT INTO T_PLP_KMS(RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL)"
                                        + "VALUES (?,?,?,?,TO_DATE(?,'YYYYMMDD'))";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setInt(1, responID);
                                preparedStatement.setString(2, JNS_KMS);
                                preparedStatement.setString(3, JML_KMS);
                                preparedStatement.setString(4, NO_BL_AWB);
                                preparedStatement.setString(5, TGL_BL_AWB);

                                preparedStatement.executeUpdate();
                                mydb.execute("commit");

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
                                try {
                                    JNS_CONT = ListDtlCONT.getChildText("JNS_CONT");
                                    if (JNS_CONT == null) {
                                        JNS_CONT = "";
                                    }
                                } catch (Exception e) {
                                    JNS_CONT = "";
                                }
                                try {
                                    NO_POS_BC11 = ListDtlCONT.getChildText("NO_POS_BC11");
                                    if (NO_POS_BC11 == null) {
                                        NO_POS_BC11 = "";
                                    }
                                } catch (Exception e) {
                                    NO_POS_BC11 = "";
                                }
                                try {
                                    CONSIGNEE = ListDtlCONT.getChildText("CONSIGNEE");
                                    if (CONSIGNEE == null) {
                                        CONSIGNEE = "";
                                    }
                                } catch (Exception e) {
                                    CONSIGNEE = "";
                                }
                                try {
                                    NO_BL_AWB = ListDtlCONT.getChildText("NO_BL_AWB");
                                    if (NO_BL_AWB == null) {
                                        NO_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    NO_BL_AWB = "";
                                }
                                try {
                                    TGL_BL_AWB = ListDtlCONT.getChildText("TGL_BL_AWB");
                                    if (TGL_BL_AWB == null) {
                                        TGL_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    TGL_BL_AWB = "";
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

                                query = "INSERT INTO T_PLP_CONT(RESPONID,NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE,NO_BL_AWB,TGL_BL_AWB)"
                                        + "VALUES (?,?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'))";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setInt(1, responID);
                                preparedStatement.setString(2, NO_CONT);
                                preparedStatement.setString(3, UK_CONT);
                                preparedStatement.setString(4, JNS_CONT);
                                preparedStatement.setString(5, NO_POS_BC11);
                                preparedStatement.setString(6, CONSIGNEE);
                                preparedStatement.setString(7, NO_BL_AWB);
                                preparedStatement.setString(8, TGL_BL_AWB);

                                preparedStatement.executeUpdate();
                                mydb.execute("commit");
                            }
                        }

                    }

                    tgl = new Tanggalan();
                    query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
                            + "VALUES (?,?,?)";

                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setDate(1, getCurrentTimeStamp());
                    preparedStatement.setString(2, "RESPLP");
                    preparedStatement.setString(3, KD_TPS + "" + tgl.getNow("yyyyMMddhhms"));

                    preparedStatement.executeUpdate();
                    mydb.execute("commit");

                    result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + "Insert Success";

                } catch (Exception e) {
                    result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + e.getMessage();
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + ex.getMessage();
                                 exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLResponPLP_Tujuan", result);

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
                preparedStatement = null;
                rs = null;
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
