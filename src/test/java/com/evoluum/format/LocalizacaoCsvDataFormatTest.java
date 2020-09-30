package com.evoluum.format;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.evoluum.model.dto.LocalizacaoDTO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LocalizacaoCsvDataFormat.class })
public class LocalizacaoCsvDataFormatTest {

	@MockBean
	private HttpServletResponse response;

	@MockBean
	private LocalizacaoCsvDataFormat localizacaoCsvDataFormat;

	@MockBean
	private ServletOutputStream servletOutputStream;

	@Test
	public void testGerarAquivo() throws IOException {
		doReturn(servletOutputStream).when(response).getOutputStream();
		localizacaoCsvDataFormat.gerarArquivo(response, new ArrayList<LocalizacaoDTO>());
	}

	@Test
	public void testTransformarDadosException() throws IOException {
		doThrow(new IOException()).when(response).getOutputStream();
		localizacaoCsvDataFormat.gerarArquivo(response, new ArrayList<LocalizacaoDTO>());
	}

}
