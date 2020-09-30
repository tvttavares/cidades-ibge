package com.evoluum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.evoluum.model.dto.EstadoDTO;
import com.evoluum.model.dto.LocalizacaoDTO;
import com.evoluum.model.dto.MunicipioDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class WebClientService {

	private Logger logger = LoggerFactory.getLogger(WebClientService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${endpoint.ibge.estados}")
	private String endpointEstados;

	@Value("${endpoint.ibge.municipios}")
	private String endpointMunicipios;

	@Value("${endpoint.ibge.municipio}")
	private String endpointMunicipio;

	@HystrixCommand(fallbackMethod = "gerarListaVazia", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000") })
	public List<LocalizacaoDTO> getTodosOsDadosJSON() {
		List<LocalizacaoDTO> listaLocalizacao = new ArrayList<>();
		List<EstadoDTO> listaEstados = getTodosEstados();

		logger.info("Leitura de municipios de cada estado.");
		listaEstados.forEach(estado -> {
			List<MunicipioDTO> listaMunicipios = getTodosMunicipiosPorEstado(estado);
			listaMunicipios.forEach(municipio -> listaLocalizacao.add(new LocalizacaoDTO(estado, municipio)));
		});
		logger.info("Municipios lidos com sucesso.");

		return listaLocalizacao;
	}

	@Cacheable("idMunicipio")
	public String getIdMunicipio(String nomeCidade) {
		logger.info("Buscando id da cidade: " + nomeCidade);
		String idCidade = restTemplate.getForObject(endpointMunicipio + nomeCidade, MunicipioDTO.class).getId();
		logger.info("Id da cidade lido com sucesso.");

		return idCidade;
	}

	public List<LocalizacaoDTO> gerarListaVazia() {
		logger.info("Circuit break executado!");
		return new ArrayList<>();
	}

	private List<EstadoDTO> getTodosEstados() {
		logger.info("Leitura de todos estados.");
		List<EstadoDTO> listaEstados = Arrays
				.asList(restTemplate.getForEntity(endpointEstados, EstadoDTO[].class).getBody());
		logger.info("Estados lidos com sucesso.");

		return listaEstados;
	}

	private List<MunicipioDTO> getTodosMunicipiosPorEstado(EstadoDTO estado) {
		List<MunicipioDTO> listaMunicipios = Arrays.asList(restTemplate
				.getForEntity(String.format(endpointMunicipios, estado.getId()), MunicipioDTO[].class).getBody());

		return listaMunicipios;
	}

}
