package com.clinica.DataAcessObject;

import java.util.List;
import java.util.Optional;

/**
 * Interface DAO Genérica que define as operações crud PADRÃO
 * 
 * @param <T> O tipo de entidade a ser gerenciada.
 * @param <K> O tipo de chave primária (ID) da entidade.
 */

public interface GenericDatabase<T,K> {
    
    /**
     * Salva uma nova entidade no banco de dados.
     * @param entity A entidade a ser salva.
     * @return A entidade salva (Geralmente com o ID preenchido.)
     */
     T save(T entity);

     /**
      * Busca uma entidade pela chave primária.
      * @param id A chave primária da entidade.
      * @return um Optional contendo a entidade encontrada,ou o vazia caso contrário.
      */
      Optional<T> findById(K id);


      /**
       * Retorna toas as entidades do tipo T.
       * @return Uma Lista com todas as entidades.
       */
      List<T> findAll();

      /**
       * Atualiza uma entidade existente no banco de dados.
       * @param entity A entidade a ser atualizada.
       * @return A entidade atu
       */
       T update(T entity);

       /**
        * Deleta uma entidade pela sua chave primária.
        * @param id id A chave primária a ser deletada.
        */
       void deleteById(K id);

}
