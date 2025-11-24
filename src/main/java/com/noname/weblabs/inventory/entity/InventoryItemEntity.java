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
@Table(name = "inventory")
public class InventoryItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "base_item_id", nullable = false)
    private BaseItemEntity baseItem;

    @Builder.Default
    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_new", nullable = false)
    private Boolean isNew = false;

    @Builder.Default
    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_stat_track", nullable = false)
    private Boolean isStatTrack = false;

    @ColumnDefault("localtimestamp")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ColumnDefault("false")
    @Column(name = "is_equipped_ct")
    private Boolean isEquippedCt;

    @ColumnDefault("false")
    @Column(name = "is_equipped_t")
    private Boolean isEquippedT;
}