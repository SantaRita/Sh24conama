package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWEB_LOGIN extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_LOGIN.class);
    private Connection conn = null;

    public PAC_SHWEB_LOGIN(Connection conn) {
    	//System.out.println("Entramos por el login");
        this.conn = conn;
    }

    // *******************************************************
    // *****************************   PAC_SHWEB_LOGIN.F_LOGIN
    // *******************************************************
    			   
    public HashMap ejecutaPAC_SHWEB_LOGIN__F_LOGIN (String pPUSUARIO) throws Exception {
        return this.callPAC_SHWEB_LOGIN__F_LOGIN(pPUSUARIO); 
    }
    
    private HashMap callPAC_SHWEB_LOGIN__F_LOGIN (String pPUSUARIO) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{?=call PAC_SHWEB_LOGIN.F_LOGIN(?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (2, pPUSUARIO);
            cStmt.registerOutParameter (1, java.sql.Types.NUMERIC); // Valor de "RETURN"
            cStmt.registerOutParameter (3, java.sql.Types.VARCHAR); // Valor de "NOMBRE"           
            cStmt.registerOutParameter (4, java.sql.Types.VARCHAR); // Valor de "APELLIDOS"
            cStmt.registerOutParameter (5, java.sql.Types.DATE); // Valor de "ALTA"
            cStmt.registerOutParameter (6, java.sql.Types.DATE); // Valor de "BAJA"
            cStmt.registerOutParameter (7, java.sql.Types.VARCHAR); // Valor de "PPOSALTAEXP"
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }
            try{
            		
                    retVal.put ("NOMBRE", cStmt.getObject (3));
            }catch (SQLException e){
                    retVal.put ("NOMBRE", null);
            }
            try{
            		
                    retVal.put ("APELLIDOS", cStmt.getObject (4));
            }catch (SQLException e){
                    retVal.put ("APELLIDOS", null);
            }

            try{
        		
                retVal.put ("POSALTAEXP", cStmt.getObject (7));
            }catch (SQLException e){
                retVal.put ("POSALTAEXP", null);
            }
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }


    
    // *******************************************************
    // *****************************   PAC_SHWEB_LOGIN.F_PETICION_DESBLOQUEO_USUARIO
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWEB_LOGIN__F_PETICION_DESBLOQUEO_USUARIO (String pPUSUARIO, String pMAIL, Date pNACIMIENTO) throws Exception {
		//System.out.println("peiticion de desbloqueo");
        return this.callPAC_SHWEB_LOGIN__F_PETICION_DESBLOQUEO_USUARIO(pPUSUARIO, pMAIL, pNACIMIENTO); 
    }
    
    
    private HashMap callPAC_SHWEB_LOGIN__F_PETICION_DESBLOQUEO_USUARIO (String pPUSUARIO, String pMAIL, Date pNACIMIENTO) throws Exception {
    	
    	    
            String callQuery = "{?=call PAC_SHWEB_LOGIN.F_PETICION_DESBLOQUEO_USUARIO(?,?,?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (2, pPUSUARIO);
            cStmt.setObject (3, pMAIL);
            cStmt.setObject (4, pNACIMIENTO);
            cStmt.registerOutParameter (1, java.sql.Types.NUMERIC); // Valor de "RETURN"
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            return retVal;
    }
    
    // *******************************************************
    // *****************************   PAC_SHWEB_LOGIN.F_DESBLOQUEO USUARIO
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWEB_LOGIN__F_DESBLOQUEO_USUARIO (String pPUSUARIO, String pPASSWORD, Integer pCODIGO) throws Exception {
		//System.out.println("Desbloqueo Usuario definitivo");
        return this.callPAC_SHWEB_LOGIN__F_DESBLOQUEO_USUARIO(pPUSUARIO, pPASSWORD, pCODIGO); 
    }
    
    
    private HashMap callPAC_SHWEB_LOGIN__F_DESBLOQUEO_USUARIO (String pPUSUARIO, String pPASSWORD, Integer pCODIGO) throws Exception {
    	
    	    
            String callQuery = "{?=call PAC_SHWEB_LOGIN.F_DESBLOQUEO_USUARIO(?,?,?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (2, pPUSUARIO);
            cStmt.setObject (3, pPASSWORD);
            cStmt.setObject (4, pCODIGO);
            cStmt.registerOutParameter (1, java.sql.Types.NUMERIC); // Valor de "RETURN"
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            return retVal;
    }    
    
    
    // *******************************************************
    // *****************************   PAC_SHWEB_LOGIN.F_REGISTRO_NUEVO_USUARIO
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWEB_LOGIN__F_REGISTRO_NUEVO_USUARIO (String pNombre, String pApe1, String pApe2
    		, String pPerfil, String pUsuario) throws Exception {
		
        return this.callPAC_SHWEB_LOGIN__F_REGISTRO_NUEVO_USUARIO(pNombre, pApe1, pApe2, pPerfil, pUsuario ); 
    }

    private HashMap callPAC_SHWEB_LOGIN__F_REGISTRO_NUEVO_USUARIO (String pNombre, String pApe1, String pApe2
    		, String pPerfil, String pUsuario ) throws Exception {
    	
    	//System.out.println("***************** dentro ************************");
        String callQuery = "{?=call PAC_SHWEB_LOGIN.F_REGISTRO_NUEVO_USUARIO(?,?,?,?,?,?)}";
        CallableStatement cStmt = conn.prepareCall(callQuery);
        String USERNAME = conn.getMetaData().getUserName().toUpperCase();
        cStmt.setObject (2, pNombre);
        cStmt.setObject (3, pApe1);
        cStmt.setObject (4, pApe2);
        cStmt.setObject (5, pPerfil);
        cStmt.setObject (6, pUsuario);
        
        cStmt.registerOutParameter (1, java.sql.Types.NUMERIC); // Valor de "RETURN"
        cStmt.registerOutParameter (6, java.sql.Types.VARCHAR); // Valor de "usuario"
        cStmt.registerOutParameter (7, java.sql.Types.VARCHAR); // Valor de "mensaje"
        cStmt.execute ();
        HashMap retVal = new HashMap ();
        try{
                retVal.put ("RETURN", cStmt.getObject (1));
                retVal.put ("MENSAJE", cStmt.getObject (7));
                retVal.put ("USUARIO", cStmt.getObject (6));                
        }catch (SQLException e){
                retVal.put ("RETURN", null);
                retVal.put ("USUARIO", null);
                retVal.put ("MENSAJE", null);
        }
        
        retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
        cStmt.close();//AXIS-WLS1SERVER-Ready
        //System.out.println("Nos vamos con: " + retVal);
        return retVal;
}    
}
