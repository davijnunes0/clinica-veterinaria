package com.clinica;

import com.clinica.DataAcessObject.ClientDao;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Long clientId = 1L;

        ClientDao.getInstance().findById(clientId).ifPresentOrElse(
            // Ação a ser executada se o cliente for encontrado
            clienteEncontrado -> {
                System.out.println("Mostrando detalhes de: " + clienteEncontrado.getFirstName());
            },
            // Ação a ser executada se o cliente NÃO for encontrado
            () -> {
                System.out.println("Nenhum cliente com este ID foi encontrado para exibir.");
            }
        );
   
    }
}