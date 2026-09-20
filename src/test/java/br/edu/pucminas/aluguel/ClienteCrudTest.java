package br.edu.pucminas.aluguel;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.edu.pucminas.aluguel.model.Cliente;
import br.edu.pucminas.aluguel.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteCrudTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void executaFluxoCompletoDoCrud() throws Exception {
        mvc.perform(post("/clientes")
                        .param("nome", "Lucas Nogueira")
                        .param("cpf", "123.456.789-00")
                        .param("rg", "MG-12.345.678")
                        .param("endereco", "Rua das Flores, 10")
                        .param("profissao", "Desenvolvedor")
                        .param("empregadores[0].nome", "Empresa Exemplo")
                        .param("empregadores[0].rendimento", "7500.00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/clientes/*"));

        Cliente cliente = repository.findByCpf("12345678900").orElseThrow();
        mvc.perform(get("/clientes/{id}", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lucas Nogueira")))
                .andExpect(content().string(containsString("Empresa Exemplo")));

        mvc.perform(post("/clientes/{id}", cliente.getId())
                        .param("nome", "Lucas Nogueira Atualizado")
                        .param("cpf", "12345678900")
                        .param("rg", "MG-12.345.678")
                        .param("endereco", "Avenida Brasil, 20")
                        .param("profissao", "Analista"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/clientes/{id}", cliente.getId()))
                .andExpect(content().string(containsString("Lucas Nogueira Atualizado")))
                .andExpect(content().string(containsString("Avenida Brasil, 20")));

        mvc.perform(post("/clientes/{id}/excluir", cliente.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes"));

        mvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nenhum cliente cadastrado")));
    }

    @Test
    void rejeitaCamposObrigatoriosInvalidos() throws Exception {
        mvc.perform(post("/clientes")
                        .param("nome", "")
                        .param("cpf", "123")
                        .param("rg", "")
                        .param("endereco", "")
                        .param("profissao", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/formulario"))
                .andExpect(model().attributeHasFieldErrors(
                        "clienteForm", "nome", "cpf", "rg", "endereco", "profissao"));
    }

    @Test
    void rejeitaCpfDuplicado() throws Exception {
        cadastrarCliente("Primeiro Cliente", "11122233344");

        mvc.perform(post("/clientes")
                        .param("nome", "Segundo Cliente")
                        .param("cpf", "111.222.333-44")
                        .param("rg", "MG-2")
                        .param("endereco", "Rua Dois")
                        .param("profissao", "Professora"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/formulario"))
                .andExpect(content().string(containsString("Já existe um cliente cadastrado")));
    }

    @Test
    void retorna404ParaClienteInexistente() throws Exception {
        mvc.perform(get("/clientes/99999"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("erro/404"));
    }

    private void cadastrarCliente(String nome, String cpf) throws Exception {
        mvc.perform(post("/clientes")
                        .param("nome", nome)
                        .param("cpf", cpf)
                        .param("rg", "MG-1")
                        .param("endereco", "Rua Um")
                        .param("profissao", "Engenheiro"))
                .andExpect(status().is3xxRedirection());
    }
}
