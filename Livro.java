import java.util.Objects;

public class Livro {
    private String titulo;
    private String autor;
    private boolean raro;
    private boolean vipOnly;
    private boolean disponivel;

    public Livro(String titulo, String autor, boolean raro, boolean vipOnly) {
        this.titulo = titulo;
        this.autor = autor;
        this.raro = raro;
        this.vipOnly = vipOnly;
        this.disponivel = true;
    }

    public Livro(String titulo, String autor) {
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isRaro() { return raro; }
    public boolean isVipOnly() { return vipOnly; }
    public boolean isDisponivel() { return disponivel; }

    public void emprestar() { disponivel = false; }
    public void devolver() { disponivel = true; }

    @Override
    public String toString() {
        String tipo = raro ? "Raro" : (vipOnly ? "VIP-Only" : "Normal");
        return titulo + " - " + autor + " [" + tipo + "]" + (disponivel ? " (Disponível)" : " (Emprestado)");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return titulo.equalsIgnoreCase(livro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo.toLowerCase());
    }
}
