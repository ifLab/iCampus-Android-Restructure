package org.iflab.icampus.School;

/**
 * 解析详细学院信息的类
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSchoolDetail {

    public SchoolDetail getList(String information) {
        try {
            JSONArray jsonArray = new JSONArray(information);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            SchoolDetail detail = new SchoolDetail();
            detail.setId(jsonObject.getString("id"));
            detail.setMod(jsonObject.getString("mod"));
            detail.setIntroName(jsonObject.getString("introName"));
            detail.setIntroCont(jsonObject.getString("introCont"));
            detail.setHref(jsonObject.getString("href"));
            detail.setRank(jsonObject.getString("rank"));
            return detail;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
