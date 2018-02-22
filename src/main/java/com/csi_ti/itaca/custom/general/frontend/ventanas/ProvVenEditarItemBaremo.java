package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.custom.general.frontend.ProvDatosPresupuestoExpediente;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.server.jdbc.WEB_PROV;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
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
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("tests-valo-reindeer")
public class ProvVenEditarItemBaremo extends Window   {

	/**
	 * 
	 */
	public String UsuarioSave ;

	
	String acceso;
	String item2;
	String item3;
	java.math.BigDecimal presu;
	java.math.BigDecimal unidades;
	java.math.BigDecimal importe;
	
	public ProvDatosPresupuestoExpediente.Gremios gremio;
	
	private static GeneralBusinessServiceImpl service;
	
	PropertysetItem item;
	
	BotoneraDoble botonera;
	
	String exp = "^[0-9]+(\\,([0-9]{1,2})?)?$";
	//String exp = "^[0-9]+\\,?[0-9]*$";
	//String exp = "^(\\d{1}\\.)?(\\d+\\.?)+(,\\d)?$";

	public List<Map> valoresBaremos;
	
	private static final long serialVersionUID = 1L;

	private FieldGroup binder;

	// Ponemos los campos
	// Campos del titular para cartera o propuesta
	public TextField tfGremio = new TextField("Gremio:");
	public java.math.BigDecimal idgremio;
	public ComboBox cbItem1 = new ComboBox("Item");
	public ComboBox cbItem2 = new ComboBox("Item");
	public ComboBox cbTipoDano = new ComboBox("Tipo Daño");
	public TextField tfUnidades = new TextField("Unidades");
	public TextField tfImporte= new TextField("Importe");
	public TextField tfOtrosTrabajos = new TextField("Otros Trabajos");
	
	

	

	public ProvVenEditarItemBaremo( ProvDatosPresupuestoExpediente provDatosPresupuestoExpediente) {

		UsuarioSave = provDatosPresupuestoExpediente.UsuarioSave;
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		
		// TODO Auto-generated constructor stub
		setCaption("A�ADIR ITEM BAREMO");
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

		cbItem1.setRequired(true);
		cbItem1.setWidth("95%");
		cbItem1.setValidationVisible(true);
		cbItem1.setRequiredError("Campo obligatorio Item1");
		hl.addComponent(cbItem1);
		
		cbItem2.setRequired(true);
		cbItem2.setWidth("95%");
		cbItem2.setValidationVisible(true);
		cbItem2.setRequiredError("Campo obligatorio Item2");
		hl.addComponent(cbItem2);		
		
		cbTipoDano.setRequired(true);
		cbTipoDano.setWidth("95%");
		cbTipoDano.setValidationVisible(true);
		cbTipoDano.setRequiredError("Campo obligatorio Tipo de Da�o");
		cbTipoDano.addItem("DD");
		cbTipoDano.addItem("DE");
		cbTipoDano.setItemCaption("DD","Daños Directos");
		cbTipoDano.setItemCaption("DE","Daños Estéticos");
		hl.addComponent(cbTipoDano);
		
		
		hl.addComponent(tfUnidades);
		tfUnidades.setRequired(true);
		tfUnidades.setWidth("50px");
		tfUnidades.setValidationVisible(true);
		tfUnidades.setRequiredError("Campo obligatorio Unidades");
		
		
		tfImporte.setRequired(true);
		tfImporte.setValidationVisible(true);
		tfImporte.setRequiredError("Campo obligatorio Importe");		
		hl.addComponent(tfImporte);
		
		
		hl.setExpandRatio(cbItem1,30);
		hl.setExpandRatio(cbItem2,35);
		hl.setExpandRatio(cbTipoDano,15);
		hl.setExpandRatio(tfUnidades,10);
		hl.setExpandRatio(tfImporte,10);
		
		tfUnidades.setNullRepresentation("");
		tfImporte.setNullRepresentation("");
		
		//tfImporte.setEnabled(false);

		item.addItemProperty("cbItem1", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbItem2", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbTipoDano", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("tfUnidades", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("tfImporte", new ObjectProperty<String>(null, String.class));

		tfUnidades.addValidator(new RegexpValidator(exp, "Unidades incorrectas"));
		tfImporte.addValidator(new RegexpValidator(exp, "Importe incorrecto"));
		
		tfUnidades.addStyleName("align-right");
		tfImporte.addStyleName("align-right");
		
		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);	
		binder.bindMemberFields(this);	
		
	    botonera = new BotoneraDoble();
	    
	    FormLayout hl2 = new FormLayout();
	    hl2.setMargin(true);
	    hl2.addComponent(tfGremio);
	    tfGremio.setEnabled(false);
	    vl.addComponent(hl2);
	    vl.addComponent(hl);

	    FormLayout hl3 = new FormLayout();
	    hl3.setMargin(true);
	    hl3.addComponent(tfOtrosTrabajos);
	    tfOtrosTrabajos.setWidth("100%");
	    tfOtrosTrabajos.setVisible(false);
	    tfOtrosTrabajos.setNullRepresentation("");
	    vl.addComponent(hl3);
	    vl.addComponent(botonera);		
		setContent(vl);
		
		cbItem1.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				//System.out.println("******************> Baremos" + cbItem1.getValue());
				
				// Si escojemos no recogido en Baremo mostramos el campo de texto
				if ( cbItem1.getValue()!=null && cbItem1.getValue().equals("OTR")) {
					tfOtrosTrabajos.setVisible(true);
					tfOtrosTrabajos.setValue(null);
					
					tfImporte.setEnabled(true);
					tfUnidades.setEnabled(true);
				}
				else {
					tfOtrosTrabajos.setVisible(false);
					tfOtrosTrabajos.setValue(null);
					
					tfImporte.setEnabled(false);
					tfUnidades.setEnabled(true);
				}
				
				cbItem2.removeAllItems();
				for (Map map : valoresBaremos) {


					if ( map.get("IDITEMN2").equals(cbItem1.getValue()) ) {
						cbItem2.addItem(map.get("IDITEMN3"));
						cbItem2.setItemCaption(map.get("IDITEMN3"),(String) map.get("DSITEMN3"));						
					}

					
				}
				
			}
		});
		
		cbItem2.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
				tfUnidades.setValue(null);
				tfImporte.setValue(null);
				for (Map map : valoresBaremos) {


					if ( map.get("IDITEMN2").equals(cbItem1.getValue()) &&  map.get("IDITEMN3").equals(cbItem2.getValue())) {
						
						if ( map.get("UNIDADES")!=null) {
						tfUnidades.setValue(map.get("UNIDADES").toString().replace(".", ","));
						}

						if (map.get("IMPORTE")!=null) {
						tfImporte.setValue(map.get("IMPORTE").toString().replace(".", ","));
						}
						
					}

					
				}
				
			}
		});		
		
		botonera.btAceptar.setCaption("Confirmar Datos Item");
		
		
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
					
					// Una vez validamos todo hacemos la modificacion o la inserci�n
					
					if ( acceso.equals("MODIFICAR")) {

					    			WS_AMA llamada = new WS_AMA(service.plsqlDataSource.getConnection());
					    			HashMap respuestaGremios = null;
					    			try {
					    				//pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ, pIDITEM2, pIDITEM3, pESTADO)
					    				
										//System.out.println("Expediente: " + UI.getCurrent().getSession().getAttribute("expediente").toString());
										//System.out.println("prespuesto: " + provDatosPresupuestoExpediente.presupuesto);
										//System.out.println("unidades: " + unidades );
										//System.out.println("importe : " + importe );
										//System.out.println("Estado: " +UI.getCurrent().getSession().getAttribute("estadoExpediente").toString());
										//System.out.println("Trabajos: " +tfOtrosTrabajos.getValue());
				
										String trabajos;
										if ( tfOtrosTrabajos.getValue()==null ) {
											trabajos = null;
										} else trabajos = tfOtrosTrabajos.getValue().toString();
										
					    				/*String pPUSUARIO, String pORIGEN, java.math.BigDecimal pEXPEDIENTE
					    		  		   , java.math.BigDecimal pIDPRESUP, java.math.BigDecimal pIDGREMIO
					    		  		   , String pASEGPERJ, String pIDITEM2, String pIDITEM3, java.math.BigDecimal pUNIDADES
					    		  		   , java.math.BigDecimal pIMPORTE, String pOBSERVACIONES, String pESTADO , String pDANOS*/
					    				respuestaGremios = llamada.ejecutaWS_AMA__MODIFICAR_ITEM_PRESUPUESTO(
					    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					    						UsuarioSave,
					    						UI.getCurrent().getSession().getAttribute("origen").toString(),
					    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					    					    presu,
					    					    gremio.idgremio,
					    					    gremio.asegperf,
					    					    cbItem1.getValue().toString(),
					    					    cbItem2.getValue().toString(),
					    					    new BigDecimal(tfUnidades.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    new BigDecimal(tfImporte.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    trabajos,
					    					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString(),
					    					    cbTipoDano.getValue().toString()
					    						);
					    				
					    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
					    				//System.out.println("Error eliminar item: " + retornoGremios.get("CODIGOERROR") );
					    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
					    					
					    					new Notification("Error al modificar el item",
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
					    							"Item modificado",
					    							Notification.Type.TRAY_NOTIFICATION, true)
					    							.show(Page.getCurrent());
					    					
					
					    					
					    				}
					    				
					    			} catch (Exception e) {
					    				e.printStackTrace();
					    			}	
					    			close();
					}
					else if (acceso.equals("NUEVO") )  {
						
					    			WS_AMA llamada = new WS_AMA(service.plsqlDataSource.getConnection());
					    			//WEB_PROV llamada = new WEB_PROV(service.plsqlDataSource.getConnection());
					    			HashMap respuestaGremios = null;
					    			try {

										String trabajos;
										if ( tfOtrosTrabajos.getValue()==null ) {
											trabajos = null;
										} else trabajos = tfOtrosTrabajos.getValue().toString();
										
					    				respuestaGremios = llamada.ejecutaWS_AMA__MODIFICAR_ITEM_PRESUPUESTO(
					    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					    						UsuarioSave,
					    						UI.getCurrent().getSession().getAttribute("origen").toString(),
					    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					    					    presu,
					    					    gremio.idgremio,
					    					    gremio.asegperf,
					    					    cbItem1.getValue().toString(),
					    					    cbItem2.getValue().toString(),
					    					    new BigDecimal(tfUnidades.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    new BigDecimal(tfImporte.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    trabajos,
					    					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString(),
					    					    cbTipoDano.getValue().toString()
					    						);
										
										// Cambios el modificar por el insert del Web_prov
					    				/*respuestaGremios = llamada.ejecutaWEB_PROV__ANADIR_ITEM_PRESUPUESTO(
					    						//pEXPEDIENTE, pCDPROVEE, pIDPRESUP, 
					    						//pCD_SERVICA, pCDELEMA, pNIVELA,
					    						//pCD_SERVICB, pCDELEMB, pNIVELB, 
					    						//pASEGPERJ, pFINALIZADO, pUNIDADES, 
					    						//pIMPORTE, pOTROSTRABAJOS, pFHINICIO, 
					    						//pDANOS)
					    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					    						UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					    					    presu,
					    					    
					    					    gremio.idgremio,
					    					    cbItem1.getValue().toString(),
					    					    new BigDecimal("2"),
					    					    
					    					    gremio.idgremio,
					    					    cbItem2.getValue().toString(),
					    					    new BigDecimal("3"),
					    					    
					    					    gremio.asegperf,
					    					    "N",
					    					    new BigDecimal(tfUnidades.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    new BigDecimal(tfImporte.getValue().toString().replace(".", "").replace(",",".")) ,
					    					    trabajos,
					    					    new Date().toString(),
					    					    cbTipoDano.getValue().toString()
					    						);					    				
					    				*/
					    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
					    				//System.out.println("Error insertar item: " + retornoGremios.get("CODIGOERROR") );
					    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
					    					
					    					new Notification("Error al insertar el item",
					    							retornoGremios.get("CODIGOERROR").toString() + " - " +
					    							//"ERROR AL INSERTAR ITEM",
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
					    							"Item creado",
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
	public void init( String tipoAcceso, java.math.BigDecimal presupuesto) {

		System.out.println("El tipo de acceso es : " + tipoAcceso);
		tfGremio.setValue(null);
		cbItem1.setValue(null);
		cbItem2.setValue(null);
		cbTipoDano.setValue(null);
		tfUnidades.setValue(null);
		tfImporte.setValue(null);
		acceso = tipoAcceso;
		this.setCaption(tipoAcceso + " ITEM BAREMO");
		presu = presupuesto;

	}
	
	public void modificar( String tipoAcceso, String pitem2, String pitem3, String tipoDano, 
			String punidades, String pimporte, java.math.BigDecimal presupuesto ) {

		tfGremio.setValue(gremio.txgremio);
		cbItem1.setValue(pitem2);
		cbItem2.setValue(pitem3);
		cbTipoDano.setValue(tipoDano);
		
		//System.out.println(" Importe: " + pimporte);
		/*tfUnidades.setValue(punidades.toString().replace(".", ","));
		tfImporte.setValue(pimporte.toString().replace(".", ","));*/
		tfUnidades.setValue(punidades);
		tfImporte.setValue(pimporte);
		
		acceso = tipoAcceso;
		presu = presupuesto;
		item2 = pitem2;
		item3 = pitem3;
		
		
		unidades = new BigDecimal(punidades);
		importe = new BigDecimal(pimporte);
		this.setCaption(tipoAcceso + " ITEM BAREMO");

	}
	

	
	/*@Override
	public void close() {
		// TODO Auto-generated method stub
		//super.close();
		//System.out.println("Cerramos la ventana de anular");
		UI.getCurrent().removeWindow(this);
		
	}*/

}