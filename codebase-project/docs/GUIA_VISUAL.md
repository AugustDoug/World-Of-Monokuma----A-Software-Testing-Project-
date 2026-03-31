# 🗺️ Guia Visual do Jogo - World of Monokuma

## Mapa do Jogo

### Visualização do Mapa Inicial

```
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ 🎮 │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Legenda:
  🎮 = Jogador (posição inicial: linha 0, coluna 0)
  ⬛ = Parede (intransponível)
  ⬜ = Caminho livre
  🎯 = Objetivo (linha 4, coluna 4)
```

### Dados do Mapa (Array Java)

```java
int[][] mapa = {
    {0, 1, 0, 0, 0},  // linha 0: livre, parede, livre, livre, livre
    {0, 1, 0, 1, 0},  // linha 1: livre, parede, livre, parede, livre
    {0, 0, 0, 1, 0},  // linha 2: livre, livre, livre, parede, livre
    {1, 1, 0, 0, 0},  // linha 3: parede, parede, livre, livre, livre
    {0, 0, 0, 1, 2}   // linha 4: livre, livre, livre, parede, OBJETIVO
};

Converte em:
  0 = espaço livre ⬜
  1 = parede ⬛
  2 = objetivo 🎯
```

---

## 🎮 Walkthrough Completo

### Caminho da Vitória (Exemplo)

```
Movimento 1: go south
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ 🎮 │ ⬛ │ ⬜ │ ⬛ │ ⬜  │  ← Jogador se moveu de (0,0) para (1,0)
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 2: go south
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ 🎮 │ ⬜ │ ⬜ │ ⬛ │ ⬜  │  ← Jogador em (2,0)
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 3: go east
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ 🎮 │ ⬜ │ ⬛ │ ⬜  │  ← Jogador em (2,1)
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 4: go east
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ 🎮 │ ⬛ │ ⬜  │  ← Jogador em (2,2)
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 5: go south
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ 🎮 │ ⬜ │ ⬜  │  ← Jogador em (3,2)
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 6: go south
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ 🎮 │ ⬛ │ 🎯  │  ← Jogador em (4,2)
└─────┴────┴────┴────┴────┴─────┘

Movimento 7: go east
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ❌  │  ← Erro! Parede em (4,3)
└─────┴────┴────┴────┴────┴─────┘

Movimento 7 (Corrigido): go south (não é possível, já está no final)

Movimento 7 (Corrigido): go east (de (4,2) para (4,3)) → ERRO - parede!

Alternativa: Retroceder e ir por outro caminho...

Movimento 7 (Real): go north
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ 🎮 │ ⬜ │ ⬜  │  ← Retorna (3,2)
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 8: go east
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ 🎮 │ ⬜  │  ← Jogador em (3,3)
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 9: go east
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ 🎮  │  ← Jogador em (3,4)
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🎯  │
└─────┴────┴────┴────┴────┴─────┘

Movimento 10: go south
┌─────────────────────────────────┐
│  POS │  0 │  1 │  2 │  3 │  4  │
├─────┼────┼────┼────┼────┼─────┤
│  0  │ ⬜ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  1  │ ⬜ │ ⬛ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  2  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  3  │ ⬛ │ ⬛ │ ⬜ │ ⬜ │ ⬜  │
├─────┼────┼────┼────┼────┼─────┤
│  4  │ ⬜ │ ⬜ │ ⬜ │ ⬛ │ 🏆  │  ← OBJETIVO ALCANÇADO! (4,4)
└─────┴────┴────┴────┴────┴─────┘

🎉 VOCÊ VENCEU!
```

---

## 📊 Exemplos de Validação

### Exemplo 1: Movimento Válido

```
Situação: Jogador em (1,1), tenta "go north"
- newX = 1 - 1 = 0
- newY = 1 (sem mudança)
- Valida limites: 0 >= 0 ✅
- Valida parede: mapa[0][1] = 1 (PAREDE!) ❌
- RESULTADO: Movimento bloqueado

OUTPUT:
> go north
🚫 Não pode ir nessa direção!
```

### Exemplo 2: Movimento Fora dos Limites

```
Situação: Jogador em (0,0), tenta "go north"
- newX = 0 - 1 = -1
- newY = 0
- Valida limites: -1 >= 0? ❌
- RESULTADO: Fora dos limites

OUTPUT:
> go north
🚫 Não pode ir nessa direção!
```

### Exemplo 3: Comando Inválido

```
Situação: Usuário digita "attack west"

PROCESSAMENTO:
1. Valida formato: "attack" NÃO é "go" ❌
2. Exibe erro
3. Mostra ajuda

OUTPUT:
> attack west
❌ Use o comando 'go'
Comandos possíveis:
go north
go south
go east
go west
```

### Exemplo 4: Direção Inválida

```
Situação: Usuário digita "go northeast"

PROCESSAMENTO:
1. Valida formato: "go" ✅
2. Valida direção: "northeast" ∉ {north, south, east, west} ❌
3. Exibe erro

OUTPUT:
> go northeast
❌ Direção inválida!
Comandos possíveis:
go north
go south
go east
go west
```

---

## 🔍 Detalhes Técnicos

### Sistema de Coordenadas

O sistema usa coordenadas de matriz [linha][coluna]:

```
       Coluna (y) →
         0    1    2    3    4
    0 [  ]  [  ]  [  ]  [  ]  [  ]
    1 [  ]  [  ]  [  ]  [  ]  [  ]
    2 [  ]  [  ]  [  ]  [  ]  [  ]
    3 [  ]  [  ]  [  ]  [  ]  [  ]
    4 [  ]  [  ]  [  ]  [  ]  [  ]
    ↑
Linha (x)

Ponto (2,3) = Linha 2, Coluna 3 = [linha 2][coluna 3]
```

### Direções e Cálculos

```
"north"  → x--  (move para cima)
"south"  → x++  (move para baixo)
"west"   → y--  (move para esquerda)
"east"   → y++  (move para direita)

Exemplo: De (2,2) com "go east"
  nova posição = [2][2+1] = [2][3]
```

### Estados Possíveis de uma Célula

```
        Código  Renderização    Passável
Vazio     0        ⬜ Branco       ✅
Parede    1        ⬛ Cinza        ❌
Objetivo  2        🟩 Verde        ✅
```

---

## 🎯 Checklist para Jogar

- [ ] Entender as direções (norte, sul, leste, oeste)
- [ ] Localizar o objetivo (célula verde no canto inferior direito)
- [ ] Planejar rota evitando paredes
- [ ] Executar movimentos: `go [direção]`
- [ ] Monitorar posição no mapa visual
- [ ] Alcançar objetivo e ver mensagem de vitória

---

## 📈 Estatísticas do Mapa

```
Dimensões: 5x5 = 25 células total
Células livres: 16
Paredes: 8
Objetivo: 1

Posição Inicial: (0, 0)
Posição Objetivo: (4, 4)
Distância Mínima: 8 movimentos

Complexidade: Média
- Requer navegação estratégica
- Múltiplos caminhos possíveis
- Obstáculos intermediários
```

---

## 💡 Dicas de Jogo

1. **Exploração:** Mova-se livremente para entender o mapa
2. **Planejamento:** Identifique os caminhos passáveis
3. **Objetivo:** Sempre vá em direção ao quadrado verde
4. **Paciência:** Não há limite de movimentos
5. **Feedback:** Leia as mensagens para entender bloqueios

---

## 🐛 Testes de Casos Extremos

### Teste 1: Muro de Paredes

```
Situação: Tentar contornar bloco de paredes em (3,0-1)

Tentativa: go south, go south, go south (posição: 3,0)
Resultado: ❌ Parede a oeste, deve continuar ou retroceder
```

### Teste 2: Caminhos Alternativos

```
Situação: Mesma meta, rotas diferentes

Rota A: South, South, South, East, East
Rota B: East, East, South, South, South
Rota C: South, East, South, East, South

Todos levam ao objetivo ✅
```

---

