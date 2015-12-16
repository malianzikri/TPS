/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edii.db;

import com.edii.tools.ExcuteProses;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Clob;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.Calendar;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleDriver;

public class ConnDbOrcl {

    private boolean process = true;

    public boolean getProcessValue() {
        return this.process;
    }

    public Connection connect(String host, String port, String sid, String user, String pswd, boolean autoCommit) throws Exception {
        Connection conn = null;
        boolean stsconn = false;
        int maxtry = 0;
        try {
            while ((!stsconn) && (maxtry < 5)) {
                try {
                    Thread.currentThread().sleep(100);
                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid, user, pswd);
                    conn.setAutoCommit(autoCommit);
                    stsconn = true;
                    maxtry = 777;
                } catch (Exception e) {
                    stsconn = false;
                    close(conn);
                    conn = null;
                }
                maxtry += 1;
            }
        } catch (Exception e) {
            conn = null;
        } finally {
            stsconn = false;
            maxtry = 0;
        }
        return conn;
    }

    public Connection jndiconnect(String jndi, boolean autoCommit) throws Exception {
        Connection conn = null;
        try {
            conn = getDataSource(jndi).getConnection();
            conn.setAutoCommit(autoCommit);
        } catch (Exception e) {
            conn = null;
        }
        return conn;
    }

    private DataSource getDataSource(String jndi) throws Exception {
        DataSource ds = null;
        Context ic = null;
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup(jndi);
        } catch (Exception e) {
            ds = null;
        } finally {
            ic = null;
        }
        return ds;
    }

    public ResultSet execute(Connection conn, String sql) throws Exception {
        ExcuteProses exc = new ExcuteProses(); 
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (Exception e) {
            //err.mailError("SQL: " + sql, e.getStackTrace());
            System.out.println("SQL: " + sql);
            e.printStackTrace();
            exc.ExcuteError(e.getMessage(), "SQL", "");
            this.process = false;
            rs = null;
        }
        return rs;
    }

    public String getString(ResultSet rs, int s) throws Exception {
        String temp = null;
        try {
            temp = "";
            temp = rs.getString(s);
        } catch (Exception e) {
            temp = null;
        }

        return (temp == null) ? "" : temp;

    }

    public String getString(ResultSet rs, int s, String d) throws Exception {
        String temp = null;
        try {
            temp = "";
            temp = rs.getString(s);
        } catch (Exception e) {
            temp = null;
        }

        return (temp == null) ? d : temp;

    }

    public String getString(ResultSet rs, String s) throws Exception {
        String temp = null;
        try {
            temp = "";
            temp = rs.getString(s);
        } catch (Exception e) {
            temp = null;
        }

        return (temp == null) ? "" : temp;

    }

    public String getString(ResultSet rs, String s, String d) throws Exception {
        String temp = null;
        try {
            temp = rs.getString(s);
        } catch (Exception e) {
            temp = null;
        }

        return (temp == null) ? d : temp;

    }

    public PreparedStatement preparedstmt(Connection conn, String stmt) {
        PreparedStatement tmpstmt = null;
        try {
            tmpstmt = conn.prepareStatement(stmt);
        } catch (Exception e) {
            tmpstmt = null;
        }

        return tmpstmt;

    }

    public CLOB createCLOB(Connection conn) {
        CLOB tempCLOB = null;
        try {
            conn.setAutoCommit(false);
            tempCLOB = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);
            tempCLOB.open(CLOB.MODE_READWRITE);
        } catch (Exception e) {
            tempCLOB = null;
        }

        return tempCLOB;

    }

    public boolean insertBLOB(Connection conn, String sqlCommand, String filePath) throws IOException {
        boolean retVal = true;
        Statement stmt = null;
        ResultSet rs = null;
        BLOB blob = null;
        OutputStream os = null;
        InputStream is = null;
        File file = null;
        byte[] buffer = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlCommand + " FOR UPDATE");
            if (rs.next()) {
                blob = ((OracleResultSet) rs).getBLOB(1);
                os = blob.getBinaryOutputStream();
                file = new File(filePath);
                is = new FileInputStream(file);
                buffer = new byte[blob.getBufferSize()];
                int bytesRead = 0;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } else {
                retVal = false;
            }
        } catch (Exception e) {
            retVal = false;
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception e) {
            } finally {
                os.flush();
                rs = null;
                stmt = null;
                blob = null;
                os = null;
                is = null;
                file = null;
                buffer = null;
            }
        }
        return retVal;
    }

    public boolean insertBLOBString(Connection conn, String sqlCommand, String tempString) throws IOException {
        boolean retVal = true;
        Statement stmt = null;
        ResultSet rs = null;
        BLOB blob = null;
        OutputStream os = null;
        ByteArrayInputStream is = null;
        byte[] tempByte = null;
        byte[] buffer = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlCommand + " FOR UPDATE");
            if (rs.next()) {
                blob = ((OracleResultSet) rs).getBLOB(1);
                os = blob.getBinaryOutputStream();
                tempByte = tempString.getBytes();
                is = new ByteArrayInputStream(tempByte);
                buffer = new byte[blob.getBufferSize()];
                int bytesRead = 0;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } else {
                retVal = false;
            }
        } catch (Exception e) {
            retVal = false;
        } finally {
            try {
                rs.close();
                stmt.close();
                os.flush();
            } catch (Exception e) {
            } finally {
                rs = null;
                stmt = null;
                blob = null;
                os = null;
                is = null;
                buffer = null;
            }
        }
        return retVal;
    }

    public BLOB getBLOB(ResultSet rs, String s) throws Exception {
        BLOB temp = null;
        try {
            temp = ((OracleResultSet) rs).getBLOB(s);
        } catch (Exception e) {
            temp = null;
        }
            return (temp == null) ? null : temp;

    }

    public BLOB getBLOB(ResultSet rs, int s) throws Exception {
        BLOB temp = null;
        try {
            temp = ((OracleResultSet) rs).getBLOB(s);
        } catch (Exception e) {
            temp = null;
        }
            return (temp == null) ? null : temp;

    }

    public Clob getClob(ResultSet rs, String s) throws Exception {
        Clob temp = null;
        try {
            temp = rs.getClob(s);
        } catch (Exception e) {
            temp = null;
        }
            return (temp == null) ? null : temp;

    }

    public Clob getClob(ResultSet rs, int s) throws Exception {
        Clob temp = null;
        try {
            temp = rs.getClob(s);
        } catch (Exception e) {
            temp = null;
        }
            return (temp == null) ? null : temp;

    }

    public int getInt(ResultSet rs, int s) throws Exception {
        int temp = 0;
        try {
            temp = rs.getInt(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public int getInt(ResultSet rs, String s) throws Exception {
        int temp = 0;
        try {
            temp = rs.getInt(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public long getLong(ResultSet rs, int s) throws Exception {
        long temp = 0;
        try {
            temp = rs.getLong(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public long getLong(ResultSet rs, String s) throws Exception {
        long temp = 0;
        try {
            temp = rs.getLong(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public java.util.Date getDate(ResultSet rs, int s) throws Exception {
        java.util.Date temp = null;
        try {
            temp = rs.getDate(s);
            if (temp.equals(null)) {
                temp = Calendar.getInstance().getTime();
            }
        } catch (Exception e) {
            temp = null;
        }
            return temp;

    }

    public java.util.Date getDate(ResultSet rs, String s) throws Exception {
        java.util.Date temp = null;
        try {
            temp = rs.getTimestamp(s);
            if (temp == null) {
                temp = Calendar.getInstance().getTime();
            }
        } catch (Exception e) {
            temp = null;
        }
            return temp;

    }

    public void commit(Connection conn) throws Exception {
        try {
            conn.commit();
        } catch (Exception e) {
            //
        }
    }

    public void rollback(Connection conn) throws Exception {
        try {
            conn.rollback();
        } catch (Exception e) {
            //
        }
    }

    public void close(Connection conn) throws Exception {
        try {
            conn.close();
        } catch (Exception e) {
            conn = null;
        } finally {
            conn = null;
        }
    }

    public boolean next(ResultSet rs) throws Exception {
        return rs.next();
    }

    public boolean previous(ResultSet rs) throws Exception {
        return rs.previous();
    }

    public boolean absolute(ResultSet rs, int row) throws Exception {
        return rs.absolute(row);
    }

    public double getDouble(ResultSet rs, int s) throws Exception {
        double temp = 0;
        try {
            temp = rs.getDouble(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public double getDouble(ResultSet rs, String s) throws Exception {
        double temp = 0;
        try {
            temp = rs.getDouble(s);
        } catch (Exception e) {
            temp = 0;
        }
            return temp;

    }

    public CallableStatement getCallableStatement(Connection conn, String s) throws Exception {
        CallableStatement temp = null;
        try {
            temp = conn.prepareCall(s);
        } catch (Exception e) {
            temp = null;
        }
            return temp;

    }
    
    public String executeError(Connection conn, String sql) throws Exception {
        ResultSet rs = null;
        String error = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (Exception e) {
            //err.mailError("SQL: " + sql, e.getStackTrace());
            System.out.println("SQL: " + sql);
            e.printStackTrace();
            this.process = false;
            rs = null;
            error = e.getMessage().toString();
        }
        return error;
    }
}
