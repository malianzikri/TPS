/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.controller;

import com.edii.model.ModelGetResponPLP;
import com.edii.model.ModelGetResponPLPTujuan;
import com.edii.model.ModelUploadBatalPLP;
import com.edii.model.ModelUploadMohonPLP;

/**
 *
 * @author Aslichatun Nisa
 */
public interface SaveData {

    String savedata_uploadmohonplp(ModelUploadMohonPLP plp, String type);

    String savedata_uploadbatalplp(ModelUploadBatalPLP plp, String type);

    String savedata_getresponplp_header(ModelGetResponPLP plp);

    String savedata_getresponplp_kms(ModelGetResponPLP plp, String respon_id);

    String savedata_getresponplp_cont(ModelGetResponPLP plp, String respon_id);

    String savedata_getresponplptujuan_header(ModelGetResponPLPTujuan plp);

    String savedata_getresponplptujuan_kms(ModelGetResponPLPTujuan plp, String respon_id);

    String savedata_getresponplptujuan_cont(ModelGetResponPLPTujuan plp, String respon_id);
}
