package com.csi_ti.itaca.custom.general.frontend;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.csi_ti.itaca.architecture.tools.webmodule.componentes.ContenedorDeComponentes;
import com.csi_ti.itaca.architecture.tools.webmodule.componentes.WrapperComponentContainer;
import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.PantallaBaseConInputOutput;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_CONSULTASAMA;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.google.gwt.thirdparty.common.css.compiler.ast.ParseException;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
@SpringUI
public class ProvPantallaBusquedaDocumentos extends PantallaBaseConInputOutput<VerticalLayout, Void, Void> {

	@Autowired
	private GeneralBusinessServiceImpl service;
	
	public String UsuarioSave;
	ProvPantallaEjecutarConsultaCompleta provPantallaEjecutarConsultaCompleta;

	Map<String, Object> parameters = new HashMap<String, Object>();
	BrowserWindowOpener opener;
	BrowserWindowOpener openerListado;
	boolean primeraImpresion = true;

	private String usuAdmin = "AMA_ADMON";
	Connection conn = null;
	private String pwdAdmin = "inicio";
	private String url = UI.getCurrent().getSession().getAttribute("url").toString();

	public ComboBox impresion;

	private FieldGroup binder;
	private FormLayout flayout;
	private HorizontalLayout hlayout;
	Label msgAlta;
	Button buscar;
	Button btConsultar;
	Button limpiar;
	String inTipoAlta;
	String inFiltro;
	GridLayout gl;
	int primeraEntrada = 0;

	HorizontalLayout panelAlta = new HorizontalLayout();
	HorizontalLayout hBotonera = new HorizontalLayout();

	private static final long serialVersionUID = 4440733679587692241L;

	Table tableexp;

	TextField consulta;

	Label lblInc;
	Label lblRev;

	Label titulo;

	int validarCampos = 0;

	// ConexionFactoria service = new ConexionFactoria();



	@Override
	public void enter(ViewChangeEvent event) {

		String args[] = event.getParameters().split("/");

		// System.out.println(UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase()
		// + " < El usuario es : " + args[0] );
		UsuarioSave = args[0];

		if (UsuarioSave == null || UsuarioSave == "") {
			UsuarioSave = UI.getCurrent().getSession().getAttribute("user").toString().toUpperCase();
		}

		tableexp.setPageLength(0);
		tableexp.setVisible(false);

		PropertysetItem item = new PropertysetItem();
		item.addItemProperty("consulta", new ObjectProperty<String>(""));

		binder = new FieldGroup(item);
		binder.setBuffered(true);
		binder.bindMemberFields(this);

		btConsultar.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				// System.out.println("Vamos a consulta con usuario: " +
				// UsuarioSave);
				UI.getCurrent().getNavigator().navigateTo("ProvPantallaConsultaExpediente" + "/" + UsuarioSave);

			}

		});

	}

	// **************************************** BOTON LIMPIAR CAMPOS
	// **************************************************

	public void limpiarButton() {

		tableexp.removeAllItems();
		tableexp.setVisible(false);
		gl.setVisible(false);
		consulta.clear();

	}

	// ****************************************************************************************************************
	// *************************+ BUSCAR consulta
	// *******************************************************
	// ****************************************************************************************************************
	@SuppressWarnings({ "unchecked", "serial" })
	public void buscarButton() {

		try {

			// System.out.println("Esntramos en buscarbutton");

			validarCampos = 1;
			// binder.commit();

			// Validaciones
			String mensajes = null;

			/*
			 * if ( !consulta.isValid() ) { mensajes =
			 * "Campo expediente incorrecto. Solo se permiten números \n"; }
			 * else if ( ( dffecha.getValue()==null ||
			 * dffecha.getValue().equals("") ) && ( consulta.getValue()==null ||
			 * consulta.getValue().equals("") ) && ( estado.getValue()==null ||
			 * estado.getValue().equals("") )
			 * 
			 * && primeraEntrada==1 && estado.getValue()!="CON") { mensajes =
			 * "Es obligatorio introducir la consulta \n"; } else if ( (
			 * expediente.getValue()==null || expediente.getValue().equals("") )
			 * && ( estado.getValue().equals("CUR") ) && (
			 * ckInc.getValue()==null && ckRev.getValue()==null ) ) {
			 * 
			 * mensajes =
			 * "Es situación CUR es obligatorio introducir expediente o Inc/Rev\n"
			 * ; } else if ( ( expediente.getValue()==null ||
			 * expediente.getValue().equals("") ) && (
			 * estado.getValue().equals("FIN") )) { mensajes =
			 * "Es situación FIN es obligatorio poner un expediente. \n"; } if (
			 * mensajes!=null && primeraEntrada==1 ) { new Notification(
			 * "Se han detectado errores", mensajes,
			 * Notification.Type.TRAY_NOTIFICATION,
			 * true).show(Page.getCurrent()); } else {
			 */

			tableexp.removeAllItems();
			gl.setVisible(false);
			tableexp.setVisible(false);

			String inFiltros;
			int inVertodos = 0;
			String outMsgerror = null;

			try {

				buscarConsultas();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }

		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			// System.out.println("Error de validacion");
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected ContenedorDeComponentes<VerticalLayout> crearCuerpo() {

		// System.out.println("Contenedor de componentes");

		HashMap respuesta = null;

		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		
	/*

		System.out.println("------------Llamamos a obetener conecion " + service);
		PAC_SHWEB_PROVEEDORES llamadaListas = null;
		try {
			llamadaListas = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		// System.out.println("------------- DESPUES Llamamos a obetener
		// conecion");
		respuesta = null;
		/*try {
			respuesta = llamadaListas.ejecutaPAC_SHWEB_PROVEEDORES__F_LISTA_ESTADOS_EXPEDIENTE("P");
			Map<String, Object> retListas = new HashMap<String, Object>(respuesta);
			List<Map> valorRespuesta = (List<Map>) retListas.get("REGISTROS");
			UI.getCurrent().getSession().setAttribute("estadosConsulta", valorRespuesta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		VerticalLayout layout = new VerticalLayout();
		// layout.setSizeFull();
		layout.setMargin(false);

		tableexp = new Table() {
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

		consulta = new TextField("Consulta:");
		consulta.setWidth("300px");

		titulo = new Label();

		hlayout = new HorizontalLayout();
		hlayout.setMargin(false);
		hlayout.setSpacing(false);

		flayout = new FormLayout();
		flayout.addComponent(consulta);

		hlayout.addComponent(flayout);

		consulta.setImmediate(true);
		consulta.setValidationVisible(true);

		consulta.setImmediate(true);
		consulta.setValidationVisible(true);

		Util.addComponentsToLayoutInHorizontal(layout, Alignment.TOP_LEFT, "cuadrosinbarras", hlayout);
		consulta.focus();
		// BOTONERA

		buscar = new Button("Buscar");
		buscar.setClickShortcut(KeyCode.ENTER);
		buscar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buscar.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				buscarButton();
			}
		});
		limpiar = new Button("Limpiar");
		limpiar.setStyleName(ValoTheme.BUTTON_DANGER);
		limpiar.addClickListener((e) -> limpiarButton());
		GridLayout hlbot = new GridLayout(4, 1);
		hlbot.setMargin(true);
		hlbot.setWidth("100%");
		hlbot.addComponent(limpiar, 2, 0);
		hlbot.addComponent(buscar, 3, 0);
		layout.addComponent(hlbot);

		// Botonera imprimir

		Button btFocus = new Button();
		// layout.addComponent(btFocus);
		hlbot.setComponentAlignment(limpiar, Alignment.MIDDLE_CENTER);
		hlbot.setComponentAlignment(buscar, Alignment.MIDDLE_RIGHT);
		hlbot.setColumnExpandRatio(0, 90);
		hlbot.setColumnExpandRatio(1, 5);
		hlbot.setColumnExpandRatio(2, 5);
		// TABLAS
		layout.setMargin(false);
		String[] columnsexp = { "Imprimir", "Consultar", "Hoja", "Fhvisita", "Fhpc", "Fhasignacion", "Agrupacion",
				"Expediente", "Idconsulta", "Direccion", "Cp", "ConSQL", "Provincia", "Estado", "Incidencia", "Revisar",
				"Cia", "Anular", "ColBtRechazar", "ColBtAceptar", "Causa", "F.Cierre", "Nro.Fra.Proveedor",
				"Nro.Fra.Comis", "Importe", "Estado Fra.", "F.Factura", "F.Vto", "RgFactur", "tienecf",
				"gremioinicial" };
		Object[] typesexp = { Button.class, Button.class, Button.class, Date.class, Date.class, Date.class,
				String.class, String.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, String.class, Button.class, Button.class, Button.class,
				String.class, Date.class, String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, java.math.BigDecimal.class };
		Object[] visibleColumnsexp = new Object[] { "Consultar", "Idconsulta", "Expediente",};
		Util.defineTable(tableexp, columnsexp, typesexp, visibleColumnsexp, true);
		tableexp.setColumnHeaders(
				new String[] { "Detalle","Idconsulta", "Consulta"});

		tableexp.setColumnExpandRatio("Expediente", 9);


		tableexp.setTabIndex(-1);



		gl = new GridLayout(1, 1);
		gl.setVisible(false);
		gl.setStyleName("box_verde");
		gl.setMargin(true);
		gl.setSpacing(true);
		gl.setWidth("100%");
		gl.setHeight("45px");

		btConsultar = new Button("Alta");
		btConsultar.setVisible(false);
		btConsultar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		gl.addComponent(btConsultar);
		gl.setComponentAlignment(btConsultar, Alignment.MIDDLE_CENTER);
		msgAlta = new Label("No se ha encontrado ningún registro");
		gl.addComponent(msgAlta);
		gl.setComponentAlignment(msgAlta, Alignment.MIDDLE_CENTER);
		msgAlta.setVisible(false);

		layout.addComponent(gl);

		VerticalLayout vtable = new VerticalLayout();



		vtable.setMargin(true);
		vtable.addComponent(tableexp);
		layout.setMargin(false);
		layout.setStyleName("expediente-panel-busqueda");
		layout.addComponent(vtable);
		layout.setComponentAlignment(vtable, Alignment.MIDDLE_CENTER);

		layout.setExpandRatio(vtable, 1);

		// NAVEGACION CAMPOS
		consulta.setTabIndex(1);

		limpiar.setTabIndex(12);
		buscar.setTabIndex(13);
		btFocus.setTabIndex(14);
		btFocus.setStyleName("botoninvisible");

		btFocus.addFocusListener(new FocusListener() {

			@Override
			public void focus(FocusEvent event) {
				// TODO Auto-generated method stub
				consulta.focus();

			}
		});

		tableexp.setTabIndex(-1);
		

		return new WrapperComponentContainer<VerticalLayout>(layout);
	}

	@Override
	protected void enlazarDatos() {
		// TODO Auto-generated method stub

	}

	private void buscarConsultas() throws ParseException {
		// ***************************************************************************************************
		// TABLA DETALLE CONSULTAS
		// ***************************************************************************************************

		try {

			// System.out.println("Creamos de nuevo la ventana");

			btConsultar.setVisible(false);
			msgAlta.setVisible(false);

			gl.setVisible(false);

			tableexp.removeAllItems();

			System.out.println("Después de buscar");
			HashMap respuesta = null;
			BigDecimal expbd = null;

			try {

				// sI ES LA PRIMERA ENTRADA EL ESTADO = 'CON'
				UI.getCurrent().getSession().setAttribute("consultabuscar", consulta.getValue());

				String inc = null;
				String rev = null;
				String factura = null;

				tableexp.setVisibleColumns(new Object[] { "Consultar", "Expediente", "Idconsulta" });

				//System.out.println("Usuario pantalla : " + UsuarioSave + " Origen."
				//		+ UI.getCurrent().getSession().getAttribute("origen").toString());

				PAC_SHWEB_CONSULTASAMA llamada2 = new PAC_SHWEB_CONSULTASAMA(service.plsqlDataSource.getConnection());
				respuesta = llamada2.ejecutaPAC_SHWEB_CONSULTASAMA__F_LISTA_CONSULTAS(consulta.getValue());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("Después de buscar buscar");

			Map<String, Object> retorno = new HashMap<String, Object>(respuesta);

			//System.out.println("retorno:" + retorno);
			if (!retorno.get("CODIGOERROR").toString().equals("0")) {

				// System.out.println("Error");
				new Notification("Error", retorno.get("TEXTOERROR").toString(), Notification.Type.ERROR_MESSAGE, true)
						.show(Page.getCurrent());
			} else if (retorno != null) {

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fecha;
				Date date = null;
				List<Map> valor = (List<Map>) retorno.get("REGISTROS");
				// System.out.println("Registros hay" + valor.size());

				if (valor == null || valor.size() == 0) {
					// System.out.println("no hay registros");
					gl.setStyleName("box_rojo");
					msgAlta.setVisible(true);
					gl.setVisible(true);
				} else {
					gl.setStyleName("box_verde");
					gl.setVisible(false);
					btConsultar.setVisible(true);
					msgAlta.setVisible(false);
				}
				btConsultar.setVisible(false);
				for (Map map : valor) {

					Object newItemId = tableexp.addItem();
					Item row1 = tableexp.getItem(newItemId);

					// row1.getItemProperty("Poliza").setValue(map.get("NUMPOL"));
					// row1.getItemProperty("Expediente").setValue(String.valueOf(map.get("NUMEXP")));

					// Botoner IMPRIMIR FACTURAS

					// Botonera buscar

					Button btBuscar = new Button();
					btBuscar.setId(newItemId.toString());
					btBuscar.setData(newItemId);
					btBuscar.addStyleName(ValoTheme.BUTTON_FRIENDLY);
					btBuscar.addStyleName(ChameleonTheme.BUTTON_DOWN);
					btBuscar.setIcon(FontAwesome.SEARCH);
					btBuscar.addClickListener(new ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							// TODO Auto-generated method stub
							Object data = event.getButton().getData();
							tableexp.select(data);
							Item itemClickEvent = tableexp.getItem(data);
							btConsultar.setVisible(false);
							UI.getCurrent().getSession().setAttribute("Descripcion",
									(String) itemClickEvent.getItemProperty("Expediente").getValue());
							
							UI.getCurrent().getSession().setAttribute("Idconsulta",
									(String) itemClickEvent.getItemProperty("Idconsulta").getValue());
							
							UI.getCurrent().getSession().setAttribute("ConSQL",
									(String) itemClickEvent.getItemProperty("ConSQL").getValue());							
							
							UI.getCurrent().getSession().setAttribute("ordenColumna",
									tableexp.getSortContainerPropertyId());
							UI.getCurrent().getSession().setAttribute("orden", tableexp.isSortAscending());


							UI.getCurrent().getSession().setAttribute("usuariosave", UsuarioSave);
							
							provPantallaEjecutarConsultaCompleta = new ProvPantallaEjecutarConsultaCompleta(ProvPantallaBusquedaDocumentos.this, service);
							//proven.addCloseListener(lstClose);
							UI.getCurrent().addWindow(provPantallaEjecutarConsultaCompleta);
							
							/*UI.getCurrent().getNavigator()
									.navigateTo("ProvPantallaEjecutarConsulta" + "/" + UsuarioSave);*/
						}
					});

					row1.getItemProperty("Consultar").setValue(btBuscar);
					row1.getItemProperty("Idconsulta").setValue(map.get("RGCONSULTA").toString());
					row1.getItemProperty("Expediente").setValue(map.get("TXDESCRIPCION").toString());
					row1.getItemProperty("ConSQL").setValue(map.get("TXSQL").toString());



				}

			}

			tableexp.setFooterVisible(true);
			tableexp.setColumnFooter("Expediente", "Total: " + String.valueOf(tableexp.size()));

			tableexp.setVisible(true);
			tableexp.setSelectable(true);
			tableexp.setImmediate(true);
			tableexp.setPageLength((int) UI.getCurrent().getSession().getAttribute("resoluciony") / 33);


			tableexp.setSortContainerPropertyId(UI.getCurrent().getSession().getAttribute("ordenColumna"));
			if (UI.getCurrent().getSession().getAttribute("orden") != null) {
				tableexp.setSortAscending((boolean) UI.getCurrent().getSession().getAttribute("orden"));
				tableexp.sort();

			}

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			// System.out.println("Error");
			e.printStackTrace();

		}

		// Cuando cerramos la ventana de rechazo del expediente

		/*provVenRechazoExpediente.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				// System.out.println("Cerramos la vetnana");
				// TODO Auto-generated method stub



			}
		});*/

	}

}