#include <stdlib.h>
#include <string.h>

#include <chrono>
#include <condition_variable>
#include <iomanip>
#include <iostream>
#include <map>
#include <mutex>
#include <string>
#include <vector>

#include "common.h"

std::mutex g_mtx;
std::condition_variable g_finished;
TpsReportService g_tps;

using namespace rocketmq;

class MyMsgListener : public MessageListenerConcurrently {
 public:
  MyMsgListener() {}
  virtual ~MyMsgListener() {}

  virtual ConsumeStatus consumeMessage(const std::vector<MQMessageExt> &msgs) {
    g_msgCount.store(g_msgCount.load() - msgs.size());
    for (size_t i = 0; i < msgs.size(); ++i) {
      g_tps.Increment();
      // cout << "msg body: "<<  msgs[i].getBody() << endl;
    }

    if (g_msgCount.load() <= 0) {
      std::unique_lock<std::mutex> lck(g_mtx);
      g_finished.notify_one();
    }
    return CONSUME_SUCCESS;
  }
};

int main(int argc, char *argv[]) {
  RocketmqSendAndConsumerArgs info;
  if (!ParseArgs(argc, argv, &info)) {
    exit(-1);
  }
  PrintRocketmqSendAndConsumerArgs(info);
  DefaultMQPushConsumer consumer("please_rename_unique_group_name");
  DefaultMQProducer producer("please_rename_unique_group_name");
  producer.setSessionCredentials("mEbjOEonoo5TREFS",
                                 "xZRP6rejrDjxLxGFHbDfppfJt1S0VJ", "ALIYUN");
  producer.setTcpTransportTryLockTimeout(1000);
  producer.setTcpTransportConnectTimeout(400);
  producer.setNamesrvDomain(info.namesrv_domain);
  producer.setNamesrvAddr(info.namesrv);
  producer.setGroupName("msg-persist-group_producer_sandbox");
  producer.start();

  consumer.setNamesrvAddr(info.namesrv);
  consumer.setGroupName(info.groupname);
  consumer.setSessionCredentials("mEbjOEonoo5TREFS",
                                 "xZRP6rejrDjxLxGFHbDfppfJt1S0VJ", "ALIYUN");
  consumer.setConsumeThreadCount(info.thread_count);
  consumer.setNamesrvDomain(info.namesrv_domain);
  consumer.setConsumeFromWhere(CONSUME_FROM_LAST_OFFSET);

  if (info.syncpush) consumer.setAsyncPull(false);  // set sync pull
  if (info.broadcasting) {
    consumer.setMessageModel(BROADCASTING);
  }

  consumer.setInstanceName(info.groupname);

  consumer.subscribe(info.topic, "*");
  consumer.setConsumeThreadCount(15);
  consumer.setTcpTransportTryLockTimeout(1000);
  consumer.setTcpTransportConnectTimeout(400);

  MyMsgListener msglistener;
  consumer.registerMessageListener(&msglistener);

  try {
    consumer.start();
  } catch (MQClientException &e) {
    cout << e << endl;
  }
  g_tps.start();

  int msgcount = g_msgCount.load();
  for (int i = 0; i < msgcount; ++i) {
    MQMessage msg(info.topic,  // topic
                  "*",         // tag
                  info.body);  // body

    //    std::this_thread::sleep_for(std::chrono::seconds(100000));
    try {
      producer.send(msg);
    } catch (MQException &e) {
      std::cout << e << endl;  // if catch excepiton , need re-send this msg by
                               // service
    }
  }

  {
    std::unique_lock<std::mutex> lck(g_mtx);
    g_finished.wait(lck);
  }
  producer.shutdown();
  consumer.shutdown();
  return 0;
}
