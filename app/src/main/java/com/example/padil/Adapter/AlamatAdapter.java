package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padil.Model.AlamatModel;
import com.example.padil.R;

import org.w3c.dom.Text;

import java.util.List;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.ViewHolder> {

    Context context;
    List<AlamatModel> alamatModelList;
    SelectedAddress selectedAddress;

    private RadioButton selectedRadioBtn;

    public AlamatAdapter(Context context, List<AlamatModel> alamatModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.alamatModelList = alamatModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AlamatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alamat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlamatAdapter.ViewHolder holder, int position) {

        holder.alamat.setText(alamatModelList.get(position).getUserAlamat());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AlamatModel alamatModel: alamatModelList){
                    alamatModel.setSelected(false);
                }
                alamatModelList.get(position).setSelected(true);

                if (selectedRadioBtn != null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAlamat(alamatModelList.get(position).getUserAlamat());
            }
        });
    }

    @Override
    public int getItemCount() {
        return alamatModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView alamat;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alamat = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }
    public interface SelectedAddress{
        void setAlamat(String alamat);
    }
}
