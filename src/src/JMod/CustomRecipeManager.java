package JMod;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;

public class CustomRecipeManager {
	private CustomRecipeManager() {}
	
	public static CustomRecipeManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CustomRecipeManager();
		return INSTANCE;
	}
	
	public void RegisterRecipe(CustomRecipe recipe) {
		CraftingManager.getInstance().addRecipe(new ItemStack(
				recipe.GetProductItem(), recipe.GetProductAmount()), recipe.GetArrangement());
		CraftingManager.getInstance().sort();
	}
	
	private static CustomRecipeManager INSTANCE;

}
