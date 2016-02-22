package maps;

/**
 * Created by tmaher on 2/18/2016.
 */
public class CollectionOfCharges {
    String object;
    Object[] data;
    boolean has_more;
    String url;
    public String getObjectType() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }
    public Object[] getData() {
        return data;
    }
    public void setData(Object[] data) {
        this.data = data;
    }
    public boolean isHas_more() {
        return has_more;
    }
    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}