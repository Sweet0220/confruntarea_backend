package com.mirceanealcos.confruntarea.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.Item;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ItemPDFExporter {

    private List<Item> items;

    public ItemPDFExporter(List<Item> items) {
        this.items = items;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.yellow);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Bonus Damage", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Base HP", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Picture", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name Color", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for(Item item : items) {
            table.addCell(item.getId().toString());
            table.addCell(item.getName());
            table.addCell(item.getBonusDamage().toString());
            table.addCell(item.getBonusHp().toString());
            table.addCell(item.getPrice().toString());
            table.addCell(item.getType().toString());
            table.addCell(item.getPicture());
            table.addCell(item.getNameColor());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("confruntarea_items", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.2f, 3.5f, 2f, 2.5f, 2.5f,3f, 3.5f, 2f});
        table.setSpacingBefore(30);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

}
