/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.edii.db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aslichatun Nisa
 */
public class LicenseHeader {

    private Db db = null;

    public long getIdIjin(String noIjin, String tgIjin, String kdIjin) throws SQLException, Exception {

        long id_ijin = 0;
        PreparedStatement preparedStatement = null;
        String query = "SELECT b.id_ijin FROM tbllichdr b "
                + "WHERE b.no_ijin = ? "
                + "AND b.tgl_ijin = TO_DATE(?, 'yyyymmdd')"
                + "AND b.kd_ijin = ? ";
        ResultSet rs = null;
        try {
            db = new Db();
            //tanyakan nama field kd_ijin
            preparedStatement = db.preparedstmt(query);
            preparedStatement.setString(1, noIjin);
            preparedStatement.setString(2, tgIjin);
            preparedStatement.setString(3, kdIjin);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id_ijin = rs.getLong("b.id_ijin");
            }
        } catch (Exception e) {
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (db != null) {
                db.close();
            }
            if (rs != null) {
                rs.close();
            }
            return id_ijin;
        }
    }

    public String getStatus(long idIjin) throws SQLException, Exception {
        String status = "";
        PreparedStatement preparedStatement = null;
        String query = "SELECT b.kode_status FROM tbllichdr b "
                + "WHERE b.id_ijin = ? ";
        ResultSet rs = null;
        try {

            db = new Db();
            preparedStatement = db.preparedstmt(query);
            preparedStatement.setLong(1, idIjin);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                status = rs.getString("b.kode_status");
            }
        } catch (Exception e) {
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (db != null) {
                db.close();
            }
            if (rs != null) {
                rs.close();
            }
            return status;
        }
    }

    public long getNextVal(String seq_id_ijin) throws Exception {
        db = new Db();
        long id_ijin = 0;
        String sqlCommand = "";
        try {
            //tanyakan nama field kd_ijin
            sqlCommand = "SELECT " + seq_id_ijin + ".NEXTVAL FROM tbllichdr";
            db.execute(sqlCommand);
            if (db.next()) {
                id_ijin = db.getLong(1);
            }
        } catch (Exception e) {
        }
        return id_ijin;
    }
}
