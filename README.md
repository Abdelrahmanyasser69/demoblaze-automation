# Demoblaze Automation Project

This project is a complete Selenium automation suite for the e-commerce website [Demoblaze](https://www.demoblaze.com).

## 🧰 Tech Stack
- Java 17
- Selenium WebDriver 4.12.1
- TestNG 7.8.0
- WebDriverManager
- Maven
- Page Object Model (POM)

## 🧪 Test Coverage
- ✅ Homepage Load Verification
- ✅ User Login (valid, invalid, and empty field)
- ✅ Product Search and Validation (case-insensitive)
- ✅ Product Details Extraction
- ✅ Add to Cart and Cart Total Validation
- ✅ Checkout Flow with Valid and Invalid Data
- ✅ Alert Handling and Confirmation Pop-up Parsing
- ✅ Screenshot Capture for Order Confirmation

## 📂 Project Structure
```
src/
 └── main/
     └── java/
         └── Pages/
         └── Selectors/
 └── test/
     └── java/
         └── tests/
 └── resources/
     └── testData.json
```

## ▶️ How to Run

1. Clone the repository:
```bash
git clone https://github.com/Abdelrahmanyasser69/demoblaze-automation.git
```

2. Navigate to project folder and run tests:
```bash
mvn clean test
```

## 📝 Notes
- Ensure ChromeDriver version matches your Chrome browser.
- Test data is loaded from `testData.json`.
- Screenshots are saved in `screenshots/` directory.

## 📋 Requirements
- Java JDK 17 or higher
- Maven installed (`mvn -v` to check)
- Google Chrome (latest)

---

**Author:** Abdelrahman Yasser  
**Contact:** [abdelrahmanyasser606@gmail.com]