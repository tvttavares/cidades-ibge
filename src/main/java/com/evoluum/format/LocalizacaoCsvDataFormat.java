package com.evoluum.format;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.evoluum.model.dto.LocalizacaoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class LocalizacaoCsvDataFormat implements DataFormat {

	List<LocalizacaoDTO> localizacoes;

	private static final String FILE_NAME = "localizacoes.csv";

	public LocalizacaoCsvDataFormat(List<LocalizacaoDTO> localizacoes) {
		this.localizacoes = localizacoes;
	}

	public void gerarArquivo(HttpServletResponse response, List<LocalizacaoDTO> listLocalizacoes) throws IOException {
		response.setContentType("text/csv; charset=utf-8");
		response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME + "\"");

		LocalizacaoCsvDataFormat csvDataFormat = new LocalizacaoCsvDataFormat(listLocalizacoes);
		byte[] data = csvDataFormat.getData();

		response.getOutputStream().write(data);
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
