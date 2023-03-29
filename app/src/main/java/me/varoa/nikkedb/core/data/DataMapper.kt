package me.varoa.nikkedb.core.data

import me.varoa.nikkedb.core.data.local.entity.FavoriteEntity
import me.varoa.nikkedb.core.data.remote.json.NikkeJson
import me.varoa.nikkedb.core.domain.model.Nikke

fun NikkeJson.toModel() =
    Nikke(
        id = id.padStart(3, '0'),
        name = name,
        url = url,
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
        name = name,
        url = url
    )

fun FavoriteEntity.toModel() =
    Nikke(
        id = id,
        name = name,
        url = url,
        rarity = "",
        classType = "",
        weapon = "",
        burst = "",
        manufacturer = "",
        squad = "",
        description = ""
    )

fun dummyNikke() =
    Nikke(
        id = "222",
        name = "Scarlet",
        url = "scarlet",
        rarity = "SSR",
        classType = "Attacker",
        weapon = "AR",
        burst = "3",
        manufacturer = "Pilgrim",
        squad = "Pioneer",
        description = "A wandering swordswoman from Pioneer who's partial to a good drink. Despite the common perception of melee weapons being ineffective in combat, she chooses to wield a sword."
    )
