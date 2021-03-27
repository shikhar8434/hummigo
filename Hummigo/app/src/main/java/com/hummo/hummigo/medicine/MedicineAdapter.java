package com.hummo.hummigo.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hummo.hummigo.R;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {

    ArrayList<MedicineHelper> mlist;
    Context context;

    public MedicineAdapter(Context context,ArrayList<MedicineHelper> mlist){
        this.mlist=mlist;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.medicard,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MedicineHelper medihelp= mlist.get(position);
        holder.medicinename.setText(medihelp.getMedicinename());
        holder.medicinedesc.setText(medihelp.getMedicinedesc());
        holder.medicinepriority.setText(Integer.toString(medihelp.getMedicinepriority()));


    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView medicinename,medicinedesc,medicinepriority;
        private DatabaseReference dbref;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medicinename= itemView.findViewById(R.id.title_single_med);
            medicinedesc=itemView.findViewById(R.id.desc_single_med);
            medicinepriority=itemView.findViewById(R.id.prior);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int p= getLayoutPosition();
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    dbref= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("medicine");
                    dbref.child(String.valueOf(p)).removeValue();


                    return true;
                }
            });




        }
    }

}
