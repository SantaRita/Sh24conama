package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWEB_GENERALES extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_GENERALES.class);
    private Connection conn = null;

    public PAC_SHWEB_GENERALES(Connection conn) {
    	//System.out.println("Entramos por el login");
        this.conn = conn;
    }

    // *******************************************************
    // *****************************   PAC_SHWEB_GENERALES.F_OBTENER_LOG_CONSULTAS
    // *******************************************************
    			   
    public HashMap ejecutaPAC_SHWEB_GENERALES__F_OBTENER_LOG_CONSULTAS (String pORIGEN, String pLIMPIAR) throws Exception {
        return this.callPAC_SHWEB_GENERALES__F_OBTENER_LOG_CONSULTAS(pORIGEN, pLIMPIAR); 
    }
    
    private HashMap callPAC_SHWEB_GENERALES__F_OBTENER_LOG_CONSULTAS (String pORIGEN, String pLIMPIAR) throws Exception {
    	
    		//System.out.println("Dentro obtener log consulltas");
    	    
            String callQuery = "{?=call PAC_SHWEB_GENERALES.F_OBTENER_LOG_CONSULTAS(?,?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pLIMPIAR);

            cStmt.execute ();
           
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }

            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            ////System.out.println("2..RETVAL antes de F_OBTENER_LOG_CONSULTAS:"+retVal.toString());
            return retVal;
    }
    
    // *******************************************************
    // *****************************   PAC_SHWEB_GENERALES.F_OBTENER_LOG
    // *******************************************************
    			   
    public HashMap ejecutaPAC_SHWEB_GENERALES__F_OBTENER_LOG (String pLIMPIAR) throws Exception {
        return this.callPAC_SHWEB_GENERALES__F_OBTENER_LOG(pLIMPIAR); 
    }
    
    private HashMap callPAC_SHWEB_GENERALES__F_OBTENER_LOG (String pLIMPIAR) throws Exception {
    	
    	    
            String callQuery = "{?=call PAC_SHWEB_GENERALES.F_OBTENER_LOG(?)}";
            
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
            cStmt.setObject (2, pLIMPIAR);

            cStmt.execute ();
            
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("RETURN", cStmt.getObject (1));
            }catch (SQLException e){
                    retVal.put ("RETURN", null);
            }

            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            ////System.out.println("2..RETVAL antes de F_OBTENER_LOG:"+retVal.toString());
            return retVal;
    }    


    
   
    
}
