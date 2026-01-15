# Katalon Data Driven Automation – Demo QA Form
Repository ini berisi **Automation Test Web** menggunakan **Katalon Studio** dengan pendekatan **Data Driven Testing** pada **Demo QA Form**.
---

## 📝 Description
Automation ini dibuat untuk memvalidasi input form menggunakan beberapa set data yang diambil dari file Excel sebagai test data driven bersumber dari dokumen Test Case Design 
CASE01_UI_Katalon.xlsx.
---

## 🧪 Test Coverage
Automation test ini mencakup:
- Validasi input form menggunakan **data driven**
- Multiple data set dari file Excel
- Assertion pada hasil submit form
- Penggunaan Object Repository
- Penggunaan Global Variable (**Profile Default**)
---

## 🛠 Requirements
Pastikan environment berikut sudah ter-install sebelum menjalankan test.

### Tools
- **Git**
- **Katalon Studio**
- **Google Chrome**

### Contoh Environment
- OS: Windows  
- Katalon Studio: v10  
- Browser: Chrome 

---

## 📂 Project Structure
Struktur utama project sebagai berikut:

```
katalon-data-driven-automation-qademo/
│
├── Data Files/ # Data driven test (Excel / internal data)
├── Object Repository/ # Object locator halaman Demo QA
│ └── Page_DEMOQA
├── Profiles/ # Environment & Global Variable
│ └── default
├── Scripts/
│ └── Case01_DataDrivenTest
├── Test Cases/
├── Test Suites/
│ └── TS_DataDriven_Form
├── data-driven-test.xlsx # File Excel data test
├── build.gradle
├── console.properties
├── CASE01_v2.prj
└── README.md
```

---


---

## ⬇️ Clone Repository
Clone repository ini ke local machine:

```bash
git clone https://github.com/rezaparamarta/katalon-data-driven-automation-qademo.git

```
Masuk ke folder project:

cd katalon-data-driven-automation-qademo

---
🚀 Cara Menjalankan Test (Katalon Studio GUI)

1. Buka Katalon Studio

2. Pilih File → Open Project

3. Arahkan ke folder hasil clone repository

4. Tunggu hingga project selesai di-load

5. Buka menu Test Suites

6. Pilih test suite: TS_DataDriven_Form

7. Pastikan Profile yang digunakan adalah: default

8. Klik tombol Run

---

📊 Test Data

Automation ini menggunakan Data Driven Testing dengan sumber data dari file: data-driven-test.xlsx

Setiap baris data akan dieksekusi sebagai satu skenario test.

📑 Report

Setelah test dijalankan, report dapat dilihat di:

Tab Report pada Katalon Studio

Folder:

Reports/

Report akan berisi 2 file di bawah ini:
1. datetime-stamp-report.html
2. Junit_Report.xml


👤 Author

Reza Paramarta
