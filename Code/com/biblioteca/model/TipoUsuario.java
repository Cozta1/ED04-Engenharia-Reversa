package com.biblioteca.model;

public enum TipoUsuario {
    ADMIN(0),
    ESTUDANTE(3),
    PROFESSOR(5);

    private final int limiteEmprestimos;

    TipoUsuario(int limite) {
        this.limiteEmprestimos = limite;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }
}
