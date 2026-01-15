package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myshop.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private var currentProduct: Product? = null
    private var safeProductId: String? = null
    private var selectedAmount: Amount? = null


    private fun renderCount() {
        val id = safeProductId ?: return
        val product = currentProduct ?: return

        val realItem = AppState.cartManager.getItem(id)
        val inCart = realItem != null

        val amountToShow: Amount =
            realItem?.amount
                ?: selectedAmount
                ?: when (product.unit) {
                    ProductUnit.PIECE -> Amount.Pieces(1)
                    ProductUnit.GRAM -> Amount.Grams(1000)
                }

        binding.tvProductCount.text = when (amountToShow) {
            is Amount.Grams -> "${amountToShow.grams} g"
            is Amount.Pieces -> amountToShow.count.toString()
        }

        val itemForPrice = realItem ?: CartItem(productId = id, amount = amountToShow)
        val cents = AppState.cartManager.lineTotalCents(itemForPrice)
        binding.tvProductPrice.text = AppState.cartManager.formatCents(cents)

        if (inCart) {
            binding.bntAddToCart.text = "Added"
            binding.bntAddToCart.isEnabled = false
        } else {
            binding.bntAddToCart.text = "Add to cart"
            binding.bntAddToCart.isEnabled = true
        }

    }

    private fun increasePreview(product: Product) {
        val cur = selectedAmount ?: when (product.unit) {
            ProductUnit.PIECE -> Amount.Pieces(1)
            ProductUnit.GRAM -> Amount.Grams(1000)
        }

        selectedAmount = when (cur) {
            is Amount.Pieces -> Amount.Pieces(cur.count + 1)
            is Amount.Grams -> Amount.Grams(cur.grams + 20) // шаг как в CartManager
        }
    }

    private fun decreasePreview(product: Product) {
        val cur = selectedAmount ?: when (product.unit) {
            ProductUnit.PIECE -> Amount.Pieces(1)
            ProductUnit.GRAM -> Amount.Grams(1000)
        }

        selectedAmount = when (cur) {
            is Amount.Pieces -> Amount.Pieces(maxOf(1, cur.count - 1))
            is Amount.Grams -> Amount.Grams(maxOf(20, cur.grams - 20))
        }
    }

    private fun renderFavourite() {
        val id = safeProductId ?: return
        AppState.favouriteManager.isFavorite(productId = id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductDetailBinding.bind(view)


        val productIdArg = arguments?.getString("productId")

        val foundProduct = productIdArg?.let {
            ProductStore.findById(it)
        }

        if (foundProduct == null) {
            findNavController().popBackStack()
            return
        }


        currentProduct = foundProduct
        safeProductId = foundProduct.id
        renderCount()



        binding.tvProductTitle.text = foundProduct.title
        binding.tvProductWeight.text = foundProduct.weight
        binding.tvProductPrice.text = foundProduct.price
        binding.tvProductDescription.text = foundProduct.productDescription
        binding.ivPicture.setImageResource(foundProduct.imageRes)
        renderCount()


        binding.bntAddToCart.setOnClickListener {
            val id = safeProductId ?: return@setOnClickListener
            val product = currentProduct ?: return@setOnClickListener

            val a = selectedAmount ?: when (product.unit) {
                ProductUnit.PIECE -> Amount.Pieces(1)
                ProductUnit.GRAM -> Amount.Grams(1000)
            }

            when (a) {
                is Amount.Pieces -> AppState.cartManager.setAmount(id, a.count)
                is Amount.Grams -> AppState.cartManager.setAmount(id, a.grams)
            }

            selectedAmount = null // сбрасываем превью, потому что теперь источник правды — корзина
            renderCount()
        }



        binding.btnProductIncrease.setOnClickListener {
            val id = safeProductId ?: return@setOnClickListener
            val product = currentProduct ?: return@setOnClickListener

            if (AppState.cartManager.getItem(id) != null) {
                AppState.cartManager.increase(id)
            } else {
                increasePreview(product)
            }
            renderCount()
        }

        binding.btnProductDecrease.setOnClickListener {
            val id = safeProductId ?: return@setOnClickListener
            val product = currentProduct ?: return@setOnClickListener

            if (AppState.cartManager.getItem(id) != null) {
                AppState.cartManager.decrease(id)
            } else {
                decreasePreview(product)
            }
            renderCount()
        }


        binding.btnBackToFirstFragment.setOnClickListener {
            findNavController().popBackStack()
        }

        var isDescriptionExpanded = false

        binding.btnToggleDescription.setOnClickListener {
            isDescriptionExpanded = !isDescriptionExpanded

            if (isDescriptionExpanded) {
                binding.tvProductDescription.visibility = View.VISIBLE
                binding.btnToggleDescription.rotation = 90f
            } else {
                binding.tvProductDescription.visibility = View.GONE
                binding.btnToggleDescription.rotation = 0f
            }
        }

        binding.btnAddToFavorite.setOnClickListener {
            val id = safeProductId ?: return@setOnClickListener
            AppState.favouriteManager.toggle(id)
            renderFavourite()
        }


    }

    override fun onResume() {
        super.onResume()
        renderCount()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

