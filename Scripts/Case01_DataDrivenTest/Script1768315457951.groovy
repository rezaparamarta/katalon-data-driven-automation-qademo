import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint

import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

import internal.GlobalVariable as GlobalVariable
import java.util.Arrays as Arrays
import org.openqa.selenium.Keys as Keys


WebUI.openBrowser('')
WebUI.navigateToUrl('https://demoqa.com/automation-practice-form')


WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/input_firstName'),
	firstName
)

WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/input_lastName'),
	lastName
)

WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/input_userEmail'),
	emailName
)


TestObject genderMale = findTestObject('Object Repository/Page_DEMOQA/label_Male')
boolean genderClicked = false
boolean selectGender = (selectGenderFlag == 'Y')

//WebUI.scrollToElement(genderMale, 10)
//WebUI.waitForElementVisible(genderMale, 10)
//WebUI.click(genderMale)

// Klik gender (aktifkan flag)
// Jika ingin menguji test case negative mandatory field gender harus di uncomment
//WebUI.click(genderMale)
//genderClicked = true
if (selectGender) {
	WebUI.scrollToElement(genderMale, 10)
	WebUI.waitForElementVisible(genderMale, 10)
	WebUI.click(genderMale)
	genderClicked = true
}


WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/input_mobile_phone'),
	mobilePhone == null ? '' : mobilePhone.toString()
)


TestObject dobField = findTestObject('Object Repository/Page_DEMOQA/input_dateOfBirth')

// Date picker di DemoQA sering override value,
// jadi inject value langsung via JS
WebUI.scrollToElement(dobField, 10)
WebUI.waitForElementPresent(dobField, 15)

WebUI.executeJavaScript(
	'arguments[0].value = \'\';',
	Arrays.asList(WebUI.findWebElement(dobField, 10))
)

WebUI.executeJavaScript(
	'arguments[0].value = arguments[1];',
	Arrays.asList(WebUI.findWebElement(dobField, 10), bDO)
)

WebUI.sendKeys(dobField, Keys.ENTER.toString())


WebUI.click(findTestObject('Object Repository/Page_DEMOQA/div'))
WebUI.click(findTestObject('Object Repository/Page_DEMOQA/container_subject_field'))

WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/field_subject_input'),
	subjects
)

//WebUI.click(findTestObject('Object Repository/Page_DEMOQA/input_subject_maths'))
// tunggu option muncul (DOM baru)
TestObject subjectMaths = findTestObject('Object Repository/Page_DEMOQA/input_subject_maths')
WebUI.waitForElementVisible(subjectMaths, 10)

// click via JS supaya gak stale
WebUI.executeJavaScript(
	"arguments[0].click();",
	Arrays.asList(WebUI.findWebElement(subjectMaths, 10))
)
WebUI.click(findTestObject('Object Repository/Page_DEMOQA/label_Reading'))

WebUI.setText(
	findTestObject('Object Repository/Page_DEMOQA/input_Address'),
	address
)


WebUI.click(findTestObject('Object Repository/Page_DEMOQA/dropdown_select_state'))
WebUI.click(findTestObject('Object Repository/Page_DEMOQA/container_city_ncr'))
WebUI.click(findTestObject('Object Repository/Page_DEMOQA/dropdown_select_city'))
WebUI.click(findTestObject('Object Repository/Page_DEMOQA/input_state_delhi'))


TestObject submitBtn = findTestObject('Object Repository/Page_DEMOQA/button_Submit')

// button sering ke-block div lain
WebUI.scrollToElement(submitBtn, 10)
WebUI.delay(1)

WebUI.executeJavaScript(
	'arguments[0].click();',
	Arrays.asList(WebUI.findWebElement(submitBtn, 10))
)

// Normalisasi data
String mobileStr = mobilePhone == null ? '' : mobilePhone.toString()

boolean isEmailValid      = emailName.contains('@')
boolean isMobileValid     = mobileStr.trim().length() > 0 && mobileStr.matches('\\d{10}')
boolean isFirstNameValid  = firstName != null && firstName.trim().length() > 0
boolean isLastNameValid   = lastName != null && lastName.trim().length() > 0


if (isFirstNameValid && isLastNameValid && isEmailValid && isMobileValid && genderClicked) {

	// Positive Case
	WebUI.waitForElementVisible(
		findTestObject('Page_DEMOQA/messages_success_form'),
		10
	)

	WebUI.getText(findTestObject('Page_DEMOQA/messages_success_form'))

	WebUI.verifyElementText(
		findTestObject(
			'Object Repository/Page_DEMOQA/table_value_by_label',
			['label' : 'Student Name']
		),
		firstName + ' ' + lastName
	)

	WebUI.verifyElementText(
		findTestObject(
			'Object Repository/Page_DEMOQA/table_value_by_label',
			['label' : 'Student Email']
		),
		emailName
	)

	WebUI.verifyElementText(
		findTestObject(
			'Object Repository/Page_DEMOQA/table_value_by_label',
			['label' : 'Gender']
		),
		'Male'
	)

	WebUI.verifyElementText(
		findTestObject(
			'Object Repository/Page_DEMOQA/table_value_by_label',
			['label' : 'Mobile']
		),
		mobileStr
	)

} else {

	// Negative Cases
	WebUI.verifyElementNotPresent(
		findTestObject('Page_DEMOQA/messages_success_form'),
		3
	)

	// Invalid jika firstName kosong
	if (!isFirstNameValid) {
		TestObject firstNameField =
			findTestObject('Object Repository/Page_DEMOQA/input_firstName')

		Boolean firstNameInvalid = WebUI.executeJavaScript(
			"return arguments[0].matches(':invalid');",
			Arrays.asList(WebUI.findWebElement(firstNameField, 10))
		)

		assert firstNameInvalid == true
	}

	// Invalid jika lastName kosong
	if (!isLastNameValid) {
		TestObject lastNameField =
			findTestObject('Object Repository/Page_DEMOQA/input_lastName')

		Boolean lastNameInvalid = WebUI.executeJavaScript(
			"return arguments[0].matches(':invalid');",
			Arrays.asList(WebUI.findWebElement(lastNameField, 10))
		)

		assert lastNameInvalid == true
	}

	// Invalid jika format email tidak sesuai
	if (!isEmailValid) {
		TestObject emailField =
			findTestObject('Object Repository/Page_DEMOQA/input_userEmail')

		Boolean emailInvalid = WebUI.executeJavaScript(
			"return arguments[0].matches(':invalid');",
			Arrays.asList(WebUI.findWebElement(emailField, 10))
		)

		assert emailInvalid == true
	}

	// Invalid jika nomor telepon kurang dari 10 atau kosong
	if (!isMobileValid) {
		TestObject mobileField =
			findTestObject('Object Repository/Page_DEMOQA/input_mobile_phone')

		Boolean mobileInvalid = WebUI.executeJavaScript(
			"return arguments[0].matches(':invalid');",
			Arrays.asList(WebUI.findWebElement(mobileField, 10))
		)

		assert mobileInvalid == true
	}
	
	// Invalid jika gender tidak dipilih
	if (!genderClicked) {
		TestObject genderWrapper =
			findTestObject('Object Repository/Page_DEMOQA/gender_wrapper')
	
		Boolean genderInvalid =
			WebUI.executeJavaScript(
//				"return arguments[0].classList.contains('was-validated') || arguments[0].querySelector('input:invalid') != null;",
				"return arguments[0].querySelector('input:invalid') != null;",
				Arrays.asList(WebUI.findWebElement(genderWrapper, 10))
			)
	
		assert genderInvalid == true
	}
}

WebUI.closeBrowser()
