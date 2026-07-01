package com.biblioteca.service;

import com.biblioteca.model.Livro;
import com.biblioteca.model.PacoteCurso;
import com.biblioteca.model.Recurso;
import com.biblioteca.model.Revista;
import com.biblioteca.model.TipoUsuario;
import com.biblioteca.model.Usuario;

public class AdminService {

    private final RepositorioRecursos repositorioRecursos;
    private final RepositorioUsuarios repositorioUsuarios;

    public AdminService(RepositorioRecursos repositorioRecursos,
                        RepositorioUsuarios repositorioUsuarios) {
        this.repositorioRecursos = repositorioRecursos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public int adicionarUsuario(String nomeUsuario, String senha, TipoUsuario tipo) {
        if (repositorioUsuarios.existeComNome(nomeUsuario)) {
            throw new IllegalArgumentException("Nome de usuário já existe.");
        }
        int id = repositorioUsuarios.proximoId();
        repositorioUsuarios.salvar(new Usuario(id, nomeUsuario, senha, tipo));
        return id;
    }

    public boolean removerUsuario(int id) {
        return repositorioUsuarios.remover(id);
    }

    public int adicionarLivro(String nome) {
        int id = repositorioRecursos.proximoId();
        repositorioRecursos.adicionar(new Livro(id, nome));
        return id;
    }

    public int adicionarPacoteCurso(String nome) {
        int id = repositorioRecursos.proximoId();
        repositorioRecursos.adicionar(new PacoteCurso(id, nome));
        return id;
    }

    public int adicionarRevista(String nome) {
        int id = repositorioRecursos.proximoId();
        repositorioRecursos.adicionar(new Revista(id, nome));
        return id;
    }

    public boolean removerRecurso(int id) {
        return repositorioRecursos.remover(id);
    }
}
