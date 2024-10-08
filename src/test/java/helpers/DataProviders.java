package helpers;

import org.testng.annotations.DataProvider;

import java.time.LocalDate;

public class DataProviders {

    @DataProvider
    public Object[][] getWrongPhoneNumbers() {
        return new Object[][]{{"380 50 123 1212"}, {"0507222161"}, {"507222161"}, {"asAxcvqwert"}, {""},
                {"+380507777777"}, {"@380507222121"}, {"  "}, {"380871234444"}};
    }

//039 – мобильный оператор Киевстар (крайне редко используется) ????????
//050 – мобильный оператор Водафон (МТС, UMC)
//063 – мобильный оператор Lifecell (Лайф)
//066 – мобильный оператор Водафон (МТС, UMC)
//067 – мобильный оператор Киевстар
//068 – мобильный оператор Киевстар
//073 – сотовый оператор Lifecell (Лайф)
//091 – Укртелекомом
//092 – мобильный оператор ПиплНет (PeopleNet)
//093 – мобильный оператор Lifecell (Лайф)
//094 – мобильный оператор Интертелеком
//095 – мобильный оператор Водафон (МТС, UMC)
//096 – мобильный оператор Киевстар
//097 – мобильный оператор Киевстар
//098 – мобильный оператор Киевстар
//099 – сотовый оператор Водафон (МТС, UMC)

    @DataProvider
    public Object[][] getPhoneNumbers() {
        return new Object[][]{{"380505555555"}, {"380635555555"}, {"380665555555"}, {"380675555555"},
                {"380685555555"}, {"380735555555"}, {"380915555555"}, {"380925555555"}, {"380935555555"}, {"380945555555"}
                , {"380955555555"}, {"380965555555"}, {"380975555555"}, {"380985555555"}, {"380995555555"}};
    }

    @DataProvider
    public Object[][] getSimplePins() {
        return new Object[][]{{"0000"}, {"1234"}, {"1119"}, {"6666"}, {"7654"}, {"4777"}, {""},
                {"12125"}, {"####"}, {"aVcD"}};
    }

    @DataProvider
    public Object[][] getInvalidMonths() {
        return new Object[][]{{"0"}, {"13"}, {"Aa"}, {"111"}, {"-1"}, {"$#"}, {""}};
    }

    @DataProvider
    public Object[][] getInvalidYears() {
        var currentDate = LocalDate.now();
        var previousYear = String.valueOf(currentDate.getYear() - 1);

        return new Object[][]{{previousYear}, {"21111"}, {"211"}, {"0"}, {""}, {"-2023"}, {"aasA"}, {"$$%^"}};
    }

    @DataProvider
    public Object[][] getInvalidCvvs() {
        return new Object[][]{{""}, {"12"}, {"0"}, {"-123"}, {"1234"}, {"aSd"}, {"$%^"}};
    }

    @DataProvider
    public Object[][] full() {
        return new Object[][]{{"phone"}, {"otp_code"}, {"action_resend_code"}, {"action_otp_code_back"},
                {"pin_hash"}, {"init_pin"}, {"action_pin_hash"}, {"action_back_to_enter_pin_hash"},
                {"recovery_card_num"}, {"recovery_card_exp_year"}, {"recovery_card_exp_month"}, {"recovery_card_cvv"}
        };
    }

    @DataProvider
    public Object[][] getStepEnterPhoneValues() {
        return new Object[][]{{"action_resend_code"}, {"action_otp_code_back"},
                {"pin_hash"}, {"init_pin"}, {"action_pin_hash"}, {"action_back_to_enter_pin_hash"},
                {"recovery_card_num"}, {"recovery_card_exp_year"}, {"recovery_card_exp_month"}, {"recovery_card_cvv"}
        };
    }


    @DataProvider
    public Object[][] getStepOtpValues() {
        return new Object[][]{{"phone"},
                {"pin_hash"}, {"init_pin"}, {"action_pin_hash"}, {"action_back_to_enter_pin_hash"},
                {"recovery_card_num"}, {"recovery_card_exp_year"}, {"recovery_card_exp_month"}, {"recovery_card_cvv"}
        };
    }


    @DataProvider
    public Object[][] getStepPinValues() {
        return new Object[][]{{"phone"}, {"otp_code"}, {"action_resend_code"}, {"action_otp_code_back"},
                {"init_pin"}, {"action_back_to_enter_pin_hash"},
                {"recovery_card_num"}, {"recovery_card_exp_year"}, {"recovery_card_exp_month"}, {"recovery_card_cvv"}
        };
    }

    @DataProvider
    public Object[][] getStepInitPinValues() {
        return new Object[][]{{"phone"}, {"otp_code"}, {"action_resend_code"}, {"action_otp_code_back"},
                {"pin_hash"}, {"action_back_to_enter_pin_hash"},
                {"recovery_card_num"}, {"recovery_card_exp_year"}, {"recovery_card_exp_month"}, {"recovery_card_cvv"}
        };
    }

    @DataProvider
    public Object[][] getStepRecoveryValues() {
        return new Object[][]{{"phone"}, {"otp_code"}, {"action_resend_code"}, {"action_otp_code_back"},
                {"pin_hash"}, {"init_pin"}, {"action_pin_hash"}
        };
    }
}
