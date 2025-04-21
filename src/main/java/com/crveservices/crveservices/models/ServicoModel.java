package com.crveservices.crveservices.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServicoModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descricao;

    private double valorMaoDeObra;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private FuncionarioModel funcionario;

    @ManyToMany
    @JoinTable(
            name = "servico_produto",
            joinColumns = @JoinColumn(name = "servico_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<ProdutoModel> produtos;
}
