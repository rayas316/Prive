package ray.io.raysakakibara.prive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Liststar5 extends AppCompatActivity {
    Realm realm;
    ListView list5;
    List<Card> items2;
    CardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststar5);
        realm=Realm.getDefaultInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater5 = getMenuInflater();
        inflater5.inflate(R.menu.activity_actions5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_back:

                break;

            case R.id.action_list1:
                Intent intent = new Intent(Liststar5.this, Liststar1.class);
                startActivity(intent);
                break;
            case R.id.action_list2:
                Intent intent2 = new Intent(Liststar5.this, Liststar2.class);
                startActivity(intent2);
                break;
            case R.id.action_list3:
                Intent intent3 = new Intent(Liststar5.this, Liststar3.class);
                startActivity(intent3);
                break;
            case R.id.action_list4:
                Intent intent4 = new Intent(Liststar5.this, Liststar4.class);
                startActivity(intent4);
                break;
            case R.id.action_list5:
                Intent intent5 = new Intent(Liststar5.this, Liststar5.class);
                startActivity(intent5);
                break;
            case R.id.action_list:
                Intent intent0=new Intent(Liststar5.this,MainActivity.class);
                startActivity(intent0);
                break;
        }
        return true;
    }

    public void setMemoList() {

        list5 = (ListView) findViewById(R.id.listView5);

        RealmResults<Card> results = realm.where(Card.class).equalTo("listjudge", 5).findAll();
        items2 = realm.copyFromRealm(results);

        adapter = new CardAdapter(this, R.layout.layout_item_memo, items2, realm);

        list5.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMemoList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }

}
