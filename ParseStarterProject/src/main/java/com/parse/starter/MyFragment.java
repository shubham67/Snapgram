package com.parse.starter;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyFragment extends Fragment {
    RecyclerView r_view;
    LinearLayoutManager l_manage;
    ArrayList<mycard> myarr;
    ArrayList<mycard> myarrfollow;
    ArrayList<mycard> myarrpopular;
    ArrayList<mycard> removed;
    myadapter m_adap;
    ImageButton mainview;
    static View.OnClickListener listener;
    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    ArrayList<Bitmap> bitmaps2 = new ArrayList<Bitmap>();

    ImageView ib;
    ParseQuery<ParseUser> queryprofile;
    private String title;


    public static Fragment newInstance(String title, String username) {
        MyFragment mm = new MyFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putString("username", username);
        mm.setArguments(b);
        return mm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        Bundle b = getArguments();
        title = b.getString("title");
        if (title.equals("Users")) {

            View v = inflater.inflate(R.layout.activity_mainnew, container, false);
            r_view = (RecyclerView) v.findViewById(R.id.myrecycle);

            l_manage = new LinearLayoutManager(getActivity());
            r_view.setLayoutManager(l_manage);

            //mainview= (ImageButton) findViewById(R.id.fab);
            setuparraylist();
           /* setupmenu();*/
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView name = (TextView) v.findViewById(R.id.textViewName);
                    Fragment ff = MyFragment.newInstance("UserProfile", name.getText().toString());
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, ff);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            };
            m_adap = new myadapter(myarr);


            return v;
        }if (title.equals("Following")) {

            View v = inflater.inflate(R.layout.activity_mainnew, container, false);
            r_view = (RecyclerView) v.findViewById(R.id.myrecycle);

            l_manage = new LinearLayoutManager(getActivity());
            r_view.setLayoutManager(l_manage);

            //mainview= (ImageButton) findViewById(R.id.fab);
            setuparraylist2();
           /* setupmenu();*/
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView name = (TextView) v.findViewById(R.id.textViewName);
                    Fragment ff = MyFragment.newInstance("UserProfile", name.getText().toString());
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, ff);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            };
            m_adap = new myadapter(myarrfollow);


            return v;
        }if (title.equals("Popular Users")) {

            View v = inflater.inflate(R.layout.activity_mainnew, container, false);
            r_view = (RecyclerView) v.findViewById(R.id.myrecycle);

            l_manage = new LinearLayoutManager(getActivity());
            r_view.setLayoutManager(l_manage);

            //mainview= (ImageButton) findViewById(R.id.fab);
            setuparraylist3();
           /* setupmenu();*/
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView name = (TextView) v.findViewById(R.id.textViewName);
                    Fragment ff = MyFragment.newInstance("UserProfile", name.getText().toString());
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout, ff);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            };
            m_adap = new myadapter(myarrpopular);


            return v;
        } else if (title.equals("Profile")) {
            View v = inflater.inflate(R.layout.profile, container, false);


            queryprofile = ParseUser.getQuery();

            // Locate the objectId from the class
            queryprofile.getInBackground(ParseUser.getCurrentUser().getObjectId(),
                    new GetCallback<ParseUser>() {

                        public void done(ParseUser object,
                                         ParseException e) {

                            ParseFile fileObject = (ParseFile) object.get("profile_pic");

                            if (fileObject != null) {
                                fileObject.getDataInBackground(new GetDataCallback() {

                                    public void done(byte[] data,
                                                     ParseException e) {
                                        if (e == null) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            if (getActivity() != null) {
                                                ib = (ImageView) getActivity().findViewById(R.id.imageButton2);
                                                if(ib != null)
                                                    ib.setImageBitmap(bitmap);
                                            }

                                        } else {
                                            Toast.makeText(getActivity(), "There was an error getting you Profile Pic", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    });

            return v;
        } else if (title.equals("Contact")) {
            View v = inflater.inflate(R.layout.contact, container, false);
            return v;
        } else if (title.equals("Home")) {


            bitmaps2.clear();
            View v = inflater.inflate(R.layout.myuserprofile, container, false);
            final GridView gf = (GridView) v.findViewById(R.id.gridView2);


            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("images");
            query2.whereEqualTo("username", (String) ParseUser.getCurrentUser().get("username"));


            query2.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {


                        if (objects.size() > 0) {

                            Log.i("AppInfo", "object :" + objects.size());
                            for (ParseObject user : objects) {


                                ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("images");


                                query3.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        ParseFile fileObject = (ParseFile) object.get("image");

                                            fileObject.getDataInBackground(new GetDataCallback() {

                                                public void done(byte[] data, ParseException e) {
                                                    if (e == null) {
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                        bitmaps2.add(bitmap);
                                                        Log.v("AppInfo", "" + bitmaps2.size());

                                                        if (bitmaps2.size() == 1) {


                                                            gf.setAdapter(new gridadap2());

                                                        } else {
                                                            Log.v("AppInfo", "setadapter");
                                                            gridadap2 adap = (gridadap2) gf.getAdapter();
                                                            adap.notifyDataSetChanged();
                                                        }
                                                        gridadap2 adap = (gridadap2) gf.getAdapter();
                                                        adap.notifyDataSetChanged();


                                                    } else {
                                                        Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_LONG).show();

                                                        e.printStackTrace();
                                                    }
                                                }
                                            });

                                    }
                                });


                            }


                        }


                    }else{
                        Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_LONG).show();

                        e.printStackTrace();
                    }


                }
            });


            return v;
        } else if (title.equals("UserProfile")) {
            bitmaps.clear();
            View v = inflater.inflate(R.layout.userprofile, container, false);
            final GridView gf = (GridView) v.findViewById(R.id.gridView);
            final ImageView iv = (ImageView) v.findViewById(R.id.imageView4);
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", getArguments().get("username"));
            // Locate the objectId from the class
            query.getFirstInBackground(new GetCallback<ParseUser>() {

                public void done(ParseUser object, ParseException e) {

                    ParseFile fileObject = (ParseFile) object.get("profile_pic");
                    if (fileObject != null) {
                        fileObject.getDataInBackground(new GetDataCallback() {

                            public void done(byte[] data,
                                             ParseException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    iv.setImageBitmap(bitmap);

                                } else {
                                    Toast.makeText(getActivity(), "There was an error getting the Profile Pic", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });

            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("images");
            query2.whereEqualTo("username", getArguments().get("username"));
            query2.addAscendingOrder("createdAt");

            query2.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {


                        if (objects.size() > 0) {


                            for (ParseObject user : objects) {


                                ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("images");

                                Log.v("AppInfo", "ID:" + user.getObjectId());
                                query3.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        ParseFile fileObject = (ParseFile) object.get("image");
                                        if (fileObject != null) {

                                            fileObject.getDataInBackground(new GetDataCallback() {

                                                public void done(byte[] data, ParseException e) {
                                                    if (e == null) {
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                        bitmaps.add(bitmap);
                                                        Log.v("AppInfo", "" + bitmaps.size());

                                                        if (bitmaps.size() == 1) {


                                                            gf.setAdapter(new gridadap());
                                                            gridadap adap = (gridadap) gf.getAdapter();
                                                            adap.notifyDataSetChanged();

                                                        } else {
                                                            Log.v("AppInfo", "setadapter");
                                                            gridadap adap = (gridadap) gf.getAdapter();
                                                            adap.notifyDataSetChanged();
                                                        }



                                                    } else {
                                                        Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_LONG).show();

                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });


                            }


                        }


                    }


                }
            });


            return v;
        } else {

            View v = inflater.inflate(R.layout.myfragment, container, false);
            TextView t = (TextView) v.findViewById(R.id.textView6);
            t.setText(title);
            //iv.setOnClickListener(((Material_Example)getActivity()).longlistener);
            return v;
        }

    }


    private void setuparraylist() {
        myarr = new ArrayList<mycard>();

        removed = new ArrayList<mycard>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {


                        for (final ParseUser user : objects) {
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("username", user.getUsername());
                            // Locate the objectId from the class
                            query.getFirstInBackground(new GetCallback<ParseUser>() {

                                public void done(ParseUser object, ParseException e) {

                                    ParseFile fileObject = (ParseFile) object.get("profile_pic");
                                    if (fileObject != null) {
                                        fileObject.getDataInBackground(new GetDataCallback() {

                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                    boolean checked = false;
                                                    if(ParseUser.getCurrentUser().getList("isFollowing") != null && ParseUser.getCurrentUser().getList("isFollowing").contains(user.getUsername())) {
                                                        checked = true;
                                                        Log.v("AppInfo", "true");
                                                        mycard m = new mycard(checked, user.getUsername(), (String) user.get("profile_status"), bitmap);
                                                        myarr.add(m);
                                                        r_view.setAdapter(m_adap);
                                                        r_view.setItemAnimator(new DefaultItemAnimator());
                                                    }else{
                                                        Log.v("AppInfo", "false");
                                                            mycard m = new mycard(checked, user.getUsername(), (String) user.get("profile_status"), bitmap);
                                                            myarr.add(m);
                                                            r_view.setAdapter(m_adap);
                                                            r_view.setItemAnimator(new DefaultItemAnimator());
                                                    }





                                                } else {
                                                    Toast.makeText(getActivity(), "There was an error getting the Profile Pic", Toast.LENGTH_LONG).show();
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                            });


                        }




                    }


                }

            }
        });


    }
    private void setuparraylist2() {

        myarrfollow = new ArrayList<mycard>();


        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {


                        for (final ParseUser user : objects) {
                            if(ParseUser.getCurrentUser().getList("isFollowing").contains(user.getUsername())) {
                                ParseQuery<ParseUser> query = ParseUser.getQuery();
                                query.whereEqualTo("username", user.getUsername());
                                // Locate the objectId from the class
                                query.getFirstInBackground(new GetCallback<ParseUser>() {

                                    public void done(ParseUser object, ParseException e) {

                                        ParseFile fileObject = (ParseFile) object.get("profile_pic");
                                        if (fileObject != null) {
                                            fileObject.getDataInBackground(new GetDataCallback() {

                                                public void done(byte[] data, ParseException e) {
                                                    if (e == null) {
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


                                                        mycard m = new mycard(true, user.getUsername(), (String) user.get("profile_status"), bitmap);
                                                        myarrfollow.add(m);
                                                        r_view.setAdapter(m_adap);
                                                        r_view.setItemAnimator(new DefaultItemAnimator());


                                                    } else {
                                                        Toast.makeText(getActivity(), "There was an error getting the Profile Pic", Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }




                    }


                }

            }
        });


    }
    private void setuparraylist3() {
        myarrpopular = new ArrayList<mycard>();



        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addDescendingOrder("popularity");


        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {


                        for (final ParseUser user : objects) {
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("username", user.getUsername());
                            // Locate the objectId from the class
                            query.getFirstInBackground(new GetCallback<ParseUser>() {

                                public void done(ParseUser object, ParseException e) {

                                    ParseFile fileObject = (ParseFile) object.get("profile_pic");
                                    if (fileObject != null) {
                                        fileObject.getDataInBackground(new GetDataCallback() {

                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                    boolean checked = false;
                                                    if(ParseUser.getCurrentUser().getList("isFollowing") != null && ParseUser.getCurrentUser().getList("isFollowing").contains(user.getUsername())) {
                                                        checked = true;
                                                        Log.v("AppInfo", "true");
                                                        mycard m = new mycard(checked, user.getUsername(), (String) user.get("profile_status"), bitmap);
                                                        myarrpopular.add(m);
                                                        r_view.setAdapter(m_adap);
                                                        r_view.setItemAnimator(new DefaultItemAnimator());
                                                    }else{
                                                        Log.v("AppInfo", "false");
                                                        mycard m = new mycard(checked, user.getUsername(), (String) user.get("profile_status"), bitmap);
                                                        myarrpopular.add(m);
                                                        r_view.setAdapter(m_adap);
                                                        r_view.setItemAnimator(new DefaultItemAnimator());
                                                    }





                                                } else {
                                                    Toast.makeText(getActivity(), "There was an error getting the Profile Pic", Toast.LENGTH_LONG).show();
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                            });


                        }




                    }


                }

            }
        });


    }

    public class gridadap extends BaseAdapter {
        @Override
        public int getCount() {
            Log.v("Appinfo", "size: " + bitmaps.size());
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            myholder mHolder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.mygrid, parent, false);

                ImageView iv = (ImageView) convertView.findViewById(R.id.imageView5);
                mHolder = new myholder(iv);
                convertView.setTag(mHolder);
            } else
                mHolder = (myholder) convertView.getTag();


            mHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // show(position);
                }
            });

            mHolder.iv.setImageBitmap(bitmaps.get(position));
            return convertView;
        }


        class myholder {
            ImageView iv;

            myholder(ImageView i) {
                iv = i;
            }
        }




    }
    public class gridadap2 extends BaseAdapter {
        @Override
        public int getCount() {
            Log.v("Appinfo", "size: " + bitmaps2.size());
            return bitmaps2.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            myholder mHolder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.mygrid2, parent, false);

                ImageView iv = (ImageView) convertView.findViewById(R.id.imageView6);
                mHolder = new myholder(iv);
                convertView.setTag(mHolder);
            } else
                mHolder = (myholder) convertView.getTag();


            mHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // show(position);
                }
            });

            mHolder.iv.setImageBitmap(bitmaps2.get(position));
            Log.v("AppInfo", "returning" + position);
            return convertView;
        }


        class myholder {
            ImageView iv;

            myholder(ImageView i) {
                iv = i;
            }
        }
    }
}