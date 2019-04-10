package com.example.tag.util;

import com.hsbc.carm.ha.WebAppComvert;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class RTFUtil {

    public static String toRtfString(Map<String, List<Map<String, byte[]>>> map) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            List<Map<String, byte[]>> list = map.get(key);
            for (Map<String, byte[]> dataMap : list) {
                for (String dataKey : dataMap.keySet()) {
                    byte[] bytes = dataMap.get(dataKey);
                    String newStr = new String(WebAppComvert.getUnicodeString(bytes).getBytes(), "utf-8");
                    if (dataKey.equals("1")) {
                        System.out.println(newStr);
                    }
                    sb.append(newStr);
                }
            }
        }
        return sb.toString();
    }

    /**
     * toRtf
     *
     * @throws SQLException
     */
    public static String toRtf(Map<String, List<Map<String, byte[]>>> map) throws IOException {
        String rtfName = "";
        for (String key : map.keySet()) {
            List<Map<String, byte[]>> list = map.get(key);
            File tempFile = new File("tempFile");
            tempFile.delete();
            tempFile.createNewFile();
            FileOutputStream tempFileOut = new FileOutputStream(tempFile);
            for (Map<String, byte[]> dataMap : list) {
                for (String dataKey : dataMap.keySet()) {
                    byte[] bytes = dataMap.get(dataKey);
                    String newStr = new String(WebAppComvert.getUnicodeString(bytes).getBytes(), "utf-8");
                    System.out.println(dataKey);
                    System.out.println(dataKey.equals("1"));
                    System.out.println(new String(newStr.getBytes("iso-8859-1"), "utf-8"));
                    tempFileOut.write(newStr.getBytes("iso-8859-1"));
                }
            }
            tempFileOut.flush();
            tempFileOut.close();

            // Use Gzip to unzip temp file and write data to rtf file
            FileInputStream tempFileIn = new FileInputStream(tempFile);
            File destFile = new File("./tempFiles/" + key + ".rtf");
            destFile.delete();
            destFile.createNewFile();

            GZIPInputStream gzip = null;
            FileOutputStream destFileOut = new FileOutputStream(destFile);
            try {
                gzip = new GZIPInputStream(tempFileIn);
                writeToOutputStream(gzip, destFileOut, true);
                gzip.close();
            } catch (Exception e) {
                e.printStackTrace();
                writeToOutputStream(tempFileIn, destFileOut, true);
            }
            tempFileIn.close();

            // remove temp file
            tempFile.delete();

            rtfName = destFile.getPath();
        }
        return rtfName;
    }

    public static String coverToHtml(Reader rtf) throws IOException, BadLocationException {
        JEditorPane p = new JEditorPane();
        p.setContentType("text/rtf");
        EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
        try {
            kitRtf.read(rtf, p.getDocument(), 0);
            kitRtf = null;
            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
            return writer.toString();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String coverToHtmlByStr(String str) throws IOException, BadLocationException {
        JEditorPane p = new JEditorPane();
        p.setContentType("text/rtf");
        EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
        kitRtf.read(new ByteArrayInputStream(str.getBytes("utf-8")), p.getDocument(), 0);
        EditorKit kitHtml = p.getEditorKitForContentType("text/html");
        Writer writer = new StringWriter();
        kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
        return writer.toString();
    }

    /**
     * Write data from inputStream to outputStream.
     *
     * @param in
     * @param out
     * @param closeStreams
     * @throws IOException
     */
    private static void writeToOutputStream(InputStream in, OutputStream out, boolean closeStreams) throws IOException {
        byte[] buffer = new byte[2048];
        int bytesRead;
        bytesRead = in.read(buffer, 0, buffer.length);
        while (bytesRead != -1) {
            out.write(buffer, 0, bytesRead);
            bytesRead = in.read(buffer, 0, buffer.length);
        }
        out.flush();
        if (closeStreams) {
            out.close();
            in.close();
        }
    }

    public static void coverToDocx(String filePath, String outputPath) throws Exception {
        WordprocessingMLPackage target = WordprocessingMLPackage.createPackage();
        MainDocumentPart main = target.getMainDocumentPart();
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(AltChunkType.Rtf);
        afiPart.setContentType(new ContentType("application/rtf"));
        Relationship altChunkRel = main.addTargetPart(afiPart, RelationshipsPart.AddPartBehaviour.RENAME_IF_NAME_EXISTS);
        afiPart.registerInContentTypeManager();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] byteArray;
        try {
            GZIPInputStream gzip = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            writeToOutputStream(gzip, baos, false);
            byteArray = baos.toByteArray();
        } catch (ZipException ex) {
            byteArray = bytes;
        }
        afiPart.setBinaryData(byteArray);
        CTAltChunk ac = Context.getWmlObjectFactory().createCTAltChunk();
        ac.setId(altChunkRel.getId());
        main.addObject(ac);
        target.save(new File(outputPath));
    }
}
