/*
 * Copyright 2015 Adaptris Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adaptris.core.socket;

import static com.adaptris.core.AdaptrisMessageFactory.defaultIfNull;
import static com.adaptris.core.util.DestinationHelper.mustHaveEither;

import java.net.Socket;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.adaptris.annotation.AdapterComponent;
import com.adaptris.annotation.ComponentProfile;
import com.adaptris.annotation.DisplayOrder;
import com.adaptris.annotation.InputFieldHint;
import com.adaptris.annotation.Removal;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ProduceDestination;
import com.adaptris.core.ProduceException;
import com.adaptris.core.RequestReplyProducerImp;
import com.adaptris.core.util.DestinationHelper;
import com.adaptris.core.util.LoggingHelper;
import com.adaptris.interlok.util.Closer;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

/**
 * Message Producer implemention for TCP.
 *
 * @config socket-producer
 *
 *
 * @author lchan
 * @author $Author: lchan $
 */
@XStreamAlias("socket-producer")
@AdapterComponent
@ComponentProfile(summary = "Write a arbitrary message to a socket", tag = "producer,socket,tcp",
recommended = {ProduceConnection.class})
@DisplayOrder(order = {"protocolImplementation", "url"})
public class SocketProducer extends RequestReplyProducerImp {

  private static final long DEFAULT_TIMEOUT = 300000;

  /**
   * The protocol implementation used by the socket.
   *
   * @see Protocol
   */
  @Getter
  @Setter
  @NotBlank
  private String protocolImplementation;
  /**
   * The URL used by the socket to send the message.
   */
  @InputFieldHint(expression = true)
  @Getter
  @Setter
  // Needs to be @NotBlank when destination is removed.
  private String url;
  @Getter
  @Setter
  @Deprecated
  @Valid
  @Removal(version = "4.0.0", message = "Use 'url' instead if possible")
  private ProduceDestination destination;

  private transient boolean destinationWarningLogged = false;

  @Override
  public void prepare() throws CoreException {
    DestinationHelper.logWarningIfNotNull(destinationWarningLogged,
        () -> destinationWarningLogged = true, getDestination(),
        "{} uses destination, use 'url' instead", LoggingHelper.friendlyName(this));
    mustHaveEither(getUrl(), getDestination());
  }

  /**
   *
   * @see com.adaptris.core.RequestReplyProducerImp#defaultTimeout()
   */
  @Override
  protected long defaultTimeout() {
    return DEFAULT_TIMEOUT;
  }

  @Override
  protected void doProduce(AdaptrisMessage msg, String endpoint) throws ProduceException {
    sendMessage(msg, endpoint, DEFAULT_TIMEOUT, false);
  }

  @Override
  protected AdaptrisMessage doRequest(AdaptrisMessage msg, String endpoint, long timeout)
      throws ProduceException {
    return sendMessage(msg, endpoint, timeout, true);
  }

  private AdaptrisMessage sendMessage(AdaptrisMessage msg, String url, long timeout,
      boolean expectReply) throws ProduceException {

    Protocol p = null;
    AdaptrisMessage reply = defaultIfNull(getMessageFactory()).newMessage();
    Socket sock = null;
    try {
      Map<Object, Object> m = msg.getObjectHeaders();
      // Use the object metadata socket if available.
      sock = m.containsKey(MetadataConstants.SOCKET_OBJECT_KEY)
          ? (Socket) m.get(MetadataConstants.SOCKET_OBJECT_KEY)
              : retrieveConnection(
                  ProduceConnection.class).createSocket(url);

          sock.setSoTimeout(Math.max(new Long(timeout).intValue(), 0));
          p = (Protocol) Class.forName(protocolImplementation).newInstance();
          p.setSocket(sock);
          p.sendDocument(msg.getPayload());

          if (!p.wasSendSuccess()) {
            throw new Exception("Send of document [" + msg.getUniqueId()
            + "] failed");
          }
          if (expectReply) {
            reply.setPayload(p.getReplyAsBytes());
          }
    }
    catch (Exception e) {
      throw new ProduceException(e);
    }
    finally {
      msg.getObjectHeaders().remove(MetadataConstants.SOCKET_OBJECT_KEY);
      Closer.closeQuietly(sock);
    }
    return reply;
  }

  @Override
  public String endpoint(AdaptrisMessage msg) throws ProduceException {
    return DestinationHelper.resolveProduceDestination(getUrl(), getDestination(), msg);
  }

}
