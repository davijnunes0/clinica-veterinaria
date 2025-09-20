package com.clinica.Model.Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Animal {
    
    private Long id;
    private String name;
    private LocalDate birthDate;
    private char sex;
    private Specie specie;

    /**
     * Construtor padrão. Exigido por muitos frameworks.
     */
    public Animal() {}
    
    /**
     * Construtor completo para criar um animal a partir de dados existentes (ex: do banco de dados).
     */
    public Animal(Long id, String name, LocalDate birthDate, char sex,Specie specie) {
        // 1. REUTILIZAÇÃO DE LÓGICA: Chamando os setters para garantir que todas as validações sejam aplicadas.
        this.id = id;
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setSex(sex);
        this.setSpecie(specie);
    }

    /**
     * Construtor de cópia. Garante que uma cópia segura seja criada.
     */
    public Animal(Animal other) {
        // 2. CONSTRUTOR DE CÓPIA SEGURO: Usando Objects.requireNonNull para evitar criar uma cópia de um objeto nulo.
        Objects.requireNonNull(other, "O objeto 'other' para cópia não pode ser nulo.");
        this.id = other.id;
        this.name = other.name;
        this.birthDate = other.birthDate;
        this.sex = other.sex;
        this.specie = other.specie;
    }

    /**
     * Construtor para criar um novo animal (sem ID), ideal para novos registros.
     */
    public Animal(String name, LocalDate birthDate, char sex) {
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setSex(sex);
    }
    
    // --- Getters ---

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        this.name = name.trim();
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        // 3. VALIDAÇÃO DE NULIDADE: Adicionada verificação para data de nascimento nula.
        Objects.requireNonNull(birthDate, "A data de nascimento não pode ser nula.");
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser uma data futura.");
        }
        this.birthDate = birthDate;
    }

    public char getSex() {
        return this.sex;
    }
    
    // 4. VALIDAÇÃO PARA O SEXO: Novo setter para garantir que apenas valores válidos sejam aceitos.
    public void setSex(char sex) {
        char upperSex = Character.toUpperCase(sex);
        if (upperSex != 'M' && upperSex != 'F') {
            throw new IllegalArgumentException("O sexo deve ser 'M' (Macho) ou 'F' (Fêmea).");
        }
        this.sex = upperSex;
    }

    public Specie getSpecie(){
        return this.specie;
    }

    public void setSpecie(Specie specie){
        this.specie = specie;
    }

    @Override
    public String toString() {
        // 5. NOME DA CLASSE NO TOSTRING: Corrigido de "Pet" para "Animal" para consistência.
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }
}