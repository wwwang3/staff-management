package top.wang3.staffmanagement.ui;

import top.wang3.staffmanagement.model.Admin;
import top.wang3.staffmanagement.service.AdminService;
import top.wang3.staffmanagement.service.impl.AdminServiceImpl;
import top.wang3.staffmanagement.util.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class LoginFrame extends JFrame {

    private static final String TITLE = "管理员登陆";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 240;

    //防卡顿
    private volatile boolean inLoginProgress = false;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBth;
    private JButton cancelBtn;
    private JPanel buttons;
    private JPanel mainPanel;
    private final AdminService adminService = new AdminServiceImpl();

    private LoginFrame() throws HeadlessException {
    }

    private void initButton() {
        loginBth = ComponentUtils.buildButton("登陆");
        cancelBtn = ComponentUtils.buildButton("退出");
        //addEventListener
        loginBth.addActionListener((e) -> handleLogin());
        cancelBtn.addActionListener((e) -> handleExit());
        buttons = ComponentUtils.getLeftFlowPane(ComponentUtils.getGapC(36, 20), loginBth, cancelBtn);
    }



    private void initFrame() {
        //登录信息部分界面
        mainPanel = new JPanel();
        usernameLabel = ComponentUtils.getLabel("用户名", 64, 32);
        passwordLabel = ComponentUtils.getLabel("密码", 64, 32);
        usernameField = ComponentUtils.getTextField(128, 32);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(128, 32));
        usernameLabel.setLabelFor(usernameField);
        passwordLabel.setLabelFor(passwordField);
        JPanel username = ComponentUtils.getLeftFlowPane(usernameLabel, usernameField);
        JPanel password = ComponentUtils.getLeftFlowPane(passwordLabel, passwordField);

        Box box = Box.createVerticalBox();
        box.add(ComponentUtils.getGapC(20, 20));
        box.add(username);
        box.add(ComponentUtils.getGapC(20, 10));
        box.add(password);
        box.add(ComponentUtils.getGapC(20, 10));
        box.add(buttons);
        mainPanel.add(box);
    }

    public static LoginFrame create() {
        return new LoginFrame()
                .build()
                .showInCenter();
    }

    public LoginFrame build() {
        initButton();
        initFrame();
        this.add(mainPanel, BorderLayout.CENTER);
        return this;
    }

    public LoginFrame showInCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocation((int) (screenSize.getWidth() - WIDTH) / 2,
                (int) (screenSize.getHeight() - HEIGHT) / 2);
        this.setVisible(true);
        this.setResizable(false);
        return this;
    }

    private void handleLogin() {
        System.out.println(inLoginProgress);
        if (inLoginProgress) {
            return;
        }
        inLoginProgress = true;
        try {
            String adminName = this.usernameField.getText().trim();
            String password = String.valueOf(this.passwordField.getPassword());
            CompletableFuture.runAsync(() -> {
                Admin admin = adminService.getAdminByName(adminName);
                if (admin != null && admin.getPassword().equals(password)) {
                    //if success, not need to reset inLoinProgress
                    successLogin();
                } else {
                    inLoginProgress = false;
                    //modal
                    JOptionPane.showMessageDialog(this, "管理员账号或密码错误", "错误",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleExit() {
        if (inLoginProgress) {
            return;
        }
        System.exit(0);
    }

    private void successLogin() {
        //init staff table;
        new StaffInfoTable().buildFrame();
        this.dispose();
    }

    private void handleException(Exception e) {
        JOptionPane.showMessageDialog(this, "系统异常", "错误",
                JOptionPane.ERROR_MESSAGE);
    }
}
