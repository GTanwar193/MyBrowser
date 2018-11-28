package com.engineering.blockbrew.mysearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gaurav Kumar Tanwar on 27-11-2018.
 */
public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    Activity mActivity;
    LayoutInflater mInflater;
    UserInfo userInfo;
    ArrayList<String> arrayListBookMark;

    public BookMarkAdapter(Activity mActivity, ArrayList<String> arrayListBookMark) {
        this.mActivity = mActivity;
        this.arrayListBookMark = arrayListBookMark;
        mInflater = (LayoutInflater) mActivity.
                getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.layout_bookmark, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtMenuName.setText(arrayListBookMark.get(i));

        viewHolder.txt_no.setText(String.valueOf(i+1));

        viewHolder.txtMenuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mActivity,MainActivity.class);
                intent.putExtra("strUrl",viewHolder.txtMenuName.getText().toString());
                mActivity.startActivity(intent);
                mActivity.finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListBookMark.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtMenuName;
        TextView txt_no;
        boolean aBoolean = false;
        View v;


        public ViewHolder(View itemView) {
            super(itemView);
            txtMenuName = (TextView) itemView.findViewById(R.id.txt_bookmark);
            txt_no= (TextView) itemView.findViewById(R.id.txt_no);
            userInfo = new UserInfo(mActivity);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            this.v = v;
            switch (v.getId()) {

                case R.id.txt_bookmark:


                    break;

                default:
                    break;
            }

        }
    }

}
