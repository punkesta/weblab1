package com.punksoft.weblab1.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Integer> {
    List<InventoryItemEntity> findByOwnerId(int ownerId);

    void removeById(int id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO inventory(owner_id, base_item_id) VALUES(:ownerId, :baseItemId)", nativeQuery = true)
    void addItem(int ownerId, String baseItemId);
}