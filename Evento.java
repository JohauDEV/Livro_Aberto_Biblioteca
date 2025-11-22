import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String nome;
    private int vagas;
    private List<Associado> participantes;

    public Evento(String nome, int vagas) {
        this.nome = nome;
        this.vagas = vagas;
        this.participantes = new ArrayList<>();
    }

    public boolean reservarVaga(Associado associado) {
        if (participantes.size() < vagas) {
            participantes.add(associado);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nome + " | Vagas restantes: " + (vagas - participantes.size());
    }
}
