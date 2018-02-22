package com.csi_ti.itaca.custom.general.frontend;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csi_ti.itaca.custom.general.server.jdbc.util.Constantes;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class Util {
	
	 public static Object tratarRETURNyMENSAJES(Map map) {
       
        if (map==null)
            throw new IllegalArgumentException("El argumento map no puede ser nulo.");

        if (!isEmpty(map.get("MENSAJES")))
            parsearMensajes((List)map.get("MENSAJES"));
       
        if (!isEmpty(map.get("RETURN")))
            return map.get("RETURN");

        return null;
    }
	 
	/**  
     * Comprueba que el String pasado no es nulo ni est? en blanco.
     * @param s El String a comprobar.
     * @return boolean True si es nulo o en blanco, False en otro caso.
     */
    public static boolean isEmpty(String s) {
        return (s==null||s.equals(""));
    }

    /**
     * Comprueba que la Collection pasada no es nula ni est? vac?a.
     * @param col La Collection a comprobar (List, ArrayList, Set...).
     * @return boolean True si es nula o vac?a, False en otro caso.
     */
    public static boolean isEmpty(Collection col) {
        return (col==null||col.isEmpty());
    }

    /**
     * Comprueba que el Map pasado no es nulo ni est? vac?o.
     * @param map El Map a comprobar (Map, HashMap...).
     * @return boolean True si es nulo o vac?o, False en otro caso.
     */
    public static boolean isEmpty(Map map) {
        return (map==null||map.isEmpty());
    }

    /**
     * Comprueba que el array pasado no es nulo ni est? vac?o.
     * @param object El Object[] a comprobar (String[], int[]...).
     * @return boolean True si es nulo o vac?o, False en otro caso.
     */
    public static boolean isEmpty(Object[] object) {
        return (object==null||object.length==0);
    }

    /**
     * Comprueba que el Objeto pasado no es nulo ni est? vac?o.
     * Estar vac?o (no tener elmentos o valor) funciona para Collections, Maps o Strings.
     * Para los dem?s objetos devuelve el resultado de la condici?n object == null.
     * @param object El objeto a comprobar.
     * @return boolean True si es nulo o vac?o, False en otro caso.
     * @throws IllegalArgumentException si el argumento pasado es de tipo err?neo.
     */
    public static boolean isEmpty(Object object) {
        if (object==null)
            return true;
        else if (object instanceof Collection)
            return isEmpty((Collection)object);
        else if (object instanceof Map)
            return isEmpty((Map)object);
        else if (object instanceof String)
            return isEmpty((String)object);
        else if (object instanceof String[])
            return isEmpty((String[])object);
        else
            return false;
       
    }
    
    public static void parsearMensajes(List MENSAJES) {
    	
        if (MENSAJES==null || MENSAJES.size()==0) return;

        for (int i=0;i<MENSAJES.size();i++) {
            HashMap mensaje=(HashMap)MENSAJES.get(i);
            HashMap OB_IAX_MENSAJES=(HashMap)mensaje.get("OB_IAX_MENSAJES");
            BigDecimal TIPERROR=(BigDecimal)OB_IAX_MENSAJES.get("TIPERROR");
            BigDecimal CERROR=(BigDecimal)OB_IAX_MENSAJES.get("CERROR");
            String TERROR=(String)OB_IAX_MENSAJES.get("TERROR");
            
            int mensajeTipo=Constantes.MENSAJE_INFO;
            switch(TIPERROR.intValue()) {
                case 1: mensajeTipo=Constantes.MENSAJE_ERROR; break;
                case 2: mensajeTipo=Constantes.MENSAJE_INFO; break;
            }
        }
    }

	private static void addComponentsToLayoutInHorizontal(AbstractOrderedLayout layout, Alignment alignment, Boolean sizeFull, Boolean margin,
															Boolean spacing, String style, Component...components){
		
		if (layout!=null){
			HorizontalLayout horizontal = new HorizontalLayout();
			if (style!=null) horizontal.setStyleName(style);
			if (Boolean.TRUE.equals(margin))horizontal.setSpacing(true);
			if (Boolean.TRUE.equals(spacing))horizontal.setMargin(true);
			if (Boolean.TRUE.equals(sizeFull))horizontal.setSizeFull();			
			for (Component component : components){
				horizontal.addComponent(component);				
			}
			layout.addComponent(horizontal);
			if (alignment!=null)layout.setComponentAlignment(horizontal, alignment);
		}
	}
	
	//sizefull-->true
	static void addComponentsInHorizontalFullSize(AbstractOrderedLayout layout, String style, Component...components){
		addComponentsToLayoutInHorizontal(layout, null, true, true, true, style, components);	
	}
	
	//alignment-->??
	static void addComponentsToLayoutInHorizontal(AbstractOrderedLayout layout, Alignment alignment, String style, Component...components){
		addComponentsToLayoutInHorizontal(layout, alignment, false, true, true, style, components);	
	}
	
	//BOTONERA --> alignment RIGHT
	static void addButtonsBar(AbstractOrderedLayout layout, String style, Button...components){
		addComponentsToLayoutInHorizontal(layout, Alignment.TOP_RIGHT, false, true, true, style, components);	
	}
	
	//TITLES --> alignment RIGHT
	static void addTitle(AbstractOrderedLayout layout, String style, Label component){
		addComponentsToLayoutInHorizontal(layout, Alignment.TOP_RIGHT, false, true, true, style, component);	
	}
	
	//TITLE SECTION 
	static void addTitleSection(AbstractOrderedLayout layout, Label component){
		component.setStyleName("border1");
		layout.addComponent(component);
		//addComponentsToLayoutInHorizontal(layout, null, true, false, false, null, component);	
	}	
	
	static void addArrayComponentsToLayout(AbstractOrderedLayout layout, Boolean sizeFull, String style, Component[][] components){
		if (layout!=null){
			
			int maxComponentsRow =0;
			for (Component[] array : components){
			   if (maxComponentsRow<array.length)maxComponentsRow = array.length;
			}
			
			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.setMargin(true);
			verticalLayout.setSpacing(true);			
			
			for (Component[] array : components){
				HorizontalLayout horizontal = new HorizontalLayout();				
				if (Boolean.TRUE.equals(sizeFull))horizontal.setSizeFull();
				horizontal.setSpacing(true);
				for (Component component : array){
					horizontal.addComponent(component);
				}
				for (int i=0; i<maxComponentsRow-array.length;i++){
					Label emptyLabel = new Label();
					horizontal.addComponent(emptyLabel);
				}
				verticalLayout.addComponent(horizontal);
			}
			layout.addComponent(verticalLayout);
			if (style!=null) verticalLayout.setStyleName(style);
		}
	}
	
	static void addArrayComponentsToLayout(AbstractOrderedLayout layout, String style, Component[][] components){
		addArrayComponentsToLayout(layout, true, style, components);
	}
	
	
	public static void defineTable(Table table,String[]columns, Object[] typeColumns, Object[] visibleColumns, Boolean fullSize){
		
		if (table!=null && columns!=null &&  typeColumns!=null && columns.length == typeColumns.length){
			if(fullSize)table.setSizeFull();
			int index =0;
			for(String column: columns){
				table.addContainerProperty(column, (Class<?>) typeColumns[index], null);
				index++;
			}			
			if (visibleColumns!=null)table.setVisibleColumns(visibleColumns);
		}
	}
}
