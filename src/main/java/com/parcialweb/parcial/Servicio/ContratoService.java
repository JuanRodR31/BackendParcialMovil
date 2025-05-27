package com.parcialweb.parcial.Servicio;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcialweb.parcial.DTO.ContratoCreationDTO;
import com.parcialweb.parcial.DTO.ContratoDTO;
import com.parcialweb.parcial.Entidades.Contrato;
import com.parcialweb.parcial.Entidades.Entidad;
import com.parcialweb.parcial.Repositories.ContratoRepository;
import com.parcialweb.parcial.Repositories.EntidadRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private EntidadRepository entidadRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ContratoDTO saveContrato(ContratoCreationDTO contratoCreationDTO) {
        Contrato contrato = modelMapper.map(contratoCreationDTO, Contrato.class);

        if (contratoCreationDTO.getEntidadId() != null) {
            Entidad entidad = entidadRepository.findById(contratoCreationDTO.getEntidadId())
                    .orElseThrow(() -> new RuntimeException("Entidad no encontrada con id " + contratoCreationDTO.getEntidadId()));
            contrato.setEntidad(entidad);
        }

        Contrato savedContrato = contratoRepository.save(contrato);
        return modelMapper.map(savedContrato, ContratoDTO.class);
    }

    public List<ContratoDTO> getAllContratos() {
        return contratoRepository.findAll().stream()
                .map(contrato -> {
                    ContratoDTO contratoDTO = modelMapper.map(contrato, ContratoDTO.class);
                    if (contrato.getEntidad() != null) {
                        contratoDTO.setEntidadId(contrato.getEntidad().getId());
                    }
                    return contratoDTO;
                })
                .collect(Collectors.toList());
    }

    public Optional<ContratoDTO> getContratoById(Long id) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    ContratoDTO contratoDTO = modelMapper.map(contrato, ContratoDTO.class);
                    if (contrato.getEntidad() != null) {
                        contratoDTO.setEntidadId(contrato.getEntidad().getId());
                    }
                    return contratoDTO;
                });
    }

    public ContratoDTO updateContrato(Long id, ContratoCreationDTO contratoDetailsDTO) {
        Optional<Contrato> optionalContrato = contratoRepository.findById(id);
        if (optionalContrato.isPresent()) {
            Contrato contrato = optionalContrato.get();

            // ModelMapper se encargará de mapear los campos existentes y los nuevos
            // (nombreContratista, documentoContratista) del DTO a la entidad.
            modelMapper.map(contratoDetailsDTO, contrato);

            // Actualiza la relación con la Entidad si se proporciona un nuevo entidadId
            if (contratoDetailsDTO.getEntidadId() != null) {
                // Solo busca y actualiza si el ID de la entidad en el DTO es diferente
                // del ID de la entidad actual en el contrato, o si el contrato no tiene una entidad asociada
                if (contrato.getEntidad() == null || !contratoDetailsDTO.getEntidadId().equals(contrato.getEntidad().getId())) {
                    Entidad entidad = entidadRepository.findById(contratoDetailsDTO.getEntidadId())
                            .orElseThrow(() -> new RuntimeException("Entidad no encontrada con id " + contratoDetailsDTO.getEntidadId()));
                    contrato.setEntidad(entidad);
                }
            } else {
                // Si el entidadId en el DTO es null, y el contrato tenía una entidad, la desasocia
                contrato.setEntidad(null);
            }


            Contrato updatedContrato = contratoRepository.save(contrato);
            return modelMapper.map(updatedContrato, ContratoDTO.class);
        } else {
            throw new RuntimeException("Contrato no encontrado con id " + id);
        }
    }

    public void deleteContrato(Long id) {
        contratoRepository.deleteById(id);
    }
}