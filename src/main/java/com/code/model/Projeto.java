package com.code.model;

import com.code.enums.Risco;
import com.code.enums.Status;
import lombok.Data;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Projeto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7645539289154744934L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    @Column(name = "data_previsao_fim")
    private LocalDate dataPrevisaoFim;
    @Column(name = "data_fim")
    private LocalDate dataFim;
    private String descricao;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    private Double orcamento;
    private Risco risco;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pessoa gerente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto", fetch = FetchType.LAZY)
    List<Membros> membros;
    
}
