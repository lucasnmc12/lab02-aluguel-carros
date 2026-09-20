package br.edu.pucminas.aluguel.service;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(Long id) {
        super("Cliente " + id + " não encontrado");
    }
}

