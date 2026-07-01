package com.biblioteca.model;

public abstract class Recurso {
    private final int id;
    private final String nome;

    protected Recurso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public abstract boolean podeSerEmprestado();

    public int getId() { return id; }
    public String getNome() { return nome; }
}
