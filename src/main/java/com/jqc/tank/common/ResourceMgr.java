package com.jqc.tank.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceMgr {

	public static BufferedImage aiTankL, aiTankU, aiTankR, aiTankD;
	public static BufferedImage redTankL, redTankU, redTankR, redTankD;
	public static BufferedImage bulletL, bulletU, bulletR, bulletD;
	public static BufferedImage[] explodes = new BufferedImage[16];

	static {
		try {
			aiTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			aiTankL = CommonUtil.rotateImage(aiTankU,-90);
			aiTankR = CommonUtil.rotateImage(aiTankU,90);
			aiTankD = CommonUtil.rotateImage(aiTankU,180);

			redTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			redTankL = CommonUtil.rotateImage(redTankU,-90);
			redTankR = CommonUtil.rotateImage(redTankU,90);
			redTankD = CommonUtil.rotateImage(redTankU,180);
			
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = CommonUtil.rotateImage(bulletU,-90);
			bulletR = CommonUtil.rotateImage(bulletU,90);
			bulletD = CommonUtil.rotateImage(bulletU,180);

			for(int i = 0; i< explodes.length; i++)
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
