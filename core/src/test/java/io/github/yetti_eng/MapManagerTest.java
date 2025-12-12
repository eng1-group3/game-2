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
        when(mockLayer.getCell(0, 0)).thenReturn(null);
        when(mockLayer.getCell(0,1)).thenReturn(null);
        when(mockLayer.getCell(1, 0)).thenReturn(null);
        TiledMapTileLayer.Cell invalidCell = mock(TiledMapTileLayer.Cell.class);
        when(mockLayer.getCell(1, 1)).thenReturn(invalidCell);

        mapManager.setCollisionsLayer(mockLayer);
        mapManager.setRenderer(mock(OrthogonalTiledMapRenderer.class));
        when(mapManager.getRenderer().getUnitScale()).thenReturn(1 / 48f);
    }
    
    @Test
    public void validPositionTest() {
        int x = 1;
        int y = 0;

        assertFalse(mapManager.isPositionInvalid(x, y));
    }

    @Test 
    public void invalidPositionTest() {
        int x = 1;
        int y = 1;

        assertTrue(mapManager.isPositionInvalid(x, y));
    }

    @Test
    public void invalidRectTest() {
        // A rect that doesn't avoid the (1,1) invalid cell
        Rectangle testRect = new Rectangle(0f, 1f, 1.5f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void validRectTest() {
        // A rect that avoids the (1,1) invalid cell
        Rectangle testRect = new Rectangle(0.25f, 0.25f, 0.5f, 1f);
        
        assertFalse(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void boundaryInvalidRectTest() {
        // A rect that goes just over the boundary
        Rectangle testRect = new Rectangle(0f, 1f, 1f, 0.5f);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void boundaryValidRectTest() {
        // A rect that stay
        Rectangle testRect = new Rectangle(0f, 1f, 0.9999f, 0.5f);
        
        assertFalse(mapManager.isRectInvalid(testRect));
    }
}