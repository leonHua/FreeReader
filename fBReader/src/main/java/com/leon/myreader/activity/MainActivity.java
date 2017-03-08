package com.leon.myreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.zlibrary.ui.android.R;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBook(View view) {
        //String path = "/storage/emulated/0/Download/回到明朝当王爷 校对版.txt";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "666.txt";
        Book book = new Book(1, path, "王爷", null, null);
        FBReader.openBookActivity(this, book, null);
    }
}
