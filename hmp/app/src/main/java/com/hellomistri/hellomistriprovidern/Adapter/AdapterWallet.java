package com.hellomistri.hellomistriprovidern.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.ModelWallet;
import com.hellomistri.hellomistriprovidern.Model.ModelWallet;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterWallet extends RecyclerView.Adapter<AdapterWallet.ViewHolder> {


    List<ModelWallet> walletList = new ArrayList<>();

    public AdapterWallet(List<ModelWallet> walletList) {

        this.walletList= walletList;
    }

    @NonNull
    @Override
    public AdapterWallet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction,parent,false);
        return new AdapterWallet.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWallet.ViewHolder holder, int position) {
        ModelWallet modelWallet= walletList.get(position);
        holder.title.setText(modelWallet.getP_mode());
        holder.dt.setText(modelWallet.getDate());
        holder.credDeb.setText(modelWallet.getAmount());

        if (modelWallet.getStatus().equals("Credit"))
        {
         holder.credDeb.setText("+"+modelWallet.getAmount());
            holder.credDeb.setTextColor(Color.parseColor("#00A300"));

        }else
        {
            holder.credDeb.setText("-"+modelWallet.getAmount());
            holder.credDeb.setTextColor(Color.parseColor("#FF0000"));

        }

    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView  title,dt,credDeb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            dt=itemView.findViewById(R.id.dateTime);
            credDeb=itemView.findViewById(R.id.credDeb);


        }

        }
    }

