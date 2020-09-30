package com.evoluum.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.evoluum.model.dto.LocalizacaoDTO;
import com.evoluum.service.LocalizacaoService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LocalizacaoController.class })
public class LocalizacaoControllerTest {

	@Autowired
	private LocalizacaoController localizacaoController;

	@MockBean
	private LocalizacaoService localizacaoService;

	@MockBean
	private HttpServletResponse response;

	@Test
	public void testgetTodosOsDadosJSON404() {
		when(localizacaoService.getTodosOsDados(response)).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, localizacaoController.getTodosOsDadosJSON(response).getStatusCode());
	}

	@Test
	public void testGetTodosOsDadosJSON() {
		when(localizacaoService.getTodosOsDados(response)).thenReturn((new ArrayList<LocalizacaoDTO>()));
		assertEquals(HttpStatus.OK, localizacaoController.getTodosOsDadosJSON(response).getStatusCode());
	}

	@Test
	public void getIdMunicipio() {
		when(localizacaoService.getIdMunicipio("cidade_teste")).thenReturn(new String());
		assertEquals(HttpStatus.OK, localizacaoController.getIdMunicipio("cidade_teste").getStatusCode());
	}

}
