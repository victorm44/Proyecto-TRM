package com.aliatic.core.trm.services.impl;

import java.util.List;
import java.util.Optional;

import com.aliatic.core.trm.persistence.entities.*;
import com.aliatic.core.trm.persistence.repositories.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.MensajeEntranteWebScrapingDTO;
import com.aliatic.core.trm.services.AlmacenarTRMService;
import jakarta.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import static com.aliatic.core.trm.config.Textos.Es.*;
import static com.aliatic.core.trm.config.Constants.*;

@Service
public class AlmacenarTRMServiceImpl implements AlmacenarTRMService {

	private final TiposCambioRepository tiposCambioRepository;
	private final FuentesRepository fuentesRepository;
	private final MonedasRepository monedasRepository;
	private final MetodosExtraccionRepository metodosExtraccionRepository;
	private final TiposTasasRepository tiposTasasRepository;
	private final ProveedoresRepository proveedoresRepository;
	private final String nombreClase;

	public AlmacenarTRMServiceImpl(TiposCambioRepository tiposCambioRepository, FuentesRepository fuentesRepository,
                                   MonedasRepository monedasRepository, MetodosExtraccionRepository metodosExtraccionRepository,
                                   TiposTasasRepository tiposTasasRepository, ProveedoresRepository proveedoresRepository) {
		this.tiposCambioRepository = tiposCambioRepository;
		this.fuentesRepository = fuentesRepository;
		this.monedasRepository = monedasRepository;
		this.metodosExtraccionRepository = metodosExtraccionRepository;
		this.tiposTasasRepository = tiposTasasRepository;
        this.proveedoresRepository = proveedoresRepository;
        this.nombreClase = this.getClass().getName();

	}

	@Override
	public List<TiposCambioEntity> convertirAInterno(List<MensajeEntranteWebScrapingDTO> mensajeEntrante) {

		String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();

		AliaticLogger.info(INICIO_TRANSFORMACION_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);
		List<TiposCambioEntity> listaSalida = new ArrayList<>();

		try {

			mensajeEntrante.forEach((MensajeEntranteWebScrapingDTO mensaje) -> {

				TiposCambioEntity cambio = new TiposCambioEntity();

				// Datos administrativos
				cambio.setFechaCreacion(new Date());
				cambio.setFechaModificacion(new Date());
				cambio.setUsuarioCreacion(ALIATIC_BACKGROUND);
				cambio.setUsuarioModificacion(ALIATIC_BACKGROUND);

				// Datos de TRM
				cambio.setAnioSemanaVigencia(Integer.parseInt(mensaje.getSemana()));
				cambio.setEstado(1);
				try {
					cambio.setFechafinVigencia(new SimpleDateFormat(FORMAT_YYYY_MM_DD).parse(mensaje.getFechaFinal()));
					cambio.setFechaInicioVigencia(new SimpleDateFormat(FORMAT_YYYY_MM_DD).parse(mensaje.getFechaInicial()));
				} catch (ParseException e) {
					AliaticLogger.error(HUBO_UN_ERROR_AL_TRANSORMAR_ALGUNA_FECHA.getVal(), nombreClase, nombreMetodo,
							e.getMessage());
				}

				// Establecer el importe
				cambio.setImporte(Float.parseFloat(mensaje.getValorTasa()));

				// Procesamiento de las FK
				List<MonedasEntity> procedencia = monedasRepository.findByCodigoMoneda(mensaje.getMonedaOrigen());

				if (procedencia.isEmpty()) {
					AliaticLogger.error(NO_SE_ENCONTRO_PROCEDENCIA.getVal(), nombreClase, nombreMetodo,
							mensaje.getMonedaOrigen());
					return;
				}

				List<MonedasEntity> destino = monedasRepository.findByCodigoMoneda(mensaje.getMonedaDestino());

				if (destino.isEmpty()) {
					AliaticLogger.error(NO_SE_ENCONTRO_DESTINO.getVal(), nombreClase, nombreMetodo,
							mensaje.getMonedaDestino());
					return;
				}

				Optional<MetodosExtracionEntity> metodoExtracion = metodosExtraccionRepository
						.findById((long) mensaje.getMetodoExtraccion());

				if (metodoExtracion.isEmpty()) {
					AliaticLogger.error(NO_SE_ENCONTRO_METODO_EXTRACCION.getVal(), nombreClase, nombreMetodo,
							String.valueOf(mensaje.getMetodoExtraccion()));
					return;
				}


				Optional<TiposTasasEntity> tiposTasas = tiposTasasRepository
						.findById((long) mensaje.getTipoTasaInt());

				if (tiposTasas.isEmpty()) {
					AliaticLogger.error(NO_SE_ENCONTRARON_TIPOS_TASAS.getVal(), nombreClase, nombreMetodo,
							String.valueOf(mensaje.getTipoTasaInt()));
					return;
				}

				Optional<ProveedoresEntity> proveedores = proveedoresRepository
						.findById((long) mensaje.getProveedorInformacion());

				if (proveedores.isEmpty()){
					AliaticLogger.error(NO_SE_ENCONTRO_PROVEEDOR.getVal(), nombreClase, nombreMetodo,
							String.valueOf(mensaje.getProveedorInformacion()));
					return;
				}

				List<FuentesEntity> fuente = fuentesRepository
						.findBymonedaProcedenciaAndMonedaDestinoAndMetodoExtracionAndTipoTasaAndProveedoresInfo(procedencia.get(0),
								destino.get(0), metodoExtracion.get(), tiposTasas.get(), proveedores.get());

				if (fuente.isEmpty()) {
					AliaticLogger.error(NO_SE_ENCONTRO_FUENTE.getVal(), nombreClase, nombreMetodo, null);
					return;
				}

				cambio.setFuentes(fuente.get(0));
				listaSalida.add(cambio);

			});

			AliaticLogger.info(FIN_TRANSFORMACION_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);
			return listaSalida;

		} catch (Exception e) {
			AliaticLogger.error(ERROR_AL_TRANSOFRMAR_INFO_COLA_RABBIT.getVal(), nombreClase, nombreMetodo,
					e.getMessage());
		}

		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void almacenarImportes(List<TiposCambioEntity> tiposCambioEntity) {

		String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
		AliaticLogger.info(INICIO_ALMACENAMIENTO_DESDE_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);

		try {

			tiposCambioEntity.forEach((TiposCambioEntity tipoCambioFE) -> {

				if (tipoCambioFE != null) {
					tiposCambioRepository.save(tipoCambioFE);
				}

			});

			AliaticLogger.info(FIN_ALMACENAMIENTO_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);

		} catch (Exception e) {
			AliaticLogger.error(HUBO_ERROR_ALMACENAR_COLA_RABBIT.name(), nombreClase, nombreMetodo,
					e.getMessage());
		}

	}

}
