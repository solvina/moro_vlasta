package com.solvina.esf.server.dao;

import com.solvina.esf.data.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 10:22 AM
 */
public interface MessageDAO extends JpaRepository<Message,Long> {
}
