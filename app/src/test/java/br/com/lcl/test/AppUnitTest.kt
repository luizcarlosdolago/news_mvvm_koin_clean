package br.com.lcl.test

import android.content.Context
import br.com.lcl.data.repositories.NewsRepositoryImpl
import br.com.lcl.domain.common.NoParams
import br.com.lcl.domain.entities.NewsEntity
import br.com.lcl.domain.usecases.*
import br.com.lcl.test.common.SessionUser
import br.com.lcl.test.di.GET_NEWS_DETAIL_USE_CASE
import br.com.lcl.test.di.GET_NEWS_USE_CASE
import br.com.lcl.test.di.SESSION_USER
import br.com.lcl.test.di.allModules
import io.reactivex.observers.TestObserver
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.mockito.Mockito.mock
import retrofit2.HttpException

class AppUnitTest : KoinTest {

    private val sessionUser: SessionUser by inject(qualifier = named(SESSION_USER))
    private val loginUseCase: LoginUseCase by inject()
    private val getNewsUseCase: GetNewsUseCase by inject(qualifier = named(GET_NEWS_USE_CASE))
    private val getNewsDetailUseCase: GetNewsDetailUseCase by inject(qualifier = named(GET_NEWS_DETAIL_USE_CASE))

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(mock(Context::class.java))
        modules(allModules)
    }

    @Test
    fun test_authentication_invalid_credential() {
        val testObserver = TestObserver<String>()
        loginUseCase.call(LoginUseParams("a", "b")).subscribe(testObserver)
        testObserver.assertError { throwable ->
            throwable is HttpException && throwable.code() == 401
        }
    }

    @Test
    fun test_authentication_valid_credential() {
        val testObserver = TestObserver<String>()
        loginUseCase.call(LoginUseParams("devmobile", "uC&+}H4wg?rYbF:")).subscribe(testObserver)
        testObserver.assertValue {
            sessionUser.token = it
            !it.isNullOrEmpty()
        }
    }

    @Test
    fun test_get_news() {
        val loginTestObserver = TestObserver<String>()
        loginUseCase.call(LoginUseParams("devmobile", "uC&+}H4wg?rYbF:")).subscribe(loginTestObserver)
        loginTestObserver.assertValue {
            sessionUser.token = it
            !it.isNullOrEmpty()
        }
        val getNewsTestObserver = getNewsUseCase.call(NoParams()).test()
        getNewsTestObserver.assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun test_get_news_by_invalid_id() {
        val loginTestObserver = TestObserver<String>()
        loginUseCase.call(LoginUseParams("devmobile", "uC&+}H4wg?rYbF:")).subscribe(loginTestObserver)
        loginTestObserver.assertValue {
            sessionUser.token = it
            !it.isNullOrEmpty()
        }
        val getNewsDetailTestObserver = getNewsDetailUseCase.call(GetNewsDetailUseCaseParams(id = "99")).test()
        getNewsDetailTestObserver.assertError { throwable ->
            throwable is HttpException && throwable.code() == 404
        }
    }

    @Test
    fun test_get_news_by_valid_id() {
        val loginTestObserver = TestObserver<String>()
        loginUseCase.call(LoginUseParams("devmobile", "uC&+}H4wg?rYbF:")).subscribe(loginTestObserver)
        loginTestObserver.assertValue {
            sessionUser.token = it
            !it.isNullOrEmpty()
        }

        var news: NewsEntity? = null
        val getNewsTestObserver = getNewsUseCase.call(NoParams()).test()
        getNewsTestObserver.assertValue {
            news = it.firstOrNull()
            it.isNotEmpty()
        }

        news?.let { itNews ->
            val getNewsDetailTestObserver = getNewsDetailUseCase.call(GetNewsDetailUseCaseParams(id = itNews.idDocumento ?: "")).test()
            getNewsDetailTestObserver.assertValue {
                it.documento != null
            }
        }
    }

}