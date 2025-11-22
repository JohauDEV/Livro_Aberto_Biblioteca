import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SistemaBiblioteca sistema = new SistemaBiblioteca();

        int opcao;

        do {
            System.out.println("\n=== 📚 Sistema da Biblioteca Livro Aberto ===");
            System.out.println("1. Cadastrar associado");
            System.out.println("2. Cadastrar livro");
            System.out.println("3. Realizar empréstimo");
            System.out.println("4. Devolver livro");
            System.out.println("5. Visualizar relatórios");
            System.out.println("0. Sair e salvar");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {

                // =======================================
                case 1: // CADASTRAR ASSOCIADO
                    // =======================================
                    System.out.print("Nome do associado: ");
                    String nome = sc.nextLine();

                    System.out.print("Tipo (1-Regular | 2-VIP): ");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    Associado associado = (tipo == 2)
                            ? new AssociadoVIP(nome)
                            : new Associado(nome);

                    sistema.cadastrarAssociado(associado);
                    System.out.println("Associado cadastrado com sucesso!");
                    break;

                // =======================================
                case 2: // CADASTRAR LIVRO
                    // =======================================
                    System.out.print("Título do livro: ");
                    String titulo = sc.nextLine();

                    System.out.print("Autor: ");
                    String autor = sc.nextLine();

                    System.out.print("É livro raro? (s/n): ");
                    boolean raro = sc.nextLine().equalsIgnoreCase("s");

                    Livro livro = raro
                            ? new LivroRaro(titulo, autor)
                            : new Livro(titulo, autor);

                    sistema.cadastrarLivro(livro);
                    System.out.println("Livro cadastrado!");
                    break;

                // =======================================
                case 3: // REALIZAR EMPRÉSTIMO
                    // =======================================
                    System.out.print("Nome do associado: ");
                    String nomeEmp = sc.nextLine();
                    Associado assocEmp = sistema.buscarAssociado(nomeEmp);

                    if (assocEmp == null) {
                        System.out.println("Associado não encontrado!");
                        break;
                    }

                    System.out.print("Título do livro: ");
                    String tituloEmp = sc.nextLine();
                    Livro livroEmp = sistema.buscarLivro(tituloEmp);

                    if (livroEmp == null || !livroEmp.isDisponivel()) {
                        System.out.println("Livro indisponível!");
                        break;
                    }

                    // Livro raro → somente VIP
                    if (livroEmp instanceof LivroRaro && !(assocEmp instanceof AssociadoVIP)) {
                        System.out.println("Apenas associados VIP podem pegar livros raros!");
                        break;
                    }

                    System.out.print("Digite a data do empréstimo (AAAA-MM-DD): ");
                    String dataEmpStr = sc.nextLine();

                    try {
                        LocalDate dataEmprestimo = LocalDate.parse(dataEmpStr);

                        Emprestimo emp = new Emprestimo(livroEmp, assocEmp, dataEmprestimo);

                        assocEmp.adicionarEmprestimo(emp);
                        sistema.registrarEmprestimo(emp);

                        System.out.println("Empréstimo realizado com sucesso!");

                    } catch (Exception e) {
                        System.out.println("Data inválida! Use o formato AAAA-MM-DD.");
                    }

                    break;

                // =======================================
                case 4: // DEVOLVER LIVRO
                    // =======================================
                    System.out.print("Nome do associado que está devolvendo: ");
                    String nomeDev = sc.nextLine();

                    Associado assocDev = sistema.buscarAssociado(nomeDev);

                    if (assocDev == null) {
                        System.out.println("Associado não encontrado!");
                        break;
                    }

                    System.out.print("Título do livro a devolver: ");
                    String tituloDev = sc.nextLine();

                    Emprestimo emprestimo = sistema.getEmprestimos().stream()
                            .filter(e -> e.getLivro().getTitulo().equalsIgnoreCase(tituloDev)
                                    && e.getAssociado().getNome().equalsIgnoreCase(nomeDev)
                                    && !e.isDevolvido())
                            .findFirst()
                            .orElse(null);

                    if (emprestimo == null) {
                        System.out.println("Nenhum empréstimo ativo encontrado para este associado e livro!");
                        break;
                    }

                    System.out.print("Digite a data da devolução (AAAA-MM-DD): ");
                    String dataDevStr = sc.nextLine();

                    try {
                        LocalDate dataDevolucao = LocalDate.parse(dataDevStr);

                        emprestimo.devolverLivro(dataDevolucao);

                        double multa = emprestimo.calcularMulta();

                        if (multa > 0) {
                            System.out.println("Multa aplicada: R$ " + multa);
                            Pagamento pagamento = new Pagamento(
                                    assocDev.getNome(),
                                    emprestimo.getLivro().getTitulo(),
                                    multa
                            );
                            sistema.registrarPagamento(pagamento);
                        } else {
                            System.out.println("Devolução dentro do prazo. Sem multa.");
                        }

                        System.out.println("Livro devolvido com sucesso!");

                    } catch (Exception e) {
                        System.out.println("Data inválida! Use o formato AAAA-MM-DD.");
                    }

                    break;

                // =======================================
                case 5: // RELATÓRIOS
                    // =======================================
                    Relatorio.gerarRelatorioDetalhado(sistema);
                    break;

                // =======================================
                case 0: // SALVAR E SAIR
                    // =======================================
                    sistema.salvarDados();
                    System.out.println("Sistema encerrado. Dados salvos!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}
