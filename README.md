# BELUGA FRAMEWORK

![beluga](image/beluga.png)

This README provides a brief overview of my **BDD Test framework** "**_Beluga_**" to automate testing of web,mobile applications and web services.

## Introduction

Cucumber is a testing tool that supports Behavior Driven Development (BDD) framework. It allows you to write test cases in a natural language that is easy to understand for non-technical stakeholders. Selenium is a popular open-source testing tool used for automating web browsers. Appium is an open-source tool used for automating mobile applications. RestAssured is a Java library that provides a domain-specific language (DSL) for testing RESTful web services. Java is a popular programming language used for writing test scripts.

## Getting Started
To get started with Beluga Framework, you need to follow below steps:

* Install Java on your system.
* Install an Integrated Development Environment (IDE) like Eclipse or IntelliJ IDEA.
* Clone the product from Github to your IDE.
* Ensure dependencies from pom.xml file is downloaded into your .m2 folder.

## Execute Test Scripts

To execute follow below steps

If you are executing the Test from IntelliJ IDE, follow below steps
* Click on Run --> Edit Configuration
![beluga](image/runConfig.png)
* Add new TestNg configuration
![beluga](image/testNGConfig.png)
* Add your desired Runner Name
![beluga](image/runnerName.png)
* Add the Test runner(FeatureRunner) in the class
![beluga](image/addClass.png)
* Set the VM options 
![beluga](image/addVMOptions.png)

Set below parameter for VM Options to execute your Test Script

1. [ ] **-DPLATFORM**="_Browser Name or App_" 
2. [ ] **-Dcucumber.filter.tags**="_Tag for the Test case which needs to be executed_"

### Example
**-DPLATFORM="Chrome" -Dcucumber.filter.tags="@JustEatTakeAwayAssessment"**

_**Parameters accepted in -DPLATFORM**_

* Chrome, Firefox, Safari --> for GUI Testing
* Restful --> for API Testing
* iOS, iOSSimulator --> for mobile Testing

## Software products used in Beluga

- **Cucumber**
- **RestAssured**
- **Selenium**
- **Appium**
- **TestNG**




