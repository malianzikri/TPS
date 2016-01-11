 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelSPPB;
import com.edii.operation.db.operation;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingSPPB {

    private final ParsingXML px;
    private final ModelSPPB model;
    private final SaveData sv;

    public ParsingSPPB() {
        px = new ParsingXML();
        model = new ModelSPPB();
        sv = new operation();
    }

    public String parseDocument(String fStream, String username) throws Exception {
        boolean duplicateFile;
        String result = "";
        String id = "";
        try {
            ArrayList<String> list = new ArrayList<String>();
            if (fStream != null) {
                list = px.xmlParsing(fStream, "SPPB>HEADER", "CAR,NO_SPPB,TGL_SPPB,KD_KPBC,KD_KANTOR_PENGAWAS,KD_KANTOR_BONGKAR,NO_PIB,TGL_PIB"
                        + "NPWP_IMP,NAMA_IMP,ALAMAT_IMP,NPWP_PPJK,NAMA_PPJK,ALAMAT_PPJK,NM_ANGKUT,NO_VOY_FLIGHT,BRUTTO,NETTO,GUDANG,STATUS_JALUR,JML_CONT"
                        + "NO_BC11,TGL_BC11,NO_POS_BC11,NO_BL_AWB,TG_BL_AWB,NO_MASTER_BL_AWB,TG_MASTER_BL_AWB");
                for (String list_header : list) {
                    String[] split_header = list_header.split(",");
                    model.setCAR(split_header[0]);
                    model.setNO_SPPB(split_header[1]);
                    model.setTGL_SPPB(split_header[2]);
                    model.setTGL_SPPB(split_header[3]);
                    model.setKD_KPBC(split_header[4]);
                    model.setKD_KANTOR_PENGAWAS(split_header[5]);
                    model.setKD_KANTOR_BONGKAR(split_header[6]);
                    model.setNO_PIB(split_header[7]);
                    model.setTGL_PIB(split_header[8]);
                    model.setNPWP_IMP(split_header[9]);
                    model.setNAMA_IMP(split_header[10]);
                    model.setALAMAT_IMP(split_header[11]);
                    model.setNPWP_PPJK(split_header[12]);
                    model.setNAMA_PPJK(split_header[13]);
                    model.setALAMAT_PPJK(split_header[14]);
                    model.setNM_ANGKUT(split_header[15]);
                    model.setNO_VOY_FLIGHT(split_header[16]);
                    model.setBRUTTO(split_header[17]);
                    model.setNETTO(split_header[18]);
                    model.setGUDANG(split_header[19]);
                    model.setSTATUS_JALUR(split_header[20]);
                    model.setJML_CONT(split_header[21]);
                    model.setNO_BC11(split_header[22]);
                    model.setTGL_BC11(split_header[23]);
                    model.setNO_POS_BC11(split_header[24]);
                    model.setNO_BL_AWB(split_header[25]);
                    model.setTG_BL_AWB(split_header[26]);
                    model.setNO_MASTER_BL_AWB(split_header[27]);
                    model.setTG_MASTER_BL_AWB(split_header[28]);

                }
                duplicateFile = false;
                if (sv.cekdata_sppb_header(model)) {
                    duplicateFile = true;
                } else {
                    id = sv.savedata_sppb_header(model);
                }

                ArrayList<String> detil_kms = new ArrayList<>();
                detil_kms = px.xmlParsing(fStream, "SPPB>DETIL>KMS", "CAR,JNS_KMS,MERK_KMS,JML_KMS");
                for (String detil : detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setCAR(split_kms[0]);
                    model.setJNS_KMS(split_kms[1]);
                    model.setMERK_KMS(split_kms[2]);
                    model.setJML_KMS(split_kms[3]);
                    if (!duplicateFile) {
                        sv.savedata_sppb_kms(model);
                    }
                }

                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "SPJM>DETIL>CONT", "CAR,NO_CONT,SIZE,JNS_MUAT");
                for (String detil : detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setCAR(split_cont[0]);
                    model.setNO_CONT(split_cont[1]);
                    model.setSIZE(split_cont[2]);
                    model.setJNS_MUAT(split_cont[3]);
                    if (!duplicateFile) {
                        sv.savedata_sppb_cont(model);
                    }
                }
//                try {
//                    doc = builder.build(new StringReader(fStream));
//                    Element rootNode = doc.getRootElement();
//                    List list = rootNode.getChildren("SPPB");
//                    for (int i = 0; i < list.size(); i++) {
//                        ListRes = (Element) list.get(i);
//                        List header = ListRes.getChildren("HEADER");
//                        for (int j = 0; j < header.size(); j++) {
//                            ListHdr = (Element) header.get(j);
//                            try {
//                                CAR = ListHdr.getChildText("CAR");
//                                if (CAR == null) {
//                                    CAR = "";
//                                }
//                            } catch (Exception e) {
//                                CAR = "";
//                            }
//                            try {
//                                NO_SPPB = ListHdr.getChildText("NO_SPPB");
//                                if (NO_SPPB == null) {
//                                    NO_SPPB = "";
//                                }
//                            } catch (Exception e) {
//                                NO_SPPB = "";
//                            }
//                            try {
//                                TGL_SPPB = ListHdr.getChildText("TGL_SPPB");
//                                if (TGL_SPPB == null) {
//                                    TGL_SPPB = "";
//                                }
//                                if (!TGL_SPPB.equalsIgnoreCase("")) {
//                                    TGL_SPPB = "TO_DATE('" + TGL_SPPB + "','MM/DD/YYYY')";
//                                } else {
//                                    TGL_SPPB = "'" + TGL_SPPB + "'";
//                                }
//                            } catch (Exception e) {
//                                TGL_SPPB = "";
//                            }
//                            try {
//                                KD_KPBC = ListHdr.getChildText("KD_KPBC");
//                                if (KD_KPBC == null) {
//                                    KD_KPBC = "";
//                                }
//                            } catch (Exception e) {
//                                KD_KPBC = "";
//                            }
//                            try {
//                                KD_KANTOR_PENGAWAS = ListHdr.getChildText("KD_KANTOR_PENGAWAS");
//                                if (KD_KANTOR_PENGAWAS == null) {
//                                    KD_KANTOR_PENGAWAS = "";
//                                }
//                            } catch (Exception e) {
//                                KD_KANTOR_PENGAWAS = "";
//                            }
//                            try {
//                                KD_KANTOR_BONGKAR = ListHdr.getChildText("KD_KANTOR_BONGKAR");
//                                if (KD_KANTOR_BONGKAR == null) {
//                                    KD_KANTOR_BONGKAR = "";
//                                }
//                            } catch (Exception e) {
//                                KD_KANTOR_BONGKAR = "";
//                            }
//                            try {
//                                NO_PIB = ListHdr.getChildText("NO_PIB");
//                                if (NO_PIB == null) {
//                                    NO_PIB = "";
//                                }
//                            } catch (Exception e) {
//                                NO_PIB = "";
//                            }
//                            try {
//                                TGL_PIB = ListHdr.getChildText("TGL_PIB");
//                                if (TGL_PIB.equals(null)) {
//                                    TGL_PIB = "";
//                                }
//                                if (!TGL_PIB.equalsIgnoreCase("")) {
//                                    TGL_PIB = "TO_DATE('" + TGL_PIB + "','MM/DD/YYYY')";
//                                } else {
//                                    TGL_PIB = "'" + TGL_PIB + "'";
//                                }
//                            } catch (Exception e) {
//                                TGL_PIB = "";
//                            }
//                            try {
//                                NPWP_IMP = ListHdr.getChildText("NPWP_IMP");
//                                if (NPWP_IMP == null) {
//                                    NPWP_IMP = "";
//                                }
//                            } catch (Exception e) {
//                                NPWP_IMP = "";
//                            }
//                            try {
//                                NAMA_IMP = ListHdr.getChildText("NAMA_IMP");
//                                if (NAMA_IMP == null) {
//                                    NAMA_IMP = "";
//                                }
//                            } catch (Exception e) {
//                                NAMA_IMP = "";
//                            }
//                            try {
//                                ALAMAT_IMP = ListHdr.getChildText("ALAMAT_IMP");
//                                if (ALAMAT_IMP == null) {
//                                    ALAMAT_IMP = "";
//                                }
//                            } catch (Exception e) {
//                                ALAMAT_IMP = "";
//                            }
//                            try {
//                                NPWP_PPJK = ListHdr.getChildText("NPWP_PPJK");
//                                if (NPWP_PPJK == null) {
//                                    NPWP_PPJK = "";
//                                }
//                            } catch (Exception e) {
//                                NPWP_PPJK = "";
//                            }
//                            try {
//                                NAMA_PPJK = ListHdr.getChildText("NAMA_PPJK");
//                                if (NAMA_PPJK == null) {
//                                    NAMA_PPJK = "";
//                                }
//                            } catch (Exception e) {
//                                NAMA_PPJK = "";
//                            }
//                            try {
//                                ALAMAT_PPJK = ListHdr.getChildText("ALAMAT_PPJK");
//                                if (ALAMAT_PPJK == null) {
//                                    ALAMAT_PPJK = "";
//                                }
//                            } catch (Exception e) {
//                                ALAMAT_PPJK = "";
//                            }
//                            try {
//                                NM_ANGKUT = ListHdr.getChildText("NM_ANGKUT");
//                                if (NM_ANGKUT == null) {
//                                    NM_ANGKUT = "";
//                                }
//                            } catch (Exception e) {
//                                NM_ANGKUT = "";
//                            }
//                            try {
//                                NO_VOY_FLIGHT = ListHdr.getChildText("NO_VOY_FLIGHT");
//                                if (NO_VOY_FLIGHT == null) {
//                                    NO_VOY_FLIGHT = "";
//                                }
//                            } catch (Exception e) {
//                                NO_VOY_FLIGHT = "";
//                            }
//                            try {
//                                BRUTTO = ListHdr.getChildText("BRUTO");
//                                if (BRUTTO == null) {
//                                    BRUTTO = "";
//                                }
//                            } catch (Exception e) {
//                                BRUTTO = "";
//                            }
//                            try {
//                                NETTO = ListHdr.getChildText("NETTO");
//                                if (NETTO == null) {
//                                    NETTO = "";
//                                }
//                            } catch (Exception e) {
//                                NETTO = "";
//                            }
//                            try {
//                                GUDANG = ListHdr.getChildText("GUDANG");
//                                if (GUDANG == null) {
//                                    GUDANG = "";
//                                }
//                            } catch (Exception e) {
//                                GUDANG = "";
//                            }
//                            try {
//                                STATUS_JALUR = ListHdr.getChildText("STATUS_JALUR");
//                                if (STATUS_JALUR == null) {
//                                    STATUS_JALUR = "";
//                                    System.out.println("masuk" + STATUS_JALUR);
//                                }
//                            } catch (Exception e) {
//                                STATUS_JALUR = "";
//                            }
//                            try {
//                                JML_CONT = ListHdr.getChildText("JML_CONT");
//                                if (JML_CONT == null) {
//                                    JML_CONT = "0";
//                                }
//                                if (JML_CONT == "") {
//                                    JML_CONT = "0";
//                                }
//                            } catch (Exception e) {
//                                JML_CONT = "";
//                            }
//                            try {
//                                NO_BC11 = ListHdr.getChildText("NO_BC11");
//                                if (NO_BC11 == null) {
//                                    NO_BC11 = "";
//                                }
//                            } catch (Exception e) {
//                                NO_BC11 = "";
//                            }
//                            try {
//                                TGL_BC11 = ListHdr.getChildText("TGL_BC11");
//                                if (TGL_BC11 == null) {
//                                    TGL_BC11 = "";
//                                }
//                                if (!TGL_BC11.equalsIgnoreCase("")) {
//                                    TGL_BC11 = "TO_DATE('" + TGL_BC11 + "','MM/DD/YYYY')";
//                                } else {
//                                    TGL_BC11 = "'" + TGL_BC11 + "'";
//                                }
//                            } catch (Exception e) {
//                                TGL_BC11 = "";
//                            }
//                            try {
//                                NO_POS_BC11 = ListHdr.getChildText("NO_POS_BC11");
//                                if (NO_POS_BC11 == null) {
//                                    NO_POS_BC11 = "";
//                                }
//                            } catch (Exception e) {
//                                NO_POS_BC11 = "";
//                            }
//                            try {
//                                NO_BL_AWB = ListHdr.getChildText("NO_BL_AWB");
//                                if (NO_BL_AWB == null) {
//                                    NO_BL_AWB = "";
//                                }
//                            } catch (Exception e) {
//                                NO_BL_AWB = "";
//                            }
//                            try {
//                                TG_BL_AWB = ListHdr.getChildText("TG_BL_AWB");
//                                if (TG_BL_AWB == null) {
//                                    TG_BL_AWB = "";
//                                }
//                                if (!TG_BL_AWB.equalsIgnoreCase("")) {
//                                    TG_BL_AWB = "TO_DATE('" + TG_BL_AWB + "','MM/DD/YYYY')";
//                                } else {
//                                    TG_BL_AWB = "'" + TG_BL_AWB + "'";
//                                }
//                            } catch (Exception e) {
//                                TG_BL_AWB = "";
//                            }
//                            try {
//                                NO_MASTER_BL_AWB = ListHdr.getChildText("NO_MASTER_BL_AWB");
//                                if (NO_MASTER_BL_AWB == null) {
//                                    NO_MASTER_BL_AWB = "";
//                                }
//                            } catch (Exception e) {
//                                NO_MASTER_BL_AWB = "";
//                            }
//                            try {
//                                TG_MASTER_BL_AWB = ListHdr.getChildText("TG_MASTER_BL_AWB");
//                                if (TG_MASTER_BL_AWB == null) {
//                                    TG_MASTER_BL_AWB = "";
//                                }
//                                if (!TG_MASTER_BL_AWB.equalsIgnoreCase("")) {
//                                    TG_MASTER_BL_AWB = "TO_DATE('" + TG_MASTER_BL_AWB + "','MM/DD/YYYY')";
//                                } else {
//                                    TG_MASTER_BL_AWB = "'" + TG_MASTER_BL_AWB + "'";
//                                }
//                            } catch (Exception e) {
//                                TG_MASTER_BL_AWB = "";
//                            }
//                            duplicateFile = false;
//                            mydb.execute("SELECT * FROM CUSPERMITHDR WHERE CAR = '" + CAR + "'");
//                            if (mydb.next()) {
//                                duplicateFile = true;
//                            } else {
//                                query = "INSERT INTO CUSPERMITHDR(CAR,NO_SPPB,TGL_SPPB,KD_KPBC,NO_PIB,"
//                                        + "TGL_PIB,NPWP_IMP,NAMA_IMP,ALAMAT_IMP,NPWP_PPJK,NAMA_PPJK,"
//                                        + "ALAMAT_PPJK,NM_ANGKUT,NO_VOY_FLIGHT,BRUTO,NETTO,GUDANG,"
//                                        + "STATUS_JALUR,JML_CONT,NO_BC11,TGL_BC11,NO_POS_BC11,NO_BL_AWB,"
//                                        + "TG_BL_AWB,NO_MASTER_BL_AWB,TG_MASTER_BL_AWB,FLAG_TRANSFER_IPC,"
//                                        + "WK_INSERT)"
//                                        + "VALUES (?,?,?,?,?,?,?,?,?,?,"
//                                        + "?,?,?,?,?,?,?,?,?,?,"
//                                        + "?,?,?,?,?,?,?)";
//
//                                preparedStatement = mydb.preparedstmt(query);
//                                preparedStatement.setString(1, CAR);
//                                preparedStatement.setString(2, NO_SPPB);
//                                preparedStatement.setString(3, TGL_SPPB);
//                                preparedStatement.setString(4, KD_KPBC);
//                                preparedStatement.setString(5, NO_PIB);
//                                preparedStatement.setString(6, TGL_PIB);
//                                preparedStatement.setString(7, NPWP_IMP);
//                                preparedStatement.setString(8, NAMA_IMP);
//                                preparedStatement.setString(9, ALAMAT_IMP);
//                                preparedStatement.setString(10, NPWP_PPJK);
//                                preparedStatement.setString(11, NAMA_PPJK);
//                                preparedStatement.setString(12, ALAMAT_PPJK);
//                                preparedStatement.setString(13, NM_ANGKUT);
//                                preparedStatement.setString(14, NO_VOY_FLIGHT);
//                                preparedStatement.setString(15, BRUTTO);
//                                preparedStatement.setString(16, NETTO);
//                                preparedStatement.setString(17, GUDANG);
//                                preparedStatement.setString(18, STATUS_JALUR);
//                                preparedStatement.setString(19, JML_CONT);
//                                preparedStatement.setString(20, NO_BC11);
//                                preparedStatement.setString(21, NO_POS_BC11);
//                                preparedStatement.setString(22, NO_BL_AWB);
//                                preparedStatement.setString(23, TG_BL_AWB);
//                                preparedStatement.setString(24, NO_MASTER_BL_AWB);
//                                preparedStatement.setString(25, TG_MASTER_BL_AWB);
//                                preparedStatement.setString(26, "0");
//                                preparedStatement.setDate(27, getCurrentTimeStamp());
//
//                                preparedStatement.executeUpdate();
//                                mydb.execute("commit");
//
//                            }
//                        }
//                        List detil = ListRes.getChildren("DETIL");
//                        for (int k = 0; k < detil.size(); k++) {
//                            ListDtl = (Element) detil.get(k);
//                            List kms = ListDtl.getChildren("KMS");
//                            for (int l = 0; l < kms.size(); l++) {
//                                ListDtlKMS = (Element) kms.get(l);
//                                try {
//                                    CAR = ListDtlKMS.getChildText("CAR");
//                                    if (CAR == null) {
//                                        CAR = "";
//                                    }
//                                } catch (Exception e) {
//                                    CAR = "";
//                                }
//                                try {
//                                    JNS_KMS = ListDtlKMS.getChildText("JNS_KMS");
//                                    if (JNS_KMS == null) {
//                                        JNS_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JNS_KMS = "";
//                                }
//                                try {
//                                    MERK_KMS = ListDtlKMS.getChildText("MERK_KMS");
//                                    if (MERK_KMS == null) {
//                                        MERK_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    MERK_KMS = "";
//                                }
//                                try {
//                                    JML_KMS = ListDtlKMS.getChildText("JML_KMS");
//                                    if (JML_KMS == null) {
//                                        JML_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JML_KMS = "";
//                                }
//                                if (!duplicateFile) {
//                                    query = "INSERT INTO CUSPERMITKMS(CAR,JNS_KMS,MERK_KMS,JML_KMS,FLAG_TRANSFER_IPC,WK_INSERT)"
//                                            + "VALUES (?,?,?,?,?,?)";
//
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, CAR);
//                                    preparedStatement.setString(2, JNS_KMS);
//                                    preparedStatement.setString(3, MERK_KMS);
//                                    preparedStatement.setString(4, JML_KMS);
//                                    preparedStatement.setString(5, "0");
//                                    preparedStatement.setDate(6, getCurrentTimeStamp());
//
//                                    preparedStatement.executeUpdate();
//                                    mydb.execute("commit");
//                                }
//                            }
//                            List cont = ListDtl.getChildren("CONT");
//                            for (int l = 0; l < cont.size(); l++) {
//                                ListDtlCONT = (Element) cont.get(l);
//                                try {
//                                    CAR = ListDtlCONT.getChildText("CAR");
//                                    if (CAR == null) {
//                                        CAR = "";
//                                    }
//                                } catch (Exception e) {
//                                    CAR = "";
//                                }
//                                try {
//                                    NO_CONT = ListDtlCONT.getChildText("NO_CONT");
//                                    if (NO_CONT == null) {
//                                        NO_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_CONT = "";
//                                }
//                                try {
//                                    SIZE = ListDtlCONT.getChildText("SIZE");
//                                    if (SIZE == null) {
//                                        SIZE = "";
//                                    }
//                                } catch (Exception e) {
//                                    SIZE = "";
//                                }
//                                try {
//                                    JNS_MUAT = ListDtlCONT.getChildText("JNS_MUAT");
//                                    if (JNS_MUAT == null) {
//                                        JNS_MUAT = "";
//                                    }
//                                } catch (Exception e) {
//                                    JNS_MUAT = "";
//                                }
//
//                                //DECISION VALIDATION CHARACTER NO_CONT AND UK_CONT
//                                if ((11 - NO_CONT.length()) > -1) {
//                                    NO_CONT = NO_CONT.substring(0, NO_CONT.length());
//                                } else {
//                                    NO_CONT = NO_CONT.substring(0, 11);
//                                }
//                                if ((2 - SIZE.length()) > -1) {
//                                    SIZE = SIZE.substring(0, SIZE.length());
//                                } else {
//                                    SIZE = SIZE.substring(0, 2);
//                                }
//                                if (!duplicateFile) {
//                                    query = "INSERT INTO CUSPERMITCONT(CAR,NO_CONT,UK_CONT,JNS_MUAT,FLAG_TRANSFER_IPC,WK_INSERT)"
//                                            + "VALUES (?,?,?,?,?,?)";
//
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, CAR);
//                                    preparedStatement.setString(2, JNS_KMS);
//                                    preparedStatement.setString(3, SIZE);
//                                    preparedStatement.setString(4, JNS_MUAT);
//                                    preparedStatement.setString(5, "0");
//                                    preparedStatement.setDate(6, getCurrentTimeStamp());
//
//                                    preparedStatement.executeUpdate();
//                                    mydb.execute("commit");
//                                }
//                            }
//                        }
//
            }
//
//                    if (username.equalsIgnoreCase("PLDC")) {
//                        query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF,FILESIZE) "
//                                + "VALUES (?,?,?,?)";
//
//                        preparedStatement = mydb.preparedstmt(query);
//                        preparedStatement.setDate(1, getCurrentTimeStamp());
//                        preparedStatement.setString(2, "DOKSPPB");
//                        preparedStatement.setString(3, CAR);
//                        preparedStatement.setInt(4, fStream.length());
//
//                        preparedStatement.executeUpdate();
//                        mydb.execute("commit");
//                    }
//                    result = CAR + "_" + "Insert Success";
//
//                } catch (Exception e) {
//                    result = CAR + "_" + e.getMessage();
//                    exc.ExcuteError(e.getMessage(), "execute_class_SPBB", result);
//
//                    e.printStackTrace();
//                }
//            }
        } catch (Exception ex) {
        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (mydb != null) {
//                    mydb.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (Exception ex1) {
//            } finally {
//                mydb = null;
//                preparedStatement = null;
//                rs = null;
//            }
//            doc = null;
//            List = null;;
//            ListRes = null;
//            ListHdr = null;
//            ListDtl = null;
//            ListDtlKMS = null;
//            ListDtlCONT = null;
//            iter = null;
//            iterRes = null;
//            iterHdr = null;
//            iterDtl = null;
//            iterDtlKMS = null;
//            iterDtlCONT = null;
//            return result;
            return result;
        }
    }

//    private static java.sql.Date getCurrentTimeStamp() {
//
//        java.util.Date today = new java.util.Date();
//        return new java.sql.Date(today.getTime());
//
//    }
}
