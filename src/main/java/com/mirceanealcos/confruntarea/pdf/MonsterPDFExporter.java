package com.mirceanealcos.confruntarea.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Monster;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MonsterPDFExporter {
    private List<Monster> monsters;

    public MonsterPDFExporter(List<Monster> monsters) {
        this.monsters = monsters;
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

        cell.setPhrase(new Phrase("Level", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("HP", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Base Damage", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Money Reward", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Exp Reward", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Picture", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name Color", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for(Monster monster : monsters) {
            table.addCell(monster.getId().toString());
            table.addCell(monster.getName());
            table.addCell(monster.getLevel().toString());
            table.addCell(monster.getHp().toString());
            table.addCell(monster.getBaseDamage().toString());
            table.addCell(monster.getMoneyReward().toString());
            table.addCell(monster.getExpReward().toString());
            table.addCell(monster.getPicture());
            table.addCell(monster.getNameColor());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("confruntarea_monsters", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.2f, 3.5f, 2f, 2.5f, 2.5f,3f, 2f, 3.5f, 2.5f});
        table.setSpacingBefore(30);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
