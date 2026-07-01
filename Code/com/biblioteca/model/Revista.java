package com.biblioteca.model;

public class Revista extends Recurso {

    public Revista(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeSerEmprestado() {
        return false;
    }
}
