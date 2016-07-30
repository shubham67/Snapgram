package com.parse.starter;


import android.graphics.Bitmap;

public class mycard {

    boolean checked;
    String name, email;
    Bitmap bitmap;
    mycard(boolean c, String n, String e, Bitmap u) {
        checked = c;
        name = n;
        email = e;
        bitmap = u;
    }
}
