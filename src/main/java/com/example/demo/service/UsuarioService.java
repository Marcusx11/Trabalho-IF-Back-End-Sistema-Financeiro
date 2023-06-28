package com.example.demo.service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioService extends BaseService {

    private static final String LOGIN = "Login";
    private static final String SENHA = "Senha";
    private static final String USUARIO = "Usuário";

    private MessageDigest md;

    @Autowired
    private UsuarioRepository repository;

    @PostConstruct
    private void inicializarMD() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, messages.get("erro.inesperado"));
        }
    }

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

        usuario.transformer(dto, gerarSenhaHash(dto.getSenha()));
        repository.save(usuario);

        return messages.getAndReplace("entidade.salva", USUARIO);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO autenticar(UsuarioDTO dto) {
        Optional<Usuario> usuarioBD = repository.findByLogin(dto.getLogin());
        if (usuarioBD.isPresent()) {
            Usuario usuario = usuarioBD.get();
            boolean senhaValida = usuario.getSenha().equals(gerarSenhaHash(dto.getSenha()));
            if (Boolean.TRUE.equals(senhaValida)) {
                return new UsuarioDTO(usuario.getLogin(), usuario.getSenha());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.get("campo.login.senha.invalido"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.get("campo.login.senha.invalido"));
    }

    private String gerarSenhaHash(String senha) {
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        return hash.toString(16);
    }
}
