import JMod.EventType;
import JMod.ModListener;
import JMod.Plugin;
import org.lwjgl.input.Keyboard;


public class TestPlugin1 extends Plugin {
	public void OnInitialize() {
		System.out.println("Initializing TestPlugin #1");
		try {
			ModListener.getInstance().AddHook(EventType.UpdateGame, this, 
					getClass().getDeclaredMethod("OnUpdate"));
			
			ModListener.getInstance().AddHook(EventType.KeyPressed, this, 
					getClass().getDeclaredMethod("OnKeyPressed", int.class));
			
			ModListener.getInstance().AddHook(EventType.BlockPlaced, this, 
					getClass().getDeclaredMethod("OnBlockPlaced", int.class,int.class,int.class,int.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OnUpdate() {

	}
	
	public void OnBlockPlaced(int itemID,int posX,int posY,int posZ) {
		System.out.println("You placed a " + itemID + " on the position ("+posX+","+posY+","+posZ+")");
	}
	
	public void OnKeyPressed(int key) {
		
		if(key == Keyboard.KEY_E){
			System.out.println("You pressed E on your keyboard.");
		}
	}
		
	public String GetName() {
		return "TestPlugin #1";
	}
	
	public String GetVersion() {
		return "0.0.1";
	}
}
