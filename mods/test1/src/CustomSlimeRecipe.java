import net.minecraft.src.Block;
import net.minecraft.src.Item;
import JMod.CustomRecipe;

public class CustomSlimeRecipe extends CustomRecipe {
	public Item GetProductItem() {
		//return CustomItemManager.getInstance().GetItem("CustomSlime");
		return Item.diamond;
	}

	public int GetProductAmount() {
		return 8;
	}

	public Object[] GetArrangement() {
		return new Object[] {
				"#",
				Character.valueOf('#'), Block.sand
		};
	}
}
