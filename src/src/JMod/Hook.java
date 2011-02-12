package JMod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Hook {
	public void Invoke(Event e) {
		if(!mPlugin.mEnabled)
			return;
		
		try {
			
			if(e.mType == EventType.KeyPressed || e.mType == EventType.KeyReleased){
				mMethod.invoke(mTargetObject, e.GetData(0));
			}else if(e.mType == EventType.BlockPlaced){
				mMethod.invoke(mTargetObject, e.GetData(0),e.GetData(1),e.GetData(2),e.GetData(3));
				
			}else{
				mMethod.invoke(mTargetObject);
			}
			
			
		} catch (InvocationTargetException ex) {
			System.out.println("Could not call method " + mMethod.getName() 
					+ " of an object of type " + mTargetObject.getClass().getName() 
					+ " as callback for Event " + e.mType.toString() + 
					" due to wrong invocation.");
		} catch (IllegalAccessException ex) {
			System.out.println("Could not call method " + mMethod.getName() 
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
