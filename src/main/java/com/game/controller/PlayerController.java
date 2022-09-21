package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //спринг поймет что это контроллер с REST
@RequestMapping("/rest/players") //запрос по условию
public class PlayerController {

    private final PlayerService playerService; //внедряем сервис

    @Autowired //автосвязка
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping() //по запросу по условию
    public List<Player> getPlayers() {
        return playerService.findAll();
    }

    @GetMapping("/count")
    public Integer count() {
        return playerService.findAll().size();
    }

    @PostMapping()
    public Player create(@ModelAttribute("player") Player player,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }

        playerService.save(player);
        return playerService.findOne(player.getId());
    }

    @GetMapping("/{id}")
    public Player getOne(@PathVariable("id") long id, Player player) {
        return playerService.findOne(id);
    }

    @PostMapping("/{id}")
    public Player update(@ModelAttribute("player") Player player,
                         BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return null;
        }

        playerService.update(id, player);
        return playerService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public List<Player> delete(@PathVariable("id") long id){
        playerService.delete(id);
        return playerService.findAll();
    }
}
