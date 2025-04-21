package com.crveservices.crveservices.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record ServicoRecordDto(
        @NotBlank String descricao,
        @NotNull double valorMaoDeObra,
        @NotNull long funcionarioId,
        @NotNull List<Long> produtosIds
) {}
