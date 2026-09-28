package br.edu.pucminas.aluguel.controller;

import br.edu.pucminas.aluguel.dto.PedidoForm;
import br.edu.pucminas.aluguel.model.Modalidade;
import br.edu.pucminas.aluguel.service.PedidoNaoEncontradoException;
import br.edu.pucminas.aluguel.service.PedidoService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) { this.service = service; }

    @GetMapping
    public String listar(Model model, Principal principal) {
        model.addAttribute("pedidos", service.listarProprios(principal.getName()));
        return "pedidos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pedidoForm", new PedidoForm());
        prepararFormulario(model, "Novo pedido", "/pedidos");
        return "pedidos/formulario";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute PedidoForm pedidoForm, BindingResult binding,
                        Model model, Principal principal, RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            prepararFormulario(model, "Novo pedido", "/pedidos");
            return "pedidos/formulario";
        }
        try {
            var pedido = service.criar(principal.getName(), pedidoForm);
            redirect.addFlashAttribute("mensagem", "Pedido criado com sucesso.");
            return "redirect:/pedidos/" + pedido.getId();
        } catch (IllegalArgumentException e) {
            binding.rejectValue("automovelId", "automovel.invalido", e.getMessage());
            prepararFormulario(model, "Novo pedido", "/pedidos");
            return "pedidos/formulario";
        }
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model, Principal principal) {
        var pedido = service.buscarProprio(id, principal.getName());
        model.addAttribute("pedido", pedido);
        model.addAttribute("podeAlterar", service.podeAlterar(pedido));
        return "pedidos/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, Principal principal) {
        var pedido = service.buscarProprio(id, principal.getName());
        if (!service.podeAlterar(pedido)) {
            return "redirect:/pedidos/" + id;
        }
        model.addAttribute("pedidoForm", PedidoForm.de(pedido));
        prepararFormulario(model, "Editar pedido", "/pedidos/" + id);
        return "pedidos/formulario";
    }

    @PostMapping("/{id}")
    public String alterar(@PathVariable Long id, @Valid @ModelAttribute PedidoForm pedidoForm,
                          BindingResult binding, Model model, Principal principal,
                          RedirectAttributes redirect) {
        var pedido = service.buscarProprio(id, principal.getName());
        if (!service.podeAlterar(pedido)) {
            return "redirect:/pedidos/" + id;
        }
        if (binding.hasErrors()) {
            prepararFormulario(model, "Editar pedido", "/pedidos/" + id);
            return "pedidos/formulario";
        }
        try {
            service.alterar(id, principal.getName(), pedidoForm);
            redirect.addFlashAttribute("mensagem", "Pedido atualizado com sucesso.");
            return "redirect:/pedidos/" + id;
        } catch (IllegalArgumentException e) {
            binding.rejectValue("automovelId", "automovel.invalido", e.getMessage());
            prepararFormulario(model, "Editar pedido", "/pedidos/" + id);
            return "pedidos/formulario";
        }
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id, Principal principal, RedirectAttributes redirect) {
        service.cancelar(id, principal.getName());
        redirect.addFlashAttribute("mensagem", "Pedido cancelado.");
        return "redirect:/pedidos/" + id;
    }

    private void prepararFormulario(Model model, String titulo, String acao) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("acao", acao);
        model.addAttribute("automoveis", service.listarAutomoveis());
        model.addAttribute("modalidades", Modalidade.values());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public String naoEncontrado(PedidoNaoEncontradoException e, Model model) {
        model.addAttribute("mensagemErro", e.getMessage());
        return "erro/404";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public String conflito(IllegalStateException e, Model model) {
        model.addAttribute("mensagemErro", e.getMessage());
        return "erro/409";
    }
}
