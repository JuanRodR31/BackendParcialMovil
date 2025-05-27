package com.parcialweb.parcial.Controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcialweb.parcial.DTO.EntidadCreationDTO;
import com.parcialweb.parcial.DTO.EntidadDTO;
import com.parcialweb.parcial.Servicio.EntidadService;

import java.util.List;

@RestController
@RequestMapping("/api/entidades")
public class EntidadController {

    @Autowired
    private EntidadService entidadService;

    @PostMapping
    public ResponseEntity<EntidadDTO> createEntidad(@RequestBody EntidadCreationDTO entidadCreationDTO) {
        EntidadDTO savedEntidadDTO = entidadService.saveEntidad(entidadCreationDTO);
        return new ResponseEntity<>(savedEntidadDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EntidadDTO>> getAllEntidades() {
        List<EntidadDTO> entidades = entidadService.getAllEntidades();
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadDTO> getEntidadById(@PathVariable Long id) {
        return entidadService.getEntidadById(id)
                .map(entidadDTO -> new ResponseEntity<>(entidadDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadDTO> updateEntidad(@PathVariable Long id, @RequestBody EntidadCreationDTO entidadDetailsDTO) {
        try {
            EntidadDTO updatedEntidadDTO = entidadService.updateEntidad(id, entidadDetailsDTO);
            return new ResponseEntity<>(updatedEntidadDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntidad(@PathVariable Long id) {
        entidadService.deleteEntidad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
