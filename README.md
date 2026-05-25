# EatSync

EatSync is a collaborative group food-ordering Android app where users can create or join a session, select a restaurant, add food items to a shared cart, customize menu items with variants/add-ons, and split the bill user-wise.

The app solves a common group-ordering problem: when friends or college groups order together, one person usually manages restaurant selection, cart updates, item prices, and bill splitting manually. EatSync turns this into a shared real-time session.

---

## Features

### Multi-user Session Flow
- Host can create a session with a unique session code.
- Participants can join the same session using the code.
- Session state is synced using Firebase Realtime Database.

### Shared Restaurant Selection
- Host selects a delivery address.
- Host selects a restaurant.
- Selected restaurant is synced to participants.
- Restaurant/menu flow is handled through backend APIs.

### Real-time Shared Cart
- Users can add items to a shared cart.
- Quantity can be increased or decreased.
- Users can remove only their own items.
- Cart updates are synced across devices in real time.

### Menu and Customization Flow
- Restaurant menus are fetched from the Spring Boot backend.
- Menu items show name, description, price, veg/non-veg status, image, and customization tags.
- Items with variants/add-ons open a customization screen.
- Customization screen supports variant selection, add-on selection, dynamic final price calculation, customized item description in cart, and multiple customized versions of the same item.

### Bill Split
- Food cost is assigned to the user who added the item.
- Delivery fee and platform fee are shared equally.
- Each participant can mark themselves ready.
- Host can lock the cart once the order is ready for checkout.
- Bill split screen shows item-wise and user-wise payable amount.

### Public Backend Deployment
- Spring Boot backend is deployed on Render.
- Android app fetches food/menu/customization data from the deployed backend.
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
- Java 21
- Spring Boot
- REST APIs
- Docker
- Render deployment

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
Spring Boot Backend on Render
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
https://eatsync-v31g.onrender.com
```

### Health Check

```http
GET /api/health
```

Example:

```text
https://eatsync-v31g.onrender.com/api/health
```

### Get Addresses

```http
GET /api/food/addresses
```

### Get Restaurants

```http
GET /api/food/restaurants
```

### Get Menu Items by Restaurant

```http
GET /api/food/menu/{restaurantId}
```

Example:

```http
GET /api/food/menu/1256056
```

### Get Item Customization Details

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
- Public Spring Boot backend deployed on Render
- Swiggy MCP-tested restaurant/menu/customization data
- Variant and add-on selection flow
- Dynamic add-on price calculation
- User-wise bill splitting
- Cart locking and ready status flow
- Works across different devices and networks

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
- Login, authentication, and user profile support

---

## Author

Absar Ali
