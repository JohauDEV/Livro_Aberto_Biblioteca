public class Main {
    public static void main(String[] args) {
        // Layout / cabeçalho simples
        System.out.println("=========================================");
        System.out.println("   Biblioteca Livro Aberto - Sistema v1   ");
        System.out.println("=========================================");
        // Delega execução ao app (que contém a interação)
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }
}

