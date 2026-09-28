package br.edu.pucminas.aluguel.service;

import br.edu.pucminas.aluguel.dto.ClienteForm;
import br.edu.pucminas.aluguel.dto.EmpregadorForm;
import br.edu.pucminas.aluguel.model.Cliente;
import br.edu.pucminas.aluguel.model.Empregador;
import br.edu.pucminas.aluguel.repository.ClienteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public Cliente buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public Cliente buscarProprio(Long id, String cpf) {
        Cliente cliente = buscar(id);
        if (!cliente.getCpf().equals(cpf)) {
            throw new ClienteNaoEncontradoException(id);
        }
        return cliente;
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow();
    }

    @Transactional
    public Cliente criar(ClienteForm form) {
        if (form.getSenha() == null || form.getSenha().length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres");
        }
        String cpf = somenteDigitos(form.getCpf());
        if (repository.existsByCpf(cpf)) {
            throw new CpfDuplicadoException();
        }
        Cliente cliente = new Cliente(form.getRg().trim(), cpf, form.getNome().trim(),
                form.getEndereco().trim(), form.getProfissao().trim(), passwordEncoder.encode(form.getSenha()));
        cliente.substituirEmpregadores(converterEmpregadores(form));
        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, String cpfAutenticado, ClienteForm form) {
        Cliente cliente = buscarProprio(id, cpfAutenticado);
        String cpf = somenteDigitos(form.getCpf());
        if (repository.existsByCpfAndIdNot(cpf, id)) {
            throw new CpfDuplicadoException();
        }
        cliente.atualizarDados(form.getRg().trim(), cpf, form.getNome().trim(),
                form.getEndereco().trim(), form.getProfissao().trim());
        cliente.substituirEmpregadores(converterEmpregadores(form));
        return cliente;
    }

    @Transactional
    public void excluir(Long id, String cpfAutenticado) {
        Cliente cliente = buscarProprio(id, cpfAutenticado);
        repository.delete(cliente);
    }

    private List<Empregador> converterEmpregadores(ClienteForm form) {
        List<Empregador> empregadores = new ArrayList<>();
        if (form.getEmpregadores() == null) {
            return empregadores;
        }
        for (EmpregadorForm item : form.getEmpregadores()) {
            boolean temNome = item.getNome() != null && !item.getNome().isBlank();
            boolean temRendimento = item.getRendimento() != null;
            if (temNome != temRendimento) {
                throw new IllegalArgumentException(
                        "Preencha nome e rendimento de cada empregador, ou deixe ambos vazios");
            }
            if (temNome) {
                empregadores.add(new Empregador(item.getNome().trim(), item.getRendimento()));
            }
        }
        return empregadores;
    }

    private String somenteDigitos(String valor) {
        return valor == null ? "" : valor.replaceAll("\\D", "");
    }
}
