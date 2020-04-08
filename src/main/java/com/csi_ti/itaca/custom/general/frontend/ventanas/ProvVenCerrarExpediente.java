package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.csi_ti.itaca.custom.general.frontend.ProvDatosDetalleExpediente;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.frontend.utiles.GenerarComunicado;
import com.csi_ti.itaca.custom.general.frontend.utiles.ValidarComunicado;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("tests-valo-reindeer")
public class ProvVenCerrarExpediente extends Window   {

	/**
	 * 
	 */
	public String UsuarioSave;

	
	PropertysetItem item;
	
	BotoneraDoble botonera;
	

	private static final long serialVersionUID = 1L;

	private FieldGroup binder;

	// Ponemos los campos
	// Campos del titular para cartera o propuesta
	public ComboBox tfMotivoCierre = new ComboBox();
	public TextArea taCierre = new TextArea("Observaciones");
	public DateField tfFinTrabajos = new DateField("Fecha Fin Trabajos:");
	

	

	public ProvVenCerrarExpediente( ProvDatosDetalleExpediente provDatosDetalleExpediente) {
		
		//System.out.println("Carga provvencerrarexpediente "  + UsuarioSave);
		UsuarioSave = provDatosDetalleExpediente.UsuarioSave;

		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		
		//setStyleName("panel-alta");
		setWidth("60%");
		
		setClosable(true);
		setResizable(false);
		setStyleName("ventanamodal");
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		
	

		FormLayout fm = new FormLayout();
		tfMotivoCierre.setCaption("Motivo Cierre Expediente:");
	    fm.addComponent(tfMotivoCierre);
	    fm.addComponent(tfFinTrabajos);
	    tfFinTrabajos.setDateFormat("dd/MM/yyyy HH:mm");
	    tfFinTrabajos.setResolution(Resolution.MINUTE);
	    fm.addComponent(taCierre);
	    taCierre.setRows(5);
	    taCierre.setWidth("100%");
	    
	    
	    vl.addComponent(fm);
	    
	    vl.addComponent(new HorizontalRule());
	    

	    
		item = new PropertysetItem();
		binder = new FieldGroup(item);
		binder.setBuffered(true);

		tfMotivoCierre.setRequired(true);
		tfMotivoCierre.setWidth("70%");
		tfMotivoCierre.setValidationVisible(true);
		tfMotivoCierre.setRequiredError("Campo obligatorio Motivo Cierre");
		

		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
	
		if ( ValidarComunicado.EsValido("FT") ) {
			tfMotivoCierre.addItem("CON");
			tfMotivoCierre.setItemCaption("CON","Con trabajos finalizados");
			
			tfFinTrabajos.setVisible(true);
			tfFinTrabajos.setRequired(true);
			tfFinTrabajos.setValidationVisible(true);
			tfFinTrabajos.setRequiredError("Campo obligatorio Fecha Fin Trabajos");
			tfFinTrabajos.setValue(null);
			

		}
		if ( ValidarComunicado.EsValido("CA") ) {
			tfMotivoCierre.addItem("SIN");
			tfMotivoCierre.setItemCaption("SIN","Sin cargos");
			tfFinTrabajos.setVisible(false);
			tfFinTrabajos.setRequired(false);			
		}		
		
		tfMotivoCierre.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( tfMotivoCierre.getValue()==null ) {
					taCierre.setCaption("Observaciones:");
				}
			    else if ( tfMotivoCierre.getValue().equals("CON")) {
					taCierre.setCaption("Obs. cerrar con cargos:");
					tfFinTrabajos.setVisible(true);
					tfFinTrabajos.setRequired(true);
					tfFinTrabajos.setValidationVisible(true);
					tfFinTrabajos.setRequiredError("Campo obligatorio Fecha Fin Trabajos");
					tfFinTrabajos.setValue(null);
					
					/*tfFinTrabajos.setDateFormat("dd/MM/yyyy HH:mm");
				    tfFinTrabajos.setResolution(Resolution.MINUTE);					
					tfFinTrabajos.setValue(new Date());
				    java.util.Date dini=(java.util.Date) tfFinTrabajos.getValue();
					Calendar fin=Calendar.getInstance();
					fin.setTime(dini);
					tfFinTrabajos.setRangeEnd(fin.getTime());	
					tfFinTrabajos.setRangeStart(null);
					tfFinTrabajos.setDateOutOfRangeMessage("No puede poner una fecha posterior a la hora actual");
					tfFinTrabajos.setValidationVisible(true);
					
					System.out.println(">>> Rangos:"+tfFinTrabajos.getRangeStart() + " end:" + tfFinTrabajos.getRangeEnd()+ " Date out:" + tfFinTrabajos.getDateOutOfRangeMessage());
						*/				
				}
				else if ( tfMotivoCierre.getValue().equals("SIN")) {
					taCierre.setCaption("Obs. cerrar sin cargos:");
					tfFinTrabajos.setVisible(false);
					tfFinTrabajos.setRequired(false);
				}
				
			}
		});
				
		
		taCierre.setRequired(true);
		taCierre.setValidationVisible(true);
		taCierre.setNullRepresentation("");
		taCierre.setRequiredError("Campo Observaciones obligatorio");
		taCierre.setValue("");
		
		

		item.addItemProperty("tfMotivoCierre", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("taCierre", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("tfFinTrabajos", new ObjectProperty<String>(null, String.class));
		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);	
		binder.bindMemberFields(this);	
		
	    botonera = new BotoneraDoble();
	    vl.addComponent(botonera);		
		setContent(vl);
		
		botonera.btAceptar.setCaption("Confirmar Cierre Expediente");
		
		
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
				String mensajes = "";
				try {
					
					String retorno = "1";
					
					System.out.println("Ciere " + UsuarioSave);
					
					binder.commit();
					
					// LA fecha NO PUEDE SUPER A LA HORA
					if ( tfFinTrabajos.isVisible() ) {
						
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    				String fechaMax = formatter.format(new Date()).toString();
	    				String fechaMin = formatter.format(tfFinTrabajos.getValue()).toString();
	    				
	    				Date fecha = new Date();
	    				
	    				//System.out.println("Compradno fechas:" + fecha.compareTo(tfFinTrabajos.getValue()));
	    				if ( fecha.compareTo(tfFinTrabajos.getValue()) < 0  )  {
	    				   mensajes = "La fecha no puede ser superior a la hora actual\n"	;
	    				   throw new CommitException();
	    				}
						
					}
					
					
					 
					// Hacemos la llamada al comunicado
				    if ( tfMotivoCierre.getValue().equals("CON")) {
					
				    	Object[] arrayObjectRecord = { "C14","N" };
			    	
				    	// Enviamos la fecha
	    				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    				String fecha = formatter.format(tfFinTrabajos.getValue()).toString();
				    	UI.getCurrent().getSession().setAttribute("fhfintrabajos",fecha);
						retorno = GenerarComunicado.NuevoComunicado("FT",taCierre.getValue(),
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(),arrayObjectRecord,
								UsuarioSave);
				    }
				    else if ( tfMotivoCierre.getValue().equals("SIN")) {
				    	
				    	//Object[] arrayObjectRecord = { "C14","N" };
				    	//System.out.println("Sin trabajos finalizados. Estado:"+UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString());
						retorno = GenerarComunicado.NuevoComunicado("CA",taCierre.getValue(),
								UI.getCurrent().getSession().getAttribute("tit.estadoexp").toString(), null, UsuarioSave);
				    }
				    
					if (retorno.equals("0")) {
						provDatosDetalleExpediente.btCerrarExpediente.setVisible(false);
						provDatosDetalleExpediente.recargarDatos();
						
					}
					
					
					close();
					

				} catch (CommitException e) {

					
					
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
		
		tfMotivoCierre.removeAllItems();
		if ( ValidarComunicado.EsValido("FT") ) {
			tfMotivoCierre.addItem("CON");
			tfMotivoCierre.setItemCaption("CON","Con trabajos finalizados");
		}
		if ( ValidarComunicado.EsValido("CA") ) {
			tfMotivoCierre.addItem("SIN");
			tfMotivoCierre.setItemCaption("SIN","Sin cargos");
		}	
		
		tfMotivoCierre.setValue(null);
		tfFinTrabajos.setValue(null);
		taCierre.setValue(null);


	}
	

	
	/*@Override
	public void close() {
		// TODO Auto-generated method stub
		//super.close();
		//System.out.println("Cerramos la ventana de anular");
		UI.getCurrent().removeWindow(this);
		
	}*/
	
	

}