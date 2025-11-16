package com.noname.weblabs.inventory.entity;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INVENTORY")
public class InventoryItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "OWNER_ID", nullable = false)
    private Integer ownerId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "BASE_ITEM_ID", nullable = false)
    private BaseItemEntity baseItem;

    @Builder.Default
    @NotNull
    @ColumnDefault("FALSE")
    @Column(name = "IS_NEW", nullable = false)
    private Boolean isNew = false;

    @Builder.Default
    @NotNull
    @ColumnDefault("FALSE")
    @Column(name = "IS_STAT_TRACK", nullable = false)
    private Boolean isStatTrack = false;

    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ColumnDefault("FALSE")
    @Column(name = "IS_EQUIPPED_CT")
    private Boolean isEquippedCt;

    @ColumnDefault("FALSE")
    @Column(name = "IS_EQUIPPED_T")
    private Boolean isEquippedT;
}