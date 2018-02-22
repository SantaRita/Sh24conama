package com.csi_ti.itaca.custom.general.frontend;

import java.util.ArrayList;
import java.util.Map;

import com.csi_ti.itaca.architecture.tools.webmodule.pantallas.ItacaView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Label;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("tests-valo-reindeer")
public class ProvDatosObservacionesExpediente extends Panel implements ItacaView {

	public String UsuarioSave;

    TextArea observaciones = new TextArea();

	private static final long serialVersionUID = -304344441663815443L;

	// constructor inicial
	public ProvDatosObservacionesExpediente( Map<String, Object> retorno ) {


		
		
		// TAB TITULAR
		GridLayout dtitLayout = new GridLayout(1,2);
		dtitLayout.setStyleName("layout-lineas-juntas");
		dtitLayout.addComponent(new Label("Observaciones:"),0,0);
		
		dtitLayout.addComponent(observaciones);
		observaciones.setValue("");
		try {
		observaciones.setValue(retorno.get("OBSERVACIONES").toString());
		} catch ( Exception e) {};
		
		observaciones.setReadOnly(true);
		observaciones.setWidth("100%");
		observaciones.setHeight("100%");
		observaciones.setRows(25);


		dtitLayout.setWidth("100%");
		setContent(dtitLayout);

		
	}
	

	

	
	@Override
	public void enter(ViewChangeEvent event) {
		

	}	


}