package com.csi_ti.itaca.custom.general.frontend;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenDocumentacion;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;


@Theme("tests-valo-reindeer")
public class ProvPantallaConsultaExpediente extends Panel implements ItacaView {

	public String abrirDocumentos = "";
	public String UsuarioSave;
	
	public TabSheet tabExp = new TabSheet();
	private static GeneralBusinessServiceImpl service;
	
	ProvVenDocumentacion proven;
	Map<String, Object> retornoGed;
	
	
	ProvDatosTitularExpediente provDatosTitularExpediente = new ProvDatosTitularExpediente(UsuarioSave);
	ProvDatosDetalleExpediente provDatosDetalleExpediente;
	ProvDatosObservacionesExpediente provDatosObservacionesExpediente;
	ProvDatosComunicadosExpediente provDatosComunicadosExpediente;
	ProvDatosPresupuestoExpediente provDatosPresupuestoExpediente;

	UploadBox uploadbox = new UploadBox();
	String ruta;

	
	VerticalLayout tabObservaciones = new VerticalLayout();
	VerticalLayout tabComunicados = new VerticalLayout();
	VerticalLayout tabPresupuestos = new VerticalLayout();
	
	public Button btGed = new Button("Documentación(GED)");		
	
	VerticalLayout layoutPrincipal = new VerticalLayout();
	
	public ProvPantallaConsultaExpediente () {
		
		//System.out.println("Inicializamos la clase ProvPantallaConsultaExpediente");
		UsuarioSave = UI.getCurrent().getSession().getAttribute("usuariosave").toString();
		
		
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		setStyleName("panel-detalle");
		setCaption("GESTIÓN DEL EXPEDIENTE Nº " + UI.getCurrent().getSession().getAttribute("expediente")
				+ " ( " + 
				UI.getCurrent().getSession().getAttribute("estadoExpediente") + " ) ");
		
		
		// Recuperamos los datos del expediente
		
			
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


		try {
			respuesta = llamada.ejecutaWS_AMA__F_DETALLE_EXPEDIENTE(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Recuperamos los comunicados
		
		
		/*PAC_SHWEB_PROVEEDORES llamadaProv = new PAC_SHWEB_PROVEEDORES(ConexionFactoria.conn);
		HashMap respuestaCom = null;

		try {
			respuestaCom = llamadaProv.ejecutaPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE(
					UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		// Recuperamos el presupuesto
		WS_AMA llamadaPresupuesto = null;
		try {
			llamadaPresupuesto = new WS_AMA(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaPresupuesto = null;

		try {
			respuestaPresupuesto = llamadaPresupuesto.ejecutaWS_AMA__CABECERA_PRESUPUESTO(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
					null
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		

		Map<String, Object> retorno = new HashMap<String, Object>(respuesta);
		provDatosDetalleExpediente = new ProvDatosDetalleExpediente(retorno, this);
		provDatosObservacionesExpediente = new ProvDatosObservacionesExpediente(retorno);
		
		//Map<String, Object> retornoComunicados = new HashMap<String, Object>(null);
		provDatosComunicadosExpediente = new ProvDatosComunicadosExpediente(null,this, UsuarioSave);
		provDatosComunicadosExpediente.recargarComunicados();
		
		Map<String, Object> retornoPresupuesto = new HashMap<String, Object>(respuestaPresupuesto);
		//System.out.println("provDatosPresupuestoExpediente " + UsuarioSave);
		provDatosPresupuestoExpediente = new ProvDatosPresupuestoExpediente(retornoPresupuesto, provDatosDetalleExpediente, UsuarioSave);
		
		// Detalle del expediente
		
		// Recuperamos los comunicados
		WS_AMA detalleExpedienteLlamada = null;
		try {
			detalleExpedienteLlamada = new WS_AMA(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaDetalleExpediente = null;

		//PUSUARIO, pORIGEN, pEXPEDIENTE).ejecutaPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE(
		try {
			respuestaDetalleExpediente = detalleExpedienteLlamada.ejecutaWS_AMA__F_DETALLE_EXPEDIENTE(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
			Map<String, Object> retornoDetalleExpediente = new HashMap<String, Object>(respuestaDetalleExpediente);
			UI.getCurrent().getSession().setAttribute("cliente", respuestaDetalleExpediente.get("IDCLIENTE"));
			UI.getCurrent().getSession().setAttribute("contrato", respuestaDetalleExpediente.get("IDCONTRATO"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		/*// Consultamos la documentacion
		try {
			HashMap documentacion = null;

			PAC_SHWEB_CLIENTES docllamada= null;
			try {
				docllamada = new PAC_SHWEB_CLIENTES(service.plsqlDataSource.getConnection());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			documentacion = docllamada.ejecutaPAC_SHWEB_CLIENTES__F_RELACIONDOCUMENTOS(
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					//new BigDecimal(917005864)
					);
			retornoGed = new HashMap<String, Object>(documentacion);
			List<Map> valorGed = (List<Map>) retornoGed.get("DOCUMENTACION");			
			//System.out.println("Respuesta documentos: " + valorGed);
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/			
		
		layoutPrincipal.setMargin(false);
		layoutPrincipal.setSizeFull();
		layoutPrincipal.addComponent(provDatosTitularExpediente);
		provDatosTitularExpediente.UsuarioSave = UsuarioSave;
		layoutPrincipal.addComponent(provDatosDetalleExpediente);
		
		// Añadimos los telefonos
		Table tbTelefonos = new Table();
		Button btTelefonos = new Button();
		
		
		VerticalLayout popupContent = new VerticalLayout();
		popupContent.setWidth("500px");
		popupContent.setHeight("700");
		tbTelefonos.setWidth("100%");
		tbTelefonos.setHeight("100%");
		popupContent.addComponent(tbTelefonos);

		

		// The component itself
		PopupView popup = new PopupView("Consultar Teléfonos", popupContent);
		layoutPrincipal.addComponent(popup);
		layoutPrincipal.setComponentAlignment(popup, Alignment.MIDDLE_CENTER);
		layoutPrincipal.addComponent(btGed);
		btGed.setStyleName(ValoTheme.BUTTON_PRIMARY);

		proven = new ProvVenDocumentacion(ProvPantallaConsultaExpediente.this, UsuarioSave);
		
		CloseListener lstClose = new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("Cerramos y abrimos de nuevo:" + abrirDocumentos.equals("SI"));
				if ( abrirDocumentos.equals("SI")) {

						proven = null;
						proven = new ProvVenDocumentacion(ProvPantallaConsultaExpediente.this, UsuarioSave);
						proven.addCloseListener(this);
						UI.getCurrent().addWindow(proven);
				}
			}
		};
		
		
		
		btGed.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				proven = new ProvVenDocumentacion(ProvPantallaConsultaExpediente.this, UsuarioSave);
				proven.addCloseListener(lstClose);
				UI.getCurrent().addWindow(proven);

			}
		});		
		

		
		
		
		String[] columnsexp ={"Descripcion","Telefono"};
		Object[] typesexp = {String.class,String.class};
		Object[] visibleColumnsexp = new Object[]{"Descripcion","Telefono"};
		Util.defineTable(tbTelefonos, columnsexp, typesexp, visibleColumnsexp,true);
		tbTelefonos.setColumnHeaders(new String[] {"Descripcion","Telefono" });

		tbTelefonos.setColumnExpandRatio("Descripcion", 70);
		tbTelefonos.setColumnExpandRatio("Telefono", 30);
		tbTelefonos.setColumnReorderingAllowed(false);
		tbTelefonos.setTabIndex(-1);
		tbTelefonos.setPageLength(7);
		
		// telefonos
		tbTelefonos.removeAllItems();
		
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if ( UI.getCurrent().getSession().getAttribute("telefonosExpediente")!=null ) {;
		//System.out.println("*********************************");
			List<Map> valor = (List<Map>) UI.getCurrent().getSession().getAttribute("telefonosExpediente");

			for (Map map : valor) {

				Object newItemId = tbTelefonos.addItem();
				Item row1 = tbTelefonos.getItem(newItemId);
				
				if ( map.get("DSTELEF")!=null ) {
					row1.getItemProperty("Descripcion").setValue(map.get("DSTELEF"));
				}
				if ( map.get("TXTELEF")!=null ) {
					row1.getItemProperty("Telefono").setValue(map.get("TXTELEF").toString());
				}


			}	
		}		
		
		
		// A�adimos las pesta�as
		
		VerticalLayout vTabs = new VerticalLayout();
		vTabs.setMargin(true);
		
		tabExp.addTab(tabComunicados,"COMUNICADOS");
		//tabExp.addTab(tabObservaciones,"OBSERVACIONES");		
		tabExp.addTab(tabPresupuestos,"PRESUPUESTOS");
		
		tabExp.setSizeFull();
		tabObservaciones.setSizeFull();
		tabComunicados.setSizeFull();
		tabPresupuestos.setSizeFull();
		vTabs.setSizeFull();
		vTabs.addComponent(tabExp);
		
		// Cambiamos la pestaña
		tabExp.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				// TODO Auto-generated method stub
		
				//System.out.println("Cambios la pestaña " + tabExp.getTabPosition(tabExp.getTab(tabExp.getSelectedTab())));
				
				if ( tabExp.getTabPosition(tabExp.getTab(tabExp.getSelectedTab()))==1) {
					
					
	            	provDatosPresupuestoExpediente.removeFromParent(provDatosPresupuestoExpediente.dtitLayout);
	            	//provDatosPresupuestoExpediente = null;
	            	
	        		// Recuperamos el presupuesto
	        		WS_AMA llamadaPresupuesto = null;
					try {
						llamadaPresupuesto = new WS_AMA(service.plsqlDataSource.getConnection());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		HashMap respuestaPresupuesto = null;

	        		try {
	        			respuestaPresupuesto = llamadaPresupuesto.ejecutaWS_AMA__CABECERA_PRESUPUESTO(
	        					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
	        					UsuarioSave,
	        					UI.getCurrent().getSession().getAttribute("origen").toString(),
	        					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
	        					null
	        					);
	        			//retornoPresupuesto =  new HashMap<String, Object>(respuestaPresupuesto);
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        		
	        		
	            	//provDatosPresupuestoExpediente = new ProvDatosPresupuestoExpediente(respuestaPresupuesto, provDatosDetalleExpediente);
	            	//System.out.println("Añadimos el contect");
	            	//provDatosPresupuestoExpediente.setContent(provDatosPresupuestoExpediente.dtitLayout);
	            	//provDatosPresupuestoExpediente.setCaption("hola pepito");
	            	
	            	provDatosPresupuestoExpediente.Cargar_Datos();
	            	provDatosPresupuestoExpediente.setContent(provDatosPresupuestoExpediente.dtitLayout);
				}
 			
			}
		});

		
		//tabExp.setStyleName(ValoTheme.TABSHEET_FRAMED);
		tabExp.setStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		
		provDatosObservacionesExpediente.setWidth("100%");
		provDatosObservacionesExpediente.setHeight("100%");
		tabObservaciones.addComponent(provDatosObservacionesExpediente);
		tabComunicados.addComponent(provDatosComunicadosExpediente);
		tabPresupuestos.addComponent(provDatosPresupuestoExpediente);
		
		layoutPrincipal.addComponent(vTabs);
		layoutPrincipal.setSizeFull();
		
		// Finalmente a�adimos la el layout principal donde hemos puestos el resto de componentes.
		setContent(layoutPrincipal);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		//System.out.println("Entramos a la ventana <>");
		
		String args[] = event.getParameters().split("/");
		UsuarioSave = args[0];
		
		//System.out.println("Abrimos la venta >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> naaaaaaaaaaaa  " + UsuarioSave);

	}
	
	/*******************************************************************************************************
	
	 *
	 */
	
    // Implement both receiver that saves upload in a file and
    // listener for successful upload
    class ImageReceiver implements Receiver, SucceededListener {
        private static final long serialVersionUID = -1276759102490466761L;

        public File file;
        
        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            // Create upload stream
            FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
                file = new File("/tmp/uploads/" + filename);
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification("Could not open file<br/>",
                                 e.getMessage(),
                                 Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
                return null;
            }
            return fos; // Return the output stream to write to
        }

        public void uploadSucceeded(SucceededEvent event) {
        	//System.out.println("Upload sucess");
        	
            // Show the uploaded file in the image viewer
            //image.setVisible(true);
            //image.setSource(new FileResource(file));
        }
    };
	
    @UIScope
    class UploadBox extends CustomComponent
		    implements Receiver, ProgressListener,
		               FailedListener, SucceededListener {
		  private static final long serialVersionUID = -46336015006190050L;
		
		  // Put upload in this memory buffer that grows automatically
		  ByteArrayOutputStream os =
		      new ByteArrayOutputStream(10240);
		
		  // Name of the uploaded file
		  String filename;
		  
		  ProgressBar progress = new ProgressBar(0.0f);
		  
		  Upload upload = new Upload();
		  
		  
		  // Show uploaded file in this placeholder
		  //Image image = new Image("Uploaded Image");
		  
		  public UploadBox() {
		      // Create the upload component and handle all its events
		      
		      upload.setReceiver(this);
		      upload.addProgressListener(this);
		      upload.addFailedListener(this);
		      upload.addSucceededListener(this);
		      
		      upload.setButtonCaption(null);
		      
		      // Put the upload and image display in a panel
		      
		      
		      VerticalLayout panelContent = new VerticalLayout();
		      
		      panelContent.setWidth("100%");
		      

		      panelContent.addComponent(upload);
		      panelContent.addComponent(progress);
		      progress.setWidth("200px");
		      progress.setImmediate(true);
		      //panelContent.addComponent(image);
		      

		      progress.setVisible(false);
		      //image.setVisible(false);
		      
		      setCompositionRoot(panelContent);
		  }            
		  
		  public OutputStream receiveUpload(String filename, String mimeType) {
			  //System.out.println("Entreamos a receiveUpload:" + filename);
			  
			  FileOutputStream fos = null;
			  
		      this.filename = filename;
		      os.reset(); // Needed to allow re-uploading
		      
		      //fichero = new File(tfNombreFichero.getValue().toString());
		      try {
		          // Open the file for writing.
		    	  //System.out.println("SUbimos el fichero: "+ruta);
		          fos = new FileOutputStream(ruta);
		      } catch (final java.io.FileNotFoundException e) {
		          // Error while opening the file. Not reported here.
		          e.printStackTrace();
		          return null;
		      }		      
		      return fos;
		  }
		
		  @Override
		  public void updateProgress(long readBytes, long contentLength) {
		      progress.setVisible(true);
		      
		      if (contentLength == -1)
		          progress.setIndeterminate(true);
		      else {
		          progress.setIndeterminate(false);
		          progress.setValue(((float)readBytes) /
		                            ((float)contentLength));
		      }
		  }
		
		  public void uploadSucceeded(SucceededEvent event) {
			  //System.out.println("Hemos subido el fichero.Upload Succeeded");

				//tfOrigen.focus();
				
				/*Bean fila = (Bean) table.getValue();
				// GUARDAMOS LOS DATOS EN LA TABLA SI TODO ES CORRECTO
				
				fila.setOrigen((Integer) tfOrigen.getValue());
				fila.setTipo((Integer) tfTipo.getValue());
				fila.setSubtipo((Integer) tfSubtipo.getValue());
				fila.setfichero(tfNombreFichero.getValue());
				fila.setDescripcion(tfDescripcion.getValue());
				fila.setDesorigen(tfOrigen.getItemCaption(tfOrigen.getValue()));
				fila.setDestipo(tfTipo.getItemCaption(tfTipo.getValue()));
				fila.setDessubtipo(tfSubtipo.getItemCaption(tfSubtipo.getValue()));
				fila.setEstado("M");
				fila.setCarga(carga);
				fila.setRuta(ruta);

				
				table.setData(fila);
				table.refreshRowCache();
				
				// Y nos colocamos en el último registro en blanco o creamos uno nuevo
				
				table.select(table.lastItemId());
				
				if ( table.getItem(table.getValue()).getItemProperty("desorigen")!=null && table.getItem(table.getValue()).getItemProperty("desorigen").toString()!="") {
					//System.out.println("Creamos registro"+table.getItem(table.getValue()).getItemProperty("desorigen"));
					Object itemId = beansCon.addItem(new Bean("",null,null,null,"","","","","","",""));
					table.setData(itemId);	
					table.select(table.lastItemId());	
				}
				
				tfNombreFichero.setEnabled(false);*/
				uploadbox.upload.setEnabled(false);
				
				
			  new Notification("Fichero subido correctamente.",
						event.getFilename().toString(),
						Notification.Type.TRAY_NOTIFICATION, true)
						.show(Page.getCurrent());
		  }
		
		  @Override
		  public void uploadFailed(FailedEvent event) {
		      Notification.show("Error al subir el fichero "+event.getFilename().toString(),
		                        Notification.Type.ERROR_MESSAGE);
		  }
	}
	
	
	
}
