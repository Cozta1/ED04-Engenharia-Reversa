package com.biblioteca.ui;

import com.biblioteca.model.RecursoEmprestavel;
import com.biblioteca.model.Recurso;
import com.biblioteca.service.RepositorioRecursos;
import com.biblioteca.service.RepositorioUsuarios;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExibidorDeRecursos {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final RepositorioRecursos repositorioRecursos;

    public ExibidorDeRecursos(RepositorioRecursos repositorioRecursos) {
        this.repositorioRecursos = repositorioRecursos;
    }

    public void exibirRecursosEmprestados(List<Integer> ids) {
        if (ids.isEmpty()) {
            System.out.println("Nenhum recurso emprestado.");
            return;
        }
        System.out.println("No.\tID --- Recurso --- Emprestimo --- Devolucao");
        for (int i = 0; i < ids.size(); i++) {
            exibirLinhaRecurso(i + 1, ids.get(i));
        }
    }

    public void exibirTodosRecursos() {
        List<Recurso> lista = repositorioRecursos.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum recurso cadastrado.");
            return;
        }
        System.out.println("No.\tID --- Recurso --- Disponivel");
        for (int i = 0; i < lista.size(); i++) {
            Recurso r = lista.get(i);
            String status = r.podeSerEmprestado() ? "Sim" : "Nao";
            System.out.printf("%d.\t%d --- %s --- %s%n", i + 1, r.getId(), r.getNome(), status);
        }
    }

    private void exibirLinhaRecurso(int numero, int idRecurso) {
        repositorioRecursos.buscarPorId(idRecurso).ifPresent(r -> {
            if (r instanceof RecursoEmprestavel) {
                RecursoEmprestavel re = (RecursoEmprestavel) r;
                String dataEmp = re.getDataEmprestimo() != null
                        ? re.getDataEmprestimo().format(FORMATO_DATA) : "-";
                String dataDev = re.getDataDevolucao() != null
                        ? re.getDataDevolucao().format(FORMATO_DATA) : "-";
                System.out.printf("%d.\t%d --- %s --- %s --- %s%n",
                        numero, r.getId(), r.getNome(), dataEmp, dataDev);
            }
        });
    }
}
