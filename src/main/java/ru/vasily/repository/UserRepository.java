package ru.vasily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vasily.model.ChatUser;

public interface UserRepository extends JpaRepository<ChatUser, Long> {
}
