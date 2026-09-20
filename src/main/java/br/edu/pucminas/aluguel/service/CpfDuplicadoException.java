package br.edu.pucminas.aluguel.service;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException() {
        super("Já existe um cliente cadastrado com este CPF");
    }
}

