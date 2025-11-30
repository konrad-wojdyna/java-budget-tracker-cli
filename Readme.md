# 💰 Java Budget Tracker CLI

> A feature-rich command-line budget tracking application demonstrating **Core Java** proficiency, **OOP principles**, and **clean code practices**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Status](https://img.shields.io/badge/Status-In%20Development-yellow.svg)]()

## 📋 Project Overview

This project demonstrates mastery of Core Java concepts through incremental development of a real-world CLI application.

**Tech Focus:** OOP, Collections, Streams, File I/O, Exception Handling

---

## ✨ Features

### Core Functionality
- [x] Expense tracking with full CRUD operations
- [x] Category-based organization (type-safe enums)
- [x] Priority system for expense management
- [ ] Advanced statistics with HashMap
- [ ] File persistence (CSV)
- [ ] Lambda expressions & Stream API
- [ ] Null safety with Optional

### Technical Highlights
- [x] Object-oriented design (encapsulation, inheritance)
- [x] ArrayList-based data management
- [x] Interactive CLI with Scanner
- [ ] Repository pattern with interfaces
- [ ] Exception handling with custom exceptions
- [ ] Modern Java features (lambdas, streams, records)
---

## 🛠️ Tech Stack

| Category | Technology                          |
|----------|-------------------------------------|
| **Language** | Java 21 (LTS)                       |
| **Paradigm** | Object-Oriented Programming         |
| **Collections** | ArrayList, HashMap, HashSet         |
| **I/O** | BufferedReader/Writer, File I/O     |
| **Modern Java** | Lambdas, Streams, Optional, Records |
| **Build** | Maven (planned)                     |
| **Version Control** | Git & GitHub                        |

---

## 📂 Project Structure
```
src/
├── Main.java                    # CLI entry point
├── model/                       # Domain models
│   ├── Expense.java
│   ├── Category.java (enum)
│   └── Priority.java (enum)
├── service/                     # Business logic
│   ├── ExpenseRepository.java (interface)
│   └── BudgetManager.java
├── exception/                   # Custom exceptions
└── util/                        # Helper classes
```

---

## 🚀 How to Run

### Prerequisites
- Java 21 
- Terminal/Command Prompt

### Steps
```bash
# Clone repository
git clone https://github.com/konrad-wojdyna/java-budget-tracker-cli.git
cd java-budget-tracker-cli

# Compile
javac -d bin src/**/*.java src/*.java

# Run
java -cp bin Main
```

---

## 📸 Screenshots

_Coming soon: CLI interface demo_

---

## 🎯 Core Java Concepts Demonstrated

### Fundamentals
- ✅ Classes & Objects
- ✅ Encapsulation & Data Hiding
- ✅ Constructors & Methods
- ✅ Enums for Type Safety

### Intermediate
- ✅ Collections Framework (ArrayList)
- ⏳ HashMap & Set
- ⏳ Interfaces & Abstraction
- ⏳ Inheritance & Polymorphism
- ⏳ Exception Handling

### Advanced
- ⏳ Lambda Expressions
- ⏳ Stream API
- ⏳ Optional for Null Safety
- ⏳ Date & Time API
- ⏳ Records (Java 14+)

---

## 🤝 About This Project

Built to demonstrate proficiency in Core Java and software development best practices.

**Key Learning Areas:**
- Problem-solving with OOP
- Clean, maintainable code
- Professional Git workflow
- Industry-standard design patterns
---

## 📧 Contact

**GitHub:** 

---

## 📄 License

This project is open source and available for educational purposes.

---

**Status:** 🚧 Active Development |  **Focus:** Core Java Mastery