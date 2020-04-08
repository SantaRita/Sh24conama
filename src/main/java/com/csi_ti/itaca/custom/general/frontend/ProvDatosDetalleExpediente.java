package com.csi_ti.itaca.custom.general.frontend;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.csi_ti.itaca.custom.general.frontend.utiles.ValidarComunicado;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenCerrarExpediente;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenRechazoExpediente;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import oracle.jdbc.driver.OracleConnection;

@Theme("tests-valo-reindeer")
public class ProvDatosDetalleExpediente extends Panel implements ItacaView {

	public String UsuarioSave;

	JasperReport jasperReport = null;
	Connection conn = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	BrowserWindowOpener opener;
	
	private String usuAdmin = "AMA_ADMON";
	private String pwdAdmin = "inicio";
	private String url = UI.getCurrent().getSession().getAttribute("url").toString();	

	public ArrayList lsCon = new ArrayList();
	public Button btCerrarExpediente = new Button("Cerrar Expediente");
	public Button btImprimirExpediente = new Button("Imprimir");
	public Button btGED = new Button("Documentación(GED)");
	//public Button btColRechazar = new Button("Rechazar");
	public CheckBox ckRevisar = new CheckBox("Revisar");
	public ProvVenCerrarExpediente provVenCerrarExpediente = new ProvVenCerrarExpediente(this);
	public ProvVenRechazoExpediente provVenRechazoExpediente; 
	private ProvPantallaConsultaExpediente provPantallaConsultaExpedienteInicial;	
	private static GeneralBusinessServiceImpl service;
	    
    Property.ValueChangeListener listenerChgVip;
    
	Table tableTel = new Table();
	Label lCaption = new Label();
	Label dgenCausa =new Label("Causa:");
	Label dgenObservaciones =new Label("Observaciones:");
	
	

	private static final long serialVersionUID = -304344441663815443L;

	// constructor inicial
	public ProvDatosDetalleExpediente( Map<String, Object> retorno, ProvPantallaConsultaExpediente provPantallaConsultaExpediente ) {

		//provVenRechazoExpediente = new ProvVenRechazoExpediente( provPantallaConsultaExpediente );
		UsuarioSave = provPantallaConsultaExpediente.UsuarioSave;
		provPantallaConsultaExpedienteInicial = provPantallaConsultaExpediente;
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		
		// TAB TITULAR
		GridLayout dtitLayout = new GridLayout(6,2);
		dtitLayout.setStyleName("layout-lineas-juntas");
		dtitLayout.addComponent(new Label("Causa:"),0,0);
		dgenCausa.setCaptionAsHtml(true);
		dgenCausa.setContentMode(ContentMode.HTML);
		if ( retorno.get("CAUSA")!=null ) {
			dgenCausa.setValue("<h style='font-weight: normal;'>"+ retorno.get("CAUSA").toString()+" </h>");
		}
		dtitLayout.addComponent(dgenCausa,1,0);
		

		dtitLayout.addComponent(new Label("Observaciones:"),0,1);
		dgenObservaciones.setCaptionAsHtml(true);
		dgenObservaciones.setContentMode(ContentMode.HTML);
		if ( retorno.get("OBSERVACIONES")==null )
			dgenObservaciones.setValue("<h style='font-weight: normal;'></h>");
		else
			dgenObservaciones.setValue("<h style='font-weight: normal;'>"+ retorno.get("OBSERVACIONES").toString()+" </h>");
		dtitLayout.addComponent(dgenObservaciones,1,1,4,1);
		
		
		dtitLayout.addComponent(btCerrarExpediente,3,0);
		btCerrarExpediente.setStyleName(ValoTheme.BUTTON_DANGER);
		dtitLayout.setComponentAlignment(btCerrarExpediente, Alignment.BOTTOM_CENTER);
		dtitLayout.addComponent(btImprimirExpediente,4,0);
		btImprimirExpediente.setStyleName(ValoTheme.BUTTON_PRIMARY);
		dtitLayout.setComponentAlignment(btImprimirExpediente, Alignment.BOTTOM_CENTER);		
		dtitLayout.addComponent(ckRevisar,5,0);
		dtitLayout.setComponentAlignment(ckRevisar, Alignment.MIDDLE_CENTER);
		//dtitLayout.addComponent(btColRechazar,5,1);
		//dtitLayout.setComponentAlignment(btColRechazar, Alignment.MIDDLE_CENTER);
		//btColRechazar.setStyleName(ValoTheme.BUTTON_DANGER);
		//System.out.println("Estado exp: " + UI.getCurrent().getSession().getAttribute("tit.estadoexp"));
		/*if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("CON")) {
			btColRechazar.setVisible(true);
		} else {
			btColRechazar.setVisible(false);
		}
		btColRechazar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				//recargarComunicados();
				Object data =  event.getButton().getData();
				//tableexp.select(data);
				//Item itemClickEvent = tableexp.getItem(data);
				provVenRechazoExpediente.init();
				UI.getCurrent().removeWindow(provVenRechazoExpediente);
				//UI.getCurrent().getSession().setAttribute("exprechazar",(String) itemClickEvent.getItemProperty("Expediente").getValue());
				UI.getCurrent().getSession().setAttribute("exprechazar",(String) UI.getCurrent().getSession().getAttribute("expediente"));
				UI.getCurrent().addWindow(provVenRechazoExpediente);
					
		}
				
		});	*/
		
		// Cuando cerramos la ventana de rechazo del expediente
		
		/*provVenRechazoExpediente.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				
				System.out.println("Cerramos la vetnana");
				// TODO Auto-generated method stub
				
				if ( UI.getCurrent().getSession().getAttribute("botonpulsadorechazo").equals("ACEPTAR")) {
					
					//Object rowId = tableexp.getValue(); // get the selected rows id
					String expRechazar = UI.getCurrent().getSession().getAttribute("exprechazar").toString();
					
					
					PAC_SHWEB_PROVEEDORES llamada = null;
					try {
						llamada = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//System.out.println("El motivo del rechazo es: " + provVenRechazoExpediente.cbMotivoRechazo.getValue().toString());
					HashMap respuesta = null;
					try {
						respuesta = llamada.ejecutaPAC_SHWEB_PROVEEDORES__ACCION_MENSAJE_SMS(
								new BigDecimal("1"),
								"N",
								new BigDecimal(expRechazar),
								new BigDecimal(UsuarioSave.toUpperCase().replace("PROV_", "")),
								new BigDecimal(provVenRechazoExpediente.cbMotivoRechazo.getValue().toString()),
								provVenRechazoExpediente.taObservaciones.getValue().toString()
								);
						
						Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
						
						//System.out.println("Código error rechazar " + retorno.get("CODIGOERROR").toString());
						if (retorno.get("CODIGOERROR").toString().equals("0"))  {
							
							btColRechazar.setVisible(false);
							
							new Notification("Proceso finalizado",
									"Se ha rechazado el expediente correctamente",
									Notification.Type.TRAY_NOTIFICATION, true)
							.show(Page.getCurrent());
							
							
							
						} else {
							
							new Notification("Error al rechazar el expediente",
									retorno.get("TEXTOERROR").toString(),
									Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
							
						}
						
						
						
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						new Notification("Error",
								"Error no contralado al rechazar el expediente",
								Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());											
					}
					
				}
				
				
				
			}


		});*/		
		

		
		if ( UI.getCurrent().getSession().getAttribute("revisar").toString().trim().equals("N")) {
			ckRevisar.setValue(false);	
		}
		else {
			ckRevisar.setValue(true);
		}
		
		dtitLayout.setWidth("100%");
		
		dtitLayout.setColumnExpandRatio(0,12);
		dtitLayout.setColumnExpandRatio(1,58);
		dtitLayout.setColumnExpandRatio(2,0);
		dtitLayout.setColumnExpandRatio(3,15);
		dtitLayout.setColumnExpandRatio(4,0);
		dtitLayout.setColumnExpandRatio(5,15);

				
		setCaption("Datos del Expediente");
		setContent(dtitLayout);
		
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		
		if ( !ValidarComunicado.EsValido("CA") && !ValidarComunicado.EsValido("FT") ) {
			btCerrarExpediente.setVisible(false);
		}
		
		ckRevisar.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				//System.out.println("Hemos pulsado" + ckRevisar.getValue().toString());
				//ConexionFactoria.establecerConexion();
				WS_AMA llamada = null;
				try {
					llamada = new WS_AMA(service.plsqlDataSource.getConnection());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HashMap respuesta = null;
				BigDecimal expbd = null;

				String  valor = null;
				if ( ckRevisar.getValue())
					valor = "S";
				else
					valor = "N";
			 
				try {
					respuesta = llamada.ejecutaWS_AMA__ACTUALIZAR_FLAG_REVISADO(
							//UI.getCurrent().getSession().getAttribute("userxx").toString(),
							UsuarioSave,
							UI.getCurrent().getSession().getAttribute("origen").toString(),
							new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
							valor
							);
					
					Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
					if ( retorno.get("CODIGOERROR").toString().equals("0"))
					
						new Notification("Proceso realizado",
								"Cambios de revisión realizado correctamente",
								Notification.Type.TRAY_NOTIFICATION, true)
								.show(Page.getCurrent());
					else
						new Notification("Se han producido un error",
								"Error al modificar el estado de Revisar \n" + retorno.get("TEXTOERROR"),
								Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());
					
				} catch (Exception e) {
					new Notification("Se han producido un error",
							"Error al modificar el estado de Revisar",
							Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
					e.printStackTrace();
				}
			}
		});
		
		btCerrarExpediente.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
					provVenCerrarExpediente.UsuarioSave = UsuarioSave;
					provVenCerrarExpediente.init();
					UI.getCurrent().addWindow(provVenCerrarExpediente);
				
			}
		});	
		
		btImprimirExpediente.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				   // Compile jrxml file.
			
					opener.setResource( imprimirReportPantalla(1) );
					//directories=0,titlebar=0,toolbar=0,location=0,status=0,   
					opener.setFeatures("directories=0,titlebar=0,toolbar=0,location=0,status=0,height=600,width=600,resizable");
					opener.setWindowName("_blank");

			}
		});
		
		opener =new BrowserWindowOpener( imprimirReportPantalla(0) );
		opener.setFeatures("directories=0,titlebar=0,toolbar=0,location=0,status=0,height=600,width=600,resizable");
		opener.setWindowName("_blank");
		opener.extend(btImprimirExpediente);
		
		//btImprimirExpediente.setVisible(false);
		
		


	}
	
	private class HorizontalRule extends Label {
		  public HorizontalRule() {
		    super("<hr style='color:gray;height:1px' />", ContentMode.HTML);
		  }
		}
	
	


	
	

	
	@Override
	public void enter(ViewChangeEvent event) {

		UsuarioSave = provPantallaConsultaExpedienteInicial.UsuarioSave;
	}
	
	public void recargarDatos( ) {

		// Recuperamos los comunicados
		
		//System.out.println(">>>>>>>>>> Recargamos comunicados con el expediente:" + new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()));
		PAC_SHWEB_PROVEEDORES llamadaProv = null;
		try {
			llamadaProv = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaCom = null;

		try {
			respuestaCom = llamadaProv.ejecutaPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE(
					//UI.getCurrent().getSession().getAttribute("userxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Mostramos el estado del expediente
		PAC_SHWEB_PROVEEDORES llamada = null;
		try {
			llamada = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuesta = null;
		try {
			respuesta = llamada.ejecutaPAC_SHWEB_PROVEEDORES__F_ESTADO_EXPEDIENTE(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
			Map<String, Object> retorno = new HashMap<String, Object>(respuesta);

			UI.getCurrent().getSession().setAttribute("estadoExpediente",retorno.get("ESTADO").toString());
			
			provPantallaConsultaExpedienteInicial.setCaption("GESTIÓN DEL EXPEDIENTE Nº " + UI.getCurrent().getSession().getAttribute("expediente")
					+ " ( " + 
					UI.getCurrent().getSession().getAttribute("estadoExpediente") + " ) ");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	
		
		// Maestro comunicados

		WS_AMA llamadaAMA = null;
		try {
			llamadaAMA = new WS_AMA(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaMaestro = null;
		
		//System.out.println("Llamammos maestro comunicados: " + UI.getCurrent().getSession().getAttribute("tipousuario").toString().substring(1,1));;
		try {
			// pPUSUARIO, pORIGEN, pTPCOMUNI, pTPUSUARIO, pESTADO)
			
			respuestaMaestro = llamadaAMA.ejecutaWS_AMA__MAESTRO_COMUNICADOS(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					null,
					UI.getCurrent().getSession().getAttribute("tipousuario").toString().substring(1,1),
					UI.getCurrent().getSession().getAttribute("estadoExpediente").toString()
					);			

			
			Map<String, Object> retornoMaestro = new HashMap<String, Object>(respuestaMaestro);
			List<Map> valorMaestro = (List<Map>) retornoMaestro.get("COMUNICADOS");
			UI.getCurrent().getSession().setAttribute("comunicadosExpediente",valorMaestro);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UI.getCurrent().getSession().setAttribute("comunicadosExpediente",null);
		}
		
		// 	
		
		
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		
		if ( !ValidarComunicado.EsValido("CA") && !ValidarComunicado.EsValido("FT") ) {
			provPantallaConsultaExpedienteInicial.provDatosDetalleExpediente.btCerrarExpediente.setVisible(false);
		}
		else {
			provPantallaConsultaExpedienteInicial.provDatosDetalleExpediente.btCerrarExpediente.setVisible(true);

			
			
		}
		// Mostramos botonera de cerrar expediente
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		

	}

    private final Resource imprimirReportPantalla(int valor){
    	
    	//System.out.println("[[[ IMPRIMIR REPORT PANTALLA ]]]" );
 
    	try {
			//System.out.println("Compilando..........");
			String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/reports/" + "expediente_webprov.jrxml";
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
    	   //parameters.put("CDPROVEE", UI.getCurrent().getSession().getAttribute("userxxx").toString());
    	   parameters.put("CDPROVEE", UsuarioSave);
    	   
    	   //System.out.println("Parametros: " + parameters);

	       try {
	    	   

		        String URL = url;
		        String USER = usuAdmin;
		        String PASS = pwdAdmin;
		        
		     
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
		        	       "**** PWP REPORTS 2 ****" );
		        try {
		        	if ( valor== 0 ) {
		        		//System.out.println("No hacemos la conexion");;
		        	}
		        	else {
						DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
						conn = DriverManager.getConnection(URL, props);
		        	}       	

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        	        
		     
               StreamResource.StreamSource source = new StreamResource.StreamSource() {
                       public InputStream getStream() {
                               byte[] b = null;
                               try{                                
                                       b = JasperRunManager.runReportToPdf(jasperReport,parameters, conn);
                                       conn.close();
                                       conn = null;
                                   
                               } catch (Exception ex) {
                                   ex.printStackTrace();

                               }
                               return new ByteArrayInputStream(b);    
                       }
               };				        

               StreamResource resourcesReport = new StreamResource(source, "informe_" + System.currentTimeMillis() + ".pdf");
               resourcesReport.setCacheTime(0);
               
               
               
        
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