package com.csi_ti.itaca.custom.general.api.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public interface Generico0 {

	List<MapObject> getMapObject();

		
	@Getter
	@Setter
	class MapObject{
	   Map map;
	}
	
}
