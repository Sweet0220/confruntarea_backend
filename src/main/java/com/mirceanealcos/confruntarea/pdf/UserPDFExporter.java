package com.mirceanealcos.confruntarea.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mirceanealcos.confruntarea.entity.User;
import org.springframework.security.core.parameters.P;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter {
    private List<User> users;

    public UserPDFExporter(List<User> users) {
        this.users = users;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.yellow);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Username", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Role", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Funds", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Picture", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Level", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("EXP", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Activated", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for(User user : users) {
            table.addCell(user.getId().toString());
            table.addCell(user.getUsername());
            table.addCell(user.getEmail());
            table.addCell(user.getRole());
            table.addCell(user.getFunds().toString());
            table.addCell(user.getPicture());
            table.addCell(user.getLevel().toString());
            table.addCell(user.getExp().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("confruntarea_users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.2f, 3.5f, 3.5f, 2.5f, 2.5f,3f, 2f, 2f, 2f});
        table.setSpacingBefore(30);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
