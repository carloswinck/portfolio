package com.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MembrosKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "idPessoa")
    private Long idPessoa;

    @Column(name = "idProjeto")
    private Long idProjeto;

}
