package com.evoluum.service;

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

	public List<LocalizacaoDTO> getTodosOsDados(HttpServletResponse response) {
		return webClientService.getTodosOsDadosJSON();
	}

	public void getTodosOsDadosCSV(HttpServletResponse response) {
		List<LocalizacaoDTO> listLocalizacoes = webClientService.getTodosOsDadosJSON();
		LocalizacaoCsvDataFormat localizacaoCsvDataFormat = new LocalizacaoCsvDataFormat(listLocalizacoes);

		try {
			logger.info("Inicio da geração do arquivo CSV");
			localizacaoCsvDataFormat.gerarArquivo(response, listLocalizacoes);
			logger.info("Arquivo csv gerado com sucesso.");
		} catch (IOException e) {
			logger.info("Falha na geração do arquivo csv.");
			logger.error(e.getMessage());
		}
	}

	public String getIdMunicipio(String nomeCidade) {
		return webClientService.getIdMunicipio(nomeCidade);
	}

}
