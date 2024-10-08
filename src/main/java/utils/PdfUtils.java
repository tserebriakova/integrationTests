package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PdfUtils {
    public static String extractPdfContent(byte[] pdf) throws IOException {

        var document = PDDocument.load(new ByteArrayInputStream(pdf));
        try {
            return new PDFTextStripper().getText(document);
        } finally {
            document.close();
        }
    }
}
