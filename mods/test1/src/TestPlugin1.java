import JMod.*;
import org.lwjgl.input.Keyboard;


public class TestPlugin1 extends Plugin {
	public void OnInitialize() {		
		System.out.println("Initializing Testplugin1");
		try {
			CustomItemManager.getInstance().RegisterItem(new CustomSlimeItem(400));
			CustomRecipeManager.getInstance().RegisterRecipe(new CustomSlimeRecipe());
			CustomEntityManager.getInstance().RegisterEntityWithRender(CustomFireball.class, new RenderCustomFireball());
			CustomTextureManager.getInstance().RegisterTexture(this, "/plugins/TestPlugin1/icons.png");
				
			
			ModListener.getInstance().AddHook(EventType.UpdateGame, this, 
					getClass().getDeclaredMethod("OnUpdate"));
			
			ModListener.getInstance().AddHook(EventType.KeyPressed, this, 
					getClass().getDeclaredMethod("OnKeyPressed", int.class));
			
			ModListener.getInstance().AddHook(EventType.BlockPlaced, this, 
					getClass().getDeclaredMethod("OnBlockPlaced", int.class,int.class,int.class,int.class));
			
			ModListener.getInstance().AddHook(EventType.KeyReleased, this, 
					getClass().getDeclaredMethod("OnKeyReleased", int.class));
			
			ModListener.getInstance().AddHook(EventType.MouseButtonDown, this, 
					getClass().getDeclaredMethod("OnMouseClick", int.class,int.class,int.class));
			
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
	
	public void OnMouseClick(int button,int posX, int posY) {
		System.out.println("MousePos: " + "(" + posX + "," + posY + ")");
	}
	
	public void OnKeyPressed(int key) {
		if(key == Keyboard.KEY_E){
			
			PluginListGuiScreen.CreateManageScreen();
					
			System.out.println("Pressed E");
			
			//SoundSystem sound = SoundManager.sndSystem;
			//try {
				//sound.backgroundMusic("test", new URL("http://localhost/exp_loop_2.wav"), "exp_loop_2.wav", true);
			//} catch (IOException e) {}
		}else if(key == Keyboard.KEY_P) {
			
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
