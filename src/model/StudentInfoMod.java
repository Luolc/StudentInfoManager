package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.StudentInfoEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by LuoLiangchen on 16/3/1.
 */
public class StudentInfoMod {
    private static final String TAG = "StudentInfoMod";

    public static final String PATH = "./data";
    public static final String FILE_NAME = "student_info.json";

    private Gson gson = new Gson();

    private ArrayList<StudentInfoEntity> mEntities;

    public StudentInfoMod() {
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
        mEntities = gson.fromJson(json, new TypeToken<ArrayList<StudentInfoEntity>>() {}.getType());
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

    public int getStudentCount() {
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
        StudentInfoEntity entity = mEntities.get(rowIndex);
        Vector<String> v = new Vector<>();
        v.add(entity.getName());
        v.add(entity.getUid());
        v.add(entity.getBirthday());
        v.add(entity.getDepartment());
        v.add(entity.getSpeciality());
        return v;
    }

    public void addRowData(StudentInfoEntity entity) {
        mEntities.add(entity);
    }

    public void removeRowData(int rowIndex) {
        mEntities.remove(rowIndex);
    }

    public void setRowData(int rowIndex, StudentInfoEntity entity) {
        mEntities.set(rowIndex, entity);
    }

    public ArrayList<Vector<String>> getDataMatched(String keyword) {
        ArrayList<Vector<String>> data = new ArrayList<>();
        for (int i = 0; i < mEntities.size(); ++i) {
            if (keyword.equals(mEntities.get(i).getName()) || keyword.equals(mEntities.get(i).getUid())) {
                data.add(getRowData(i));
            }
        }
        return data;
    }

    public int getIndex(String uid) {
        for (int i = 0; i < mEntities.size(); ++i) {
            if (uid.equals(mEntities.get(i).getUid())) {
                return i;
            }
        }
        return -1;
    }
}
