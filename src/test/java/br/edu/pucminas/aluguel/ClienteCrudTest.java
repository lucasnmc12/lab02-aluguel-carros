package br.edu.pucminas.aluguel;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.edu.pucminas.aluguel.model.Cliente;
import br.edu.pucminas.aluguel.repository.ClienteRepository;
import br.edu.pucminas.aluguel.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteCrudTest {
    @Autowired private MockMvc mvc;
    @Autowired private ClienteRepository clientes;
    @Autowired private PedidoRepository pedidos;

    @BeforeEach
    void limparBanco() {
        pedidos.deleteAll();
        clientes.deleteAll();
    }

    @Test
    void cadastraConsultaAtualizaEExcluiOProprioCliente() throws Exception {
        cadastrarCliente("Lucas Nogueira", "123.456.789-00");
        Cliente cliente = clientes.findByCpf("12345678900").orElseThrow();
        mvc.perform(get("/clientes/{id}", cliente.getId()).with(user(cliente.getCpf())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lucas Nogueira")));

        mvc.perform(post("/clientes/{id}", cliente.getId()).with(user(cliente.getCpf())).with(csrf())
                        .param("nome", "Lucas Atualizado").param("cpf", cliente.getCpf())
                        .param("rg", "MG-1").param("endereco", "Avenida Brasil, 20")
                        .param("profissao", "Analista"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/clientes/{id}", cliente.getId()).with(user(cliente.getCpf())))
                .andExpect(content().string(containsString("Lucas Atualizado")));

        mvc.perform(post("/clientes/{id}/excluir", cliente.getId())
                        .with(user(cliente.getCpf())).with(csrf()))
                .andExpect(status().is3xxRedirection());
        org.junit.jupiter.api.Assertions.assertEquals(0, clientes.count());
    }

    @Test
    void rejeitaCadastroInvalidoECpfDuplicado() throws Exception {
        mvc.perform(post("/clientes").with(csrf())
                        .param("nome", "").param("cpf", "123").param("rg", "")
                        .param("endereco", "").param("profissao", "").param("senha", "curta"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("clienteForm",
                        "nome", "cpf", "rg", "endereco", "profissao", "senha"));
        cadastrarCliente("Primeiro Cliente", "11122233344");
        mvc.perform(post("/clientes").with(csrf())
                        .param("nome", "Segundo Cliente").param("cpf", "111.222.333-44")
                        .param("rg", "MG-2").param("endereco", "Rua Dois")
                        .param("profissao", "Professora").param("senha", "senhaSegura123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Já existe um cliente cadastrado")));
    }

    @Test
    void outroClienteNaoVeNemAlteraCadastro() throws Exception {
        cadastrarCliente("Primeiro Cliente", "11122233344");
        cadastrarCliente("Segundo Cliente", "99988877766");
        Long id = clientes.findByCpf("11122233344").orElseThrow().getId();
        mvc.perform(get("/clientes/{id}", id).with(user("99988877766")))
                .andExpect(status().isNotFound());
        mvc.perform(post("/clientes/{id}/excluir", id).with(user("99988877766")).with(csrf()))
                .andExpect(status().isNotFound());
        org.junit.jupiter.api.Assertions.assertEquals(2, clientes.count());
    }

    @Test
    void rotaPrivadaExigeLoginECadastroPublicoAbre() throws Exception {
        mvc.perform(get("/clientes/novo")).andExpect(status().isOk());
        mvc.perform(get("/clientes")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/entrar"));
    }

    private void cadastrarCliente(String nome, String cpf) throws Exception {
        mvc.perform(post("/clientes").with(csrf())
                        .param("nome", nome).param("cpf", cpf).param("rg", "MG-1")
                        .param("endereco", "Rua Um").param("profissao", "Engenheiro")
                        .param("senha", "senhaSegura123"))
                .andExpect(status().is3xxRedirection());
    }
}
