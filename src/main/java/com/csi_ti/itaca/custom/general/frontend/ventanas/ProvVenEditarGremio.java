package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.csi_ti.itaca.custom.general.frontend.ProvDatosPresupuestoExpediente;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConexionFactoria;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("tests-valo-reindeer")
public class ProvVenEditarGremio extends Window   {

	/**
	 * 
	 */
	public String UsuarioSave;

	
	String acceso;
	
	PropertysetItem item;
	
	BotoneraDoble botonera;
	
	
	private static final long serialVersionUID = 1L;

	private FieldGroup binder;

	// Ponemos los campos
	// Campos del titular para cartera o propuesta
	public ComboBox cbAseguradoPerjudicado = new ComboBox("Asegurado/Perjudicado");
	public ComboBox cbGremio = new ComboBox("Gremio");
	public DateField dtFechaIntervencion = new DateField("F. Intervención");
	public ComboBox cbFinalizado = new ComboBox("Finalizado");
	public TextArea taCierre = new TextArea("Observaciones");


	private static GeneralBusinessServiceImpl service;
	

	public ProvVenEditarGremio( ProvDatosPresupuestoExpediente provDatosPresupuestoExpediente) {

		UsuarioSave = provDatosPresupuestoExpediente.UsuarioSave;
		
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		// TODO Auto-generated constructor stub
		setCaption("Modificar Gremio");
		setModal(true);
		setWidth("90%");
		setClosable(true);
		setResizable(false);
		setStyleName("ventanamodal");
		
		VerticalLayout vl = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		vl.setMargin(true);
		vl.setWidth("100%");
		hl.setWidth("100%");


		item = new PropertysetItem();
		binder = new FieldGroup(item);
		binder.setBuffered(true);

		cbAseguradoPerjudicado.setRequired(true);
		cbAseguradoPerjudicado.setWidth("95%");
		cbAseguradoPerjudicado.setValidationVisible(true);
		cbAseguradoPerjudicado.setRequiredError("Campo obligatorio Asegurado/Perjudicado");
		cbAseguradoPerjudicado.addItem("A");
		cbAseguradoPerjudicado.addItem("P");
		cbAseguradoPerjudicado.setItemCaption("A","Asegurado");
		cbAseguradoPerjudicado.setItemCaption("P","Perjudicado");
		hl.addComponent(cbAseguradoPerjudicado);
		
		cbGremio.setRequired(true);
		cbGremio.setWidth("95%");
		cbGremio.setValidationVisible(true);
		cbGremio.setRequiredError("Campo obligatorio Gremio");
		/*cbGremio.addItem("A");
		cbGremio.addItem("P");
		cbGremio.setItemCaption("A","Asegurado");
		cbGremio.setItemCaption("P","Perjudicado");*/
		hl.addComponent(cbGremio);
		
		dtFechaIntervencion.setRequired(true);
		dtFechaIntervencion.setWidth("130px");
		dtFechaIntervencion.setValidationVisible(true);
		dtFechaIntervencion.setRequiredError("Campo obligatorio Fecha Intervenci�n");
		dtFechaIntervencion.setDateFormat("dd/MM/yyyy");
		hl.addComponent(dtFechaIntervencion);
	
		cbFinalizado.setRequired(true);
		cbFinalizado.setWidth("95%");
		cbFinalizado.setValidationVisible(true);
		cbFinalizado.setRequiredError("Campo obligatorio Finalizado");
		cbFinalizado.addItem("S");
		cbFinalizado.addItem("N");
		cbFinalizado.setItemCaption("S","Si");
		cbFinalizado.setItemCaption("N","No");
		hl.addComponent(cbFinalizado);		

		item.addItemProperty("cbAseguradoPerjudicado", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbGremio", new ObjectProperty<java.math.BigDecimal>(null, java.math.BigDecimal.class));
		item.addItemProperty("dtFechaIntervencion", new ObjectProperty<Date>(null, Date.class));
		item.addItemProperty("cbFinalizado", new ObjectProperty<String>(null, String.class));
		
		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);	
		binder.bindMemberFields(this);	
		
	    botonera = new BotoneraDoble();
	    vl.addComponent(hl);
	    vl.addComponent(botonera);		
		setContent(vl);
		
		botonera.btAceptar.setCaption("Confirmar Datos Gremio");
		
		
		botonera.btCancelar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				UI.getCurrent().getSession().setAttribute("botonpulsado","CANCELAR");
				UI.getCurrent().getSession().setAttribute("motivocierre",null);
				close();
				
			}
		});	
		
		botonera.btAceptar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				try {
				
					binder.commit();
					if ( acceso.equals("NUEVO" )) {
						
						PAC_SHWEB_PROVEEDORES llamada = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		    			HashMap respuestaGremios = null;
		    			try {

		    				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    		    	String fecha = formatter.format(dtFechaIntervencion.getValue());
		    		    	
		    		    	//System.out.println("El Usuariosave es " + UsuarioSave);
		    		    	
		    				respuestaGremios = llamada.ejecutaPAC_SHWEB_PROVEEDORES__CREAR_GREMIO_PRESUPUESTO(
		    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
		    						UsuarioSave,
		    						UI.getCurrent().getSession().getAttribute("origen").toString(),
		    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
		    						provDatosPresupuestoExpediente.presupuesto,
		    					    new BigDecimal(cbGremio.getValue().toString()),
		    					    cbAseguradoPerjudicado.getValue().toString(),
		    					    fecha,
		    					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString()
		    						);
		    				
		    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
		    				//System.out.println("Error insertar gremio: " + retornoGremios.get("CODIGOERROR") );
		    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
		    					
		    					new Notification("Error al insertar el gremio",
		    							retornoGremios.get("CODIGOERROR").toString() + " - " +
		    							retornoGremios.get("TEXTOERROR").toString(),
		    							Notification.Type.ERROR_MESSAGE, true)
		    							.show(Page.getCurrent());
		    				}
		    				else {
		
		    					// Borramos la fila
		    					//gremios.tbBaremos.removeItem(gremios.tbBaremos.getValue());
		    	            	
		    	            	//System.out.println("Detach");
		    	            	provDatosPresupuestoExpediente.removeFromParent(provDatosPresupuestoExpediente.dtitLayout);
		    	            	provDatosPresupuestoExpediente.Cargar_Datos();
		    	            	
		    					new Notification("Proceso finalizado correctamente",
		    							"Gremio creado",
		    							Notification.Type.TRAY_NOTIFICATION, true)
		    							.show(Page.getCurrent());
		    					
		
		    					
		    				}
		    				
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}	
		    			close();
						
					}
					else if ( acceso.equals("MODIFICAR") ) {
						WS_AMA llamada = new WS_AMA(service.plsqlDataSource.getConnection());
		    			HashMap respuestaGremios = null;
		    			try {

		    				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    		    	String fecha = formatter.format(dtFechaIntervencion.getValue());
		    		    	
		    				respuestaGremios = llamada.ejecutaWS_AMA__CREAR_GREMIO_PRESUPUESTO(
		    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
		    						UsuarioSave,
		    						UI.getCurrent().getSession().getAttribute("origen").toString(),
		    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
		    						provDatosPresupuestoExpediente.presupuesto,
		    					    new BigDecimal(cbGremio.getValue().toString()),
		    					    cbAseguradoPerjudicado.getValue().toString(),
		    					    fecha,
		    					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString()
		    						);
		    				
		    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
		    				//System.out.println("Error insertar gremio: " + retornoGremios.get("CODIGOERROR") );
		    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
		    					
		    					new Notification("Error al modificar el gremio",
		    							retornoGremios.get("CODIGOERROR").toString() + " - " +
		    							retornoGremios.get("TEXTOERROR").toString(),
		    							Notification.Type.ERROR_MESSAGE, true)
		    							.show(Page.getCurrent());
		    				}
		    				else {
		
		    					// Borramos la fila
		    					//gremios.tbBaremos.removeItem(gremios.tbBaremos.getValue());
		    	            	
		    	            	//System.out.println("Detach");
		    	            	provDatosPresupuestoExpediente.removeFromParent(provDatosPresupuestoExpediente.dtitLayout);
		    	            	provDatosPresupuestoExpediente.Cargar_Datos();
		    	            	
		    					new Notification("Proceso finalizado correctamente",
		    							"Gremio modificado",
		    							Notification.Type.TRAY_NOTIFICATION, true)
		    							.show(Page.getCurrent());
		    					
		
		    					
		    				}
		    				
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}	
		    			close();						
					}
					

				} catch (CommitException e) {

					
					String mensajes = "";
		            for (Field<?> field: binder.getFields()) {
		                ErrorMessage errMsg = ((AbstractField<?>)field).getErrorMessage();
		                
		                if (errMsg != null) {
		                	//System.out.println("Error:"+errMsg.getFormattedHtmlMessage());
		                	mensajes+=errMsg.getFormattedHtmlMessage();
		                }
		            }
		            
					new Notification("Se han detectado errores",
							mensajes,
							Notification.Type.TRAY_NOTIFICATION, true)
							.show(Page.getCurrent());
					
					

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});	
				
		
		
		
		
	
		
	}


	private class HorizontalRule extends Label {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public HorizontalRule() {
		    super("<hr style='color:blue' />", ContentMode.HTML);
		  }
		}

	//@PostConstruct
	public void init( String tipoAcceso) {

		cbAseguradoPerjudicado.setValue(null);
		acceso = tipoAcceso;
		cbGremio.setValue(null);
		cbFinalizado.setValue("N");
		cbFinalizado.setEnabled(false);
		dtFechaIntervencion.setValue(null);
		
		this.setCaption(tipoAcceso + " GREMIO");

	}
	

	
	/*@Override
	public void close() {
		// TODO Auto-generated method stub
		//super.close();
		//System.out.println("Cerramos la ventana de anular");
		UI.getCurrent().removeWindow(this);
		
	}*/

}