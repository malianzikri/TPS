package com.edii.tools;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aslichatun Nisa
 */
public class Encrypt {

    MessageDigest messageDigest;
    private static final String ALGO = "AES";
    private static final byte[] keyValue
            = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public String encrypt(String plaintext) throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
        //System.out.println("Metode Enkripsi : " + messageDigest.getAlgorithm());
        String input = plaintext;
        messageDigest.update(input.getBytes());
        byte[] output = messageDigest.digest();
        return convert(output);
    }

    private int byte2int(byte data) {
        return data < 0 ? 256 + data : data;
    }

    public String convert(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : data) {
            String s = Integer.toHexString(byte2int(b));
            if (s.length() < 2) {
                buffer.append("0" + s);
            } else {
                buffer.append(s);
            }
        }
        return buffer.toString();
    }

    public static String encrypt_AES(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decrypt_AES(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public static void main(String[] args) throws Exception {

        String password = "cd";
        String passwordEnc = Encrypt.encrypt_AES(password);
        String passwordDec = Encrypt.decrypt_AES(passwordEnc);
        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);
    }
//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String input = "IPC0";
//        Encrypt mde = new Encrypt();
//        System.out.println("(" + input + ") = " + mde.encrypt(input));
//    }
}
