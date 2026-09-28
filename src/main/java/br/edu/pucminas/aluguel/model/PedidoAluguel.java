package br.edu.pucminas.aluguel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class PedidoAluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Modalidade modalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected PedidoAluguel() { }

    public PedidoAluguel(Cliente cliente, Automovel automovel, Modalidade modalidade) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.modalidade = modalidade;
        this.status = StatusPedido.AGUARDANDO_ANALISE;
        this.criadoEm = LocalDateTime.now();
    }

    public void alterar(Automovel automovel, Modalidade modalidade) {
        exigirAguardandoAnalise();
        this.automovel = automovel;
        this.modalidade = modalidade;
    }

    public void cancelar() {
        exigirAguardandoAnalise();
        this.status = StatusPedido.CANCELADO;
    }

    private void exigirAguardandoAnalise() {
        if (status != StatusPedido.AGUARDANDO_ANALISE) {
            throw new IllegalStateException("O pedido não pode mais ser alterado ou cancelado");
        }
    }

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Automovel getAutomovel() { return automovel; }
    public Modalidade getModalidade() { return modalidade; }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
