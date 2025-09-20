package com.clinica.Model.Entities;

import java.util.Objects;

public class Exam {
    
    private Long id;
    private String describe;
    private Consultation consultation;

    public Exam(){}

    /**
     * 
     * @param id
     * @param describe
     * @param idConsultation
     */
    public Exam(Long id,String describe,Consultation consultation){
        this.id = id;
        this.setDescribe(describe);
        this.consultation = consultation;
    }

    /**
     * 
     * @param describe
     * @param idConsultation
     */
    public Exam(String describe, Consultation consultation){
        this.describe = describe;
        this.consultation = consultation;
    }

    public Long getId(){
        return this.id;
    }

    public Consultation getConsultation(){
        return this.consultation;
    }

    public void setDescribe(String describe){
        this.describe = Objects.requireNonNull(describe, "A descrição tem que ser preenchida pelo usuário.");
    }

    public String getDescribe(){
        return this.describe;
    }
}
