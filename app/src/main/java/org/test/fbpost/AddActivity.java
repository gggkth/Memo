package org.test.fbpost;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.time.LocalDate;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    public final static int RESULT_CODE_ADD = 100;
    public final static int RESULT_CODE_BACK = 200;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private EditText edit_title, edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edit_title = (EditText) findViewById(R.id.title);
        edit_content = (EditText) findViewById(R.id.content);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void write(View v) {
        String title = edit_title.getText().toString();
        String content = edit_content.getText().toString();
        insertData(title, content);

        Toast.makeText(getApplicationContext(), "추가 성공: " + LocalDate.now(), Toast.LENGTH_SHORT).show();
        setResult(RESULT_CODE_ADD);
        finish();
    }

    public void back(View v) {
        setResult(RESULT_CODE_BACK);
        finish();
    }

    private void insertData(String title,  String content) {
        String query="INSERT INTO tableName VALUES(null, ?, ?, '" + new Date().getTime() + "');";
        String[] selections={title, content};
        db.execSQL(query, selections);
    }

}