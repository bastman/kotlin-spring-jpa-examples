package com.example.demo.api.realestate.handler.properties.links.unlink

import java.util.*

data class UnlinkPropertiesResponse(
        val linkedToPropertyIds: List<UUID>,
        val linkedByPropertyIds: List<UUID>
)