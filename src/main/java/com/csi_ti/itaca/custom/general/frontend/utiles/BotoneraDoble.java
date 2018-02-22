package com.csi_ti.itaca.custom.general.frontend.utiles;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BotoneraDoble extends HorizontalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Button btAceptar = new Button();
	public Button btCancelar = new Button();
	
	// constructor inicial
	public BotoneraDoble() {
		
		
	    btAceptar.setCaption("Aceptar");
	    btCancelar.setCaption("Cancelar");
	    
    
	    btAceptar.setId("BTACEPTAR");
	    btCancelar.setId("BTCANCELAR");
	    
	    btAceptar.setStyleName(ValoTheme.BUTTON_FRIENDLY);
	    btCancelar.setStyleName(ValoTheme.BUTTON_DANGER);
	    
	    this.setMargin(true);
	    this.setSpacing(true);

		
		this.addComponent(btCancelar);
		this.addComponent(btAceptar);
		
		
		
		this.setWidth("100%");
		
		this.setComponentAlignment(btAceptar, Alignment.TOP_RIGHT);
		this.setComponentAlignment(btCancelar, Alignment.TOP_LEFT);
		
		btAceptar.setTabIndex(-1);
		btCancelar.setTabIndex(-1);
		

		
	}

}