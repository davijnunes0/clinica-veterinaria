package com.clinica.DataAcessObject;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.clinica.Factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbsctractDatabase<T,K> implements GenericDatabase<T,K>{
    
    // Métodos abstratos, ou seja, métodos que serão implementandos pela classe que implementar essa interface.
    protected abstract String getFindByIdSql();
    protected abstract String getFindAllSql();
    protected abstract String getSaveSql();
    protected abstract String getUpdateSql();
    protected abstract String getDeleteByIdSql();
    protected abstract T mapRowToEntity(ResultSet rs) throws SQLException;
    protected abstract void mapEntityToSaveStatement(T entity,PreparedStatement pstmt) throws SQLException;
    protected abstract void mapEntityToUpdateStatement(T entity,PreparedStatement pstmt) throws SQLException;
    protected abstract void setGenerateId(T entity,Long id);

    // MÉTODOS CRUD CONCRETOS ---:
    @Override
    public T save(T entity){
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(getSaveSql(),Statement.RETURN_GENERATED_KEYS)){
            
            mapEntityToSaveStatement(entity, pstmt);
            pstmt.executeUpdate();
            
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    setGenerateId(entity, generatedKeys.getLong(1));
                }
            } 
        
        }catch(SQLException exception){
            throw new RuntimeException("Error to save entity: " + exception.getMessage());
        }

        return entity;
    }


    // Read (By id:)
    @Override
    public Optional<T> findById(K id){
        T entity = null;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(getFindByIdSql())){

                pstmt.setObject(1, id);

                try(ResultSet rs = pstmt.executeQuery()){
                    if(rs.next()){
                        entity = mapRowToEntity(rs);
                    }
                }
            }catch(SQLException e){
                     throw new RuntimeException("Erro ao buscar entidade por ID: " + id, e);
            }
            return Optional.ofNullable(entity);

    }
    
    // Read (By All)
    @Override
    public List<T> findAll(){
        List<T> entities  = new ArrayList<T>();
        try(
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getFindAllSql())
        ){

            while(rs.next()){
                entities.add(mapRowToEntity(rs));
            }
        }catch(SQLException e){
                throw new RuntimeException("Erro ao buscar todas as entidades", e);
        }

        return entities;
    }

    // UPDATE
    @Override
    public T update(T entity){
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(getUpdateSql())){
            
            mapEntityToUpdateStatement(entity, pstmt);
            int affectedRows = pstmt.executeUpdate();

            if(affectedRows  == 0 ){
                throw new SQLException("Falha ao atualizar a entidade, nenhuma linha afetada.");
            }
        }catch(SQLException e){
            throw new RuntimeException("\"Erro ao atualizar entidade: " + entity, e);
        }

        return entity;
    }

    @Override
    // DELETE
    public void deleteById(K id){
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(getDeleteByIdSql())){
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Erro ao deletar entidade por ID: " + id,e);
        }
    }

}
