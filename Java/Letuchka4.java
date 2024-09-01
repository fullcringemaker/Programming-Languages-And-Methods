-------------------------------------------------- RecursiveDescentParser.java --------------------------------------------------
  import java.util.Scanner;

public class RecursiveDescentParser {
    private String input;
    private int pos = 0;
    private char currentChar;

    public RecursiveDescentParser(String input) {
        this.input = input.replaceAll("\\s", "");
        currentChar = input.charAt(pos);
    }

    private void nextChar() {
        pos++;
        if (pos < input.length()) {
            currentChar = input.charAt(pos);
        } else {
            currentChar = '\0';
        }
    }

    private void error(String message) {
        throw new RuntimeException("Syntax error at position " + pos + ": " + message);
    }

    private void match(char expectedChar) {
        if (currentChar == expectedChar) {
            nextChar();
        } else {
            error("Expected '" + expectedChar + "' but found '" + currentChar + "'");
        }
    }

    // <Expr> ::= (<List>)
    public void parseExpr() {
        if (currentChar == '(') {
            System.out.println("<Expr> ::= (<List>)");
            nextChar();
            parseList();
            match(')');
        } else {
            error("Expected '('");
        }
    }

    // <List> ::= <Atom> <Tail>
    public void parseList() {
        System.out.println("<List> ::= <Atom> <Tail>");
        parseAtom();
        parseTail();
    }

    // <Tail> ::= <Atom> <Tail> | ε
    public void parseTail() {
        if (Character.isDigit(currentChar) || Character.isLetter(currentChar) || currentChar == '(') {
            System.out.println("<Tail> ::= <Atom> <Tail>");
            parseAtom();
            parseTail();
        } else {
            System.out.println("<Tail> ::= ε");
        }
    }

    // <Atom> ::= NUMBER | IDENT | <Expr>
    public void parseAtom() {
        if (Character.isDigit(currentChar)) {
            StringBuilder number = new StringBuilder();
            while (Character.isDigit(currentChar)) {
                number.append(currentChar);
                nextChar();
            }
            System.out.println("<Atom> ::= NUMBER (" + number + ")");
        } else if (Character.isLetter(currentChar)) {
            System.out.println("<Atom> ::= IDENT (" + currentChar + ")");
            nextChar();
        } else if (currentChar == '(') {
            System.out.println("<Atom> ::= <Expr>");
            parseExpr();
        } else {
            error("Expected NUMBER, IDENT, or '('");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression to parse:");
        String input = scanner.nextLine();

        RecursiveDescentParser parser = new RecursiveDescentParser(input);
        try {
            parser.parseExpr();
            if (parser.pos < parser.input.length()) {
                parser.error("Extra input after valid expression");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
