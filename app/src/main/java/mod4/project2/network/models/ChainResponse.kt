package mod4.project2.network.models

import com.google.gson.annotations.SerializedName

data class ChainResponse(
    @SerializedName("step_1_output") val step1Output: String,
    @SerializedName("step_2_output") val step2Output: String,
    @SerializedName("final_result") val finalResult: String,
    @SerializedName("status") val status: String
)