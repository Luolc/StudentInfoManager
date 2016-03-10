import model.GoalMod;
import model.StudentInfoMod;

import java.awt.*;

public class StudentInfoManager {
    private static StudentInfoMod mStudentInfoMod = new StudentInfoMod();
    private static GoalMod mGoalMod = new GoalMod();

    public static void main(String[] args) {
        mStudentInfoMod.loadData();
        mGoalMod.loadData();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentInfoManagerForm mStudentInfoManagerForm = new StudentInfoManagerForm();
                mStudentInfoManagerForm.setStudentInfoMod(mStudentInfoMod);
                mStudentInfoManagerForm.setGoalMod(mGoalMod);
                mStudentInfoManagerForm.setupTableStudentInfo();
                mStudentInfoManagerForm.setupTableGoal();

                mStudentInfoManagerForm.setupShowAllButton();
                mStudentInfoManagerForm.setupAddButton();
                mStudentInfoManagerForm.setupChangeButton();
                mStudentInfoManagerForm.setupRemoveButton();
                mStudentInfoManagerForm.setupSearchButton();
                mStudentInfoManagerForm.setupSaveButton();

                mStudentInfoManagerForm.setVisible(true);
            }
        });
    }
}
