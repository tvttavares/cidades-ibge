package com.evoluum.controller;

import static com.evoluum.config.swagger.SwaggerLinks.DESC_CIDADE_BY_NAME;
import static com.evoluum.config.swagger.SwaggerLinks.DESC_CONTROLLER_CSV;
import static com.evoluum.config.swagger.SwaggerLinks.DESC_CONTROLLER_JSON;
import static com.evoluum.config.swagger.SwaggerLinks.TAG_LOCALIZACOES;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evoluum.model.dto.LocalizacaoDTO;
import com.evoluum.service.LocalizacaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/localizacao")
@Api(tags = TAG_LOCALIZACOES)
public class LocalizacaoController {

	private Logger logger = LoggerFactory.getLogger(LocalizacaoController.class);

	@Autowired
	private LocalizacaoService localizacaoService;

	@GetMapping("/todos/json")
	@ApiOperation(value = DESC_CONTROLLER_JSON)
	public ResponseEntity<List<LocalizacaoDTO>> getTodosOsDadosJSON(HttpServletResponse response) {
		logger.info("Iniciando requisição de todos os dados em formato Json");
		List<LocalizacaoDTO> dados = localizacaoService.getTodosOsDados(response);

		return ResponseEntity.status(dados == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(dados);
	}

	@GetMapping("/todos/csv")
	@ApiOperation(value = DESC_CONTROLLER_CSV)
	public ResponseEntity<Object> getTodosOsDadosCSV(HttpServletResponse response) {
		logger.info("Iniciando requisição de todos os dados em formato CSV");
		localizacaoService.getTodosOsDadosCSV(response);

		return ResponseEntity.status(response.getContentType() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
				.body(null);
	}

	@GetMapping("/municipio/{nomeCidade}")
	@ApiOperation(value = DESC_CIDADE_BY_NAME)
	public ResponseEntity<String> getIdMunicipio(@PathVariable String nomeCidade) {
		logger.info("Iniciando requisição para nome de cidade.");
		String idCidade = localizacaoService.getIdMunicipio(nomeCidade.trim());
		return ResponseEntity.status(HttpStatus.OK).body(idCidade);
	}

}
