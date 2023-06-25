Feature: CucumberJava

  Scenario: Login functionality exists
    Given I have open the browser
    When I open PlanningInformation Form
    When I pick "052" in "טלפון נייד"
#    When I write "פראס" in "שם פרטי"
#    When I write "אבו סנינה" in "שם משפחה"
#    When I write "firas.abu.sneneh@hotmail.com" in "דוא"
#    Then Validate "שם פרטי" text is "פראס"
#    Then Clear "שם פרטי" text
#
    Then Close the chrome


