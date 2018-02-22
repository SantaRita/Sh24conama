package com.csi_ti.itaca.custom.general.api.service;

import com.csi_ti.itaca.architecture.tools.api.ItacaAPIResponse;
import com.csi_ti.itaca.architecture.tools.api.ItacaBusinessServiceProxyRestClient;
import com.csi_ti.itaca.custom.general.api.model.Generico0DTO;

public class GeneralBusinessServiceProxyRestClient extends ItacaBusinessServiceProxyRestClient implements GeneralBusinessServiceProxy {

	public GeneralBusinessServiceProxyRestClient(String url) {
		super(url);
	}

	private static class ItacaAPIResponseGenerico0DTO extends ItacaAPIResponse<Generico0DTO>{}
		
	@Override
	public ItacaAPIResponse<Generico0DTO> ejecutaPAC(String pac, String function, boolean tratarMensajes, Object... parameters) {
		return connector.post("ejecutaPAC?pac={pac}&function={function}&tratarMensajes={tratarMensajes}", parameters, ItacaAPIResponseGenerico0DTO.class, pac, function, tratarMensajes);
	}
}
