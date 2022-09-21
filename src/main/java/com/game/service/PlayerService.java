package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service //указываем для спринг, что это сервис сущности
@Transactional(readOnly = true) //указываем спринг, что методы класса по умолчанию будут работать только на чтение (get)
public class PlayerService {

    private final PlayerRepository playerRepository; //внедряем репозиторий

    @Autowired //автосвязка с спрингом
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findOne(long id) {
        Optional<Player> foundPlayer = playerRepository.findById(id);
        return foundPlayer.orElse(null);
    }

    @Transactional //не только для чтения
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void update(long id, Player updatedPlayer){
        updatedPlayer.setId(id);
        playerRepository.save(updatedPlayer);
    }

    @Transactional
    public void delete(long id){
        playerRepository.deleteById(id);
    }
}
