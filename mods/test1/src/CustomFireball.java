import java.util.List;

import net.minecraft.src.*;

public class CustomFireball extends Entity
{

    public CustomFireball(World world)
    {
        super(world);
        x = -1;
        y = -1;
        z = -1;
        inTile = 0;
        inGround = false;
        shake = 0;
        field_9395_l = 0;
        setSize(1.0F, 1.0F);
    }

    protected void entityInit()
    {
    }

    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public CustomFireball(World world, EntityLiving entityliving, double d, double d1, double d2)
    {
        super(world);
        x = -1;
        y = -1;
        z = -1;
        inTile = 0;
        inGround = false;
        shake = 0;
        field_9395_l = 0;
        spawnerEntity = entityliving;
        setSize(1.0F, 1.0F);
        setLocationAndAngles(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = motionY = motionZ = 0.0D;
        /*d += rand.nextGaussian() * 0.40000000000000002D;
        d1 += rand.nextGaussian() * 0.40000000000000002D;
        d2 += rand.nextGaussian() * 0.40000000000000002D;*/
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        speedX = (d / d3) * 0.10000000000000001D;
        speedY = (d1 / d3) * 0.10000000000000001D;
        speedZ = (d2 / d3) * 0.10000000000000001D;
    }

    public void onUpdate()
    {
        super.onUpdate();
        fire = 10;
        if(shake > 0)
        {
            shake--;
        }
        if(inGround)
        {
            int i = worldObj.getBlockId(x, y, z);
            if(i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                lifeTime = 0;
                field_9395_l = 0;
            } else
            {
                lifeTime++;
                if(lifeTime == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_9395_l++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        @SuppressWarnings("rawtypes")
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.canBeCollidedWith() || entity1 == spawnerEntity && field_9395_l < 25)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.func_1169_a(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.attackEntityFrom(spawnerEntity, 0));
            }
            int r = 5;
            for(int dx = -r; dx <= r; dx++) {
                for(int dz = -r; dz <= r; dz++) {
                    for(int dy = r; dy >= -r; dy--) {
                    	int x = (int)posX + dx;
                    	int y = (int)posY + dy;
                    	int z = (int)posZ + dz;
                    	
                    	if(!worldObj.isAirBlock(x, y - 1, z) && worldObj.isAirBlock(x,y,z) && dx*dx + dy*dy + dz*dz <= r*r) {
                    		worldObj.setBlockWithNotify(x, y, z, Block.lavaStill.blockID);
                    	}
                    }	
                }	
            }
            setEntityDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.95F;
        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX += speedX;
        motionY += speedY;
        motionZ += speedZ;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)x);
        nbttagcompound.setShort("yTile", (short)y);
        nbttagcompound.setShort("zTile", (short)z);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("shake", (byte)shake);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        x = nbttagcompound.getShort("xTile");
        y = nbttagcompound.getShort("yTile");
        z = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        shake = nbttagcompound.getByte("shake") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public float getCollisionBorderSize()
    {
        return 1.0F;
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        setBeenAttacked();
        if(entity != null)
        {
            Vec3D vec3d = entity.getLookVec();
            if(vec3d != null)
            {
                motionX = vec3d.xCoord;
                motionY = vec3d.yCoord;
                motionZ = vec3d.zCoord;
                speedX = motionX * 0.10000000000000001D;
                speedY = motionY * 0.10000000000000001D;
                speedZ = motionZ * 0.10000000000000001D;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    private int x;
    private int y;
    private int z;
    private int inTile;
    private boolean inGround;
    public int shake;
    private EntityLiving spawnerEntity;
    private int lifeTime;
    private int field_9395_l;
    public double speedX;
    public double speedY;
    public double speedZ;
}
