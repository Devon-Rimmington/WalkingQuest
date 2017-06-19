package walkingquest.kinematicworld.library.database.objects;

import android.provider.BaseColumns;


/**
 * Created by Devon on 6/18/2017.
 */

public class StepLog extends BaseObject{

    private String date;

    public StepLog(){
        this._ID = null;
        date = null;
    }

    public StepLog(String date){
        this._ID = null;
        this.date = date;
    }

    public StepLog(String _ID, String date){
        this._ID = _ID;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
