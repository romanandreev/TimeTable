import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Vector;


public class RowHeaderTable
{
    public static void main(String[] args)
    {
        DefaultTableModel headerData = new DefaultTableModel(0, 1);
        DefaultTableModel data = new DefaultTableModel(0, 6);

        for (int i = 0; i < 30; i++)
        {
            headerData.addRow(new Object[] { "Line: "+i } );

            Vector v = new Vector();

            for (int k = 0; k < 6; k++)
                v.add(new Float(k / (float)i));

            data.addRow(v);
        }

        JTable table = new JTable(data);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        JTable rowHeader = new JTable(headerData){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        //table = new JTable(model)

        LookAndFeel.installColorsAndFont
            (rowHeader, "TableHeader.background", 
            "TableHeader.foreground", "TableHeader.font");

        
        rowHeader.setIntercellSpacing(new Dimension(0, 0));
        Dimension d = rowHeader.getPreferredScrollableViewportSize();
        d.width = rowHeader.getPreferredSize().width;
        rowHeader.setPreferredScrollableViewportSize(d);
        rowHeader.setRowHeight(table.getRowHeight());
        rowHeader.setDefaultRenderer(Object.class, new RowHeaderRenderer());
        rowHeader.setRowSelectionAllowed(false);
        rowHeader.setColumnSelectionAllowed(false);
        rowHeader.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(rowHeader);
        new RowHeaderResizer(scrollPane).setEnabled(true);
        JTableHeader corner = rowHeader.getTableHeader();
        corner.setReorderingAllowed(false);
        corner.setResizingAllowed(false);
        corner.setForeground(Color.WHITE);
        corner.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);


        JFrame f = new JFrame("Row Header Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);

        f.pack();
        f.setLocation(200, 100);
        f.setVisible(true);

    }
}
