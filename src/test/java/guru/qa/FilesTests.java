package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesTests {
    private static final String
            CSVFILE = "books.csv",
            XLSXFILE = "tracks.xlsx",
            PDFFILE = "tracks.pdf";

    @Test
    void readZip() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/homework.zip");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains("csv")) {
                assertThat(entry.getName()).isEqualTo(CSVFILE);
                parseCsvTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("xlsx")) {
                assertThat(entry.getName()).isEqualTo(XLSXFILE);
                parseXlsTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("pdf")) {
                assertThat(entry.getName()).isEqualTo(PDFFILE);
                parsePdfTest(zipFile.getInputStream(entry));
            }
        }
    }

    void parseCsvTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file));) {
            List<String[]> strA = reader.readAll();
            assertThat(strA.get(2)).contains(
                    "Data Smart", "Foreman, John"
            );
        }
    }

    void parseXlsTest(InputStream file) throws Exception {
        XLS xls = new XLS(file);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(5)
                .getCell(0)
                .getStringCellValue()).contains("HUNTR");

    }

    void parsePdfTest(InputStream file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains("The Silence,Heroic");

    }


}

