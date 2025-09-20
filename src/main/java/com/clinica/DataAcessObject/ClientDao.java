package com.clinica.DataAcessObject;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.clinica.Factory.ConnectionFactory;
import com.clinica.Model.Entities.Address;
import com.clinica.Model.Entities.Animal;
import com.clinica.Model.Entities.Client;

public class ClientDao extends AbsctractDatabase<Client, Long> {

    private static ClientDao instance = new ClientDao();

    private ClientDao(){
    }


    // Singleton 
    public static ClientDao getInstance(){
        return instance;
    }

    // Helper para evitar repetição de código nas queries de SELECT
    private final String selectJoinSql = 
        "SELECT c.id_client, c.first_name, c.last_name, c.telephone, c.email, " +
        "a.id_address, a.street, a.number, a.district, a.city, a.zip_code, a.country " +
        "FROM client c " +
        "INNER JOIN address a ON c.id_address = a.id_address ";

    @Override
    protected String getFindByIdSql() {
        // CORREÇÃO: Usando a query com JOIN e corrigindo o nome da coluna para "id_client"
        return selectJoinSql + "WHERE c.id_client = ?";
    }

    @Override
    protected String getFindAllSql() {
        // CORREÇÃO: Usando a query com JOIN
        return selectJoinSql;
    }

    @Override
    protected String getSaveSql() {
        return "INSERT INTO client(first_name, last_name, id_address, telephone, email) VALUES(?, ?, ?, ?, ?)";
    }

    private String getSaveAnimalSql(){
        return "INSERT INTO animal(name,birth_date,sex,id_client,id_species) VALUES(?,?,?,?,?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE client SET first_name = ?, last_name = ?, id_address = ?, telephone = ?, email = ? WHERE id_client = ?";
    }

    @Override
    protected String getDeleteByIdSql() {
        return "DELETE FROM client WHERE id_client = ?";
    }

    @Override
    protected Client mapRowToEntity(ResultSet rs) throws SQLException {
        // CORREÇÃO: Mapeando o Cliente e o Endereço a partir do ResultSet do JOIN
        
        // 1. Criar o objeto Address
        Address address = new Address();
        address.setId(rs.getLong("id_address"));
        address.setStreet(rs.getString("street"));
        address.setNumber(rs.getString("number"));
        address.setDistrict(rs.getString("district"));
        address.setCity(rs.getString("city"));
        address.setZipCode(rs.getString("zip_code"));
        address.setCountry(rs.getString("country"));

        // 2. Criar o objeto Client
        Client client = new Client();
        // CORREÇÃO: Usando a coluna correta "id_client"
        client.setId(rs.getLong("id_client")); 
        client.setFirstName(rs.getString("first_name"));
        client.setLastName(rs.getString("last_name"));
        client.setTelephone(rs.getString("telephone"));
        client.setEmail(rs.getString("email"));
        
        // 3. Associar o Endereço ao Cliente
        client.setAddress(address);

        return client;
    }

    @Override
    protected void mapEntityToSaveStatement(Client entity, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, entity.getFirstName());
        pstmt.setString(2, entity.getLastName());
        
        // Adicionar uma verificação para evitar NullPointerException
        if (entity.getAddress() == null || entity.getAddress().getId() == null) {
            throw new SQLException("O cliente deve ter um endereço com ID válido para ser salvo.");
        }
        pstmt.setLong(3, entity.getAddress().getId());
        
        pstmt.setString(4, entity.getTelephone());
        pstmt.setString(5, entity.getEmail());
    }

    @Override
    protected void mapEntityToUpdateStatement(Client entity, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, entity.getFirstName());
        pstmt.setString(2, entity.getLastName());
        
        if (entity.getAddress() == null || entity.getAddress().getId() == null) {
            throw new SQLException("O cliente deve ter um endereço com ID válido para ser atualizado.");
        }
        pstmt.setLong(3, entity.getAddress().getId());

        pstmt.setString(4, entity.getTelephone());
        pstmt.setString(5, entity.getEmail());
        pstmt.setLong(6, entity.getId()); 
    }

    @Override
    protected void setGenerateId(Client entity, Long id) {
        entity.setId(id);
    }

    public void saveAnimalsForClient(Client entity) throws SQLException {
        Objects.requireNonNull(entity, "Client não pode ser nulo");

        if (entity.getAnimals() == null || entity.getAnimals().isEmpty()) {
            // Se não há animais para salvar, a operação é concluída com sucesso sem ir ao banco.
            return;
        }

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            // 1. Inicia a transação
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(getSaveAnimalSql(), PreparedStatement.RETURN_GENERATED_KEYS)) {
                for (Animal a : entity.getAnimals()) {
                    // Validações de nulidade para garantir a integridade dos dados antes do batch.
                    Objects.requireNonNull(a.getSpecie(), "A espécie do animal não pode ser nula. Animal: " + a.getName());

                    pstmt.setString(1, a.getName());
                    pstmt.setObject(2, a.getBirthDate());
                    pstmt.setString(3, String.valueOf(a.getSex()));
                    pstmt.setLong(4, entity.getId());
                    pstmt.setLong(5, a.getSpecie().getId());

                    // 2. Adiciona o statement ao lote
                    pstmt.addBatch();
                }

                // 3. Executa todos os statements em lote
                pstmt.executeBatch();

                // Recupera todos os IDs gerados e os atribui de volta aos objetos
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    for (Animal a : entity.getAnimals()) {
                        if (generatedKeys.next()) {
                            a.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            }
            
            // 4. Se tudo deu certo, confirma a transação
            conn.commit();

        } catch (SQLException e) {
            // 5. Se algo deu errado, reverte a transação
            if (conn != null) {
                System.err.println("Erro ao salvar animais, revertendo a transação.");
                conn.rollback();
            }
            // Propaga a exceção para a camada superior saber do erro
            throw e;
        } finally {
            // Garante que o auto-commit seja restaurado e a conexão fechada
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}