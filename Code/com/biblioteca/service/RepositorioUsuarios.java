package com.biblioteca.service;

import com.biblioteca.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioUsuarios {

    private static int proximoId = 14100180;
    private final List<Usuario> usuarios;

    public RepositorioUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public int proximoId() {
        return proximoId++;
    }

    public void salvar(Usuario u) {
        usuarios.add(u);
    }

    public boolean remover(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarios.stream()
                .filter(u -> u.getNomeUsuario().equals(nome))
                .findFirst();
    }

    public boolean existeComNome(String nome) {
        return buscarPorNome(nome).isPresent();
    }

    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }
}
