import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import JMod.CustomItem;


public class CustomSlimeItem extends CustomItem{
	protected CustomSlimeItem(int i) {
		super(i);
		setIconCoord(14, 1);
	}

	public String GetIdentifier() {
		return "CustomSlime";
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.stackSize--;
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if(!world.multiplayerWorld) {
            world.entityJoinedWorld(new EntityEgg(world, entityplayer));
        }
        return itemstack;
    }

	
}
