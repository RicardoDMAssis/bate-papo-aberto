package com.ricardo.sala_aberta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MensagemResponseDTO {


    private String texto;
    private String usuarioApelido;
    private LocalDateTime dataHora;

}
