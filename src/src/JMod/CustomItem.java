package JMod;
import net.minecraft.src.Item;

public abstract class CustomItem extends Item {

	protected CustomItem(int i) {
		super(i);
	}

	public abstract String GetIdentifier();

}
