package com.csi_ti.itaca.custom.general.frontend.ventanas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.custom.general.api.model.Generico0;
import com.csi_ti.itaca.custom.general.frontend.ProvPantallaConsultaExpediente;
import com.csi_ti.itaca.custom.general.frontend.Util;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_CLIENTES;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_LISTAS;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ChangeEvent;
import com.vaadin.ui.Upload.ChangeListener;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
public class ProvVenDocumentacion extends Window   {

	/**
	 * 
	 */

	public String UsuarioSave;
	
	protected FileDownloader fileDownloader;
	
	public ProvPantallaConsultaExpediente pantallaConsultaExpediente;
	
	//private static GeneralBusinessServiceImpl service;
	
	private static GeneralBusinessServiceImpl service;

	
	PropertysetItem item;
	
	// Añadimos la documentacion
	Table tbGed = new Table();
	Button btVisualizar = new Button("Visualizar Documento Seleccionado");
	Map<String, Object> retornoGed;	
	
	UploadBox uploadbox = new UploadBox();
	TextField tfNombreFichero = new TextField();
	TextField tfDescripcion = new TextField();
	
	BotoneraDoble botonera;

	BotoneraDoble botoneraabajo;

	private static final long serialVersionUID = 1L;

	private FieldGroup binder;
	String exp = "^[0-9]+(\\,([0-9]{1,2})?)?$";
	
	ComboBox  tfOrigen = new ComboBox();
	ComboBox  tfEstancias = new ComboBox();
	ComboBox  tfAntesDespues = new ComboBox();
	ComboBox  tfAsegPerj = new ComboBox();
	ComboBox  tfTipoDocumento = new ComboBox();
	
	ImageReceiver receiver = new ImageReceiver();
	
	Button btNuevoDocumento = new Button ("Nuevo Documento");
	Button btConfirmarDocumento = new Button ("Guardar Documento");
	
	Button btBorrarDocumento = new Button ("Borrar Documento");

	GridLayout gd;
	String ruta;
	String carga = null;


		
	

	@SuppressWarnings("deprecation")
	public ProvVenDocumentacion( ProvPantallaConsultaExpediente pantalla, String pusuario) {
		
		UsuarioSave = pusuario;

		pantallaConsultaExpediente = pantalla;
		pantallaConsultaExpediente.abrirDocumentos = "NO";
		
		// TODO Auto-generated constructor stub
		
		setModal(true);
		
		//setStyleName("panel-alta");
		setWidth("80%");

		
		setHeight("90%");
		
		setClosable(true);
		setResizable(false);
		setStyleName("ventanamodal");
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		
	

		FormLayout fm = new FormLayout();


		
		// TABLA GED
		
		String[] columnsexpGed ={"Tipo","Descripcion","Fecha","Url","Estancias","Asegperj","Antdes"};
		Object[] typesexpGed = {String.class,String.class,Date.class,String.class,String.class,String.class,String.class};
		Object[] visibleColumnsexpGed = new Object[]{"Tipo","Estancias","Asegperj","Antdes","Descripcion","Fecha","Url"};
		Util.defineTable(tbGed, columnsexpGed, typesexpGed, visibleColumnsexpGed,true);
		tbGed.setColumnHeaders(new String[] {"Tipo","Estancias","Aseg/Perj","Antes/Desp.","Descripcion","Fecha","Url" });

		tbGed.setColumnExpandRatio("Descripcion", 50);
		tbGed.setColumnExpandRatio("Fecha", 25);
		tbGed.setColumnExpandRatio("Url", 25);
		tbGed.setColumnReorderingAllowed(false);
		tbGed.setTabIndex(-1);
		tbGed.setPageLength(10);
		tbGed.setSelectable(true);
		
		// 
		tbGed.removeAllItems();
		
		tbGed.addValueChangeListener(new Property.ValueChangeListener() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
	            // Mostramos boton de visualizar en función de si hemos seleccionado EL DOCUMENTO O NO

	            if (event.getProperty().getValue() == null) {
	            	botonera.btAceptar.setEnabled(false);
	                return;
	            }
	            else
	            	botonera.btAceptar.setEnabled(true);
			}
	    });		
		
		LlenarTabla();
		
		
		fm.addComponent(tbGed);
		
		vl.addComponent(fm);
	    botonera = new BotoneraDoble();
	    botonera.btCancelar.setVisible(false);
	    vl.addComponent(botonera);
	    botonera.btAceptar.setEnabled(false);
	    
	    fm.setCaption("<b>CONSULTAR DOCUMENTACIÓN GED</b><hr>");
	    fm.setCaptionAsHtml(true);
	    // añadimos los uploads
	    
	    // AÑADIMOS EL DETALLE
	    
	    gd = new GridLayout(8, 5);
	    
	    gd.setCaption("<b>SUBIR DOCUMENTACIÓN GED</b><hr>");
	    gd.setCaptionAsHtml(true);

	    
		
	    gd.setSpacing(true);
	    gd.setMargin(false);
	    gd.setStyleName("grid-centrado");		
	    gd.addStyleName("example");
	    gd.setWidth("100%");
	    gd.setMargin(true);
	    gd.setSpacing(true);
	    
	    tfOrigen.setCaption("Tipo de Fichero");
	    gd.addComponent(tfOrigen,0,0);
	    tfOrigen.setWidth("100px");
	    tfOrigen.setRequired(true);
	    
	    tfOrigen.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
			    tfEstancias.setValue(null);
			    tfAsegPerj.setValue(null);
			    tfTipoDocumento.setValue(null);
			    tfAntesDespues.setValue(null);
			    
			    //System.out.println("Entramos tforigen " + tfOrigen.getValue());
				if ( tfOrigen.getValue() == null) {
				    tfEstancias.setVisible(false);
				    tfAsegPerj.setVisible(false);
				    tfTipoDocumento.setVisible(false);
				    tfAntesDespues.setVisible(false);
				}
				else if ( tfOrigen.getValue().equals("B")) {
				    tfEstancias.setVisible(true);
				    tfAsegPerj.setVisible(true);
				    tfTipoDocumento.setVisible(false);
				    tfAntesDespues.setVisible(true);
				}
				else {
				    tfEstancias.setVisible(false);
				    tfAsegPerj.setVisible(false);
				    tfTipoDocumento.setVisible(false);
				    tfAntesDespues.setVisible(false);
				}				
				
			}
		});
	    

	    tfEstancias.setCaption("Estancias");
	    gd.addComponent(tfEstancias,1,0);
	    	    

	    tfAsegPerj.setCaption("Aseg/Perj");
	    gd.addComponent(tfAsegPerj,2,0);
	    
	    tfTipoDocumento.setCaption("Tipo Documento");
	    gd.addComponent(tfTipoDocumento,3,0);
	    
	    
	    tfAntesDespues.setCaption("Antes/Despues");
	    gd.addComponent(tfAntesDespues,4,0);	    

	    tfEstancias.setVisible(false);
	    tfAsegPerj.setVisible(false);
	    tfTipoDocumento.setVisible(false);
	    tfAntesDespues.setVisible(false);

	    tfNombreFichero.setCaption("NombreFichero");
	    gd.addComponent(tfNombreFichero,4,1,7,1);	
	    tfNombreFichero.setWidth("100%");
	    tfNombreFichero.setEnabled(false);
	    tfNombreFichero.setRequired(true);
 

	    
	    tfDescripcion.setCaption("Descripcion:");
	    gd.addComponent(tfDescripcion,0,1,3,1);
	    tfDescripcion.setWidth("100%");
	    tfDescripcion.setMaxLength(50);
	    tfDescripcion.setRequired(true);

	    gd.addComponent(uploadbox,0,3,7,3);
	    //btUpload.addSucceededListener(receiver);
	    uploadbox.upload.setWidth("100%");
	    uploadbox.setCaption("Seleccione el archivo o arrastre el fichero al botón");
	    

	    
	
	    //VerticalLayout v1 = new VerticalLayout();
	    gd.addComponent(btConfirmarDocumento,0,4);
	    btConfirmarDocumento.setClickShortcut(KeyCode.ENTER);
	    btConfirmarDocumento.setStyleName(ValoTheme.BUTTON_PRIMARY);
	    gd.setComponentAlignment(btConfirmarDocumento,Alignment.BOTTOM_LEFT);
	    //hl.addComponent(v1);
	    
	    botoneraabajo = new BotoneraDoble();
	    botoneraabajo.btCancelar.setVisible(false);
	    botoneraabajo.btAceptar.setEnabled(true);
	    botoneraabajo.btAceptar.setStyleName(ValoTheme.BUTTON_DANGER);
	    botoneraabajo.btAceptar.setCaption("Cerrar Ventana");

		botoneraabajo.btAceptar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				

				System.out.println("Cerramos");
				close();
				
			}
		});	

	    vl.addComponent(gd);
	    vl.addComponent(botoneraabajo);
	    
		
		tfDescripcion.setNullRepresentation("");
		tfNombreFichero.setNullRepresentation("");
	    
		setContent(vl);
		
		botonera.btAceptar.setCaption("Visualizar Documento");
		
		
		botonera.btCancelar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				

				close();
				
			}
		});	
		
		//StreamResource myResource = createResource();
        //FileDownloader fileDownloader = new OnDemandFileDownloader(myResource);
        //fileDownloader.extend(botonera.btAceptar);
		
        final StreamResource stream = new StreamResource(
                new StreamSource() {
                    @Override
                    public InputStream getStream() {
                    	//System.out.println("Dentro del stream");

        				Item item = tbGed.getItem(tbGed.getValue());
        				
        				String fichero = item.getItemProperty("Url").getValue().toString();
        				//System.out.println("usuario" + UsuarioSave + " ..." + UsuarioSave.substring(4) + " ->Subimos el fichero" + fichero);

        				
        				byte[] fileContent;
      					try {
							fileContent = Files.readAllBytes(new File(fichero).toPath());
							return new ByteArrayInputStream(fileContent);							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
        					new Notification("Error al descargar el fichero " + fichero,
        							"Razón: " + e.getMessage(),
        							Notification.Type.ERROR_MESSAGE, true)
        							.show(Page.getCurrent());
        					e.printStackTrace();							
						}
      					
        				return null;


                    	
                    }
                }, "badname.txt");
        FileDownloader downloader = new FileDownloader(stream) {
            @Override
            public boolean handleConnectorRequest(VaadinRequest request,
                    VaadinResponse response, String path)
                    throws IOException {
            	
            	//System.out.println("Entramos");
				Item item = tbGed.getItem(tbGed.getValue());
				String fichero = item.getItemProperty("Url").getValue().toString();
				fichero = fichero.substring(fichero.lastIndexOf("/")+1);
				//System.out.println("usuario" + UsuarioSave + " ..." + UsuarioSave.substring(4) + " ----->Subimos el fichero" + fichero);
				
                stream.setFilename(fichero);
                return super
                        .handleConnectorRequest(request, response, path);
            }
        };

        downloader.extend(botonera.btAceptar);
        
		uploadbox.upload.addChangeListener(new ChangeListener() {
			
			@Override
			public void filenameChanged(ChangeEvent event) {
				// TODO Auto-generated method stub
				
				//System.out.println("************** ponemos el fichero *********"+event.getFilename());
				if ( event.getFilename()!=null) {
					tfNombreFichero.setValue(event.getFilename().toString());
				}
				
			}
		});        
        

		btConfirmarDocumento.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				
				try {

					File file = new File(tfNombreFichero.getValue().toString());
					String absolutePath = file.getName();
					int idx = file.getName().lastIndexOf("\\");
					absolutePath = file.getName().substring(idx + 1);
					
					String mensaje = "";
					if ( tfOrigen.getValue() == null ) mensaje += "Falta introducir el tipo de Fichero!\n";
					if ( absolutePath==null || absolutePath.equals("")) mensaje += "Seleccione un fichero\n";
					if ( tfDescripcion.getValue()==null || tfDescripcion.getValue().equals("")) mensaje += "Falta informar la descripción\n";
					
					//System.out.println(" Los mensajes son " + mensaje);
					if ( !mensaje.equals("")) {
						
						new Notification("Campos obligatorios",
								mensaje,
								Notification.Type.TRAY_NOTIFICATION, true)
								.show(Page.getCurrent());
						return;
					}
					
					
					Generico0 doc;
					Integer xsubtipo ;
	
					//System.out.println("2.El fichero es:"+ absolutePath);
					HashMap respuesta = null;
					service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
					//System.out.println("------------Llamamos a obetener conecion");
					PAC_SHWEB_PROVEEDORES guardarDoc = null;
					try {
						guardarDoc = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					respuesta = null;
					try {
						respuesta = guardarDoc.ejecutaPAC_SHWEB_PROVEEDORES__F_GUARDAR_DOC(
								new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()),
								new BigDecimal("1"), 
								UsuarioSave.substring(5), 
								tfOrigen.getValue().toString(), 
								tfDescripcion.getValue().toString(), 
								new BigDecimal("0"), 
								new BigDecimal("0"),
								new BigDecimal("0"), 
								absolutePath,
								(String)tfEstancias.getValue(),
								(String)tfAntesDespues.getValue(),
								(String)tfAsegPerj.getValue(),
								(String)absolutePath);

						Map<String, Object> retDoc = new HashMap<String, Object>(respuesta);
						List<Map> valorRespuesta = (List<Map>) retDoc.get("RETURN");
						
						//System.out.println("Valor respuesta: " + valorRespuesta.get(0).get("COD"));
						
						if (valorRespuesta.get(0).get("COD").equals("0") ) {
							
							new Notification("Error ¡¡¡. Se ha producido un error al guardar el documento",
									(String) valorRespuesta.get(0).get("MSG").toString(),
									Notification.Type.ERROR_MESSAGE, true)
									.show(Page.getCurrent());
						}
						else {
							
							ruta = valorRespuesta.get(0).get("RUTA").toString();
							carga = valorRespuesta.get(0).get("CDCARGA").toString();
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					

					
					// Finalmente subimos el fichero
					
					//System.out.println("1111.SUbimos el fichero");
					uploadbox.upload.submitUpload();
					//System.out.println("2222.Después de subir el fichero");
					
				/*} catch (CommitException e) {
	
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
					
					
					
					*/
	
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
								
				
			
				
				
			}
		});			
	        
        		
		// RECUPERAMOS LAS LISTAS DE VALORES
		
		tfOrigen.addItem(1);
		tfOrigen.setItemCaption(1,"UNO");

		tfEstancias.addItem(1);
		tfEstancias.setItemCaption(1,"UNO");
		
		tfAntesDespues.addItem(1);
		tfAntesDespues.setItemCaption(1,"UNO");		
		
		
		// Cargamos las listas
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		HashMap respuesta = null;
		try {
			PAC_SHWEB_LISTAS llamadaquery = new PAC_SHWEB_LISTAS(service.plsqlDataSource.getConnection());
			String sql = "SELECT cdapartado tipo, descripcion atributo, cdvalor valorc " +
		              " FROM geuah_valor_apartado where cdapartado IN (  'C1','C2','C3')" +
		              " UNION " +
					  "select TIPO, ATRIBUTO, VALORC from shweb_valoresfijos " + 
						" where tipo in ( 'GEDTIPOFICHERO','GEDASEGPERJ','GEDTIPODOC') " +
						" union " +
						" SELECT 'DOC' tipo, nombre ATRIBUTO, id_tipodocumento valorc FROM SIUAH_TIPO_DOCUMENTO " +
						" ORDER BY TIPO" ;
			respuesta = llamadaquery.ejecutaPAC_SHWEB_LISTAS__F_QUERY(sql) ;
			
			Map<String, Object> retDoc = new HashMap<String, Object>(respuesta);
			List<Map> valorRespuesta = (List<Map>) retDoc.get("RETURN");			

			//System.out.println("Valor respuesta:" + valorRespuesta);
			
			tfOrigen.removeAllItems();
			tfEstancias.removeAllItems();
			tfAntesDespues.removeAllItems();
			tfTipoDocumento.removeAllItems();
			tfAsegPerj.removeAllItems();
			for (int i = 0; i < valorRespuesta.size(); i++) {
				//System.out.println("Atribbuto: " + valorRespuesta.get(i).get(("ATRIBUTO")));
				//System.out.println("Valor: " + valorRespuesta.get(i).get(("VALORC")));
				
				if ( valorRespuesta.get(i).get(("TIPO")).equals("DOC")  )
				{
					tfOrigen.addItem(valorRespuesta.get(i).get(("VALORC")));
					tfOrigen.setItemCaption(valorRespuesta.get(i).get(("VALORC")),valorRespuesta.get(i).get(("ATRIBUTO")).toString());
				}
				if ( valorRespuesta.get(i).get(("TIPO")).equals("C1")  )
				{
					tfEstancias.addItem(valorRespuesta.get(i).get(("VALORC")));
					tfEstancias.setItemCaption(valorRespuesta.get(i).get(("VALORC")),valorRespuesta.get(i).get(("ATRIBUTO")).toString());
				}
				if ( valorRespuesta.get(i).get(("TIPO")).equals("C3")  )
				{
					tfAsegPerj.addItem(valorRespuesta.get(i).get(("VALORC")));
					tfAsegPerj.setItemCaption(valorRespuesta.get(i).get(("VALORC")),valorRespuesta.get(i).get(("ATRIBUTO")).toString());
				}
				if ( valorRespuesta.get(i).get(("TIPO")).equals("GEDTIPODOC")  )
				{
					tfTipoDocumento.addItem(valorRespuesta.get(i).get(("VALORC")));
					tfTipoDocumento.setItemCaption(valorRespuesta.get(i).get(("VALORC")),valorRespuesta.get(i).get(("ATRIBUTO")).toString());
				}
				if ( valorRespuesta.get(i).get(("TIPO")).equals("C2")  )
				{
					tfAntesDespues.addItem(valorRespuesta.get(i).get(("VALORC")));
					tfAntesDespues.setItemCaption(valorRespuesta.get(i).get(("VALORC")),valorRespuesta.get(i).get(("ATRIBUTO")).toString());
				}					
				

			}
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
    private StreamResource createResource() {
    	return new StreamResource(new StreamSource() {
            @Override
            public InputStream getStream() {

            	
				Item item = tbGed.getItem(tbGed.getValue());
				
				String fichero = item.getItemProperty("Url").getValue().toString();
				//System.out.println("provee: " + tfOrigen.getValue().toString() + " - > Subimos el fichero" + fichero);

				fichero = "c:/basura/telefono.png";
				//String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
				//Resource res = new FileResource(new File(fichero));
				byte[] fileContent;
				try {
					fileContent = Files.readAllBytes(new File(fichero).toPath());
					return new ByteArrayInputStream(fileContent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					new Notification("Error al descargar el fichero " + fichero,
							"Razón: " + e.getMessage(),
							Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());
					e.printStackTrace();
				}
				return null;
		        }
    	}, "myImage.png");

  
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
		

		
	}
	
	
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
		    	  //ruta = "c:/basura/mupiticarga.txt";
		    	  //System.out.println("1. provee: " + UsuarioSave.substring(5) + "  ---> SUbimos el fichero: "+ruta);
		    	  
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

				uploadbox.upload.setEnabled(true);
				
				//LlenarTabla();
				
				tfOrigen.setValue(null);
				tfDescripcion.setValue(null);
				tfNombreFichero.setValue(null);
				
				
			    new Notification("Fichero subido correctamente.",
						event.getFilename().toString(),
						Notification.Type.TRAY_NOTIFICATION, true)
						.show(Page.getCurrent());
			    
			    pantallaConsultaExpediente.abrirDocumentos = "SI";
				close();			      
		  }
		
		  @Override
		  public void uploadFailed(FailedEvent event) {
		      Notification.show("Error al subir el fichero "+event.getFilename().toString(),
		                        Notification.Type.ERROR_MESSAGE);

		          
		  }
		  

		
    }
    
	  public void LlenarTabla() {
			//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< LLENAMOS TABLA EXPEDIENTE:" + UI.getCurrent().getSession().getAttribute("expediente").toString());
		  
			service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
			
		    tbGed.removeAllItems();
			// Consultamos la documentacion
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
			}
			
			
			if ( retornoGed!=null && retornoGed.get("DOCUMENTACION")!=null ) {
			
				List<Map> valor = (List<Map>) retornoGed.get("DOCUMENTACION");	;
				
				String cdcarga = "0";

				Item row1 = null;
				for (Map map : valor) {

					//System.out.println("Carga:" + cdcarga + " CDGARGA: " + map.get("CDCARGA").toString());
					if ( cdcarga.equals("0") || !map.get("CDCARGA").toString().equals(cdcarga) ) {
						//System.out.println("nueva linea");
						Object newItemId = tbGed.addItem();
						row1 = tbGed.getItem(newItemId);
					}
					cdcarga = map.get("CDCARGA").toString();
					
					if ( map.get("CDDOCID")!=null ) {
						row1.getItemProperty("Descripcion").setValue(map.get("TXOBSERVACIONES"));
					}
					if ( map.get("PATH_DOC")!=null ) {
						row1.getItemProperty("Url").setValue(map.get("PATH_DOC").toString());
					}
					if ( map.get("FECARGA")!=null ) {
						row1.getItemProperty("Fecha").setValue(map.get("FECARGA"));
					}
					
					row1.getItemProperty("Tipo").setValue(map.get("TIPODOCUMENTO"));

					if ( map.get("C1")!=null ) {
						row1.getItemProperty("Estancias").setValue(map.get("C1"));
					}
					if ( map.get("C2")!=null ) {
						row1.getItemProperty("Antdes").setValue(map.get("C2"));
					}					
					if ( map.get("C3")!=null ) {
						row1.getItemProperty("Asegperj").setValue(map.get("C3"));
					}										
											


				}	
			}
	  }    


}