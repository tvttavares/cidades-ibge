package com.evoluum.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LocalizacaoService.class })
public class LocalizacaoServiceTest {

	@MockBean
	private LocalizacaoService localizacaoService;

	@MockBean
	private WebClientService webClientService;

	@MockBean
	private HttpServletResponse response;

	@Test
	public void testGetTodosOsDados() {
		when(webClientService.getTodosOsDadosJSON()).thenReturn(null);
		assertEquals(null, localizacaoService.getTodosOsDados(response));
	}

}
