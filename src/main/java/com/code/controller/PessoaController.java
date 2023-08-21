package com.code.controller;

import com.code.dto.PessoaDTO;
import com.code.service.PessoaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(final PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAllPessoas() {
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoa(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(pessoaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPessoa(@RequestBody @Valid final PessoaDTO pessoaDTO) {
        final Long createdId = pessoaService.create(pessoaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePessoa(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PessoaDTO pessoaDTO) {
        pessoaService.update(id, pessoaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePessoa(@PathVariable(name = "id") final Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
