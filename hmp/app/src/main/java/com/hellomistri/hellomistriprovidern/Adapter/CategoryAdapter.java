package com.hellomistri.hellomistriprovidern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.Select_Category_Model;
import com.hellomistri.hellomistriprovidern.Model.Select_Category_Model;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Select_Category_Model>  select_category_models= new ArrayList<>();

    public CategoryAdapter(List<Select_Category_Model> select_category_models) {
        this.select_category_models = select_category_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cetegory,parent,false);
        return new CategoryAdapter.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Select_Category_Model model = select_category_models.get(position);
        holder.cat_name.setText(model.getCat_name());




    }

    @Override
    public int getItemCount() {
        return select_category_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, select;
        TextView cat_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.imageView1);
            select=itemView.findViewById(R.id.img_checkb);
            cat_name=itemView.findViewById(R.id.txt_title);

        }

    }
}
