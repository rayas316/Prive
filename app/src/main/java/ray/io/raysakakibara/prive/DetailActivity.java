package ray.io.raysakakibara.prive;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    TextInputEditText titleText;
    TextInputEditText contentText;
    TextInputLayout titleEditTextTextInputLayout2;
    TextInputLayout contentEditTextTextInputLayout2;
    FloatingActionButton fab;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();
        card = (Card) getIntent().getSerializableExtra("Card");


        titleText = (TextInputEditText) findViewById(R.id.titleEditText2);
        contentText = (TextInputEditText) findViewById(R.id.contentEditText2);
        showData();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePrefecture();
            }
        });

    }


    public void choosePrefecture() {
        final String[] items = {"⭐️⭐️⭐️⭐️⭐", "⭐⭐️⭐️⭐️", "⭐️⭐️⭐️", "⭐️⭐️️", "⭐️"};
        final int defaultItem = 0;
        new AlertDialog.Builder((this))
                .setTitle("使用終了！これは良い買いものでしたか？")
                .setSingleChoiceItems(items, defaultItem, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (defaultItem == 0) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    card.listjudge = 5;
                                    realm.insertOrUpdate(card);
                                }
                            });
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (defaultItem == 1) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    card.listjudge = 4;
                                    realm.insertOrUpdate(card);
                                }
                            });
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (defaultItem == 2) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    card.listjudge = 3;
                                    realm.insertOrUpdate(card);
                                }
                            });
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (defaultItem == 3) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    card.listjudge = 2;
                                    realm.insertOrUpdate(card);
                                }
                            });
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (defaultItem == 4) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    card.listjudge = 1;
                                    realm.insertOrUpdate(card);
                                }
                            });
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        realm.close();

    }


    public void showData() {
        titleText.setText(card.title);
        contentText.setText(card.content);

    }

    public void update(View view) {
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        titleEditTextTextInputLayout2 = findViewById(R.id.titleEditTextTextInputLayout2);
        contentEditTextTextInputLayout2 = findViewById(R.id.contentEditTextTextInputLayout2);
        if (title.matches("") && content.matches("")) {

            contentEditTextTextInputLayout2.setError("商品名と値段が入力されていません");
            return;
        } else if (title.matches("")) {
            titleEditTextTextInputLayout2.setError("商品名が入力されていません");
            return;
        } else if (content.matches("")) {
            contentEditTextTextInputLayout2.setError("値段が入力されていません");
            return;
        } else if (title.length() > 10) {
            titleEditTextTextInputLayout2.setError("入力できるのは10文字までです");
            return;
        } else if (content.length() > 10) {
            contentEditTextTextInputLayout2.setError("入力できるのは10桁までです");
            return;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                card.title = titleText.getText().toString();
                card.content = contentText.getText().toString();
            }
        });
        finish();
    }

}