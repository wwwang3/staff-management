package top.wang3.staffmanagement.ui;

import top.wang3.staffmanagement.model.Constants;
import top.wang3.staffmanagement.model.Staff;
import top.wang3.staffmanagement.service.StaffService;
import top.wang3.staffmanagement.service.impl.StaffServiceImpl;
import top.wang3.staffmanagement.util.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

public class EditStaffFrame extends JDialog {

    private final StaffService staffService = new StaffServiceImpl();
    private static final int WIDTH = 360;
    private static final int HEIGHT = 500;

    private static final Vector<String> DEPARTMENTS = new Vector<String>() {{
        addAll(Arrays.asList("FBI", "NASA", "CIA", "董事会", "秘书局"));
    }};

    private static final Vector<String> SEX_TYPE = new Vector<String>() {{
        addAll(Arrays.asList("男", "女"));
    }};

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private JLabel  nameLabel;
    private JLabel sexLabel;
    private JLabel phoneLabel;
    private JLabel departmentLabel;
    private JLabel birthLabel;
    private JLabel hireTimeLabel;
    private JLabel salaryLabel;

    private JTextField name;
    private JComboBox<String> sex = new JComboBox<>(SEX_TYPE);
    private JTextField phone;
    private JComboBox<String> department = new JComboBox<>(DEPARTMENTS);
    private JTextField birth;
    private JTextField hireTime;
    private JTextField salary;

    private JButton confirm;
    private JButton cancel;

    private Integer id;

    public EditStaffFrame(Frame owner, String title) {
        super(owner, title, true);
    }

    public static void of(JFrame parent, String title) {
        new EditStaffFrame(parent, title).init().build();
    }

    public static void of(JFrame parent, String title, Vector<Object> initData) {
        new EditStaffFrame(parent, title).init().setData(initData).build();
    }

    public EditStaffFrame init() {
        nameLabel = ComponentUtils.getLabel("姓名");
        sexLabel = ComponentUtils.getLabel("性别");
        phoneLabel =  ComponentUtils.getLabel("手机号");
        departmentLabel = ComponentUtils.getLabel("部门");
        birthLabel =  ComponentUtils.getLabel("出生日期");
        hireTimeLabel =  ComponentUtils.getLabel("入职日期");
        salaryLabel =  ComponentUtils.getLabel("薪资");

        name = ComponentUtils.getTextField();
        phone = ComponentUtils.getTextField();
        birth = ComponentUtils.getTextField("yyyy-MM-dd");
        hireTime = ComponentUtils.getTextField("yyyy-MM-dd");
        salary = ComponentUtils.getTextField();

        confirm = ComponentUtils.buildButton("确定");
        cancel = ComponentUtils.buildButton("取消");

        confirm.addActionListener((e) -> {
            try {
                handleConfirm();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cancel.addActionListener((e) -> {
            try {
                handleCancel();;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JPanel btns = ComponentUtils.getLeftFlowPane(ComponentUtils.getGapC(20, 20), confirm,
                ComponentUtils.getGapC(20, 20), cancel);

        JPanel np = ComponentUtils.getLeftFlowPane(nameLabel, name);
        JPanel sexp = ComponentUtils.getLeftFlowPane(sexLabel, sex);
        JPanel pp = ComponentUtils.getLeftFlowPane(phoneLabel, phone);
        JPanel dp = ComponentUtils.getLeftFlowPane(departmentLabel, department);
        JPanel bp = ComponentUtils.getLeftFlowPane(birthLabel, birth);
        JPanel hp = ComponentUtils.getLeftFlowPane(hireTimeLabel, hireTime);
        JPanel sp = ComponentUtils.getLeftFlowPane(salaryLabel, salary);

        Box box = Box.createVerticalBox();
        box.add(ComponentUtils.getSmallGapC());
        box.add(np);
        box.add(sexp);
        box.add(pp);
        box.add(dp);
        box.add(bp);
        box.add(hp);
        box.add(sp);
        box.add(ComponentUtils.getGapC(20, 20));
        box.add(btns);
        JPanel p = new JPanel();
        p.add(box);
        this.add(p, BorderLayout.CENTER);
        return this;
    }

    private EditStaffFrame setData(Vector<Object> initData) {
        //设置初始数据
        if (initData == null || initData.size() < 8) {
            throw new IllegalArgumentException("参数错误");
        }
        this.id = Integer.valueOf(initData.get(0).toString());
        this.name.setText(initData.get(1).toString());
        this.sex.setSelectedItem(initData.get(2).toString());
        this.birth.setText(DATE_FORMAT.format((Date) initData.get(3)));
        this.phone.setText(initData.get(4).toString());
        this.department.setSelectedItem(initData.get(5));
        this.hireTime.setText(initData.get(6).toString());
        this.salary.setText(initData.get(7).toString());
        return this;
    }

    public EditStaffFrame build() {
        showInCenter();
        return this;
    }

    private void showInCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(WIDTH, HEIGHT);
        this.setLocation((int) (screenSize.getWidth() - WIDTH) / 2,
                (int) (screenSize.getHeight() - HEIGHT) / 2);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void preCheck() throws IllegalArgumentException {
        //检查个字段是否合法(just simple check)
        String name = this.name.getText().trim();
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("姓名不能为空");
        }
        String phone = this.phone.getText().trim();
        if (!checkPhone(phone)) {
            throw new IllegalStateException("手机号格式错误");
        }
        String birth = this.birth.getText().trim();
        if (!checkDate(birth)) {
            throw new IllegalStateException("出生日期格式错误");
        }
        String hireTime = this.hireTime.getText().trim();
        if (!checkDate(hireTime)) {
            throw new IllegalStateException("入职日期格式错误");
        }
        String salary = this.salary.getText().trim();
        if (!checkSalary(salary)) {
            throw new IllegalStateException("薪资非法");
        }
    }

    private boolean checkPhone(String d) {
        Pattern pattern = Pattern.compile("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        return pattern.matcher(d).matches();
    }

    private boolean checkSalary(String d) {
        try {
            //just check d whether or not be a number and d >= 0
            Double salary = Double.parseDouble(d);
            return salary >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkDate(String d) {
        try {
            //just check format
            Date date = DATE_FORMAT.parse(d);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg, "提示",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleConfirm() {
        try {
            preCheck();
            String name = this.name.getText().trim();
            String sex = this.sex.getSelectedItem().toString();
            String phone = this.phone.getText().trim();
            String birth = this.birth.getText().trim();
            String hireTime = this.hireTime.getText().trim();
            String salary = this.salary.getText().trim();
            String dept = this.department.getSelectedItem().toString();
            Staff staff = new Staff(name, sex, phone, dept,
                    DATE_FORMAT.parse(birth), DATE_FORMAT.parse(hireTime), Double.parseDouble(salary));
            int r = 0;
            if (this.id != null) {
                staff.setId(this.id);
                r = staffService.updateStaff(staff);
            } else {
                r = staffService.addStaff(staff);
            }
            if (r != 1) {
                //更新或插入失败
                JOptionPane.showMessageDialog(this, this.getTitle() + "失败", "提示",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                StaffInfoTable parent = (StaffInfoTable) this.getOwner();
                JOptionPane.showMessageDialog(this, this.getTitle() + "成功", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                //can be optimized
                //notify parent component
                parent.refreshModel();
            }
        } catch (IllegalStateException | ParseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "提示",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        this.dispose();
    }


}
