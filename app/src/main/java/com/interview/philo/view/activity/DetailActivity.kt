package com.interview.philo.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.interview.philo.R
import com.interview.philo.view.Constants
import com.interview.philo.view.item.SearchItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var sectionItem: SearchItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        sectionItem = intent.getParcelableExtra(Constants.PEOPLE_EXTRA_KEY)

        displayDetails()
    }

    private fun displayDetails() {
        tv_display_name.text = sectionItem.result.name
        tv_height.text = sectionItem.result.height
        tv_mass.text = sectionItem.result.mass
        tv_hair_color.text = sectionItem.result.hairColor
        tv_skin_color.text = sectionItem.result.skinColor
        tv_eye_color.text = sectionItem.result.eyeColor
        tv_gender.text = sectionItem.result.gender
    }
}
