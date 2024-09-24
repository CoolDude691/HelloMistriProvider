package com.hellomistri.hellomistriprovidern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.ProLeads;
import com.hellomistri.hellomistriprovidern.Model.ProLeads;
import com.hellomistri.hellomistriprovidern.R;

import java.util.ArrayList;
import java.util.List;

public class ProcessingAdapter extends RecyclerView.Adapter<ProcessingAdapter.ViewHolder> {

    List<ProLeads> proLeadsList = new ArrayList<>();
    private ItemClickListner itemClickListner;

    public ProcessingAdapter( List<ProLeads> proLeadsList, ItemClickListner itemClickListner) {
        this.proLeadsList = proLeadsList;
        this.itemClickListner = itemClickListner;
    }



    public  interface ItemClickListner
    {
        void onItemClick(TextView textView, int pos);

        void onItemSelection(View view, ConstraintLayout constraintLayout, int pos);
    }



    @NonNull
    @Override
    public ProcessingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadnew,parent,false);
        return new ProcessingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessingAdapter.ViewHolder holder, int position) {

        ProLeads model= proLeadsList.get(position);
        holder.address.setText(model.getOr_address());
        //   holder.date.setText(leads.getDate());
        holder.status.setText(model.getO_status());
        holder.title.setText(model.getCname());

        itemClickListner.onItemClick(holder.viewDet,position);
    }

    @Override
    public int getItemCount() {
        return proLeadsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address,date,time,status,title,Name,viewDet;
        RecyclerView recyclerItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.addressI2);
            date=itemView.findViewById(R.id.dateI2);
//            time=itemView.findViewById(R.id.timeI);
            status=itemView.findViewById(R.id.statusI);
            recyclerItem=itemView.findViewById(R.id.recyclerViewP);
            title = itemView.findViewById(R.id.title);
            Name = itemView .findViewById(R.id.title);
            viewDet = itemView.findViewById(R.id.viewdetails);

           /* itemView.setOnClickListener(new View.OnClickListener() {
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
            });*/
        }
    }
}
