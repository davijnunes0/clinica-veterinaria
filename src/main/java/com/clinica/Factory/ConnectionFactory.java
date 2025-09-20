// Define o pacote onde a classe está localizada, ajudando na organização do projeto.
package com.clinica.Factory;

// Importa as classes necessárias do Java para trabalhar com arquivos e banco de dados.
import java.io.IOException;         // Para tratar erros de leitura/escrita de arquivos.
import java.io.InputStream;         // Para ler dados de um arquivo como um fluxo de bytes.
import java.sql.Connection;         // Representa a conexão com o banco de dados.
import java.sql.DriverManager;      // Gerencia os drivers JDBC e estabelece as conexões.
import java.sql.SQLException;       // Para tratar erros relacionados ao banco de dados (SQL).
import java.util.Properties;        // Para carregar e manipular arquivos de configuração (.properties).

/**
 * Esta classe implementa o padrão de projeto "Factory".
 * Sua única responsabilidade é criar e fornecer conexões de banco de dados
 * de uma forma centralizada e pré-configurada.
 */
public class ConnectionFactory {

    // Declara um objeto `Properties` que armazenará as configurações lidas do arquivo.
    // `private`: Só pode ser acessado de dentro desta classe.
    // `static`: Pertence à classe, não a uma instância. Haverá apenas UM objeto `properties` para toda a aplicação.
    // `final`: A referência ao objeto `properties` não pode ser alterada após a sua inicialização.
    private static final Properties properties = new Properties();

    // --- Bloco de Inicialização Estático ---
    // Este código é executado APENAS UMA VEZ, no exato momento em que a classe 
    // `ConnectionFactory` é carregada pela primeira vez na memória pela JVM (Java Virtual Machine).
    static {
        // Usa o 'try-with-resources'. O `InputStream` (fluxo de dados do arquivo) 
        // será fechado automaticamente no final do bloco, mesmo que ocorra um erro. Isso evita vazamento de recursos.
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties")) {
            
            // Verificação crucial. Se o arquivo "database.properties" não for encontrado no classpath,
            // o método `getResourceAsStream` retornará `null`.
            if (input == null) {
                // Imprime uma mensagem de erro clara no console.
                System.out.println("Desculpe, não foi possível encontrar o arquivo database.properties");
                // Lança uma exceção para indicar que o arquivo não foi encontrado.
                throw new IOException("Arquivo de propriedades não encontrado.");
            }
            
            // Se o arquivo foi encontrado, este método carrega os dados do arquivo (no formato chave=valor)
            // para dentro do nosso objeto `properties`, que fica na memória.
            properties.load(input);

        } catch (IOException ex) {
            // Se ocorrer qualquer erro de I/O (Input/Output) durante a leitura do arquivo 
            // (ex: arquivo corrompido, falta de permissão), ele será capturado aqui.
            
            // Imprime o "stack trace" do erro no console para ajudar na depuração.
            // Em uma aplicação real, aqui usaríamos um framework de log (como SLF4J/Logback).
            ex.printStackTrace();
            
            // Lança uma `RuntimeException`. Isso é uma estratégia "fail-fast" (falhar rápido).
            // Se a aplicação não consegue ler a configuração do banco, ela não pode funcionar.
            // É melhor que ela pare imediatamente com um erro claro do que continuar executando de forma instável.
            throw new RuntimeException("Erro ao carregar as propriedades do banco de dados.", ex);
        }
    }
    
    /**
     * Método público e estático que o restante da aplicação usará para obter uma conexão com o banco de dados.
     * @return um objeto Connection pronto para uso.
     * @throws SQLException se a conexão com o banco de dados falhar (ex: usuário/senha inválidos, banco offline).
     */
    public static Connection getConnection() throws SQLException {
        // Usa o DriverManager do JDBC para criar uma nova conexão.
        // O método `getProperty` busca os valores (URL, usuário e senha) que foram carregados
        // do arquivo .properties e os passa para o DriverManager.
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.user"),
            properties.getProperty("db.password")
        );
    }
}