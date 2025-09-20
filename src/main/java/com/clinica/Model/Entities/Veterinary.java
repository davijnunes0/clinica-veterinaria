package com.clinica.Model.Entities;

import java.util.Objects;

public class Veterinary {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;

    
    public Veterinary() {}
    /**
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param telephone
     */
    public Veterinary(Long id, String firstName, String lastName, String telephone){
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setTelephone(telephone);

    }
     /**
     * 
     * @param id
     * @param firstName
     * @param lastName
     * @param telephone
     */
    public Veterinary(Long id, String firstName, String lastName){
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = Objects.requireNonNull(firstName, "O Primeiro nome do veterinário deve ser preenchido.");
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = Objects.requireNonNull(lastName,"O sobrenome nome do veterinário deve ser preenchido.");
    }

    public String getTelephone(){
        return this.telephone;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Veterinary [getId()=" + getId() + ", getFirstName()=" + getFirstName() + ", getLastName()="
                + getLastName() + ", getTelephone()=" + getTelephone() + "]";
    }

    
}
