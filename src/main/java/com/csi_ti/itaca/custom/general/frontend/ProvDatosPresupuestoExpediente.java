package com.csi_ti.itaca.custom.general.frontend;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenEditarGremio;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenEditarItemBaremo;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenFactura;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WEB_PROV;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
public class ProvDatosPresupuestoExpediente extends Panel implements ItacaView {

    
	public String UsuarioSave;

	
	ProvVenEditarItemBaremo  provVenEditarItemBaremo = new ProvVenEditarItemBaremo(this);
	ProvVenEditarGremio provVenEditarGremio = new ProvVenEditarGremio(this);
	ProvVenFactura provVenFactura = new ProvVenFactura( this);
	
    Label lbIdPresupuesto = new Label();
    private static GeneralBusinessServiceImpl service;

    ProvDatosDetalleExpediente provDatosDetalleExpediente;
    
    String todosFinalizados = "N";
    
    public BigDecimal presupuesto;
    public BigDecimal totalPresupuesto = new BigDecimal("0");
    
    public class Gremios {
    	public int gremioContador;
    	public GridLayout cabeceraGremio = new GridLayout(10,1);
    	public Table tbBaremos;
    	public TextField tfImporte = new TextField();
    	public String asegperf;
    	public String finalizado;
    	public DateField fecha = new DateField();
    	public BigDecimal idgremio;
    	public String txgremio;
    	Button btNuevoItemBaremo = new Button("Nuevo Item");
    	Button btBorrarGremio= new Button("Borrar Gremio");
    	Button btModificarGremio= new Button("Finalizar Gremio");
    	
    }
    
    public Button btAnadirGremio = new Button("Añadir Gremio");
    TextArea taObs = new TextArea("Trabajos no recogidos en Baremo");
    Button btAnadirPresupuesto = new Button("Nuevo Presupuesto");
    Label lbTotalPresupuesto  = new Label ("Importe total presupuesto: ");
    public Button btFactura = new Button("Factura");
    
    List<Gremios> listaGremios = new ArrayList<Gremios>();
    public GridLayout dtitLayout;

    int contador = 0;
    
    
	private static final long serialVersionUID = -304344441663815443L;

	// constructor inicial
	public ProvDatosPresupuestoExpediente( Map<String, Object> retorno, ProvDatosDetalleExpediente provDatosDetalleExpedienteCopy, String pusuario ) {
	
		UsuarioSave = pusuario;
		provDatosDetalleExpediente = provDatosDetalleExpedienteCopy;
		System.out.println("*********************" + pusuario);
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		taObs.setVisible(false);
		Cargar_Pantalla( retorno, pusuario);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		System.out.println(UsuarioSave + " Enter provDatosPresupuestoExpediente " + provDatosDetalleExpediente.UsuarioSave);
		UsuarioSave = provDatosDetalleExpediente.UsuarioSave;

		
	}

	
	public void Cargar_Datos() {
		
		
		totalPresupuesto = new BigDecimal("0");
		
		
		
		// Recuperamos el presupuesto
		WS_AMA llamadaPresupuesto = null;
		try {
			llamadaPresupuesto = new WS_AMA(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaPresupuesto = null;

		taObs.setVisible(false);
		
		try {
			respuestaPresupuesto = llamadaPresupuesto.ejecutaWS_AMA__CABECERA_PRESUPUESTO(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					null
					);
			
			Map<String, Object> retornoPresupuesto = new HashMap<String, Object>(respuestaPresupuesto);
			Cargar_Pantalla( retornoPresupuesto, UsuarioSave);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void Cargar_Pantalla( Map<String, Object> retorno, String pUsuario ) {
		
		// TAB TITULAR
		
		todosFinalizados = "X";
		
		totalPresupuesto = new BigDecimal("0");
		
		dtitLayout = new GridLayout(1,2);
		dtitLayout.setStyleName("layout-presupuestos");


		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha;
		Date date = null;
		List<Map> valor = (List<Map>) retorno.get("REGISTROS");
		
		// Llenamos la lista del gremio
		WEB_PROV llamadaGremio = null;
		try {
			llamadaGremio = new WEB_PROV(service.plsqlDataSource.getConnection());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HashMap respuestaGremios = null;
		System.out.println("Llamamos a obtener gremios con usuario: " + UsuarioSave + " pUsuario: " + pUsuario);
		try {
			//respuestaGremios = llamadaGremio.ejecutaWS_AMA__MAESTRO_GREMIOS(
			respuestaGremios = llamadaGremio.ejecutaWEB_PROV__OBTENER_GREMIOS(
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					//UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().replace("PROV_", "")
					pUsuario.replace("PROV_", "")
					);
			
			Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
			List<Map> valorGremios = (List<Map>) retornoGremios.get("GREMIOS");
			System.out.println("Llamamos a obtener gremios con usuario: " + UsuarioSave);

			provVenEditarGremio.cbGremio.removeAllItems();
			for (Map map : valorGremios) {
				provVenEditarGremio.cbGremio.addItem(map.get("CDELEM_BAREMO"));
				provVenEditarGremio.cbGremio.setItemCaption(map.get("CDELEM_BAREMO"),(String) map.get("TXELEM_BAREMO"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		//System.out.println("EL RETORNO************>" + retorno);
		// Fin lista Gremios		
		if ( retorno.get("IDPRESU")!=null) {
			//System.out.println("Despues de idpresu" + retorno.get("IDPRESU"));
			setCaption("Presupuesto: " + retorno.get("IDPRESU").toString() + " Estado: " + retorno.get("ESTADOPRESU").toString() );
			presupuesto = new BigDecimal(retorno.get("IDPRESU").toString());
			btAnadirGremio.setVisible(true);
			btAnadirPresupuesto.setVisible(false);
			lbIdPresupuesto.setVisible(true);
			if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("PFA")) {
				btFactura.setVisible(true);
			} else {
				btFactura.setVisible(false);
			}

		}
		else {
			System.out.println("No encontrado el presu");
			setCaption("No hay ningún presupuesto");
			
			btAnadirGremio.setVisible(true);
			btAnadirPresupuesto.setVisible(false);
			btFactura.setVisible(false);
			presupuesto = null;
			lbIdPresupuesto.setVisible(false);
		}
		
		if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("VIS")
				//|| UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("CER")
				|| UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("CON")
				|| UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")
				|| UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("REC")
				) {
			btAnadirGremio.setVisible(false);
		}
		
		
		if (valor!=null ) {
			for (Map map : valor) {
				
				
				Gremios gremios = new Gremios();
				gremios.gremioContador = contador;
				
				gremios.cabeceraGremio.setStyleName("cabecera-gremio");
				gremios.cabeceraGremio.setWidth("100%");
				gremios.cabeceraGremio.addComponent(new Label("Gremio:"),0,0);
				gremios.cabeceraGremio.addComponent(new Label(map.get("DSGREMIO").toString()),1,0);
				gremios.cabeceraGremio.addComponent(new Label("Finalizado:"),2,0);
				gremios.cabeceraGremio.addComponent(new Label(map.get("FINALIZADO").toString()),3,0);
				gremios.finalizado=map.get("FINALIZADO").toString();
				gremios.cabeceraGremio.addComponent(new Label("Fecha:"),4,0);
				
				if ( gremios.finalizado.equals("S") && todosFinalizados.equals("X" )) {
					todosFinalizados = "S";
				} else if ( gremios.finalizado.equals("N")  ){
					todosFinalizados = "N";
				}
				

				if ( map.get("FHGREMIO")!=null) {
					SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy"); 
			        Date fgremio;
					try {
						fgremio = sdfDate.parse(map.get("FHGREMIO").toString());
						
						gremios.fecha.setValue(fgremio);
						gremios.cabeceraGremio.addComponent(new Label(sdfDate.format(fgremio).toString()),5,0);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				gremios.cabeceraGremio.addComponent(gremios.btBorrarGremio,7,0);
				gremios.cabeceraGremio.addComponent(gremios.btModificarGremio,8,0);
				gremios.cabeceraGremio.addComponent(gremios.btNuevoItemBaremo,9,0);
				
				if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")) {
					gremios.btNuevoItemBaremo.setVisible(false);
					gremios.btBorrarGremio.setVisible(false);
					gremios.btModificarGremio.setVisible(false);
				}								
				

				gremios.btBorrarGremio.setStyleName(ValoTheme.BUTTON_DANGER);
				gremios.btModificarGremio.setStyleName(ValoTheme.BUTTON_FRIENDLY);
				gremios.btNuevoItemBaremo.setStyleName(ValoTheme.BUTTON_PRIMARY);
				gremios.asegperf = map.get("ASEGPERJ").toString();
				gremios.idgremio = (BigDecimal) map.get("IDGREMIO");
				gremios.txgremio = map.get("DSGREMIO").toString();
				if ( gremios.asegperf.toString().equals("A")) {
					gremios.cabeceraGremio.addComponent(new Label("Asegurado"),6,0);
				}
				else if ( gremios.asegperf.toString().equals("P")) {
					gremios.cabeceraGremio.addComponent(new Label("Perjudicado"),6,0);
				}
				listaGremios.add(contador, gremios);
				dtitLayout.addComponent(listaGremios.get(contador).cabeceraGremio);
				
				
				gremios.btModificarGremio.addClickListener(new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							// TODO Auto-generated method stub
							ConfirmDialog.Factory df = new DefaultConfirmDialogFactory() {

								
							    @Override
								public ConfirmDialog create(String caption, String message, String okCaption,
										String cancelCaption, String notOkCaption) {

							        ConfirmDialog  d = super.create(caption,message,okCaption, cancelCaption, notOkCaption
							               );

							        // Change the order of buttons
							        Button ok = d.getOkButton();
							        HorizontalLayout buttons = (HorizontalLayout) ok.getParent();
							        buttons.removeComponent(ok);
							        buttons.addComponent(ok,1);
							        
							        buttons.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

							        return d;
							    }

							};
							ConfirmDialog.setFactory(df);		    	
					    	
					    	ConfirmDialog.show(UI.getCurrent(), "Confirmación", "¿Seguro que desea finalizar el Gremio? \n" +
					    	"Gremio:" + gremios.txgremio,
					    	        "Si", "No", new ConfirmDialog.Listener() {

							private static final long serialVersionUID = 1L;
							
							@Override
							public void onClose(ConfirmDialog dialog) {
		    	                if (dialog.isConfirmed()) {

		    	                	WS_AMA llamada = null;
									try {
										llamada = new WS_AMA(service.plsqlDataSource.getConnection());
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
		    		    			HashMap respuestaGremios = null;
		    		    			try {


		    		    		    	// pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ, pESTADO)
		    		    				respuestaGremios = llamada.ejecutaWS_AMA__FINALIZAR_GREMIO_PRESUPUESTO(
		    		    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
		    		    						UsuarioSave,
		    		    						UI.getCurrent().getSession().getAttribute("origen").toString(),
		    		    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
		    		    						new BigDecimal(retorno.get("IDPRESU").toString()),
		    		    					    new BigDecimal(gremios.idgremio.toString()),
		    		    					    gremios.asegperf
		    		    						);
		    		    				
		    		    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
		    		    				//System.out.println("Error FINALIZAR gremio: " + retornoGremios.get("CODIGOERROR") );
		    		    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
		    		    					
		    		    					new Notification("Error al finalizar el gremio",
		    		    							retornoGremios.get("CODIGOERROR").toString() + " - " +
		    		    							retornoGremios.get("TEXTOERROR").toString(),
		    		    							Notification.Type.ERROR_MESSAGE, true)
		    		    							.show(Page.getCurrent());
		    		    				}
		    		    				else {
		    		
		    		    					// Borramos la fila
		    		    					//gremios.tbBaremos.removeItem(gremios.tbBaremos.getValue());

		    		    					new Notification("Proceso finalizado correctamente",
		    		    							"Gremio finalizado",
		    		    							Notification.Type.TRAY_NOTIFICATION, true)
		    		    							.show(Page.getCurrent());
		    		    					
		    		    					ProvDatosPresupuestoExpediente.this.removeFromParent(dtitLayout);
		    		    					Cargar_Datos();
				    	                	//Cargar_Pantalla(retorno);	    		    					
		    		    					
		    		
		    		    					
		    		    				}
		    		    				
		    		    			} catch (Exception e) {
		    		    				e.printStackTrace();
		    		    			}	

		    	                } else {
		    	                    // User did not confirm
		    	                	
		    	                	
		    	                    
		    	                }
		    	            }

				
		    	        });
					    	
					    		
						}
				});

				
				gremios.btNuevoItemBaremo.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						provVenEditarItemBaremo.init("NUEVO", presupuesto);
						
						System.out.println("Editar item Baremo 2: " + UsuarioSave);
						provVenEditarItemBaremo.UsuarioSave = UsuarioSave;
						provVenEditarItemBaremo.gremio = gremios;
												

						// Llenamos la lista del gremio
						WS_AMA llamada = null;
						try {
							llamada = new WS_AMA(service.plsqlDataSource.getConnection());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						HashMap respuestaBaremos = null;
						try {
							//pPUSUARIO, pORIGEN, pIDCLIENTE, pIDCONTRATO, pTPBAREMO, pIDGREMIO, pIDITEM2, pIDITEM3, pFECHA, pNUMPAG)
							

							respuestaBaremos = llamada.ejecutaWS_AMA__MAESTRO_BAREMOS(
									//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
									UsuarioSave,
									UI.getCurrent().getSession().getAttribute("origen").toString(),
									new BigDecimal(UI.getCurrent().getSession().getAttribute("cliente").toString()),
									new BigDecimal(UI.getCurrent().getSession().getAttribute("contrato").toString()),
									null,
									gremios.idgremio,
									null,
									null,
									null,
									null
									
									);
							
							Map<String, Object> retornoBaremos = new HashMap<String, Object>(respuestaBaremos);
							List<Map> valorBaremos = (List<Map>) retornoBaremos.get("BAREMOS");
							provVenEditarItemBaremo.cbItem1.removeAllItems();
							provVenEditarItemBaremo.cbItem2.removeAllItems();
							provVenEditarItemBaremo.idgremio = gremios.idgremio;
							provVenEditarItemBaremo.tfGremio.setValue(gremios.txgremio);
							
							for (Map map : valorBaremos) {
								provVenEditarItemBaremo.cbItem1.addItem(map.get("IDITEMN2"));
								provVenEditarItemBaremo.cbItem1.setItemCaption(map.get("IDITEMN2"),(String) map.get("DSITEMN2"));
								
							}
							provVenEditarItemBaremo.valoresBaremos = valorBaremos;
							
						} catch (Exception e) {
							e.printStackTrace();
						}	
						// Fin lista Gremios
						System.out.println("EditarGremio " + UsuarioSave);
						provVenEditarGremio.UsuarioSave = UsuarioSave;
						 
						UI.getCurrent().addWindow(provVenEditarItemBaremo);
					}
				});
				
				gremios.btBorrarGremio.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						ConfirmDialog.Factory df = new DefaultConfirmDialogFactory() {

							
						    @Override
							public ConfirmDialog create(String caption, String message, String okCaption,
									String cancelCaption, String notOkCaption) {

						        ConfirmDialog  d = super.create(caption,message,okCaption, cancelCaption, notOkCaption
						               );

						        // Change the order of buttons
						        Button ok = d.getOkButton();
						        HorizontalLayout buttons = (HorizontalLayout) ok.getParent();
						        buttons.removeComponent(ok);
						        buttons.addComponent(ok,1);
						        
						        buttons.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

						        return d;
						    }

						};
						ConfirmDialog.setFactory(df);		    	
				    	
				    	ConfirmDialog.show(UI.getCurrent(), "Confirmación", "¿Seguro que desea eliminar el Gremio? \n",
				    	        "Si", "No", new ConfirmDialog.Listener() {

						private static final long serialVersionUID = 1L;
						
						@Override
						public void onClose(ConfirmDialog dialog) {
	    	                if (dialog.isConfirmed()) {

	    	                	WS_AMA llamada = null;
								try {
									llamada = new WS_AMA(service.plsqlDataSource.getConnection());
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	    		    			HashMap respuestaGremios = null;
	    		    			try {


	    		    		    	// pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ, pESTADO)
	    		    				respuestaGremios = llamada.ejecutaWS_AMA__ELIMINAR_GREMIO_PRESUPUESTO(
	    		    						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
	    		    						UsuarioSave,
	    		    						UI.getCurrent().getSession().getAttribute("origen").toString(),
	    		    						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
	    		    						new BigDecimal(retorno.get("IDPRESU").toString()),
	    		    					    new BigDecimal(gremios.idgremio.toString()),
	    		    					    gremios.asegperf,
	    		    					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString()
	    		    						);
	    		    				
	    		    				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
	    		    				//System.out.println("Error eliminar gremio: " + retornoGremios.get("CODIGOERROR") );
	    		    				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
	    		    					
	    		    					new Notification("Error al eliminar el gremio",
	    		    							retornoGremios.get("CODIGOERROR").toString() + " - " +
	    		    							retornoGremios.get("TEXTOERROR").toString(),
	    		    							Notification.Type.ERROR_MESSAGE, true)
	    		    							.show(Page.getCurrent());
	    		    				}
	    		    				else {
	    		
	    		    					// Borramos la fila
	    		    					//gremios.tbBaremos.removeItem(gremios.tbBaremos.getValue());

	    		    					new Notification("Proceso finalizado correctamente",
	    		    							"Gremio eliminado",
	    		    							Notification.Type.TRAY_NOTIFICATION, true)
	    		    							.show(Page.getCurrent());
	    		    					
	    		    					ProvDatosPresupuestoExpediente.this.removeFromParent(dtitLayout);
	    		    					Cargar_Datos();
			    	                	//Cargar_Pantalla(retorno);	    		    					
	    		    					
	    		
	    		    					
	    		    				}
	    		    				
	    		    			} catch (Exception e) {
	    		    				e.printStackTrace();
	    		    			}	

	    	                } else {
	    	                    // User did not confirm
	    	                	
	    	                	
	    	                    
	    	                }
	    	            }

			
	    	        });
				    	
				    		
					}
				});
				
				// Si el gremio est� finalizado ya no lo podemos tocar
				if ( map.get("FINALIZADO").toString().equals("S")) {
					gremios.btBorrarGremio.setVisible(false);
					gremios.btModificarGremio.setVisible(false);
					gremios.btNuevoItemBaremo.setVisible(false);
				}
				// Si el gremio es el primero y solo hay uno, no lo podemos borrar
				//System.out.println("¿ Cuantos gremios tenemos ? "+ valor.size() );
				if ( valor.size() == 1  ) {
					gremios.btBorrarGremio.setVisible(false);
				}
				
				
				gremios.cabeceraGremio.setColumnExpandRatio(0, 8);
				gremios.cabeceraGremio.setColumnExpandRatio(1, 15);
				gremios.cabeceraGremio.setColumnExpandRatio(2, 10);
				gremios.cabeceraGremio.setColumnExpandRatio(3, 5);
				gremios.cabeceraGremio.setColumnExpandRatio(4, 10);
				gremios.cabeceraGremio.setColumnExpandRatio(5, 16);
				gremios.cabeceraGremio.setColumnExpandRatio(6, 12);
				gremios.cabeceraGremio.setColumnExpandRatio(7, 8);
				gremios.cabeceraGremio.setColumnExpandRatio(8, 8);
				gremios.cabeceraGremio.setColumnExpandRatio(9, 8);

				
				
				
		
				gremios.tbBaremos = new Table();
				gremios.tbBaremos.setStyleName("pie-tabla");
				
				gremios.tbBaremos.setPageLength(0);
				gremios.tbBaremos.setColumnReorderingAllowed(false);
				dtitLayout.addComponent(gremios.tbBaremos);
				String[] columnsexp ={"DSITEMN3","DSITEMN2","TRABAJOS","IMPORTE","IDITEMN3","IDITEMN2","UNIDADES","SUBTOTAL","EDITAR","ELIMINAR","CDDANOS","DESDANOS"};
				Object[] typesexp = {String.class,String.class,String.class,java.math.BigDecimal.class,String.class,String.class,java.math.BigDecimal.class,java.math.BigDecimal.class, Button.class, Button.class,String.class,String.class};
				Object[] visibleColumnsexp = 
						new Object[]{"DSITEMN2","DSITEMN3","DESDANOS","UNIDADES","IMPORTE","SUBTOTAL","EDITAR","ELIMINAR"};
				Util.defineTable(gremios.tbBaremos, columnsexp, typesexp, visibleColumnsexp,true);
				gremios.tbBaremos.setColumnHeaders(new String[] {"Item1","Item2","Daños","Unid.","Importe","Subtotal","","" });

				gremios.tbBaremos.setColumnExpandRatio("DSITEMN2", 20);
				gremios.tbBaremos.setColumnExpandRatio("DSITEMN3", 42);
				gremios.tbBaremos.setColumnExpandRatio("UNIDADES", 4);
				gremios.tbBaremos.setColumnExpandRatio("DESDANOS", 8);				
				gremios.tbBaremos.setColumnExpandRatio("IMPORTE",  9);
				gremios.tbBaremos.setColumnExpandRatio("SUBTOTAL", 9);
				gremios.tbBaremos.setColumnExpandRatio("EDITAR",   4);
				gremios.tbBaremos.setColumnExpandRatio("ELIMINAR", 4);				
				
				gremios.tbBaremos.setColumnAlignment("UNIDADES", Align.CENTER);
				gremios.tbBaremos.setColumnAlignment("IMPORTE", Align.RIGHT);
				gremios.tbBaremos.setColumnAlignment("SUBTOTAL", Align.RIGHT);
				
				gremios.tbBaremos.setCellStyleGenerator(new Table.CellStyleGenerator() {                
			        @Override
			        public String getStyle(Table source, Object itemId, Object propertyId) {

			        	
			            if(propertyId != null ) {
			                return "normal";
			            } else {
			                return null;
			            }
			        }
			      });
				
				
				
				gremios.tbBaremos.setColumnReorderingAllowed(false);
				gremios.tbBaremos.setSelectable(true);
				
				gremios.tbBaremos.setTabIndex(-1);				
				
				PAC_SHWEB_PROVEEDORES llamada = null;
				try {
					llamada = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HashMap respuestaTrabajos = null;
				try {
					respuestaTrabajos = llamada.ejecutaPAC_SHWEB_PROVEEDORES__DETALLE_GREMIO_PRESUPUESTO(
							//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
							UsuarioSave,
							UI.getCurrent().getSession().getAttribute("origen").toString(),
							new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
							presupuesto,
							gremios.idgremio,
							gremios.asegperf
							);
					
					//System.out.println("llamada Gremio: " + gremios.idgremio + " Presu: " + presupuesto);
					
					//System.out.println("Respuesta trabajos >>>"+ respuestaTrabajos);
					Map<String, Object> retornoTrabajos = new HashMap<String, Object>(respuestaTrabajos);
					List<Map> valorTrabajos = (List<Map>) retornoTrabajos.get("TRABAJOS");
					
					
					if ( valorTrabajos != null ) {
							taObs.setReadOnly(false);
							taObs.setValue("");
							taObs.setReadOnly(true);
							gremios.btBorrarGremio.setVisible(false);
							
							if ( valorTrabajos.size()== 0 && valor.size() > 1) {
								gremios.btBorrarGremio.setVisible(true);
								if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")) {
									gremios.btBorrarGremio.setVisible(false);
								}								
							}
							
							
							// Si no hay detalle no podemos finalizar el gremio
							if ( valorTrabajos.size()==0) {
								gremios.btModificarGremio.setVisible(false);
							}
							
							for (Map mapTrabajos : valorTrabajos) {
								
	
								Object newItemId = gremios.tbBaremos.addItem();
								Item row1 = gremios.tbBaremos.getItem(newItemId);
								
								//System.out.println(">>>>>>>>>>>>>>>>  " + mapTrabajos.get("DSITEMN3"));
								row1.getItemProperty("DSITEMN3").setValue(mapTrabajos.get("DSITEMN3"));
								row1.getItemProperty("DSITEMN2").setValue(mapTrabajos.get("DSITEMN2"));
								row1.getItemProperty("TRABAJOS").setValue(mapTrabajos.get("TRABAJOS"));
								row1.getItemProperty("IMPORTE").setValue(mapTrabajos.get("IMPORTE"));
								row1.getItemProperty("IDITEMN3").setValue(mapTrabajos.get("IDITEMN3"));
								row1.getItemProperty("IDITEMN2").setValue(mapTrabajos.get("IDITEMN2"));
								row1.getItemProperty("UNIDADES").setValue(new BigDecimal(mapTrabajos.get("UNIDADES").toString()));
								if ( mapTrabajos.get("CDDANOS")==null) {
									
								} else {
									
									row1.getItemProperty("CDDANOS").setValue(mapTrabajos.get("CDDANOS").toString());
									if ( mapTrabajos.get("CDDANOS").toString().equals("DD")) {
										row1.getItemProperty("DESDANOS").setValue("Directos");
									}
									if ( mapTrabajos.get("CDDANOS").toString().equals("DE")) {
										row1.getItemProperty("DESDANOS").setValue("Estéticos");
									}									
								}
								BigDecimal subtotal = new BigDecimal(mapTrabajos.get("UNIDADES").toString()).multiply(new BigDecimal(mapTrabajos.get("IMPORTE").toString()));
								
								row1.getItemProperty("SUBTOTAL").setValue(subtotal.setScale(2, BigDecimal.ROUND_HALF_UP));
								
								totalPresupuesto = totalPresupuesto.add(subtotal.setScale(2, BigDecimal.ROUND_HALF_UP));
								
								//System.out.println("***********************************" + mapTrabajos.get("TRABAJOS") );
								// Si el campo de trabajos viene informado lo ponemos en el textarea de observaciones
								if ( mapTrabajos.get("TRABAJOS") != null ) {
									taObs.setVisible(true);
									taObs.setReadOnly(false);
									if (gremios.asegperf.equals("A")) {
										taObs.setValue( taObs.getValue() + "(" +gremios.asegperf+")SEGURADO - "+ gremios.txgremio +" > TRABAJOS:" + mapTrabajos.get("TRABAJOS") + "\n");
									} else {
										taObs.setValue( taObs.getValue() + "(" +gremios.asegperf+")PERJUDICADO - "+ gremios.txgremio +" > TRABAJOS:" + mapTrabajos.get("TRABAJOS") + "\n");	
									}
									
									taObs.setReadOnly(true);
								}

								//
								// A�adimos los botones del baremos
								// Bot�n editar
								Button btEditarBaremo = new Button();
								btEditarBaremo.setId(newItemId.toString());
								btEditarBaremo.setData(newItemId);
								btEditarBaremo.addStyleName(ValoTheme.BUTTON_FRIENDLY);
								btEditarBaremo.addStyleName(ChameleonTheme.BUTTON_DOWN);
								btEditarBaremo.setIcon(FontAwesome.EDIT);
								
								if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")) {
									btEditarBaremo.setVisible(false);
								}
								btEditarBaremo.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										// TODO Auto-generated method stub
										Object data =  event.getButton().getData();
										gremios.tbBaremos.select(data);
										Item itemClickEvent = gremios.tbBaremos.getItem(data);
										
										//UI.getCurrent().addWindow(provVenEditarItemBaremo);
										provVenEditarItemBaremo.gremio = gremios;
										provVenEditarItemBaremo.modificar("MODIFICAR", mapTrabajos.get("IDITEMN2").toString(), mapTrabajos.get("IDITEMN3").toString()
												, null, mapTrabajos.get("UNIDADES").toString() , mapTrabajos.get("IMPORTE").toString()
												, presupuesto);
										gremios.btNuevoItemBaremo.click();
										provVenEditarItemBaremo.idgremio = gremios.idgremio;
										provVenEditarItemBaremo.tfGremio.setValue(gremios.txgremio);
										provVenEditarItemBaremo.cbItem1.setValue(mapTrabajos.get("IDITEMN2"));
										provVenEditarItemBaremo.cbItem2.setValue(mapTrabajos.get("IDITEMN3"));
										
										//System.out.println(">>> importe:"+mapTrabajos.get("IMPORTE").toString());
										provVenEditarItemBaremo.tfImporte.setValue(mapTrabajos.get("IMPORTE").toString().replace(".", ","));
										provVenEditarItemBaremo.tfUnidades.setValue(mapTrabajos.get("UNIDADES").toString().replace(".",","));
										if ( mapTrabajos.get("CDDANOS")==null ) {
											provVenEditarItemBaremo.cbTipoDano.setValue(null);
										} else {
											provVenEditarItemBaremo.cbTipoDano.setValue(mapTrabajos.get("CDDANOS").toString());
										}
										if ( mapTrabajos.get("TRABAJOS")==null ) {
											provVenEditarItemBaremo.tfOtrosTrabajos.setValue(null);
										}
										else {
											provVenEditarItemBaremo.tfOtrosTrabajos.setValue(mapTrabajos.get("TRABAJOS").toString());
										}
										
										
									}
								});
								row1.getItemProperty("EDITAR").setValue(btEditarBaremo);
								// Bot�n Eliminar
								Button btEliminarBaremo = new Button();
								btEliminarBaremo.setId(newItemId.toString());
								btEliminarBaremo.setData(newItemId);
								btEliminarBaremo.addStyleName(ValoTheme.BUTTON_DANGER);
								btEliminarBaremo.addStyleName(ChameleonTheme.BUTTON_DOWN);
								btEliminarBaremo.setIcon(FontAwesome.TRASH);
								
								if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")) {
									btEliminarBaremo.setVisible(false);
								}								
								btEliminarBaremo.addClickListener(new ClickListener() {
									@Override
									public void buttonClick(ClickEvent event) {
										// TODO Auto-generated method stub
										Object data =  event.getButton().getData();
										gremios.tbBaremos.select(data);
										Item itemClickEvent = gremios.tbBaremos.getItem(data);
										
										// Borramos el ITEM. Confirmacion primero
										ConfirmDialog.Factory df = new DefaultConfirmDialogFactory() {

											
										    @Override
											public ConfirmDialog create(String caption, String message, String okCaption,
													String cancelCaption, String notOkCaption) {

										        ConfirmDialog  d = super.create(caption,message,okCaption, cancelCaption, notOkCaption
										               );

										        // Change the order of buttons
										        Button ok = d.getOkButton();
										        HorizontalLayout buttons = (HorizontalLayout) ok.getParent();
										        buttons.removeComponent(ok);
										        buttons.addComponent(ok,1);
										        buttons.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

										        return d;
										    }

										};
										ConfirmDialog.setFactory(df);		
								    	ConfirmDialog.show(UI.getCurrent(), "Eliminar Item", "¿Seguro que desea eliminar el Item? \n " +
								    			"Gremio: " + gremios.txgremio + "\n" +
								    			"Item: " + mapTrabajos.get("DSITEMN2") + " -> " +mapTrabajos.get("DSITEMN3") + "\n" +
								    			"Unidades: " + mapTrabajos.get("UNIDADES") + "\n" +
								    			"Importe: " + mapTrabajos.get("IMPORTE")
								    			,
								    	        "Si", "No", new ConfirmDialog.Listener() {

								    	            public void onClose(ConfirmDialog dialog) {
								    	                if (dialog.isConfirmed()) {
								    	                    // Confirmamos la eliminaci�n del gremio
								    	        			// Llenamos la lista del gremio
								    	        			WS_AMA llamada = null;
															try {
																llamada = new WS_AMA(service.plsqlDataSource.getConnection());
															} catch (SQLException e1) {
																// TODO Auto-generated catch block
																e1.printStackTrace();
															}
								    	        			HashMap respuestaGremios = null;
								    	        			try {
								    	        				//pPUSUARIO, pORIGEN, pEXPEDIENTE, pIDPRESUP, pIDGREMIO, pASEGPERJ, pIDITEM2, pIDITEM3, pESTADO)
								    	        				
						    	        						//System.out.println("Expediente: " + UI.getCurrent().getSession().getAttribute("expediente").toString());
						    	        						//System.out.println("prespuesto: " + presupuesto);
						    	        						//System.out.println("gremio: " + gremios.idgremio);
						    	        						//System.out.println("asegperf: " + gremios.asegperf);
						    	        						//System.out.println("item1: " + mapTrabajos.get("IDITEMN2").toString() );
						    	        						//System.out.println("item2: " + mapTrabajos.get("IDITEMN3").toString() );
						    	        						//System.out.println("Estado: " +UI.getCurrent().getSession().getAttribute("estadoExpediente").toString());
						    	        						
								    	        				respuestaGremios = llamada.ejecutaWS_AMA__ELIMINAR_ITEM_PRESUPUESTO(
								    	        						//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
								    	        						UsuarioSave,
								    	        						UI.getCurrent().getSession().getAttribute("origen").toString(),
								    	        						new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
								    	        					    presupuesto,
								    	        					    gremios.idgremio,
								    	        					    gremios.asegperf,
								    	        					    mapTrabajos.get("IDITEMN2").toString(),
								    	        					    mapTrabajos.get("IDITEMN3").toString(),
								    	        					    UI.getCurrent().getSession().getAttribute("estadoExpediente").toString(),
								    	        					    mapTrabajos.get("CDDANOS").toString()
								    	        						);
								    	        				
								    	        				Map<String, Object> retornoGremios = new HashMap<String, Object>(respuestaGremios);
								    	        				//System.out.println("Error eliminar item: " + retornoGremios.get("CODIGOERROR") );
								    	        				if ( !retornoGremios.get("CODIGOERROR").toString().equals("0") ) {
								    	        					
								    	        					new Notification("Error al borrar el item",
								    	        							retornoGremios.get("CODIGOERROR").toString() + " - " +
								    	        							retornoGremios.get("TEXTOERROR").toString(),
								    	        							Notification.Type.ERROR_MESSAGE, true)
								    	        							.show(Page.getCurrent());
								    	        				}
								    	        				else {

								    	        					// Borramos la fila
								    	        					//gremios.tbBaremos.removeItem(gremios.tbBaremos.getValue());
								    	        					new Notification("Proceso finalizado correctamente",
								    	        							"Item eliminado",
								    	        							Notification.Type.TRAY_NOTIFICATION, true)
								    	        							.show(Page.getCurrent());
								    	        					
										    	                	ProvDatosPresupuestoExpediente.this.removeFromParent(dtitLayout);
										    	                	
										    	                	Cargar_Datos();
										    	                 	Cargar_Pantalla(retorno, UsuarioSave);
								    	        					

								    	        					
								    	        				}
								    	        				
								    	        			} catch (Exception e) {
								    	        				e.printStackTrace();
								    	        			}	
								    	        			// Fin lista Gremios								    	                	
								    	                	

								    	                } else {
								    	                    // User did not confirm
								    	                    
								    	                }
								    	            }
								    	        });
										
									}
								});
								row1.getItemProperty("ELIMINAR").setValue(btEliminarBaremo);
								
								// Si el gremio est� finalizado ocoultamos los botones de editar y eliminiar
								if ( map.get("FINALIZADO").toString().equals("S")) {
									btEditarBaremo.setVisible(false);
									btEliminarBaremo.setVisible(false);
								}
		
							}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
	
			}
		}
		
		
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.addComponent(btAnadirGremio);
		
		
		if ( UI.getCurrent().getSession().getAttribute("tit.estadoexp").equals("FIN")) {
			btAnadirGremio.setVisible(false);
		}
		
		hl1.addComponent(lbTotalPresupuesto);
		hl1.setComponentAlignment(lbTotalPresupuesto, Alignment.MIDDLE_RIGHT);
		
		lbTotalPresupuesto.setValue("Total presupuesto: " + totalPresupuesto.toString());
		
		
		hl1.addComponent(btFactura);
		hl1.setWidth("100%");
		btAnadirGremio.setStyleName(ValoTheme.BUTTON_PRIMARY);
		hl1.setComponentAlignment(btAnadirGremio, Alignment.BOTTOM_LEFT);
		btFactura.setStyleName(ValoTheme.BUTTON_PRIMARY);
		hl1.setComponentAlignment(btFactura, Alignment.BOTTOM_RIGHT);
		

		btFactura.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if ( !todosFinalizados.equals("S")) {
					new Notification("Error al generar la factura",
							"No puede crear la Factura. No están finalizados todos los gremios",
							Notification.Type.TRAY_NOTIFICATION, true)
							.show(Page.getCurrent());
				} else {
					provVenFactura.init(presupuesto, provDatosDetalleExpediente, ProvDatosPresupuestoExpediente.this);
					UI.getCurrent().removeWindow(provVenFactura);
					provVenFactura.UsuarioSave = UsuarioSave;
					System.out.println("Llamamos a provvenfactura "+ provVenFactura.UsuarioSave);
					UI.getCurrent().addWindow(provVenFactura);
				}
			}
		});
		
		btAnadirGremio.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				//System.out.println("Añadir gremio pulsado");
				provVenEditarGremio.UsuarioSave = UsuarioSave;
				provVenEditarGremio.init("NUEVO");
				provVenEditarGremio.cbGremio.setEnabled(true);
				getUI().removeWindow(provVenEditarGremio);
				UI.getCurrent().addWindow(provVenEditarGremio);
	
			}
		});
		
		hl1.setMargin(true);
		
		taObs.setWidth("100%");
		taObs.setRows(4);
		
		dtitLayout.addComponent(taObs);
		dtitLayout.setComponentAlignment(taObs, Alignment.TOP_CENTER);
		
		
		dtitLayout.addComponent(hl1);
		dtitLayout.setMargin(true);
		dtitLayout.setWidth("100%");
		setContent(dtitLayout);
		


		
	}



}