
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.clinica.Factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {

    @Test
    @DisplayName("Deve estabelecer uma conexão válida com o banco de dados (sem lambda)")
    void shouldEstablishValidDatabaseConnectionWithoutLambda() {
        
        try {
            // A instrução 'try-with-resources' garante que a conexão será fechada ao final do bloco.
            try (Connection conn = ConnectionFactory.getConnection()) {
                
                // Se o código chegar aqui, a conexão foi obtida sem erros.
                // Agora, fazemos as verificações para garantir que o objeto de conexão é válido.
                
                System.out.println("Conexão obtida com sucesso. Verificando o estado da conexão...");

                // 1. Garante que o objeto de conexão não é nulo.
                assertNotNull(conn, "A conexão não deve ser nula.");
                
                // 2. Garante que a conexão está aberta.
                assertFalse(conn.isClosed(), "A conexão deve estar aberta.");
                
                System.out.println("Teste passou: A conexão é válida e está aberta.");
            }
            
        } catch (SQLException e) {
            
            // Se o método ConnectionFactory.getConnection() lançar uma SQLException,
            // o código cairá neste bloco 'catch'.
            // Isso significa que o nosso teste falhou.
            
            // O método fail() do JUnit força o teste a falhar e exibe uma mensagem.
            // Passamos a exceção original 'e' para que o relatório do JUnit mostre a causa do erro.
            fail("Falha ao conectar ao banco de dados. Uma SQLException foi lançada.", e);
        }
    }
}