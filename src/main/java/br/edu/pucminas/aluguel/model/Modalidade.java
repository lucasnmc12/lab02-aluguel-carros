package br.edu.pucminas.aluguel.model;

public enum Modalidade {
    LOCACAO("Locação"), ASSINATURA("Assinatura"), LEASING("Leasing");

    private final String descricao;

    Modalidade(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
