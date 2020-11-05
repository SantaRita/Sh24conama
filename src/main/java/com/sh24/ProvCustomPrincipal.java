package com.sh24;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import com.csi_ti.itaca.architecture.tools.configurator.Configurator;
import com.csi_ti.itaca.architecture.tools.i18n.ItacaMessageResolver;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
@PreserveOnRefresh
@UIScope
@SpringComponent
public class ProvCustomPrincipal extends CustomComponent {
	
	private static final long serialVersionUID = -641725931242567777L;

	private VerticalLayout layout;
	private HorizontalLayout topLayout;
	
	//private LogTablas logUsuario;
	private ProvMenuPrincipal menuPrincipal;

	private HorizontalLayout menuLayout;
	
	private Button logout;

	public String usuario = "";;
	
	private VerticalLayout main;
	private Label lblProveedores = new Label("WEB Consultas AMA");

	@Getter
	private Panel viewContainer;

	@Autowired
	private ItacaMessageResolver messageResolver;

	@Autowired
	private Configurator configurator;

	public void init(  ) {
		
		// TODO Auto-generated method stub
		
		// LAYOUT PRINCIPAL
		
		//System.out.println("Entramos al init**************************************");
		layout = new VerticalLayout();
		layout.setSizeFull();
		
		layout.setMargin(false);
		layout.setSpacing(false);

		Resource res = new ThemeResource("img/logosh24_menu.jpg");
		Image image = new Image(null, res);
		image.setHeight("30px");
		
		Resource resMenu = new ThemeResource("img/menu.png");
		Image imageMenu = new Image(null, resMenu);
		imageMenu.setHeight("28px");
		
	
		
		// LAYOUT LA BARRA DE MENU
		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setMargin(false);
		menuLayout.addComponent(image);
		menuLayout.setComponentAlignment(image, Alignment.TOP_LEFT);
		
		
		
		
		
		menuLayout.setStyleName("menu-panel");
		//menuLayout.setHeight("40px");
		menuLayout.setWidth("100%");


		Label lbluser = new Label();
		usuario = (String) UI.getCurrent().getSession().getAttribute("user");
		
		
		

		String idsesion = "&nbsp;[&nbsp;"+(String) UI.getCurrent().getSession().getSession().getId()+"&nbsp;]&nbsp;&nbsp;";
		String usunombre = "&nbsp;[&nbsp;"+(String) UI.getCurrent().getSession().getAttribute("username")+"&nbsp;]&nbsp;&nbsp;";
		String tipousuario = "<b>&nbsp;[&nbsp;"+(String) UI.getCurrent().getSession().getAttribute("tipousuario")+"&nbsp;]&nbsp;&nbsp;</b>";
		StringBuffer strb = new StringBuffer();   
		
		if ( UI.getCurrent().getSession().getAttribute("entorno").equals("TEST" )) {
				strb.append("<p style='background-color:white;height:15px;font-size:10px'><b>Usuario:&nbsp;&nbsp;</b>")
				.append(usuario)
				.append(usunombre)
				.append(tipousuario)
				
				//.append(idsesion)
				.append("</p>");
		}
		else if ( UI.getCurrent().getSession().getAttribute("entorno").equals("AWS" )) {
					strb.append("<p style='background-color:white;height:15px;font-size:10px;color:blue'><b>Usuario:&nbsp;&nbsp;</b>")
					.append(usuario)
					.append(usunombre)
					.append(tipousuario)
					
					//.append(idsesion)
					.append("</p>");				
		} else {
			strb.append("<p style='height:25px;font-size:10px'><b>Usuario:&nbsp;&nbsp;</b>")
			.append(usuario)
			.append(usunombre)
			.append(tipousuario)
			
			.append("</p>");
			
		}
		lbluser.setCaption(strb.toString());   
		lbluser.setCaptionAsHtml(true);      
		

		logout = new Button("Logout");
		logout.addClickListener((e) -> doLogout());
		logout.setStyleName(ValoTheme.BUTTON_DANGER);
		logout.setWidth("70px");
		


		HorizontalLayout barraH = new HorizontalLayout();
		barraH.setWidth("100%");
		barraH.setHeight("32px");
		

		
		// Añadimos el menú principal de momento también solo para AMA_ADMON
		//if (usuario.toUpperCase().equals("AMA_ADMON") || usuario.toUpperCase().equals("RVALLSBA")
		//		|| UI.getCurrent().getSession().getAttribute("entorno").equals("TEST" ) ) {

		menuPrincipal = new ProvMenuPrincipal();
		//menuPrincipal.menuOpcionesBoton.addComponent(imageMenu);
		barraH.addComponent(menuPrincipal);
		
		//}
			
		/*TimerTask tt = new TimerTask() {

		    @Override
		    public void run() {
		        try {
		        	System.out.println("Entramos timer");
		        } catch (Exception ex) {
		        	System.out.println("Exception timer");
		        }
		    }
		};
		Timer t = new Timer(true);
		t.scheduleAtFixedRate(tt, 0, 3000);		

		barraH.addComponent(imageMensajes);
		barraH.setComponentAlignment(imageMensajes,Alignment.MIDDLE_LEFT);*/
			
		/*menuLayout.addComponent(lblProveedores);
		menuLayout.setComponentAlignment(lblProveedores, Alignment.MIDDLE_LEFT);*/
		menuLayout.addComponent(lbluser);
		menuLayout.setComponentAlignment(lbluser, Alignment.TOP_LEFT);
		
		//barraH.addComponent(lbluser);
		barraH.addComponent(logout);
		barraH.setMargin(false);
		
		
		barraH.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
		

		
		barraH.setCaptionAsHtml(true);
		
		logout.setVisible(true);
		logout.setTabIndex(-1);
		

		menuLayout.addComponent(barraH);
		menuLayout.setComponentAlignment(barraH, Alignment.TOP_LEFT);

		
	

		viewContainer = new Panel();
		viewContainer.setStyleName("views-panel");
		viewContainer.setSizeFull();


		
		layout.addComponent(menuLayout);
		layout.addComponent(viewContainer);
		  
		layout.setExpandRatio(viewContainer,1);
		
        super.setCompositionRoot(layout);
		super.setSizeFull();
		

		
		
	}



	
	
	/**
	 * MÃ©todo que realiza el logout de la aplicaciÃ³n.
	 */
	private void doLogout() {
		
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
    	ConfirmDialog.show(UI.getCurrent(), "LOGOUT", "Seguro que desea desconectarse de la sesión ? \n",
    	
    	        "Si", "No", new ConfirmDialog.Listener() {

    	            public void onClose(ConfirmDialog dialog) {
    	                if (dialog.isConfirmed()) {
    	                    // Confirmed to continue
    	                	//VaadinSession.getCurrent().getSession().invalidate();
    	                	
    	                	// Vaciamos las variables de session
    	                	UI.getCurrent().getSession().close();
    	                	getUI().close();
    	            		getUI().getPage().reload();
    	                } else {
    	                    // User did not confirm
    	                    
    	                }
    	            }
    	        });
	
	}
	
   

}
