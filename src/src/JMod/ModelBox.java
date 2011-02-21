package JMod;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;

public class ModelBox implements IModel {

	public ModelBox(Vec3D pos, Vec3D size) {
		faces = new TextureInfo[6];
	}
	
	public void SetRotation(Vec3D rot) {
		mRotation = rot;
	}
	
	public void SetTexture(TextureInfo info) {
		for(int i = 0; i <= 5; i++) {
			faces[i] = info;
		}
	}
	
	public void SetTexture(Face face, TextureInfo info) {
		faces[face.id] = info; 
	}
	
	public void Render() {
		// center 
		double px = mPosition.xCoord;
		double py = mPosition.yCoord;
		double pz = mPosition.zCoord;
		
		// half size
		double sx = mSize.xCoord / 2;
		double sy = mSize.yCoord / 2;
		double sz = mSize.zCoord / 2;
		
		RenderFace(Face.Top);
		RenderFace(Face.Bottom);
		RenderFace(Face.Left);
		RenderFace(Face.Right);
		RenderFace(Face.Front);
		RenderFace(Face.Back);
	}
	
	private void RenderFace(Face face) {
        TextureInfo i = faces[face.id];
        
		GL11.glPushMatrix();
        GL11.glTranslated(mPosition.xCoord, mPosition.yCoord, mPosition.zCoord);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glScaled(mSize.xCoord, mSize.yCoord, mSize.zCoord);
        CustomTextureManager.getInstance().LoadTexture(i.Resource);
        
        
        Tessellator tess = Tessellator.instance;
        GL11.glRotatef((float) mRotation.xCoord, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef((float) mRotation.yCoord, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef((float) mRotation.zCoord, 0.0F, 0.0F, 1.0F);
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);
        
        float x1,x2,x3,x4;
        float y1,y2,y3,y4;
        float z1,z2,z3,z4;
        x1 = x2 = x3 = x4 = y1 = y2 = y3 = y4 = z1 = z2 = z3 = z4 = 0;
        
        switch(face) {
        case Top:
        	x1 = -1; y1 = +1; z1 = -1;
        	x2 = +1; y2 = +1; z2 = -1;
        	x3 = +1; y3 = +1; z3 = +1;
        	x4 = -1; y4 = +1; z4 = +1;
        	break;
        case Bottom:
        	x1 = -1; y1 = -1; z1 = +1;
        	x2 = +1; y2 = -1; z2 = +1;
        	x3 = +1; y3 = -1; z3 = -1;
        	x4 = -1; y4 = -1; z4 = -1;
        	break;
        case Left:
        	x1 = -1; y1 = +1; z1 = -1;
        	x2 = -1; y2 = +1; z2 = +1;
        	x3 = -1; y3 = -1; z3 = +1;
        	x4 = -1; y4 = -1; z4 = -1;
        	break;
        case Right:
        	x1 = +1; y1 = +1; z1 = +1;
        	x2 = +1; y2 = +1; z2 = -1;
        	x3 = +1; y3 = -1; z3 = -1;
        	x4 = +1; y4 = -1; z4 = +1;
        	break;
        case Front:
        	x1 = -1; y1 = +1; z1 = +1;
        	x2 = +1; y2 = +1; z2 = +1;
        	x3 = +1; y3 = -1; z3 = +1;
        	x4 = -1; y4 = -1; z4 = +1;
        	break;
        case Back:
        	x1 = +1; y1 = +1; z1 = -1;
        	x2 = +1; y2 = -1; z2 = -1;
        	x3 = -1; y3 = -1; z3 = -1;
        	x4 = -1; y4 = +1; z4 = -1;
        	break;
        }

        tess.addVertexWithUV(x1, y1, z1, i.U, i.V);								// top left
        tess.addVertexWithUV(x2, y2, z2, i.U + i.UVWidth, i.V);					// top right
        tess.addVertexWithUV(x3, y3, z3, i.U + i.UVWidth, i.V + i.UVHeight);	// bottom right
        tess.addVertexWithUV(x4, y4, z4, i.U, i.V + i.UVHeight);        		// bottom left
        tess.draw();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
	}
	
	private Vec3D mPosition, mSize, mRotation;
	private TextureInfo[] faces;
	
	public enum Face {
		Top(0),
		Bottom(1),
		Left(2),
		Right(3),
		Front(4),
		Back(5);
		
		public final int id;
		Face(int i) {
			id = i;
		}
	}
}
