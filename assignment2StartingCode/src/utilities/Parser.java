package utilities;

public class Parser {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide an XML document as input.");
            return;
        }

        String xmlDocument = args[0];
        parseXML(xmlDocument);
    }

    public static void parseXML(String xmlDocument) {
        MyStack<String> tagStack = new MyStack<>(); // Stack for tracking tags
        MyArrayList<String> errors = new MyArrayList<>(); // List for storing error messages
        MyArrayList<Integer> lineNumbers = new MyArrayList<>(); // Line numbers corresponding to errors

        String[] lines = splitByLines(xmlDocument); // Splitting XML document by lines
        boolean foundRootTag = false;
        String rootTag = null;

        // Process each line of the XML document
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber].trim();
            if (line.isEmpty()) continue;

            // Ignore processing instructions like <?xml version="1.0"?>
            if (line.startsWith("<?") && line.endsWith("?>")) {
                continue;
            }

            int index = 0; // Start processing the line from the beginning
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

                if (tagContent.startsWith("/")) { // Closing tag
                    String tagName = tagContent.substring(1);
                    if (tagStack.isEmpty()) {
                        errors.add("Unmatched closing tag </" + tagName + "> at line " + (lineNumber + 1));
                        lineNumbers.add(lineNumber + 1);
                    } else {
                        String topTag = tagStack.pop();
                        if (!topTag.equals(tagName)) {
                            errors.add("Mismatched closing tag </" + tagName + "> at line " + (lineNumber + 1) +
                                       ", expected </" + topTag + ">");
                            lineNumbers.add(lineNumber + 1);
                        }
                    }
                } else if (tagContent.endsWith("/")) { // Self-closing tag
                    continue; // Nothing to push or pop
                } else { // Opening tag
                    if (!foundRootTag) {
                        rootTag = tagContent;
                        foundRootTag = true;
                    }
                    tagStack.push(tagContent);
                }
            }
        }

        // Check for unclosed tags
        while (!tagStack.isEmpty()) {
            String unclosedTag = tagStack.pop();
            errors.add("Unclosed tag: <" + unclosedTag + ">");
        }

        // Check for missing root tag
        if (!foundRootTag) {
            errors.add("No root tag found.");
        }

        // Display results
        if (errors.size() == 0) {
            System.out.println("XML is well-formed.");
        } else {
            System.out.println("Errors found in the XML document:");
            for (int i = 0; i < errors.size(); i++) {
                System.out.println(errors.get(i));
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
