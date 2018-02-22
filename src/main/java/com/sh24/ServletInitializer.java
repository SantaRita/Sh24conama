package com.sh24;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessService;
import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessServiceProxy;
import com.csi_ti.itaca.custom.general.api.service.GeneralBusinessServiceProxyRestClient;
import com.csi_ti.itaca.custom.general.server.jdbc.util.Constantes;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.PreserveOnRefresh;

import lombok.AccessLevel;
import lombok.Getter;

@PreserveOnRefresh
@SpringBootApplication
public class ServletInitializer extends org.springframework.boot.web.support.SpringBootServletInitializer {

	
	
	@Value("${services.external.general.url}")
	@Getter(AccessLevel.PROTECTED)
	protected String serverUrl;
	
	
	@Value("${plsql.poolowner}")
	private String poolOwner;

	
	
	@PostConstruct
	public void recuperarPoolOwner() {
		Constantes.DB01_POOLOWNER = poolOwner;
	}
	
	@Bean(name = "plsqlDataSource")
	@Primary
	@ConfigurationProperties(prefix = "plsql.datasource")
	public DataSource plsqlDataSource() {
		
		
	    System.out.println("*************** inicializamos el datasource ********************");	
		return DataSourceBuilder.create().build();
	}
	/*DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("AMA_ADMON");
        dataSource.setPassword("inicio");
        dataSource.setURL("jdbc:oracle:thin:@10.101.7.15:1521:SIAMA");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        
        return dataSource;
    }*/
	

	
	@Bean
	public GeneralBusinessService generalBusinessServiceEndpoint(){
		return new GeneralBusinessServiceImpl();
	}

	@Bean
	public GeneralBusinessServiceProxy generalBusinessServiceProxy(){
		return new GeneralBusinessServiceProxyRestClient(serverUrl);
	}	

	
	

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		System.out.println(">>> SpringApplicationBuilder");
		return application.sources(Sh24proveedoresApplication.class);
	}

}
