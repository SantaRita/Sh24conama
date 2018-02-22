package com.csi_ti.itaca.custom.general.api.service;

import com.csi_ti.itaca.custom.general.api.model.Generico0;

public interface GeneralBusinessService {
	
	Generico0 ejecutaPAC(String pac, String function, boolean tratarMensajes, Object... parameters);
}
