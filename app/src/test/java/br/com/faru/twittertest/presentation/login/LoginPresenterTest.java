package br.com.faru.twittertest.presentation.login;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginContract.View viewMock;

    LoginPresenter loginPresenter;

    @Before
    public void setup() {
        loginPresenter = new LoginPresenter();
        loginPresenter.setView(viewMock);
    }

    @Test
    public void shouldGoToMainWhenLoginSuccess() {
        loginPresenter.loginSuccess(mock(Result.class));
        verify(viewMock).goToHome();
    }

    @Test
    public void shouldShowMessageWhenLoginFailure() {
        loginPresenter.loginFailure(mock(TwitterException.class));
        verify(viewMock).showMessage(anyString());
    }
}