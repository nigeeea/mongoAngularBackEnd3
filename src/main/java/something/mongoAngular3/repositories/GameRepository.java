package something.mongoAngular3.repositories;


import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import something.mongoAngular3.models.Game;

@Repository
public class GameRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    //GET METHOD 1 - retrieving A SINGLE game object from mongoDB using the gid as a parameter
    public Game searchByGameId(Integer gameId){

        Query query = new Query();

        query.addCriteria(Criteria.where("gid").is(gameId));

        Document returnedDoc = mongoTemplate.findOne(query, Document.class, "game");

        //convert the Document into a Game object
        Game returnedGame = new Game();

        returnedGame.setGid(returnedDoc.getInteger("gid"));
        returnedGame.setImage(returnedDoc.getString("image"));
        returnedGame.setName(returnedDoc.getString("name"));
        returnedGame.setRanking(returnedDoc.getInteger("ranking"));
        returnedGame.setUrl(returnedDoc.getString("url"));
        returnedGame.setUsers_rated(returnedDoc.getInteger("users_rated"));
        returnedGame.setYear(returnedDoc.getInteger("year"));

        return returnedGame;
    }
    
    //GET METHOD 2 - GET MULTIPLE GAMES from the collection with offset and limit parameter
    public List<Game> searchWithOffsetAndLimit(Integer offset, Integer limit){

        //final org.springframework.data.domain.Pageable pageableRequest = PageRequest.of(offset, limit);

        Query query = new Query();

        //query.with(pageableRequest);

        //can try this later -- should be the same as above
        query.skip(offset).limit(limit);

        return mongoTemplate.find(query, Document.class, "game")
                            .stream()
                            .map(eachGame -> Game.documentToGame(eachGame))
                            .toList();


    }

    //POST METHOD 1 - POST a single game into the collection
    public Game insertGame(Game newGame){

        return mongoTemplate.insert(newGame, "game");
    }

    //POST METHOD 2 - Post multiple games into the collection, might require nested formgroup/arrays in the angular app


    
}
