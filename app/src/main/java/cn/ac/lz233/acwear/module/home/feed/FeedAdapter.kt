package cn.ac.lz233.acwear.module.home.feed

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import cn.ac.lz233.acwear.AcWearApp
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.util.getTime
import cn.ac.lz233.acwear.util.safePadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(private val feedList: List<Feed>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = feedList[position]
        if (position == 0) {
            holder.itemView.feedTitleTextView?.apply {
                visibility = View.VISIBLE
                setPadding(0, safePadding, 0, resources.getDimensionPixelSize(R.dimen.margin_special2))
            }
        } else {
            holder.itemView.feedTitleTextView?.visibility = View.GONE
        }
        Glide.with(AcWearApp.context).load(feed.avatarUrl).placeholder(R.drawable.ic_account).error(R.drawable.ic_account).apply(RequestOptions.bitmapTransform(CircleCrop())).into(holder.itemView.feedAvatarImageView)
        holder.itemView.feedUserNameTextView.text = feed.userName
        holder.itemView.userSignTextView.text = feed.userSign
        //holder.itemView.feedTimeTextView.text = position.toString()
        holder.itemView.feedVideoTitleTextView.text = feed.videoTitle
        Glide.with(AcWearApp.context).load(feed.videoImageUrl)
                //.preload(480,270)
                //.placeholder(R.drawable.testit)
                //.error(R.drawable.ic_account)
                //.apply(RequestOptions.bitmapTransform(RoundedCorners(dp2px(AcWearApp.context,5.0f))))
                .into(holder.itemView.feedVideoCoverImageView)
    }

    override fun getItemCount(): Int = feedList.size


}