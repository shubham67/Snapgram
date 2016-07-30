package com.parse.starter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Material_Example extends ActionBarActivity
{

    private android.support.v7.widget.Toolbar toolbar;
    RecyclerView r_view;
    myadaptermultiplenew m_adap;
    String text[] = {"Home","Profile","Popular Users", "Users", "Following", "Contact","Rate Us"};
    int imgs[] = {R.drawable.home,R.drawable.setting, R.drawable.profile, R.drawable.profile, R.drawable.profile,R.drawable.contact, R.drawable.rate};
    DrawerLayout dl;
    ActionBarDrawerToggle bar_toggle;
    static View.OnClickListener listener;
    ShareActionProvider sp;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materiallayside);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        m_adap=new myadaptermultiplenew(text,imgs);
        r_view= (RecyclerView) findViewById(R.id.RecyclerView);
        r_view.setHasFixedSize(true);
        r_view.setAdapter(m_adap);


        Fragment ff = MyFragment.newInstance("Home", null);
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, ff);

        ft.commit();






        listener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView t = (TextView) v.findViewById(R.id.text);
            String title = t.getText().toString();
            if(title.equals("Rate Us")){
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.satechnologies.spaceshooter2")));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.satechnologies.spaceshooter2&hl=en")));
                }
            }else if(title.equals("Users")) {
                Fragment ff = MyFragment.newInstance(title, null);
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, ff);
                ft.addToBackStack(title);
                ft.commit();

            }else if(title.equals("Following")) {
                Fragment ff = MyFragment.newInstance(title, null);
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, ff);
                ft.addToBackStack(title);
                ft.commit();

            }else if(title.equals("Popular Users")) {
                Fragment ff = MyFragment.newInstance(title, null);
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, ff);
                ft.addToBackStack(title);
                ft.commit();

            }else{

                Fragment ff = MyFragment.newInstance(title, null);
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, ff);

                ft.commit();
            }
            dl.closeDrawer(r_view);
        }
    };
        r_view.setLayoutManager(new LinearLayoutManager(this));
        dl= (DrawerLayout) findViewById(R.id.DrawerLayout);
        bar_toggle=new ActionBarDrawerToggle(this,dl,toolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        dl.setDrawerListener(bar_toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bar_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        bar_toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.share) {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);


            return true;
        }else if (id == R.id.camera) {

            Intent cameraI = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraI, 3);

        }else if (id == R.id.share) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);


                return true;





        }if (id == R.id.logout) {

            ParseUser.logOut();
            Intent i = new Intent(Material_Example.this, MainActivity.class);
            startActivity(i);

            return true;
        }



        if(bar_toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Log.i("AppInfo", "Image Received");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);




                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = new ParseObject("images");

                object.put("username", ParseUser.getCurrentUser().getUsername());

                object.put("image", file);



                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplication().getBaseContext(), "Your image has been posted!", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();


            }


        }else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {


                final Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Log.i("AppInfo", "Image Received");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);


                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = ParseUser.getCurrentUser();



                object.put("profile_pic", file);


                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplication().getBaseContext(), "Your image has been posted!", Toast.LENGTH_LONG).show();
                            ImageView iv = (ImageView) findViewById(R.id.image);
                            ImageView ib = (ImageView) findViewById(R.id.imageButton2);
                            ib.setImageBitmap(bitmapImage);
                            iv.setImageBitmap(bitmapImage);
                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();


            }


        }else if (requestCode == 3 && resultCode == RESULT_OK && data != null) {

            Bitmap picture = (Bitmap) data.getExtras().get("data");

            Toast.makeText(getApplication().getBaseContext(), "Your image 1!", Toast.LENGTH_LONG).show();

            try {



                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                picture.compress(Bitmap.CompressFormat.PNG, 100, stream);


                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = new ParseObject("images");

                object.put("username", ParseUser.getCurrentUser().getUsername());

                object.put("image", file);


                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplication().getBaseContext(), "Your image has been posted!", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again" + e.getCause().toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }
                });



            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();


            }


        }

    }

    public void mail(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"shubhamsharma67@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporting a Bug");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));






        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }


    public void call(View v){
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:0996-863-6370"));
        startActivity(i);
    }

    public void change_profile_pic(View v){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);

    }

    public void update(View v){
        ParseObject object = ParseUser.getCurrentUser();

        final EditText et = (EditText) findViewById(R.id.editText2);

        object.put("profile_status", et.getText().toString());


        object.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    Toast.makeText(getApplication().getBaseContext(), "Your status has been saved!", Toast.LENGTH_LONG).show();
                    TextView name2 = (TextView) findViewById(R.id.name);
                    name2.setText(et.getText().toString());
                } else {

                    Toast.makeText(getApplication().getBaseContext(), "There was an error - please try again", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }
        });


    }

}
