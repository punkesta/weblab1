package com.punksoft.weblab1.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseItemRepository extends JpaRepository<BaseItemEntity, String> {
    List<BaseItemEntity> findAll();
}