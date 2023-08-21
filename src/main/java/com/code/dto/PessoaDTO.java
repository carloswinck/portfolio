package com.code.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class PessoaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3430530473659210668L;

    private Long id;
    @NotNull
    @Size(max = 100)
    private String nome;
    private LocalDate datanascimento;
    @Size(max = 11)
    private String cpf;
    private Boolean funcionario;

}
