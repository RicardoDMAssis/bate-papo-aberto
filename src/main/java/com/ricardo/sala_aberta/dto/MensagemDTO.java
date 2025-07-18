package com.ricardo.sala_aberta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MensagemDTO {
    private Long usuarioId;
    private String texto;
}
