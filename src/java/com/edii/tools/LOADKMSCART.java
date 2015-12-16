/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author Aslichatun Nisa
 */
public class LOADKMSCART {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocument(String fStream, String username) throws Exception {
        Db mydb = null;
        Document doc = null;
        Element List = null;;
        Element ListRes = null;
        Element ListHdr = null;
        Element ListDtl = null;
        Element ListDtlKMS = null;
        Iterator iter = null;
        Iterator iterRes = null;
        Iterator iterHdr = null;
        Iterator iterDtl = null;
        Iterator iterDtlKMS = null;
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

        //element detil kms
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;
        String NO_MASTER_BL_AWB = null;
        String TGL_MASTER_BL_AWB = null;
        String ID_CONSIGNEE = null;
        String CONSIGNEE = null;
        String BRUTO = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_POS_BC11 = null;
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
        String KODE_KANTOR = null;
        String NO_DAFTAR_PABEAN = null;
        String TGL_DAFTAR_PABEAN = null;
        String NO_SEGEL_BC = null;
        String TGL_SEGEL_BC = null;
        String NO_IJIN_TPS = null;
        String TGL_IJIN_TPS = null;
        String CONT_ASAL = null;
        String SERI_KEMAS = null;
        String KD_KEMAS = null;
        String JML_KEMAS = null;
        String year = null;
        int ID = 0;
        String result = null;
        validasi_tanggal val = null;
        boolean cekTgl = false;
        boolean cekTglJam = false;
        boolean condition = false;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            val = new validasi_tanggal();
            mydb = new Db();
            mydb.execute("select to_char(sysdate,'yyyy') as tahun from dual");
            if (mydb.next()) {
                year = mydb.getString("tahun");
            }
            if (fStream != null) {
                try {
                    fStream = fStream.replace("xmlns=\"cocokms.xsd\"", "");
                    fStream = fStream.replaceAll(" /", "/");
                    doc = DocumentHelper.parseText(fStream);
                    for (iter = doc.selectNodes("DOCUMENT").iterator(); iter.hasNext();) {
                        List = (Element) iter.next();
                        for (iterRes = List.selectNodes("COCOKMS").iterator(); iterRes.hasNext();) {
                            ListRes = (Element) iterRes.next();
                            for (iterHdr = ListRes.selectNodes("HEADER").iterator(); iterHdr.hasNext();) {
                                ListHdr = (Element) iterHdr.next();
                                try {
                                    KD_DOK = ListHdr.selectSingleNode("KD_DOK").getText();
                                    if (KD_DOK == null) {
                                        KD_DOK = "";
                                    }
                                } catch (Exception e) {
                                    KD_DOK = "";
                                }
                                try {
                                    KD_TPS = ListHdr.selectSingleNode("KD_TPS").getText();
                                    if (KD_TPS == null) {
                                        KD_TPS = "";
                                    }
                                } catch (Exception e) {
                                    KD_TPS = "";
                                }
                                try {
                                    NM_ANGKUT = ListHdr.selectSingleNode("NM_ANGKUT").getText();
                                    if (NM_ANGKUT == null) {
                                        NM_ANGKUT = "";
                                    }
                                } catch (Exception e) {
                                    NM_ANGKUT = "";
                                }
                                try {
                                    NO_VOY_FLIGHT = ListHdr.selectSingleNode("NO_VOY_FLIGHT").getText();
                                    if (NO_VOY_FLIGHT == null) {
                                        NO_VOY_FLIGHT = "";
                                    }
                                } catch (Exception e) {
                                    NO_VOY_FLIGHT = "";
                                }
                                try {
                                    CALL_SIGN = ListHdr.selectSingleNode("CALL_SIGN").getText();
                                    if (CALL_SIGN == null) {
                                        CALL_SIGN = "";
                                    }
                                } catch (Exception e) {
                                    CALL_SIGN = "";
                                }
                                try {
                                    TGL_TIBA = ListHdr.selectSingleNode("TGL_TIBA").getText();
                                    if (TGL_TIBA == null || TGL_TIBA.equals("00000000")) {
                                        TGL_TIBA = "";
                                    } else {
                                        cekTgl = val.validasiTgl(TGL_TIBA);
                                        if (!cekTgl) {
                                            if (!year.equalsIgnoreCase(TGL_TIBA.substring(0, 4))) {
                                                result = "Format Tgl Tiba Salah, Ref Number " + REF_NUMBER;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    TGL_TIBA = "";
                                }
                                try {
                                    KD_GUDANG = ListHdr.selectSingleNode("KD_GUDANG").getText();
                                    if (KD_GUDANG == null) {
                                        KD_GUDANG = "";
                                    }
                                } catch (Exception e) {
                                    KD_GUDANG = "";
                                }
                                try {
                                    REF_NUMBER = ListHdr.selectSingleNode("REF_NUMBER").getText();
                                    if (REF_NUMBER == null) {
                                        REF_NUMBER = "";
                                    }
                                } catch (Exception e) {
                                    REF_NUMBER = "";
                                }
                                try {
                                    mydb.execute("SELECT ID FROM COCOHDR WHERE REF_NUMBER = '" + REF_NUMBER + "'");
                                    if (mydb.next()) {
                                        ID = mydb.getInt("ID");
                                    } else {
                                        mydb.execute("SELECT COARRI_CODECO_SEQ.NEXTVAL as ID FROM DUAL");
                                        if (mydb.next()) {
                                            ID = mydb.getInt("ID");
                                            condition = true;
                                        }
                                        query = "INSERT INTO COCOHDR(ID,KD_DOK,KD_TPS,NM_ANGKUT,NO_VOY_FLIGHT,CALL_SIGN,TGL_TIBA,KD_GUDANG,REF_NUMBER)"
                                                + "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setInt(1, ID);
                                        preparedStatement.setString(2, KD_DOK);
                                        preparedStatement.setString(3, KD_TPS);
                                        preparedStatement.setString(4, NM_ANGKUT);
                                        preparedStatement.setString(5, NO_VOY_FLIGHT);
                                        preparedStatement.setString(6, CALL_SIGN);
                                        preparedStatement.setString(7, TGL_TIBA);
                                        preparedStatement.setString(8, KD_GUDANG);
                                        preparedStatement.setString(9, REF_NUMBER);

                                        preparedStatement.executeUpdate();
                                        //mydb.execute("commit");

                                        //mydb.execute("commit");
                                    }
                                } catch (Exception e) {
                                    result = REF_NUMBER + "_" + e.getMessage();
                                    exc.ExcuteError(e.getMessage(), "execute_class_LOADKMSCART", result);
                                    e.printStackTrace();
                                    break;
                                }

                            }
                            for (iterDtl = ListRes.selectNodes("DETIL").iterator(); iterDtl.hasNext();) {
                                ListDtl = (Element) iterDtl.next();
                                for (iterDtlKMS = ListDtl.selectNodes("KMS").iterator(); iterDtlKMS.hasNext();) {
                                    ListDtlKMS = (Element) iterDtlKMS.next();
                                    try {
                                        NO_BL_AWB = ListDtlKMS.selectSingleNode("NO_BL_AWB").getText();
                                        if (NO_BL_AWB == null) {
                                            NO_BL_AWB = "";
                                        }
                                    } catch (Exception e) {
                                        NO_BL_AWB = "";
                                    }
                                    try {
                                        TGL_BL_AWB = ListDtlKMS.selectSingleNode("TGL_BL_AWB").getText();

                                        if (TGL_BL_AWB == null || TGL_BL_AWB.equals("00000000")) {
                                            TGL_BL_AWB = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_BL_AWB);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_BL_AWB.substring(0, 4))) {
                                                    result = "Format Tgl BL AWB Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_BL_AWB = "";
                                    }
                                    try {
                                        NO_MASTER_BL_AWB = ListDtlKMS.selectSingleNode("NO_MASTER_BL_AWB ").getText();
                                        if (NO_MASTER_BL_AWB == null) {
                                            NO_MASTER_BL_AWB = "";
                                        }
                                    } catch (Exception e) {
                                        NO_MASTER_BL_AWB = "";
                                    }
                                    try {
                                        TGL_MASTER_BL_AWB = ListDtlKMS.selectSingleNode("TGL_MASTER_BL_AWB").getText();
                                        if (TGL_MASTER_BL_AWB == null || TGL_MASTER_BL_AWB.equals("00000000")) {
                                            TGL_MASTER_BL_AWB = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_MASTER_BL_AWB);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_MASTER_BL_AWB.substring(0, 4))) {
                                                    result = "Format Tgl Master BL AWB Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_MASTER_BL_AWB = "";
                                    }
                                    try {
                                        ID_CONSIGNEE = ListDtlKMS.selectSingleNode("ID_CONSIGNEE").getText();
                                        if (ID_CONSIGNEE == null) {
                                            ID_CONSIGNEE = "";
                                        }
                                    } catch (Exception e) {
                                        ID_CONSIGNEE = "";
                                    }
                                    try {
                                        CONSIGNEE = ListDtlKMS.selectSingleNode("CONSIGNEE").getText();
                                        if (CONSIGNEE == null) {
                                            CONSIGNEE = "";
                                        } else {
                                            ID_CONSIGNEE = ID_CONSIGNEE.replaceAll("[.-]", "");
                                        }
                                    } catch (Exception e) {
                                        CONSIGNEE = "";
                                    }
                                    try {
                                        BRUTO = ListDtlKMS.selectSingleNode("BRUTO").getText();
                                        if (BRUTO == null) {
                                            BRUTO = "";
                                        }
                                    } catch (Exception e) {
                                        BRUTO = "";
                                    }
                                    try {
                                        NO_BC11 = ListDtlKMS.selectSingleNode("NO_BC11").getText();
                                        if (NO_BC11 == null) {
                                            NO_BC11 = "";
                                        }
                                    } catch (Exception e) {
                                        NO_BC11 = "";
                                    }
                                    try {
                                        TGL_BC11 = ListDtlKMS.selectSingleNode("TGL_BC11").getText();
                                        if (TGL_BC11 == null || TGL_BC11.equals("00000000")) {
                                            TGL_BC11 = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_BC11);
                                            if (!cekTgl) {
                                                result = "Format Tgl BC 1.1 Salah, Ref Number " + REF_NUMBER;
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_BC11 = "";
                                    }
                                    try {
                                        NO_POS_BC11 = ListDtlKMS.selectSingleNode("NO_POS_BC11").getText();
                                        if (NO_POS_BC11 == null) {
                                            NO_POS_BC11 = "";
                                        }
                                    } catch (Exception e) {
                                        NO_POS_BC11 = "";
                                    }
                                    try {
                                        CONT_ASAL = ListDtlKMS.selectSingleNode("CONT_ASAL").getText();
                                        if (CONT_ASAL == null) {
                                            CONT_ASAL = "";
                                        }
                                    } catch (Exception e) {
                                        CONT_ASAL = "";
                                    }
                                    try {
                                        SERI_KEMAS = ListDtlKMS.selectSingleNode("SERI_KEMAS").getText();
                                        if (SERI_KEMAS == null) {
                                            SERI_KEMAS = "";
                                        }
                                    } catch (Exception e) {
                                        SERI_KEMAS = "";
                                    }
                                    try {
                                        KD_KEMAS = ListDtlKMS.selectSingleNode("KD_KEMAS").getText();
                                        if (KD_KEMAS == null) {
                                            KD_KEMAS = "";
                                        }
                                    } catch (Exception e) {
                                        KD_KEMAS = "";
                                    }
                                    try {
                                        JML_KEMAS = ListDtlKMS.selectSingleNode("JML_KEMAS").getText();
                                        if (JML_KEMAS == null) {
                                            JML_KEMAS = "";
                                        }
                                    } catch (Exception e) {
                                        JML_KEMAS = "";
                                    }
                                    try {
                                        KD_TIMBUN = ListDtlKMS.selectSingleNode("KD_TIMBUN").getText();
                                        if (KD_TIMBUN == null) {
                                            KD_TIMBUN = "";
                                        }
                                    } catch (Exception e) {
                                        KD_TIMBUN = "";
                                    }
                                    try {
                                        KD_DOK_INOUT = ListDtlKMS.selectSingleNode("KD_DOK_INOUT").getText();
                                        if (KD_DOK_INOUT == null) {
                                            KD_DOK_INOUT = "";
                                        }
                                    } catch (Exception e) {
                                        KD_DOK_INOUT = "";
                                    }
                                    try {
                                        NO_DOK_INOUT = ListDtlKMS.selectSingleNode("NO_DOK_INOUT").getText();
                                        if (NO_DOK_INOUT == null) {
                                            NO_DOK_INOUT = "";
                                        }
                                    } catch (Exception e) {
                                        NO_DOK_INOUT = "";
                                    }
                                    try {
                                        TGL_DOK_INOUT = ListDtlKMS.selectSingleNode("TGL_DOK_INOUT").getText();
                                        if (TGL_DOK_INOUT == null || TGL_DOK_INOUT.equals("00000000")) {
                                            TGL_DOK_INOUT = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_DOK_INOUT);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_DOK_INOUT.substring(0, 4))) {
                                                    result = "Format Tgl Dok Inout Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_DOK_INOUT = "";
                                    }
                                    try {
                                        WK_INOUT = ListDtlKMS.selectSingleNode("WK_INOUT").getText();
                                        if (WK_INOUT == null || WK_INOUT.equals("00000000000000") || WK_INOUT == "") {
                                            WK_INOUT = "NULL";
                                        }
                                        if (WK_INOUT.length() >= 14) {
                                            cekTglJam = val.validasiTglJam(WK_INOUT);
                                            if (!cekTglJam) {
                                                result = "Format Wk Inout Salah, Ref Number " + REF_NUMBER;
                                            } else {
                                                WK_INOUT = WK_INOUT.substring(0, 14);
                                                WK_INOUT = "TO_DATE('" + WK_INOUT + "','YYYYMMDDhh24miss')";
                                            }
                                        } else if (WK_INOUT.length() == 8) {
                                            cekTgl = val.validasiTgl(WK_INOUT);
                                            if (!cekTgl) {
                                                result = "Format Wk Inout Salah, Ref Number " + REF_NUMBER;
                                            } else {
                                                WK_INOUT = WK_INOUT.substring(0, 8);
                                                WK_INOUT = "TO_DATE('" + WK_INOUT + "','YYYYMMDD')";
                                            }
                                        } else {
                                            result = "Format Wk_Inout Inout Salah, Ref Number " + REF_NUMBER;
                                        }
                                    } catch (Exception e) {
                                        WK_INOUT = "";
                                    }
                                    try {
                                        KD_SAR_ANGKUT_INOUT = ListDtlKMS.selectSingleNode("KD_SAR_ANGKUT_INOUT").getText();
                                        if (KD_SAR_ANGKUT_INOUT == null) {
                                            KD_SAR_ANGKUT_INOUT = "";
                                        }
                                    } catch (Exception e) {
                                        KD_SAR_ANGKUT_INOUT = "";
                                    }
                                    try {
                                        NO_POL = ListDtlKMS.selectSingleNode("NO_POL").getText();
                                        if (NO_POL == null) {
                                            NO_POL = "";
                                        }
                                    } catch (Exception e) {
                                        NO_POL = "";
                                    }
                                    try {
                                        PEL_MUAT = ListDtlKMS.selectSingleNode("PEL_MUAT").getText();
                                        if (PEL_MUAT == null) {
                                            PEL_MUAT = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_MUAT = "";
                                    }
                                    try {
                                        PEL_TRANSIT = ListDtlKMS.selectSingleNode("PEL_TRANSIT").getText();
                                        if (PEL_TRANSIT == null) {
                                            PEL_TRANSIT = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_TRANSIT = "";
                                    }
                                    try {
                                        PEL_BONGKAR = ListDtlKMS.selectSingleNode("PEL_BONGKAR").getText();
                                        if (PEL_BONGKAR == null) {
                                            PEL_BONGKAR = "";
                                        }
                                    } catch (Exception e) {
                                        PEL_BONGKAR = "";
                                    }
                                    try {
                                        GUDANG_TUJUAN = ListDtlKMS.selectSingleNode("GUDANG_TUJUAN").getText();
                                        if (GUDANG_TUJUAN == null) {
                                            GUDANG_TUJUAN = "";
                                        }
                                    } catch (Exception e) {
                                        GUDANG_TUJUAN = "";
                                    }
                                    try {
                                        KODE_KANTOR = ListDtlKMS.selectSingleNode("KODE_KANTOR").getText();
                                        if (KODE_KANTOR == null) {
                                            KODE_KANTOR = "";
                                        }
                                    } catch (Exception e) {
                                        KODE_KANTOR = "";
                                    }
                                    try {
                                        NO_DAFTAR_PABEAN = ListDtlKMS.selectSingleNode("NO_DAFTAR_PABEAN").getText();
                                        if (NO_DAFTAR_PABEAN == null) {
                                            NO_DAFTAR_PABEAN = "";
                                        }
                                    } catch (Exception e) {
                                        NO_DAFTAR_PABEAN = "";
                                    }
                                    try {
                                        TGL_DAFTAR_PABEAN = ListDtlKMS.selectSingleNode("TGL_DAFTAR_PABEAN").getText();
                                        if (TGL_DAFTAR_PABEAN == null || TGL_DAFTAR_PABEAN.equals("00000000")) {
                                            TGL_DAFTAR_PABEAN = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_DAFTAR_PABEAN);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_DAFTAR_PABEAN.substring(0, 4))) {
                                                    result = "Format Tgl Daftar Pabean Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_DAFTAR_PABEAN = "";
                                    }
                                    try {
                                        NO_SEGEL_BC = ListDtlKMS.selectSingleNode("NO_SEGEL_BC").getText();
                                        if (NO_SEGEL_BC == null) {
                                            NO_SEGEL_BC = "";
                                        }
                                    } catch (Exception e) {
                                        NO_SEGEL_BC = "";
                                    }
                                    try {
                                        TGL_SEGEL_BC = ListDtlKMS.selectSingleNode("TGL_SEGEL_BC").getText();
                                        if (TGL_SEGEL_BC == null || TGL_SEGEL_BC.equals("00000000")) {
                                            TGL_SEGEL_BC = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_SEGEL_BC);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_SEGEL_BC.substring(0, 4))) {
                                                    result = "Format Tgl Segel BC Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_SEGEL_BC = "";
                                    }
                                    try {
                                        NO_IJIN_TPS = ListDtlKMS.selectSingleNode("NO_IJIN_TPS").getText();
                                        if (NO_IJIN_TPS == null) {
                                            NO_IJIN_TPS = "";
                                        }
                                    } catch (Exception e) {
                                        NO_IJIN_TPS = "";
                                    }
                                    try {
                                        TGL_IJIN_TPS = ListDtlKMS.selectSingleNode("TGL_IJIN_TPS").getText();
                                        if (TGL_IJIN_TPS == null || TGL_IJIN_TPS.equals("00000000")) {
                                            TGL_IJIN_TPS = "";
                                        } else {
                                            cekTgl = val.validasiTgl(TGL_IJIN_TPS);
                                            if (!cekTgl) {
                                                if (!year.equalsIgnoreCase(TGL_IJIN_TPS.substring(0, 4))) {
                                                    result = "Format Tgl Ijin TPS Salah, Ref Number " + REF_NUMBER;
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        TGL_IJIN_TPS = "";
                                    }
                                    try {
                                        if (condition) {
                                            query = "INSERT INTO COCOKMS(ID,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                                                    + "ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,CONT_ASAL,SERI_KEMAS,KD_KEMAS,"
                                                    + "JML_KEMAS,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,WK_INOUT,KD_SAR_ANGKUT_INOUT,"
                                                    + "NO_POL,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,REF_NUMBER,FLAG,GUDANG_TUJUAN,KODE_KANTOR,"
                                                    + "NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS,KD_DOK)"
                                                    + "VALUES (?,?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,TO_DATE(?,'YYYYMMDD'),"
                                                    + "?,?,?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,"
                                                    + "?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),"
                                                    + "?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),"
                                                    + "?)";

                                            preparedStatement = mydb.preparedstmt(query);
                                            preparedStatement.setInt(1, ID);
                                            preparedStatement.setString(2, NO_BL_AWB);
                                            preparedStatement.setString(3, TGL_BL_AWB);
                                            preparedStatement.setString(4, NO_MASTER_BL_AWB);
                                            preparedStatement.setString(5, TGL_MASTER_BL_AWB);
                                            preparedStatement.setString(6, ID_CONSIGNEE);
                                            preparedStatement.setString(7, CONSIGNEE);
                                            preparedStatement.setString(8, BRUTO);
                                            preparedStatement.setString(9, NO_BC11);
                                            preparedStatement.setString(10, TGL_BC11);
                                            preparedStatement.setString(11, NO_POS_BC11);
                                            preparedStatement.setString(12, CONT_ASAL);
                                            preparedStatement.setString(13, SERI_KEMAS);
                                            preparedStatement.setString(14, KD_KEMAS);
                                            preparedStatement.setString(15, JML_KEMAS);
                                            preparedStatement.setString(16, KD_TIMBUN);
                                            preparedStatement.setString(17, KD_DOK_INOUT);
                                            preparedStatement.setString(18, NO_DOK_INOUT);
                                            preparedStatement.setString(19, TGL_DOK_INOUT);
                                            preparedStatement.setString(20, WK_INOUT);
                                            preparedStatement.setString(21, KD_SAR_ANGKUT_INOUT);
                                            preparedStatement.setString(22, NO_POL);
                                            preparedStatement.setString(23, PEL_MUAT);
                                            preparedStatement.setString(24, PEL_TRANSIT);
                                            preparedStatement.setString(25, PEL_BONGKAR);
                                            preparedStatement.setString(26, REF_NUMBER);
                                            preparedStatement.setString(27, GUDANG_TUJUAN);
                                            preparedStatement.setString(28, KODE_KANTOR);
                                            preparedStatement.setString(29, NO_DAFTAR_PABEAN);
                                            preparedStatement.setString(30, TGL_DAFTAR_PABEAN);
                                            preparedStatement.setString(31, NO_SEGEL_BC);
                                            preparedStatement.setString(32, TGL_SEGEL_BC);
                                            preparedStatement.setString(33, NO_IJIN_TPS);
                                            preparedStatement.setString(34, TGL_IJIN_TPS);
                                            preparedStatement.setString(35, KD_DOK);

                                            preparedStatement.executeUpdate();
                                            condition = mydb.getProcessValue();
                                        }
                                    } catch (Exception e) {
                                        result = REF_NUMBER + "_" + e.getMessage();
                                        exc.ExcuteError(e.getMessage(), "execute_class_LOADKMSCART", result);
                                        e.printStackTrace();
                                    }
                                }
                                if (!condition) {
                                    mydb.execute("rollback");
                                    break;
                                } else {
                                    mydb.execute("commit");
                                    result = REF_NUMBER + "_Proses Berhasil.";
                                }
                            }
                        }
                        if (username.equalsIgnoreCase("PLDC")) {

                            query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF,FILESIZE) "
                                    + "VALUES (?,?,?,?)";

                            preparedStatement = mydb.preparedstmt(query);
                            preparedStatement.setDate(1, getCurrentTimeStamp());
                            preparedStatement.setString(2, "DOKCOARRI_CODECO_KMS");
                            preparedStatement.setString(3, REF_NUMBER);
                            preparedStatement.setInt(4, fStream.length());

                            preparedStatement.executeUpdate();
                            mydb.execute("commit");

                        }
                    }
                } catch (Exception e) {
                    result = REF_NUMBER + "_" + e.getMessage();
                    exc.ExcuteError(e.getMessage(), "execute_class_LOADKMSCART", result);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            result = REF_NUMBER + "_" + e.getMessage();
            exc.ExcuteError(e.getMessage(), "execute_class_LOADKMSCART", result);
            e.printStackTrace();
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
                exc.ExcuteError(ex1.getMessage(), "execute_class_LOADKMSCART", result);
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
//
//    public static void main(String[] args) {
//        File inFile = null;
//        BufferedReader input = null;
//        String content = "";
//        String content_final = "";
//        String[] file = null;
//        String filename = null;
//        MoveFile mv = null;
//        BufferedReader br = null;
//        FileReader fReader = null;
//        try {
//            mv = new MoveFile();
//            File folder = new File("D:/CART/januari");
//            file = folder.list();
//            for (int j = 0; j < file.length; j++) {
//                filename = file[j];
//                content = "";
//                content_final = "";
//
//                inFile = new File(folder + File.separator + filename);
//                if (inFile.isFile()) {
//                    fReader = new FileReader(inFile);
//                    br = new BufferedReader(fReader);
//                    while ((content = br.readLine()) != null) {
//                        content_final += content;
//                    }
//
//                    try {
//                        br.close();
//                    } catch (Exception e) {
//                    } finally {
//                        br = null;
//                    }
//                    try {
//                        fReader.close();
//                    } catch (Exception e) {
//                    } finally {
//                        fReader = null;
//                    }
//
//                    LOADKMSCART l = new LOADKMSCART();
//                    String r = l.parseDocument(content_final, "");
//                    System.out.println("hasilllllllllllll:::::::::" + r);
//                    if (r.contains("Success")) {
//                        //Move File to Error
//                        mv.move(folder + File.separator + filename, folder + File.separator + "History" + File.separator + filename);
//                    } else {
//                        mv.move(folder + File.separator + filename, folder + File.separator + "gagal" + File.separator + filename);
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
//    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
