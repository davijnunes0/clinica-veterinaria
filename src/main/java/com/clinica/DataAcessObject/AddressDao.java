package com.clinica.DataAcessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.clinica.Model.Entities.Address;

public class AddressDao extends AbsctractDatabase<Address, Long> {

    private static AddressDao instance = new AddressDao();

    private AddressDao(){}

    // Singleton
    public static AddressDao getInstance(){
        return instance;
    }

    @Override
    protected String getFindByIdSql() {
        return "SELECT id_address, street, number, district, city, zip_code, country FROM address WHERE id_address = ?";
    }

    @Override
    protected String getFindAllSql() {
        // CORREÇÃO: Removido o ponto inválido entre "district" e "city"
        return "SELECT id_address, street, number, district, city, zip_code, country FROM address";
    }

    @Override
    protected String getSaveSql() {
        // CORREÇÃO: Adicionado o 6º placeholder '?' para corresponder às 6 colunas
        return "INSERT INTO address(street, number, district, city, zip_code, country) VALUES(?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        // CORREÇÃO: Adicionado o campo "city = ?" à query
        return "UPDATE address SET street = ?, number = ?, district = ?, city = ?, zip_code = ?, country = ? WHERE id_address = ?";
    }

    @Override
    protected String getDeleteByIdSql() {
        return "DELETE FROM address WHERE id_address = ?";
    }

    @Override
    protected Address mapRowToEntity(ResultSet rs) throws SQLException {
        // CORREÇÃO: Mapeamento correto de cada coluna para seu respectivo atributo
        Address address = new Address();
        address.setId(rs.getLong("id_address"));
        address.setStreet(rs.getString("street")); // Corrigido
        address.setNumber(rs.getString("number"));
        address.setDistrict(rs.getString("district"));
        address.setCity(rs.getString("city"));     // Corrigido (Adicionado)
        address.setZipCode(rs.getString("zip_code"));
        address.setCountry(rs.getString("country")); // Corrigido
        return address;
    }

    @Override
    protected void mapEntityToSaveStatement(Address entity, PreparedStatement pstmt) throws SQLException {
        // CORREÇÃO: Ordem correta e completa dos parâmetros
        pstmt.setString(1, entity.getStreet());
        pstmt.setString(2, entity.getNumber());
        pstmt.setString(3, entity.getDistrict());
        pstmt.setString(4, entity.getCity());
        pstmt.setString(5, entity.getZipCode());
        pstmt.setString(6, entity.getCountry());
    }

    @Override
    protected void mapEntityToUpdateStatement(Address entity, PreparedStatement pstmt) throws SQLException {
        // CORREÇÃO: Ordem correta e completa dos parâmetros para o UPDATE
        pstmt.setString(1, entity.getStreet());
        pstmt.setString(2, entity.getNumber());
        pstmt.setString(3, entity.getDistrict());
        pstmt.setString(4, entity.getCity());
        pstmt.setString(5, entity.getZipCode());
        pstmt.setString(6, entity.getCountry());
        pstmt.setLong(7, entity.getId()); // O ID agora é o 7º parâmetro
    }

    @Override
    protected void setGenerateId(Address entity, Long id) {
        entity.setId(id);
    }
}