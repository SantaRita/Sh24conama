package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWEB_CONSULTASAMA extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_CONSULTASAMA.class);
    private Connection conn = null;

    public PAC_SHWEB_CONSULTASAMA(Connection conn) {
    	
    	//System.out.println("Indicamos la conexion");
        this.conn = conn;
        //System.out.println("Despues Indicamos la conexion");
    }




    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_LISTA_CONSULTAS (String pCONSULTA) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_LISTA_CONSULTAS(pCONSULTA); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_LISTA_CONSULTAS (String pCONSULTA) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.LISTA_CONSULTAS(?,?,?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pCONSULTA);
            cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (3, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"            

            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (2));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (3));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (4));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }
    
    
    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_LISTA_PARAMETROS (String pIDCONSULTA) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_LISTA_PARAMETROS(pIDCONSULTA); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_LISTA_PARAMETROS (String pIDCONSULTA) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.LISTA_PARAMETROS(?,?,?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pIDCONSULTA);
            cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (3, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"            

            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (2));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (3));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (4));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }
    
    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA (String pCONSULTA, String pPARAMETROS, java.math.BigDecimal pIDCONSULTA) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA(pCONSULTA, pPARAMETROS, pIDCONSULTA); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA (String pCONSULTA, String pPARAMETROS,  java.math.BigDecimal pIDCONSULTA) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.EJECUTAR_CONSULTA(?,?,?,?,?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pCONSULTA);
            cStmt.setObject (2, pPARAMETROS);            
            cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (5, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"            

            cStmt.setObject (6, pIDCONSULTA);          

            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (3));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (4));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (5));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            System.out.println("Retval:" + retVal);
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }    
    
    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA_COMPLETA (String pCONSULTA, String pPARAMETROS, String pFICHERO, java.math.BigDecimal pIDCONSULTA) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA_COMPLETA(pCONSULTA, pPARAMETROS, pFICHERO, pIDCONSULTA); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA_COMPLETA (String pCONSULTA, String pPARAMETROS,  String pFICHERO, java.math.BigDecimal pIDCONSULTA) throws Exception {
    	
    	    
    		System.out.println("Entramos a ejecutar_consulta_completa");
    		System.out.println("pCONSULTA: " + pCONSULTA );
    		System.out.println("pPARAMETROS: " + pPARAMETROS );
    		System.out.println("pFICHERO: " + pFICHERO );
    		System.out.println("pIDCONSULTA: " + pIDCONSULTA );


            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.EJECUTAR_CONSULTA_COMPLETA(?,?,?,?,?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pCONSULTA);
            cStmt.setObject (2, pPARAMETROS);  
            cStmt.setObject (3, pFICHERO);            
            cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (5, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"            

            cStmt.setObject (6, pIDCONSULTA);          

            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (3));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (4));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (5));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            System.out.println("Retval rESULTADO:" + retVal);

            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }  
    
    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_ESTADISTICA (java.math.BigDecimal pIDCONSULTA) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_ESTADISTICA(pIDCONSULTA); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_ESTADISTICA (java.math.BigDecimal pIDCONSULTA) throws Exception {
    	
    	    
            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.ESTADISTICA(?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pIDCONSULTA);
            cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
         


            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (2));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           


            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }  
    
    public HashMap ejecutaPAC_SHWEB_CONSULTASAMA__F_RECUPERAR_CONSULTA_FICHERO (String pFICHERO) throws Exception {
        return this.callPAC_SHWEB_CONSULTASAMA__F_RECUPERAR_CONSULTA_FICHERO(pFICHERO); 
    }
    
    private HashMap callPAC_SHWEB_CONSULTASAMA__F_RECUPERAR_CONSULTA_FICHERO (String pFICHERO) throws Exception {
    	
    	    
            String callQuery = "{call PAC_SHWEB_CONSULTASAMA.RECUPERAR_CONSULTA_FICHERO(?,?)}";
            CallableStatement cStmt = conn.prepareCall(callQuery);
            

            cStmt.setObject (1, pFICHERO);
            cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CLOB); // Valor de "REFCURSOR"
         


            
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (2));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
           


            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }     
    

    

    
}
