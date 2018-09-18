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
import io.realm.annotations.RealmClass;
import io.realm.annotations.RealmField;

import java.util.Date;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {
    Realm realm;
    public LayoutInflater layoutInflater;
    int i;


    CardAdapter(Context context, int textViewResourceId, List<Card> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Card card = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_item_memo, null);
        }

        Button button = (Button) convertView.findViewById(R.id.use);
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView contentsText = (TextView) convertView.findViewById(R.id.contentText);
        TextView valueOfEverydayText = (TextView) convertView.findViewById(R.id.valueOfEverydayText);
        TextView dateText = (TextView) convertView.findViewById(R.id.dateView);
        final TextView countView2 = (TextView) convertView.findViewById(R.id.countView2);

        titleText.setText(card.title);
        contentsText.setText("¥" + card.content);
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
        valueOfEverydayText.setText("¥" + String.valueOf(value2));
        if (value2 >= 100) {
            valueOfEverydayText.setTextColor(Color.RED);

        }
        dateText.setText(String.valueOf(diffDays) + "日前");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
        countView2.setText(String.valueOf(i) + "回");
        return convertView;


    }
}