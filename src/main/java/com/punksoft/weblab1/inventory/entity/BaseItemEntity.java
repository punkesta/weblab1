package com.punksoft.weblab1.inventory.entity;

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
@Table(name = "BASE_ITEMS")
public class BaseItemEntity {
    @Id
    @Size(max = 64)
    @Column(name = "ID", nullable = false, length = 64)
    private String id;

    @NotNull
    @Column(name = "TYPE_ID", nullable = false)
    private String typeId;

    @Size(max = 64)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 64)
    private String name;

    @Column(name = "WEAPON_ID")
    private String weaponId;

    @NotNull
    @Column(name = "RARITY_ID", nullable = false)
    private String rarityId;

    @NotNull
    @Column(name = "COLLECTION_ID", nullable = false)
    private String collectionId;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Size(max = 64)
    @Column(name = "ICON_PATH", length = 64)
    private String iconPath;
}