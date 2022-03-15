package checkers.resources;

import checkers.game.Game;

import java.io.*;

public abstract class GameLoader {
	
	static FileOutputStream fos;
	static ObjectOutputStream oos;
	static FileInputStream fis;
	static ObjectInputStream ois;
	
	public static void writeGameToFile(Game game, String path) throws IOException {
		//writes a game to the filepath specified in "path"
		
		fos = new FileOutputStream(path);
		oos = new ObjectOutputStream(fos);
		
		oos.writeObject(game);
	}
	
	public static Game readGameFromFile(String path) throws IOException {
		//returns a game that was read from the filepath
		
		fis = new FileInputStream(path);
		ois = new ObjectInputStream(fis);
		
		try {
			return (Game)ois.readObject();
		} catch (ClassNotFoundException e) {throw new IOException("Wrong file type or invalid file path.");}
	}
}