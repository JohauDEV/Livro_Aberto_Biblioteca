public class LivroRaro extends Livro {
    private boolean reservado;

    public LivroRaro(String titulo, String autor) {
        super(titulo, autor);
        this.reservado = false;
    }

    public boolean isReservado() { return reservado; }
    public void reservar() { reservado = true; }
    public void liberarReserva() { reservado = false; }

    @Override
    public String toString() {
        return super.toString() + " [Raro]";
    }
}
