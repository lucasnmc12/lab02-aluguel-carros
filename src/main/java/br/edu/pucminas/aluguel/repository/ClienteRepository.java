package br.edu.pucminas.aluguel.repository;

import br.edu.pucminas.aluguel.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);
    Optional<Cliente> findByCpf(String cpf);
}

