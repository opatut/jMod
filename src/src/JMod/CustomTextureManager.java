package JMod;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.GLAllocation;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;

public class CustomTextureManager {
	private CustomTextureManager() {
		mTextures = new HashMap<String, BufferedImage>();
	}
	public static CustomTextureManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CustomTextureManager();
		return INSTANCE;
	}
	
	public boolean RegisterTexture(Plugin plugin, String source) {
		try {
			InputStream s = plugin.getClass().getResourceAsStream(source);
			BufferedImage bufferedimage;
			bufferedimage = ImageIO.read(s);
			s.close();
	        mTextures.put(source, bufferedimage);
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Image not loaded ;(");
			System.exit(1);
			return false;
		}
	}
	
	public void LoadTexture(String source) {
		if(!mTextures.containsKey(source)) {
			System.out.println("Could not Load Texture: " + source);
			System.exit(1);
		}
			
		RenderEngine renderengine = RenderManager.instance.renderEngine;
		Integer index = renderengine.getTextureIndex(source);
		int i;
		if(index != null) {
			i = index.intValue();
		} else {
			renderengine.singleIntBuffer.clear();
	        GLAllocation.generateTextureNames(renderengine.singleIntBuffer);
	        i = renderengine.singleIntBuffer.get(0);
			renderengine.setupTexture(mTextures.get(source), i);
		}
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, i);
	}
	
	private HashMap<String, BufferedImage> mTextures;
	private static CustomTextureManager INSTANCE;
}
