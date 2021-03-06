package codeone.com.br.gympass.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import codeone.com.br.gympass.R
import codeone.com.br.gympass.adpters.PhotoAdapter
import codeone.com.br.gympass.adpters.ReviewsAdapter
import codeone.com.br.gympass.fragments.MapsFragment
import codeone.com.br.gympass.models.*
import codeone.com.br.gympass.presenters.DetailsActivityPresenter
import codeone.com.br.gympass.utils.BundlesConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.app_bar_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_reviews.*

class DetailsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DetailsActivityPresenter.ViewCallBack {

    private val presenter:DetailsActivityPresenter by lazy { DetailsActivityPresenter(this) }
    private var adapter:PhotoAdapter? = null
    private var detail:Details? = null
    private var adapterReviews:ReviewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbarDetails)

        fabDetails.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbarDetails, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        presenter.onCreate(intent.extras, this)

    }

    override fun setMapDetails(extras:Bundle) {

        var map:MapsFragment = MapsFragment()

        map?.arguments = extras

        supportFragmentManager.beginTransaction().replace(R.id.frag_layout, map).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun setDetails(details: Details) {
        detail = details
        tNameDetails.text = details.result.name
        tRatingDetails.text = "Avaliação: " + details.result.rating.toString()
        rbDetails.rating = details.result.rating.toFloat()
        rvPhotos.visibility = View.VISIBLE
        if(adapter == null && adapterReviews == null){
            if(details.result.photos != null){
               adapter = PhotoAdapter(this,details.result.photos, onClick())
            }else{
                tImgErrorPhotos.visibility = View.VISIBLE
            }
            rvPhotos.adapter = adapter
            if(details.result.reviews != null){
                adapterReviews = ReviewsAdapter(this, details.result.reviews, onClickReviews())
            }
            rvReviews.adapter = adapterReviews
        }else{
            adapter?.setList(details.result.photos)
            adapter?.notifyDataSetChanged()
            adapterReviews?.setListReviews(details.result.reviews)
            adapterReviews?.notifyDataSetChanged()
        }


    }

    private fun onClick():(Photos) -> Unit = {
        photos ->  presenter.clickedItem(photos)
    }
    private fun onClickReviews():(Reviews) -> Unit = {
         reviews ->  presenter.clickedItemReview(reviews)
    }
    override fun setUpRecycler() {
        rvPhotos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.itemAnimator = DefaultItemAnimator()
        rvReviews.layoutManager = LinearLayoutManager(this)
        rvReviews.itemAnimator = DefaultItemAnimator()
    }
    override fun setUpToolbar(title: String?) {
        supportActionBar?.title = title
    }

}
