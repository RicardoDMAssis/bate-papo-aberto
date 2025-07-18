package com.ricardo.sala_aberta.controller;

import com.ricardo.sala_aberta.dto.MensagemDTO;
import com.ricardo.sala_aberta.dto.MensagemResponseDTO;
import com.ricardo.sala_aberta.entity.Mensagem;
import com.ricardo.sala_aberta.entity.Usuario;
import com.ricardo.sala_aberta.repository.MensagemRepository;
import com.ricardo.sala_aberta.repository.UsuarioRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<MensagemResponseDTO>> listarMensagens(){
        List<MensagemResponseDTO> mensagens = mensagemRepository.findAllByOrderByDataHoraAsc()
                .stream()
                .map(m -> new MensagemResponseDTO(
                        m.getTexto(),
                        m.getUsuario().getApelido(),
                        m.getDataHora()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(mensagens);
    }

    @PostMapping
    public ResponseEntity<?> enviarMensagem(@RequestBody MensagemDTO mensagemDTO){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(mensagemDTO.getUsuarioId());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario não encontrado!!");
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setTexto(mensagemDTO.getTexto());
        mensagem.setDataHora(LocalDateTime.now());
        mensagem.setUsuario(usuarioOpt.get());
        mensagemRepository.save(mensagem);

        return ResponseEntity.status(201).body("Mensagem Enviada");

    }

}
