package com.code.controller;

import com.code.dto.ProjetoDTO;
import com.code.enums.Status;
import com.code.service.ProjetoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/projetos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(final ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> getAllProjetos() {
        return ResponseEntity.ok(projetoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> getProjeto(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(projetoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProjeto(@RequestBody @Valid final ProjetoDTO projetoDTO) {
        final Long createdId = projetoService.create(projetoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProjeto(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ProjetoDTO projetoDTO) {
        projetoService.update(id, projetoDTO);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Long> updateStatusProjeto(@PathVariable(name = "id") final Long id,
                                                    @RequestParam(name = "status") final Status status) {
        projetoService.updateStatus(id, status);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProjeto(@PathVariable(name = "id") final Long id) {
        projetoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
