package com.clinica.Model.Entities;

import java.time.LocalDateTime;
import java.util.Objects;


public class Consultation {
    
    // Nomes de variáveis simplificados e mais claros
    private Long id;
    private LocalDateTime dateTime; // Unifica data e hora
    private String comment;
    private boolean finished; // Mais descritivo que "end"

    // --- Relacionamentos com Objetos Completos ---
    private Animal animal;
    private Veterinary veterinary;
    private Treatment treatment;

    public Consultation() {}

    /**
     * Construtor completo para criar uma consulta com todos os dados.
     */
    public Consultation(Long id, LocalDateTime dateTime, String comment, boolean finished, 
                        Animal animal, Veterinary veterinary, Treatment treatment) {
        this.id = id;
        this.setDateTime(dateTime);
        this.setComment(comment); // Reutiliza a validação do setter
        this.setFinished(finished);
        this.setAnimal(animal);
        this.setVeterinary(veterinary);
        this.setTreatment(treatment);
    }

    // --- Getters e Setters Corrigidos ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        // Validação de nulidade para garantir que a data seja sempre fornecida
        this.dateTime = Objects.requireNonNull(dateTime, "A data e hora da consulta não podem ser nulas.");
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser nulo ou vazio.");
        }
        this.comment = comment.trim();
    }

    // Convenção padrão "is" para getters de booleanos
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = Objects.requireNonNull(animal, "O animal da consulta не pode ser nulo.");
    }

    public Veterinary getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(Veterinary veterinary) {
        this.veterinary = Objects.requireNonNull(veterinary, "O veterinário da consulta não pode ser nulo.");
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        // toString() atualizado para refletir o novo modelo e ser mais útil para depuração
        return "Consultation{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", finished=" + finished +
                ", animal=" + (animal != null ? animal.getName() : "null") +
                // ", veterinary=" + (veterinary != null ? veterinary.getName() : "null") +
                ", comment='" + comment + '\'' +
                '}';
    }
}