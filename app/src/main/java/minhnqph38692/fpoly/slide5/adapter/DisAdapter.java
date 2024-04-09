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

import java.util.ArrayList;
import java.util.List;

import minhnqph38692.fpoly.slide5.R;
import minhnqph38692.fpoly.slide5.handle.Item_Dis_Handle;
import minhnqph38692.fpoly.slide5.modal.Distributor;

public class DisAdapter extends RecyclerView.Adapter<DisAdapter.ViewHolder> {

    List<Distributor>list;
    Context context;
    Item_Dis_Handle handle;

//    public DisAdapter(List<Distributor> list, Context context) {
//        this.list = list;
//        this.context = context;
//
//    }


    public DisAdapter(List<Distributor> list, Context context, Item_Dis_Handle handle) {
        this.list = list;
        this.context = context;
        this.handle = handle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_distributor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Distributor distributor = list.get(position);

        holder.id.setText(list.get(position).getId());
        holder.name.setText(list.get(position).getName());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
handle.Delete(list.get(position).getId());
            }
        });
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        handle.Update(list.get(position).getId(),distributor);
    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView id,name;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.delete);
        }
    }

}
