package ru.shafikova.UnpackingService.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shafikova.UnpackingService.Models.HuffmanCode;
import ru.shafikova.UnpackingService.Models.Node;
import ru.shafikova.UnpackingService.Models.OutputFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnpackingService {

    public OutputFile unpack(MultipartFile file) throws IOException {
        HuffmanCode codedLine = readBinFile(file);
        System.out.println("codedLine.getCode()" + codedLine.getCode());

        String fileName = codedLine.getCode().remove(0);
        System.out.println("fileName " + fileName);

        String fileFormat = codedLine.getCode().remove(0);
        System.out.println("fileFormat " + fileFormat);

        System.out.println(codedLine.getCode());

        Decoder decoder = new Decoder();
        String decodedLine = decoder.unpack(codedLine);

        File outputFile = writeOriginalFile(decodedLine);

        return new OutputFile(outputFile, fileName, fileFormat);
    }

    public HuffmanCode readBinFile(MultipartFile file) throws IOException {
        File tmp = Files.createTempFile("data", null).toFile();
        file.transferTo(tmp);

        List<String> codedLine = new ArrayList<>();

        Node huffmanTree = null;
        try (FileInputStream fileIn = new FileInputStream(tmp);
             HackedObjectInputStream objectIn = new HackedObjectInputStream(fileIn);
             DataInputStream dis = new DataInputStream(fileIn)) {
            huffmanTree = (Node) objectIn.readObject();

            System.out.println("huffmanTree: " + huffmanTree);

            short fileNameLength = dis.readShort();
            StringBuilder fileName = new StringBuilder();
            for (int i = 0; i < fileNameLength; i++) fileName.append(dis.readChar());
            codedLine.add(String.valueOf(fileName));

            System.out.println("fileNameLength: " + fileNameLength);
            System.out.println("fileName: " + fileName);

            short formatLength = dis.readShort();
            StringBuilder fileFormat = new StringBuilder();
            for (int i = 0; i < formatLength; i++) fileFormat.append(dis.readChar());
            codedLine.add(String.valueOf(fileFormat));

            System.out.println("formatLength: " + formatLength);
            System.out.println("fileFormat: " + fileFormat);

            while (dis.available() > 0) {
                int b = dis.read();
//                System.out.println("b " + Integer.toBinaryString(b));
                StringBuilder codedSymbols = new StringBuilder(Integer.toBinaryString(b));
                if (dis.available() > 0) {
                    while (codedSymbols.length() < 8) {
                        codedSymbols.insert(0, '0');
                    }
                }
                System.out.println("codedSymbols " + codedSymbols);
                codedLine.add(String.valueOf(codedSymbols));
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tmp.deleteOnExit();

        return new HuffmanCode(huffmanTree, codedLine);
    }

    public File writeOriginalFile(String text) throws IOException {
//        String fileName = (metadata.getOriginalFileName().split("\\.")[0] + "." + metadata.getOriginalFileName().split("\\.")[1]).replace(" ", "_");
        File decodedFile = Files.createTempFile(null, null).toFile();

        try (FileWriter fileWriter = new FileWriter(decodedFile)) {
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        decodedFile.deleteOnExit();

        return decodedFile;
    }
}
