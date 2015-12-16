/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

/**
 *
 * @author Aslichatun Nisa
 */
public class ResCFS {

    public String KdRes(String kdRes) {
        String res = "";
        try {
            res = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                    + "<DOCUMENT>"
                    + "<RESPON>"
                    + "<KD_RES>" + kdRes + "</KD_RES>"
                    + "</RESPON>"
                    + "</DOCUMENT>";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static void main(String[] args) {
        ResCFS res = new ResCFS();
        String result = res.KdRes("001");
        System.out.println(result);
    }
}
