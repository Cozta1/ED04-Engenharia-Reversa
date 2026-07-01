package com.biblioteca.service;

import com.biblioteca.model.Recurso;
import com.biblioteca.model.RecursoEmprestavel;
import com.biblioteca.model.Usuario;

import java.util.List;

public class EmprestimoService {

    private final RepositorioRecursos repositorioRecursos;
    private final RepositorioUsuarios repositorioUsuarios;

    public EmprestimoService(RepositorioRecursos repositorioRecursos,
                             RepositorioUsuarios repositorioUsuarios) {
        this.repositorioRecursos = repositorioRecursos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void emprestar(int idUsuario, int idRecurso) {
        Usuario usuario = encontrarUsuario(idUsuario);
        Recurso recurso = encontrarRecurso(idRecurso);

        validarEmprestimo(usuario, recurso);

        RecursoEmprestavel emprestavel = (RecursoEmprestavel) recurso;
        int prazo = emprestavel.getPrazoPara(usuario.getTipo());
        emprestavel.emprestar(idUsuario, prazo);
        usuario.registrarEmprestimo(idRecurso);
    }

    public long devolver(int idUsuario, int idRecurso) {
        Usuario usuario = encontrarUsuario(idUsuario);
        Recurso recurso = encontrarRecurso(idRecurso);
        RecursoEmprestavel emprestavel = validarDevolucao(usuario, recurso);

        long multa = emprestavel.calcularMulta();
        emprestavel.devolver();
        usuario.registrarDevolucao(idRecurso);
        return multa;
    }

    public void adicionarSolicitacao(int idUsuario, int idRecurso) {
        Usuario usuario = encontrarUsuario(idUsuario);

        if (usuario.temSolicitacaoPendente(idRecurso)) {
            throw new IllegalStateException("Solicitação já existe para este recurso.");
        }
        Recurso recurso = encontrarRecurso(idRecurso);
        if (!recurso.podeSerEmprestado()) {
            throw new IllegalStateException("Este recurso não pode ser solicitado.");
        }
        usuario.adicionarSolicitacao(idRecurso);
    }

    public List<Integer> listarEmprestadosPor(int idUsuario) {
        return encontrarUsuario(idUsuario).getRecursosEmprestados();
    }

    private void validarEmprestimo(Usuario usuario, Recurso recurso) {
        if (!recurso.podeSerEmprestado()) {
            throw new IllegalStateException("Este recurso não pode ser emprestado.");
        }
        if (usuario.atingiuLimiteDeEmprestimos()) {
            throw new IllegalStateException("Usuário atingiu o limite de empréstimos.");
        }
        RecursoEmprestavel emprestavel = (RecursoEmprestavel) recurso;
        if (emprestavel.estaEmprestadoPor(usuario.getId())) {
            throw new IllegalStateException("Recurso já está emprestado para este usuário.");
        }
    }

    private RecursoEmprestavel validarDevolucao(Usuario usuario, Recurso recurso) {
        if (!(recurso instanceof RecursoEmprestavel)) {
            throw new IllegalStateException("Recurso não é emprestável.");
        }
        RecursoEmprestavel emprestavel = (RecursoEmprestavel) recurso;
        if (!emprestavel.estaEmprestadoPor(usuario.getId())) {
            throw new IllegalStateException("Este recurso não está emprestado para este usuário.");
        }
        return emprestavel;
    }

    private Usuario encontrarUsuario(int id) {
        return repositorioUsuarios.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
    }

    private Recurso encontrarRecurso(int id) {
        return repositorioRecursos.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Recurso não encontrado: " + id));
    }
}
