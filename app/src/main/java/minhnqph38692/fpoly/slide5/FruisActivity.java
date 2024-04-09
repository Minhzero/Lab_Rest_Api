package minhnqph38692.fpoly.slide5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import minhnqph38692.fpoly.slide5.adapter.FruitsAdapter;
import minhnqph38692.fpoly.slide5.handle.Item_Fruit_Handle;
import minhnqph38692.fpoly.slide5.modal.Fruit;
import minhnqph38692.fpoly.slide5.modal.Response;
import minhnqph38692.fpoly.slide5.service.HttpRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class FruisActivity extends AppCompatActivity {
    HttpRequest httpRequest;
    RecyclerView recyclerView;
    FruitsAdapter adapter;

    SharedPreferences sharedPreferences;
    String token;

    ArrayList<Fruit> list = new ArrayList<>();
    Item_Fruit_Handle handle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruis);
        httpRequest = new HttpRequest();
        recyclerView = findViewById(R.id.recycviewfruits);
        sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        httpRequest.callAPI().getListFruit("Bearer "+token).enqueue(getListFruitAPI);

    }

    private  void  dodulieu(ArrayList<Fruit> listfr){
        adapter = new FruitsAdapter(this,listfr,handle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    Callback<Response<ArrayList<Fruit>>> getListFruitAPI = new Callback<Response<ArrayList<Fruit>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Fruit>>> call, retrofit2.Response<Response<ArrayList<Fruit>>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus()==200){
                    list=response.body().getData();
                    //  ArrayList<Fruit> listfr=response.body().getData();
                    dodulieu(list);
                    Toast.makeText(FruisActivity.this, "succ", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Fruit>>> call, Throwable t) {
            Toast.makeText(FruisActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();

        }
    };

    Callback<Response<Fruit>> responseFruitAPI = new Callback<Response<Fruit>>() {
        @Override
        public void onResponse(Call<Response<Fruit>> call, retrofit2.Response<Response<Fruit>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    //
                    // gọi lại API để load lại dữ liệu sau khi thêm thành công.
                    httpRequest.callAPI().getListFruit("Bearer "+token).enqueue(getListFruitAPI);//
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Fruit>> call, Throwable t) {
            Log.d(">>>> Fruit", "onFailure: " + t.getMessage());

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        httpRequest.callAPI().getListFruit("Bearer "+token).enqueue(getListFruitAPI);
    }
}