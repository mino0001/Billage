package com.example.app_ui

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

import com.example.contract.MyNFT
import com.example.app_ui.databinding.ActivityNewnftBinding
import com.example.app_ui.databinding.ActivitySendBinding
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.generated.Uint256

import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthChainId
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.websocket.WebSocketService
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import org.web3j.crypto.Credentials
import java.math.BigInteger


class NewnftActivity : ComponentActivity(){
    private lateinit var binding: ActivityNewnftBinding

    val web3j = Web3j.build(HttpService("https://eth-sepolia.g.alchemy.com/v2/musyAUHHyrKtOkx90Ygr7A-q7_1AYfLH"))
    val contractAddress = "0x8481b9693fFabb79463B03566af2391ef150f957"
    lateinit var mynft: MyNFT

    private fun createNft(
        brand: String, modelName: String, manufacturer: String,
        purchaseSource: String, purchaseDate: BigInteger, additionalInfo: String
    ) {
        // Perform the necessary steps to create the NFT using the provided parameters
        // You can use the 'mynft' instance of your contract to interact with the smart contract functions.
        // Make sure you handle exceptions and callbacks appropriately.

        mynft.createNFT(
            brand,
            modelName,
            manufacturer,
            purchaseSource,
            purchaseDate,
            additionalInfo
        )
            .sendAsync()
            .thenApply { transactionReceipt: TransactionReceipt? ->
                runOnUiThread {
                    Toast.makeText(this@NewnftActivity, "트랜잭션이 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show()
                }
                // TransactionReceipt 객체에서 토큰 아이디를 추출
                // val tokenId = extractTokenIdFromReceipt(transactionReceipt)
                // 토큰 아이디를 활용하여 추가 작업 수행
                // processTokenId(tokenId)
            }
            .exceptionally { throwable: Throwable? ->
                runOnUiThread {
                    Toast.makeText(this@NewnftActivity, "트랜잭션 전송 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
                null
            }
    }

    /*
    // TransactionReceipt에서 토큰 아이디 추출
    private fun extractTokenIdFromReceipt(receipt: TransactionReceipt?): BigInteger {
        // TransactionReceipt의 logs에서 이벤트 로그를 추출
        val logs = receipt?.logs ?: emptyList()
        // "NFTCreated" 이벤트 로그 추출
        val nftCreatedEvent = MyNFT.NFTCreatedEventResponse()
        val event = nftCreatedEvent.getEvents(logs).singleOrNull() ?: return BigInteger.ZERO
        // 토큰 아이디 반환
        return event.newTokenId
    }

    // 토큰 아이디 활용하여 추가 작업 수행
    private fun processTokenId(tokenId: BigInteger) {
        // 토큰 아이디를 활용하여 원하는 작업 수행
        // 예시: 토큰 아이디를 UI에 표시하거나 로컬 저장소에 저장 등
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewnftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 생성 버튼
        binding.btnNewMake.setOnClickListener {
            val brand = findViewById<EditText>(R.id.et_brand).text.toString()
            val modelName = findViewById<EditText>(R.id.et_model_name).text.toString()
            val manufacturer = findViewById<EditText>(R.id.et_manufacturer).text.toString()
            val purchaseSource = findViewById<EditText>(R.id.et_po).text.toString()
            val purchaseDate =
                BigInteger(findViewById<EditText>(R.id.et_date_purchase).text.toString())
            val additionalInfo = findViewById<EditText>(R.id.tv_new_memo).text.toString()
            val privateKey = findViewById<EditText>(R.id.et_private_key).text.toString()

            val credentials = Credentials.create(privateKey)
            mynft = MyNFT.load(contractAddress, web3j, credentials, DefaultGasProvider())
            createNft(brand, modelName, manufacturer, purchaseSource, purchaseDate, additionalInfo)
        }

        //취소 버튼
        binding.btnNewCancel.setOnClickListener {
            finish()
        }


//    private fun addNftItem() {
//        binding.btnNewMake.setOnClickListener {
//            //예시. 생성하기에서 입력받은 데이터 리사이클러 뷰에 추가
//            nftList.add(Nft(1,R.drawable.icon_nft2, "NFT1", "more_1", "카테고리1",false))
//        }
//    }
    }
}