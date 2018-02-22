package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;


public class PAC_SHWEB_LISTAS extends AccesoPL {
    static Log logger = LogFactory.getLog(PAC_SHWEB_LISTAS.class);
    private Connection conn = null;

    public PAC_SHWEB_LISTAS(Connection conn) {
        this.conn = conn;
    }


    
    // COMPAÑIAS
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCOMPANIAS(String pCONTRATO) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTCOMPANIAS( pCONTRATO);
    }
    
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTCOMPANIAS( String pCONTRATO ) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTCOMPANIAS(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRATO);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    
    // POBLACIONES
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTPOBLACIONES(String pPROVINCIA) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTPOBLACIONES(pPROVINCIA);
    }
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTPOBLACIONES(String pPROVINCIA) throws Exception {
    	//System.out.println("f_get_lstpoblaciones cond codigo de provincia: " + pPROVINCIA);
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTPOBLACIONES(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pPROVINCIA);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }
        
        

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        
        //System.out.println("Nos vamos de get_lstpoblaciones con :" + retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }    
    
    // PROVINCIAS
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTPROVINCIAS(String pPAIS) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTPROVINCIAS(pPAIS);
    }
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTPROVINCIAS(String pPAIS) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTPROVINCIAS(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pPAIS);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    } 
    
    // CP - CODIGOS POSTALES
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCP(String pPOBLACION) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTCP(pPOBLACION);
    }
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTCP(String pPOBLACION) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTCP(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pPOBLACION);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }     
    
    // CONTRATOS
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCONTRATOS(String pCIA ) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTCONTRATOS(pCIA);
    }
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTCONTRATOS(String pCIA ) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTCONTRATOS(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCIA);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }     
    
    // PAISES
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTPAISES() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTPAISES();
    }
    
    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTPAISES() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTPAISES()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida paises:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    // PARAMCAMPOS - PARAMETRIZACION DE CAMPOS
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTPARAMCAMPOS(String pSECCION) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTPARAMCAMPOS( pSECCION );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTPARAMCAMPOS( String pSECCION) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTPARAMCAMPOS(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pSECCION);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida paises:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    // LSTCAUSAS - LISTA DE CAUSAS DEL EXPEDIENTE
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCAUSAS(String pCONTRA, java.math.BigDecimal pSUPLEM ) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTCAUSAS( pCONTRA, pSUPLEM  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTCAUSAS( String pCONTRA, java.math.BigDecimal pSUPLEM) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTCAUSAS(?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida CAUSAS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }     
    
    // LSTSUBCAUSAS - LISTA DE SUB CAUSAS DEL EXPEDIENTE EN FUNCIÓN DE UNA CAUSA
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTSUBCAUSAS(String pCONTRA, java.math.BigDecimal pSUPLEM,  java.math.BigDecimal pCAUSA) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTSUBCAUSAS( pCONTRA, pSUPLEM, pCAUSA  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTSUBCAUSAS(String pCONTRA, java.math.BigDecimal pSUPLEM,  java.math.BigDecimal pCAUSA) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTSUBCAUSAS(?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.setObject (4, pCAUSA);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida SUBCAUSAS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }      
    
    // GARANTIAS 
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTGARANTIAS(String pCONTRA, java.math.BigDecimal pSUPLEM,  java.math.BigDecimal pSUBCAUSA, String pNUMPOL) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTGARANTIAS( pCONTRA, pSUPLEM, pSUBCAUSA, pNUMPOL  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTGARANTIAS(String pCONTRA, java.math.BigDecimal pSUPLEM,  java.math.BigDecimal pSUBCAUSA, String pNUMPOL) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTGARANTIAS(?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.setObject (4, pSUBCAUSA);
        if (pNUMPOL.equals("-1")) {
        	cStmt.setObject (5, null);
        }
        else {
        	cStmt.setObject (5, pNUMPOL);
        }
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida SUBCAUSAS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    } 
    
    
    // LISTA SERVICIOS 
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS(String pCONTRA, java.math.BigDecimal pSUPLEM,  String pCOBERT, String pGARANT) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS( pCONTRA, pSUPLEM, pCOBERT, pGARANT  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS(String pCONTRA, java.math.BigDecimal pSUPLEM,  String pCOBERT, String pGARANT) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_SERVICIOS(?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.setObject (4, pCOBERT);
       	cStmt.setObject (5, pGARANT);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista servicios:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    // LISTA SERVICIOS NO GARANTIZADOS 
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_NO_GAR(String pCONTRA, java.math.BigDecimal pSUPLEM,  String pCOBERT, String pGARANT) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_NO_GAR( pCONTRA, pSUPLEM, pCOBERT, pGARANT  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_NO_GAR(String pCONTRA, java.math.BigDecimal pSUPLEM,  String pCOBERT, String pGARANT) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_SERVICIOS_NO_GAR(?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.setObject (4, pCOBERT);
       	cStmt.setObject (5, pGARANT);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista servicios no gar:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }     
    
    
    // LSTCAMPOSAGRUPACION - DEVUELVE LOS CAMPOS QUE TENEMOS QUE MOSTRAR EN CADA PANTALLA SEGÚN EL CLIENTE 
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCAMPOS_AGRUPACION(java.math.BigDecimal pCLIENTE) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTCAMPOS_AGRUPACION( pCLIENTE  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTCAMPOS_AGRUPACION(java.math.BigDecimal pCLIENTE) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTCAMPOS_AGRUPACION(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCLIENTE);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida Campos Agrupacion:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
     
    // f_get_lista_motivo_perito
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVO_PERITO(java.math.BigDecimal pCLIENTE) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVO_PERITO( pCLIENTE  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVO_PERITO(java.math.BigDecimal pCLIENTE) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_MOTIVO_PERITO(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCLIENTE);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida Campos Perito:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    
    // f_get_lista_tipo_fraude
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_FRAUDE(String pCONTRA, java.math.BigDecimal pSUPLEM ) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_FRAUDE( pCONTRA, pSUPLEM  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_FRAUDE( String pCONTRA, java.math.BigDecimal pSUPLEM) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_TIPO_FRAUDE(?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONTRA);
        cStmt.setObject (3, pSUPLEM);
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida tipos fraude:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }  
    
    // f_get_valoresfijos
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTVALORESFIJOS(String pTIPO, java.math.BigDecimal pVALORPADREN,
    		String pVALORPADREC) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTVALORESFIJOS( pTIPO, pVALORPADREN, pVALORPADREC  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTVALORESFIJOS( String pTIPO, java.math.BigDecimal pVALORPADREN,
    		String pVALORPADREC ) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTVALORESFIJOS(?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        
        cStmt.setObject (2, pTIPO);
        if ( (pVALORPADREN.intValue())==-1) {
        cStmt.setObject (3, null);
        }
        else {
            cStmt.setObject (3, pVALORPADREN);
        }
        if ( pVALORPADREC.equals("-1")) {
        cStmt.setObject (4, null);
        }
        else {
            cStmt.setObject (4, pVALORPADREC);
        }        

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida VALORES FIJOS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    // f_get_lstentidades
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LSTENTIDADES() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LSTENTIDADES(   );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LSTENTIDADES() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LSTENTIDADES()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida Campos Perito:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    } 
    
    // f_get_lista_ANULA_RECHAZO
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_ANULA_RECHAZO(java.math.BigDecimal pCLIENTE, String pTIPOLISTA) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_ANULA_RECHAZO( pCLIENTE, pTIPOLISTA  );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_ANULA_RECHAZO(java.math.BigDecimal pCLIENTE, String pTIPOLISTA) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_ANULA_RECHAZO(?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCLIENTE);
        cStmt.setObject (3, pTIPOLISTA);        
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida anula rechazo:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    } 
    
    // f_get_lista_RED
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_RED() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_RED();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_RED() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_RED()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista red:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }  
    
    // f_get_lista_proveedores
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_PROVEEDORES() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_PROVEEDORES();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_PROVEEDORES() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_PROVEEDORES()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista proveedores:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    // f_get_lista_servicios_gral
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_GRAL() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_GRAL();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_SERVICIOS_GRAL() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_SERVICIOS_GRAL()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista SERVICIOS GRAL:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }  
    
    // f_get_lista_tipo_proveedor
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_PROVEEDOR() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_PROVEEDOR();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_TIPO_PROVEEDOR() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_TIPO_PROVEEDOR()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista tipo_proveedor:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }  
    
    // f_get_lista_tipo_proveedor
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_PROVEEDORES(String pPLSTPARAMS) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_PROVEEDORES(pPLSTPARAMS);
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_PROVEEDORES(String pPLSTPARAMS) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_PROVEEDORES(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pPLSTPARAMS);

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida Campos Get Proveedores:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }       
    
    
    // f_get_lista_motivos_citanoconc
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_CITANOCONC() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_CITANOCONC();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_CITANOCONC() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_MOTIVOS_CITANOCONC()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista MOTIVOS_CITANOCONC:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    // f_get_lista_motivos_standby
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_STANDBY() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_STANDBY();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_MOTIVOS_STANDBY() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_MOTIVOS_STANDBY()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista MOTIVOS_STANDBY:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }    
    
    // F_GET_ESTIMACION_INICIAL
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_ESTIMACION_INICIAL(java.math.BigDecimal pCDASISTE,
    		java.math.BigDecimal pNUORDEN, String pCDCOBERT, String pCDGARANT, String pCDPAIS, 
    		java.math.BigDecimal pTPZONA, String pCDZONA, java.math.BigDecimal pCDSERVIC) throws Exception {
    	
        return this.callPAC_SHWEB_LISTAS__F_GET_ESTIMACION_INICIAL(pCDASISTE, pNUORDEN, pCDCOBERT, pCDGARANT,
        		pCDPAIS, pTPZONA, pCDZONA, pCDSERVIC);
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_ESTIMACION_INICIAL(java.math.BigDecimal pCDASISTE,
    		java.math.BigDecimal pNUORDEN, String pCDCOBERT, String pCDGARANT, String pCDPAIS, 
    		java.math.BigDecimal pTPZONA, String pCDZONA, java.math.BigDecimal pCDSERVIC) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_ESTIMACION_INICIAL(?,?,?,?,?,?,?,?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCDASISTE);
        cStmt.setObject (3, pNUORDEN);
        cStmt.setObject (4, pCDCOBERT);  
        cStmt.setObject (5, pCDGARANT); 
        cStmt.setObject (6, pCDPAIS); 
        cStmt.setObject (7, pTPZONA);  
        cStmt.setObject (8, pCDZONA);  
        cStmt.setObject (9, pCDSERVIC);  
        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista GET_ESTIMACION_INICIAL:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }   
    
    // F_GET_DIVISAS
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_DIVISAS() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_DIVISAS();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_DIVISAS() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_DIVISAS()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista DIVISAS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }  
    
    // F_GET_LISTA_RECHAZO_PROVEEDOR
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_RECHAZO_PROVEEDOR() throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_RECHAZO_PROVEEDOR();
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_RECHAZO_PROVEEDOR() throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_RECHAZO_PROVEEDOR()}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista GET_LISTA_RECHAZO_PROVEEDOR:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    // FQUERY - CONSULTA DINAMICA
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_QUERY(String pCONSULTA) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_QUERY( pCONSULTA);
    }
    
   
    private HashMap callPAC_SHWEB_LISTAS__F_QUERY( String pCONSULTA) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_QUERY(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"
        cStmt.setObject (2, pCONSULTA);
        cStmt.execute();
        HashMap retVal=new HashMap();
        //System.out.println("Salida lista GET_F_QUERY:"+retVal);
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    // F_SENTENCIA - EJECUTA UNA SENTENCIA DML/DLL/PLSQL
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_SENTENCIA(String pSENTENCIA) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_SENTENCIA( pSENTENCIA);
    }
    
   
    private HashMap callPAC_SHWEB_LISTAS__F_SENTENCIA( String pSENTENCIA) throws Exception {
    	//System.out.println("Entramos a ejecutar la sentencia");
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_SENTENCIA(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor de "RETURN"
        cStmt.setObject (2, pSENTENCIA);
        cStmt.execute();
        HashMap retVal=new HashMap();
        //System.out.println("Salida lista GET_F_SENTENCIA:"+retVal);
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Nos vamos de la funcion sentencia"+retVal );
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }
    
    // PROVEEDORES- *****************************
    // F_GET_LISTA_RECHAZO_PROVEEDOR
    public HashMap ejecutaPAC_SHWEB_LISTAS__F_GET_LISTA_PRESUPUESTOS( java.math.BigDecimal pCDASISTE) throws Exception {
        return this.callPAC_SHWEB_LISTAS__F_GET_LISTA_PRESUPUESTOS(pCDASISTE );
    }    

    private HashMap callPAC_SHWEB_LISTAS__F_GET_LISTA_PRESUPUESTOS( java.math.BigDecimal pCDASISTE) throws Exception {
        String callQuery="{?=call PAC_SHWEB_LISTAS.F_GET_LISTA_PRESUPUESTOS(?)}";
        CallableStatement cStmt=conn.prepareCall(callQuery);
        cStmt.setObject (2, pCDASISTE);
        cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor de "RETURN"

        cStmt.execute();
        HashMap retVal=new HashMap();
        try {
            retVal.put("RETURN", cStmt.getObject(1));
        }
        catch (SQLException e) {
            retVal.put("RETURN", null);
        }

        retVal=new ConversionUtil().convertOracleObjects(retVal); //AXIS-WLS1SERVER-Ready
        //System.out.println("Salida lista GET_LISTA_PRESUPUESTOS:"+retVal);
        cStmt.close(); //AXIS-WLS1SERVER-Ready
        cStmt = null;
        conn.close();
        conn = null;

        return retVal;
    }    
}
