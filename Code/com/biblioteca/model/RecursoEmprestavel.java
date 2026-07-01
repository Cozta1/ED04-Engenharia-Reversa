package com.biblioteca.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class RecursoEmprestavel extends Recurso {

    private int idUsuarioEmprestou;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean disponivel;

    protected RecursoEmprestavel(int id, String nome) {
        super(id, nome);
        this.disponivel = true;
        this.idUsuarioEmprestou = -1;
    }

    @Override
    public boolean podeSerEmprestado() {
        return disponivel;
    }

    public void emprestar(int idUsuario, int diasPrazo) {
        this.disponivel = false;
        this.idUsuarioEmprestou = idUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(diasPrazo);
    }

    public void devolver() {
        this.disponivel = true;
        this.idUsuarioEmprestou = -1;
        this.dataEmprestimo = null;
        this.dataDevolucao = null;
    }

    public boolean estaEmprestadoPor(int idUsuario) {
        return idUsuarioEmprestou == idUsuario;
    }

    public boolean estaAtrasado() {
        return dataDevolucao != null && LocalDate.now().isAfter(dataDevolucao);
    }

    public long diasDeAtraso() {
        if (!estaAtrasado()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dataDevolucao, LocalDate.now());
    }

    public abstract long calcularMulta();
    public abstract int getPrazoPara(TipoUsuario tipo);

    public int getIdUsuarioEmprestou() { return idUsuarioEmprestou; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
}
