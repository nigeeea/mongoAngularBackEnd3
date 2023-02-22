package something.mongoAngular3.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import something.mongoAngular3.models.Game;
import something.mongoAngular3.services.GameService;

@RestController
@RequestMapping(path = "/api")
public class GameRestController {

    @Autowired
    GameService gameSvc;

    @Autowired
    MongoTemplate mongoTemplate;
    
    //Method 0 -- Just for testing
    @GetMapping(path = "/something")
    public ResponseEntity<String> forTesting(){


        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("frsfrs");
    }


    //METHOD 1 -- search for an individual game by the gid
    @GetMapping(path = "/id")
    public ResponseEntity<String> searchByGameId(
        @RequestParam Integer gameId){

        Game gameObj = new Game();
        gameObj = gameSvc.searchByGameId(gameId);

        //convert the game object into a JSON
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        JsonObject returnObject = objectBuilder
                                    .add("image", gameObj.getImage())
                                    .add("name", gameObj.getName())
                                    .add("url", gameObj.getUrl())
                                    .add("gid", gameObj.getGid())
                                    .add("ranking", gameObj.getRanking())
                                    .add("users_rated", gameObj.getUsers_rated())
                                    .add("year", gameObj.getYear())
                                    .build();

        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(returnObject.toString());
    }

    //GET METHOD 2 -- Get multiple documents
    @GetMapping(path = "/aFewGames")
    public ResponseEntity<String> searchWithOffsetAndLimit(
        @RequestParam Integer offset,
        @RequestParam Integer limit
    ){

        List<Game> gameList = gameSvc.searchWithOffsetAndLimit(offset, limit);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Game g: gameList){
            JsonObject jsonGame = g.gameToJson(g);
            arrayBuilder.add(jsonGame);
        }

        JsonArray jsonGameArray = arrayBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonGameArray.toString());
    }



    // POST METHOD 1 - Post/Add an individual Game into the Collection as a Document
    @PostMapping(path = "/addGame", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGame(
        @RequestBody String newGameJsonString){

            try {

                Game newGame = new ObjectMapper().readValue(newGameJsonString, Game.class);

                Game gameResponse = gameSvc.insertGame(newGame);

                //turn game into Json
                JsonObject returnedJsonObject = Game.gameToJson(gameResponse);

                return ResponseEntity.status(HttpStatus.OK)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(returnedJsonObject.toString());

            } catch (Exception e) {

                JsonObject errorObject = Json.createObjectBuilder().add("error", e.getMessage().toString()).build();
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(errorObject.toString());
            }
            
    }

}
