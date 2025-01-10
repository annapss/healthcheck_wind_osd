package com.example.demo.databaseTables;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "relatorio")
public class Relatorio {

    @ManyToOne //FK de embarcacao
    @JoinColumn(name = "embarcacao_id")
    private Embarcacao embarcacao;

    Date data;

    String descricao;

    String tipo;
    /*public enum Tipo {
        NAVTI,
        INOVACAO
    }

    @Enumerated(EnumType.STRING)
    Tipo tipo;*/

    public Relatorio(Date data, String descricao, Embarcacao embarcacao, String tipo) {
        this.data = data;
        this.descricao = descricao;
        this.embarcacao = embarcacao;
        this.tipo = tipo;
    }
    @Id
    @SequenceGenerator(
        name = "relatorio_sequence",
        sequenceName = "relatorio_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "relatorio_sequence"
    )
    private Long relatorio_id;

    @Override
    public String toString() {
        return this.descricao;
    }

    public Embarcacao getEmbarcacao() {
        return embarcacao;
    }

    public void setEmbarcacao(Embarcacao embarcacao) {
        this.embarcacao = embarcacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getRelatorio_id() {
        return relatorio_id;
    }

    public void setRelatorio_id(Long relatorio_id) {
        this.relatorio_id = relatorio_id;
    }
}
