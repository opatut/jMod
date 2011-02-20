// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

import JMod.CustomTextureManager;
import net.minecraft.src.*;

public class RenderCustomFireball extends Render
{

    public RenderCustomFireball()
    {
    }

    public void func_4012_a(CustomFireball entityfireball, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        float f2 = 2.0F;
        GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        int i = 1;
        //loadTexture("/plugins/TestPlugin1/icons.png");
        CustomTextureManager.getInstance().LoadTexture("/plugins/TestPlugin1/icons.png");
        Tessellator tessellator = Tessellator.instance;
        float f3 = 0.125F; //l
        float f4 = 0.25F; //r
        float f5 = 0; //o
        float f6 = 0.125F; //u
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180F - renderManager.field_1225_i, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.field_1224_j, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
        tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
        tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
        tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
        tessellator.draw();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_4012_a((CustomFireball)entity, d, d1, d2, f, f1);
    }
}