package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/23/2017.
 */

public class BaseObject {

    protected long _id;

    public BaseObject(){}

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
