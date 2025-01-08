package com.example.demo.databaseTables;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @SequenceGenerator(
        name = "status_sequence",
        sequenceName = "status_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "status_sequence"
    )
    @Column(name = "status_id")
    private Long status_id;

    @ManyToOne //FK de embarcaco
    @JoinColumn(name = "embarcacao_id")
    private Embarcacao embarcacao;

    private String sentenca_mwv;
    private String sentenca_rmc;
    private String sentenca_hdt;
    private String sentenca_rov;
    private String radar;
    private String camera;
    private String servico_video;
    private String maquina_embarcacao;
    private Date data;

    public Status(){}

    public Status(Embarcacao embarcacao, String sentenca_mwv, String sentenca_rmc, String sentenca_hdt, String sentenca_rov, String radar, String camera, String servico_video, String maquina_embarcacao, Date data)
    {
        this.embarcacao = embarcacao;
        this.sentenca_mwv = sentenca_mwv;
        this.sentenca_rmc = sentenca_rmc;
        this.sentenca_hdt = sentenca_hdt;
        this.sentenca_rov = sentenca_rov;
        this.radar = radar;
        this.camera = camera;
        this.servico_video = servico_video;
        this.maquina_embarcacao = maquina_embarcacao;
        this.data = data;
    }
}
