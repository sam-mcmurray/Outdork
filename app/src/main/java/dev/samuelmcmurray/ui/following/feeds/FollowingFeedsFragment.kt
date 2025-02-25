package dev.samuelmcmurray.ui.following.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.share.widget.ShareDialog
import dev.samuelmcmurray.R
import dev.samuelmcmurray.databinding.FragmentFollowingBinding
import dev.samuelmcmurray.ui.post.PostLocal
import dev.samuelmcmurray.utilities.InjectorUtils


class FollowingFeedsFragment : Fragment() {

    private val posts = listOf(
        PostLocal(
            0,
            "45444f4f",
            R.drawable.hiker_pp1,
            R.drawable.hike_image1,
            false,
            "Mr Darcy",
            "21/20/11",
            "Great hike today at the high hill sides, with my great hiking partner @superhiker2324",
            "","", "dfdsf"
        ),
        PostLocal(
            0,
            "erefe22e",
            R.drawable.hiker_pp2,
            R.drawable.hike_image2,
            false,
            "superhiker2324",
            "19/55/62",
            "hello another post",
            "",
            "","dfdfsdsfw"
        ),
        PostLocal(
            0,
            "er3e3d3e",
            R.drawable.hiker_pp3,
            R.drawable.hike_image3,
            false,
            "mY dOg",
            "14/56/95",
            "another poist",
            "",
            "","dsfdfdsdfew"
        ),
        PostLocal(
            0,
            "efr3d3d33",
            R.drawable.hiker_pp4,
            R.drawable.hike_image4,
            false,
            "Superman",
            "21/15/13",
            "the last post",
            "",
            "","sdfdgrear"
        )
    )

    companion object {
        fun newInstance() = FollowingFeedsFragment()
    }

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: FollowingFeedsViewModel

    private lateinit var shareDialog: ShareDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        binding.lifecycleOwner = this

        FacebookSdk.sdkInitialize(this.requireContext())

        // used is logged in
        val loggedIn = AccessToken.getCurrentAccessToken() != null
        println(loggedIn)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = InjectorUtils.provideFollowingViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(FollowingFeedsViewModel::class.java)

        val recyclerview = binding.root.findViewById<RecyclerView>(R.id.recycler_view_following)
        recyclerview.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            //TODO fix here
            //adapter = PostAdapter(posts, requireContext())
        }
    }
    //viewModel.getAbout().observe(this, Observer {what ever we do})

}