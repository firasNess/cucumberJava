Feature: CucumberJava
#  @in_dev
  Scenario: Login functionality exists -skipped test
    When I open PlanningInformation Form
    When I pick "052" in "טלפון נייד"
    When I write "פראס" in "שם פרטי"
    When I write "אבו סנינה" in "שם משפחה"
    When I write "firas.abu.sneneh@hotmail.com" in "דוא"
    Then Validate "שם פרטי" text is "פראס"
    Then Clear "שם פרטי" text

  Scenario: Login functionality exists -not skipped test
    When I open PlanningInformation Form
    When I pick "054" in "טלפון נייד"
    When I write "sdfsd" in "שם פרטי"
    When I write "sdfsdf" in "שם משפחה"
    When I write "sdfsdfsdf" in "דוא"
    Then Validate "שם פרטי" text is "sdfsd"
    Then Clear "שם פרטי" text
#


