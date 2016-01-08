/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingFile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoarriCodecoContainer;
import com.edii.model.ModelCoarriCodecoSHL;
import com.edii.operation.db.operation;
import com.edii.tools.Log;
import com.nsw.db.Db;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingCoarriCodecoSHL {

    private ParsingXML px;
    private ModelCoarriCodecoSHL model;
    private SaveData sv;

    public String parseDocument(String fStream) throws Exception {
        String result = "";
        px = new ParsingXML();
        model = new ModelCoarriCodecoSHL();
        sv = new operation();
        String id = "";
        try {
//            log = new Log();
//            mydb = new Db("SHL.properties");
//            SAXBuilder builder = new SAXBuilder();
            if (fStream != null) {
//                try {
//                    if (fStream != null) {
                ArrayList<String> header = new ArrayList();
                header = px.xmlParsing(fStream, "COCOCONT>HEADER,", "KD_DOK,KD_TPS,"
                        + "NO_VOY_FLIGHT,CALL_SIGN,KD_GUDANG,ETA,ETD,TGL_TIBA,VESSEL_CODE,VESSEL_NAME");
                String[] split_header = header.get(0).split(",");
                model.setKd_dok(split_header[0]);
                model.setKd_tps(split_header[1]);
                model.setNo_voy_flight(split_header[2]);
                model.setCall_sign(split_header[3]);
                model.setCall_sign(split_header[4]);
                model.setKd_gudang(split_header[5]);
                model.setEta(split_header[6]);
                model.setEtd(split_header[7]);

                if (sv.cekdata_coarricodecshl_header(model)) {
                    
                } else {
                    id = sv.savedata_coarricodecshl_header(model);
                }
                
                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "COCOCONT>DETIL>CONT",
                                "KD_DOK,NO_CONT,UK_CONT,ISO_CODE,NO_SEGEL,BRUTO,OWNER,LOC_IN_VESSEL,LOC_IN_YARD,"
                                + "WK_INOUT,TRUCKER_NAME,NO_POL,REF_NUMBER,POL,POT,POD,NO_BL_AWB,TGL_BL_AWB,"
                                + "BOOKING_NO,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,ID_CONSIGNEE,CONSIGNEE,NO_BC11,"
                                + "TGL_BC11,NO_POS_BC11,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,FL_CONT_KOSONG,"
                                + "TEMPERATURE,SHIPPER,STAT_CONT,REMARKS,PORT_ORIGIN,PORT_FINAL_DESTINATION");
                for (String detil : detil_cont) {
                            String[] split_cont = detil.split(",");
                            model.setKd_dok(split_cont[0]);
                            model.setNo_cont(split_cont[1]);
                            model.setUk_cont(split_cont[2]);
                            model.setIso_code(split_cont[3]);
                            model.setNo_segel(split_cont[4]);
                            model.setBruto(split_cont[5]);
                            model.setOwner(split_cont[6]);
                            model.setLoc_in_vessel(split_cont[7]);
                            model.setLoc_in_yard(split_cont[8]);
                            model.setWk_inout(split_cont[9]);
                            model.setTrucker_name(split_cont[10]);
                            model.setNo_pol(split_cont[11]);
                            model.setRef_number(split_cont[12]);
                            model.setPel_muat(split_cont[13]);
                            model.setPel_transit(split_cont[14]);
                            model.setPel_bongkar(split_cont[15]);
                            model.setNo_bl_awb(split_cont[16]);
                            model.setTgl_bl_awb(split_cont[17]);
                            model.setBooking_no(split_cont[18]);
                            model.setNo_master_bl_awb(split_cont[19]);
                            model.setId_consignee(split_cont[20]);
                            model.setConsignee(split_cont[21]);
                            model.setNo_bc11(split_cont[22]);
                            model.setTgl_bc11(split_cont[23]);
                            model.setNo_pos_bc_11(split_cont[24]);
                            model.setKd_dok_inout(split_cont[25]);
                            model.setNo_dok_inout(split_cont[26]);
                            model.setTgl_dok_inout(split_cont[27]);
                            model.setFl_cont_kosong(split_cont[28]);
                            model.setTemperature(split_cont[29]);
                            model.setShipper(split_cont[30]);
                            model.setStat_cont(split_cont[31]);
                            model.setRemarks(split_cont[32]);
                            model.setPort_origin(split_cont[33]);
                            model.setPort_final_destination(split_cont[34]);
                            sv.savedata_coarricodecshl_con(model, id);
                }
                
//
//
//                        query = "SELECT ID FROM COCOHDR WHERE "
//                                + "VESSEL_CODE = ? AND "
//                                + "VESSEL_NAME = ? AND "
//                                + "VOYAGE_NO = ? AND "
//                                + "ETA = ? AND "
//                                + "ETD = ? ";
//
//                        preparedStatement = mydb.preparedstmt(query);
//                        preparedStatement.setString(1, VESSEL_CODE);
//                        preparedStatement.setString(2, VESSEL_NAME);
//                        preparedStatement.setString(3, NO_VOY_FLIGHT);
//                        preparedStatement.setString(4, ETA);
//                        preparedStatement.setString(5, ETD);
//                        rs = preparedStatement.executeQuery();
//
//                        if (rs.next()) {
//                            ID = rs.getInt("ID");
//                            condition = mydb.getProcessValue();
//                        } else {
//                            mydb.execute("SELECT SEQ_COCOHDR.NEXTVAL as ID FROM DUAL");
//                            if (mydb.next()) {
//                                ID = mydb.getInt("ID");
//                            }
//                            query = "INSERT INTO COCOHDR(ID,KD_TPS,KD_GUDANG,VESSEL_CODE,VESSEL_NAME,"
//                                    + "VOYAGE_NO,CALL_SIGN,ETA,ETD,WK_REKAM)"
//                                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
//
//                            preparedStatement = mydb.preparedstmt(query);
//                            preparedStatement.setInt(1, ID);
//                            preparedStatement.setString(2, KD_TPS);
//                            preparedStatement.setString(3, KD_GUDANG);
//                            preparedStatement.setString(4, VESSEL_CODE);
//                            preparedStatement.setString(5, VESSEL_NAME);
//                            preparedStatement.setString(6, NO_VOY_FLIGHT);
//                            preparedStatement.setString(7, CALL_SIGN);
//                            preparedStatement.setString(8, ETA);
//                            preparedStatement.setString(9, ETD);
//                            preparedStatement.setDate(10, getCurrentTimeStamp());
//                            preparedStatement.executeUpdate();
//                            condition = mydb.getProcessValue();
//                            mydb.execute("commit");
//                        }
//                        String detil_cont = px.getStringParsingXml(fStream, "COCOCONT>DETIL>CONT",
//                                "KD_DOK,NO_CONT,UK_CONT,ISO_CODE,NO_SEGEL,BRUTO,OWNER,LOC_IN_VESSEL,LOC_IN_YARD,"
//                                + "WK_INOUT,TRUCKER_NAME,NO_POL,REF_NUMBER,POL,POT,POD,NO_BL_AWB,TGL_BL_AWB,"
//                                + "BOOKING_NO,NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,ID_CONSIGNEE,CONSIGNEE,NO_BC11,"
//                                + "TGL_BC11,NO_POS_BC11,KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,FL_CONT_KOSONG,"
//                                + "TEMPERATURE,SHIPPER,STAT_CONT,REMARKS,PORT_ORIGIN,PORT_FINAL_DESTINATION");
//                        String[] split_detil_cont = detil_cont.split(";");
//                        for (String detil : split_detil_cont) {
//                            String[] split_cont = detil.split(",");
//                            model.setKd_dok(split_cont[0]);
//                            model.setNo_cont(split_cont[1]);
//                            model.setUk_cont(split_cont[2]);
//                            model.setIso_code(split_cont[3]);
//                            model.setNo_segel(split_cont[4]);
//                            model.setBruto(split_cont[5]);
//                            model.setOwner(split_cont[6]);
//                            model.setLoc_in_vessel(split_cont[7]);
//                            model.setLoc_in_yard(split_cont[8]);
//                            model.setWk_inout(split_cont[9]);
//                            model.setTrucker_name(split_cont[10]);
//                            model.setNo_pol(split_cont[11]);
//                            model.setRef_number(split_cont[12]);
//                            model.setPel_muat(split_cont[13]);
//                            model.setPel_transit(split_cont[14]);
//                            model.setPel_bongkar(split_cont[15]);
//                            model.setNo_bl_awb(split_cont[16]);
//                            model.setTgl_bl_awb(split_cont[17]);
//                            model.setBooking_no(split_cont[18]);
//                            model.setNo_master_bl_awb(split_cont[19]);
//                            model.setId_consignee(split_cont[20]);
//                            model.setConaignee(split_cont[21]);
//                            model.setNo_bc11(split_cont[22]);
//                            model.setTgl_bc11(split_cont[23]);
//                            model.setNo_pos_bc_11(split_cont[24]);
//                            model.setKd_dok_inout(split_cont[25]);
//                            model.setNo_dok_inout(split_cont[26]);
//                            model.setTgl_dok_inout(split_cont[27]);
//                            model.setFl_cont_kosong(split_cont[28]);
//                            model.setTemperature(split_cont[29]);
//                            model.setShipper(split_cont[30]);
//                            model.setStat_cont(split_cont[31]);
//                            model.setRemarks(split_cont[32]);
//                            model.setPort_origin(split_cont[33]);
//                            model.setPort_final_destination(split_cont[34]);
//                            try {
//                                if (condition) {
//                                    query = "INSERT INTO COCOCONT(ID,KD_DOK,NO_CONT,UK_CONT,"
//                                            + "OWNER,ISO_CODE,NO_SEGEL,BRUTO,LOC_IN_VESSEL,"
//                                            + "LOC_IN_YARD,WK_INOUT,TRUCKER_NAME,NO_POL,"
//                                            + "POL,POT,POD,REF_NUMBER,NO_BL_AWB,TGL_BL_AWB,"
//                                            + "NO_BOOKING,"
//                                            + "NO_MASTER_BL_AWB,TGL_MASTER_BL_AWB,"
//                                            + "ID_CONSIGNEE,CONSIGNEE,NO_BC11,TGL_BC11,NO_POS_BC11,"
//                                            + "KD_DOK_INOUT,NO_DOK_INOUT,TGL_DOK_INOUT,"
//                                            + "FL_CONT_KOSONG,TEMPERATURE,SHIPPER,STAT_CONT,REMARKS,"
//                                            + "WK_REKAM,PORT_ORIGIN,PORT_FINAL_DESTINATION) "
//                                            + "VALUES (?,?,?,?,?,?,?,?,?,?,"
//                                            + "?,?,?,?,?,?,?,?,?,?,"
//                                            + "?,?,?,?,?,?,?,?,?,?,"
//                                            + "?,?,?,?,"
//                                            + "?,?,?,?)";
//
//                                    preparedStatement = mydb.preparedstmt(query);
//                                    preparedStatement.setInt(1, ID);
//                                    preparedStatement.setString(2, KD_DOK);
//                                    preparedStatement.setString(3, NO_CONT);
//                                    preparedStatement.setString(4, UK_CONT);
//                                    preparedStatement.setString(5, OWNER);
//                                    preparedStatement.setString(6, ISO_CODE);
//                                    preparedStatement.setString(7, NO_SEGEL);
//                                    preparedStatement.setString(8, BRUTO);
//                                    preparedStatement.setString(9, LOC_IN_VESSEL);
//                                    preparedStatement.setString(10, LOC_IN_YARD);
//                                    preparedStatement.setString(11, WK_INOUT);
//                                    preparedStatement.setString(12, TRUCKER_NAME);
//                                    preparedStatement.setString(13, NO_POL);
//                                    preparedStatement.setString(14, PEL_MUAT);
//                                    preparedStatement.setString(15, PEL_TRANSIT);
//                                    preparedStatement.setString(16, PEL_BONGKAR);
//                                    preparedStatement.setString(17, REF_NUMBER);
//                                    preparedStatement.setString(18, NO_BL_AWB);
//                                    preparedStatement.setString(19, TGL_BL_AWB);
//                                    preparedStatement.setString(20, BOOKING_NO);
//                                    preparedStatement.setString(21, NO_MASTER_BL_AWB);
//                                    preparedStatement.setString(22, TGL_MASTER_BL_AWB);
//                                    preparedStatement.setString(23, ID_CONSIGNEE);
//                                    preparedStatement.setString(24, CONSIGNEE);
//                                    preparedStatement.setString(25, NO_BC11);
//                                    preparedStatement.setString(26, TGL_BC11);
//                                    preparedStatement.setString(27, NO_POS_BC11);
//                                    preparedStatement.setString(28, KD_DOK_INOUT);
//                                    preparedStatement.setString(29, NO_DOK_INOUT);
//                                    preparedStatement.setString(30, TGL_DOK_INOUT);
//                                    preparedStatement.setString(31, FL_CONT_KOSONG);
//                                    preparedStatement.setString(32, TEMPERATURE);
//                                    preparedStatement.setString(33, SHIPPER);
//                                    preparedStatement.setString(34, STAT_CONT);
//                                    preparedStatement.setString(35, REMARKS);
//                                    preparedStatement.setDate(36, getCurrentTimeStamp());
//                                    preparedStatement.setString(37, PORT_ORIGIN);
//                                    preparedStatement.setString(38, PORT_FINAL_DESTINATION);
//
//                                    preparedStatement.executeUpdate();
//                                    System.out.println(query);
//                                    condition = mydb.getProcessValue();
//                                    mydb.execute("commit");
//                                }
//                            } catch (Exception e) {
//                                exc.ExcuteError(e.getMessage(), "execute_class_ParsingXMLCoarriCodecoSHL baris 610", String.valueOf(result));
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//            if (condition) {
//                mydb.execute("commit");
//                mydb.close();
//                result = true;
//                log.logSucces("COARRI_CODECO_SHL", "INSERT DB", "", "SUCCESS", REF_NUMBER);
//            } else {
//                mydb.execute("rollback");
//                mydb.close();
//                result = false;
//                log.logError("COARRI_CODECO_SHL", "INSERT DB", query.replace("'", ""), "NOT SUCCESS", REF_NUMBER);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.logError("COARRI_CODECO_SHL", "INTERASI XML", query, e.getMessage(), REF_NUMBER);
//        }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            log.logError("COARRI_CODECO_SHL", "KONEKSI DB", query, ex.getMessage(), REF_NUMBER);
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
//                rs = null;
//                preparedStatement = null;
//            }
//            List = null;
//            doc = null;
        }
        return result;
    }
//
    private static java.sql.Date getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());

    }
}
