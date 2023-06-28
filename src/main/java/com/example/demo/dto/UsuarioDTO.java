package com.example.demo.dto;

import com.example.demo.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String login;
    private String senha;

    public UsuarioDTO(Usuario usuario) {
        if (usuario == null) {
            return;
        }

        setLogin(usuario.getLogin());
        setSenha(usuario.getSenha());
    }


}
