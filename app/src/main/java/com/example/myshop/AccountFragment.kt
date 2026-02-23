package com.example.myshop

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.databinding.FragmentAccountBinding
import com.yalantis.ucrop.UCrop
import java.io.File

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountMenuAdapter: AccountMenuAdapter

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                startCrop(uri)
            }
        }

    private val cropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    binding.ivAvatar.setImageURI(resultUri)
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAccountBinding.bind(view)

        setInsetsForFragment(binding.root, 10)

        setupAccountMenu()

    }

    private fun setupAccountMenu() {
        accountMenuAdapter = AccountMenuAdapter { id ->
            when (id) {
                AccountMenuData.ORDERS -> {
                    Toast.makeText(requireContext(), R.string.order, Toast.LENGTH_SHORT)
                        .show()
                }

                AccountMenuData.MY_DETAILS -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.my_details,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AccountMenuData.DELIVERY_ADDRESS -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.delivery_address,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AccountMenuData.PAYMENT_METHODS -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.payment_methods,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AccountMenuData.PROMO_CODE -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.promo_code,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AccountMenuData.NOTIFICATIONS -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.notifications,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AccountMenuData.HELP -> {
                    Toast.makeText(requireContext(), R.string.help, Toast.LENGTH_SHORT)
                        .show()
                }

                AccountMenuData.ABOUT -> {
                    Toast.makeText(requireContext(), R.string.about, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.rvAccountMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountMenuAdapter
        }

        binding.ivAvatar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        accountMenuAdapter.submitList(AccountMenuData.defaultAccountMenuItems())

    }

    private fun createCropDestinationUri(): Uri {
        val file = File(requireContext().cacheDir, "cropped_image.jpg")
        return Uri.fromFile(file)
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationUri = createCropDestinationUri()

        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1024, 1024)

        val intent = uCrop.getIntent(requireContext())
        cropLauncher.launch(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


