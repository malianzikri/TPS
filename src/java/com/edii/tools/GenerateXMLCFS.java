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
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Aslichatun Nisa
 */
public class GenerateXMLCFS {

    public String execute(String outboxDir, String kdAsp) throws Exception {
        ExcuteProses exc = new ExcuteProses();

        Db mydb = null;
        CreateFile cf = null;
        Tanggalan tg = null;
        Document doc = null;
        Document doc1 = null;
        Element root = null;
        Element row = null;
        Element doclist = null;
        Element detil = null;

        // element Header
        String KD_KANTOR = null;
        String KD_TPS_ASAL = null;
        String REF_NUMBER = null;
        String NO_SURAT_PLP = null;
        String TGL_SURAT_PLP = null;
        String GUDANG_ASAL = null;
        String KD_TPS_TUJUAN = null;
        String GUDANG_TUJUAN = null;
        String KD_ALASAN_PLP = null;
        String CALL_SIGN = null;
        String NM_ANGKUT = null;
        String NO_VOY_FLIGHT = null;
        String TGL_TIBA = null;
        String NO_BC11 = null;
        String filename = null;
        String YOR_ASAL = null;
        String YOR_TUJUAN = null;
        String TGL_BC11 = null;
        String NM_PEMOHON = null;
        String TIPE_DATA = null;

        String NO_BATAL_PLP = null;
        String TGL_BATAL_PLP = null;
        String ALASAN = null;
        String filename1 = null;

        // element Detail Permohonan
        String NO_CONT = null;
        String UK_CONT = null;
        String JNS_KMS = null;
        String JML_KMS = null;
        String NO_BL_AWB = null;
        String TGL_BL_AWB = null;

        String result = null;
        String query = null;
        String ID = null;

        PreparedStatement preparedStatement = null;
        query = "SELECT KODE_KANTOR,TIPE_DATA,KODE_TPS_ASAL,REF_NUMBER,NO_PLP,"
                + "TO_CHAR(TGL_PLP,'YYYYMMDD'),KODE_GUDANG_ASAL,KODE_TPS_TUJUAN,KODE_GUDANG_TUJUAN,"
                + "KODE_ALASAN_PLP,YOR_ASAL,YOR_TUJUAN,CALL_SIGN,NAMA_KAPAL,NO_VOYAGE,"
                + "TO_CHAR(TGL_TIBA,'YYYYMMDD'),NO_BC11,TO_CHAR(TGL_BC11,'YYYYMMDD'),"
                + "NAMA_PEMOHON FROM T_REQ_UBAH_STATUS WHERE STATUS = ? AND FL_SEND = ? AND "
                + "KODE_TPS_ASAL = ?";
        ResultSet rs = null;
        try {
            mydb = new Db();
            cf = new CreateFile();
            tg = new Tanggalan();
            //Create PLP AJU

            root = new Element("DOCUMENT","loadplp.xsd");
            doc = new Document(root);
            doclist = new Element("LOADPLP");
//            doc = DocumentHelper.createDocument();
//            root = doc.addElement("DOCUMENT").addAttribute("xmlns", "loadplp.xsd");
//            doclist = root.addElement("LOADPLP");

            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setString(1, "300");
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, kdAsp);
            rs = preparedStatement.executeQuery();

            try {
                if (rs.next()) {
                    KD_KANTOR = null == rs.getString("KODE_KANTOR") ? "" : rs.getString("KODE_KANTOR");
                    TIPE_DATA = null == rs.getString("TIPE_DATA") ? "" : rs.getString("TIPE_DATA");
                    KD_TPS_ASAL = null == rs.getString("KODE_TPS_ASAL") ? "" : rs.getString("KODE_TPS_ASAL");
                    REF_NUMBER = null == rs.getString("REF_NUMBER") ? "" : rs.getString("REF_NUMBER");
                    NO_SURAT_PLP = null == rs.getString("NO_PLP") ? "" : rs.getString("NO_PLP");
                    TGL_SURAT_PLP = null == rs.getString("TO_CHAR(TGL_PLP,'YYYYMMDD')") ? "" : rs.getString("TO_CHAR(TGL_PLP,'YYYYMMDD')");
                    GUDANG_ASAL = null == rs.getString("KODE_GUDANG_ASAL") ? "" : rs.getString("KODE_GUDANG_ASAL");
                    KD_TPS_TUJUAN = null == rs.getString("KODE_TPS_TUJUAN") ? "" : rs.getString("KODE_TPS_TUJUAN");
                    GUDANG_TUJUAN = null == rs.getString("KODE_GUDANG_TUJUAN") ? "" : rs.getString("KODE_GUDANG_TUJUAN");
                    KD_ALASAN_PLP = null == rs.getString("KODE_ALASAN_PLP") ? "" : rs.getString("KODE_ALASAN_PLP");
                    YOR_ASAL = null == rs.getString("YOR_ASAL") ? "" : rs.getString("YOR_ASAL");
                    YOR_TUJUAN = null == rs.getString("YOR_TUJUAN") ? "" : rs.getString("YOR_TUJUAN");
                    CALL_SIGN = null == rs.getString("CALL_SIGN") ? "" : rs.getString("CALL_SIGN");
                    NM_ANGKUT = null == rs.getString("NAMA_KAPAL") ? "" : rs.getString("NAMA_KAPAL");
                    NO_VOY_FLIGHT = null == rs.getString("NO_VOYAGE") ? "" : rs.getString("NO_VOYAGE");
                    TGL_TIBA = null == rs.getString("TO_CHAR(TGL_TIBA,'YYYYMMDD')") ? "" : rs.getString("TO_CHAR(TGL_TIBA,'YYYYMMDD')");
                    NO_BC11 = null == rs.getString("NO_BC11") ? "" : rs.getString("NO_BC11");
                    TGL_BC11 = null == rs.getString("TO_CHAR(TGL_BC11,'YYYYMMDD')") ? "" : rs.getString("TO_CHAR(TGL_BC11,'YYYYMMDD')");
                    NM_PEMOHON = null == rs.getString("NAMA_PEMOHON") ? "" : rs.getString("NAMA_PEMOHON");

                    row = new Element("HEADER");
                    row.addContent(new Element("KD_KANTOR").setText(KD_KANTOR));
                    row.addContent(new Element("TIPE_DATA").setText(TIPE_DATA));
                    row.addContent(new Element("KD_TPS_ASAL").setText(KD_TPS_ASAL));
                    row.addContent(new Element("REF_NUMBER").setText(REF_NUMBER));
                    row.addContent(new Element("NO_SURAT_PLP").setText(NO_SURAT_PLP));
                    row.addContent(new Element("TGL_SURAT_PLP").setText(TGL_SURAT_PLP));
                    row.addContent(new Element("GUDANG_ASAL").setText(GUDANG_ASAL));
                    row.addContent(new Element("KD_TPS_TUJUAN").setText(KD_TPS_TUJUAN));
                    row.addContent(new Element("GUDANG_TUJUAN").setText(GUDANG_TUJUAN));
                    row.addContent(new Element("KD_ALASAN_PLP").setText(KD_ALASAN_PLP));
                    row.addContent(new Element("YOR_ASAL").setText(YOR_ASAL));
                    row.addContent(new Element("YOR_TUJUAN").setText(YOR_TUJUAN));
                    row.addContent(new Element("CALL_SIGN").setText(CALL_SIGN));
                    row.addContent(new Element("NM_ANGKUT").setText(NM_ANGKUT));
                    row.addContent(new Element("NO_VOY_FLIGHT").setText(NO_VOY_FLIGHT));
                    row.addContent(new Element("TGL_TIBA").setText(TGL_TIBA));
                    row.addContent(new Element("NO_BC11").setText(NO_BC11));
                    row.addContent(new Element("TGL_BC11").setText(TGL_BC11));
                    row.addContent(new Element("NM_PEMOHON").setText(NM_PEMOHON));
                    doclist.addContent(row);

                    //detil = doclist.addElement("DETIL");
                    detil = new Element("DETIL");
                    query = "SELECT * FROM T_REQ_UBAH_STATUS_CONT WHERE REF_NUMBER = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, REF_NUMBER);
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        NO_CONT = null == rs.getString("NO_CONT") ? "" : rs.getString("NO_CONT");
                        UK_CONT = null == rs.getString("UKURAN_CONT") ? "" : rs.getString("UKURAN_CONT");
                        row = new Element("CONT");
                        row.addContent(new Element("NO_CONT").setText(NO_CONT));
                        row.addContent(new Element("UK_CONT").setText(UK_CONT));
                        detil.addContent(row);
                    }

                    query = "SELECT KODE_KEMASAN,JUMLAH_KEMASAN,NO_BL,TO_CHAR(TGL_BL,'YYYYMMDD') FROM T_REQ_UBAH_STATUS_KMS WHERE REF_NUMBER = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, REF_NUMBER);
                    rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                        JNS_KMS = null == rs.getString("KODE_KEMASAN") ? "" : rs.getString("KODE_KEMASAN");
                        JML_KMS = null == rs.getString("JUMLAH_KEMASAN") ? "" : rs.getString("JUMLAH_KEMASAN");
                        NO_BL_AWB = null == rs.getString("NO_BL") ? "" : rs.getString("NO_BL");
                        TGL_BL_AWB = null == rs.getString("TO_CHAR(TGL_BL,'YYYYMMDD')") ? "" : rs.getString("TO_CHAR(TGL_BL,'YYYYMMDD')");
                        row = new Element("KMS");
                        row.addContent(new Element("JNS_KMS").setText(JNS_KMS));
                        row.addContent(new Element("JML_KMS").setText(JML_KMS));
                        row.addContent(new Element("NO_BL_AWB").setText(NO_BL_AWB));
                        row.addContent(new Element("TGL_BL_AWB").setText(TGL_BL_AWB));
                        detil.addContent(row);
                    }
                }

                //JDOM2
                doclist.addContent(detil);
                doc.getRootElement().addContent(doclist);
                String docasXML = new XMLOutputter().outputString(doc); //convert to string

                if (docasXML.length() > 100) {
                    query = "UPDATE T_REQ_UBAH_STATUS SET STATUS = ?, DATE_SEND = ? , FL_SEND = ? WHERE REF_NUMBER = ? AND FL_SEND = ?";
                    preparedStatement = mydb.preparedstmt(query);
                    preparedStatement.setString(1, "400");
                    preparedStatement.setTimestamp(2, getCurrentTimeStamp());
                    preparedStatement.setString(3, "1");
                    preparedStatement.setString(4, REF_NUMBER);
                    preparedStatement.setString(5, "0");
                    mydb.execute("commit");
                    filename = REF_NUMBER + "_GET_UBAH_STATUS_" + tg.UNIXNUMBER() + ".xml";
                    if (cf.execute(outboxDir + File.separator + filename)) {
                        cf.content(outboxDir + File.separator + filename, docasXML);
                        result = docasXML;
                    }
                }
            } catch (SQLException e) {
                exc.ExcuteError(e.getMessage(), "execute_class_GenerateXMLCFS", REF_NUMBER);
                System.out.println("message" + e.getMessage());
            }
        } catch (Exception e) {
            exc.ExcuteError(e.getMessage(), "execute_class_GenerateXMLCFS", REF_NUMBER);

            System.out.println("message nian" + e.getMessage());
        } finally {
            tg = null;
            cf = null;
            doc = null;
            doc1 = null;
            root = null;
            row = null;
            doclist = null;
            detil = null;
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
