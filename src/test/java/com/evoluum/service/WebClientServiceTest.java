package com.evoluum.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.evoluum.model.dto.EstadoDTO;
import com.evoluum.model.dto.MesorregiaoDTO;
import com.evoluum.model.dto.MicrorregiaoDTO;
import com.evoluum.model.dto.MunicipioDTO;
import com.evoluum.model.dto.RegiaoDTO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebClientService.class })
public class WebClientServiceTest {

	@SpyBean
	private WebClientService webClientService;

	@MockBean
	private RestTemplate restTemplate;

	@Value("${endpoint.ibge.estados}")
	private String endpointEstados;

	@Value("${endpoint.ibge.municipios}")
	private String endpointMunicipios;

	private static final int ESTADO_ID = 1;

	@Test
	public void testgetTodosOsDadosJSON() {
		EstadoDTO[] arrayEstadoDTO = new EstadoDTO[1];
		arrayEstadoDTO[0] = gerarEstadoDTO();
		ResponseEntity<EstadoDTO[]> responseEntityEstadoDTO = new ResponseEntity<EstadoDTO[]>(arrayEstadoDTO,
				HttpStatus.OK);
		when(restTemplate.getForEntity(endpointEstados, EstadoDTO[].class)).thenReturn(responseEntityEstadoDTO);

		MunicipioDTO[] arrayMunicipioDTO = new MunicipioDTO[1];
		arrayMunicipioDTO[0] = gerarMunicipioDTO();
		ResponseEntity<MunicipioDTO[]> responseEntityMunicipioDTO = new ResponseEntity<MunicipioDTO[]>(
				arrayMunicipioDTO, HttpStatus.OK);
		when(restTemplate.getForEntity(String.format(endpointMunicipios, ESTADO_ID), MunicipioDTO[].class))
				.thenReturn(responseEntityMunicipioDTO);

		assertNotNull(webClientService.getTodosOsDadosJSON());
	}

	private MunicipioDTO gerarMunicipioDTO() {
		MunicipioDTO municipioDTO = new MunicipioDTO();
		MicrorregiaoDTO microrregiao = new MicrorregiaoDTO();
		MesorregiaoDTO mesorregiao = new MesorregiaoDTO();

		mesorregiao.setNome("mesorregiao teste");
		microrregiao.setMesorregiao(mesorregiao);
		municipioDTO.setMicrorregiao(microrregiao);

		return municipioDTO;
	}

	private EstadoDTO gerarEstadoDTO() {
		EstadoDTO estadoDTO = new EstadoDTO();
		estadoDTO.setId(ESTADO_ID);

		RegiaoDTO regiao = new RegiaoDTO();
		regiao.setNome("regiao teste");
		estadoDTO.setRegiao(regiao);

		return estadoDTO;
	}
}
