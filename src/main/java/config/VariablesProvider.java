package config;

import java.awt.image.BufferedImage;

public class VariablesProvider {

	private static String resourcesPath;
	private static BufferedImage dummyImage;

	public static BufferedImage getDummyImage() {
		return dummyImage;
	}

	public static void setDummyImage(BufferedImage dummyImage) {
		VariablesProvider.dummyImage = dummyImage;
	}

	public static String getResourcesPath() {
		return resourcesPath;
	}
	public static void setResourcesPath(String resourcesPath) {
		VariablesProvider.resourcesPath = resourcesPath;
	}
}
