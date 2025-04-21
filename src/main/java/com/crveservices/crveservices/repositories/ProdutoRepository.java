package com.crveservices.crveservices.repositories;

import com.crveservices.crveservices.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {}
