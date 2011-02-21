import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import JMod.CustomItem;


public class CustomSlimeItem extends CustomItem{
	protected CustomSlimeItem(int i) {
		super(i);
		setIconCoord(14, 1);
		setItemName("Fireball");
	}

	public String GetIdentifier() {
		return "CustomSlime";
	}
	
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.stackSize--;
        if(!world.multiplayerWorld) {        	
            //world.entityJoinedWorld(new CustomFireball(world, entityplayer, entityplayer.getLookVec().xCoord * 5, entityplayer.getLookVec().yCoord * 5, entityplayer.getLookVec().zCoord * 5));
        	if(!world.entityJoinedWorld(new Armageddon(world, entityplayer, 40, 20, 1)))
        		System.err.println("could not create entity: chunk not exists?");
        }
        return itemstack;
    }

	
}
