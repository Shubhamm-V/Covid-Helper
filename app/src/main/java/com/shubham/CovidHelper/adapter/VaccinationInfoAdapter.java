package com.shubham.CovidHelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.CovidHelper.R;
import com.shubham.CovidHelper.model.VaccineModel;

import java.util.List;

public class VaccinationInfoAdapter extends RecyclerView.Adapter<VaccinationInfoAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<VaccineModel> list_vaccine_center;

    public VaccinationInfoAdapter(Context mcontext, List<VaccineModel> list_vaccine_center) {
        this.layoutInflater = LayoutInflater.from(mcontext);
        this.list_vaccine_center = list_vaccine_center;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.vaccine_info_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.vaccinationCenter.setText(list_vaccine_center.get(position).getVaccineCenter());
        holder.vaccinationCenterAddr.setText(list_vaccine_center.get(position).getVaccineCenterAddress());
        holder.vaccinationTiming.setText(list_vaccine_center.get(position).getVaccinationTimings() + "-" +
                list_vaccine_center.get(position).getVaccineCenterTime());
        holder.vaccinationAvailable.setText("Available");
        holder.vaccinationAge.setText("Min-Age : " + list_vaccine_center.get(position).getVaccinationAge());
        holder.vaccineCharges.setText(list_vaccine_center.get(position).getVaccinationCharges());
        holder.vaccineName.setText(list_vaccine_center.get(position).getVaccineName());
    }

    @Override
    public int getItemCount() {
        return list_vaccine_center.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vaccinationCenter,vaccinationCenterAddr,vaccinationTiming,vaccineName,vaccineCharges,vaccinationAge,vaccinationAvailable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vaccinationAge = (TextView)itemView.findViewById(R.id.vaccinationAge);
            vaccinationCenterAddr = (TextView)itemView.findViewById(R.id.vaccineLocation);
            vaccinationCenter = (TextView)itemView.findViewById(R.id.vaccinationCenter);
            vaccinationTiming = (TextView)itemView.findViewById(R.id.vaccineTime);
            vaccineName = (TextView)itemView.findViewById(R.id.vaccineName);
            vaccineCharges = (TextView)itemView.findViewById(R.id.vaccineCharges);
            vaccinationAvailable = (TextView)itemView.findViewById(R.id.vaccinationAge);
        }
    }
}
