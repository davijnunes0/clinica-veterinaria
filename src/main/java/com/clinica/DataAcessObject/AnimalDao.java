package com.clinica.DataAcessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.clinica.Model.Entities.Animal;
import com.clinica.Model.Entities.Client;
import com.clinica.Model.Entities.Specie;

public class AnimalDao extends AbsctractDatabase<Animal,Long> {

    private static final AnimalDao instance = new AnimalDao();

    private AnimalDao(){}


    public static AnimalDao getInstance(){
        return instance;
    }

    // Query SIMPLES para os métodos padrão (findById, findAll)
    private String selectJoin = 
        "SELECT a.*, sp.name AS nome_da_especie " + // Pega tudo de animal e o nome da especie
        "FROM animal AS a " +
        "INNER JOIN species AS sp ON sp.id_species = a.id_species";

    // Query COMPLEXA para relatórios (novo método)
    @SuppressWarnings("unused")
    private String selectFullJoin =
        "SELECT " +
        "  a.id_animal, a.name AS nome_do_animal, a.birth_date, a.sex, " +
        "  CONCAT(c.first_name, ' ', c.last_name) AS nome_do_cliente, " +
        "  sp.name AS nome_da_especie " +
        "FROM animal AS a " +
        "INNER JOIN client AS c ON c.id_client = a.id_client " +
        "INNER JOIN species AS sp ON sp.id_species = a.id_species;";


    @Override
    protected String getFindByIdSql() {
        return selectJoin + " WHERE id_animal = ?";
    }

    @Override
    protected String getFindAllSql() {
        return selectJoin;
    }

    @Override
    protected String getSaveSql() {
        return "INSERT INTO animal(name,birth_date,sex,id_client,id_species) VALUES (?,?,?,?,?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE animal SET name = ?, birth_date = ?, sex = ?,id_client = ?, id_species = ? WHERE id_animal = ?";
    }

    @Override
    protected String getDeleteByIdSql() {
        return "DELETE FROM animal WHERE id_animal = ?";
    }

    @Override
    protected Animal mapRowToEntity(ResultSet rs) throws SQLException {
        Specie specie = new Specie();
        specie.setId(rs.getLong("id_species"));
        specie.setName(rs.getString("nome_da_especie"));
        // Para pegar o sexo como character:

        Animal animal = new Animal();
        animal.setId(rs.getLong("id_animal"));
        animal.setName(rs.getString("name"));
        animal.setBirthDate(rs.getDate("birth_date").toLocalDate());
        
        // Obtém o valor da coluna "sex" como String e pega o primeiro caractere, representando o sexo do animal
        char sex = rs.getString("sex").charAt(0);
        
        animal.setSex(sex);
        animal.setSpecie(specie);

        return animal;


    }

    @Override
    protected void mapEntityToSaveStatement(Animal entity, PreparedStatement pstmt) throws SQLException {


    }

    @Override
    protected void mapEntityToUpdateStatement(Animal entity, PreparedStatement pstmt) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapEntityToUpdateStatement'");
    }
    

    @Override
    protected void setGenerateId(Animal entity, Long id) {
        entity.setId(id);
    }

    private Client findByclientId(Long clientId){
        return 
        ClientDao.getInstance().findById(clientId)
        .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }
    
}
