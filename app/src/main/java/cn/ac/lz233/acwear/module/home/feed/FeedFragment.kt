package cn.ac.lz233.acwear.module.home.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.ac.lz233.acwear.BaseFragment
import cn.ac.lz233.acwear.R
import cn.ac.lz233.acwear.module.network.NetworkService
import cn.ac.lz233.acwear.util.LogUtil
import cn.ac.lz233.acwear.util.isLogin
import cn.ac.lz233.acwear.util.isRound
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.heytap.wearable.support.widget.HeyMainTitleBar
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.footer.LoadingView
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout
import kotlinx.android.synthetic.main.fragment_feed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : BaseFragment() {

    private val feedList = ArrayList<Feed>()
    private val feedAdapter = FeedAdapter(feedList)
    private var feedPageCursor = 1
    private var feedPageSize = 1
    override fun onRound() {
        super.onRound()
        feedRecyclerView.setPadding(
            resources.getDimensionPixelSize(R.dimen.margin_special),
            0,
            resources.getDimensionPixelSize(R.dimen.margin_special),
            0
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (!isRound) activity?.findViewById<HeyMainTitleBar>(R.id.homeHeyMainTitleBar)
            ?.setTitle(getString(R.string.feed_title))
        if (feedList.size == 0) getFeed()
    }

    private fun initView() {
        //feedTwinklingRefreshLayout.setTargetView(feedRecyclerView)
        if (isRound) {
            feedTwinklingRefreshLayout.setHeaderView(ProgressLayout(this.context))
        } else {
            feedTwinklingRefreshLayout.setHeaderView(SinaRefreshView(this.context).apply {
                setPullDownStr(getString(R.string.pulldown_string))
                setReleaseRefreshStr(getString(R.string.release_string))
                setRefreshingStr(getString(R.string.refreshing_string))
            })
        }
        feedTwinklingRefreshLayout.setBottomView(LoadingView(this.context))
        feedTwinklingRefreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                feedPageCursor = 1
                feedPageSize = 1
                getFeed(true)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                getFeed()
            }
        })
        feedRecyclerView.layoutManager = LinearLayoutManager(this.context)
        feedRecyclerView.adapter = feedAdapter
        getFeed()
        //feedList.add(Feed("我是郭杰瑞", "https://tx-free-imgs.acfun.cn/style/image/201907/xuQbK1mypws5ebLoZQPvF4vIEIMg0iXl.jpg?imageslim", "挑战美国暴风雪中送外卖!一小时竟赚30美元？", 233, "https://imgs.aixifan.com/o_1etvqfbd7g2s8as16p16jv195k0.jpeg"))
    }

    private fun getFeed(isRefresh: Boolean = false) {
        if (isLogin()){
            if ((feedPageCursor <= feedPageSize)) {
                NetworkService.webService.getFeedList("5", feedPageCursor.toString())
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>, response: Response<JsonObject>
                        ) {
                            feedPageSize = response.body()!!.asJsonObject.get("totalPage").asInt
                            feedPageCursor++
                            if (isRefresh) feedList.clear()
                            val feeds = Gson().fromJson(
                                response.body()!!.getAsJsonArray("feedList"), Array<Feed>::class.java
                            )
                            for (i in feeds.indices) {
                                feedList.add(feeds[i])
                            }
                            feedTwinklingRefreshLayout.finishRefreshing()
                            feedTwinklingRefreshLayout.finishLoadmore()
                            feedAdapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            } else {
                LogUtil.toast(getString(R.string.no_more))
                feedTwinklingRefreshLayout.finishRefreshing()
                feedTwinklingRefreshLayout.finishLoadmore()
            }
        }else{
            feedTwinklingRefreshLayout.finishRefreshing()
            feedTwinklingRefreshLayout.finishLoadmore()
        }
    }
}