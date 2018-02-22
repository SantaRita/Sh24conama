package com.csi_ti.itaca.custom.general.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Label;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Theme("tests-valo-reindeer")
public class ProvDatosTitularExpediente extends Panel implements ItacaView {


	public ArrayList lsCon = new ArrayList();
	
	public String UsuarioSave;

	    
    Property.ValueChangeListener listenerChgVip;
    
	Table tableTel = new Table();
	Label lCaption = new Label();
	public Label dgenNombreTitular = new Label();
	Label dgenCompania =new Label("Compañía:");
	Label dgenProvincia =new Label("Provincia:");
	Label dgenCPoblacion = new Label("Población:");
	Label dgenDireccion = new Label("Dirección:");
	Label dgenPolizas =new Label();
	Button btVolver = new Button();
	Button btTelefonos = new Button();

	
	Label dgenPais = new Label();
	
	public Table tbTelefonos = new Table();
	

	

	

	private static final long serialVersionUID = -304344441663815443L;

	// constructor inicial
	public ProvDatosTitularExpediente( String pusuario) {
		
		UsuarioSave = pusuario;


		String[] columnsexp ={"Descripcion","Telefono"};
		Object[] typesexp = {String.class,String.class};
		Object[] visibleColumnsexp = new Object[]{"Descripcion","Telefono"};
		Util.defineTable(tbTelefonos, columnsexp, typesexp, visibleColumnsexp,true);
		tbTelefonos.setColumnHeaders(new String[] {"Descripcion","Telefono" });

		tbTelefonos.setColumnExpandRatio("Descripcion", 60);
		tbTelefonos.setColumnExpandRatio("Telefono", 40);
		tbTelefonos.setWidth("60%");
		tbTelefonos.setColumnReorderingAllowed(false);
		tbTelefonos.setTabIndex(-1);
		tbTelefonos.setPageLength(3);
		
		// TAB TITULAR
		GridLayout dtitLayout = new GridLayout(8,3);
		dtitLayout.setStyleName("layout-lineas-juntas");
		dtitLayout.addComponent(new Label("Nombre:"),0,0);
		dgenNombreTitular.setCaptionAsHtml(true);
		dgenNombreTitular.setContentMode(ContentMode.HTML);
		dgenNombreTitular.setValue("<h style='font-weight: normal;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.nombretit")+" </h>"
				);		
		dtitLayout.addComponent(dgenNombreTitular,1,0);
		
		dtitLayout.addComponent(new Label("Compañia:"),2,0);
		dgenCompania.setCaptionAsHtml(true);
		dgenCompania.setContentMode(ContentMode.HTML);
		dgenCompania.setValue("<h style='font-weight: normal;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.cia")+" </h>"
				);
		dtitLayout.addComponent(dgenCompania,3,0,4,0);

		/*dtitLayout.addComponent(btTelefonos,5,0,6,0);
		btTelefonos.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		dtitLayout.setComponentAlignment(btTelefonos, Alignment.MIDDLE_LEFT);
		btTelefonos.setIcon(FontAwesome.PHONE);
		btTelefonos.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if (  tbTelefonos.isVisible() ) tbTelefonos.setVisible(false);
				else tbTelefonos.setVisible(true);
				
				llamadaTelefonica llamada = new llamadaTelefonica();
				
				
			}
		});*/

		dtitLayout.addComponent(btVolver,7,0);
		btVolver.setStyleName(ValoTheme.BUTTON_DANGER);
		dtitLayout.setComponentAlignment(btVolver, Alignment.MIDDLE_RIGHT);
		btVolver.setIcon(FontAwesome.CLOSE);
		btVolver.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				UI.getCurrent().getNavigator().navigateTo("ProvPantallaBusquedaExpedientes/"+ UsuarioSave);	 
			}
		});
		
		dtitLayout.addComponent(new Label("Dirección:"),0,1);
		dgenDireccion.setCaptionAsHtml(true);
		dgenDireccion.setContentMode(ContentMode.HTML);
		dgenDireccion.setValue("<h style='font-weight: normal;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.direccion")+" </h>"
				);						
		dtitLayout.addComponent(dgenDireccion,1,1);
		

		dtitLayout.addComponent(new Label("Población:"),2,1);
		dgenCPoblacion.setCaptionAsHtml(true);
		dgenCPoblacion.setContentMode(ContentMode.HTML);
		dgenCPoblacion.setValue("<h style='font-weight: normal;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.poblacion")+"-"+(String) UI.getCurrent().getSession().getAttribute("tit.cp")+" </h>"
				);				
		dtitLayout.addComponent(dgenCPoblacion,3,1);
		
		dtitLayout.addComponent(new Label("Provincia:"),4,1);
		dgenProvincia.setCaptionAsHtml(true);
		dgenProvincia.setContentMode(ContentMode.HTML);
		dgenProvincia.setValue("<h style='font-weight: normal;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.provincia")+" </h>"
				);		
		dtitLayout.addComponent(dgenProvincia,5,1);
		
		dtitLayout.setWidth("100%");
		dtitLayout.setColumnExpandRatio(0,7);
		dtitLayout.setColumnExpandRatio(1,27);
		dtitLayout.setColumnExpandRatio(2,7);
		dtitLayout.setColumnExpandRatio(3,27);
		dtitLayout.setColumnExpandRatio(4,8);
		dtitLayout.setColumnExpandRatio(5,25);
		
		dtitLayout.addComponent(tbTelefonos,0,2,5,2);
		dtitLayout.setComponentAlignment(tbTelefonos, Alignment.TOP_CENTER);
		tbTelefonos.setVisible(false);
				
		setCaption("Datos del Titular");
		setContent(dtitLayout);
		
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


	}
	
	private class HorizontalRule extends Label {
		  public HorizontalRule() {
		    super("<hr style='color:gray;height:1px' />", ContentMode.HTML);
		  }
		}
	
	


	
	@SuppressWarnings("unchecked")
	public void recuperarDatosTitular ( ) {
		
			

			dgenNombreTitular.setCaptionAsHtml(true);
			dgenNombreTitular.setContentMode(ContentMode.HTML);
			dgenNombreTitular.setValue("<h style='font-weight: bold;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.nif") + " - "
					+ (String) UI.getCurrent().getSession().getAttribute("tit.nombretit") +" </h>"
					);
			
    

            
		
			dgenCompania.setValue((String) UI.getCurrent().getSession().getAttribute("tit.cia"));
			//dgenCliente.setValue((String) UI.getCurrent().getSession().getAttribute("tit.desccontra"));
			dgenPolizas.setValue("P�li: " +(String) UI.getCurrent().getSession().getAttribute("tit.poliza") );
			

			dgenDireccion.setCaptionAsHtml(true);
			dgenDireccion.setContentMode(ContentMode.HTML);
			dgenDireccion.setValue("<h style='font-weight: bold;'>"+(String) UI.getCurrent().getSession().getAttribute("tit.domicilio")+"</h>");
			dgenCPoblacion.setValue((String) UI.getCurrent().getSession().getAttribute("tit.despoblacion") + " - " 
						+ (String) UI.getCurrent().getSession().getAttribute("tit.descp")  
					);
			
			dgenProvincia.setValue( (String) UI.getCurrent().getSession().getAttribute("tit.desprovincia") + " " 
						+ "( " + (String) UI.getCurrent().getSession().getAttribute("tit.despais") + " )"
					);
			
			


			
			
			

	}
	

	
	@Override
	public void enter(ViewChangeEvent event) {
		
		String args[] = event.getParameters().split("/");
		UsuarioSave = args[0];

	}	


}