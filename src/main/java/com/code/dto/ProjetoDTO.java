package com.code.dto;

import com.code.enums.Risco;
import com.code.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjetoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3202555422939055393L;

    private Long id;
    @NotNull
    @Size(max = 200)
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataPrevisaoFim;
    private LocalDate dataFim;
    @Size(max = 5000)
    private String descricao;
    private Status status;
    private Double orcamento;
    private Risco risco;
    @NotNull
    private Long idgerente;
    private List<Long> pessoasMembros;

}
