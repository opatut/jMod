package JMod;

import java.util.HashMap;
import java.util.Map.Entry;

import JMod.PluginInfo.ServerStatus;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;

public class PluginListGuiScreen extends GuiScreen{
	public PluginListGuiScreen(Mode mode) {
		mMode = mode;
		mLifeTime = 0;
	}
	
	public void SetPluginList(HashMap<String, PluginInfo> plugin_infos) {
		mPlugins = plugin_infos;
	}
	
	public static void CreateManageScreen() {
		PluginListGuiScreen s = new PluginListGuiScreen(Mode.Manage);
		s.SetPluginList(PluginLoader.getInstance().mInstalledPlugins);
		s.Display();
	}
	
	public static void CreateSearchScreen(PluginSearchQuery query) {
		PluginListGuiScreen s = new PluginListGuiScreen(Mode.Search);
		if(query != null) {
			s.mTmpSearchString = query.mSearchString;
			s.SetPluginList(PluginLoader.getInstance().SearchForPlugins(query));
		}
		s.Display();
	}
	
	public static void CreateInstallScreen(HashMap<String, PluginInfo> plugins_to_install) {
		PluginListGuiScreen s = new PluginListGuiScreen(Mode.Install);
		if(plugins_to_install != null) {
			s.SetPluginList(plugins_to_install);
			PluginDownloader.getInstance().InstallPlugins(plugins_to_install);
			s.Display();
		}		
	}
	
	public static void ReturnToTitle() {
		if(ModListener.getInstance().mc.isMultiplayerWorld())
        {
			ModListener.getInstance().mc.theWorld.sendQuittingDisconnectingPacket();
        }
		ModListener.getInstance().mc.changeWorld(null, "", null);
		ModListener.getInstance().mc.displayGuiScreen(new GuiMainMenu());
	}
	
	public static void ReturnToGame() {
		ModListener.getInstance().mc.displayGuiScreen(null);
	}
	
	void Display() {
		ModListener.getInstance().mc.displayGuiScreen(this);
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		controlList.clear();
		
		if(mMode == Mode.Manage) {
			controlList.add(new GuiButton(0, width / 3 * 0 + 10, height - 45, width / 3 - 20, 15, "Enable selected"));
			controlList.add(new GuiButton(1, width / 3 * 1 + 10, height - 45, width / 3 - 20, 15, "Disable selected"));
			controlList.add(new GuiButton(2, width / 3 * 2 + 10, height - 45, width / 3 - 20, 15, "Remove selected"));
			
			controlList.add(new GuiButton(3, width / 3 * 0 + 10, height - 25, width / 3 - 20, 15, "Search"));
			controlList.add(new GuiButton(4, width / 3 * 1 + 10, height - 25, width / 3 - 20, 15, "Scan for updates"));
			controlList.add(new GuiButton(5, width / 3 * 2 + 10, height - 25, width / 3 - 20, 15, "Back"));
		} else if(mMode == Mode.ServerConnect) {
			controlList.add(new GuiButton(6, width / 4 * 0 + 10, height - 25, width / 4 - 20, 15, "Install all"));
			controlList.add(new GuiButton(7, width / 4 * 1 + 10, height - 25, width / 4 - 20, 15, "Install required"));
			controlList.add(new GuiButton(8, width / 4 * 2 + 10, height - 25, width / 4 - 20, 15, "Install selected"));
			controlList.add(new GuiButton(9, width / 4 * 3 + 10, height - 25, width / 4 - 20, 15, "Cancel"));
		} else if(mMode == Mode.Update) {
			controlList.add(new GuiButton(10, width / 3 * 0 + 10, height - 25, width / 3 - 20, 15, "Update all"));
			controlList.add(new GuiButton(11, width / 3 * 1 + 10, height - 25, width / 3 - 20, 15, "Update selected"));
			controlList.add(new GuiButton(12, width / 3 * 2 + 10, height - 25, width / 3 - 20, 15, "Cancel"));
		} else if(mMode == Mode.Search) {
			controlList.add(new GuiButton(13, width / 3 * 0 + 10, height - 25, width / 3 - 20, 15, "Refresh"));
			controlList.add(new GuiButton(14, width / 3 * 1 + 10, height - 25, width / 3 - 20, 15, "Install selected"));
			controlList.add(new GuiButton(15, width / 3 * 2 + 10, height - 25, width / 3 - 20, 15, "Cancel"));
			
			// input field
			//controlList.add(new Gui)
		}
		
		controlList.add(new GuiButton(100, width - 55, mListMarginTop - 18, 20, 13, "<"));
		controlList.add(new GuiButton(101, width - 30, mListMarginTop - 18, 20, 13, ">"));
	}
	
	public PluginInfo GetSelectedPlugin() {
		if(mPlugins == null) return null;
		int i = 0;
		for(Entry<String, PluginInfo> e: mPlugins.entrySet()) {
			if(i == mSelectedIndex)
				return e.getValue();
			++i;
		}
		return null;
	}
	
	protected void actionPerformed(GuiButton guibutton) {
		switch(guibutton.id) {
		case 0:
			if(GetSelectedPlugin() != null)
				PluginLoader.getInstance().EnablePlugin(GetSelectedPlugin().GetName());
			break;
		case 1:
			if(GetSelectedPlugin() != null)
				PluginLoader.getInstance().DisablePlugin(GetSelectedPlugin().GetName());
			break;
		case 2:
			if(GetSelectedPlugin() != null)
				PluginLoader.getInstance().DeletePlugin(GetSelectedPlugin().GetName());
			System.out.println("Removed the plugin. It's too late ;(");
			break;
		case 3:
			CreateSearchScreen(null);
			break;
		case 4:
			System.out.println("Scan for updates.");
			break;
		case 5:
			ReturnToGame();
			break;
		case 6:
			System.out.println("Install all.");
			break;
		case 7:
			System.out.println("Install required.");
			break;
		case 8:
			System.out.println("Install selected.");
			break;
		case 9:
			ReturnToTitle();
			break;
		case 10:
			System.out.println("Update all.");
			break;
		case 11:
			System.out.println("Update selected.");
			break;
		case 12:
			CreateManageScreen();
			break;
		case 13:
			CreateSearchScreen(new PluginSearchQuery(mTmpSearchString));
			break;
		case 14:
			if(GetSelectedPlugin() != null) {
				HashMap<String, PluginInfo> l = new HashMap<String, PluginInfo>();
				l.put(GetSelectedPlugin().GetName(), GetSelectedPlugin());
				CreateInstallScreen(l);
			}
			else
				System.out.println("Nothing selected.");
			break;
		case 15:
			CreateManageScreen();
			break;
		case 100:
			if(mListPage > 0)
				mListPage--;
			break;
		case 101:
			if(mListPage < GetNumPages() - 1)
				mListPage++;
			break;
		} 
	}

	public void updateScreen() {
		mLifeTime++;
	    super.updateScreen();
	}
	
	protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        if(k == 0) { // LMB
        	for(int l = 0; l < GetEntriesPerPage(); ++l) {
        		int y = GetYPositionOfLine(l);
        		if(j >= y && j <= y + mListEntryHeight) {
        			mSelectedIndex = l + mListPage * GetEntriesPerPage();
        		}
        	}
        }
    }
	
	public int GetEntriesPerPage() {
		return (int) Math.floor((height - mListMarginTop - mListMarginBottom - 2 * mListPadding) / mListEntryHeight);
	}
	
	public int GetNumPages() {
		if(mPlugins == null) return 0;
		return (int) Math.ceil(mPlugins.size() * 1.0 / GetEntriesPerPage());
	}
	
	private int GetEntryPage(int entry) {
		if(mPlugins == null) return 0;
		return (int) Math.floor(entry * 1.0 / GetEntriesPerPage());
	}
	
	private int GetYPositionOfLine(int line) {
		return line * mListEntryHeight + mListMarginTop + mListPadding;
	}
	
	private int GetLineOnPage(int entry) {
		return entry - GetEntryPage(entry) * GetEntriesPerPage(); 
	}
	
	public void drawScreen(int i, int j, float f) {
		drawBackground(0);
		
		String headline = "";
		if(mMode == Mode.Manage) {
			headline = "List of all installed plugins:";
		} else if(mMode == Mode.ServerConnect) {
			headline = "The server requires/proposes the following additional plugins:";
		} else if(mMode == Mode.Update) {
			headline = "Updates are available for the following plugins:";
		} else if(mMode == Mode.Search){
			headline = "Search: ";
			headline += mTmpSearchString;
			if((mLifeTime / 6) % 2 == 0)
				headline += "_";
		}
		
		drawString(fontRenderer, headline, 20, mListMarginTop - 15, 0xffffff);
		
		drawGradientRect(0, mListMarginTop, width, mListMarginTop + mListPadding, 
				0xff000000, 0x80000000);
		drawRect(0, mListMarginTop + mListPadding, width, height - mListMarginBottom - mListPadding, 
				0x80000000);
		drawGradientRect(0, height - mListMarginBottom - mListPadding, width, height - mListMarginBottom, 
				0x80000000, 0xff000000);
		
		int i1 = -1;
		int l = 0;
		if(mPlugins != null && mPlugins.size() > 0) {
			for(Entry<String, PluginInfo> e: mPlugins.entrySet()) {
				++i1;
				
				if (GetEntryPage(i1) != mListPage)
					continue;
				int y = GetYPositionOfLine(GetLineOnPage(i1));
				if(mSelectedIndex == i1)
					drawRect(mListPadding, y, width - mListPadding, y + mListEntryHeight - mListEntryMargin, 0x60FFFFFF);
				else 
					drawRect(mListPadding, y, width - mListPadding, y + mListEntryHeight - mListEntryMargin, 0x60000000);
				
				drawString(fontRenderer, e.getKey(), mListPadding * 2, y + 3, 0xFFFFFF);
				
				if(mMode == Mode.ServerConnect) {
					// Show what the server requires
					if(e.getValue().mServerStatus == ServerStatus.Forbidden)
						drawString(fontRenderer, "Forbidden", width / 2, y + 3, 0xaa0000);
					if(e.getValue().mServerStatus == ServerStatus.Required)
						drawString(fontRenderer, "Required", width / 2, y + 3, 0xFFFF00);
					if(e.getValue().mServerStatus == ServerStatus.Proposed)
						drawString(fontRenderer, "Proposed", width / 2, y + 3, 0x00aa00);
					if(e.getValue().mServerStatus == ServerStatus.None)
						drawString(fontRenderer, "Not required", width / 2, y + 3, 0x888888);
				}
				
				if(mMode == Mode.Manage) {
					if(e.getValue().IsCurrentlyEnabled())
						drawString(fontRenderer, "Enabled", width / 2, y + 3, 0x00aa00);
					else
						drawString(fontRenderer, "Disabled", width / 2, y + 3, 0xff8800);
				}
				
				if(mMode == Mode.Search) {
					drawString(fontRenderer, "Version: " + e.getValue().GetLatestConfigVersion(), width / 2, y + 3, 0xCCCCCC);
					if(e.getValue().mInstalled) {
						if(e.getValue().IsUpToDate())
							drawString(fontRenderer, "Installed", width / 4 * 3, y + 3, 0x00aa00);
						else
							drawString(fontRenderer, "Outdated", width / 4 * 3, y + 3, 0xaa0000);
					} 
				}
				
				++l;
			}
		} else {
			drawCenteredString(fontRenderer, "Plugin list empty.", width / 2, 
					mListMarginTop + (mListMarginBottom - mListMarginTop) / 2, 0xCCCCCC);
		}
		
		String pages = "Page " + (mListPage+1) + "/" + GetNumPages();
		if(GetNumPages() == 0) pages = "Page 0/0";
		drawString(fontRenderer, pages, width - 70 - 
				fontRenderer.getStringWidth(pages), mListMarginTop - 15, 0xaaaaaa);
		super.drawScreen(i1, j, f);
	}
	
	protected void keyTyped(char c, int i){
		if(mMode == Mode.Search) {
	        if(c == '\026') {
	            String clip = GuiScreen.getClipboardString();
	            if(clip == null)
	            	clip = "";
	            int chars_left = mMaxSearchQueryLength - mTmpSearchString.length();
	            if(clip.length() == chars_left) {
	            	chars_left = clip.length();
	            }
	            if(chars_left > 0) {
	            	mTmpSearchString += clip.substring(0, chars_left);
	            }
	        }
	        if(c == '\r') {
	            // return
	        	CreateSearchScreen(new PluginSearchQuery(mTmpSearchString));
	        }
	        if(i == 14 && mTmpSearchString.length() > 0) {
	        	// delete last
	        	mTmpSearchString = mTmpSearchString.substring(0, mTmpSearchString.length() - 1);
	        }
	        if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.#()".indexOf(c) >= 0 && 
	        		mTmpSearchString.length() < mMaxSearchQueryLength) {
	        	mTmpSearchString += c;
	        }
		}
    }
	
	public HashMap<String, PluginInfo> mPlugins;
	public Mode mMode;
	
	private int mListPage = 0;
	private int mSelectedIndex = -1;
	
	private static int mListMarginBottom = 60;
	private static int mListMarginTop = 30;
	private static int mListEntryHeight = 20;
	private static int mListEntryMargin = 5;
	private static int mListPadding = 10;
	private static int mMaxSearchQueryLength = 32;
	
	private int mLifeTime;
	public String mTmpSearchString = "";
	
	public enum Mode {
		Manage,
		ServerConnect,
		Update, 
		Search,
		Install
	}
}
