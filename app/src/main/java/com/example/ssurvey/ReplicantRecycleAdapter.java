package com.example.ssurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplicantRecycleAdapter extends RecyclerView.Adapter<ReplicantRecycleAdapter.ViewHolder> {
    private ArrayList<String> replicantNames;

    @NonNull
    @Override
    public ReplicantRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replicant_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplicantRecycleAdapter.ViewHolder holder, int position) {
        holder.onBind(replicantNames.get(position));
    }

    public void setReplicantNames(ArrayList<String> replicantNames){
        this.replicantNames = replicantNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return replicantNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.user_name);
        }

        void onBind(String userName) {
            name.setText(userName);
        }
    }
}