package com.biblioteca.model;

public class PacoteCurso extends RecursoEmprestavel {

    private static final int PRAZO_DIAS = 15;
    private static final int MULTA_DIARIA = 500;

    public PacoteCurso(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeSerEmprestado() {
        return super.podeSerEmprestado();
    }

    @Override
    public int getPrazoPara(TipoUsuario tipo) {
        return PRAZO_DIAS;
    }

    @Override
    public long calcularMulta() {
        return diasDeAtraso() * MULTA_DIARIA;
    }
}
