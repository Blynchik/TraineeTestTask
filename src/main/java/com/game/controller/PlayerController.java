package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.PlayerSpecs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //спринг поймет что это контроллер с REST
@RequestMapping("/rest/players") //запрос по условию
public class PlayerController {

    private final PlayerService playerService; //внедряем сервис

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping //по запросу по условию
    @ResponseBody
    public List<Player> getAllPlayers(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "race", required = false) Race race,
                                      @RequestParam(value = "profession", required = false) Profession profession,
                                      @RequestParam(value = "after", required = false) Long after,
                                      @RequestParam(value = "before", required = false) Long before,
                                      @RequestParam(value = "banned", required = false) Boolean banned,
                                      @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                      @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                      @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                      @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                      @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                      @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
       List<Player> allPlayers = playerService.findAll(Specification.where(
                                PlayerSpecs.filterLikeByTitle(title))
                        .and(PlayerSpecs.filterLikeByName(name))
                        .and(PlayerSpecs.filterEqualsByRace(race))
                        .and(PlayerSpecs.filterEqualsByProfession(profession))
                        .and(PlayerSpecs.filterGreaterThanOrEqualToByAfter(after))
                        .and(PlayerSpecs.filterLessThanOrEqualToByBefore(before))
                        .and(PlayerSpecs.filterGreaterThanOrEqualToByMinLevel(minLevel))
                        .and(PlayerSpecs.filterLessThanOrEqualToByMaxLevel(maxLevel))
                        .and(PlayerSpecs.filterByBanned(banned))
                        .and(PlayerSpecs.filterByExperience(minExperience, maxExperience)),
                pageable).getContent();
        return allPlayers;
    }

    @GetMapping("/count") //по запросу по условию
    @ResponseBody
    public Long getAllPlayersCount(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "race", required = false) Race race,
                                   @RequestParam(value = "profession", required = false) Profession profession,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        return playerService.findAllCount(Specification.where(
                        PlayerSpecs.filterLikeByTitle(title))
                .and(PlayerSpecs.filterLikeByName(name))
                .and(PlayerSpecs.filterEqualsByRace(race))
                .and(PlayerSpecs.filterEqualsByProfession(profession))
                .and(PlayerSpecs.filterGreaterThanOrEqualToByAfter(after))
                .and(PlayerSpecs.filterLessThanOrEqualToByBefore(before))
                .and(PlayerSpecs.filterGreaterThanOrEqualToByMinLevel(minLevel))
                .and(PlayerSpecs.filterLessThanOrEqualToByMaxLevel(maxLevel))
                .and(PlayerSpecs.filterByBanned(banned))
                .and(PlayerSpecs.filterByExperience(minExperience, maxExperience)));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable("id") Long id) {
        return playerService.findOne(id);
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.save(player));
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player,
                                               @PathVariable("id") long id) {
        return ResponseEntity.ok(playerService.update(id, player));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
        playerService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
