# XYoutube Search Test Automation

## 📝 Project Overview

**XYoutube Search Test Automation** is a Selenium-based automation project that tests multiple interactive and dynamic elements on YouTube using automated browser actions. This suite includes validations across different YouTube sections like Movies, Music, News, and Search, utilizing Soft Asserts and dynamic data input.

---

## ✅ Automated Test Cases

### 🧪 testCase01 – Verify About Section
- Navigate to [YouTube.com](https://www.youtube.com)
- Assert that the current URL is correct
- Click on the **"About"** link at the bottom of the sidebar
- Print the **About message** displayed on the screen

---

### 🧪 testCase02 – Validate Top Selling Movies
- Go to the **"Films"/"Movies"** tab
- In the **Top Selling** section, scroll to the extreme right
- Soft Assert whether the movie is marked **“A” for Mature**
- Soft Assert whether movie category exists (e.g., "Comedy", "Animation", "Drama")

---

### 🧪 testCase03 – Inspect Music Playlist
- Go to the **"Music"** tab
- In the **first section**, find the **rightmost playlist**
- Print the **name** of that playlist
- Soft Assert that the number of tracks listed is **less than or equal to 50**

---

### 🧪 testCase04 – Scrape Latest News
- Go to the **"News"** tab
- Print the **title** and **body** of the **first 3 Latest News Posts**
- Calculate and print the **sum of likes** across those posts  
  (If no likes are shown, consider it as 0)

---

### 🧪 testCase05 – Search & View Count Check
- For each item listed in `src/test/resources/data.xlsx`
- Perform a **search** on YouTube
- **Scroll through results** until the **cumulative views** of listed videos reach **10 Crores (100M)**

---

## 🔧 Tech Stack

- **Language:** Java  
- **Automation Framework:** Selenium WebDriver  
- **Testing Framework:** TestNG  
- **Build Tool:** Gradle
- **Browser:** Google Chrome

---

## 🌐 System Requirements

- Java 8 or later  
- Google Chrome (latest version)  
- ChromeDriver (compatible with your Chrome version)  
- Internet connection

> Ensure `chromedriver` is in your system `PATH`, or specify its path in your Selenium setup.

---

## 📦 Installation

Follow these steps to set up and run the project locally:

### 1. Clone the Repository

```bash
git clone https://github.com/NiviyaJ/YouTubeSearchAutomation.git
cd YouTubeSearchAutomation
```

### 2. Make the Gradle Wrapper Executable (Linux/macOS)

```bash
chmod +x gradlew
```

> Windows users can skip this step and use `gradlew.bat` instead.

### 3. Build the Project

```bash
./gradlew clean build
```

---

## 🚀 Running the Tests

To run all test cases:

**Linux/macOS:**
```bash
./gradlew test
```

**Windows:**
```bash
gradlew.bat test
```

You can also run specific tests via your IDE.

---

## ⚙️ Configuration

- The search data file is located at:  
  `src/test/resources/data.xlsx`  
- No additional config or `.env` is required

---

## ❌ Reporting

No reporting tools are currently integrated with this project.

---

## 📬 Author

Maintained by **[Niviya Joy]**  
GitHub: [https://github.com/NiviyaJ]

---
