package fr.simplex_software.micro_services.core.domain;

import java.net.*;

public class JmsTopicSubscriberInfo
{
  private String messageSelector;
  private String clientId;
  private String callback;

  public JmsTopicSubscriberInfo()
  {
  }

  public JmsTopicSubscriberInfo(String messageSelector, String clientId, String callback) throws MalformedURLException
  {
    this.messageSelector = messageSelector;
    this.callback = callback;
    this.clientId = clientId;
  }

  public String getMessageSelector()
  {
    return messageSelector;
  }

  public void setMessageSelector(String messageSelector)
  {
    this.messageSelector = messageSelector;
  }

  public String getCallback()
  {
    return callback;
  }

  public void setCallback(String callback)
  {
    this.callback = callback;
  }

  public String getClientId()
  {
    return clientId;
  }

  public void setClientId(String clientId)
  {
    this.clientId = clientId;
  }
}
