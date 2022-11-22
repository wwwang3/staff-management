package top.wang3.staffmanagement.ui.mode;

import top.wang3.staffmanagement.model.Constants;

import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Vector;

public class StaffInfoTableModel extends DefaultTableModel {

    private StaffInfoTableModel() {
        super();
    }

    public StaffInfoTableModel(Vector<? extends Vector> data, String[] columnNames) {
       super(data, convertToVector(columnNames));
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //in to better sort
        Class<?> clazz;
        if (columnIndex >= 0 && columnIndex < getColumnCount()) {
            clazz = getValueAt(0, columnIndex).getClass();
        } else {
            clazz = Object.class;
        }
        return clazz;
    }

    @Override
    public Object getValueAt(int row, int column) {
        String name = super.getColumnName(column);
        Object value = super.getValueAt(row, column);
        if ("年龄".equals(name)) {
            return (System.currentTimeMillis() - ((Date) value).getTime())/ Constants.YEAR_MIllIS;
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        //forbid cell edit
        return false;
    }

    public int getHeaderIndex(String header) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (getColumnName(i).equals(header)) {
                return i;
            }
        }
        throw new NoSuchElementException("没有该列字段");
    }

    public Vector<Object> convertToV(String[] columns) {
        return convertToVector(columns);
    }
}
