package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.csi_ti.itaca.custom.general.frontend.ProvPantallaBusquedaExpedientes;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.frontend.utiles.GenerarComunicado;
import com.csi_ti.itaca.custom.general.frontend.utiles.ValidarComunicado;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("tests-valo-reindeer")
public class ProvVenRechazoExpediente extends Window   {

	/**
	 * 
	 */
	
	public String UsuarioSave;

	
	
	private static GeneralBusinessServiceImpl service;
	
	PropertysetItem item;
	
	BotoneraDoble botonera;

	private static final long serialVersionUID = 1L;

	private FieldGroup binder;
	String exp = "^[0-9]+(\\,([0-9]{1,2})?)?$";
	
	
	
	// Ponemos los campos
	// Campos del titular para cartera o propuesta
	public ComboBox cbMotivoRechazo = new ComboBox();
	public TextArea taObservaciones = new TextArea("Observaciones");

	


	@SuppressWarnings("deprecation")
	public ProvVenRechazoExpediente( ProvPantallaBusquedaExpedientes provPantallaBusquedaExpedientes) {

		UsuarioSave = provPantallaBusquedaExpedientes.UsuarioSave;
		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		
		//setStyleName("panel-alta");
		setWidth("60%");

		
		//setHeight("470px");
		
		setClosable(true);
		setResizable(false);
		setStyleName("ventanamodal");
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		
	

		FormLayout fm = new FormLayout();
		cbMotivoRechazo.setCaption("Motivo Rechazo:");
	    fm.addComponent(cbMotivoRechazo);

	    
	   
	    
	    
	   
	    fm.addComponent(taObservaciones);
	    taObservaciones.setRows(5);
	    taObservaciones.setWidth("100%");

	    vl.addComponent(fm);
	    vl.addComponent(new HorizontalRule());

		
		item = new PropertysetItem();
		item.addItemProperty("cbMotivoRechazo", new ObjectProperty<BigDecimal>(null, BigDecimal.class));
		item.addItemProperty("taObservaciones", new ObjectProperty<String>(null, String.class));
	
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);

		cbMotivoRechazo.setRequired(true);
		cbMotivoRechazo.setWidth("70%");
		cbMotivoRechazo.setValidationVisible(true);
		cbMotivoRechazo.setRequiredError("Campo obligatorio Tipo Rechazo");
		
		taObservaciones.setRequired(true);
		taObservaciones.setValidationVisible(true);
		taObservaciones.setRequiredError("Campo obligatorio Observaciones");
		

		taObservaciones.setRequired(true);
		taObservaciones.setValidationVisible(true);
		taObservaciones.setNullRepresentation("");
		taObservaciones.setRequiredError("Campo Observaciones obligatorio");
		taObservaciones.setValue("");
		
		
		
	    botonera = new BotoneraDoble();
	    vl.addComponent(botonera);		
		setContent(vl);
		
		botonera.btAceptar.setCaption("Confirmar Rechazo");
		
		
		botonera.btCancelar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				UI.getCurrent().getSession().setAttribute("botonpulsadorechazo","CANCELAR");
				
				close();
				
			}
		});	
		
		botonera.btAceptar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					
					binder.commit();
					
					UI.getCurrent().getSession().setAttribute("botonpulsadorechazo","ACEPTAR");			
					close();

					

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
	public void init() {
		
		taObservaciones.setValue(null);
		cbMotivoRechazo.setValue(null);
		
		// Recuperamos el estado del expediente
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		PAC_SHWEB_PROVEEDORES llamada = null;
		try {
			llamada = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*HashMap respuesta = null;
		try {
			respuesta = llamada.ejecutaPAC_SHWEB_PROVEEDORES__F_ESTADO_EXPEDIENTE(
					UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
			Map<String, Object> retorno = new HashMap<String, Object>(respuesta);


			//System.out.println("El estado del expediente es " + UI.getCurrent().getSession().getAttribute("estadoExpediente"));
			UI.getCurrent().getSession().setAttribute("tit.estadoexp",retorno.get("ESTADO").toString());
			

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	*/	
		
		// Recuperamos los comunicados que podemos hacer
		// Modificamos la lista de valores de los comunicados de la ventana
		recargarMotivosListaRechazos();
		
	}
	

	
	public void recargarMotivosListaRechazos( ) {

		// Recuperamos los comunicados
		
		//
		PAC_SHWEB_PROVEEDORES llamadaProv = null;
		try {
			llamadaProv = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaCom = null;

		try {
			respuestaCom = llamadaProv.ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_MOTIVOS_RECHAZO();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbMotivoRechazo.removeAllItems();
		Map<String, Object> retornoComunicados = new HashMap<String, Object>(respuestaCom);
		List<Map> valor = (List<Map>) retornoComunicados.get("REGISTROS");

		for (Map map : valor) {

			cbMotivoRechazo.addItem(map.get("CDCAUSA"));
			cbMotivoRechazo.setItemCaption(map.get("CDCAUSA"),map.get("NBCAUSA").toString() );

		}
		

	}

}