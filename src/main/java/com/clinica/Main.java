package com.clinica;

import java.sql.SQLException;
import com.clinica.DataAcessObject.ClientDao;
import com.clinica.Model.Entities.Address;
import com.clinica.Model.Entities.Animal;
import com.clinica.Model.Entities.Client;
import com.clinica.Model.Entities.Specie;

public class Main {
    public static void main(String[] args)  throws SQLException{
        System.out.println("Hello world!");


        Address address = new Address();

        address.setId(16L);
        address.setStreet("Rua das Flores");
        address.setNumber("123");
        address.setDistrict("Centro");
        address.setCity("São Paulo");
        address.setCountry("BR");;
        address.setZipCode("01000");

        //AddressDao.getInstance().save(address);


        Specie specie = new Specie();
        specie.setId(2L);
        specie.setName("MamíferoS");


        Client client = new Client();
        // Removido: client.setName("João Silva");
        // Removido: client.setBirthDate(LocalDate.of(1990, 5, 15));
        // Removido: client.setSex('M');
        // Removido: client.setSpecie(specie);

        client.setId(8L);
        client.setFirstName("João");
        client.setLastName("Silva");
        client.setEmail("joao.silva@email.com");
        client.setTelephone("11988888888");
        client.setAddress(address);

        // Animal dog = new Animal();
        // dog.setName("Dog");
        // dog.setBirthDate(java.time.LocalDate.of(2019, 1, 10));
        // dog.setSex('M');
        // dog.setSpecie(specie);

        Animal monkey = new Animal();
        monkey.setName("Monkey");
        monkey.setBirthDate(java.time.LocalDate.now());
        monkey.setSex('M');
        monkey.setSpecie(specie);
        client.addAnimal(monkey);
        
        // Animal cat = new Animal();
        // cat.setName("Cat");
        // cat.setBirthDate(java.time.LocalDate.of(2021, 3, 5));
        // cat.setSex('F');
        // cat.setSpecie(specie);


        // Animal chicken = new Animal();
        // chicken.setName("Chicken");
        // chicken.setBirthDate(java.time.LocalDate.of(2023, 7, 20));
        // chicken.setSex('F');
        // chicken.setSpecie(specie);

        
        // ClientDao.getInstance().save(client);
        ClientDao.getInstance().saveAnimalsForClient(client);
        
   
    }
}