package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //спринг поймет что это контроллер с REST
@RequestMapping("/rest/players") //запрос по условию
public class PlayerController {

    private final PlayerService playerService; //внедряем сервис

    @Autowired //автосвязка
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping() //по запросу по условию
    public List<Player> getPlayers(){
        return playerService.findAll();
    }
}
