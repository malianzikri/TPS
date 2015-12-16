/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edii.db;

import oracle.sql.CLOB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import oracle.sql.BLOB;

public class DbOrcl
{
    SupportDbOrcl sdborcl = null;
    ConnDbOrcl cdborcl = null;
    private Connection connection = null;
    private ResultSet ResultSet = null;
    private String sql = null;
    private String jndi = null;
    public String host = null;
    public String port = null;
    public String sid = null;
    public String user = null;
    public String pswd = null;
    private boolean autoCommit = false;
    private boolean process = true;

    public void setJNDI(String jndi) {
        this.jndi = jndi;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public void setSID(String sid) {
        this.sid = sid;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public boolean connect() throws Exception {
        boolean stsconn = false;
        try {
            cdborcl = new ConnDbOrcl();
            this.connection = cdborcl.connect(this.host, this.port, this.sid, this.user, this.pswd, this.autoCommit);
            this.connection.commit();
            stsconn = true;
        }
        catch(Exception e) {
            stsconn = false;
        }
        finally {
            cdborcl = null;
        }
         return stsconn;
    }

    public boolean jndiconnect() throws Exception {
        boolean stsconn = false;
        try {
            cdborcl = new ConnDbOrcl();
            this.connection = cdborcl.jndiconnect(this.jndi, this.autoCommit);
            this.connection.commit();
            stsconn = true;
        }
        catch (Exception e) {
            stsconn = false;
        }
        finally {
            cdborcl = null;

        }
         return stsconn;
    }

    public void execute() throws Exception {
        try {
            cdborcl = new ConnDbOrcl();
            this.ResultSet = cdborcl.execute(this.connection, this.sql);
        }
        catch(Exception e) {
        }
        finally {
            this.process = cdborcl.getProcessValue();
            cdborcl = null;
        }
    }

    public boolean execute(String s) throws Exception {
        try {
            cdborcl = new ConnDbOrcl();
            this.sql = s;
            this.ResultSet = cdborcl.execute(this.connection, this.sql);
        }
        catch(Exception e) {
        }
        finally {
            this.process = cdborcl.getProcessValue();
            cdborcl = null;
        }
        return this.process;
    }

    public ResultSet getResultSet() throws Exception {
        return this.ResultSet;
    }

    public String getString(int s) throws Exception {
        String temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = "";
            temp = cdborcl.getString(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
         return temp;
    }

    public String getString(int s, String d) throws Exception {
        String temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = "";
            temp = cdborcl.getString(this.ResultSet, s, d);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public String getString(String s) throws Exception {
        String temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = "";
            temp = cdborcl.getString(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public String getString(String s, String d) throws Exception {
        String temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = "";
            temp = cdborcl.getString(this.ResultSet, s, d);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public PreparedStatement preparedstmt(String stmt) {
        PreparedStatement tmpstmt = null;
        try {
            cdborcl = new ConnDbOrcl();
            tmpstmt = cdborcl.preparedstmt(this.connection, stmt);
        }
        catch(Exception e) {
            tmpstmt = null;
        }
        finally {
            cdborcl = null;

        }
        return tmpstmt;
    }

    public CLOB createCLOB() {
        CLOB tempCLOB = null;
        try {
            cdborcl = new ConnDbOrcl();
            tempCLOB = cdborcl.createCLOB(this.connection);
        }
        catch(Exception e) {
            tempCLOB = null;
        }
        finally {
            cdborcl = null;

        }
         return tempCLOB;
    }

    public boolean insertBLOB(String sqlCommand, String filePath) {
        boolean temp = false;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.insertBLOB(this.connection, sqlCommand, filePath);
        }
        catch(Exception e) {
            temp = false;
        }
        finally{
            cdborcl = null;

        }
        return temp;
    }

    public boolean insertBLOBString(String sqlCommand, String tempString) {
        boolean temp = false;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.insertBLOBString(this.connection, sqlCommand, tempString);
        }
        catch(Exception e) {
            temp = false;
        }
        finally{
            cdborcl = null;
        }
        return temp;
    }

    public BLOB getBLOB(String s) {
        BLOB temp = null;
        try{
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getBLOB(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally{
            cdborcl = null;

        }
        return temp;
    }

    public BLOB getBLOB(int i) {
        BLOB temp = null;
        try{
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getBLOB(this.ResultSet, i);
        }
        catch(Exception e) {
            temp = null;
        }
        finally{
            cdborcl = null;

        }
        return temp;
    }

    public Clob getClob(String s) throws Exception {
        Clob temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getClob(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public Clob getClob(int s) throws Exception {
        Clob temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getClob(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public int getInt(int s) throws Exception {
        int temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getInt(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public int getInt(String s) throws Exception {
        int temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getInt(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public long getLong(int s) throws Exception {
        long temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getLong(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;
        }
        return temp;
    }

    public long getLong(String s) throws Exception {
        long temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getLong(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public java.util.Date getDate(int s) throws Exception {
        java.util.Date temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getDate(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public java.util.Date getDate(String s) throws Exception {
        java.util.Date temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getDate(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public void close() throws Exception {
        try {
            cdborcl = new ConnDbOrcl();
            cdborcl.close(this.connection);
        }
        catch(Exception e) {
        }
        finally {
            cdborcl = null;
        }
    }

    public boolean next() throws Exception {
        boolean next = false;
        try {
            cdborcl = new ConnDbOrcl();
            next = cdborcl.next(this.ResultSet);
        }
        catch(Exception e) {
            next = false;
        }
        finally {
            cdborcl = null;

        }
        return next;
    }

    public boolean previous() throws Exception {
        boolean previous = false;
        try {
            cdborcl = new ConnDbOrcl();
            previous = cdborcl.previous(this.ResultSet);
        }
        catch(Exception e) {
            previous = false;
        }
        finally {
            cdborcl = null;

        }
        return previous;
    }

    public boolean absolute(int row) throws Exception {
        boolean absolute = false;
        try {
            cdborcl = new ConnDbOrcl();
            absolute = cdborcl.absolute(this.ResultSet, row);
        }
        catch(Exception e) {
            absolute = false;
        }
        finally {
            cdborcl = null;

        }
       return absolute;
    }

    public String now(String format) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.now(format);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public String getSatuNilai(String cmd, String jndiName, String username, String password) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.getSatuNilai(cmd, jndiName, username, password);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public void setSQL(String sqlScript) {
        this.sql = sqlScript;
    }

    public String getDateFormated(int field, String format, String jndiName, String username, String password) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.formatDate(this.getDate(field), format, jndiName, username, password);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public String getDateFormated(String field, String format, String jndiName, String username, String password) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.formatDate(this.getDate(field), format, jndiName, username, password);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public String getDateFormatedHour(String field, String format, String jndiName, String username, String password) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.formatDateHour(this.getDate(field), format, jndiName, username, password);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public String getDateFormated(String date, String inputformat, String outputformat) throws Exception {
        String tgl = null;
        try {
            sdborcl = new SupportDbOrcl();
            tgl = "";
            tgl = sdborcl.getDateFormated(date, inputformat, outputformat);
        }
        catch(Exception e) {
            tgl = null;
        }
        finally {
            sdborcl = null;

        }
        return tgl;
    }

    public boolean getProcessValue() {
        return this.process;
    }

    public double getDouble(String s) throws Exception {
        double temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getDouble(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public double getDouble(int s) throws Exception {
        double temp = 0;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getDouble(this.ResultSet, s);
        }
        catch(Exception e) {
            temp = 0;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }

    public CallableStatement getCallableStatement(String s) throws Exception {
        CallableStatement temp = null;
        try {
            cdborcl = new ConnDbOrcl();
            temp = cdborcl.getCallableStatement(this.connection, s);
        }
        catch(Exception e) {
            temp = null;
        }
        finally {
            cdborcl = null;

        }
        return temp;
    }    

    public String executeError(String s) throws Exception {
        String error = null;
        try {
            cdborcl = new ConnDbOrcl();
            this.sql = s;
            error = cdborcl.executeError(this.connection, this.sql);
        }
        catch(Exception e) {
        }
        finally {
            this.process = cdborcl.getProcessValue();
            cdborcl = null;
        }
        return error;
    }
}

