package com.example.tag.util;

import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeUtils;

import java.io.File;
import java.io.IOException;

public class OfficeParseUtil {


    private static OfficeParseUtil officeParseUtil;

    /**
     * @return OfficeParseUtil
     */
    public static synchronized OfficeParseUtil getOfficeParseUtil() {
        if (officeParseUtil == null) {
            officeParseUtil = new OfficeParseUtil();
        }
        return officeParseUtil;
    }

    public static void main(String[] args) throws IOException, OfficeException {
        File inputFile = new File("test.rtf");
        File outputFile = new File("test1.docx");
        // Create an office manager using the default configuration.
        // The default port is 2002. Note that when an office manager
        // is installed, it will be the one used by default when
        // a converter is created.
//        ((LocalOfficeManager.Builder)builder().install()).build();
        final LocalOfficeManager officeManager = (LocalOfficeManager.builder().officeHome("/opt/openoffice4").killExistingProcess(true)
                .install()).build();
        try {

            // Start an office process and connect to the started instance (on port 2002).
            officeManager.start();
            // Convert
            JodConverter
                    .convert(inputFile)
                    .to(outputFile)
                    .execute();
        } finally {
            // Stop the office process
            OfficeUtils.stopQuietly(officeManager);
        }
    }

}
