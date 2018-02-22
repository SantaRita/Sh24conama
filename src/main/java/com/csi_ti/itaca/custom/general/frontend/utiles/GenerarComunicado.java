package com.csi_ti.itaca.custom.general.frontend.utiles;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConexionFactoria;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;


public class GenerarComunicado {

	private static GeneralBusinessServiceImpl service;
	
	
	public static String NuevoComunicado ( String pTPCOMUNI, String pTXCOMUNI, String pESTADO, Object[] pATRIBUTOS, String Usuario) {
	//
		
				service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
				WS_AMA llamada = null;
				try {
					llamada = new WS_AMA(service.plsqlDataSource.getConnection());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HashMap respuesta;
			
				try {
					respuesta = llamada.ejecutaWS_AMA__CREAR_COMUNICADO(
							//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
							Usuario,
							UI.getCurrent().getSession().getAttribute("origen").toString(),
							new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
							pTPCOMUNI,
							pTXCOMUNI,
							pESTADO,
							pATRIBUTOS
							);
					
					Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
					if ( retorno.get("CODIGOERROR").toString().equals("0")) {
						
						
						// En el caso del FT tenemos que actualizar la fecha tambien 
						// llamando a otra función
		                if ( pTPCOMUNI.equals("FT")) {
		               
		    				PAC_SHWEB_PROVEEDORES llamadaFt = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		    				HashMap respuestaFt;
		    				
		    				respuestaFt = llamadaFt.ejecutaPAC_SHWEB_PROVEEDORES__ACTUALIZAR_COMUNICADO_FT(
		    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
		    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
		    						Usuario,
		    						UI.getCurrent().getSession().getAttribute("fhfintrabajos").toString());
		    				Map<String, Object> retornoFt = new HashMap<String, Object>(respuestaFt);
		    				
		                }
					
		                if ( retorno.get("CODIGOERROR").toString().equals("0")) {
							new Notification("Proceso realizado",
									"Comunicado finalizado correctamente",
									Notification.Type.TRAY_NOTIFICATION, true)
									.show(Page.getCurrent());
						    return "0";
		                } else {
							new Notification("Se han producido un error",
									"Error al actualizar fecha comunicado",
									Notification.Type.ERROR_MESSAGE, true)
									.show(Page.getCurrent());
						    return "1";
		                }
					}
					else {
						
						String mensajeError;
						if ( retorno.get("CODIGOERROR").toString().equals("34") ) {
							mensajeError = "Error, no puede hacer este tipo de comunicado";
						}
						else {
							mensajeError = "Error al generar el comunicado. \n" + retorno.get("TEXTOERROR");
						}
						new Notification("Se han producido un error",
								mensajeError,
								Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					    return "1";
					}
					
				} catch (Exception e) {
					new Notification("Se han producido un error",
							"Error no controlado" + e.getMessage().toString(),
							Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
					e.printStackTrace();
					return "1";
				}
				
	}
	
	

}
