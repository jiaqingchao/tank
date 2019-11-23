package com.jqc.tank.common;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    void rotateImage() throws IOException {

        long startTime = System.nanoTime();
        BufferedImage aiTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
        long startTime2 = System.nanoTime();
        System.out.println(startTime2- startTime);
        BufferedImage aiTankL = CommonUtil.rotateImage(aiTankU,-90);
        System.out.println(System.nanoTime()- startTime2);
    }
}