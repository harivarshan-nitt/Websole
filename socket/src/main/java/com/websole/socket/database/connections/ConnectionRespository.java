package com.websole.socket.database.connections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRespository extends JpaRepository<Connection, Integer> {

}