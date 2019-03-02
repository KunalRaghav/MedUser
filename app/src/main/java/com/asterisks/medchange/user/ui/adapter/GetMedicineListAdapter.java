package com.asterisks.medchange.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GetMedicineListAdapter extends RecyclerView.Adapter<GetMedicineListAdapter.GetMedicineViewHolder> {

    private List<MedicineModel> medicineModelList;

    @NonNull
    @Override
    public GetMedicineListAdapter.GetMedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_medicine,parent,false);
        GetMedicineViewHolder medicineViewHolder = new GetMedicineViewHolder(view);
        return medicineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetMedicineViewHolder holder, int position) {
        MedicineModel medicine= medicineModelList.get(position);

        //Set Values
        holder.mMedicineName.setText(medicine.getName());
        holder.mMedicineQuantity.setText(medicine.getQuantity());
        holder.mMedicineCost.setText(medicine.getMrp());

    }

    @Override
    public int getItemCount() {
        return medicineModelList.size();
    }

    class GetMedicineViewHolder extends RecyclerView.ViewHolder{
        public TextView mMedicineName;
        public TextView mMedicineCost;
        public TextView mMedicineQuantity;
        public GetMedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicineName=itemView.findViewById(R.id.list_item_medicine_name);
            mMedicineCost=itemView.findViewById(R.id.list_item_cost);
            mMedicineQuantity=itemView.findViewById(R.id.list_item_quantity);

        }
    }

    public GetMedicineListAdapter(List<MedicineModel> medicineModelList) {
        this.medicineModelList = medicineModelList;
    }
}
