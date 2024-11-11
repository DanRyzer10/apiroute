package com.danam.iroute.apiroute.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class ReportWriter {

    private List<Map<String,String>> data;
    public ReportWriter(List<Map<String,String>> data){
        this.data = data;
    }
    public byte[] createReport() {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            if (data == null || data.isEmpty()) {
                throw new IllegalArgumentException("Data is null or empty");
            }

            PdfPTable table = new PdfPTable(this.data.get(0).size());
            data.forEach(row -> {
                row.forEach((key, value) -> {
                    PdfPCell keyCell = new PdfPCell();
                    PdfPCell valueCell = new PdfPCell();
                    keyCell.setBorderWidth(2);
                    keyCell.setPhrase(new Phrase(key));
                    valueCell.setBorderWidth(2);
                    valueCell.setPhrase(new Phrase(value));
                    table.addCell(key);
                    table.addCell(value);
                });
            });
            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void AddHeaders(PdfPTable table){
        data.get(0).forEach((key, value) -> {
            table.addCell(key);
        });
    }

    private void AddColumns(PdfPTable table){
        data.forEach(row -> {
            row.forEach((key, value) -> {
                table.addCell(value);
            });
        });
    }
}
