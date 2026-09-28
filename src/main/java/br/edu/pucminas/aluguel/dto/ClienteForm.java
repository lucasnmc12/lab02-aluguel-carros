package br.edu.pucminas.aluguel.dto;

import br.edu.pucminas.aluguel.model.Cliente;
import br.edu.pucminas.aluguel.model.Empregador;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ClienteForm {
    @NotBlank(message = "Informe o RG")
    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres")
    private String rg = "";

    @NotBlank(message = "Informe o CPF")
    @Pattern(regexp = "(?:\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})",
            message = "Informe 11 dígitos ou use o formato 000.000.000-00")
    private String cpf = "";

    @NotBlank(message = "Informe o nome")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    private String nome = "";

    @NotBlank(message = "Informe o endereço")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String endereco = "";

    @NotBlank(message = "Informe a profissão")
    @Size(max = 100, message = "A profissão deve ter no máximo 100 caracteres")
    private String profissao = "";

    private String senha = "";

    @Valid
    @Size(max = Cliente.MAXIMO_EMPREGADORES, message = "Informe no máximo três empregadores")
    private List<EmpregadorForm> empregadores = tresEmpregadoresVazios();

    public static ClienteForm de(Cliente cliente) {
        ClienteForm form = new ClienteForm();
        form.rg = cliente.getRg();
        form.cpf = cliente.getCpf();
        form.nome = cliente.getNome();
        form.endereco = cliente.getEndereco();
        form.profissao = cliente.getProfissao();
        form.empregadores = new ArrayList<>();
        for (Empregador empregador : cliente.getEmpregadores()) {
            EmpregadorForm item = new EmpregadorForm();
            item.setNome(empregador.getNome());
            item.setRendimento(empregador.getRendimento());
            form.empregadores.add(item);
        }
        while (form.empregadores.size() < Cliente.MAXIMO_EMPREGADORES) {
            form.empregadores.add(new EmpregadorForm());
        }
        return form;
    }

    private static List<EmpregadorForm> tresEmpregadoresVazios() {
        List<EmpregadorForm> itens = new ArrayList<>();
        for (int i = 0; i < Cliente.MAXIMO_EMPREGADORES; i++) {
            itens.add(new EmpregadorForm());
        }
        return itens;
    }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public List<EmpregadorForm> getEmpregadores() { return empregadores; }
    public void setEmpregadores(List<EmpregadorForm> empregadores) { this.empregadores = empregadores; }
}
