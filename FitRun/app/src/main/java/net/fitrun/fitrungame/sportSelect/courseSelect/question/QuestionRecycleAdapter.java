package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.OnItemClickListener;

import java.util.List;

import static net.fitrun.fitrungame.R.id.down_question;
import static net.fitrun.fitrungame.R.id.question_line;

/**
 * Created by 晁东洋 on 2017/4/11.
 * 调查问卷的数据适配器
 */

public class QuestionRecycleAdapter extends RecyclerView.Adapter<QuestionRecycleAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<QuestionBean.ContentBean> mList ;
    private OnItemClickListener mListener;
    public QuestionRecycleAdapter(Context context, List<QuestionBean.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_question_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (mList.get(position).getSelectType() ==0){
            holder.question_title.setText(position+1+"、"+mList.get(position).getContent());
        }else {
            holder.question_title.setText(position+1+"、"+mList.get(position).getContent()+"(可多选)");
        }
        Typeface typeFace_sport =Typeface.createFromAsset(mContext.getAssets(),"fonts/FANGZHENG.TTF");
        Typeface typeFace_jan =Typeface.createFromAsset(mContext.getAssets(),"fonts/JINGDIANZONG.TTF");
        holder.question_title.setTypeface(typeFace_sport);
        holder.sport_type_name.setTypeface(typeFace_jan);
        holder.sport_type_kl.setTypeface(typeFace_jan);
        holder.sport_type_b.setTypeface(typeFace_jan);
        holder.sport_type_kr.setTypeface(typeFace_jan);
        holder.question_position.setTypeface(typeFace_jan);
        holder.question_count.setTypeface(typeFace_jan);
        holder.question_position.setText(position+1+"");
        holder.question_count.setText(mList.size()+"");
        //内容的布局管理器

        DefaultSingleAdapter madapter = new DefaultSingleAdapter(mContext);
        DefaultMultipleAdapter mDefaultMultipleAdapter = new DefaultMultipleAdapter(mContext);
        mDefaultMultipleAdapter.addItems(mList.get(position).getQuestionResults(),1,position);
        madapter.addItems(mList.get(position).getQuestionResults(),0,position);
        if (mList.get(position).getSelectType() ==0){
            holder.question_item_content.setAdapter(madapter);
        }else {
            holder.question_item_content.setAdapter(mDefaultMultipleAdapter);
        }

        //设置布局管理
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        holder.question_item_content.setLayoutManager(manager);

        if (position+1 == getItemCount()){
            holder.question_line.setVisibility(View.VISIBLE);
            holder.lock_layout.setVisibility(View.VISIBLE);
            holder.lock_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,"");
                }
            });
            holder.down_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,"最后一项");
                }
            });

        }else {
            holder.question_line.setVisibility(View.GONE);
            holder.lock_layout.setVisibility(View.GONE);
            holder.down_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,1);
                }
            });
        }

        if (position ==0){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)holder.question_item_content.getLayoutParams();
            lp.setMargins(30,0,30,24);
            holder.question_item_content.setLayoutParams(lp);
            holder.up_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,"最后一项");
                }
            });
        }else if (position+1 == getItemCount()){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)holder.question_item_content.getLayoutParams();
            lp.setMargins(30,0,30,0);
            holder.question_item_content.setLayoutParams(lp);
            holder.up_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,1);
                }
            });
        }else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)holder.question_item_content.getLayoutParams();
            lp.setMargins(30,0,30,29);
            holder.question_item_content.setLayoutParams(lp);
            holder.up_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(),position,1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getSelectType();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerView question_item_content;
        TextView next;
        TextView  question_title;
        TextView  question_position;
        TextView  question_count;
        LinearLayout bc_question;
        ImageView down_question;
        ImageView up_question;
        ImageView lock_result;
        LinearLayout lock_layout;
        View question_line;
        TextView sport_type_name;
        TextView sport_type_kl;
        TextView sport_type_b;
        TextView sport_type_kr;
        public MyViewHolder(View itemView) {
            super(itemView);
            question_line = (View)itemView.findViewById(R.id.question_line);
            bc_question = (LinearLayout)itemView.findViewById(R.id.bc_question);
            question_item_content = (RecyclerView) itemView.findViewById(R.id.question_item_content);
            next = (TextView) itemView.findViewById(R.id.next);
            sport_type_name = (TextView)itemView.findViewById(R.id.sport_type_name);
            sport_type_kl = (TextView)itemView.findViewById(R.id.sport_type_kl);
            sport_type_b = (TextView)itemView.findViewById(R.id.sport_type_b);
            sport_type_kr = (TextView)itemView.findViewById(R.id.sport_type_kr);

            question_title = (TextView)itemView.findViewById(R.id.question_title);
            question_position = (TextView)itemView.findViewById(R.id.question_position);
            question_count = (TextView)itemView.findViewById(R.id.question_count);
            down_question = (ImageView)itemView.findViewById(R.id.down_question);
            up_question = (ImageView)itemView.findViewById(R.id.up_question);
            lock_layout = (LinearLayout)itemView.findViewById(R.id.lock_layout);
            lock_result = (ImageView)itemView.findViewById(R.id.lock_result);

        }
    }
}
