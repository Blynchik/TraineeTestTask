package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //указываем для спринг, что это репозиторий
public interface PlayerRepository extends JpaRepository<Player, Integer> {// для создания стандартных методов сохранения, чтения, обвновления, удаления
}
