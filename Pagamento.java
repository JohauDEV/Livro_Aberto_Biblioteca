public class Pagamento {
    private String associado;
    private String livro;
    private double valor;

    public Pagamento(String associado, String livro, double valor) {
        this.associado = associado;
        this.livro = livro;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return associado + ";" + livro + ";" + valor;
    }
}
