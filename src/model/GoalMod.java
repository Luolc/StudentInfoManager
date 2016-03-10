package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.GoalEntity;
import entity.StudentInfoEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by LuoLiangchen on 16/3/1.
 */
public class GoalMod {
    private static final String TAG = "GoalMod";

    public static final String PATH = "./data";
    public static final String FILE_NAME = "goal.json";

    private Gson gson = new Gson();

    private ArrayList<GoalEntity> mEntities;

    public GoalMod() {
        mEntities = new ArrayList<>();
    }

    public void loadData() {
        File file = new File(PATH + "/" + FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br = null;
        String json = "";
        try {
            br = new BufferedReader(new FileReader(file));
            String read;
            while ((read = br.readLine()) != null) {
                json += read;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mEntities = gson.fromJson(json, new TypeToken<ArrayList<GoalEntity>>() {}.getType());
    }

    public void saveData() {
        File file = new File(PATH + "/" + FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String json = gson.toJson(mEntities);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGoalCount() {
        return mEntities.size();
    }

    public ArrayList<Vector<String>> getData() {
        ArrayList<Vector<String>> data = new ArrayList<>();
        for (int i = 0; i < mEntities.size(); ++i) {
            data.add(getRowData(i));
        }
        return data;
    }

    public Vector<String> getRowData(int rowIndex) {
        GoalEntity entity = mEntities.get(rowIndex);
        Vector<String> v = new Vector<>();
        v.add(entity.getUid());
        v.add(entity.getName());
        v.add(entity.getGoal());
        return v;
    }

    public void addRowData(GoalEntity entity) {
        mEntities.add(entity);
    }

    public void removeRowData(int rowIndex) {
        mEntities.remove(rowIndex);
    }

    public void setRowData(int rowIndex, GoalEntity entity) {
        mEntities.set(rowIndex, entity);
    }

    public ArrayList<Vector<String>> getDataMatched(String keyword) {
        ArrayList<Vector<String>> data = new ArrayList<>();
        for (int i = 0; i < mEntities.size(); ++i) {
            if (keyword.equals(mEntities.get(i).getUid())) {
                data.add(getRowData(i));
            }
        }
        return data;
    }

    public int getIndex(String uid, String name) {
        for (int i = 0; i < mEntities.size(); ++i) {
            if (uid.equals(mEntities.get(i).getUid()) && name.equals(mEntities.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
