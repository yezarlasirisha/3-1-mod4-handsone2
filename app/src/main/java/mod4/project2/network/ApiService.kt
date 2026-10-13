package mod4.project2.network

import mod4.project2.network.models.ChainRequest
import mod4.project2.network.models.ChainResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("process_chain")
    suspend fun runChain(@Body request: ChainRequest): ChainResponse
}