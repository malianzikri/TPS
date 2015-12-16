/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edii.db;

public class SupportDbOrcl
{
    public String formatDate(java.util.Date date, String format, String jndiName, String username, String password) throws Exception
    {
        Db mydb = null;
        String tanggal = null;
        try
        {
            mydb = new Db();
            tanggal = "";
            mydb.execute("SELECT TO_CHAR(TO_DATE('" +
                         (new Integer(date.getDate())).toString() + "/" +
                         (new Integer(date.getMonth() + 1)).toString() + "/" +
                         (new Integer(date.getYear() + 1900)).toString() + " " +
                         //(new Integer(date.getHours())).toString() + ":" +
                         //(new Integer(date.getMinutes())).toString() + ":" +
                         //(new Integer(date.getSeconds())).toString() +
                         "', 'DD/MM/YYYY'), '" + format + "') TANGGAL FROM DUAL");
            if(mydb.next())
            {
                tanggal = mydb.getString("TANGGAL");
            }
        }
        catch(Exception e){tanggal = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
         return tanggal;
    }

    public String formatDateHour(java.util.Date date, String format, String jndiName, String username, String password) throws Exception
    {
        Db mydb = null;
        String tanggal = null;
        try
        {
            mydb = new Db();
            tanggal = "";
            mydb.execute("SELECT TO_CHAR(TO_DATE('" +
                         (new Integer(date.getDate())).toString() + "/" +
                         (new Integer(date.getMonth() + 1)).toString() + "/" +
                         (new Integer(date.getYear() + 1900)).toString() + " " +
                         (new Integer(date.getHours())).toString() + ":" +
                         (new Integer(date.getMinutes())).toString() + ":" +
                         (new Integer(date.getSeconds())).toString() +
                         "', 'DD/MM/YYYY HH24:MI:SS'), '" + format + "') TANGGAL FROM DUAL");
            if(mydb.next())
            {
                tanggal = mydb.getString("TANGGAL");
            }
        }
        catch(Exception e){tanggal = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
         return tanggal;
    }
    
    public String formatDate(String date, String format, String jndiName, String username, String password) throws Exception
    {
        Db mydb = null;
        String tanggal = null;
        try
        {
            mydb = new Db();
            tanggal = "";
            mydb.execute("SELECT TO_CHAR(TO_DATE('" + date + "', 'DD/MM/YYYY'), '" + format + "') TANGGAL FROM DUAL");
            if(mydb.next())
            {
                tanggal = mydb.getString("TANGGAL");
            }
        }catch(Exception e){tanggal = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
        return tanggal;
    }

    public String now(String format) throws Exception
    {
        Db mydb = null;
        String tanggal = null;
        try
        {
            mydb = new Db();
            tanggal = "";
            mydb.execute("SELECT TO_CHAR(SYSDATE, '" + format + "') SEKARANG FROM DUAL");
            if(mydb.next())
            {
                tanggal = mydb.getString("SEKARANG");
            }
        }catch(Exception e){tanggal = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
        return tanggal;
    }

    public String getSatuNilai(String cmd, String jndiName, String username, String password) throws Exception
    {
        Db mydb = null;
        String sn = null;
        try
        {
            mydb = new Db();
            sn = "";
            mydb.execute("SELECT " + cmd + " X FROM DUAL");
            if(mydb.next())
            {
                sn = mydb.getString("X");
            }
        }catch(Exception e){sn = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
        return sn;
    }
    
    public String getDateFormated(String date, String inputformat, String outputformat) throws Exception
    {
        Db mydb = null;
        String tanggal = null;
        try
        {
            mydb = new Db();
            tanggal = "";
            mydb.execute("select to_char(to_date('"+date+"','"+inputformat+"'),'"+outputformat+"') tanggal from dual");
            if(mydb.next())
            {
                tanggal = mydb.getString("tanggal");
            }
        }catch(Exception e){tanggal = null;}
        finally
        {
            try{mydb.close();}catch(Exception e){}
            mydb = null;
        }
        return tanggal;
    }
}
