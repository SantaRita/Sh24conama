package com.csi_ti.itaca.custom.general.server.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class AccesoSQL  {
    private Connection conn=null;
    
    public AccesoSQL(Connection conn) {
            super();
            this.conn=conn;
    }

    public int executeUpdate(Connection conn, String sql, Object[] params) throws Exception {
            //log.info("...llamando sql:"+sql+" con:"+paramsToString(params));
            PreparedStatement stmt=conn.prepareStatement(sql);
            for (int i=0;i<params.length;i++) {
                stmt.setObject(i+1, params[i]);
            }
            int retVal=stmt.executeUpdate();
            stmt.close();
            conn.close();
            stmt = null;
            conn = null;
            return retVal;
    }

    public ArrayList executeQuery(Connection conn, String sql, Object[] params) throws Exception {
        //log.info("...llamando sql:"+sql+" con:"+paramsToString(params));
        PreparedStatement stmt=conn.prepareStatement(sql);
        for (int i=0;i<params.length;i++) {
            stmt.setObject(i+1, params[i]);
        }
        ResultSet rs=stmt.executeQuery();
        ArrayList retVal = ConversionUtil.resultSet2ArrayList(rs);
        stmt.close();
        conn.close();
        stmt = null;
        conn = null;
        return retVal;
    }

    public static  String paramsToString(Object[] params) {
        StringBuffer sB=new StringBuffer();
        if (sB==null) {
            sB.append("(null)");
        }
        else {
            for (int i=0;i<params.length;i++) {
                sB.append(params[i]).append(", ");
            }
        }
        return sB.toString();

    }
    
    public ArrayList executeQuery(Connection conn, String sql) throws Exception {
            //log.warn("...llamando sql query:"+sql);
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            ArrayList retVal = ConversionUtil.resultSet2ArrayList(rs);
            stmt.close();
            conn.close();
            stmt = null;
            conn = null;
            return retVal;
    }

    public int executeUpdate(Connection conn, String sql) throws Exception {
            //log.warn("...llamando sql update:"+sql);
            Statement stmt=conn.createStatement();
            int retVal=stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            stmt = null;
            conn = null;
            return retVal;
    }

    public boolean execute(Connection conn, String sql) throws Exception {
            //log.info("...llamando instruccion:"+sql);
            Statement stmt=conn.createStatement();
            boolean retVal=stmt.execute(sql);
            stmt.close();
            conn.close();
            stmt = null;
            conn = null;
            return retVal;
    }
    
}
