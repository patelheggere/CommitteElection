package com.patelheggere.committeelection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.model.AnubhandaModel;

import java.lang.reflect.Member;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private Context mContext;
    private List<AnubhandaModel> anubhandaModelList;

    public MemberAdapter(Context mContext, List<AnubhandaModel> anubhandaModelList) {
        this.mContext = mContext;
        this.anubhandaModelList = anubhandaModelList;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.rv_list_item, parent, false);
       return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        AnubhandaModel anubhandaModel = anubhandaModelList.get(position);
        if(anubhandaModel!=null)
        {
            holder.name.setText(anubhandaModel.getName());
            holder.id.setText(anubhandaModel.getId());
        }
    }

    @Override
    public int getItemCount() {
        return anubhandaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, id;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            id = itemView.findViewById(R.id.tv_id);
        }
    }
}

