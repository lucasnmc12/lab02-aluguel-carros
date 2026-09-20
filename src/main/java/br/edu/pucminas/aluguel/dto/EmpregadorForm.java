package br.edu.pucminas.aluguel.dto;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class EmpregadorForm {
    private String nome = "";

    @DecimalMin(value = "0.0", message = "O rendimento não pode ser negativo")
    private BigDecimal rendimento;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getRendimento() { return rendimento; }
    public void setRendimento(BigDecimal rendimento) { this.rendimento = rendimento; }
}

