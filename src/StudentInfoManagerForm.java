import entity.GoalEntity;
import entity.StudentInfoEntity;
import model.GoalMod;
import model.StudentInfoMod;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by LuoLiangchen on 16/2/26.
 */
public class StudentInfoManagerForm extends JFrame {
    private JPanel helloWorldPanel;
    private JTabbedPane tabPanel;
    private JTable tableStudentInfo;
    private JTable tableGoal;
    private JScrollPane panelStudentInfo;
    private JScrollPane panelGoal;
    private JButton buttonAdd;
    private JButton buttonSearch;
    private JButton buttonRemove;
    private JButton buttonSave;
    private JButton buttonChange;
    private JButton buttonShowAll;

    private StudentInfoMod mStudentInfoMod;
    private GoalMod mGoalMod;
    private DefaultTableModel mStudentInfoTableModel;
    private DefaultTableModel mGoalTableModel;

    public StudentInfoManagerForm() {
        super("学生信息管理");
        setContentPane(helloWorldPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setStudentInfoMod(StudentInfoMod mod) {
        mStudentInfoMod = mod;
    }

    public void setGoalMod(GoalMod mod) {
        mGoalMod = mod;
    }

    public void setupTableStudentInfo() {
        String[] columnTitles = {"姓名", "学号", "生日", "院系", "专业"};
        mStudentInfoTableModel = new DefaultTableModel(columnTitles, 0);
        ArrayList<Vector<String>> data = mStudentInfoMod.getData();
        for (Vector<String> rowData: data) {
            mStudentInfoTableModel.addRow(rowData);
        }
        tableStudentInfo.setModel(mStudentInfoTableModel);
    }

    public void setupTableGoal() {
        String[] columnTitles = {"学号", "课程名称", "成绩"};
        mGoalTableModel = new DefaultTableModel(columnTitles, 0);
        ArrayList<Vector<String>> data = mGoalMod.getData();
        for (Vector<String> rowData: data) {
            mGoalTableModel.addRow(rowData);
        }
        tableGoal.setModel(mGoalTableModel);
    }

    public void setupShowAllButton() {
        buttonShowAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    ArrayList<Vector<String>> data = mStudentInfoMod.getData();
                    while (mStudentInfoTableModel.getRowCount() != 0) {
                        mStudentInfoTableModel.removeRow(0);
                    }
                    for (Vector<String> rowData: data) {
                        mStudentInfoTableModel.addRow(rowData);
                    }
                } else {
                    ArrayList<Vector<String>> data = mGoalMod.getData();
                    while (mGoalTableModel.getRowCount() != 0) {
                        mGoalTableModel.removeRow(0);
                    }
                    for (Vector<String> rowData: data) {
                        mGoalTableModel.addRow(rowData);
                    }
                }
            }
        });
    }
    public void setupAddButton() {
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    final StudentInfoInputDialog dialog = new StudentInfoInputDialog();
                    dialog.setOnClickConfirmListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            StudentInfoEntity entity = dialog.getInputEntity();
                            String[] rowData = { entity.getName(), entity.getUid(), entity.getBirthday(), entity.getDepartment(), entity.getSpeciality() };
                            mStudentInfoTableModel.addRow(rowData);
                            mStudentInfoMod.addRowData(entity);
                        }
                    });
                    dialog.showDialog();
                } else {
                    final GoalInputDialog dialog = new GoalInputDialog();
                    dialog.setOnClickConfirmListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GoalEntity entity = dialog.getInputEntity();
                            String[] rowData = { entity.getUid(), entity.getName(), entity.getGoal() };
                            mGoalTableModel.addRow(rowData);
                            mGoalMod.addRowData(entity);
                        }
                    });
                    dialog.showDialog();
                }
            }
        });
    }

    public void setupChangeButton() {
        buttonChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    final int selectedRowIndex = tableStudentInfo.getSelectedRow();
                    if (selectedRowIndex != -1) {
                        String name = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 0);
                        final String uid = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 1);
                        String birthday = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 2);
                        String department = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 3);
                        String speciality = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 4);
                        StudentInfoEntity entity = new StudentInfoEntity();
                        entity.setName(name);
                        entity.setUid(uid);
                        entity.setBirthday(birthday);
                        entity.setDepartment(department);
                        entity.setSpeciality(speciality);

                        final StudentInfoInputDialog dialog = new StudentInfoInputDialog();
                        dialog.setInputTextField(entity);
                        dialog.setOnClickConfirmListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                StudentInfoEntity newEntity = dialog.getInputEntity();
                                int index = mStudentInfoMod.getIndex(uid);
                                if (index != -1) {
                                    mStudentInfoMod.setRowData(index, newEntity);
                                }
                                mStudentInfoTableModel.removeRow(selectedRowIndex);
                                String[] rowData = { newEntity.getName(), newEntity.getUid(), newEntity.getBirthday(), newEntity.getDepartment(), newEntity.getSpeciality() };
                                mStudentInfoTableModel.insertRow(selectedRowIndex, rowData);
                            }
                        });
                        dialog.showDialog();
                    }
                } else {
                    final int selectedRowIndex = tableGoal.getSelectedRow();
                    if (selectedRowIndex != -1) {
                        final String uid = (String) mGoalTableModel.getValueAt(selectedRowIndex, 0);
                        final String name = (String) mGoalTableModel.getValueAt(selectedRowIndex, 1);
                        String goal = (String) mGoalTableModel.getValueAt(selectedRowIndex, 2);
                        GoalEntity entity = new GoalEntity();
                        entity.setUid(uid);
                        entity.setName(name);
                        entity.setGoal(goal);

                        final GoalInputDialog dialog = new GoalInputDialog();
                        dialog.setInputTextField(entity);
                        dialog.setOnClickConfirmListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                GoalEntity newEntity = dialog.getInputEntity();
                                int index = mGoalMod.getIndex(uid, name);
                                if (index != -1) {
                                    mGoalMod.setRowData(index, newEntity);
                                }
                                mGoalTableModel.removeRow(selectedRowIndex);
                                String[] rowData = { newEntity.getUid(), newEntity.getName(), newEntity.getGoal() };
                                mGoalTableModel.insertRow(selectedRowIndex, rowData);
                            }
                        });
                        dialog.showDialog();
                    }
                }
            }
        });
    }

    public void setupRemoveButton() {
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    int selectedRowIndex = tableStudentInfo.getSelectedRow();
                    if (selectedRowIndex != -1) {
                        String uid = (String) mStudentInfoTableModel.getValueAt(selectedRowIndex, 1);
                        int index = mStudentInfoMod.getIndex(uid);
                        if (index != -1) {
                            mStudentInfoMod.removeRowData(index);
                        }
                        mStudentInfoTableModel.removeRow(selectedRowIndex);
                    }
                } else {
                    int selectedRowIndex = tableGoal.getSelectedRow();
                    if (selectedRowIndex != -1) {
                        String uid = (String) mGoalTableModel.getValueAt(selectedRowIndex, 0);
                        String name = (String) mGoalTableModel.getValueAt(selectedRowIndex, 1);
                        int index = mGoalMod.getIndex(uid, name);
                        if (index != -1) {
                            mGoalMod.removeRowData(index);
                        }
                        mGoalTableModel.removeRow(selectedRowIndex);
                    }
                }
            }
        });
    }

    public void setupSearchButton() {
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    String keyword = JOptionPane.showInputDialog("请输入搜索关键字", "");
                    ArrayList<Vector<String>> data = mStudentInfoMod.getDataMatched(keyword);
                    while (mStudentInfoTableModel.getRowCount() != 0) {
                        mStudentInfoTableModel.removeRow(0);
                    }
                    for (Vector<String> rowData: data) {
                        mStudentInfoTableModel.addRow(rowData);
                    }
                } else {
                    String keyword = JOptionPane.showInputDialog("请输入搜索关键字", "");
                    ArrayList<Vector<String>> data = mGoalMod.getDataMatched(keyword);
                    while (mGoalTableModel.getRowCount() != 0) {
                        mGoalTableModel.removeRow(0);
                    }
                    for (Vector<String> rowData: data) {
                        mGoalTableModel.addRow(rowData);
                    }
                }
            }
        });
    }

    public void setupSaveButton() {
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOnStudentInfoTab()) {
                    mStudentInfoMod.saveData();
                } else {
                    mGoalMod.saveData();
                }
            }
        });
    }

    private int getTabIndex() {
        return tabPanel.getSelectedIndex();
    }

    private boolean isOnStudentInfoTab() {
        return getTabIndex() == 0;
    }
}
