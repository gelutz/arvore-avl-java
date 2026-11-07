package avl.view;

import avl.view.Command.Type;

public class CommandTest {

    public static void parseTest() {
        assert Command.parse("i 45").equals(new Command(Type.INSERT, 45));
        assert Command.parse("  r   -7 ").equals(new Command(Type.REMOVE, -7));
        assert Command.parse("b 9").equals(new Command(Type.SEARCH, 9));
        assert Command.parse("pre").type() == Type.PRE;
        assert Command.parse("pos").type() == Type.POS;
        assert Command.parse("em").type() == Type.EM;
    }

    public static void invalidTest() {
        for (String input : new String[] {
            "", "   ", "i", "i abc", "i 1 2", "r 1.5", "b", "x 1", "I 1",
            "pre 1", "em em", "i 99999999999",
        }) {
            assert rejected(input) : input;
        }
    }

    private static boolean rejected(String input) {
        try {
            Command.parse(input);
            return false;
        } catch (IllegalArgumentException e) {
            return e.getMessage() != null && !e.getMessage().isBlank();
        }
    }
}
