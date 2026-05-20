# EatSync

EatSync is a collaborative group food-ordering Android app where users can create or join a session, select a restaurant, add food items to a shared cart, customize selected menu items with variants/add-ons, and split the bill user-wise.

The app is built to solve a common group-ordering problem: when friends or college groups order together, one person usually handles the restaurant selection, cart, price tracking, and bill split manually. EatSync brings this flow into a shared real-time session.

---

## Features

### Multi-user session flow
- Host can create a session with a unique session code.
- Participants can join the same session using the code.
- Session state is synced using Firebase Realtime Database.

### Shared restaurant selection
- Host selects a delivery address.
- Host selects a restaurant.
- Selected restaurant is synced to participants.
- If the restaurant changes, the cart/session flow updates safely.

### Real-time shared cart
- Users can add items to a shared cart.
- Quantity can be increased/decreased.
- Users can remove their own items.
- Cart updates are synced across devices in real time.

### Menu and customization flow
- Restaurant menus are fetched from the Spring Boot backend.
- Menu items show:
  - Name
  - Description
  - Price
  - Veg / non-veg status
  - Food image
  - Customizable / add-on tags
- Items with variants/add-ons open a customization screen.
- Customization screen supports:
  - Variant selection
  - Add-on selection
  - Dynamic final price calculation
  - Customized item description in cart

### Bill split
- Food cost is paid by the person who added the item.
- Delivery fee and platform fee are shared equally.
- Each participant can mark themselves ready.
- Host can lock the cart once everyone is ready.
- Bill split screen shows customized item details and final user-wise amount.

### Public backend deployment
- Spring Boot backend is deployed on Railway.
- Android app fetches food data from the deployed backend instead of localhost.
- App works across different devices and networks.

---

## Tech Stack

### Android
- Kotlin
- Jetpack Compose
- Material 3
- MVVM architecture
- StateFlow
- Retrofit
- Coil for image loading

### Backend
- Java
- Spring Boot
- REST APIs
- Railway deployment

### Realtime Sync
- Firebase Realtime Database

### API / Food Data
- Swiggy MCP-tested restaurant, menu, and customization data
- Backend exposes mapped API responses for the Android app

---

## Architecture

```text
Android App
   |
   | Retrofit
   v
Spring Boot Backend on Railway
   |
   | Mapped food APIs using MCP-tested data
   v
Restaurant / Menu / Customization Data


Android App
   |
   | Firebase Realtime Database
   v
Shared Session + Shared Cart Sync
```
---

## Architecture

```text
Android App
   |
   | Retrofit
   v
Spring Boot Backend on Railway
   |
   | Mapped food APIs using MCP-tested data
   v
Restaurant / Menu / Customization Data


Android App
   |
   | Firebase Realtime Database
   v
Shared Session + Shared Cart Sync
```

---

## Current App Flow

```text
Create Session / Join Session
        ↓
Waiting Room
        ↓
Select Address
        ↓
Select Restaurant
        ↓
View Menu
        ↓
Add Normal Item / Customize Item
        ↓
Shared Cart
        ↓
Bill Split
        ↓
Lock Cart
        ↓
Checkout
```

---

## Backend API Endpoints

Base URL:

```text
https://eatsync-production.up.railway.app
```

### Get addresses

```http
GET /api/food/addresses
```

### Get restaurants

```http
GET /api/food/restaurants
```

### Get menu items by restaurant

```http
GET /api/food/menu/{restaurantId}
```

Example:

```http
GET /api/food/menu/1256056
```

### Get item customization details

```http
GET /api/food/menu-item-details/{restaurantId}/{itemId}
```

Example:

```http
GET /api/food/menu-item-details/1256056/192016466
```

---

## Swiggy MCP Integration Status

Swiggy MCP tools were tested through the MCP client environment and used to understand real response structures for restaurants, menus, and item customization.

Currently implemented:

- `get_addresses` tested and mapped
- `search_restaurants` tested and mapped
- `get_restaurant_menu` tested and mapped
- `search_menu` tested for item variants and add-ons
- Restaurant/menu/customization data mapped into Spring Boot APIs
- Android app consumes backend APIs and displays this data

Current limitation:

- Spring Boot backend does not directly call Swiggy MCP dynamically yet.
- MCP-fetched data is currently mapped into backend responses for MVP demonstration.
- Cart/order-related MCP tools are intentionally not used yet for safety.

Planned next step:

- Implement direct backend-to-Swiggy MCP integration with proper OAuth/session handling.
- Dynamically fetch restaurants, menus, variants, add-ons, coupons, and safe cart payloads.

---

## Screens Implemented

- Home Screen
- Create Session Screen
- Join Session Screen
- Waiting Room Screen
- Address Selection Screen
- Restaurant Selection Screen
- Menu Screen
- Customization Screen
- Shared Cart Screen
- Bill Split Screen
- Checkout Screen

---

## Key Highlights

- Real-time collaborative shared cart using Firebase
- Public Spring Boot backend deployed on Railway
- Swiggy MCP-tested restaurant/menu/customization data
- Variant and add-on selection flow
- Add-on price calculation
- User-wise bill splitting
- Cart locking and ready status flow
- Works across different devices

---

## Future Scope

- Direct live Swiggy MCP calls from backend
- Dynamic restaurant search by address/query
- Dynamic menu and customization fetching for all items
- Store selected variant/add-on IDs in cart payload
- Coupon support and discount-aware bill splitting
- Safe Swiggy cart payload generation
- Order placement flow after proper permissions and safeguards
- Order tracking and ETA updates
- Better checkout screen with payment status
- Session expiry and participant management

---

## Author

Absar Ali
