package ru.vladroid.projs.mems.screens

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.domain.Mem
import ru.vladroid.projs.mems.utils.DateUtils

class MemDetailsActivity : AppCompatActivity() {
    private lateinit var closeButton: ImageView
    private lateinit var shareButton: ImageView
    private lateinit var userPhoto: CircularImageView
    private lateinit var username: TextView
    private lateinit var memTitle: TextView
    private lateinit var memImage: ImageView
    private lateinit var memDate: TextView
    private lateinit var memDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mem_details)

        initViews()
        configureButtons()
        fillViews()
    }

    private fun initViews() {
        closeButton = findViewById(R.id.mem_close_button)
        shareButton = findViewById(R.id.mem_share_button)
        userPhoto = findViewById(R.id.user_photo)
        username = findViewById(R.id.username)
        memTitle = findViewById(R.id.mem_title)
        memImage = findViewById(R.id.mem_image)
        memDate = findViewById(R.id.mem_date)
        memDesc = findViewById(R.id.mem_desc)
    }

    private fun configureButtons() {
        closeButton.setOnClickListener {
            finish()
        }
    }

    private fun fillViews() {
        val mem = intent.getParcelableExtra<Mem>(MEM_KEY)!!
        memTitle.text = mem.title
        memDesc.text = HtmlCompat.fromHtml(mem.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        Glide.with(this)
            .load(mem.photoUrl)
            .placeholder(R.drawable.mem_placeholder)
            .into(memImage)
        memDate.text = DateUtils.parseDateTimeStamp(mem.createdDate, resources)
    }

    companion object {
        val MEM_KEY = "mems_mem_detail_activity_key"
    }
}
