package me.nettee.financial;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.nettee.financial.ui.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewAccountTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static abstract class AccountCreator {

        @FunctionalInterface
        protected interface InputOperation {
            void operate();
        }

        protected abstract int getCandidateViewText();

        protected abstract InputOperation[] getInputOperations();

        final void create() {
            clickAdd();
            clickCandidate(getCandidateViewText());

            for (InputOperation operation : getInputOperations()) {
                operation.operate();
                closeSoftKeyboard();
            }

            clickSave();
        }

        private void clickSave() {
            onView(withId(R.id.button_save))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }

        private void clickCandidate(int viewText) {
            onView(withText(viewText))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }

        private void clickAdd() {
            onView(withId(R.id.account_list_add))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }
    }

    private static class CashAccountCreator extends AccountCreator {

        @Override
        public int getCandidateViewText() {
            return R.string.candidate_account_name_cash;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("钱包B"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("1234.00"));
            return new InputOperation[] {remark, balance};
        }
    }

    private static class CreditCardAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_credit_card;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("刷刷卡"));
            InputOperation bankCardNumber = () -> onView(allOf(withId(R.id.input_bar_bank_card_number_content), withParent(withId(R.id.account_bank_card_number))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("994852"));
            InputOperation creditLimit = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_credit_limit))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("5000.00"));
            InputOperation arrears = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_arrears))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("4239.32"));
            return new InputOperation[] {remark, bankCardNumber, creditLimit, arrears};
        }

    }

    private static class DebitCardAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_debit_card;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("存存卡"));
            InputOperation bankCardNumber = () -> onView(allOf(withId(R.id.input_bar_bank_card_number_content), withParent(withId(R.id.account_bank_card_number))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("852340"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("50293.04"));
            return new InputOperation[] {remark, bankCardNumber, balance};
        }
    }

    private static class AlipayAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_alipay;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("支付宝宝"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("4685.30"));
            InputOperation yuebao = () -> onView(allOf(withId(R.id.input_bar_toggle_switch), withParent(withId(R.id.account_yuebao_open))))
                    .check(matches(isDisplayed()))
                    .perform(click());
            return new InputOperation[] {remark, balance, yuebao};
        }
    }

    private static class WeixinAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_weixin;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("微信钱包包"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("45.00"));
            return new InputOperation[] {remark, balance};
        }
    }

    private static class CampusCardAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_campus_card;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("丑丑卡"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("120.01"));
            return new InputOperation[] {remark, balance};
        }
    }

    private static class BusCardAccountCreator extends AccountCreator {

        @Override
        protected int getCandidateViewText() {
            return R.string.candidate_account_name_bus_card;
        }

        @Override
        protected InputOperation[] getInputOperations() {
            InputOperation remark = () -> onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("Hello kitty"));
            InputOperation balance = () -> onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("82.56"));
            return new InputOperation[] {remark, balance};
        }
    }

    @Test
    public void newAccounts() {

        AccountCreator[] creators = {
                new CashAccountCreator(),
                new CreditCardAccountCreator(),
                new DebitCardAccountCreator(),
                new AlipayAccountCreator(),
                new WeixinAccountCreator(),
                new CampusCardAccountCreator(),
                new BusCardAccountCreator(),
        };

        for (AccountCreator creator : creators) {
            creator.create();
            pressBack();
        }

        while (true) ;
    }

}
