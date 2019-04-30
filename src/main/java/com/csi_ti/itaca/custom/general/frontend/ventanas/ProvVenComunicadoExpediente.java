package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.csi_ti.itaca.custom.general.frontend.ProvDatosComunicadosExpediente;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.frontend.utiles.GenerarComunicado;
import com.csi_ti.itaca.custom.general.frontend.utiles.ValidarComunicado;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConexionFactoria;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
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
public class ProvVenComunicadoExpediente extends Window   {

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
	public ComboBox cbComunicado = new ComboBox();
	public TextArea taObservaciones = new TextArea("Observaciones");

	public DateField tfConfirmacion = new DateField("Fecha Confirmación Llegada:");	
	// CAMPOS COMUNICADO PRIMER CONTACTO
	
	
	public DateField tfPrimerContacto = new DateField("Fecha primer Contacto:");
	/*public PopupDateField tfHoraPrimerContacto = new PopupDateField(){
		private static final long serialVersionUID = 1L;

		@Override
	    protected Date handleUnparsableDateString(String dateString) throws ConversionException
	    {
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
	    	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy"); 
	        String fcita = sdfDate.format(tfPrimerContacto.getValue());
	    	if ( dateString!=null && dateString.length()==4 && Integer.valueOf(dateString)>=0 && Integer.valueOf(dateString)<=2359 ) {
	    		try {
					return formatter.parse(fcita.toString().substring(0, 10) + " " + dateString);
				} catch (ParseException e) { return null; }
	    	}
	    	return null;
	    }
	};*/
	public DateField tfPrevista = new DateField("Fecha Prevista:");
	/*public PopupDateField tfHoraPrevista = new PopupDateField(){
		private static final long serialVersionUID = 1L;

		@Override
	    protected Date handleUnparsableDateString(String dateString) throws ConversionException
	    {
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
	    	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy"); 
	        String fcita = sdfDate.format(tfPrevista.getValue());
	    	if ( dateString!=null && dateString.length()==4 && Integer.valueOf(dateString)>=0 && Integer.valueOf(dateString)<=2359 ) {
	    		try {
					return formatter.parse(fcita.toString().substring(0, 10) + " " + dateString);
				} catch (ParseException e) { return null; }
	    	}
	    	return null;
	    }
	};	*/
	
	public DateField tfPrimeravisita= new DateField("Fecha Primera Visita:");
	/*public PopupDateField tfHoraPrimeraVisita = new PopupDateField(){
		private static final long serialVersionUID = 1L;

		@Override
	    protected Date handleUnparsableDateString(String dateString) throws ConversionException
	    {
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
	    	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy"); 
	        String fcita = sdfDate.format(tfPrimeravisita.getValue());
	    	if ( dateString!=null && dateString.length()==4 && Integer.valueOf(dateString)>=0 && Integer.valueOf(dateString)<=2359 ) {
	    		try {
					return formatter.parse(fcita.toString().substring(0, 10) + " " + dateString);
				} catch (ParseException e) { return null; }
	    	}
	    	return null;
	    }
	};*/		
	
	public CheckBox ckNoVisita = new CheckBox("No se ha podido concretar visita con el asegurado");
	public CheckBox ckNoContacto = new CheckBox("No se ha podido contactar con el asegurado");
	public CheckBox ckCerrarExpedienteSinCargos = new CheckBox("Cerrar expediente sin cargos");
	public TextField tfImporte = new TextField("Valoración aprox:");
	
	// CAMPOS COMUNICADO PRIMERA VISITA
	
	ComboBox cbNumero = new ComboBox("Número de Gremios:");
	public CheckBox ckPerito = new CheckBox("Es preciso que intervenga perito?");
	public CheckBox ckExisteTerceroCausante = new CheckBox("Existe tercer causante");
	public CheckBox ckExistePerjudicado = new CheckBox("Existe Perjudicado");
	public CheckBox ckSinCobertura = new CheckBox("Siniestro sin cobertura");
	public CheckBox ckCerrarExpedienteTrabajosFinalizados = new CheckBox("Cerrar expediente trabajos finalizados");
	


	@SuppressWarnings("deprecation")
	public ProvVenComunicadoExpediente( ProvDatosComunicadosExpediente provDatosComunicadosExpediente) {

		UsuarioSave = provDatosComunicadosExpediente.UsuarioSave;
		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		
		//setStyleName("panel-alta");
		setWidth("60%");
		
		
		
		setResizable(true);

		//setHeight("470px");
		setClosable(true);
		setStyleName("ventanamodal");
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		
	

		FormLayout fm = new FormLayout();
		cbComunicado.setCaption("Tipo de comunicado:");
	    fm.addComponent(cbComunicado);
	    fm.addComponent(tfPrimerContacto);
	    tfPrimerContacto.setLocale(new Locale("es", "ES"));
	    fm.addComponent(tfPrevista);
	    fm.addComponent(tfPrimeravisita);
	    fm.addComponent(tfConfirmacion);
	    
	    tfConfirmacion.setRangeStart(new Date("01/01/2000"));
	    tfPrimeravisita.setRangeStart(new Date("01/01/2000"));
	    tfPrevista.setRangeStart(new Date("01/01/2000"));
	    tfPrimerContacto.setRangeStart(new Date("01/01/2000"));
	    
	    tfPrimerContacto.setDateFormat("dd/MM/yyyy HH:mm");
	    tfPrevista.setDateFormat("dd/MM/yyyy HH:mm");
	    tfPrimeravisita.setDateFormat("dd/MM/yyyy HH:mm");

	    tfPrimeravisita.setLocale(new Locale("es", "ES"));
	    tfConfirmacion.setDateFormat("dd/MM/yyyy HH:mm");
	    
	    tfPrimerContacto.setResolution(Resolution.MINUTE);
	    tfPrevista.setResolution(Resolution.MINUTE);
	    tfPrimeravisita.setResolution(Resolution.MINUTE);
	    tfConfirmacion.setResolution(Resolution.MINUTE);
	    
	    
	    fm.addComponent(ckNoVisita);
	    fm.addComponent(ckNoContacto);
	    fm.addComponent(ckCerrarExpedienteSinCargos);
	    tfImporte.addValidator(new RegexpValidator(exp, "Importe incorrecto"));
	    tfImporte.setNullRepresentation("");
	    tfImporte.addStyleName("align-right");
	    tfImporte.setVisible(false);
	    fm.addComponent(tfImporte);
	    

	    for (int i=1;i<=9;i++) {
			cbNumero.addItem(String.valueOf(i));
			cbNumero.setItemCaption(String.valueOf(i),String.valueOf(i));
	    }
	    
	    ckCerrarExpedienteSinCargos.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( ckCerrarExpedienteSinCargos.getValue() ) {
					tfPrevista.setValue(null);
					tfPrevista.setEnabled(false);
					tfPrevista.setRequired(false);
				}
				else {
					tfPrevista.setValue(null);
					tfPrevista.setEnabled(true);
					tfPrevista.setRequired(true);
					tfPrevista.setValidationVisible(true);
				}
			}
		});
	    
	    ckNoVisita.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRIVIS" ) { 
					if ( ckNoVisita.getValue() ) {
					tfPrimeravisita.setValue(null);
					tfPrimeravisita.setRequired(false);
					}
					else {
						tfPrimeravisita.setValue(null);
						tfPrimeravisita.setRequired(true);
					}
				}
				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRICON" ) { 
					if ( ckNoVisita.getValue() ) {
					tfPrimerContacto.setValue(null);
					tfPrimerContacto.setRequired(false);
					tfPrevista.setValue(null);
					tfPrevista.setRequired(false);
					}
					else {
						tfPrimerContacto.setValue(null);
						tfPrimerContacto.setRequired(true);
						tfPrevista.setValue(null);
						tfPrevista.setRequired(true);
					}
				}				
			}
		});
	    
	    ckNoContacto.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRIVIS" ) { 
					if ( ckNoContacto.getValue() ) {
					tfPrimeravisita.setValue(null);
					tfPrimeravisita.setRequired(false);
					}
					else {
						tfPrimeravisita.setValue(null);
						tfPrimeravisita.setRequired(true);
					}
				}
				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRICON" ) { 
					if ( ckNoContacto.getValue() ) {
					tfPrimerContacto.setValue(null);
					tfPrimerContacto.setRequired(true);
					tfPrevista.setValue(null);
					tfPrevista.setRequired(false);
					tfPrimerContacto.setValue(new Date());
					tfPrevista.setValue(null);
					tfPrevista.setEnabled(false);
					
					}
					else {
						tfPrimerContacto.setValue(null);
						tfPrimerContacto.setRequired(true);
						tfPrevista.setValue(null);
						tfPrevista.setRequired(true);
						tfPrevista.setEnabled(true);
					}
				}				
			}
		});	    
	    
	    
	    /*tfPrimeravisita.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRIVIS" ) {
					if ( tfPrimeravisita.getValue()==null) {
						ckNoVisita.setValue(true);
					} else
						ckNoVisita.setValue(false);
					
				}
				
			}
		});*/
	    
	    /*tfPrimerContacto.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub

				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRICON" ) {
					if ( tfPrevista.getValue()==null && tfPrimerContacto.getValue()==null ) {
						ckNoVisita.setValue(true);
					} else
						ckNoVisita.setValue(false);
					
				}				
			}
		});	*/   
	    
	    
	    /*tfPrevista.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub

				if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRICON" ) {
					if ( tfPrevista.getValue()==null && tfPrimerContacto.getValue()==null ) {
						ckNoVisita.setValue(true);
					} else
						ckNoVisita.setValue(false);
					
				}				
			}
		});	*/     
			
		
		
	    fm.addComponent(cbNumero);
	    fm.addComponent(ckPerito);
	    fm.addComponent(ckExisteTerceroCausante);
	    fm.addComponent(ckExistePerjudicado);
	    fm.addComponent(ckSinCobertura);
	    fm.addComponent(ckCerrarExpedienteTrabajosFinalizados);
	    fm.addComponent(ckCerrarExpedienteSinCargos);
	    fm.addComponent(taObservaciones);
	    taObservaciones.setRows(5);
	    taObservaciones.setWidth("100%");

	    vl.addComponent(fm);
	    vl.addComponent(new HorizontalRule());

		
		item = new PropertysetItem();
		item.addItemProperty("cbComunicado", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("taObservaciones", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("tfPrevista", new ObjectProperty<Date>(null, Date.class));
		item.addItemProperty("tfPrimeravisita", new ObjectProperty<Date>(null, Date.class));
		item.addItemProperty("tfConfirmacion", new ObjectProperty<Date>(null, Date.class));
		item.addItemProperty("tfPrimerContacto", new ObjectProperty<Date>(null, Date.class));
		item.addItemProperty("cbNumero", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("tfImporte", new ObjectProperty<String>(null, String.class));
		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);

		cbComunicado.setRequired(true);
		cbComunicado.setWidth("70%");
		cbComunicado.setValidationVisible(true);
		cbComunicado.setRequiredError("Campo obligatorio Motivo Cierre");
		
		taObservaciones.setRequired(true);
		taObservaciones.setValidationVisible(true);
		taObservaciones.setRequiredError("Campo obligatorio Observaciones");
		
		tfPrevista.setRequired(true);
		tfPrevista.setValidationVisible(true);
		tfPrevista.setRequiredError("Campo obligatorio Fecha Prevista");

		tfPrimeravisita.setRequired(true);
		tfPrimeravisita.setValidationVisible(true);
		tfPrimeravisita.setRequiredError("Campo obligatorio Fecha Primera Visita");
		
		cbNumero.setRequired(true);
		cbNumero.setValidationVisible(true);
		cbNumero.setRequiredError("Campo número de gremios obligatorio");
		
		tfImporte.setRequired(true);
		tfImporte.setValidationVisible(true);
		tfImporte.setRequiredError("Importe valoración obligatorio");		
		
		tfPrimerContacto.setRequired(true);
		tfPrimerContacto.setValidationVisible(true);
		tfPrimerContacto.setRequiredError("Campo obligatorio Fecha Primer Contacto");
		tfPrimerContacto.setValue(new Date());
		
		tfConfirmacion.setRequired(true);
		tfConfirmacion.setValidationVisible(true);
		tfConfirmacion.setRequiredError("Campo obligatorio Fecha Confirmación");
		tfConfirmacion.setValue(new Date());		
		
		cbNumero.setRequired(true);
		cbNumero.setValidationVisible(true);
		cbNumero.setRequiredError("Campo obligatorio Número");		
		


			if ( ValidarComunicado.EsValido("CV")  ) {
				cbComunicado.addItem("CONLLE");
				cbComunicado.setItemCaption("CONLLE","CONFIRMACIÓN DE LLEGADA");
			}
			if ( ValidarComunicado.EsValido("NO")  ) {
				cbComunicado.addItem("NOMOPE");
				cbComunicado.setItemCaption("NOMOPE","NOMBRE OPERARIO");
			}			
			if ( ValidarComunicado.EsValido("PC")  ) {
				cbComunicado.addItem("PRICON");
				cbComunicado.setItemCaption("PRICON","INFORMACION PRIMER CONTACTO");
			}
			if ( ValidarComunicado.EsValido("V1")  ) {
				cbComunicado.addItem("PRIVIS");
				cbComunicado.setItemCaption("PRIVIS","INFORMACION PRIMERA VISITA");
			}
			if ( ValidarComunicado.EsValido("RS")  ) {
				cbComunicado.addItem("RESINC");
				cbComunicado.setItemCaption("RESINC","RESPUESTA INCIDENCIA");
			}
			if ( ValidarComunicado.EsValido("IT")  ) {
				cbComunicado.addItem("INSTRU");
				cbComunicado.setItemCaption("INSTRU","INSTRUCCIONES");
			}
			if ( ValidarComunicado.EsValido("H")  ) {
				cbComunicado.addItem("INFORM");
				cbComunicado.setItemCaption("INFORM","INFORMACION");
			}
			if ( ValidarComunicado.EsValido("FR")  ) {
				cbComunicado.addItem("FACREC");
				cbComunicado.setItemCaption("FACREC","FACTURA RECHAZADA");
			}			


		cbComunicado.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( cbComunicado.getValue()==null ) {
					ckNoVisita.setVisible(false);
					ckNoContacto.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfConfirmacion.setVisible(false);
					tfPrevista.setVisible(false);
					tfPrimeravisita.setVisible(false);					
					taObservaciones.setCaption("Observaciones:");
					tfImporte.setVisible(false);
					setHeight("200px");
					taObservaciones.setRows(5);
					
				}
				
				// Información confirmación llegada
			    else if ( cbComunicado.getValue().equals("CONLLE")) {
			    	
					
					taObservaciones.setRequired(false);
					taObservaciones.setVisible(false);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					
					tfConfirmacion.setVisible(true);
					tfConfirmacion.setRequired(true);
					tfConfirmacion.setValue(new Date());
					
					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					setHeight("150px");
					/*taObservaciones.setRows(5);
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");*/
				}
				
				// NOMBRE OPERARIO
			    else if ( cbComunicado.getValue().equals("NOMOPE")) {
			    	
					
					taObservaciones.setRequired(true);
					taObservaciones.setVisible(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					
					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					taObservaciones.setCaption("Nombre del Operario");
					taObservaciones.setRequiredError("Campo Nombre Operario obligatorio");
					taObservaciones.setRows(1);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					
					setHeight("140px");
				}				
				
				// Información primer contacto
			    else if ( cbComunicado.getValue().equals("PRICON")) {
			    	
					
					taObservaciones.setRequired(true);
					tfPrevista.setRequired(true);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(true);
					cbNumero.setRequired(false);			    	

					tfImporte.setVisible(false);
					tfImporte.setRequired(false);

			    	tfPrimerContacto.setVisible(true);
					tfPrevista.setVisible(true);
					ckNoVisita.setVisible(true);
					ckNoContacto.setVisible(true);
					ckCerrarExpedienteSinCargos.setVisible(true);
					
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					
					
					tfPrimeravisita.setVisible(false);
					taObservaciones.setVisible(true);
					taObservaciones.setCaption("Observaciones:");
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					
					setHeight("320px");
				}
				else if ( cbComunicado.getValue().equals("PRIVIS")) {
					
					taObservaciones.setRequired(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(true);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					
					tfImporte.setVisible(true);
					tfImporte.setRequired(true);
					tfImporte.setImmediate(true);
					
					tfPrimeravisita.setVisible(true);
					cbNumero.setVisible(true);
					ckPerito.setVisible(true);
					ckExisteTerceroCausante.setVisible(true);
					ckExistePerjudicado.setVisible(true);
					ckSinCobertura.setVisible(true);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(true);
					ckCerrarExpedienteSinCargos.setVisible(true);
					ckNoVisita.setVisible(true);
					
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					taObservaciones.setVisible(true);
					taObservaciones.setCaption("Observaciones:");
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					
					setHeight("450px");
				}
				// Respuesta incidencia
				else if ( cbComunicado.getValue().equals("RESINC")) {

					taObservaciones.setRequired(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					
					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					
					taObservaciones.setCaption("Respuesta incidencia:");
					taObservaciones.setVisible(true);
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					
					setHeight("200px");

				}
				else if ( cbComunicado.getValue().equals("INSTRU")) {
					
					taObservaciones.setRequired(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);

					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					taObservaciones.setCaption("Observaciones:");
					taObservaciones.setVisible(true);
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					setHeight("200px");
				}
				else if ( cbComunicado.getValue().equals("INFORM")) {
					
					taObservaciones.setRequired(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					
					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					taObservaciones.setCaption("Observaciones:");
					taObservaciones.setVisible(true);
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					
					setHeight("200px");
				}
				else if ( cbComunicado.getValue().equals("FACREC")) {
					
					taObservaciones.setRequired(true);
					tfPrevista.setRequired(false);
					tfPrimeravisita.setRequired(false);
					tfPrimerContacto.setRequired(false);
					cbNumero.setRequired(false);
					tfImporte.setVisible(false);
					tfImporte.setRequired(false);
					tfPrimeravisita.setVisible(false);
					cbNumero.setVisible(false);
					ckPerito.setVisible(false);
					ckExisteTerceroCausante.setVisible(false);
					ckExistePerjudicado.setVisible(false);
					ckSinCobertura.setVisible(false);
					ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
					ckCerrarExpedienteSinCargos.setVisible(false);
					ckNoVisita.setVisible(false);
					tfPrimerContacto.setVisible(false);
					tfPrevista.setVisible(false);
					ckNoContacto.setVisible(false);
					
					taObservaciones.setCaption("Observaciones:");
					taObservaciones.setVisible(true);
					taObservaciones.setRequiredError("Campo Observaciones obligatorio");
					taObservaciones.setRows(5);
					
					tfConfirmacion.setVisible(false);
					tfConfirmacion.setRequired(false);
					setHeight("200px");
				}				
				
			}
		});
				
		
		taObservaciones.setRequired(true);
		taObservaciones.setValidationVisible(true);
		taObservaciones.setNullRepresentation("");
		taObservaciones.setRequiredError("Campo Observaciones obligatorio");
		taObservaciones.setValue("");
		
		
		
	    botonera = new BotoneraDoble();
	    vl.addComponent(botonera);		
		setContent(vl);
		
		botonera.btAceptar.setCaption("Confirmar Comunicado");
		
		
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
					String retorno = "1";
					Boolean validado = true;
					
					binder.commit();
					
					// Validaciones de primer contacto
					if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRICON" ) {
					
						// Si hemos informado alguna fecha no podemos seleccionar los checks de no visita
						/*if ( 
								(ckNoContacto.getValue() || ckNoVisita.getValue())
							&&  (tfPrimerContacto.getValue()!=null || tfPrevista.getValue()!=null)
							
								
							) {
							new Notification("Se han detectado errores",
									"Si activa las casilla de no vista/contacto no puede informar las fechas",
									Notification.Type.TRAY_NOTIFICATION, true)
									.show(Page.getCurrent());
							
							return;
						}*/	
						
					}
					
					// Validaciones de primera visita
					if ( cbComunicado.getValue()!=null && cbComunicado.getValue()=="PRIVIS" ) {
					
						// Si hemos informado alguna fecha no podemos seleccionar los checks de no visita
						if ( 
								(ckNoVisita.getValue())
							&&  (tfPrimeravisita.getValue()!=null)
							
								
							) {
							new Notification("Se han detectado errores",
									"Si activa las casilla de no vista no puede informar las fecha de primera Visita",
									Notification.Type.TRAY_NOTIFICATION, true)
									.show(Page.getCurrent());
							
							return;
						}	
						
					}					
						
					if ( cbComunicado.getValue().equals("NOMOPE")) { 
						
			 
				    	
						retorno = GenerarComunicado.NuevoComunicado("NO",taObservaciones.getValue(),
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),null, UsuarioSave);
						
						
					}
					else if ( cbComunicado.getValue().equals("CONLLE")) { 
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						String obs = "APP PWP - El operario confirma su llegada para solucionar el expediente " +
								UI.getCurrent().getSession().getAttribute("expediente") + ". Fecha llegada: "
								+ formatter.format(tfConfirmacion.getValue()).toString();
						
						retorno = GenerarComunicado.NuevoComunicado("CV",obs,
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),null, UsuarioSave);
						
						
					}										
					else if ( cbComunicado.getValue().equals("PRICON")) { 
							
				    		// PC>>> - C1, C14, C16, C2,
				    	/*	C1	FECHA PRIMER CONTACTO DD/MM/YYYY HH24:MI:SS
							C14	CERRAR EXPEDIENTE SIN CARGOS S/N
							C16	NO SE HA PODIDO CONTACTAR CON ASEGURADO S/N
							C2	FECHA PREVISTA DE 1ª VISITA DD/MM/YYYY HH24:MI:SS
							C3	NO SE HA PODIDO CONCERTAR VISITA CON ASEGURADO S/N
							*/
				    		String cerrarsn = "N";
				    		if ( ckCerrarExpedienteSinCargos.getValue())  {
				    			cerrarsn = "S";
				    		}
				    		String contactarsn = "N";
				    		if ( ckNoContacto.getValue())  {
				    			contactarsn = "N";
				    		}
				    		String visitasn = "N";
				    		if ( ckNoVisita.getValue())  {
				    			visitasn = "S";
				    		}				
				    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				    		
				    		String fechapc;
				    		if ( tfPrimerContacto.getValue()==null) {
				    		  fechapc = null;
				    		  
				    		} else {
				    			fechapc = formatter.format(tfPrimerContacto.getValue()).toString();
				    		}
				    		
				    		String fechapre;
				    		if ( tfPrevista.getValue()==null) {
				    		  fechapre = null;
				    		  
				    		} else {
				    			fechapre = formatter.format(tfPrevista.getValue()).toString();
				    		}				    		
				    		
				    		
					    	Object[] arrayObjectRecord = { 
					    			 "C1",fechapc
					    			,"C14",cerrarsn
					    			,"C16",contactarsn
					    			,"C2",fechapre
					    			,"C3",visitasn};
				    		
				    		
	
				    	
					    	//System.out.println("Primer Contacto. Estado ."+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
							retorno = GenerarComunicado.NuevoComunicado("PC",taObservaciones.getValue(),
									UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),arrayObjectRecord, UsuarioSave);
							
							
					}
				    else if ( cbComunicado.getValue().equals("PRIVIS")) {   // ok, funciona
				    	
				    	// V1 >>- C10, C14, C15, C17, C4, C5, C6, C7, C8, C9
				    	/*	C17	IMPORTE TOTAL APROX. SINIESTRO
							C10	CERRAR EXPEDIENTE TRABAJOS FINALIZADOS
							C14	CERRAR EXPEDIENTE SIN CARGOS
							C15	CLIENTE AUSENTE
							C4	FECHA PRIMERA VISITA
							C5	NUMERO GREMIOS
							C6	¿ES PRECISO QUE INTERVENGA PERITO?
							C7	EXISTE PERJUDICADO
							C8	EXISTE TERCERO CAUSANTE
							C9	SINIESTRO SIN COBERTURA
							*/
			    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			    		
			    		String peritosn = "N";
			    		if ( ckPerito.getValue())  {
			    			peritosn = "S";
			    		}
			    		String tercersn = "N";
			    		if ( ckExisteTerceroCausante.getValue())  {
			    			tercersn = "S";
			    		}
			    		String perjudicadosn = "N";
			    		if ( ckExistePerjudicado.getValue())  {
			    			perjudicadosn = "S";
			    		}
			    		String coberturasn = "N";
			    		if ( ckSinCobertura.getValue())  {
			    			coberturasn = "S";
			    		}			   
			    		String cerrarsn = "N";
			    		if ( ckCerrarExpedienteTrabajosFinalizados.getValue())  {
			    			cerrarsn = "S";
			    		}
			    		String sincargossn = "N";
			    		if ( ckCerrarExpedienteSinCargos.getValue())  {
			    			sincargossn = "S";
			    		}
			    		String visitasn = "N";
			    		if ( ckNoVisita.getValue())  {
			    			visitasn = "S";
			    		}			    		
			    		
			    		String fechapv;
			    		if ( tfPrimeravisita.getValue()==null) {
			    		  fechapv = null;
			    		  
			    		} else {
			    			fechapv = formatter.format(tfPrimeravisita.getValue()).toString();
			    		}
			    		
			    		String numero;
			    		if ( cbNumero.getValue()==null ) {
			    			numero = "0";
			    		} else {
			    			numero = cbNumero.getValue().toString();
			    		}
			    			
						
						Object[] arrayObjectRecord = { 
							 "C17",tfImporte.getValue().toString()
							,"C10",cerrarsn
							,"C14",sincargossn
							,"C15",visitasn
							,"C4",fechapv
							,"C5",numero
							,"C6",peritosn
							,"C7",perjudicadosn
							,"C8",tercersn
							,"C9",coberturasn};


				    	
				    	//System.out.println("Primera visita. Estado ."+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
						retorno = GenerarComunicado.NuevoComunicado("V1",taObservaciones.getValue(),
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),arrayObjectRecord, UsuarioSave);
				    }	
					
				    else if ( cbComunicado.getValue().equals("FACREC")) { // factura rechazada
				    	
				    	
				    	//System.out.println("Respuesta incidencia. Estado:"+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
						retorno = GenerarComunicado.NuevoComunicado("FR",taObservaciones.getValue(),
						UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(), null, UsuarioSave);
				}					
				    else if ( cbComunicado.getValue().equals("RESINC")) { // OK FUNCIONA
				    	
					    	
					    	//System.out.println("Respuesta incidencia. Estado:"+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
							retorno = GenerarComunicado.NuevoComunicado("RS",taObservaciones.getValue(),
							UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(), null, UsuarioSave);
					}
				    else if ( cbComunicado.getValue().equals("INSTRU")) {   // ok, funciona
				    	
				    	Object[] arrayObjectRecord = { "C11",1 };
				    	
				    	
				    	//System.out.println("Primer Contacto. Estado ."+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
						retorno = GenerarComunicado.NuevoComunicado("IT",taObservaciones.getValue(),
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),arrayObjectRecord, UsuarioSave);
				    }
					else if ( cbComunicado.getValue().equals("INFORM")) {// OK FUNCIONA
							    	
							    	//Object[] arrayObjectRecord = { "C11",8 };
							    	
							    	//System.out.println("Solicitar informacion. Estado ."+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
									retorno = GenerarComunicado.NuevoComunicado("H",taObservaciones.getValue(),
											UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),null, UsuarioSave);
					}	
					
					
					if ( validado ){
					
						if (retorno.equals("0")) {
							provDatosComunicadosExpediente.recargarComunicados();
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
		
		// Ocultamos los campos
		
		ckNoVisita.setVisible(false);
		ckNoContacto.setVisible(false);
		ckCerrarExpedienteSinCargos.setVisible(false);
		cbNumero.setVisible(false);
		ckPerito.setVisible(false);
		ckExisteTerceroCausante.setVisible(false);
		ckExistePerjudicado.setVisible(false);
		ckSinCobertura.setVisible(false);
		ckCerrarExpedienteTrabajosFinalizados.setVisible(false);
		tfPrimerContacto.setVisible(false);
		tfPrevista.setVisible(false);
		tfPrimeravisita.setVisible(false);
		tfConfirmacion.setVisible(false);
		


		
		
	
		
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
		
	
		ckNoVisita.setValue(false);
		ckNoContacto.setValue(false);
		ckCerrarExpedienteSinCargos.setValue(false);
		cbNumero.setValue(false);
		ckPerito.setValue(false);
		ckExisteTerceroCausante.setValue(false);
		ckExistePerjudicado.setValue(false);
		ckSinCobertura.setValue(false);
		ckCerrarExpedienteTrabajosFinalizados.setValue(false);
		tfPrimerContacto.setValue(null);
		tfPrevista.setValue(null);
		tfPrimeravisita.setValue(null);
		taObservaciones.setValue(null);
		tfImporte.setValue(null);
		cbNumero.setValue(null);
		cbComunicado.setValue(null);
		
		// Recuperamos el estado del expediente
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
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


			//System.out.println("El estado del expediente es " + UI.getCurrent().getSession().getAttribute("estadoExpediente"));
			UI.getCurrent().getSession().setAttribute("tit.estadoexp",retorno.get("ESTADO").toString());
			

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}		
		
		// Recuperamos los comunicados que podemos hacer
		// Modificamos la lista de valores de los comunicados de la ventana
		cbComunicado.removeAllItems();
		if ( ValidarComunicado.EsValido("CV")  ) {
			cbComunicado.addItem("CONLLE");
			cbComunicado.setItemCaption("CONLLE","CONFIRMACIÓN DE LLEGADA");
		}
		if ( ValidarComunicado.EsValido("NO")  ) {
			cbComunicado.addItem("NOMOPE");
			cbComunicado.setItemCaption("NOMOPE","NOMBRE OPERARIO");
		}			
		if ( ValidarComunicado.EsValido("PC")  ) {
			cbComunicado.addItem("PRICON");
			cbComunicado.setItemCaption("PRICON","INFORMACION PRIMER CONTACTO");
		}
		if ( ValidarComunicado.EsValido("V1")  ) {
			cbComunicado.addItem("PRIVIS");
			cbComunicado.setItemCaption("PRIVIS","INFORMACION PRIMERA VISITA");
		}
		if ( ValidarComunicado.EsValido("RS")  ) {
			cbComunicado.addItem("RESINC");
			cbComunicado.setItemCaption("RESINC","RESPUESTA INCIDENCIA");
		}
		if ( ValidarComunicado.EsValido("IT")  ) {
			cbComunicado.addItem("INSTRU");
			cbComunicado.setItemCaption("INSTRU","INSTRUCCIONES");
		}
		if ( ValidarComunicado.EsValido("H")  ) {
			cbComunicado.addItem("INFORM");
			cbComunicado.setItemCaption("INFORM","INFORMACION");
		}
		if ( ValidarComunicado.EsValido("FR")  ) {
			cbComunicado.addItem("FACREC");
			cbComunicado.setItemCaption("FACREC","FACTURA RECHAZADA");
		}					
		
			
		
	}
	
	
	

	
	/*@Override
	public void close() {
		// TODO Auto-generated method stub
		//super.close();
		//System.out.println("Cerramos la ventana de anular");
		UI.getCurrent().removeWindow(this);
		
	}*/

}