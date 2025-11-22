Aqui está um **README completíssimo**, pronto para **copiar e colar no GitHub ou no VSCode**.
Ele segue o formato padrão de projetos Java, bem organizado e com seções claras.

---

# 📚 Biblioteca "Livro Aberto" – Sistema de Empréstimo

Sistema completo de gerenciamento de biblioteca, desenvolvido em **Java**, com:

* Cadastro de associados (Regular / VIP)
* Cadastro de livros (Comuns / Raros)
* Empréstimo de livros com prazos diferenciados
* Devolução com cálculo automático de multas
* Programa de benefícios para associados VIP
* Registro de pagamentos
* Relatórios detalhados de uso e frequência
* Salvamento automático em arquivos
* Carregamento dos dados ao iniciar o sistema

---

## 📌 Funcionalidades Principais

### ✔ Cadastro de Associados

* Associado **Regular** → Prazo de 10 dias, sem desconto
* Associado **VIP** → Prazo de 15 dias, 50% de desconto em multas

### ✔ Cadastro de Livros

* Livros comuns
* Livros raros (somente associados VIP podem retirar)

### ✔ Empréstimo de Livros

* Digitação da data do empréstimo
* Geração automática da data prevista de devolução conforme o tipo de associado
* Registro completo salvo em arquivo

### ✔ Devolução de Livros

* Solicita nome do associado e título do livro
* Digita data da devolução
* Calcula multa automaticamente:

  ```
  R$1,00 por dia após o prazo limite
  (10 dias Regular / 15 dias VIP)
  ```
* Inclui desconto VIP automaticamente
* Registra pagamento da multa no arquivo

### ✔ Relatórios Detalhados

* Histórico completo de empréstimos
* Empréstimos em aberto
* Quantidade total por associado
* Multas pagas e total acumulado
* Datas de empréstimo/devolução e valores por livro

### ✔ Persistência dos Dados

O sistema salva automaticamente:

| Arquivo                 | Conteúdo                             |
| ----------------------- | ------------------------------------ |
| `dados_associados.txt`  | Nome e tipo do associado             |
| `dados_livros.txt`      | Livros cadastrados e disponibilidade |
| `dados_emprestimos.txt` | Empréstimos completos com datas      |
| `dados_pagamentos.txt`  | Registros de multas pagas            |

Ao iniciar, o sistema lê todos os arquivos e restaura o estado anterior.

---

## 📦 Estrutura do Projeto

```
📂 src
 ┣ 📜 Main.java
 ┣ 📜 SistemaBiblioteca.java
 ┣ 📜 Associado.java
 ┣ 📜 AssociadoVIP.java
 ┣ 📜 Livro.java
 ┣ 📜 LivroRaro.java
 ┣ 📜 Emprestimo.java
 ┣ 📜 Pagamento.java
 ┗ 📜 Relatorio.java

📄 dados_associados.txt
📄 dados_livros.txt
📄 dados_emprestimos.txt
📄 dados_pagamentos.txt
```

---

## ▶ Como Executar

### 1. Compile o projeto:

```
javac *.java
```

### 2. Execute:

```
java Main
```

---

## 🖥 Menu Principal

```
1. Cadastrar associado
2. Cadastrar livro
3. Realizar empréstimo
4. Devolver livro
5. Visualizar relatórios
0. Sair e salvar
```

---

## 🎁 Benefícios VIP

Associados VIP têm:

* 5 dias a mais de prazo
* 50% de desconto nas multas
* Acesso exclusivo a livros raros

---

## 🧮 Cálculo da Multa

```
diasDeAtraso = diasEntreEmprestimoEDevolucao - prazoAssociado
multaBase = diasDeAtraso * 1.0

if VIP:
    multaFinal = multaBase * 0.5
else:
    multaFinal = multaBase
```

---

## 🗄 Persistência

Cada ação é automaticamente salva em arquivo ao sair.
Na próxima execução, tudo é restaurado.

---

## 📝 Exemplo de Registro de Empréstimo (dados_emprestimos.txt)

```
O Hobbit;João;2025-01-10;2025-01-17;true
Dom Quixote (1ª Edição);Maria;2025-01-08;null;false
```

---

## 🛠 Tecnologias Utilizadas

* **Java 8+**
* Manipulação de arquivos (`FileReader`, `FileWriter`)
* Programação Orientada a Objetos
* Uso de datas com `LocalDate` e `ChronoUnit`

---

## 📄 Licença

Uso livre para fins educacionais e acadêmicos.

---
