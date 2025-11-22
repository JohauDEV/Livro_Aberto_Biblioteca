import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {

    private Livro livro;
    private Associado associado;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucaoReal;
    private boolean devolvido;

    public Emprestimo(Livro livro, Associado associado, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.associado = associado;
        this.dataEmprestimo = dataEmprestimo;

        // Prazo depende do tipo do associado (Regular ou VIP)
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(associado.getPrazoEmprestimo());

        this.devolvido = false;
        livro.emprestar();
    }

    // ========================
    //   GETTERS
    // ========================
    public Livro getLivro() { return livro; }
    public Associado getAssociado() { return associado; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public boolean isDevolvido() { return devolvido; }

    // ========================
    //   CÁLCULO DE MULTA
    // ========================
    public double calcularMulta() {
        if (dataDevolucaoReal == null) {
            return 0;
        }

        long diasDecorridos = ChronoUnit.DAYS.between(dataEmprestimo, dataDevolucaoReal);
        int prazo = associado.getPrazoEmprestimo();

        if (diasDecorridos <= prazo) {
            return 0; // Sem multa
        }

        // R$1,00 por dia após o prazo (10 dias Regular, 15 dias VIP)
        double multaBase = (diasDecorridos - prazo) * 1.0;

        // Desconto para VIP
        return multaBase * (1 - associado.getDescontoMulta());
    }

    // ========================
    //   DEVOLVER LIVRO
    // ========================
    public void devolverLivro(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.devolvido = true;
        livro.devolver();
    }

    // ========================
    //   EXPORTAR PARA ARQUIVO
    // ========================
    public String exportarTexto() {
        return livro.getTitulo() + ";" +
                associado.getNome() + ";" +
                dataEmprestimo + ";" +
                (dataDevolucaoReal != null ? dataDevolucaoReal : "null") + ";" +
                devolvido;
    }

    // ========================
    //   TO STRING
    // ========================
    @Override
    public String toString() {
        return "Livro: " + livro.getTitulo() +
                " | Associado: " + associado.getNome() +
                " | Empréstimo: " + dataEmprestimo +
                " | Previsto: " + dataPrevistaDevolucao +
                " | Devolvido: " + (dataDevolucaoReal != null ? dataDevolucaoReal : "Ainda não") +
                " | Multa: R$" + calcularMulta();
    }
}
