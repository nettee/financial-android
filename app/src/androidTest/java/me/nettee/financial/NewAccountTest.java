package me.nettee.financial;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.nettee.financial.model.account.Account;
import me.nettee.financial.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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

        protected abstract int getCandidateViewText();

        protected abstract void createAccount();

        public void create() {
            clickAdd();
            clickCandidate(getCandidateViewText());
            createAccount();
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
        public void createAccount() {
            onView(allOf(withId(R.id.input_bar_text_content), withParent(withId(R.id.account_remark))))
                    .check(matches(isDisplayed()))
                    .perform(replaceText("钱包B"));

            onView(allOf(withId(R.id.input_bar_amount_content), withParent(withId(R.id.account_balance))))
                    .check(matches(isDisplayed()))
                    .perform(typeText("1234.00"));
        }
    }

    @Test
    public void newAccounts() {

        AccountCreator[] creators = {
                new CashAccountCreator(),
        };

        for (AccountCreator creator : creators) {
            creator.create();
            pressBack();
        }

        while (true) ;
    }

}
