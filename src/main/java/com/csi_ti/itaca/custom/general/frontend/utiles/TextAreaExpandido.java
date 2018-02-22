package com.csi_ti.itaca.custom.general.frontend.utiles;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class TextAreaExpandido extends CustomComponent  {
    private static final long serialVersionUID = -3354143876393393750L;

    public TextArea texto = new TextArea();
    public Label contador = new Label("123");
    public Label contadorgrande = new Label("123");
    public int pendiente = 0;
    TextArea textoModal  = new TextArea();
    
    public TextAreaExpandido ( String caption) {
    	
    	
    	// VENTANA
    	Window ventana = new Window();
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
    	
        // Display the current length interactively in the counter
        textoModal.addTextChangeListener(new TextChangeListener() {
            public void textChange(TextChangeEvent event) {
                int len = pendiente - event.getText().length();
                contadorgrande.setValue(" "+String.valueOf(len));
                
                float ini = (float) event.getText().length();
                float fin = pendiente;
                float quedan = ( ini / fin )  * 100 ;
                
                
                if ( quedan  > 98 ) {
                	contadorgrande.setStyleName("contador_rojo");
                } else if ( quedan > 70 ) {
                	contadorgrande.setStyleName("contador_naranja");
                }
                else {
                	contadorgrande.setStyleName("contador_verde");
                }                
            }
        });    	

	    BotoneraDoble botonera = new BotoneraDoble();
	    vl.addComponent(textoModal);
    	vl.addComponent(botonera);
	    vl.setComponentAlignment(botonera, Alignment.BOTTOM_CENTER);
	    vl.addComponent(contadorgrande);

		
		botonera.btCancelar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				texto.focus();
				ventana.close();
				
			}
		});
		
		botonera.btAceptar.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				
				texto.setValue( textoModal.getValue());

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
    	
    	// FIN VENTANA
    	
    	
    	
        GridLayout layout = new GridLayout(2,1);
        
        layout.setWidth("100%");
        layout.setSpacing(false);

        texto.setCaption(caption);
        layout.addComponent(texto,0,0);
        Button boton = new Button();
        boton.setStyleName(ValoTheme.BUTTON_TINY);
        boton.setCaption("+");
        //boton.setStyleName(ValoTheme.BUTTON_TINY);
        
        VerticalLayout vld = new VerticalLayout();
        
        vld.addComponent(contador);
        vld.addComponent(boton);
        vld.setMargin(false);
        vld.setWidth("30px");
        layout.addComponent(vld,1,0);
        layout.setColumnExpandRatio(0, 50);
        layout.setColumnExpandRatio(1, 1);
        vld.setComponentAlignment(boton, Alignment.BOTTOM_LEFT);
        vld.setComponentAlignment(contador, Alignment.TOP_RIGHT);
        layout.setComponentAlignment(vld, Alignment.BOTTOM_LEFT);
        
        // Display the current length interactively in the counter
        texto.addTextChangeListener(new TextChangeListener() {
            public void textChange(TextChangeEvent event) {
                int len = pendiente - event.getText().length();
                contador.setValue(" "+String.valueOf(len));
                
                float ini = (float) event.getText().length();
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
            }
        });
        
        texto.setImmediate(true);
        
        contador.setStyleName("contador_verde");
        contadorgrande.setStyleName("contador_verde");
        
        int longitud = texto.getMaxLength();
        contador.setValue(String.valueOf(longitud));
        contadorgrande.setValue(String.valueOf(longitud));
        
        boton.setTabIndex(-1);
        
		boton.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(ventana);
				textoModal.setValue(texto.getValue());
				
				int len = pendiente - texto.getValue().length();
                contadorgrande.setValue(" "+String.valueOf(len));
                
                float ini = (float) texto.getValue().length();
                float fin = pendiente;
                float quedan = ( ini / fin )  * 100 ;
                
                
                if ( quedan  > 98 ) {
                	contadorgrande.setStyleName("contador_rojo");
                } else if ( quedan > 70 ) {
                	contadorgrande.setStyleName("contador_naranja");
                }
                else {
                	contadorgrande.setStyleName("contador_verde");
                }				
				
		    	
		    }
		});	
		

		setWidth("100%");
        setCompositionRoot(layout);
    }
    
    public void longitudMaxima( int longitud ) {
    	
    	texto.setMaxLength(longitud);
    	textoModal.setMaxLength(longitud);
    	pendiente = longitud;
    	contador.setValue(String.valueOf(longitud));
    	contadorgrande.setValue(String.valueOf(longitud));
    }

    

    
     
}
