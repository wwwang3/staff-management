package top.wang3.staffmanagement.ui;
import top.wang3.staffmanagement.model.Constants;
import top.wang3.staffmanagement.model.Staff;
import top.wang3.staffmanagement.service.StaffService;
import top.wang3.staffmanagement.service.impl.StaffServiceImpl;
import top.wang3.staffmanagement.ui.mode.StaffInfoTableModel;
import top.wang3.staffmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;

import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 员工信息操作界面
 */
public class StaffInfoTable extends JFrame {

    private static final String[] TABLE_COLUMNS =
            {"员工编号", "姓名", "性别", "年龄", "手机号", "部门", "雇佣日期", "薪水"};

    private static final Font DEFAULT_FONT = new Font(Constants.FONT_TYPE, Font.PLAIN, Constants.FONT_SIZE);

    private static final String APP_TITLE = "员工管理系统";
    //width
    private static final int WIDTH = 960;
    //height
    private static final int HEIGHT = 540;

    private final StaffService staffService = new StaffServiceImpl();

    private final SearchTextListener searchTextListener = new SearchTextListener();
    //新增按钮
    private JButton addButton;
    //编辑按钮
    private JButton editButton;
    //删除按钮
    private JButton deleteButton;

    //员工信息表格
    private JTable staffTable;

    private Vector<Vector<Object>> staffInfo;

    private final JComboBox<String> searchType = new JComboBox<String>() {{
        addItem("姓名");
        addItem("编号");
    }};

    private JTextField searchText;
    private JButton searchButton;
    //行高
    private int rowHeight = 30;

    public StaffInfoTable() {
        super();
    }

    private JPanel initSearchFrame() {
        this.searchText = new JTextField(10);
        this.searchText.setPreferredSize(new Dimension(64, 32));
        this.searchText.setFont(DEFAULT_FONT);

        this.searchText.getDocument().addDocumentListener(searchTextListener);

        this.searchType.setSize(42, 36);
        this.searchType.setFont(DEFAULT_FONT);
        this.searchType.addItemListener((e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.searchText.setText("");
            }
        }));

        this.searchButton = ComponentUtils.buildButton("查询");
        this.searchButton.addActionListener((e) -> handleSearch());

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(6);
        JPanel p = new JPanel();
        p.setLayout(layout);
        p.add(this.searchType);
        p.add(this.searchText);
        p.add(this.searchButton);
        return p;
    }

    private JPanel initButtons() {
        addButton = ComponentUtils.buildButton("新增");
        editButton = ComponentUtils.buildButton("编辑");
        deleteButton = ComponentUtils.buildButton("删除");
        //时间监听
        addButton.addActionListener((e) -> handleAdd());
        editButton.addActionListener((e) -> handleEdit());
        deleteButton.addActionListener((e) -> {
            try {
                handleDelete();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //左对齐
        FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
        flowLayout.setHgap(6);
        JPanel panel = new JPanel();
        panel.setLayout(flowLayout);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.setVisible(true);
        return panel;
    }

    public JScrollPane initTable() {
        this.staffInfo = getAllStaff();
        TableModel tableModel = new StaffInfoTableModel(this.staffInfo, TABLE_COLUMNS);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        staffTable = new JTable(tableModel);
        staffTable.setRowSorter(sorter);
        staffTable.setRowHeight(rowHeight);
        staffTable.setFont(new Font(Constants.FONT_TYPE, Font.PLAIN, Constants.FONT_SMALL_SIZE));
        staffTable.getSelectionModel().addListSelectionListener((e) -> {
            this.handleSelect();
        });
        //头部
        JTableHeader header = staffTable.getTableHeader();
        header.setFont(DEFAULT_FONT);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        setCellWidth(staffTable.getColumnModel());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(staffTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    public JPanel build() {
        JPanel s = initSearchFrame();
        JPanel b = initButtons();
        JScrollPane t = initTable();
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        JPanel p = new JPanel(layout);
        p.add(s);
        p.add(ComponentUtils.getGapC());
        p.add(b);
        t.setPreferredSize(new Dimension(824, 360));
        Box box = Box.createVerticalBox();
        box.add(ComponentUtils.getGapC());
        box.add(p);
        box.add(ComponentUtils.getSmallGapC());
        box.add(t);

        JPanel panel = new JPanel();
        panel.add(box);
        return panel;
    }

    public void buildFrame() {
        JPanel panel = build();
        this.add(panel, BorderLayout.CENTER);
        showInCenter();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void showInCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(APP_TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocation((int) (screenSize.getWidth() - WIDTH) / 2,
                (int) (screenSize.getHeight() - HEIGHT) / 2);
    }

    private DefaultTableCellRenderer centerCellRenderer() {
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        return r;
    }

    private void setCellWidth(TableColumnModel model) {
        int count = model.getColumnCount();
        for (int i = 0; i < count; i++) {
            TableColumn column = model.getColumn(i);
            column.setMaxWidth(160);
            column.setCellRenderer(centerCellRenderer());
            column.setResizable(false);
        }
    }

    private Vector<Vector<Object>> getAllStaff() {
        List<Staff> staffs = staffService.getAllStaff();
        int size = Staff.class.getDeclaredFields().length;
        return staffs.parallelStream().map((staff -> {
            Vector<Object> vector = new Vector<>(size);
            vector.add(staff.getId());
            vector.add(staff.getName());
            vector.add(staff.getSex());
            vector.add(staff.getBirth());
            vector.add(staff.getPhone());
            vector.add(staff.getDepartment());
            vector.add(staff.getHireTime());
            vector.add(staff.getSalary());
            return vector;
        })).collect(() -> new Vector<>(staffs.size()), Vector::add, Vector::addAll);
    }

    private void handleClose() {
        int option = JOptionPane.showConfirmDialog(this, "确定退出系统?", "提示",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.dispose();
            System.exit(0);
        }
    }

    @SuppressWarnings({"unchecked"})
    private void handleSearch() {
        if (this.staffTable.getModel().getRowCount() <= 0) {
            //no data, return
            return;
        }
        String type = (String) this.searchType.getSelectedItem();
        final String t = "编号".equals(type) ? "员工编号" : type;
        String condition = this.searchText.getText();
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) this.staffTable.getRowSorter();
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                if (condition == null || condition.isBlank()) {
                    return true;
                }
                try {
                    StaffInfoTableModel model = (StaffInfoTableModel) entry.getModel();
                    //获取编号或姓名列的索引
                    int index = model.getHeaderIndex(t);
                    //full match
                    //比较编号或者姓名这一列单元格的内容是否匹配condition(输入的编号姓名);
                    //entry内部维护了行的的索引，传入列索引获取对应单元格的值
                    return entry.getStringValue(index).equals(condition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    private void handleSelect() {
        this.editButton.setEnabled(this.staffTable.getSelectedRowCount() <= 1);
    }

    private void handleAdd() {
        try {
            EditStaffFrame.of(this, "新增员工");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "internal error",
                    "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEdit() {
        if (this.staffTable.getSelectedRowCount() == 0) {
            return;
        }
        //获取选中的行对应model的索引
        int index = this.staffTable.convertRowIndexToModel(this.staffTable.getSelectedRow());
        try {
            EditStaffFrame.of(this, "更新信息", this.staffInfo.get(index));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "internal error",
                    "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int[] rows = this.staffTable.getSelectedRows();
        if (rows != null && rows.length > 0) {
            String msg = "确定删除这" + rows.length + "个员工?";
            int option = JOptionPane.showConfirmDialog(this, msg, "提示", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                ArrayList<Integer> ids = new ArrayList<>();
                int size = rows.length;
                for (int i = 0; i < size; i++) {
                    //由于表格可以排序
                    //需要获取选中行的索引实际在TableModel中的索引
                    rows[i] = this.staffTable.convertRowIndexToModel(rows[i]);
                    ids.add((int) this.staffInfo.get(rows[i]).get(0));
                }
                //异步删除
                CompletableFuture.runAsync(() -> this.staffService.deleteStaffByIds(ids));
                //排序要删除行的索引
                Arrays.sort(rows);
                StaffInfoTableModel model = (StaffInfoTableModel) this.staffTable.getModel();
                for (int i = size - 1; i >= 0; i--) {
                    //从后往前删除，防止索引越界
                    model.removeRow(rows[i]);
                }
                this.searchText.setText("");
                JOptionPane.showMessageDialog(this, "删除成功", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void refreshModel() {
        //refresh table data
        this.staffInfo = getAllStaff();
        TableModel newModel = new StaffInfoTableModel(this.staffInfo, TABLE_COLUMNS);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(newModel);
        staffTable.setModel(newModel);
        staffTable.setRowSorter(sorter);
        JTableHeader header = staffTable.getTableHeader();
        header.setFont(DEFAULT_FONT);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        setCellWidth(staffTable.getColumnModel());
        staffTable.updateUI();
    }

    class SearchTextListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if ("".equals(StaffInfoTable.this.searchText.getText())) {
                StaffInfoTable.this.handleSearch();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if ("".equals(StaffInfoTable.this.searchText.getText())) {
                StaffInfoTable.this.handleSearch();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if ("".equals(StaffInfoTable.this.searchText.getText())) {
                StaffInfoTable.this.handleSearch();
            }
        }
    }

}
