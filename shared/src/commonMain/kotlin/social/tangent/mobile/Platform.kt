package social.tangent.mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform