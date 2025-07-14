package com.prm.productsale.repository;

import com.prm.productsale.entity.ChatMessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessEntity,Integer> {

    List<ChatMessEntity> findAllByOrderBySentDateAsc();
}
