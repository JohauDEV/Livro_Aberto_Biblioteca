public class Pagamento {

    private String nomeAssociado;
    private String nomeLivro;
    private double valor;

    public Pagamento(String nomeAssociado, String nomeLivro, double valor) {
        this.nomeAssociado = nomeAssociado;
        this.nomeLivro = nomeLivro;
        this.valor = valor;
    }

    // ============================
    // GETTERS
    // ============================
    public String getNomeAssociado() {
        return nomeAssociado;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public double getValor() {
        return valor;
    }

    // ============================
    // EXPORTAÇÃO PARA ARQUIVO
    // ============================
    public String exportarTexto() {
        return nomeAssociado + ";" + nomeLivro + ";" + valor;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "associado='" + nomeAssociado + '\'' +
                ", livro='" + nomeLivro + '\'' +
                ", valor=" + valor +
                '}';
    }
}
