# blockchain
blockchain - 一个简单的区块链与比特币的实现，包含区块链与比特币的一些基础特性，如去中心化， 挖矿，共识算法，密码学验证，比特币交易，比特币钱包，P2P通讯网络等

### Quick start
```
git clone https://github.com/aaronrao/blockchain.git
cd blockchain
mvn clean install
cd target
java -jar blockchain.jar 8081 7001
java -jar blockchain.jar 8082 7002 ws://localhost:7001

```


### HTTP API

- 查询区块链(GET)

  ```
  curl http://localhost:8081/chain
  ```
  
- 创建钱包(POST)

  ```
  curl http://localhost:8081/wallet/create
  ```
  
- 挖矿(POST)

  ```
  curl http://localhost:8081/mine?address=518522f475ab591cf55d5f79bef629a0
  ```

- 转账交易(POST)

  ```
  curl -H "Content-type:application/json" --data '{"sender": "d4e44223434sdfdgerewfd3fefe9dfe","recipient": "45adiy5grt4544sdfdg454efe54dssq5","amount": 10}' http://localhost:8081/transactions/new
  ```
  
- 查询当前未记账交易(GET)

  ```
  curl http://localhost:8081/transactions/unpacked/get
  ```
  
- 查询当前节点所有钱包(GET)

  ```
  curl http://localhost:8081/wallet/get
  ```
  
- 查询钱包余额(GET)

  ```
  curl http://localhost:8081/wallet/balance/get?address=518522f475ab591cf55d5f79bef629a0
  ```