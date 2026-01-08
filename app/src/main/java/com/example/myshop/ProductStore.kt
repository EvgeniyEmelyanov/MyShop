package com.example.myshop

object ProductStore {


    val allProducts = listOf(

        Product(
            id = "001_red_apple",
            title = "Red Apple",
            weight = "1kg, Price/kg",
            price = "$4.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Crisp and sweet red apples — great for snacks, oatmeal, and fresh salads. A simple source of fiber and natural energy.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "002_organic_bananas",
            title = "Organic Bananas",
            weight = "7pcs, Price/ea",
            price = "$3.49",
            imageRes = R.drawable.banana_picture,
            productDescription = "Organic bananas with a soft, creamy texture. Perfect for smoothies, breakfast bowls, or a quick pre-workout snack.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "003_bell_pepper_red",
            title = "Bell Pepper Red",
            weight = "1kg, Price/kg",
            price = "$2.99",
            imageRes = R.drawable.pepper_picture,
            productDescription = "Juicy red bell pepper with a bright, sweet taste. Adds crunch to salads and works great in stir-fries.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "004_strawberries",
            title = "Strawberries",
            weight = "500g, Price/pack",
            price = "$5.49",
            imageRes = R.drawable.apple_picture,
            productDescription = "Fresh strawberries with a rich aroma and balanced sweetness. Ideal for desserts, yogurt, and fruit bowls.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.BEST_SELLING),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "005_blueberries",
            title = "Blueberries",
            weight = "250g, Price/pack",
            price = "$3.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Sweet blueberries — a classic topping for oatmeal and yogurt. Great for baking and light snacking.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "006_grapes_seedless",
            title = "Grapes Seedless",
            weight = "1kg, Price/kg",
            price = "$4.29",
            imageRes = R.drawable.apple_picture,
            productDescription = "Seedless grapes that are crisp and refreshing. Easy snack, perfect for fruit salads and cheese boards.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.BEST_SELLING),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "007_mandarins",
            title = "Mandarins",
            weight = "1kg, Price/kg",
            price = "$2.79",
            imageRes = R.drawable.apple_picture,
            productDescription = "Easy-to-peel mandarins with a bright citrus flavor. Great for kids and everyday snacking.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "008_avocado_hass",
            title = "Avocado Hass",
            weight = "2pcs, Price/ea",
            price = "$3.59",
            imageRes = R.drawable.apple_picture,
            productDescription = "Creamy Hass avocado with a buttery texture. Perfect for toast, salads, and homemade guacamole.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "009_pineapple",
            title = "Pineapple",
            weight = "1pc, Price/ea",
            price = "$2.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Sweet and tangy pineapple with a tropical aroma. Great fresh, in smoothies, or grilled.",
            tags = setOf(ProductTag.BEST_SELLING),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "010_kiwi",
            title = "Kiwi",
            weight = "6pcs, Price/ea",
            price = "$3.19",
            imageRes = R.drawable.apple_picture,
            productDescription = "Juicy kiwi with a sweet-tart taste. A fresh way to upgrade fruit bowls and breakfasts.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "011_mango",
            title = "Mango",
            weight = "1pc, Price/ea",
            price = "$2.49",
            imageRes = R.drawable.apple_picture,
            productDescription = "Ripe mango with smooth tropical sweetness. Perfect for smoothies, desserts, and fruit salads.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.BEST_SELLING),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "012_watermelon",
            title = "Watermelon",
            weight = "1pc, Price/ea",
            price = "$6.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Refreshing watermelon with a clean, sweet finish. Ideal for hot days and sharing.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.BEST_SELLING),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "013_tomatoes_cherry",
            title = "Tomatoes Cherry",
            weight = "400g, Price/pack",
            price = "$2.69",
            imageRes = R.drawable.apple_picture,
            productDescription = "Sweet cherry tomatoes — juicy, bite-sized, and perfect for salads and quick snacks.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "014_cucumber",
            title = "Cucumber",
            weight = "1pc, Price/ea",
            price = "$0.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Fresh cucumber with a crisp, clean taste. Great in salads, sandwiches, and light meals.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "015_carrots",
            title = "Carrots",
            weight = "1kg, Price/kg",
            price = "$1.39",
            imageRes = R.drawable.apple_picture,
            productDescription = "Crunchy carrots — perfect for soups, roasting, and healthy snacking.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "016_broccoli",
            title = "Broccoli",
            weight = "1pc, Price/ea",
            price = "$1.79",
            imageRes = R.drawable.apple_picture,
            productDescription = "Tender broccoli with a mild flavor. Great steamed, roasted, or in stir-fries.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "017_potatoes",
            title = "Potatoes",
            weight = "2kg, Price/bag",
            price = "$2.49",
            imageRes = R.drawable.apple_picture,
            productDescription = "Versatile potatoes for mashing, roasting, and baking. A staple for hearty meals.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "018_onions",
            title = "Onions",
            weight = "1kg, Price/kg",
            price = "$1.09",
            imageRes = R.drawable.apple_picture,
            productDescription = "Everyday onions that add sweetness and depth to cooked dishes. Essential for most recipes.",
            tags = setOf(ProductTag.BEST_SELLING),
            unit = ProductUnit.GRAM
        ),

        Product(
            id = "019_milk_2percent",
            title = "Milk 2%",
            weight = "1L, Price/ea",
            price = "$1.29",
            imageRes = R.drawable.apple_picture,
            productDescription = "Smooth 2% milk for coffee, cereal, and cooking. Balanced taste for everyday use.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "020_greek_yogurt",
            title = "Greek Yogurt",
            weight = "500g, Price/pack",
            price = "$2.99",
            imageRes = R.drawable.apple_picture,
            productDescription = "Thick Greek yogurt with a clean, creamy taste. Great for breakfast and high-protein snacks.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.EXCLUSIVE_OFFER),
            unit = ProductUnit.PIECE
        ),

        Product(
            id = "021_cheddar_cheese",
            title = "Cheddar Cheese",
            weight = "200g, Price/pack",
            price = "$3.49",
            imageRes = R.drawable.apple_picture,
            productDescription = "Rich cheddar with a balanced savory flavor. Perfect for sandwiches, pasta, and melting.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE
        )
    )


    fun findById(id: String): Product? {
        return allProducts.find { it.id == id }
    }

    fun exclusiveOffers(): List<Product> {
        return allProducts.filter { product ->
            ProductTag.EXCLUSIVE_OFFER in product.tags
        }
    }

    fun bestSelling(): List <Product> {
        return allProducts.filter {product ->
            ProductTag.BEST_SELLING in product.tags
        }
    }

    fun groceriesProduct(): List <Product> {
        return allProducts.filter {product ->
            ProductTag.GROCERIES_PRODUCT in product.tags
        }
    }

}