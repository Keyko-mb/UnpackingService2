package ru.shafikova.UnpackingService.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shafikova.UnpackingService.Models.HuffmanCode;
import ru.shafikova.UnpackingService.Models.Node;
import ru.shafikova.UnpackingService.Models.OutputFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UnpackingService {

    public OutputFile unpack(byte[] bytes) throws IOException {
        HuffmanCode codedLine = readBinFile(bytes);

        String fileName = codedLine.getCode().remove(0);
        System.out.println("fileName " + fileName);

        String fileFormat = codedLine.getCode().remove(0);
        System.out.println("fileFormat " + fileFormat);

        Decoder decoder = new Decoder();
        String decodedLine = decoder.unpack(codedLine);
        log.info("File {}{} is unpacked", fileName, fileFormat);

        File outputFile = writeOriginalFile(decodedLine);

        return new OutputFile(outputFile, fileName, fileFormat);
    }

    public HuffmanCode readBinFile(byte[] bytes) throws IOException {
        File tmp = Files.createTempFile("data", null).toFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(tmp)) {
            fileOutputStream.write(bytes);
        }

        List<String> codedLine = new ArrayList<>();

        Node huffmanTree = null;
        try (FileInputStream fileIn = new FileInputStream(tmp);
             HackedObjectInputStream objectIn = new HackedObjectInputStream(fileIn);
             DataInputStream dis = new DataInputStream(fileIn)) {
            huffmanTree = (Node) objectIn.readObject();

            log.info("Huffman tree was read");

            short fileNameLength = dis.readShort();
            StringBuilder fileName = new StringBuilder();
            for (int i = 0; i < fileNameLength; i++) fileName.append(dis.readChar());
            codedLine.add(String.valueOf(fileName));

            short formatLength = dis.readShort();
            StringBuilder fileFormat = new StringBuilder();
            for (int i = 0; i < formatLength; i++) fileFormat.append(dis.readChar());
            codedLine.add(String.valueOf(fileFormat));

            while (dis.available() > 0) {
                int b = dis.read();
                StringBuilder codedSymbols = new StringBuilder(Integer.toBinaryString(b));
                if (dis.available() > 0) {
                    while (codedSymbols.length() < 8) {
                        codedSymbols.insert(0, '0');
                    }
                }
                codedLine.add(String.valueOf(codedSymbols));
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Reading error {}", e.getMessage());
        }

        tmp.deleteOnExit();

        log.info("Huffman code wad read");

        return new HuffmanCode(huffmanTree, codedLine);
    }

    public File writeOriginalFile(String text) throws IOException {
        File decodedFile = Files.createTempFile(null, null).toFile();

        try (FileWriter fileWriter = new FileWriter(decodedFile)) {
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Writing error {}", e.getMessage());
        }

        decodedFile.deleteOnExit();

        log.info("Unpacked text was written");

        return decodedFile;
    }
}
