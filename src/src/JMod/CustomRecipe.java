package JMod;

import net.minecraft.src.Item;

public abstract class CustomRecipe {
	public abstract Item GetProductItem();
	public abstract int GetProductAmount();
	public abstract Object[] GetArrangement();
}
