package JMod;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;

public class JoinServerGuiScreen extends GuiScreen{
	public JoinServerGuiScreen() {}
	
	public void initGui() {
		controlList.clear();
        controlList.add(new GuiSmallButton(1, width / 2 - 50, height / 4 + 48, "test1"));
        controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24, "test2"));
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96, "close"));
	}
	
	 protected void actionPerformed(GuiButton guibutton)
	    {
	        if(guibutton.id == 0)
	        {
	        	mc.displayGuiScreen(null);
	        }
	        if(guibutton.id == 1)
	        {
	        	System.out.println("Button 1");
	        }
	        if(guibutton.id == 4)
	        {
	        	System.out.println("Button 4");
	        }
	    }

	    public void updateScreen()
	    {
	        super.updateScreen();
	    }

	    public void drawScreen(int i, int j, float f)
	    {
	        //drawBackground(0);
	    	drawDefaultBackground();
	        //drawString(fontRenderer, "Saving level..", 8, height - 16, k << 16 | k << 8 | k);
	        drawCenteredString(fontRenderer, "Plugin", width / 2, 40, 0xffffff);
	        super.drawScreen(i, j, f);
	    }
}
