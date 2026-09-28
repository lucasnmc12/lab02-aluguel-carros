package br.edu.pucminas.aluguel.dto;

import br.edu.pucminas.aluguel.model.Modalidade;
import br.edu.pucminas.aluguel.model.PedidoAluguel;
import jakarta.validation.constraints.NotNull;

public class PedidoForm {
    @NotNull(message = "Escolha um automóvel")
    private Long automovelId;

    @NotNull(message = "Escolha uma modalidade")
    private Modalidade modalidade;

    public static PedidoForm de(PedidoAluguel pedido) {
        PedidoForm form = new PedidoForm();
        form.automovelId = pedido.getAutomovel().getId();
        form.modalidade = pedido.getModalidade();
        return form;
    }

    public Long getAutomovelId() { return automovelId; }
    public void setAutomovelId(Long automovelId) { this.automovelId = automovelId; }
    public Modalidade getModalidade() { return modalidade; }
    public void setModalidade(Modalidade modalidade) { this.modalidade = modalidade; }
}
