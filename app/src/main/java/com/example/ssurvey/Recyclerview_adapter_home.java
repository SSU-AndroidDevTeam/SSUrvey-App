package com.example.ssurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recyclerview_adapter_home extends RecyclerView.Adapter<Recyclerview_adapter_home.ViewHolder> {
    private List<SurveyItem> data;
    private OnItemClickListener onItemClickListener; // 인터페이스 선언

    public Recyclerview_adapter_home(List<SurveyItem> data) {
        this.data = data;
    }

    // 아이템 클릭 리스너를 설정하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 아이템 클릭 리스너를 위한 인터페이스
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SurveyItem item = data.get(position);
        holder.bind(item);

        // 아이템뷰가 클릭되었을 때의 이벤트 처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDdate;
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView imageViewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDdate = itemView.findViewById(R.id.textView_Ddate_itemview);
            textViewTitle = itemView.findViewById(R.id.textView_title_itemview);
            textViewDescription = itemView.findViewById(R.id.textView_discription_itemview);
            imageViewImage = itemView.findViewById(R.id.imageView_image_itemview);
        }

        // 이 코드를 사용해서 행의 내용을 구성
        public void bind(SurveyItem item) {
            textViewDdate.setText(item.getDate());
            textViewTitle.setText(item.getName());
            textViewDescription.setText(item.getDescription());
            // 이미지를 로드하는 코드 (Glide, Picasso 등을 사용할 수 있음)
            // imageViewImage.setImageResource(item.getImageResId());
        }
    }
}
