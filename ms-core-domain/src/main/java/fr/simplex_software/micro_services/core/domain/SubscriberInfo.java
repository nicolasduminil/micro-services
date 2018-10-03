package fr.simplex_software.micro_services.core.domain;

public class SubscriberInfo
{
  private String subscriptionName;
  private JmsTopicSubscriberInfo subscriberInfo;

  public SubscriberInfo()
  {
  }

  public SubscriberInfo(String subscriptionName, JmsTopicSubscriberInfo subscriberInfo)
  {
    this.subscriptionName = subscriptionName;
    this.subscriberInfo = subscriberInfo;
  }

  public String getSubscriptionName()
  {
    return subscriptionName;
  }

  public void setSubscriptionName(String subscriptionName)
  {
    this.subscriptionName = subscriptionName;
  }

  public JmsTopicSubscriberInfo getSubscriberInfo()
  {
    return subscriberInfo;
  }

  public void setSubscriberInfo(JmsTopicSubscriberInfo subscriberInfo)
  {
    this.subscriberInfo = subscriberInfo;
  }
}
