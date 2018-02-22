package com.sh24;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ProvMenuPrincipal extends CustomComponent  {
    private static final long serialVersionUID = -3354143876393393750L;

    public HorizontalLayout menuOpcionesBoton;
    

    public ProvMenuPrincipal ( )  {
    	
		// MENU DE OPCIONES
		
		HorizontalLayout menuOpcionesLayout = new HorizontalLayout();
		menuOpcionesBoton = new HorizontalLayout();
		HorizontalLayout menuOpcionesLayout3 = new HorizontalLayout();
		
		menuOpcionesBoton.setStyleName("menu-pestana");
		
		menuOpcionesLayout.setMargin(false);
		menuOpcionesLayout.setStyleName("menu-opciones-panel");
		menuOpcionesLayout.addComponent(menuOpcionesBoton);
		
		
		// opciones de menu
		
		VerticalLayout ventana = new VerticalLayout();
	
		GridLayout gridMenu = new GridLayout(7,5);
		
		gridMenu.setStyleName("opciones-menu");
		gridMenu.setWidth("740px");
		
				
		Label opcion0 = new Label("WEB DE PROVEEDORES - Menú Principal");
		opcion0.setStyleName("titulo-menu");
		Label opcion1 = new Label("Busqueda de Expedientes");
		Label opcion2 = new Label("Manejo Servicios (WS)");
		Label opcion3 = new Label("Trazas");
		Label opcion4 = new Label("Gestión Documental Liberty ");
		Label opcion5 = new Label("Registro Nuevo Usuario ");
		Label opcion6 = new Label("API Rest Liberty (WS)");
		Label opcion7 = new Label("Presupuestos");
		
		opcion1.setStyleName("titulo-opciones-menu");
		opcion2.setStyleName("titulo-opciones-menu");
		opcion3.setStyleName("titulo-opciones-menu");
		opcion4.setStyleName("titulo-opciones-menu");
		opcion5.setStyleName("titulo-opciones-menu");
		opcion6.setStyleName("titulo-opciones-menu");
		opcion7.setStyleName("titulo-opciones-menu");
		
		gridMenu.setColumnExpandRatio(0, 13f);
		gridMenu.setColumnExpandRatio(1, 20f);
		gridMenu.setColumnExpandRatio(2, 13f);
		gridMenu.setColumnExpandRatio(3, 20f);
		gridMenu.setColumnExpandRatio(4, 13f);
		gridMenu.setColumnExpandRatio(5, 20f);
		
		Embedded nuevoExpedienteIcon = new Embedded( null, new ThemeResource("img/nuevoexpediente.png") ) ;
		nuevoExpedienteIcon.setWidth("40px");
		nuevoExpedienteIcon.setStyleName("imagen-opciones-menu");

		Embedded trazasIcon = new Embedded( null, new ThemeResource("img/trazas.png") ) ;
		trazasIcon.setWidth("40px");
		trazasIcon.setStyleName("imagen-opciones-menu");
		
		Embedded interfaseIcon = new Embedded( null, new ThemeResource("img/interfase.png") ) ;
		interfaseIcon.setWidth("40px");
		interfaseIcon.setStyleName("imagen-opciones-menu");
		
		Embedded gedoxIcon = new Embedded( null, new ThemeResource("img/gedox.png") ) ;
		gedoxIcon.setWidth("40px");
		gedoxIcon.setStyleName("imagen-opciones-menu");
		
		Embedded registroIcon = new Embedded( null, new ThemeResource("img/nuevousuario.png") ) ;
		registroIcon.setWidth("40px");
		registroIcon.setStyleName("imagen-opciones-menu");
		
		Embedded apirestIcon = new Embedded( null, new ThemeResource("img/interfase.png") ) ;
		apirestIcon.setWidth("40px");
		apirestIcon.setStyleName("imagen-opciones-menu");
		
		Embedded presupuestosIcon = new Embedded( null, new ThemeResource("img/calculadora.png") ) ;
		presupuestosIcon.setWidth("40px");
		presupuestosIcon.setStyleName("imagen-opciones-menu");		
		
		gridMenu.addComponent(opcion0,0,0,5,0);
		gridMenu.addComponent(new HorizontalRule(),0,1,5,1);
		gridMenu.setComponentAlignment(opcion0, Alignment.MIDDLE_CENTER);
		
		
		
		HorizontalLayout hlOpc1 = new HorizontalLayout();
		hlOpc1.setMargin(true);
		hlOpc1.setSpacing(true);
		hlOpc1.addComponent(nuevoExpedienteIcon);
		hlOpc1.addComponent(opcion1);
		hlOpc1.setComponentAlignment(opcion1, Alignment.MIDDLE_LEFT);
		
		/*HorizontalLayout hlOpc2 = new HorizontalLayout();
		hlOpc2.setMargin(true);
		hlOpc2.setSpacing(true);
		hlOpc2.addComponent(interfaseIcon);
		hlOpc2.addComponent(opcion2);
		hlOpc2.setComponentAlignment(opcion2, Alignment.MIDDLE_LEFT);
		
		HorizontalLayout hlOpc3 = new HorizontalLayout();
		hlOpc3.setMargin(true);
		hlOpc3.setSpacing(true);
		hlOpc3.addComponent(trazasIcon);
		hlOpc3.addComponent(opcion3);
		hlOpc3.setComponentAlignment(opcion3, Alignment.MIDDLE_LEFT);
		
		HorizontalLayout hlOpc4 = new HorizontalLayout();
		hlOpc4.setMargin(true);
		hlOpc4.setSpacing(true);
		hlOpc4.addComponent(gedoxIcon);
		hlOpc4.addComponent(opcion4);
		hlOpc4.setComponentAlignment(opcion4, Alignment.MIDDLE_LEFT);*/
		

	
	    gridMenu.addComponent(hlOpc1,0,2);
		/*gridMenu.addComponent(hlOpc2,2,2);
		gridMenu.addComponent(hlOpc3,4,2);
		gridMenu.addComponent(hlOpc4,0,3);*/
		
		
		/*String sql = "SELECT COUNT(1) CUANTOS FROM EXUAH_PERMISOS WHERE CDPERMISO = 15 AND upper(CDUSUARIO) = '" + UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase() +"'" ;
		//System.out.println("Senetencia: " + sql);
		Generico0 sentencia  = service.ejecutaPAC("PAC_SHWEB_LISTAS", "F_QUERY", true,
				sql) ;
			
		
		HorizontalLayout hlOpc5 = new HorizontalLayout();
		if (sentencia!=null ) {
			for (Generico0.MapObject map : sentencia.getMapObject()){
				
				if ( map.getMap().get("CUANTOS").toString().equals("1") ) {
	
					
					hlOpc5.setMargin(true);
					hlOpc5.setSpacing(true);
					hlOpc5.addComponent(registroIcon);
					hlOpc5.addComponent(opcion5);
					hlOpc5.setComponentAlignment(opcion5, Alignment.MIDDLE_LEFT);
					gridMenu.addComponent(hlOpc5,2,3);
					
				}
	
			}	
		}*/
		
		/*HorizontalLayout hlOpc6 = new HorizontalLayout();
		hlOpc6.setMargin(true);
		hlOpc6.setSpacing(true);
		hlOpc6.addComponent(apirestIcon);
		hlOpc6.addComponent(opcion6);
		hlOpc6.setComponentAlignment(opcion6, Alignment.MIDDLE_LEFT);
		gridMenu.addComponent(hlOpc6,4,3);*/
		
		/*HorizontalLayout hlOpc7 = new HorizontalLayout();
		hlOpc7.setMargin(true);
		hlOpc7.setSpacing(true);
		hlOpc7.addComponent(presupuestosIcon);
		hlOpc7.addComponent(opcion7);
		hlOpc7.setComponentAlignment(opcion7, Alignment.MIDDLE_LEFT);
		gridMenu.addComponent(hlOpc7,0,4);*/
		
		/*if ( !UI.getCurrent().getSession().getAttribute("userxxx").toString().toUpperCase().equals("AMA_ADMON") ) {
			hlOpc7.setVisible(false);
		}*/
				

        
        menuOpcionesLayout.addComponent(gridMenu);
		setWidth("100%");
        setCompositionRoot(menuOpcionesLayout);
        
        // eventos del click de lasopciones del menu
        
        

          
        
        // OPCION ALTA DE EXPEDIENTE
        hlOpc1.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
				UI.getCurrent().getNavigator().navigateTo("ProvPantallaBusquedaExpedientes");	 
			}
		});
        
        // OPCION WS LIBERTY
       /* hlOpc2.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
				
				UI.getCurrent().getNavigator().navigateTo("PantallaWSLiberty(NORMAL)");	 
			}
		});*/
        
        // OPCION API REST LIBERTY ( WS ) 
        /*hlOpc6.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
				
				UI.getCurrent().getNavigator().navigateTo("PantallaAPIRest(NORMAL)");	 
			}
		});*/
        
        // OPCION PRESUPUESTOS
        /*hlOpc7.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
				
				UI.getCurrent().getNavigator().navigateTo("PantallaPresupuestos(NORMAL)");	 
			}
		}); */        
        
               
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
    
     
}
