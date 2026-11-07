package avl.view;

import avl.tree.AvlTree;
import dev.tamboui.style.Modifier;
import dev.tamboui.text.Line;
import dev.tamboui.text.Span;
import java.util.ArrayList;
import java.util.List;

public class TreeViewTest {

    private static AvlTree tree(int... values) {
        var tree = new AvlTree();
        for (int value : values) {
            tree.insert(value);
        }
        return tree;
    }

    private static List<String> text(List<Line> lines) {
        return lines.stream().map(Line::rawContent).toList();
    }

    private static List<String> underlined(List<Line> lines) {
        List<String> found = new ArrayList<>();
        for (Line line : lines) {
            for (Span span : line.spans()) {
                if (span.style().effectiveModifiers().contains(Modifier.UNDERLINED)) {
                    found.add(span.content());
                }
            }
        }
        return found;
    }

    public static void layoutTest() {
        var lines = text(TreeView.draw(tree(30, 20, 40).getRoot(), null));
        assert lines.equals(List.of(" ┌30─┐", "20  40")) : lines;

        // número longo e negativo não podem desalinhar os conectores
        lines = text(TreeView.draw(tree(0, -100, 12345).getRoot(), null));
        assert lines.equals(List.of("  ┌─0──┐  ", "-100 12345")) : lines;
    }

    public static void underlineTest() {
        var root = tree(30, 20, 40, -100).getRoot();
        assert underlined(TreeView.draw(root, null)).isEmpty();
        assert underlined(TreeView.draw(root, 30)).equals(List.of("30"));
        assert underlined(TreeView.draw(root, -100)).equals(List.of("-100"));
        // valor fora da árvore não sublinha nada
        assert underlined(TreeView.draw(root, 7)).isEmpty();
        // sublinhar não muda o desenho
        assert text(TreeView.draw(root, 20)).equals(text(TreeView.draw(root, null)));
    }
}
