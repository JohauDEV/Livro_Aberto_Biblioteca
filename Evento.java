import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String nome;
    private int vagas;
    private List<Associado> participantes;
    private List<Evento> eventos;


    public Evento(String nome, int vagas) {
        this.nome = nome;
        this.vagas = vagas;
        this.participantes = new ArrayList<>();
        eventos = new ArrayList<>();

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
    public String getNome() {
        return nome;
    }

    public boolean reservaVIP(Associado associado) {
        // VIP sempre tenta entrar antes caso esteja no limite
        if (participantes.size() < vagas) {
            participantes.add(0, associado);  // VIP entra no topo
            return true;
        }
        return false;
    }

}
