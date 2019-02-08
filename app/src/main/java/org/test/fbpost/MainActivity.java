package org.test.fbpost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MemoAdapter memoAdapter;
    private EditText searchText;

    private List<Memo> memoList;
    private ArrayList<Memo> arrayList;

    private final static int REQUEST_CODE_ADD = 101;
    private final static int REQUEST_CODE_SHOW = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        initView();
        updateData();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        memoAdapter = new MemoAdapter(getApplicationContext());
        searchText = findViewById(R.id.searchText);

        mRecyclerView.setAdapter(memoAdapter);
        memoAdapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder holder, View view, int position) {
                Memo m = memoAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                intent.putExtra("id", m.getId());

                startActivityForResult(intent, REQUEST_CODE_SHOW);
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchText.getText().toString();
                search(text);
            }
        });
    }

    private void updateData() {
        memoAdapter.setItem(getData());
        runAnimation(mRecyclerView);
    }

    private  List<Memo> getData() {
        Cursor cursor = db.rawQuery("SELECT * FROM tableName", null);
        cursor.moveToFirst();
        memoList = new ArrayList<>();
        do{
            if(cursor.getCount() > 0) {
                memoList.add(new Memo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), new Date(cursor.getLong(3))));
            }
        } while(cursor.moveToNext());
        cursor.close();

        arrayList = new ArrayList<Memo>();
        arrayList.addAll(memoList);

        return memoList;
    }

    private void search(String charText) {
        memoList.clear();
        if(charText.length() == 0) {
            memoList.addAll(arrayList);
        }
        else {
            for(int i=0; i < arrayList.size(); i++) {
                if(arrayList.get(i).getTitle().toLowerCase().contains(charText)) {
                    memoList.add(arrayList.get(i));
                }
            }
        }
        memoAdapter.setItem(memoList);
        runAnimation(mRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_ADD:
                if(resultCode == AddActivity.RESULT_CODE_ADD) {
                    Log.d("TAG", "onActivityResult >> requestCode = "+requestCode+", resultCode = " + resultCode);
                    updateData();
                }
                break;

            case REQUEST_CODE_SHOW:
                if(resultCode == ShowActivity.RESULT_CODE_SHOW) {
                    updateData();
                }
                break;
        }
    }

    private void runAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;
//        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_from_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}