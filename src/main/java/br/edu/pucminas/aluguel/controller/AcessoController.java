package br.edu.pucminas.aluguel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcessoController {
    @GetMapping("/entrar")
    public String entrar() {
        return "acesso/entrar";
    }
}
