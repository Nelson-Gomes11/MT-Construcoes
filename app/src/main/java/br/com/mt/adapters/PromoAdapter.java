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
import br.com.mt.models.PromoModel;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {

    Context context;
    List<PromoModel> list;

    public PromoAdapter(Context context, List<PromoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(holder.getAdapterPosition()).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(holder.getAdapterPosition()).getName());
        holder.description.setText(list.get(holder.getAdapterPosition()).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, ShowAllActivity.class);
                    intent.putExtra("type", list.get(adapterPosition).getType());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,description;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_dec);
        }
    }
}
