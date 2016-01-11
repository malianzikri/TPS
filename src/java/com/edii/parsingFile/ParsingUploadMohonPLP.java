/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.parsingfile;

import XMLGenerator.ParsingXML;
import com.edii.controller.SaveData;
import com.edii.model.ModelUploadMohonPLP;
import com.edii.operation.db.operation;
import java.util.ArrayList;

/**
 *
 * @author Aslichatun Nisa
 */
public class ParsingUploadMohonPLP {

    private ParsingXML px;
    private ModelUploadMohonPLP model;
    private SaveData sv;

    public String parseDocument(String fStream) throws Exception {

        String result = null;
        px = new ParsingXML();
        model = new ModelUploadMohonPLP();
        sv = new operation();
        try {
            if (fStream != null) {
                ArrayList<String> header = new ArrayList<>();
                header = px.xmlParsing(fStream, "LOADPLP>HEADER", "KD_KANTOR,TIPE_DATA,KD_TPS_ASAL,REF_NUMBER,NO_SURAT,TGL_SURAT,GUDANG_ASAL,KD_TPS_TUJUAN,GUDANG_TUJUAN,KD_ALASAN_PLP,YOR_ASAL,YOR_TUJUAN,CALL_SIGN,NM_ANGKUT,NO_VOY_FLIGHT,TGL_TIBA,NO_BC11,TGL_BC11,NM_PEMOHON");

                for (String headersss : header) {
                    String[] split_header = headersss.split(",");

                    model.setKd_kantor(split_header[0]);
                    model.setTipe_data(split_header[1]);
                    model.setKd_tps_asal(split_header[2]);
                    model.setRef_number(split_header[3]);
                    model.setNo_surat(split_header[4]);
                    model.setTgl_surat(split_header[5]);
                    model.setGudang_asal(split_header[6]);
                    model.setKd_tps_tujuan(split_header[7]);
                    model.setGudang_tujuan(split_header[8]);
                    model.setKd_alasan_plp(split_header[9]);
                    model.setYor_asal(split_header[10]);
                    model.setYor_tujuan(split_header[11]);
                    model.setCall_sign(split_header[12]);
                    model.setNm_angkut(split_header[13]);
                    model.setNo_voy_flight(split_header[14]);
                    model.setTgl_tiba(split_header[15]);
                    model.setNo_bc11(split_header[16]);
                    model.setTgl_bc11(split_header[17]);
                    model.setNm_pemohon(split_header[18]);
                }
                sv.savedata_uploadmohonplp(model, "header");
                System.out.println(model.getNm_angkut());

                ArrayList<String> detil_kms = new ArrayList<>();
                detil_kms = px.xmlParsing(fStream, "LOADPLP>DETIL>KMS", "JNS_KMS,JML_KMS,NO_BL_AWB,TGL_BL_AWB");

                for (String detil : detil_kms) {
                    String[] split_kms = detil.split(",");
                    model.setJns_kms(split_kms[0]);
                    model.setJml_kms(split_kms[1]);
                    model.setNo_bl_awb(split_kms[2]);
                    model.setTgl_bl_awb(split_kms[3]);
                    sv.savedata_uploadmohonplp(model, "kms");
                }

                ArrayList<String> detil_cont = new ArrayList<>();
                detil_cont = px.xmlParsing(fStream, "LOADPLP>DETIL>CONT", "NO_CONT,UK_CONT");

                for (String detil : detil_cont) {
                    String[] split_cont = detil.split(",");
                    model.setNo_cont(split_cont[0]);
                    model.setUk_cont(split_cont[1]);
                    sv.savedata_uploadmohonplp(model, "cont");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ParsingUploadMohonPLP pxp = new ParsingUploadMohonPLP();
        String a = pxp.parseDocument("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DOCUMENT xmlns=\"loadplp.xsd\"><LOADPLP><HEADER><KD_KANTOR>040300</KD_KANTOR><TIPE_DATA>1</TIPE_DATA><KD_TPS_ASAL>PLDC</KD_TPS_ASAL><REF_NUMBER>151223000100844</REF_NUMBER><NO_SURAT>1077/PLP/GM.III/PTP-15</NO_SURAT><TGL_SURAT>20151223</TGL_SURAT><GUDANG_ASAL>TER3</GUDANG_ASAL><KD_TPS_TUJUAN>PLDC</KD_TPS_TUJUAN><GUDANG_TUJUAN>223X</GUDANG_TUJUAN><KD_ALASAN_PLP>1</KD_ALASAN_PLP><YOR_ASAL>66</YOR_ASAL><YOR_TUJUAN>18</YOR_TUJUAN><CALL_SIGN>N/A</CALL_SIGN><NM_ANGKUT>asl</NM_ANGKUT><NO_VOY_FLIGHT>018S</NO_VOY_FLIGHT><TGL_TIBA>20151212</TGL_TIBA><NO_BC11>005095</NO_BC11><TGL_BC11>20151211</TGL_BC11><NM_PEMOHON>SARKANI</NM_PEMOHON></HEADER><DETIL><CONT><NO_CONT>aslich</NO_CONT><UK_CONT>22</UK_CONT></CONT></DETIL></LOADPLP>"
                + "<LOADPLP><HEADER><KD_KANTOR>040300</KD_KANTOR><TIPE_DATA>1</TIPE_DATA><KD_TPS_ASAL>PLDC</KD_TPS_ASAL><REF_NUMBER>151223000100844</REF_NUMBER><NO_SURAT>1077/PLP/GM.III/PTP-15</NO_SURAT><TGL_SURAT>20151223</TGL_SURAT><GUDANG_ASAL>TER3</GUDANG_ASAL><KD_TPS_TUJUAN>PLDC</KD_TPS_TUJUAN><GUDANG_TUJUAN>223X</GUDANG_TUJUAN><KD_ALASAN_PLP>1</KD_ALASAN_PLP><YOR_ASAL>66</YOR_ASAL><YOR_TUJUAN>18</YOR_TUJUAN><CALL_SIGN>N/A</CALL_SIGN><NM_ANGKUT>bakri</NM_ANGKUT><NO_VOY_FLIGHT>018S</NO_VOY_FLIGHT><TGL_TIBA>20151212</TGL_TIBA><NO_BC11>005095</NO_BC11><TGL_BC11>20151211</TGL_BC11><NM_PEMOHON>SARKANI</NM_PEMOHON></HEADER><DETIL><CONT><NO_CONT>kamal</NO_CONT><UK_CONT>20</UK_CONT></CONT></DETIL></LOADPLP></DOCUMENT>");
        System.out.println(a);
    }
}
