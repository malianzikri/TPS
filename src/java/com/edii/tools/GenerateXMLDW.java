/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Aslichatun Nisa
 */
public class GenerateXMLDW {

    public String excute(String RefNumber, String responId) throws Exception {
        ExcuteProses exc = new ExcuteProses();

        Db mydb = null;
        Db mydbdetail = null;

        Document doc = null;
        Element root = null;
        Element row = null;
        Element doclist = null;
        Element detil = null;
        // element Header
        String KD_TPS_ASAL = null;
        String GUDANG_ASAL = null;
        String KD_TPS_TUJUAN = null;
        String GUDANG_TUJUAN = null;
        String CALL_SIGN = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String REF_NUMBER = null;
        String RESPONID = null;
        // element Detail Permohonan
        String NO_CONT = null;
        String UK_CONT = null;
        String STATUS_PLP = null;
        String result = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT A.KODE_TPS_ASAL , A.KODE_GUDANG_ASAL , A.CALL_SIGN, A.NAMA_KAPAL, "
                + "A.NO_VOYAGE, A.KODE_TPS_TUJUAN, A.KODE_GUDANG_TUJUAN, B.REF_NUMBER, B.RESPONID   "
                + "FROM T_REQUEST_PLP A "
                + "INNER JOIN T_RESPON_PLP B ON A.REF_NUMBER = B.REF_NUMBER "
                + "WHERE A.KODE_TPS_ASAL <> ? AND B.FL_SEND = ?"
                + "AND B.REF_NUMBER = ? AND B.RESPONID =?";
        ResultSet rs = null;
        try {
            mydb = new Db();
            //Create PLP AJU
            root = new Element("DOCUMENT");
            doc = new Document(root);
            doclist = new Element("RESPLP");
            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, "PLDC");
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, RefNumber);
            preparedStatement.setString(4, responId);
            rs = preparedStatement.executeQuery();

            try {
                if (rs.next()) {
                    KD_TPS_ASAL = null == rs.getString("KODE_TPS_ASAL") ? "" : rs.getString("KODE_TPS_ASAL");
                    GUDANG_ASAL = null == rs.getString("KODE_GUDANG_ASAL") ? "" : rs.getString("KODE_GUDANG_ASAL");
                    KD_TPS_TUJUAN = null == rs.getString("KODE_TPS_TUJUAN") ? "" : rs.getString("KODE_TPS_TUJUAN");
                    GUDANG_TUJUAN = null == rs.getString("KODE_GUDANG_TUJUAN") ? "" : rs.getString("KODE_GUDANG_TUJUAN");
                    CALL_SIGN = null == rs.getString("CALL_SIGN") ? "" : rs.getString("CALL_SIGN");
                    NM_ANGKUT = null == rs.getString("NAMA_KAPAL") ? "" : rs.getString("NAMA_KAPAL");
                    NO_VOY_FLIGHT = null == rs.getString("NO_VOYAGE") ? "" : rs.getString("NO_VOYAGE");
                    REF_NUMBER = null == rs.getString("REF_NUMBER") ? "" : rs.getString("REF_NUMBER");
                    RESPONID = null == rs.getString("RESPONID") ? "" : rs.getString("RESPONID");

                    row = new Element("HEADER");
                    row.addContent(new Element("KD_TPS_ASAL").setText(KD_TPS_ASAL));
                    row.addContent(new Element("GUDANG_ASAL").setText(GUDANG_ASAL));
                    row.addContent(new Element("KD_TPS_TUJUAN").setText(KD_TPS_TUJUAN));
                    row.addContent(new Element("GUDANG_TUJUAN").setText(GUDANG_TUJUAN));
                    row.addContent(new Element("CALL_SIGN").setText(CALL_SIGN));
                    row.addContent(new Element("NM_ANGKUT").setText(NM_ANGKUT));
                    row.addContent(new Element("NO_VOY_FLIGHT").setText(NO_VOY_FLIGHT));
                    doclist.addContent(row);

                    detil = new Element("DETIL");
                    //detil = doclist.addElement("DETIL");

                    query = "SELECT * FROM T_RESPON_PLP_CONT WHERE RESPONID = ? AND STATUS_PLP = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, RESPONID);
                    preparedStatement.setString(2, "Y");
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        NO_CONT = null == rs.getString("NO_CONT") ? "" : rs.getString("NO_CONT");
                        UK_CONT = null == rs.getString("UK_CONT") ? "" : rs.getString("UK_CONT");
                        STATUS_PLP = null == rs.getString("STATUS_PLP") ? "" : rs.getString("STATUS_PLP");

                        row = new Element("CONT");
                        row.addContent(new Element("NO_CONT").setText(NO_CONT));
                        row.addContent(new Element("UK_CONT").setText(UK_CONT));
                        row.addContent(new Element("STATUS_PLP").setText(STATUS_PLP));
                        detil.addContent(row);

                    }

                }
                doclist.addContent(detil);
                doc.getRootElement().addContent(doclist);
                String docasXML = new XMLOutputter().outputString(doc);
                if (docasXML.length() > 100) {
                    query = "UPDATE T_RESPON_PLP SET FL_SEND = ? , DATE_SEND = ? WHERE RESPONID = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, "1");
                    preparedStatement.setTimestamp(2, getCurrentTimeStamp());
                    preparedStatement.setString(3, RESPONID);
                    mydb.execute("commit");
                    result = docasXML;
                }
            } catch (SQLException e) {
                exc.ExcuteError(e.getMessage(), "execute_class_GenerateXMLDW", REF_NUMBER);

                System.out.println("message" + e.getMessage());
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_GenerateXMLDW", REF_NUMBER);

            System.out.println("message nian" + e.getMessage());
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (mydb != null) {
                mydb.close();
            }
            if (rs != null) {
                rs.close();
            }
            return result;
        }
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }
}
