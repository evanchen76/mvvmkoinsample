package evan.chen.tutorial.mvvmkoinsample

import evan.chen.tutorial.mvvmkoinsample.api.ProductAPI
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        val productAPI = ProductAPI()
        val productRepository = ProductRepository(productAPI)

        ProductViewModel(productRepository)
    }
}


