/*
 * (swing1.1beta3)
 * 
 */


import java.awt.*;


/**
 * @version 1.0 11/22/98
 */

public class MyCellAttribute
        implements CellAttribute, CellSpan {
    //implements CellAttribute ,CellSpan ,ColoredCell ,CellFont {

    //
    // !!!! CAUTION !!!!!
    // these values must be synchronized to Table data
    //
    protected int rowSize;
    protected int columnSize;
    protected int[][][] span;                   // CellSpan


    public MyCellAttribute(int numRows, int numColumns) {
        setSize(new Dimension(numRows, numColumns));
        System.out.println(rowSize + " " + columnSize);
    }

    protected void initValue() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                span[i][j][CellSpan.COLUMN] = 1;
                span[i][j][CellSpan.ROW] = 1;
            }
        }
    }


    //
    // CellSpan
    //
    public int[] getSpan(int row, int column) {
        if (isOutOfBounds(row, column)) {
            int[] ret_code = {1, 1};
            return ret_code;
        }
        return span[row][column];
    }

    public void setSpan(int[] span, int row, int column) {
        if (isOutOfBounds(row, column)) return;
        this.span[row][column] = span;
    }

    public boolean isVisible(int row, int column) {
        if (isOutOfBounds(row, column)) return false;
        if ((span[row][column][CellSpan.COLUMN] != 1)
                || (span[row][column][CellSpan.ROW] != 1)) return false;
        return true;
    }

    public void combine(int[] rows, int[] columns) {
        System.out.println("Try to combine " + rowSize + " " + columnSize);

        if (isOutOfBounds(rows, columns)) return;
        System.out.println("Try to combine");
        int rowSpan = rows.length;
        int columnSpan = columns.length;
        int startRow = rows[0];
        int startColumn = columns[0];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                if ((span[startRow + i][startColumn + j][CellSpan.COLUMN] != 1)
                        || (span[startRow + i][startColumn + j][CellSpan.ROW] != 1)) {
                    System.out.println("can't combine " + i + " " + j + " " + span[startRow + i][startColumn + j][CellSpan.COLUMN] + " " + span[startRow + i][startColumn + j][CellSpan.ROW]);
                    return;
                }
            }
        }
        for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
            for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
                span[startRow + i][startColumn + j][CellSpan.COLUMN] = jj;
                span[startRow + i][startColumn + j][CellSpan.ROW] = ii;
                //System.out.println("r " +ii +"  c " +jj);
            }
        }
        span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
        span[startRow][startColumn][CellSpan.ROW] = rowSpan;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                System.out.print(span[i][j][CellSpan.COLUMN] + " ");
            }
            System.out.println();

        }

    }

    public void split(int row, int column) {
        if (isOutOfBounds(row, column)) return;
        int columnSpan = span[row][column][CellSpan.COLUMN];
        int rowSpan = span[row][column][CellSpan.ROW];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                span[row + i][column + j][CellSpan.COLUMN] = 1;
                span[row + i][column + j][CellSpan.ROW] = 1;
            }
        }
    }


    //
    // CellAttribute
    //
    public void addColumn() {
        int[][][] oldSpan = span.clone();
        int numRows = rowSize;
        int numColumns = columnSize;
        span = new int[numRows][numColumns + 1][2];
        System.arraycopy(oldSpan, 0, span, 0, numRows);
        for (int i = 0; i < numRows; i++) {
            span[i][numColumns][CellSpan.COLUMN] = 1;
            span[i][numColumns][CellSpan.ROW] = 1;
        }
        columnSize++;
    }

    public void addRow() {
        int[][][] oldSpan = span.clone();
        int numRows = rowSize;
        int numColumns = columnSize;
        span = new int[numRows + 1][numColumns][2];
        System.arraycopy(oldSpan, 0, span, 0, numRows);
        for (int i = 0; i < numColumns; i++) {
            span[numRows][i][CellSpan.COLUMN] = 1;
            span[numRows][i][CellSpan.ROW] = 1;
        }
        rowSize++;
        System.out.println("!!" + span[0][0][0]);
    }

    public void insertRow(int row) {
        int[][][] oldSpan = span.clone();
        int numRows = rowSize;
        int numColumns = columnSize;
        span = new int[numRows + 1][numColumns][2];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < columnSize; j++) {
                span[i][j][CellSpan.COLUMN] = oldSpan[i][j][CellSpan.COLUMN];
                span[i][j][CellSpan.ROW] = oldSpan[i][j][CellSpan.ROW];
            }
        }
        for (int i = row; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                span[i + 1][j][CellSpan.COLUMN] = oldSpan[i][j][CellSpan.COLUMN];
                span[i + 1][j][CellSpan.ROW] = oldSpan[i][j][CellSpan.ROW];
            }
        }

//      System.arraycopy(oldSpan,0,span,row,numRows - row);
        for (int i = 0; i < numColumns; i++) {
            span[row][i][CellSpan.COLUMN] = 1;
            span[row][i][CellSpan.ROW] = 1;
        }
        //System.out.println("!!" + span[0][0][0]);
        rowSize++;
    }

    public Dimension getSize() {
        return new Dimension(rowSize, columnSize);
    }

    public void setSize(Dimension size) {
        columnSize = size.height;
        rowSize = size.width;
        span = new int[rowSize][columnSize][2];   // 2: COLUMN,ROW
        initValue();
    }

    /*
    public void changeAttribute(int row, int column, Object command) {
    }

    public void changeAttribute(int[] rows, int[] columns, Object command) {
    }
    */


    protected boolean isOutOfBounds(int row, int column) {
        if ((row < 0) || (rowSize <= row)
                || (column < 0) || (columnSize <= column)) {
            return true;
        }
        return false;
    }

    protected boolean isOutOfBounds(int[] rows, int[] columns) {
        for (int i = 0; i < rows.length; i++) {
            if ((rows[i] < 0) || (rowSize <= rows[i])) return true;
        }
        for (int i = 0; i < columns.length; i++) {
            if ((columns[i] < 0) || (columnSize <= columns[i])) return true;
        }
        return false;
    }

    protected void setValues(Object[][] target, Object value,
                             int[] rows, int[] columns) {
        for (int i = 0; i < rows.length; i++) {
            int row = rows[i];
            for (int j = 0; j < columns.length; j++) {
                int column = columns[j];
                target[row][column] = value;
            }
        }
    }
}
