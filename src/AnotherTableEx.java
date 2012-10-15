import javax.swing.*;
import javax.swing.table.AbstractTableModel;

class AnotherTableEx extends JFrame
{
    public AnotherTableEx()
    {
        final TransparentJTable table = new TransparentJTable(new MyTableModel());
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        setSize(200, 200);
        show();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new AnotherTableEx();
    }


    class MyTableModel extends AbstractTableModel
    {
        Object[][] data = {
                {"Calories",    "130", "calories"},
                {"Total Fat",   "0", "%"},
                {"Sodium",      "1", "%"},
                {"Total Carbs", "12", "%"},
        };

        String[] columns = {"Item", "Amount", "Units"};
        //String[] rows = {"AAA", "BBB", "CCCC", "DFFF"};

        public int getColumnCount()
        { return data[0].length; }

        public int getRowCount()
        { return data.length; }

        public String getColumnName(int c)
        { return columns[c]; }
       /* public String getRowName(int c)
        { return rows[c]; }*/

        public Object getValueAt(int rowIndex, int colIndex)
        { return data[rowIndex][colIndex]; }

        public boolean isCellEditable(int rowIndex, int colIndex)
        { return false; }
    }
}
