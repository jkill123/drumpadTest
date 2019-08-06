package com.repos;

import com.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message,Integer> {
    List<Message> findAll();
    ArrayList<Message> findAllById(int id);
}
