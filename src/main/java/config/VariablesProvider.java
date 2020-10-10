package config;

import model.DummyImage;


public class VariablesProvider {

	private static String resourcesPath;
	private static DummyImage dummyImage;

	public static DummyImage getDummyImage() {
		return dummyImage;
	}

	public static void setDummyImage(DummyImage dummyImage) {
		VariablesProvider.dummyImage = dummyImage;
	}

	public static String getResourcesPath() {
		return resourcesPath;
	}
	public static void setResourcesPath(String resourcesPath) {
		VariablesProvider.resourcesPath = resourcesPath;
	}
}
