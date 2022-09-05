package com.mirceanealcos.confruntarea.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mirceanealcos.confruntarea.entity.Ability;
import com.mirceanealcos.confruntarea.entity.Item;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AbilityPDFExporter {
    private List<Ability> abilities;

    public AbilityPDFExporter(List<Ability> abilities) {
        this.abilities = abilities;
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

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Healing", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Damage", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Mana Cost", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Picture", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("For Champion", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for(Ability ability : abilities) {
            table.addCell(ability.getId().toString());
            table.addCell(ability.getName());
            table.addCell(ability.getType().toString());
            table.addCell(ability.getHealing().toString());
            table.addCell(ability.getDamage().toString());
            table.addCell(ability.getManaCost().toString());
            table.addCell(ability.getPicture());
            table.addCell(ability.getChampion().getName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("confruntarea_abilities", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.2f, 3.5f, 2f, 2.5f, 2.5f,3f, 2f, 3.5f});
        table.setSpacingBefore(30);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
