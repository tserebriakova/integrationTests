**Test suite location:** src/test/resources/test.xml (provide classes for test, or packages)
**Tests location:** src/test/java/tests/endpoints/*
**Env config location:** src/main/resources/dev.properties

**How to run?**

**To run xml suite run command:** gradle clean test -Denv=prod3 -Pprod , where Denv=prod3  (env config) and -Pprod (prod = property to run suites, see build.gradle  line 52)

**To run single test:** gradle clean test -Dtest=ExistingClientFlowTest   = to run single test, where ExistingClientFlowTest == Class Name


Important note:
if you run not onboarding tests add this to the test block in suite xml file:
This will login once and will reuse token (see examples in stage_suite.xml, prod_suite.xml)

        <groups>
            <run>
                <include name="auth-client" />
            </run>
        </groups>

**To get report after tests need to run:** gradle allureReport allureServe
Where allureReport - will generate report
And allureServe - will run the generated report

report will be generated to folder /build/allure-report/# integrationTests
