package br.edu.pucminas.aluguel.controller;

import br.edu.pucminas.aluguel.dto.ClienteForm;
import br.edu.pucminas.aluguel.model.Cliente;
import br.edu.pucminas.aluguel.service.ClienteNaoEncontradoException;
import br.edu.pucminas.aluguel.service.ClienteService;
import br.edu.pucminas.aluguel.service.CpfDuplicadoException;
import jakarta.validation.Valid;
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
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("clienteForm", new ClienteForm());
        model.addAttribute("titulo", "Novo cliente");
        model.addAttribute("acao", "/clientes");
        return "clientes/formulario";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute ClienteForm clienteForm, BindingResult binding,
                        Model model, RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            prepararFormulario(model, "Novo cliente", "/clientes");
            return "clientes/formulario";
        }
        try {
            Cliente cliente = service.criar(clienteForm);
            redirect.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso.");
            return "redirect:/clientes/" + cliente.getId();
        } catch (CpfDuplicadoException | IllegalArgumentException e) {
            binding.reject("cliente.invalido", e.getMessage());
            prepararFormulario(model, "Novo cliente", "/clientes");
            return "clientes/formulario";
        }
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscar(id));
        return "clientes/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = service.buscar(id);
        model.addAttribute("clienteForm", ClienteForm.de(cliente));
        prepararFormulario(model, "Editar cliente", "/clientes/" + id);
        return "clientes/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute ClienteForm clienteForm,
                            BindingResult binding, Model model, RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            prepararFormulario(model, "Editar cliente", "/clientes/" + id);
            return "clientes/formulario";
        }
        try {
            service.atualizar(id, clienteForm);
            redirect.addFlashAttribute("mensagem", "Cliente atualizado com sucesso.");
            return "redirect:/clientes/" + id;
        } catch (CpfDuplicadoException | IllegalArgumentException e) {
            binding.reject("cliente.invalido", e.getMessage());
            prepararFormulario(model, "Editar cliente", "/clientes/" + id);
            return "clientes/formulario";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("mensagem", "Cliente excluído com sucesso.");
        return "redirect:/clientes";
    }

    private void prepararFormulario(Model model, String titulo, String acao) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("acao", acao);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public String naoEncontrado(ClienteNaoEncontradoException e, Model model) {
        model.addAttribute("mensagemErro", e.getMessage());
        return "erro/404";
    }
}

