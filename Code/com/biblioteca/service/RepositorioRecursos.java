package com.biblioteca.service;

import com.biblioteca.model.Recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositorioRecursos {

    private static int proximoId = 1001;
    private final List<Recurso> recursos;

    public RepositorioRecursos() {
        this.recursos = new ArrayList<>();
    }

    public int proximoId() {
        return proximoId++;
    }

    public void adicionar(Recurso r) {
        recursos.add(r);
    }

    public boolean remover(int id) {
        return recursos.removeIf(r -> r.getId() == id);
    }

    public Optional<Recurso> buscarPorId(int id) {
        return recursos.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    public List<Recurso> buscarPorNome(String nome) {
        return recursos.stream()
                .filter(r -> r.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }

    public List<Recurso> listar() {
        return new ArrayList<>(recursos);
    }
}
