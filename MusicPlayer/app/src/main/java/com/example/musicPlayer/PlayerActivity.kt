package com.example.musicPlayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicPlayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection {
    companion object{
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying:Boolean = false
        var musicService: MusicService? = null
    }
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MusicPlayer)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
        initilizeLayout()
        binding.playPauseBtnPA.setOnClickListener{
            if(isPlaying)pauseMusic()
            else playMusic()
        }
        binding.previousBtnPA.setOnClickListener { prevNextSong(false) }
        binding.nextBtnPA.setOnClickListener { prevNextSong(true)  }
    }
    private  fun setLayout(){
        Glide.with(this)
            .load(musicListPA[songPosition].duration.toString())
            .apply(RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen).centerCrop())
            .into(binding.songImgPA)
        binding.songNamePA.text = musicListPA[songPosition].title
    }
    private fun createMediaPlayer(){
        try{
        if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
        isPlaying = true
            binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        }
        catch(e: Exception){return}
    }
    private fun initilizeLayout(){
        songPosition =intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter" ->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()
            }
            "MainActivity"->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()
            }
        }
    }
    private fun playMusic(){
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        isPlaying=true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic(){
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        isPlaying= false
        musicService!!.mediaPlayer!!.pause()
    }
    private fun prevNextSong(increment:Boolean){
        if(increment){
            setSongPosition(increment)
            setLayout()
            createMediaPlayer()
        }else{
            setSongPosition(increment)
            setLayout()
            createMediaPlayer()
        }
    }
    private fun setSongPosition(increment:Boolean){
        if(increment){
            if(musicListPA.size -1 == songPosition)
                songPosition=0
            else ++songPosition
        }else{
            if(0 == songPosition)
                songPosition= musicListPA.size-1
            else --songPosition
        }
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}