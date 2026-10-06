# AI Subscription Management System

A desktop application developed in Java using **Java Swing** for the **Object-Oriented Programming** coursework at **Islington College** (affiliated with London Metropolitan University). The project demonstrates core object-oriented principles—inheritance, polymorphism, abstraction, and encapsulation—through an interactive interface that simulates AI model subscription tiers and token consumption.

---

## Overview

- **Author:** Om Dangol
- **Role:** Solo Developer (Coursework Project)
- **Language:** Java (JDK 17+)
- **GUI Framework:** Java Swing & AWT
- **Architecture:** Object-Oriented Hierarchy with Event-Driven GUI

---

## Class Hierarchy & Architecture

```
                          ┌──────────────────────┐
                          │   abstract AIModel   │
                          ├──────────────────────┤
                          │ - modelName: String  │
                          │ - price: double      │
                          │ - paramCount: int    │
                          │ - contextWindow: int │
                          ├──────────────────────┤
                          │ + calculateTokens()  │
                          │ + abstract display() │
                          └──────────┬───────────┘
                                     │
                 ┌───────────────────┴───────────────────┐
                 │                                       │
     ┌───────────┴──────────┐                ┌───────────┴──────────┐
     │     PersonalPlan     │                │       ProPlan        │
     ├──────────────────────┤                ├──────────────────────┤
     │ - availableTokens    │                │ - totalSlots: int    │
     ├──────────────────────┤                │ - members: String[]  │
     │ + buyTokens()        │                │ - memberCount: int   │
     │ + usePrompt()        │                ├──────────────────────┤
     │ + display()          │                │ + addMember()        │
     └──────────────────────┘                │ + removeMember()     │
                                             │ + searchMember()     │
                                             │ + display()          │
                                             └──────────────────────┘
```

### Module Responsibilities

1. **`AIModel.java` (Abstract Base Class):**
   - Enforces common attributes across all AI subscription offerings: model name, monthly subscription price, parameter count (in billions), and context window size (in tokens).
   - Implements `calculateTotalToken(String promptText, int expectedOutputTokens)` to estimate prompt token lengths and guard against context-window overflow.
   - Declares the polymorphic `display()` method contract.

2. **`PersonalPlan.java` (Subclass):**
   - Extends `AIModel` for individual developers with a metered token balance.
   - Features `buyTokens(int extraTokens)` for balance top-ups.
   - Implements `usePrompt(...)` to decrement token balances dynamically based on prompt input and anticipated output.

3. **`ProPlan.java` (Subclass):**
   - Extends `AIModel` for enterprise teams requiring multi-seat slot allocation.
   - Manages team capacity using bounded array mechanics with shift-deletion on member removal.
   - Provides membership search and real-time slot availability tracking.

4. **`SubscriptionGUI.java` (GUI Orchestrator):**
   - Comprehensive Java Swing dashboard with tabbed/card layout, responsive forms, data validation, and an interactive `JTable`.
   - Supports file-based export and reload of plan configurations.

---

## Features

- **Plan Configuration:** Form controls to register new Personal and Pro subscription tiers with explicit context windows and pricing.
- **Token Estimation & Quota Enforcement:** Simulates token consumption against strict context-window limits.
- **Seat Management:** Add, remove, and query team members within predefined seat limits.
- **State Persistence:** Save and reload plan data using file I/O.
- **Input Sanitization:** Strict type checking with dialog feedback prevents runtime exceptions on malformed numbers or empty inputs.

---

## Quick Start

### Prerequisites
- Java Development Kit (JDK 17 or higher recommended; tested up to JDK 25).
- Git.

### Building & Running

```bash
# Clone the repository
git clone https://github.com/omwe77/ai-subscription-management-system.git
cd ai-subscription-management-system

# Compile all source files
javac *.java

# Launch the desktop GUI
java SubscriptionGUI
```

---

## Implementation Status

### Implemented (Verified in Active Codebase)
- **Object-Oriented Architecture:** Abstract base class `AIModel` enforcing contract attributes (model name, price, parameter count, context window) and `calculateTotalToken` validation.
- **Personal Plan Tier:** Concrete subclass `PersonalPlan` implementing metered token quotas, top-ups (`buyTokens`), and dynamic prompt consumption deductions (`usePrompt`).
- **Enterprise Team Tier:** Concrete subclass `ProPlan` with bounded team slot allocations and shift-deletion array management (`addMember`, `removeMember`, `searchMember`).
- **Desktop Graphical Interface:** Java Swing GUI (`SubscriptionGUI`) with input sanitization dialogs, plan selection cards, and interactive `JTable` rendering.
- **File-Based Persistence:** Serializes and reloads subscription configurations to and from local text storage.

### In Progress
- *None (Academic Coursework Deliverable Complete).*

### Planned (Future Enhancements)
- **Byte-Pair Encoding (BPE):** Integrating real subword tokenization algorithms (e.g. tiktoken) to replace whitespace estimation.
- **Headless & Web APIs:** Wrapping model subscription tracking into a headless CLI and Spring Boot REST API.

---

## Design Decisions

- **Direct Inheritance:** `PersonalPlan` and `ProPlan` inherit from `AIModel` to guarantee consistent telemetry while specializing behaviors (token balance vs. seat allocation).
- **Zero Third-Party Dependencies:** Built entirely with standard JDK packages (`javax.swing`, `java.awt`, `java.io`, `java.util`) for frictionless compilation on any platform.
- **Defensive Copying & Bounds Checking:** Array shifts in `ProPlan` guarantee memory consistency without requiring external collection libraries.

---

## Known Limitations

- **Token Approximation:** Uses whitespace tokenization rather than Byte-Pair Encoding (BPE) for token calculation.
- **Desktop Focus:** Designed for local desktop environments; does not provide a headless server API.

---

## License

Developed as academic coursework for Islington College / London Metropolitan University. Open for educational and portfolio reference.
