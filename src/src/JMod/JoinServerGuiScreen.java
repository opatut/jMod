package JMod;

import java.util.HashMap;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class JoinServerGuiScreen extends GuiScreen{
	public JoinServerGuiScreen() {
		mPluginsRequired = new HashMap<String, Boolean>();
		mPluginsRequired.put("TestPlugin1", true);
		mPluginsRequired.put("TestPlugin2", true);
		mPluginsRequired.put("TestPlugin3", true);
		mPluginsRequired.put("TestPlugin4", false);
		mPluginsRequired.put("TestPlugin5", false);
		mPluginsRequired.put("TestPlugin6", false);
		mPluginsRequired.put("TestPlugin7", false);
		mPluginsRequired.put("TestPlugin8", false);
		mPluginsRequired.put("TestPlugin9", true);
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		controlList.clear();
		
		controlList.add(new GuiButton(1, width / 4 * 0 + 10, height - 25, width / 4 - 20, 15, "Install all"));
		controlList.add(new GuiButton(2, width / 4 * 1 + 10, height - 25, width / 4 - 20, 15, "Install required"));
		controlList.add(new GuiButton(3, width / 4 * 2 + 10, height - 25, width / 4 - 20, 15, "Install selected"));
		controlList.add(new GuiButton(4, width / 4 * 3 + 10, height - 25, width / 4 - 20, 15, "Cancel"));
	}
	
	protected void actionPerformed(GuiButton guibutton) {
		if(guibutton.id == 0) {
			mc.displayGuiScreen(null);
		} else if(guibutton.id == 1) {
			System.out.println("Blindly installing everything.");
		} else if(guibutton.id == 2) {
			System.out.println("Installing everything I need.");
		} else if(guibutton.id == 3) {
			System.out.println("Installing everything I want.");
		} else if(guibutton.id == 4) {
			System.out.println("Installing nothing");
		}
	}
	
	/*public void updateScreen() {
	    super.updateScreen();
	}*/
	
	public void drawScreen(int i, int j, float f) {
		drawBackground(0);
		drawCenteredString(fontRenderer, "The server requires/proposes the following plugins:", width / 2, 20, 0xffffff);
		
		int h = (int) Math.floor((height - 200) / 20);
		for(int l = 0; l < h; ++l) {
			DrawListLine(mPluginsRequired.keySet().toArray()[l + mListOffset].toString(), l);
		}
		
		super.drawScreen(i, j, f);
	}
	
	private void DrawListLine(String name, int line) {
		int x = line * 20 + 100;
		drawRect(2, x, 10, width - 20, 15);
		drawString(fontRenderer, name, x,  30, 0xFFFFFF);
	}
	
	public HashMap<String, Boolean> mPluginsRequired;
	int mListOffset = 0;
}
