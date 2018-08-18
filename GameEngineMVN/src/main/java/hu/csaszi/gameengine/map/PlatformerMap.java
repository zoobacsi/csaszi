package hu.csaszi.gameengine.map;

public class PlatformerMap {


    public PlatformerMap(String title, String levelName, String backgroundMusic) {
        this.title = title;
        this.levelName = levelName;
        this.backgroundMusic = backgroundMusic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(String backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    private String title;
    private String levelName;
    private String backgroundMusic;
}
