package com.example.taopiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.taopiao.mvp.activity.AppEntryActivity;

import java.util.HashMap;
import java.util.Map;

public class ShareManager {

   /**
     * 保存在手机里面的文件名
    */
 private static final String FILE_SHARE = "taopiaoShare";

    public static void setShare(Context context,String key,Object object){
        String type=object.getClass().getSimpleName();
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        if("String".equals(type)){
                editor.putString(key, (String)object);
               }
           else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
       }
      else if("Boolean".equals(type)){
              editor.putBoolean(key, (Boolean)object);
         }
      else if("Float".equals(type)){
             editor.putFloat(key, (Float)object);
             }
      else if("Long".equals(type)){
              editor.putLong(key, (Long)object);
       }

    editor.commit();

    }
    /**
     * 将数据写入SharePreference
     * @param token
     * @param nickname
     * @param password
     */

    public static void save(Context context,int user_id,String token,String nickname,String password){
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("user_id",user_id);
        editor.putString("token",token);
        editor.putString("nickname",nickname);
        editor.putString("password",password);
        editor.commit();
//        Toast.makeText(mcontext,"已将信息写入SharePreference中",Toast.LENGTH_SHORT).show();
    }
    public static Map<String,String> read(Context context){
        Map<String,String> shareData=new HashMap<>();
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        shareData.put("user_id", String.valueOf(sp.getInt("user_id",0)));
        shareData.put("token",sp.getString("token",""));
        shareData.put("nickname",sp.getString("nickname",""));
        shareData.put("password",sp.getString("password",""));
  return shareData;
    }

    public static String getToken(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        String token=sp.getString("token","");
        return token;
    }
    public static String getCity(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        String city=sp.getString("city","");
        return city;
    }
    public static int getUserId(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
        int user_id=sp.getInt("user_id",0);
        return user_id;
    }
    public static String getLogin(Context context){
        SharedPreferences sp=context.getSharedPreferences(FILE_SHARE,Context.MODE_PRIVATE);
       int user_id=sp.getInt("user_id",0);
        String token=sp.getString("token","");
        String nickname=sp.getString("nickname","");
        String password=sp.getString("password","");
        return user_id+","+token+","+nickname+","+password;
    }

}
