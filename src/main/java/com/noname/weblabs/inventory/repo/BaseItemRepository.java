package com.noname.weblabs.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.noname.weblabs.inventory.entity.BaseItemEntity;

@Repository
public interface BaseItemRepository extends JpaRepository<BaseItemEntity, String> {
    List<BaseItemEntity> findAll();
}