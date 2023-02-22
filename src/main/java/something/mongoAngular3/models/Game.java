package something.mongoAngular3.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Game {

    Integer gid;
    String name;
    Integer year;
    Integer ranking;
    Integer users_rated;
    String url;
    String image;


    public Integer getGid() {
        return this.gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsers_rated() {
        return this.users_rated;
    }

    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public static JsonObject gameToJson(Game game){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        JsonObject jsonObject =
        objectBuilder.add("image", game.getImage())
                    .add("name", game.getName())
                    .add("url", game.getUrl())
                    .add("gid", game.getGid())
                    .add("ranking", game.getRanking())
                    .add("users_rated", game.getUsers_rated())
                    .add("year", game.getYear())
                    .build();

                    return jsonObject;
    }

    public static Game documentToGame(Document doc){
        Game g = new Game();

        g.setGid(doc.getInteger("gid"));
        g.setImage(doc.getString("image"));
        g.setName(doc.getString("name"));
        g.setRanking(doc.getInteger("ranking"));
        g.setUrl(doc.getString("url"));
        g.setUsers_rated(doc.getInteger("users_rated"));
        g.setYear(doc.getInteger("year"));

        return g;
    }
    
}
