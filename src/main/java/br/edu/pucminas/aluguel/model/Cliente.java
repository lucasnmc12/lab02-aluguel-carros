package br.edu.pucminas.aluguel.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    public static final int MAXIMO_EMPREGADORES = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String rg;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 200)
    private String endereco;

    @Column(nullable = false, length = 100)
    private String profissao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordem")
    private List<Empregador> empregadores = new ArrayList<>();

    protected Cliente() {
    }

    public Cliente(String rg, String cpf, String nome, String endereco, String profissao) {
        atualizarDados(rg, cpf, nome, endereco, profissao);
    }

    public void atualizarDados(String rg, String cpf, String nome, String endereco, String profissao) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    public void substituirEmpregadores(List<Empregador> novosEmpregadores) {
        if (novosEmpregadores.size() > MAXIMO_EMPREGADORES) {
            throw new IllegalArgumentException("Um cliente pode ter no máximo três empregadores");
        }
        empregadores.clear();
        novosEmpregadores.forEach(this::adicionarEmpregador);
    }

    public void adicionarEmpregador(Empregador empregador) {
        if (empregadores.size() >= MAXIMO_EMPREGADORES) {
            throw new IllegalStateException("Um cliente pode ter no máximo três empregadores");
        }
        empregador.vincularA(this);
        empregadores.add(empregador);
    }

    public Long getId() { return id; }
    public String getRg() { return rg; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getProfissao() { return profissao; }
    public List<Empregador> getEmpregadores() { return List.copyOf(empregadores); }
}

