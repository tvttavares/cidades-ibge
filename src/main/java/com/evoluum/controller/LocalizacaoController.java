package com.evoluum.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evoluum.model.dto.LocalizacaoDTO;
import com.evoluum.service.LocalizacaoService;

@RestController
@RequestMapping(value = "/api/localizacao")
public class LocalizacaoController {

	private Logger logger = LoggerFactory.getLogger(LocalizacaoController.class);

	@Autowired
	private LocalizacaoService localizacaoService;

	@GetMapping("/todos/json")
	public ResponseEntity<List<LocalizacaoDTO>> getTodosOsDadosJSON(HttpServletResponse response) {
		logger.info("Iniciando requisição de todos os dados em formato Json");
		List<LocalizacaoDTO> dados = localizacaoService.getTodosOsDados(response);

		return ResponseEntity.status(dados == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(dados);
	}

	@GetMapping("/todos/csv")
	public ResponseEntity<Object> getTodosOsDadosCSV(HttpServletResponse response) {
		logger.info("Iniciando requisição de todos os dados em formato CSV");
		localizacaoService.getTodosOsDadosCSV(response);

		return ResponseEntity.status(response.getContentType() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
				.body(null);
	}

}
