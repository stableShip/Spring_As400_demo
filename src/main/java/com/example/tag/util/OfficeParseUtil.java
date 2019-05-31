package com.example.tag.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import java.io.File;
import java.net.ConnectException;
import java.nio.file.Paths;

public class OfficeParseUtil {

    public static String coverToPdf(String filePath, String outputPath) throws ConnectException {
        if (Paths.get(filePath).toFile().exists()) {
            File inputFile = new File(filePath);
            File outputFile = new File(outputPath);
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            // convert
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            connection.disconnect();
            return outputPath;
        } else {
            return "";
        }
    }

}
