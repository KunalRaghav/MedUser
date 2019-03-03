package com.asterisks.medchange.user.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.models.UserMedicineModel;
import com.asterisks.medchange.user.ui.fragments.SellMedicineFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SellMedicineListAdapter extends RecyclerView.Adapter<SellMedicineListAdapter.ItemViewHolder> {
    List<UserMedicineModel> userMedicineModelList;
    FrameLayout mContainer;
    Context mContext;
    FragmentManager fm;
    MedicineModel medicine;
    List<MedicineModel> medicineModelList;


//    public SellMedicineListAdapter(List<UserMedicineModel> userMedicineModelList) {
//        this.userMedicineModelList = userMedicineModelList;
//    }


    public SellMedicineListAdapter(List<UserMedicineModel> userMedicineModelList, FrameLayout mContainer, Context mContext, FragmentManager fm) {
        this.userMedicineModelList = userMedicineModelList;
        this.mContainer = mContainer;
        this.mContext = mContext;
        this.fm = fm;
    }

    public SellMedicineListAdapter(List<UserMedicineModel> userMedicineModelList, FrameLayout mContainer, Context mContext, FragmentManager fm, List<MedicineModel> medicineModelList) {
        this.userMedicineModelList = userMedicineModelList;
        this.mContainer = mContainer;
        this.mContext = mContext;
        this.fm = fm;
        this.medicineModelList = medicineModelList;
        List<UserMedicineModel> userMedicineModels=new ArrayList<>();
        for(int i=0;i<userMedicineModelList.size();i++){
            UserMedicineModel model = userMedicineModelList.get(i);
            if(!model.getAcceptedByPharmacist()){
                userMedicineModels.add(model);
            }
        }
        this.userMedicineModelList=userMedicineModels;
    }

    @NonNull
    @Override
    public SellMedicineListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_medicine_sell,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellMedicineListAdapter.ItemViewHolder holder, int position) {
        UserMedicineModel model = userMedicineModelList.get(position);
        holder.mMedicineID.setText(String.valueOf(model.getId()));
//        holder.mMedicineName.setText("Calpol 650");
        holder.mMedicineName.setText(
                getName(Integer.parseInt(model.getMedicine()))
        );
        holder.mExpiryDate.setText("Expires ON: "+model.getExpiryDate());
        holder.mCredits.setText("Credits: "+model.getCreditForMedicine());
        holder.mQuantity.setText("Quantity: "+model.getQuantityOfMedicine());
    }

    @Override
    public int getItemCount() {
        return userMedicineModelList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mMedicineID;
        public TextView mMedicineName;
        public TextView mExpiryDate;
        public TextView mQuantity;
        public TextView mCredits;
        public MaterialButton mSELL;
        public MaterialButton mDELETE;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicineID = itemView.findViewById(R.id.sell_medicine_id);
            mMedicineName = itemView.findViewById(R.id.sell_medicine_name);
            mExpiryDate = itemView.findViewById(R.id.sell_Expiry_Date);
            mQuantity = itemView.findViewById(R.id.sell_medicine_quantity);
            mCredits = itemView.findViewById(R.id.sell_medicine_credits);
            mSELL = itemView.findViewById(R.id.sell_button);
            mDELETE = itemView.findViewById(R.id.sell_delete_button);
            mSELL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SellMedicineFragment sellfragment = new SellMedicineFragment();
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("medicine",getMedicine(Integer.parseInt(mMedicineID.getText().toString())));
                    sellfragment.setArguments(bundle);
                    FragmentTransaction ft =fm.beginTransaction();
                    ft.add(R.id.sell_container,sellfragment,"sellFrag")
                            .addToBackStack("sellFrag").commit();
                    Log.d(TAG, "onClick: clicked sell");
                }
            });
        }

    }
    UserMedicineModel getMedicine(int ID){
        for(int i=0;i<userMedicineModelList.size();i++){
            UserMedicineModel model = userMedicineModelList.get(i);
            if(model.getId()==ID){
                return model;
            }
        }
        return null;
    }
    String getName(int ID){
        for(int i=0;i<medicineModelList.size();i++){
            MedicineModel model = medicineModelList.get(i);
            if(model.getId()==ID){
                return model.getName();
            }
        }
        return null;
    }
}
