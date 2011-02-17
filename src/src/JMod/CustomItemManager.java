package JMod;
import java.util.ArrayList;

public class CustomItemManager {
	private CustomItemManager() {
		mCustomItems = new ArrayList<CustomItem>();
	}
	
	public static CustomItemManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CustomItemManager();
		return INSTANCE;
	}
	
	public void RegisterItem(CustomItem item) {
		mCustomItems.add(item);
	}
	
	public CustomItem GetItem(String identifier) {
		for(CustomItem i: mCustomItems) {
			if(i.GetIdentifier() == identifier)
				return i;
		}
		return null;
	}
	
	public ArrayList<CustomItem> mCustomItems;
	private static CustomItemManager INSTANCE;
}
