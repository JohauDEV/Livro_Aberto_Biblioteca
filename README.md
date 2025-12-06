# 📚 **Sistema de Biblioteca – README Completo**

## 🏛️ Sobre o Projeto

Este é um **sistema completo de gerenciamento de biblioteca**, desenvolvido em Java com arquitetura orientada a objetos.
O objetivo é criar uma aplicação robusta que:

✔ Gerencie livros, associados e empréstimos
✔ Controle eventos e reserva de vagas
✔ Registre pagamentos automáticos de multas
✔ Diferencie privilégios entre usuários regulares e VIP
✔ Salve tudo em arquivos **UTF-8 persistentes**
✔ Garanta integridade dos dados e regras reais de negócio

A aplicação roda no console por meio da classe `BibliotecaApp`, deixando toda a lógica nas classes de domínio.

---

# 🧩 **Funcionalidades Principais**

## 👤 **1. Gerenciamento de Associados**

* Cadastro de associado
* Dois tipos disponíveis:

  * **Regular** → prazo 10 dias, sem desconto
  * **VIP** → prazo 15 dias, desconto em multa, acesso a livros e eventos exclusivos
* Armazenados com persistência

---

## 📘 **2. Gerenciamento de Livros**

Cada livro contém:

* Título
* Autor
* Boolean **raro**
* Boolean **vipOnly** (acesso restrito aos VIPs)
* Disponibilidade
* Persistência completa em arquivo

### ➤ Regras aplicadas:

✔ Não permite cadastrar **mesmo título** com modalidades diferentes (VIP/Normal, Raro/Não raro).
✔ Livros VIP só podem ser emprestados para VIPs.
✔ Livros raros também usam as mesmas regras.

---

## 🔄 **3. Sistema de Empréstimo**

O sistema controla:

* Data de empréstimo
* Data prevista (conforme tipo do associado)
* Data de devolução
* Controle automático de disponibilidade

### Multas:

* R$ 1,00 por dia de atraso
* VIP paga somente **50% da multa**
* Registro manual em `Pagamento`

---

## 💳 **4. Sistema de Pagamentos**

Toda multa gerada é convertida em um objeto:

```
Pagamento(nomeAssociado, tituloLivro, valor)
```

Esses pagamentos são:

* Registrados no sistema
* Persistidos em `dados_pagamentos.txt`
* Exibidos nos relatórios

---

## 🗓️ **5. Sistema de Eventos**

A biblioteca permite:

✔ Criar eventos
✔ Definir vagas
✔ Associar data
✔ Inscrever participantes
✔ Garantir prioridade VIP

### Regra especial:

Se o evento estiver lotado e um **VIP tentar entrar**, o sistema:

1. Procura um participante não-VIP
2. Remove ele
3. Adiciona o VIP

---

## 📊 **6. Relatórios Detalhados**

### Disponíveis:

* Relatório detalhado do sistema
* Relatório de livros cadastrados
* Relatório de eventos

### Relatório detalhado inclui:

* Empréstimos totais por associado
* Empréstimos em aberto
* Multas pagas
* Lista de livros emprestados com datas
* Todos os pagamentos registrados

---

## ♻️ **7. Persistência Completa (UTF-8)**

Todos os dados são salvos em arquivos:

```
dados_associados.txt
dados_livros.txt
dados_emprestimos.txt
dados_pagamentos.txt
dados_eventos.txt
```

Todos lidos e carregados automaticamente no início do programa.

---

# 🧱 **Estrutura do Projeto (Arquitetura)**

```
📦 src/
 ┣ 📜 BibliotecaApp.java     → Interface do usuário (console)
 ┣ 📜 SistemaBiblioteca.java → Lógica principal e persistência
 ┣ 📜 Associado.java
 ┣ 📜 AssociadoVIP.java
 ┣ 📜 Livro.java
 ┣ 📜 Emprestimo.java
 ┣ 📜 Pagamento.java
 ┣ 📜 Evento.java
 ┗ 📜 Relatorio.java
```

### Arquitetura:

* **BibliotecaApp** apenas exibe menus e lê dados do usuário
* **SistemaBiblioteca** faz toda a lógica
* **Domínio (Livro, Associado, Evento, etc)** contém as regras
* **Relatorio** imprime tudo formatado

---

# 🖥️ **Fluxo de Execução**

1. Usuário inicia o `BibliotecaApp`
2. Menu oferece todas as operações
3. App chama funções da lógica (`SistemaBiblioteca`)
4. Sistema usa classes de domínio
5. Lógica termina → App mostra o resultado

---

# 🧪 **Restrições & Validações Implementadas**

### Livros:

✔ Proíbe duplicação com modos diferentes
✔ Proíbe empréstimo VIPOnly para regular
✔ Proíbe emprestar livro não disponível

### Associados:

✔ Nome convertido corretamente
✔ Busca insensível a maiúsculas/minúsculas

### Eventos:

✔ VIP tem prioridade
✔ Impede duplicação de inscrição
✔ Registra todas reservas

### Multas:

✔ VIP recebe desconto automático
✔ Persistência garantida

---

# ✔️ **Como Executar**

Compile:

```
javac *.java
```

Execute:

```
java BibliotecaApp
```

---

# 💡 **Possíveis Melhorias Futuras**

* Interface gráfica (JavaFX)
* Banco de dados SQLite ou PostgreSQL
* Controle de renovação de empréstimo
* Geração de PDF dos relatórios

---

# 🎉 **Conclusão**

Este sistema é uma aplicação completa com:

* Persistência
* Regras avançadas
* Prioridade por tipo de associado
* Gestão de eventos
* Boas práticas de organização

