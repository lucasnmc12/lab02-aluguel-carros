package br.edu.pucminas.aluguel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "empregadores")
public class Empregador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rendimento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    protected Empregador() {
    }

    public Empregador(String nome, BigDecimal rendimento) {
        this.nome = nome;
        this.rendimento = rendimento;
    }

    void vincularA(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getRendimento() { return rendimento; }
}

