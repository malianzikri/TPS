/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelCoarriCodecoSHL;
import com.edii.operation.db.operation;
import java.util.ArrayList;

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
<<<<<<< HEAD


=======
<<<<<<< HEAD
=======


>>>>>>> bfa8815... mengefesiensikan kodingan dan merapihkan kodingan tps online
>>>>>>> master
            if (fStream != null) {
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
<<<<<<< HEAD


            }
        } catch (Exception e) {
            e.printStackTrace();
=======
<<<<<<< HEAD
            }
        } catch (Exception ex) {
        } finally {
=======


            }
        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> bfa8815... mengefesiensikan kodingan dan merapihkan kodingan tps online
>>>>>>> master
        }
        return result;
    }
}
