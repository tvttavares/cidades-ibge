package com.evoluum.model.dto;

import lombok.Data;

@Data
public class EstadoDTO {
	
	private int id;
	private String sigla;
	private RegiaoDTO regiao;
}
