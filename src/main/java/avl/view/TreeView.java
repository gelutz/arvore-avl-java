package avl.view;

import static dev.tamboui.toolkit.Toolkit.*;

import avl.node.Node;
import avl.node.Order;
import avl.tree.AvlTree;
import dev.tamboui.layout.Flex;
import dev.tamboui.text.Line;
import dev.tamboui.text.Span;
import dev.tamboui.text.Text;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.app.ToolkitRunner;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.widgets.input.TextInputState;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TreeView extends ToolkitApp {

    private static final Duration STEP_DELAY = Duration.ofMillis(600);
    private static final String HELP = "Comandos: i N, r N, b N, pre, pos, em. Ctrl+C sai.";

    private final AvlTree tree = new AvlTree();
    private final TextInputState input = new TextInputState();

    // Passos da animação em andamento. Só são lidos e alterados na thread de render,
    // então a árvore nunca muda enquanto está sendo desenhada.
    private final Deque<Runnable> steps = new ArrayDeque<>();

    // Por valor, e não por Node: a remoção copia valores entre nós e a rotação move os nós.
    private Integer highlighted = null;
    private String output = "";
    private String message = HELP;

    @Override
    protected Element render() {
        // Todas as linhas do desenho têm a mesma largura, então centralizar linha a linha
        // não desalinha os conectores. O length é o que deixa o flex centralizar na vertical.
        List<Line> lines = draw(tree.getRoot(), highlighted);
        Element content = lines.isEmpty()
            ? text("(árvore vazia)").dim().centered().length(1)
            : richText(Text.from(lines)).centered().length(lines.size());

        return column(
            panel("Árvore AVL", content).flex(Flex.CENTER).fill(),
            panel("Saída", richText(Text.from(Line.from(output), Line.from(message))).wrapWord()).length(6),
            textInput(input).title("Comando").placeholder(" i 45").onSubmit(this::submit).id("comando").focusable()
        );
    }

    private void submit() {
        String typed = input.text();
        input.clear();

        // Um comando por vez: o caminho da animação vale para a árvore de quando ela começou.
        if (!steps.isEmpty()) {
            message = "Animação em andamento, comando ignorado: " + typed;
            return;
        }

        output = "";
        try {
            execute(Command.parse(typed));
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
    }

    private void execute(Command command) {
        switch (command.type()) {
            case INSERT -> insert(command.value());
            case REMOVE -> remove(command.value());
            case SEARCH -> search(command.value());
            case PRE -> traverse(Order.PRE, "Pré-ordem");
            case POS -> traverse(Order.POS, "Pós-ordem");
            case EM -> traverse(Order.EM, "Em-ordem");
        }
        play();
    }

    private void insert(int value) {
        message = "Inserindo " + value + "...";
        List<Integer> path = tree.searchPath(value);
        addWalk("Caminho: ", " -> ", path);

        if (path.contains(value)) {
            addFinish("Elemento " + value + " já está na árvore, nada foi inserido.");
            return;
        }

        // A inserção só acontece depois da descida, e o sublinhado acompanha o novo
        // nó até a posição final, já com as rotações aplicadas.
        steps.add(() -> {
            tree.insert(value);
            highlighted = value;
            message = "Elemento " + value + " inserido.";
        });
        steps.add(() -> highlighted = null);
    }

    private void remove(int value) {
        if (tree.getRoot() == null) {
            message = "Árvore vazia, nada para remover.";
            return;
        }

        message = "Removendo " + value + "...";
        List<Integer> path = tree.removalPath(value);
        addWalk("Caminho: ", " -> ", path);

        if (!path.contains(value)) {
            addFinish("Elemento " + value + " não está na árvore, nada foi removido.");
            return;
        }

        int last = path.getLast();
        String done = last == value
            ? "Elemento " + value + " removido."
            : "Elemento " + value + " removido, substituído por " + last + ".";
        steps.add(() -> {
            tree.remove(value);
            highlighted = null;
            message = done;
        });
    }

    private void search(int value) {
        if (tree.getRoot() == null) {
            message = "Árvore vazia, elemento " + value + " não encontrado.";
            return;
        }

        message = "Buscando " + value + "...";
        List<Integer> path = tree.searchPath(value);
        addWalk("Caminho: ", " -> ", path);
        addFinish(path.contains(value)
            ? "Elemento " + value + " encontrado."
            : "Elemento " + value + " não está na árvore.");
    }

    private void traverse(Order order, String name) {
        if (tree.getRoot() == null) {
            message = "Árvore vazia, nada para percorrer.";
            return;
        }

        message = "Percorrendo...";
        addWalk(name + ": ", " ", tree.traversal(order));
        addFinish("Percurso concluído.");
    }

    // Um passo por valor: sublinha o nó e mostra a sequência visitada até ali.
    private void addWalk(String label, String separator, List<Integer> values) {
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            List<String> visited = values.subList(0, i + 1).stream().map(String::valueOf).toList();
            String shown = label + String.join(separator, visited);
            steps.add(() -> {
                highlighted = value;
                output = shown;
            });
        }
    }

    private void addFinish(String finalMessage) {
        steps.add(() -> {
            highlighted = null;
            message = finalMessage;
        });
    }

    // Roda um passo e agenda o próximo. A espera fica na thread do scheduler para não
    // travar o render, e o passo em si volta para a thread de render.
    private void play() {
        Runnable step = steps.poll();
        if (step == null) return;
        step.run();

        if (!steps.isEmpty()) {
            ToolkitRunner runner = runner();
            runner.schedule(() -> runner.runOnRenderThread(this::play), STEP_DELAY);
        }
    }

    // Desenha a subárvore de baixo pra cima: cada nó vira o valor com ┌── e ──┐
    // apontando pro meio do desenho de cada filho.
    static List<Line> draw(Node n, Integer highlighted) {
        if (n == null) return List.of();
        return block(n, highlighted).lines;
    }

    private record Block(List<Line> lines, int width, int mid) {}

    private static Block block(Node n, Integer highlighted) {
        String label = String.valueOf(n.getValue());
        int len = label.length();
        Block l = n.getLeft() == null ? null : block(n.getLeft(), highlighted);
        Block r = n.getRight() == null ? null : block(n.getRight(), highlighted);

        int lw = l == null ? 0 : l.width;
        int rw = r == null ? 0 : r.width;

        String before = "";
        if (l != null) before = " ".repeat(l.mid) + "┌" + "─".repeat(l.width - l.mid - 1);
        String after = "";
        if (r != null) after = "─".repeat(r.mid) + "┐" + " ".repeat(r.width - r.mid - 1);

        // Span separado para o sublinhado pegar só o valor, sem os conectores.
        Span value = highlighted != null && highlighted == n.getValue() ? Span.raw(label).underlined() : Span.raw(label);

        List<Line> lines = new ArrayList<>(List.of(Line.from(Span.raw(before), value, Span.raw(after))));
        int rows = Math.max(l == null ? 0 : l.lines.size(), r == null ? 0 : r.lines.size());
        for (int i = 0; i < rows; i++) {
            Line ls = l != null && i < l.lines.size() ? l.lines.get(i) : Line.from(" ".repeat(lw));
            Line rs = r != null && i < r.lines.size() ? r.lines.get(i) : Line.from(" ".repeat(rw));
            lines.add(ls.append(Span.raw(" ".repeat(len))).append(rs));
        }
        return new Block(lines, lw + len + rw, lw + len / 2);
    }

    public static void main(String[] args) throws Exception {
        new TreeView().run();
    }
}
