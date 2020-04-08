package com.csi_ti.itaca.custom.general.frontend;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.csi_ti.itaca.custom.general.frontend.utiles.BotoneraDoble;
import com.csi_ti.itaca.custom.general.frontend.utiles.ValidarComunicado;
import com.csi_ti.itaca.custom.general.frontend.ventanas.ProvVenComunicadoExpediente;
import com.csi_ti.itaca.custom.general.server.jdbc.PAC_SHWEB_PROVEEDORES;
import com.csi_ti.itaca.custom.general.server.jdbc.WS_AMA;
import com.csi_ti.itaca.custom.general.server.jdbc.util.ConexionFactoria;
import com.csi_ti.itaca.custom.general.server.service.GeneralBusinessServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
public class ProvDatosComunicadosExpediente extends Panel implements ItacaView {
	
	public String UsuarioSave;

    public TextArea texto = new TextArea();
    public Label contador = new Label("123");
    public Label contadorgrande = new Label("123");
    public int pendiente = 0;
    TextArea textoModal  = new TextArea();
    Window ventana = new Window();
    

    public Table tbCom = new Table();
    TextArea observaciones = new TextArea();
    Button btComunicado = new Button("Nuevo Comunicado");
    
    ProvVenComunicadoExpediente provVenComunicadoExpediente;
    ProvPantallaConsultaExpediente provPantallaconsultaExpedienteInicial;

    private static GeneralBusinessServiceImpl service;
	private static final long serialVersionUID = -304344441663815443L;

	// constructor inicial
	public ProvDatosComunicadosExpediente( Map<String, Object> retorno, ProvPantallaConsultaExpediente provPantallaConsultaExpediente, String pusu ) {

		//System.out.println("Usuario provdatoscomunidadosexpediente: " + pusu);
		//System.out.println("2.Usuario provdatoscomunidadosexpediente: " + provPantallaConsultaExpediente.UsuarioSave);
		UsuarioSave = provPantallaConsultaExpediente.UsuarioSave;
		service = (GeneralBusinessServiceImpl) UI.getCurrent().getSession().getAttribute("service");
		provPantallaconsultaExpedienteInicial = provPantallaConsultaExpediente;

		ProvVenComunicadoExpediente provVenComunicadoExpediente = new ProvVenComunicadoExpediente(this);
		
		// TAB TITULAR
		GridLayout dtitLayout = new GridLayout(1,2);
		dtitLayout.setStyleName("layout-lineas-juntas");

		// A�adimos los datos a dtitlayout
		

		String[] columnsexp ={"Fecha","Tipo","dstipcom","Origen","Destino","Descripcion"};
		Object[] typesexp = {String.class,String.class,String.class,String.class,String.class,String.class,};
		Object[] visibleColumnsexp = new Object[]{"Fecha","dstipcom","Origen","Destino","Descripcion"};
		Util.defineTable(tbCom, columnsexp, typesexp, visibleColumnsexp,true);
		tbCom.setColumnHeaders(new String[] {"Fecha","Tipo", "Origen","Destino","Descripción" });

		tbCom.setColumnExpandRatio("Fecha", 13);
		tbCom.setColumnExpandRatio("dstipcom", 20);
		tbCom.setColumnExpandRatio("Origen", 13);
		tbCom.setColumnExpandRatio("Destino", 13);
		tbCom.setColumnExpandRatio("Descripcion", 41 ) ;
		tbCom.setColumnReorderingAllowed(false);
		
		tbCom.setTabIndex(-1);

		
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha;
		Date date = null;
		/*List<Map> valor = (List<Map>) retorno.get("REGISTROS");
		//System.out.println("Registros hay" + valor.size());
		


		for (Map map : valor) {

			Object newItemId = tbCom.addItem();
			Item row1 = tbCom.getItem(newItemId);

			if ( !map.get("TIPO").toString().equals("PP")) {
			
				row1.getItemProperty("Fecha").setValue(map.get("FECHA"));
				row1.getItemProperty("Tipo").setValue(map.get("TIPO").toString());
				row1.getItemProperty("Origen").setValue(map.get("ORIGEN"));
				row1.getItemProperty("Destino").setValue(map.get("DESTINO"));
				row1.getItemProperty("Descripcion").setValue(map.get("TEXTO"));
			
			}

		}*/
		
		tbCom.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				// TODO Auto-generated method stub

				observaciones.setReadOnly(false);
				observaciones.setValue("");
				if ( event.getItem().getItemProperty("Descripcion").getValue()!=null) {
					observaciones.setValue(event.getItem().getItemProperty("Descripcion").getValue().toString());
				}
				observaciones.setReadOnly(true);
			}
		});
		
		tbCom.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub

				
				observaciones.setReadOnly(false);
				observaciones.setValue("");
			
				Item row1 = tbCom.getItem(tbCom.getValue());
				if ( row1 != null && row1.getItemProperty("Descripcion").getValue()!=null) {
					observaciones.setValue(row1.getItemProperty("Descripcion").getValue().toString());
				}
				observaciones.setReadOnly(true);
			}
		});
		

		tbCom.setCellStyleGenerator(new Table.CellStyleGenerator() {                
		        @Override
		        public String getStyle(Table source, Object itemId, Object propertyId) {

		            if(propertyId != null ) {
		                return "normal";
		            } else {
		                return null;
		            }
		        }
		      });	
		
		tbCom.setVisible(true);
		tbCom.setSelectable(true);
		tbCom.setImmediate(true);
		tbCom.setPageLength(10);
		dtitLayout.addComponent(tbCom);
		dtitLayout.setMargin(true);
		dtitLayout.setWidth("100%");
		observaciones.setReadOnly(true);
		observaciones.setWidth("100%");
		observaciones.setHeight("100%");
		observaciones.setRows((int)UI.getCurrent().getSession().getAttribute("resoluciony")/53);
		//observaciones.setRows(13);
		dtitLayout.addComponent(observaciones);
		dtitLayout.addComponent(btComunicado);
		btComunicado.setStyleName(ValoTheme.BUTTON_PRIMARY);
		dtitLayout.setComponentAlignment(btComunicado, Alignment.BOTTOM_CENTER);
		setContent(dtitLayout);
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		
		//System.out.println("VAlidamos >>>>>>>>>>> " + UI.getCurrent().getSession().getAttribute("comunicadosExpediente"));
		if ( !ValidarComunicado.EsValido("PC") &&
			 !ValidarComunicado.EsValido("V1") &&
			 !ValidarComunicado.EsValido("RS") &&
			 !ValidarComunicado.EsValido("H")  &&
			 !ValidarComunicado.EsValido("IT") &&
			 !ValidarComunicado.EsValido("FR")
		   ) {
			
			btComunicado.setVisible(false);
		}		
		
		
		btComunicado.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
					recargarComunicados();
					provVenComunicadoExpediente.init();
					UI.getCurrent().addWindow(provVenComunicadoExpediente);
				
			}
		});
		
		
		
		observaciones.addContextClickListener(new ContextClickListener() {
			
			@Override
			public void contextClick(ContextClickEvent event) {
				// TODO Auto-generated method stub
				UI.getCurrent().addWindow(ventana);
				

                textoModal.focus();
				textoModal.setReadOnly(false);
				textoModal.setValue(observaciones.getValue());
				textoModal.setReadOnly(true);
			}
		});
		
		textoModal.addContextClickListener(new ContextClickListener() {
			
			@Override
			public void contextClick(ContextClickEvent event) {
				// TODO Auto-generated method stub
				ventana.close();
			}
		});		
		
		
		
    	// VENTANA
    	
    	ventana.setWidth("800px");
    	ventana.setHeight("500px");
    	ventana.setStyleName("ventana_texto_ampliar");
    	ventana.center();
    	textoModal.setWidth("780px");
    	textoModal.setHeight("430px");
    	VerticalLayout vl = new VerticalLayout();

    	textoModal.setStyleName("texto_modal_ampliar");
    	vl.setMargin(true);
    	ventana.setContent(vl);
    	
   	

	    BotoneraDoble botonera = new BotoneraDoble();
	    vl.addComponent(textoModal);
    	vl.addComponent(botonera);
	    vl.setComponentAlignment(botonera, Alignment.BOTTOM_CENTER);
	    //vl.addComponent(contadorgrande);

		
		botonera.btCancelar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				

				ventana.close();
				
			}
		});
		
		botonera.btAceptar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
				

				int len = pendiente - textoModal.getValue().length();
                contador.setValue(" "+String.valueOf(len));
                
                float ini = (float) textoModal.getValue().length();
                float fin = pendiente;
                float quedan = ( ini / fin )  * 100 ;
                
                
                if ( quedan  > 98 ) {
                	contador.setStyleName("contador_rojo");
                } else if ( quedan > 70 ) {
                	contador.setStyleName("contador_naranja");
                }
                else {
                	contador.setStyleName("contador_verde");
                } 
                texto.focus();
				ventana.close();
				
			}
		});			

    	ventana.setContent(vl);
    	
    	botonera.btCancelar.setVisible(false);
    	contador.setVisible(false);
    	
    	// FIN VENTANA		

		
	}
	

	

	
	@Override
	public void enter(ViewChangeEvent event) {

	}
	
	public void recargarComunicados( ) {

		// Recuperamos los comunicados
		
		//System.out.println(">>>>>>>>>> Recargamos comunicados con el expediente:" + new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString()));
		PAC_SHWEB_PROVEEDORES llamadaProv = null;
		try {
			llamadaProv = new PAC_SHWEB_PROVEEDORES(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaCom = null;
		
		System.out.println("Antes de comunicados expediente: " + UsuarioSave);

		try {
			respuestaCom = llamadaProv.ejecutaPAC_SHWEB_PROVEEDORES__F_COMUNICADOS_EXPEDIENTE(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					new BigDecimal(UI.getCurrent().getSession().getAttribute("expediente").toString())
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tbCom.removeAllItems();
		Map<String, Object> retornoComunicados = new HashMap<String, Object>(respuestaCom);
		
		//System.out.println("Retornocomunicados: " + retornoComunicados);
		List<Map> valor = (List<Map>) retornoComunicados.get("REGISTROS");

		//System.out.println("Comunicados: " + valor.size());
		if ( valor.size() > 0)  {
			for (Map map : valor) {
	
				Object newItemId = tbCom.addItem();
				Item row1 = tbCom.getItem(newItemId);
	
				if ( !map.get("TIPO").toString().equals("PP")) {
				
					row1.getItemProperty("Fecha").setValue(map.get("FECHA"));
					row1.getItemProperty("Tipo").setValue(map.get("TIPO").toString());
					row1.getItemProperty("Origen").setValue(map.get("ORIGEN"));
					row1.getItemProperty("dstipcom").setValue(map.get("DSTIPCOM"));
					row1.getItemProperty("Destino").setValue(map.get("DESTINO"));
					row1.getItemProperty("Descripcion").setValue(map.get("TEXTO"));
				
				}
	
			}
		}
		

		
		
		// Mostramos el estado del expediente
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

			UI.getCurrent().getSession().setAttribute("estadoExpediente",retorno.get("ESTADO").toString());
			provPantallaconsultaExpedienteInicial.setCaption("GESTIÓN DEL EXPEDIENTE Nº " + UI.getCurrent().getSession().getAttribute("expediente")
					+ " ( " + 
					UI.getCurrent().getSession().getAttribute("estadoExpediente") + " ) ");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}	
		
		// Maestro comunicados

		WS_AMA llamadaAMA = null;
		try {
			llamadaAMA = new WS_AMA(service.plsqlDataSource.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap respuestaMaestro = null;
		
		//System.out.println("Llamammos maestro comunicados: " + UI.getCurrent().getSession().getAttribute("tipousuario").toString().substring(1,1));;
		try {
			// pPUSUARIO, pORIGEN, pTPCOMUNI, pTPUSUARIO, pESTADO)
			
			respuestaMaestro = llamadaAMA.ejecutaWS_AMA__MAESTRO_COMUNICADOS(
					//UI.getCurrent().getSession().getAttribute("userxxx").toString(),
					UsuarioSave,
					UI.getCurrent().getSession().getAttribute("origen").toString(),
					null,
					UI.getCurrent().getSession().getAttribute("tipousuario").toString().substring(1,1),
					UI.getCurrent().getSession().getAttribute("estadoExpediente").toString()
					);			

			
			Map<String, Object> retornoMaestro = new HashMap<String, Object>(respuestaMaestro);
			List<Map> valorMaestro = (List<Map>) retornoMaestro.get("COMUNICADOS");
			UI.getCurrent().getSession().setAttribute("comunicadosExpediente",valorMaestro);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UI.getCurrent().getSession().setAttribute("comunicadosExpediente",null);
		}
		
		// 	
		
		
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		
		if ( !ValidarComunicado.EsValido("CA") && !ValidarComunicado.EsValido("FT") ) {
			provPantallaconsultaExpedienteInicial.provDatosDetalleExpediente.btCerrarExpediente.setVisible(false);
		}
		else {
			provPantallaconsultaExpedienteInicial.provDatosDetalleExpediente.btCerrarExpediente.setVisible(true);

			
			
		}
		// Mostramos botonera de cerrar expediente
		// Validamos si tenemos que mostrar el bot�n de cerrar expediente
		

	}


}