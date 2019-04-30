package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class WS_AMA extends AccesoPL {
    static Log logger = LogFactory.getLog(WS_AMA.class);
    private Connection conn = null;

    public WS_AMA(Connection conn) {
    	//System.out.println("Entramos por el login");
        this.conn = conn;
    }

    // *******************************************************
    // *****************************   WS_AMA.F_LOGIN
    // *******************************************************
    	/*
    	 * pi_cdusuario   IN       VARCHAR2,
      pi_origen      IN       VARCHAR2,
      po_tpusuario   OUT      VARCHAR2,
      po_cderror     OUT      NUMBER,
      po_txerror     OUT      VARCHAR2
      
    	 */
    public HashMap ejecutaWS_AMA__F_LOGIN (String pPUSUARIO, String pORIGEN) throws Exception {
        return this.callWS_AMA__F_LOGIN(pPUSUARIO, pORIGEN); 
    }
    
    private HashMap callWS_AMA__F_LOGIN (String pPUSUARIO, String pORIGEN) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call WS_AMA.LOGIN(?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.registerOutParameter (3, java.sql.Types.VARCHAR); // Valor de "TIPOUSUARIO"           
            cStmt.registerOutParameter (4, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
            cStmt.registerOutParameter (5, java.sql.Types.VARCHAR); // Valor de "ERROR"
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
                    retVal.put ("TIPOUSUARIO", cStmt.getObject (3));
            }catch (SQLException e){
                    retVal.put ("TIPOUSUARIO", null);
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

            try{
        		
                retVal.put ("POSALTAEXP", cStmt.getObject (7));
            }catch (SQLException e){
                retVal.put ("POSALTAEXP", null);
            }
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }

    // lista de expedientes
    /*
   PROCEDURE lista_expedientes (
      pi_cdusuario   IN       VARCHAR2,
      pi_origen      IN       VARCHAR2,
      pi_semana      IN       VARCHAR2,
      pi_numpag      IN       NUMBER,
      po_lista_exp   OUT      t_refcursor,
      po_numtotpag   OUT      NUMBER,
      po_cderror     OUT      NUMBER,
      po_txerror     OUT      VARCHAR2
   );*/

    public HashMap ejecutaWS_AMA__F_LISTA_EXPEDIENTE (String pPUSUARIO, String pORIGEN, String pSEMANA, java.math.BigDecimal pNUMPAG) throws Exception {
        return this.callWS_AMA__F_LISTA_EXPEDIENTE(pPUSUARIO, pORIGEN, pSEMANA, pNUMPAG); 
    }
    
    private HashMap callWS_AMA__F_LISTA_EXPEDIENTE (String pPUSUARIO, String pORIGEN, String pSEMANA, java.math.BigDecimal pNUMPAG) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call WS_AMA.LISTA_EXPEDIENTES(?,?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pSEMANA);
            cStmt.setObject (4, pNUMPAG);
            cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (6, oracle.jdbc.OracleTypes.NUMBER); // Valor de "NUMTOTPAG"
            cStmt.registerOutParameter (7, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (8, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"
           // System.out.println("Hacemos la llamada");
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (5));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
            try{
                    retVal.put ("NUMTOTPAG", cStmt.getObject (6));
            }catch (SQLException e){
                    retVal.put ("NUMTOTPAG", null);
            }
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (7));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (8));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            try{
        		
                retVal.put ("POSALTAEXP", cStmt.getObject (7));
            }catch (SQLException e){
                retVal.put ("POSALTAEXP", null);
            }
            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }
    
    
/*  PROCEDURE detalle_expediente (*/

    public HashMap ejecutaWS_AMA__F_DETALLE_EXPEDIENTE (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE ) throws Exception {
        return this.callWS_AMA__F_DETALLE_EXPEDIENTE(pPUSUARIO, pORIGEN, pEXPEDIENTE); 
    }
    
    private HashMap callWS_AMA__F_DETALLE_EXPEDIENTE (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call WS_AMA.DETALLE_EXPEDIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pEXPEDIENTE);

            cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "NOMBRE"
            cStmt.registerOutParameter (5, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "APELLIDO1"
            cStmt.registerOutParameter (6, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "APELLIDO2"
            cStmt.registerOutParameter (7, oracle.jdbc.OracleTypes.NUMBER); // Valor de "IDCLIENTE"
            cStmt.registerOutParameter (8, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "DSCLIENTE"
            cStmt.registerOutParameter (9, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "IDCONTRATO"
            cStmt.registerOutParameter (10, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TPBAREMO"
            cStmt.registerOutParameter (11, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "CAUSA"
            cStmt.registerOutParameter (12, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "DIRECCION"
            cStmt.registerOutParameter (13, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "CP"
            cStmt.registerOutParameter (14, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "POBLACION"
            cStmt.registerOutParameter (15, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "PROVINCIA"
            cStmt.registerOutParameter (16, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TELEFONO"
            cStmt.registerOutParameter (17, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "MAIL"
            cStmt.registerOutParameter (18, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "INCIDENCIA"
            cStmt.registerOutParameter (19, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "REVISAR"
            cStmt.registerOutParameter (20, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "OBSERVACIONES"
            cStmt.registerOutParameter (21, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "ESTADOEXP"
            cStmt.registerOutParameter (22, oracle.jdbc.OracleTypes.NUMBER); // Valor de "CDERROR"
            cStmt.registerOutParameter (23, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXERROR"
                        

            //System.out.println("Hacemos la llamada");
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{ retVal.put ("NOMBRE", cStmt.getObject (4)); }catch (SQLException e){ retVal.put ("NOMBRE", null); }
            try{ retVal.put ("APELLIDO1", cStmt.getObject (5)); }catch (SQLException e){ retVal.put ("APELLIDO1", null); }
            try{ retVal.put ("APELLIDO2", cStmt.getObject (6)); }catch (SQLException e){ retVal.put ("APELLIDO2", null); }
            try{ retVal.put ("IDCLIENTE", cStmt.getObject (7)); }catch (SQLException e){ retVal.put ("IDCLIENTE", null); }
            try{ retVal.put ("DSCLIENTE", cStmt.getObject (8)); }catch (SQLException e){ retVal.put ("DSCLIENTE", null); }
            try{ retVal.put ("IDCONTRATO", cStmt.getObject (9)); }catch (SQLException e){ retVal.put ("IDCONTRATO", null); }
            try{ retVal.put ("TPBAREMO", cStmt.getObject (10)); }catch (SQLException e){ retVal.put ("TPBAREMO", null); }
            try{ retVal.put ("CAUSA", cStmt.getObject (11)); }catch (SQLException e){ retVal.put ("CAUSA", null); }
            try{ retVal.put ("DIRECCION", cStmt.getObject (12)); }catch (SQLException e){ retVal.put ("DIRECCION", null); }
            try{ retVal.put ("CP", cStmt.getObject (13)); }catch (SQLException e){ retVal.put ("CP", null); }
            try{ retVal.put ("POBLACION", cStmt.getObject (14)); }catch (SQLException e){ retVal.put ("POBLACION", null); }
            try{ retVal.put ("PROVINCIA", cStmt.getObject (15)); }catch (SQLException e){ retVal.put ("PROVINCIA", null); }
            try{ retVal.put ("TELEFONO", cStmt.getObject (16)); }catch (SQLException e){ retVal.put ("TELEFONO", null); }
            try{ retVal.put ("MAIL", cStmt.getObject (17)); }catch (SQLException e){ retVal.put ("MAIL", null); }
            try{ retVal.put ("INCIDENCIA", cStmt.getObject (18)); }catch (SQLException e){ retVal.put ("INCIDENCIA", null); }
            try{ retVal.put ("REVISAR", cStmt.getObject (19)); }catch (SQLException e){ retVal.put ("REVISAR", null); }
            try{ retVal.put ("OBSERVACIONES", cStmt.getObject (20)); }catch (SQLException e){ retVal.put ("OBSERVACIONES", null); }
            try{ retVal.put ("ESTADOEXP", cStmt.getObject (21)); }catch (SQLException e){ retVal.put ("ESTADOEXP", null); }
            try {
                    retVal.put ("CODIGOERROR", cStmt.getObject (22));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (23));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }


            
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL DETALLE_EXPEDIENTE :"+retVal.toString());
            return retVal;
    }
    
    /* actualizar flag revisado
   PROCEDURE actualizar_flag_revisado (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      pi_valor        IN       VARCHAR2,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )     * 
     */
    
    public HashMap ejecutaWS_AMA__ACTUALIZAR_FLAG_REVISADO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE, String pVALOR) throws Exception {
        return this.callWS_AMA__ACTUALIZAR_FLAG_REVISADO(pPUSUARIO, pORIGEN, pEXPEDIENTE, pVALOR); 
    }
    
    private HashMap callWS_AMA__ACTUALIZAR_FLAG_REVISADO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE, String pVALOR) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call WS_AMA.ACTUALIZAR_FLAG_REVISADO(?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pEXPEDIENTE);
            cStmt.setObject (4, pVALOR);
          
            cStmt.registerOutParameter (5, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
            cStmt.registerOutParameter (6, java.sql.Types.VARCHAR); // Valor de "ERROR"
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (5));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (6));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

               
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
           // System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }
    
    // CABECERA_PRESUPUESTO
    /*   PROCEDURE cabecera_presupuesto (
      pi_cdusuario       IN       VARCHAR2,
      pi_origen          IN       VARCHAR2,
      pi_expediente      IN       NUMBER,
      pi_idgremio        IN       NUMBER,
      po_idpresup        OUT      NUMBER,
      po_estadopresup    OUT      VARCHAR2,
      po_lista_gremios   OUT      t_refcursor,
      po_cderror         OUT      NUMBER,
      po_txerror         OUT      VARCHAR2
   )*/
    
    public HashMap ejecutaWS_AMA__CABECERA_PRESUPUESTO(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		, java.math.BigDecimal pIDGREMIO) throws Exception {
        return this.callWS_AMA__CABECERA_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDGREMIO); 
    }
    
    private HashMap callWS_AMA__CABECERA_PRESUPUESTO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		, java.math.BigDecimal pIDGREMIO) throws Exception {
    	
    	    
    		//System.out.println("Entramos cabecera presupuesto con exp:" + pEXPEDIENTE);
            String callQuery = "{call WS_AMA.CABECERA_PRESUPUESTO(?,?,?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pEXPEDIENTE);
            cStmt.setObject (4, pIDGREMIO);
          
            cStmt.registerOutParameter (5, java.sql.Types.NUMERIC); // Valor de "IDPRESU"
            cStmt.registerOutParameter (6, java.sql.Types.VARCHAR); // Valor de "ESTADOPRESU"
            cStmt.registerOutParameter (7, oracle.jdbc.OracleTypes.CURSOR); // Valor de "LISTAGREMIOS"
            cStmt.registerOutParameter (8, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
            cStmt.registerOutParameter (9, java.sql.Types.VARCHAR); // Valor de "ERROR"
            
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("IDPRESU", cStmt.getObject (5));
	        }catch (SQLException e){
	                retVal.put ("IDPRESU", null);
	        }
            try{
                retVal.put ("ESTADOPRESU", cStmt.getObject (6));
	        }catch (SQLException e){
	                retVal.put ("ESTADOPRESU", null);
	        }               
            try{
                retVal.put ("REGISTROS", cStmt.getObject (7));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }             
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (8));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (9));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

               
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL WS_AMA.CABECERA_PRESUPUESTO antes de salir:"+retVal.toString());
            return retVal;
    }
    
    
    // DETALLE GREMIO PRESUPUESTO
    /*
     * PROCEDURE detalle_gremio_presupuesto (
      pi_cdusuario        IN       VARCHAR2,
      pi_origen           IN       VARCHAR2,
      pi_expediente       IN       NUMBER,
      pi_idpresup         IN       NUMBER,
      pi_idgremio         IN       NUMBER,
      pi_asegperj         IN       VARCHAR2,
      po_detalle_gremio   OUT      t_refcursor,
      po_cderror          OUT      NUMBER,
      po_txerror          OUT      VARCHAR2
   )*/
     public HashMap ejecutaWS_AMA__DETALLE_GREMIO_PRESUPUESTO(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
     		,java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO, String pASEGPERJ) throws Exception {
         return this.callWS_AMA__DETALLE_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ); 
     }
     
     private HashMap callWS_AMA__DETALLE_GREMIO_PRESUPUESTO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		 ,java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO, String pASEGPERJ) throws Exception {
     	
     	    
     		//System.out.println("Entramos a pac_shweblogin");
             String callQuery = "{call WS_AMA.DETALLE_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?,?)}";
             logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
             CallableStatement cStmt = conn.prepareCall(callQuery);
             //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
             cStmt.setObject (1, pPUSUARIO);
             cStmt.setObject (2, pORIGEN);
             cStmt.setObject (3, pEXPEDIENTE);
             cStmt.setObject (4, pIDPRESUP);
             cStmt.setObject (5, pIDGREMIO);
             cStmt.setObject (6, pASEGPERJ);
           
             cStmt.registerOutParameter (7, oracle.jdbc.OracleTypes.CURSOR); // Valor de "LISTABAREMOS"
             cStmt.registerOutParameter (8, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
             cStmt.registerOutParameter (9, java.sql.Types.VARCHAR); // Valor de "ERROR"
             
             cStmt.execute ();
             //System.out.println("Despues del login:"+ cStmt.getObject (1));
             HashMap retVal = new HashMap ();
              
             try{
                 retVal.put ("TRABAJOS", cStmt.getObject (7));
 	        }catch (SQLException e){
 	                retVal.put ("TRABAJOS", null);
 	        }             
             try{
             		
                     retVal.put ("CODIGOERROR", cStmt.getObject (8));
             }catch (SQLException e){
                     retVal.put ("CODIGOERROR", null);
             }
             try{
             		
                     retVal.put ("TEXTOERROR", cStmt.getObject (9));
             }catch (SQLException e){
                     retVal.put ("TEXTOERROR", null);
             }

                
             retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
             cStmt.close();//AXIS-WLS1SERVER-Ready
             cStmt = null;
             conn.close();
             conn = null;
             //System.out.println("2..RETVAL DETALLE_GREMIO_PRESUPUESTO:"+retVal.toString());
             return retVal;
     }
     
     
     // CREAR COMUNICADO
     /*   PROCEDURE crear_comunicado (
      pi_cdusuario     IN       VARCHAR2,
      pi_origen        IN       VARCHAR2,
      pi_expediente    IN       NUMBER,
      pi_tpcomuni      IN       VARCHAR2,
      pi_txcomuni      IN       VARCHAR2,
      pi_lista_atrib   IN       t_lista_atrib,
      pi_estadoexp     IN       VARCHAR2,
      po_estadoexp     OUT      VARCHAR2,
      po_cderror       OUT      NUMBER,
      po_txerror       OUT      VARCHAR2
   )*/
     public HashMap ejecutaWS_AMA__CREAR_COMUNICADO(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
      		,String pTPCOMUNI, String pTXCOMUNI, String pESTADO, Object[] pATRIBUTOS ) throws Exception {
          return this.callWS_AMA__CREAR_COMUNICADO(pPUSUARIO, pORIGEN, pEXPEDIENTE, pTPCOMUNI, pTXCOMUNI, pESTADO, pATRIBUTOS); 
      }
      
      private HashMap callWS_AMA__CREAR_COMUNICADO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
        		,String pTPCOMUNI, String pTXCOMUNI, String pESTADO, Object[] pATRIBUTOS) throws Exception {
      	
      	    
    	  
    	
      	//System.out.println("Entramos a ama_crear_comunicado con size atributo:" + pATRIBUTOS);
              String callQuery = "{call WS_AMA.CREAR_COMUNICADO(?,?,?,?,?,?,?,?,?,?)}";
              logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
              CallableStatement cStmt = conn.prepareCall(callQuery);
              //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
              cStmt.setObject (1, pPUSUARIO);
              cStmt.setObject (2, pORIGEN);
              cStmt.setObject (3, pEXPEDIENTE);
              cStmt.setObject (4, pTPCOMUNI);
              cStmt.setObject (5, pTXCOMUNI);

             // System.out.println("Usuario: " + pPUSUARIO + " Origen: "+pORIGEN + " PTCOMUNI:"+pTPCOMUNI + " XCOMUNI: " + pTXCOMUNI);
              
              // Si el comunicado es V1 o PC tenemos que modificar la estructura
              if ( pTPCOMUNI.equals("PC")) {
            	
            	  Struct registros = null;
            	  List<Object> registrosList = new ArrayList<>();
            	  Array registrosArray = null;            	  
            	  
            	  System.out.println("111. - " + pATRIBUTOS[0] + ">> " + pATRIBUTOS[1]);
            	  System.out.println("222. - " + pATRIBUTOS[2] + ">> " + pATRIBUTOS[3]);
            	  System.out.println("333. - " + pATRIBUTOS[4] + ">> " + pATRIBUTOS[5]);
            	  System.out.println("444. - " + pATRIBUTOS[6] + ">> " + pATRIBUTOS[7]);
            	  System.out.println("555. - " + pATRIBUTOS[8] + ">> " + pATRIBUTOS[9]);
            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[0], pATRIBUTOS[1] } );
            	  registrosList.add(registros);
            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[2], pATRIBUTOS[3] } );
            	  registrosList.add(registros);
            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[4], pATRIBUTOS[5] } );
            	  registrosList.add(registros);
            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[6], pATRIBUTOS[7] } );
            	  registrosList.add(registros);
            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[8], pATRIBUTOS[9] } );
            	  registrosList.add(registros);            	  
            	  
            	  registrosArray = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",registrosList.toArray());
            	  cStmt.setObject (6, registrosArray);
              }
              else if ( pTPCOMUNI.equals("V1")) {
					
	            	  Struct registros = null;
	            	  List<Object> registrosList = new ArrayList<>();
	            	  Array registrosArray = null;            	  
	            	  
	            	  System.out.println("111. - " + pATRIBUTOS[0] + ">> " + pATRIBUTOS[1]);
	            	  System.out.println("222. - " + pATRIBUTOS[2] + ">> " + pATRIBUTOS[3]);
	            	  System.out.println("333. - " + pATRIBUTOS[4] + ">> " + pATRIBUTOS[5]);
	            	  System.out.println("444. - " + pATRIBUTOS[6] + ">> " + pATRIBUTOS[7]);
	            	  System.out.println("555. - " + pATRIBUTOS[8] + ">> " + pATRIBUTOS[9]);
	            	  System.out.println("666. - " + pATRIBUTOS[10] + ">> " + pATRIBUTOS[11]);
	            	  System.out.println("777. - " + pATRIBUTOS[12] + ">> " + pATRIBUTOS[13]);
	            	  System.out.println("888. - " + pATRIBUTOS[14] + ">> " + pATRIBUTOS[15]);
	            	  System.out.println("999. - " + pATRIBUTOS[16] + ">> " + pATRIBUTOS[17]);
	            	  System.out.println("100. - " + pATRIBUTOS[18] + ">> " + pATRIBUTOS[19]);	            	  
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[0], pATRIBUTOS[1] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[2], pATRIBUTOS[3] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[4], pATRIBUTOS[5] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[6], pATRIBUTOS[7] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[8], pATRIBUTOS[9] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[10], pATRIBUTOS[11] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[12], pATRIBUTOS[13] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[14], pATRIBUTOS[15] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[16], pATRIBUTOS[17] } );
	            	  registrosList.add(registros);
	            	  registros = conn.createStruct("AMA_ADMON.T_ATRIB", new Object [] { pATRIBUTOS[18], pATRIBUTOS[19] } );
	            	  registrosList.add(registros); 	            	  
	            	  
	            	  registrosArray = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",registrosList.toArray());
	            	  cStmt.setObject (6, registrosArray);
              }
              else {
              
            	  	  if ( pATRIBUTOS != null ) {
            	  		  
            	  		 System.out.println("entramos con atributos" + pATRIBUTOS);
				              Struct recordStruct = conn.createStruct(("AMA_ADMON" + ".t_atrib").toUpperCase(), pATRIBUTOS);
				              Object[] arrayObjectTable = {recordStruct};
		              		  Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",arrayObjectTable);
		                 	  //a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",null);
		                 	 cStmt.setObject (6, a);
		              }
		              else {
		            	  System.out.println("entramos sin atributos");
			              Struct recordStruct = conn.createStruct(("AMA_ADMON" + ".t_atrib").toUpperCase(), null);
			              Object[] arrayObjectTable = {recordStruct};
	              		  Array a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",arrayObjectTable);
	                 	  a = conn.unwrap(oracle.jdbc.OracleConnection.class).createARRAY("T_LISTA_ATRIB",null);
	                 	 cStmt.setObject (6, a);
		              }
		              
              }
              
              System.out.println("PESTADO: " + pESTADO );              
              cStmt.setObject (7, pESTADO); // pestado lo ponemos a null
            
              cStmt.registerOutParameter (8, java.sql.Types.VARCHAR); // Valor de "ESTADOEXPEDIENTE"
              cStmt.registerOutParameter (9, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
              cStmt.registerOutParameter (10, java.sql.Types.VARCHAR); // Valor de "ERROR"
              
              cStmt.execute ();
              //System.out.println("Despues del login:"+ cStmt.getObject (1));
              HashMap retVal = new HashMap ();
               
              try{
                  retVal.put ("ESTADOEXPEDIENTE", cStmt.getObject (8));
  	        }catch (SQLException e){
  	                retVal.put ("ESTADOEXPEDIENTE", null);
  	        }             
              try{
              		
                      retVal.put ("CODIGOERROR", cStmt.getObject (9));
              }catch (SQLException e){
                      retVal.put ("CODIGOERROR", null);
              }
              try{
              		
                      retVal.put ("TEXTOERROR", cStmt.getObject (10));
              }catch (SQLException e){
                      retVal.put ("TEXTOERROR", null);
              }

                 
              retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
              cStmt.close();//AXIS-WLS1SERVER-Ready
              cStmt = null;
              conn.close();
              conn = null;
              //System.out.println("2..RETVAL CREAR_COMUNICADO:"+retVal.toString());
              return retVal;
      }
      
      /* MESTRO COMUNICADOS
       * 
       *    PROCEDURE maestro_comunicados (
      pi_cdusuario     IN       VARCHAR2,
      pi_origen        IN       VARCHAR2,
      pi_tpcomuni      IN       VARCHAR2,
      pi_tpusuario     IN       VARCHAR2,
      pi_estadoexp     IN       VARCHAR2,
      po_comunicados   OUT      t_refcursor,
      po_cderror       OUT      NUMBER,
      po_txerror       OUT      VARCHAR2
   )
       */
      
      public HashMap ejecutaWS_AMA__MAESTRO_COMUNICADOS(String pPUSUARIO, String pORIGEN, String pTPCOMUNI,
    		     String pTPUSUARIO, String pESTADO  ) throws Exception {
            return this.callWS_AMA__MAESTRO_COMUNICADOS(pPUSUARIO, pORIGEN, pTPCOMUNI,
            		pTPUSUARIO, pESTADO); 
      }
        
      private HashMap callWS_AMA__MAESTRO_COMUNICADOS(String pPUSUARIO, String pORIGEN, String pTPCOMUNI,
 		     String pTPUSUARIO, String pESTADO) throws Exception {
        	
        	    
        		//System.out.println("Entramos a pac_shweblogin");
                String callQuery = "{call WS_AMA.MAESTRO_COMUNICADOS(?,?,?,?,?,?,?,?)}";
                logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
                CallableStatement cStmt = conn.prepareCall(callQuery);
                //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
                cStmt.setObject (1, pPUSUARIO);
                cStmt.setObject (2, pORIGEN);
                cStmt.setObject (3, pTPCOMUNI);
                cStmt.setObject (4, pTPUSUARIO);
                cStmt.setObject (5, pESTADO);
              
                cStmt.registerOutParameter (6, oracle.jdbc.OracleTypes.CURSOR); // Valor de "LISTABAREMOS"
                cStmt.registerOutParameter (7, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
                cStmt.registerOutParameter (8, java.sql.Types.VARCHAR); // Valor de "ERROR"
                
                cStmt.execute ();
                //System.out.println("Despues del login:"+ cStmt.getObject (1));
                HashMap retVal = new HashMap ();
                 
                try{
                    retVal.put ("COMUNICADOS", cStmt.getObject (6));
    	        }catch (SQLException e){
    	                retVal.put ("COMUNICADOS", null);
    	        }             
                try{
                		
                        retVal.put ("CODIGOERROR", cStmt.getObject (7));
                }catch (SQLException e){
                        retVal.put ("CODIGOERROR", null);
                }
                try{
                		
                        retVal.put ("TEXTOERROR", cStmt.getObject (8));
                }catch (SQLException e){
                        retVal.put ("TEXTOERROR", null);
                }

                   
                retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
                cStmt.close();//AXIS-WLS1SERVER-Ready
                cStmt = null;
                conn.close();
                conn = null;
                //System.out.println("2..RETVAL MAESTRO_COMUNICADOS:"+retVal.toString());
                return retVal;
     }
      
      /* maestro gremios
       * 
   PROCEDURE maestro_gremios (
      pi_cdusuario       IN       VARCHAR2,
      pi_origen          IN       VARCHAR2,
      pi_idgremio        IN       NUMBER,
      po_lista_gremios   OUT      t_refcursor,
      po_cderror         OUT      NUMBER,
      po_txerror         OUT      VARCHAR2
   )
       */

   public HashMap ejecutaWS_AMA__MAESTRO_GREMIOS(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pIDGREMIO) throws Exception {
         return this.callWS_AMA__MAESTRO_GREMIOS(pPUSUARIO, pORIGEN, pIDGREMIO); 
   }
     
   private HashMap callWS_AMA__MAESTRO_GREMIOS(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pIDGREMIO) 
		   throws Exception {

             String callQuery = "{call WS_AMA.MAESTRO_GREMIOS(?,?,?,?,?,?)}";
             logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
             CallableStatement cStmt = conn.prepareCall(callQuery);
             //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
             cStmt.setObject (1, pPUSUARIO);
             cStmt.setObject (2, pORIGEN);
             cStmt.setObject (3, pIDGREMIO);

           
             cStmt.registerOutParameter (4, oracle.jdbc.OracleTypes.CURSOR); // Valor de "LISTAGREMIOS"
             cStmt.registerOutParameter (5, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
             cStmt.registerOutParameter (6, java.sql.Types.VARCHAR); // Valor de "ERROR"
             
             cStmt.execute ();
             //System.out.println("Despues del login:"+ cStmt.getObject (1));
             HashMap retVal = new HashMap ();
              
             try{
                 retVal.put ("GREMIOS", cStmt.getObject (4));
 	        }catch (SQLException e){
 	                retVal.put ("COMUNICADOS", null);
 	        }             
             try{
             		
                     retVal.put ("CODIGOERROR", cStmt.getObject (5));
             }catch (SQLException e){
                     retVal.put ("CODIGOERROR", null);
             }
             try{
             		
                     retVal.put ("TEXTOERROR", cStmt.getObject (6));
             }catch (SQLException e){
                     retVal.put ("TEXTOERROR", null);
             }

                
             retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
             cStmt.close();//AXIS-WLS1SERVER-Ready
             cStmt = null;
             conn.close();
             conn = null;
             //System.out.println("2..RETVAL MAESTRO_GREMIOS:"+retVal.toString());
             return retVal;
  }
   
  /* MAESTRO BAREMOS
   *   PROCEDURE maestro_baremo (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_idcliente    IN       NUMBER,
      pi_idcontrato   IN       NUMBER,
      pi_tpbaremo     IN       VARCHAR2,
      pi_idgremio     IN       NUMBER,
      pi_iditem2      IN       VARCHAR2,
      pi_iditem3      IN       VARCHAR2,
      pi_fecha        IN       VARCHAR2,
      pi_numpag       IN       NUMBER,
      po_baremo       OUT      t_refcursor,
      po_numtotpag    OUT      NUMBER,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )
   */
   public HashMap ejecutaWS_AMA__MAESTRO_BAREMOS(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pIDCLIENTE
		   , java.math.BigDecimal pIDCONTRATO, String pTPBAREMO, java.math.BigDecimal pIDGREMIO
		   , String pIDITEM2, String pIDITEM3, Date pFECHA, java.math.BigDecimal pNUMPAG ) throws Exception {
       return this.callWS_AMA__MAESTRO_BAREMOS(pPUSUARIO, pORIGEN, pIDCLIENTE
    		   , pIDCONTRATO, pTPBAREMO, pIDGREMIO
    		   , pIDITEM2, pIDITEM3, pFECHA, pNUMPAG ); 
   }
   
   private HashMap callWS_AMA__MAESTRO_BAREMOS(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pIDCLIENTE
		   , java.math.BigDecimal pIDCONTRATO, String pTPBAREMO, java.math.BigDecimal pIDGREMIO
		   , String pIDITEM2, String pIDITEM3, Date pFECHA, java.math.BigDecimal pNUMPAG) 
		   throws Exception {

           String callQuery = "{call WS_AMA.MAESTRO_BAREMO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
           logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
           CallableStatement cStmt = conn.prepareCall(callQuery);
           //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
           cStmt.setObject (1, pPUSUARIO);
           cStmt.setObject (2, pORIGEN);
           cStmt.setObject (3, pIDCLIENTE);
           cStmt.setObject (4, pIDCONTRATO);
           cStmt.setObject (5, pTPBAREMO);
           cStmt.setObject (6, pIDGREMIO);
           cStmt.setObject (7, pIDITEM2);
           cStmt.setObject (8, pIDITEM3);
           cStmt.setObject (9, pFECHA);
           cStmt.setObject (10, pNUMPAG);

         
           cStmt.registerOutParameter (11, oracle.jdbc.OracleTypes.CURSOR); // Valor de "LISTABAREMOS"
           cStmt.registerOutParameter (12, oracle.jdbc.OracleTypes.NUMERIC); // Valor de "NUMEROPAGINAS"
           cStmt.registerOutParameter (13, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
           cStmt.registerOutParameter (14, java.sql.Types.VARCHAR); // Valor de "ERROR"
           
           cStmt.execute ();
           //System.out.println("Despues del login:"+ cStmt.getObject (1));
           HashMap retVal = new HashMap ();
            
           try{
               retVal.put ("BAREMOS", cStmt.getObject (11));
	        }catch (SQLException e){
	                retVal.put ("BAREMOS", null);
	        }             
           try{
           		
                   retVal.put ("CODIGOERROR", cStmt.getObject (13));
           }catch (SQLException e){
                   retVal.put ("CODIGOERROR", null);
           }
           try{
           		
                   retVal.put ("TEXTOERROR", cStmt.getObject (14));
           }catch (SQLException e){
                   retVal.put ("TEXTOERROR", null);
           }

              
           retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
           cStmt.close();//AXIS-WLS1SERVER-Ready
           cStmt = null;
           conn.close();
           conn = null;
           //System.out.println("2..RETVAL MAESTRO_BAREMOS:"+retVal.toString());
           return retVal;
   }
   
   /* ELIMINAR ITEM PRESPUESTO
    *    PROCEDURE eliminar_item_presupuesto (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      pi_idpresup     IN       NUMBER,
      pi_idgremio     IN       NUMBER,
      pi_asegperj     IN       VARCHAR2,
      pi_iditem2      IN       VARCHAR2,
      pi_iditem3      IN       VARCHAR2,
      pi_estadoexp    IN       VARCHAR2,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )*/

    public HashMap ejecutaWS_AMA__ELIMINAR_ITEM_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
 		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
 		   , String pASEGPERJ, String pIDITEM2, String pIDITEM3, String pESTADO, String pDANOS ) throws Exception {
        return this.callWS_AMA__ELIMINAR_ITEM_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
     		   , pIDPRESUP, pIDGREMIO, pASEGPERJ, pIDITEM2, pIDITEM3, pESTADO, pDANOS ); 
    }
    
    private HashMap callWS_AMA__ELIMINAR_ITEM_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
  		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
  		   , String pASEGPERJ, String pIDITEM2, String pIDITEM3, String pESTADO, String pDANOS ) 
 		   throws Exception {

            String callQuery = "{call WS_AMA.ELIMINAR_ITEM_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            //String USERNAME = conn.getMetaData().getUserName().toUpperCase();
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pEXPEDIENTE);
            cStmt.setObject (4, pIDPRESUP);
            cStmt.setObject (5, pIDGREMIO);
            cStmt.setObject (6, pASEGPERJ);
            cStmt.setObject (7, pIDITEM2);
            cStmt.setObject (8, pIDITEM3);
            cStmt.setObject (9, pESTADO);
            
            System.out.println("pPUSUARIO: " + pPUSUARIO);
            System.out.println("pORIGEN: " + pORIGEN);
            System.out.println("pEXPEDIENTE: " + pEXPEDIENTE);
            System.out.println("pIDPRESUP: " + pIDPRESUP);
            System.out.println("pIDGREMIO: " + pIDGREMIO);
            System.out.println("pASEGPERJ: " + pASEGPERJ);
            System.out.println("pIDITEM2: " + pIDITEM2);
            System.out.println("pIDITEM3: " + pIDITEM3);
            System.out.println("pESTADO: " + pESTADO);
            

            cStmt.registerOutParameter (10, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
            cStmt.registerOutParameter (11, java.sql.Types.VARCHAR); // Valor de "ERROR"
            
            System.out.println("eliminamos los danos: " + pDANOS);
            
            cStmt.setObject (12, pDANOS);
            
            System.out.println("pDANOS: " + pDANOS);
            
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
           
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (10));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (11));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

               
            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL eliminar item presupuesto:"+retVal.toString());
            return retVal;
    }
    
    /* MODIFICAR ITEM PRESUPUESTO
     *    PROCEDURE modificar_item_presupuesto (
      pi_cdusuario       IN       VARCHAR2,
      pi_origen          IN       VARCHAR2,
      pi_expediente      IN       NUMBER,
      pi_idpresup        IN       NUMBER,
      pi_idgremio        IN       NUMBER,
      pi_asegperj        IN       VARCHAR2,
      pi_iditem2         IN       VARCHAR2,
      pi_iditem3         IN       VARCHAR2,
      pi_unidades        IN       NUMBER,
      pi_importe         IN       NUMBER,
      pi_observaciones   IN       VARCHAR2,
      pi_estadoexp       IN       VARCHAR2,
      po_cderror         OUT      NUMBER,
      po_txerror         OUT      VARCHAR2,
      pi_danos           IN       VARCHAR2 DEFAULT NULL     --D917 - ARBOL LIB
   )
     */
    
    public HashMap ejecutaWS_AMA__MODIFICAR_ITEM_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
  		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
  		   , String pASEGPERJ, String pIDITEM2, String pIDITEM3, java.math.BigDecimal pUNIDADES
  		   , java.math.BigDecimal pIMPORTE, String pOBSERVACIONES, String pESTADO , String pDANOS ) throws Exception {
         return this.callWS_AMA__MODIFICAR_ITEM_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
      		   , pIDPRESUP, pIDGREMIO, pASEGPERJ, pIDITEM2, pIDITEM3, pUNIDADES, pIMPORTE, pOBSERVACIONES, pESTADO, pDANOS ); 
     }
     
     private HashMap callWS_AMA__MODIFICAR_ITEM_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
      		   , String pASEGPERJ, String pIDITEM2, String pIDITEM3, java.math.BigDecimal pUNIDADES
      		   , java.math.BigDecimal pIMPORTE, String pOBSERVACIONES, String pESTADO , String pDANOS ) 
  		   throws Exception {

    	 	 System.out.println("Entramos a modificar");
    	 	 System.out.println("USUARIO: " + pPUSUARIO);
    	 	 System.out.println("ORIGEN: " + pORIGEN);
    	 	 System.out.println("EXPEDIENTE: " + pEXPEDIENTE);
    	 	 System.out.println("PRESUP: " + pIDPRESUP);
    	 	 System.out.println("GREMIO: " + pIDGREMIO);
    	 	 System.out.println("ASEGPERJ: " + pASEGPERJ);
    	 	 System.out.println("IDITEM2: " + pIDITEM2);
    	 	System.out.println("IDITEM3: " + pIDITEM3);
    	 	System.out.println("UNIDADES: " + pUNIDADES);
    	 	System.out.println("IMPORTE: " + pIMPORTE);
    	 	System.out.println("OBSERVACIONES: " + pOBSERVACIONES);
    	 	System.out.println("ESTADO: " + pESTADO);
    	 	System.out.println("DAÑOS: " + pDANOS);
    	 	 
             String callQuery = "{call WS_AMA.MODIFICAR_ITEM_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
             logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
             CallableStatement cStmt = conn.prepareCall(callQuery);
             ////String USERNAME = conn.getMetaData().getUserName().toUpperCase();
             cStmt.setObject (1, pPUSUARIO);
             cStmt.setObject (2, pORIGEN);
             cStmt.setObject (3, pEXPEDIENTE);
             cStmt.setObject (4, pIDPRESUP);
             cStmt.setObject (5, pIDGREMIO);
             cStmt.setObject (6, pASEGPERJ);
             cStmt.setObject (7, pIDITEM2);
             cStmt.setObject (8, pIDITEM3);
             cStmt.setObject (9, pUNIDADES);
             cStmt.setObject (10, pIMPORTE);
             cStmt.setObject (11, pOBSERVACIONES);
             cStmt.setObject (12, pESTADO);
             
             //System.out.println("Los danños son: " + pDANOS);
             cStmt.setObject (15, pDANOS);

             cStmt.registerOutParameter (13, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
             cStmt.registerOutParameter (14, java.sql.Types.VARCHAR); // Valor de "ERROR"
             
             cStmt.execute ();
             //System.out.println("Despues del login:"+ cStmt.getObject (1));
             HashMap retVal = new HashMap ();
            
             try{
             		
                     retVal.put ("CODIGOERROR", cStmt.getObject (13));
             }catch (SQLException e){
                     retVal.put ("CODIGOERROR", null);
             }
             try{
             		
                     retVal.put ("TEXTOERROR", cStmt.getObject (14));
             }catch (SQLException e){
                     retVal.put ("TEXTOERROR", null);
             }

                
             retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
             cStmt.close();//AXIS-WLS1SERVER-Ready
             cStmt = null;
             conn.close();
             conn = null;
             System.out.println("2..RETVAL MODIFICAR ITEM baremos presupuesto:"+retVal.toString());
             return retVal;
     }
     
     /* crear gremio
      *    PROCEDURE crear_gremio_presupuesto (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      pi_idpresup     IN       NUMBER,
      pi_idgremio     IN       NUMBER,
      pi_asegperj     IN       VARCHAR2,
      pi_fhgremio     IN       VARCHAR2,
      pi_estadoexp    IN       VARCHAR2,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )*/
     
     public HashMap ejecutaWS_AMA__CREAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
    		   , String pASEGPERJ, String pFHGREMIO, String pESTADO  ) throws Exception {
           return this.callWS_AMA__CREAR_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
        		   , pIDPRESUP, pIDGREMIO, pASEGPERJ, pFHGREMIO, pESTADO ); 
       }
       
       private HashMap callWS_AMA__CREAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
    		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
    		   , String pASEGPERJ, String pFHGREMIO, String pESTADO ) 
    		   throws Exception {

               String callQuery = "{call WS_AMA.CREAR_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?)}";
               logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
               CallableStatement cStmt = conn.prepareCall(callQuery);
               ////String USERNAME = conn.getMetaData().getUserName().toUpperCase();
               cStmt.setObject (1, pPUSUARIO);
               cStmt.setObject (2, pORIGEN);
               cStmt.setObject (3, pEXPEDIENTE);
               cStmt.setObject (4, pIDPRESUP);
               cStmt.setObject (5, pIDGREMIO);
               cStmt.setObject (6, pASEGPERJ);
               cStmt.setObject (7, pFHGREMIO);
               cStmt.setObject (8, pESTADO);
               
               cStmt.registerOutParameter (9, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
               cStmt.registerOutParameter (10, java.sql.Types.VARCHAR); // Valor de "ERROR"
               
               cStmt.execute ();
               //System.out.println("Despues del login:"+ cStmt.getObject (1));
               HashMap retVal = new HashMap ();
              
               try{
               		
                       retVal.put ("CODIGOERROR", cStmt.getObject (9));
               }catch (SQLException e){
                       retVal.put ("CODIGOERROR", null);
               }
               try{
               		
                       retVal.put ("TEXTOERROR", cStmt.getObject (10));
               }catch (SQLException e){
                       retVal.put ("TEXTOERROR", null);
               }

                  
               retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
               cStmt.close();//AXIS-WLS1SERVER-Ready
               cStmt = null;
               conn.close();
               conn = null;
               System.out.println("2..RETVAL eliminar MODIFICAR ITEM:"+retVal.toString());
               return retVal;
       }
       
       /* ELIMINAR GREMIO PRESUPUESTO
        *    PROCEDURE eliminar_gremio_presupuesto (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      pi_idpresup     IN       NUMBER,
      pi_idgremio     IN       NUMBER,
      pi_asegperj     IN       VARCHAR2,
      pi_encascada    IN       VARCHAR2,
      pi_estadoexp    IN       VARCHAR2,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
  
        */
       
       
       public HashMap ejecutaWS_AMA__ELIMINAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
      		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
      		   , String pASEGPERJ, String pESTADO  ) throws Exception {
             return this.callWS_AMA__ELIMINAR_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
          		   , pIDPRESUP, pIDGREMIO, pASEGPERJ, pESTADO ); 
         }
         
         private HashMap callWS_AMA__ELIMINAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
      		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
      		   , String pASEGPERJ, String pESTADO ) 
      		   throws Exception {

                 String callQuery = "{call WS_AMA.ELIMINAR_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?)}";
                 logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
                 CallableStatement cStmt = conn.prepareCall(callQuery);
                 ////String USERNAME = conn.getMetaData().getUserName().toUpperCase();
                 cStmt.setObject (1, pPUSUARIO);
                 cStmt.setObject (2, pORIGEN);
                 cStmt.setObject (3, pEXPEDIENTE);
                 cStmt.setObject (4, pIDPRESUP);
                 cStmt.setObject (5, pIDGREMIO);
                 cStmt.setObject (6, pASEGPERJ);
                 cStmt.setObject (7, "S");
                 cStmt.setObject (8, pESTADO);
                 
                 cStmt.registerOutParameter (9, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
                 cStmt.registerOutParameter (10, java.sql.Types.VARCHAR); // Valor de "ERROR"
                 
                 cStmt.execute ();
                 //System.out.println("Despues del login:"+ cStmt.getObject (1));
                 HashMap retVal = new HashMap ();
                
                 try{
                 		
                         retVal.put ("CODIGOERROR", cStmt.getObject (9));
                 }catch (SQLException e){
                         retVal.put ("CODIGOERROR", null);
                 }
                 try{
                 		
                         retVal.put ("TEXTOERROR", cStmt.getObject (10));
                 }catch (SQLException e){
                         retVal.put ("TEXTOERROR", null);
                 }

                    
                 retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
                 cStmt.close();//AXIS-WLS1SERVER-Ready
                 cStmt = null;
                 conn.close();
                 conn = null;
                 //System.out.println("2..RETVAL eliminar MODIFICAR ITEM:"+retVal.toString());
                 return retVal;
         }
         
         /*
          *    PROCEDURE finalizar_gremio_presupuesto (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      pi_idpresup     IN       NUMBER,
      pi_idgremio     IN       NUMBER,
      pi_asegperj     IN       VARCHAR2,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )*/         
         
         
         public HashMap ejecutaWS_AMA__FINALIZAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
        		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
        		   , String pASEGPERJ) throws Exception {
               return this.callWS_AMA__FINALIZAR_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
            		   , pIDPRESUP, pIDGREMIO, pASEGPERJ ); 
           }
           
           private HashMap callWS_AMA__FINALIZAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
        		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
        		   , String pASEGPERJ  ) 
        		   throws Exception {

                   String callQuery = "{call WS_AMA.FINALIZAR_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?)}";
                   logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
                   CallableStatement cStmt = conn.prepareCall(callQuery);
                   ////String USERNAME = conn.getMetaData().getUserName().toUpperCase();
                   cStmt.setObject (1, pPUSUARIO);
                   cStmt.setObject (2, pORIGEN);
                   cStmt.setObject (3, pEXPEDIENTE);
                   cStmt.setObject (4, pIDPRESUP);
                   cStmt.setObject (5, pIDGREMIO);
                   cStmt.setObject (6, pASEGPERJ);
                   
                   cStmt.registerOutParameter (7, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
                   cStmt.registerOutParameter (8, java.sql.Types.VARCHAR); // Valor de "ERROR"
                   
                   cStmt.execute ();
                   //System.out.println("Despues del login:"+ cStmt.getObject (1));
                   HashMap retVal = new HashMap ();
                  
                   try{
                   		
                           retVal.put ("CODIGOERROR", cStmt.getObject (7));
                   }catch (SQLException e){
                           retVal.put ("CODIGOERROR", null);
                   }
                   try{
                   		
                           retVal.put ("TEXTOERROR", cStmt.getObject (8));
                   }catch (SQLException e){
                           retVal.put ("TEXTOERROR", null);
                   }

                      
                   retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
                   cStmt.close();//AXIS-WLS1SERVER-Ready
                   cStmt = null;
                   conn.close();
                   conn = null;
                   //System.out.println("2..RETVAL FINALIZAR MODIFICAR ITEM:"+retVal.toString());
                   return retVal;
           }
        
}
