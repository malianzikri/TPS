/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.io.File;
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
public class GenerateResponPLP_Tujuan_Tes {

    public String excute(String outboxDir, String kdAsp) throws SQLException, Exception {
        Db mydb = null;
        CreateFile cf = null;
        Tanggalan tg = null;
        Document doc = null;
        Document doc1 = null;
        Element root = null;
        Element row = null;
        Element doclist = null;
        Element detil = null;

        //DOK PLP Element Header
        String RESPONID = null;
        String KD_KANTOR = null;
        String KD_TPS = null;
        String KD_TPS_ASAL = null;
        String GUDANG_TUJUAN = null;
        String NO_PLP = null;
        String TGL_PLP = null;
        String CALL_SIGN = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String TGL_TIBA = null;
        String NO_BC11 = null;
        String TGL_BC11 = null;
        String NO_SURAT = null;
        String TGL_SURAT = null;

        //DOK PLP Element Dtl CONT
        String NO_CONT = null;
        String UK_CONT = null;
        String JNS_CONT = null;
        String NO_POS_BC11 = null;
        String CONSIGNEE = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;
        //DOK PLP Element Dtl KMS
        String JNS_KMS = null;
        String JML_KMS = null;
        String result = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT RESPONID,KD_KANTOR,KD_TPS,KD_TPS_ASAL,NO_PLP,TO_CHAR(TGL_PLP,'YYYYMMDD') AS TGL_PLP,"
                + "NAMA_KAPAL,NO_VOYAGE,CALL_SIGN,TO_CHAR(TGL_TIBA,'YYYYMMDD') AS TGL_TIBA , GUDANG_TUJUAN,NO_BC11, "
                + "TO_CHAR(TGL_BC11,'YYYYMMDD') AS TGL_BC11, NO_SURAT, TO_CHAR(TGL_SURAT,'YYYYMMDD') AS TGL_SURAT "
                + "FROM T_PLP_TES WHERE FL_SEND = ? AND KD_TPS = ? ";
        ResultSet rs = null;
        try {
            mydb = new Db();
            cf = new CreateFile();
            tg = new Tanggalan();
            //Create PLP AJU
            root = new Element("DOCUMENT");
            doc = new Document(root);
            doclist = new Element("RESPONPLP");

            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, "0");
            preparedStatement.setString(2, kdAsp);
            rs = preparedStatement.executeQuery();

            try {

                if (rs.next()) {
                    RESPONID = null == rs.getString("RESPONID") ? "" : rs.getString("RESPONID");
                    KD_KANTOR = null == rs.getString("KD_KANTOR") ? "" : rs.getString("KD_KANTOR");
                    KD_TPS = null == rs.getString("KD_TPS") ? "" : rs.getString("KD_TPS");
                    KD_TPS_ASAL = null == rs.getString("KD_TPS_ASAL") ? "" : rs.getString("KD_TPS_ASAL");
                    NO_PLP = null == rs.getString("NO_PLP") ? "" : rs.getString("NO_PLP");
                    TGL_PLP = null == rs.getString("TGL_PLP") ? "" : rs.getString("TGL_PLP");
                    NM_ANGKUT = null == rs.getString("NAMA_KAPAL") ? "" : rs.getString("NAMA_KAPAL");
                    NO_VOY_FLIGHT = null == rs.getString("NO_VOYAGE") ? "" : rs.getString("NO_VOYAGE");
                    CALL_SIGN = null == rs.getString("CALL_SIGN") ? "" : rs.getString("CALL_SIGN");
                    TGL_TIBA = null == rs.getString("TGL_TIBA") ? "" : rs.getString("TGL_TIBA");
                    GUDANG_TUJUAN = null == rs.getString("GUDANG_TUJUAN") ? "" : rs.getString("GUDANG_TUJUAN");
                    NO_BC11 = null == rs.getString("NO_BC11") ? "" : rs.getString("NO_BC11");
                    TGL_BC11 = null == rs.getString("TGL_BC11") ? "" : rs.getString("TGL_BC11");
                    NO_SURAT = null == rs.getString("NO_SURAT") ? "" : rs.getString("NO_SURAT");
                    TGL_SURAT = null == rs.getString("TGL_SURAT") ? "" : rs.getString("TGL_SURAT");

                    //detil = doclist.addElement("DETIL");
                    detil = new Element("DETIL");

                    query = "SELECT NO_CONT,UK_CONT,JNS_CONT,NO_POS_BC11,CONSIGNEE, NO_BL_AWB, TO_CHAR(TGL_BL_AWB,'YYYYMMDD') AS TGL_BL_AWB FROM T_PLP_CONT_TES WHERE RESPONID = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, RESPONID);
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        NO_CONT = null == rs.getString("NO_CONT") ? "" : rs.getString("NO_CONT");
                        UK_CONT = null == rs.getString("UK_CONT") ? "" : rs.getString("UK_CONT");
                        JNS_CONT = null == rs.getString("JNS_CONT") ? "" : rs.getString("JNS_CONT");
                        NO_POS_BC11 = null == rs.getString("NO_POS_BC11") ? "" : rs.getString("NO_POS_BC11");
                        CONSIGNEE = null == rs.getString("CONSIGNEE") ? "" : rs.getString("CONSIGNEE");
                        NO_BL_AWB = null == rs.getString("NO_BL_AWB") ? "" : rs.getString("NO_BL_AWB");
                        TGL_BL_AWB = null == rs.getString("TGL_BL_AWB") ? "" : rs.getString("TGL_BL_AWB");

                        //row = detil.addElement("CONT");
                        row = new Element("CONT");

                        row.addContent(new Element("NO_CONT").setText(NO_CONT));
                        row.addContent(new Element("UK_CONT").setText(UK_CONT));
                        row.addContent(new Element("JNS_CONT").setText(JNS_CONT));
                        row.addContent(new Element("NO_POS_BC11").setText(NO_POS_BC11));
                        row.addContent(new Element("CONSIGNEE").setText(CONSIGNEE));
                        row.addContent(new Element("NO_BL_AWB").setText(NO_BL_AWB));
                        row.addContent(new Element("TGL_BL_AWB").setText(TGL_BL_AWB));
                        detil.addContent(row);
                    }

                    query = "SELECT JNS_KMS,JML_KMS,NO_BL,TO_CHAR(TGL_BL,'YYYYMMDD') FROM T_PLP_KMS WHERE RESPONID = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, RESPONID);
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        JNS_KMS = null == rs.getString("JNS_KMS") ? "" : rs.getString("JNS_KMS");
                        JML_KMS = null == rs.getString("JML_KMS") ? "" : rs.getString("JML_KMS");
                        NO_BL_AWB = null == rs.getString("NO_BL") ? "" : rs.getString("NO_BL");
                        TGL_BL_AWB = null == rs.getString("TO_CHAR(TGL_BL,'YYYYMMDD')") ? "" : rs.getString("TO_CHAR(TGL_BL,'YYYYMMDD')");

                        row = new Element("KMS");
                        row.addContent(new Element("JNS_KMS").setText(JNS_KMS));
                        row.addContent(new Element("JML_KMS").setText(JML_KMS));
                        row.addContent(new Element("NO_BL_AWB").setText(NO_BL_AWB));
                        row.addContent(new Element("TGL_BL_AWB").setText(TGL_BL_AWB));
                        detil.addContent(row);
//                                        row = detil.addElement("KMS");
//                                        row.addElement("JNS_KMS").setText(JNS_KMS);
//                                        row.addElement("JML_KMS").setText(JML_KMS);
//                                        row.addElement("NO_BL_AWB").setText(NO_BL_AWB);
//                                        row.addElement("TGL_BL_AWB").setText(TGL_BL_AWB);
                    }
                }
                doclist.addContent(detil);
                doc.getRootElement().addContent(doclist);
                if (new XMLOutputter().outputString(doc).length() > 100) {
                    query = "UPDATE T_PLP_TES SET FL_SEND = ? , DATE_SEND = ?  WHERE RESPONID = ? AND FL_SEND = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, "1");
                    preparedStatement.setDate(2, getCurrentTimeStamp());
                    preparedStatement.setString(3, RESPONID);
                    preparedStatement.setString(4, "0");
                    mydb.execute("commit");
                    String filename = RESPONID + "_T_PLP_TES_" + tg.UNIXNUMBER() + ".xml";
                    if (cf.execute(outboxDir + File.separator + filename)) {
                        cf.content(outboxDir + File.separator + filename, new XMLOutputter().outputString(doc));

                        result = new XMLOutputter().outputString(doc);
                    }
                }

            } catch (SQLException e) {
                System.out.println("message" + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("message nian" + e.getMessage());
        } finally {
//                result=doc.asXML();
            doclist.addContent(detil);
            doc.getRootElement().addContent(doclist);
            result = new XMLOutputter().outputString(doc);
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

    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }

    public static void main(String[] args) throws Exception {
        GenerateResponPLP_Tujuan_Tes tes = new GenerateResponPLP_Tujuan_Tes();
        String r = tes.excute("E:/Coba/", "KOJA");
        System.out.println(r);

    }
}
