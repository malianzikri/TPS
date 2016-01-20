///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.edii.tools;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import java.util.Iterator;
//import com.edii.db.Db;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
////import com.nsw.Mailbox;
////import com.nsw.ErrorHandler;
//
///**
// *
// * @author Aslichatun Nisa
// */
//public class karantina {
//
//    private final String SQL_COMMIT = "commit";
//    private final String SQL_ROLLBACK = "rollback";
//
//    private final String NODE_NPWP = "/persetujuan/licheader/npwp";
//    private final String NODE_NM_TRADER = "/persetujuan/licheader/nm_trader";
//    private final String NODE_AL_TRADER = "/persetujuan/licheader/alm_trader";
//    private final String NODE_NO_IJIN = "/persetujuan/licheader/no_ijin";
//    private final String NODE_TG_IJIN = "/persetujuan/licheader/tgl_ijin";
//    private final String NODE_KD_UPT = "/persetujuan/krthdr/kd_upt";
//    private final String NODE_NO_DAFTAR_PPK = "/persetujuan/krthdr/no_daftppk";
//    private final String NODE_TG_DAFTAR_PPK = "/persetujuan/krthdr/tg_daftppk";
//    private final String NODE_JN_RESPON = "/persetujuan/krthdr/jn_respon";
//    private final String NODE_NO_RESPON = "/persetujuan/krthdr/no_respon";
//    private final String NODE_TG_RESPON = "/persetujuan/krthdr/tg_respon";
//    private final String NODE_ISI_RESPON = "/persetujuan/krthdr/isi_respon";
//    private final String NODE_NM_PARTNER = "/persetujuan/krthdr/nm_partner";
//    private final String NODE_ALM_PARTNER = "/persetujuan/krthdr/alm_partner";
//    private final String NODE_NEG_PARTNER = "/persetujuan/krthdr/neg_partner";
//    private final String NODE_PEL_BKR = "/persetujuan/krthdr/pel_bkr";
//    private final String NODE_PEL_MUAT = "/persetujuan/krthdr/pel_muat";
//    private final String NODE_TMP_TIMBUN = "/persetujuan/krthdr/tmp_timbun";
//    private final String NODE_MODA = "/persetujuan/krthdr/moda";
//    private final String NODE_NO_ANGKUT = "/persetujuan/krthdr/angkutno";
//    private final String NODE_NM_ANGKUT = "/persetujuan/krthdr/angkutnama";
//    private final String NODE_TG_TIBA = "/persetujuan/krthdr/tg_tiba";
//    private final String NODE_TMP_INSTALASI = "/persetujuan/krthdr/tmp_instalasi";
//    private final String NODE_ALM_INSTALASI = "/persetujuan/krthdr/alm_instalasi";
//    private final String NODE_TUJ_MASUK = "/persetujuan/krthdr/tuj_masuk";
//    private final String NODE_DRH_TUJU = "/persetujuan/krthdr/drh_tuju";
//    private final String NODE_NEG_TUJU = "/persetujuan/krthdr/neg_tuju";
//    private final String NODE_NM_JAB = "/persetujuan/krthdr/nm_jab";
//    private final String NODE_NIP_JAB = "/persetujuan/krthdr/nip_jab";
//    private final String NODE_JAB = "/persetujuan/krthdr/jab";
//
//    private final String NODE_CONTAINER_LIST = "/persetujuan/container/loop";
//    private final String NODE_NO_CONTAINER = "no_cont";
//    private final String NODE_SEGEL = "segel";
//
//    private final String NODE_KEMASAN_LIST = "/persetujuan/kemasan/loop";
//    private final String NODE_JN_KEMASAN = "jn_kemas";
//    private final String NODE_JM_KEMASAN = "jm_kemas";
//
//    private final String NODE_DOKUMEN_LIST = "/persetujuan/dokumen/loop";
//    private final String NODE_KD_DOKUMEN = "kd_dok";
//    private final String NODE_NO_DOKUMEN = "no_dok";
//    private final String NODE_TG_DOKUMEN = "tg_dok";
//    private final String NODE_NEG_DOKUMEN = "neg_dok";
//
//    private final String NODE_KOMODITAS_LIST = "/persetujuan/komoditas/loop";
//    private final String NODE_SERIAL = "serial";
//    private final String NODE_NO_HS = "no_hs";
//    private final String NODE_UR_BRG = "ur_brg";
//    private final String NODE_NM_LATIN = "nm_latin";
//    private final String NODE_KD_SATUAN = "kd_sat";
//    private final String NODE_JM_SATUAN = "jm_sat";
//    private final String NODE_NETTO = "dtl_netto";
//    private final String NODE_SPP = "spp";
//
//    private String KODE_IJIN = "01";
//    private ExcuteProses exc = new ExcuteProses();
//
//    public boolean insertPerijinan(String idGA, String kodeIjin, String data) {
//        this.KODE_IJIN = kodeIjin;
//        return insertIjinImpor(idGA, data);
//    }
//
//    private boolean insertIjinImpor(String idGA, String data) {
//        boolean retVal = false;
//        boolean condition = false;
//        String sqlCommand = "";
//        String kdIjin = this.KODE_IJIN;
//        String kdStatus = "001";
//        String noIjin = "";
//        String tgIjin = "";
//        String negDok = "";
//        String jmSat = "";
//        String netto = "";
//        String jmKemas = "";
//        String statusIjin = "";
//
//        long idIjin = 0;
//        int jmContainer = 0;
//        int jmBarang = 0;
//        Db db = null;
//        //ErrorHandler err = null;
//        Document ijin = null;
//        ResultSet rs = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            //err = new ErrorHandler();
//            ijin = DocumentHelper.parseText(data);
//            noIjin = ijin.selectSingleNode(NODE_NO_IJIN).getText();
//            tgIjin = ijin.selectSingleNode(NODE_TG_IJIN).getText();
//            if (noIjin.equalsIgnoreCase("") || noIjin.equals(null)) {
//                retVal = false;
//            } else {
//
//                Iterator iter = null;
//                Element e = null;
//                try {
//                    db = new Db();
//
//                    //get next id
//                    LicenseHeader lh = null;
//                    try {
//                        lh = new LicenseHeader();
//                        //perlu ditanyakan kdIjin
//                        idIjin = lh.getIdIjin(noIjin, tgIjin, kdIjin);
//                        if (idIjin > 0) {
//                            statusIjin = lh.getStatus(idIjin);
//                            if (statusIjin.equalsIgnoreCase("001")) {
//                                //Delete the existing license to be overwrite with new one
//                                db.execute("DELETE FROM tblppkcon WHERE id_ijin = " + idIjin);
//                                db.execute("DELETE FROM tblppkkms WHERE id_ijin = " + idIjin);
//                                db.execute("DELETE FROM tblppkdok WHERE id_ijin = " + idIjin);
//                                db.execute("DELETE FROM tblppkdtl WHERE id_ijin = " + idIjin);
//                                db.execute("DELETE FROM tblkrthdr WHERE id_ijin = " + idIjin);
//                                db.execute(SQL_COMMIT);
//                                db.execute("DELETE FROM tbllichdr WHERE id_ijin = " + idIjin);
//                                db.execute(SQL_COMMIT);
//                                condition = true;
//                            } else {
//                                condition = false;
//                            }
//                        } else {
//                            //belum selesai
//                            idIjin = lh.getNextVal("seq_id_ijin");
//                            condition = true;
//                        }
//                    } catch (Exception ex) {
//                    } finally {
//                        lh = null;
//                    }
//
//                    //insert tbllichdr
//                    if (condition) {
//                        sqlCommand = "INSERT INTO tbllichdr (id_ijin, no_ijin, tgl_ijin, "
//                                + "npwp, nm_trader, alm_trader, "
//                                + "id_ga, wkproses, kd_ijin, kode_status)"
//                                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
//
//                        preparedStatement = db.preparedstmt(sqlCommand);
//                        preparedStatement.setLong(1, idIjin);
//                        preparedStatement.setString(2, noIjin);
//                        preparedStatement.setDate(3, java.sql.Date.valueOf(tgIjin));
//                        preparedStatement.setString(4, ijin.selectSingleNode(NODE_NPWP).getText());
//                        preparedStatement.setString(5, ijin.selectSingleNode(NODE_NM_TRADER).getText().replaceAll("'", "''"));
//                        preparedStatement.setString(6, ijin.selectSingleNode(NODE_AL_TRADER).getText().replaceAll("'", "''"));
//                        preparedStatement.setString(7, idGA);
//                        preparedStatement.setDate(8, getCurrentTimeStamp());
//                        preparedStatement.setString(9, kdIjin);
//                        preparedStatement.setString(10, kdStatus);
//
//                        preparedStatement.executeUpdate();
//                        condition = db.getProcessValue();
//                        db.execute(SQL_COMMIT);
//                    }
//
//                    //insert tblkrthdr
//                    if (condition) {
//                        sqlCommand = "INSERT INTO tblkrthdr (id_ijin, jn_respon, nm_partner, kd_upt, "
//                                + "alm_partner, neg_partner, pel_bkr, pel_muat, tmp_timbun, moda, "
//                                + "angkutnama, angkutno, tg_tiba, tuj_masuk, no_respon, neg_tuju, "
//                                + "drh_tuju, tg_respon, no_daftppk, tg_daftppk, isi_respon, "
//                                + "nip_jab, nm_jab, jab, tmp_instalasi, alm_instalasi)"
//                                + "VALUES (?,?,?,?,?,?,?,?,?,?,"
//                                + "?,?,?,?,?,?,?,?,?,?,"
//                                + "?,?,?,?,?,?)";
//
//                        preparedStatement = db.preparedstmt(sqlCommand);
//                        preparedStatement.setLong(1, idIjin);
//                        preparedStatement.setString(2, ijin.selectSingleNode(NODE_JN_RESPON).getText());
//                        preparedStatement.setString(3, ijin.selectSingleNode(NODE_NM_PARTNER).getText().replaceAll("'", "''"));
//                        preparedStatement.setString(4, ijin.selectSingleNode(NODE_KD_UPT).getText());
//                        preparedStatement.setString(5, ijin.selectSingleNode(NODE_ALM_PARTNER).getText().replaceAll("'", "''"));
//                        preparedStatement.setString(6, ijin.selectSingleNode(NODE_NEG_PARTNER).getText());
//                        preparedStatement.setString(7, ijin.selectSingleNode(NODE_PEL_BKR).getText());
//                        preparedStatement.setString(8, ijin.selectSingleNode(NODE_PEL_MUAT).getText());
//                        preparedStatement.setString(9, ijin.selectSingleNode(NODE_TMP_TIMBUN).getText());
//                        preparedStatement.setString(10, ijin.selectSingleNode(NODE_MODA).getText());
//                        preparedStatement.setString(11, ijin.selectSingleNode(NODE_NM_ANGKUT).getText());
//                        preparedStatement.setString(12, ijin.selectSingleNode(NODE_NO_ANGKUT).getText());
//                        preparedStatement.setDate(13, java.sql.Date.valueOf(ijin.selectSingleNode(NODE_TG_TIBA).getText()));
//                        preparedStatement.setString(14, ijin.selectSingleNode(NODE_TUJ_MASUK).getText());
//                        preparedStatement.setString(15, ijin.selectSingleNode(NODE_NO_RESPON).getText());
//                        preparedStatement.setString(16, ijin.selectSingleNode(NODE_NEG_TUJU).getText());
//                        preparedStatement.setString(17, ijin.selectSingleNode(NODE_DRH_TUJU).getText());
//                        preparedStatement.setDate(18, java.sql.Date.valueOf(ijin.selectSingleNode(NODE_TG_RESPON).getText()));
//                        preparedStatement.setString(19, ijin.selectSingleNode(NODE_NO_DAFTAR_PPK).getText());
//                        preparedStatement.setDate(20, java.sql.Date.valueOf(ijin.selectSingleNode(NODE_TG_DAFTAR_PPK).getText()));
//                        preparedStatement.setString(21, ijin.selectSingleNode(NODE_ISI_RESPON).getText());
//                        preparedStatement.setString(22, ijin.selectSingleNode(NODE_NIP_JAB).getText());
//                        preparedStatement.setString(23, ijin.selectSingleNode(NODE_NM_JAB).getText());
//                        preparedStatement.setString(24, ijin.selectSingleNode(NODE_JAB).getText());
//                        preparedStatement.setString(25, ijin.selectSingleNode(NODE_TMP_INSTALASI).getText());
//                        preparedStatement.setString(26, ijin.selectSingleNode(NODE_ALM_INSTALASI).getText());
//
//                        preparedStatement.executeUpdate();
//                        condition = db.getProcessValue();
//                        db.execute(SQL_COMMIT);
//                    }
//
//                    //insert tblppkcon
//                    if (condition) {
//                        for (iter = ijin.selectNodes(NODE_CONTAINER_LIST).iterator(); iter.hasNext();) {
//                            jmContainer++;
//                            e = (Element) iter.next();
//                            sqlCommand = "INSERT INTO tblppkcon (id_ijin, no_cont, segel)"
//                                    + "VALUES (?,?,?)";
//
//                            preparedStatement = db.preparedstmt(sqlCommand);
//                            preparedStatement.setLong(1, idIjin);
//                            preparedStatement.setString(2, e.selectSingleNode(NODE_NO_CONTAINER).getText());
//                            preparedStatement.setString(3, e.selectSingleNode(NODE_SEGEL).getText());
//
//                            preparedStatement.executeUpdate();
//                            condition = db.getProcessValue();
//                            if (!condition) {
//                                break;
//                            }
//                        }
//                    }
//
//                    //insert tblppkkms
//                    if (condition) {
//                        for (iter = ijin.selectNodes(NODE_KEMASAN_LIST).iterator(); iter.hasNext();) {
//                            e = (Element) iter.next();
//                            jmKemas = e.selectSingleNode(NODE_JM_KEMASAN).getText();
//                            if (jmKemas.equalsIgnoreCase("") || jmKemas.equals(null)) {
//                                jmKemas = "0";
//                            }
//                            sqlCommand = "INSERT INTO tblppkkms (id_ijin, jn_kemas, jm_kemas) "
//                                    + "VALUES (?,?,?)";
//
//                            preparedStatement = db.preparedstmt(sqlCommand);
//                            preparedStatement.setLong(1, idIjin);
//                            preparedStatement.setString(2, e.selectSingleNode(NODE_JN_KEMASAN).getText());
//                            preparedStatement.setInt(3, Integer.parseInt(jmKemas));
//
//                            //Integer.parseInt(e.selectSingleNode(NODE_JM_KEMASAN).getText()) + ")";
//                            preparedStatement.executeUpdate();
//                            condition = db.getProcessValue();
//                            if (!condition) {
//                                break;
//                            }
//                        }
//                    }
//
//                    //insert tblppkdok
//                    if (condition) {
//                        for (iter = ijin.selectNodes(NODE_DOKUMEN_LIST).iterator(); iter.hasNext();) {
//                            e = (Element) iter.next();
//                            try {
//                                negDok = e.selectSingleNode(NODE_NEG_DOKUMEN).getText();
//                            } catch (Exception edok) {
//                                negDok = "";
//                            }
//                            sqlCommand = "INSERT INTO tblppkdok (id_ijin, kd_dok, no_dok, tg_dok, neg_dok) "
//                                    + "VALUES (?,?,?,?,?)";
//
//                            preparedStatement = db.preparedstmt(sqlCommand);
//                            preparedStatement.setLong(1, idIjin);
//                            preparedStatement.setString(2, e.selectSingleNode(NODE_KD_DOKUMEN).getText());
//                            preparedStatement.setString(3, e.selectSingleNode(NODE_NO_DOKUMEN).getText());
//                            preparedStatement.setDate(4, java.sql.Date.valueOf(e.selectSingleNode(NODE_TG_DOKUMEN).getText()));
//                            preparedStatement.setString(5, negDok);
//
//                            preparedStatement.executeUpdate();
//                            condition = db.getProcessValue();
//                            if (!condition) {
//                                break;
//                            }
//                        }
//                    }
//
//                    //insert tblppkdtl
//                    if (condition) {
//                        for (iter = ijin.selectNodes(NODE_KOMODITAS_LIST).iterator(); iter.hasNext();) {
//                            jmBarang++;
//                            e = (Element) iter.next();
//                            jmSat = e.selectSingleNode(NODE_JM_SATUAN).getText();
//                            if (jmSat.equalsIgnoreCase("") || jmSat.equals(null)) {
//                                jmSat = "0";
//                            }
//                            netto = e.selectSingleNode(NODE_NETTO).getText();
//                            if (netto.equalsIgnoreCase("") || netto.equals(null)) {
//                                netto = "0";
//                            }
//                            sqlCommand = "INSERT INTO tblppkdtl (id_ijin, noseri, no_hs, ur_brg, kd_sat, "
//                                    + "jm_sat, dtl_netto, spp, nm_latin) "
//                                    + "VALUES (?,?,?,?,?,?,?,?)";
//
//                            preparedStatement = db.preparedstmt(sqlCommand);
//                            preparedStatement.setInt(1, Integer.parseInt(e.selectSingleNode(NODE_SERIAL).getText()));
//                            preparedStatement.setString(2, e.selectSingleNode(NODE_NO_HS).getText());
//                            preparedStatement.setString(3, e.selectSingleNode(NODE_UR_BRG).getText());
//                            preparedStatement.setString(4, e.selectSingleNode(NODE_KD_SATUAN).getText());
//                            preparedStatement.setFloat(5, Float.parseFloat(jmSat.replaceAll(",", ".")));
//                            preparedStatement.setFloat(6, Float.parseFloat(netto.replaceAll(",", ".")));
//                            preparedStatement.setString(7, e.selectSingleNode(NODE_SPP).getText());
//                            preparedStatement.setString(8, e.selectSingleNode(NODE_NM_LATIN).getText());
//
//                            preparedStatement.executeUpdate();
//                            condition = db.getProcessValue();
//                            if (!condition) {
//                                break;
//                            }
//                        }
//                    }
//
//                    //update jumlah container dan jumlah barang
//                    if (condition) {
//                        sqlCommand = "UPDATE tblkrthdr SET jm_cont = ?, "
//                                + "jm_brg = ? WHERE id_ijin = ?";
//
//                        preparedStatement = db.preparedstmt(sqlCommand);
//                        preparedStatement.setInt(1, jmContainer);
//                        preparedStatement.setInt(2, jmBarang);
//                        preparedStatement.setLong(3, idIjin);
//
//                        preparedStatement.executeUpdate();
//                        condition = db.getProcessValue();
//                    }
//
//                    //commit transaction
//                    if (condition) {
//                        db.execute(SQL_COMMIT);
//                        //save perijinan into mailbox
//                        // Mailbox mb = null;
////                        try {
////                            mb = new Mailbox();
////                            retVal = mb.insertMailbox("CUSTOMS", idGA, "EXIM RECOMMENDATION", noIjin, data);
////                        } catch (Exception exmb) {
////                            err.mailError("KARANTINA.insertIjinImpor() --> " + exmb.getMessage(), exmb.getStackTrace());
////                        } finally {
////                            mb = null;
////                        }
//                        if (!retVal) {
//                            db.execute(SQL_ROLLBACK);
//                            retVal = false;
//                            db.execute("DELETE FROM tblppkdtl WHERE id_ijin = " + idIjin);
//                            db.execute("DELETE FROM tblppkdok WHERE id_ijin = " + idIjin);
//                            db.execute("DELETE FROM tblppkkms WHERE id_ijin = " + idIjin);
//                            db.execute("DELETE FROM tblppkcon WHERE id_ijin = " + idIjin);
//                            db.execute("DELETE FROM tblkrthdr WHERE id_ijin = " + idIjin);
//                            db.execute(SQL_COMMIT);
//                            db.execute("DELETE FROM tbllichdr WHERE id_ijin = " + idIjin);
//                            db.execute(SQL_COMMIT);
//                        }
//                    } else if (statusIjin.equalsIgnoreCase("001")) {
//                        db.execute(SQL_ROLLBACK);
//                        retVal = false;
//                        db.execute("DELETE FROM tblppkdtl WHERE id_ijin = " + idIjin);
//                        db.execute("DELETE FROM tblppkdok WHERE id_ijin = " + idIjin);
//                        db.execute("DELETE FROM tblppkkms WHERE id_ijin = " + idIjin);
//                        db.execute("DELETE FROM tblppkcon WHERE id_ijin = " + idIjin);
//                        db.execute("DELETE FROM tblkrthdr WHERE id_ijin = " + idIjin);
//                        db.execute(SQL_COMMIT);
//                        db.execute("DELETE FROM tbllichdr WHERE id_ijin = " + idIjin);
//                        db.execute(SQL_COMMIT);
//                    } else {
//                        retVal = false;
//                    }
//                } catch (Exception ex) {
////                    err.mailError("KARANTINA.insertIjinImpor() --> " + ex.getMessage(), ex.getStackTrace());
////                    err.mailError("KARANTINA.insertIjinImpor() --> " + this.KODE_IJIN, data);
//                    db.execute(SQL_ROLLBACK);
//                    retVal = false;
//                    db.execute("DELETE FROM tblppkdtl WHERE id_ijin = " + idIjin);
//                    db.execute("DELETE FROM tblppkdok WHERE id_ijin = " + idIjin);
//                    db.execute("DELETE FROM tblppkkms WHERE id_ijin = " + idIjin);
//                    db.execute("DELETE FROM tblppkcon WHERE id_ijin = " + idIjin);
//                    db.execute("DELETE FROM tblkrthdr WHERE id_ijin = " + idIjin);
//                    db.execute(SQL_COMMIT);
//                    db.execute("DELETE FROM tbllichdr WHERE id_ijin = " + idIjin);
//                    db.execute(SQL_COMMIT);
//                    exc.ExcuteError(ex.getMessage(), "execute_class_karantina", "karantina");
//
//                } finally {
//                    try {
//                        db.close();
//                    } catch (Exception xdb) {
//                    } finally {
//                        db = null;
//                        iter = null;
//                        e = null;
//                    }
//                }
//            }
//        } catch (Exception e) {
////            err.mailError("KARANTINA.insertIjinImpor() --> " + e.getMessage(), e.getStackTrace());
//            exc.ExcuteError(e.getMessage(), "execute_class_karantina", "karantina");
//
////            err.mailError("KARANTINA.insertIjinImpor() --> " + this.KODE_IJIN, data);
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (db != null) {
//                    db.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (Exception ex1) {
//            } finally {
//                db = null;
//                preparedStatement = null;
//                rs = null;
//                ijin = null;
////            err = null;
//                return retVal;
//            }
//
//        }
//    }
//
//    public boolean validatePermission(String car, String id_ga, String no_ijin, String tgl_ijin) {
//        Db db = null;
////        ErrorHandler err = null;
//        String sqlCommand = "";
//        long id_ijin = 0;
//        String _noIjin = "";
//        boolean condition = false;
//        try {
////            err = new ErrorHandler();
//            db = new Db();
//            sqlCommand = "SELECT a.impnpwp, b.id_ijin, b.no_ijin FROM tblpibhdr a, tbllichdr b "
//                    + "WHERE SUBSTR(a.impnpwp, 1, 9) = SUBSTR(b.npwp, 1, 9) AND b.no_ijin = '" + no_ijin + "' "
//                    + "AND a.car = '" + car + "' AND b.tgl_ijin = TO_DATE('" + tgl_ijin + "', 'yyyymmdd') AND b.id_ga = '" + id_ga + "' AND b.kode_status = '001'";
//            db.execute(sqlCommand);
//            if (db.next()) {
//                id_ijin = db.getLong(2);
//                _noIjin = db.getString(3);
//                condition = true;
//            }
//        } catch (Exception ex) {
////            err.mailError("KARANTINA.validatePermission() --> " + ex.getMessage(), ex.getStackTrace());
//        } finally {
//            try {
//                db.close();
//            } catch (Exception xdb) {
//            } finally {
//                db = null;
////                err = null;
//                return condition;
//            }
//        }
//    }
//
//    private static java.sql.Date getCurrentTimeStamp() {
//
//        java.util.Date today = new java.util.Date();
//        return new java.sql.Date(today.getTime());
//
//    }
//}
