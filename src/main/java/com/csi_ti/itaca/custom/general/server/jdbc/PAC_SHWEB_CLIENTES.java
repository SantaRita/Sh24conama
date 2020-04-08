package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;

public class PAC_SHWEB_CLIENTES extends AccesoPL {
	static Log logger = LogFactory.getLog(PAC_SHWEB_CLIENTES.class);
	private Connection conn = null;

	public PAC_SHWEB_CLIENTES(Connection conn) {

		// System.out.println("Indicamos la conexion");
		this.conn = conn;
		// System.out.println("Despues Indicamos la conexion");
	}

	/*
	 * FUNCTION f_validar_token_sms (PCADENA IN VARCHAR2, PDNI IN VARCHAR2 )
	 * RETURN NUMBER IS
	 */
	public HashMap ejecutaPAC_SHWEB_CLIENTES__F_VALIDAR_TOKEN_SMS(String pCADENA, String pDNI) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__F_VALIDAR_TOKEN_SMS(pCADENA, pDNI);
	}

	private HashMap callPAC_SHWEB_CLIENTES__F_VALIDAR_TOKEN_SMS(String pCADENA, String pDNI) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{?=call PAC_SHWEB_CLIENTES.F_VALIDAR_TOKEN_SMS(?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setObject(2, pCADENA);
		cStmt.setObject(3, pDNI);
		cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "ERROR"
		cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "EXPEDIENTE"
		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(1));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		try {

			retVal.put("EXPEDIENTE", cStmt.getObject(4));
		} catch (SQLException e) {
			retVal.put("EXPEDIENTE", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}

	public HashMap ejecutaPAC_SHWEB_CLIENTES__DATOSFIJOEXPEDIENTE(java.math.BigDecimal pCDASISTE) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__DATOSFIJOEXPEDIENTE(pCDASISTE);
	}

	private HashMap callPAC_SHWEB_CLIENTES__DATOSFIJOEXPEDIENTE(java.math.BigDecimal pCDASISTE) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{call PAC_SHWEB_CLIENTES.DATOSFIJOEXPEDIENTE(?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setObject(1, pCDASISTE);
		cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor
																		// de
																		// "REFCURSOR"
		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor
																		// de
																		// "REFCURSOR"
		cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "ERROR"
		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
		try {
			retVal.put("EXPEDIENTE", cStmt.getObject(2));
		} catch (SQLException e) {
			retVal.put("EXPEDIENTE", null);
		}
		try {
			retVal.put("DETALLE", cStmt.getObject(3));
		} catch (SQLException e) {
			retVal.put("DETALLE", null);
		}

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(4));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}
	
	
	
	/*
	 * PROCEDURE  PROCEDURE relaciondocumentos (
      p_in_expediente   IN       NUMBER,
      p_out_docs        OUT      t_refcursor,
      p_out_error       OUT      NUMBER
   )
	
	 */
	public HashMap ejecutaPAC_SHWEB_CLIENTES__F_RELACIONDOCUMENTOS(java.math.BigDecimal pCDASISTE) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__F_RELACIONDOCUMENTOS(pCDASISTE);
	}

	private HashMap callPAC_SHWEB_CLIENTES__F_RELACIONDOCUMENTOS(java.math.BigDecimal pCDASISTE) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{call PAC_SHWEB_PROVEEDORES.RELACIONDOCUMENTOS(?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setObject(1, pCDASISTE);
		cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor
																		// de
																		// "REFCURSOR"
		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "ERROR"
		cStmt.execute();
		// System.out.println("Despues de RELACIONDOCUMENTOS:"+ pCDASISTE + " _____ "+ cStmt.getObject (2));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(3));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		try {

			retVal.put("DOCUMENTACION", cStmt.getObject(2));
		} catch (SQLException e) {
			retVal.put("DOCUMENTACION", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}	

	/*
	 * PROCEDURE obtenertelefonos ( p_in_expediente IN NUMBER, p_out_telefonos
	 * OUT t_refcursor, p_out_error OUT NUMBER )
	 */
	public HashMap ejecutaPAC_SHWEB_CLIENTES__F_OBTENER_TELEFONOS(java.math.BigDecimal pCDASISTE) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__F_OBTENER_TELEFONOS(pCDASISTE);
	}

	private HashMap callPAC_SHWEB_CLIENTES__F_OBTENER_TELEFONOS(java.math.BigDecimal pCDASISTE) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{call PAC_SHWEB_CLIENTES.OBTENERTELEFONOS(?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setObject(1, pCDASISTE);
		cStmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); // Valor
																		// de
																		// "REFCURSOR"
		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "ERROR"
		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(3));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		try {

			retVal.put("TELEFONOS", cStmt.getObject(2));
		} catch (SQLException e) {
			retVal.put("TELEFONOS", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}

	/*
	 * PROCEDURE relacionseguimientos ( p_in_expediente IN NUMBER,
	 * p_out_seguimientos OUT t_refcursor, p_out_error OUT NUMBER )
	 */
	public HashMap ejecutaPAC_SHWEB_CLIENTES__F_RELACION_SEGUIMIENTOS(java.math.BigDecimal pCDASISTE, String USUARIO ) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__F_RELACION_SEGUIMIENTOS(pCDASISTE, USUARIO);
	}

	private HashMap callPAC_SHWEB_CLIENTES__F_RELACION_SEGUIMIENTOS(java.math.BigDecimal pCDASISTE, String USUARIO) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{call PAC_SHWEB_CLIENTES.RELACIONSEGUIMIENTOS(?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setObject(1, pCDASISTE);
		cStmt.setObject(2, USUARIO);		
		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor
																		// de
																		// "REFCURSOR"
		cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER); // Valor
																		// de
																		// "ERROR"
		cStmt.execute();
		//System.out.println("Despues del comunicados:" + cStmt.getObject(3));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(4));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		try {

			retVal.put("COMUNICADOS", cStmt.getObject(3));
		} catch (SQLException e) {
			retVal.put("COMUNICADOS", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}

	/*
	 * FUNCTION obtener_query_expedientes ( p_in_usuario IN VARCHAR2,
	 * p_in_exp_uah IN NUMBER, p_in_poliza IN VARCHAR2, p_in_exp_cia IN
	 * VARCHAR2, 
	 * p_in_mov_eco IN NUMBER, p_in_nif IN VARCHAR2, p_in_nombre IN
	 * VARCHAR2, p_in_apellidos IN VARCHAR2, p_in_contrato IN VARCHAR2,
	 * p_in_causa IN NUMBER, p_in_garantia IN VARCHAR2, p_in_est_exp IN
	 * VARCHAR2, p_in_fhexp_ini IN VARCHAR2, p_in_fhexp_fin IN VARCHAR2,
	 * p_in_fhexpcierre_ini IN VARCHAR2, p_in_fhexpcierre_fin IN VARCHAR2,
	 * p_in_numexp_ini IN NUMBER, p_in_numexp_fin IN NUMBER, p_in_tipo_sin IN
	 * VARCHAR2 ) RETURN sys_refcursor
	 */

	public HashMap ejecutaPAC_SHWEB_CLIENTES__OBTENER_QUERY_EXPEDIENTES(
			String pUSUARIO,
			java.math.BigDecimal pEXPUAH,
			String pPOLIZA,
			java.math.BigDecimal pEXCIA,
			java.math.BigDecimal pMOVECTO,
			String pNIF,
			String pNOMBRE,
			String pAPE,
			String pCONTRATO,
			java.math.BigDecimal pCAUSA,
			String pGARANTIA,
			String pESTADO,
			String pFHEXPINI,
			String pFHEXPFIN,
			String pFHEXPCIERREINI,
			String pFHEXPCIERREFIN,
			java.math.BigDecimal pNUMEXPINI,
			java.math.BigDecimal pNUMEXPFIN,
			String pTIPOSIN
			
			
			
			) throws Exception {
		return this.callPAC_SHWEB_CLIENTES__OBTENER_QUERY_EXPEDIENTES(
				pUSUARIO, pEXPUAH, pPOLIZA, pEXCIA, pMOVECTO, pNIF, pNOMBRE, pAPE,pCONTRATO,
				pCAUSA, pGARANTIA, pESTADO, pFHEXPINI, pFHEXPFIN, pFHEXPCIERREINI, pFHEXPCIERREFIN,
				pNUMEXPINI, pNUMEXPFIN, pTIPOSIN);
	}

	private HashMap callPAC_SHWEB_CLIENTES__OBTENER_QUERY_EXPEDIENTES(String pUSUARIO,
			java.math.BigDecimal pEXPUAH,
			String pPOLIZA,
			java.math.BigDecimal pEXCIA,
			java.math.BigDecimal pMOVECTO,
			String pNIF,
			String pNOMBRE,
			String pAPE,
			String pCONTRATO,
			java.math.BigDecimal pCAUSA,
			String pGARANTIA,
			String pESTADO,
			String pFHEXPINI,
			String pFHEXPFIN,
			String pFHEXPCIERREINI,
			String pFHEXPCIERREFIN,
			java.math.BigDecimal pNUMEXPINI,
			java.math.BigDecimal pNUMEXPFIN,
			String pTIPOSIN) throws Exception {

		// System.out.println("Entramos a obtener query expedientes");

		String callQuery = "{?=call PAC_SHWEB_CLIENTES.OBTENER_QUERY_EXPEDIENTES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		logCall(callQuery, new String[] { "pESTADO" }, new Object[] { pESTADO });
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR); // Valor
		cStmt.setObject(2, pUSUARIO);
		cStmt.setObject(3, pEXPUAH);
		cStmt.setObject(4, pPOLIZA);
		cStmt.setObject(5, pEXCIA);
		cStmt.setObject(6, pMOVECTO);
		cStmt.setObject(7, pNIF);
		cStmt.setObject(8, pNOMBRE);
		cStmt.setObject(9, pAPE);
		cStmt.setObject(10, pCONTRATO);
		cStmt.setObject(11, pCAUSA);
		cStmt.setObject(12, pGARANTIA);
		cStmt.setObject(13, pESTADO);
		cStmt.setObject(14, pFHEXPINI);
		cStmt.setObject(15, pFHEXPFIN);
		cStmt.setObject(16, pFHEXPCIERREINI);
		cStmt.setObject(17, pFHEXPCIERREFIN);
		cStmt.setObject(18, pNUMEXPINI);
		cStmt.setObject(19, pNUMEXPFIN);
		cStmt.setObject(20, pTIPOSIN);
		
		cStmt.execute();
		
		//System.out.println("Salimos con" + cStmt.getObject(1)  );
		HashMap retVal = new HashMap();
		try {
			retVal.put("REGISTROS", cStmt.getObject(1));
		} catch (SQLException e) {
			retVal.put("REGISTROS", null);
		}
		
		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL antes de salir obtener query:" + retVal.toString());
		return retVal;
	}
	
    
    

}
