package com.ocdsoft.bacta.swg.precu.zone;

import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

public interface Zone {

	String getTerrainName();
	String getTerrainFile();
	
	void add(TangibleObject obj);
	void remove(TangibleObject obj);
	
	void clear();
	int refresh();
	
	int handleCollisions(CollisionCallback callback);

	int intersects(Vec3 offset, float radius, int max, long collidesWith, SearchCallback callback);
	int contains(Vec3 offset, float radius, int max, long collidesWith, SearchCallback callback);
	int knn(Vec3 point, int k, long collidesWith, SpatialEntity[] nearest, float[] distance);
}
