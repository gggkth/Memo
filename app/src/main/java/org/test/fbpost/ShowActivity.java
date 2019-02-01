package org.test.fbpost;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class ShowActivity extends AppCompatActivity {

    TextView title, content;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        ID = intent.getIntExtra("id", 0);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        cursor = db.rawQuery("SELECT * FROM tableName WHERE id = ?", new String[]{String.valueOf(ID)});
        cursor.moveToFirst();


        title.setText(cursor.getString(1));
        content.setText(cursor.getString(2));

        cursor.close();

    }

    public void back(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void delete(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("삭제하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.execSQL("DELETE FROM tableName WHERE id = ?", new String[]{String.valueOf(ID)});
                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(title, "취소되었습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void edit(View v) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("id", ID);
        setResult(Activity.RESULT_OK, intent);
        startActivity(intent);
        finish();
    }


}
