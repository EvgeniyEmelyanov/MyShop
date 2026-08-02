package com.example.myshop.data.product.datasource

import com.example.myshop.domain.product.model.Category
import com.example.myshop.data.product.model.Product
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.data.product.model.ProductUnit

object ProductStore {


    val allProducts = listOf(

        Product(
            id = "001_red_apple",
            title = "Red Apple",
            weight = "1kg, Price/kg",
            price = "$4.99",
            imageKey = "apple_picture",
            productDescription = "Crisp and sweet red apples — great for snacks, oatmeal, and fresh salads. A simple source of fiber and natural energy.",
            tags = setOf(
                ProductTag.EXCLUSIVE_OFFER,
                ProductTag.BEST_SELLING,
                ProductTag.GROCERIES_PRODUCT
            ),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "002_organic_bananas",
            title = "Organic Bananas",
            weight = "7pcs, Price/ea",
            price = "$3.49",
            imageKey = "banana_picture",
            productDescription = "Organic bananas with a soft, creamy texture. Perfect for smoothies, breakfast bowls, or a quick pre-workout snack.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "003_bell_pepper_red",
            title = "Bell Pepper Red",
            weight = "1kg, Price/kg",
            price = "$2.99",
            imageKey = "pepper_picture",
            productDescription = "Juicy red bell pepper with a bright, sweet taste. Adds crunch to salads and works great in stir-fries.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "004_strawberries",
            title = "Strawberries",
            weight = "500g, Price/pack",
            price = "$5.49",
            imageKey = "apple_picture",
            productDescription = "Fresh strawberries with a rich aroma and balanced sweetness. Ideal for desserts, yogurt, and fruit bowls.",
            tags = setOf(
                ProductTag.EXCLUSIVE_OFFER,
                ProductTag.BEST_SELLING,
                ProductTag.GROCERIES_PRODUCT
            ),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "005_blueberries",
            title = "Blueberries",
            weight = "250g, Price/pack",
            price = "$3.99",
            imageKey = "apple_picture",
            productDescription = "Sweet blueberries — a classic topping for oatmeal and yogurt. Great for baking and light snacking.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "006_grapes_seedless",
            title = "Grapes Seedless",
            weight = "1kg, Price/kg",
            price = "$4.29",
            imageKey = "apple_picture",
            productDescription = "Seedless grapes that are crisp and refreshing. Easy snack, perfect for fruit salads and cheese boards.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.BEST_SELLING),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "007_mandarins",
            title = "Mandarins",
            weight = "1kg, Price/kg",
            price = "$2.79",
            imageKey = "apple_picture",
            productDescription = "Easy-to-peel mandarins with a bright citrus flavor. Great for kids and everyday snacking.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "008_avocado_hass",
            title = "Avocado Hass",
            weight = "2pcs, Price/ea",
            price = "$3.59",
            imageKey = "apple_picture",
            productDescription = "Creamy Hass avocado with a buttery texture. Perfect for toast, salads, and homemade guacamole.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "009_pineapple",
            title = "Pineapple",
            weight = "1pc, Price/ea",
            price = "$2.99",
            imageKey = "apple_picture",
            productDescription = "Sweet and tangy pineapple with a tropical aroma. Great fresh, in smoothies, or grilled.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "010_kiwi",
            title = "Kiwi",
            weight = "6pcs, Price/ea",
            price = "$3.19",
            imageKey = "apple_picture",
            productDescription = "Juicy kiwi with a sweet-tart taste. A fresh way to upgrade fruit bowls and breakfasts.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "011_mango",
            title = "Mango",
            weight = "1pc, Price/ea",
            price = "$2.49",
            imageKey = "apple_picture",
            productDescription = "Ripe mango with smooth tropical sweetness. Perfect for smoothies, desserts, and fruit salads.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.BEST_SELLING),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "012_watermelon",
            title = "Watermelon",
            weight = "1pc, Price/ea",
            price = "$6.99",
            imageKey = "apple_picture",
            productDescription = "Refreshing watermelon with a clean, sweet finish. Ideal for hot days and sharing.",
            tags = setOf(
                ProductTag.EXCLUSIVE_OFFER,
                ProductTag.BEST_SELLING,
                ProductTag.GROCERIES_PRODUCT
            ),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "013_tomatoes_cherry",
            title = "Tomatoes Cherry",
            weight = "400g, Price/pack",
            price = "$2.69",
            imageKey = "apple_picture",
            productDescription = "Sweet cherry tomatoes — juicy, bite-sized, and perfect for salads and quick snacks.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "014_cucumber",
            title = "Cucumber",
            weight = "1pc, Price/ea",
            price = "$0.99",
            imageKey = "apple_picture",
            productDescription = "Fresh cucumber with a crisp, clean taste. Great in salads, sandwiches, and light meals.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "015_carrots",
            title = "Carrots",
            weight = "1kg, Price/kg",
            price = "$1.39",
            imageKey = "apple_picture",
            productDescription = "Crunchy carrots — perfect for soups, roasting, and healthy snacking.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "016_broccoli",
            title = "Broccoli",
            weight = "1pc, Price/ea",
            price = "$1.79",
            imageKey = "apple_picture",
            productDescription = "Tender broccoli with a mild flavor. Great steamed, roasted, or in stir-fries.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "017_potatoes",
            title = "Potatoes",
            weight = "2kg, Price/bag",
            price = "$2.49",
            imageKey = "apple_picture",
            productDescription = "Versatile potatoes for mashing, roasting, and baking. A staple for hearty meals.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        ),
        Product(
            id = "018_onions",
            title = "Onions",
            weight = "1kg, Price/kg",
            price = "$1.09",
            imageKey = "apple_picture",
            productDescription = "Everyday onions that add sweetness and depth to cooked dishes. Essential for most recipes.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.ORCHARD_LANE
        ),

        Product(
            id = "019_milk_2percent",
            title = "Milk 2%",
            weight = "1L, Price/ea",
            price = "$1.29",
            imageKey = "apple_picture",
            productDescription = "Smooth 2% milk for coffee, cereal, and cooking. Balanced taste for everyday use.",
            tags = setOf(
                ProductTag.EXCLUSIVE_OFFER,
                ProductTag.BEST_SELLING,
                ProductTag.GROCERIES_PRODUCT
            ),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "020_greek_yogurt",
            title = "Greek Yogurt",
            weight = "500g, Price/pack",
            price = "$2.99",
            imageKey = "apple_picture",
            productDescription = "Thick Greek yogurt with a clean, creamy taste. Great for breakfast and high-protein snacks.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT, ProductTag.EXCLUSIVE_OFFER),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "021_cheddar_cheese",
            title = "Cheddar Cheese",
            weight = "200g, Price/pack",
            price = "$3.49",
            imageKey = "apple_picture",
            productDescription = "Rich cheddar with a balanced savory flavor. Perfect for sandwiches, pasta, and melting.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "022_eggs_free_range",
            title = "Eggs Free-Range",
            weight = "10pcs, Price/box",
            price = "$2.79",
            imageKey = "apple_picture",
            productDescription = "Free-range eggs for breakfast and baking.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.SUNNY_HEN
        ),
        Product(
            id = "023_butter_unsalted",
            title = "Butter Unsalted",
            weight = "200g, Price/pack",
            price = "$2.39",
            imageKey = "apple_picture",
            productDescription = "Creamy unsalted butter for cooking and toast.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "024_sour_cream",
            title = "Sour Cream",
            weight = "400g, Price/pack",
            price = "$1.89",
            imageKey = "apple_picture",
            productDescription = "Classic sour cream for sauces and meals.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "025_cottage_cheese",
            title = "Cottage Cheese",
            weight = "300g, Price/pack",
            price = "$2.09",
            imageKey = "apple_picture",
            productDescription = "High-protein cottage cheese, great with fruits.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),
        Product(
            id = "026_mozzarella",
            title = "Mozzarella",
            weight = "125g, Price/pack",
            price = "$2.59",
            imageKey = "apple_picture",
            productDescription = "Soft mozzarella for salads and pizza.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.DAIRY_EGGS,
            brand = Brand.MEADOW_DAIRY
        ),

        Product(
            id = "101_water_still",
            title = "Water Still",
            weight = "1.5L, Price/ea",
            price = "$0.79",
            imageKey = "apple_picture",
            productDescription = "Still drinking water, refreshing and clean.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.CLEARSPRING
        ),
        Product(
            id = "102_water_sparkling",
            title = "Water Sparkling",
            weight = "1.5L, Price/ea",
            price = "$0.89",
            imageKey = "apple_picture",
            productDescription = "Sparkling water with light bubbles.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.CLEARSPRING
        ),
        Product(
            id = "103_orange_juice",
            title = "Orange Juice",
            weight = "1L, Price/ea",
            price = "$2.49",
            imageKey = "apple_picture",
            productDescription = "Classic orange juice for breakfast.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "104_apple_juice",
            title = "Apple Juice",
            weight = "1L, Price/ea",
            price = "$2.29",
            imageKey = "apple_picture",
            productDescription = "Sweet apple juice, perfect chilled.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.ORCHARD_LANE
        ),
        Product(
            id = "105_cola_soda",
            title = "Cola Soda",
            weight = "2L, Price/ea",
            price = "$1.59",
            imageKey = "apple_picture",
            productDescription = "Classic cola soda drink.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.CLEARSPRING
        ),
        Product(
            id = "106_lemonade",
            title = "Lemonade",
            weight = "1.5L, Price/ea",
            price = "$1.29",
            imageKey = "apple_picture",
            productDescription = "Fresh lemonade taste, lightly sweet.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.CLEARSPRING
        ),
        Product(
            id = "107_iced_tea",
            title = "Iced Tea",
            weight = "1.5L, Price/ea",
            price = "$1.49",
            imageKey = "apple_picture",
            productDescription = "Iced tea with a smooth taste.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.CLEARSPRING
        ),
        Product(
            id = "108_coffee_ground",
            title = "Coffee Ground",
            weight = "250g, Price/pack",
            price = "$3.99",
            imageKey = "apple_picture",
            productDescription = "Ground coffee for moka pot or filter.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BEVERAGES,
            brand = Brand.NORTH_ROAST
        ),

        Product(
            id = "201_olive_oil",
            title = "Olive Oil",
            weight = "500ml, Price/bottle",
            price = "$5.49",
            imageKey = "apple_picture",
            productDescription = "Extra virgin olive oil for salads and cooking.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.GOLDEN_HARVEST
        ),
        Product(
            id = "202_sunflower_oil",
            title = "Sunflower Oil",
            weight = "1L, Price/bottle",
            price = "$2.19",
            imageKey = "apple_picture",
            productDescription = "Neutral sunflower oil, great for frying.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.PUREPRESS
        ),
        Product(
            id = "203_canola_oil",
            title = "Canola Oil",
            weight = "1L, Price/bottle",
            price = "$2.39",
            imageKey = "apple_picture",
            productDescription = "Light canola oil for everyday cooking.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.GOLDEN_HARVEST
        ),
        Product(
            id = "204_coconut_oil",
            title = "Coconut Oil",
            weight = "400ml, Price/jar",
            price = "$4.59",
            imageKey = "apple_picture",
            productDescription = "Coconut oil for baking and cooking.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.PUREPRESS
        ),
        Product(
            id = "205_ghee",
            title = "Ghee",
            weight = "300g, Price/jar",
            price = "$4.99",
            imageKey = "apple_picture",
            productDescription = "Clarified butter (ghee) with rich flavor.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.GOLDEN_HARVEST
        ),
        Product(
            id = "206_sesame_oil",
            title = "Sesame Oil",
            weight = "250ml, Price/bottle",
            price = "$3.49",
            imageKey = "apple_picture",
            productDescription = "Aromatic sesame oil for Asian dishes.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.PUREPRESS
        ),
        Product(
            id = "207_butter_ghee_blend",
            title = "Butter & Ghee Blend",
            weight = "250g, Price/pack",
            price = "$3.29",
            imageKey = "apple_picture",
            productDescription = "Blend for quick cooking and toast.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.GOLDEN_HARVEST
        ),
        Product(
            id = "208_cooking_spray",
            title = "Cooking Spray",
            weight = "200ml, Price/can",
            price = "$2.99",
            imageKey = "apple_picture",
            productDescription = "Non-stick cooking spray for pans and baking.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.OIL_GHEE,
            brand = Brand.PUREPRESS
        ),

        Product(
            id = "301_chicken_breast",
            title = "Chicken Breast",
            weight = "1kg, Price/kg",
            price = "$6.49",
            imageKey = "apple_picture",
            productDescription = "Lean chicken breast, great for grilling.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.MEAT_FISH,
            brand = Brand.FARMSTEAD
        ),
        Product(
            id = "302_chicken_thighs",
            title = "Chicken Thighs",
            weight = "1kg, Price/kg",
            price = "$5.79",
            imageKey = "apple_picture",
            productDescription = "Juicy chicken thighs for roasting.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.MEAT_FISH,
            brand = Brand.FARMSTEAD
        ),
        Product(
            id = "303_beef_minced",
            title = "Beef Minced",
            weight = "500g, Price/pack",
            price = "$4.99",
            imageKey = "apple_picture",
            productDescription = "Minced beef for burgers and pasta.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.MEAT_FISH,
            brand = Brand.FARMSTEAD
        ),
        Product(
            id = "304_pork_chops",
            title = "Pork Chops",
            weight = "1kg, Price/kg",
            price = "$6.19",
            imageKey = "apple_picture",
            productDescription = "Pork chops for pan-fry or grill.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.GRAM,
            category = Category.MEAT_FISH,
            brand = Brand.FARMSTEAD
        ),
        Product(
            id = "305_salmon_fillet",
            title = "Salmon Fillet",
            weight = "400g, Price/pack",
            price = "$8.99",
            imageKey = "apple_picture",
            productDescription = "Salmon fillet for baking and salads.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.MEAT_FISH,
            brand = Brand.HARBOR_CATCH
        ),
        Product(
            id = "306_tuna_steak",
            title = "Tuna Steak",
            weight = "300g, Price/pack",
            price = "$7.49",
            imageKey = "apple_picture",
            productDescription = "Tuna steak, quick to sear.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.MEAT_FISH,
            brand = Brand.HARBOR_CATCH
        ),
        Product(
            id = "307_shrimp",
            title = "Shrimp",
            weight = "500g, Price/pack",
            price = "$6.99",
            imageKey = "apple_picture",
            productDescription = "Shrimp for pasta and stir-fry.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.MEAT_FISH,
            brand = Brand.HARBOR_CATCH
        ),
        Product(
            id = "308_sausages",
            title = "Sausages",
            weight = "500g, Price/pack",
            price = "$4.29",
            imageKey = "apple_picture",
            productDescription = "Classic sausages for breakfast or grill.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.MEAT_FISH,
            brand = Brand.FARMSTEAD
        ),

        Product(
            id = "401_white_bread",
            title = "White Bread",
            weight = "500g, Price/loaf",
            price = "$1.29",
            imageKey = "apple_picture",
            productDescription = "Soft white bread loaf.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.BAKER_STREET
        ),
        Product(
            id = "402_wholegrain_bread",
            title = "Wholegrain Bread",
            weight = "500g, Price/loaf",
            price = "$1.49",
            imageKey = "apple_picture",
            productDescription = "Wholegrain bread for healthy sandwiches.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.BAKER_STREET
        ),
        Product(
            id = "403_croissant",
            title = "Croissant",
            weight = "4pcs, Price/pack",
            price = "$2.39",
            imageKey = "apple_picture",
            productDescription = "Buttery croissants, great with coffee.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.BAKER_STREET
        ),
        Product(
            id = "404_muffins",
            title = "Muffins",
            weight = "6pcs, Price/pack",
            price = "$2.99",
            imageKey = "apple_picture",
            productDescription = "Soft muffins for snacks and breakfast.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.BAKER_STREET
        ),
        Product(
            id = "405_potato_chips",
            title = "Potato Chips",
            weight = "150g, Price/pack",
            price = "$1.79",
            imageKey = "apple_picture",
            productDescription = "Crunchy potato chips.",
            tags = setOf(ProductTag.BEST_SELLING, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.CRUNCH_CLUB
        ),
        Product(
            id = "406_crackers",
            title = "Crackers",
            weight = "200g, Price/pack",
            price = "$1.59",
            imageKey = "apple_picture",
            productDescription = "Salty crackers for cheese and dips.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.CRUNCH_CLUB
        ),
        Product(
            id = "407_granola_bars",
            title = "Granola Bars",
            weight = "6pcs, Price/box",
            price = "$2.49",
            imageKey = "apple_picture",
            productDescription = "Granola bars for quick energy.",
            tags = setOf(ProductTag.EXCLUSIVE_OFFER, ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.CRUNCH_CLUB
        ),
        Product(
            id = "408_cookies",
            title = "Cookies",
            weight = "300g, Price/pack",
            price = "$2.19",
            imageKey = "apple_picture",
            productDescription = "Classic cookies for tea time.",
            tags = setOf(ProductTag.GROCERIES_PRODUCT),
            unit = ProductUnit.PIECE,
            category = Category.BAKERY_SNACKS,
            brand = Brand.CRUNCH_CLUB
        )
    )



    fun findById(id: String): Product? {
        return allProducts.find { it.id == id }
    }

}
