# EatSync

EatSync is a real-time group food ordering Android app that helps friends create a shared food order, add items together, split the bill automatically, and proceed to checkout through a host-controlled flow.

The project is currently built as an MVP using dummy restaurant/menu data, Firebase Realtime Database, and a Swiggy-style ordering flow. The next planned step is Swiggy Food MCP integration for real restaurant/menu data and checkout sync.

---

## Problem Statement

Ordering food in groups is messy.

Usually one person has to:
- Ask everyone what they want
- Keep switching between chats and food apps
- Add everyone’s items manually
- Calculate who owes how much
- Handle last-minute changes
- Place the order from one account

EatSync solves this by creating one shared ordering session where everyone can join, add their own items, view the shared cart, split the bill, and mark themselves ready.

---

## Solution

EatSync allows a host to create a group food session and share a session code with friends.

Users can then:
- Join the session using the code
- View live participants
- Open the same selected restaurant menu
- Add their own food items
- See a shared cart in real time
- Edit only their own items
- Split the bill automatically
- Mark themselves ready
- Lock the cart when everyone is ready
- Move to a checkout status flow

---

## Features

### Session Management
- Create a group order session
- Join a session using a 6-character code
- Live participant list using Firebase Realtime Database
- Host and participant roles

### Restaurant Selection
- Host selects the restaurant
- Selected restaurant syncs live across devices
- Participants can open the same selected restaurant menu
- Restaurant change warning flow
- Changing restaurant clears cart and resets ready status
- Prevents mixed carts from old restaurant screens

### Menu and Cart
- Different dummy menus for different restaurants
- Add items to shared cart
- Firebase-backed real-time cart sync
- Quantity update using `- quantity +` controls
- Item ownership control
- Users can edit only the items they added
- Cart lock prevents further cart edits

### Bill Split
- Calculates each user’s item total
- Splits delivery and platform fee equally
- Shows final amount per participant
- Each user can mark only themselves ready
- Ready status syncs live across devices

### Cart Lock and Checkout
- Host can lock the cart once everyone is ready
- Participants can view locked checkout status
- Host can unlock the cart if changes are needed
- Unlocking resets ready status
- Checkout screen prepared for future Swiggy MCP integration

---

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Firebase Realtime Database
- MVVM Architecture
- StateFlow
- Navigation Compose
- Android Studio
- Git and GitHub

---

## Architecture

EatSync follows a simple MVVM-based architecture.

```text
UI Screens
   ↓
ViewModels
   ↓
FirebaseSessionManager
   ↓
Firebase Realtime Database
