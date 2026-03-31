# 📚 Documentação - World of Monokuma

## 📋 Índice
1. [Visão Geral](#visão-geral)
2. [Arquitetura do Projeto](#arquitetura-do-projeto)
3. [Fluxo da Aplicação](#fluxo-da-aplicação)
4. [Estrutura das Classes](#estrutura-das-classes)
5. [Descrição Detalhada das Classes](#descrição-detalhada-das-classes)
6. [Fluxo de Dados](#fluxo-de-dados)
7. [Guia de Uso](#guia-de-uso)
8. [Tecnologias Utilizadas](#tecnologias-utilizadas)

---

## 🎮 Visão Geral

**World of Monokuma** é um jogo educacional desenvolvido em Java que permite ao jogador navegar por um mapa 2D usando comandos de texto. O objetivo é alcançar a posição final marcada no mapa, evitando as paredes e respeitando os limites do mundo.

### Características Principais
- Interface gráfica intuitiva usando Swing
- Sistema de movimento baseado em direções (norte, sul, leste, oeste)
- Validação de movimentos (limites do mapa e paredes)
- Feedback visual e textual ao jogador
- Sistema de vitória quando o objetivo é alcançado

---

## 🏗️ Arquitetura do Projeto

### Estrutura de Pastas
```
codebase-project/
├── src/
│   ├── main/
│   │   ├── java/st/project/
│   │   │   ├── Main.java           # Ponto de entrada
│   │   │   ├── Game.java           # Lógica do jogo
│   │   │   └── GameGUI.java        # Interface gráfica
│   │   └── resources/
│   │       └── images/
│   │           └── monoplayer.png  # Ícone do jogador
│   └── test/
│       └── java/st/project/
│           ├── MainTest.java
│           ├── GameTest.java
│           └── GameGUITest.java
├── pom.xml                         # Configuração Maven
└── DOCUMENTACAO.md                 # Este arquivo

```

### Padrão de Arquitetura: MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────┐
│           CAMADA DE APRESENTAÇÃO            │
│                 (GameGUI)                   │
│    - Interface Gráfica (JFrame, JPanel)    │
│    - Renderização do Mapa                   │
│    - Exibição de Mensagens                  │
└──────────────────┬──────────────────────────┘
                   │ Interage com
                   ▼
┌─────────────────────────────────────────────┐
│          CAMADA DE CONTROLADOR              │
│                 (GameGUI)                   │
│    - Processamento de Entrada do Usuário   │
│    - Validação de Comandos                  │
│    - Execução de Movimentos                 │
└──────────────────┬──────────────────────────┘
                   │ Manipula
                   ▼
┌─────────────────────────────────────────────┐
│          CAMADA DE NEGÓCIO/LÓGICA           │
│                  (Game)                     │
│    - Mapa do Jogo                          │
│    - Posição do Jogador                     │
│    - Validação de Movimentos                │
│    - Verificação de Vitória                 │
└─────────────────────────────────────────────┘
```

---

## 🔄 Fluxo da Aplicação

### Fluxo Geral de Execução

```
┌─────────────┐
│   START     │
└──────┬──────┘
       │
       ▼
┌──────────────────────────┐
│ Main.main()              │
│ - Cria nova GameGUI()    │
│ - Define result = true   │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────────────┐
│ GameGUI Construtor (novo Game)   │
│ - Inicializa JFrame              │
│ - Carrega Mapa Gráfico           │
│ - Configura Componentes GUI      │
│ - Exibe Janela                   │
└──────┬───────────────────────────┘
       │
       ▼
┌──────────────────────────────────┐
│ Aguardando Entrada do Usuário    │
│ (ActionListener no inputField)   │
└──────┬───────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────┐
│ Usuário digita comando (ex: "go north")  │
└──────┬───────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────┐
│ processarComando(String input)           │
│ - Divide comando em partes               │
│ - Valida formato "go [direção]"          │
│ - Valida direção (south|north|east|west) │
│ - Retorna direção ou null                │
└──────┬───────────────────────────────────┘
       │
       ▼
┌────────────────────────────────────────┐
│ executarMovimento(String direcao)      │
└──────┬───────────────────────────────────┘
       │
       ▼
┌────────────────────────────────────────────┐
│ game.movePlayer(String direction)          │
│ - Calcula nova posição                     │
│ - Valida limites do mapa                   │
│ - Verifica se há parede                    │
│ - Move jogador se válido                   │
│ - Retorna true/false                       │
└──────┬───────────────────────────────────────┘
       │
       ▼
┌────────────────────────────────────────────┐
│ atualizarMapa()                            │
│ - Limpa renderização anterior              │
│ - Desenha paredes (cinza)                  │
│ - Desenha objetivo (verde)                 │
│ - Desenha jogador (ícone)                  │
└──────┬───────────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│ Verificar Vitória               │
│ venceu() == true ?              │
└──────┬──────────────────────────┘
       │
     SIM? ──────────────────┐
       │                    │
      NÃO                   ▼
       │             ┌──────────────────┐
       │             │ Exibir "Você     │
       │             │ venceu!" e fim   │
       │             └──────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│ Aguardando Nova Entrada         │
│ (volta ao início do loop)        │
└─────────────────────────────────┘
```

---

## 📦 Estrutura das Classes

### Diagrama de Classes

```
┌─────────────────────────────────────┐
│          Main (Ponto de Entrada)    │
├─────────────────────────────────────┤
│ - result: static boolean            │
├─────────────────────────────────────┤
│ + main(String[] args): void         │
│ + getIsInterface(): boolean          │
└─────────────┬───────────────────────┘
              │ cria instância
              ▼
┌─────────────────────────────────────────────┐
│         GameGUI extends JFrame              │
├─────────────────────────────────────────────┤
│ - game: Game                                │
│ - outputArea: JTextArea                     │
│ - inputField: JTextField                    │
│ - mapaPanel: JPanel                         │
│ - mapa: JLabel[][]                          │
│ - playerIcon: ImageIcon                     │
├─────────────────────────────────────────────┤
│ + GameGUI()                                 │
│ + executarMovimento(direcao): boolean       │
│ + venceu(resultado): boolean                │
│ + processarComando(input): String           │
│ - direcaoValida(d): boolean                 │
│ - mostrarAjuda(): void                      │
│ - atualizarMapa(): void                     │
│ - print(text): void                         │
│ - configurarInput(): void                   │
│ + tratarEntrada(): void                     │
│ + getInputField(): JTextField               │
└─────────────┬───────────────────────────────┘
              │ utiliza
              ▼
┌─────────────────────────────────────────┐
│            Game (Lógica)                │
├─────────────────────────────────────────┤
│ - mapa: int[][]                         │
│ - x: int (linha atual)                  │
│ - y: int (coluna atual)                 │
├─────────────────────────────────────────┤
│ + Game()                                │
│ + movePlayer(direction): boolean        │
│ + getX(): int                           │
│ + getY(): int                           │
│ + getMapa(): int[][]                    │
│ + venceu(): boolean                     │
└─────────────────────────────────────────┘
```

---

## 📖 Descrição Detalhada das Classes

### 1. **Main.java** - Ponto de Entrada

**Responsabilidade:** Iniciar a aplicação e instanciar a interface gráfica.

**Atributos:**
- `result: static boolean` - Flag indicando se a interface foi criada (inicialmente `false`)

**Métodos:**

| Método | Descrição |
|--------|-----------|
| `main(String[] args): void` | Ponto de entrada da aplicação. Cria nova instância de `GameGUI` e define `result = true` |
| `getIsInterface(): boolean` | Retorna o valor do atributo `result` |

**Fluxo:**
```java
public static void main(String [] args){
    new GameGUI();        // Instancia a interface gráfica
    result = true;        // Marca que a interface foi criada
}
```

**Observações:**
- Classe simples e direta
- Responsável apenas por inicializar a aplicação
- O `result` estático é útil para testes

---

### 2. **Game.java** - Motor do Jogo (Lógica de Negócio)

**Responsabilidade:** Gerenciar a lógica do jogo, incluindo o mapa, posição do jogador e validação de movimentos.

**Atributos:**

| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `mapa` | `int[][]` | Matriz 5x5 representando o mapa. 0=vazio, 1=parede, 2=objetivo |
| `x` | `int` | Posição linha do jogador (0-4) |
| `y` | `int` | Posição coluna do jogador (0-4) |

**Mapa Padrão:**
```
Índices:   0  1  2  3  4
       0 [ 0, 1, 0, 0, 0 ]
       1 [ 0, 1, 0, 1, 0 ]
       2 [ 0, 0, 0, 1, 0 ]
       3 [ 1, 1, 0, 0, 0 ]
       4 [ 0, 0, 0, 1, 2 ]

Legenda:
  0 = Caminho livre
  1 = Parede
  2 = Objetivo (posição: linha 4, coluna 4)
  
Posição inicial do jogador: (0, 0)
```

**Métodos:**

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `Game()` | void | Construtor. Inicializa x=0, y=0 |
| `movePlayer(String direction): boolean` | boolean | Tenta mover o jogador na direção especificada |
| `getX(): int` | int | Retorna posição linha |
| `getY(): int` | int | Retorna posição coluna |
| `getMapa(): int[][]` | int[][] | Retorna referência ao mapa |
| `venceu(): boolean` | boolean | Verifica se o jogador alcançou o objetivo |

**Fluxo de movePlayer:**

```
movePlayer(String direction)
    ↓
Copiar posição atual (x, y) para (newX, newY)
    ↓
Ajustar novo[X/Y] conforme direção:
    ├─ "north"  → newX--
    ├─ "south"  → newX++
    ├─ "west"   → newY--
    └─ "east"   → newY++
    ↓
Validar Limites:
    ├─ newX < 0? ❌ return false
    ├─ newX >= 5? ❌ return false
    ├─ newY < 0? ❌ return false
    └─ newY >= 5? ❌ return false
    ↓
Validar Parede (mapa[newX][newY] == 1)?
    └─ ❌ return false
    ↓
Atualizar Posição:
    ├─ x = newX
    ├─ y = newY
    └─ ✅ return true
```

**Exemplo de Validação:**
```java
// Se jogador está em (0,0) e tenta "west"
newX = 0
newY = -1  // menor que 0
return false  // movimento inválido
```

---

### 3. **GameGUI.java** - Interface Gráfica (View e Controller)

**Responsabilidade:** Exibir a interface gráfica, processar entrada do usuário e atualizar a visualização do jogo.

**Herança:** Estende `JFrame` (janela principal do Swing)

**Atributos:**

| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `game` | `Game` | Instância do motor do jogo |
| `outputArea` | `JTextArea` | Área de texto para mensagens |
| `inputField` | `JTextField` | Campo de entrada de comandos |
| `mapaPanel` | `JPanel` | Painel que contém a grade do mapa |
| `mapa` | `JLabel[][]` | Grade de 5x5 JLabels para renderizar mapa |
| `playerIcon` | `ImageIcon` | Ícone do jogador carregado de `/images/monoplayer.png` |

**Componentes da Janela:**

```
┌─────────────────────────────────────────┐
│         "World of Monokuma" (Título)    │
├────────────────────┬────────────────────┤
│                    │                    │
│                    │  outputArea        │
│   mapaPanel        │  (Messages)        │
│   (5x5 Grid)       │                    │
│                    │                    │
├────────────────────┴────────────────────┤
│  inputField (Digite comando aqui)       │
└─────────────────────────────────────────┘
```

**Métodos Principais:**

| Método | Retorno | Descrição |
|--------|---------|-----------|
| `GameGUI()` | void | Construtor. Inicializa GUI completa |
| `processarComando(String input): String` | String | Valida e processa entrada do usuário |
| `executarMovimento(String direcao): boolean` | boolean | Executa movimento e atualiza GUI |
| `venceu(boolean resultado): boolean` | boolean | Verifica e trata vitória |
| `direcaoValida(String d): boolean` | boolean | Valida se direção é válida |
| `mostrarAjuda(): void` | void | Exibe comandos disponíveis |
| `atualizarMapa(): void` | void | Redesenha o mapa visual |
| `print(String text): void` | void | Adiciona mensagem ao outputArea |
| `configurarInput(): void` | void | Configura listener de entrada |
| `tratarEntrada(): void` | void | Handler do ActionListener |
| `getInputField(): JTextField` | JTextField | Retorna campo de entrada (para testes) |

**Fluxo de processarComando:**

```
processarComando(String input)
    ├─ Exibe entrada com print("> " + input)
    ├─ Valida se não está vazio
    ├─ Divide em partes por espaço: [0]=comando, [1]=direção
    ├─ Verifica se tem exatamente 2 partes
    ├─ Valida se primeira parte é "go"
    │   └─ Se não: print erro e mostra ajuda
    ├─ Valida se direção está em {north, south, east, west}
    │   └─ Se não: print erro e mostra ajuda
    └─ Return direcao (se tudo OK) ou null (se erro)
```

**Fluxo de executarMovimento:**

```
executarMovimento(String direcao)
    ├─ Verifica se direcao é null → return false
    ├─ Chama game.movePlayer(direcao)
    ├─ Chama atualizarMapa()
    ├─ Se não moveu:
    │   └─ print("🚫 Não pode ir nessa direção!")
    ├─ Se moveu:
    │   └─ print("Movendo para " + direcao)
    ├─ Chama venceu(game.venceu())
    ├─ Se venceu:
    │   ├─ print("🎉 Você venceu!")
    │   └─ return true
    └─ return false/true conforme moveu
```

**Fluxo de atualizarMapa:**

```
atualizarMapa()
    ├─ Para cada célula [i][j] do mapa:
    │   ├─ Limpa ícone e texto
    │   ├─ Se mapa[i][j] == 1 → cor cinza (parede)
    │   ├─ Se mapa[i][j] == 2 → cor verde (objetivo)
    │   └─ Senão → cor branca (vazio)
    └─ Adiciona playerIcon na posição (game.getX(), game.getY())
```

**Cores e Símbolos:**
- 🚫 Parede: `Color.DARK_GRAY`
- 🎯 Objetivo: `Color.GREEN`
- ⬜ Caminho: `Color.WHITE`
- 🎮 Jogador: Ícone PNG

---

## 🔀 Fluxo de Dados

### Fluxo Completo de um Movimento

```
┌──────────────────┐
│  jogador digita  │
│   "go north"     │
└────────┬─────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ inputField.actionPerformed()        │
│ - text = "go north"                 │
│ - inputField.setText("")            │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ executarMovimento(                  │
│   processarComando("go north")      │
│ )                                   │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ processarComando("go north")        │
│ - valida format "go [direção]"      │
│ - valida direção ∈ {N,S,E,W}        │
│ → returns "north"                   │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ executarMovimento("north")          │
│ - game.movePlayer("north")          │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ Game.movePlayer("north")            │
│ - newX = x - 1                      │
│ - valida newX ≥ 0                   │
│ - valida mapa[newX][y] ≠ 1          │
│ - x = newX                          │
│ → returns true                      │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ executarMovimento continua          │
│ - atualizarMapa()                   │
│ - print("Movendo para north")       │
│ - venceu(game.venceu())             │
│ - retorna true/false                │
└─────────────────────────────────────┘
```

### Estrutura de Dados - Mapa

O jogo usa uma matriz 2D de inteiros para representar o mundo:

```
     Coluna (y) →
       0  1  2  3  4
    0 [ 0  1  0  0  0 ]
    1 [ 0  1  0  1  0 ]
    2 [ 0  0  0  1  0 ]
    3 [ 1  1  0  0  0 ]
    4 [ 0  0  0  1  P* ]
    ↑
Linha (x)

P* = objetivo (2), jogador começa em (0,0)
```

**Índices:**
- Linha (x): 0 (topo) → 4 (fundo)
- Coluna (y): 0 (esquerda) → 4 (direita)

**Valores:**
- `0` = Espaço livre passável
- `1` = Parede (obstáculo)
- `2` = Objetivo final

---

## 🎮 Guia de Uso

### Como Executar

1. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Executar a aplicação:**
   ```bash
   mvn exec:java -Dexec.mainClass="st.project.Main"
   ```
   
   Ou importar em uma IDE (IntelliJ, Eclipse) e executar `Main.java`

### Controles do Jogo

#### Comandos Válidos

| Comando | Direção | Move para |
|---------|---------|-----------|
| `go north` | ⬆ Norte | linha anterior (x-1) |
| `go south` | ⬇ Sul | próxima linha (x+1) |
| `go west` | ⬅ Oeste | coluna anterior (y-1) |
| `go east` | ➡ Leste | próxima coluna (y+1) |

#### Exemplos de Entrada

```
Entrada:  go north
Resultado: Tenta mover norte (linha - 1)

Entrada:  go east
Resultado: Tenta mover leste (coluna + 1)

Entrada:  go south
Resultado: Tenta mover sul (linha + 1)

Entrada:  INVALID
Resultado: ❌ Erro de validação
```

### Indicadores Visuais

| Elemento | Cor/Ícone | Significado |
|----------|-----------|------------|
| Parede | ⬛ Cinza escuro | Obstáculo intransponível |
| Objetivo | 🟩 Verde | Local de vitória (linha 4, coluna 4) |
| Vazio | ⬜ Branco | Caminho livre |
| Jogador | 🎮 PNG | Sua posição atual |

### Mensagens do Jogo

```
✅ Movimento bem-sucedido
→ "Movendo para [direção]"

❌ Movimento falhou (parede/limite)
→ "🚫 Não pode ir nessa direção!"

🎉 Objetivo alcançado
→ "🎉 Você venceu!"

⚠️ Comando inválido
→ "❌ Comando inválido!"
→ (mostra ajuda com comandos válidos)
```

### Objetivo do Jogo

1. Começar na posição superior esquerda (linha 0, coluna 0)
2. Navegar pelo mapa evitando paredes
3. Alcançar a posição inferior direita (linha 4, coluna 4) marcada em verde
4. Mensagem de vitória será exibida

---

## 🛠 Tecnologias Utilizadas

### Linguagem
- **Java 8+** - Linguagem de programação principal

### Framework GUI
- **Swing** - API Java para construção de interfaces gráficas
  - `JFrame` - Janela principal
  - `JPanel` - Painel para organização de componentes
  - `JTextField` - Campo de entrada de texto
  - `JTextArea` - Área de texto para mensagens
  - `JLabel` - Rótulos para células do mapa
  - `JScrollPane` - Barra de rolagem
  - `BorderFactory` - Criação de bordas
  - `BorderLayout` / `GridLayout` - Gerenciadores de layout
  - `ImageIcon` - Carregamento de imagens

### Build Tool
- **Maven 3.x** - Gerenciador de dependências e compilação
  - Configuração em `pom.xml`

### Dependências de Teste
- **JUnit 5 (Jupiter)** - Framework de testes unitários
  - Versão: 5.12.2

- **AssertJ** - Biblioteca para assertions fluentes
  - Versão: 3.27.7

### Recursos
- **Imagens:** `/src/main/resources/images/monoplayer.png`

---

## 📊 Diagrama de Sequência de um Movimento

```
jogador        inputField        GameGUI         Game           Logic
   │                │               │              │               │
   │─ digita "go north" ────────────│              │               │
   │                │               │              │               │
   │                │─────── actionPerformed ────│              │
   │                │               │              │               │
   │                │      processarComando()    │               │
   │                │     [valida comando]       │               │
   │                │              │              │               │
   │                │     executarMovimento()    │               │
   │                │              │              │               │
   │                │              │ movePlayer()──→              │
   │                │              │    [calcula, valida]         │
   │                │              │ ←──── retorna true/false    │
   │                │        atualizarMapa()     │               │
   │                │     [redesenha grid]       │               │
   │                │              │              │               │
   │                │       venceu()?             │               │
   │                │      [verifica objetivo]    │               │
   │                │              │              │               │
   │    ← message display           │              │               │
```

---

## 🧪 Testes

O projeto inclui testes unitários para cada classe:

```
test/java/st/project/
├── MainTest.java       - Testes de inicialização
├── GameTest.java       - Testes da lógica de movimentos
└── GameGUITest.java    - Testes da interface gráfica
```

### Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar teste específico
mvn test -Dtest=GameTest
```

---

## 📝 Resumo da Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                   CAMADA DE APRESENTAÇÃO                │
│                      (GameGUI)                          │
│  - Renderização de componentes Swing                    │
│  - Gerenciamento de eventos                            │
│  - Feedback visual ao usuário                          │
└─────────────┬───────────────────────────────────────────┘
              │
              │ Interage com estado através de
              │
┌─────────────▼───────────────────────────────────────────┐
│                   CAMADA DE LÓGICA                       │
│                      (Game)                             │
│  - Mapa do jogo (estado imutável)                      │
│  - Posição do jogador                                  │
│  - Validação de movimentos                            │
│  - Verificação de vitória                             │
└─────────────────────────────────────────────────────────┘

ENTRADA → [Processamento] → ESTADO → [Renderização] → SAÍDA
```

---

## ✨ Fluxo Completo Detalhado

### 1. Inicialização
- `Main.main()` é chamado
- Cria instância de `GameGUI`
- `GameGUI` construtor:
  1. Instancia `Game` (inicializa mapa e posição)
  2. Carrega ícone do jogador
  3. Cria componentes Swing (JTextArea, JTextField, JPanel)
  4. Configura layout (BorderLayout + GridLayout)
  5. Configura listeners de entrada
  6. Exibe a janela
  7. Atualiza mapa visual
  8. Exibe mensagens de boas-vindas

### 2. Loop Principal de Jogo
- Aguarda entrada do usuário no `inputField`
- Quando Enter é pressionado:
  1. Recupera texto do campo
  2. Limpa o campo para próxima entrada
  3. Processa comando (validação)
  4. Executa movimento (se comando válido)
  5. Atualiza renderização visual
  6. Verifica se ganhou
  7. Volta ao início do loop

### 3. Processamento de Comando
- Valida formato: `go [direção]`
- Valida direção: norte, sul, leste, oeste
- Retorna apenas a direção se válido
- Retorna `null` e exibe ajuda se inválido

### 4. Execução de Movimento
- Valida limites do mapa
- Query `Game.movePlayer()` para validação
- Se movimento OK atualiza posição em `Game`
- Exibe mensagem de movimento
- Se movimento FALHA exibe erro
- Verifica condição de vitória

### 5. Atualização Visual
- Limpa células anteriores
- Desenha paredes (cinza)
- Desenha objetivo (verde)
- Insere ícone do jogador na posição atual

### 6. Verificação de Vitória
- Chama `game.venceu()` que verifica se `mapa[x][y] == 2`
- Se verdadeiro, exibe "🎉 Você venceu!" e jogo continua aceitando movimentos

---

## 🎯 Pontos Importantes

1. **Separação de Responsabilidades:**
   - `Game`: apenas lógica
   - `GameGUI`: apenas apresentação e coordenação

2. **Validação em Múltiplas Camadas:**
   - `GameGUI.processarComando()`: valida formato
   - `GameGUI.direcaoValida()`: valida direção
   - `Game.movePlayer()`: valida movimento

3. **Imutabilidade do Mapa:**
   - Os valores do mapa nunca mudam
   - Apenas a posição (x, y) é actualizada

4. **Feedback Imediato:**
   - Mensagens textuais + atualizações visuais
   - Ajuda sempre acessível

---

## 📌 Conclusão

O projeto **World of Monokuma** é uma aplicação bem estruturada que demonstra:
- Bom encapsulamento de responsabilidades (MVC)
- Uso adequado de GUI com Swing
- Validação robusta de entrada
- Separação clara entre lógica de negócio e apresentação

A arquitetura facilita manutenção, testes e futuras extensões como:
- Novos tipos de terreno
- Múltiplos níveis
- Sistema de pontuação
- Inimigos ou obstáculos dinâmicos
- Salvamento de progresso

