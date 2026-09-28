package br.edu.pucminas.aluguel.repository;

import br.edu.pucminas.aluguel.model.PedidoAluguel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoAluguel, Long> {
    List<PedidoAluguel> findByClienteCpfOrderByCriadoEmDesc(String cpf);
    Optional<PedidoAluguel> findByIdAndClienteCpf(Long id, String cpf);
}
