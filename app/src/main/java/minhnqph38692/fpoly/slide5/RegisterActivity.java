package minhnqph38692.fpoly.slide5;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import minhnqph38692.fpoly.slide5.modal.Response;
import minhnqph38692.fpoly.slide5.modal.User;
import minhnqph38692.fpoly.slide5.service.HttpRequest;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText username,password, name, email;
    Button btn;
    ImageView avatar;

    HttpRequest httpRequest;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        btn= findViewById(R.id.btn);
        avatar= findViewById(R.id.avatar);
        httpRequest = new HttpRequest();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody _username= RequestBody.create(MediaType.parse("multipart/from-data"),username.getText().toString());
                RequestBody _password= RequestBody.create(MediaType.parse("multipart/from-data"),password.getText().toString());
                RequestBody _email= RequestBody.create(MediaType.parse("multipart/from-data"),email.getText().toString());
                RequestBody _name= RequestBody.create(MediaType.parse("multipart/from-data"),name.getText().toString());

                MultipartBody.Part multipartBody;
                if(file != null){
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
                    multipartBody = MultipartBody.Part.createFormData("avatar",file.getName(),requestBody);
                }else {
                    multipartBody = null;
                }
                httpRequest.callAPI().register(_username,_password,_email,_name,multipartBody).enqueue(responseUser);
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    Toast.makeText(RegisterActivity.this, " Dawng ki thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }else {
                    Toast.makeText(RegisterActivity.this, "Dk fail", Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d(">>> getListDistributor","onFailur" + t.getMessage());
        }
    };
//    private  void chooseImage(){
//        if(ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            getImage.launch(intent);
//        }else {
//            ActivityCompat.requestPermissions(this,new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
//
//
//    }
private void chooseImage() {
    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    getImage.launch(intent);
}
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == Activity.RESULT_OK){
                        Intent intent = o.getData();
                        Uri imagePath = intent.getData();
                        file= createFileFromUri(imagePath,"avatar");
                        // Glide để load ảnh
                        Glide.with(RegisterActivity.this)
                                .load(file)
                                .thumbnail(Glide.with(RegisterActivity.this).load(R.mipmap.ic_launcher))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(avatar);

                    }
                }
            });
    private  File createFileFromUri(Uri path, String name){
        File _file = new File(RegisterActivity.this.getFilesDir(),name+".png");
        try {
            InputStream in = RegisterActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
            return _file;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


}