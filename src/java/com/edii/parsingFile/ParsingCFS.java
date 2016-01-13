///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.edii.parsingfile;
//
//import com.edii.db.Db;
//import com.edii.tools.ExcuteProses;
//import com.edii.tools.ResCFS;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.StringReader;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Iterator;
//import java.util.List;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.jdom2.input.SAXBuilder;
//
///**
// *
// * @author Aslichatun Nisa
// */
//public class ParsingCFS {
//
//    private ExcuteProses exc = new ExcuteProses();
//
//    public String parseDocument(String fStream) throws Exception {
//
//        Db mydb = null;
//        Document doc = null;
//        Element List = null;;
//        Element ListRes = null;
//        Element ListHdr = null;
//        Element ListDtl = null;
//        Element ListDtlKMS = null;
//        Element ListDtlCONT = null;
//        Iterator iter = null;
//        Iterator iterRes = null;
//        Iterator iterHdr = null;
//        Iterator iterDtl = null;
//        Iterator iterDtlKMS = null;
//        Iterator iterDtlCONT = null;
//        String query = null;
//
//        //DOK PLP Element Header
//        String KD_KANTOR = null;
//        String TIPE_DATA = null;
//        String KD_TPS_ASAL = null;
//        String REF_NUMBER = null;
//        String NO_SURAT = null;
//        String TGL_SURAT = null;
//        String GUDANG_ASAL = null;
//        String KD_TPS_TUJUAN = null;
//        String GUDANG_TUJUAN = null;
//        String KD_ALASAN_PLP = null;
//        String YOR_ASAL = null;
//        String YOR_TUJUAN = null;
//        String CALL_SIGN = null;
//        String NM_ANGKUT = null;
//        String NO_VOY_FLIGHT = null;
//        String TGL_TIBA = null;
//        String NO_BC11 = null;
//        String TGL_BC11 = null;
//        String NM_PEMOHON = null;
//
//        //DOK PLP Element Dtl KMS
//        String JNS_KMS = null;
//        String JML_KMS = null;
//        String NO_BL_AWB = null;
//        String TGL_BL_AWB = null;
//
//        //DOK PLP Element Dtl CONT
//        String NO_CONT = null;
//        String UK_CONT = null;
//
//        String result = null;
//        int count = 0;
//        boolean duplicateRef = false;
//        String tempRefNumber = null;
//
//        ResCFS res = null;
//        ResultSet rs = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            res = new ResCFS();
//            mydb = new Db();
//            SAXBuilder builder = new SAXBuilder();
//            if (fStream != null) {
//                try {
//                    doc = builder.build(new StringReader(fStream));
//                    Element rootNode = doc.getRootElement();
//                    List list = rootNode.getChildren("LOADUBAHSTATUS");
//                    for (int i = 0; i < list.size(); i++) {
//                        ListRes = (Element) list.get(i);
//                        List header = ListRes.getChildren("HEADER");
//                        for (int j = 0; j < header.size(); j++) {
//                            ListHdr = (Element) header.get(j);
//                            try {
//                                //<KD_KANTOR />
//                                KD_KANTOR = ListHdr.getChildText("KD_KANTOR");
//                                if (KD_KANTOR == null) {
//                                    KD_KANTOR = "";
//                                }
//                            } catch (Exception e) {
//                                KD_KANTOR = "";
//                            }
//                            try {
//                                //<KD_TP_ASAL />
//                                KD_TPS_ASAL = ListHdr.getChildText("KD_TPS_ASAL");
//                                if (KD_TPS_ASAL == null) {
//                                    KD_TPS_ASAL = "";
//                                    result = res.KdRes("004");
//                                    break;
//                                } else {
//                                    query = "SELECT * FROM REFF_TPS WHERE KD_TPS = ? ";
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, KD_TPS_ASAL);
//                                    rs = preparedStatement.executeQuery();
//
//                                    if (!rs.next()) {
//                                        result = res.KdRes("004");
//                                        break;
//                                    }
//                                }
//                            } catch (Exception e) {
//                                KD_TPS_ASAL = "";
//                            }
//                            try {
//                                //<REF_NUMBER />
//                                REF_NUMBER = ListHdr.getChildText("REF_NUMBER");
//                                if (REF_NUMBER == null) {
//                                    REF_NUMBER = "";
//                                }
//                            } catch (Exception e) {
//                                REF_NUMBER = "";
//                            }
//                            try {
//                                //<NO_SURAT />
//                                NO_SURAT = ListHdr.getChildText("NO_SURAT");
//                                if (NO_SURAT == null) {
//                                    NO_SURAT = "";
//                                }
//                            } catch (Exception e) {
//                                NO_SURAT = "";
//                            }
//                            try {
//                                //<TGL_SURAT />
//                                TGL_SURAT = ListHdr.getChildText("TGL_SURAT");
//                                if (TGL_SURAT == null) {
//                                    TGL_SURAT = "";
//                                }
//                            } catch (Exception e) {
//                                TGL_SURAT = "";
//                            }
//                            try {
//                                //<GUDANG_ASAL>
//                                GUDANG_ASAL = ListHdr.getChildText("GUDANG_ASAL");
//                                if (GUDANG_ASAL == null) {
//                                    GUDANG_ASAL = "";
//                                    result = res.KdRes("005");
//                                    break;
//                                } else {
//                                    query = "SELECT * FROM REFF_GUDANG WHERE KD_GUDANG = ? ";
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, GUDANG_ASAL);
//                                    rs = preparedStatement.executeQuery();
//
//                                    if (!rs.next()) {
//                                        result = res.KdRes("005");
//                                        break;
//                                    }
//                                }
//                            } catch (Exception e) {
//                                GUDANG_ASAL = "";
//                            }
//                            try {
//                                //<KD_TPS_TUJUAN>
//                                KD_TPS_TUJUAN = ListHdr.getChildText("KD_TPS_TUJUAN");
//                                if (KD_TPS_TUJUAN == null) {
//                                    KD_TPS_TUJUAN = "";
//                                    result = res.KdRes("006");
//                                    break;
//                                } else {
//                                    query = "SELECT * FROM REFF_TPS WHERE KD_TPS = ? ";
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, KD_TPS_TUJUAN);
//                                    rs = preparedStatement.executeQuery();
//
//                                    if (!rs.next()) {
//                                        result = res.KdRes("006");
//                                        break;
//                                    }
//                                }
//                            } catch (Exception e) {
//                                KD_TPS_TUJUAN = "";
//                            }
//                            try {
//                                //<GUDANG_TUJUAN />
//                                GUDANG_TUJUAN = ListHdr.getChildText("GUDANG_TUJUAN");
//                                if (GUDANG_TUJUAN == null) {
//                                    GUDANG_TUJUAN = "";
//                                    result = res.KdRes("007");
//                                    break;
//                                } else {
//                                    query = "SELECT * FROM REFF_GUDANG WHERE KD_GUDANG =  ? ";
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, GUDANG_TUJUAN);
//                                    rs = preparedStatement.executeQuery();
//
//                                    if (!rs.next()) {
//                                        result = res.KdRes("007");
//                                        break;
//                                    }
//                                }
//                            } catch (Exception e) {
//                                GUDANG_TUJUAN = "";
//                            }
//                            try {
//                                //<CALL_SIGN />
//                                CALL_SIGN = ListHdr.getChildText("CALL_SIGN");
//                                if (CALL_SIGN == null) {
//                                    CALL_SIGN = "";
//                                }
//                            } catch (Exception e) {
//                                CALL_SIGN = "";
//                            }
//                            try {
//                                //<NM_ANGKUT />
//                                NM_ANGKUT = ListHdr.getChildText("NM_ANGKUT");
//                                if (NM_ANGKUT == null) {
//                                    NM_ANGKUT = "";
//                                }
//                            } catch (Exception e) {
//                                NM_ANGKUT = "";
//                            }
//                            try {
//                                //<NO_VOY_FLIGHT />
//                                NO_VOY_FLIGHT = ListHdr.getChildText("NO_VOY_FLIGHT");
//                                if (NO_VOY_FLIGHT == null) {
//                                    NO_VOY_FLIGHT = "";
//                                }
//                            } catch (Exception e) {
//                                NO_VOY_FLIGHT = "";
//                            }
//                            try {
//                                //<TGL_TIBA />
//                                TGL_TIBA = ListHdr.getChildText("TGL_TIBA");
//                                if (TGL_TIBA == null) {
//                                    TGL_TIBA = "";
//                                }
//                            } catch (Exception e) {
//                                TGL_TIBA = "";
//                            }
//                            try {
//                                //<NO_BC11 />
//                                NO_BC11 = ListHdr.getChildText("NO_BC11");
//                                if (NO_BC11 == null) {
//                                    NO_BC11 = "";
//                                }
//                            } catch (Exception e) {
//                                NO_BC11 = "";
//                            }
//                            try {
//                                //<TGL_BC11 />
//                                TGL_BC11 = ListHdr.getChildText("TGL_BC11");
//                                if (TGL_BC11 == null) {
//                                    TGL_BC11 = "";
//                                }
//                            } catch (Exception e) {
//                                TGL_BC11 = "";
//                            }
//                            try {
//                                //<NM_PEMOHON />
//                                NM_PEMOHON = ListHdr.getChildText("NM_PEMOHON");
//                                if (NM_PEMOHON == null) {
//                                    NM_PEMOHON = "";
//                                }
//                            } catch (Exception e) {
//                                NM_PEMOHON = "";
//                            }
//                            duplicateRef = false;
//                            query = "SELECT * FROM T_REQ_UBAH_STATUS WHERE REF_NUMBER =  ? ";
//                            preparedStatement = mydb.preparedstmt(query);
//                            preparedStatement.setString(1, REF_NUMBER);
//                            rs = preparedStatement.executeQuery();
//                            if (!rs.next()) {
//                                duplicateRef = true;
//                                try {
//                                    query = "INSERT INTO T_REQ_UBAH_STATUS(REF_NUMBER,NO_PLP,TGL_PLP,KODE_TPS_ASAL,"
//                                            + "KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,KODE_GUDANG_TUJUAN,NAMA_KAPAL,NO_VOYAGE,"
//                                            + "TGL_TIBA,CALL_SIGN,KODE_KANTOR,NO_BC11,TGL_BC11,"
//                                            + "NAMA_PEMOHON,STATUS,RECEIVED_DATE)"
//                                            + "VALUES (?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?)";
//
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, REF_NUMBER);
//                                    preparedStatement.setString(2, NO_SURAT);
//                                    preparedStatement.setString(3, TGL_SURAT);
//                                    preparedStatement.setString(4, KD_TPS_ASAL);
//                                    preparedStatement.setString(5, GUDANG_ASAL);
//                                    preparedStatement.setString(6, KD_TPS_TUJUAN);
//                                    preparedStatement.setString(7, GUDANG_TUJUAN);
//                                    preparedStatement.setString(8, NM_ANGKUT);
//                                    preparedStatement.setString(9, NO_VOY_FLIGHT);
//                                    preparedStatement.setString(10, TGL_TIBA);
//                                    preparedStatement.setString(11, CALL_SIGN);
//                                    preparedStatement.setString(12, KD_KANTOR);
//                                    preparedStatement.setString(13, NO_BC11);
//                                    preparedStatement.setString(14, TGL_BC11);
//                                    preparedStatement.setString(15, NM_PEMOHON);
//                                    preparedStatement.setString(16, "300");
//                                    preparedStatement.setDate(17, getCurrentTimeStamp());
//                                    preparedStatement.executeUpdate();
//                                    mydb.execute("commit");
//                                    result = "Proses Berhasil Refnumber " + REF_NUMBER;
//                                } catch (Exception e) {
//                                    query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES(?,?,?,?,?)";
//
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setString(1, "XMLPLPBATAL");
//                                    preparedStatement.setString(2, "Parsing Dok PLP");
//                                    preparedStatement.setString(3, "Insert DB Header");
//                                    preparedStatement.setString(4, "RefNumber" + REF_NUMBER + " ; Error " + e.getMessage());
//                                    preparedStatement.setDate(5, getCurrentTimeStamp());
//
//                                    preparedStatement.executeUpdate();
//                                    mydb.execute("commit");
//                                    result = res.KdRes("101");
//                                }
//                            } else {
//                                if (tempRefNumber == null) {
//                                    tempRefNumber = REF_NUMBER + ";";
//                                    //Jika RefNumber sudah Ada
//                                    result = res.KdRes("103");
//                                } else {
//                                    tempRefNumber = REF_NUMBER + ";" + tempRefNumber;
//                                }
//                            }
//                        }
//                        List detil = ListRes.getChildren("DETIL");
//                        for (int k = 0; k < detil.size(); k++) {
//                            ListDtl = (Element) detil.get(k);
//                            List kms = ListDtl.getChildren("KMS");
//                            for (int l = 0; l < kms.size(); l++) {
//                                ListDtlKMS = (Element) kms.get(l);
//                                try {
//                                    //<JNS_KMS />
//                                    JNS_KMS = ListDtlKMS.getChildText("JNS_KMS");
//                                    if (JNS_KMS == null) {
//                                        JNS_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JNS_KMS = "";
//                                }
//                                try {
//                                    //<JML_KMS />
//                                    JML_KMS = ListDtlKMS.getChildText("JML_KMS");
//                                    if (JML_KMS == null) {
//                                        JML_KMS = "";
//                                    }
//                                } catch (Exception e) {
//                                    JML_KMS = "";
//                                }
//                                try {
//                                    //<NO_BL_AWB />
//                                    NO_BL_AWB = ListDtlKMS.getChildText("NO_BL_AWB");
//                                    if (NO_BL_AWB == null) {
//                                        NO_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_BL_AWB = "";
//                                }
//                                try {
//                                    //<TGL_BL_AWB />
//                                    TGL_BL_AWB = ListDtlKMS.getChildText("TGL_BL_AWB");
//                                    if (TGL_BL_AWB == null) {
//                                        TGL_BL_AWB = "";
//                                    }
//                                } catch (Exception e) {
//                                    TGL_BL_AWB = "";
//                                }
//                                if (duplicateRef == true) {
//                                    try {
//                                        query = "INSERT INTO T_REQ_UBAH_STATUS_KMS(REF_NUMBER,KODE_KEMASAN,NO_BL,TGL_BL,JUMLAH_KEMASAN) VALUES(?,?,?,TO_DATE(?,'YYYYMMDD'),?)";
//
//                                        preparedStatement = mydb.preparedstmt(query);
//                                        preparedStatement.setString(1, REF_NUMBER);
//                                        preparedStatement.setString(2, JNS_KMS);
//                                        preparedStatement.setString(3, NO_BL_AWB);
//                                        preparedStatement.setString(4, TGL_BL_AWB);
//                                        preparedStatement.setString(5, JML_KMS);
//
//                                        preparedStatement.executeUpdate();
//                                        mydb.execute("commit");
//
//                                    } catch (Exception e) {
//                                        query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES(?,?,?,?,?)";
//
//                                        preparedStatement = mydb.preparedstmt(query);
//                                        preparedStatement.setString(1, "XMLPLPBATAL");
//                                        preparedStatement.setString(2, "Parsing Dok PLP");
//                                        preparedStatement.setString(3, "Insert DB Kms");
//                                        preparedStatement.setString(4, "RefNumber" + REF_NUMBER + " ; Error " + e.getMessage());
//                                        preparedStatement.setDate(5, getCurrentTimeStamp());
//
//                                        preparedStatement.executeUpdate();
//                                        mydb.execute("commit");
//                                        result = res.KdRes("009");
//                                    }
//                                }
//                            }
//                            List cont = ListDtl.getChildren("CONT");
//                            for (int l = 0; l < cont.size(); l++) {
//                                ListDtlCONT = (Element) cont.get(l);
//                                try {
//                                    //<NO_CONT />
//                                    NO_CONT = ListDtlCONT.getChildText("NO_CONT");
//                                    if (NO_CONT == null) {
//                                        NO_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    NO_CONT = "";
//                                }
//                                try {
//                                    //<UK_CONT />
//                                    UK_CONT = ListDtlCONT.getChildText("UK_CONT");
//                                    if (UK_CONT == null) {
//                                        UK_CONT = "";
//                                    }
//                                } catch (Exception e) {
//                                    UK_CONT = "";
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
//                                if (duplicateRef) {
//                                    try {
//                                        query = "INSERT INTO T_REQ_UBAH_STATUS_CONT(REF_NUMBER,NO_CONT,UKURAN_CONT) VALUES(?,?,?)";
//
//                                        preparedStatement = mydb.preparedstmt(query);
//                                        preparedStatement.setString(1, REF_NUMBER);
//                                        preparedStatement.setString(2, NO_CONT);
//                                        preparedStatement.setString(3, UK_CONT);
//
//                                        preparedStatement.executeUpdate();
//                                        mydb.execute("commit");
//                                        count++;
//                                    } catch (Exception e) {
//                                        query = "INSERT INTO TPSLOG (ERROR_CODE,PROCESS_NAME,SUB_PROCESS_NAME,ERROR_DESC,LOG_DATE) VALUES(?,?,?,?,?)";
//
//                                        preparedStatement = mydb.preparedstmt(query);
//                                        preparedStatement.setString(1, "UploadMohonPLP");
//                                        preparedStatement.setString(2, "Parsing Dok PLP");
//                                        preparedStatement.setString(3, "Insert DB Cont");
//                                        preparedStatement.setString(4, "Jumlah Cont Ex" + count + " ; Error " + e.getMessage());
//                                        preparedStatement.setDate(5, getCurrentTimeStamp());
//
//                                        preparedStatement.executeUpdate();
//                                        mydb.execute("commit");
//                                        result = res.KdRes("009");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    result = res.KdRes("002");
//                     exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLCFS", result);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//             exc.ExcuteError(ex.getMessage(), "execute_class_ParsingXMLCFS", result);
//        } finally {
//            try {
//                if (mydb != null) {
//                    mydb.close();
//                }
//            } catch (Exception ex1) {
//            } finally {
//                mydb = null;
//            }
//            List = null;
//            doc = null;
//            return result;
//        }
//    }
//
//    private static java.sql.Date getCurrentTimeStamp() {
//
//        java.util.Date today = new java.util.Date();
//        return new java.sql.Date(today.getTime());
//
//    }
////     public static void main(String[] args) {
////                File inFile = null;
////        BufferedReader input = null;
////        String content = "";
////        String content_final = "";
////
////        try {
////            inFile = new File("D:/UJI/MAL0.UploadMohonPLP.06302014153112131.XML");
////            if (inFile.isFile()) {
////                input = new BufferedReader(new FileReader(inFile));
////                while ((content = input.readLine()) != null) {
////                    content_final += content;
////                }
////                input.close();
////                inFile = null;
////            }
////            System.out.println(content_final);
////            //tps t = new tps();
////            //String r = t.UploadUbahStatus(content_final, "cds", "cdspassword");
////            ParsingCFS cfs = new ParsingCFS();
////            String r = cfs.parseDocument(content_final);
////            System.out.println(r);
////        } catch (Exception e) {
////        } finally {
////            input = null;
////            inFile = null;
////
////        }
////    }
//}
