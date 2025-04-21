package com.crveservices.crveservices.controllers;

import com.crveservices.crveservices.dto.ServicoRecordDto;
import com.crveservices.crveservices.models.ProdutoModel;
import com.crveservices.crveservices.models.ServicoModel;
import com.crveservices.crveservices.repositories.FuncionarioRepository;
import com.crveservices.crveservices.repositories.ProdutoRepository;
import com.crveservices.crveservices.repositories.ServicoRepository;
import jakarta.validation.Valid;
import com.crveservices.crveservices.models.FuncionarioModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/servico")
public class ServicoController {


    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServico(@PathVariable Long id) {
        Optional<ServicoModel> servicoOpt = servicoRepository.findById(id);
        if (servicoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        return ResponseEntity.ok(servicoOpt.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateServico(@PathVariable Long id, @RequestBody @Valid ServicoRecordDto dto) {
        Optional<ServicoModel> servicoOpt = servicoRepository.findById(id);
        if (servicoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }

        Optional<FuncionarioModel> funcionarioOpt = funcionarioRepository.findById(dto.funcionarioId());
        if (funcionarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }

        List<ProdutoModel> produtos = produtoRepository.findAllById(dto.produtosIds());
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum produto válido informado");
        }

        ServicoModel servico = servicoOpt.get();
        servico.setDescricao(dto.descricao());
        servico.setValorMaoDeObra(dto.valorMaoDeObra());
        servico.setFuncionario(funcionarioOpt.get());
        servico.setProdutos(produtos);

        return ResponseEntity.status(HttpStatus.OK).body(servicoRepository.save(servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteServico(@PathVariable Long id) {
        Optional<ServicoModel> servicoOpt = servicoRepository.findById(id);
        if (servicoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
        servicoRepository.delete(servicoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Serviço excluído com sucesso!");
    }



}
