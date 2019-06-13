package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;

public class PAC_REPARTOS_PROVEEDOR extends AccesoPL {
	static Log logger = LogFactory.getLog(PAC_REPARTOS_PROVEEDOR.class);
	private Connection conn = null;

	public PAC_REPARTOS_PROVEEDOR(Connection conn) {

		// System.out.println("Indicamos la conexion");
		this.conn = conn;
		// System.out.println("Despues Indicamos la conexion");
	}

	/*
	 * FUNCTION ejecutaPAC_REPARTOS_PROVEEDOR__F_CARGAR_FICHERO
	 */
	public HashMap ejecutaPAC_REPARTOS_PROVEEDOR__F_CARGAR_FICHERO(FileReader pCADENA,
			String pSESION, String pUSUARIO, java.math.BigDecimal pCLIENTE) throws Exception {
		return this.callPAC_REPARTOS_PROVEEDOR__F_CARGAR_FICHERO(pCADENA, pSESION, pUSUARIO, pCLIENTE);
	}

	private HashMap callPAC_REPARTOS_PROVEEDOR__F_CARGAR_FICHERO(FileReader pCADENA,String pSESION, String pUSUARIO
			, java.math.BigDecimal pCLIENTE) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{?=call PAC_REPARTOS_PROVEEDOR.F_CARGAR_FICHERO(?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setClob(2, pCADENA);
		cStmt.setString(3, pSESION);
		cStmt.setString(4, pUSUARIO);
		cStmt.setObject(5, pCLIENTE);
		cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor de ERROR
		cStmt.registerOutParameter(6, oracle.jdbc.OracleTypes.VARCHAR); // Texto Error
																		// 

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(1));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}
		
		try {

			retVal.put("TEXTOERROR", cStmt.getObject(6));
		} catch (SQLException e) {
			retVal.put("TEXTOERROR", null);
		}		



		retVal = new ConversionUtil().convertOracleObjects(retVal);
		System.out.println("Nos vamos con retval: " + retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}
	
	
	/*
	 * FUNCTION fmain
	 * RETURN NUMBER IS
	 */
	public HashMap ejecutaPAC_REPARTOS_PROVEEDOR__F_PROCESAR_FMAIN(String pSESION) throws Exception {
		return this.callPAC_REPARTOS_PROVEEDOR__F_PROCESAR_FMAIN(pSESION);
	}

	private HashMap callPAC_REPARTOS_PROVEEDOR__F_PROCESAR_FMAIN(String pSESION) throws Exception {

		// System.out.println("Entramos a pac_shweblogin");
		String callQuery = "{?=call PAC_REPARTOS_PROVEEDOR.F_MAIN(?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		cStmt.setString(2, pSESION);
		cStmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER); // Valor de ERROR
		
																		// 

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(1));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}
		
		try {

			retVal.put("TEXTOERROR", cStmt.getObject(5));
		} catch (SQLException e) {
			retVal.put("TEXTOERROR", null);
		}		



		retVal = new ConversionUtil().convertOracleObjects(retVal);
		System.out.println("Nos vamos con retval: " + retVal);
		cStmt.close();
		cStmt = null;
		conn.close();
		conn = null;
		return retVal;
	}	


}
