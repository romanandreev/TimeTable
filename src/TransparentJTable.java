import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class TransparentJTable extends JTable implements MouseMotionListener, MouseListener
{
    protected Point begin = new Point();
    protected Point end = new Point();
    boolean canSelect = false;
    public TransparentJTable(TableModel tm)
    {
        super(tm);
        setOpaque(false);
        addMouseListener( this );
        addMouseMotionListener( this );
    }

    public void mouseClicked(MouseEvent me)
    { }

    public void mousePressed(MouseEvent me)
    {
        begin = me.getPoint();
    }

    public void mouseReleased(MouseEvent me)
    { }

    public void mouseEntered(MouseEvent me)
    { canSelect = true;}

    public void mouseExited(MouseEvent me)
    { canSelect = false; }
    int getRow(int x) {
        return Math.min(Math.max(x, 0), getRowCount() - 1);
    }
    int getColumn(int x) {
        return Math.min(Math.max(x, 0), getColumnCount() - 1);
    }

    public void mouseDragged(MouseEvent me)
    {
        if (canSelect) {
        end = me.getPoint();
        update(getGraphics());
        }
        //getGraphics().drawRect(begin.x, begin.y, end.x - begin.x, end.y - begin.y);

        addRowSelectionInterval(getRow(rowAtPoint(begin)), getRow(rowAtPoint(end)));
        addColumnSelectionInterval(getColumn(columnAtPoint(begin)), getColumn(columnAtPoint(end)));
    }

    public void mouseMoved(MouseEvent me)
    { }
}
