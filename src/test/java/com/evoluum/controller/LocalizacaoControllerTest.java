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
import org.springframework.http.ResponseEntity;
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
		assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null).getStatusCode(),
				localizacaoController.getTodosOsDadosJSON(response).getStatusCode());
	}

	@Test
	public void testGetTodosOsDadosJSON() {
		when(localizacaoService.getTodosOsDados(response)).thenReturn((new ArrayList<LocalizacaoDTO>()));
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(null).getStatusCode(),
				localizacaoController.getTodosOsDadosJSON(response).getStatusCode());
	}

}
