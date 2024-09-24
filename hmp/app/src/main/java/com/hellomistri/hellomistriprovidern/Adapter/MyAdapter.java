package com.hellomistri.hellomistriprovidern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Leads>  leadsList = new ArrayList<>();
    private ItemClickListner itemClickListner;
    public MyAdapter(List<Leads> leadsList,ItemClickListner itemClickListner) {

        this.leadsList = leadsList;
        this.itemClickListner = itemClickListner;

    }
    public  interface ItemClickListner
    {
        void onItemClick(TextView textView, int pos);

        void onItemSelection(View view, ConstraintLayout constraintLayout, int pos);
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadnew,parent,false);

        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Leads leads= leadsList.get(position);
        holder.address.setText(leads.getOr_address());
       holder.date.setText(leads.getOr_date());
        holder.status.setText(leads.getO_status());
        holder.Name.setText(leads.getCname());






        itemClickListner.onItemClick(holder.viewDet,position);

            }

    @Override
    public int getItemCount() {
        return leadsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address,date,time,status,Name,viewDet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.addressI2);
            date=itemView.findViewById(R.id.dateI2);
//            time=itemView.findViewById(R.id.timeI);
            status=itemView.findViewById(R.id.statusI);
            Name = itemView .findViewById(R.id.title);
            viewDet = itemView.findViewById(R.id.viewdetails);
        }
    }
}
