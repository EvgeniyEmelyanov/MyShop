package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.databinding.FragmentCartBinding


class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter


    private fun buildCartUiList(): List<CartUiModel> {
        val cartItems: List<CartItem> = AppState.cartManager.getItems()

        return cartItems.mapNotNull { cartItem ->
            val product = ProductStore.findById(cartItem.productId) ?: return@mapNotNull null

            val quantityText = when (val a = cartItem.amount) {
                is Amount.Pieces -> "${a.count} pcs"
                is Amount.Grams -> "${a.grams} g"
            }

            val lineTotalText = when (val a = cartItem.amount) {
                is Amount.Pieces -> {
                    val cents = AppState.cartManager.lineTotalCents(cartItem)
                    AppState.cartManager.formatCents(cents)
                }

                is Amount.Grams -> {
                    val cents = AppState.cartManager.lineTotalCents(cartItem)
                    AppState.cartManager.formatCents(cents)
                }
            }

            CartUiModel(
                productId = product.id,
                title = product.title,
                imageRes = product.imageRes,
                weightText = product.weight,
                quantityText = quantityText,
                lineTotalText = lineTotalText
            )
        }
    }

    private fun renderCart() {
        // 1) список
        cartAdapter.submitList(buildCartUiList())

        // 2) общий итог
        val totalCents = AppState.cartManager.cartTotalCents()
        binding.tvCheckoutTotal.text = AppState.cartManager.formatCents(totalCents)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCartBinding.bind(view)

        cartAdapter = CartAdapter(
            items = emptyList(),
            onClickIncrease = { productId ->
                AppState.cartManager.increase(productId)
                renderCart()
            },
            onClickDecrease = { productId ->
                AppState.cartManager.decrease(productId)
                renderCart()
            }
        )

        binding.rvProductsCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CartDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.line_for_products_banner,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(25),
                        skipLast = true
                    )
                )
            }
        }
        renderCart()
    }

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
    }




