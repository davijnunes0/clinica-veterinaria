package com.clinica.Model.Entities;

import jakarta.validation.constraints.NotBlank;

// Seus comentários sobre as anotações estavam ótimos e foram removidos daqui
// apenas para limpar o código final.

public class Address {
    
    private Long id;

    // 1. VALIDAÇÃO: Padronizado para @NotBlank para todos os campos de texto obrigatórios.
    // É a anotação mais robusta para Strings, garantindo que não sejam nulas, vazias ou só com espaços.
    @NotBlank(message = "A rua não pode estar em branco")
    private String street;

    // O número e o bairro (district) são frequentemente opcionais, então não adicionei @NotBlank.
    // Se eles forem obrigatórios no seu caso de uso, basta adicionar a anotação.
    private String number;
    private String district;

    @NotBlank(message = "A cidade não pode estar em branco")
    private String city;

    @NotBlank(message = "O código postal não pode estar em branco")
    private String zipCode;

    @NotBlank(message = "O país não pode estar em branco")
    private String country;

    /**
     * Construtor padrão exigido por muitos frameworks.
     */
    public Address() {}

    /**
     * Construtor para criar um endereço com todos os campos.
     */
    public Address(Long id, String street, String number, String district, String city, String zipCode, String country) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    /**
     * Construtor para criar um endereço com todos os campos, menos id.
     * 
     * @param street
     * @param number
     * @param district
     * @param city
     * @param zipCode
     * @param country
     */
    public Address(String street,String number, String district, String city, String zipCode, String country){
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    // --- Getters e Setters Simplificados ---
    // 2. LÓGICA DE VALIDAÇÃO: Removida dos setters para evitar redundância.
    // A validação agora é responsabilidade exclusiva das anotações (@NotBlank),
    // que são processadas por um framework (como Spring ou Jakarta EE).

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    
    // 3. CORREÇÃO DE NOME: O getter foi corrigido de getDisctrict para getDistrict
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        // 4. MELHORA NO TOSTRING: Adicionado o 'id' para facilitar a depuração.
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}