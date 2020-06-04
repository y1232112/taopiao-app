package com.example.taopiao.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
     public static double version=0.1;
    public static JSONObject ceateForParams(JSONObject params) throws JSONException {
        JSONObject object=new JSONObject();
        object.put("version",version);


        object.put("params",params).toString();
        return object;
    }
    public static ArrayList<String> getMainCity(JSONObject object) throws JSONException {
        ArrayList<String> list=new ArrayList<>();
       JSONObject yi=object.getJSONObject("data");
        JSONArray er=yi.getJSONArray("areas");
        for(int i = 0;i<er.length();i++){

            JSONArray san = (JSONArray) er.getJSONObject(i).get("children");
            for (int k=0;k<san.length();k++){
                String city=san.getJSONObject(k).getString("name");
                list.add(city);
            }
        }
        return list;
    }
    public static ArrayList<String> getMyCityInfo(JSONObject object,String sCity) throws JSONException {
        ArrayList<String> list=new ArrayList<>();
        list.add("全城");
        JSONObject yi=object.getJSONObject("data");
        JSONArray er=yi.getJSONArray("areas");
        for(int i = 0;i<er.length();i++){
            JSONArray san = (JSONArray) er.getJSONObject(i).get("children");
            for (int k=0;k<san.length();k++){
                String city=san.getJSONObject(k).getString("name");
                if (sCity.equals(city)){
                    JSONArray mCountyList=san.getJSONObject(k).getJSONArray("children");
                    for (int j=0;j<mCountyList.length();j++){
                        String county=mCountyList.getJSONObject(j).getString("name");
                        list.add(county);
                    }
                }
            }
        }
        return list;
    }
}
