package com.leon.myreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.libraryService.SQLiteBooksDatabase;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.zlibrary.ui.android.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static SQLiteBooksDatabase ourDatabase;
    private ListView mListView;
    private List<String> mBookLists = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ourDatabase = new SQLiteBooksDatabase(getApplicationContext());
        mListView = (ListView) findViewById(R.id.listview);
        initData();
        MyAdatapter adatapter = new MyAdatapter();
        mListView.setAdapter(adatapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openBook(mBookLists.get(position));
            }
        });

    }

    private void initData() {
        mBookLists.add("/storage/emulated/0/Download/回到明朝当王爷 校对版.txt");
    }

    public void openBook(String path) {
        //Strin;g path = "/storage/emulated/0/Download/回到明朝当王爷 校对版.txt";
        //String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "666.txt";
        Book book = new Book(1, path, "王爷", null, null);
        FBReader.openBookActivity(this, book, null);
    }



    class MyAdatapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBookLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mBookLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder;
            if (null == convertView) {
                convertView = View.inflate(MainActivity.this, R.layout.item_book, null);
                viewHolder = new MyViewHolder();
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            }
            viewHolder = (MyViewHolder) convertView.getTag();
            //viewHolder.tvName.setText(mBookLists.get(position));
            return convertView;
        }
    }

    class MyViewHolder {
        TextView tvName;
    }

}
