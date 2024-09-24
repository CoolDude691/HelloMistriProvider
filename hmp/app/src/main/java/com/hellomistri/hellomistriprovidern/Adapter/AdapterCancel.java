package com.hellomistri.hellomistriprovidern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.LeadsCancel;
import com.hellomistri.hellomistriprovidern.Model.LeadsCancel;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCancel extends RecyclerView.Adapter<AdapterCancel.ViewHolder> {


    List<LeadsCancel> leadsCancels=new ArrayList<>();


    public AdapterCancel(List<LeadsCancel> leadsCancels) {

        this.leadsCancels = leadsCancels;

    }

    @NonNull
    @Override
    public AdapterCancel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadnew,parent,false);
        return new AdapterCancel.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCancel.ViewHolder holder, int position) {
        LeadsCancel model= leadsCancels.get(position);
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.status.setText(model.getO_status());
        holder.Name.setText(model.getName());



    }

    @Override
    public int getItemCount() {
        return leadsCancels.size();
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

/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null)
                    {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){

                            recyclerViewInterface.onItemClick(pos);

                        }


                    }
                }
            });
*/


        }
    }
}
