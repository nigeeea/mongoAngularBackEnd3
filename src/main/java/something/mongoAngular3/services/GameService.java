package something.mongoAngular3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import something.mongoAngular3.models.Game;
import something.mongoAngular3.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepo;

    public Game searchByGameId(Integer gameId){
        return gameRepo.searchByGameId(gameId);
    }

    public List<Game> searchWithOffsetAndLimit(Integer offset, Integer limit){
        return gameRepo.searchWithOffsetAndLimit(offset, limit);
    }
    

    public Game insertGame(Game newGame){
        return gameRepo.insertGame(newGame);
    }
}
