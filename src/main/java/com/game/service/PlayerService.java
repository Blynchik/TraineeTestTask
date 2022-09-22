package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.utils.Exception400;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service //указываем для спринг, что это сервис сущности
@Transactional(readOnly = true) //указываем спринг, что методы класса по умолчанию будут работать только на чтение (get)
public class PlayerService {

    private static final int NAME_MAX_LENGTH = 12;
    private static final int TITLE_MAX_LENGTH = 30;
    private static final int MAX_EXPERIENCE = 10000000;
    private static final int MIN_EXPERIENCE = 0;
    private static final Date START_TIME = new Date(2000, 0, 1);
    private static final Date END_TIME = new Date(3000, 11, 31);

    private final PlayerRepository playerRepository; //внедряем репозиторий

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Page<Player> findAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Long findAllCount() {
        return playerRepository.count();
    }

    @Transactional
    public Player save(Player player) {
        approvedToSave(player);
        if(player.getBanned() == null) player.setBanned(false);
        player.setLevel(countCurrentLevel(player));
        player.setUntilNextLevel(countTillNextLevel(player));
        return playerRepository.save(player);
    }

    private void approvedToSave(Player player) {


        if (player.getName() == null ||
                player.getName().isEmpty() ||
                player.getName().length() > NAME_MAX_LENGTH) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getTitle() == null ||
                player.getTitle().isEmpty() ||
                player.getTitle().length() > TITLE_MAX_LENGTH) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getRace() == null) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getProfession() == null) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getBirthday() == null ||
                player.getBirthday().getTime() < 0 ||
                player.getBirthday().getTime() < START_TIME.getTime() &&
                        player.getBirthday().getTime() > END_TIME.getTime()) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getExperience() == null ||
                player.getExperience() > MAX_EXPERIENCE ||
                player.getExperience() < MIN_EXPERIENCE) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getLevel() == null) {
            throw new Exception400("Inf should not be empty");
        }

        if (player.getUntilNextLevel() == null) {
            throw new Exception400("Inf should not be empty");
        }
    }

    private int countCurrentLevel(Player player) {
        double level = (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100;
        return (int) level;
    }

    private int countTillNextLevel(Player player) {
        int tillNextLevel = 50 * (countCurrentLevel(player) + 1) * (countCurrentLevel(player) + 2) - player.getExperience();
        return tillNextLevel;
    }
}
