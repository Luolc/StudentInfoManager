import entity.StudentInfoEntity;

import javax.swing.*;
import java.awt.event.*;

public class StudentInfoInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldUid;
    private JTextField textFieldBirthday;
    private JTextField textFieldDepartment;
    private JTextField textFieldSpeciality;

    private int mActionCode;

    public StudentInfoInputDialog() {
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(getRootPane());
        getRootPane().setDefaultButton(buttonConfirm);

        buttonConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onConfirm();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onConfirm() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void showDialog() {
        pack();
        setVisible(true);
    }

    public void setOnClickConfirmListener(ActionListener listener) {
        buttonConfirm.addActionListener(listener);
    }

    public void setOnClickCancelListener(ActionListener listener) {
        buttonCancel.addActionListener(listener);
    }

    public StudentInfoEntity getInputEntity() {
        StudentInfoEntity entity = new StudentInfoEntity();
        entity.setName(textFieldName.getText());
        entity.setUid(textFieldUid.getText());
        entity.setBirthday(textFieldBirthday.getText());
        entity.setDepartment(textFieldDepartment.getText());
        entity.setSpeciality(textFieldSpeciality.getText());

        return entity;
    }

    public void setInputTextField(StudentInfoEntity entity) {
        textFieldName.setText(entity.getName());
        textFieldUid.setText(entity.getUid());
        textFieldBirthday.setText(entity.getBirthday());
        textFieldDepartment.setText(entity.getDepartment());
        textFieldSpeciality.setText(entity.getSpeciality());
    }
}
