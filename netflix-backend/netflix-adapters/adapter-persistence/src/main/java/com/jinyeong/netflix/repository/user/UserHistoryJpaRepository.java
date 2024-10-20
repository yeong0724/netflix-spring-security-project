package com.jinyeong.netflix.repository.user;

import com.jinyeong.netflix.entity.user.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryJpaRepository extends JpaRepository<UserHistoryEntity, Long> {
}
