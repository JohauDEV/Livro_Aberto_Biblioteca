public class AssociadoVIP extends Associado {
    public AssociadoVIP(String nome) {
        super(nome);
        this.tipo = "VIP";
        this.prazoEmprestimo = 15; // prazo estendido
        this.descontoMulta = 0.5; // 50% de desconto em multas
    }
}
