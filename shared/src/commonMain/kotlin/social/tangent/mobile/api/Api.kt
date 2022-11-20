package social.tangent.mobile.api

import de.jensklingenberg.ktorfit.http.*
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Instance
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.entities.Token

interface Api {

    @GET("api/v1/instance")
    suspend fun getInstance(
        @Header("domain") domain: String? = null
    ): Instance

    @FormUrlEncoded
    @POST("api/v1/apps")
    suspend fun authenticateApp(
        @Header("domain") domain: String,
        @Field("client_name") clientName: String,
        @Field("redirect_uris", encoded = true) redirectUris: String,
        @Field("scopes") scopes: String? = null,
        @Field("website") website: String? = null
    ): Application

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun fetchOAuthToken(
        @Header("domain") domain: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String,
        @Field("code") code: String? = null
    ): Token

    @GET("api/v1/timelines/public")
    suspend fun getPublicTimeline(
        @Header("Authorization") token: String,
        @Query("local") local: Boolean? = null,
        @Query("max_id") maxId: String? = null,
        @Query("since_id") sinceId: String? = null,
        @Query("limit") limit: Int? = null
    ): List<Status>
}