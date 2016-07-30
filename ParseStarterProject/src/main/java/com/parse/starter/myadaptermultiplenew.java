package com.parse.starter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;



public class myadaptermultiplenew extends RecyclerView.Adapter<myadaptermultiplenew.myholder> {
    myholder m;
    String texts[];
    int ids[];
    int flag = 0;
    Bitmap bitmap;
final static int VIEW_HEADER=0;
    final static int VIEW_ROW=1;
    public myadaptermultiplenew(String t[],int id[])
    {
        texts=t;
        ids=id;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_HEADER;
        else
            return VIEW_ROW;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater lf=LayoutInflater.from(viewGroup.getContext());
View v;

        if(i==VIEW_HEADER) {
            v = lf.inflate(R.layout.header_view, viewGroup, false);
            m=new myholder(v,VIEW_HEADER);
            return m;
        }
else {
            v = lf.inflate(R.layout.row_view, viewGroup, false);
            v.setOnClickListener(Material_Example.listener);
            m=new myholder(v,VIEW_ROW);
            return m;

        }



    }


    @Override
    public void onBindViewHolder(final myholder m, int i) {

        if(i!=0) {
            m.cimage.setImageResource(ids[i-1]);

            m.name.setText(texts[i-1]);
        }
        else
        {

            ParseQuery<ParseUser> query = ParseUser.getQuery();

            // Locate the objectId from the class
            query.getInBackground(ParseUser.getCurrentUser().getObjectId(),
                    new GetCallback<ParseUser>() {

                        public void done(ParseUser object,
                                         ParseException e) {
                            if(object != null){

                            ParseFile fileObject = (ParseFile) object.get("profile_pic");
                            if(fileObject != null) {
                                fileObject
                                        .getDataInBackground(new GetDataCallback() {

                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                    flag = 1;
                                                    m.cimage.setImageBitmap(bitmap);

                                                } else {

                                                }
                                            }
                                        });
                            }
                            }
                        }
                    });
            String status = (String)ParseUser.getCurrentUser().get("profile_status");

            if(flag == 1){
                //m.cimage.setImageBitmap(bitmap);
            }else
                m.cimage.setImageResource(R.drawable.four);
            m.email.setText((String) ParseUser.getCurrentUser().getUsername());

            if(status != null){
                m.name.setText(status);
            }else
                m.name.setText("Hey wassup!!!");
        }

         }

    @Override
    public int getItemCount()  {return ids.length+1;}


    public static class myholder extends RecyclerView.ViewHolder
    {
        ImageView cimage;
        TextView name;
        TextView email;


        public myholder(View itemView,int id) {
            super(itemView);
if(id==VIEW_HEADER)
{
    cimage= (ImageView) itemView.findViewById(R.id.image);
    email= (TextView) itemView.findViewById(R.id.email);
    name=(TextView)itemView.findViewById(R.id.name);
}
            else
{
    cimage= (ImageView) itemView.findViewById(R.id.icon);
    name=(TextView)itemView.findViewById(R.id.text);
}

        }
    }



}
