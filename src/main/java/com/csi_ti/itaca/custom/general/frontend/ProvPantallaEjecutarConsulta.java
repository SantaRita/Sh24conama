package com.csi_ti.itaca.custom.general.frontend;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_CONSULTASAMA;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_LISTAS;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
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
public class ProvPantallaEjecutarConsulta extends Window implements ItacaView {
    int cuantasConsultas = 0;
    int cuantosSegundos = 0;
    int hora = 0;
    String[] horario = new String[24];
	public String abrirDocumentos = "";
	public static String valoresDevueltos = "";
	protected FileDownloader downloader;
	StreamResource resource;
	public String UsuarioSave;
	Long fechaNumerica = new Date().getTime();
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
	String strDate;
	Table tableexp  = new Table() {
		@Override
		protected String formatPropertyValue(Object rowId, Object colId, Property property) {
			// Format by property type
			if (property.getType() == Date.class) {

				if (property.getValue() == null) {
					return null;
				}
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				return df.format((Date) property.getValue());
			}

			return super.formatPropertyValue(rowId, colId, property);
		}
	};
	String parametros = "";
	
	//public static String resultadoRegistros = "";
	
	public String[] idParametros = new String[15];
	
	CheckBox ckSql = new CheckBox("Visualizar consulta");
	
	public TabSheet tabExp = new TabSheet();
	
	@Autowired
	private static GeneralBusinessServiceImpl service;
	
	int cuantosParametros;
	
	public VerticalLayout vLayoutParam = new VerticalLayout();
	
	public TextField par1 = new TextField();
	public TextField par2 = new TextField();
	public TextField par3 = new TextField();
	public TextField par4 = new TextField();
	public TextField par5 = new TextField();
	public TextField par6 = new TextField();
	public TextField par7 = new TextField();
	public TextField par8 = new TextField();
	public TextField par9 = new TextField();
	public TextField par10 = new TextField();
	
	public TextArea consulta = new TextArea("Consulta");
	
	public HorizontalLayout horizontalPar1 = new HorizontalLayout();
	public HorizontalLayout horizontalPar2 = new HorizontalLayout();
	public HorizontalLayout horizontalPar3 = new HorizontalLayout();
	public HorizontalLayout horizontalPar4 = new HorizontalLayout();
	public HorizontalLayout horizontalPar5 = new HorizontalLayout();
	public HorizontalLayout horizontalPar6 = new HorizontalLayout();
	public HorizontalLayout horizontalPar7 = new HorizontalLayout();
	public HorizontalLayout horizontalPar8 = new HorizontalLayout();
	public HorizontalLayout horizontalPar9 = new HorizontalLayout();
	public HorizontalLayout horizontalPar10 = new HorizontalLayout();
	

	
	//ProvVenDocumentacion proven;
	Map<String, Object> retornoGed;
	

	UploadBox uploadbox = new UploadBox();
	String ruta;
	
	Button btEjecutar = new Button("Ejecutar Consulta");
	Button btVolver = new Button("Volver");
	Button btImprimir = new Button("Imprimir Consulta generada");

	
	VerticalLayout layoutEjecutar = new VerticalLayout();
	VerticalLayout tabPresupuestos = new VerticalLayout();
	
	public Button btGed = new Button("Documentación(GED)");		
	
	VerticalLayout layoutPrincipal = new VerticalLayout();
	

	
	public ProvPantallaEjecutarConsulta ( ProvPantallaBusquedaDocumentos provPantallaBusquedaDocumentos, GeneralBusinessServiceImpl service) {
		
	
		//System.out.println("Inicializamos la clase ProvPantallaEjecutarConsulta");
		//UsuarioSave = UI.getCurrent().getSession().getAttribute("usuariosave").toString();
		
		setModal(true);
        
        setWidth("95%");
        setHeight("98%");
        
        setClosable(true);
        setResizable(false);
        setStyleName("ventanamodal");
		
		//service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		
		//System.out.println("El service detalle es : "  + service);
		//setStyleName("panel-detalle");
		setCaption("EJECUTAR CONSULTA:º " + UI.getCurrent().getSession().getAttribute("Descripcion")
				+ " ( " + 
				UI.getCurrent().getSession().getAttribute("Idconsulta") + " ) ");
		
		
		String[] columnsexp = { "Concepto", "Detalle" };
		Object[] typesexp = { String.class, GridLayout.class };
		Object[] visibleColumnsexp = new Object[] { "Concepto", "Detalle" };
		Util.defineTable(tableexp, columnsexp, typesexp, visibleColumnsexp, true);
		tableexp.setColumnHeaders(
				new String[] { "Concepto","Detalle"});

		tableexp.setColumnExpandRatio("Concepto", 20 );
		tableexp.setColumnExpandRatio("Detalle",  80 );
		tableexp.setPageLength(2);
		tableexp.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		tableexp.setTabIndex(-1);	
		

		
		// ESTADISTICA
		
		try {
			PAC_SHWEB_CONSULTASAMA llamada2 = new PAC_SHWEB_CONSULTASAMA(service.plsqlDataSource.getConnection());
			
			HashMap respuesta = null;

			respuesta = llamada2.ejecutaPAC_SHWEB_CONSULTASAMA__F_ESTADISTICA(new BigDecimal(UI.getCurrent().getSession().getAttribute("Idconsulta").toString()));
			
		
			List<Map> valor = (List<Map>) respuesta.get("REGISTROS");
			System.out.println("ESTADISTICA: " + valor);
			
			int cont = 0;
			
			cuantasConsultas = 0;
			
			horario[10] = "A";
			horario[11] = "A";
			horario[12] = "A";
			horario[13] = "A";
			horario[14] = "A";
			
			try {
				if ( valor!=null) {
					for (Map map : valor) {
						
						cuantosSegundos += ((BigDecimal)map.get("SEGUNDOS")).intValue();
						cuantasConsultas += ((BigDecimal)map.get("CUANTOS")).intValue();
						System.out.println("Hora:" + map.get("HORA"));
						hora = Integer.valueOf((String)map.get("HORA"));
	
						System.out.println("Cuantos segundos: " + cuantosSegundos );
						
						if  ( cuantosSegundos > 60 ) {
							System.out.println("Amarillo hora " + hora);
							horario[hora] = "A";
						}
						
						/*if ( cuantosSegundos > 120 ) {
							System.out.println("Rojo hora " + hora);
						    horario[hora] = "R";
						}*/
						
					}
				}		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			
			try {
				PAC_SHWEB_LISTAS llamada3 = new PAC_SHWEB_LISTAS(service.plsqlDataSource.getConnection());
				
				HashMap respuesta3 = null;

				respuesta = llamada3.ejecutaPAC_SHWEB_LISTAS__F_QUERY(
						"SELECT valor " 
						+ " FROM exuah_paramproc "
						+ " WHERE cdproceso = 'WEB_CONSULTAS_AMA' AND cdparam = 'BLOQUEO_HORAS'");
				
			
				List<Map> valor3 = (List<Map>) respuesta.get("RETURN");
				System.out.println("RETURN FQUERY: " + valor);
				
				int horaActual = new Date().getHours();
				Boolean horaBloqueada = false;
				//System.out.println("Que hora es: " +  horaActual);
				
				
				try {
					if ( valor!=null) {
						for (Map map : valor3) {
							
							System.out.println("Hora:" + map.get("VALOR"));
							
							
							String[] sHolder = map.get("VALOR").toString().split(",");
							for (int i=0; i < sHolder.length; i++)
							{
					        	System.out.println("Sholder:" + sHolder[i] + " hora actual: " + horaActual);
					        	if ( String.valueOf(horaActual).equals(sHolder[i]) ) {
									
					        		horaBloqueada = true;
									System.out.println("ACtivamos la Hora bloquedada");

									horario[Integer.valueOf(sHolder[i].toString())-1] = "R";
									
								}
									else { // Aunque no sea la hora actual ponemos la en rojo en la tabla
										horario[Integer.valueOf(sHolder[i].toString())-1] = "R";
								}
									
									
									
					        }
					        
					        
							
						}
					}		
				} catch (Exception e) {
					System.out.println("Error excepcion " + e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				System.out.println("Hora bloqueada: " + horaBloqueada);
				if ( horaBloqueada ) {
					
					new Notification("No se puede ejecutar la consulta",
							"La hora actual es crítica o se ha bloqueado para no permitir la ejecución de consultas.",
							Notification.Type.ERROR_MESSAGE, true)
							.show(Page.getCurrent());	
					
					btEjecutar.setVisible(false);
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("2.Error excepcion " + e);

				e.printStackTrace();
			}			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		// FIN ESTADISTICA
		
		

		// Número de ejecuciones de la consulta
		Object newItemId1 = tableexp.addItem();
		Item row1 = tableexp.getItem(newItemId1);
		row1.getItemProperty("Concepto").setValue("Nº ejecuciones de la consulta");
		
		GridLayout gridRow1 = new GridLayout(1,1);
		gridRow1.setSizeFull();
		gridRow1.addComponent(new Label(String.valueOf(cuantasConsultas)));
		row1.getItemProperty("Detalle").setValue(gridRow1);

		
		// Tiempo medio duración
		Object newItemId2 = tableexp.addItem();
		Item row2 = tableexp.getItem(newItemId2);
		row2.getItemProperty("Concepto").setValue("Duración media de la consulta");
		
		GridLayout gridRow2 = new GridLayout(1,1);
		gridRow2.setSizeFull();
		
		if ( cuantosSegundos > 0 && cuantasConsultas > 0 ) {
			Label labelgrid2 = new Label(String.valueOf((cuantosSegundos / cuantasConsultas)) + " segundos");
		
			labelgrid2.setSizeFull();
			labelgrid2.setWidth("300px");
			gridRow2.addComponent(labelgrid2);
			row2.getItemProperty("Detalle").setValue(gridRow2);
		}
		
		
		
		GridLayout grid = new GridLayout(24,3);
		grid.setSizeFull();
		

		
		
		
		
		for (int i=0;i<=23;i++) {
			String hora = String.valueOf(i+1);
			if ( hora.equals("24")) hora = "00";
			Label etiqueta = new Label(hora + ":00");
			etiqueta.setSizeFull();
			if ( i%2== 0)
				etiqueta.setStyleName("celda-gris");
			else
				etiqueta.setStyleName("celda-blanca");
			grid.addComponent(etiqueta,i,1);
			grid.setComponentAlignment(etiqueta, Alignment.MIDDLE_CENTER);
		}
		
		// celdas colores
		
		
		for (int i=0;i<=23;i++) {
			Label etiqueta = new Label("XXX");
			etiqueta.setSizeFull();
			if ( horario[i]!= null  && horario[i].equals("R") ) 
				etiqueta.setStyleName("celda-roja");
			else if  ( horario[i]!= null  && horario[i].equals("A") )

				etiqueta.setStyleName("celda-amarilla");			
			else
			{
					etiqueta.setStyleName("celda-verde");	
				}
				
			grid.addComponent(etiqueta,i,2);
			grid.setComponentAlignment(etiqueta, Alignment.MIDDLE_CENTER);
			

		}
		
		
		Label cabecera = new Label("Rendimiento horario para ejecutar la consulta");
		cabecera.setSizeFull();
		grid.addComponent(cabecera,0,0,23,0);
		cabecera.setStyleName("celda-blanca");
		grid.setComponentAlignment(cabecera, Alignment.MIDDLE_CENTER);
	
		
		// Recuperamos el presupuesto

		HashMap respuestaPresupuesto = null;
		HashMap respuesta = null;
		
		try {
			PAC_SHWEB_CONSULTASAMA llamada2 = new PAC_SHWEB_CONSULTASAMA(service.plsqlDataSource.getConnection());
			respuesta = llamada2.ejecutaPAC_SHWEB_CONSULTASAMA__F_LISTA_PARAMETROS(UI.getCurrent().getSession().getAttribute("Idconsulta").toString());
			
			//System.out.println("Respuesta detalle parametros:" + respuesta);
			
			// PANTALLA CON LOS PARÁMETROS
			

			
			
			// Recorremos los parámetros para ponerlos en un vertical layout
			
			par1.setVisible(false);
			par2.setVisible(false);
			par3.setVisible(false);
			par4.setVisible(false);
			par5.setVisible(false);
			par6.setVisible(false);
			par7.setVisible(false);
			par8.setVisible(false);
			par9.setVisible(false);
			par10.setVisible(false);
			
			List<Map> valor = (List<Map>) respuesta.get("REGISTROS");

			for (Map map : valor) {


				cuantosParametros ++;
				
				//System.out.println("Parametro: " + map.get("TXPARAM").toString() );
				
				idParametros[cuantosParametros] =  map.get("TXPARAM").toString();

				
				switch (cuantosParametros) {
				case 1:
					vLayoutParam.addComponent(new Label("Rellene los parámetros de la consulta ( TODOS SON OBLIGATORIOS ) :"));
					vLayoutParam.addComponent(new HorizontalRule());
					horizontalPar1.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					//par1.setCaption(map.get("TXNOMBRE").toString());
					horizontalPar1.addComponent(par1);
					horizontalPar1.setSpacing(true);
					horizontalPar1.setMargin(true);
					vLayoutParam.addComponent(horizontalPar1);
					par1.setRequired(true);
					par1.setVisible(true);
					break;
					
				case 2:
					horizontalPar2.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar2.addComponent(par2);
					horizontalPar2.setSpacing(true);
					horizontalPar2.setMargin(true);
					vLayoutParam.addComponent(horizontalPar2);
					par2.setRequired(true);
					par2.setVisible(true);
					break;					
					
				case 3:
					horizontalPar3.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar3.addComponent(par3);
					horizontalPar3.setSpacing(true);
					horizontalPar3.setMargin(true);
					vLayoutParam.addComponent(horizontalPar3);
					par3.setRequired(true);
					par3.setVisible(true);
					break;		
					
				case 4:
					horizontalPar4.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar4.addComponent(par4);
					horizontalPar4.setSpacing(true);
					horizontalPar4.setMargin(true);
					vLayoutParam.addComponent(horizontalPar4);
					par4.setRequired(true);
					par4.setVisible(true);
					break;
					
				case 5:
					horizontalPar5.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar5.addComponent(par5);
					horizontalPar5.setSpacing(true);
					horizontalPar5.setMargin(true);
					vLayoutParam.addComponent(horizontalPar5);
					par5.setRequired(true);
					par5.setVisible(true);
					break;
					
				case 6:
					horizontalPar6.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar6.addComponent(par6);
					horizontalPar6.setSpacing(true);
					horizontalPar6.setMargin(true);
					vLayoutParam.addComponent(horizontalPar6);
					par6.setRequired(true);
					par6.setVisible(true);
					break;
					
				case 7:
					horizontalPar7.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar7.addComponent(par2);
					horizontalPar7.setSpacing(true);
					horizontalPar7.setMargin(true);
					vLayoutParam.addComponent(horizontalPar7);
					par7.setRequired(true);
					par7.setVisible(true);
					break;
					
				case 8:
					horizontalPar8.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar8.addComponent(par8);
					horizontalPar8.setSpacing(true);
					horizontalPar8.setMargin(true);
					vLayoutParam.addComponent(horizontalPar8);
					par8.setRequired(true);
					par8.setVisible(true);
					break;
					
				case 9:
					horizontalPar9.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar9.addComponent(par9);
					horizontalPar9.setSpacing(true);
					horizontalPar9.setMargin(true);
					vLayoutParam.addComponent(horizontalPar9);
					par9.setRequired(true);
					par9.setVisible(true);
					break;
					
				case 10:
					horizontalPar10.addComponent(new Label(map.get("TXNOMBRE").toString() + ":   "));
					horizontalPar10.addComponent(par10);
					horizontalPar10.setSpacing(true);
					horizontalPar10.setMargin(true);
					vLayoutParam.addComponent(horizontalPar10);
					par10.setRequired(true);
					par10.setVisible(true);
					break;
					
				default:
					break;
				}

			}
			
			if ( cuantosParametros == 0 ) {
				vLayoutParam.addComponent(new Label("Consulta SIN parámetros"));

			}
			
			consulta.setValue(UI.getCurrent().getSession().getAttribute("ConSQL").toString());
			consulta.setRows(10);
			consulta.setReadOnly(true);
			consulta.setWidth("100%");
			
			// botonera basica
			layoutEjecutar.addComponent(new HorizontalRule());
			layoutEjecutar.addComponent(btEjecutar);
			btEjecutar.setStyleName(ValoTheme.BUTTON_PRIMARY);
			
			layoutEjecutar.setMargin(true);
			layoutEjecutar.setSpacing(true);
			layoutEjecutar.setComponentAlignment(btEjecutar, Alignment.TOP_LEFT);

			
			layoutEjecutar.addComponent(btVolver);
			btVolver.setStyleName(ValoTheme.BUTTON_DANGER);
			layoutEjecutar.setComponentAlignment(btVolver, Alignment.TOP_RIGHT);
			
			
			btVolver.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
			    	UI.getCurrent().removeWindow(ProvPantallaEjecutarConsulta.this);
			        close(); 		
				}
			});
			
			consulta.setVisible(false);
			ckSql.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					if (ckSql.getValue() )consulta.setVisible(true);
					else consulta.setVisible(false);
				}
			});
			
			btEjecutar.addClickListener(new ClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
					//fechaNumerica = new Date().getTime();
					
					System.out.println("Creamos el resource");
					/*resource = null;
					resource = new StreamResource(new StreamResource.StreamSource() {

						private static final long serialVersionUID = 1L;

						@Override
			            public InputStream getStream() {
			                try {
			                	//System.out.println("************* IMPRIMIR DOCUMENTO **********");
			                    InputStream targetStream = new ByteArrayInputStream(valoresDevueltos.getBytes());
			                    return targetStream;
			                } catch (Exception e) {
			                    e.printStackTrace();
			                    return null;
			                }
			            }
			        }, "ConsultaAMA_" +UI.getCurrent().getSession().getAttribute("Idconsulta").toString() + "_" + fechaNumerica + ".csv");
*/
					
					StreamResource stream = new StreamResource(
			                new StreamSource() {
			                	private static final long serialVersionUID = 1L;

								@Override
					            public InputStream getStream() {
					                try {
					                	//System.out.println("************* IMPRIMIR DOCUMENTO **********");
					                    InputStream targetStream = new ByteArrayInputStream(valoresDevueltos.getBytes());
					                    return targetStream;
					                } catch (Exception e) {
					                    e.printStackTrace();
					                    return null;
					                }
					            }
			                }, fechaNumerica + ".csv");	
					

			        if (downloader != null) 
			        {
			        	System.out.println("Borramos la extension");
			        	btImprimir.removeExtension(downloader);
			        }
			        
			        downloader = new FileDownloader(stream) {
			        	/*@Override public Resource getFileDownloadResource() {
			                return new FileResource(stream);        
			           }*/
			            @Override
			            public boolean handleConnectorRequest(VaadinRequest request,
			                    VaadinResponse response, String path)
			                    throws IOException {
			            	
			            	//fechaNumerica = new Date().getTime();
			            	//System.out.println("Entramos");
							//String fichero = item.getItemProperty("Url").getValue().toString();
							//fichero = fichero.substring(fichero.lastIndexOf("/")+1);
							//System.out.println("usuario" + UsuarioSave + " ..." + UsuarioSave.substring(4) + " ----->Subimos el fichero" + fichero);
							
			            	String fc = "ConsultaAMA_" +UI.getCurrent().getSession().getAttribute("Idconsulta").toString() + "_" + fechaNumerica + ".csv";
			            	stream.setFilename(fc);
			                return super
			                        .handleConnectorRequest(request, response, path);
			            }
			        };
			        

			        
			        //System.out.println("Extensiones " + btImprimir.getExtensions().size());
			        
			        downloader.extend(btImprimir);					
					//new FileDownloader(resource).extend(btImprimir);
			        
			        
					System.out.println("fechanumerica:" + fechaNumerica);
					valoresDevueltos = "";
					parametros = "";
					
				    Date now = new Date();
				    strDate = sdfDate.format(now);					
					
					
					int obligatorio = 0 ;
					if (par1.isVisible() && par1.getValue().equals("")) {
						
						new Notification("Error ¡¡¡. Parámetros obligatorios",
								"No puede ejecutar la consulta si no ha rellenado antes TODOS los parámetros",
								Notification.Type.ERROR_MESSAGE, true)
								.show(Page.getCurrent());		
						return;
					}
						
					
					if (par1.isVisible()) parametros += "|" + idParametros[1] + "-->" + par1.getValue();
					if (par2.isVisible()) parametros += "|" +  idParametros[2] + "-->" + par2.getValue();
					if (par3.isVisible()) parametros += "|" +  idParametros[3] + "-->" + par3.getValue();
					if (par4.isVisible()) parametros += "|" +  idParametros[4] + "-->" + par4.getValue();
					if (par5.isVisible()) parametros += "|" +  idParametros[5] + "-->" + par5.getValue();
					if (par6.isVisible()) parametros += "|" +  idParametros[6] + "-->" + par6.getValue();
					if (par7.isVisible()) parametros += "|" +  idParametros[7] + "-->" + par7.getValue();
					if (par8.isVisible()) parametros += "|" +  idParametros[8] + "-->" + par8.getValue();
					if (par9.isVisible()) parametros += "|" +  idParametros[9] + "-->" + par9.getValue();
					if (par10.isVisible()) parametros += "|" +  idParametros[10] + "-->" + par10.getValue();				
	
					
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
			    	
			    	ConfirmDialog.show(UI.getCurrent(), "Confirmación", "Puede que estas consultas tarden bastante en generarse.\n"
							+"Recuerde que si cancela la consulta cerrando la ventana,\n"
							+ "pongase en contacto con informática para verificar que la sesión no se ha quedado en proceso.\n\n"
							+ "¿ Desea continuar con la ejecución ?"
							,
			    	        "Continuar", "Cancelar", new ConfirmDialog.Listener() {

								private static final long serialVersionUID = 1L;
								
								@Override
								public void onClose(ConfirmDialog dialog) {
			    	                if (dialog.isConfirmed()) {
			    	                       
			    	                	//System.out.println("Ejecutamos la consulta");
			    	                	
			    	                	btEjecutar.setVisible(false);
			    	                	HashMap respuestaPresupuesto = null;
			    						HashMap respuesta = null;
			    						
			    						try {
			    							PAC_SHWEB_CONSULTASAMA llamada2 = new PAC_SHWEB_CONSULTASAMA(service.plsqlDataSource.getConnection());
			    							
			    							//System.out.println("Consulta:" + consulta.getValue());
			    							//System.out.println("Parametros:" + parametros);
			    							respuesta = llamada2.ejecutaPAC_SHWEB_CONSULTASAMA__F_EJECUTAR_CONSULTA(consulta.getValue(), parametros, new BigDecimal(UI.getCurrent().getSession().getAttribute("Idconsulta").toString()));
			    							
			    							//System.out.println("Respuesta ejecutar_consulta:" + respuesta);
			    							
			    							List<Map> valor = (List<Map>) respuesta.get("REGISTROS");
			    							
			    							
			    						
			    							int cont = 0;
			    							for (Map map : valor) {
			    								//System.out.println("Longitud: " + map.size() + " Columnas: " + map.get(1) );
			    								
			    							    if ( cont == 0) {
			    							    	printMap(map, "C");
			    							    	cont = 1;
			    							    }
			    							    else {
			    							    	printMap(map, "D");
			    							    }

			    								
			    							}
			    							
			    							//System.out.println ( "*************************>>>>>>> " + valoresDevueltos);
			    							
			    							// TENEMOS QUE BAJAR EL RESULTADO EN FICHERO


			    							
			    							
			    						} catch (Exception e) {
			    							// TODO Auto-generated catch block
			    							e.printStackTrace();
			    						}	
			    						
			    						ConfirmDialog.Factory dffin = new DefaultConfirmDialogFactory() {

			    							
			    						    @Override
			    							public ConfirmDialog create(String caption, String message, String okCaption,
			    									String cancelCaption, String notOkCaption) {

			    						        ConfirmDialog  d = super.create(caption,message,okCaption, cancelCaption, notOkCaption
			    						               );

			    						        // Change the order of buttons
			    						        Button ok = d.getOkButton();
			    						        ok.setVisible(false);
			    						        HorizontalLayout buttons = (HorizontalLayout) ok.getParent();
			    						        buttons.removeComponent(ok);
			    						        buttons.addComponent(ok,1);
			    						        
			    						        buttons.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

			    						        return d;
			    						    }

			    						};
			    						ConfirmDialog.setFactory(dffin);	
			    						
			    						
			    				    	ConfirmDialog.show(UI.getCurrent(), "Confirmación", "La consulta ya ha finalizado.\n Pulse el botón imprimir para recuperar el fichero generado."
			    								,
			    				    	        "", "Continuar",  new ConfirmDialog.Listener() {

			    									private static final long serialVersionUID = 1L;
			    									
			    									@Override
			    									public void onClose(ConfirmDialog dialog) {
			    				    	                if (dialog.isConfirmed()) {
			    				    	                       
			    				    	           
			    				    						btImprimir.setVisible(true);

			    				    	                } else {
			    				    	                	btImprimir.setVisible(true);

			    				    	                	
			    				    	                	
			    				    	                    
			    				    	                }
			    				    	            }

			    						
			    				    	});
			    				    	
			    				    	
			    						/*new Notification("Consulta generada",
			    								"La consulta ya ha finalizado. Pulse el botón imprimir para recuperar el fichero generado.",
			    								Notification.Type.WARNING_MESSAGE, true)
			    								.show(Page.getCurrent());	*/
			    						
			    	                	

			    	                } else {
			    	                    return;
			    	                	
			    	                	
			    	                    
			    	                }
			    	            }

					
			    	});
					
					
				}
			});
			

	        
	        
			

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	;

		layoutPrincipal.addComponent(vLayoutParam);
		vLayoutParam.setMargin(true);
		vLayoutParam.setSpacing(true);
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setMargin(false);
		layoutPrincipal.setSizeFull();
		vLayoutParam.addComponent(new HorizontalRule());
		vLayoutParam.addComponent(tableexp);
		vLayoutParam.addComponent(grid);
		vLayoutParam.addComponent(ckSql);
		
		ckSql.setValue(false);
		vLayoutParam.addComponent(consulta);
		vLayoutParam.addComponent(layoutEjecutar);
		vLayoutParam.addComponent(btImprimir);
		btImprimir.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		btImprimir.setCaption("Imprimir última consulta generada ");
		btImprimir.setVisible(false);
		
		setContent(layoutPrincipal);


		
		//tabExp.setStyleName(ValoTheme.TABSHEET_FRAMED);
		tabExp.setStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);	
	//	layoutPrincipal.addComponent(vTabs);
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
	
	

    private class HorizontalRule extends Label {
        public HorizontalRule() {
          super("<hr style='color:blue' />", ContentMode.HTML);
        }
      }
    
    public static <K,V> void printMap(Map<K, V> map, String tipo)
	{
		// using Iterator
    	String linea = "";
    	String cabecera = "";
		Iterator<K> itr = map.keySet().iterator();
		while (itr.hasNext())
		{
			K key = itr.next();
			V value = map.get(key);
			//System.out.println(key + "=" + value);
			if ( tipo == "C") {
				cabecera += key.toString().replace(";", ",") + ";";
			}
			
			try {
			linea += value.toString().replace(";",",") + ";";
			}
			catch ( Exception e ) {
				linea += ";";
			}
			
			
			
			

			
		}
		
		linea = linea.replaceAll("\\r|\\n", "");
		//System.out.println("Despues:" + linea);

		if ( tipo == "C")
			valoresDevueltos += cabecera + '\n' + linea + '\n';
		else
			valoresDevueltos += linea + '\n';
	
		// For-each Loop
		//for (K key : map.keySet()) {
		//	System.out.println(key + "=========" + map.get(key));
		//}
	}
    
    
    private StreamResource createFileResource(File file) {

        StreamResource sr = new StreamResource(new StreamSource() {

            @Override
            public InputStream getStream() {

                try {
                    return new FileInputStream(file);

                } catch (IOException e) {
                    System.out.println("Error fichero"); 
                }
                return null;
            }
        }, null);

        sr.setCacheTime(0);

        return sr;
    }   
    
    
    
    
}
