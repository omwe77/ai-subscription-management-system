# AI Subscription System

A Java Swing desktop application for managing AI subscription plans, allowing users to create, manage, and interact with Personal and Pro AI subscription models through a graphical user interface.

---

# Table of Contents

- Overview
- Features
- Technologies Used
- Project Structure
- Installation
- Running the Project
- Usage
- Design Decisions
- Testing
- Extending the Project
- Versioning
- License
- Author
- Screenshots
- Future Improvements

---

# Overview

The AI Subscription System is an object-oriented Java application developed as coursework. The application demonstrates the practical implementation of Java OOP principles together with Java Swing GUI components.

Users can:

- Create AI subscription plans
- Manage Personal and Pro subscriptions
- Calculate prompt token usage
- Manage Pro team members
- Export subscription data
- Load previously saved plans
- View all subscriptions through an interactive table

---

# Features

### Personal Plan

- Create personal AI subscriptions
- Calculate total prompt tokens
- Purchase additional tokens
- Display subscription details

### Pro Plan

- Create Pro subscriptions
- Add team members
- Remove team members
- Search members
- Display subscription information

### General Features

- Java Swing graphical interface
- Dynamic form switching
- JTable for displaying plans
- Export plans to text file
- Load saved plans
- Input validation
- Object-oriented architecture

---

# Technologies Used

- Java
- Java Swing
- Java Collections Framework
- File I/O
- BlueJ (development environment)

---

# Project Structure

```
AI_Subscription_System/
│
├── AIModel.java
├── PersonalPlan.java
├── ProPlan.java
├── SubscriptionGUI.java
├── plans_export.txt
├── package.bluej
└── README.md
```

---

# Installation

## Requirements

- Java JDK 17 or newer (JDK 11+ should also work)
- BlueJ (optional)
- Git (optional)

Clone the repository:

```bash
git clone https://github.com/yourusername/AI_Subscription_System.git
```

Navigate into the project:

```bash
cd AI_Subscription_System
```

---

# Running the Project

## Compile

```bash
javac *.java
```

## Run

```bash
java SubscriptionGUI
```

No additional command-line arguments are required.

---

# Usage

### Creating a Personal Plan

1. Select **Personal Plan**
2. Enter:
   - Model Name
   - Price
   - Parameters
   - Context Window
   - Token Quota
3. Click **Add**

---

### Creating a Pro Plan

1. Select **Pro Plan**
2. Enter:
   - Model Name
   - Price
   - Parameters
   - Context Window
   - Team Slots
3. Click **Add**

---

### Token Calculation

Users can enter:

- Prompt text
- Expected output tokens

The application calculates the total token usage while ensuring it does not exceed the model's context window.

---

### Team Management

For Pro plans:

- Add team members
- Remove members
- Search for members

---

### Export Data

Click **Export** to save subscription information to:

```
plans_export.txt
```

---

# Example

Creating a Personal Plan:

```java
AIModel plan = new PersonalPlan(
    "GPT-4",
    20.0,
    175,
    128000,
    500000
);
```

Running the application:

```bash
javac *.java
java SubscriptionGUI
```

---

# Design Decisions

This project follows Object-Oriented Programming principles.

## Inheritance

```
AIModel
   │
   ├── PersonalPlan
   └── ProPlan
```

## Abstraction

`AIModel` is implemented as an abstract base class containing common attributes and behaviours.

## Polymorphism

The `display()` method is overridden by each subscription type to provide plan-specific information.

## Encapsulation

All class fields are private and accessed through getter methods.

---

# Testing

The project was manually tested using multiple scenarios including:

- Creating Personal Plans
- Creating Pro Plans
- Token calculation
- Team member addition
- Team member removal
- Search functionality
- Export and load operations
- Invalid input handling

No automated testing framework (JUnit) is currently included.

---

# Extending the Project

Future contributors can enhance the project by adding:

- User authentication
- Database integration (MySQL/PostgreSQL)
- Subscription editing
- Dark mode
- Monthly payment tracking
- REST API integration
- Cloud storage
- Analytics dashboard

### Contribution Workflow

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit your changes

```bash
git commit -m "Added new feature"
```

4. Push

```bash
git push origin feature-name
```

5. Open a Pull Request

---

# Versioning

Current Version

```
v1.0
```

This version represents the initial coursework submission.

---

# License

This project was developed for academic coursework purposes.

You may use the source code for educational and learning purposes.

---

# Author

**Om Dangol**

Computer Science Student

GitHub: https://github.com/yourusername

Email: your-email@example.com

---

# Screenshots

You can add screenshots after uploading them to the repository.

Example:

```
screenshots/

├── home.png
├── personal-plan.png
├── pro-plan.png
└── export.png
```

Markdown:

```md
## Home Screen

![Home](screenshots/home.png)

## Personal Plan

![Personal](screenshots/personal-plan.png)

## Pro Plan

![Pro](screenshots/pro-plan.png)
```

---

# Demo / Class Diagram

Example class hierarchy:

```
              AIModel
                 ▲
        ┌────────┴────────┐
        │                 │
 PersonalPlan        ProPlan
```

Application Flow

```
User
 │
 ▼
GUI
 │
 ▼
AIModel
 │
 ├── PersonalPlan
 └── ProPlan
 │
 ▼
Export / Load File
```

---

# Future Improvements

- Database support
- Login system
- Subscription editing
- Better UI themes
- Charts and analytics
- Cloud synchronization
- Unit testing with JUnit
- Maven project structure
- REST API integration

---

**Developed as a Java Object-Oriented Programming Coursework Project.**
