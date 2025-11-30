package io.github.yetti_eng;

import org.junit.jupiter.api.*;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapManagerTest {

    MapManager mapManager;
    TiledMapTileLayer mockLayer;

    @BeforeEach
    public void setup2() {
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
        // Rect covers (0,1) and (1,1)
        Rectangle testRect = new Rectangle(0, 1, 1, 0);
        
        assertTrue(mapManager.isRectInvalid(testRect));
    }

    @Test
    public void validRectTest() {
        // A rect that avoids the (1,1) invalid cell
        // Rect covers (0,0) and (0,1)
        Rectangle testRect = new Rectangle(0, 0, 0, 1);
        
        assertFalse(mapManager.isRectInvalid(testRect));
    }

    @Test 
    public void disposeTest() {
        mapManager.dispose();

        assertNull(mapManager.getRenderer());
        assertNull(mapManager.getMap());
    }
}