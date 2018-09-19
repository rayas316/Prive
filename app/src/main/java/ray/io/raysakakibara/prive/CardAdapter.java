package ray.io.raysakakibara.prive;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import java.util.Date;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {
    Realm realm;
    public LayoutInflater layoutInflater;


    CardAdapter(Context context, int textViewResourceId, List<Card> objects, Realm realm) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.realm = realm;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Card card = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_item_memo, null);
        }

        final ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.titleText.setText(card.title);
        viewHolder.pricecountView.setText("¥"+String.valueOf(Integer.parseInt(card.content) / card.count));
        viewHolder.contentsText.setText("¥" + card.content);
        Date date1 = new Date();
        Date date2 = card.date;
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 24 * 60 * 60 * 1000;
        long diffDays = (datetime1 - datetime2) / one_date_time;
        if (diffDays == 0) {
            diffDays = 1;
        }
        long value1 = Long.parseLong(card.content);
        long value2 = (value1 / diffDays);
        viewHolder.valueOfEverydayText.setText("¥" + String.valueOf(value2));
        if (value2 >= 100) {
            viewHolder.valueOfEverydayText.setTextColor(Color.RED);

        }
        viewHolder.countView2.setText(String.valueOf(card.count));
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        card.count = card.count + 1;
                        viewHolder.countView2.setText(String.valueOf(card.count) + "回");
                        realm.insertOrUpdate(card);
                    }
                });
                viewHolder.pricecountView.setText("¥"+String.valueOf(Integer.parseInt(card.content) / card.count));
            }
        });
        viewHolder.countView2.setText(String.valueOf(card.count) + "回");
        viewHolder.dateText.setText(String.valueOf(diffDays) + "日前");


        return convertView;
    }


    class ViewHolder {
        TextView titleText;
        Button button;
        TextView contentsText;
        TextView valueOfEverydayText;
        TextView dateText;
        TextView countView2;
        TextView pricecountView;


        public ViewHolder(View convertView) {
            titleText = (TextView) convertView.findViewById(R.id.titleText);
            button = (Button) convertView.findViewById(R.id.use);
            contentsText = (TextView) convertView.findViewById(R.id.contentText);
            valueOfEverydayText = (TextView) convertView.findViewById(R.id.valueOfEverydayText);
            dateText = (TextView) convertView.findViewById(R.id.dateView);
            countView2 = (TextView) convertView.findViewById(R.id.countView2);
            pricecountView = (TextView) convertView.findViewById(R.id.pricecountView);
        }
    }

}