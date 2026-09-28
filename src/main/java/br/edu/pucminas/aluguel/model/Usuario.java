package br.edu.pucminas.aluguel.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String senhaHash;

    protected Usuario() { }

    protected Usuario(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Long getId() { return id; }
    public String getSenhaHash() { return senhaHash; }
}
