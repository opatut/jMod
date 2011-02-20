package JMod;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;


public class CustomEntityManager {
	
	private CustomEntityManager() {
		
	}
	public static CustomEntityManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CustomEntityManager();
		return INSTANCE;
	}
	
	public void RegisterEntityWithRender(Class<? extends Entity> e, Render r) {
		RenderManager.instance.addEntityRender(e, r);
		RenderManager.instance.sort();
	}
	
	private static CustomEntityManager INSTANCE;
}
