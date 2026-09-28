package br.edu.pucminas.aluguel;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.edu.pucminas.aluguel.model.StatusPedido;
import br.edu.pucminas.aluguel.repository.AutomovelRepository;
import br.edu.pucminas.aluguel.repository.ClienteRepository;
import br.edu.pucminas.aluguel.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoFluxoTest {
    @Autowired private MockMvc mvc;
    @Autowired private ClienteRepository clientes;
    @Autowired private PedidoRepository pedidos;
    @Autowired private AutomovelRepository automoveis;

    @BeforeEach
    void limparBanco() {
        pedidos.deleteAll();
        clientes.deleteAll();
    }

    @Test
    void clienteLogadoCriaVisualizaEditaECancelaPedido() throws Exception {
        cadastrar("11122233344", "Cliente Um");
        var login = mvc.perform(formLogin("/entrar").user("cpf", "11122233344")
                        .password("senhaSegura123"))
                .andExpect(status().is3xxRedirection()).andReturn();
        MockHttpSession sessao = (MockHttpSession) login.getRequest().getSession();
        Long automovelId = automoveis.findAll().getFirst().getId();

        mvc.perform(post("/pedidos").session(sessao).with(csrf())
                        .param("automovelId", automovelId.toString()).param("modalidade", "LOCACAO"))
                .andExpect(status().is3xxRedirection());
        var pedido = pedidos.findAll().getFirst();
        assertEquals(StatusPedido.AGUARDANDO_ANALISE, pedido.getStatus());
        mvc.perform(get("/pedidos").session(sessao))
                .andExpect(content().string(containsString("Aguardando análise")));
        mvc.perform(get("/pedidos/{id}", pedido.getId()).session(sessao))
                .andExpect(content().string(containsString("Aguardando análise")));

        mvc.perform(post("/pedidos/{id}", pedido.getId()).session(sessao).with(csrf())
                        .param("automovelId", automovelId.toString()).param("modalidade", "ASSINATURA"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/pedidos/{id}", pedido.getId()).session(sessao))
                .andExpect(content().string(containsString("Assinatura")));
        mvc.perform(post("/pedidos/{id}/cancelar", pedido.getId()).session(sessao).with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertEquals(StatusPedido.CANCELADO, pedidos.findById(pedido.getId()).orElseThrow().getStatus());
        mvc.perform(get("/pedidos/{id}", pedido.getId()).session(sessao))
                .andExpect(content().string(containsString("Cancelado")));
        mvc.perform(post("/pedidos/{id}/cancelar", pedido.getId()).session(sessao).with(csrf()))
                .andExpect(status().isConflict());
    }

    @Test
    void impedeAcessoCruzadoEPedidoSemLoginOuCsrf() throws Exception {
        cadastrar("11122233344", "Cliente Um");
        cadastrar("99988877766", "Cliente Dois");
        Long automovelId = automoveis.findAll().getFirst().getId();
        mvc.perform(post("/pedidos").with(user("11122233344")).with(csrf())
                        .param("automovelId", automovelId.toString()).param("modalidade", "LEASING"))
                .andExpect(status().is3xxRedirection());
        Long id = pedidos.findAll().getFirst().getId();
        mvc.perform(get("/pedidos/{id}", id).with(user("99988877766")))
                .andExpect(status().isNotFound());
        mvc.perform(post("/pedidos/{id}/cancelar", id).with(user("99988877766")).with(csrf()))
                .andExpect(status().isNotFound());
        mvc.perform(get("/pedidos")).andExpect(status().is3xxRedirection());
        mvc.perform(post("/pedidos").with(user("11122233344"))
                        .param("automovelId", automovelId.toString()).param("modalidade", "LOCACAO"))
                .andExpect(status().isForbidden());
        assertEquals(1, pedidos.count());
    }

    @Test
    void rejeitaAutomovelInexistenteEFormularioIncompleto() throws Exception {
        cadastrar("11122233344", "Cliente Um");
        mvc.perform(post("/pedidos").with(user("11122233344")).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Escolha um automóvel")));
        mvc.perform(post("/pedidos").with(user("11122233344")).with(csrf())
                        .param("automovelId", "999999").param("modalidade", "LOCACAO"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Escolha um automóvel disponível")));
        assertEquals(0, pedidos.count());
    }

    @Test
    void senhaIncorretaNaoAutentica() throws Exception {
        cadastrar("11122233344", "Cliente Um");
        mvc.perform(formLogin("/entrar").user("cpf", "11122233344")
                        .password("senhaIncorreta"))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }

    @Test
    void formulariosRenderizadosContemTokenCsrf() throws Exception {
        cadastrar("11122233344", "Cliente Um");
        mvc.perform(get("/entrar"))
                .andExpect(content().string(containsString("name=\"_csrf\"")));
        mvc.perform(get("/pedidos/novo").with(user("11122233344")))
                .andExpect(content().string(containsString("name=\"_csrf\"")));
    }

    private void cadastrar(String cpf, String nome) throws Exception {
        mvc.perform(post("/clientes").with(csrf())
                        .param("nome", nome).param("cpf", cpf).param("rg", "MG-1")
                        .param("endereco", "Rua Um").param("profissao", "Engenheiro")
                        .param("senha", "senhaSegura123"))
                .andExpect(status().is3xxRedirection());
    }
}
