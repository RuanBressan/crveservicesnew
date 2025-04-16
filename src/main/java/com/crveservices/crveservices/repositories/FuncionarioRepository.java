package com.crveservices.crveservices.repositories;

import com.crveservices.crveservices.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Integer> {
    Optional<FuncionarioModel> findById(int id);

    Optional<FuncionarioModel> findAllById(int id);




}
