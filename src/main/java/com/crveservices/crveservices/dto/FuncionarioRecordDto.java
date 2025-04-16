package com.crveservices.crveservices.dto;


import jakarta.validation.constraints.NotNull;

public record FuncionarioRecordDto(@NotNull String nmFuncionario, @NotNull String dsGmail, @NotNull String dsSenha) {

}
