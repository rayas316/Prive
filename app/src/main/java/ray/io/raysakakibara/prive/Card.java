package ray.io.raysakakibara.prive;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Card extends RealmObject {
    @PrimaryKey
    public int uid;

    public int listjudge;

    public String title;

    public String updateDate;

    public String content;

    public Date date;

    public int count;

}
