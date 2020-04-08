package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWEB_PROVEEDORES extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_PROVEEDORES.class);
    private Connection conn = null;

    public PAC_SHWEB_PROVEEDORES(Connection conn) {
    	
    	//System.out.println("Indicamos la conexion");
        this.conn = conn;
        //System.out.println("Despues Indicamos la conexion");
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

    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_EXPEDIENTE (java.math.BigDecimal pCDASISTE, 
    		String pPUSUARIO, String pORIGEN, String pSEMANA, java.math.BigDecimal pNUMPAG, String pFECHA, 
    		String pESTADO, String pINC, String pREV,
    		String pFACTURA, String pFINI, String pFFIN) throws Exception {
        return this.callPAC_SHWEB_PROVEEDORES__F_LISTA_EXPEDIENTE(pCDASISTE, pPUSUARIO, pORIGEN, 
        		pSEMANA, pNUMPAG, pFECHA, pESTADO, pINC, pREV, pFACTURA, pFINI, pFFIN); 
    }
    
    private HashMap callPAC_SHWEB_PROVEEDORES__F_LISTA_EXPEDIENTE (java.math.BigDecimal pCDASISTE, String pPUSUARIO, String pORIGEN,
    		String pSEMANA, java.math.BigDecimal pNUMPAG, String pFECHA, String pESTADO, String pINC, String pREV
    		,String pFACTURA, String pFINI, String pFFIN) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call PAC_SHWEB_PROVEEDORES.LISTA_EXPEDIENTES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            
            //System.out.println(" usuario: " + pPUSUARIO);
            //System.out.println(" usuario: " + pCDASISTE);
            //System.out.println(" usuario: " + pORIGEN);
           // System.out.println(" estado: " + pESTADO);
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pCDASISTE);
            cStmt.setObject (3, pORIGEN);
            cStmt.setObject (4, pSEMANA);
            cStmt.setObject (5, pNUMPAG);
            cStmt.setObject (6, pFECHA);
            cStmt.setObject (7, pESTADO);
            cStmt.registerOutParameter(8, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (9, oracle.jdbc.OracleTypes.NUMBER); // Valor de "NUMTOTPAG"
            cStmt.registerOutParameter (10, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (11, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"
            cStmt.setObject (12, pINC);
            cStmt.setObject (13, pREV);
            cStmt.setObject (14, pFACTURA);
            cStmt.setObject (15, pFINI);
            cStmt.setObject (16, pFFIN);
            
            //System.out.println("Hacemos la llamada con pinc " + pINC + " rEV: "+ pREV);
            cStmt.execute ();
            //System.out.println("Despues del login:"+ cStmt.getObject (1));
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (8));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
            try{
                    retVal.put ("NUMTOTPAG", cStmt.getObject (9));
            }catch (SQLException e){
                    retVal.put ("NUMTOTPAG", null);
            }
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
            //System.out.println("2..RETVAL consulta antes de salir:"+retVal.toString());
            return retVal;
    }
    
    // COMUNICADOS EXPEDIENTE
    /*   PROCEDURE comunicados_expediente (
      pi_cdusuario    IN       VARCHAR2,
      pi_origen       IN       VARCHAR2,
      pi_expediente   IN       NUMBER,
      po_lista_com    OUT      t_refcursor,
      po_numtotpag    OUT      NUMBER,
      po_cderror      OUT      NUMBER,
      po_txerror      OUT      VARCHAR2
   )*/
    

    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE ) throws Exception {
        return this.callPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE(pPUSUARIO, pORIGEN, pEXPEDIENTE); 
    }
    
    private HashMap callPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
            String callQuery = "{call PAC_SHWEB_PROVEEDORES.COMUNICADOS_EXPEDIENTE(?,?,?,?,?,?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.setObject (1, pPUSUARIO);
            cStmt.setObject (2, pORIGEN);
            cStmt.setObject (3, pEXPEDIENTE);

            cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.registerOutParameter (5, oracle.jdbc.OracleTypes.NUMBER); // Valor de "NUMTOTPAG"
            cStmt.registerOutParameter (6, oracle.jdbc.OracleTypes.NUMBER); // Valor de "ERROR"
            cStmt.registerOutParameter (7, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "TXTERROR"
            //System.out.println("Hacemos la llamada");
            cStmt.execute ();
            //System.out.println("Despues del comunicados:"+ cStmt.getObject (4));
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (4));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
            try{
                    retVal.put ("NUMTOTPAG", cStmt.getObject (5));
            }catch (SQLException e){
                    retVal.put ("NUMTOTPAG", null);
            }
            try{
            		
                    retVal.put ("CODIGOERROR", cStmt.getObject (6));
            }catch (SQLException e){
                    retVal.put ("CODIGOERROR", null);
            }
            try{
            		
                    retVal.put ("TEXTOERROR", cStmt.getObject (7));
            }catch (SQLException e){
                    retVal.put ("TEXTOERROR", null);
            }

            retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }   
    
    // F_ESTADO_EXPEDIENTE
    /* FUNCTION f_estado_expediente  ( pi_cdusuario IN VARCHAR2, pi_expediente IN NUMBER )
     * 
     */

    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_ESTADO_EXPEDIENTE (String pPUSUARIO, java.math.BigDecimal pEXPEDIENTE ) throws Exception {
        return this.callPAC_SHWEB_PROVEEDORES__F_ESTADO_EXPEDIENTE(pPUSUARIO, pEXPEDIENTE); 
    }
    
    private HashMap callPAC_SHWEB_PROVEEDORES__F_ESTADO_EXPEDIENTE (String pPUSUARIO, java.math.BigDecimal pEXPEDIENTE) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
        
            String callQuery = "{?=call PAC_SHWEB_PROVEEDORES.F_ESTADO_EXPEDIENTE(?,?)}";
            logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.VARCHAR); // Valor de "ESTADO"
            cStmt.setObject (2, pPUSUARIO);
            cStmt.setObject (3, pEXPEDIENTE);
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("ESTADO", cStmt.getObject (1));
	        }catch (SQLException e){
	                retVal.put ("ESTADO", null);
	        }            
        retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }
    
    // F_lista_ESTADOe_EXPEDIENTE
    /* FUNCTION f_lista_estados_expediente( PTIPO IN VARCHAR2 ) RETURN SYS_REFCURSOR;
     * 
     */

    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_ESTADOS_EXPEDIENTE (String pESTADO) throws Exception {
        return this.callPAC_SHWEB_PROVEEDORES__F_LISTA_ESTADOS_EXPEDIENTE(pESTADO); 
    }
    
    private HashMap callPAC_SHWEB_PROVEEDORES__F_LISTA_ESTADOS_EXPEDIENTE (String pESTADO) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
        
            String callQuery = "{?=call PAC_SHWEB_PROVEEDORES.F_LISTA_ESTADOS_EXPEDIENTE(?)}";
            logCall (callQuery, new String[] {"pESTADO"}, new Object[] {pESTADO});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.setObject (2, pESTADO);
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (1));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
        retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
            return retVal;
    }    
        
    
    // F_lista_TELEFONOS_EXPEDIENTE
    /* FUNCTION f_lista_estados_expediente( PTIPO IN VARCHAR2 ) RETURN SYS_REFCURSOR;
     * 
     */

    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_TELEFONOS_EXPEDIENTE (java.math.BigDecimal pCDASISTE) throws Exception {
        return this.callPAC_SHWEB_PROVEEDORES__F_LISTA_TELEFONOS_EXPEDIENTE(pCDASISTE); 
    }
    
    private HashMap callPAC_SHWEB_PROVEEDORES__F_LISTA_TELEFONOS_EXPEDIENTE (java.math.BigDecimal pCDASISTE) throws Exception {
    	
    	    
    		//System.out.println("Entramos a pac_shweblogin");
        
            String callQuery = "{?=call PAC_SHWEB_PROVEEDORES.F_LISTA_TELEFONOS_EXPEDIENTE(?)}";
            logCall (callQuery, new String[] {"pESTADO"}, new Object[] {pCDASISTE});
            CallableStatement cStmt = conn.prepareCall(callQuery);
            cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
            cStmt.setObject (2, pCDASISTE);
            cStmt.execute ();
            HashMap retVal = new HashMap ();
            try{
                retVal.put ("REGISTROS", cStmt.getObject (1));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
        retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
            cStmt.close();//AXIS-WLS1SERVER-Ready
            cStmt = null;
            conn.close();
            conn = null;
            //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
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
    
    public HashMap ejecutaPAC_SHWEB_PROVEEDORES__CREAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
   		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
   		   , String pASEGPERJ, String pFHGREMIO, String pESTADO  ) throws Exception {
          return this.callPAC_SHWEB_PROVEEDORES__CREAR_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE
       		   , pIDPRESUP, pIDGREMIO, pASEGPERJ, pFHGREMIO, pESTADO ); 
      }
      
      private HashMap callPAC_SHWEB_PROVEEDORES__CREAR_GREMIO_PRESUPUESTO( String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
   		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
   		   , String pASEGPERJ, String pFHGREMIO, String pESTADO ) 
   		   throws Exception {

              String callQuery = "{call PAC_SHWEB_PROVEEDORES.CREAR_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?)}";
              logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
              CallableStatement cStmt = conn.prepareCall(callQuery);
              String USERNAME = conn.getMetaData().getUserName().toUpperCase();
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
              //System.out.println("2..RETVAL eliminar MODIFICAR ITEM:"+retVal.toString());
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
       public HashMap ejecutaPAC_SHWEB_PROVEEDORES__DETALLE_GREMIO_PRESUPUESTO(String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
       		,java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO, String pASEGPERJ) throws Exception {
           return this.callPAC_SHWEB_PROVEEDORES__DETALLE_GREMIO_PRESUPUESTO(pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ); 
       }
       
       private HashMap callPAC_SHWEB_PROVEEDORES__DETALLE_GREMIO_PRESUPUESTO (String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
      		 ,java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO, String pASEGPERJ) throws Exception {
       	
       	    
       		//System.out.println("Entramos a pac_shweblogin");
               String callQuery = "{call PAC_SHWEB_PROVEEDORES.DETALLE_GREMIO_PRESUPUESTO(?,?,?,?,?,?,?,?,?)}";
               logCall (callQuery, new String[] {"pPUSUARIO"}, new Object[] {pPUSUARIO});
               CallableStatement cStmt = conn.prepareCall(callQuery);
               String USERNAME = conn.getMetaData().getUserName().toUpperCase();
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
               //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
               return retVal;
       }
       
       /*
        *    PROCEDURE actualizar_comunicado_ft (
      pi_cdasiste   IN       NUMBER,
      pi_cdprovee   IN       VARCHAR2,
      pi_fhcomuni   IN       VARCHAR2,
      po_cderror    OUT      NUMBER,
      po_txerror    OUT      VARCHAR2
   )*/
      
        
        public HashMap ejecutaPAC_SHWEB_PROVEEDORES__ACTUALIZAR_COMUNICADO_FT(java.math.BigDecimal pEXPEDIENTE
           		,String pCDPROVEE, String pFHCOMUNI) throws Exception {
               return this.callPAC_SHWEB_PROVEEDORES__ACTUALIZAR_COMUNICADO_FT(pEXPEDIENTE, pCDPROVEE, pFHCOMUNI); 
           }
           
           private HashMap callPAC_SHWEB_PROVEEDORES__ACTUALIZAR_COMUNICADO_FT (java.math.BigDecimal pEXPEDIENTE
              		,String pCDPROVEE, String pFHCOMUNI) throws Exception {
           	
           	    
           		//System.out.println("Entramos a pac_shweblogin");
                   String callQuery = "{call PAC_SHWEB_PROVEEDORES.ACTUALIZAR_COMUNICADO_FT(?,?,?,?,?)}";
                   CallableStatement cStmt = conn.prepareCall(callQuery);
                   cStmt.setObject (1, pEXPEDIENTE);
                   cStmt.setObject (2, pCDPROVEE);
                   cStmt.setObject (3, pFHCOMUNI);

                   cStmt.registerOutParameter (4, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
                   cStmt.registerOutParameter (5, java.sql.Types.VARCHAR); // Valor de "ERROR"
                   
                   cStmt.execute ();
                   //System.out.println("Despues del login:"+ cStmt.getObject (1));
                   HashMap retVal = new HashMap ();
           
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
                   cStmt.close();//AXIS-WLS1SERVER-Ready
                   cStmt = null;
                   conn.close();
                   conn = null;
                   //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
                   return retVal;
           }
           
    /*
     *         
     *              -- ACCION SMS
   PROCEDURE accion_mensaje_sms ( pi_rgenvio IN NUMBER,
                                  pi_accion IN VARCHAR2,
                                  po_cderror OUT NUMBER,
                                  po_txerror OUT VARCHAR2,
                                  -- eSTOS DOS PARAMETROS SON POR SI NO VENIMOS POR EL SMS
                                  pi_cdasiste IN NUMBER DEFAULT NULL,
                                  pi_cdprovee   IN       VARCHAR2) IS
     */
           
   public HashMap ejecutaPAC_SHWEB_PROVEEDORES__ACCION_MENSAJE_SMS(java.math.BigDecimal pRGENVIO
      		,String pACCION, java.math.BigDecimal pCDASISTE, java.math.BigDecimal pCDPROVEE, java.math.BigDecimal pMOTIVO, String Observaciones) throws Exception {
          return this.callPAC_SHWEB_PROVEEDORES__ACCION_MENSAJE_SMS(pRGENVIO, pACCION, pCDASISTE, pCDPROVEE, pMOTIVO, Observaciones); 
      }
      
   private HashMap callPAC_SHWEB_PROVEEDORES__ACCION_MENSAJE_SMS (java.math.BigDecimal pRGENVIO
     		,String pACCION, java.math.BigDecimal pCDASISTE, java.math.BigDecimal pCDPROVEE, java.math.BigDecimal pMOTIVO, String Observaciones) throws Exception {
      	
      	    
              String callQuery = "{call PAC_SHWEB_PROVEEDORES.ACCION_MENSAJE_SMS(?,?,?,?,?,?,?,?)}";
              CallableStatement cStmt = conn.prepareCall(callQuery);
              cStmt.setObject (1, pRGENVIO);
              cStmt.setObject (2, pACCION);
              cStmt.setObject (5, pCDASISTE);
              cStmt.setObject (6, pCDPROVEE);
              cStmt.setObject (7, pMOTIVO);
              cStmt.setObject (8, Observaciones);

              cStmt.registerOutParameter (3, java.sql.Types.NUMERIC); // Valor de "CODIGOERRORA"
              cStmt.registerOutParameter (4, java.sql.Types.VARCHAR); // Valor de "TEXTOERROR"
              
              cStmt.execute ();
              //System.out.println("Despues del login:"+ cStmt.getObject (1));
              HashMap retVal = new HashMap ();
      
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
              //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
              return retVal;
   }           
       
   
   // F_lista_motivos_rechazo
   /* FUNCTION f_lista_movitos_rechazo RETURN SYS_REFCURSOR;
    * 
    */

   public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_MOTIVOS_RECHAZO () throws Exception {
       return this.callPAC_SHWEB_PROVEEDORES__F_LISTA_MOTIVOS_RECHAZO(); 
   }
   
   private HashMap callPAC_SHWEB_PROVEEDORES__F_LISTA_MOTIVOS_RECHAZO() throws Exception {
   	
   	    
   		//System.out.println("Entramos a pac_shweblogin");
       
           String callQuery = "{?=call PAC_SHWEB_PROVEEDORES.F_LISTA_MOTIVOS_RECHAZO()}";
           // logCall (callQuery, new String[] {"pESTADO"}, new Object[] {pCDASISTE});
           CallableStatement cStmt = conn.prepareCall(callQuery);
           cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
           cStmt.execute ();
           HashMap retVal = new HashMap ();
           try{
               retVal.put ("REGISTROS", cStmt.getObject (1));
	        }catch (SQLException e){
	                retVal.put ("REGISTROS", null);
	        }            
       retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
           cStmt.close();//AXIS-WLS1SERVER-Ready
           cStmt = null;
           conn.close();
           conn = null;
           //System.out.println("2..RETVAL antes de salir:"+retVal.toString());
           return retVal;
   }
   
   // 
   /* FUNCTION f_crear_hoja_encargo (      p_rgpresta   IN   NUMBER         )
    * 
    */

   public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_CREAR_HOJA_ENCARGO ( java.math.BigDecimal pRGPRESTA) throws Exception {
       return this.callPAC_SHWEB_PROVEEDORES__F_CREAR_HOJA_ENCARGO( pRGPRESTA); 
   }
   
   private HashMap callPAC_SHWEB_PROVEEDORES__F_CREAR_HOJA_ENCARGO( java.math.BigDecimal pRGPRESTA) throws Exception {
   	
   	    
   		//System.out.println("Entramos a hoJA: " + pRGPRESTA);
       
           String callQuery = "{?=call PAC_SHWEB_PROVEEDORES.F_CREAR_HOJA_ENCARGO(?)}";
           // logCall (callQuery, new String[] {"pESTADO"}, new Object[] {pCDASISTE});
           CallableStatement cStmt = conn.prepareCall(callQuery);
           cStmt.setObject (2, pRGPRESTA);
           cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMERIC); // Valor de "REFCURSOR"
           cStmt.execute ();
           HashMap retVal = new HashMap ();
           try{
               retVal.put ("ERROR", cStmt.getObject (1));
	        }catch (SQLException e){
	                retVal.put ("ERROR", null);
	        }            
       retVal=new ConversionUtil().convertOracleObjects(retVal);//AXIS-WLS1SERVER-Ready
           cStmt.close();//AXIS-WLS1SERVER-Ready
           cStmt = null;
           conn.close();
           conn = null;
           //System.out.println("2..RETVAL antes de salir hoja encargo:"+retVal.toString());
           return retVal;
   }   
      
             
	
   // GUARDAR DOCUMENTOS de pacshweb_expedientes
   public HashMap ejecutaPAC_SHWEB_PROVEEDORES__F_GUARDAR_DOC(java.math.BigDecimal pCDASISTE
   		,java.math.BigDecimal pNUORDEN, String pCDPROVE, 
   		String pTIPODOC, String pOBS, java.math.BigDecimal pGEDORIGEN,
   		java.math.BigDecimal pGEDTIPO, java.math.BigDecimal pGEDSUBTIPO, String pFICHERO,
   		String pESTANCIAS, String pANTESDES, String pASEGPERJ, String pFILE) throws Exception {
       return callPAC_SHWEB_PROVEEDORES__F_GUARDAR_DOC( pCDASISTE, pNUORDEN, pCDPROVE, pTIPODOC, 
       		pOBS, pGEDORIGEN, pGEDTIPO, pGEDSUBTIPO, pFICHERO, pESTANCIAS, pANTESDES, pASEGPERJ, pFILE);
   }
   
   @SuppressWarnings("deprecation")
	private HashMap callPAC_SHWEB_PROVEEDORES__F_GUARDAR_DOC( java.math.BigDecimal pCDASISTE
   		,java.math.BigDecimal pNUORDEN, String pCDPROVE, 
   		String pTIPODOC, String pOBS, java.math.BigDecimal pGEDORIGEN,
   		java.math.BigDecimal pGEDTIPO, java.math.BigDecimal pGEDSUBTIPO, String pFICHERO,
   		String pESTANCIAS, String pANTESDES, String pASEGPERJ, String pFILE) throws Exception  {
   	

       String callQuery="{?=call PAC_SHWEB_PROVEEDORES.F_GUARDAR_DOC(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
       CallableStatement cStmt;
       
       //System.out.println("El provee es " + pCDPROVE );
       
		try {
			cStmt = conn.prepareCall(callQuery);
	        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
	        cStmt.setObject (2, pCDASISTE);
	        cStmt.setObject (3, pNUORDEN);
	        cStmt.setObject (4, pCDPROVE);   // De momento siempre pasamos un null en PCDPROVEE
	        cStmt.setObject (5, pTIPODOC);   
	        cStmt.setObject (6, pOBS);  // longitud maxima 50
	        cStmt.setObject (7, pGEDORIGEN);  
	        cStmt.setObject (8, pGEDTIPO);  
	        if ( pGEDSUBTIPO==BigDecimal.valueOf(-1)) {
	        	 cStmt.setObject (9, null);  
	        }
	        else {
	        	 cStmt.setObject (9, pGEDSUBTIPO);  
	        }

	        cStmt.setObject (10, pFICHERO);
	        
	        cStmt.setObject (11, pESTANCIAS);
	        cStmt.setObject (12, pANTESDES);
	        cStmt.setObject (13, pASEGPERJ);
	        cStmt.setObject (14, pFILE);
	        
	        	        
	        
	        cStmt.execute();

	        HashMap retVal=new HashMap();
	        try {
	            retVal.put("RETURN", cStmt.getObject(1));
	        }
	        catch (SQLException e) {
	            retVal.put("RETURN", null);
	        }
	        
	        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
	        //System.out.println("Salida Guardar documentos:"+retVal);
			cStmt.close();
           cStmt = null;
           conn.close();
           conn = null;
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("ERROR:"+e);
			e.printStackTrace();
			return null;
		} 

   }
   
   // BORRAR_DOC
   public HashMap ejecutaPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC(java.math.BigDecimal pCDCARGA ) throws Exception {
       return callPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC( pCDCARGA );
   }
   
   @SuppressWarnings("deprecation")
	private HashMap callPAC_SHWEB_EXPEDIENTES__F_BORRAR_DOC( java.math.BigDecimal pCDCARGA ) throws Exception  {
   	

       String callQuery="{?=call PAC_SHWEB_EXPEDIENTES.F_BORRAR_DOC(?)}";
       CallableStatement cStmt;
       
		try {
			cStmt = conn.prepareCall(callQuery);
	        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
	        cStmt.setObject (2, pCDCARGA);
	        
	        cStmt.execute();

	        HashMap retVal=new HashMap();
	        try {
	            retVal.put("RETURN", cStmt.getObject(1));
	        }
	        catch (SQLException e) {
	            retVal.put("RETURN", null);
	        }
	        
	        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
	        //System.out.println("Salida Borrar documentos:"+retVal);
			cStmt.close();
           cStmt = null;
           conn.close();
           conn = null;
			return retVal;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("ERROR:"+e);
			e.printStackTrace();
			return null;
		} 

   }          
    
}
