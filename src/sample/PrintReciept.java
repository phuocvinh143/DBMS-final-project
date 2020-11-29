package sample;

import java.awt.*;
import java.awt.print.*;

public class PrintReciept {
    public static boolean printcard(final String bill) {
        final PrinterJob job = PrinterJob.getPrinterJob();
        Printable contentToprint = new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
                String[] billz = bill.split(";");
                int y = 15;
                for (int i = 0; i < billz.length; ++i) {
                    graphics.drawString(billz[i], 5, y);
                    y = y + 15;
                }
                if (pageIndex > 0) return NO_SUCH_PAGE;
                return PAGE_EXISTS;
            }
        };
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(pageFormat.PORTRAIT);
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()-2);
        pageFormat.setPaper(paper);
        job.setPrintable(contentToprint, pageFormat);
        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
        return true;
    }
}
