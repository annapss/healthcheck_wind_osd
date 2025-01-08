package com.example.demo.databaseTables;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "relatorio")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long relatorio_id;

    @OneToOne
    @JoinColumn(name = "embarcacao_id")
    Embarcacao embarcacao;

    @Temporal(TemporalType.TIMESTAMP)
    Date data;

    String descricao;

    enum Tipo {
        NAVTI,
        INOVACAO
    }

    @Enumerated(EnumType.STRING)
    Tipo tipo;

}
