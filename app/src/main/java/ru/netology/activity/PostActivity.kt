package ru.netology.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            /* content */
            content.requestFocus()
            content.setText(intent.getStringExtra(Intent.EXTRA_TEXT))

            /* fab: ok */
            save.setOnClickListener {
                val intent = Intent()
                if (content.text.isBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val text = content.text.toString()
                    intent.putExtra(Intent.EXTRA_TEXT, text)
                    setResult(Activity.RESULT_OK, intent)
                }

                finish()
            }
        }
    }
}
