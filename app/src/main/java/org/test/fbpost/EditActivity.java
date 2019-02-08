package org.test.fbpost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    int ID;
    EditText edit_title, edit_content;

    public final static int RESULT_CODE_EDIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        ID = intent.getIntExtra("id", 0);

        edit_title = (EditText) findViewById(R.id.title);
        edit_content = (EditText) findViewById(R.id.content);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM tableName WHERE id = ?", new String[]{String.valueOf(ID)});
        cursor.moveToFirst();

        edit_title.setText(cursor.getString(1));
        edit_content.setText(cursor.getString(2));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(View v) {
        String title = edit_title.getText().toString();
        String content = edit_content.getText().toString();
        String query="UPDATE tableName SET title = ?, content = ?, created_date = '" + new Date().getTime() + "' WHERE id = ?";
        String[] selections={title, content, String.valueOf(ID)};
        db.execSQL(query, selections);
        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
        intent.putExtra("id", ID);
        setResult(RESULT_CODE_EDIT);
        finish();
    }

    public void back(View v) {
        finish();
    }
}