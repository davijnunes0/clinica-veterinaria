package com.clinica.Model.Entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Client {

    private Long id;

    // 1. VALIDAÇÃO: Usando @NotBlank para consistência com as outras classes.
    @NotBlank(message = "O primeiro nome não pode estar em branco")
    private String firstName;

    @NotBlank(message = "O sobrenome não pode estar em branco")
    private String lastName;

    // A anotação @NotNull garante que o objeto Address em si não seja nulo.
    @NotNull(message = "O endereço não pode ser nulo")
    private Address address;
    
    @NotBlank(message = "O telefone не pode estar em branco")
    private String telephone;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    private String email;
    
    // A lista de animais é inicializada aqui para garantir que nunca seja nula.
    private final List<Animal> animals = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public Client() {}

    /**
     * Construtor completo.
     */
    public Client(Long id, String firstName, String lastName, Address address, String telephone, String email) {
        // 2. CONSTRUTOR MELHORADO: Atribuição direta e uso de setters para consistência.
        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setTelephone(telephone);
        this.setEmail(email);
    }
    
    /**
     * Construtor para novos clientes (sem ID), utilizando encadeamento de construtores.
     */
    public Client(String firstName, String lastName, Address address, String telephone, String email) {
        // 3. ENCADEAMENTO DE CONSTRUTORES: Chama o construtor completo passando 'null' para o id.
        // Isso evita código duplicado e garante que a lógica de inicialização seja a mesma.
        this(null, firstName, lastName, address, telephone, email);
    }
    
    // --- Getters e Setters Refatorados ---

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        // Usando Objects.requireNonNull para uma validação de nulidade mais concisa.
        this.address = Objects.requireNonNull(address, "O endereço não pode ser nulo.");
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }
    
    // 4. CORREÇÃO DE BUG: O getter de email não deve ter parâmetros.
    public void setEmail(String email) {
        this.email = email;
    }

    // --- Gerenciamento da Lista de Animais ---

    /**
     * 5. ENCAPSULAMENTO DA LISTA: Retorna uma "visão" não modificável da lista.
     * Isso impede que código externo adicione ou remova animais diretamente da lista,
     * forçando o uso dos métodos addAnimal() e removeAnimal().
     */
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(this.animals);
    }

    public void addAnimal(Animal animal) {
        // Validação para garantir que não se adicione um animal nulo à lista.
        Objects.requireNonNull(animal, "Não é possível adicionar um animal nulo.");
        this.animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        Objects.requireNonNull(animal, "Não é possível remover um animal nulo.");
        this.animals.remove(animal);
    }
    
    // Método de conveniência para limpar a lista de animais
    public void clearAnimals() {
        this.animals.clear();
    }

    @Override
    public String toString() {
        // 6. TOSTRING MELHORADO: Incluindo o ID e o número de animais.
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", numberOfAnimals=" + animals.size() +
                '}';
    }
}