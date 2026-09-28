package br.edu.pucminas.aluguel.service;

import br.edu.pucminas.aluguel.dto.PedidoForm;
import br.edu.pucminas.aluguel.model.Automovel;
import br.edu.pucminas.aluguel.model.PedidoAluguel;
import br.edu.pucminas.aluguel.model.StatusPedido;
import br.edu.pucminas.aluguel.repository.AutomovelRepository;
import br.edu.pucminas.aluguel.repository.ClienteRepository;
import br.edu.pucminas.aluguel.repository.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    private final PedidoRepository pedidos;
    private final ClienteRepository clientes;
    private final AutomovelRepository automoveis;

    public PedidoService(PedidoRepository pedidos, ClienteRepository clientes,
                         AutomovelRepository automoveis) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.automoveis = automoveis;
    }

    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarProprios(String cpf) {
        return pedidos.findByClienteCpfOrderByCriadoEmDesc(cpf);
    }

    @Transactional(readOnly = true)
    public PedidoAluguel buscarProprio(Long id, String cpf) {
        return pedidos.findByIdAndClienteCpf(id, cpf)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<Automovel> listarAutomoveis() {
        return automoveis.findAll();
    }

    @Transactional
    public PedidoAluguel criar(String cpf, PedidoForm form) {
        var cliente = clientes.findByCpf(cpf).orElseThrow();
        Automovel automovel = automovel(form.getAutomovelId());
        return pedidos.save(new PedidoAluguel(cliente, automovel, form.getModalidade()));
    }

    @Transactional
    public void alterar(Long id, String cpf, PedidoForm form) {
        PedidoAluguel pedido = buscarProprio(id, cpf);
        pedido.alterar(automovel(form.getAutomovelId()), form.getModalidade());
    }

    @Transactional
    public void cancelar(Long id, String cpf) {
        buscarProprio(id, cpf).cancelar();
    }

    @Transactional(readOnly = true)
    public boolean podeAlterar(PedidoAluguel pedido) {
        return pedido.getStatus() == StatusPedido.AGUARDANDO_ANALISE;
    }

    private Automovel automovel(Long id) {
        return automoveis.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Escolha um automóvel disponível"));
    }
}
