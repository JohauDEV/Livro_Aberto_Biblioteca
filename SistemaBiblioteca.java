import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaBiblioteca {

    private List<Associado> associados;
    private List<Livro> livros;
    private List<Emprestimo> emprestimos;
    private List<Evento> eventos;
    private List<Pagamento> pagamentos;

    public SistemaBiblioteca() {
        associados = new ArrayList<>();
        livros = new ArrayList<>();
        emprestimos = new ArrayList<>();
        eventos = new ArrayList<>();
        pagamentos = new ArrayList<>();
        carregarDados();
    }

    // ============================================================
    // CRUD BÁSICO
    // ============================================================

    public void cadastrarAssociado(Associado a) { associados.add(a); }

    public boolean cadastrarLivro(Livro l) {
        for (Livro existing : livros) {
            if (existing.getTitulo().equalsIgnoreCase(l.getTitulo())) {
                // impedir coexistência VIP/normal ou raro/normal com mesmo título
                if (existing.isVipOnly() != l.isVipOnly() ||
                        existing.isRaro() != l.isRaro()) {

                    System.out.println("❌ ERRO: já existe um livro com este título, porém com tipo diferente (VIP/Normal ou Raro/Comum).");
                    return false;
                }

                System.out.println("❌ ERRO: livro com este título já está cadastrado.");
                return false;
            }
        }

        livros.add(l);
        return true;
    }

    public void registrarEmprestimo(Emprestimo e) { emprestimos.add(e); }
    public void cadastrarEvento(Evento e) { eventos.add(e); }
    public void registrarPagamento(Pagamento p) { pagamentos.add(p); }

    public List<Associado> getAssociados() { return associados; }
    public List<Livro> getLivros() { return livros; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }
    public List<Evento> getEventos() { return eventos; }
    public List<Pagamento> getPagamentos() { return pagamentos; }

    public Associado buscarAssociado(String nome) {
        return associados.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }

    public Livro buscarLivro(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst().orElse(null);
    }

    // ============================================================
    // ARQUIVOS (UTF-8)
    // ============================================================

    public void salvarDados() {
        try (
                PrintWriter pa = new PrintWriter(new OutputStreamWriter(new FileOutputStream("dados_associados.txt"), StandardCharsets.UTF_8));
                PrintWriter pl = new PrintWriter(new OutputStreamWriter(new FileOutputStream("dados_livros.txt"), StandardCharsets.UTF_8));
                PrintWriter pe = new PrintWriter(new OutputStreamWriter(new FileOutputStream("dados_emprestimos.txt"), StandardCharsets.UTF_8));
                PrintWriter pp = new PrintWriter(new OutputStreamWriter(new FileOutputStream("dados_pagamentos.txt"), StandardCharsets.UTF_8));
                PrintWriter pev = new PrintWriter(new OutputStreamWriter(new FileOutputStream("dados_eventos.txt"), StandardCharsets.UTF_8))
        ) {

            // ASSOCIADOS
            for (Associado a : associados) {
                pa.println(a.getNome() + ";" + a.getTipo());
            }

            // LIVROS
            for (Livro l : livros) {
                pl.println(l.getTitulo() + ";" + l.getAutor() + ";" +
                        l.isRaro() + ";" + l.isVipOnly() + ";" + l.isDisponivel());
            }

            // EMPRESTIMOS
            for (Emprestimo e : emprestimos) {
                pe.println(e.exportarTexto());
            }

            // PAGAMENTOS
            for (Pagamento p : pagamentos) {
                pp.println(p.exportarTexto());
            }

            // EVENTOS
            for (Evento ev : eventos) {
                String inscritos = String.join(",", ev.getParticipantes()
                        .stream().map(Associado::getNome).toArray(String[]::new));

                pev.println(ev.getNome() + ";" + ev.getData() + ";" +
                        ev.getVagas() + ";" + inscritos);
            }

            System.out.println("💾 Dados salvos com sucesso!");

        } catch (IOException ex) {
            System.out.println("Erro ao salvar dados: " + ex.getMessage());
        }
    }

    // ============================================================
    // CARREGAR DADOS
    // ============================================================

    public void carregarDados() {
        try {
            // ASSOCIADOS
            File fA = new File("dados_associados.txt");
            if (fA.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fA), StandardCharsets.UTF_8))) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        String[] p = l.split(";");
                        if (p.length >= 2) {
                            Associado a = p[1].equalsIgnoreCase("VIP")
                                    ? new AssociadoVIP(p[0])
                                    : new Associado(p[0]);
                            associados.add(a);
                        }
                    }
                }
            }

            // LIVROS
            File fL = new File("dados_livros.txt");
            if (fL.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fL), StandardCharsets.UTF_8))) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        String[] p = l.split(";");
                        if (p.length >= 5) {
                            String titulo = p[0];
                            String autor = p[1];
                            boolean raro = Boolean.parseBoolean(p[2]);
                            boolean vip = Boolean.parseBoolean(p[3]);
                            boolean disponivel = Boolean.parseBoolean(p[4]);

                            Livro livro = new Livro(titulo, autor, raro, vip);
                            if (!disponivel) livro.emprestar();
                            livros.add(livro);
                        }
                    }
                }
            }

            // EMPRESTIMOS
            File fE = new File("dados_emprestimos.txt");
            if (fE.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fE), StandardCharsets.UTF_8))) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        String[] p = l.split(";");
                        if (p.length >= 5) {
                            Livro livro = buscarLivro(p[0]);
                            Associado a = buscarAssociado(p[1]);

                            if (livro != null && a != null) {
                                LocalDate dataEmp = LocalDate.parse(p[2]);
                                Emprestimo emp = new Emprestimo(livro, a, dataEmp);

                                if (!p[3].equals("null")) {
                                    emp.devolverLivro(LocalDate.parse(p[3]));
                                }

                                emprestimos.add(emp);
                            }
                        }
                    }
                }
            }

            // PAGAMENTOS
            File fP = new File("dados_pagamentos.txt");
            if (fP.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fP), StandardCharsets.UTF_8))) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        String[] p = l.split(";");
                        if (p.length >= 3) {
                            pagamentos.add(new Pagamento(p[0], p[1], Double.parseDouble(p[2])));
                        }
                    }
                }
            }

            // EVENTOS
            File fEv = new File("dados_eventos.txt");
            if (fEv.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fEv), StandardCharsets.UTF_8))) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        String[] p = l.split(";");
                        if (p.length >= 3) {
                            Evento ev = new Evento(p[0], LocalDate.parse(p[1]), Integer.parseInt(p[2]));

                            if (p.length >= 4 && !p[3].isEmpty()) {
                                String[] list = p[3].split(",");
                                for (String nome : list) {
                                    Associado a = buscarAssociado(nome);
                                    if (a != null) ev.reservarVaga(a);
                                }
                            }

                            eventos.add(ev);
                        }
                    }
                }
            }

            System.out.println("📂 Dados carregados com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    // ============================================================
    // RESERVAR EVENTO – VIP PRIORIDADE (CORRIGIDO)
    // ============================================================

    public boolean reservarEvento(String nomeEvento, Associado associado) {

        Evento ev = eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeEvento))
                .findFirst().orElse(null);

        if (ev == null) return false;

        // vaga disponível — registra direto
        if (ev.getParticipantes().size() < ev.getVagas()) {
            return ev.reservarVaga(associado);
        }

        // Caso esteja lotado: reacomodar VIP
        if (associado instanceof AssociadoVIP) {

            List<Associado> lista = ev.getParticipantes();

            for (int i = 0; i < lista.size(); i++) {
                Associado a = lista.get(i);

                if (!(a instanceof AssociadoVIP)) {
                    lista.remove(i);
                    return ev.reservarVaga(associado);
                }
            }
        }

        return false;
    }
}
