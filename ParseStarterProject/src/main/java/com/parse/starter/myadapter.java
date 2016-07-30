package com.parse.starter;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;


public class myadapter extends RecyclerView.Adapter<myadapter.myholder> {

    ArrayList<mycard> arraylist;

    public myadapter(ArrayList<mycard> ar) {
        arraylist=ar;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup viewGroup, int i) {
    LayoutInflater lf=LayoutInflater.from(viewGroup.getContext());
        View v=lf.inflate(R.layout.card_lay,viewGroup,false);
        v.setOnClickListener(MyFragment.listener);
        myholder m=new myholder(v);
        return m;

    }

    @Override
    public void onBindViewHolder(myholder m, int i) {

        m.cbox.setChecked(arraylist.get(i).checked);
        m.cbox.setOnCheckedChangeListener(new ccl(i));
        m.cimage.setImageBitmap(arraylist.get(i).bitmap);
        m.name.setText(arraylist.get(i).name);
        m.email.setText(arraylist.get(i).email);

    }

    @Override
    public int getItemCount()  {return arraylist.size();}

    public static class myholder extends RecyclerView.ViewHolder
    {
        ImageView cimage;
        TextView name,email;
        CheckBox cbox;

        public myholder(View itemView) {
            super(itemView);
            cbox = (CheckBox) itemView.findViewById(R.id.checkBox);

            cimage= (ImageView) itemView.findViewById(R.id.imageView);
            name= (TextView) itemView.findViewById(R.id.textViewName);
            email= (TextView) itemView.findViewById(R.id.textViewEmail);
        }
    }

    private class ccl implements CompoundButton.OnCheckedChangeListener {
        int i;
        ccl(int i){
            this.i = i;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){

                ParseUser.getCurrentUser().getList("isFollowing").add(arraylist.get(i).name);
                ParseUser.getCurrentUser().saveInBackground();


            }else{

                ParseUser.getCurrentUser().getList("isFollowing").remove(arraylist.get(i).name);
                ParseUser.getCurrentUser().saveInBackground();

            }
        }
    }
}
