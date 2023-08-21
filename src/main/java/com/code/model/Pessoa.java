package com.code.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Pessoa implements Serializable {

    @Serial
    private static final long serialVersionUID = -3448964172659859135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate datanascimento;
    private String cpf;
    private Boolean funcionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    List<Membros> membros;

}
