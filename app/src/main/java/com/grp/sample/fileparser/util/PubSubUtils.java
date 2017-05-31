/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;
import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PublishResponse;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.ReceivedMessage;
import com.google.api.services.pubsub.model.Subscription;
import com.google.common.collect.ImmutableList;

/*
 * The PubSubUtils program implements utilities to perform various operation on pubsub
 * such as publishing message, pulling message from topic, creating subscription to topic and client object creation.
 */

public final class PubSubUtils {
  
  /**
   * Pull batch size.
   */
  static final int BATCH_SIZE = 1000;

  /**
   * A name of environment variable for decide whether or not to loop.
   */
  static final String LOOP_ENV_NAME = "LOOP";
  
  private Pubsub client;
  
  /**
   * The application name will be attached to the API requests.
   */
  private final String APP_NAME = "cloud-pubsub-sample-cli/1.0";

  /**
   * Enum representing a resource type.
   */
  public enum ResourceType {
    /**
     * Represents topics.
     */
    TOPIC("topics"),
    /**
     * Represents subscriptions.
     */
    SUBSCRIPTION("subscriptions");
    /**
     * A path representation for the resource.
     */
    private String collectionName;

    /**
     * A constructor.
     *
     * @param collectionName String representation of the resource.
     */
    private ResourceType(final String collectionName) {
      this.collectionName = collectionName;
    }

    /**
     * Returns its collection name.
     *
     * @return the collection name.
     */
    public String getCollectionName() {
      return this.collectionName;
    }
  }

  /**
   * Returns the fully qualified resource name for Pub/Sub.
   *
   * @param resourceType ResourceType.
   * @param project A project id.
   * @param resource topic name or subscription name.
   * @return A string in a form of PROJECT_NAME/RESOURCE_NAME
   */
  private String getFullyQualifiedResourceName(final ResourceType resourceType,
      final String resource) {
    return String.format("projects/%s/%s/%s", "sample-prep",
        resourceType.getCollectionName(), resource);
  }

  /**
   * Builds a new Pubsub client with default HttpTransport and JsonFactory and returns it.
   *
   * @return Pubsub client.
   * @throws IOException when we can not get the default credentials.
   */
  private Pubsub getClient() throws IOException {
    return getClient(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
  }

  /**
   * Builds a new Pubsub client and returns it.
   *
   * @param httpTransport HttpTransport for Pubsub client.
   * @param jsonFactory JsonFactory for Pubsub client.
   * @return Pubsub client.
   * @throws IOException when we can not get the default credentials.
   */
  private Pubsub getClient(final HttpTransport httpTransport, final JsonFactory jsonFactory)
      throws IOException {
    Preconditions.checkNotNull(httpTransport);
    Preconditions.checkNotNull(jsonFactory);
    /*InputStream resourceAsStream =
        PubSubUtils.class.getClassLoader().getResourceAsStream("pull-test-subscriber.json");*/

    //GoogleCredential credential = GoogleCredential.fromStream(resourceAsStream);
   
    GoogleCredential credential = GoogleCredential.getApplicationDefault(httpTransport,
      jsonFactory);
     
    if (credential.createScopedRequired()) {
      credential = credential.createScoped(PubsubScopes.all());
    }
    if(client == null){
      client = new Pubsub.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME)
          .build();
    }
    return client;
  }
  
  /**
   * Publishes the given message to the given topic.
   *
   * @param client Cloud Pub/Sub client.
   * @param args Command line arguments.
   * @throws IOException when Cloud Pub/Sub API calls fail.
   */
  public void publishMessage(String topicName, String message)
          throws IOException {
      String topic = getFullyQualifiedResourceName(
              ResourceType.TOPIC, topicName);
      PubsubMessage pubsubMessage = new PubsubMessage()
              .encodeData(message.getBytes("UTF-8"));
      List<PubsubMessage> messages = ImmutableList.of(pubsubMessage);
      PublishRequest publishRequest = new PublishRequest();
      publishRequest.setMessages(messages);
      PublishResponse publishResponse = getClient().projects().topics()
              .publish(topic, publishRequest)
              .execute();
      List<String> messageIds = publishResponse.getMessageIds();
      if (messageIds != null) {
          for (String messageId : messageIds) {
              System.out.println("Published with a message id: " + messageId);
          }
      }
  }
  
  /**
   * Creates a new subscription.
   *
   * @param client Cloud Pub/Sub client.
   * @param args Arguments as an array of String.
   * @throws IOException when Cloud Pub/Sub API calls fail.
   */
  public void createSubscription(String subName, String topicName)
      throws IOException {
    String subscriptionName =
       getFullyQualifiedResourceName(PubSubUtils.ResourceType.SUBSCRIPTION, subName);
    Subscription subscription =
        new Subscription().setTopic(getFullyQualifiedResourceName(
            PubSubUtils.ResourceType.TOPIC, topicName));
    /*subscription = getClient().projects().subscriptions().get(subscriptionName).execute();
    if(subscription == null){*/
      subscription =
          getClient().projects().subscriptions().create(subscriptionName, subscription).execute();
      System.out.printf("Subscription %s was created.\n", subscription.getName());
      System.out.println(subscription.toPrettyString());
    /*}else{
      System.out.println("Subscription Exists : \\n"+subscription.toPrettyString());
    }*/
  }
  
  /**
   * Keeps pulling messages from the given subscription.
   *
   * @param client Cloud Pub/Sub client.
   * @param args Arguments as an array of String.
   * @return 
   * @throws IOException when Cloud Pub/Sub API calls fail.
   */
  public List<String> pullMessages(String subName) throws IOException {
    String subscriptionName =
        getFullyQualifiedResourceName(PubSubUtils.ResourceType.SUBSCRIPTION, subName);
    PullRequest pullRequest =
        new PullRequest().setReturnImmediately(false).setMaxMessages(BATCH_SIZE);
    List<String> messages = new ArrayList<String>();

    do {
      PullResponse pullResponse;
      pullResponse =
          getClient().projects().subscriptions().pull(subscriptionName, pullRequest).execute();
      List<String> ackIds = new ArrayList<>(BATCH_SIZE);
      List<ReceivedMessage> receivedMessages = pullResponse.getReceivedMessages();
      if (receivedMessages != null) {
        for (ReceivedMessage receivedMessage : receivedMessages) {
          PubsubMessage pubsubMessage = receivedMessage.getMessage();
          if (pubsubMessage != null && pubsubMessage.decodeData() != null) {
            messages.add(new String(pubsubMessage.decodeData(), "UTF-8"));
          }
          ackIds.add(receivedMessage.getAckId());
        }
        AcknowledgeRequest ackRequest = new AcknowledgeRequest();
        ackRequest.setAckIds(ackIds);
        getClient().projects().subscriptions().acknowledge(subscriptionName, ackRequest).execute();
      }
    } while (System.getProperty(LOOP_ENV_NAME) != null);
    
    return messages;
  }
}
