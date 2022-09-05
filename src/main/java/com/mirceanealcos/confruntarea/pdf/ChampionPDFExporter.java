package com.mirceanealcos.confruntarea.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ChampionPDFExporter {
    private List<Champion> champions;

    public ChampionPDFExporter(List<Champion> champions) {
        this.champions = champions;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.yellow);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("HP", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Base Damage", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Mana", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Picture", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name Color", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for(Champion champion : champions) {
            table.addCell(champion.getId().toString());
            table.addCell(champion.getName());
            table.addCell(champion.getHp().toString());
            table.addCell(champion.getBaseDamage().toString());
            table.addCell(champion.getPrice().toString());
            table.addCell(champion.getMana().toString());
            table.addCell(champion.getPicture());
            table.addCell(champion.getNameColor());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("confruntarea_champions", font);
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
