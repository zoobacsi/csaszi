package hu.csaszi.twodee.util;

import hu.csaszi.twodee.map.MapBuilder;
import hu.csaszi.twodee.map.MapType;
import hu.csaszi.twodee.map.TileUtil;
import hu.csaszi.twodee.map.beans.MapTile;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.map.interfaces.TiledMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.util.Log;

public class FileUtil {

	public static TiledMap parseFile(File file) throws FileNotFoundException {

		int x = -1;
		int y = -1;
		int depth = 0;
		int tile = -1;
		int tileNum = 0;
		
		int mapTypeId = -1;
		int maxX = -1;
		int maxY = -1;
		int defaultTileId = -1;
		List<MapTile> mapTiles = new ArrayList<>();
		TiledMap tiledMap = null;
		
		if (file.canRead()) {
			Scanner scanner = new Scanner(file);
			String line = null;
			String[] params;
			try {
				int lineNum = 0;

				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					params = line.split(";", 5);
					if (lineNum == 0) {
						maxX = Integer.valueOf(params[0]).intValue();
						maxY = Integer.valueOf(params[1]).intValue();
						defaultTileId = Integer.valueOf(params[2]).intValue();
						mapTypeId = Integer.valueOf(params[3]).intValue();
						
					} else {
						x = Integer.valueOf(params[0]).intValue();
						y = Integer.valueOf(params[1]).intValue();
						depth = Integer.valueOf(params[2]).intValue();
						tile = Integer.valueOf(params[3]).intValue();
						tileNum = Integer.valueOf(params[4]).intValue();
						mapTiles.add(new MapTile(x, y, depth, tile, tileNum));
					}

					lineNum++;

				}
				
				MapType mapType = MapType.getById(mapTypeId);
				tiledMap = MapBuilder.create(mapType)
						.setDimensions(maxX, maxY)
						.setDefaultTileId(defaultTileId)
						.setMapTiles(mapTiles)
						.setMapFilePath(file.getCanonicalPath())
						.build();
				
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			} finally {
				scanner.close();
			}
		}
		
		return tiledMap;
	}
	
	public static void saveMap(File mapFile, TiledMap tiledMap) {
		boolean file = mapFile != null && mapFile.exists() && mapFile.canWrite();

		int tileId = -1;
		int maxX = tiledMap.getMaxX();
		int maxY = tiledMap.getMaxY();
		int defaultTileId = tiledMap.getDefaultTileId();
		int mapTypeId = tiledMap.getMapTypeId();
		TileObject[][][] map = tiledMap.getTileObjectMap();
		
		BufferedWriter writer = null;
		try {
			if (file) {
				writer = new BufferedWriter(new FileWriter(mapFile, false));
			}
			try {
				if (file) {
					writer.append(maxX + ";" + maxY + ";" + defaultTileId + ";" + mapTypeId);
				} else {
					//TODO: implement network save
				}
				for (int d = 0; d < 4; d++) {
					for (int y = 0; y < maxY; y++) {
						for (int x = 0; x < maxX; x++) {
							TileObject tile = map[x][y][d];
							if (tile != null) {
								tileId = TileUtil.getTileId(tile.getType(), tiledMap.getMapTypeId() == MapType.ISOMETRIC.getId());

								if (file) {
									writer.newLine();
									writer.append(tile.getXIndex() + ";" + tile.getYIndex() + ";" + tile.getDepth() + ";" + tileId + ";" + tile.getTileNum());
								} else {
									//TODO: implement network save
								}
							}
						}
					}
				}
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			} finally {
				if (file) {
					writer.flush();
					writer.close();
				}
			}

		} catch (IOException e) {
			Log.error(e.getMessage(), e);
		}

	}
	
	public static void appendToMapFile(File file, TileObject tile, boolean isometric) {
		boolean fileBool = (file != null && file.exists() && file.canWrite() && tile != null);
		
		int tileId = TileUtil.getTileId(tile.getType(), isometric);

		if (tileId >= 0) {
			try {
				BufferedWriter writer = null;
				if (fileBool) {
					writer = new BufferedWriter(new FileWriter(file, true));
				}
				try {
					if (fileBool) {
						writer.newLine();
						writer.append(tile.getXIndex() + ";" + tile.getYIndex() + ";" + tile.getDepth() + ";" + tileId + ";" + tile.getTileNum());
					} else {
						//TODO: implement network save
					}

				} catch (Exception e) {
					Log.error(e.getMessage(), e);
				} finally {
					if (fileBool) {
						writer.flush();
						writer.close();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
