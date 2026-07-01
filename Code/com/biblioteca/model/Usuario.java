package com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final int id;
    private final String nomeUsuario;
    private final String senha;
    private final TipoUsuario tipo;
    private final List<Integer> recursosEmprestados;
    private final List<Integer> recursosSolicitados;

    public Usuario(int id, String nomeUsuario, String senha, TipoUsuario tipo) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.tipo = tipo;
        this.recursosEmprestados = new ArrayList<>();
        this.recursosSolicitados = new ArrayList<>();
    }

    public boolean autenticar(String nome, String senhaInformada) {
        return nomeUsuario.equals(nome) && senha.equals(senhaInformada);
    }

    public boolean atingiuLimiteDeEmprestimos() {
        return recursosEmprestados.size() >= tipo.getLimiteEmprestimos();
    }

    public boolean temRecursoEmprestado(int idRecurso) {
        return recursosEmprestados.contains(idRecurso);
    }

    public boolean temSolicitacaoPendente(int idRecurso) {
        return recursosSolicitados.contains(idRecurso);
    }

    public void registrarEmprestimo(int idRecurso) {
        recursosEmprestados.add(idRecurso);
    }

    public void registrarDevolucao(int idRecurso) {
        recursosEmprestados.remove(Integer.valueOf(idRecurso));
    }

    public void adicionarSolicitacao(int idRecurso) {
        recursosSolicitados.add(idRecurso);
    }

    public void removerSolicitacao(int idRecurso) {
        recursosSolicitados.remove(Integer.valueOf(idRecurso));
    }

    public int getId() { return id; }
    public String getNomeUsuario() { return nomeUsuario; }
    public TipoUsuario getTipo() { return tipo; }
    public List<Integer> getRecursosEmprestados() { return new ArrayList<>(recursosEmprestados); }
    public List<Integer> getRecursosSolicitados() { return new ArrayList<>(recursosSolicitados); }
}
