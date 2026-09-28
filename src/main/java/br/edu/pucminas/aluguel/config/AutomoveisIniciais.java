package br.edu.pucminas.aluguel.config;

import br.edu.pucminas.aluguel.model.Automovel;
import br.edu.pucminas.aluguel.repository.AutomovelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutomoveisIniciais {
    @Bean
    CommandLineRunner carregarAutomoveis(AutomovelRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Automovel("ABC1D23", 2024, "Fiat", "Argo"));
                repository.save(new Automovel("DEF4G56", 2025, "Volkswagen", "Polo"));
                repository.save(new Automovel("GHI7J89", 2023, "Chevrolet", "Onix"));
            }
        };
    }
}
