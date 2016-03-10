import entity.GoalEntity;

import javax.swing.*;
import java.awt.event.*;

public class GoalInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JTextField textFieldUid;
    private JTextField textFieldName;
    private JTextField textFieldGoal;

    public GoalInputDialog() {
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

    public GoalEntity getInputEntity() {
        GoalEntity entity = new GoalEntity();
        entity.setUid(textFieldUid.getText());
        entity.setName(textFieldName.getText());
        entity.setGoal(textFieldGoal.getText());
        return entity;
    }

    public void setInputTextField(GoalEntity entity) {
        textFieldUid.setText(entity.getUid());
        textFieldName.setText(entity.getName());
        textFieldGoal.setText(entity.getGoal());
    }
}
