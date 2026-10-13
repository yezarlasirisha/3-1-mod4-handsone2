package mod4.project2.network.models

import com.google.gson.annotations.SerializedName

data class ChainRequest(
    @SerializedName("user_input") val userInput: String
)