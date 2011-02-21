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
		mPosition = pos;
		mSpeed = speed;
	}

	protected void entityInit() {
	}
	
	public void onUpdate() {
		super.onUpdate();
		mPosition.xCoord += mSpeed.xCoord;
		mPosition.yCoord += mSpeed.yCoord;
		mPosition.zCoord += mSpeed.zCoord;
		
		// check collisions
		@SuppressWarnings("unchecked")
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, 
				boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		for(Object e: list.toArray()) {
			if(e.getClass() == getClass()) {
				// 2 meteors collided
				entityDropItem(new ItemStack(Item.diamond), rand.nextInt(5) + 5);
			} else {
				// damage area
				worldObj.createExplosion(this, mPosition.xCoord, mPosition.yCoord, mPosition.zCoord, 2);
			}
		}
		setPosition(mPosition.xCoord, mPosition.yCoord, mPosition.zCoord);
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
    	return 3.0F;
    }
	
	public Vec3D GetPosition() {
		return Vec3D.createVector(posX, posY, posZ);
	}
    
	private Vec3D mPosition;
	private Vec3D mSpeed;

}
