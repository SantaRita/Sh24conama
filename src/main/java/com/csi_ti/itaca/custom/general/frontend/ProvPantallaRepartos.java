
package com.csi_ti.itaca.custom.general.frontend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.csi_ti.itaca.architecture.tools.webmodule.componentes.ContenedorDeComponentes;
import com.csi_ti.itaca.architecture.tools.webmodule.componentes.WrapperComponentContainer;
import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.PantallaBaseConInputOutput;
import com.csi_ti.itaca.custom.general.api.model.Generico0;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_REPARTOS_PROVEEDOR;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_LISTAS;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
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
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
@SpringUI
public class ProvPantallaRepartos extends PantallaBaseConInputOutput<VerticalLayout, Void, Void>{
	
	
	String setAttribute = "txt[i].setAttribute('autocomplete', 'off');";
	
	UploadBox uploadbox ;
	String ruta;
	
	public String UsuarioSave;
	
    protected File tempFile;

	
	Map<String, Object> parameters = new HashMap<String, Object>();

	
	Connection conn = null;
	private String url = UI.getCurrent().getSession().getAttribute("url").toString();		
	
	private TextField tfNombreFichero; 
	private Button btCargar;
	private Button btProcesar;
	private Button btOtro;
	private FieldGroup binder;
	private FormLayout flayout;
	private HorizontalLayout hlayout;
	private FormLayout flayout2;
	private FormLayout flayout3;
	private HorizontalLayout flayout4;
	
	GridLayout gl;
	int primeraEntrada = 0;
	
	HorizontalLayout panelAlta = new HorizontalLayout();
	HorizontalLayout hBotonera = new HorizontalLayout();
	
	
	private static final long serialVersionUID = 4440733679587692241L;
	
	Table tableexp;
	ComboBox cbCliente;
	ComboBox cbCarga;


	@Autowired
	private GeneralBusinessServiceImpl service;
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		String args[] = event.getParameters().split("/");
		
		System.out.println(UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase() + " < El usuario es : " + args[0] );
		UsuarioSave = args[0];
		
		if ( UsuarioSave == null || UsuarioSave == "") {
			UsuarioSave = UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase() ;
		}
		
				
		
		
		tableexp.setPageLength(0);
		tableexp.setEnabled(true);
		//tableexp.setVisible(false);
		
		PropertysetItem item = new PropertysetItem();
		item.addItemProperty("expediente", new ObjectProperty<String>(""));
		
		
		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);
	

	}	
	
	

	
	@Override
	protected ContenedorDeComponentes<VerticalLayout> crearCuerpo() {
		
		
		cbCliente = new ComboBox("Cliente:");
		cbCliente.setWidth("400px");
		cbCliente.setFilteringMode(FilteringMode.CONTAINS);
		cbCliente.setRequired(true);
		
		cbCarga = new ComboBox("Modo de carga:");
		cbCarga.setWidth("100px");
		
		uploadbox  = new UploadBox();
		
		tfNombreFichero =  new TextField();
		
		
		//System.out.println("Contenedor de componentes");
		
		HashMap respuesta = null;
		
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		
		System.out.println("------------Llamamos a obetener conecion");
		PAC_SHWEB_LISTAS llamadaListas = null;
		try {
			llamadaListas = new PAC_SHWEB_LISTAS(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("------------- DESPUES Llamamos a obetener conecion");
		respuesta = null;
		try {
			respuesta = llamadaListas.ejecutaPAC_SHWEB_LISTAS__F_GET_LSTCOMPANIAS("-1");
			Map<String, Object> retListas= new HashMap<String, Object>(respuesta);
			List<Map> valorRespuesta = (List<Map>) retListas.get("RETURN");
			
			System.out.println("valorRespuesta: " + valorRespuesta);
			
			
			cbCliente.removeAllItems();
			for (Map map : valorRespuesta) {
				cbCliente.addItem(map.get("RGCLIEN"));
				cbCliente.setItemCaption(map.get("RGCLIEN"),map.get("DSCLIENTE").toString() + " [" + map.get("RGCLIEN") + "]");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbCarga.removeAllItems();
		cbCarga.addItem("N");
		//cbCarga.addItem("A");
		//cbCarga.addItem("M");
		cbCarga.setItemCaption("N","Nuevo");
		//cbCarga.setItemCaption("A","Añadir");
		//cbCarga.setItemCaption("M","Modificar");
		cbCarga.setValue("N");
		cbCarga.setEnabled(false);
		
		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(false);
		
		
		
		tableexp = new Table()  {
			@Override
			protected String formatPropertyValue(Object rowId,
					Object colId, Property property) {
				// Format by property type
				if (property.getType() == Date.class) {
					
					if (property.getValue() == null) {
						return null;
					}
					SimpleDateFormat df =
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					return df.format((Date)property.getValue());
				}
				
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		




		hlayout = new HorizontalLayout();
		hlayout.setMargin(false);
		hlayout.setSpacing(false);
		
		
		flayout = new FormLayout();
		flayout.addComponent(cbCliente);
		flayout.addComponent(cbCarga);
		flayout.addComponent(uploadbox);

		tfNombreFichero.setCaption("NombreFichero");
	    flayout.addComponent(tfNombreFichero);	
	    tfNombreFichero.setWidth("100%");
	    tfNombreFichero.setEnabled(false);
	    tfNombreFichero.setRequired(true);
	    tfNombreFichero.setNullRepresentation("");
		
		hlayout.setCaption("<b>CARGA FICHERO REPARTOS</b>");
		hlayout.setCaptionAsHtml(true);
		hlayout.setStyleName("panel-detalle");
 
		
		Util.addComponentsToLayoutInHorizontal(layout, Alignment.TOP_LEFT, "cuadrosinbarras", hlayout);
		cbCliente.focus();
		
		// BOTONERA
		
		btProcesar = new Button("Procesar Registros Cargados");
		btProcesar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		btProcesar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				// **********************************************************
				// PROCESAR FICHERO
				// **********************************************************

		        try {    
			        service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
					System.out.println("------------Llamamos a obetener conecion");
					PAC_REPARTOS_PROVEEDOR guardarDoc = null;
		   			guardarDoc = new PAC_REPARTOS_PROVEEDOR(service.plsqlDataSource.getConnection());
		   			  
		   			System.out.println("La sesion es: " + VaadinSession.getCurrent().getSession().getId().toString());
	
		   			HashMap respuesta = null;
		   			  
		   		
					respuesta = guardarDoc.ejecutaPAC_REPARTOS_PROVEEDOR__F_PROCESAR_FMAIN(
							  VaadinSession.getCurrent().getSession().getId().toString()
							  );
							  
	
					  Map<String, Object> retDoc = new HashMap<String, Object>(respuesta);
					  BigDecimal valorRespuesta = (BigDecimal) retDoc.get("CODIGOERROR");
					
		
					
					  if (valorRespuesta.equals(new BigDecimal("0")) ) {
	
						  
						  Notification note = new Notification("Datos cargados correctamente.",
									"Haga click para continuar",
									Notification.Type.WARNING_MESSAGE, true);
						  note.setDelayMsec(-1);
						  note.show(Page.getCurrent());		    	        				
									  
					  }
					  else {
						
					      Notification.show("["  +valorRespuesta +"] - Error al procesar los registros " ,
			                        Notification.Type.ERROR_MESSAGE);
					  }
					

		          

			        
		        } catch ( Exception e) {
				  Notification.show("Error al procesar los datos de la tabla"+e.getMessage(),
		                        Notification.Type.ERROR_MESSAGE);
		          e.printStackTrace();
		        }
			}
		});		
		
		btCargar = new Button("Cargar Fichero");
		btCargar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		GridLayout hlbot = new GridLayout(4,1);
		hlbot.setMargin(true);
		hlbot.setWidth("100%");
		hlbot.addComponent(btCargar,1,0);
		
		hlbot.addComponent(btProcesar,3,0);
		btProcesar.setVisible(false);
		
		btOtro = new Button("Cargar otro fichero");
		btOtro.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		hlbot.addComponent(btOtro,0,0);
		btOtro.setVisible(false);
		
		
		
		
		flayout.setWidth("100%");
		flayout.setMargin(true);
		flayout.setSpacing(true);
		layout.addComponent(flayout);
		
		layout.addComponent(hlbot);
		
		Button btFocus = new Button();
		hlbot.setComponentAlignment(btCargar, Alignment.MIDDLE_RIGHT);
		hlbot.setColumnExpandRatio(0, 90);
		hlbot.setColumnExpandRatio(1, 5);
		hlbot.setColumnExpandRatio(2, 5);
		
		// TABLAS
		layout.setMargin(false);
		String[] columnsexp ={"RGTPRED","TXRED","CDSERVIC","TXSERVIC","RGAMBITO","TXAMBITO","CDPROVEE","TXPROVEEDOR","PCTREPARTO","FECARGA","CDUSUCARGA","RGCLIEN"};
		Object[] typesexp = {java.math.BigDecimal.class, String.class, java.math.BigDecimal.class, String.class, java.math.BigDecimal.class,String.class,String.class,String.class,java.math.BigDecimal.class,Date.class,String.class,java.math.BigDecimal.class};
		Object[] visibleColumnsexp = 
				new Object[]{"RGTPRED","TXRED","CDSERVIC","TXSERVIC","RGAMBITO","TXAMBITO","CDPROVEE","TXPROVEEDOR","PCTREPARTO","FECARGA","RGCLIEN"};
		Util.defineTable(tableexp, columnsexp, typesexp, visibleColumnsexp,true);
		tableexp.setColumnHeaders(new String[] {"RGTPRED","TXRED","CDSERVIC","TXSERVIC","RGAMBITO","TXAMBITO","CDPROVEE","TXPROVEEDOR","PCTREPARTO","FECARGA","RGCLIEN"});
		
		tableexp.setColumnExpandRatio("RGTPRED", 5);
		tableexp.setColumnExpandRatio("TXRED", 15);
		tableexp.setColumnExpandRatio("CDSERVIC",5 );
		tableexp.setColumnExpandRatio("TXSERVIC", 15);
		tableexp.setColumnExpandRatio("RGAMBITO", 5);
		tableexp.setColumnExpandRatio("TXAMBITO",15 );
		tableexp.setColumnExpandRatio("TXPROVINCIA", 15);
		tableexp.setColumnExpandRatio("CDPROVEE", 5);
		tableexp.setColumnExpandRatio("TXPROVEEDOR", 15);
		tableexp.setColumnExpandRatio("PCTREPARTO", 5);
		tableexp.setColumnExpandRatio("FECARGA", 5);
		tableexp.setColumnExpandRatio("CDUSUCARGA", 5);
		tableexp.setColumnExpandRatio("RGCLIEN", 5);


		tableexp.setTabIndex(-1);
		
		tableexp.setColumnAlignment("RGTPRED", Align.RIGHT);
		tableexp.setColumnAlignment("CDSERVIC", Align.RIGHT);
		tableexp.setColumnAlignment("RGAMBITO", Align.RIGHT);
		tableexp.setColumnAlignment("PCTREPARTO", Align.RIGHT);
		tableexp.setColumnAlignment("RGCLIEN", Align.RIGHT);
		
		tableexp.setPageLength(20);

		VerticalLayout vtable = new VerticalLayout();
		
		vtable.setMargin(true);
		vtable.addComponent(tableexp);
		layout.setMargin(false);
		layout.setStyleName("expediente-panel-busqueda");
		layout.addComponent(vtable);
		layout.setComponentAlignment(vtable, Alignment.MIDDLE_CENTER);		
		
		
		layout.setExpandRatio(vtable, 1);
		
		
		// NAVEGACION CAMPOS
		cbCliente.setTabIndex(1);
		btCargar.setTabIndex(13);
		btFocus.setTabIndex(14);
		btFocus.setStyleName("botoninvisible");
		
		btFocus.addFocusListener(new FocusListener() {
			
			@Override
			public void focus(FocusEvent event) {
				// TODO Auto-generated method stub
				cbCliente.focus();
				
			}
		});
		
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
		
        final StreamResource stream = new StreamResource(
                new StreamSource() {
                    @Override
                    public InputStream getStream() {
                    	System.out.println("Dentro del stream");

        				Item item = tableexp.getItem(tableexp.getValue());
        				
        				String fichero = item.getItemProperty("Url").getValue().toString();
        				System.out.println("Subimos el fichero" + fichero);

        				
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
        
        
        btOtro.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				UI.getCurrent().getNavigator().navigateTo("ProvPantallaRepartos");	 
			}
		});
        
        
		btCargar.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				
				try {
					
					String mensaje = "";

					
					if ( cbCliente.getValue() == null ) {
						mensaje += "Es obligatorio introducir un cliente";
					}

					File file = new File(tfNombreFichero.getValue().toString());
					String absolutePath = file.getName();
					int idx = file.getName().lastIndexOf("\\");
					absolutePath = file.getName().substring(idx + 1);

					//System.out.println("Fichero uploadbox: " + uploadbox.filename);
					//if ( cbCliente.getValue() == null ) mensaje += "Falta introducir el tipo de Fichero!\n";
					if ( absolutePath==null || absolutePath.equals("")
							 ) mensaje += "Seleccione un fichero\n";
					
					
					
					System.out.println(" Los mensajes son " + mensaje);
					if ( !mensaje.equals("")) {
						
						new Notification("Campos obligatorios",
								mensaje,
								Notification.Type.TRAY_NOTIFICATION, true)
								.show(Page.getCurrent());
						return;
					}
					
					
					Generico0 doc;
					Integer xsubtipo ;
	
					System.out.println("2.El fichero es:"+ absolutePath);
					
					uploadbox.upload.submitUpload();
					
					
					

	
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
								
				
			
				
				
			}
		});        
        

		
		
		
		return new WrapperComponentContainer<VerticalLayout>(layout);
	}
	
	
	
	
	
	@Override
	protected void enlazarDatos() {
		// TODO Auto-generated method stub
		
	}
	

	
    @UIScope
    class UploadBox extends CustomComponent
		    implements Receiver, ProgressListener,
		               FailedListener, SucceededListener {
		  private static final long serialVersionUID = -46336015006190050L;
		
		  // Put upload in this memory buffer that grows automatically
		  ByteArrayOutputStream os =
		      new ByteArrayOutputStream(10240);
		  
		  VerticalLayout panelContent;
		
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
		      
		      
		      panelContent = new VerticalLayout();
		      
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
			  System.out.println("Entreamos a receiveUpload:" + filename);
			  
			  FileOutputStream fos = null;
			  
		      this.filename = filename;
		      os.reset(); // Needed to allow re-uploading
		      
		      //fichero = new File(tfNombreFichero.getValue().toString());
		      try {
		          // Open the file for writing.
		    	  /// System.out.println("SUbimos el fichero: "+ruta);
		          //fos = new FileOutputStream(ruta);
		    	  tempFile = File.createTempFile("temp_" + UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase(), ".csv");
		          return new FileOutputStream(tempFile);		    	  
		      } catch (final java.io.FileNotFoundException e) {
		          // Error while opening the file. Not reported here.
		          e.printStackTrace();
		          return null;
		      } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

			try {
				  
	          /* Let's build a container from the CSV File */
	          FileReader lector = new FileReader(tempFile);
	          
	          System.out.println("Tamaño del fichero:" + tempFile.getName() + " " + tempFile.length());
	          System.out.println("El fichero reader" + lector.toString());
	          System.out.println("Leemos un poco" + lector.read() );
	          

	          // fin leer clob;

	          
	          service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
			  System.out.println("------------Llamamos a obetener conecion");
			  PAC_REPARTOS_PROVEEDOR guardarDoc = null;
   			  guardarDoc = new PAC_REPARTOS_PROVEEDOR(service.plsqlDataSource.getConnection());
   			  
   			  System.out.println("La sesion es: " + VaadinSession.getCurrent().getSession().getId().toString());

   			  HashMap respuesta = null;
   			  
   			  BigDecimal cliente = new BigDecimal("0");
   			  if (cbCliente.getValue() != null) {
   				  cliente = new BigDecimal(cbCliente.getValue().toString());
   			  }
   			  
			  respuesta = guardarDoc.ejecutaPAC_REPARTOS_PROVEEDOR__F_CARGAR_FICHERO(
					  lector,
					  VaadinSession.getCurrent().getSession().getId().toString(),
					  UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase()
					  ,cliente);
					  

			  Map<String, Object> retDoc = new HashMap<String, Object>(respuesta);
			  BigDecimal valorRespuesta = (BigDecimal) retDoc.get("CODIGOERROR");
			  String textoError = (String) retDoc.get("TEXTOERROR");
			
			  lector.close();
			  
			  tfNombreFichero.setValue("");
			  
			  if (valorRespuesta.equals(new BigDecimal("0")) ) {

				  btProcesar.setVisible(true);
				  btCargar.setVisible(false);
				  cbCliente.setVisible(false);
				  cbCarga.setVisible(false);
				  upload.setVisible(false);
				  tfNombreFichero.setVisible(false);
				  btOtro.setVisible(true);
				  progress.setVisible(false);
				  panelContent.setVisible(false);
				  CargarTabla(VaadinSession.getCurrent().getSession().getId().toString() );
			  }
			  else {
				
			      Notification.show("["  +valorRespuesta +"] - Error al validar el fichero. \nRectifique el fichero y vuelva a cargarlo.\n\n "+textoError,
	                        Notification.Type.ERROR_MESSAGE);
				  UI.getCurrent().getNavigator().navigateTo("ProvPantallaRepartos");	 

			  }
				

	          

		        
	        } catch ( Exception e) {
			  Notification.show("Error al subir/procesar el fichero "+event.getFilename().toString(),
	                        Notification.Type.ERROR_MESSAGE);
	          e.printStackTrace();
    			UI.getCurrent().getNavigator().navigateTo("ProvPantallaRepartos");	 

	        }
				
				
			new Notification("Fichero subido correctamente.",
						event.getFilename().toString(),
						Notification.Type.TRAY_NOTIFICATION, true)
						.show(Page.getCurrent());
		  }
		
		  @Override
		  public void uploadFailed(FailedEvent event) {
		      Notification.show("Ha habido un Error al subir el fichero "+event.getFilename().toString(),
		                        Notification.Type.ERROR_MESSAGE);
		  }




		  protected IndexedContainer buildContainerFromCSV(Reader reader) throws IOException {
			    IndexedContainer container = new IndexedContainer();
			    /*CSVReader csvReader = new CSVReader(reader);
			    String[] columnHeaders = null;
			    String[] record;
			    while ((record = csvReader.readNext()) != null) {
			      if (columnHeaders == null) {
			        columnHeaders = record;
			        addItemProperties(container, columnHeaders);
			      } else {
			        addItem(container, columnHeaders, record);
			      }
			    }*/
			    return container;
			  }
	}
    
    private void CargarTabla ( String sesion ) {
		// Cargamos las listas
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		HashMap respuesta = null;
		try {
			PAC_SHWEB_LISTAS llamadaquery = new PAC_SHWEB_LISTAS(service.plsqlDataSource.getConnection());
			String sql = "SELECT rgtpred, txred, cdservic, txservic, rgambito, txambito, cdprovee, txproveedor, pctreparto, rgclien, sesion, fecarga  "
					+ " from tmp_carga_red_reparto_pv "
					+ " where rownum < 100 and sesion = '" + VaadinSession.getCurrent().getSession().getId().toString() + "'";
			respuesta = llamadaquery.ejecutaPAC_SHWEB_LISTAS__F_QUERY(sql) ;
			
			Map<String, Object> retDoc = new HashMap<String, Object>(respuesta);
			List<Map> valorRespuesta = (List<Map>) retDoc.get("RETURN");			

			//System.out.println("Valor respuesta:" + valorRespuesta);
			
			Item row1 = null;
			tableexp.removeAllItems();
			for (Map map : valorRespuesta) {
				
				//if ( valorRespuesta.get(i).get(("TIPO")).equals("DOC")  )
				Object newItemId = tableexp.addItem();
				row1 = tableexp.getItem(newItemId);
				
				if ( map.get("RGTPRED")!=null ) row1.getItemProperty("RGTPRED").setValue(map.get("RGTPRED"));
				if ( map.get("TXRED")!=null ) row1.getItemProperty("TXRED").setValue(map.get("TXRED"));
				if ( map.get("CDSERVIC")!=null ) row1.getItemProperty("CDSERVIC").setValue(map.get("CDSERVIC"));
				if ( map.get("TXSERVIC")!=null ) row1.getItemProperty("TXSERVIC").setValue(map.get("TXSERVIC"));
				if ( map.get("RGAMBITO")!=null ) row1.getItemProperty("RGAMBITO").setValue(map.get("RGAMBITO"));
				if ( map.get("TXAMBITO")!=null ) row1.getItemProperty("TXAMBITO").setValue(map.get("TXAMBITO"));
				if ( map.get("CDPROVEE")!=null ) row1.getItemProperty("CDPROVEE").setValue(map.get("CDPROVEE"));
				if ( map.get("TXPROVEEDOR")!=null ) row1.getItemProperty("TXPROVEEDOR").setValue(map.get("TXPROVEEDOR"));
				if ( map.get("PCTREPARTO")!=null ) row1.getItemProperty("PCTREPARTO").setValue(map.get("PCTREPARTO"));
				if ( map.get("FECARGA")!=null ) row1.getItemProperty("FECARGA").setValue(map.get("FECARGA"));
				if ( map.get("CDUSUCARGA")!=null ) row1.getItemProperty("CDUSUCARGA").setValue(map.get("CDUSUCARGA"));
				if ( map.get("RGCLIEN")!=null ) row1.getItemProperty("RGCLIEN").setValue(map.get("RGCLIEN"));
	
				System.out.println("x");
			}
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    Notification.show("Ha habido un Error al cargar los datos de la tabla" ,
                      Notification.Type.ERROR_MESSAGE);			
		}
		System.out.println("Nos vamos");
    }


	

	
}