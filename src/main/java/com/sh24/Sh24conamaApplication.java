package com.sh24;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessService;
import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessServiceProxy;
import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessServiceProxyRestClient;
import com.csi_ti.itaca.custom.general.server.jdbc.util.Constantes;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;

import lombok.AccessLevel;
import lombok.Getter;

@SpringBootApplication
public class Sh24conamaApplication {


	
	public static void main(String[] args) {
		
		//System.out.println(">>> Sh24proveedoresApplication");
		SpringApplication.run(Sh24conamaApplication.class, args);
		
	}
}
