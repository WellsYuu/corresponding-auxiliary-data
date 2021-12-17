# blockchain
blockchain - һ���򵥵�����������رҵ�ʵ�֣���������������رҵ�һЩ�������ԣ���ȥ���Ļ��� �ڿ󣬹�ʶ�㷨������ѧ��֤�����رҽ��ף����ر�Ǯ����P2PͨѶ�����

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

- ��ѯ������(GET)

  ```
  curl http://localhost:8081/chain
  ```
  
- ����Ǯ��(POST)

  ```
  curl http://localhost:8081/wallet/create
  ```
  
- �ڿ�(POST)

  ```
  curl http://localhost:8081/mine?address=518522f475ab591cf55d5f79bef629a0
  ```

- ת�˽���(POST)

  ```
  curl -H "Content-type:application/json" --data '{"sender": "d4e44223434sdfdgerewfd3fefe9dfe","recipient": "45adiy5grt4544sdfdg454efe54dssq5","amount": 10}' http://localhost:8081/transactions/new
  ```
  
- ��ѯ��ǰδ���˽���(GET)

  ```
  curl http://localhost:8081/transactions/unpacked/get
  ```
  
- ��ѯ��ǰ�ڵ�����Ǯ��(GET)

  ```
  curl http://localhost:8081/wallet/get
  ```
  
- ��ѯǮ�����(GET)

  ```
  curl http://localhost:8081/wallet/balance/get?address=518522f475ab591cf55d5f79bef629a0
  ```