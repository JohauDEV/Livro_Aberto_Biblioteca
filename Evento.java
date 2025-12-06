import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private String nome;
    private LocalDate data;
    private int vagas;
    private List<Associado> participantes;

    public Evento(String nome, LocalDate data, int vagas) {
        this.nome = nome;
        this.data = data;
        this.vagas = vagas;
        this.participantes = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public LocalDate getData() { return data; }
    public int getVagas() { return vagas; }
    public List<Associado> getParticipantes() { return participantes; }
    public int getVagasDisponiveis() {
        return vagas - participantsCount();
    }

    // Evita acesso direto ao tamanho da lista
    private int participantsCount() {
        return participantes.size();
    }

    // =============================
    //  Reserva de vaga
    // =============================
    public boolean reservarVaga(Associado a) {
        if (participantsCount() >= vagas) return false;
        if (participantes.contains(a)) return false;
        participantes.add(a);
        return true;
    }

    // =============================
    //   Exportar para TXT
    // =============================
    public String exportarTexto() {
        String inscricoes = String.join(",",
                participantes.stream().map(Associado::getNome).toArray(String[]::new)
        );
        return nome + ";" + data + ";" + vagas + ";" + inscricoes;
    }
}
