package utilities;

import implementations.MyStack;
import implementations.MyQueue;
import java.io.FileInputStream;
import java.io.IOException;

public class Parser {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar Parser.jar <XML file path>");
            return;
        }

        String filePath = args[0];
        String xmlDocument = readXMLFile(filePath);

        if (xmlDocument != null) {
            parseXML(xmlDocument);
        }
    }

    // Reads XML file content without using BufferedReader or FileReader
    public static String readXMLFile(String filePath) {
        StringBuilder content = new StringBuilder();
        MyQueue<Character> charQueue = new MyQueue<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            int c;
            while ((c = fis.read()) != -1) {
                charQueue.enqueue((char) c);
            }
        } catch (IOException e) {
            System.out.println("Error reading XML file: " + e.getMessage());
            return null;
        }

        // Convert queued characters to a single string
        while (!charQueue.isEmpty()) {
            content.append(charQueue.dequeue());
        }

        return content.toString();
    }

    public static void parseXML(String xmlDocument) {
        MyStack<String> tagStack = new MyStack<>();
        MyArrayList<String> errors = new MyArrayList<>();
        MyArrayList<Integer> lineNumbers = new MyArrayList<>();
        String[] lines = splitByLines(xmlDocument);
        boolean foundRootTag = false;

        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber].trim();
            if (line.isEmpty()) continue;

            int index = 0;
            while (index < line.length()) {
                int openTagStart = line.indexOf('<', index);
                if (openTagStart == -1) break;

                int openTagEnd = line.indexOf('>', openTagStart);
                if (openTagEnd == -1) {
                    errors.add("Malformed tag - missing '>' at line " + (lineNumber + 1));
                    lineNumbers.add(lineNumber + 1);
                    break;
                }

                String tagContent = line.substring(openTagStart + 1, openTagEnd);
                index = openTagEnd + 1;

                if (tagContent.startsWith("/")) {
                    String tagName = tagContent.substring(1);
                    if (tagStack.isEmpty()) {
                        errors.add("Unmatched closing tag </" + tagName + "> at line " + (lineNumber + 1));
                    } else {
                        String topTag = tagStack.pop();
                        if (!topTag.equals(tagName)) {
                            errors.add("Mismatched closing tag </" + tagName + ">, expected </" + topTag + ">");
                        }
                    }
                } else if (tagContent.endsWith("/")) {
                    continue;
                } else {
                    if (!foundRootTag) foundRootTag = true;
                    tagStack.push(tagContent);
                }
            }
        }

        while (!tagStack.isEmpty()) {
            String unclosedTag = tagStack.pop();
            errors.add("Unclosed tag: <" + unclosedTag + ">");
        }

        if (!foundRootTag) {
            errors.add("No root tag found.");
        }

        if (errors.isEmpty()) {
            System.out.println("XML is well-formed.");
        } else {
            System.out.println("Errors found in the XML document:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }

    private static String[] splitByLines(String document) {
        MyArrayList<String> lines = new MyArrayList<>();
        int lastIndex = 0;
        for (int i = 0; i < document.length(); i++) {
            if (document.charAt(i) == '\n') {
                lines.add(document.substring(lastIndex, i));
                lastIndex = i + 1;
            }
        }
        if (lastIndex < document.length()) {
            lines.add(document.substring(lastIndex));
        }

        String[] lineArray = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            lineArray[i] = lines.get(i);
        }
        return lineArray;
    }
}
