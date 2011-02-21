// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

import JMod.CustomTextureManager;
import JMod.ModelBox;
import JMod.TextureInfo;
import net.minecraft.src.*;

public class RenderMeteor extends Render
{

    public RenderMeteor()
    {
    }

    public void func_4012_a(Meteor meteor, double d, double d1, double d2, 
            float f, float f1)
    {
        float s = meteor.getCollisionBorderSize();
        CustomTextureManager.getInstance().LoadTexture("/plugins/TestPlugin1/icons.png");
        ModelBox b = new ModelBox(Vec3D.createVector(d,d1,d2),Vec3D.createVector(s,s,s));
        b.SetRotation(Vec3D.createVector(f, f1, 0));
        TextureInfo face = new TextureInfo();
        face.Resource = "/plugins/TestPlugin1/icons.png";
        face.U = 1 / 8F;
        face.V = 0F;
        face.UVHeight = 1 / 8F;
        face.UVWidth = 1 / 8F;
        b.SetTexture(face);
        b.Render();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_4012_a((Meteor)entity, d, d1, d2, f, f1);
    }
}