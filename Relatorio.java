public class Relatorio {

    public static void imprimirTodosLivros(SistemaBiblioteca sistema) {
        System.out.println("\n=== 📚 LISTA DE LIVROS ===");

        for (Livro l : sistema.getLivros()) {
            System.out.println(
                    "Título: " + l.getTitulo() +
                            " | Autor: " + l.getAutor() +
                            " | Raro: " + l.isRaro() +
                            " | Apenas VIP: " + l.isVipOnly() +
                            " | Disponível: " + (l.isDisponivel() ? "✔" : "❌")
            );
        }
    }

    public static void gerarRelatorioEventos(SistemaBiblioteca sistema) {
        System.out.println("\n=== 🎉 RELATÓRIO DE EVENTOS ===");

        if (sistema.getEventos().isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Evento ev : sistema.getEventos()) {
            System.out.println("\n📌 Evento: " + ev.getNome());
            System.out.println("📅 Data: " + ev.getData());
            System.out.println("👥 Vagas: " + ev.getParticipantes().size() + "/" + ev.getVagas());

            System.out.println("Participantes:");
            if (ev.getParticipantes().isEmpty()) {
                System.out.println("  Nenhum participante.");
            } else {
                for (Associado a : ev.getParticipantes()) {
                    System.out.println("  - " + a.getNome() + " (" + a.getTipo() + ")");
                }
            }
        }
    }

    public static void gerarRelatorioDetalhado(SistemaBiblioteca sistema) {

        System.out.println("\n===== 📘 RELATÓRIO DETALHADO =====\n");

        for (Associado a : sistema.getAssociados()) {

            long totalEmprestimos = sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().getNome().equalsIgnoreCase(a.getNome()))
                    .count();

            long emprestimosAbertos = sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().getNome().equalsIgnoreCase(a.getNome()) && !e.isDevolvido())
                    .count();

            double totalMultas = sistema.getPagamentos().stream()
                    .filter(p -> p.getNomeAssociado().equalsIgnoreCase(a.getNome()))
                    .mapToDouble(Pagamento::getValor)
                    .sum();

            // ---- Impressão ----
            System.out.println("\n👤 Associado: " + a.getNome());
            System.out.println("Tipo: " + a.getTipo());
            System.out.println("Total de empréstimos: " + totalEmprestimos);
            System.out.println("Empréstimos em aberto: " + emprestimosAbertos);
            System.out.println("Total em multas: R$ " + totalMultas);

            System.out.println("\nHistórico:");
            sistema.getEmprestimos().stream()
                    .filter(e -> e.getAssociado().getNome().equalsIgnoreCase(a.getNome()))
                    .forEach(e -> {
                        System.out.println(" - Livro: " + e.getLivro().getTitulo() +
                                " | Empréstimo: " + e.getDataEmprestimo() +
                                " | Previsto: " + e.getDataPrevistaDevolucao() +
                                " | Devolvido: " + (e.getDataDevolucaoReal() == null ? "Não" : e.getDataDevolucaoReal()) +
                                " | Multa: R$ " + e.calcularMulta());
                    });
        }
    }
}
