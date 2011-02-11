import JMod.EventType;
import JMod.ModListener;
import JMod.Plugin;

public class TestPlugin1 extends Plugin {
	public void OnInitialize() {
		System.out.println("Initializing TestPlugin #1");
		try {
			ModListener.getInstance().AddHook(EventType.UpdateGame, this, 
					getClass().getDeclaredMethod("OnUpdate"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OnUpdate() {
		System.out.println("up!");
	}
	
	public String GetName() {
		return "TestPlugin #1";
	}
	public String GetVersion() {
		return "0.0.1";
	}
}
