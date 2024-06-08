package br.com.julianeandrade.service;


import br.com.julianeandrade.entity.Usuario;
import br.com.julianeandrade.exception.ObjetoNaoEncontradoException;
import br.com.julianeandrade.repositorio.UsuarioRepositorio;
import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Dependent
public class UsuarioService {

    UsuarioRepositorio repositorio;

    public UsuarioService(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public Usuario novoUsuario(Usuario usuario){
        repositorio.persist(usuario);
        return usuario;
    }

    public List<Usuario> listarUsuario(Integer page, Integer pageSize) {
        return repositorio.findAll().page(page, pageSize).list();
    }

    public Usuario buscarUsuario(UUID usuarioId) {
        return repositorio.findByIdOptional(usuarioId).orElseThrow(ObjetoNaoEncontradoException::new);
    }

    @Transactional
    public Usuario atualizarUsuario(UUID usuarioId, Usuario usuario) {
        Usuario usuarioAtual = buscarUsuario(usuarioId);
        usuarioAtual.setNome(usuario.getNome());
        usuarioAtual.setDataNascimento(usuario.getDataNascimento());
        repositorio.persist(usuarioAtual);
        return usuarioAtual;
    }

    @Transactional
    public void excluirUsuario(UUID usuarioId) {
        repositorio.delete(buscarUsuario(usuarioId));
    }
}
