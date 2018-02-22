package com.csi_ti.itaca.custom.general.api.model;

import java.util.List;

import com.csi_ti.itaca.architecture.tools.beaner.BeanerConfig;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Generico0DTO implements Generico0 {
	
	@BeanerConfig(contentAs = MapObject.class)
	private List<Generico0.MapObject> mapObject;

	
}
