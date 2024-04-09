package minhnqph38692.fpoly.slide5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import minhnqph38692.fpoly.slide5.modal.Response;
import minhnqph38692.fpoly.slide5.modal.User;
import minhnqph38692.fpoly.slide5.service.HttpRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText lg_username, lg_password;
    Button lg_btn;
    HttpRequest httpRequest;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lg_username = findViewById(R.id.lg_username);
        lg_password = findViewById(R.id.lg_password);
        lg_btn = findViewById(R.id.lg_btn);
        signup = findViewById(R.id.signup);
        httpRequest= new HttpRequest();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        lg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String _username = lg_username.getText().toString();
                String _password = lg_password.getText().toString();

                user.setUsername(_username);
                user.setPassword(_password);
                httpRequest.callAPI().login(user).enqueue(responseUser);


            }
        });


    }

    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(LoginActivity.this, "dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, FruisActivity.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d(">>> GetlistDistributor", "onFailure" + t.getMessage());
        }
    };
}