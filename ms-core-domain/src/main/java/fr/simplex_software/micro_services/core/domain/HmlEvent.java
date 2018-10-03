package fr.simplex_software.micro_services.core.domain;

import java.io.*;

public class HmlEvent implements Serializable
{
  private String subscriptionName;
  private String messageId;
  private String payload;

  public HmlEvent()
  {
  }

  public HmlEvent(String subscriptionName, String messageId, String payload)
  {
    this.subscriptionName = subscriptionName;
    this.messageId = messageId;
    this.payload = payload;
  }

  public String getMessageId()
  {
    return messageId;
  }

  public String getPayload()
  {
    return payload;
  }

  public void setMessageId(String messageId)
  {
    this.messageId = messageId;
  }

  public void setPayload(String payload)
  {
    this.payload = payload;
  }

  public String getSubscriptionName()
  {
    return subscriptionName;
  }

  public void setSubscriptionName(String subscriptionName)
  {
    this.subscriptionName = subscriptionName;
  }
}
