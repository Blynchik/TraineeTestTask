package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //указываем для спринг, что это репозиторий
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {// для создания стандартных методов поиска и работы: всех, одного, сохранить, удалить и т.д.
}
