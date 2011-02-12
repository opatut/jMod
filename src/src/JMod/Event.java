package JMod;
import java.util.ArrayList;


public class Event {
	public Event(EventType type) {
		mData = new ArrayList<Object>();
		mType = type;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T GetData(int index) {
		return (T)mData.get(index);
	}
	
	public void AddData(Object o) {
		mData.add(o);
	}
	
	EventType mType;
	ArrayList<Object> mData;
}
