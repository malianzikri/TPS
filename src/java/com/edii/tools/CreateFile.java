package com.edii.tools;

import java.io.File;
import java.io.RandomAccessFile;

public class CreateFile
{
    public boolean execute(String filename) throws Exception
    {
        boolean success = false;
        File f = null;
        try
        {
            f = new File(filename);
            try{
                f.delete();
                success = true;
            }catch(Exception e){
            
            }
            f.createNewFile();
        }catch(Exception e){
            success = false;
        }
        finally
        {
            f = null;
            return success;
        }
    }
    public boolean content(String filename,String content) throws Exception
    {
        boolean success = false;
        File f = null;
        RandomAccessFile rf = null;
        try
        {
            f = new File(filename);
            rf = new RandomAccessFile(f, "rwd");
            rf.writeBytes(content);
            rf.close();
            success = true;
        }catch(Exception e){success = false;}
        finally
        {
            f = null;
            rf = null;
            return success;
        }
    }
    public synchronized boolean content(String filename,byte[] content) throws Exception
    {
        boolean success = false;
        File f = null;
        RandomAccessFile rf = null;
        try
        {
            f = new File(filename);
            rf = new RandomAccessFile(f, "rwd");
            rf.write(content);
            rf.close();
            success = true;
        }catch(Exception e){success = false;}
        finally
        {
            f = null;
            rf = null;
            return success;
        }
    }
    public boolean content(String filename,byte[] content,int start,int end) throws Exception
    {
        boolean success = false;
        File f = null;
        RandomAccessFile rf = null;
        try
        {
            f = new File(filename);
            rf = new RandomAccessFile(f, "rwd");
            rf.write(content,start,end);
            rf.close();
            success = true;
        }catch(Exception e){success = false;}
        finally
        {
            f = null;
            rf = null;
            return success;
        }
    }
}
