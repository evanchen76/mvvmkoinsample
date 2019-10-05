package evan.chen.tutorial.mvvmkoinsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import evan.chen.tutorial.mvvmkoinsample.databinding.ActivityProductBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProductActivity : AppCompatActivity() {

    private val productId = "pixel3"

    //加上by viewModel
    private val productViewModel: ProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val dataBinding = DataBindingUtil.setContentView<ActivityProductBinding>(this, R.layout.activity_product)

        //改用Koin，註解以下
//        val productAPI = ProductAPI()
//        val productRepository = ProductRepository(productAPI)
//        productViewModel =
//            ViewModelProviders.of(this, ProductViewModelFactory(productRepository)).get(ProductViewModel::class.java)

        dataBinding.productViewModel = productViewModel

        //加這一段就可以讓model有變就更新回UI
        dataBinding.lifecycleOwner = this

        productViewModel.getProduct(productId)

        productViewModel.alertText.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it).setTitle("錯誤")
                builder.show()
            }
        })

        productViewModel.buySuccessText.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show();
            }
        })
    }

}
