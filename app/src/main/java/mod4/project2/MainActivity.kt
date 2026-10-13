package mod4.project2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import mod4.project2.databinding.ActivityMainBinding
import mod4.project2.network.ApiClient
import mod4.project2.network.models.ChainRequest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProcess.setOnClickListener {
            val input = binding.etInput.text.toString().trim()
            if (input.isNotEmpty()) {
                binding.tilInput.error = null
                runSequentialChain(input)
            } else {
                binding.tilInput.error = getString(R.string.error_empty_input)
            }
        }
    }

    private fun runSequentialChain(input: String) {
        // Reset UI State
        binding.tvError.visibility = View.GONE
        binding.cardOutput.visibility = View.GONE
        binding.loadingContainer.visibility = View.VISIBLE
        binding.btnProcess.isEnabled = false
        binding.tilInput.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.runChain(ChainRequest(input))

                // Populate Result
                val resultText = """
                    {
                      "step_1_output": "${response.step1Output}",
                      "step_2_output": "${response.step2Output}",
                      "final_result": "${response.finalResult}"
                    }
                """.trimIndent()

                binding.tvResult.text = resultText
                binding.tvStatus.text = "Status: ${response.status}"

                // Show Output Card
                binding.cardOutput.visibility = View.VISIBLE

            } catch (e: Exception) {
                binding.tvError.text = "${getString(R.string.error_network)}: ${e.localizedMessage}"
                binding.tvError.visibility = View.VISIBLE
            } finally {
                // Restore UI State
                binding.loadingContainer.visibility = View.GONE
                binding.btnProcess.isEnabled = true
                binding.tilInput.isEnabled = true
            }
        }
    }
}