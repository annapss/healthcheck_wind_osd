package com.example.demo.databaseTables;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizacao")
public class Organizacao {
    @Id
    @SequenceGenerator(
        name = "organizacao_sequence",
        sequenceName = "organizacao_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "organizacao_sequence"
    )
    @Column(name = "organizacao_id")
    private Long organizacao_id;

    @OneToMany(mappedBy = "organizacao")
    private List<Embarcacao> embarcacoes;

    private String nome_organizacao;

    public Organizacao(){}

    public Organizacao(String nome_organizacao)
    {
        this.nome_organizacao = nome_organizacao;
    }
}
