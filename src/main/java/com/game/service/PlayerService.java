package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //указываем для спринг, что это сервис сущности
@Transactional(readOnly = true) //указываем спринг, что методы класса по умолчанию будут работать только на чтение (get)
public class PlayerService {

    private final PlayerRepository playerRepository; //внедряем репозиторий

    @Autowired //автосвязка с спрингом, что это метод
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll(){
        return playerRepository.findAll();
    }
}
