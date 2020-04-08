package com.csi_ti.itaca.custom.general.frontend.utiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import oracle.jdbc.driver.OracleConnection;

public class ImprimirJasper {
	
	JasperReport jasperReport = null;
	Connection conn = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	BrowserWindowOpener opener;
	
	private String usuAdmin = "AMA_ADMON";
	private String pwdAdmin = "inicio";
	private String url = UI.getCurrent().getSession().getAttribute("url").toString();

	
	public final Resource imprimirReportPantalla(String fichero){
	    	
		   
	    	try {
				//System.out.println("Compilando..........");
				String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/reports/" + fichero;
				//System.out.println("Directorio:" + path );
				jasperReport = JasperCompileManager.compileReport(path);
				//System.out.println("Compilado");
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		       // Parameters for report
		       
		       parameters.clear();
		       parameters.put("DIRECTORIO", VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/reports/");
	    	   parameters.put("CDASISTE", new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()));

		       try {
		    	   

			        String URL = url;
			        String USER = usuAdmin;
			        String PASS = pwdAdmin;
			        
			        //System.out.println("Usuario:" + UI.getCurrent().getSession().getAttribute("userxxx").toString() );
			        //System.out.println("PWD:"+ UI.getCurrent().getSession().getAttribute("pwd").toString() );
			        
			        java.util.Properties props = new java.util.Properties();
			        
			        props.setProperty("password",PASS);
			        props.setProperty("user",USER);
			        props.setProperty("program","*** REPORT PWP ***");
			        try {
						props.setProperty("machine", InetAddress.getLocalHost().getCanonicalHostName());
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
			        props.setProperty(OracleConnection.CONNECTION_PROPERTY_INSTANCE_NAME, "instancia**");
			        props.setProperty(
			        	       OracleConnection.CONNECTION_PROPERTY_THIN_VSESSION_PROGRAM,
			        	       "**** PWP REPORTS 1 ****" );
			        try {
						DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
						conn = DriverManager.getConnection(URL, props);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			        	        
			        
					   //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
			       // Make sure the output directory exists.
			        
	               StreamResource.StreamSource source = new StreamResource.StreamSource() {
	                       public InputStream getStream() {
	                               byte[] b = null;
	                               try{                                
	                                       b = JasperRunManager.runReportToPdf(jasperReport,parameters, conn);
	                                       conn.close();
	                                       conn = null;
	                                   
	                               } catch (Exception ex) {
	                            	   //System.out.println("no cerramos la conexión report");
	                                   ex.printStackTrace();
                                

	                               }
	                               return new ByteArrayInputStream(b);    
	                       }
	               };				        

	               StreamResource resourcesReport = new StreamResource(source, fichero + System.currentTimeMillis() + ".pdf");
	               resourcesReport.setCacheTime(0);
	               
	               
	               
	               //opener.setResource(resourcesReport);
	               //
	               
			       //System.out.println("Done!");
			       
	               return resourcesReport;

	               
			       

		   	} catch (Exception e) {
				// TODO Auto-generated catch block
		   		e.printStackTrace();
		   		try {
					if ( !conn.isClosed() ) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
			}
			return null;    	
	}


	
}
