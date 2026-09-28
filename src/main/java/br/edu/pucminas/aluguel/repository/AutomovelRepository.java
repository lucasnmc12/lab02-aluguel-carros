package br.edu.pucminas.aluguel.repository;

import br.edu.pucminas.aluguel.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> { }
