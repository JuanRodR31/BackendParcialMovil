package com.parcialweb.parcial.Servicio;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcialweb.parcial.DTO.EntidadDTO;
import com.parcialweb.parcial.DTO.EntidadCreationDTO;
import com.parcialweb.parcial.Entidades.Contrato;
import com.parcialweb.parcial.Entidades.Entidad;
import com.parcialweb.parcial.Repositories.EntidadRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntidadService {

    @Autowired
    private EntidadRepository entidadRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EntidadDTO saveEntidad(EntidadCreationDTO entidadCreationDTO) {
        Entidad entidad = modelMapper.map(entidadCreationDTO, Entidad.class);

        // Mapea y asocia los contratos
        if (entidadCreationDTO.getContratos() != null) {
            List<Contrato> contratos = entidadCreationDTO.getContratos().stream()
                    .map(contratoDTO -> {
                        Contrato contrato = modelMapper.map(contratoDTO, Contrato.class);
                        contrato.setEntidad(entidad); // Asegura la relación bidireccional
                        return contrato;
                    })
                    .collect(Collectors.toList());
            entidad.setContratos(contratos);
        }

        Entidad savedEntidad = entidadRepository.save(entidad);
        return modelMapper.map(savedEntidad, EntidadDTO.class);
    }

    public List<EntidadDTO> getAllEntidades() {
        return entidadRepository.findAll().stream()
                .map(entidad -> modelMapper.map(entidad, EntidadDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<EntidadDTO> getEntidadById(Long id) {
        return entidadRepository.findById(id)
                .map(entidad -> modelMapper.map(entidad, EntidadDTO.class));
    }

    @Transactional
    public EntidadDTO updateEntidad(Long id, EntidadCreationDTO entidadDetailsDTO) {
        Optional<Entidad> optionalEntidad = entidadRepository.findById(id);
        if (optionalEntidad.isPresent()) {
            Entidad entidad = optionalEntidad.get();
            modelMapper.map(entidadDetailsDTO, entidad);
            if (entidadDetailsDTO.getContratos() != null) {
                entidad.getContratos().clear(); // Elimina los contratos existentes
                entidadDetailsDTO.getContratos().forEach(contratoDTO -> {
                    Contrato newContrato = modelMapper.map(contratoDTO, Contrato.class);
                    newContrato.setEntidad(entidad);
                    entidad.getContratos().add(newContrato);
                });
            }


            Entidad updatedEntidad = entidadRepository.save(entidad);
            return modelMapper.map(updatedEntidad, EntidadDTO.class);
        } else {
            throw new RuntimeException("Entidad no encontrada con id " + id);
        }
    }

    public void deleteEntidad(Long id) {
        entidadRepository.deleteById(id);
    }
}
