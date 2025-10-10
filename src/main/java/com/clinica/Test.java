package com.clinica;

// CONCEITO: Esta classe é a "planta" ou o "molde" para criar objetos do tipo Pessoa.
// Ela define que toda Pessoa terá uma propriedade (campo) para armazenar um nome.
class Pessoa {
    public String nome;

    // CONCEITO: Este é o "construtor". É a "linha de montagem" que é chamada
    // toda vez que usamos a palavra-chave 'new Pessoa(...)'. Ele pega o nome
    // fornecido e o armazena dentro do novo objeto que está sendo criado.
    public Pessoa(String nome) { 
        this.nome = nome; 
    }
}

public class Test {

    /**
     * CONCEITO: Este método demonstra o comportamento da passagem de parâmetros de objeto.
     * @param p - Quando o método é chamado, 'p' se torna uma NOVA VARIÁVEL LOCAL que existe apenas aqui dentro.
     * O valor que 'p' recebe é uma CÓPIA da referência (do endereço) que foi passada.
     * Neste momento, 'p' e a variável original ('minhaPessoa') apontam para o MESMO objeto na memória.
     */
    public static void modificarObjeto(Pessoa p) {
        
        // --- 1ª AÇÃO DENTRO DO MÉTODO: MODIFICANDO O ESTADO DO OBJETO ---
        // CONCEITO: Esta linha usa a cópia da referência ('p') para encontrar o objeto original na memória.
        // Em seguida, ela altera o ESTADO INTERNO daquele objeto, trocando o valor do campo 'nome'.
        // Como só existe um objeto sendo apontado, esta mudança é permanente e visível de fora do método.
        // É o equivalente a usar a cópia do endereço para ir até a casa e pintar a porta.
        p.nome = "Maria"; 

        Pessoa person = new Pessoa("Davi");
        
        
        // --- 2ª AÇÃO DENTRO DO MÉTODO: REATRIBUINDO A REFERÊNCIA LOCAL ---
        // CONCEITO: Um objeto Pessoa completamente NOVO é criado na memória (o objeto 'Carlos').
        // A variável LOCAL 'p' deixa de apontar para o objeto original ('Ana'/'Maria') e agora
        // passa a apontar para este novo objeto 'Carlos'.
        //
        // PONTO CRUCIAL: Esta reatribuição afeta APENAS a variável local 'p'.
        // A variável original no método 'main' (`minhaPessoa`) não é afetada e continua
        // firmemente apontando para o objeto original.
        // É o equivalente a rasgar a cópia do endereço e anotar um endereço novo no lugar.
        // O papel original na mesa do 'main' não é tocado.
        p = person; 
    
    } // CONCEITO: O método termina. A variável 'p' é destruída. O objeto 'Carlos', para o qual 'p'
      // apontava, agora está 'órfão' (sem referências) e será eventualmente removido pelo Garbage Collector.

    
    public static void main(String[] args) {
        // CONCEITO: O ponto de entrada do nosso programa.

        // INÍCIO
        // CONCEITO: Um objeto Pessoa é criado na memória (a "casa" Ana).
        // A variável 'minhaPessoa' é criada e armazena a referência (o "endereço") para este objeto.
        Pessoa minhaPessoa = new Pessoa("Ana");
        
        // CONCEITO: Imprime o valor atual do campo 'nome' do objeto apontado por 'minhaPessoa'.
        // A saída aqui será "Antes: Ana".
        System.out.println("Antes: " + minhaPessoa.nome);

        // CONCEITO: O método 'modificarObjeto' é chamado. O valor da referência contido
        // em 'minhaPessoa' é COPIADO para o parâmetro 'p' do método.
        modificarObjeto(minhaPessoa);

        // FIM
        // CONCEITO: Esta linha é executada APÓS o método 'modificarObjeto' ter terminado.
        // Ela olha para a variável 'minhaPessoa', que NUNCA MUDOU e ainda aponta para o objeto original.
        // Em seguida, ela acessa o campo 'nome' DAQUELE objeto. Como a 1ª Ação do método
        // alterou o estado daquele objeto para "Maria", a saída aqui será "Depois: Maria".
        System.out.println("Depois: " + minhaPessoa.nome);
    }
}