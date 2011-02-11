package JMod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Hook {
	public void Invoke(Event e) {
		if(!mPlugin.mEnabled)
			return;
		
		try {
			switch(e.mType) {
			case KeyPressed:
			case KeyReleased:
				mMethod.invoke(mTargetObject, e.GetData(0));
				break;
			default:
				mMethod.invoke(mTargetObject);
				break;
			}
			
		} catch (InvocationTargetException ex) {
			Logger.getLogger("Minecraft").severe("Could not call method " + mMethod.getName() 
					+ " of an object of type " + mTargetObject.getClass().getName() 
					+ " as callback for Event " + e.mType.toString() + 
					" due to wrong invocation.");
		} catch (IllegalAccessException ex) {
			Logger.getLogger("Minecraft").severe("Could not call method " + mMethod.getName() 
					+ " of an object of type " + mTargetObject.getClass().getName() 
					+ " as callback for Event " + e.mType.toString() + 
					" due to restricted method access.");
		}
	}
	
	Object mTargetObject;
	Method mMethod;
	EventType mEventType;
	Plugin mPlugin;
}
