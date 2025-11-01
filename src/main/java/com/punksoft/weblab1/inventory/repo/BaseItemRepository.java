package com.punksoft.weblab1.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.punksoft.weblab1.inventory.entity.BaseItemEntity;

@Repository
public interface BaseItemRepository extends JpaRepository<BaseItemEntity, String> {
    List<BaseItemEntity> findAll();
}