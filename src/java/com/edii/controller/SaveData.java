/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.controller;

import com.edii.model.ModelCoarriCodecoContainer;
import com.edii.model.ModelCoarriCodecoKemasan;
import com.edii.model.ModelCoarriCodecoSHL;
import com.edii.model.ModelGetResponBatalPLP;
import com.edii.model.ModelGetResponBatalPLPTujuan;
import com.edii.model.ModelGetResponPLP;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.model.ModelGetSPJM;
import com.edii.model.ModelSPPB;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.model.ModelUploadMohonPLP;

/**
 *
 * @author Aslichatun Nisa
 */
public interface SaveData {

    String savedata_uploadmohonplp(ModelUploadMohonPLP plp, String type);

    String savedata_uploadbatalplp(ModelUploadBatalPLP plp, String type);

    //GetresponPLP
    String savedata_getresponplp_header(ModelGetResponPLP plp);

    String savedata_getresponplp_kms(ModelGetResponPLP plp, String respon_id);

    String savedata_getresponplp_cont(ModelGetResponPLP plp, String respon_id);

    //GetresponPLPTujuan
    String savedata_getresponplptujuan_header(ModelGetResponPLPTujuan plp);

    String savedata_getresponplptujuan_kms(ModelGetResponPLPTujuan plp, String respon_id);

    String savedata_getresponplptujuan_cont(ModelGetResponPLPTujuan plp, String respon_id);

    //GetresponBatalPLP
    String savedata_getresponbatalplp_header(ModelGetResponBatalPLP plp);

    String savedata_getresponbatalplp_kms(ModelGetResponBatalPLP plp, String respon_id);

    String savedata_getresponbatalplp_cont(ModelGetResponBatalPLP plp, String respon_id);

    //GetresponBatalPLPTujuan
    String savedata_getresponbatalplptujuan_header(ModelGetResponBatalPLPTujuan plp);

    String savedata_getresponbatalplptujuan_kms(ModelGetResponBatalPLPTujuan plp, String respon_id);

    String savedata_getresponbatalplptujuan_cont(ModelGetResponBatalPLPTujuan plp, String respon_id);

    //CoarriCodeco_Contianer
    String savedata_coarricodecocontianer_header(ModelCoarriCodecoContainer coco);

    String savedata_coarricodecocontianer_cont(ModelCoarriCodecoContainer coco, String id);

    //CoarriCodeco_Kemasan
    String savedata_coarricodecokemasan_header(ModelCoarriCodecoKemasan coco);

    String savedata_coarricodecokemasan_kms(ModelCoarriCodecoKemasan coco, String id);

    //CoarriCodeco_shl
    boolean cekdata_coarricodecshl_header(ModelCoarriCodecoSHL shl);
    
    String savedata_coarricodecshl_header(ModelCoarriCodecoSHL shl);

    String savedata_coarricodecshl_con(ModelCoarriCodecoSHL shl, String id);
    
    //SPJM
    boolean cekdata_spjm_header(ModelGetSPJM spjm);
    
    String savedata_spjm_header(ModelGetSPJM spjm);
    
    String savedata_spjm_kms(ModelGetSPJM spjm);
    
    String savedata_spjm_cont(ModelGetSPJM spjm);
    
    String savedata_spjm_dok(ModelGetSPJM spjm);
    
    //SPPB
    boolean cekdata_sppb_header(ModelSPPB sppb);
    
    String savedata_sppb_header(ModelSPPB sppb);
    
    String savedata_sppb_kms(ModelSPPB sppb);
    
    String savedata_sppb_cont(ModelSPPB sppb);
}
