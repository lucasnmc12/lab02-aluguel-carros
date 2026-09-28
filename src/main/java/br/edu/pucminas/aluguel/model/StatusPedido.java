package br.edu.pucminas.aluguel.model;

public enum StatusPedido {
    AGUARDANDO_ANALISE("Aguardando análise"),
    AGUARDANDO_CLIENTE("Aguardando decisão do cliente"),
    REJEITADO("Rejeitado"), ACEITO("Aceito"),
    RECUSADO_PELO_CLIENTE("Recusado pelo cliente"), CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
