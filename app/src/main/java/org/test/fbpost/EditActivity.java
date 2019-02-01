package org.test.fbpost;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    int ID;
    EditText edit_title, edit_content;

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

    public void update(View v) {
        String title = edit_title.getText().toString();
        String content = edit_content.getText().toString();
        String query="UPDATE tableName SET title = ?, content = ? WHERE id = ?";
        String[] selections={title, content, String.valueOf(ID)};
        db.execSQL(query, selections);
        Toast.makeText(getApplicationContext(), "수정 성공", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
        intent.putExtra("id", ID);
        startActivity(intent);
        finish();
    }

    public void back(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
