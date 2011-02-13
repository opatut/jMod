package JMod;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;


public class ModListener {
	private ModListener() {
		mHooks = new ArrayList<Hook>();
	}
	public static void createInstance() {
		if(INSTANCE == null)
			INSTANCE = new ModListener();
	}
	public static ModListener getInstance() {
		return INSTANCE;
	}
	
	public void AddHook(Hook hook) {
		mHooks.add(hook);
	}
	
	public void AddHook(EventType type, Plugin plugin, Method method) {
		AddHook(type, plugin, method, plugin);
	}
	public void AddHook(EventType type, Plugin plugin, Method method, Object target) {
		
		Hook h = new Hook();
		h.mEventType = type;
		h.mTargetObject = target;
		h.mMethod = method;
		h.mPlugin = plugin;
		mHooks.add(h);
		
	}
	
	public void RemoveHook(Hook hook) {
		mHooks.remove(hook);
	}
	
	public void HandleEvent(Event event) {
		for(Hook h: mHooks) {
			if(h.mEventType == event.mType) {
				h.Invoke(event);
			}
		}
	}
	public Minecraft mc;
	public ArrayList<Hook> mHooks;
	private static ModListener INSTANCE = null;
}
