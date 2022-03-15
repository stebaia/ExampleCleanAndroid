package com.sbaiardi.examplecleanandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbaiardi.examplecleanandroid.adapters.PostAdapter
import com.sbaiardi.examplecleanandroid.databinding.FragmentFirstBinding
import com.sbaiardi.examplecleanandroid.entities.PostWithStatus
import com.sbaiardi.examplecleanandroid.factory.PostsViewModelFactory
import com.sbaiardi.examplecleanandroid.viewmodel.PostsViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PostListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private lateinit var postsAdapter: PostAdapter

    private val postsViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory(
            ((requireActivity().application) as App).checkedUseCase,
            ((requireActivity().application) as App).getCheckedUseCase,
            ((requireActivity().application) as App).getPostsUseCase,
            ((requireActivity().application) as App).uncheckUseCase,
            ((requireActivity().application) as App).postWithStatusMapper,
        )
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postsAdapter = PostAdapter(requireContext(), object : PostAdapter.ActionClickListener{
            override fun check(post: PostWithStatus) {
                postsViewModel.check(post)
            }

            override fun uncheck(post: PostWithStatus) {
                postsViewModel.uncheck(post)
            }
        })
        postsViewModel.getPosts("Robert C. Martin")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel.posts.observe(viewLifecycleOwner) {
            postsAdapter.submitUpdate(it)
        }

        postsViewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> LayoutUtils.crossFade(binding.pbLoading, binding.rvBooks)
                false -> LayoutUtils.crossFade(binding.rvBooks, binding.pbLoading)
            }
        }

        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = postsAdapter
        }

        postsViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        }

        //binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}