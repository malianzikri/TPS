/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.nsw.db.Db;
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
public class ParsingXMLCoarriCodecoSHL {
    private ExcuteProses exc = new ExcuteProses();

    public boolean parseDocument(String fStream) throws Exception {
        Db mydb = null;

        Document doc = null;
        Element List = null;
        Element ListRes = null;
        Element ListHdr = null;
        Element ListDtl = null;
        Element ListDtlCONT = null;
        Iterator iter = null;
        Iterator iterRes = null;
        Iterator iterHdr = null;
        Iterator iterDtl = null;
        Iterator iterDtlCONT = null;
        String query = null;

//        element header
        String KD_DOK = null;
        String KD_TPS = null;
        String NO_VOY_FLIGHT = null;
        String CALL_SIGN = null;
        String KD_GUDANG = null;
        String ETA = null;
        String ETD = null;
        String OWNER = null;
        String VESSEL_CODE = null;
        String VESSEL_NAME = null;
        String REF_NUMBER = null;

//        element detil cont
        String NO_CONT = null;
        String UK_CONT = null;
        String ISO_CODE = null;
        String NO_SEGEL = null;
        String BRUTO = null;
        String LOC_IN_VESSEL = null;
        String LOC_IN_YARD = null;
        String WK_INOUT = null;
        String TRUCKER_NAME = null;
        String NO_POL = null;
        String PEL_MUAT = null;
        String PEL_TRANSIT = null;
        String PEL_BONGKAR = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;
        String BOOKING_NO = null;

//        tambahan elemen detil cont
        String NO_MASTER_BL_AWB = null;
        String TGL_MASTER_BL_AWB = null;
        String ID_CONSIGNEE = null;
        String CONSIGNEE = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_POS_BC11 = null;
        String KD_DOK_INOUT = null;
        String NO_DOK_INOUT = null;
        String TGL_DOK_INOUT = null;
        String FL_CONT_KOSONG = null;
        String TEMPERATURE = null;
        String SHIPPER = null;
        String STAT_CONT = null;
        String REMARKS = null;
        String PORT_ORIGIN = null;
        String PORT_FINAL_DESTINATION = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int ID = 0;
        int a = 0;
        boolean condition = false;
        boolean result = false;
        Log log = null;
        try {
            log = new Log();
            mydb = new Db("SHL.properties");

            SAXBuilder builder = new SAXBuilder();
            if (fStream != null) {
                try {
                    fStream = fStream.replace("xmlns=\"cococont.xsd\"", "");
                    fStream = fStream.replaceAll(" /", "/");
                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("COCOCONT");
                    for (int i = 0; i < list.size(); i++) {
                        ListRes = (Element) list.get(i);
                        List header = ListRes.getChildren("HEADER");
                        for (int j = 0; j < header.size(); j++) {
                            ListHdr = (Element) header.get(j);
                            try {
                                KD_DOK = ListHdr.getChildText("KD_DOK");
                                if (KD_DOK == null) {
                                    KD_DOK = "";
                                }
                            } catch (Exception e) {
                                KD_DOK = "";
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
                                NO_VOY_FLIGHT = ListHdr.getChildText("NO_VOY_FLIGHT");
                                if (NO_VOY_FLIGHT == null) {
                                    NO_VOY_FLIGHT = "";
                                }
                            } catch (Exception e) {
                                NO_VOY_FLIGHT = "";
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
                                KD_GUDANG = ListHdr.getChildText("KD_GUDANG");
                                if (KD_GUDANG == null) {
                                    KD_GUDANG = "";
                                }
                            } catch (Exception e) {
                                KD_GUDANG = "";
                            }
                            try {
                                ETA = ListHdr.getChildText("ETA");
                                if (ETA == null) {
                                    ETA = null;
                                } else {
                                    ETA = "TO_DATE('" + ETA + "','YYYYMMDDHH24MISS')";
                                }
                            } catch (Exception e) {
                                ETA = null;
                            }
                            try {
                                ETD = ListHdr.getChildText("ETD");
                                if (ETD == null) {
                                    ETD = null;
                                } else {
                                    ETD = "TO_DATE('" + ETD + "','YYYYMMDDHH24MISS')";
                                }
                            } catch (Exception e) {
                                ETD = null;
                            }
                            try {
                                VESSEL_CODE = ListHdr.getChildText("VESSEL_CODE");
                                if (VESSEL_CODE == null) {
                                    VESSEL_CODE = "";
                                }
                            } catch (Exception e) {
                                VESSEL_CODE = "";
                            }
                            try {
                                VESSEL_NAME = ListHdr.getChildText("VESSEL_NAME").replace("'", "");
                                if (VESSEL_NAME == null) {
                                    VESSEL_NAME = "";
                                }
                            } catch (Exception e) {
                                VESSEL_NAME = "";
                            }
                            query = "SELECT ID FROM COCOHDR WHERE "
                                    + "VESSEL_CODE = ? AND "
                                    + "VESSEL_NAME = ? AND "
                                    + "VOYAGE_NO = ? AND "
                                    + "ETA = ? AND "
                                    + "ETD = ? ";

                            preparedStatement = mydb.preparedstmt(query);
                            preparedStatement.setString(1, VESSEL_CODE);
                            preparedStatement.setString(2, VESSEL_NAME);
                            preparedStatement.setString(3, NO_VOY_FLIGHT);
                            preparedStatement.setString(4, ETA);
                            preparedStatement.setString(5, ETD);
                            rs = preparedStatement.executeQuery();

                            if (rs.next()) {
                                ID = rs.getInt("ID");
                                condition = mydb.getProcessValue();
                            } else {
                                mydb.execute("SELECT SEQ_COCOHDR.NEXTVAL as ID FROM DUAL");
                                if (mydb.next()) {
                                    ID = mydb.getInt("ID");
                                }
                                query = "INSERT INTO COCOHDR(ID,KD_TPS,KD_GUDANG,VESSEL_CODE,VESSEL_NAME,"
                                        + "VOYAGE_NO,CALL_SIGN,ETA,ETD,WK_REKAM)"
                                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setInt(1, ID);
                                preparedStatement.setString(2, KD_TPS);
                                preparedStatement.setString(3, KD_GUDANG);
                                preparedStatement.setString(4, VESSEL_CODE);
                                preparedStatement.setString(5, VESSEL_NAME);
                                preparedStatement.setString(6, NO_VOY_FLIGHT);
                                preparedStatement.setString(7, CALL_SIGN);
                                preparedStatement.setString(8, ETA);
                                preparedStatement.setString(9, ETD);
                                preparedStatement.setDate(10, getCurrentTimeStamp());
                                preparedStatement.executeUpdate();
                                condition = mydb.getProcessValue();
                                mydb.execute("commit");
                            }
                            List detil = ListRes.getChildren("DETIL");
                            for (int k = 0; k < detil.size(); k++) {
                                ListDtl = (Element) detil.get(k);
                                List cont = ListDtl.getChildren("CONT");
                                for (int l = 0; l < cont.size(); l++) {
                                    ListDtlCONT = (Element) cont.get(l);
                                    try {
                                        KD_DOK = ListDtlCONT.getChildText("KD_DOK");
                                        if (KD_DOK == null) {
                                            KD_DOK = "";
                                        }
                                    } catch (Exception e) {
                                        KD_DOK = "";
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
                                        UK_CONT = ListDtlCONT.getChildText("UK_CONT");
                                        if (UK_CONT == null) {
                                            UK_CONT = "";
                                        }
                                    } catch (Exception e) {
                                        UK_CONT = "";
                                    }
                                    try {
                                        ISO_CODE = ListDtlCONT.getChildText("ISO_CODE");
                                        if (ISO_CODE == null) {
                                            ISO_CODE = "";
                                        }
                                    } catch (Exception e) {
                                        ISO_CODE = "";
                                    }
                                    try {
                                        NO_SEGEL = ListDtlCONT.getChildText("NO_SEGEL");
                                        if (NO_SEGEL == null) {
                                            NO_SEGEL = "";
                                        }
                                    } catch (Exception e) {
                                        NO_SEGEL = "";
                                    }
                                    try {
                                        BRUTO = ListDtlCONT.getChildText("BRUTO");
                                        if (BRUTO == null) {
                                            BRUTO = "";
                                        }
                                    } catch (Exception e) {
                                        BRUTO = "";
                                    }
                                    try {
                                        OWNER = ListDtlCONT.getChildText("OWNER").replace("'", "");
                                        if (OWNER == null) {
                                            OWNER = "";
                                        }
                                    } catch (Exception e) {
                                        OWNER = "";
                                    }
                                    try {
                                        LOC_IN_VESSEL = ListDtlCONT.getChildText("LOC_IN_VESSEL");
                                        if (LOC_IN_VESSEL == null) {
                                            LOC_IN_VESSEL = "";
                                        }
                                    } catch (Exception e) {
                                        LOC_IN_VESSEL = "";
                                    }
                                    try {
                                        LOC_IN_YARD = ListDtlCONT.getChildText("LOC_IN_YARD");
                                        if (LOC_IN_YARD == null) {
                                            LOC_IN_YARD = "";
                                        }
                                    } catch (Exception e) {
                                        LOC_IN_YARD = "";
                                    }
                                    try {
                                        LOC_IN_YARD = ListDtlCONT.getChildText("LOC_IN_YARD");
                                        if (LOC_IN_YARD == null) {
                                            LOC_IN_YARD = "";
                                        }
                                    } catch (Exception e) {
                                        LOC_IN_YARD = "";
                                    }
                                    try {
                                        WK_INOUT = ListDtlCONT.getChildText("WK_INOUT");
                                        if (WK_INOUT == null) {
                                            WK_INOUT = null;
                                        } else {
                                            WK_INOUT = "TO_DATE('" + WK_INOUT + "','YYYYMMDDHH24MISS')";
                                        }
                                    } catch (Exception e) {
                                        WK_INOUT = null;
                                    }
                                    try {
                                        TRUCKER_NAME = ListDtlCONT.getChildText("TRUCKER_NAME").replace("'", "");
                                        if (TRUCKER_NAME == null) {
                                            TRUCKER_NAME = "";
                                        }
                                    } catch (Exception e) {
                                        TRUCKER_NAME = "";
                                    }
                                    try {
                                        NO_POL = ListDtlCONT.getChildText("NO_POL");
                                        if (NO_POL == null) {
                                            NO_POL = "";
                                        }
                                    } catch (Exception e) {
                                        NO_POL = "";
                                    }
                                    try {
                                        REF_NUMBER = ListDtlCONT.getChildText("REF_NUMBER");
                                        if (REF_NUMBER == null) {
                                            REF_NUMBER = "";
                                        }
                                    } catch (Exception e) {
                                        REF_NUMBER = "";
                                    }
                                    try {
                                        PEL_MUAT = ListDtlCONT.getChildText("POL");
                                        if (PEL_MUAT == null) {
                                            PEL_MUAT = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_MUAT = "";
                                    }
                                    try {
                                        PEL_TRANSIT = ListDtlCONT.getChildText("POT");
                                        if (PEL_TRANSIT == null) {
                                            PEL_TRANSIT = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_TRANSIT = "";
                                    }
                                    try {
                                        PEL_BONGKAR = ListDtlCONT.getChildText("POD");
                                        if (PEL_BONGKAR == null) {
                                            PEL_BONGKAR = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_BONGKAR = "";
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
                                            TGL_BL_AWB = null;
                                        } else {
                                            TGL_BL_AWB = "TO_DATE('" + TGL_BL_AWB + "'," + "'YYYYMMDD')";
                                        }
                                    } catch (Exception e) {
                                        TGL_BL_AWB = null;
                                    }
                                    try {
                                        BOOKING_NO = ListDtlCONT.getChildText("BOOKING_NO");
                                        if (BOOKING_NO == null) {
                                            BOOKING_NO = "";
                                        }
                                    } catch (Exception e) {
                                        BOOKING_NO = "";
                                    }
                                    try {
                                        NO_MASTER_BL_AWB = ListDtlCONT.getChildText("NO_MASTER_BL_AWB");
                                        if (NO_MASTER_BL_AWB == null) {
                                            NO_MASTER_BL_AWB = "";
                                        }
                                    } catch (Exception e) {
                                        NO_MASTER_BL_AWB = "";
                                    }
                                    try {
                                        TGL_MASTER_BL_AWB = ListDtlCONT.getChildText("TGL_MASTER_BL_AWB");
                                        if (TGL_MASTER_BL_AWB == null) {
                                            TGL_MASTER_BL_AWB = null;
                                        } else {
                                            TGL_MASTER_BL_AWB = "TO_DATE('" + TGL_MASTER_BL_AWB + "','YYYYMMDD')";
                                        }
                                    } catch (Exception e) {
                                        TGL_MASTER_BL_AWB = null;
                                    }
                                    try {
                                        ID_CONSIGNEE = ListDtlCONT.getChildText("ID_CONSIGNEE");
                                        if (ID_CONSIGNEE == null) {
                                            ID_CONSIGNEE = "";
                                        }
                                    } catch (Exception e) {
                                        ID_CONSIGNEE = "";
                                    }
                                    try {
                                        CONSIGNEE = ListDtlCONT.getChildText("CONSIGNEE").replace("'", "");
                                        if (CONSIGNEE == null) {
                                            CONSIGNEE = "";
                                        }
                                    } catch (Exception e) {
                                        CONSIGNEE = "";
                                    }
                                    try {
                                        NO_BC11 = ListDtlCONT.getChildText("NO_BC11");
                                        if (NO_BC11 == null) {
                                            NO_BC11 = "";
                                        }
                                    } catch (Exception e) {
                                        NO_BC11 = "";
                                    }
                                    try {
                                        TGL_BC11 = ListDtlCONT.getChildText("TGL_BC11");
                                        if (TGL_BC11 == null) {
                                            TGL_BC11 = null;
                                        } else {
                                            TGL_BC11 = "TO_DATE('" + TGL_BC11 + "','YYYYMMDD')";
                                        }
                                    } catch (Exception e) {
                                        TGL_BC11 = null;
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
                                        KD_DOK_INOUT = ListDtlCONT.getChildText("KD_DOK_INOUT");
                                        if (KD_DOK_INOUT == null) {
                                            KD_DOK_INOUT = "";
                                        }
                                    } catch (Exception e) {
                                        KD_DOK_INOUT = "";
                                    }
                                    try {
                                        NO_DOK_INOUT = ListDtlCONT.getChildText("NO_DOK_INOUT");
                                        if (NO_DOK_INOUT == null) {
                                            NO_DOK_INOUT = "";
                                        }
                                    } catch (Exception e) {
                                        NO_DOK_INOUT = "";
                                    }
                                    try {
                                        TGL_DOK_INOUT = ListDtlCONT.getChildText("TGL_DOK_INOUT");
                                        if (TGL_DOK_INOUT == null) {
                                            TGL_DOK_INOUT = null;
                                        } else {
                                            TGL_DOK_INOUT = "TO_DATE('" + TGL_DOK_INOUT + "','YYYyMMDDHH24MISS')";
                                        }
                                    } catch (Exception e) {
                                        TGL_DOK_INOUT = null;
                                    }
                                    try {
                                        FL_CONT_KOSONG = ListDtlCONT.getChildText("FL_CONT_KOSONG");
                                        if (FL_CONT_KOSONG == null) {
                                            FL_CONT_KOSONG = "";
                                        }
                                    } catch (Exception e) {
                                        FL_CONT_KOSONG = "";
                                    }
                                    try {
                                        TEMPERATURE = ListDtlCONT.getChildText("TEMPERATURE");
                                        if (TEMPERATURE == null) {
                                            TEMPERATURE = "";
                                        }
                                    } catch (Exception e) {
                                        TEMPERATURE = "";
                                    }
                                    try {
                                        SHIPPER = ListDtlCONT.getChildText("SHIPPER").replace("'", "");
                                        if (SHIPPER == null) {
                                            SHIPPER = "";
                                        }
                                    } catch (Exception e) {
                                        SHIPPER = "";
                                    }
                                    try {
                                        STAT_CONT = ListDtlCONT.getChildText("STAT_CONT");
                                        if (STAT_CONT == null) {
                                            STAT_CONT = "";
                                        }
                                    } catch (Exception e) {
                                        STAT_CONT = "";
                                    }
                                    try {
                                        REMARKS = ListDtlCONT.getChildText("REMARKS");
                                        if (REMARKS == null) {
                                            REMARKS = "";
                                        }
                                    } catch (Exception e) {
                                        REMARKS = "";
                                    }
                                    try {
                                        PORT_ORIGIN = ListDtlCONT.getChildText("PORT_ORIGIN");
                                        if (PORT_ORIGIN == null) {
                                            PORT_ORIGIN = "";
                                        }
                                    } catch (Exception e) {
                                        PORT_ORIGIN = "";
                                    }
                                    try {
                                        PORT_FINAL_DESTINATION = ListDtlCONT.getChildText("PORT_FINAL_DESTINATION");
                                        if (PORT_FINAL_DESTINATION == null) {
                                            PORT_FINAL_DESTINATION = "";
                                        }
                                    } catch (Exception e) {
                                        PORT_FINAL_DESTINATION = "";
                                    }
                                    try {
                                        if (condition) {
                                            query = "INSERT INTO COCOCONT(ID,KD_DOK,NO_CONT,UK_CONT,"
                                                    + "OWNER,ISO_CODE,NO_SEGEL,BRUTO,LOC_IN_VESSEL,"
                                                    + "LOC_IN_YARD,WK_INOUT,TRUCKER_NAME,NO_POL,"
                                                    + "POL,POT,POD,REF_NUMBER,NO_BL_AWB,TGL_BL_AWB,"
                                                    + "NO_BOOKING,"
                                                    + "NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                                                    + "ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,"
                                                    + "KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
                                                    + "FL_CONT_KOSONG,TEMPERATURE,SHIPPER,STAT_CONT,REMARKS,"
                                                    + "WK_REKAM,PORT_ORIGIN,PORT_FINAL_DESTINATION) "
                                                    + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                                                    + "?,?,?,?,?,?,?,?,?,?,"
                                                    + "?,?,?,?,?,?,?,?,?,?,"
                                                    + "?,?,?,?,"
                                                    + "?,?,?,?)";

                                            preparedStatement = mydb.preparedstmt(query);
                                            preparedStatement.setInt(1, ID);
                                            preparedStatement.setString(2, KD_DOK);
                                            preparedStatement.setString(3, NO_CONT);
                                            preparedStatement.setString(4, UK_CONT);
                                            preparedStatement.setString(5, OWNER);
                                            preparedStatement.setString(6, ISO_CODE);
                                            preparedStatement.setString(7, NO_SEGEL);
                                            preparedStatement.setString(8, BRUTO);
                                            preparedStatement.setString(9, LOC_IN_VESSEL);
                                            preparedStatement.setString(10, LOC_IN_YARD);
                                            preparedStatement.setString(11, WK_INOUT);
                                            preparedStatement.setString(12, TRUCKER_NAME);
                                            preparedStatement.setString(13, NO_POL);
                                            preparedStatement.setString(14, PEL_MUAT);
                                            preparedStatement.setString(15, PEL_TRANSIT);
                                            preparedStatement.setString(16, PEL_BONGKAR);
                                            preparedStatement.setString(17, REF_NUMBER);
                                            preparedStatement.setString(18, NO_BL_AWB);
                                            preparedStatement.setString(19, TGL_BL_AWB);
                                            preparedStatement.setString(20, BOOKING_NO);
                                            preparedStatement.setString(21, NO_MASTER_BL_AWB);
                                            preparedStatement.setString(22, TGL_MASTER_BL_AWB);
                                            preparedStatement.setString(23, ID_CONSIGNEE);
                                            preparedStatement.setString(24, CONSIGNEE);
                                            preparedStatement.setString(25, NO_BC11);
                                            preparedStatement.setString(26, TGL_BC11);
                                            preparedStatement.setString(27, NO_POS_BC11);
                                            preparedStatement.setString(28, KD_DOK_INOUT);
                                            preparedStatement.setString(29, NO_DOK_INOUT);
                                            preparedStatement.setString(30, TGL_DOK_INOUT);
                                            preparedStatement.setString(31, FL_CONT_KOSONG);
                                            preparedStatement.setString(32, TEMPERATURE);
                                            preparedStatement.setString(33, SHIPPER);
                                            preparedStatement.setString(34, STAT_CONT);
                                            preparedStatement.setString(35, REMARKS);
                                            preparedStatement.setDate(36, getCurrentTimeStamp());
                                            preparedStatement.setString(37, PORT_ORIGIN);
                                            preparedStatement.setString(38, PORT_FINAL_DESTINATION);

                                            preparedStatement.executeUpdate();
                                            System.out.println(query);
                                            condition = mydb.getProcessValue();
                                            mydb.execute("commit");
                                        }
                                    } catch (Exception e) {
                                         exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLCoarriCodecoSHL baris 610", String.valueOf(result));
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    if (condition) {
                        mydb.execute("commit");
                        mydb.close();
                        result = true;
                        log.logSucces("COARRI_CODECO_SHL", "INSERT DB", "", "SUCCESS", REF_NUMBER);
                    } else {
                        mydb.execute("rollback");
                        mydb.close();
                        result = false;
                        log.logError("COARRI_CODECO_SHL", "INSERT DB", query.replace("'", ""), "NOT SUCCESS", REF_NUMBER);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.logError("COARRI_CODECO_SHL", "INTERASI XML", query, e.getMessage(), REF_NUMBER);
                }
            }
        } catch (Exception ex) {
            log.logError("COARRI_CODECO_SHL", "KONEKSI DB", query, ex.getMessage(), REF_NUMBER);
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
                rs = null;
                preparedStatement = null;
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
