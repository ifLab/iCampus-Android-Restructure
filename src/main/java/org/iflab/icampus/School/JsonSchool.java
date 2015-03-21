package org.iflab.icampus.School;

/**
 * 解析学院列表的类
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonSchool {
    public List<School> getList(String information){
        List<School> schools = new ArrayList<School>();
        try {
            JSONArray jsonArray = new JSONArray(information);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                School school = new School();
                school.setId(jsonObject.getString("id"));
                school.setIntroName(jsonObject.getString("introName"));
                school.setMod(jsonObject.getString("mod"));
                schools.add(school);
            }
            return schools;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}