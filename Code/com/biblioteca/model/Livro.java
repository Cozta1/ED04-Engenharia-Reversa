package com.biblioteca.model;

public class Livro extends RecursoEmprestavel {

    private static final int PRAZO_ESTUDANTE_DIAS = 15;
    private static final int PRAZO_PROFESSOR_DIAS = 30;
    private static final int MULTA_DIARIA = 100;

    public Livro(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeSerEmprestado() {
        return super.podeSerEmprestado();
    }

    @Override
    public int getPrazoPara(TipoUsuario tipo) {
        if (tipo == TipoUsuario.PROFESSOR) {
            return PRAZO_PROFESSOR_DIAS;
        }
        return PRAZO_ESTUDANTE_DIAS;
    }

    @Override
    public long calcularMulta() {
        return diasDeAtraso() * MULTA_DIARIA;
    }
}
