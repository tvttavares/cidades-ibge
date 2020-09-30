package com.evoluum.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.evoluum.format.LocalizacaoCsvDataFormat;
import com.evoluum.model.dto.LocalizacaoDTO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LocalizacaoService.class })
public class LocalizacaoServiceTest {

	@MockBean
	private LocalizacaoService localizacaoService;

	@MockBean
	private WebClientService webClientService;

	@MockBean
	private HttpServletResponse response;

	@MockBean
	private LocalizacaoCsvDataFormat localizacaoCsvDataFormat;

	@Test
	public void testGetTodosOsDados() {
		when(webClientService.getTodosOsDadosJSON()).thenReturn(Collections.emptyList());
		assertEquals(Collections.emptyList(), localizacaoService.getTodosOsDados(response));
	}

	@Test
	public void testGetTodosOsDadosCSV() throws IOException {
		doNothing().when(localizacaoService).getTodosOsDadosCSV(response);
		localizacaoService.getTodosOsDadosCSV(response);
	}

	@Test
	public void testGetTodosOsDadosCSVException() throws IOException {
		doThrow(new IOException()).when(localizacaoCsvDataFormat).gerarArquivo(response,
				new ArrayList<LocalizacaoDTO>());
		localizacaoService.getTodosOsDadosCSV(response);
	}

}
