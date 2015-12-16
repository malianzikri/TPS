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
public class ParsingXmlCoarriCodeco_Con {

    private ExcuteProses exc = new ExcuteProses();

    public String parseDocument(String fStream, String sementara) throws Exception {
        Db mydb = null;
        Document doc = null;
        Element List = null;;
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

        //Element Header
        String KD_DOK = null;
        String KD_TPS = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String CALL_SIGN = null;
        String TGL_TIBA = null;
        String KD_GUDANG = null;
        String REF_NUMBER = null;

        //element detil cont
        String NO_CONT = null;
        String UK_CONT = null;
        String NO_SEGEL = null;
        String JNS_CONT = null;
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
        String FL_CONT_KOSONG = null;
        String ISO_CODE = null;
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
        String year = null;
        int ID = 0;
        int a = 0;
        boolean hasilQuery = true;
        String result = null;
        validasi_tanggal val = null;
        boolean cekTgl = false;
        boolean cekTglJam = false;
        ExcuteProses exc = new ExcuteProses();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            val = new validasi_tanggal();
            mydb = new Db();
            mydb.execute("select to_char(sysdate,'yyyy') as tahun from dual");
            if (mydb.next()) {
                year = mydb.getString("tahun");
            }
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

                            try {
                                mydb.execute("SELECT COARRI_CODECO_SEQ.NEXTVAL as ID FROM DUAL");
                                if (mydb.next()) {
                                    ID = mydb.getInt("ID");
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
                            } catch (Exception e) {
                                result = REF_NUMBER + "_" + " Error " + e.getMessage();
                                exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLCoarriCodeco_Con", result);

                                e.printStackTrace();
                                break;
                            }
                        }
                        List detil = ListRes.getChildren("DETIL");
                        for (int k = 0; k < detil.size(); k++) {
                            ListDtl = (Element) detil.get(k);
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
                                    NO_SEGEL = ListDtlCONT.getChildText("NO_SEGEL");
                                    if (NO_SEGEL == null) {
                                        NO_SEGEL = "";
                                    }
                                } catch (Exception e) {
                                    NO_SEGEL = "";
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
                                    NO_BL_AWB = ListDtlCONT.getChildText("NO_BL_AWB");
                                    if (NO_BL_AWB == null) {
                                        NO_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    NO_BL_AWB = "";
                                }
                                try {
                                    TGL_BL_AWB = ListDtlCONT.getChildText("TGL_BL_AWB");
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
                                    NO_MASTER_BL_AWB = ListDtlCONT.getChildText("NO_MASTER_BL_AWB");
                                    if (NO_MASTER_BL_AWB == null) {
                                        NO_MASTER_BL_AWB = "";
                                    }
                                } catch (Exception e) {
                                    NO_MASTER_BL_AWB = "";
                                }
                                try {
                                    TGL_MASTER_BL_AWB = ListDtlCONT.getChildText("TGL_MASTER_BL_AWB");
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
                                    ID_CONSIGNEE = ListDtlCONT.getChildText("ID_CONSIGNEE");
                                    if (ID_CONSIGNEE == null) {
                                        ID_CONSIGNEE = "";
                                    } else {
                                        ID_CONSIGNEE = ID_CONSIGNEE.replaceAll("[.-]", "");
                                    }
                                } catch (Exception e) {
                                    ID_CONSIGNEE = "";
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
                                    BRUTO = ListDtlCONT.getChildText("BRUTO");
                                    if (BRUTO == null) {
                                        BRUTO = "";
                                    }
                                } catch (Exception e) {
                                    BRUTO = "";
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
                                    NO_POS_BC11 = ListDtlCONT.getChildText("NO_POS_BC11");
                                    if (NO_POS_BC11 == null) {
                                        NO_POS_BC11 = "";
                                    }
                                } catch (Exception e) {
                                    NO_POS_BC11 = "";
                                }
                                try {
                                    KD_TIMBUN = ListDtlCONT.getChildText("KD_TIMBUN");
                                    if (KD_TIMBUN == null) {
                                        KD_TIMBUN = "";
                                    }
                                } catch (Exception e) {
                                    KD_TIMBUN = "";
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
                                    WK_INOUT = ListDtlCONT.getChildText("WK_INOUT");
                                    System.out.println(WK_INOUT);
                                    System.out.println(WK_INOUT.length());
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
                                        //WK_INOUT = "TO_DATE('" + WK_INOUT + "','YYYYMMDDhh24miss')";
                                    }
                                } catch (Exception e) {
                                    WK_INOUT = "";
                                }
                                try {
                                    KD_SAR_ANGKUT_INOUT = ListDtlCONT.getChildText("KD_SAR_ANGKUT_INOUT");
                                    if (KD_SAR_ANGKUT_INOUT == null) {
                                        KD_SAR_ANGKUT_INOUT = "";
                                    }
                                } catch (Exception e) {
                                    KD_SAR_ANGKUT_INOUT = "";
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
                                    FL_CONT_KOSONG = ListDtlCONT.getChildText("FL_CONT_KOSONG");
                                    if (FL_CONT_KOSONG == null) {
                                        FL_CONT_KOSONG = "";
                                    }
                                } catch (Exception e) {
                                    FL_CONT_KOSONG = "";
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
                                    PEL_MUAT = ListDtlCONT.getChildText("PEL_MUAT");
                                    if (PEL_MUAT == null) {
                                        PEL_MUAT = "";
                                    }
                                } catch (Exception e) {
                                    PEL_MUAT = "";
                                }
                                try {
                                    PEL_TRANSIT = ListDtlCONT.getChildText("PEL_TRANSIT");
                                    if (PEL_TRANSIT == null) {
                                        PEL_TRANSIT = "";
                                    }
                                } catch (Exception e) {
                                    PEL_TRANSIT = "";
                                }
                                try {
                                    PEL_BONGKAR = ListDtlCONT.getChildText("PEL_BONGKAR");
                                    if (PEL_BONGKAR == null) {
                                        PEL_BONGKAR = "";
                                    }
                                } catch (Exception e) {
                                    PEL_BONGKAR = "";
                                }
                                try {
                                    GUDANG_TUJUAN = ListDtlCONT.getChildText("GUDANG_TUJUAN");
                                    if (GUDANG_TUJUAN == null) {
                                        GUDANG_TUJUAN = "";
                                    }
                                } catch (Exception e) {
                                    GUDANG_TUJUAN = "";
                                }
                                try {
                                    KODE_KANTOR = ListDtlCONT.getChildText("KODE_KANTOR");
                                    if (KODE_KANTOR == null) {
                                        KODE_KANTOR = "";
                                    }
                                } catch (Exception e) {
                                    KODE_KANTOR = "";
                                }
                                try {
                                    NO_DAFTAR_PABEAN = ListDtlCONT.getChildText("NO_DAFTAR_PABEAN");
                                    if (NO_DAFTAR_PABEAN == null) {
                                        NO_DAFTAR_PABEAN = "";
                                    }
                                } catch (Exception e) {
                                    NO_DAFTAR_PABEAN = "";
                                }
                                try {
                                    TGL_DAFTAR_PABEAN = ListDtlCONT.getChildText("TGL_DAFTAR_PABEAN");
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
                                    NO_SEGEL_BC = ListDtlCONT.getChildText("NO_SEGEL_BC");
                                    if (NO_SEGEL_BC == null) {
                                        NO_SEGEL_BC = "";
                                    }
                                } catch (Exception e) {
                                    NO_SEGEL_BC = "";
                                }
                                try {
                                    TGL_SEGEL_BC = ListDtlCONT.getChildText("TGL_SEGEL_BC");
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
                                    NO_IJIN_TPS = ListDtlCONT.getChildText("NO_IJIN_TPS");
                                    if (NO_IJIN_TPS == null) {
                                        NO_IJIN_TPS = "";
                                    }
                                } catch (Exception e) {
                                    NO_IJIN_TPS = "";
                                }
                                try {
                                    TGL_IJIN_TPS = ListDtlCONT.getChildText("TGL_IJIN_TPS");
                                    if (TGL_IJIN_TPS == null || TGL_IJIN_TPS.equals("00000000")) {
                                        TGL_IJIN_TPS = "";
                                    } else {
                                        cekTgl = val.validasiTgl(TGL_IJIN_TPS);
                                        if (!cekTgl) {
                                            if (!year.equalsIgnoreCase(TGL_IJIN_TPS.substring(0, 4))) {
                                                result = "Format Tgl Ijin Tps Salah, Ref Number " + REF_NUMBER;
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    TGL_IJIN_TPS = "";
                                }
                                if (sementara.equalsIgnoreCase("ter3")) {
                                    try {
                                        query = "INSERT INTO COCOCONT(ID,NO_CONT,UK_CONT,NO_SEGEL,JNS_CONT,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                                                + "ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
                                                + "WK_INOUT,KD_SAR_ANGKUT_INOUT,NO_POL,FL_CONT_KOSONG,ISO_CODE,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,FLAG,GUDANG_TUJUAN,"
                                                + "KODE_KANTOR,NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS,REF_NUMBER,KD_DOK,FLAG)"
                                                + "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,"
                                                + "?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,"
                                                + "?,?,?,?,?,?,?,?,?,?,"
                                                + "?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setInt(1, ID);
                                        preparedStatement.setString(2, NO_CONT);
                                        preparedStatement.setString(3, UK_CONT);
                                        preparedStatement.setString(4, NO_SEGEL);
                                        preparedStatement.setString(5, JNS_CONT);
                                        preparedStatement.setString(6, NO_BL_AWB);
                                        preparedStatement.setString(7, TGL_BL_AWB);
                                        preparedStatement.setString(8, NO_MASTER_BL_AWB);
                                        preparedStatement.setString(9, TGL_MASTER_BL_AWB);
                                        preparedStatement.setString(10, ID_CONSIGNEE);
                                        preparedStatement.setString(11, CONSIGNEE);
                                        preparedStatement.setString(12, BRUTO);
                                        preparedStatement.setString(13, NO_BC11);
                                        preparedStatement.setString(14, TGL_BC11);
                                        preparedStatement.setString(15, NO_POS_BC11);
                                        preparedStatement.setString(16, KD_TIMBUN);
                                        preparedStatement.setString(17, KD_DOK_INOUT);
                                        preparedStatement.setString(18, NO_DOK_INOUT);
                                        preparedStatement.setString(19, TGL_DOK_INOUT);
                                        preparedStatement.setString(20, WK_INOUT);
                                        preparedStatement.setString(21, KD_SAR_ANGKUT_INOUT);
                                        preparedStatement.setString(22, NO_POL);
                                        preparedStatement.setString(23, FL_CONT_KOSONG);
                                        preparedStatement.setString(24, ISO_CODE);
                                        preparedStatement.setString(25, PEL_MUAT);
                                        preparedStatement.setString(26, PEL_TRANSIT);
                                        preparedStatement.setString(27, PEL_BONGKAR);
                                        preparedStatement.setString(28, "1");
                                        preparedStatement.setString(29, GUDANG_TUJUAN);
                                        preparedStatement.setString(30, KODE_KANTOR);
                                        preparedStatement.setString(31, NO_DAFTAR_PABEAN);
                                        preparedStatement.setString(32, TGL_DAFTAR_PABEAN);
                                        preparedStatement.setString(33, NO_SEGEL_BC);
                                        preparedStatement.setString(34, TGL_SEGEL_BC);
                                        preparedStatement.setString(35, NO_IJIN_TPS);
                                        preparedStatement.setString(36, TGL_IJIN_TPS);
                                        preparedStatement.setString(37, REF_NUMBER);
                                        preparedStatement.setString(38, KD_DOK);
                                        preparedStatement.setString(39, "9");

                                        //preparedStatement.executeUpdate();
                                        //mydb.execute("commit");
                                        a = a + 1;
                                        if (!preparedStatement.execute()) {
                                            hasilQuery = false;
                                        }
                                    } catch (Exception e) {
                                    }
                                } else {
                                    try {
                                        query = "INSERT INTO COCOCONT(ID,NO_CONT,UK_CONT,NO_SEGEL,JNS_CONT,NO_BL_AWB,TGL_BL_AWB,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
                                                + "ID_CONSIGNEE,CONSIGNEE,BRUTO,NO_BC11,TGL_BC11,NO_POS_BC11,KD_TIMBUN,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
                                                + "WK_INOUT,KD_SAR_ANGKUT_INOUT,NO_POL,FL_CONT_KOSONG,ISO_CODE,PEL_MUAT,PEL_TRANSIT,PEL_BONGKAR,FLAG,GUDANG_TUJUAN,"
                                                + "KODE_KANTOR,NO_DAFTAR_PABEAN,TGL_DAFTAR_PABEAN,NO_SEGEL_BC,TGL_SEGEL_BC,NO_IJIN_TPS,TGL_IJIN_TPS,REF_NUMBER,KD_DOK)"
                                                + "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,"
                                                + "?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,"
                                                + "?,?,?,?,?,?,?,?,?,?,"
                                                + "?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'),?,?)";

                                        preparedStatement = mydb.preparedstmt(query);
                                        preparedStatement.setInt(1, ID);
                                        preparedStatement.setString(2, NO_CONT);
                                        preparedStatement.setString(3, UK_CONT);
                                        preparedStatement.setString(4, NO_SEGEL);
                                        preparedStatement.setString(5, JNS_CONT);
                                        preparedStatement.setString(6, NO_BL_AWB);
                                        preparedStatement.setString(7, TGL_BL_AWB);
                                        preparedStatement.setString(8, NO_MASTER_BL_AWB);
                                        preparedStatement.setString(9, TGL_MASTER_BL_AWB);
                                        preparedStatement.setString(10, ID_CONSIGNEE);
                                        preparedStatement.setString(11, CONSIGNEE);
                                        preparedStatement.setString(12, BRUTO);
                                        preparedStatement.setString(13, NO_BC11);
                                        preparedStatement.setString(14, TGL_BC11);
                                        preparedStatement.setString(15, NO_POS_BC11);
                                        preparedStatement.setString(16, KD_TIMBUN);
                                        preparedStatement.setString(17, KD_DOK_INOUT);
                                        preparedStatement.setString(18, NO_DOK_INOUT);
                                        preparedStatement.setString(19, TGL_DOK_INOUT);
                                        preparedStatement.setString(20, WK_INOUT);
                                        preparedStatement.setString(21, KD_SAR_ANGKUT_INOUT);
                                        preparedStatement.setString(22, NO_POL);
                                        preparedStatement.setString(23, FL_CONT_KOSONG);
                                        preparedStatement.setString(24, ISO_CODE);
                                        preparedStatement.setString(25, PEL_MUAT);
                                        preparedStatement.setString(26, PEL_TRANSIT);
                                        preparedStatement.setString(27, PEL_BONGKAR);
                                        preparedStatement.setString(28, "1");
                                        preparedStatement.setString(29, GUDANG_TUJUAN);
                                        preparedStatement.setString(30, KODE_KANTOR);
                                        preparedStatement.setString(31, NO_DAFTAR_PABEAN);
                                        preparedStatement.setString(32, TGL_DAFTAR_PABEAN);
                                        preparedStatement.setString(33, NO_SEGEL_BC);
                                        preparedStatement.setString(34, TGL_SEGEL_BC);
                                        preparedStatement.setString(35, NO_IJIN_TPS);
                                        preparedStatement.setString(36, TGL_IJIN_TPS);
                                        preparedStatement.setString(37, REF_NUMBER);
                                        preparedStatement.setString(38, KD_DOK);

                                        //preparedStatement.executeUpdate();
                                        //mydb.execute("commit");
                                        a = a + 1;
                                        if (!preparedStatement.execute()) {
                                            hasilQuery = false;
                                        }

                                    } catch (Exception e) {
                                        result = REF_NUMBER + "_" + " Error " + e.getMessage();

                                        e.printStackTrace();
                                        exc.ExcuteError(e.getMessage(), "COCOCONT", REF_NUMBER);
                                    }
                                }
                            }

                        }

                        if (hasilQuery) {
                            mydb.execute("commit");
                            result = REF_NUMBER + "_Proses Berhasil Ref Number " + REF_NUMBER + " Dari " + a + " Container.";
                        } else {
                            mydb.execute("rollback");
                        }
                    }
                } catch (Exception e) {
                    result = REF_NUMBER + "_" + " Error " + e.getMessage();
                    exc.ExcuteError(e.getMessage(), "COCOCONT", REF_NUMBER);
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            result = REF_NUMBER + "_" + " Error " + ex.getMessage();
            exc.ExcuteError(ex.getMessage(), "COCOCONT", REF_NUMBER);
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
