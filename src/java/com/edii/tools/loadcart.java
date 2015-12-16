/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class loadcart {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocument(String fStream) throws Exception {
        Db mydb = null;
        Document doc = null;
        Element List = null;;
        Element ListRes = null;
        Element ListHdr = null;
        Element ListDtl = null;
        Element ListDtlCAR = null;
        Iterator iter = null;
        Iterator iterRes = null;
        Iterator iterHdr = null;
        Iterator iterDtl = null;
        Iterator iterDtlCAR = null;
        String query = null;

        //Element Header
        String KD_DOK = null;
        String KD_TPS = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String CALL_SIGN = null;
        String TGL_TIBA = null;
        String KD_GUDANG = null;
        String REF_NUMBER = null;

        //Element
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;
        String ID_CONSIGNEE = null;
        String CONSIGNEE = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_POS_BC11 = null;
        String VIN_NUMBER = null;
        String NO_RANGKA = null;
        String NO_MESIN = null;
        String TIPE = null;
        String WARNA = null;
        String MERK = null;
        String BRUTO = null;
        String KD_TIMBUN = null;
        String KD_DOK_INOUT = null;
        String NO_DOK_INOUT = null;
        String TGL_DOK_INOUT = null;
        String WK_INOUT = null;
        String KD_SAR_ANGKUT_INOUT = null;
        String NO_POL = null;
        String PEL_MUAT = null;
        String PEL_TRANSIT = null;
        String PEL_BONGKAR = null;
        String GUDANG_TUJUAN = null;

        int ID = 0;
        String result = null;
        boolean condition = false;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            mydb = new Db();
            SAXBuilder builder = new SAXBuilder();

            if (fStream != null) {
                try {
                    fStream = fStream.replace("xmlns=\"cococar.xsd\"", "");
                    doc = builder.build(new StringReader(fStream));
                    Element rootNode = doc.getRootElement();
                    List list = rootNode.getChildren("RESPON_BATAL");
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
                                CALL_SIGN = ListHdr.getChildText("CALL_SIGN");
                                if (CALL_SIGN == null) {
                                    CALL_SIGN = "";
                                }
                            } catch (Exception e) {
                                CALL_SIGN = "";
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
                                KD_GUDANG = ListHdr.getChildText("KD_GUDANG");
                                if (KD_GUDANG == null) {
                                    KD_GUDANG = "";
                                }
                            } catch (Exception e) {
                                KD_GUDANG = "";
                            }
                            try {
                                REF_NUMBER = ListHdr.getChildText("REF_NUMBER");
                                if (REF_NUMBER == null) {
                                    REF_NUMBER = "";
                                }
                            } catch (Exception e) {
                                REF_NUMBER = "";
                            }

                            mydb.execute("SELECT COARRI_CODECO_SEQ.NEXTVAL as ID FROM DUAL");
                            if (mydb.next()) {
                                ID = mydb.getInt("ID");
                            }
                            try {
                                query = "INSERT INTO COCOHDR(ID,KD_DOK,KD_TPS,NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER,RECEIVED_DATE)"
                                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";

                                preparedStatement = mydb.preparedstmt(query);
                                preparedStatement.setInt(1, ID);
                                preparedStatement.setString(2, KD_DOK);
                                preparedStatement.setString(3, KD_TPS);
                                preparedStatement.setString(4, NM_ANGKUT);
                                preparedStatement.setString(5, NO_VOY_FLIGHT);
                                preparedStatement.setString(6, CALL_SIGN);
                                preparedStatement.setDate(7, java.sql.Date.valueOf(TGL_TIBA));
                                preparedStatement.setString(8, KD_GUDANG);
                                preparedStatement.setString(9, REF_NUMBER);
                                preparedStatement.setDate(10, getCurrentTimeStamp());

                                preparedStatement.executeUpdate();
                                //mydb.execute("commit");
                                condition = mydb.getProcessValue();
                                //mydb.execute("commit");
                            } catch (Exception e) {
                                result = e.getMessage();
                                e.printStackTrace();
                                try {
                                    query = "INSERT INTO LOG_ERROR (DATE_INSERT,ERROR,NAMA_CLASS) "
                                            + "VALUES (?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setDate(1, getCurrentTimeStamp());
                                    preparedStatement.setString(2, result);
                                    preparedStatement.setString(3, "ParsingXMLCococaret");

                                    preparedStatement.executeUpdate();
                                    mydb.execute("commit");
                                } catch (Exception ex) {
                                    exc.ExcuteError(e.getMessage(), "execute_class_loadcart", result);

                                }
                            }
                        }
                        List detil = ListRes.getChildren("DETIL");
                        for (int k = 0; k < detil.size(); k++) {
                            ListDtl = (Element) detil.get(k);
                            List car = ListDtl.getChildren("CAR");
                            for (int l = 0; l < car.size(); l++) {
                                ListDtlCAR = (Element) car.get(l);

                                try {
                                    NO_BL_AWB = ListDtlCAR.getChildText("NO_BL_AWB");
                                    if (NO_BL_AWB == null) {
                                        NO_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    NO_BL_AWB = "";
                                }
                                try {
                                    TGL_BL_AWB = ListDtlCAR.getChildText("TGL_BL_AWB");
                                    if (TGL_BL_AWB == null) {
                                        TGL_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    TGL_BL_AWB = "";
                                }
                                try {
                                    ID_CONSIGNEE = ListDtlCAR.getChildText("ID_CONSIGNEE");
                                    if (ID_CONSIGNEE == null) {
                                        ID_CONSIGNEE = "";
                                    } else {
                                        ID_CONSIGNEE = ID_CONSIGNEE.replaceAll("[.-]", "");
                                    }
                                } catch (Exception e) {
                                    ID_CONSIGNEE = "";
                                }
                                try {
                                    CONSIGNEE = ListDtlCAR.getChildText("CONSIGNEE");
                                    if (CONSIGNEE == null) {
                                        CONSIGNEE = "";
                                    }
                                } catch (Exception e) {
                                    CONSIGNEE = "";
                                }
                                try {
                                    NO_BC11 = ListDtlCAR.getChildText("NO_BC11");
                                    if (NO_BC11 == null) {
                                        NO_BC11 = "";
                                    } else {
                                        NO_BC11 = NO_BC11.replaceAll(" ", "");
                                    }
                                } catch (Exception e) {
                                    NO_BC11 = "";
                                }
                                try {
                                    TGL_BC11 = ListDtlCAR.getChildText("TGL_BC11");
                                    if (TGL_BC11 == null) {
                                        TGL_BC11 = "";
                                    }
                                } catch (Exception e) {
                                    TGL_BC11 = "";
                                }
                                try {
                                    NO_POS_BC11 = ListDtlCAR.getChildText("NO_POS_BC11");
                                    if (NO_POS_BC11 == null) {
                                        NO_POS_BC11 = "";
                                    }
                                } catch (Exception e) {
                                    NO_POS_BC11 = "";
                                }
                                try {
                                    VIN_NUMBER = ListDtlCAR.getChildText("VIN_NUMBER");
                                    if (VIN_NUMBER == null) {
                                        VIN_NUMBER = "";
                                    }
                                } catch (Exception e) {
                                    VIN_NUMBER = "";
                                }
                                try {
                                    NO_RANGKA = ListDtlCAR.getChildText("NO_RANGKA");
                                    if (NO_RANGKA == null) {
                                        NO_RANGKA = "";
                                    }
                                } catch (Exception e) {
                                    NO_RANGKA = "";
                                }
                                try {
                                    NO_MESIN = ListDtlCAR.getChildText("NO_MESIN");
                                    if (NO_MESIN == null) {
                                        NO_MESIN = "";
                                    }
                                } catch (Exception e) {
                                    NO_MESIN = "";
                                }
                                try {
                                    TIPE = ListDtlCAR.getChildText("TIPE");
                                    if (TIPE == null) {
                                        TIPE = "";
                                    }
                                } catch (Exception e) {
                                    TIPE = "";
                                }
                                try {
                                    WARNA = ListDtlCAR.getChildText("WARNA");
                                    if (WARNA == null) {
                                        WARNA = "";
                                    }
                                } catch (Exception e) {
                                    WARNA = "";
                                }
                                try {
                                    MERK = ListDtlCAR.getChildText("MERK");
                                    if (MERK == null) {
                                        MERK = "";
                                    }
                                } catch (Exception e) {
                                    MERK = "";
                                }
                                try {
                                    BRUTO = ListDtlCAR.getChildText("BRUTO");
                                    if (BRUTO == null) {
                                        BRUTO = "";
                                    }
                                } catch (Exception e) {
                                    BRUTO = "";
                                }
                                try {
                                    KD_TIMBUN = ListDtlCAR.getChildText("KD_TIMBUN");
                                    if (KD_TIMBUN == null) {
                                        KD_TIMBUN = "";
                                    }
                                } catch (Exception e) {
                                    KD_TIMBUN = "";
                                }
                                try {
                                    KD_DOK_INOUT = ListDtlCAR.getChildText("KD_DOK_INOUT");
                                    if (KD_DOK_INOUT == null) {
                                        KD_DOK_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    KD_DOK_INOUT = "";
                                }
                                try {
                                    NO_DOK_INOUT = ListDtlCAR.getChildText("NO_DOK_INOUT");
                                    if (NO_DOK_INOUT == null) {
                                        NO_DOK_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    NO_DOK_INOUT = "";
                                }
                                try {
                                    TGL_DOK_INOUT = ListDtlCAR.getChildText("TGL_DOK_INOUT");
                                    if (TGL_DOK_INOUT == null) {
                                        TGL_DOK_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    TGL_DOK_INOUT = "";
                                }
                                try {
                                    WK_INOUT = ListDtlCAR.getChildText("WK_INOUT");
                                    if (WK_INOUT == null) {
                                        WK_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    WK_INOUT = "";
                                }
                                try {
                                    KD_SAR_ANGKUT_INOUT = ListDtlCAR.getChildText("KD_SAR_ANGKUT_INOUT");
                                    if (KD_SAR_ANGKUT_INOUT == null) {
                                        KD_SAR_ANGKUT_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    KD_SAR_ANGKUT_INOUT = "";
                                }
                                try {
                                    NO_POL = ListDtlCAR.getChildText("NO_POL");
                                    if (NO_POL == null) {
                                        NO_POL = "";
                                    }
                                } catch (Exception e) {
                                    NO_POL = "";
                                }
                                try {
                                    PEL_MUAT = ListDtlCAR.getChildText("PEL_MUAT");
                                    if (PEL_MUAT == null) {
                                        PEL_MUAT = "";
                                    }
                                } catch (Exception e) {
                                    PEL_MUAT = "";
                                }
                                try {
                                    PEL_TRANSIT = ListDtlCAR.getChildText("PEL_TRANSIT");
                                    if (PEL_TRANSIT == null) {
                                        PEL_TRANSIT = "";
                                    }
                                } catch (Exception e) {
                                    PEL_TRANSIT = "";
                                }
                                try {
                                    PEL_BONGKAR = ListDtlCAR.getChildText("PEL_BONGKAR");
                                    if (PEL_BONGKAR == null) {
                                        PEL_BONGKAR = "";
                                    }
                                } catch (Exception e) {
                                    PEL_BONGKAR = "";
                                }
                                try {
                                    GUDANG_TUJUAN = ListDtlCAR.getChildText("GUDANG_TUJUAN");
                                    if (GUDANG_TUJUAN == null) {
                                        GUDANG_TUJUAN = "";
                                    }
                                } catch (Exception e) {
                                    GUDANG_TUJUAN = "";
                                }
                                try {

                                    query = "INSERT INTO COCOCARTER(ID,NO_BL_AWB,TGL_BL_AWB,ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,VIN_NUMBER,"
                                            + "NO_RANGKA,NO_MESIN,TIPE,WARNA,MERK,BRUTO,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,"
                                            + "KD_SAR_ANGKUT_INOUT,NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,GUDANG_TUJUAN)"
                                            + "VALUES (?,?,?,?,?,?,?,?,?,?,"
                                            + "?,?,?,?,?,?,?,?,?,?,"
                                            + "?,?,?,?,?,?)";

                                    preparedStatement = mydb.preparedstmt(query);
                                    preparedStatement.setInt(1, ID);
                                    preparedStatement.setString(2, NO_BL_AWB);
                                    preparedStatement.setDate(3, java.sql.Date.valueOf(TGL_BL_AWB));
                                    preparedStatement.setString(4, ID_CONSIGNEE);
                                    preparedStatement.setString(5, CONSIGNEE);
                                    preparedStatement.setString(6, NO_BC11);
                                    preparedStatement.setDate(7, java.sql.Date.valueOf(TGL_BC11));
                                    preparedStatement.setString(8, NO_POS_BC11);
                                    preparedStatement.setString(9, VIN_NUMBER);
                                    preparedStatement.setString(10, NO_RANGKA);
                                    preparedStatement.setString(11, NO_MESIN);
                                    preparedStatement.setString(12, TIPE);
                                    preparedStatement.setString(13, WARNA);
                                    preparedStatement.setString(14, MERK);
                                    preparedStatement.setString(15, BRUTO);
                                    preparedStatement.setString(16, KD_TIMBUN);
                                    preparedStatement.setString(17, KD_DOK_INOUT);
                                    preparedStatement.setString(18, NO_DOK_INOUT);
                                    preparedStatement.setDate(19, java.sql.Date.valueOf(TGL_DOK_INOUT));
                                    preparedStatement.setDate(20, java.sql.Date.valueOf(WK_INOUT));
                                    preparedStatement.setString(21, KD_SAR_ANGKUT_INOUT);
                                    preparedStatement.setString(22, NO_POL);
                                    preparedStatement.setString(23, PEL_MUAT);
                                    preparedStatement.setString(24, PEL_TRANSIT);
                                    preparedStatement.setString(25, PEL_BONGKAR);
                                    preparedStatement.setString(26, GUDANG_TUJUAN);
                                    preparedStatement.executeUpdate();
                                    //mydb.execute("commit");
                                    condition = mydb.getProcessValue();
                                    //result = "Insert Success";
                                } catch (Exception e) {
                                    result = e.getMessage();
                                    //e.printStackTrace();
                                    exc.ExcuteError(e.getMessage(), "execute_class_loadcart", result);

                                    System.out.println("result   : " + result);
                                    try {
                                        query = "INSERT INTO LOG_ERROR (DATE_INSERT,ERROR,NAMA_CLASS) "
                                                + "VALUES (?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setDate(1, getCurrentTimeStamp());
                                        preparedStatement.setString(2, result);
                                        preparedStatement.setString(3, "ParsingXMLCococaret");

                                        preparedStatement.executeUpdate();
                                        mydb.execute("commit");

                                    } catch (Exception ex) {
                                        exc.ExcuteError(e.getMessage(), "execute_class_loadcart", result);

                                    }
                                }
                            }
                        }

                    }

                    if (condition) {
                        mydb.execute("commit");
                        result = "Insert Success";
                    } else {
                        mydb.execute("rollback");
                        result = "gagal " + REF_NUMBER;
                    }

                } catch (Exception e) {
                    exc.ExcuteError(e.getMessage(), "execute_class_loadcart", result);

                    result = e.getMessage();
                    //e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            exc.ExcuteError(ex.getMessage(), "execute_class_loadcart", result);

            result = ex.getMessage();
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

    public static void main(String[] args) {
        File inFile = null;
        BufferedReader input = null;
        String content = "";
        String content_final = "";
        String[] file = null;
        String filename = null;
        MoveFile mv = null;
        BufferedReader br = null;
        FileReader fReader = null;
        try {
            mv = new MoveFile();
            File folder = new File("D:/");
            file = folder.list();
            for (int j = 0; j < file.length; j++) {
                filename = file[j];
                content = "";
                content_final = "";

                inFile = new File(folder + File.separator + filename);
                if (inFile.isFile()) {
                    fReader = new FileReader(inFile);
                    br = new BufferedReader(fReader);
                    while ((content = br.readLine()) != null) {
                        content_final += content;
                    }

                    try {
                        br.close();
                    } catch (Exception e) {
                    } finally {
                        br = null;
                    }
                    try {
                        fReader.close();
                    } catch (Exception e) {
                    } finally {
                        fReader = null;
                    }

                    ParsingXMLCocoCarTer l = new ParsingXMLCocoCarTer();
                    String r = l.parseDocument(content_final);
                    System.out.println("hasilllllllllllll:::::::::" + r);
                    if (r.contains("Success")) {
                        //Move File to Error
                        mv.move(folder + File.separator + filename, folder + File.separator + "History" + File.separator + filename);
                    } else {
                        mv.move(folder + File.separator + filename, folder + File.separator + "gagal" + File.separator + filename);
                    }
                }
            }
        } catch (Exception e) {
        }

    }

}
