package com.websole.socket.database.mappings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingRespository extends JpaRepository<Mapping, Integer> {

}