package com.example.demo.databaseTables;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "embarcacao")
public class Embarcacao {
    @Id
    @SequenceGenerator(
        name = "embarcacao_sequence",
        sequenceName = "embarcacao_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "embarcacao_sequence"
    )
    @Column(name = "embarcacao_id")
    private Long embarcacao_id;
    @ManyToOne //FK de organizacao
    @JoinColumn(name = "organizacao_id")
    private Organizacao organizacao_id;
    @OneToMany(mappedBy = "embarcacao") //relacao de 1:N com a tabela status
    private Set<Status> status_id = new HashSet<>();
    private String mmsi;
    private String nome;
    private String data_inicio_contrato;
    private String data_fim_contrato;
    public Embarcacao(Organizacao organizacao_id, String mmsi, String nome, String data_inicio_contrato,
            String data_fim_contrato) {
        this.organizacao_id = organizacao_id;
        this.mmsi = mmsi;
        this.nome = nome;
        this.data_inicio_contrato = data_inicio_contrato;
        this.data_fim_contrato = data_fim_contrato;
    }
}
