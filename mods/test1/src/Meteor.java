import java.util.List;
import java.util.Random;

import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;


public class Meteor extends Entity {

	public Meteor(World world, Vec3D pos, Vec3D speed) {
		super(world);
		posX = pos.xCoord;
		posY = pos.yCoord;
		posZ = pos.zCoord;
		
		motionX = speed.xCoord;
		motionY = speed.yCoord;
		motionZ = speed.zCoord;
	}

	protected void entityInit() {
	}
	
	public void onUpdate() {
		super.onUpdate();
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		// check collisions
		@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, 
				boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		for(Object e: list.toArray()) {
			if(e.getClass() == getClass()) {
				// 2 meteors collided
				entityDropItem(new ItemStack(Item.diamond), rand.nextInt(5) + 5);
				setEntityDead();
			} else if (((Entity)e).canBeCollidedWith()) {
				// damage area
				worldObj.createExplosion(this, posX, posY, posZ, 5F);
				setEntityDead();
			}
		}
		if(! worldObj.isAirBlock((int)posX, (int)posY, (int)posZ)) {
			worldObj.createExplosion(this, posX, posY, posZ, 5F);
			setEntityDead();
		}
		
		if(posY < -128) {
			setEntityDead();
		}
		
		setPosition(posX, posY, posZ);
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {		
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}
	
    public boolean canBeCollidedWith() {
        return true;
    }

    public float getShadowSize()
    {
        return 8.0F;
    }
    
    public float getCollisionBorderSize() {
    	return 1.0F;
    }
	
	public Vec3D GetPosition() {
		return Vec3D.createVector(posX, posY, posZ);
	}

}
