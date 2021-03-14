package dev.samuelmcmurray.ui.discoveries

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import dev.samuelmcmurray.R
import dev.samuelmcmurray.data.model.Post
import dev.samuelmcmurray.data.singelton.CurrentUserSingleton
import dev.samuelmcmurray.databinding.DiscoveriesFragmentBinding
import dev.samuelmcmurray.ui.post.PostAdapter


private const val TAG = "DiscoveriesFragment"

class DiscoveriesFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoveriesFragment()
    }

    private lateinit var binding: DiscoveriesFragmentBinding
    private lateinit var viewModel: DiscoveriesViewModel
    private lateinit var fragment: View
    private lateinit var floatingActionButton: FloatingActionButton
    lateinit var postTextView : MaterialTextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.discoveries_fragment, container, false)
        binding.lifecycleOwner = this
        fragment =  binding.postFragment
        postTextView = binding.editTextPost
        floatingActionButton = binding.floatingActionButton
        fragment.visibility = View.GONE
        postTextView.visibility = View.VISIBLE
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),defaultViewModelProviderFactory).get(DiscoveriesViewModel::class.java)

        getPostsList()
        getCurrentUser()

        floatingActionButton.setOnClickListener{
            showHide()
        }

        postTextView.setOnClickListener {
            showHide()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentUser() {
        if (CurrentUserSingleton.getInstance.loggedIn || CurrentUserSingleton.getInstance.currentUser == null) {
            viewModel.getCurrentUser()
            viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
                val currentUser = it
                if (currentUser != null) {
                    Log.d(TAG, "currentUser success: ")
                } else {
                    Log.d(TAG, "getCurrentUser: failure")
                }
            })
        }
    }

    private fun showHide(){
        postTextView.visibility = View.GONE
        fragment.visibility = View.VISIBLE
        viewModel.hideNewPostFragment(false)
        viewModel.hideBoolean.observe(viewLifecycleOwner, Observer {
            if (it) {
                postTextView.visibility = View.VISIBLE
                fragment.visibility = View.GONE
                floatingActionButton.show()
            } else {
                postTextView.visibility = View.GONE
                fragment.visibility = View.VISIBLE
                floatingActionButton.hide()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPostsList() {
        viewModel.getPostsList()
        viewModel.postsListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                list
                val recyclerview = binding.root.findViewById<RecyclerView>(R.id.recycler_view_discoveries)
                recyclerview.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = PostAdapter(list, requireContext())
                }
            }
        })

    }

}