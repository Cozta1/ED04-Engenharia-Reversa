package com.biblioteca;

import com.biblioteca.model.TipoUsuario;
import com.biblioteca.service.AdminService;
import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.RepositorioRecursos;
import com.biblioteca.service.RepositorioUsuarios;
import com.biblioteca.ui.MenuPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RepositorioRecursos recursos = new RepositorioRecursos();
        RepositorioUsuarios usuarios = new RepositorioUsuarios();
        AdminService adminService = new AdminService(recursos, usuarios);
        EmprestimoService emprestimoService = new EmprestimoService(recursos, usuarios);
        MenuPrincipal menu = new MenuPrincipal(usuarios, adminService, emprestimoService, recursos);

        adminService.adicionarUsuario("munshi:p", "lums123", TipoUsuario.ADMIN);

        try (Scanner sc = new Scanner(System.in)) {
            menu.executar(sc);
        }
    }
}
