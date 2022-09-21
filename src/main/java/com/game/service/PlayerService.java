package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
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
    public void save(Player player) {
        if (approvedToSave(player)) {
            playerRepository.save(player);
        }
    }

    private boolean approvedToSave(Player player) {
        if (player.getName() == null ||
                player.getName().length() > NAME_MAX_LENGTH) {
            return false;
        }

        if (player.getTitle() == null ||
                player.getTitle().length() > TITLE_MAX_LENGTH) {
            return false;
        }

        if (player.getRace() == null) {
            return false;
        }

        if (player.getProfession() == null) {
            return false;
        }

        if (player.getBirthday() == null ||
                player.getBirthday().getTime() < 0 ||
                player.getBirthday().getTime() < START_TIME.getTime() ||
                player.getBirthday().getTime() > END_TIME.getTime()) {
            return false;
        }

        if (player.getExperience() == null ||
                player.getExperience() > MAX_EXPERIENCE ||
                player.getExperience() < MIN_EXPERIENCE) {
            return false;
        }

        if (player.getLevel() == null) {
            return false;
        }

        if (player.getUntilNextLevel() == null) {
            return false;
        }

        return true;
    }
}
