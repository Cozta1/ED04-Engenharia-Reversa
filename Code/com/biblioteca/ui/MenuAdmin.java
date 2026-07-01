package com.biblioteca.ui;

import com.biblioteca.model.TipoUsuario;
import com.biblioteca.service.AdminService;

import java.util.Scanner;

public class MenuAdmin {

    private static final String MENU =
            "1-Add Admin  2-Add Professor  3-Add Estudante  4-Add Livro" +
            "  5-Add Pacote  6-Add Revista  7-Rem Usuario  8-Rem Recurso  0-Sair";

    private final Scanner scanner;
    private final AdminService adminService;

    public MenuAdmin(Scanner scanner, AdminService adminService) {
        this.scanner = scanner;
        this.adminService = adminService;
    }

    public void executar() {
        boolean ativo = true;
        while (ativo) {
            System.out.println(MENU);
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
            case 1: adicionarUsuario(TipoUsuario.ADMIN); break;
            case 2: adicionarUsuario(TipoUsuario.PROFESSOR); break;
            case 3: adicionarUsuario(TipoUsuario.ESTUDANTE); break;
            case 4: System.out.println("ID: " + adminService.adicionarLivro(lerTexto("Nome: "))); break;
            case 5: System.out.println("ID: " + adminService.adicionarPacoteCurso(lerTexto("Nome: "))); break;
            case 6: System.out.println("ID: " + adminService.adicionarRevista(lerTexto("Nome: "))); break;
            case 7: adminService.removerUsuario(lerInteiroComLabel("ID do usuario: ")); break;
            case 8: adminService.removerRecurso(lerInteiroComLabel("ID do recurso: ")); break;
            case 0: return false;
            default: System.out.println("Opcao invalida.");
        }
        return true;
    }

    private void adicionarUsuario(TipoUsuario tipo) {
        String nome = lerTexto("Nome: ");
        String senha = lerTexto("Senha: ");
        int id = adminService.adicionarUsuario(nome, senha, tipo);
        System.out.println("Usuario criado. ID: " + id);
    }

    private String lerTexto(String label) {
        System.out.print(label);
        return scanner.nextLine();
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
