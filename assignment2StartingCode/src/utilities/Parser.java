package utilities;

import implementations.MyStack;
import implementations.MyQueue;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Parser class reads an XML file and checks for syntax and structural errors.
 * It identifies invalid or malformed tags and ensures proper nesting.
 *
 * <p>Usage:
 * <pre>{@code
 * java -jar Parser.jar <XML file path>
 * }</pre>
 * </p>
 *
 * @author Anthony Yang, Elina Chin, Anjhel Balane, Sumaiya Khurshid, Sila Demirkaya
 * @version 1.0
 * @since 2024-11-22
 */
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
    
    /**
     * Reads the contents of an XML file into a String.
     *
     * @param filePath the path to the XML file to be read
     * @return the contents of the XML file as a String, or null if an error occurs
     */
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

        while (!charQueue.isEmpty()) {
            content.append(charQueue.dequeue());
        }

        return content.toString();
    }
    
    /**
     * Parses the given XML document for syntax and structural errors.
     *
     * @param xmlDocument the XML content as a String
     */
    public static void parseXML(String xmlDocument) {
        MyStack<TagInfo> tagStack = new MyStack<>();
        MyArrayList<String> errors = new MyArrayList<>();
        String[] lines = splitByLines(xmlDocument);

        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber].trim();

            if (line.isEmpty()) {
                continue;
            }

            int index = 0;

            while (index < line.length()) {
                int openTagStart = line.indexOf('<', index);
                if (openTagStart == -1) break;

                int openTagEnd = line.indexOf('>', openTagStart);
                if (openTagEnd == -1) {

                    errors.add("Error at line " + (lineNumber + 1) + ": Malformed tag\n" + line.trim());
                    break;
                }

                String tagContent = line.substring(openTagStart + 1, openTagEnd).trim();
                index = openTagEnd + 1;

                if (line.indexOf(">>", openTagStart) != -1) {
                    errors.add("Invalid close tag at line " + (lineNumber + 1) + "\n" + line.trim());
                    break;
                }

                if (tagContent.startsWith("!--") || tagContent.startsWith("?")) {
                    continue;
                }

                if (tagContent.startsWith("/")) {
                    String closingTagName = tagContent.substring(1).split(" ")[0];
                    if (tagStack.isEmpty()) {
                        errors.add("Error at line " + (lineNumber + 1) + ": </" + closingTagName + ">");
                    } else {
                        TagInfo topTag = tagStack.pop();
                        if (!topTag.name.equals(closingTagName)) {
                            errors.add("Error at line " + (lineNumber + 1) + ": </" + closingTagName + ">");
                        }
                    }
                    continue;
                }

                if (tagContent.endsWith("/")) {
                    continue;
                }

                String openingTagName = tagContent.split(" ")[0];
                tagStack.push(new TagInfo(openingTagName, lineNumber + 1));
            }
        }

        while (!tagStack.isEmpty()) {
            TagInfo unclosedTag = tagStack.pop();
            errors.add("Error at line " + unclosedTag.line + ": <" + unclosedTag.name + ">");
        }

        if (errors.isEmpty()) {
            System.out.println("XML is well-formed.");
        } else {
            System.out.println("===========Error Log===========");
            Iterator<String> iterator = errors.iterator();
            while (iterator.hasNext()) {
                String error = iterator.next();
                System.out.println(error);
            }
        }
    }
    
    /**
     * A helper class to store tag information for the stack.
     */
    static class TagInfo {
        String name;
        int line;

        TagInfo(String name, int line) {
            this.name = name;
            this.line = line;
        }
    }

    /**
     * Splits a document into lines, preserving the original line structure.
     *
     * @param document the document to split
     * @return an array of lines from the document
     */
    private static String[] splitByLines(String document) {
        return document.split("\r\n|\r|\n");
    }
}
