package com.example.myshop

object ProductStore {

    val productForExclusiveOffers = listOf(
        ProductForExclusiveOffer(
            id = "001_red_apple",
            title = "Red Apple",
            weight = "1kg, Price/kg",
            price = "$4.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "002_organic_bananas",
            title = "Organic Bananas",
            weight = "7pcs, Price/ea",
            price = "$3.49",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "003_bell_pepper_red",
            title = "Bell Pepper Red",
            weight = "1kg, Price/kg",
            price = "$2.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "004_strawberries",
            title = "Strawberries",
            weight = "500g, Price/pack",
            price = "$5.49",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "005_blueberries",
            title = "Blueberries",
            weight = "250g, Price/pack",
            price = "$3.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "006_grapes_seedless",
            title = "Grapes Seedless",
            weight = "1kg, Price/kg",
            price = "$4.29",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "007_mandarins",
            title = "Mandarins",
            weight = "1kg, Price/kg",
            price = "$2.79",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "008_avocado_hass",
            title = "Avocado Hass",
            weight = "2pcs, Price/ea",
            price = "$3.59",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "009_pineapple",
            title = "Pineapple",
            weight = "1pc, Price/ea",
            price = "$2.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "010_kiwi",
            title = "Kiwi",
            weight = "6pcs, Price/ea",
            price = "$3.19",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "011_mango",
            title = "Mango",
            weight = "1pc, Price/ea",
            price = "$2.49",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "012_watermelon",
            title = "Watermelon",
            weight = "1pc, Price/ea",
            price = "$6.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "013_tomatoes_cherry",
            title = "Tomatoes Cherry",
            weight = "400g, Price/pack",
            price = "$2.69",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "014_cucumber",
            title = "Cucumber",
            weight = "1pc, Price/ea",
            price = "$0.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "015_carrots",
            title = "Carrots",
            weight = "1kg, Price/kg",
            price = "$1.39",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "016_broccoli",
            title = "Broccoli",
            weight = "1pc, Price/ea",
            price = "$1.79",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "017_potatoes",
            title = "Potatoes",
            weight = "2kg, Price/bag",
            price = "$2.49",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "018_onions",
            title = "Onions",
            weight = "1kg, Price/kg",
            price = "$1.09",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "019_milk_2percent",
            title = "Milk 2%",
            weight = "1L, Price/ea",
            price = "$1.29",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "020_greek_yogurt",
            title = "Greek Yogurt",
            weight = "500g, Price/pack",
            price = "$2.99",
            imageRes = R.drawable.apple_picture
        ),
        ProductForExclusiveOffer(
            id = "021_cheddar_cheese",
            title = "Cheddar Cheese",
            weight = "200g, Price/pack",
            price = "$3.49",
            imageRes = R.drawable.apple_picture
        )
    )

    fun findById(id: String): ProductForExclusiveOffer? {
        return productForExclusiveOffers.find { it.id == id }
    }

}