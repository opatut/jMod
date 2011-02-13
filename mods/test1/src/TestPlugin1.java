import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import JMod.EventType;
import JMod.JoinServerGuiScreen;
import JMod.ModListener;
import JMod.Plugin;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.SoundManager;

import org.lwjgl.input.Keyboard;

import paulscode.sound.*;


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
			
			ModListener.getInstance().AddHook(EventType.KeyReleased, this, 
					getClass().getDeclaredMethod("OnKeyReleased", int.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OnUpdate() {

	}
	
	public void OnBlockPlaced(int itemID,int posX,int posY,int posZ) {
		String ItemName = "";
		
		switch(itemID){
		case 1:
			ItemName = "stone";
			break;
		case 2:
			ItemName = "grass";
			break;
		case 3:
			ItemName = "dirt";
			break;
		case 4:
			ItemName = "cobblestone";
			break;
		case 5:
			ItemName = "wooden plank";
			break;
		case 12:
			ItemName = "sand";
			break;
		case 13:
			ItemName = "gravel";
			break;
		}
		
		
		System.out.println("You placed a " + ItemName + " block on the position ("+posX+","+posY+","+posZ+")");
	}
	
	public void OnKeyPressed(int key) {
		if(key == Keyboard.KEY_E){
			
			ModListener.getInstance().mc.displayGuiScreen(new JoinServerGuiScreen());
			
			//SoundSystem sound = SoundManager.sndSystem;
			//try {
				//sound.backgroundMusic("test", new URL("http://localhost/exp_loop_2.wav"), "exp_loop_2.wav", true);
			//} catch (IOException e) {}
		}
	}
	
	public void OnKeyReleased(int key) {
		System.out.println("Released: "+key);
	}
		
	public String GetName() {
		return "TestPlugin #1";
	}
	
	public String GetVersion() {
		return "0.0.1";
	}
}
