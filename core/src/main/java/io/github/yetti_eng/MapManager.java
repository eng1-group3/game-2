package io.github.yetti_eng;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Manages the tile map of the game.
 * Responsible for it's loading, rendering and collision detection with the player.
 */
public class MapManager {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private TiledMapTileLayer collisionsLayer;

    public MapManager(OrthographicCamera camera) {
        this.camera = camera; //for rendering on the screen
    }

    // Methods for unit testing using Mockito
    public void setCollisionsLayer(TiledMapTileLayer newCollisionsLayer) {
        collisionsLayer = newCollisionsLayer;
    }

    public void setRenderer(OrthogonalTiledMapRenderer newRenderer) {
        renderer = newRenderer;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public TiledMap getMap() {
        return map;
    }
    // End of unit test methods

    /**
     *Loads the tilemap and defines the collision layer
     * @param mapName internal path of the file of the map
     */
    public void loadMap(String mapName) {
        map = new TmxMapLoader().load(mapName);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 48f); //48x48 pixels per tile
        //"collisions" layer includes all tiles that cannot be walked on
        collisionsLayer = (TiledMapTileLayer) map.getLayers().get("collisions");
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    /**
     *Checks if a world coordinate is unwalkable/ causes a collision with the player
     * @param posX
     * @param posY
     * @return true if position falls within a collision tile, false otherwise
     */
    public boolean isPositionInvalid(float posX, float posY) {
        float tileWidth = collisionsLayer.getTileWidth() * renderer.getUnitScale();
        float tileHeight = collisionsLayer.getTileHeight() * renderer.getUnitScale();
        //finds tile coordinates of a map position
        int x = (int)(posX / tileWidth);
        int y = (int)(posY / tileHeight);
        //if the cell at tile coords is null, position is valid
        TiledMapTileLayer.Cell cell = collisionsLayer.getCell(x, y);
        return cell != null; //position is invalid if it falls in an unwalkable tile
    }

    /**
     *Checks if any corner of a rectangle object falls in an invalid position.
     *
     * @param rect represents the hitbox of the player
     * @return true if any corner is in the collision layer
     */
    //rectangle is blocked if any corners of the rectangle are invalid tiles
    public boolean isRectInvalid(Rectangle rect) {
        return isPositionInvalid(rect.x, rect.y) || // bottom left corner
            isPositionInvalid(rect.x + rect.width, rect.y) || // bottom right
            isPositionInvalid(rect.x, rect.y + rect.height) || // top left
            isPositionInvalid(rect.x + rect.width, rect.y + rect.height); // top right
    }

    public float getMapWidth() {
        int mapWidthInTiles = map.getProperties().get("width", Integer.class);
        int tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);

        return (mapWidthInTiles * tilePixelWidth / 48f);
    }

    public float getMapHeight() {
        int mapHeightInTiles = map.getProperties().get("height", Integer.class);
        int tilePixelHeight = map.getProperties().get("tileheight", Integer.class);

        return (mapHeightInTiles * tilePixelHeight / 48f);
    }

    public void dispose(){
        renderer.dispose();
        map.dispose();
    }
}

