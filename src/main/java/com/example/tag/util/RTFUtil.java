package com.example.tag.util;

import com.hsbc.carm.ha.WebAppComvert;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RTFUtil {

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
            FileOutputStream tempFileOut = null;
            tempFileOut = new FileOutputStream(tempFile);
            tempFile.delete();
            tempFile.createNewFile();
            for (Map<String, byte[]> dataMap : list) {
                for (String dataKey : dataMap.keySet()) {
                    byte[] bytes = dataMap.get(dataKey);
                    String newStr = WebAppComvert.getUnicodeString(bytes);
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
                writeToOutputStream(tempFileIn, destFileOut, true);
            }
            tempFileIn.close();
            // remove temp file
            tempFile.delete();
            rtfName +=  key;
        }
        return rtfName;
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
}
