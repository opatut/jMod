package JMod;
import net.minecraft.client.Minecraft;

public abstract class Plugin {
	public abstract void OnInitialize();
	public void OnEnable() {}
	public void OnDisable() {}
	
	public abstract String GetName();
	public abstract String GetVersion();
	
	public boolean mEnabled;
	public Minecraft mMinecraft;
}
