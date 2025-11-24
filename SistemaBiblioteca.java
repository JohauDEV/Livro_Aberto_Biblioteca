import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaBiblioteca {
    private List<Associado> associados;
    private List<Livro> livros;
    private List<Emprestimo> emprestimos;
    private List<Evento> eventos;
    private List<Pagamento> pagamentos = new ArrayList<>();

    public void registrarPagamento(Pagamento p) {
        pagamentos.add(p);
    }
    public List<Pagamento> getPagamentos() { return pagamentos; }


    public SistemaBiblioteca() {
        associados = new ArrayList<>();
        livros = new ArrayList<>();
        emprestimos = new ArrayList<>();
        eventos = new ArrayList<>();
        carregarDados();
    }

    public void cadastrarAssociado(Associado a) { associados.add(a); }
    public void cadastrarLivro(Livro l) { livros.add(l); }
    public void registrarEmprestimo(Emprestimo e) { emprestimos.add(e); }

    public List<Associado> getAssociados() { return associados; }
    public List<Livro> getLivros() { return livros; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }

    public Associado buscarAssociado(String nome) {
        return associados.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public Livro buscarLivro(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    // ====== SALVAR E LER DADOS ======

    public void salvarDados() {
        try (PrintWriter pa = new PrintWriter("dados_associados.txt");
             PrintWriter pl = new PrintWriter("dados_livros.txt");
             PrintWriter pe = new PrintWriter("dados_emprestimos.txt")) {

            for (Associado a : associados) {
                pa.println(a.getNome() + ";" + a.getTipo());
            }

            for (Livro l : livros) {
                pl.println(l.getTitulo() + ";" + l.getAutor() + ";" + (l instanceof LivroRaro ? "Raro" : "Normal") + ";" + l.isDisponivel());
            }

            for (Emprestimo e : emprestimos) {
                pe.println(e.exportarTexto());
            }

            System.out.println("\n💾 Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        try {
            // ===== ASSOCIADOS =====
            File fileA = new File("dados_associados.txt");
            if (fileA.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileA))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        String[] p = linha.split(";");
                        if (p.length >= 2) {
                            Associado a = p[1].equals("VIP") ? new AssociadoVIP(p[0]) : new Associado(p[0]);
                            associados.add(a);
                        }
                    }
                }
            }

            // ===== LIVROS =====
            File fileL = new File("dados_livros.txt");
            if (fileL.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileL))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        String[] p = linha.split(";");
                        if (p.length >= 4) {
                            Livro l = p[2].equals("Raro") ? new LivroRaro(p[0], p[1]) : new Livro(p[0], p[1]);
                            if (!Boolean.parseBoolean(p[3])) l.emprestar();
                            livros.add(l);
                        }
                    }
                }
            }

            // ===== EMPRÉSTIMOS =====
            File fileE = new File("dados_emprestimos.txt");
            if (fileE.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileE))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        String[] p = linha.split(";");
                        if (p.length >= 5) {
                            Livro l = buscarLivro(p[0]);
                            Associado a = buscarAssociado(p[1]);
                            if (l != null && a != null) {
                                LocalDate dataEmp = LocalDate.parse(p[2]);
                                Emprestimo emp = new Emprestimo(l, a, dataEmp);
                                if (!p[3].equals("null")) {
                                    emp.devolverLivro(LocalDate.parse(p[3]));
                                }
                                emprestimos.add(emp);
                            }
                        }
                    }
                }
            }

            System.out.println("📂 Dados carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
    public void cadastrarEvento(Evento e) {
        eventos.add(e);
    }
    public List<Evento> getEventos() {
        return eventos;
    }

}
