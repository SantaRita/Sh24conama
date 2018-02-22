package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWS extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWS.class);
    private Connection conn = null;

    public PAC_SHWS(Connection conn) {
    	
        this.conn = conn;
    }

    // *******************************************************
    // *****************************   PAC_SHWS.FF_GEDOX_LIBERTY_WS
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWS__FF_GEDOX_LIBERTY_WS (java.math.BigDecimal pCDASISTE, java.math.BigDecimal pIDDOWNLOAD) throws Exception {
        return this.callPAC_SHWS__FF_GEDOX_LIBERTY_WS(pCDASISTE, pIDDOWNLOAD); 
    }
    
    
    private HashMap callPAC_SHWS__FF_GEDOX_LIBERTY_WS (java.math.BigDecimal pCDASISTE, java.math.BigDecimal pIDDOWNLOAD) throws Exception {
    	
   
    		//System.out.println("Entramos a FF_Gedox_liberty_Ws");
    		
            String callQuery = "{?=call PAC_SHWS.FF_GEDOX_LIBERTY_WS(?,?,?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (2, pCDASISTE);
            
            if ( pIDDOWNLOAD.equals("0")) {
            	cStmt.setObject (3, null);	
            }
            else {
            	cStmt.setObject (3, pIDDOWNLOAD);
            }
            
            
            cStmt.registerOutParameter (1, java.sql.Types.INTEGER); // Valor de "RETURN"
            cStmt.registerOutParameter (4, java.sql.Types.CLOB); // Valor de "RESULTADO

            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }
            
            try{
                retVal.put ("RESULTADO", cStmt.getObject (4));
            }catch (SQLException e){
                retVal.put ("RESULTADO", null);
            }

            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            
            //System.out.println("Nos vamos de la funcion FF_GEDOX_LIBERTY_WS:"+retVal );
            return retVal;
    }
    
    // *******************************************************
    // *****************************   PAC_SHWS.FF_ENVIAR_PENDIENTE
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWS__FF_ENVIAR_PENDIENTE (java.math.BigDecimal pENVIO) throws Exception {
        return this.callPAC_SHWS__FF_ENVIAR_PENDIENTE(pENVIO); 
    }
    
    
    private HashMap callPAC_SHWS__FF_ENVIAR_PENDIENTE (java.math.BigDecimal pENVIO) throws Exception {
    	
   
    		
            String callQuery = "{?=call PAC_SHWS.FF_ENVIAR_PENDIENTE(?,?,?,?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (2, pENVIO);
            
            cStmt.registerOutParameter (1, java.sql.Types.INTEGER); // Valor de "RETURN"
            cStmt.registerOutParameter (3, java.sql.Types.CLOB); // Valor de "RESULTADO
            cStmt.registerOutParameter (4, java.sql.Types.VARCHAR); // Valor de "ESTADO
            cStmt.registerOutParameter (5, java.sql.Types.DATE); // Valor de "FENVIO
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }
            
            try{
                retVal.put ("RESULTADO", cStmt.getObject (3));
            }catch (SQLException e){
                retVal.put ("RESULTADO", null);
            }

            try{
                retVal.put ("ESTADO", cStmt.getObject (4));
	        }catch (SQLException e){
	                retVal.put ("ESTADO", null);
	        }
            
            try{
                retVal.put ("FENVIO", cStmt.getObject (5));
	        }catch (SQLException e){
	                retVal.put ("FENVIO", null);
	        }            
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            
            //System.out.println("Nos vamos de la funcion"+retVal );
            return retVal;
    }
    
    
    // *******************************************************
    // *****************************   PAC_SHWS.PP_DEMONIO_PENDIENTES
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWS__PP_DEMONIO_PENDIENTES () throws Exception {
        return this.callPAC_SHWS__PP_DEMONIO_PENDIENTES(); 
    }
    
    
    private HashMap callPAC_SHWS__PP_DEMONIO_PENDIENTES () throws Exception {
    	
   
    		
            String callQuery = "{call PAC_SHWS.PP_DEMONIO_PENDIENTES()}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);

            cStmt.execute ();
            HashMap retVal = new HashMap ();
            //retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            
            //System.out.println("Nos vamos de la funcion PP_DEMONIO_PENDIENTES"+retVal );
            return retVal;
    }
    
    
    
    // *******************************************************
    // *****************************   PAC_SHWS.FF_MOVER_ENVIO
    // *******************************************************
    
    public HashMap ejecutaPAC_SHWS__FF_MOVER_ENVIO (java.math.BigDecimal pCDASISTE, java.math.BigDecimal pINI, java.math.BigDecimal pFIN) throws Exception {
        return this.callPAC_SHWS__FF_MOVER_ENVIO(pCDASISTE, pINI, pFIN); 
    }
    
    
    private HashMap callPAC_SHWS__FF_MOVER_ENVIO (java.math.BigDecimal pCDASISTE, java.math.BigDecimal pINI, java.math.BigDecimal pFIN) throws Exception {
    	
    	//System.out.println("ENtramos a mover envio cdasiste " + pCDASISTE + " pINI: " + pINI + " pFIN:" + pFIN);
        String callQuery = "{?=call PAC_SHWS.FF_MOVER_ENVIO(?,?,?)}";
        
        CallableStatement cStmt = conn.prepareCall(callQuery);
        cStmt.setObject (2, pCDASISTE);
        cStmt.setObject (3, pINI);
        cStmt.setObject (4, pFIN);
        
        cStmt.registerOutParameter (1, java.sql.Types.INTEGER); // Valor de "RETURN"
 
        cStmt.execute ();
        HashMap retVal = new HashMap ();
        try{
                retVal.put ("RETURN", cStmt.getObject (1));
        }catch (SQLException e){
                retVal.put ("RETURN", null);
        }
        

        retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
        cStmt.close();
        cStmt = null;
        conn.close();
        conn = null;
        
        //System.out.println("Nos vamos de la funcion FF_MOVER_ENVIO"+retVal );
        return retVal;
    }    
    
    


    
}
