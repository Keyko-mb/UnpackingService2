package ru.shafikova.UnpackingService.Models;

import java.util.List;

public class HuffmanCode {
    private Node huffmanTree;
    private List<String> code;

    public HuffmanCode(Node huffmanTree, List<String> code) {
        this.huffmanTree = huffmanTree;
        this.code = code;
    }

    public Node getHuffmanTree() {
        return huffmanTree;
    }

    public void setHuffmanTree(Node huffmanTree) {
        this.huffmanTree = huffmanTree;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }
}
