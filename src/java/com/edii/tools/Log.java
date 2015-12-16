/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.tools;

import com.nsw.db.Db;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Aslichatun Nisa
 */
public class Log {

    public void logSucces(String TIPE_JOB, String NAMA_JOB, String SQL_CODE, String SQL_ERRM, String KETERANGAN) throws Exception {
        Db mydb = null;
        Integer ID = null;
        String query = null;
        PreparedStatement preparedStatement = null;
        query = "INSERT INTO LOG_JOB(ID,TIPE_JOB,NAMA_JOB,SQL_CODE,SQL_ERRM,STATUS,KETERANGAN,WK_REKAM) VALUES (?,?,?,?,?,?,?,?)";
        try {
            mydb = new Db("SHL.properties");
            mydb.execute("SELECT SEQ_LOG_JOB.NEXTVAL as ID FROM DUAL");// fungsi untuk mengambil data terakhir
            if (mydb.next()) {
                ID = mydb.getInt("ID");
            }
            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, TIPE_JOB);
            preparedStatement.setString(3, NAMA_JOB);
            preparedStatement.setString(4, SQL_CODE);
            preparedStatement.setString(5, SQL_ERRM);
            preparedStatement.setString(6, "TRUE");
            preparedStatement.setString(7, KETERANGAN);
            preparedStatement.setTimestamp(8, getCurrentTimeStamp());
            preparedStatement.executeUpdate();
            mydb.execute("commit");
        } catch (SQLException e) {

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (mydb != null) {
                mydb.close();
            }
        }

    }

    public void logError(String TIPE_JOB, String NAMA_JOB, String SQL_CODE, String SQL_ERRM, String KETERANGAN) throws Exception {
        Db mydb = null;
        Integer ID = null;
        String query = null;
        PreparedStatement preparedStatement = null;
        query = "INSERT INTO LOG_JOB(ID,TIPE_JOB,NAMA_JOB,SQL_CODE,SQL_ERRM,STATUS,KETERANGAN,WK_REKAM) VALUES (?,?,?,?,?,?,?,?)";
        try {
            mydb = new Db("SHL.properties");
            mydb.execute("SELECT SEQ_LOG_JOB.NEXTVAL as ID FROM DUAL");// fungsi untuk mengambil data terakhir
            if (mydb.next()) {
                ID = mydb.getInt("ID");
            }
            preparedStatement = mydb.preparedstmt(query);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, TIPE_JOB);
            preparedStatement.setString(3, NAMA_JOB);
            preparedStatement.setString(4, SQL_CODE);
            preparedStatement.setString(5, SQL_ERRM);
            preparedStatement.setString(6, "FALSE");
            preparedStatement.setString(7, KETERANGAN);
            preparedStatement.setTimestamp(8, getCurrentTimeStamp());
            preparedStatement.executeUpdate();
            mydb.execute("commit");
        } catch (SQLException e) {

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (mydb != null) {
                mydb.close();
            }
        }

    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    public static void main(String[] args) throws Exception {
        Log l = new Log();
        //l.logError("COARRI_CODECO_SHL", "INSERT DB", "", "", "");
        l.logSucces("COARRI_CODECO_SHL", "INSERT DB", "", "SUCCESS", "001");
    }
}
