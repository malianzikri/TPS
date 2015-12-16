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
public class ParsingXMLResponPLPBatal_Asal {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocumentPLPBatalAsal(String fStream) throws Exception {

        Db mydb = null;
        Document doc = null;
        Element List = null;;
        Element ListRes = null;
        Element ListHdr = null;
        Element ListDtl = null;
        Element ListDtlCONT = null;
        Element ListDtlKMS = null;
        Iterator iter = null;
        Iterator iterRes = null;
        Iterator iterHdr = null;
        Iterator iterDtl = null;
        Iterator iterDtlCONT = null;
        Iterator iterDtlKMS = null;
        String query = null;

        //DOK PLP Element Header
        String KD_KANTOR = null;
        String KD_TPS = null;
        String REF_NUMBER = null;
        String NO_BATAL_PLP = null;
        String TGL_BATAL_PLP = null;

        //DOK PLP Element Dtl KMS
        String JNS_KMS = null;
        String JML_KMS = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;
        String FL_SETUJU_KMS = null;

        //DOK PLP Element Dtl CONT
        String NO_CONT = null;
        String UK_CONT = null;
        String FL_SETUJU_CONT = null;

        int responID = 0;
        String result = null;
        boolean duplicateRefNumber = false;
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
                    List list = rootNode.getChildren("RESPON_BATAL");
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
                                REF_NUMBER = ListHdr.getChildText("REF_NUMBER");
                                if (REF_NUMBER == null) {
                                    REF_NUMBER = "";
                                }
                            } catch (Exception e) {
                                REF_NUMBER = "";
                            }
                            try {
                                NO_BATAL_PLP = ListHdr.getChildText("NO_BATAL_PLP");
                                if (NO_BATAL_PLP == null) {
                                    NO_BATAL_PLP = "";
                                }
                            } catch (Exception e) {
                                NO_BATAL_PLP = "";
                            }
                            try {
                                TGL_BATAL_PLP = ListHdr.getChildText("TGL_BATAL_PLP");
                                if (TGL_BATAL_PLP == null) {
                                    TGL_BATAL_PLP = "";
                                }
                            } catch (Exception e) {
                                TGL_BATAL_PLP = "";
                            }

                            mydb.execute("SELECT RESPLP_SEQ.NEXTVAL as ID FROM DUAL");
                            if (mydb.next()) {
                                responID = mydb.getInt("ID");
                            }
                            try {
                                duplicateRefNumber = false;
                                query = "SELECT * FROM T_REQUEST_PLP WHERE REF_NUMBER = ?";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setString(1, REF_NUMBER);
                                rs = preparedStatement.executeQuery();
                                if (rs.next()) {
                                    duplicateRefNumber = true;
                                }
                                if (!duplicateRefNumber) {
                                    query = "INSERT INTO T_RESPON_BATAL_PLP(RESPONID,KD_KANTOR,KD_TPS,REF_NUMBER,NO_BATAL_PLP,TGL_BATAL_PLP,RECEIVED_DATE)"
                                            + "VALUES (?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setInt(1, responID);
                                    preparedStatement.setString(2, KD_KANTOR);
                                    preparedStatement.setString(3, KD_TPS);
                                    preparedStatement.setString(4, REF_NUMBER);
                                    preparedStatement.setString(5, NO_BATAL_PLP);
                                    preparedStatement.setString(6, TGL_BATAL_PLP);
                                    preparedStatement.setDate(7, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");
                                } else {
                                    if (tempRefNumber == null) {
                                        tempRefNumber = REF_NUMBER + ";";
                                    } else {
                                        tempRefNumber = REF_NUMBER + ";" + tempRefNumber;
                                    }
                                }
                            } catch (Exception e) {
                                query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE)"
                                        + "VALUES (?,?,?,?,?)";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setString(1, "GetResponPLP");
                                preparedStatement.setString(2, "Parsing Dok Respon PLP Batal");
                                preparedStatement.setString(3, "Insert DB Header");
                                preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                preparedStatement.setDate(5, getCurrentTimeStamp());

                                preparedStatement.executeUpdate();
                                mydb.execute("commit");
                                result = "Gagal Insert Hdr";
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
                                try {
                                    FL_SETUJU_KMS = ListDtlKMS.getChildText("FL_SETUJU");
                                    if (FL_SETUJU_KMS == null) {
                                        FL_SETUJU_KMS = "";
                                    }
                                } catch (Exception e) {
                                    FL_SETUJU_KMS = "";
                                }
                                try {
                                    if (!duplicateRefNumber) {
                                        query = "INSERT INTO T_RESPON_BATAL_PLP_KMS(RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL,STATUS_PLP)"
                                                + "VALUES (?,?,?,?,TO_DATE(?,'YYYYMMDD'),?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setInt(1, responID);
                                        preparedStatement.setString(2, JNS_KMS);
                                        preparedStatement.setString(3, JML_KMS);
                                        preparedStatement.setString(4, NO_BL_AWB);
                                        preparedStatement.setString(5, TGL_BL_AWB);
                                        preparedStatement.setString(6, FL_SETUJU_KMS);

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");
                                    }
                                } catch (Exception e) {
                                    query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE)"
                                            + "VALUES (?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, "GetResponPLP");
                                    preparedStatement.setString(2, "Parsing Dok Respon PLP Batal");
                                    preparedStatement.setString(3, "Insert DB Kms");
                                    preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                    preparedStatement.setDate(5, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");

                                    result = "Gagal Insert Dtl Kms";
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
                                try {
                                    FL_SETUJU_CONT = ListDtlCONT.getChildText("FL_SETUJU");
                                    if (FL_SETUJU_CONT == null) {
                                        FL_SETUJU_CONT = "";
                                    }
                                } catch (Exception e) {
                                    FL_SETUJU_CONT = "";
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
                                try {
                                    if (!duplicateRefNumber) {

                                        query = "INSERT INTO T_RESPON_BATAL_PLP_CONT(RESPONID,NO_CONT,STATUS_PLP,UK_CONT)"
                                                + "VALUES (?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setInt(1, responID);
                                        preparedStatement.setString(2, NO_CONT);
                                        preparedStatement.setString(3, FL_SETUJU_CONT);
                                        preparedStatement.setString(4, UK_CONT);

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                    }
                                } catch (Exception e) {
                                    query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE)"
                                            + "VALUES (?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, "GetResponPLP");
                                    preparedStatement.setString(2, "Parsing Dok Respon PLP Batal");
                                    preparedStatement.setString(3, "Insert DB Cont");
                                    preparedStatement.setString(4, "RefNumber :" + REF_NUMBER + " ;Error" + e.getMessage());
                                    preparedStatement.setDate(5, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");

                                    result = "Gagal Insert Dtl Cont";
                                }
                            }
                        }
                    }

                    if (tempRefNumber == null) {
                        query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
                                + "VALUES (?,?,?)";

                        preparedStatement = mydb.preparedstmt(query);
                        preparedStatement.setDate(1, getCurrentTimeStamp());
                        preparedStatement.setString(2, "RESPLP");
                        preparedStatement.setString(3, REF_NUMBER);

                        preparedStatement.executeUpdate();
                        mydb.execute("commit");

                        result = REF_NUMBER + "_" + "Insert Success";
                    } else {
                        result = "Ref Number Sudah Pernah diajukan " + tempRefNumber + "_Gagal Insert";
                    }

                } catch (Exception e) {
                    result = REF_NUMBER + "_" + e.getMessage();
                    exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLPLPBatal_Asal", result);

                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            result = REF_NUMBER + "_" + ex.getMessage();
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