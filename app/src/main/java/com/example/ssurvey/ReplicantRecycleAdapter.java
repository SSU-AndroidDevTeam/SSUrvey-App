package com.example.ssurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplicantRecycleAdapter extends RecyclerView.Adapter<ReplicantRecycleAdapter.ViewHolder> {
    private ArrayList<String> replicantNames;
    private ArrayList<String> replicantIds;
    private OnItemClickListener listener;

    // 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(String replicantId, String replicantName);
    }

    // 리스너 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReplicantRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replicant_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplicantRecycleAdapter.ViewHolder holder, int position) {
        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        // 아이디와 이름을 전달
                        listener.onItemClick(replicantIds.get(adapterPosition), replicantNames.get(adapterPosition));
                    }
                }
            }
        });

        // 아이템 데이터를 뷰에 바인딩
        holder.onBind(replicantNames.get(position));
    }

    public void setReplicantNamesAndIds(ArrayList<String> replicantNames, ArrayList<String> replicantIds) {
        this.replicantNames = replicantNames;
        this.replicantIds = replicantIds;
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