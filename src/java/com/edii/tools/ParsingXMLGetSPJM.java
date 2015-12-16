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
public class ParsingXMLGetSPJM {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocument(String fStream) throws Exception {;
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

        //DOK SPJM Element Header
        String CAR = null;
        String KD_KANTOR = null;
        String NO_PIB = null;
        String TGL_PIB = null;
        String NPWP_IMP = null;
        String NAMA_IMP = null;
        String NPWP_PPJK = null;
        String NAMA_PPJK = null;
        String GUDANG = null;
        String JML_CONT = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_POS_BC11 = null;
        String FL_KARANTINA = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;

        //DOK PLP Element Dtl KMS
        String JNS_KMS = null;
        String MERK_KMS = null;
        String JML_KMS = null;

        //DOK SPJM Element Dtl CONT
        String NO_CONT = null;
        String SIZE = null;

        String result = null;
        boolean duplicateFile = false;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            mydb = new Db();
            SAXBuilder builder = new SAXBuilder();//jdom2
            if (fStream != null) {
                try {
                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("SPJM");
                    for (int i = 0; i < list.size(); i++) {
                        ListRes = (Element) list.get(i);
                        List header = ListRes.getChildren("HEADER");
                        for (int j = 0; j < header.size(); j++) {
                            ListHdr = (Element) header.get(j);
                            try {
                                CAR = ListHdr.getChildText("CAR");
                                if (CAR == null) {
                                    CAR = "";
                                }
                            } catch (Exception e) {
                                CAR = "";
                            }
                            try {
                                KD_KANTOR = ListHdr.getChildText("KD_KANTOR");
                                if (KD_KANTOR == null) {
                                    KD_KANTOR = "";
                                }
                            } catch (Exception e) {
                                KD_KANTOR = "";
                            }
                            try {
                                NO_PIB = ListHdr.getChildText("NO_PIB");
                                if (NO_PIB == null) {
                                    NO_PIB = "";
                                }
                            } catch (Exception e) {
                                NO_PIB = "";
                            }
                            try {
                                TGL_PIB = ListHdr.getChildText("TGL_PIB");
                                if (TGL_PIB == null) {
                                    TGL_PIB = "";
                                }
                            } catch (Exception e) {
                                TGL_PIB = "";
                            }
                            try {
                                NPWP_IMP = ListHdr.getChildText("NPWP_IMP");
                                if (NPWP_IMP == null) {
                                    NPWP_IMP = "";
                                }
                            } catch (Exception e) {
                                NPWP_IMP = "";
                            }
                            try {
                                NAMA_IMP = ListHdr.getChildText("NAMA_IMP");
                                if (NAMA_IMP == null) {
                                    NAMA_IMP = "";
                                }
                            } catch (Exception e) {
                                NAMA_IMP = "";
                            }
                            try {
                                NPWP_PPJK = ListHdr.getChildText("NPWP_PPJK");
                                if (NPWP_PPJK == null) {
                                    NPWP_PPJK = "";
                                }
                            } catch (Exception e) {
                                NPWP_PPJK = "";
                            }
                            try {
                                NAMA_PPJK = ListHdr.getChildText("NAMA_PPJK");
                                if (NAMA_PPJK == null) {
                                    NAMA_PPJK = "";
                                }
                            } catch (Exception e) {
                                NAMA_PPJK = "";
                            }
                            try {
                                GUDANG = ListHdr.getChildText("GUDANG");
                                if (GUDANG == null) {
                                    GUDANG = "";
                                }
                            } catch (Exception e) {
                                NO_VOY_FLIGHT = "";
                            }
                            try {
                                JML_CONT = ListHdr.getChildText("JML_CONT");
                                if (JML_CONT == null) {
                                    JML_CONT = "";
                                }
                            } catch (Exception e) {
                                JML_CONT = "";
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
                                NO_POS_BC11 = ListHdr.getChildText("NO_POS_BC11");
                                if (NO_POS_BC11 == null) {
                                    NO_POS_BC11 = "";
                                }
                            } catch (Exception e) {
                                NO_POS_BC11 = "";
                            }
                            try {
                                FL_KARANTINA = ListHdr.getChildText("FL_KARANTINA");
                                if (FL_KARANTINA == null) {
                                    FL_KARANTINA = "";
                                }
                            } catch (Exception e) {
                                FL_KARANTINA = "";
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
                                duplicateFile = false;
                                mydb.execute("SELECT * FROM SPJMHDR WHERE CAR = '" + CAR + "'");
                                if (mydb.next()) {
                                    duplicateFile = true;
                                } else {
                                    query = "INSERT INTO SPJMHDR(CAR,KD_KANTOR,NO_PIB,TGL_PIB,NPWP_IMP,"
                                            + "NAMA_IMP,NPWP_PPJK,NAMA_PPJK,KD_GUDANG,JML_CONT,NO_BC11,TGL_BC11,"
                                            + "NO_POS_BC11,FL_KARANTINA,NM_ANGKUT,NO_VOY_FLIGHT,RECEIVED_DATE)"
                                            + "VALUES (?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,"
                                            + "?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setString(1, CAR);
                                    preparedStatement.setString(2, KD_KANTOR);
                                    preparedStatement.setString(3, NO_PIB);
                                    preparedStatement.setString(4, TGL_PIB);
                                    preparedStatement.setString(5, NPWP_IMP);
                                    preparedStatement.setString(6, NAMA_IMP);
                                    preparedStatement.setString(7, NPWP_PPJK);
                                    preparedStatement.setString(8, NAMA_PPJK);
                                    preparedStatement.setString(9, GUDANG.toUpperCase());
                                    preparedStatement.setString(10, JML_CONT);
                                    preparedStatement.setString(11, NO_BC11);
                                    preparedStatement.setString(12, TGL_BC11);
                                    preparedStatement.setString(13, NO_POS_BC11);
                                    preparedStatement.setString(14, FL_KARANTINA);
                                    preparedStatement.setString(15, NM_ANGKUT);
                                    preparedStatement.setString(16, NO_VOY_FLIGHT);
                                    preparedStatement.setDate(17, getCurrentTimeStamp());

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");
                                }
                            } catch (Exception e) {
                                query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                        + "VALUES (?,?,?,?,?)";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setString(1, "DOKLP_TUJUAN");
                                preparedStatement.setString(2, "Insert DokSPJM Header");
                                preparedStatement.setString(3, "Nama File DOKSPJM ; CAR" + CAR);
                                preparedStatement.setString(4, "Error " + e.getMessage());
                                preparedStatement.setDate(5, getCurrentTimeStamp());

                                preparedStatement.executeUpdate();
                                mydb.execute("commit");
                                result = CAR + "_" + "Insert Failed";
                            }
                        }
                        List detil = ListRes.getChildren("DETIL");
                        for (int k = 0; k < detil.size(); k++) {
                            ListDtl = (Element) detil.get(k);
                            try {
                                List kms = ListDtl.getChildren("KMS");
                                for (int l = 0; l < kms.size(); l++) {
                                    ListDtlKMS = (Element) kms.get(l);
                                    try {
                                        CAR = ListDtlKMS.getChildText("CAR");
                                        if (CAR == null) {
                                            CAR = "";
                                        }
                                    } catch (Exception e) {
                                        CAR = "";
                                    }
                                    try {
                                        JNS_KMS = ListDtlKMS.getChildText("JNS_KMS");
                                        if (JNS_KMS == null) {
                                            JNS_KMS = "";
                                        }
                                    } catch (Exception e) {
                                        JNS_KMS = "";
                                    }
                                    try {
                                        MERK_KMS = ListDtlKMS.getChildText("MERK_KMS");
                                        if (MERK_KMS == null) {
                                            MERK_KMS = "";
                                        }
                                    } catch (Exception e) {
                                        MERK_KMS = "";
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
                                        if (!duplicateFile) {
                                            query = "INSERT INTO SPJMKMS(CAR,JNS_KMS,MERK_KMS,JML_KMS)"
                                                    + "VALUES (?,?,?,?)";

                                            preparedStatement = mydb.preparedstmt(query);
                                            preparedStatement.setString(1, CAR);
                                            preparedStatement.setString(2, JNS_KMS);
                                            preparedStatement.setString(3, MERK_KMS);
                                            preparedStatement.setString(4, JML_KMS);

                                            preparedStatement.executeUpdate();
                                            mydb.execute("commit");
                                        }
                                    } catch (Exception e) {
                                        query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                                + "VALUES (?,?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, "DOKLP_TUJUAN");
                                        preparedStatement.setString(2, "Insert DokSPJM Dtl Kms");
                                        preparedStatement.setString(3, "Nama File DOKSPJM ; CAR" + CAR);
                                        preparedStatement.setString(4, "Error " + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");
                                        result = CAR + "_" + "Insert Failed";
                                    }
                                }
                            } catch (Exception ex) {
                            }
                            try {
                                List car = ListDtl.getChildren("CAR");
                                for (int m = 0; m < car.size(); m++) {
                                    ListDtlCONT = (Element) car.get(m);
                                    try {
                                        CAR = ListDtlCONT.getChildText("CAR");
                                        if (CAR == null) {
                                            CAR = "";
                                        }
                                    } catch (Exception e) {
                                        CAR = "";
                                    }
                                    try {
                                        NO_CONT = ListDtlCONT.getChildText("NO_CONT");
                                        if (NO_CONT == null) {
                                            NO_CONT = "";
                                        }
                                    } catch (Exception e) {
                                        NO_CONT = "";
                                    }
                                    try {
                                        SIZE = ListDtlCONT.getChildText("SIZE");
                                        if (SIZE == null) {
                                            SIZE = "";
                                        }
                                    } catch (Exception e) {
                                        SIZE = "";
                                    }
                                    try {
                                        if (!duplicateFile) {
                                            query = "INSERT INTO SPJMCONT(CAR,NO_CONT,SIZE_CONT)"
                                                    + "VALUES (?,?,?)";

                                            preparedStatement = mydb.preparedstmt(query);
                                            preparedStatement.setString(1, CAR);
                                            preparedStatement.setString(2, NO_CONT.substring(0, 11));
                                            preparedStatement.setString(3, SIZE.substring(0, 2));

                                            preparedStatement.executeUpdate();
                                            mydb.execute("commit");
                                        }
                                    } catch (Exception e) {
                                        query = "INSERT INTO TPSLOG (ERROR_CODE, PROCESS_NAME, SUB_PROCESS_NAME, ERROR_DESC, LOG_DATE)"
                                                + "VALUES (?,?,?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setString(1, "DOKLP_TUJUAN");
                                        preparedStatement.setString(2, "Insert DokSPJM Dtl Cont");
                                        preparedStatement.setString(3, "Nama File DOKSPJM ; CAR" + CAR);
                                        preparedStatement.setString(4, "Error " + e.getMessage());
                                        preparedStatement.setDate(5, getCurrentTimeStamp());

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");
                                        result = CAR + "_" + "Insert Failed";

                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
                            + "VALUES (?,?,?)";

                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setDate(1, getCurrentTimeStamp());
                    preparedStatement.setString(2, "DOKSPJM");
                    preparedStatement.setString(3, CAR);

                    preparedStatement.executeUpdate();
                    mydb.execute("commit");
                    result = CAR + "_" + "Insert Success";

                } catch (Exception e) {
                    result = CAR + "_" + e.getMessage();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLGetSPJM", result);

            result = CAR + "_" + "Insert Failed";
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
                List = null;
                doc = null;
                rs = null;
                preparedStatement = null;
            }
        }
        return result;
    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
