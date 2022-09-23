package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerSpecs {

    public static Specification<Player> filterLikeByTitle(String title) {
        return ((root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        });
    }

    public static Specification<Player> filterLikeByName(String name) {
        return ((root, query, criteriaBuilder) -> {
            if (name == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        });
    }


    public static Specification<Player> filterEqualsByRace(Race race) {
        return ((root, query, criteriaBuilder) -> {
            if (race == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("race"), race);
        });
    }

    public static Specification<Player> filterEqualsByProfession(Profession profession) {
        return ((root, query, criteriaBuilder) -> {
            if (profession == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("profession"), profession);
        });
    }

    public static Specification<Player> filterGreaterThanOrEqualToByAfter(Long after) {
        return ((root, query, criteriaBuilder) -> {
            if (after == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
        });
    }

    public static Specification<Player> filterLessThanOrEqualToByBefore(Long before) {
        return ((root, query, criteriaBuilder) -> {
            if (before == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before));
        });
    }

    public static Specification<Player> filterGreaterThanOrEqualToByMinLevel(Integer minLevel) {
        return ((root, query, criteriaBuilder) -> {
            if (minLevel == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel);
        });
    }

    public static Specification<Player> filterLessThanOrEqualToByMaxLevel(Integer maxLevel) {
        return ((root, query, criteriaBuilder) -> {
            if (maxLevel == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel);
        });
    }

    public static Specification<Player> filterByBanned(Boolean banned) {
        return ((root, query, criteriaBuilder) -> {
            if (banned == null) {
                return null;
            } else if (banned) {
                return criteriaBuilder.isTrue(root.get("banned"));
            } else {
                return criteriaBuilder.isFalse(root.get("banned"));
            }
        });
    }

    public static Specification<Player> filterByExperience(Integer minExp, Integer maxExp) {
        return ((root, query, criteriaBuilder) -> {
            if (minExp == null && maxExp == null) {
                return null;
            } else if (minExp == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExp);
            } else if (maxExp == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExp);
            } else {
                return criteriaBuilder.between(root.get("experience"), minExp, maxExp);
            }
        });
    }
}
