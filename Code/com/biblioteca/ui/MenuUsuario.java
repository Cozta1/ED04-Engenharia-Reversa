package com.biblioteca.ui;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.RepositorioRecursos;

import java.util.Scanner;

public class MenuUsuario {

    private static final String MENU = "1-Emprestar  2-Devolver  3-Ver Emprestados  4-Solicitar  0-Sair";

    private final Scanner scanner;
    private final Usuario usuario;
    private final EmprestimoService emprestimoService;
    private final ExibidorDeRecursos exibidor;

    public MenuUsuario(Scanner scanner, Usuario usuario,
                       EmprestimoService emprestimoService,
                       RepositorioRecursos repositorioRecursos) {
        this.scanner = scanner;
        this.usuario = usuario;
        this.emprestimoService = emprestimoService;
        this.exibidor = new ExibidorDeRecursos(repositorioRecursos);
    }

    public void executar() {
        boolean ativo = true;
        while (ativo) {
            System.out.printf("[%s] %s%n", usuario.getNomeUsuario(), MENU);
            int opcao = lerInteiro();
            try {
                ativo = processar(opcao);
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private boolean processar(int opcao) {
        switch (opcao) {
            case 1: emprestar(); break;
            case 2: devolver(); break;
            case 3: exibidor.exibirRecursosEmprestados(usuario.getRecursosEmprestados()); break;
            case 4: solicitarRecurso(); break;
            case 0: return false;
            default: System.out.println("Opcao invalida.");
        }
        return true;
    }

    private void emprestar() {
        int idRecurso = lerInteiroComLabel("ID do recurso: ");
        emprestimoService.emprestar(usuario.getId(), idRecurso);
        System.out.println("Emprestimo realizado.");
    }

    private void devolver() {
        int idRecurso = lerInteiroComLabel("ID do recurso: ");
        long multa = emprestimoService.devolver(usuario.getId(), idRecurso);
        System.out.printf("Devolvido. Multa: Rs. %d%n", multa);
    }

    private void solicitarRecurso() {
        int idRecurso = lerInteiroComLabel("ID do recurso: ");
        emprestimoService.adicionarSolicitacao(usuario.getId(), idRecurso);
        System.out.println("Solicitacao registrada.");
    }

    private int lerInteiroComLabel(String label) {
        System.out.print(label);
        return lerInteiro();
    }

    private int lerInteiro() {
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}
