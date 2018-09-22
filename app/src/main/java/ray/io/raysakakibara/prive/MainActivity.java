package ray.io.raysakakibara.prive;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public Realm realm;
    CardAdapter adapter;
    List<Card> items2;
    TextView countView;
    Card card;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_back:
                break;

            case R.id.action_list1:
                Intent intent = new Intent(MainActivity.this, Liststar1.class);
                startActivity(intent);
                break;
            case R.id.action_list2:
                Intent intent2 = new Intent(MainActivity.this, Liststar2.class);
                startActivity(intent2);
                break;
            case R.id.action_list3:
                Intent intent3 = new Intent(MainActivity.this, Liststar3.class);
                startActivity(intent3);
                break;
            case R.id.action_list4:
                Intent intent4 = new Intent(MainActivity.this, Liststar4.class);
                startActivity(intent4);
                break;
            case R.id.action_list5:
                Intent intent5 = new Intent(MainActivity.this, Liststar5.class);
                startActivity(intent5);
                break;
        }
        return false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Prive    使用中の物");

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("変更を加えますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Card card = (Card) parent.getItemAtPosition(position);
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("Card", card
                                );
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("キャンセル", null)
                        .setCancelable(true);
                // show dialog
                builder.show();


            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("削除しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RealmResults<Card> results = realm.where(Card.class)
                                        .equalTo("title", adapter.getItem(position).title)
                                        .equalTo("updateDate", adapter.getItem(position).updateDate)
                                        .equalTo("content", adapter.getItem(position).content)
                                        .findAll();
                                realm.beginTransaction();
                                results.deleteAllFromRealm();
                                realm.commitTransaction();
                                items2.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("キャンセル", null)
                        .setCancelable(true);
                builder.show();
                return true;


            }

        });


    }

    public void setMemoList() {
        RealmResults<Card> results = realm.where(Card.class).equalTo("listjudge", 0).findAll();
        items2 = realm.copyFromRealm(results);

        adapter = new CardAdapter(this, R.layout.layout_item_memo, items2, realm);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMemoList();
    }

    public void FloatingActionButton(View v) {
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }
}
