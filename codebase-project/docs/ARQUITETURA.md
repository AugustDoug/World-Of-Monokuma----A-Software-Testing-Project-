# 🏗️ Arquitetura Técnica e Padrões de Design

## 📌 Sumário Executivo

O projeto **World of Monokuma** implementa uma arquitetura **Model-View-Controller (MVC)** com separação clara de responsabilidades entre:

- **Model (Game):** Lógica de negócio pura
- **View (GameGUI):** Apresentação e componentes visuais
- **Controller (GameGUI):** Orquestração e processamento de eventos

---

## 🏛️ Padrões de Design Utilizados

### 1. Model-View-Controller (MVC)

**Propósito:** Separar lógica de apresentação da lógica de negócio

**Implementação:**

```
┌──────────────────────────────────────────┐
│  MODEL (Game)                            │
│  ├─ mapa: int[][]                       │
│  ├─ x, y: posição                       │
│  └─ movePlayer(), venceu()              │
│  Responsabilidade: Lógica Pura           │
│  Acoplamento: Nenhum ✅                  │
└──────────┬───────────────────────────────┘
           │
           │ referencia
           ▼
┌──────────────────────────────────────────┐
│  CONTROLLER (GameGUI)                    │
│  ├─ game: Game                          │
│  ├─ processarComando()                  │
│  ├─ executarMovimento()                 │
│  └─ atualizarMapa()                     │
│  Responsabilidade: Orquestração          │
│  Acoplamento: Forte ao Model ✅          │
└──────────┬───────────────────────────────┘
           │
           │ gerencia
           ▼
┌──────────────────────────────────────────┐
│  VIEW (GameGUI - Componentes)            │
│  ├─ JFrame, JPanel, JLabel              │
│  ├─ outputArea: JTextArea               │
│  ├─ inputField: JTextField              │
│  └─ mapaPanel: JPanel                   │
│  Responsabilidade: Apresentação          │
│  Acoplamento: Fraco ao Controller ✅     │
└──────────────────────────────────────────┘
```

**Benefícios:**
- ✅ Fácil de testar (Model é testável independentemente)
- ✅ Reutilizável (Model pode ser usado em CLI, Web, etc.)
- ✅ Manutenível (mudanças na UI não afetam Game)
- ✅ Escalável (adicionar novos views é simples)

**Limitações Atuais:**
- ⚠️ View e Controller estão no mesmo arquivo (GameGUI)
- ⚠️ Possibilidade de melhora: separar em GameController e GameView

---

### 2. Singleton Pattern (Implícito)

**Aplicação:** Uma única instância de `Game` por `GameGUI`

```java
public class GameGUI extends JFrame {
    public Game game;  // uma única instância durante lifetime da GUI
    
    public GameGUI() {
        game = new Game();  // criada uma única vez
        // game é reusada para toda interação
    }
}
```

**Benefício:** Garante estado único e consistente

---

### 3. Observer Pattern (Evento/Listener)

**Aplicação:** Listeners do Swing para entrada do usuário

```java
inputField.addActionListener(e -> {
    String comando = inputField.getText();
    inputField.setText("");
    executarMovimento(processarComando(comando));
});
```

**Fluxo:**
```
Usuario digita + Enter
    ↓
JTextField emite ActionEvent
    ↓
Listener (lambda) é chamado
    ↓
processarComando() e executarMovimento()
```

**Implementação de Listener Customizado (possível melhora):**

```java
// Versão melhorada (não implementada):
public interface GameEventListener {
    void onMove(String direction);
    void onVictory();
    void onError(String message);
}
```

---

### 4. State Pattern (Implícito)

**Aplicação:** Estado posição do jogador

```java
public class Game {
    public int x;  // estado
    public int y;  // estado
    
    public boolean movePlayer(String direction) {
        // modifica estado (x, y)
        x = newX;
        y = newY;
        return true;
    }
}
```

**Transições:**
```
Estado: (x, y)
  [0,0] --movePlayer("east")--> [0,1]
  [0,1] --movePlayer("south")--> [1,1]
  [1,1] --movePlayer("south")--> [2,1]
  ... até [4,4] → VITÓRIA
```

---

### 5. Strategy Pattern (Validação de Movimento)

**Aplicação:** Diferentes validações aplicadas em sequência

```java
public boolean movePlayer(String direction) {
    // Strategy 1: Validar nova posição
    int newX = /* calcular */;
    
    // Strategy 2: Validar limites
    if (newX < 0 || newX >= mapa.length) return false;
    
    // Strategy 3: Validar parede
    if (mapa[newX][newY] == 1) return false;
    
    // Strategy 4: Aprovar movimento
    x = newX;
    return true;
}
```

**Diferentes estratégias combinadas:** Composição de validadores

---

### 6. Template Method Pattern (Processamento de Comando)

**Aplicação:** Fluxo fixo com pontos de extensão

```java
public String processarComando(String input) {
    // 1. Validar entrada
    if (input == null || input.trim().isEmpty()) {
        print("❌ Comando vazio!");
        return null;
    }
    
    // 2. Parsear entrada
    String[] partes = input.trim().toLowerCase().split(" ");
    
    // 3. Validar formato
    if (partes.length != 2) {
        print("❌ Comando inválido!");
        mostrarAjuda();
        return null;
    }
    
    // 4. Validar tipo
    if (!partes[0].equals("go")) {
        print("❌ Use o comando 'go'");
        mostrarAjuda();
        return null;
    }
    
    // 5. Validar valor
    if (!direcaoValida(partes[1])) {
        print("❌ Direção inválida!");
        mostrarAjuda();
        return null;
    }
    
    // 6. Aprovar
    return partes[1];
}
```

**Template:** Validação → Processamento → Retorno

---

### 7. Command Pattern (Processamento de Entrada)

**Aplicação:** Encapsular requisição de movimento

```
User Input ("go north")
    ↓
processarComando() - valida
    ↓
Command Object: direção = "north"
    ↓
executarMovimento(direcao) - executa
    ↓
game.movePlayer(direcao) - aplica
    ↓
atualizarMapa() - renderiza
```

**Benefício:** Separa geração do comando de sua execução

---

## 🔄 Fluxo de Dados Detalhado

### 1. Inicialização

```
┌─────────────────────────────────┐
│ Program Start                   │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│ Main.main()                     │
│ (stack frame: main)             │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│ new GameGUI()                   │
│ (chama construtor)              │
└────────────┬────────────────────┘
             │
         ┌───┴────────────────────┐
         │                        │
         ▼                        ▼
    ┌─────────────┐        ┌─────────────┐
    │ new Game()  │        │ initGUI()   │
    │ x=0, y=0    │        │ cria JFrame │
    │ mapa[5][5]  │        │ cria JPanel │
    └─────────────┘        │ ...         │
         │                 └─────────────┘
         │
         └──────────────────────────┘
                    │
                    ▼
        ┌──────────────────────────┐
        │ setVisible(true)         │
        │ Janela aparece na tela   │
        └──────────────────────────┘
```

### 2. Loop de Entrada

```
┌─────────────────────────────────────────────────────┐
│ LOOP PRINCIPAL (Aguardando Entrada)                 │
│                                                     │
│ Estado: JFrame ativo, listeners registrados        │
└─────────────┬───────────────────────────────────────┘
              │
              │ Usuário digita e pressiona ENTER
              │
              ▼
    ┌─────────────────────────────┐
    │ InputField ActionListener   │
    │ - getText()                 │
    │ - setText("")               │
    └────────┬────────────────────┘
             │
             ▼
    ┌──────────────────────────────────┐
    │ executarMovimento(               │
    │   processarComando(input)        │
    │ )                                │
    └────────┬─────────────────────────┘
             │
        ┌────┴────────┬─────────────┐
        │             │             │
        ▼             ▼             ▼
    ┌────────┐  ┌────────┐  ┌────────┐
    │ Input  │  │ Game   │  │ Output │
    │Process │  │Execute │  │Render  │
    └────────┘  └────────┘  └────────┘
        │             │             │
        └────────┬────┴────────┬────┘
                 │             │
                 ▼             ▼
        ┌──────────┐    ┌──────────────┐
        │ Validar  │    │ Atualizar    │
        │ Comando  │    │ atualizarMap │
        │ Direção  │    │ atualizarGui │
        └──────────┘    └──────────────┘
        
        ┌─────────────────────────────────────┐
        │ Verifica Vitória: game.venceu()?    │
        │ - SIM: print("🎉 Você venceu!")     │
        │ - NÃO: continua esperando entrada   │
        └─────────────────────────────────────┘
```

### 3. Validação em Camadas

```
┌────────────────────────────────────┐
│ Input (raw string)                 │
│ Exemplo: "  Go  NORTH  "          │
└────────┬───────────────────────────┘
         │
    ┌────▼──────────────────────┐
    │ Camada 1: GameGUI         │
    │ print("> " + input)       │
    │ outputArea.append()       │
    └────┬──────────────────────┘
         │ validarNaoVazio()
         │ input != null && !trim().isEmpty()
         ▼
    ┌────────────────────────────┐
    │ Camada 2: Parse            │
    │ split(" ")                 │
    │ toLowerCase()              │
    │ ["go", "north"]            │
    └────┬──────────────────────┘
         │ validarTamanho()
         │ partes.length == 2
         ▼
    ┌────────────────────────────┐
    │ Camada 3: Comando          │
    │ partes[0] == "go"          │
    └────┬──────────────────────┘
         │ validarComando()
         │
         ▼
    ┌────────────────────────────┐
    │ Camada 4: Direção          │
    │ partes[1] in              │
    │ {north, south, east, west}│
    └────┬──────────────────────┘
         │ validarDirecao()
         │
         ▼
    ┌────────────────────────────┐
    │ Camada 5: Execução         │
    │ game.movePlayer(direcao)   │
    │ validaLimites()            │
    │ validaParede()             │
    └────────────────────────────┘
         │
         ▼
    ┌────────────────────────────┐
    │ Output (comando validado)  │
    │ Return: String (direção)   │
    └────────────────────────────┘
```

---

## 🧩 Componentes Swing Utilizados

```
┌─────────────────────────────────────────────┐
│           GameGUI extends JFrame            │
├─────────────────────────────────────────────┤
│                                             │
│  BorderLayout: organiza container principal│
│                                             │
│  CENTER: JPanel (mapaPanel) + GridLayout   │
│          └─ 5x5 Grid de JLabel (células)  │
│                                             │
│  EAST: JScrollPane                         │
│        └─ JTextArea (outputArea)          │
│           [mensagens do jogo]             │
│                                             │
│  SOUTH: JTextField (inputField)            │
│         [entrada do usuário]               │
│                                             │
└─────────────────────────────────────────────┘
```

**Hierarquia:**
```
JFrame (janela principal)
├─ BorderLayout (gestor de layout)
├─ JPanel (CENTER) - mapaPanel
│  ├─ GridLayout (5x5)
│  └─ JLabel[5][5] (células)
│     ├─ BorderFactory.createLineBorder()
│     ├─ setBackground(Color)
│     ├─ setIcon(ImageIcon) - player
│     └─ setText/setFont
├─ JScrollPane (EAST)
│  └─ JTextArea (outputArea)
│     └─ setEditable(false)
└─ JTextField (SOUTH) (inputField)
   └─ ActionListener
```

---

## 🔐 Encapsulamento e Visibilidade

### Classe Game

```java
public class Game {
    // Atributos public (simplificar para demo)
    public int[][] mapa;      // ⚠️ Exposto (poderia ser private + getter)
    public int x;             // ⚠️ Exposto
    public int y;             // ⚠️ Exposto
    
    // Métodos public (correto)
    public Game()
    public boolean movePlayer(String direction)
    public int getX()
    public int getY()
    public int[][] getMapa()
    public boolean venceu()
}
```

**Recomendação de Melhoria:**
```java
public class Game {
    private int[][] mapa;  // encapsulado
    private int x;
    private int y;
    
    // getters públicos (somente leitura)
    public int getX() { return x; }
    public int getY() { return y; }
    public int[][] getMapa() { return mapa; }
    
    // setters controlados (se necessário)
    // mutadores ainda via movePlayer()
}
```

### Classe GameGUI

```java
public class GameGUI extends JFrame {
    private JTextArea outputArea;           // private ✅
    public Game game;                       // public (referência)
    private JTextField inputField;          // private (acesso via getter)
    private JPanel mapaPanel;              // private ✅
    private JLabel[][] mapa;               // private ✅
    private ImageIcon playerIcon;          // private ✅
    
    // Métodos
    public GameGUI()                        // construtor public
    public boolean executarMovimento()      // public
    public boolean venceu()                 // public
    public String processarComando()        // public
    private boolean direcaoValida()         // private (helper)
    private void mostrarAjuda()             // private (helper)
    private void atualizarMapa()            // private (helper)
    private void print()                    // private (helper)
    private void configurarInput()          // private (setup)
    public JTextField getInputField()       // public (para testes)
}
```

**Boas práticas aplicadas:**
- ✅ Métodos helpers são `private`
- ✅ Campos são `private` (exceto game e inputField)
- ✅ Getters fornecidos para testes

---

## 📈 Complexidade Algorítmica

### movePlayer()

```
Operações:
├─ Validar limites: O(1) - comparações simples
├─ Acessar mapa: O(1) - acesso direto array
└─ Atualizar posição: O(1) - atribuição

Complexidade Total: O(1) ✅ Muito eficiente
```

### processarComando()

```
Operações:
├─ String.split(): O(n) onde n = comprimento da string
├─ toLowerCase(): O(n)
├─ equals(): O(m) onde m = comprimento da palavra
└─ Validações: O(1)

Complexidade Total: O(n) onde n = tamanho do input
                      (aceitável para entrada de usuário)
```

### atualizarMapa()

```
Operações:
├─ Loop externo (5 linhas): O(5) = O(1)
├─ Loop interno (5 colunas): O(5) = O(1)
└─ Operações por célula: O(1)

Complexidade Total: O(25) = O(1) ✅ Muito eficiente
                    (mapa fixo 5x5)
```

**Resumo:** Todas as operações são O(1) → perfeitamente escalável

---

## 🧪 Testabilidade

### Game.java - Altamente Testável

```java
// Exemplo de teste
@Test
void testMovePlayerNorth() {
    Game game = new Game();  // cria instância
    game.x = 1;
    game.y = 1;
    
    boolean result = game.movePlayer("north");
    
    assertEquals(0, game.x);
    assertEquals(1, game.y);
    assertTrue(result);
}
```

**Por quê é testável:**
- ✅ Sem dependências externas
- ✅ Estado público acessível
- ✅ Métodos puros (dado entrada, saída previsível)

### GameGUI.java - Moderadamente Testável

```java
// Teste de processamento de comando
@Test
void testProcessarComandoValido() {
    GameGUI gui = new GameGUI();
    String result = gui.processarComando("go north");
    assertEquals("north", result);
}

// Requer:
// - Instanciar GUI (cria JFrame - pode ser pesado)
// - Acessar outputArea (privada - usar modificadores ou refatorar)
```

**Desafios:**
- ⚠️ GUI usa componentes Swing (pesado em testes)
- ⚠️ Muitos atributos privados limitam teste direto
- ⚠️ Melhor refatorar separá View de Controller

---

## 🔄 Ciclo de Vida da Aplicação

### Inicialização (Startup)

```
1. JVM inicia
2. main(String[] args) é chamado
3. new GameGUI() é executado
4. GameGUI() {
     - game = new Game()         [Game criado]
     - loadIcon()                [Recurso carregado]
     - initGUI()                 [Components criados]
     - setVisible(true)          [Janela exibida]
   }
5. main() encerra (não é blocking)
6. Listeners do Swing gerenciam aplicação
```

### Operação Normal (Runtime)

```
Loop Infinito (gerenciado por Swing):
  - Aguardando eventos do usuário
  - Executando listeners
  - Redraw quando necessário
  - Event Dispatch Thread (EDT) da JVM
```

### Encerramento (Shutdown)

```
1. Usuário clica botão X
2. JFrame.EXIT_ON_CLOSE acionado
3. JVM encerra
4. Aplicação fecha
```

**Não há recursos a liberar (sem I/O, arquivos, etc.)**

---

## 🚀 Possíveis Extensões Arquiteturais

### 1. Implementar MVC Completo

```
game/
  ├─ Game.java (Model - sem mudanças)
  ├─ GameController.java (novo)
  └─ GameView.java (novo)

Controllers/
  └─ GameController.java
     ├─ processarComando()
     ├─ executarMovimento()
     └─ verificarVitoria()

Views/
  ├─ GameGUIFrame.java
  ├─ GameMapPanel.java
  ├─ GameConsolePanel.java
  └─ GameInputPanel.java
```

### 2. Implementar Observer Customizado

```java
interface GameListener {
    void onPlayerMove(int x, int y);
    void onGameWin();
    void onInvalidMove();
}

class Game {
    private List<GameListener> listeners = new ArrayList<>();
    
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    
    public boolean movePlayer(String direction) {
        // ...lógica
        if (movimento_válido) {
            notifyListeners();  // avisa observers
        }
    }
    
    private void notifyListeners() {
        listeners.forEach(l -> l.onPlayerMove(x, y));
    }
}
```

### 3. Factory Pattern para Comandos

```java
interface Command {
    boolean execute(Game game);
}

class MoveCommand implements Command {
    String direction;
    
    public boolean execute(Game game) {
        return game.movePlayer(direction);
    }
}

class CommandFactory {
    public static Command createCommand(String input) {
        if (input.startsWith("go ")) {
            String direction = input.substring(3);
            return new MoveCommand(direction);
        }
        return null;
    }
}
```

### 4. Strategy Pattern para Validadores

```java
interface Validator {
    boolean validate(Game game, String direction);
}

class LimitValidator implements Validator {
    public boolean validate(Game game, String direction) {
        // valida limites
    }
}

class WallValidator implements Validator {
    public boolean validate(Game game, String direction) {
        // valida parede
    }
}

class Game {
    private List<Validator> validators = List.of(
        new LimitValidator(),
        new WallValidator()
    );
    
    public boolean movePlayer(String direction) {
        for (Validator v : validators) {
            if (!v.validate(this, direction)) return false;
        }
        // executar movimento
        return true;
    }
}
```

---

## 📊 Comparação: Arquitetura Atual vs. Ideal

| Aspecto | Atual | Ideal |
|---------|-------|-------|
| **Separação MVC** | Parcial (View+Controller junto) | Completa (View e Controller separados) |
| **Testabilidade** | Média | Alta |
| **Reutilização** | Alta (Model é reutilizável) | Muito Alta |
| **Extensibilidade** | Média | Alta |
| **Encapsulamento** | Bom | Excelente |
| **Padrões de Design** | Alguns aplicados | Mais padrões |
| **Acoplamento** | Aceitável | Baixo |
| **Coesão** | Boa | Excelente |

---

## ✅ Conclusão da Arquitetura

O projeto demonstra:

1. **Bom Design Base:** MVC bem aplicado
2. **Separação Lógica-Apresentação:** Excelente encapsulamento
3. **Código Testável:** Model é facilmente testável
4. **Padrões Reconhecíveis:** Observer, Strategy, Template Method
5. **Sala para Crescimento:** Oportunidades de refatoração para patterns avançados

**Pontuação Geral:** 7.5/10 ⭐⭐⭐⭐⭐⭐⭐⭐
- Excelente para projeto educacional
- Bom ponto de partida para refatoração
- Demonstra compreensão sólida de arquitetura

