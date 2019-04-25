package com.xcelcorp.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.Viewholder> {

    private Context mContext;
    private List<EmployeeModelClass> employeeModelClasses;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public EmployeeListAdapter(Context context, List<EmployeeModelClass> employeeModelClasses){
        mContext = context;
        this.employeeModelClasses=employeeModelClasses;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {

        String profileUrl=employeeModelClasses.get(position).getProfilePicUrl();
        String name=employeeModelClasses.get(position).getName();
        String id=employeeModelClasses.get(position).getId();
        String age=employeeModelClasses.get(position).getAge();
        String salary=employeeModelClasses.get(position).getSalary();

        viewholder.setData(profileUrl,name,id,age,salary);

    }

    @Override
    public int getItemCount() {
        return employeeModelClasses.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView tvName,tvId,tvAge,tvSalary;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profile_image);
            tvName = itemView.findViewById(R.id.employee_name);
            tvId = itemView.findViewById(R.id.employee_id);
            tvAge = itemView.findViewById(R.id.employee_age);
            tvSalary = itemView.findViewById(R.id.employee_salary);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        private void setData(String profileUrl, String name, String id, String age, String salary){

            if(!(profileUrl.equals(""))) {
                Glide
                        .with(mContext)
                        .load(profileUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile_default)
                        .into(profilePic);
            }

            tvName.setText(name);
            tvId.setText(id);
            tvAge.setText(age);
            tvSalary.setText(salary);
        }
    }
}
