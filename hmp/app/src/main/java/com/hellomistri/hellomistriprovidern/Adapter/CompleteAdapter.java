package com.hellomistri.hellomistriprovidern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hellomistri.hellomistriprovidern.Model.ModelComplete;
import com.hellomistri.hellomistriprovidern.Model.ModelComplete;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> {

    List<ModelComplete> completeAdapterList = new ArrayList<>();
    private  final RecyclerViewInterface recyclerViewInterface;

    public CompleteAdapter(List<ModelComplete> completeAdapterList,RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.completeAdapterList = completeAdapterList;
    }

    @NonNull
    @Override
    public CompleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadnew,parent,false);
        return new CompleteAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.ViewHolder holder, int position) {

        ModelComplete model= completeAdapterList.get(position);

        holder.address.setText(model.getAddress());
        //   holder.date.setText(leads.getDate());
        holder.status.setText(model.getO_status());

    }

    @Override
    public int getItemCount() {
        return completeAdapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address,date,time,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.addressI2);
            date=itemView.findViewById(R.id.dateI2);
          //  time=itemView.findViewById(R.id.timeI);
            status=itemView.findViewById(R.id.statusI);
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


        }
    }


}
