package com.evoluum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.evoluum.model.dto.EstadoDTO;
import com.evoluum.model.dto.LocalizacaoDTO;
import com.evoluum.model.dto.MunicipioDTO;

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

	public String getIdMunicipio(String nomeCidade) {
		logger.info("Buscando id da cidade: " + nomeCidade);
		String idCidade = restTemplate.getForObject(endpointMunicipio + nomeCidade, MunicipioDTO.class).getId();
		logger.info("Id da cidade lido com sucesso.");

		return idCidade;
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
