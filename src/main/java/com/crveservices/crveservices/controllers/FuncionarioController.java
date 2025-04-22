package com.crveservices.crveservices.controllers;


import com.crveservices.crveservices.models.FuncionarioModel;
import com.crveservices.crveservices.dto.FuncionarioRecordDto;
import com.crveservices.crveservices.repositories.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    FuncionarioRepository funcionarioRepository;


    @PostMapping("/login")
    public ResponseEntity<Object> loginFuncionario(@RequestBody Map<String, String> credentials) {
        String nmFuncionario = credentials.get("nmFuncionario");
        String dsSenha = credentials.get("dsSenha");

        if (nmFuncionario == null || dsSenha == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Nome de usuário e senha são obrigatórios"));
        }

        Optional<FuncionarioModel> funcionario = funcionarioRepository.findByNmFuncionario(nmFuncionario);

        if (funcionario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não encontrado"));
        }

        if (!funcionario.get().getDsSenha().equals(dsSenha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Senha incorreta"));
        }

        String token = UUID.randomUUID().toString();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("funcionario", funcionario.get());

        return ResponseEntity.ok(response);
    }



    @PostMapping
    public ResponseEntity<FuncionarioModel> saveFuncionario(@RequestBody @Valid FuncionarioRecordDto funcionarioRecordDto) {
        var funcionarioModel = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioRecordDto, funcionarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionarioModel));
    }

    @GetMapping
        public ResponseEntity<List<FuncionarioModel>> getAllFuncionarios() {
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneFuncionario(@PathVariable(value="id")int id) {
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);
        if(funcionario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionario.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFuncionario(@PathVariable(value = "id") int id,
            @RequestBody @Valid FuncionarioRecordDto funcionarioRecordDto) {
        Optional<FuncionarioModel> funcionario0 = funcionarioRepository.findAllById(id);
        if(funcionario0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        var funcionarioModel = funcionario0.get();
        BeanUtils.copyProperties(funcionarioRecordDto, funcionarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionarioModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable(value = "id")int id) {
        Optional<FuncionarioModel> funcionario0 = funcionarioRepository.findById(id);
        if (funcionario0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        funcionarioRepository.delete(funcionario0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionário excluído com sucesso!");
    }
}
