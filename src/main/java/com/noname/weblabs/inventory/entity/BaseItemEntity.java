package com.noname.weblabs.inventory.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "base_items")
public class BaseItemEntity {
    @Id
    @Size(max = 64)
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @NotNull
    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Size(max = 64)
    @NotNull
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "weapon_id")
    private String weaponId;

    @NotNull
    @Column(name = "rarity_id", nullable = false)
    private String rarityId;

    @NotNull
    @Column(name = "collection_id", nullable = false)
    private String collectionId;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "price", nullable = false)
    private Double price;

    @Size(max = 64)
    @Column(name = "icon_path", length = 64)
    private String iconPath;
}