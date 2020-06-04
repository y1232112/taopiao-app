package com.example.taopiao.mvp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.mvp.contract.DoAvatarContract;
import com.example.taopiao.mvp.presenter.DoAvatarPresenter;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class DoAvatarActivity extends AppCompatActivity implements DoAvatarContract.view {
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

     private static final String TAG="我的  选择图片--->";
     private File file;
     private Uri mUri;
     private DoAvatarContract.presenter presenter;
    @BindView(R.id.my_avatar_to_get)
    ImageView avatar;
    @BindView(R.id.avatart_cummit)
    Button avartar_cummit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_avatar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);
        presenter=new DoAvatarPresenter(this,this);

        //选择图片
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);
       avatar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Intent.ACTION_PICK,null);
               intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
               startActivityForResult(intent,2);
           }
       });
           avartar_cummit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   JSONObject ob1=new JSONObject();



                   MyApplication app=MyApplication.getInstance();
                   try {
                       ob1.put("uer_id",app.user_id);
                       ob1.put("type","image");
                       //封装两个body
                       RequestBody des=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),ob1.toString());
                      verifyStoragePermissions(DoAvatarActivity.this);
                      File file= uriFile(mUri);
                      MultipartBody.Builder builder=new MultipartBody.Builder()
                              .setType(MultipartBody.FORM)
                              .addFormDataPart("desc","这是图片");

                       RequestBody requestFile =
                               RequestBody.create(MediaType.parse("image/jpg"), file);
                       MultipartBody.Part mfile =
                               MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                      presenter.cummitAvatar(mfile);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (data!=null){
                Uri uri=data.getData();
                avatar.setImageURI(uri);
                Log.d(TAG,"uri为"+uri.toString());

                mUri=uri;

            }else {
                Toast.makeText(DoAvatarActivity.this,"你还未选择任何图片！！！！",Toast.LENGTH_LONG).show();
            }
        }
    }
    private File uriFile(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void getMessage(String message) {
          Toast.makeText(DoAvatarActivity.this,message,Toast.LENGTH_LONG).show();
    }
}
