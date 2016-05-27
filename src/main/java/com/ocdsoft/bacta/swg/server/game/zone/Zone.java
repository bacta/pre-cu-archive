package com.ocdsoft.bacta.swg.server.game.zone;

import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import org.magnos.steer.spatial.CollisionCallback;
import org.magnos.steer.spatial.SearchCallback;
import org.magnos.steer.spatial.SpatialEntity;

public interface Zone {

	String getTerrainName();
	String getTerrainFile();
	
	void add(TangibleObject obj);
	void remove(TangibleObject obj);
	
	void clear();
	int refresh();
	
	int handleCollisions(CollisionCallback callback);

	int intersects(Vector offset, float radius, int max, long collidesWith, SearchCallback callback);

	int contains(Vector offset, float radius, int max, long collidesWith, SearchCallback callback);

	int knn(Vector point, int k, long collidesWith, SpatialEntity[] nearest, float[] distance);
}
