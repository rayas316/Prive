package com.example.raysakakibara.memocamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    EditText titleText;
    EditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();

        titleText = (EditText) findViewById(R.id.titleEditText);
        contentText = (EditText) findViewById(R.id.contentEditText);
        showData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        realm.close();

    }


    public void showData() {

        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();
        titleText.setText(memo.title);
        contentText.setText(memo.content);

    }


    public void update(View view) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        if (title.matches("") && content.matches("")) {
            Toast.makeText(this, "商品名と値段が入力されていません", Toast.LENGTH_SHORT).show();
            return;
        } else if (title.matches("")) {
            Toast.makeText(this, "商品名が入力されていません。", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.matches("")) {
            Toast.makeText(this, "値段が入力されていません。", Toast.LENGTH_SHORT).show();
            return;
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    memo.title = titleText.getText().toString();
                    memo.content = contentText.getText().toString();
                }
            });
            finish();
        }
    }
}