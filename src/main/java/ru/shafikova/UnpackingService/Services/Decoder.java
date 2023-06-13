package ru.shafikova.UnpackingService.Services;

import ru.shafikova.UnpackingService.Models.HuffmanCode;
import ru.shafikova.UnpackingService.Models.Node;

public class Decoder {

    public String unpack(HuffmanCode huffmanCode) {
        Node root = huffmanCode.getHuffmanTree();
        System.out.println(root);

        String codedLine = String.join("", huffmanCode.getCode());

        String lineAfterDecoding = decoding(root, codedLine);
        System.out.println("Строка после декодирования " + lineAfterDecoding);

        return lineAfterDecoding;
    }

    public String decoding(Node root, String line) {
        StringBuilder originalLine = new StringBuilder();
        Node temporalNode = root;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '0') {
                temporalNode = temporalNode.getLeftChild();
            } else temporalNode = temporalNode.getRightChild();

            if (temporalNode.getfData() != null) {
                originalLine.append(temporalNode.getfData());
                temporalNode = root;
            }
        }
        return originalLine.toString();
    }

}
