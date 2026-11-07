package avl.view;

// Comando já validado; a mensagem da exceção do parse é o próprio texto mostrado na view.
public record Command(Type type, int value) {

    public enum Type {
        INSERT,
        REMOVE,
        SEARCH,
        PRE,
        POS,
        EM,
    }

    public static Command parse(String input) {
        String[] parts = input.trim().split("\\s+");
        return switch (parts[0]) {
            case "" -> throw new IllegalArgumentException("Digite um comando.");
            case "i" -> withElement(Type.INSERT, parts);
            case "r" -> withElement(Type.REMOVE, parts);
            case "b" -> withElement(Type.SEARCH, parts);
            case "pre" -> withoutElement(Type.PRE, parts);
            case "pos" -> withoutElement(Type.POS, parts);
            case "em" -> withoutElement(Type.EM, parts);
            default -> throw new IllegalArgumentException("Comando desconhecido: " + parts[0] + ".");
        };
    }

    private static Command withElement(Type type, String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Uso: " + parts[0] + " N (N é um número inteiro).");
        }
        try {
            return new Command(type, Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número inválido: " + parts[1] + ".");
        }
    }

    private static Command withoutElement(Type type, String[] parts) {
        if (parts.length != 1) {
            throw new IllegalArgumentException("O comando " + parts[0] + " não recebe elemento.");
        }
        return new Command(type, 0);
    }
}
