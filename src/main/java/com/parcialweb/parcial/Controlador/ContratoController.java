package com.parcialweb.parcial.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcialweb.parcial.DTO.ContratoCreationDTO;
import com.parcialweb.parcial.DTO.ContratoDTO;
import com.parcialweb.parcial.Servicio.ContratoService;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    // 1. Insertar un nuevo registro
    @PostMapping
    public ResponseEntity<ContratoDTO> createContrato(@RequestBody ContratoCreationDTO contratoCreationDTO) {
        ContratoDTO savedContratoDTO = contratoService.saveContrato(contratoCreationDTO);
        return new ResponseEntity<>(savedContratoDTO, HttpStatus.CREATED);
    }

    // 2. Consultar todos los registros
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> getAllContratos() {
        List<ContratoDTO> contratos = contratoService.getAllContratos();
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }

    // 3. Consultar un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> getContratoById(@PathVariable Long id) {
        return contratoService.getContratoById(id)
                .map(contratoDTO -> new ResponseEntity<>(contratoDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar registro
    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> updateContrato(@PathVariable Long id, @RequestBody ContratoCreationDTO contratoDetailsDTO) {
        try {
            ContratoDTO updatedContratoDTO = contratoService.updateContrato(id, contratoDetailsDTO);
            return new ResponseEntity<>(updatedContratoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        contratoService.deleteContrato(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
