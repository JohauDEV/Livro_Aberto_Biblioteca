import java.util.ArrayList;
import java.util.List;

public class Associado {
    protected String nome;
    protected String tipo;
    protected int prazoEmprestimo = 10;
    protected double descontoMulta = 0.0;
    protected List<Emprestimo> historico;

    public Associado(String nome) {
        this.nome = nome;
        this.tipo = "Regular";
        this.prazoEmprestimo = 7; // dias
        this.descontoMulta = 0.0;
        this.historico = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getPrazoEmprestimo() { return prazoEmprestimo; }
    public double getDescontoMulta() { return descontoMulta; }

    public void adicionarEmprestimo(Emprestimo e) { historico.add(e); }

    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }
}
