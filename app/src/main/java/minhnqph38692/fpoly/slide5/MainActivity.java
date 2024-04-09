package minhnqph38692.fpoly.slide5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import minhnqph38692.fpoly.slide5.adapter.DisAdapter;
import minhnqph38692.fpoly.slide5.handle.Item_Dis_Handle;
import minhnqph38692.fpoly.slide5.modal.Distributor;
import minhnqph38692.fpoly.slide5.modal.Response;
import minhnqph38692.fpoly.slide5.service.HttpRequest;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    HttpRequest httpRequest;
    RecyclerView recyclerView;
    DisAdapter adapter;
    TextInputEditText edtTimKiem;
    FloatingActionButton add;
    Item_Dis_Handle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycview);
        edtTimKiem=findViewById(R.id.edtTimKiem);
        add= findViewById(R.id.add);
        httpRequest = new HttpRequest();

        handle = new Item_Dis_Handle() {
            @Override
            public void Delete(String id) {
                DeleteD(id);
            }

            @Override
            public void Update(String id, Distributor distributor) {
UpdateS(id,distributor);
            }
        };
        httpRequest.callAPI()
                .getListDistributor()
                .enqueue(getDistributorAPI);

        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String key = edtTimKiem.getText().toString();

                    httpRequest.callAPI()
                            .searchDistributor(key)
                            .enqueue(getDistributorAPI);
                    return true;
                }
                return false;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Add();
            }
        });
    }
    private  void getData(List<Distributor> list){
        adapter=new DisAdapter(list,this,handle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    Callback<Response<ArrayList<Distributor>>> getDistributorAPI = new Callback<Response<ArrayList<Distributor>>>() {

        @Override
        public void onResponse(Call<Response<ArrayList<Distributor>>> call, retrofit2.Response<Response<ArrayList<Distributor>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    List<Distributor> list = response.body().getData();
                    getData(list);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Distributor>>> call, Throwable t) {
            Log.d(">>> GetListDitributor", " onFailure"+t.getMessage());
        }
    };

    public void  DeleteD(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có chắc xóa?");
        AlertDialog dialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                httpRequest.callAPI()
                        .deleteDistributorById(id)
                        .enqueue(responDistributorAPI);
                Toast.makeText(MainActivity.this, "Xoas thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    public  void Add(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_themcongty,null);
        builder.setView(v);
//        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        TextInputEditText ed_themten= v.findViewById(R.id.ed_tenCT);
        Button btn_themten = v.findViewById(R.id.btn_them);

        btn_themten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = ed_themten.getText().toString();
                Distributor distributor = new Distributor();
                distributor.setName(ten);
                httpRequest.callAPI()
                        .addDistributor(distributor)
                        .enqueue(responDistributorAPI);
                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public  void UpdateS(String id, Distributor distributor  ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_themcongty,null);
        builder.setView(v);
//        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        TextInputEditText ed_themten= v.findViewById(R.id.ed_tenCT);
        Button btn_updateten = v.findViewById(R.id.btn_them);
        ed_themten.setText(distributor.getName());

        btn_updateten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = ed_themten.getText().toString();
//                 distributor = new Distributor();
                distributor.setName(ten);
                httpRequest.callAPI()
                        .updateDistributorById(id,distributor)
                        .enqueue(responDistributorAPI);
                Toast.makeText(MainActivity.this, "upadte thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    Callback<Response<Distributor>> responDistributorAPI = new Callback<Response<Distributor>>() {
        @Override
        public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    httpRequest.callAPI()
                            .getListDistributor()
                            .enqueue(getDistributorAPI);
//                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Distributor>> call, Throwable t) {
            Log.d(">>> GetListDitributor", " onFailure"+t.getMessage());
        }
    };
}