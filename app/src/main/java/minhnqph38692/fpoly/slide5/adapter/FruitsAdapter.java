package minhnqph38692.fpoly.slide5.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import minhnqph38692.fpoly.slide5.R;
import minhnqph38692.fpoly.slide5.handle.Item_Fruit_Handle;
import minhnqph38692.fpoly.slide5.modal.Fruit;
import minhnqph38692.fpoly.slide5.service.HttpRequest;

public class FruitsAdapter extends RecyclerView.Adapter<FruitsAdapter.ViewHolder> {

Context context;
ArrayList<Fruit> list;
Item_Fruit_Handle handle;
HttpRequest httpRequest;

    public FruitsAdapter(Context context, ArrayList<Fruit> list, Item_Fruit_Handle handle) {
        this.context = context;
        this.list = list;
        this.handle = handle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.viewholder_fruits,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.txtnameFr.setText("Tên:"+fruit.getName());
        holder.txtquantity.setText("Số lượng:"+fruit.getQuantity());
        holder.txtprice.setText("Giá:"+fruit.getPrice());
        holder.txtdescription.setText("Miêu tả:"+fruit.getDescription());
//        holder.txtdisId.setText("Id_Dis:"+fruit.getDistributor().getId());
        Glide.with(context)
                .load(fruit.getImage().get(0))
                .thumbnail(Glide.with(context).load(fruit.getImage().get(0)))
                .into(holder.imghinhFr);
//set hình ảnh
//        Glide.with(context).load(fruit.getImage().get(0)).into(holder.imghinhFr);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {

        TextView txtnameFr,txtquantity,txtprice,txtdescription,txtdisId;
        ImageView imghinhFr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnameFr=itemView.findViewById(R.id.name1);
            txtquantity=itemView.findViewById(R.id.quantity);
            txtprice=itemView.findViewById(R.id.price);
            txtdescription=itemView.findViewById(R.id.des);
            txtdisId=itemView.findViewById(R.id.disId);
            imghinhFr=itemView.findViewById(R.id.anh);
        }
    }
}
