package com.example.gamedealsradar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform