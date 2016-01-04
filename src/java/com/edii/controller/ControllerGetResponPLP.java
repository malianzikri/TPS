/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.controller;

import com.edii.model.ModelGetResponPLP;

/**
 *
 * @author Aslichatun Nisa
 */
public interface ControllerGetResponPLP {
    //GetresponPLP

    String savedata_getresponplp_header(ModelGetResponPLP plp);

    String savedata_getresponplp_kms(ModelGetResponPLP plp, String respon_id);

    String savedata_getresponplp_cont(ModelGetResponPLP plp, String respon_id);
}
