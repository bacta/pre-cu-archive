package com.ocdsoft.bacta.swg.precu.zone;

import com.ocdsoft.bacta.swg.precu.message.game.scene.SceneDestroyObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import lombok.Getter;
import org.magnos.steer.spatial.CollisionCallback;
import org.magnos.steer.spatial.SearchCallback;
import org.magnos.steer.spatial.SpatialDatabase;
import org.magnos.steer.spatial.SpatialEntity;
import org.magnos.steer.spatial.quad.SpatialQuadTree;
import org.magnos.steer.vec.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Planet implements Zone {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Getter
    private String terrainName;

    @Getter
    private String terrainFile;

    private final PlanetMap planetMap;

    //private ProceduralTerrainAppearanceTemplate terrain;

    private SpatialDatabase<Vec3> spatialDatabase = new SpatialQuadTree<Vec3>(new Vec3(-8192, -1000, -8192), new Vec3(8192, 1000, 8192), 16, 10);

    public Planet(PlanetMap planetMap, String terrainName/*, ProceduralTerrainAppearanceTemplate terrain*/) {
        this.planetMap = planetMap;
        this.terrainName = terrainName;
        this.terrainFile = "terrain/" + terrainName + ".trn";
        ///this.terrain = terrain;
    }

    @Override
    public void add(TangibleObject obj) {

        Zone currentZone = obj.getZone();
        if (currentZone != null) {
            currentZone.remove(obj);
        }

        if(obj.getZone() == this) {
            return;
        }

        obj.setZone(this);
        obj.setPosition(obj.getTransform(), false);
        obj.setInert(false);

        spatialDatabase.add(obj);
        int count = spatialDatabase.refresh();
        logger.debug("Add " + obj.getNetworkId() + " to "  + terrainName + " Now has " + count + " objects");
        obj.updateZone();

        planetMap.addObject(this, obj);
    }

    @Override
    public void remove(TangibleObject obj) {
        obj.broadcastMessage(new SceneDestroyObject(obj.getNetworkId(), false));
        obj.setInert(true);
        int count = spatialDatabase.refresh();
        logger.debug("Remove " + obj.getNetworkId() + " to "  + terrainName + " Now has " + count + " objects");
        obj.clearZone();
        planetMap.removeObject(this, obj);
    }

    @Override
    public void clear() {
        spatialDatabase.clear();
    }

    @Override
    public int refresh() {
        return spatialDatabase.refresh();
    }

    @Override
    public int handleCollisions(CollisionCallback callback) {
        return spatialDatabase.handleCollisions(callback);
    }

    @Override
    public int intersects(Vec3 offset, float radius, int max, long collidesWith, SearchCallback callback) {
        return spatialDatabase.intersects(offset, radius, max, collidesWith, callback);
    }

    @Override
    public int contains(Vec3 offset, float radius, int max, long collidesWith, SearchCallback callback) {
        return spatialDatabase.contains(offset, radius, max, collidesWith, callback);
    }

    @Override
    public int knn(Vec3 point, int k, long collidesWith, SpatialEntity[] nearest, float[] distance) {
        return spatialDatabase.knn(point, k, collidesWith, nearest, distance);
    }
}
