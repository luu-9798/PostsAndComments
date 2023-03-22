package com.luu9798.postandcomments.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.TooltipCompat
import androidx.databinding.DataBindingUtil
import java.time.Instant
import com.luu9798.postandcomments.R
import com.luu9798.postandcomments.databinding.ActivityMainBinding
import com.luu9798.postandcomments.view.adapter.UserCardAdapter
import com.luu9798.postandcomments.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Inject
    lateinit var adapter: UserCardAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerViewMain.adapter = adapter

        viewModel.cards.observe(this) { cards ->
            binding.recyclerViewMain.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            adapter.addAll(cards)
            adapter.notifyDataSetChanged()
            sharedPreferences.edit().putLong(LAST_FETCHED_KEY, Instant.now().epochSecond).apply()
        }

        viewModel.fetchError.observe(this) { hasError ->
            binding.tvError.visibility = if (hasError) View.VISIBLE else View.GONE
        }

        viewModel.fetchDataAndMapCards(shouldFetchFromAPI())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                showInfoTooltip()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shouldFetchFromAPI(): Boolean {
        val lastFetchedTimestamp = sharedPreferences.getLong(LAST_FETCHED_KEY, 0L)
        val currentDate = Instant.now().truncatedTo(ChronoUnit.DAYS).epochSecond

        if (lastFetchedTimestamp < currentDate) {
            sharedPreferences.edit().putLong(LAST_FETCHED_KEY, Instant.now().epochSecond).apply()
            return true
        }

        return false
    }

    private fun showInfoTooltip() {
        val anchorView = findViewById<View>(R.id.action_info)
        val usersCount = viewModel.userCount
        val postsCount = viewModel.postCount
        val commentsCount = viewModel.commentCount

        val tooltipText = getString(
            R.string.tooltip_info,
            usersCount,
            postsCount,
            commentsCount
        )

        TooltipCompat.setTooltipText(anchorView, tooltipText)
    }

    companion object {
        const val PREFS_NAME = "com.luu9798.postandcomments.preferences"
        const val LAST_FETCHED_KEY = "last_fetched_timestamp"
    }
}
