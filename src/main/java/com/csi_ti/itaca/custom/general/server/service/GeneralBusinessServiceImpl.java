package com.csi_ti.itaca.custom.general.server.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.csi_ti.itaca.custom.general.api.model.Generico0;
import com.csi_ti.itaca.custom.general.api.model.Generico0DTO;
import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessService;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConversionUtil;
import com.csi_ti.itaca.custom.general.server.jdbc.util.Util;


public class GeneralBusinessServiceImpl implements GeneralBusinessService {

	public Connection conn;
	ConversionUtil convert = new ConversionUtil();
	
	@Autowired
	@Qualifier("plsqlDataSource")
	public DataSource plsqlDataSource;

	@Override
	public Generico0 ejecutaPAC(String pac, String function, boolean tratarMensajes, Object... parameters) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}
