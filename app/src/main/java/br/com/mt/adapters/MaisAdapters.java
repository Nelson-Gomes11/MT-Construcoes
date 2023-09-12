package br.com.mt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.bumptech.glide.Glide;
import br.com.mt.R;
import br.com.mt.models.MaisVend;

public class MaisAdapters extends RecyclerView.Adapter<MaisAdapters.ViewHolder> {

    private Context context;
    private List<MaisVend> maisVendList;

    public MaisAdapters(Context context, List<MaisVend> maisVendList) {
        this.context = context;
        this.maisVendList = maisVendList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mais_vend,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MaisAdapters.ViewHolder holder, int position) {

        Glide.with(context).load(maisVendList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(maisVendList.get(position).getName());
        holder.description.setText(maisVendList.get(position).getDescriptions());
    }

    @Override
    public int getItemCount() {
        return maisVendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView popImg;
            TextView name,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            }
        }
    }

