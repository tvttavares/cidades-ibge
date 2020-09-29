package com.evoluum.model.dto;

import lombok.Data;

@Data
public class LocalizacaoDTO {

	private Integer idEstado;
	private String siglaEstado;
	private String regiaoNome;
	private String nomeCidade;
	private String nomeMesorregiao;

	public LocalizacaoDTO(EstadoDTO estadoDTO, MunicipioDTO municipioDTO) {
		this.idEstado = estadoDTO.getId();
		this.siglaEstado = estadoDTO.getSigla();
		this.regiaoNome = estadoDTO.getRegiao().getNome();
		this.nomeCidade = municipioDTO.getNome();
		this.nomeMesorregiao = municipioDTO.getMicrorregiao().getMesorregiao().getNome();
	}

	public String getNomeFormatado() {
		return nomeCidade + "/" + siglaEstado;
	}

}
