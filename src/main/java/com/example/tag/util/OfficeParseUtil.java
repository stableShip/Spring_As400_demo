package com.example.tag.util;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.io.IOException;

public class OfficeParseUtil {


    public static void main(String[] args) throws IOException {
        File inputFile = new File("test.rtf");
        File outputFile = new File("test1.pdf");
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();

        // convert
        OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        connection.disconnect();
    }

}
