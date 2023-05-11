import java.io.*;
import java.util.*;

public class Tokenizer {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("/mnt/cca470e5-a614-4470-8e41-e1b348ed137b/Documents/Compilers Course Work/Compilers Tokens2/src/sourceCode.txt"));
            String sourceCode = "";
            while (input.hasNextLine()) {
                sourceCode += input.nextLine() + "\n";
            }

            List<String> tokens = tokenize(sourceCode);

            // Create a PrintWriter object to write to a file
            PrintWriter output = new PrintWriter(new File("/mnt/cca470e5-a614-4470-8e41-e1b348ed137b/Documents/Compilers Course Work/Compilers Tokens2/src/tokenOutput.txt"));
            output.println("Tokens:");
            for (String token : tokens) {
                output.println(token);
            }
            output.close(); // Remember to close the PrintWriter when you're done
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }



    public static List<String> tokenize(String sourceCode) {
        List<String> tokens = new ArrayList<>();

        String[] keywords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sourceCode.length(); i++) {
            char ch = sourceCode.charAt(i);

            if (Character.isWhitespace(ch)) {
                handleToken(sb.toString(), keywords, tokens);
                sb.setLength(0);
            } else if (isSpecial(ch)) {
                handleToken(sb.toString(), keywords, tokens);
                sb.setLength(0);

                if (ch == '"') {
                    sb.append(ch);
                    i++;
                    while (i < sourceCode.length() && sourceCode.charAt(i) != '"') {
                        sb.append(sourceCode.charAt(i));
                        i++;
                    }
                    sb.append('"');
                    tokens.add("String: " + sb.toString());
                    sb.setLength(0);
                } else if (ch == '/') {
                    if (i < sourceCode.length() - 1 && sourceCode.charAt(i + 1) == '/') {
                        sb.append(ch);
                        sb.append('/');
                        i += 2;
                        while (i < sourceCode.length() && sourceCode.charAt(i) != '\n') {
                            sb.append(sourceCode.charAt(i));
                            i++;
                        }
                        tokens.add("Comment: " + sb.toString());
                        sb.setLength(0);
                    } else if (i < sourceCode.length() - 1 && sourceCode.charAt(i + 1) == '*') {
                        sb.append(ch);
                        sb.append('*');
                        i += 2;
                        while (i < sourceCode.length() - 1 && !(sourceCode.charAt(i) == '*' && sourceCode.charAt(i + 1) == '/')) {
                            sb.append(sourceCode.charAt(i));
                            i++;
                        }
                        sb.append('*');
                        sb.append('/');
                        tokens.add("Comment: " + sb.toString());
                        sb.setLength(0);
                        i++;
                    } else {
                        tokens.add("Operator: " + "/");
                    }
                } else {
                    tokens.add("Operator: " + Character.toString(ch));
                }
            } else {
                sb.append(ch);
            }
        }

        handleToken(sb.toString(), keywords, tokens);

        return tokens;
    }

    private static void handleToken(String token, String[] keywords, List<String> tokens) {
        if (token.length() > 0) {
            if (Arrays.asList(keywords).contains(token)) {
                tokens.add("Keyword: " + token);
            } else if (isNumeric(token)) {
                tokens.add("Number: " + token);
            } else {
                tokens.add("Identifier: " + token);
            }
        }
    }

    private static boolean isSpecial(char ch) {
        return ch == '"' || ch == '/' || ch == '*' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%'
                || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '[' || ch == ']' || ch == ';' || ch == ','
                || ch == '.' || ch == ':' || ch == '<' || ch == '>' || ch == '=' || ch == '!' || ch == '&' || ch == '|'
                || ch == '?' || ch == '^' || ch == '~';
    }
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }



}