/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.db.Db;
import com.edii.model.ModelGetResponPLP;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.operation.operation;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingGetResponPLPTujuan {

    private ParsingXML px;
    private ModelGetResponPLPTujuan model;
    private SaveData sv;

    public String parseDocumentPLPTujuan(String fStream) throws Exception {

        String result = null;
//        px = new ParsingXML();
//        model = new ModelGetResponPLPTujuan();
//        sv = new operation();
//
//        try {
//
//            if (fStream != null) {
//                try {
//                                       String header = px.getStringParsingXml(fStream, "RESPONPLP>HEADER", "KD_KANTOR,"
//                            + "KD_TPS,KD_TPS_ASAL,GUDANG_TUJUAN,NO_PLP,TGL_PLP,CALL_SIGN,NM_ANGKUT,"
//                            + "NO_VOY_FLIGHT,TGL_TIBA,NO_BC11,TGL_BC11,NO_SURAT,TGL_SURAT");
//                    String[] split_header = header.replace(";", "").split(",");
//                    model.setKd_kantor(split_header[0]);
//                    model.setKd_tps(split_header[1]);
//                    model.setKd_tps_asal(split_header[2]);
//                    model.setGudang_tujuan(split_header[3]);
//                    model.setNo_plp(split_header[4]);
//                    model.setTgl_plp(split_header[5]);
//                    model.setCall_sign(split_header[6]);
//                    model.setNm_angkut(split_header[7]);
//                    model.setNo_voy_flight(split_header[8]);
//                    model.setTgl_tiba(split_header[9]);
//                    model.setNo_bc11(split_header[10]);
//                    model.setTgl_bc11(split_header[11]);
//                    model.setTgl_surat(split_header[12]);
//
//                    String respon_id = sv.savedata_getresponplp_header(model);
//                    String detil_kms = px.getStringParsingXml(fStream, "RESPONPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");
//                    String[] split_detil_kms = detil_kms.split(";");
//
//                    for (String detil : split_detil_kms) {
//                        String[] split_kms = detil.split(",");
//                        model.setJns_kms(split_kms[0]);
//                        model.setJml_kms(split_kms[1]);
//                        model.setNo_bl_awb(split_kms[2]);
//                        model.setTgl_bl_awb(split_kms[3]);
//                        sv.savedata_getresponplp_kms(mo, respon_id);
//                    }
//
//                    String detil_cont = px.getStringParsingXml(fStream, "RESPONPLP>DETIL>CONT", "NO_CONT,UK_CONT,"
//                            + "JNS_CONT,NO_POS_BC11,CONSIGNEE,NO_BL_AWB,TGL_BL_AWB");
//                    String[] split_detil_cont = detil_cont.split(";");
//
//                    for (String detil : split_detil_cont) {
//                        String[] split_cont = detil.split(",");
//                        model.setNo_cont(split_cont[0]);
//                        model.setUk_cont(split_cont[1]);
//                        model.setJns_cont(split_cont[2]);
//                        model.setNo_pos_bc11(split_cont[3]);
//                        model.setConsignee(split_cont[4]);
//                        model.setNo_bl_awb(split_cont[5]);
//                        model.setTgl_bl_awb(split_cont[6]);
//                        sv.savedata_getresponplp_cont(model, respon_id);
//                    }       
//
//                            mydb.execute("SELECT RESPLP_SEQ.NEXTVAL as ID FROM DUAL");
//                            if (mydb.next()) {
//                                responID = mydb.getInt("ID");
//                            }
//
//                            query = "INSERT INTO T_PLP(RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,GUDANG_TUJUAN,NO_PLP,TGL_PLP,NAMA_KAPAL,NO_VOYAGE,CALL_SIGN,TGL_TIBA,RECEIVED_DATE,NO_BC11,TGL_BC11,NO_SURAT,TGL_SURAT)"
//                                    + "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,TO_DATE(?,'YYYYMMDD'),?,TO_DATE(?,'YYYYMMDD'))";
//
//                            preparedStatement = mydb.preparedstmt(query);
//                            preparedStatement.setInt(1, responID);
//                            preparedStatement.setString(2, KD_KANTOR);
//                            preparedStatement.setString(3, KD_TPS);
//                            preparedStatement.setString(4, KD_TPS_ASAL);
//                            preparedStatement.setString(5, GUDANG_TUJUAN);
//                            preparedStatement.setString(6, NO_PLP);
//                            preparedStatement.setString(7, TGL_PLP);
//                            preparedStatement.setString(8, NM_ANGKUT);
//                            preparedStatement.setString(9, NO_VOY_FLIGHT);
//                            preparedStatement.setString(10, CALL_SIGN);
//                            preparedStatement.setString(11, TGL_TIBA);
//                            preparedStatement.setDate(12, getCurrentTimeStamp());
//                            preparedStatement.setString(13, NO_BC11);
//                            preparedStatement.setString(14, TGL_BC11);
//                            preparedStatement.setString(15, NO_SURAT);
//                            preparedStatement.setString(16, TGL_SURAT);
//
//                            preparedStatement.executeUpdate();
//                            mydb.execute("commit");
//                        }
//                        List detil = ListRes.getChildren("DETIL");
//                        for (int k = 0; k < detil.size(); k++) {
//                            ListDtl = (Element) detil.get(k);
//                            List kms = ListDtl.getChildren("KMS");
//                            for (int l = 0; l < kms.size(); l++) {
//                                ListDtlKMS = (Element) kms.get(l);
//                                try {
//                                    JNS_KMS = ListDtlKMS.getChildText("JNS_KMS");
//                                    if (JNS_KMS == null) {
//                                        JNS_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JNS_KMS = "";
//                                }
//                                try {
//                                    JML_KMS = ListDtlKMS.getChildText("JML_KMS");
//                                    if (JML_KMS == null) {
//                                        JML_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JML_KMS = "";
//                                }
//                                try {
//                                    NO_BL_AWB = ListDtlKMS.getChildText("NO_BL_AWB");
//                                    if (NO_BL_AWB == null) {
//                                        NO_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_BL_AWB = "";
//                                }
//                                try {
//                                    TGL_BL_AWB = ListDtlKMS.getChildText("TGL_BL_AWB");
//                                    if (TGL_BL_AWB == null) {
//                                        TGL_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    TGL_BL_AWB = "";
//                                }
//                                query = "INSERT INTO T_PLP_KMS(RESPONID,JNS_KMS,JML_KMS,NO_BL,TGL_BL)"
//                                        + "VALUES (?,?,?,?,TO_DATE(?,'YYYYMMDD'))";
//
//                                preparedStatement = mydb.preparedstmt(query);
//                                preparedStatement.setInt(1, responID);
//                                preparedStatement.setString(2, JNS_KMS);
//                                preparedStatement.setString(3, JML_KMS);
//                                preparedStatement.setString(4, NO_BL_AWB);
//                                preparedStatement.setString(5, TGL_BL_AWB);
//
//                                preparedStatement.executeUpdate();
//                                mydb.execute("commit");
//
//                            }
//                            List cont = ListDtl.getChildren("CONT");
//                            for (int l = 0; l < cont.size(); l++) {
//                                ListDtlCONT = (Element) cont.get(l);
//                                try {
//                                    NO_CONT = ListDtlCONT.getChildText("NO_CONT");
//                                    if (NO_CONT == null) {
//                                        NO_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_CONT = "";
//                                }
//                                try {
//                                    UK_CONT = ListDtlCONT.getChildText("UK_CONT");
//                                    if (UK_CONT == null) {
//                                        UK_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    UK_CONT = "";
//                                }
//                                try {
//                                    JNS_CONT = ListDtlCONT.getChildText("JNS_CONT");
//                                    if (JNS_CONT == null) {
//                                        JNS_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    JNS_CONT = "";
//                                }
//                                try {
//                                    NO_POS_BC11 = ListDtlCONT.getChildText("NO_POS_BC11");
//                                    if (NO_POS_BC11 == null) {
//                                        NO_POS_BC11 = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_POS_BC11 = "";
//                                }
//                                try {
//                                    CONSIGNEE = ListDtlCONT.getChildText("CONSIGNEE");
//                                    if (CONSIGNEE == null) {
//                                        CONSIGNEE = "";
//                                    }
//                                } catch (Exception e) {
//                                    CONSIGNEE = "";
//                                }
//                                try {
//                                    NO_BL_AWB = ListDtlCONT.getChildText("NO_BL_AWB");
//                                    if (NO_BL_AWB == null) {
//                                        NO_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_BL_AWB = "";
//                                }
//                                try {
//                                    TGL_BL_AWB = ListDtlCONT.getChildText("TGL_BL_AWB");
//                                    if (TGL_BL_AWB == null) {
//                                        TGL_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    TGL_BL_AWB = "";
//                                }
//
//                                //DECISION VALIDATION CHARACTER NO_CONT AND UK_CONT
//                                if ((11 - NO_CONT.length()) > -1) {
//                                    NO_CONT = NO_CONT.substring(0, NO_CONT.length());
//                                } else {
//                                    NO_CONT = NO_CONT.substring(0, 11);
//                                }
//                                if ((2 - UK_CONT.length()) > -1) {
//                                    UK_CONT = UK_CONT.substring(0, UK_CONT.length());
//                                } else {
//                                    UK_CONT = UK_CONT.substring(0, 2);
//                                }
//
//                                query = "INSERT INTO T_PLP_CONT(RESPONID,NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE,NO_BL_AWB,TGL_BL_AWB)"
//                                        + "VALUES (?,?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'))";
//
//                                preparedStatement = mydb.preparedstmt(query);
//                                preparedStatement.setInt(1, responID);
//                                preparedStatement.setString(2, NO_CONT);
//                                preparedStatement.setString(3, UK_CONT);
//                                preparedStatement.setString(4, JNS_CONT);
//                                preparedStatement.setString(5, NO_POS_BC11);
//                                preparedStatement.setString(6, CONSIGNEE);
//                                preparedStatement.setString(7, NO_BL_AWB);
//                                preparedStatement.setString(8, TGL_BL_AWB);
//
//                                preparedStatement.executeUpdate();
//                                mydb.execute("commit");
//                            }
//                        }
//
//                    }
//
//                    tgl = new Tanggalan();
//                    query = "INSERT INTO MIBILLING (EDIFACTTIME,APRF,SNRF)"
//                            + "VALUES (?,?,?)";
//
//                    preparedStatement = mydb.preparedstmt(query);
//                    preparedStatement.setDate(1, getCurrentTimeStamp());
//                    preparedStatement.setString(2, "RESPLP");
//                    preparedStatement.setString(3, KD_TPS + "" + tgl.getNow("yyyyMMddhhms"));
//
//                    preparedStatement.executeUpdate();
//                    mydb.execute("commit");
//
//                    result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + "Insert Success";
//
//                } catch (Exception e) {
//                    result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + e.getMessage();
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception ex) {
//            result = KD_TPS + "" + tgl.getNow("yyyyMMddhhms") + "_" + ex.getMessage();
//            exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLResponPLP_Tujuan", result);
//
//            ex.printStackTrace();
//        } finally {
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
//            List = null;
//            doc = null;
        return result;
//        }
    }

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
