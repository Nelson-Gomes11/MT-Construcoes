package br.com.mt.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.mt.R;
import br.com.mt.activities.DetalheActivity;
import br.com.mt.models.NewProductsModel;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private Context context;
    private List<NewProductsModel> list;

    public NewProductsAdapter(Context context, List<NewProductsModel> newProductsModelList) {
        this.context = context;
        this.list = newProductsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_produtcs, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (context != null) {
            Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        }
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetalheActivity.class);
                intent.putExtra("detalhes",list.get(position));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<NewProductsModel> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newImg;
        TextView newName,newPrice;

        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);

        }
    }
}