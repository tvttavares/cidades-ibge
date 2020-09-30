package com.evoluum.model.dto;

import lombok.Data;

@Data
public class MunicipioDTO {

	private String id;
	private String nome;
	private MicrorregiaoDTO microrregiao;
}
