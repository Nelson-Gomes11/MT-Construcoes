package br.com.mt.adapters;

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
import br.com.mt.activities.ShowAllActivity;
import br.com.mt.models.HomeCategory;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    List<HomeCategory> categoryList;

    public HomeAdapter(Context context, List<HomeCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();

        if (adapterPosition != RecyclerView.NO_POSITION) {
            Glide.with(context).load(categoryList.get(adapterPosition).getImg_url()).into(holder.catImg);
            holder.name.setText(categoryList.get(adapterPosition).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowAllActivity.class);
                    intent.putExtra("type",categoryList.get(adapterPosition).getType());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.home_cat_img);
            name = itemView.findViewById(R.id.cat_home_name);
        }
    }
}
