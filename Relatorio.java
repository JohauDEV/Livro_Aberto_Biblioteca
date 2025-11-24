import java.util.List;

public class Relatorio {
    public static void gerarRelatorioAssociados(List<Associado> associados) {
        System.out.println("\n=== Relatório de Associados ===");
        for (Associado a : associados) {
            System.out.println(a);
        }
    }

    public static void gerarRelatorioLivros(List<Livro> livros) {
        System.out.println("\n=== Relatório de Livros ===");
        for (Livro l : livros) {
            System.out.println(l);
        }
    }
    public static void gerarRelatorioDetalhado(SistemaBiblioteca sistema) {
        System.out.println("\n======= RELATÓRIO DETALHADO =======");

        for (Associado a : sistema.getAssociados()) {
            long totalEmp = sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().equals(a))
                    .count();

            long emAberto = sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().equals(a) && !e.isDevolvido())
                    .count();

            double totalMultas = sistema.getPagamentos().stream()
                    .filter(p -> p.toString().startsWith(a.getNome()))
                    .mapToDouble(p -> Double.parseDouble(p.toString().split(";")[2]))
                    .sum();

            System.out.println("\n🔹 Associado: " + a.getNome());
            System.out.println("Tipo: " + a.getTipo());
            System.out.println("Empréstimos totais: " + totalEmp);
            System.out.println("Em aberto: " + emAberto);
            System.out.println("Multas pagas: R$" + totalMultas);

            System.out.println("Histórico:");
            sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().equals(a))
                    .forEach(e -> {
                        System.out.println(" - Livro: " + e.getLivro().getTitulo() +
                                " | Empréstimo: " + e.getDataEmprestimo() +
                                " | Previsto: " + e.getDataPrevistaDevolucao() +
                                " | Devolvido: " + e.getDataDevolucaoReal() +
                                " | Multa: R$" + e.calcularMulta());
                    });
        }
    }
    public static void imprimirLivrosDisponibilidade(List<Livro> livros) {
        System.out.println("\n=== 📚 Lista de Livros Cadastrados ===");

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for (Livro l : livros) {
            String status = l.isDisponivel() ? "Disponível" : "Emprestado";
            System.out.println("Título: " + l.getTitulo() + " | Autor: " + l.getAutor() +
                    " | Status: " + status);
        }
    }

}
