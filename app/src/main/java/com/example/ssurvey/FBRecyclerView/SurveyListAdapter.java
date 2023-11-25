package com.example.ssurvey.FBRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ssurvey.R;
import com.example.ssurvey.SurveyItem;

import java.util.ArrayList;

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.SurveyListHolder> {
    private ArrayList<SurveyItem> arrayList;
    private Context context;

    public SurveyListAdapter(ArrayList<SurveyItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SurveyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_item, parent, false);
        SurveyListHolder holder = new SurveyListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyListHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(R.drawable.ic_launcher_background) // TODO: 이미지 동적 로딩
                .into(holder.survey_image);
        holder.survey_name.setText(arrayList.get(position).getName());
        holder.survey_desc.setText(arrayList.get(position).getDescription());
        holder.survey_dday.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class SurveyListHolder extends RecyclerView.ViewHolder {
        private ImageView survey_image;
        private TextView survey_name;
        private TextView survey_desc;
        private TextView survey_dday;

        public SurveyListHolder(@NonNull View itemView) {
            super(itemView);
            this.survey_image = itemView.findViewById(R.id.survey_image);
            this.survey_name = itemView.findViewById(R.id.survey_name);
            this.survey_desc = itemView.findViewById(R.id.survey_desc);
            this.survey_dday = itemView.findViewById(R.id.survey_dday);
        }
    }
}

