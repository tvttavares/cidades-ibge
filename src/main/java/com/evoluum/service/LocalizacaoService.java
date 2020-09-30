package com.evoluum.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evoluum.model.dto.LocalizacaoDTO;

@Service
public class LocalizacaoService {

	@Autowired
	private WebClientService webClientService;

	public List<LocalizacaoDTO> getTodosOsDados(HttpServletResponse response) {
		return webClientService.getTodosOsDadosJSON();
	}

}
