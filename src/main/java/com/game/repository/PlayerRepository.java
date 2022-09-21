package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //указываем для спринг, что это репозиторий
public interface PlayerRepository extends JpaRepository<Player, Integer> {// для создания стандартных методов поиска и работы: всех, одного, сохранить, удалить и т.д.
    Optional<Player> findById(Long id);

    void deleteById(long id);
}
