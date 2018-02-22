package com.csi_ti.itaca.custom.general.frontend.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConexionFactoria;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class ValidarComunicado {
	
	public static boolean EsValido ( String pCOMUNICADOAVALIDAR ) {

		Boolean encontrado = false;
		List<Map> valor = (List<Map>) UI.getCurrent().getSession().getAttribute("comunicadosExpediente");
		for (Map map : valor) {
			if ( map.get("TPCOMUNICADO").toString().equals(pCOMUNICADOAVALIDAR) ) {
			  encontrado = true;
			  break;
			}
		}

		if ( encontrado ) return true; else return false;
		

	}
	
	

}
