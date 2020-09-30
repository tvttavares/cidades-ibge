package com.evoluum.service;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evoluum.format.LocalizacaoCsvDataFormat;
import com.evoluum.model.dto.LocalizacaoDTO;

@Service
public class LocalizacaoService {

	private Logger logger = LoggerFactory.getLogger(LocalizacaoService.class);

	@Autowired
	private WebClientService webClientService;

	private static final String FILE_NAME = "localizacoes.csv";

	public List<LocalizacaoDTO> getTodosOsDados(HttpServletResponse response) {
		return webClientService.getTodosOsDadosJSON();
	}

	public void getTodosOsDadosCSV(HttpServletResponse response) {
		List<LocalizacaoDTO> listLocalizacoes = webClientService.getTodosOsDadosJSON();

		try {
			logger.info("Inicio da geração do arquivo CSV");
			gerarArquivo(response, listLocalizacoes);
			logger.info("Arquivo csv gerado com sucesso.");
		} catch (IOException e) {
			logger.info("Falha na geração do arquivo csv.");
			logger.error(e.getMessage());
		}
	}

	private void gerarArquivo(HttpServletResponse response, List<LocalizacaoDTO> listLocalizacoes) throws IOException {
		response.setContentType("text/csv; charset=utf-8");
		response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME + "\"");

		LocalizacaoCsvDataFormat csvDataFormat = new LocalizacaoCsvDataFormat(listLocalizacoes);
		byte[] data = csvDataFormat.getData();

		response.getOutputStream().write(data);
	}

}
