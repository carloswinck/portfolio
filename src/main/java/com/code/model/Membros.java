package com.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Membros implements Serializable {

    @Serial
    private static final long serialVersionUID = 4094373152142808987L;

    @EmbeddedId
    private MembrosKey id;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @ManyToOne
    @MapsId("idProjeto")
    @JoinColumn(name = "idProjeto")
    private Projeto projeto;

}
