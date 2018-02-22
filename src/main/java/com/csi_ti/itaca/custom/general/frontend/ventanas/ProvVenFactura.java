package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.csi_ti.itaca.custom.general.frontend.ProvDatosDetalleExpediente;
import com.csi_ti.itaca.custom.general.frontend.ProvDatosPresupuestoExpediente;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_LISTAS;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WEB_PROV;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
public class ProvVenFactura extends Window   {

	GridLayout fm = new GridLayout(4,10	);
	GridLayout fmFactura = new GridLayout(4,4	);
	
	private static GeneralBusinessServiceImpl service;
	
	public String UsuarioSave;

	
	private String ivaReducido;
	
	java.math.BigDecimal presupuesto;
	java.math.BigDecimal facturaDestino;
	
	PropertysetItem item;
	
	BotoneraDoble botonera;

	private static final long serialVersionUID = 1L;

	private FieldGroup binder;
	String exp = "^[0-9]+(\\,([0-9]{1,2})?)?$";
	
	public ProvDatosDetalleExpediente provDatosDetalleExpediente;
	public ProvDatosPresupuestoExpediente provDatosPresupuestoExpediente;
	
	// Ponemos los campos
	// Campos del titular para cartera o propuesta
	public ComboBox cbIvaReducido= new ComboBox();
	public ComboBox cbEsComunidad= new ComboBox();
	public ComboBox cbEsContruida= new ComboBox();
	public ComboBox cbCoste= new ComboBox();
	
	public TextField txDestinatario = new TextField();
	public DateField tfFechaFactura = new DateField();
	public TextField txNumeroFactura = new TextField();
	public TextField txBaseImponible = new TextField();
	public TextField txRetencion = new TextField();
	public TextField txImpuestoPorcentaje = new TextField();
	public TextField txImpuestoImporte = new TextField();
	public TextField txTotal = new TextField();
	
	Label lblDestinatario = new Label("Destinatario:");
	Label lblFechaFactura = new Label("Fecha Factura:");
	Label lblNumeroFactura = new Label("Número Factura:");
	Label lblBaseImponible = new Label("Base Imponible:");
	Label lblRetencion = new Label("Retención:");
	Label lblImpuesto= new Label();
	Label lblTotal = new Label("Total:");
	
	Label lblIvaReducido = new Label("¿Deseas aplicar IVA reducido?");
	Label lblEsComunidad = new Label("¿Es una comunidad de propietarios o una vivienda particular?");
    Label lblEsConstruida = new Label("¿La vivienda se ha construido o reformado en un periodo inferior a dos años?");
    Label lblCoste = new Label("¿El coste del material supera el 40% de la base imponible de la reparación?");
    
    Button btAplicarCambios = new Button("Aplicar Cambios");

	
	public ProvVenFactura( ProvDatosPresupuestoExpediente provDatosPresupuestoExpediente ) {
		
		UsuarioSave = provDatosPresupuestoExpediente.UsuarioSave;

		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		setWidth("60%");

		setClosable(true);
		setResizable(false);
		setStyleName("ventanamodal");

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);

		
		fm.setMargin(true);
		fm.setSpacing(true);
		fmFactura.setMargin(true);
		fmFactura.setSpacing(true);
		fmFactura.setWidth("100%");
		fmFactura.setColumnExpandRatio(0, 30);
		fmFactura.setColumnExpandRatio(1, 40);
		fmFactura.setColumnExpandRatio(2, 30);
		fmFactura.setColumnExpandRatio(3, 10);
		
		fm.addComponent(lblIvaReducido,0,0);
		fm.addComponent(cbIvaReducido,1,0);
		fm.addComponent(lblEsComunidad,0,1);
	    fm.addComponent(cbEsComunidad,1,1);
	    fm.addComponent(lblEsConstruida,0,2);
	    fm.addComponent(cbEsContruida,1,2);
	    fm.addComponent(lblCoste,0,3);
	    fm.addComponent(cbCoste,1,3);
	    
	    fm.addComponent(btAplicarCambios,1,4);
	    fm.setComponentAlignment(btAplicarCambios, Alignment.MIDDLE_CENTER);
	    btAplicarCambios.setStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    btAplicarCambios.setVisible(false);
	    

		
	    fmFactura.addComponent(lblDestinatario,0,0);
	    fmFactura.addComponent(txDestinatario,1,0);
	    txDestinatario.setWidth("100%");
	    fmFactura.addComponent(lblFechaFactura,0,1);
	    fmFactura.addComponent(tfFechaFactura,1,1);
	    fmFactura.addComponent(lblNumeroFactura,0,2);
	    fmFactura.addComponent(txNumeroFactura,1,2);
	    
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, new Date().getYear());
	    cal.set(Calendar.DAY_OF_YEAR, 1);
	    cal.set(Calendar.MONTH, 1);
	    Date start = cal.getTime();

	    
	    tfFechaFactura.setDateFormat("dd/MM/yyyy");
	    tfFechaFactura.setResolution(Resolution.MINUTE);
	    tfFechaFactura.setRangeStart(start);
	    tfFechaFactura.setRangeEnd(new Date());
	    tfFechaFactura.setParseErrorMessage("Fecha incorrecta");
	    tfFechaFactura.setWidth("100px");
	    
	    fmFactura.addComponent(lblBaseImponible,2,0);
	    fmFactura.addComponent(txBaseImponible,3,0);
	    fmFactura.addComponent(lblRetencion,2,1);
	    fmFactura.addComponent(txRetencion,3,1);
	    fmFactura.addComponent(lblImpuesto,2,2);
	    fmFactura.addComponent(txImpuestoImporte,3,2);
	    fmFactura.addComponent(lblTotal,2,3);
	    fmFactura.addComponent(txTotal,3,3);
	    
	    txDestinatario.setEnabled(false);
	    txBaseImponible.setEnabled(false);
	    txRetencion.setEnabled(false);
	    txImpuestoImporte.setEnabled(false);
	    txTotal.setEnabled(false);
	    
	    
	    
	    cbIvaReducido.addItem("S");
	    cbIvaReducido.setItemCaption("S","SI");
	    cbIvaReducido.addItem("N");
	    cbIvaReducido.setItemCaption("N","NO");
	    cbIvaReducido.setWidth("50px");
	    
	    cbEsComunidad.addItem("S");
	    cbEsComunidad.setItemCaption("S","SI");
	    cbEsComunidad.addItem("N");
	    cbEsComunidad.setItemCaption("N","NO");
	    cbEsComunidad.setWidth("50px");
	    cbEsComunidad.setWidth("50px");
	    
	    cbEsContruida.addItem("S");
	    cbEsContruida.setItemCaption("S","SI");
	    cbEsContruida.addItem("N");
	    cbEsContruida.setItemCaption("N","NO");
	    cbEsContruida.setWidth("50px");
	    
	    cbCoste.addItem("S");
	    cbCoste.setItemCaption("S","SI");
	    cbCoste.addItem("N");
	    cbCoste.setItemCaption("N","NO");	    
	    cbCoste.setWidth("50px");
	    
	    

	    vl.addComponent(fm);
	    vl.addComponent(new HorizontalRule());
	    vl.addComponent(fmFactura);
	    vl.addComponent(new HorizontalRule());
	    fmFactura.setVisible(false);

		
		item = new PropertysetItem();
		item.addItemProperty("cbIvaReducido", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbEsComunidad", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbEsContruida", new ObjectProperty<String>(null, String.class));
		item.addItemProperty("cbCoste", new ObjectProperty<String>(null, String.class));

		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);

		//cbIvaReducido.setRequired(true);
		cbIvaReducido.setValidationVisible(true);
		cbIvaReducido.setRequiredError("Campo obligatorio");
		cbIvaReducido.setValue("N");
		cbIvaReducido.setNullSelectionAllowed(false);
		cbEsComunidad.setValue("N");
		cbEsComunidad.setNullSelectionAllowed(false);
		cbEsContruida.setValue("N");
		cbEsContruida.setNullSelectionAllowed(false);
		cbCoste.setValue("N");
		cbCoste.setNullSelectionAllowed(false);
		
		cbEsComunidad.setVisible(false);
		cbEsContruida.setVisible(false);
		cbCoste.setVisible(false);
		lblEsComunidad.setVisible(false);
		lblEsConstruida.setVisible(false);
		lblCoste.setVisible(false);
		
		txNumeroFactura.setRequired(true);
		txNumeroFactura.setValidationVisible(true);
		txNumeroFactura.setRequiredError("Número de factura obligatorio");
		
		tfFechaFactura.setRequired(true);
		tfFechaFactura.setValidationVisible(true);
		tfFechaFactura.setRequiredError("Fecha factura obligatoria");		
		
		btAplicarCambios.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				fmFactura.setVisible(true);
				btAplicarCambios.setVisible(false);
				datosFactura();
				botonera.btAceptar.setEnabled(true);
				
				
			}
		});
		

		cbEsComunidad.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				fmFactura.setVisible(false);
				btAplicarCambios.setVisible(true);
				botonera.btAceptar.setEnabled(false);
			}
		});
		
		cbEsContruida.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				fmFactura.setVisible(false);
				btAplicarCambios.setVisible(true);
				botonera.btAceptar.setEnabled(false);
			}
		});
		
		cbCoste.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				fmFactura.setVisible(false);
				btAplicarCambios.setVisible(true);
			}
		});		

		cbIvaReducido.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if ( cbIvaReducido.getValue().equals("S")) {
					cbEsComunidad.setVisible(true);
					cbEsContruida.setVisible(true);
					cbCoste.setVisible(true);
					lblEsComunidad.setVisible(true);
					lblEsConstruida.setVisible(true);
					lblCoste.setVisible(true);
					btAplicarCambios.setVisible(true);
					fmFactura.setVisible(false);
					botonera.btAceptar.setEnabled(false);
				} else {
					cbEsComunidad.setVisible(false);
					cbEsContruida.setVisible(false);
					cbCoste.setVisible(false);
					lblEsComunidad.setVisible(false);
					lblEsConstruida.setVisible(false);
					lblCoste.setVisible(false);
					btAplicarCambios.setVisible(true);					
					fmFactura.setVisible(false);
					botonera.btAceptar.setEnabled(false);
				}
				
				
				
			}
		});

		
		/*tfPrevista.setRequired(true);
		tfPrevista.setValidationVisible(true);
		tfPrevista.setRequiredError("Campo obligatorio Fecha Prevista");*/

		
	    botonera = new BotoneraDoble();
	    vl.addComponent(botonera);		
		setContent(vl);
		
		botonera.btAceptar.setEnabled(false);

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
					String mensaje;
					
					
					if ( tfFechaFactura.getValue()==null || txNumeroFactura.getValue()==null
							|| txNumeroFactura.getValue().trim().length()== 0) {
					
							new Notification("Se han detectado errores",
									"Rellene todos los campos obligatorios",
									Notification.Type.TRAY_NOTIFICATION, true)
									.show(Page.getCurrent());
							
							return;
					}
					
					// LLamamos a generar la factura
					WEB_PROV llamada = new WEB_PROV(service.plsqlDataSource.getConnection());
					HashMap respuesta = null;
					
					
					// Miramos si existe ya la factura o no
					try {
						/*    existe_num_factura (p_cdprovee      IN       VARCHAR2,
					      p_num_factura   IN       VARCHAR2,
					      p_respuesta     OUT      t_refcursor,
					      p_out_error     OUT      NUMBER
					      */
						respuesta = llamada.ejecutaWEB_PROV__EXISTE_NUM_FACTURA(
								//UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", ""),
								UsuarioSave.toUpperCase().replace("PROV_", ""),
								txNumeroFactura.getValue().toString()
								);
						
						Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
	    				if ( !retorno.get("CODIGOERROR").toString().equals("0") ) {
	    					
	    					new Notification("Error al verificar el numero factura",
	    							retorno.get("CODIGOERROR").toString() + " - Error",
	    							Notification.Type.ERROR_MESSAGE, true)
	    							.show(Page.getCurrent());
	    					return;
	    				}
	    				else {
	    					ArrayList res = (ArrayList) retorno.get("REGISTROS");
	    					Map<String, String> m = (Map<String, String>) res.get(0);
	    					if ( m.get("RESPUESTA").equals("S") ) {
	    						
	        					new Notification("Error al verificar el numero factura",
		    							"Ya existe ese número de factura",
		    							Notification.Type.ERROR_MESSAGE, true)
		    							.show(Page.getCurrent());
	        					return;
	    					}
	    				}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

					WEB_PROV llamadaFac = new WEB_PROV(service.plsqlDataSource.getConnection());
					respuesta = null;
					try {
						
		            	/*Date Fecha = new java.sql.Date(tfFechaFactura.getValue().getTime());

						//
						respuesta = llamada.ejecutaWEB_PROV__GENERAR_FACTURA_PAGO(
								new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
								UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", ""),
								new BigDecimal("1"),
								presupuesto,
								txNumeroFactura.getValue().toString(),
								Fecha,
								facturaDestino
								);*/
						
					   /*   p_expediente   IN       NUMBER,
					      p_cdprovee     IN       VARCHAR2,
					      p_numfactur1   IN       VARCHAR2,
					      p_numfactur2   IN       VARCHAR2,
					      p_idpresup     IN       NUMBER,
					      p_fhfactura1   IN       VARCHAR2,
					      p_fhfactura2   IN       VARCHAR2,
					      p_destino1     IN       NUMBER,
					      p_destino2     IN       NUMBER,
					      p_respuestas   IN       VARCHAR2,
					      p_out_error    OUT      NUMBER
					   )	*/
						
						String cadena = "";
						if ( cbIvaReducido.getValue().equals("S")) {
							cadena += "<ATRIB>1";
						} else if ( cbIvaReducido.getValue().equals("N")) {
							cadena += "<ATRIB>2";
						}
						if ( cbEsComunidad.getValue().equals("S")) {
							cadena += "<ATRIB>1";
						} else if ( cbEsComunidad.getValue().equals("N")) {
							cadena += "<ATRIB>2";
						}
						if ( cbEsContruida.getValue().equals("S")) {
							cadena += "<ATRIB>1";
						} else if ( cbEsContruida.getValue().equals("N")) {
							cadena += "<ATRIB>2";
						}
						

				    	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy"); 
				        String fecha = sdfDate.format(tfFechaFactura.getValue());
				    							
						
						respuesta = llamadaFac.ejecutaWEB_PROV__GENERAR_FACTURAS(
								new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString() + "1"),
								//UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", ""),
								UsuarioSave.toUpperCase().replace("PROV_", ""),
								txNumeroFactura.getValue().toString(),
								null,
								presupuesto,
								fecha,
								null,
								facturaDestino,
								null,
								cadena
								
								);						
						
						Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
	    				if ( !retorno.get("CODIGOERROR").toString().equals("0") ) {
	    					
	    					// Vamos a ver el ultimo error que ha insertado
	    					
	    					PAC_SHWEB_LISTAS llamadaquery = new PAC_SHWEB_LISTAS(service.plsqlDataSource.getConnection());
	    					
	    					String sql = "SELECT TXTERROR FROM uah_inci_fact_web "
	    							+ " WHERE CDASISTE = " + UI.getCurrent().getSession().getAttribute("expediente").toString()
	    							//+ " AND CDPROVEE = " + UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", "")
	    							+ " AND CDPROVEE = " + UsuarioSave.toUpperCase().replace("PROV_", "")
	    							+ " AND FECHA = ( SELECT MAX(FECHA) FROM uah_inci_fact_web X WHERE X.CDASISTE = uah_inci_fact_web.CDASISTE "  
	    							+ " AND X.CDPROVEE = uah_inci_fact_web.CDPROVEE) ";
	    							
	    					
	    					respuesta = llamadaquery.ejecutaPAC_SHWEB_LISTAS__F_QUERY(sql) ;
	    						
   					
	    					
	    					new Notification("Error al generar la factura",
	    							respuesta.toString().substring(respuesta.toString().indexOf("TXTERROR=")+9) + " - Error",
	    							Notification.Type.ERROR_MESSAGE, true)
	    							.show(Page.getCurrent());
	    				}
	    				else {
	
	    		
	    					new Notification("Proceso finalizado correctamente",
	    							"Factura generada",
	    							Notification.Type.TRAY_NOTIFICATION, true)
	    							.show(Page.getCurrent());
	    					
	    					
	    					provDatosDetalleExpediente.btCerrarExpediente.setVisible(false);
							provDatosDetalleExpediente.recargarDatos();
							provDatosPresupuestoExpediente.btFactura.setVisible(false);
							provDatosPresupuestoExpediente.btAnadirGremio.setVisible(false);
							
    	                	close();	    		    					
	    					
	
	    					
	    				}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}					
					
					

				} catch (Exception e) {

			
		            
					new Notification("Error no controlado",
							"Error no controlado",
							Notification.Type.TRAY_NOTIFICATION, true)
							.show(Page.getCurrent());

				} 
				
			}
		});	
		
		// Ocultamos los campos
		
	
		


		
		
	
		
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

	//
	public void init( java.math.BigDecimal pPRESUPUESTO, ProvDatosDetalleExpediente provDatosDetalleExpedienteCopy,
			ProvDatosPresupuestoExpediente provDatosPresupuestoExpedienteCopy) {
		
		provDatosPresupuestoExpediente = provDatosPresupuestoExpedienteCopy;
		provDatosDetalleExpediente = provDatosDetalleExpedienteCopy;

		// Recuperamos el estado del expediente
		System.out.println("Init facturas");
		presupuesto = pPRESUPUESTO;
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
			UI.getCurrent().getSession().setAttribute("tit.estadoexp",retorno.get("ESTADO").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// Miramos si tiene iva reducido
		WEB_PROV llamadaIva = null;
		try {
			llamadaIva = new WEB_PROV(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaIva = null;
		try {
			respuestaIva = llamadaIva.ejecutaWEB_PROV__EXISTE_IVA_REDUCIDO(
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					//UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", ""),
					UsuarioSave.toUpperCase().replace("PROV_", ""),
					pPRESUPUESTO,
					null
					);
			
			Map<String, Object> retornoIva = new HashMap<String, Object>(respuestaIva);
			ArrayList resIva = (ArrayList) retornoIva.get("REGISTROS");
			Map<String, String> m = (Map<String, String>) resIva.get(0);
			ivaReducido = m.get("RES");
			//ivaReducido = "N";
			cbIvaReducido.setValue(ivaReducido);
			
			if ( cbIvaReducido.getValue().equals("S")) {
				
				cbIvaReducido.setValue("N");
				lblEsComunidad.setVisible(false);
				lblEsConstruida.setVisible(false);
				lblCoste.setVisible(false);
				cbEsComunidad.setVisible(false);
				cbEsContruida.setVisible(false);
				cbCoste.setVisible(false);	
				cbIvaReducido.setEnabled(true);
				btAplicarCambios.setVisible(true);
				botonera.btAceptar.setEnabled(false);
				fmFactura.setVisible(false);
				

			} else { // N

				
				cbIvaReducido.setEnabled(false);
				lblEsComunidad.setVisible(false);
				lblEsConstruida.setVisible(false);
				lblCoste.setVisible(false);
				
				cbEsComunidad.setVisible(false);
				cbEsContruida.setVisible(false);
				cbCoste.setVisible(false);
				cbEsComunidad.setValue("N");
				cbEsContruida.setValue("N");
				cbCoste.setValue("N");
				fmFactura.setVisible(true);
				btAplicarCambios.setVisible(false);
				botonera.btAceptar.setEnabled(true);
				fmFactura.setVisible(true);
				datosFactura();
								
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		
	}
	
	private void datosFactura() {
		// Obtenemos los datos de la factura
		
		//NumFacturas: <ATRIB>1<ATRIB>1<ATRIB>1<ATRIB>2
		String cadena = "";
		if ( cbIvaReducido.getValue().equals("S")) {
			cadena += "<ATRIB>1";
		} else if ( cbIvaReducido.getValue().equals("N")) {
			cadena += "<ATRIB>2";
		}
		if ( cbEsComunidad.getValue().equals("S")) {
			cadena += "<ATRIB>1";
		} else if ( cbEsComunidad.getValue().equals("N")) {
			cadena += "<ATRIB>2";
		}
		if ( cbEsContruida.getValue().equals("S")) {
			cadena += "<ATRIB>1";
		} else if ( cbEsContruida.getValue().equals("N")) {
			cadena += "<ATRIB>2";
		}	
		if ( cbCoste.getValue().equals("S")) {
			cadena += "<ATRIB>1";
		} else if ( cbCoste.getValue().equals("N")) {
			cadena += "<ATRIB>2";
		}			

		System.out.println("Cadena:"+ cadena);
		
		WEB_PROV llamadaIva = null;
		try {
			llamadaIva = new WEB_PROV(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaIva = null;
		try {
			respuestaIva = llamadaIva.ejecutaWEB_PROV__OBTENER_NUM_FACTURAS(
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					//UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", ""),
					UsuarioSave.toUpperCase().replace("PROV_", ""),
					presupuesto,
					cadena
					);
			
			Map<String, Object> retornoIva = new HashMap<String, Object>(respuestaIva);
			ArrayList resIva = (ArrayList) retornoIva.get("REGISTROS");
			Map<String, String> m = (Map<String, String>) resIva.get(0);

			/*2..{RET=0, TOTAL=74.66, IMPUESTO=12.96, 
			 * DESTINO=SINIESTRALIDAD PROPIA (CIA), 
			 * TIPO_IVA=21, 
			 * SP=N, 
			 * FACTURA=3, 
			 * BASE=61.7}], CODIGOERROR=0}

			*/
			ivaReducido = m.get("RES");
			
			txDestinatario.setValue(m.get("DESTINO"));
			txBaseImponible.setValue(String.valueOf(m.get("BASE")));
			txImpuestoImporte.setValue(String.valueOf(m.get("IMPUESTO")));
			lblImpuesto.setCaption("Impuesto (" + String.valueOf(m.get("TIPO_IVA")) + "%):" );
			txRetencion.setValue(String.valueOf(m.get("RET")));
			txTotal.setValue(String.valueOf(m.get("TOTAL")));

			facturaDestino = new BigDecimal(String.valueOf(m.get("FACTURA")));
			
			txImpuestoImporte.setStyleName("numero_derecha");
			txBaseImponible.setStyleName("numero_derecha");
			txRetencion.setStyleName("numero_derecha");
			txTotal.setStyleName("numero_derecha");
			
			

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	

}