package com.evoluum.format;

import java.util.List;

import com.evoluum.model.dto.LocalizacaoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class LocalizacaoCsvDataFormat implements DataFormat {

	List<LocalizacaoDTO> localizacoes;

	public LocalizacaoCsvDataFormat(List<LocalizacaoDTO> localizacoes) {
		this.localizacoes = localizacoes;
	}

	@Override
	public byte[] getData() throws JsonProcessingException {
		CsvMapper mapper = new CsvMapper();
		mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		CsvSchema schema = mapper.schemaFor(LocalizacaoDTO.class);
		String data = mapper.writer(schema).writeValueAsString(localizacoes);

		StringBuilder builder = new StringBuilder();

		builder.append(geraCabecalho());
		builder.append(System.lineSeparator());
		builder.append(data);

		return builder.toString().getBytes();
	}

	private String geraCabecalho() {
		StringBuilder builder = new StringBuilder();
		builder.append("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		return builder.toString();
	}
}
