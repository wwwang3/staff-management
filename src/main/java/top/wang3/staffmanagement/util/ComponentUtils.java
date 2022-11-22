package top.wang3.staffmanagement.util;

import top.wang3.staffmanagement.model.Constants;
import top.wang3.staffmanagement.ui.EditStaffFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ComponentUtils {

    public static JComponent getGapC() {
        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(250, 20));
        return gap;
    }

    public static JComponent getSmallGapC() {
        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(100, 10));
        return gap;
    }

    public static JComponent getGapC(int w, int h) {
        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(w, h));
        return gap;
    }

    public static JTextField getTextField(int w, int h) {
        JTextField field = new JTextField();
        field.setFont((new Font(Constants.FONT_TYPE, Font.PLAIN, Constants.FONT_SIZE)));

        field.setPreferredSize(new Dimension(w, h));
        return field;
    }

    public static JTextField getTextField() {
        return getTextField(128, 32);
    }

    public static  JTextField getTextField(String placeholder) {
        JTextField field = getTextField();
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new TextFieldFocusListener(placeholder, field));
        return field;
    }

    public static JLabel getLabel(String l) {
        return getLabel(l, 72, 36);
    }

    public static JLabel getLabel(String l, int w, int h) {
        JLabel label = new JLabel(l, JLabel.RIGHT);
        label.setFont(new Font(Constants.FONT_TYPE, Font.PLAIN, Constants.FONT_SIZE));
        label.setPreferredSize(new Dimension(w, h));
        return label;
    }

    public static JPanel getLeftFlowPane(Component l, Component ...c) {
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(6);
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.add(l);
        for (Component cc : c) {
            panel.add(cc);
        }
        return panel;
    }

    /**
     * 42 * 36
     * @param title 按钮文字
     * @return JButton
     */
    public static JButton buildButton(String title) {
        JButton b = new JButton(title);
        b.setSize(42, 36);
        b.setFont(new Font(Constants.FONT_TYPE, Font.PLAIN, Constants.FONT_SIZE));
        b.setFocusPainted(false);
        return b;
    }

    static class TextFieldFocusListener implements FocusListener {

        private final String placeholder;
        private final JTextField self;

        public TextFieldFocusListener(String placeholder, JTextField self) {
            this.placeholder = placeholder;
            this.self = self;
        }

        @Override
        public void focusGained(FocusEvent e) {
            String t = self.getText();
            if (placeholder.equals(t)) {
                self.setText("");
            }
            self.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            String t = self.getText();
            if (t == null || t.isBlank()) {
                self.setForeground(Color.GRAY);
                self.setText(placeholder);
            }
        }
    }
}
