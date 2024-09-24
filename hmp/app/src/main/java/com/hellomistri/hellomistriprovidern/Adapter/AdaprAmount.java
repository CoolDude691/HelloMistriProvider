package com.hellomistri.hellomistriprovidern.Adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.RechargeModel;
import com.hellomistri.hellomistriprovidern.Model.Select_Category_Model;
import com.hellomistri.hellomistriprovidern.Model.Select_Category_Model;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class AdaprAmount extends RecyclerView.Adapter<AdaprAmount.ViewHolder> {

    List<RechargeModel>  rechargeModelList= new ArrayList<>();

    private ItemClickListner itemClickListner;

    public AdaprAmount(List<RechargeModel> rechargeModelList,ItemClickListner itemClickListner) {
        this.rechargeModelList = rechargeModelList;
        this.itemClickListner = itemClickListner;

    }


    public  interface ItemClickListner
    {
        void onItemClick(CardView cardView, int pos);

        void onItemSelection(ImageView imageView, ConstraintLayout constraintLayout, int pos);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rechargeamount,parent,false);
        return new AdaprAmount.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RechargeModel model = rechargeModelList.get(position);
        holder.textView.setText(model.getCredit_amt());



        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.tick.setVisibility(View.VISIBLE);

              /*  if (view.isSelected())
                {
                }*/
            }
        });

        itemClickListner.onItemClick(holder.cardView,position);





    }

    @Override
    public int getItemCount() {
        return rechargeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textView;

        CardView cardView;

        ImageView  tick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.ramt);
            cardView=itemView.findViewById(R.id.selectAmount);
            tick=itemView.findViewById(R.id.tick);




        }

    }
}

