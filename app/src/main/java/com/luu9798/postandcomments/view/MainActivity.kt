package com.luu9798.postandcomments.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.luu9798.postandcomments.R
import com.luu9798.postandcomments.databinding.ActivityMainBinding
import com.luu9798.postandcomments.view.adapter.UserCardAdapter
import com.luu9798.postandcomments.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: UserCardAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerViewMain.adapter = adapter

        viewModel.cards.observe(this) { cards ->
            adapter.addAll(cards)
            adapter.notifyDataSetChanged()
        }

        viewModel.fetchDataAndMapCards()
    }
}
