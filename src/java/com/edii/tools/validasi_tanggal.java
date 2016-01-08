/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aslichatun Nisa
 */
public class validasi_tanggal {

    public boolean validasiTgl(String tgl) throws ParseException {
        String tahun = tgl.substring(0, 4);
        String bulan = tgl.substring(4, 6);
        String tanggal = tgl.substring(6, 8);
        boolean r = TglValid(tanggal + "-" + bulan + "-" + tahun);
        return r;
    }

    public boolean validasiTglJam(String tgl) throws ParseException {
        String tahun = tgl.substring(0, 4);
        String bulan = tgl.substring(4, 6);
        String tanggal = tgl.substring(6, 8);
        String jam = tgl.substring(8, 10);
        String menit = tgl.substring(10, 12);
        String detik = tgl.substring(12, 14);
        boolean r = TglJamValid(tanggal + "-" + bulan + "-" + tahun + " " + jam + ":" + menit + ":" + detik);
        return r;
    }

    public static boolean TglValid(String tgl) {
        String pola = "dd-MM-yyyy";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pola); //deklarasi format
            format.setLenient(false); //jika tanggal yang di input salah akan mertipe boolean false
            Date date = format.parse(tgl);
            return true;
        } catch (ParseException ex) {
            System.err.println(" Error: " + ex.getMessage()); //memberitahuan pesan error
            return false;
        }
    }

    public static boolean TglJamValid(String tgl) {
        String pola = "dd-MM-yyyy HH:mm:SS";
        try {
            SimpleDateFormat format = new SimpleDateFormat(pola); //deklarasi format
            format.setLenient(false); //jika tanggal yang di input salah akan mertipe boolean false
            Date date = format.parse(tgl);
            System.out.println(date);
            return true;
        } catch (ParseException ex) {
            System.err.println(" Error: " + ex.getMessage()); //memberitahuan pesan error
            return false;
        }
    }

    public static void main(String[] args) throws ParseException {
      
        validasi_tanggal v = new validasi_tanggal();
        //boolean r = v.validasiTgl("20141229");
        boolean r = v.validasiTglJam("20140714095900");
        System.out.println(r);
    }
}
