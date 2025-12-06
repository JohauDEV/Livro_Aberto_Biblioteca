import java.time.LocalDate;
import java.util.Scanner;

public class BibliotecaApp {

    private SistemaBiblioteca sistema;
    private Scanner sc;

    public BibliotecaApp() {
        sistema = new SistemaBiblioteca();
        sc = new Scanner(System.in, "UTF-8"); // garante suporte a caracteres especiais
    }

    public void start() {
        int opcao = 0;
        do {
            mostrarMenu();
            String entrada = sc.nextLine();
            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opcao) {
                case 1: cadastrarAssociado(); break;
                case 2: cadastrarLivro(); break;
                case 3: realizarEmprestimo(); break;
                case 4: devolverLivro(); break;
                case 5: Relatorio.gerarRelatorioDetalhado(sistema); break;
                case 6: Relatorio.imprimirTodosLivros(sistema); break;
                case 7: criarEvento(); break;
                case 8: reservarVagaEvento(); break;
                case 9: Relatorio.gerarRelatorioEventos(sistema);break;
                case 0:
                    sistema.salvarDados();
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== 📚 Biblioteca Livro Aberto ===");
        System.out.println("1. Cadastrar associado");
        System.out.println("2. Cadastrar livro");
        System.out.println("3. Realizar empréstimo");
        System.out.println("4. Devolver livro");
        System.out.println("5. Relatório detalhado");
        System.out.println("6. Imprimir todos os livros");
        System.out.println("7. Cadastrar evento");
        System.out.println("8. Reservar vaga em evento");
        System.out.println("9. Relatório de eventos");
        System.out.println("0. Salvar e sair");
        System.out.print("Escolha: ");
    }

    private void cadastrarAssociado() {
        System.out.print("Nome do associado: ");
        String nome = sc.nextLine();
        System.out.print("Tipo (1-Regular | 2-VIP): ");
        String t = sc.nextLine();
        try {
            int tipo = Integer.parseInt(t.trim());
            Associado a = (tipo == 2) ? new AssociadoVIP(nome) : new Associado(nome);
            sistema.cadastrarAssociado(a);
            System.out.println("Associado cadastrado.");
        } catch (Exception e) {
            System.out.println("Erro no cadastro.");
        }
    }

    private void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        System.out.print("É livro raro? (s/n): ");
        boolean raro = sc.nextLine().equalsIgnoreCase("s");
        System.out.print("Restrito a VIPs? (s/n): ");
        boolean vipOnly = sc.nextLine().equalsIgnoreCase("s");

        Livro l = new Livro(titulo, autor, raro, vipOnly);
        boolean ok = sistema.cadastrarLivro(l);
        if (ok) System.out.println("Livro cadastrado.");
    }

    private void realizarEmprestimo() {
        System.out.print("Nome do associado: ");
        String nome = sc.nextLine();
        Associado a = sistema.buscarAssociado(nome);
        if (a == null) { System.out.println("Associado não encontrado."); return; }

        System.out.print("Título do livro: ");
        String titulo = sc.nextLine();
        Livro l = sistema.buscarLivro(titulo);
        if (l == null || !l.isDisponivel()) { System.out.println("Livro indisponível."); return; }

        if (l.isVipOnly() && !(a instanceof AssociadoVIP)) {
            System.out.println("Livro restrito a VIPs.");
            return;
        }

        System.out.print("Data do empréstimo (AAAA-MM-DD): ");
        String data = sc.nextLine();
        try {
            LocalDate d = LocalDate.parse(data);
            Emprestimo e = new Emprestimo(l, a, d);
            a.adicionarEmprestimo(e);
            sistema.registrarEmprestimo(e);
            System.out.println("Empréstimo registrado.");
        } catch (Exception ex) {
            System.out.println("Data inválida.");
        }
    }

    private void devolverLivro() {
        System.out.print("Nome do associado: ");
        String nome = sc.nextLine();
        Associado a = sistema.buscarAssociado(nome);
        if (a == null) { System.out.println("Associado não encontrado."); return; }

        System.out.print("Título do livro: ");
        String titulo = sc.nextLine();
        Emprestimo e = sistema.getEmprestimos().stream()
                .filter(x -> !x.isDevolvido() && x.getAssociado().getNome().equalsIgnoreCase(nome)
                        && x.getLivro().getTitulo().equalsIgnoreCase(titulo))
                .findFirst().orElse(null);
        if (e == null) { System.out.println("Empréstimo não encontrado."); return; }

        System.out.print("Data da devolução (AAAA-MM-DD): ");
        String data = sc.nextLine();
        try {
            LocalDate d = LocalDate.parse(data);
            e.devolverLivro(d);
            double multa = e.calcularMulta();
            if (multa > 0) {
                Pagamento p = new Pagamento(a.getNome(), e.getLivro().getTitulo(), multa);
                sistema.registrarPagamento(p);
                System.out.println("Multa: R$" + multa + " (registrada)");
            } else {
                System.out.println("Devolução sem multa.");
            }
            System.out.println("Livro devolvido com sucesso.");
        } catch (Exception ex) {
            System.out.println("Data inválida.");
        }
    }

    private void criarEvento() {
        System.out.print("Nome do evento: ");
        String nome = sc.nextLine();
        System.out.print("Data (AAAA-MM-DD): ");
        String data = sc.nextLine();
        System.out.print("Vagas: ");
        String vagasStr = sc.nextLine();
        try {
            LocalDate d = LocalDate.parse(data);
            int vagas = Integer.parseInt(vagasStr.trim());
            Evento ev = new Evento(nome, d, vagas);
            sistema.cadastrarEvento(ev);
            System.out.println("Evento cadastrado.");
        } catch (Exception ex) {
            System.out.println("Erro ao cadastrar evento.");
        }

    }
    public static void gerarRelatorioEventos(SistemaBiblioteca sistema) {
        System.out.println("\n=== 📅 RELATÓRIO DE EVENTOS ===");

        if (sistema.getEventos().isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Evento e : sistema.getEventos()) {
            System.out.println("\nEvento: " + e.getNome());
            System.out.println("Data: " + e.getData());
            System.out.println("Vagas totais: " + e.getVagas());
            System.out.println("Vagas restantes: " + e.getVagasDisponiveis());

            if (e.getParticipantes().isEmpty()) {
                System.out.println("Nenhum participante inscrito.");
            } else {
                System.out.println("Participantes:");
                for (Associado a : e.getParticipantes()) {
                    System.out.println(" - " + a.getNome() + " (" + a.getTipo() + ")");
                }
            }
        }
    }


    private void reservarVagaEvento() {
        System.out.print("Nome do associado: ");
        String nome = sc.nextLine();
        Associado a = sistema.buscarAssociado(nome);
        if (a == null) { System.out.println("Associado não encontrado."); return; }

        System.out.print("Nome do evento: ");
        String evento = sc.nextLine();
        boolean ok = sistema.reservarEvento(evento, a);
        if (ok) System.out.println("Reserva efetuada.");
        else System.out.println("Não foi possível reservar (evento lotado ou não existe).");
    }
}
