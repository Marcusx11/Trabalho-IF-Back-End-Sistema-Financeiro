package com.example.demo.service;

import com.example.demo.definition.UsuarioDetails;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsuarioService extends BaseService implements UserDetailsService {

    private static final String LOGIN = "Login";
    private static final String SENHA = "Senha";
    private static final String USUARIO = "Usuário";


    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void validarDadosUsuario(UsuarioDTO dto) {
        if (dto.getLogin() == null || dto.getLogin().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messages.getAndReplace("campo.obrigatorio", LOGIN));
        }
        if (dto.getSenha() == null || dto.getSenha().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messages.getAndReplace("campo.obrigatorio", SENHA));
        }
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String salvar(UsuarioDTO dto) {
        validarDadosUsuario(dto);

        Optional<Usuario> usuarioBD = repository.findByLogin(dto.getLogin());
        if (usuarioBD.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    messages.getAndReplace("entidade.existente", USUARIO));
        }

        Usuario usuario = new Usuario();

        usuario.transformer(dto, passwordEncoder.encode(dto.getSenha()));
        repository.save(usuario);

        return messages.getAndReplace("entidade.salva", USUARIO);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository
                .findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", USUARIO)));

        return new UsuarioDetails(usuario);
    }
}
