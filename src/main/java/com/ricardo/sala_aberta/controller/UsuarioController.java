package com.ricardo.sala_aberta.controller;

import com.ricardo.sala_aberta.dto.UsuarioCadastroDTO;
import com.ricardo.sala_aberta.dto.UsuarioResponseDTO;
import com.ricardo.sala_aberta.entity.Usuario;
import com.ricardo.sala_aberta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getApelido()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioCadastroDTO usuarioDTO){
        if (usuarioRepository.findByApelido(usuarioDTO.getApelido()).isPresent()){
            return ResponseEntity.status(409).body("Apelido já registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setApelido(usuarioDTO.getApelido());
        usuario.setSenha(usuarioDTO.getSenha());

        usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body("Usuario cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> tentarLogin(@RequestBody UsuarioCadastroDTO usuarioDTO){
        Optional<Usuario> usuarioExistente = usuarioRepository.findByApelido(usuarioDTO.getApelido());
        if (usuarioExistente.isPresent() && usuarioExistente.get().getSenha().equals(usuarioDTO.getSenha())){
            return  ResponseEntity.ok("Usuario Logado");
        }
        return ResponseEntity.status(401).body("Apelido ou Senha Inválidos");
    }
}


