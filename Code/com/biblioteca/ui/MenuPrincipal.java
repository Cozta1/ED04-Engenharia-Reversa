package com.biblioteca.ui;

import com.biblioteca.model.TipoUsuario;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.AdminService;
import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.RepositorioRecursos;
import com.biblioteca.service.RepositorioUsuarios;

import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {

    private final RepositorioUsuarios repositorioUsuarios;
    private final AdminService adminService;
    private final EmprestimoService emprestimoService;
    private final RepositorioRecursos repositorioRecursos;

    public MenuPrincipal(RepositorioUsuarios repositorioUsuarios,
                         AdminService adminService,
                         EmprestimoService emprestimoService,
                         RepositorioRecursos repositorioRecursos) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.adminService = adminService;
        this.emprestimoService = emprestimoService;
        this.repositorioRecursos = repositorioRecursos;
    }

    public void executar(Scanner scanner) {
        System.out.println("=== Sistema de Biblioteca ===");
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n1-Login Admin  2-Login Usuario  0-Sair");
            int opcao = lerInteiro(scanner);
            switch (opcao) {
                case 1: autenticarEAbrirMenu(scanner, TipoUsuario.ADMIN); break;
                case 2: autenticarEAbrirMenu(scanner, TipoUsuario.ESTUDANTE, TipoUsuario.PROFESSOR); break;
                case 0: rodando = false; break;
                default: System.out.println("Opcao invalida.");
            }
        }
        System.out.println("Encerrando sistema.");
    }

    private void autenticarEAbrirMenu(Scanner scanner, TipoUsuario... tiposPermitidos) {
        System.out.print("Usuario: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Optional<Usuario> encontrado = repositorioUsuarios.buscarPorNome(nome);
        if (encontrado.isEmpty() || !encontrado.get().autenticar(nome, senha)) {
            System.out.println("Credenciais invalidas.");
            return;
        }

        Usuario usuario = encontrado.get();
        if (!tipoPermitido(usuario.getTipo(), tiposPermitidos)) {
            System.out.println("Tipo de usuario nao permitido neste login.");
            return;
        }

        abrirMenuPara(scanner, usuario);
    }

    private boolean tipoPermitido(TipoUsuario tipo, TipoUsuario[] permitidos) {
        for (TipoUsuario p : permitidos) {
            if (tipo == p) return true;
        }
        return false;
    }

    private void abrirMenuPara(Scanner scanner, Usuario usuario) {
        if (usuario.getTipo() == TipoUsuario.ADMIN) {
            new MenuAdmin(scanner, adminService).executar();
        } else {
            new MenuUsuario(scanner, usuario, emprestimoService, repositorioRecursos).executar();
        }
    }

    private int lerInteiro(Scanner scanner) {
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}
