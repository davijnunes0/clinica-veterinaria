package com.clinica.Model.Entities;

import java.time.LocalDate;
import java.util.Objects;


public class Treatment {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean finished;
    private Animal animal;

    public Treatment() {}

    /**
     * Construtor completo para criar um tratamento com todos os dados.
     */
    public Treatment(Long id, String title, LocalDate startDate, LocalDate endDate, Animal animal, boolean finished) {
        this.id = id;
        // Chama os setters para garantir que todas as validações sejam aplicadas na criação.
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate); // A validação do endDate depende do startDate já estar definido.
        this.setAnimal(animal);
        this.setFinished(finished);
    }
    
    // --- Getters e Setters Refatorados ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser nulo ou vazio.");
        }
        this.title = title.trim();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Objects.requireNonNull(startDate, "A data de início não pode ser nula.");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Define a data de término do tratamento, garantindo que não seja anterior à data de início.
     * @param endDate A data de término. Pode ser nula se o tratamento ainda não terminou.
     */
    public void setEndDate(LocalDate endDate) {
        if (endDate != null && this.startDate != null && endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
        }
        this.endDate = endDate;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = Objects.requireNonNull(animal, "O animal do tratamento não pode ser nulo.");
    }

    /**
     * Verifica se o tratamento foi finalizado.
     * @return true se o tratamento foi finalizado, false caso contrário.
     */
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", finished=" + finished +
                ", animal=" + (animal != null ? animal.getName() : "null") +
                '}';
    }
}