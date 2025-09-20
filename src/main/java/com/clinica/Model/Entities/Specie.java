package com.clinica.Model.Entities;

public class Specie {
    
    private Long id;
    private String name;

    public Specie() {}

    /**
     * 
     * @param id
     * @param name
     */
    public Specie(Long id,String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @param name
     */
    public Specie(String name){
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "[getName()= " + getName() + ", getId()= " + getId() + " ]";
    }
}
