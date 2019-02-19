package com.csi_ti.itaca.custom.general.server.jdbc; //WLS-Ready

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;

public class WEB_PROV extends AccesoPL {
	static Log logger = LogFactory.getLog(WEB_PROV.class);
	private Connection conn = null;

	public WEB_PROV(Connection conn) {
		// System.out.println("Entramos por el login");
		this.conn = conn;
	}

	/*
	 * PROCEDURE anadir_item_presupuesto ( p_expediente IN NUMBER, p_cdprovee IN
	 * VARCHAR2, p_idpresup IN NUMBER, p_cdservic_a IN NUMBER, p_cdelem_a IN
	 * VARCHAR2, p_nivel_a IN NUMBER, p_cdservic_b IN NUMBER, p_cdelem_b IN
	 * VARCHAR2, p_nivel_b IN NUMBER, p_asegperj IN CHAR, p_finalizado IN CHAR,
	 * p_unidades IN NUMBER, p_importe IN NUMBER, p_otros_trabajos IN VARCHAR2,
	 * p_fhinicio IN DATE, p_cddanos IN VARCHAR2 DEFAULT NULL, --D917 - ARBOL
	 * LIB p_out_error IN OUT NUMBER) )
	 */

	public HashMap ejecutaWEB_PROV__ANADIR_ITEM_PRESUPUESTO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pCD_SERVICA, String pCDELEMA,
			java.math.BigDecimal pNIVELA, java.math.BigDecimal pCD_SERVICB, String pCDELEMB,
			java.math.BigDecimal pNIVELB, String pASEGPERJ, String pFINALIZADO, java.math.BigDecimal pUNIDADES,
			java.math.BigDecimal pIMPORTE, String pOTROSTRABAJOS, String pFHINICIO, String pDANOS) throws Exception {
		return this.callWEB_PROV__ANADIR_ITEM_PRESUPUESTO(pEXPEDIENTE, pCDPROVEE, pIDPRESUP, pCD_SERVICA, pCDELEMA,
				pNIVELA, pCD_SERVICB, pCDELEMB, pNIVELB, pASEGPERJ, pFINALIZADO, pUNIDADES, pIMPORTE, pOTROSTRABAJOS,
				pFHINICIO, pDANOS);
	}

	private HashMap callWEB_PROV__ANADIR_ITEM_PRESUPUESTO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pCD_SERVICA, String pCDELEMA,
			java.math.BigDecimal pNIVELA, java.math.BigDecimal pCD_SERVICB, String pCDELEMB,
			java.math.BigDecimal pNIVELB, String pASEGPERJ, String pFINALIZADO, java.math.BigDecimal pUNIDADES,
			java.math.BigDecimal pIMPORTE, String pOTROSTRABAJOS, String pFHINICIO, String pDANOS) throws Exception {

		String callQuery = "{call WEB_PROV.ANADIR_ITEM_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pEXPEDIENTE); // NUMBER
		cStmt.setObject(2, pCDPROVEE); // VARCHAR2
		cStmt.setObject(3, pIDPRESUP); // NUMBER 
		cStmt.setObject(4, pCD_SERVICA); // NUMBER
		cStmt.setObject(5, pCDELEMA); // VARCHAR2
		cStmt.setObject(6, pNIVELA); // NUMBER
		cStmt.setObject(7, pCD_SERVICB); // NUMBER
		cStmt.setObject(8, pCDELEMB); // VARCHAR2
		cStmt.setObject(9, pNIVELB); // NUMBER
		cStmt.setObject(10, pASEGPERJ); // CHAR
		cStmt.setObject(11, pFINALIZADO); // CHAR
		cStmt.setObject(12, pUNIDADES); // NUMBER
		cStmt.setObject(13, pIMPORTE); // NUMBER
		cStmt.setObject(14, pOTROSTRABAJOS); // VARCHAR2 
		cStmt.setObject(15, null); // ??????????????????? DATE
		cStmt.setObject(16, pDANOS); // VARCHAR2Xº
		
		System.out.println("La fecha eS: " + pFHINICIO );

		cStmt.registerOutParameter(17, java.sql.Types.NUMERIC); // Valor de
																// "CODIGOERRORA"

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(17));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		System.out.println("2..RETVAL WEB_PROV.ANADIR_ITEM_PRESUPUESTO:" + retVal.toString());
		return retVal;
	}

	/*
	 * PROCEDURE modificar_item_presupuesto ( p_expediente IN NUMBER, p_cdprovee
	 * IN VARCHAR2, p_idpresup IN NUMBER, p_cdservic_a IN NUMBER, p_cdelem_a IN
	 * VARCHAR2, p_nivel_a IN NUMBER, p_cdservic_b IN NUMBER, p_cdelem_b IN
	 * VARCHAR2, p_nivel_b IN NUMBER, p_cdservic_a_old IN NUMBER, p_cdelem_a_old
	 * IN VARCHAR2, p_nivel_a_old IN NUMBER, p_cdservic_b_old IN NUMBER,
	 * p_cdelem_b_old IN VARCHAR2, p_nivel_b_old IN NUMBER, p_asegperj IN CHAR,
	 * p_asegperj_old IN CHAR, p_finalizado IN CHAR, p_unidades IN NUMBER,
	 * p_importe IN NUMBER, p_otros_trabajos IN VARCHAR2, p_fhinicio IN DATE,
	 * p_cddanos IN VARCHAR2 DEFAULT NULL, --D917 - ARBOL LIB -- p_fhfin IN
	 * DATE, p_out_error OUT NUMBER)
	 */

	public HashMap ejecutaWEB_PROV__MODIFICAR_ITEM_PRESUPUESTO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pCD_SERVICA, String pCDELEMA,
			java.math.BigDecimal pNIVELA, java.math.BigDecimal pCD_SERVICB, String pCDELEMB,
			java.math.BigDecimal pNIVELB, String pASEGPERJ, String pFINALIZADO, java.math.BigDecimal pUNIDADES,
			java.math.BigDecimal pIMPORTE, String pOTROSTRABAJOS, String pFHINICIO, String pDANOS) throws Exception {
		return this.callWEB_PROV__MODIFICAR_ITEM_PRESUPUESTO(pEXPEDIENTE, pCDPROVEE, pIDPRESUP, pCD_SERVICA, pCDELEMA,
				pNIVELA, pCD_SERVICB, pCDELEMB, pNIVELB, pASEGPERJ, pFINALIZADO, pUNIDADES, pIMPORTE, pOTROSTRABAJOS,
				pFHINICIO, pDANOS);
	}

	private HashMap callWEB_PROV__MODIFICAR_ITEM_PRESUPUESTO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pCD_SERVICA, String pCDELEMA,
			java.math.BigDecimal pNIVELA, java.math.BigDecimal pCD_SERVICB, String pCDELEMB,
			java.math.BigDecimal pNIVELB, String pASEGPERJ, String pFINALIZADO, java.math.BigDecimal pUNIDADES,
			java.math.BigDecimal pIMPORTE, String pOTROSTRABAJOS, String pFHINICIO, String pDANOS) throws Exception {

		String callQuery = "{call WEB_PROV.MODIFICAR_ITEM_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pEXPEDIENTE);
		cStmt.setObject(2, pCDPROVEE);
		cStmt.setObject(3, pIDPRESUP);
		cStmt.setObject(4, pCD_SERVICA);
		cStmt.setObject(5, pCDELEMA);
		cStmt.setObject(6, pNIVELA);
		cStmt.setObject(7, pCD_SERVICB);
		cStmt.setObject(8, pCDELEMB);
		cStmt.setObject(9, pNIVELB);
		cStmt.setObject(10, pASEGPERJ);
		cStmt.setObject(11, pFINALIZADO);
		cStmt.setObject(12, pUNIDADES);
		cStmt.setObject(13, pIMPORTE);
		cStmt.setObject(14, pOTROSTRABAJOS);
		cStmt.setObject(15, pFHINICIO);
		cStmt.setObject(16, pDANOS);

		cStmt.registerOutParameter(17, java.sql.Types.NUMERIC); // Valor de
																// "CODIGOERRORA"

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(17));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.MODIFICAR_ITEM_PRESUPUESTO:" + retVal.toString());
		return retVal;
	}
	
	/*
	PROCEDURE existe_iva_reducido (
      p_expediente   IN       NUMBER,
      p_cdprovee     IN       VARCHAR2,
      p_idpresup     IN       NUMBER,
      p_iddetalle    IN       NUMBER,
      p_resultado    OUT      t_refcursor,
      p_out_error    OUT      NUMBER
   )
	 */

	public HashMap ejecutaWEB_PROV__EXISTE_IVA_REDUCIDO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pDETALLE) throws Exception {
		return this.callWEB_PROV__EXISTE_IVA_REDUCIDO(pEXPEDIENTE, pCDPROVEE, pIDPRESUP, pDETALLE );
	}

	private HashMap callWEB_PROV__EXISTE_IVA_REDUCIDO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pDETALLE ) throws Exception {

		String callQuery = "{call WEB_PROV.EXISTE_IVA_REDUCIDO(?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pEXPEDIENTE);
		cStmt.setObject(2, pCDPROVEE);
		cStmt.setObject(3, pIDPRESUP);
		cStmt.setObject(4, pDETALLE);

		cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
		cStmt.registerOutParameter(6, java.sql.Types.NUMERIC); // Valor de// "CODIGOERRORA"

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
        try{
            retVal.put ("REGISTROS", cStmt.getObject (5));
        }catch (SQLException e){
                retVal.put ("REGISTROS", null);
        }		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(6));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.EXISTE_IVA_REDUCIDO:" + retVal.toString());
		return retVal;
	}
	
	/*
  PROCEDURE obtener_num_facturas (
      p_expediente   IN       NUMBER,
      p_cdprovee     IN       VARCHAR2,
      p_idpresup     IN       NUMBER,
      p_respuestas   IN       VARCHAR2,
      p_resultado    OUT      t_refcursor,
      p_out_error    OUT      NUMBER
   )
	 */

	public HashMap ejecutaWEB_PROV__OBTENER_NUM_FACTURAS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, String pRESPUESTAS, String pFECHA) throws Exception {
		return this.callWEB_PROV__OBTENER_NUM_FACTURAS(pEXPEDIENTE, pCDPROVEE, pIDPRESUP, pRESPUESTAS, pFECHA );
	}

	private HashMap callWEB_PROV__OBTENER_NUM_FACTURAS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pIDPRESUP, String pRESPUESTAS, String pFECHA ) throws Exception {

		String callQuery = "{call WEB_PROV.OBTENER_NUM_FACTURAS(?,?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pEXPEDIENTE);
		cStmt.setObject(2, pCDPROVEE);
		cStmt.setObject(3, pIDPRESUP);
		cStmt.setObject(4, pRESPUESTAS);
		cStmt.setObject(7, pFECHA);

		cStmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
		cStmt.registerOutParameter(6, java.sql.Types.NUMERIC); // Valor de// "CODIGOERRORA"

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
        try{
            retVal.put ("REGISTROS", cStmt.getObject (5));
        }catch (SQLException e){
                retVal.put ("REGISTROS", null);
        }		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(6));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.OBTENER_NUM_FACTURAS:" + retVal.toString());
		return retVal;
	}
	
/*
 *    --D738
   PROCEDURE generar_factura_pago (p_cdprovee        IN     VARCHAR2,
                                   p_cdasiste        IN     NUMBER,
                                   p_nuorden         IN     NUMBER,
                                   p_idpresupuesto   IN     NUMBER,
                                   p_numfactura      IN     VARCHAR2,
                                   p_fhfactura       IN     DATE,
                                   p_destfactur      IN     NUMBER,
                                   p_out_error          OUT NUMBER)
   IS
 * 	
 */
	
	public HashMap ejecutaWEB_PROV__GENERAR_FACTURA_PAGO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pNUORDEN,
			java.math.BigDecimal pIDPRESUP, String pNUMFACTURA, Date pFHFACTURA,
			java.math.BigDecimal pDESTFACTUR) throws Exception {
		return this.callWEB_PROV__GENERAR_FACTURA_PAGO(pEXPEDIENTE, pCDPROVEE, pNUORDEN, pIDPRESUP, pNUMFACTURA, pFHFACTURA,
				pDESTFACTUR);
	}

	private HashMap callWEB_PROV__GENERAR_FACTURA_PAGO(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			java.math.BigDecimal pNUORDEN,
			java.math.BigDecimal pIDPRESUP, String pNUMFACTURA, Date pFHFACTURA,
			java.math.BigDecimal pDESTFACTUR) throws Exception {

		String callQuery = "{call WEB_PROV.GENERAR_FACTURA_PAGO(?,?,?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pCDPROVEE);		
		cStmt.setObject(2, pEXPEDIENTE);
		cStmt.setObject(3, pNUORDEN);
		cStmt.setObject(4, pIDPRESUP);
		cStmt.setObject(5, pNUMFACTURA);
		cStmt.setObject(6, pFHFACTURA);
		cStmt.setObject(7, pDESTFACTUR);

		cStmt.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMERIC); // Valor de "REFCURSOR"
		

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(8));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.GENERAR_FACTURA_PAGO:" + retVal.toString());
		return retVal;
	}
	
/*
 *    PROCEDURE existe_num_factura (
      p_cdprovee      IN       VARCHAR2,
      p_num_factura   IN       VARCHAR2,
      p_respuesta     OUT      t_refcursor,
      p_out_error     OUT      NUMBER
   )*/	
	public HashMap ejecutaWEB_PROV__EXISTE_NUM_FACTURA(String pCDPROVEE, String pFACTURA ) throws Exception {
		return this.callWEB_PROV__EXISTE_NUM_FACTURA(pCDPROVEE, pFACTURA);
	}

	private HashMap callWEB_PROV__EXISTE_NUM_FACTURA(String pCDPROVEE, String pFACTURA ) throws Exception {

		String callQuery = "{call WEB_PROV.EXISTE_NUM_FACTURA(?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pCDPROVEE);		
		cStmt.setObject(2, pFACTURA);


		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
		cStmt.registerOutParameter(4, java.sql.Types.NUMERIC); // Valor de// "CODIGOERRORA"

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
        try{
            retVal.put ("REGISTROS", cStmt.getObject (3));
        }catch (SQLException e){
                retVal.put ("REGISTROS", null);
        }
		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(4));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.EXISTE_NUM_FACTURA:" + retVal.toString());
		return retVal;
	}
	
	/*   PROCEDURE generar_facturas (
			      p_expediente   IN       NUMBER,
			      p_cdprovee     IN       VARCHAR2,
			      p_numfactur1   IN       VARCHAR2,
			      p_numfactur2   IN       VARCHAR2,
			      p_idpresup     IN       NUMBER,
			      p_fhfactura1   IN       VARCHAR2,
			      p_fhfactura2   IN       VARCHAR2,
			      p_destino1     IN       NUMBER,
			      p_destino2     IN       NUMBER,
			      p_respuestas   IN       VARCHAR2,
			      p_out_error    OUT      NUMBER
			   )	*/

	public HashMap ejecutaWEB_PROV__GENERAR_FACTURAS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			String pNUMFACTUR1, String pNUMFACTUR2,
			java.math.BigDecimal pIDPRESUP, String pFHFACTURA1, String pFHFACTURA2,
			java.math.BigDecimal pDESTFACTUR1, java.math.BigDecimal pDESTFACTUR2,
			String pRESPUESTAS ) throws Exception {
		return this.callWEB_PROV__GENERAR_FACTURAS(pEXPEDIENTE, pCDPROVEE, pNUMFACTUR1, pNUMFACTUR2,
				pIDPRESUP, pFHFACTURA1, pFHFACTURA2,pDESTFACTUR1,pDESTFACTUR2,
				pRESPUESTAS );
	}

	private HashMap callWEB_PROV__GENERAR_FACTURAS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE,
			String pNUMFACTUR1, String pNUMFACTUR2,
			java.math.BigDecimal pIDPRESUP, String pFHFACTURA1, String pFHFACTURA2,
			java.math.BigDecimal pDESTFACTUR1, java.math.BigDecimal pDESTFACTUR2,
			String pRESPUESTAS) throws Exception {

		String callQuery = "{call WEB_PROV.GENERAR_FACTURAS(?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		//String USERNAME = conn.getMetaData().getUserName().toUpperCase();
		cStmt.setObject(1, pEXPEDIENTE);
		cStmt.setObject(2, pCDPROVEE);		
		cStmt.setObject(3, pNUMFACTUR1);
		cStmt.setObject(4, pNUMFACTUR2);
		cStmt.setObject(5, pIDPRESUP);
		cStmt.setObject(6, pFHFACTURA1);
		cStmt.setObject(7, pFHFACTURA2);
		cStmt.setObject(8, pDESTFACTUR1);
		cStmt.setObject(9, pDESTFACTUR2);
		cStmt.setObject(10, pRESPUESTAS);


		cStmt.registerOutParameter(11, oracle.jdbc.OracleTypes.NUMERIC); // Valor de "REFCURSOR"
		

		cStmt.execute();
		// System.out.println("Despues del login:"+ cStmt.getObject (1));
		HashMap retVal = new HashMap();
		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(11));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.GENERAR_FACTURAS:" + retVal.toString());
		return retVal;
	}
	
	
	/*PROCEDURE obtener_gremios (
		      p_expediente   IN       NUMBER,
		      p_cdprovee     IN       VARCHAR2,
		      p_curgremios   OUT      t_refcursor,
		      p_out_error    OUT      NUMBER
		   )
		   IS*/
	
	public HashMap ejecutaWEB_PROV__OBTENER_GREMIOS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE
			) throws Exception {
		return this.callWEB_PROV__OBTENER_GREMIOS(pEXPEDIENTE, pCDPROVEE );
	}

	private HashMap callWEB_PROV__OBTENER_GREMIOS(java.math.BigDecimal pEXPEDIENTE, String pCDPROVEE) throws Exception {

		System.out.println("Entramos a obtener gremios con expediente:" + pEXPEDIENTE + " y prov: " + pCDPROVEE );
		String callQuery = "{call WEB_PROV.OBTENER_GREMIOS(?,?,?,?)}";
		CallableStatement cStmt = conn.prepareCall(callQuery);
		
		cStmt.setObject(1, pEXPEDIENTE);
		cStmt.setObject(2, pCDPROVEE);
		cStmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR); // Valor de "REFCURSOR"
		cStmt.registerOutParameter(4, oracle.jdbc.OracleTypes.INTEGER); // Valor de "ERROR"


		
		cStmt.execute();
		System.out.println("Despues del obtener_gremios:"+ cStmt.getObject (3));
		HashMap retVal = new HashMap();
		

		try {

			retVal.put("CODIGOERROR", cStmt.getObject(4));
		} catch (SQLException e) {
			retVal.put("CODIGOERROR", null);
		}
		
        try{
            retVal.put ("GREMIOS", cStmt.getObject (3));
        }catch (SQLException e){
                retVal.put ("GREMIOS", null);
        } 

		retVal = new ConversionUtil().convertOracleObjects(retVal);// AXIS-WLS1SERVER-Ready
		cStmt.close();// AXIS-WLS1SERVER-Ready
		cStmt = null;
		conn.close();
		conn = null;
		//System.out.println("2..RETVAL WEB_PROV.GENERAR_FACTURAS:" + retVal.toString());
		return retVal;
	}
	

}
