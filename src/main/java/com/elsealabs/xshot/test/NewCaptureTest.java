package com.elsealabs.xshot.test;

import com.elsealabs.xshot.capture.CaptureDevice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class NewCaptureTest
{

	public static void main(String[] args)
	{
		testCaptureDevice();
	}

	public static void testCaptureDevice()
	{
		CaptureDevice cd = new CaptureDevice();

		cd.getMonitors().stream()
			.forEach(m -> System.out.println(m.getBounds()));

		BufferedImage c = cd.captureMonitors(cd.getMonitors());

		try
		{
			File file = new File("C:\\Users\\connorelsea\\Desktop\\image.png");
			ImageIO.write(c, "png", file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}