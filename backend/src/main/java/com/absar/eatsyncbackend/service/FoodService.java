package com.absar.eatsyncbackend.service;

import com.absar.eatsyncbackend.dto.FoodAddressDto;
import com.absar.eatsyncbackend.dto.FoodMenuItemDto;
import com.absar.eatsyncbackend.dto.FoodRestaurantDto;
import com.absar.eatsyncbackend.dto.McpToolStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    public List<FoodAddressDto> getAddresses() {
        return List.of(
                new FoodAddressDto(
                        "d38h0ct8t6r4daehtij0",
                        "Campus-25, A Block Main Gate, Prasanti Vihar, Patia, Bhubaneswar, Odisha 751024, India",
                        "******0000",
                        "College",
                        "College"
                )
        );
    }

    public List<McpToolStatusDto> getMcpStatus() {
        return List.of(
                new McpToolStatusDto(
                        "get_addresses",
                        "TESTED",
                        "Successfully tested through VS Code MCP. Used for selecting host delivery address."
                ),
                new McpToolStatusDto(
                        "search_restaurants",
                        "TESTED",
                        "Successfully tested through VS Code MCP. Real restaurant search data was fetched and mapped to FoodRestaurantDto."
                ),
                new McpToolStatusDto(
                        "get_restaurant_menu",
                        "TESTED",
                        "Successfully tested through VS Code MCP. Real menu data was fetched for multiple restaurants and mapped to FoodMenuItemDto."
                ),
                new McpToolStatusDto(
                        "search_menu",
                        "TESTED",
                        "Successfully tested through VS Code MCP. Returns cart-ready item details including menu_item_id, variants and add-ons."
                ),
                new McpToolStatusDto(
                        "update_food_cart",
                        "NOT_USED",
                        "Intentionally not used until EatSync stores selected variants and add-ons safely."
                ),
                new McpToolStatusDto(
                        "place_food_order",
                        "NOT_USED",
                        "Intentionally not used in MVP safety stage."
                )
        );
    }

    public List<FoodRestaurantDto> getRestaurants() {
        return List.of(
                new FoodRestaurantDto(
                        "1256056",
                        "Biryani Blues",
                        "Biryani, Hyderabadi",
                        "4.4",
                        "15-20 MINS",
                        "https://media-assets.swiggy.com/swiggy/image/upload/RX_THUMBNAIL/IMAGES/VENDOR/2025/11/8/51348928-7f45-45f8-98fb-a25b73bd2df3_1256056.jpg",
                        "₹500 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "548192",
                        "Bikkgane Biryani",
                        "Biryani, Hyderabadi, Andhra, Lucknowi, Kolkata, Desserts, Beverages",
                        "4.5",
                        "20-25 MINS",
                        "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/6/24/28ba8630-7acf-4c97-b653-67acbd83b535_6b97bc3b-ddf2-476d-9849-aa8b0e0e7856.jpg",
                        "₹350 for two",
                        "Chandrashekarpur"
                ),
                new FoodRestaurantDto(
                        "1319530",
                        "Shradhanjali Kitchen",
                        "Snacks, Pastas, Beverages, Indian",
                        "4.3",
                        "25-30 MINS",
                        "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/3/3/96b87f33-0630-45eb-a192-708e1f10275a_5b4d9fb5-c008-4d01-abdd-52722a219555.jpg_compressed",
                        "₹200 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "1258584",
                        "The Bowl & Co.",
                        "Biryani, Continental, Punjabi, Mughlai, Indian, Oriya",
                        "4.1",
                        "10-15 MINS",
                        "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/28/3135be86-3a71-49c5-a6b9-e0712c1f6c54_c01b92b7-8b0c-48da-a03c-6e0d07b83224.jpg",
                        "₹299 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "548185",
                        "Biryani Badshah",
                        "Biryani, Hyderabadi, Andhra, Lucknowi, Kolkata, Desserts, Beverages",
                        "4.3",
                        "25-30 MINS",
                        "https://media-assets.swiggy.com/swiggy/image/upload/9db3f9a11be35a49486a0f3aed1a5879",
                        "₹350 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "r1",
                        "Pizza Hut",
                        "Pizza, Fast Food",
                        "4.2",
                        "30-35 min",
                        "",
                        "₹400 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "r2",
                        "Burger King backend",
                        "Burgers, Beverages",
                        "4.1",
                        "25-30 min",
                        "",
                        "₹350 for two",
                        "KIIT Square"
                ),
                new FoodRestaurantDto(
                        "r3",
                        "KFC",
                        "Chicken, Fast Food",
                        "4.0",
                        "35-40 min",
                        "",
                        "₹500 for two",
                        "Patia"
                ),
                new FoodRestaurantDto(
                        "r4",
                        "La Pino'z Pizza",
                        "Pizza, Italian",
                        "4.3",
                        "30-35 min",
                        "",
                        "₹450 for two",
                        "Infocity"
                ),
                new FoodRestaurantDto(
                        "r5",
                        "Wow! Momo",
                        "Momos, Tibetan",
                        "4.4",
                        "20-25 min",
                        "",
                        "₹250 for two",
                        "Patia"
                )
        );
    }

    public List<FoodMenuItemDto> getMenuItems(String restaurantId) {
        return switch (restaurantId) {
            case "1256056" -> List.of(
                    new FoodMenuItemDto("186493456", "1256056", "Double Ka Meetha", "Fried bread soaked in sweetened milk garnished with nuts and rose petals", 119, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/6/21/974bd253-e8b2-4137-b101-0dd46115e591_294a718b-9f22-439d-8925-bb0cab0b0d37.jpg", true, true, false),
                    new FoodMenuItemDto("186493457", "1256056", "Fiery Potato 65", "Spicy tangy red potato fingers flavored with curry leaves and green chilies", 159, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/12/17/40b78c0d-2bb3-4d38-b6bd-831a0b552711_60a7fda9-1373-45f3-befa-f48bf4743d6d.jpg", true, true, true),
                    new FoodMenuItemDto("186493480", "1256056", "Hara Bhara Kebab", "Spiced patties made of spinach, peas & potatoes", 129, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/6/12/634d4c8c-570e-4ae3-a63f-965f4ee04d2e_64de41d8-6024-41fe-b468-4a943c659f78.jpg", true, true, true),
                    new FoodMenuItemDto("186493426", "1256056", "High Fiber Soya Biryani With Brown Rice [Serves 1]", "Healthy yet delicious soya chaap biryani prepared with flavorful hyderabadi spices", 309, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/6/21/e80894ed-dd35-4c3e-afd3-53cf485032b7_61a83735-b997-4336-a673-571c9441c82f.jpg", true, false, true),
                    new FoodMenuItemDto("192016466", "1256056", "Hyderabadi Veg Dum Biryani", "Hyderabadi veg dum biryani with beans, cauliflower, potatoes & carrots and served with raita.", 379, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/12/17/4425d4ea-ceb6-47fe-88d8-22f9d5212892_d5e8582c-c06e-4bf7-86b7-f169efa0cab8.jpg", true, true, true),
                    new FoodMenuItemDto("186493408", "1256056", "Veg Dum Biryani (Bowl 500 Ml) (2 Nos)", "Hyderabadi veg dum biryani. Raita & salan are chargeable.", 379, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/8/3/4212e047-b31c-4448-bc23-c2330abeeb37_f81c1236-0b5f-4b60-b220-7722c3878f3c.jpg", true, true, false),
                    new FoodMenuItemDto("186493396", "1256056", "Chicken Dum Biryani (Serves 1 (1 Leg + Egg))", "Hyderabadi chicken dum biryani with egg and served with raita.", 399, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/12/17/1cea3f39-d6e0-4e07-b6c9-bbe13173efaa_3e55b3cf-32e2-4566-8c9e-c91b4dca2f87.jpg", true, false, true),
                    new FoodMenuItemDto("186493427", "1256056", "Egg Biryani (Serves 1 (2 Eggs))", "Spicy fried boiled eggs layered with biryani rice.", 399, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/10/25/0b4033d7-aadc-4aab-b286-cefdd82ba640_ecdcb750-66bd-40ec-b075-6baabfda51c0.jpg", true, false, false),
                    new FoodMenuItemDto("186493433", "1256056", "Soya Chaap Biryani (Serves 1)", "Soya chaaps flavored in hyderabadi spices layered with biryani rice.", 449, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/8/10/79597223-8bb2-4990-ac89-f27444782431_0d4c7d3e-a6fe-4967-bcd7-72036e3c82d8.jpg", true, false, false),
                    new FoodMenuItemDto("186493421", "1256056", "Chicken Dum Boneless Biryani (Bowl 500 Ml) (2 Nos)", "Hyderabadi chicken biryani with boneless chicken pieces. Raita & salan are chargeable.", 729, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2024/6/21/5e643188-896d-475e-bc04-41bc9eb3e48d_3b19c14e-1a1f-45f9-8a92-fbb6e79355ab.jpg", true, true, true)
            );

            case "548192" -> List.of(
                    new FoodMenuItemDto("187317005", "548192", "Aloo 65 Mini (300ml)", "Mini portion of crispy Aloo 65", 149, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/21/0328cac2-54f0-4fc2-95ec-b0130d8752ac_7064f527-a427-49d2-a269-14512e4a346b.jpeg", true, false, false),
                    new FoodMenuItemDto("184995926", "548192", "(qp) Basmati Biryani Rice (400 Grams)", "Basmati biryani rice", 199, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/10/17/dac9a505-3e6f-4759-a8e6-e1b2ca8fa8e9_c16f3b9a-c926-41e6-ba6f-bd58539a50f1.jpg", true, false, true),
                    new FoodMenuItemDto("187317004", "548192", "3 Gulab Jamun", "Soft, melt-in-mouth gulab jamuns soaked in rich cardamom-flavoured sugar syrup", 99, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/21/8587b7de-9c50-4712-a1d0-94d917f04bda_739bdb60-dbf0-4316-9ea7-7432c0b69c33.jpeg", true, false, false),
                    new FoodMenuItemDto("187317006", "548192", "Biryani Rice", "Long grain Basmati Biryani Rice", 249, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/21/9b6e22f3-7b8f-4e8b-8c77-c19d4df64719_38a20062-3bfa-4e34-abbf-97fd1ea32bdb.jpeg", true, false, false),
                    new FoodMenuItemDto("187317008", "548192", "Handi Panner Biryani (1 Kg) + Aloo 65 Mini + Gulab Jamun", "Handi Special Paneer Hyderabadi Biryani combo", 1499, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/21/8cf18016-a0a9-447a-aab5-6f7bb131679b_032c83a2-c161-4441-aae9-ca1eb58aca62.jpeg", true, false, false),
                    new FoodMenuItemDto("184995922", "548192", "(qp) Veg Dum Biryani Bowl - 500ml", "Veg dum biryani bowl", 269, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/10/17/20032ccf-925f-4423-a7c8-cb21a6c243f8_2c7a6274-e7d2-430b-a4d3-13b0843c9974.jpg", true, false, true),
                    new FoodMenuItemDto("154673491", "548192", "Veg Dum Biryani Bowl - 500ml", "Serves 1 value bowl with raita", 289, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/6/24/99efcb85-d723-4742-b2e9-eadc9e41ba6e_af459b38-393e-4f07-85ac-beefc2435ec2.jpg", true, false, true),
                    new FoodMenuItemDto("184995918", "548192", "(qp) Chicken 65 Biryani Bowl - 500ml", "Chicken 65 biryani bowl", 289, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/10/17/1af69e02-6ed1-4b9c-b3a8-843fb65f7ad9_76c15219-4ae7-47e3-9424-72448fc4bcdf.jpg", true, false, true),
                    new FoodMenuItemDto("184995919", "548192", "(qp) Chicken Dum Biryani Bowl - 500ml", "Chicken dum biryani bowl", 289, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/10/17/8abb3084-e9eb-4e7e-bba9-aa2d5f8bdf6c_fe09081a-10c6-493a-9e3d-799d52a1f6c5.jpg", true, false, true),
                    new FoodMenuItemDto("184995920", "548192", "(qp) Special Chicken Boneless Biryani Bowl - 500ml", "Special chicken boneless biryani bowl", 289, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/10/17/04a07daa-65ec-4ee0-a0fb-7ace163e975f_8104e581-e8d9-4a7f-8849-96d4ec30cdc4.jpg", true, false, true)
            );

            case "1319530" -> List.of(
                    new FoodMenuItemDto("193843447", "1319530", "Prawn Hyderabadi", "Prawn Hyderabadi", 295, false, "", true, false, false),
                    new FoodMenuItemDto("193843766", "1319530", "Chicken Kassa", "Chicken Kassa", 235, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/30/d9ad0baf-dd4f-472c-a2d6-2b27636d1f94_361b59b2-ef9a-4a4c-8ed6-66394a2d56bb.JPG", true, false, false),
                    new FoodMenuItemDto("193512832", "1319530", "Chicken Fried Biryani", "Chicken Fried Biryani", 259, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/2/6/b36b64c5-c52a-4da5-b18d-5eb3726f3ed8_e5d06b8d-f5a8-4b20-9005-6360b1bad9f3.JPG", true, false, false),
                    new FoodMenuItemDto("193843609", "1319530", "Chicken Pulao", "Chicken Pulao", 219, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/3/9/d7ec2d44-90eb-41fc-bf0f-e82d5ee63b6d_e16d1087-a2ec-4e19-a680-2d49424e7718.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("193512824", "1319530", "Veg Biryani", "Veg Biryani", 195, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/3/3/96b87f33-0630-45eb-a192-708e1f10275a_5b4d9fb5-c008-4d01-abdd-52722a219555.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("193843527", "1319530", "Mushroom Masala", "Mushroom Masala", 189, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/2/12/4c06e656-9ae1-447e-9aaa-aa6b2da5db42_8f577bd5-5936-4a32-855b-98aa287c7af2.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("193843633", "1319530", "Chicken Alfredo Pasta", "Chicken Alfredo Pasta", 189, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/3/4/987e76fd-727e-477e-97bb-6f644601667b_6e38e0b1-282e-428c-bf54-c4daa518fb90.jpg", true, false, false),
                    new FoodMenuItemDto("193843458", "1319530", "Paneer Butter Masala", "Paneer Butter Masala", 197, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/2/12/667a4470-d65b-41d5-bc46-c86bf6489ad6_51515bbc-348b-4da3-a7ab-8fe5740563f3.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("193843625", "1319530", "American Crispy Corn", "American Crispy Corn", 170, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/3/5/1a89aa5d-247f-4f0d-8013-83affd3b77e8_104c34e7-2530-4e02-8d28-8117215cc5b8.jpg", true, false, false),
                    new FoodMenuItemDto("193843466", "1319530", "Paneer Matar", "Paneer Matar", 189, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/2/12/a7d6cdd6-0dec-4008-ba76-fcf69a7d2463_d905505e-a9cd-4a59-bd32-9b9991ca1868.jpg_compressed", true, false, false)
            );

            case "1258584" -> List.of(
                    new FoodMenuItemDto("186585238", "1258584", "Rajma Masala Bowl", "Tender legumes and aromatic spices served in a comforting bowl", 279, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/27/053b31be-e9b6-4127-a97c-22bbf9f5e94c_4bdfd4db-943e-4abf-a747-496d894993a7.jpg", true, false, true),
                    new FoodMenuItemDto("186585241", "1258584", "Paneer tikka masala bowl", "Paneer tikka masala bowl", 269, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/27/a9e99c24-a2c9-4508-9809-0c0ffa91d7be_e053f7db-ddfe-46e0-9cce-75e660ad4b88.jpg", true, false, false),
                    new FoodMenuItemDto("186585242", "1258584", "Paneer mughlai bowl", "Paneer mughlai bowl", 289, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/14/e739ccce-2e5a-4f3a-8434-74201a439852_32c58f58-f8c8-4db7-9c2f-98b85a3d1293.jpg", true, false, false),
                    new FoodMenuItemDto("186585239", "1258584", "Dal makhani Rice bowl", "Creamy slow-cooked Dal Makhani served with fragrant basmati rice", 279, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/27/ad265003-04f8-4017-b7e3-269fdbd69ed6_64277907-21ee-48f5-8ba0-7e1238463329.jpg", true, false, false),
                    new FoodMenuItemDto("186585240", "1258584", "Chole Rice bowl", "Punjabi-style chole served with steamed rice", 279, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/27/bf538d69-2e30-4c6b-a6c0-83fbdeb42211_78ed2749-654f-4567-ba45-d1ce4bb0b8f3.jpg", true, false, false),
                    new FoodMenuItemDto("186585256", "1258584", "Chicken biryani bowl (Bonless)", "Chicken biryani bowl", 269, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/28/3135be86-3a71-49c5-a6b9-e0712c1f6c54_c01b92b7-8b0c-48da-a03c-6e0d07b83224.jpg", true, false, false),
                    new FoodMenuItemDto("187278799", "1258584", "Biryani Chicken Bowl Bonless (750 Gms )", "Fragrant basmati rice layered with tender boneless chicken", 299, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/27/994aab13-16fb-4804-acdb-6d1df37e34f5_3d51029a-d12c-4e8f-b489-5b4cc0624d66.jpg", true, false, false),
                    new FoodMenuItemDto("187279095", "1258584", "Special Chicken Biryani Combo", "Special Chicken Biryani Combo with Coke and Meatball Starter", 409, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/20/650a703a-bc8d-43d0-8146-cbaccce3a003_31f8f0a9-55f0-4a00-a085-e1647cd385a2.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("186585258", "1258584", "Egg biryani bowl biryani bowl", "Egg biryani bowl", 239, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2026/1/25/98c8d9fe-a59b-4abc-b6f4-caf426d42c24_ed325ce7-7fe1-40c9-8349-77de7fb3ea1c.jpg", true, false, false),
                    new FoodMenuItemDto("186585251", "1258584", "Chicken tikka masala Rice bowl", "Chicken tikka masala Rice bowl", 269, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/27/969a9b30-c205-4d0b-ae38-4c399d616753_f2101b06-13ef-4041-84d0-749d80dbf4b3.jpg", true, false, false)
            );

            case "548185" -> List.of(
                    new FoodMenuItemDto("187317023", "548185", "3 Gulab Jamun", "Soft, melt-in-mouth gulab jamuns soaked in rich cardamom-flavoured sugar syrup", 99, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/6/9/3976dad8-2871-449b-bd23-6051ed0a5a86_798a1785-141b-4b02-bb01-3ff6654b6770.jpg_compressed", true, false, false),
                    new FoodMenuItemDto("187317026", "548185", "Aloo 65 Mini (300ml)", "Mini portion of crispy Aloo 65", 149, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/12/26/bd05b11e-4d75-4bc8-8de9-fa872bc9eadc_168819e5-c4c0-4166-8391-a7d764f3c8cf.jpeg", true, false, false),
                    new FoodMenuItemDto("187317027", "548185", "Egg 65 Mini (300ml )", "Mini portion of Egg 65", 149, false, "https://media-assets.swiggy.com/swiggy/image/upload/lj3tkgpv5w1vpb2tzz0a", true, false, false),
                    new FoodMenuItemDto("187317028", "548185", "Biryani Rice", "Long Grain Basmati Biryani Rice", 249, false, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/12/26/acd92367-43ba-4689-85a6-00bf7daa45ed_caa25a97-645f-4f12-b1e5-f1938a4af74f.jpeg", true, false, false),
                    new FoodMenuItemDto("192601450", "548185", "Badshahi Paneer 65 (full)", "Crispy fried paneer cubes coated in signature 65 spice mix", 349, true, "https://media-assets.swiggy.com/swiggy/image/upload/wkchtdzrykl3cjupdtlf", true, false, true),
                    new FoodMenuItemDto("192601448", "548185", "Paneer Shahi Chilli Paneer (full)", "Soft paneer cubes tossed in Indo-Chinese chilli sauce", 349, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/9/23/def485d2-68c1-48a4-918b-cbb46cdbd0f6_712014a1-2202-4edf-9e10-595eef98f41e.jpg", true, false, true),
                    new FoodMenuItemDto("192601449", "548185", "Kali Mirch Mushroom (pepper Mushroom (full)", "Juicy mushrooms sauteed with pepper spice", 349, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/9/23/cba657d3-08ed-4ba7-a1d2-eca60d3210a3_29b05fdf-1980-41bc-a99e-a94c3c5c225d.png_compressed", true, false, true),
                    new FoodMenuItemDto("192601447", "548185", "Shahi Aloo Peshawari (aloo 65 Full )", "Crispy golden potato bites tossed in South Indian-style 65 masala", 319, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/11/21/1403ec9f-c9d7-47e7-9630-c3d3584c566a_f66dc2df-77d1-4f30-9e56-7dd0ff8c4ceb.JPG", true, false, true),
                    new FoodMenuItemDto("192601440", "548185", "Shahi Paneer (special Paneer Hyderabadi Biryani-serve-1)", "Special Paneer Hyderabadi Biryani served with Aloo 65 and Raita", 389, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/9/23/51d975e5-70d6-41d7-aa77-d78239db6b5f_5759215e-6eed-4067-8db6-a1f23c2a6810.jpg", true, false, true),
                    new FoodMenuItemDto("192601441", "548185", "Paneer 65-e-nawabi Biryani ( Paneer 65 Biryani - Serve - 1)", "Paneer 65 biryani served with Aloo 65 and Raita", 389, true, "https://media-assets.swiggy.com/swiggy/image/upload/FOOD_CATALOG/IMAGES/CMS/2025/9/23/0a076947-5bd4-4e0c-b44d-98b8c53860dc_caad6bb2-b5e5-4586-9df7-cf99c02b1f38.jpg", true, false, true)
            );

            case "r1" -> List.of(
                    new FoodMenuItemDto("r1_m1", "r1", "Margherita Pizza", "Classic cheese pizza with tomato sauce", 299, true, "", true, false, false),
                    new FoodMenuItemDto("r1_m2", "r1", "Farmhouse Pizza", "Capsicum, onion, tomato, corn and cheese", 399, true, "", true, false, false),
                    new FoodMenuItemDto("r1_m3", "r1", "Garlic Bread", "Toasted garlic bread with cheese dip", 159, true, "", true, false, false),
                    new FoodMenuItemDto("r1_m4", "r1", "Cheese Burst Pizza", "Extra cheesy pizza with soft crust", 449, true, "", true, true, false),
                    new FoodMenuItemDto("r1_m5", "r1", "Pepsi", "Chilled soft drink", 69, false, "", true, false, false)
            );
            case "r2" -> List.of(
                    new FoodMenuItemDto("r2_m1", "r2", "Veg Whopper", "Loaded veg patty with lettuce and sauces", 189, true, "", true, true, true),
                    new FoodMenuItemDto("r2_m2", "r2", "Crispy Veg Burger", "Crispy veg patty with mayo", 99, true, "", true, false, false),
                    new FoodMenuItemDto("r2_m3", "r2", "King Fries", "Crispy salted fries", 129, true, "", true, false, true),
                    new FoodMenuItemDto("r2_m4", "r2", "Cheesy Fries", "Fries loaded with cheese sauce", 169, true, "", true, false, true),
                    new FoodMenuItemDto("r2_m5", "r2", "Coke", "Chilled beverage", 79, false, "", true, false, false)
            );
            case "r3" -> List.of(
                    new FoodMenuItemDto("r3_m1", "r3", "Zinger Burger", "Crispy chicken burger with spicy mayo", 219, false, "", true, true, true),
                    new FoodMenuItemDto("r3_m2", "r3", "Chicken Popcorn", "Bite-sized crispy chicken pieces", 179, false, "", true, false, false),
                    new FoodMenuItemDto("r3_m3", "r3", "Hot Wings", "Spicy fried chicken wings", 249, false, "", true, false, false),
                    new FoodMenuItemDto("r3_m4", "r3", "Chicken Bucket", "Assorted crispy chicken pieces", 599, false, "", true, true, false),
                    new FoodMenuItemDto("r3_m5", "r3", "Pepsi", "Chilled soft drink", 69, false, "", true, false, false)
            );
            case "r4" -> List.of(
                    new FoodMenuItemDto("r4_m1", "r4", "Cheesy 7 Pizza", "Seven cheese loaded pizza", 459, true, "", true, true, true),
                    new FoodMenuItemDto("r4_m2", "r4", "Tandoori Paneer Pizza", "Paneer tikka, onion and capsicum", 429, true, "", true, true, true),
                    new FoodMenuItemDto("r4_m3", "r4", "Garlic Sticks", "Crispy garlic sticks with herbs", 149, true, "", true, false, false),
                    new FoodMenuItemDto("r4_m4", "r4", "Pesto Veg Pizza", "Italian herbs, veggies and pesto sauce", 389, true, "", true, true, false),
                    new FoodMenuItemDto("r4_m5", "r4", "Chocolate Brownie", "Warm brownie dessert", 139, true, "", true, false, false)
            );
            case "r5" -> List.of(
                    new FoodMenuItemDto("r5_m1", "r5", "Veg Steamed Momos", "Classic steamed momos with spicy chutney", 129, true, "", true, false, false),
                    new FoodMenuItemDto("r5_m2", "r5", "Veg Fried Momos", "Crispy fried momos with chutney", 149, true, "", true, false, false),
                    new FoodMenuItemDto("r5_m3", "r5", "Paneer Momos", "Soft momos filled with paneer stuffing", 169, true, "", true, true, false),
                    new FoodMenuItemDto("r5_m4", "r5", "Momo Burger", "Burger with momo-style patty", 119, true, "", true, false, true),
                    new FoodMenuItemDto("r5_m5", "r5", "Thukpa", "Hot Tibetan noodle soup", 179, true, "", true, false, false)
            );
            default -> List.of(
                    new FoodMenuItemDto("default_m1", "default", "Veg Meal", "Simple meal combo", 199, true, "", true, false, false),
                    new FoodMenuItemDto("default_m2", "default", "French Fries", "Crispy salted fries", 129, true, "", true, false, false),
                    new FoodMenuItemDto("default_m3", "default", "Cold Coffee", "Chilled coffee with ice cream", 179, true, "", true, false, false)
            );
        };
    }
}