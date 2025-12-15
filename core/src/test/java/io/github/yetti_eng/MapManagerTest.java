package io.github.yetti_eng;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MapManagerTest {

    MapManager mapManager;
    TiledMapTileLayer mockLayer;

    @BeforeEach
    public void setup() {
        mapManager = new MapManager(null);
        mockLayer = mock(TiledMapTileLayer.class);

        /* Methods used by 'isPositionInvalid' are getTileWidth(), 
        getTileHeight() and getCell(), so we need to mock them. */
        // getTileWidth() and getTileHeight() mocking (each tile is 48x48 pixels)
        when(mockLayer.getTileWidth()).thenReturn(48);
        when(mockLayer.getTileHeight()).thenReturn(48);
        
        // Simulate a simple 2x2 map with one invalid cell
        int MAP_WIDTH = 2;
        int MAP_HEIGHT = 2;
        TiledMapTileLayer.Cell validCell = null;
        TiledMapTileLayer.Cell invalidCell = mock(TiledMapTileLayer.Cell.class);
        when(mockLayer.getCell(anyInt(), anyInt())).thenAnswer(lambda -> {
            int x = lambda.getArgument(0);
            int y = lambda.getArgument(1);

            if (x < 0 || x > MAP_WIDTH-1 || y < 0 || y > MAP_HEIGHT-1) {
                return invalidCell;
            } else if (x == 1 && y == 1) {
                return invalidCell;
            } else {
                return validCell;
            }
        });

        mapManager.setCollisionsLayer(mockLayer);
        mapManager.setRenderer(mock(OrthogonalTiledMapRenderer.class));
        when(mapManager.getRenderer().getUnitScale()).thenReturn(1 / 48f);
    }
    
    @Test
    public void validPosTest() {
        float x = 1.25f;
        float y = 0.25f;

        assertFalse(mapManager.isPositionInvalid(x, y));
    }

    @Test
    public void validBoundaryPosTest() {
        float x = 0.999f;
        float y = 1.25f;

        assertFalse(mapManager.isPositionInvalid(x, y));
    }

    @Test 
    public void invalidPosTest() {
        float x = 1.25f;
        float y = 1.25f;

        assertTrue(mapManager.isPositionInvalid(x, y));
    }

    @Test 
    public void invalidBoundaryPosTest() {
        float x = 1f;
        float y = 1.5f;

        assertTrue(mapManager.isPositionInvalid(x, y));
    }

    @Test
    public void entirelyInvalidRectTest() {
        // A rect that is entirely in the blocked cell
        Rectangle testRect = new Rectangle(1.25f, 1.25f, 0.5f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void partiallyInvalidRectTest() {
        // A rect that is partially in the blocked cell
        Rectangle testRect = new Rectangle(0.75f, 0.75f, 0.5f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void fullyOutsideMapRectTest() {
        // A rect that is not in the blocked cell, but is outside of the map
        Rectangle testRect = new Rectangle(3f, 3f, 0.5f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void partiallyOutsideMapRectTest() {
        // A rect that is not in the blocked cell, but partially
        Rectangle testRect = new Rectangle(0f, 1f, 0.5f, 2f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void boundaryInvalidRectTest() {
        // A rect that goes (just) collides with the blocked cell
        Rectangle testRect = new Rectangle(0f, 1f, 1f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void validRectTest() {
        // A rect that avoids the (1,1) invalid cell
        Rectangle testRect = new Rectangle(0.25f, 0.25f, 0.5f, 1f);
        
        assertFalse(mapManager.isRectInvalid(testRect));
    }

    
    @Test
    public void boundaryValidRectTest() {
        // A rect that (just) avoids the blocked cell
        Rectangle testRect = new Rectangle(0f, 1f, 0.9999f, 0.5f);
        
        assertFalse(mapManager.isRectInvalid(testRect));
    }
}