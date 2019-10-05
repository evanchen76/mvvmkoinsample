package evan.chen.tutorial.mvvmkoinsample.utils

import evan.chen.tutorial.mvvmkoinsample.IProductRepository
import evan.chen.tutorial.mvvmkoinsample.ProductRepository
import evan.chen.tutorial.mvvmkoinsample.api.IProductAPI
import evan.chen.tutorial.mvvmkoinsample.api.ProductResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ProductRepositoryTest {
    private lateinit var repository: IProductRepository

    private var productResponse = ProductResponse()

    @Mock
    private lateinit var productAPI: IProductAPI

    @Mock
    private lateinit var repositoryCallback : IProductRepository.LoadProductCallback

    @Before
    fun setupPresenter() {
        MockitoAnnotations.initMocks(this)

        repository = ProductRepository(productAPI)

        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"
    }

    @Test
    fun getProductTest() {

        //驗證跟Repository取得資料
        val productId = "pixel3"
        repository.getProduct(productId, repositoryCallback)

        //驗證是否有呼叫IProductAPI.getProduct
        val productAPICallbackCaptor = argumentCaptor<IProductAPI.LoadAPICallBack>()
        Mockito.verify<IProductAPI>(productAPI).getProduct(any(), capture(productAPICallbackCaptor))

        //將callback攔截下載並指定productResponse的值。
        productAPICallbackCaptor.value.onGetResult(productResponse)

        //驗證是否有呼叫Callback
        Mockito.verify(repositoryCallback).onProductResult(productResponse)
    }
}