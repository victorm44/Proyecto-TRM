package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.ParametrosRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import com.aliatic.core.trm.persistence.entities.ParametrosEntity;
import com.aliatic.core.trm.persistence.repositories.ParametrosRepository;
import com.aliatic.core.trm.services.ParametrosService;

import java.util.Date;
import java.util.Optional;

import static com.aliatic.core.trm.config.Constants.*;
import static com.aliatic.core.trm.config.Textos.Es.*;

@Service
public class ParametrosServiceImpl implements ParametrosService {

	private ParametrosRepository parametroRepository;

	public ParametrosServiceImpl(ParametrosRepository parametroRepository){
		this.parametroRepository = parametroRepository;
	}


	@Override
	public ParametrosEntity save(ParametrosEntity parametroEntity) {
		return parametroRepository.save(parametroEntity);

	}

	@Override
	public Page<ParametrosEntity> findByParametroContaining(String prametro, int numeroPagina) {
		final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
		return parametroRepository.findByParametroContaining(prametro, pageable);
	}

	@Override
	public ParametrosEntity convertirReqADTO(ParametrosRequestDTO requestDTO) {
		ParametrosEntity parametrosEntity = new ParametrosEntity();

		parametrosEntity.setParametro(requestDTO.getCodigoParametro());
		parametrosEntity.setSistemaOComponente(requestDTO.getSistemaOComponente());
		parametrosEntity.setTexto1(requestDTO.getTexto1());
		parametrosEntity.setTexto2(requestDTO.getTexto2());
		parametrosEntity.setNumero1(requestDTO.getNumero1());
		parametrosEntity.setNumero2(requestDTO.getNumero2());
		parametrosEntity.setFecha1(requestDTO.getFecha1());
		parametrosEntity.setFecha2(requestDTO.getFecha2());
		parametrosEntity.setEstado(requestDTO.getEstado());

        return parametrosEntity;
	}

	@Override
	public Optional<ParametrosEntity> findById(Long id) {
		return parametroRepository.findById(id);
	}

	@Override
	public StandardResponseDTO updateParametro(Long idParametro, ParametrosEntity parametrosEntity) {
		return parametroRepository.findById(idParametro).map(parametroExistente -> {
			// Actualizar los datos del parametroExistente con los de parametrosEntity
			parametroExistente.setParametro(parametrosEntity.getParametro());
			parametroExistente.setSistemaOComponente(parametrosEntity.getSistemaOComponente());
			parametroExistente.setTexto1(parametrosEntity.getTexto1());
			parametroExistente.setTexto2(parametrosEntity.getTexto2());
			parametroExistente.setFecha1(parametrosEntity.getFecha1());
			parametroExistente.setFecha2(parametrosEntity.getFecha2());
			parametroExistente.setNumero1(parametrosEntity.getNumero1());
			parametroExistente.setNumero2(parametrosEntity.getNumero2());
			parametroExistente.setEstado(parametrosEntity.getEstado());

			parametroRepository.save(parametroExistente);

			StandardResponseDTO response = new StandardResponseDTO();
			response.setCodigoRespuestaInterno(HttpStatus.OK.value());
			response.setMensaje("Parametro actualizado con éxito");
			response.setPayload(parametroExistente);
			return response;
		}).orElseGet(() -> {
			StandardResponseDTO response = new StandardResponseDTO();
			response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
			response.setMensaje("Parametro no encontrado");
			return response;
		});
	}


	@Override
	public StandardResponseDTO almacenarParametro(ParametrosEntity parametrosEntity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usuario = auth.getPrincipal().toString();
		StandardResponseDTO response = new StandardResponseDTO();
		response.setFechaHora(new Date());

		if (parametrosEntity.getParametro() == null || VACIO.equals(parametrosEntity.getParametro())){
			response.setCodigoRespuestaInterno(530);
			response.setMensaje(EL_CODIGO_DE_PARAMETRO_NO_VACIO.getVal());
			return response;
		}
		if (parametrosEntity.getSistemaOComponente() == null || VACIO.equals(parametrosEntity.getSistemaOComponente())){
			response.setCodigoRespuestaInterno(530);
			response.setMensaje(SISTEMA_O_COMPONENTE_NO_VACIO.getVal());
			return response;
		}

		//Consultar si el parámetro ya existía previamente
		Optional<ParametrosEntity> validacion = parametroRepository.findByParametro(parametrosEntity.getParametro());
		if(validacion.isPresent() && parametrosEntity.getSistemaOComponente().equals(validacion.get().getSistemaOComponente())){
				response.setCodigoRespuestaInterno(530);
				response.setMensaje(PARAMETRO_REGISTRADO_PREVIAMENTE.getVal());
				return response;
		}


		parametrosEntity.setFechaCreacion(new Date());
		parametrosEntity.setFechaModificacion(new Date());
		parametrosEntity.setUsuarioCreacion(usuario);
		parametrosEntity.setUsuarioModificacion(usuario);

		ParametrosEntity parametrosNew = parametroRepository.save(parametrosEntity);
		response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
		response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
		response.setPayload(parametrosNew);
		return response;
	}



}
