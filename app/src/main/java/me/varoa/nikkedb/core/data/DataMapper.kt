package me.varoa.nikkedb.core.data

import me.varoa.nikkedb.core.data.local.entity.FavoriteEntity
import me.varoa.nikkedb.core.data.remote.json.NikkeJson
import me.varoa.nikkedb.core.domain.model.Nikke

fun NikkeJson.toModel() =
    Nikke(
        id = id,
        name = name,
        rarity = rarity,
        classType = classType,
        weapon = weapon,
        burst = burst,
        manufacturer = manufacturer,
        squad = squad,
        description = description
    )

fun Nikke.toEntity() =
    FavoriteEntity(
        id = id,
        name = name
    )

fun FavoriteEntity.toModel() =
    Nikke(
        id = id,
        name = name,
        rarity = "",
        classType = "",
        weapon = "",
        burst = "",
        manufacturer = "",
        squad = "",
        description = ""
    )
