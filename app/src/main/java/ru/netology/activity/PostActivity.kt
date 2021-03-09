package ru.netology.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.databinding.ActivityPostBinding
import ru.netology.dto.Global

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            /* content */
            content.requestFocus()
            content.setText(intent.getStringExtra(Global.CONTENT))
            link.setText(intent.getStringExtra(Global.LINK))

            /* fab: ok */
            save.setOnClickListener {
                val intent = Intent()
                if (content.text.isBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val content = content.text.toString()
                    val link = link.text.toString()

                    intent.putExtra(Global.CONTENT, content)
                    intent.putExtra(Global.LINK, link)
                    setResult(Activity.RESULT_OK, intent)
                }

                finish()
            }
        }
    }
}
