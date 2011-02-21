import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class Armageddon extends Entity {
	public Armageddon(World world, EntityLiving spawner, double radius, double amount, double delay) {
        super(world);
        mWorld = world;
        mSpawner = spawner;
        mRadius = radius;
        mAmount = amount;
        mDelayBetweenMeteors = delay;
    }

    protected void entityInit() {
    }

    public void onUpdate() {
        super.onUpdate();
        if(mLifetime > mAmount * mDelayBetweenMeteors) {
            setEntityDead();	
        } else {
        	// spawn new fireballs
        	float timediff = 1 / 20F;
        	mLifetime += timediff;
        	mTimeoutForSpawn -= timediff;
        	while(mTimeoutForSpawn < 0) {
        		mWorld.entityJoinedWorld(CreateNewMeteor());
        		mTimeoutForSpawn += mDelayBetweenMeteors;
        	}
        }
        setPosition(posX, posY, posZ);
    }

    private Meteor CreateNewMeteor() {
    	Vec3D pos = Vec3D.createVector(mSpawner.posX, 128, mSpawner.posZ);
    	Vec3D speed = Vec3D.createVector(0, -1, 0);
    	pos.xCoord += mRadius * (rand.nextFloat() * 2 - 1);
    	pos.zCoord += mRadius * (rand.nextFloat() * 2 - 1);
    	pos.xCoord += 0.1 * (rand.nextFloat() * 2 - 1);
    	pos.zCoord += 0.1 * (rand.nextFloat() * 2 - 1);
    	return new Meteor(mWorld, pos, speed);
    }
    
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    private World mWorld;
    private EntityLiving mSpawner;
    private double mLifetime;
    private double mDelayBetweenMeteors;
    private double mTimeoutForSpawn;
    private double mAmount;
    private double mRadius;
}
