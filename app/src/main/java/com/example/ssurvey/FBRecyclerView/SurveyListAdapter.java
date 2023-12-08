package com.example.ssurvey.FBRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.ssurvey.SurveyResultRegistrant;
import com.example.ssurvey.Survey_complete;
import com.example.ssurvey.Survey_main;

import java.util.ArrayList;

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.SurveyListHolder> {
    private ArrayList<SurveyItem> arrayList;
    private Context context;
    private int filterCondition;

    private String currentUserID;

    public static final int FILTER_REGISTERED = 1;
    public static final int FILTER_PARTICIPATED = 2;
    public static final int FILTER_INVITED = 3;
    public static final int FILTER_ALL = 4;

    public SurveyListAdapter(ArrayList<SurveyItem> arrayList, Context context, int filterCondition) {
        this.arrayList = arrayList;
        this.context = context;
        this.filterCondition = filterCondition;
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
        SurveyItem currentItem = arrayList.get(position);

        // 필터링 조건에 따라 아이템을 제한하여 표시
        switch (filterCondition) {
            case FILTER_REGISTERED:
                //if (!currentItem.getRegisteredUserID().equals(currentUserID))
            {
                // 등록된 설문이 아닌 경우 아이템을 표시하지 않음
                //    return;
            }
            break;
            case FILTER_PARTICIPATED:
                //if (!currentItem.isParticipated(currentUserID))
            {
                // 참여한 설문이 아닌 경우 아이템을 표시하지 않음
                //    return;
            }
            break;
            case FILTER_INVITED:
                //if (!currentItem.isInvited(currentUserID))
            {
                // 초대받은 설문이 아닌 경우 아이템을 표시하지 않음
                //    return;
            }
            break;
            // FILTER_ALL의 경우 모든 아이템을 표시하므로 별도의 처리 필요 없음
        }

        Glide.with(holder.itemView)
                .load(R.drawable.ic_launcher_background) // TODO: 이미지 동적 로딩
                .into(holder.survey_image);
        holder.survey_name.setText(currentItem.getName());
        holder.survey_desc.setText(currentItem.getDescription());
        holder.survey_dday.setText(currentItem.getDateText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();

                switch (filterCondition) {
                    case SurveyListAdapter.FILTER_REGISTERED:
                        // 등록된 설문의 아이템뷰 클릭 시 수행할 동작
                        RegisteredClick(adapterPosition);
                        break;
                    case SurveyListAdapter.FILTER_PARTICIPATED:
                        // 참여한 설문의 아이템뷰 클릭 시 수행할 동작
                        ParticipatedClick(adapterPosition);
                        break;
                    case SurveyListAdapter.FILTER_INVITED:
                        // 참여한 설문의 아이템뷰 클릭 시 수행할 동작
                        InvitedClick(adapterPosition);
                        break;
                    case SurveyListAdapter.FILTER_ALL:
                        // 참여한 설문의 아이템뷰 클릭 시 수행할 동작
                        HomeClick(adapterPosition);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // 필터링된 아이템의 개수만 반환
        int count = 0;
        for (SurveyItem item : arrayList) {
            switch (filterCondition) {
                case FILTER_REGISTERED:
                    //if (item.getRegisteredUserID().equals(currentUserID))
                {
                    count++;
                }
                break;
                case FILTER_PARTICIPATED:
                    //if (item.isParticipated(currentUserID))
                {
                    count++;
                }
                break;
                case FILTER_INVITED:
                    //if (item.isInvited(currentUserID))
                {
                    count++;
                }
                break;
                case FILTER_ALL:
                    count++;
                    break;
            }
        }
        return count;
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

    private void RegisteredClick(int position) {
        // 클릭한 아이템의 정보를 가져와서 이동할 화면에 전달하거나 사용
        SurveyItem clickedItem = arrayList.get(position);
        String surveyId = clickedItem.getSurveyId();

        Intent intent = new Intent(context, SurveyResultRegistrant.class);
        intent.putExtra("surveyId", surveyId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void ParticipatedClick(int position) {
        // 클릭한 아이템의 정보를 가져와서 이동할 화면에 전달하거나 사용
        SurveyItem clickedItem = arrayList.get(position);
        String surveyId = clickedItem.getSurveyId();

        Intent intent = new Intent(context, Survey_complete.class);
        intent.putExtra("surveyId", surveyId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void InvitedClick(int position) {
        // 클릭한 아이템의 정보를 가져와서 이동할 화면에 전달하거나 사용
        SurveyItem clickedItem = arrayList.get(position);
        String surveyId = clickedItem.getSurveyId();

        Intent intent = new Intent(context, Survey_main.class);
        intent.putExtra("surveyId", surveyId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //참여한 경우와 아닌경우 구분할 필요있음
    }
    private void HomeClick(int position) {
        // 클릭한 아이템의 정보를 가져와서 이동할 화면에 전달하거나 사용
        SurveyItem clickedItem = arrayList.get(position);
        String surveyId = clickedItem.getSurveyId();

        Intent intent = new Intent(context, Survey_main.class);
        intent.putExtra("surveyId", surveyId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //참여한 경우와 아닌경우 구분할 필요있음
    }

}
